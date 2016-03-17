package controllers

import javax.inject.Inject

import actors.HelloActor
import akka.actor._
import akka.util.Timeout
import com.github.levkhomich.akka.tracing.play.PlayControllerTracing
import models.IoTData
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.duration._

class IotDataController @Inject() (system: ActorSystem) extends Controller with PlayControllerTracing {

  implicit val sentimentAskTimeout: Timeout = Duration(10, SECONDS)
  val actor = system.actorOf(HelloActor.props, "hello22")

  val form = Form("id" -> text)

  def create = Action { implicit request =>
    val id = form.bindFromRequest.get
    IoTData.create(Integer.parseInt(id))

    Ok(Json.toJson(Json.parse(s"""{"id":"${id}", "status":"success"}""")))
  }

  def findBy = Action { implicit request =>
    val id = form.bindFromRequest.get
    //val result = IoTData.findBy(Integer.parseInt(id))
    val result = s"""{"id":"${id}", "status":"success"}"""
    Ok(Json.toJson(result))

    //Ok(Json.toJson(result.head.toMap))
  }

}
