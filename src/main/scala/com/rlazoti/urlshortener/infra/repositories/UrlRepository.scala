package com.rlazoti.urlshortener.infra.repositories

import com.twitter.finagle.Service
import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.redis.{ Client, Redis }
import com.twitter.finagle.redis.protocol.{ Command, Reply, Del, Get, Set, StatusReply, BulkReply, IntegerReply }
import com.twitter.conversions.time.intToTimeableNumber
import com.twitter.util.Future
import org.jboss.netty.buffer.ChannelBuffer
import com.twitter.finagle.redis.util.{ CBToString, StringToChannelBuffer }
import com.twitter.finagle.redis.util.ReplyFormat
import java.net.SocketAddress
import java.net.InetSocketAddress
import com.twitter.finagle.builder.Cluster
import com.twitter.finagle.redis.protocol.IntegerReply
import java.security.MessageDigest

object UrlRepository {

  def getInstance: UrlRepository = {
    val client = ClientBuilder()
      .codec(Redis())
      .hosts(new InetSocketAddress("localhost", 6379))
      .hostConnectionLimit(1)
      .build()

    new UrlRepository(client)
  }

}

class UrlRepository(client: Service[Command, Reply]) {

  implicit private def stringToChannelBuffer(s: String): ChannelBuffer = StringToChannelBuffer(s)

  implicit private def channelBufferToString(cb: ChannelBuffer): String = CBToString(cb)

  private def generateKeyByHash(hash: String) = s"url:$hash"

  private def generateKeyByUrl(url: String) = generateKeyByHash(generateHash(url))

  private def generateHash(url: String) = MessageDigest.getInstance("MD5").digest(url.getBytes).map("%02X".format(_)).mkString

  def release = client.release

  def find(hash: String): Future[Option[String]] =
    client(Get(generateKeyByHash(hash))) map {
      case BulkReply(value) => Some(value)
      case _                => None
    }

  def save(url: String): Future[Option[String]] = {
    val hash = generateHash(url)

    client(Set(generateKeyByHash(hash), url)) map {
      case StatusReply("OK") => Some(hash)
      case _                 => None
    }
  }

  def remove(hash: String): Future[Option[Boolean]] = {
    val keys = Seq(stringToChannelBuffer(generateKeyByHash(hash)))

    client(Del(keys)) map {
      case IntegerReply(1) => Some(true)
      case _               => None
    }
  }

}