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
	 
	  val ringrootServer = "10.198.80.35:2550"
	  val server = system.actorSelection("akka.tcp://Server@"+ ringrootServer +"/user/server")
	  (1 to 100000).par foreach {i=> server ! "msg = " + i + "root" + ringrootServer}
  }
  
}