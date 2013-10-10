package com.rlazoti.urlshortener.bin

import com.rlazoti.urlshortener.web.services.RoutingService
import com.rlazoti.urlshortener.web.filters.HandleExceptions
import com.twitter.finagle.builder.Server
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.Http
import java.net.InetSocketAddress

object StartServer {

  val routingService = new RoutingService
  val handleExceptions = new HandleExceptions

  val server: Server = ServerBuilder()
    .codec(Http())
    .bindTo(new InetSocketAddress(10000))
    .name("URL Shortener")
    .build(handleExceptions andThen routingService)

}