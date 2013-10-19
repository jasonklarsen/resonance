package io.larsen.resonance.web

import com.twitter.finatra._
import com.twitter.finatra.ContentType._
import java.lang.System.{getProperty, setProperty}

object Server extends FinatraServer {
  val resPort = getProperty("resPort")
  val webPort = getProperty("webPort")

  setProperty("com.twitter.finatra.config.port", ":" + webPort)
  setProperty("com.twitter.finatra.config.maxRequestSize", "1") // in MB
//  setProperty("com.twitter.finatra.config.env", "production")
  setProperty("com.twitter.finatra.config.templatePath", "templates")

  val app = new SimpleFiles
  register(app)
}

class SimpleFiles extends Controller {
  get("/") { request =>
    render.static("index.html").toFuture
  }
  get("/serverRoutes.js") { request =>
    render.view(new ServerRoutes).toFuture
  }
  get(":file") { request =>
    val file = request.routeParams.get("file").get // Given above pattern matching nothing else is possible...
    render.static(file).toFuture
  }
  get(":dir1/:file") { request =>
    val file = request.routeParams.get("file").get // Given above pattern matching nothing else is possible...
    val dir1 = request.routeParams.get("dir1").get // Given above pattern matching nothing else is possible...
    render.static(dir1 + "/" + file).toFuture
  }
  get(":dir1/:dir2/:file") { request =>
    val file = request.routeParams.get("file").get // Given above pattern matching nothing else is possible...
    val dir1 = request.routeParams.get("dir1").get // Given above pattern matching nothing else is possible...
    val dir2 = request.routeParams.get("dir2").get // Given above pattern matching nothing else is possible...
    render.static(dir1 + "/" + dir2 + "/" + file).toFuture
  }
}

class ServerRoutes extends View {
  val resDomain = getProperty("resDomain", "localhost")
  val resPort = getProperty("resPort")

  val template = "server_routes.mustache"
  val serviceRoute = "http://" + resDomain + ":" + resPort
}
