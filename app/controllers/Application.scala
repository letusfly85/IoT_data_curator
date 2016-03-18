package controllers

import javax.inject.{Singleton, Inject}

import actors.{Actors, HelloActor}
import actors.HelloActor.SayHello
import akka.actor._
import akka.util.Timeout
import com.github.levkhomich.akka.tracing.BaseTracingSupport

import com.github.levkhomich.akka.tracing.play.{TracingSettings, PlayControllerTracing}
//import models.GlobalZipkin
import modules.GlobalZipkinModule
import play.api.mvc._
import play.libs.Akka

import scala.concurrent.duration._


@Singleton
class Application @Inject() (settings: GlobalZipkinModule) extends Controller with PlayControllerTracing  {

  implicit val sentimentAskTimeout: Timeout = Duration(100, SECONDS)
  val actorSystem: ActorSystem = Akka.system()
  println(play.libs.Akka.system().settings.config.hasPath("akka.tracing.host"))

  val hogeActor = actorSystem.actorOf(HelloActor.props, "hoge")
  val sh = SayHello("aaaa")

  def index = Action {
    hogeActor ! sh
    Ok(views.html.index("Your new application is ready."))
  }

}
