import controllers.IotDataController
import org.specs2.runner._
import org.junit.runner._
import play.api.libs.json.Json

import play.api.test._

/**
  *
  *
  */
@RunWith(classOf[JUnitRunner])
object IotDataControllerSpec extends PlaySpecification {
  val controller = new IotDataController

  "IotDataController#create" should {
    "create IoTData on dynalite and get success status" in {
      val request = FakeRequest().withJsonBody(Json.parse("""{"id":"10"}"""))
      val result = controller.create().apply(request)
      val bodyText = contentAsString(result)

      bodyText must be equalTo(s"""{"id":"10","status":"success"}""")
    }
  }

  "IotDataController#findBy" should {
    "find IoTData on dynalite and get records" in {
      val request = FakeRequest().withJsonBody(Json.parse("""{"id":"10"}"""))
      val result = controller.findBy().apply(request)
      val bodyText = contentAsString(result)

      println(bodyText)
      bodyText must be equalTo(s"""{"detail":"100","id":"10"}""")
    }
  }
}
