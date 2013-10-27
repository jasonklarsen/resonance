package io.larsen.resonance.finagle

import com.twitter.finagle.{Http, Service, SimpleFilter}
import com.twitter.util.{Await, Future}
import java.net.InetSocketAddress
import org.jboss.netty.handler.codec.http._
import org.jboss.netty.buffer.ChannelBuffers.copiedBuffer
import org.jboss.netty.util.CharsetUtil.UTF_8
import java.lang.System.{getProperty, setProperty}


object Server extends App {
  val resPort = getProperty("resPort")
  val service = (new HandleExceptions).andThen(new SimpleResponse)
  val server = Http.serve(":" + resPort, service)
  Await.ready(server)
}

class SimpleResponse extends Service[HttpRequest, HttpResponse] {
	def apply(req: HttpRequest): Future[HttpResponse] = {
		val response = new DefaultHttpResponse(req.getProtocolVersion, HttpResponseStatus.OK)
		val sysTime = io.larsen.resonance.Core.sysTime

		response.setHeader("Access-Control-Allow-Origin", webAddress)
		response.setHeader("Access-Control-Allow-Headers", "X-Requested-With")
		response.setHeader("Access-Control-Allow-Methods", "GET, POST")
		response.setContent(copiedBuffer("{\"sysTime\":\"" + sysTime + "\"}", UTF_8))
		Future.value(response)
	}

  private def webAddress() = {
    // Needs pulling out....
    val webPort = getProperty("webPort")
    "http://localhost:" + webPort
  }
}


class HandleExceptions extends SimpleFilter[HttpRequest, HttpResponse] {
  def apply(request: HttpRequest, service: Service[HttpRequest, HttpResponse]) = {
    service(request) handle { case error =>
      val statusCode = error match {
        case _: IllegalArgumentException =>
          HttpResponseStatus.FORBIDDEN
        case _ =>
          HttpResponseStatus.INTERNAL_SERVER_ERROR
        }

      val errorResponse = new DefaultHttpResponse(request.getProtocolVersion, statusCode)
      errorResponse.setContent(copiedBuffer(error.getStackTraceString, UTF_8))

      errorResponse
    }
  }
}