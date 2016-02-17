package controllers

import models.IoTData
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._

class IotDataController extends Controller {

  val form = Form("id" -> text)

  def create = Action { implicit request =>
    val id = form.bindFromRequest.get
    IoTData.create(Integer.parseInt(id))

    Ok("ok")
  }

  def findBy = Action(parse.json) { implicit request =>
    val id = form.bindFromRequest.get
    val result = IoTData.findBy(Integer.parseInt(id))

    println(id)
    Ok(Json.toJson(result.head.toMap))
    /*Ok(Json.toJson(
      Map("status" -> "OK", "message" -> ("Hello "))
    ))*/
  }

}
