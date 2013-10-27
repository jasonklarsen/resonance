package io.larsen.resonance.finagle

import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.finagle.http.{Http, Request, Response, RichHttp}
import com.twitter.finagle.builder.{ServerBuilder}
import com.twitter.util.{Await, Future}
import java.net.InetSocketAddress
import org.jboss.netty.handler.codec.http._
import org.jboss.netty.buffer.ChannelBuffers.copiedBuffer
import org.jboss.netty.util.CharsetUtil.UTF_8
import java.lang.System.{getProperty, setProperty}


object Server extends App {
  val resPort = getProperty("resPort")
  val service = (new HandleExceptions).andThen(new SimpleResponse)
//  val server = Http.serve("localhost:" + resPort, service)
val server  = ServerBuilder()
      .codec(RichHttp[Request](Http()))
      .bindTo(new InetSocketAddress(resPort.toInt))
      .name("resonance-server-finagle")
      .build(service)
  println("**Starting the finagle resonance server**")
//  Await.ready(server)
}

class SimpleResponse extends Service[Request, Response] {
	def apply(req: Request): Future[Response] = {
    println("**Building response**")

    Future.value {
      val sysTime = io.larsen.resonance.Core.sysTime
      val response = Response()
      response.setContentTypeJson
      response.setHeader("Access-Control-Allow-Origin", webAddress)
      response.setHeader("Access-Control-Allow-Headers", "X-Requested-With")
      response.setHeader("Access-Control-Allow-Methods", "GET, POST")
      response.content = copiedBuffer("{\"sysTime\":\"" + sysTime + "\"}", UTF_8)
      response
    }

		// val response = new DefaultHttpResponse(req.getProtocolVersion, HttpResponseStatus.OK)

		// response.setHeader("Access-Control-Allow-Origin", webAddress)
		// response.setHeader("Access-Control-Allow-Headers", "X-Requested-With")
		// response.setHeader("Access-Control-Allow-Methods", "GET, POST")
		// response.setContent(copiedBuffer("{\"sysTime\":\"" + sysTime + "\"}", UTF_8))
		// Future.value(response)
	}

  private def webAddress() = {
    // Needs pulling out....
    val webPort = getProperty("webPort")
    "http://localhost:" + webPort
  }
}


class HandleExceptions extends SimpleFilter[Request, Response] {
  def apply(request: Request, service: Service[Request, Response]) = {
    service(request) handle { case error =>
      val statusCode = error match {
        case _: IllegalArgumentException =>
          HttpResponseStatus.FORBIDDEN
        case _ =>
          HttpResponseStatus.INTERNAL_SERVER_ERROR
        }

      val errorResponse = Response(request.getProtocolVersion, statusCode)
      errorResponse.content = copiedBuffer(error.getStackTraceString, UTF_8)
      errorResponse
    }
  }
}