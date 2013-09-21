package com.rlazoti.urlshortener.web.actions

import org.jboss.netty.handler.codec.http.DefaultHttpResponse
import org.jboss.netty.handler.codec.http.HttpResponseStatus.OK
import org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1
import org.jboss.netty.buffer.ChannelBuffers.copiedBuffer
import org.jboss.netty.util.CharsetUtil.UTF_8
import com.rlazoti.urlshortener.ok
import com.rlazoti.urlshortener.infra.repositories.UrlRepository

class DeleteUrl {

  def response(hash: String) = {
    val repository = UrlRepository.getInstance

    repository
      .remove(hash)
      .map({
        case Some(true) => ok()
        case _          => error(s"Url cannot be deleted. HASH($hash) not found")
      })
      .ensure(repository.release)
  }

}