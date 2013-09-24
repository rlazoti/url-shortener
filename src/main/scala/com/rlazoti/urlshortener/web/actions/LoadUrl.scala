package com.rlazoti.urlshortener.web.actions

import com.rlazoti.urlshortener.infra.repositories.UrlRepository
import com.rlazoti.urlshortener._

class LoadUrl {

  def response(hash: String) = {
    val repository = UrlRepository.getInstance

    repository
      .find(hash)
      .map {
        case Some(url) => redirect(url)
        case _         => error(s"Redirect cannot be done. HASH($hash) not found")
      }
      .ensure(repository.release)
  }

}