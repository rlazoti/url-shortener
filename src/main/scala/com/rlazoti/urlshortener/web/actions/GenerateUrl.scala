package com.rlazoti.urlshortener.web.actions

import org.jboss.netty.handler.codec.http.DefaultHttpResponse
import org.jboss.netty.handler.codec.http.HttpResponseStatus.{ BAD_REQUEST, INTERNAL_SERVER_ERROR, OK }
import org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1
import org.jboss.netty.handler.codec.http.HttpResponse
import org.jboss.netty.buffer.ChannelBuffers.copiedBuffer
import org.jboss.netty.util.CharsetUtil.UTF_8
import com.rlazoti.urlshortener.infra.repositories.UrlRepository
import com.rlazoti.urlshortener.{ error, ok }
import com.twitter.util.Future

object GenerateUrlParametersParsing {

  import scala.collection.JavaConversions._
  import org.jboss.netty.handler.codec.http.multipart.{ Attribute, DefaultHttpDataFactory, MixedFileUpload, HttpPostRequestDecoder }
  import org.jboss.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType
  import org.jboss.netty.handler.codec.http.HttpRequest

  def apply(request: HttpRequest) = {
    val data = new HttpPostRequestDecoder(new DefaultHttpDataFactory(false), request).getBodyHttpData("url")
    if (data.getHttpDataType == HttpDataType.Attribute) Some(data.asInstanceOf[Attribute].getValue)
    else None
  }

}

class GenerateUrl {

  def response(parameter: Option[String]) = {
    parameter match {
      case Some(url) => {
        val repository = UrlRepository.getInstance
        repository
          .save(url)
          .map {
            case Some(hash) => ok(Map("content-type" -> "application/json"), s"""{"url" : "$url", "hash": "$hash"}""")
            case _          => error("URL cannot be stored in Redis")
          }
          .ensure(repository.release)
      }
      case _ => Future.value(error("Request parameter's URL is undefined"))
    }
  }

}