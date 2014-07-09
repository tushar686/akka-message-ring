import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.ActorSystem
import akka.actor.Props
import akka.dispatch._

class ServerWithoutRingInfo extends Actor with RequiresMessageQueue[BoundedMessageQueueSemantics] {
  var total_messages = 0;
  val IP_DELIMITER = ","
  def receive = {
    case msg: String => total_messages = total_messages + 1
    					println("\ntotal= " + total_messages)
      					println("########################received " + msg)
      					sendMessageToNextMachineInTheRing(msg)    					
    case _ => println("\nReceived unknown msg")
  }
  
  def sendMessageToNextMachineInTheRing(msg: String) = {
    val msgList = msg.split(IP_DELIMITER).toList
    msgList match {
      case _ if msgList.size == 1 => sendMessageBackToRoot(msgList) 
      case _ if msgList.size > 1 =>  sendMessageToNextIp(msgList)
      case _ => println()
    }
  }
  
  def sendMessageToNextIp(msgList: List[String]) = {
    val ips = msgList.tail
    println("************************forwarding to " + ips.head)
	 val serverWithoutRingInfo = ServerWithoutRingInfo.system.actorSelection("akka.tcp://Server@" + ips.head.split("-")(1) + "/user/serverWithoutRingInfo")
	 ips match {
		case _ if ips.size == 1 =>
		  serverWithoutRingInfo ! msgList.head
		case _ if ips.size > 1 =>
		  serverWithoutRingInfo ! msgList.head + IP_DELIMITER + ips.tail.mkString(IP_DELIMITER)
	 }
  }
  
  def sendMessageBackToRoot(msgList: List[String]) = {
	 if(!msgList.head.contains("back")) {
	   val rootIp = msgList.head.split("root").toList.last
	   val serverWithoutRingInfo = ServerWithoutRingInfo.system.actorSelection("akka.tcp://Server@" + rootIp +"/user/serverWithoutRingInfo")
	   println("========================sending back to Root for Ring Conpletion")
	   serverWithoutRingInfo ! msgList.head + " back"
	 }
  }
  	
}


object ServerWithoutRingInfo extends App {
  val system = ActorSystem("Server")
  val serverWithoutRingInfo = system.actorOf(Props[ServerWithoutRingInfo].withDispatcher("server-dispatcher"), name = "serverWithoutRingInfo")
  serverWithoutRingInfo ! "Test back message"
  println("Server ready")
}
