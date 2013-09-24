package com.rlazoti.urlshortener.web.filters

import com.twitter.finagle.Service
import com.twitter.finagle.SimpleFilter
import org.jboss.netty.util.CharsetUtil.UTF_8
import org.jboss.netty.handler.codec.http.HttpResponse
import org.jboss.netty.buffer.ChannelBuffers.copiedBuffer
import org.jboss.netty.handler.codec.http.DefaultHttpResponse
import org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1
import org.jboss.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR
import org.jboss.netty.handler.codec.http.HttpRequest

class HandleExceptions extends SimpleFilter[HttpRequest, HttpResponse] {

  def apply(request: HttpRequest, service: Service[HttpRequest, HttpResponse]) =
    service(request) handle { case e => error(e.toString) }

}