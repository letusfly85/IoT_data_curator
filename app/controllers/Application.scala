package controllers

import actors.{HelloActor}
import actors.SayHello
import akka.actor._
import akka.util.Timeout

import com.github.levkhomich.akka.tracing.play.{PlayControllerTracing}
import com.typesafe.config.ConfigFactory

import play.api.mvc._

import scala.concurrent.duration._

class Application extends Controller with PlayControllerTracing  {

  implicit val sentimentAskTimeout: Timeout = Duration(1, SECONDS)
  val actorSystem = ActorSystem.create("TracingBasics", ConfigFactory.load("application"))
  val helloActor = actorSystem.actorOf(Props[HelloActor], "HelloActor")

  def index = Action {implicit request =>
    val sayHello = SayHello("aaaa")
    helloActor ! sayHello

    trace.record(request, play.libs.Akka.system().name)
    trace.sample(sayHello, "dfree via!")
    trace.record(sayHello, "Start processing")
    trace.recordKeyValue(sayHello, "longAnswer", "ADDDDDD")

    Ok(views.html.index("Your new application is ready."))
  }
}

