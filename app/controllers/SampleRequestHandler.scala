package controllers

import javax.inject.Inject
import com.github.levkhomich.akka.tracing.play.PlayControllerTracing
import play.api.http._
import play.api.mvc._
import play.api.routing.Router

class SampleRequestHandler @Inject() (router: Router) extends HttpRequestHandler with PlayControllerTracing  {
  def handlerForRequest(request: RequestHeader) = {
    router.routes.lift(request) match {
      case Some(handler) =>
        trace.record(request, play.libs.Akka.system().name)
        trace.recordKeyValue(request, "request", handler.toString)
        (request, handler)
      case None => (request, Action(Results.NotFound))
    }

  }
}