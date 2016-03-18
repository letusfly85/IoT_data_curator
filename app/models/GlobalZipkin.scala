package models

import javax.inject._
import com.github.levkhomich.akka.tracing.play.TracingSettings
import play.api.inject._
import play.api.mvc._
import scala.concurrent._
import play.api._

trait GlobalZipkin {
  def write()
}

@Singleton
class GlobalZipkinImpl extends GlobalZipkin with TracingSettings {
  def write(): Unit = {
    Logger.debug("writing program!!!")
  }


  override def onRouteRequest(request: RequestHeader): Option[Handler] =
    super.onRouteRequest(request).map {
      case alreadyTraced: TracedAction =>
        alreadyTraced
      case alreadyTagged: EssentialAction with RequestTaggingHandler =>
        new TracedAction(alreadyTagged) {
          override def tagRequest(request: RequestHeader): RequestHeader =
            super.tagRequest(alreadyTagged.tagRequest(request))
        }
      case action: EssentialAction =>
        new TracedAction(action)
      case handler =>
        handler
    }

  override def onRequestCompletion(request: RequestHeader): Unit = {
    //trace.record(request, TracingAnnotations.ServerSend)
    super.onRequestCompletion(request)
  }

  override def onError(request: RequestHeader, ex: Throwable): Future[Result] = {
    trace.record(request, ex)
    super.onError(request, ex)
  }
}


