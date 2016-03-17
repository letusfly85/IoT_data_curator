
package actors

import akka.actor._
import com.github.levkhomich.akka.tracing.{TracingExtension, BaseTracingSupport, TracingSupport}
import com.github.levkhomich.akka.tracing.play.{PlayActorTracing}

object HelloActor {
  def props = Props[HelloActor]

  final case class SayHello(name: String) extends TracingSupport
}

class HelloActor extends Actor with PlayActorTracing with ActorLogging with TracingSupport {
  import HelloActor._

  def receive = {
    case SayHello(name: String) =>
      println("ADAEDFASDFASDFASFDASF")
      val hoge = TracingExtension.apply(ActorSystem.apply("hello"))

      sender() ! "Hello, " + name
    case _ =>
      sender() ! "Something Wrong!"
  }
}
