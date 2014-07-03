package client

import akka.actor._
import akka.actor.ActorDSL._

 object Client_Sender extends App {
   implicit val system = ActorSystem("Client")

   val server = system.actorSelection("akka.tcp://Server@10.198.80.147:2552/user/server")

   /*val a = actor(new Act {
      whenStarting {
        for(i <- 1 to 10000) yield {server ! "msg= " + i + ":Zeeshan-10.198.81.62:Rupin-10.198.81.142:Priti-10.198.81.152" } 
      }     
   })*/
   
   def floatMeInTheRing(msg: String) = {
     server ! msg
   }
}