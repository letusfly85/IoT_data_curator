package actors

/**
  * Created by wada on 16/03/17.
  */

import javax.inject.Inject

import actors.HelloActor.SayHello
import akka.actor.{Actor, Props}
import play.api._
import play.api.inject.{ApplicationLifecycle, Module, Binding}
import play.api.libs.concurrent.Akka

import scala.concurrent.Future

/**
  * Manages the creation of actors.
  *
  * Discovered by Play in the `play.plugins` file.
  */
class MyActors extends Module {
  def bindings(environment: Environment, configuration: Configuration): Seq[Binding[_]] = {
    Seq(
      bind[Actors].to[MyComponentImpl]
    )
  }

}

trait Actors

class MyComponentImpl @Inject()(lifecycle: ApplicationLifecycle) extends Actors with Actor {
  // previous contents of Plugin.onStart
  lifecycle.addStopHook { () =>
    // previous contents of Plugin.onStop
    Future.successful(())
  }

  def receive = {
    case SayHello(name: String) =>
      println("ADAEDFASDFASDFASFDASF")
      sender() ! "Hello, " + name
    case _ =>
      sender() ! "Something Wrong!"
  }
}