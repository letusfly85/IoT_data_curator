package actors

import akka.actor._
import com.github.levkhomich.akka.tracing.play.PlayActorTracing
import com.github.levkhomich.akka.tracing.{TracingActorLogging, TracingSupport}

import scala.util.Random


final case class SayHello(name: String) extends TracingSupport

class HelloActor extends Actor with TracingActorLogging with PlayActorTracing {

  override def receive: Receive = {
    case msg @ SayHello(name: String) =>
      println("start")

      // sample message
      trace.sample(msg, "test service name")

      // annotate trace using different methods
      trace.record(msg, "Start processing")
      trace.recordKeyValue(msg, "longAnswer", Random.nextLong())

      // log to trace
      log.debug("request: " + msg)

      println("end")

      try {
        // do something heavy
        Thread.sleep(Random.nextInt(200))
        val result = SayHello("fuga")

        // mark response
        sender ! result.asResponseTo(msg)
      } catch {
        case e: Exception =>
          // record stack trace
          trace.record(msg, e)
      }
  }
}
