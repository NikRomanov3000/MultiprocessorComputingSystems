package ru.rsu.lr1.variant1.tcp

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.io.Tcp._
import akka.util.ByteString

import java.net.InetSocketAddress

object SimpleEchoHandler {
  def apply(connection: ActorRef, remote: InetSocketAddress) = Props(classOf[SimpleEchoHandler], connection, remote);
}

//#simple-echo-handler
class SimpleEchoHandler(connection: ActorRef, remote: InetSocketAddress) extends Actor with ActorLogging {
  // sign death pact: this actor terminates when connection breaks
  context.watch(connection)

  case object Ack extends Event

  def receive = {
    case Received(data) =>
      buffer(data)
      connection ! Write(data, Ack)

      context.become({
        case Received(data) => buffer(data)
        case Ack => acknowledge()
        case PeerClosed => closing = true
      }, discardOld = false)

    case PeerClosed => context.stop(self)
  }

  //#storage-omitted
  override def postStop(): Unit = {
    log.info(s"transferred $transferred bytes from/to [$remote]")
  }

  var storage = Vector.empty[ByteString]
  var stored = 0L
  var transferred = 0L
  var closing = false

  val maxStored = 100000000L
  val highWatermark = maxStored * 5 / 10
  val lowWatermark = maxStored * 3 / 10
  var suspended = false

  //#simple-helpers
  private def buffer(data: ByteString): Unit = {
    storage :+= data
    stored += data.size

    if (stored > maxStored) {
      log.warning(s"drop connection to [$remote] (buffer overrun)")
      context.stop(self)

    } else if (stored > highWatermark) {
      log.debug(s"suspending reading")
      connection ! SuspendReading
      suspended = true
    }
  }

  private def acknowledge(): Unit = {
    require(storage.nonEmpty, "storage was empty")

    val size = storage(0).size
    stored -= size
    transferred += size

    storage = storage.drop(1)

    if (suspended && stored < lowWatermark) {
      log.debug("resuming reading")
      connection ! ResumeReading
      suspended = false
    }

    if (storage.isEmpty) {
      if (closing) context.stop(self)
      else context.unbecome()
    } else connection ! Write(storage(0), Ack)
  }
}
