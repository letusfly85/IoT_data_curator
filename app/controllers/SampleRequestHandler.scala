package controllers

import javax.inject.Inject

import akka.actor.ActorSystem
import com.github.levkhomich.akka.tracing.play.PlayControllerTracing
import play.api.http._
import play.api.mvc._
import play.api.routing.Router

class SampleRequestHandler @Inject() (router: Router, actorSystem: ActorSystem) extends HttpRequestHandler with PlayControllerTracing  {
  def handlerForRequest(request: RequestHeader) = {
    router.routes.lift(request) match {
      case Some(handler) =>
        trace.record(request, actorSystem.name)
        trace.recordKeyValue(request, "request", handler.toString)
        (request, handler)
      case None => (request, Action(Results.NotFound))
    }

  }
}