package client

import akka.actor._
import akka.actor.ActorDSL._
import scala.concurrent.Future

 object Client_Sender extends App {
	implicit val system = ActorSystem("Client")
	val root = "10.198.80.147"
   val a = actor(new Act {
      whenStarting {
        val server = system.actorSelection("akka.tcp://Server@"+ root +":2552/user/server")
       	(1 to 10).par foreach {i=> Future {server ! "msg= " + i + "root-" + root + ":Zeeshan-10.198.81.63:Rupin-10.198.80.73:Priti-10.198.81.132"}(Config.executionContext) } 
      }     
   })
}

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContextExecutorService

object Config extends Config(numberOfthreads = 200)

class Config(numberOfthreads: Int) {
  val threadPool:ExecutorService = Executors.newScheduledThreadPool(numberOfthreads)
  implicit val executionContext: ExecutionContextExecutorService = ExecutionContext.fromExecutorService(threadPool)
}