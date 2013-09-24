package com.rlazoti.urlshortener.web.services

import com.twitter.finagle._
import com.twitter.util.Future
import com.twitter.finagle.http.path.Path
import org.jboss.netty.handler.codec.http.{ HttpResponse, HttpRequest }
import org.jboss.netty.util.CharsetUtil.UTF_8
import org.jboss.netty.handler.codec.http.HttpMethod.{ POST, GET, DELETE }
import org.jboss.netty.handler.codec.http.DefaultHttpResponse
import org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1
import org.jboss.netty.handler.codec.http.HttpResponseStatus.{ NOT_FOUND }
import com.rlazoti.urlshortener.notFound
import com.rlazoti.urlshortener.web.actions.{ LoadUrl, GenerateUrlParametersParsing, GenerateUrl }

class RoutingService extends Service[HttpRequest, HttpResponse] {

  private val loadUriPattern = "^/(\\w+)$".r
  private val postUriPattern = "^/$".r

  def apply(request: HttpRequest) =
    (request.getMethod, request.getUri) match {
      case (GET, loadUriPattern(hash)) => new LoadUrl response (hash)
      case (POST, postUriPattern)      => new GenerateUrl response (GenerateUrlParametersParsing(request))
      case _                           => Future value (notFound)
    }

}