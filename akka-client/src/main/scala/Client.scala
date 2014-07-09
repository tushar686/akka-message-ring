import akka.actor.ActorSystem
import akka.actor.Props
import akka.routing.ActorRefRoutee
import akka.routing.Router
import akka.routing.RoundRobinRoutingLogic

import akka.actor._
import akka.actor.ActorDSL._
import scala.concurrent.Future
import akka.routing._
import akka.routing.RoundRobinRouter

object Client {

  def main(args: Array[String]) {
	  val system = ActorSystem()
	 
	  val ringrootServer = "10.198.80.56:2551"
	  //val ServerWithRingInfo = system.actorSelection("akka.tcp://Server@"+ ringrootServer +"/user/serverWithRingInfo")
	  val ServerWithoutRingInfo = system.actorSelection("akka.tcp://Server@"+ ringrootServer +"/user/serverWithoutRingInfo")
	  (1 to 100) foreach { i=> 
	    ServerWithoutRingInfo ! "Index = " + i + " root10.198.80.56:2551, 2-10.198.80.56:2552, 3-10.198.80.56:2553, 4-10.198.80.56:2554" 	    
	  }
  }
  
}