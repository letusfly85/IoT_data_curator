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
  def upload = Action(parse.multipartFormData) { implicit request =>
    val csv = csvForm.bindFromRequest.get
    request.body.file("csv_file").map { file =>
      import java.io.File
      file.ref.moveTo(new File("app/tmp", file.filename))
      Ok(csv.title + "のアップロード成功")

    }.getOrElse {
      Redirect(routes.Application.index).flashing(
        "error" -> "Missing file"
      )
    }
  }
}
