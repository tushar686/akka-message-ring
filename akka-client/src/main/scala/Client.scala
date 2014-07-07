package client

import akka.actor._
import akka.actor.ActorDSL._
import scala.concurrent.Future
import akka.routing._

class Client_Actor extends Actor {
   def receive = {
     case msg: String => 
   }
}


object Client {
  
   def main(args: Array[String]) {
	   sendMessages
   }

   def sendMessages = {
	  val root = "10.198.80.147:2551"
	  val system = ActorSystem("Client")
	  val client = system.actorOf(Props[Client_Actor], name = "client")
	  
	  val server = system.actorSelection("akka.tcp://Server@"+ root +"/user/server")
	  (1 to 1000000) foreach {i=> server ! "msg = " + i + "root" + root + "" 
	    									if(i % 5000 == 0) Thread.sleep(1000)
	    					}
	}
}