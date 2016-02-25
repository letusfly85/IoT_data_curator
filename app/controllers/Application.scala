package controllers

import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._

case class Csv(title: String, comment: String)


class Application extends Controller {

  def index = Action {
    Ok(views.html.index())
  }

  val csvForm = Form(
    mapping(
      "title" -> text,
      "comment" -> text
    )(Csv.apply)(Csv.unapply)
  )
  def formSample = Action { implicit request =>
    val csv = csvForm.bindFromRequest.get
    Ok(csv.title)
  }

}
