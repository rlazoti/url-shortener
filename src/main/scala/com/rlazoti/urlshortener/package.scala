package com.rlazoti

package object urlshortener {

  import com.twitter.finagle.http.Response
  import com.twitter.finagle.http.Version.Http11
  import com.twitter.finagle.http.Status.InternalServerError
  import org.jboss.netty.util.CharsetUtil.UTF_8
  import org.jboss.netty.buffer.ChannelBuffers.copiedBuffer
  import org.jboss.netty.handler.codec.http.HttpResponseStatus
  import org.jboss.netty.handler.codec.http.DefaultHttpResponse
  import org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1
  import org.jboss.netty.handler.codec.http.HttpResponseStatus.{ NOT_FOUND, INTERNAL_SERVER_ERROR, MOVED_PERMANENTLY }

  def notFound = response(NOT_FOUND, Map.empty)

  def redirect(url: String) = response(MOVED_PERMANENTLY, Map("Location" -> url))

  def error(content: String) =
    response(INTERNAL_SERVER_ERROR, Map("content-type" -> "text/plain"), content)

  def ok(headers: Map[String, Any] = Map.empty, content: String = "") =
    response(HttpResponseStatus.OK, headers, content)

  private def response(status: HttpResponseStatus, headers: Map[String, Any], content: String = "") = {
    val response = new DefaultHttpResponse(HTTP_1_1, status)
    headers.foreach(params => response.setHeader(params._1, params._2))
    if (!content.isEmpty) response.setContent(copiedBuffer(content, UTF_8))

    response
  }

}