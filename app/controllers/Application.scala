package controllers

import javax.inject.Inject

import actors.HelloActor
import actors.SayHello
import akka.actor._
import akka.util.Timeout
import com.github.levkhomich.akka.tracing.play.PlayControllerTracing
import com.typesafe.config.ConfigFactory
import play.api.mvc._

import scala.concurrent.duration._

class Application @Inject() (actorSystem: ActorSystem) extends Controller with PlayControllerTracing   {

  implicit val sentimentAskTimeout: Timeout = Duration(1, SECONDS)
  val system = ActorSystem.create("TracingBasics", ConfigFactory.load("application"))
  val helloActor = system.actorOf(Props[HelloActor], "HelloActor")

  def index = Action {implicit request =>
    //val sayHello = SayHello("aaaa")
    //helloActor ! sayHello

    //trace.start(request, actorSystem.name)
    //trace.record(request, actorSystem.name)
    //trace.sample(sayHello, "dfree via!")
    //trace.record(sayHello, "Start processing")
    //trace.recordKeyValue(sayHello, "longAnswer", "ADDDDDD")

    Ok(views.html.index("Your new application is ready."))
  }
}

