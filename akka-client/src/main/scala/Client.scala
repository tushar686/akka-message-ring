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
	 
	  val ringrootServer = "10.198.80.35:2551"
	  val server = system.actorSelection("akka.tcp://Server@"+ ringrootServer +"/user/server")
	  (1 to 1000000).par foreach {i=> server ! "msg = " + i + "root" + ringrootServer + ", 2-10.198.80.35:2552, 3-10.198.80.35:2553, 4-10.198.80.35:2554"}
  }
  
}