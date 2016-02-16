package controllers

import models.IoTData
import play.api.mvc._

class IotDataController extends Controller {

  def create = Action {
    IoTData.create()
    Ok("ok")
  }

}
