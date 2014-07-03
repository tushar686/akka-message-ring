import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContextExecutorService

object Config extends Config(numberOfthreads = 200)

class Config(numberOfthreads: Int) {
  val threadPool:ExecutorService = Executors.newScheduledThreadPool(numberOfthreads)
  implicit val executionContext: ExecutionContextExecutorService = ExecutionContext.fromExecutorService(threadPool)
}