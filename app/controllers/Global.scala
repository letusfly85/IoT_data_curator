
import com.github.levkhomich.akka.tracing.play.TracingSettings

import com.google.inject.AbstractModule
import com.google.inject.name.Names

class GlobalZipkin extends AbstractModule with TracingSettings {
  def configure() = {
  }

}