package controllers

import actors.{Actors, HelloActor}
import actors.HelloActor.SayHello
import akka.actor._
import akka.util.Timeout

import com.github.levkhomich.akka.tracing.play.PlayControllerTracing
import play.api.mvc._
import play.libs.Akka
import com.github.levkhomich.akka.tracing.play.TracingSettings

import scala.concurrent.duration._

class Application  extends Controller with PlayControllerTracing with TracingSettings {

  implicit val sentimentAskTimeout: Timeout = Duration(100, SECONDS)
  val system: ActorSystem = Akka.system()

  val hogeActor = system.actorOf(HelloActor.props, "hello")
  val sh = SayHello("aaaa")

  def index = Action {
    hogeActor ! sh
    Ok(views.html.index("Your new application is ready."))
  }

}
