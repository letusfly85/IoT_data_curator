package modules

import com.github.levkhomich.akka.tracing.play.TracingSettings
import models.{GlobalZipkinImpl, GlobalZipkin}
import com.google.inject.AbstractModule
import play.api._

class GlobalZipkinModule extends AbstractModule with TracingSettings {

   override def configure() = {

     println("ADFASDFASDFASDFASDFASDFASDFASDFASDFASDF")

     bind(classOf[GlobalZipkin]).to(classOf[GlobalZipkinImpl])

  }

}
