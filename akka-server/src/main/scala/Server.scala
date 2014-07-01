import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.ActorSystem
import akka.actor.Props

class Server extends Actor {
  def receive = {
    case msg: String => println("########################received " + msg)
      					sendMessageToNextMachineInTheRing(msg)    					
    case _ => println("Received unknown msg")
  }
  
  def sendMessageToNextMachineInTheRing(msg: String) = {
    val msgList = msg.split(":").toList
    msgList match {
      case _ if msgList.size == 1 => sendMessageBackToRoot(msgList) 
      case _ if msgList.size > 1 =>  sendMessageToNextIp(msgList)
      case _ => println()
    }
  }
  
  def sendMessageToNextIp(msgList: List[String]) = {
    val ips = msgList.tail
    println("************************forwarding to " + ips.head)
	 val server = Server.system.actorSelection("akka.tcp://Server@" + ips.head.split("-")(1) + ":2552/user/server")
	 ips match {
		case _ if ips.size == 1 =>
		  server ! msgList.head
		case _ if ips.size > 1 =>
		  server ! msgList.head + ":" + ips.tail.mkString(":")
	 }
  }
  
  def sendMessageBackToRoot(msgList: List[String]) = {
	 if(!msgList.head.contains("back")) {
	   val server = Server.system.actorSelection("akka.tcp://Server@10.198.81.101:2552/user/server")
	   println("========================sending back to Root for Ring Conpletion")
	   server ! msgList.head + " back"
	 }
  }
  	
}

object Server extends App {
  val system = ActorSystem("Server")
  val server = system.actorOf(Props[Server], name = "server")
  server ! "Hello"
  println("Server ready")
}