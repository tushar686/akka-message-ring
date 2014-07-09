import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.ActorSystem
import akka.actor.Props
import akka.dispatch._
import com.typesafe.config.ConfigFactory
import scala.concurrent.Future

class ServerWithRingInfo extends Actor with RequiresMessageQueue[BoundedMessageQueueSemantics] {
  var total_messages = 0;
  def receive = {
    case msg: String => incrementTotalMsgCount
    					println("\ntotal= " + total_messages + " message= " + msg)
      					sendMessageToNextMachineInTheRing(msg)
  }
  
  def incrementTotalMsgCount = {total_messages = total_messages + 1}
  
  def sendMessageToNextMachineInTheRing(msg: String) = {
	  if(!msg.contains("back")) {
	    ServerWithRingInfo.serverWithRingInfo ! msg  
	  }
  }
  
}


object ServerWithRingInfo {
  val system = ActorSystem("Server")
  var self = system.actorOf(Props[ServerWithRingInfo].withDispatcher("server-dispatcher"), name = "serverWithRingInfo")
  self ! "Test back message"
  println("Server ready")
  
  val config = ConfigFactory.load()
  val nextIpInTheRing =  config.getString("nextIP.in.ring")  
  val serverWithRingInfo = ServerWithRingInfo.system.actorSelection("akka.tcp://Server@" + nextIpInTheRing +"/user/serverWithRingInfo")
}