package ru.rsu.lr1.variant1

import akka.actor.typed.delivery.internal.ProducerControllerImpl.Ack
import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.io.Tcp.{Bind, Bound, CommandFailed, Connect, Connected, Event, PeerClosed, Received, Register, ResumeReading, SuspendReading, Write}
import akka.io.{IO, Tcp, Udp}
import akka.util.ByteString
import ru.rsu.lr1.variant1.tcp.SimpleEchoHandler

import java.net.InetSocketAddress

class ClientUDP(local: InetSocketAddress, remote: InetSocketAddress) extends Actor with ActorLogging {

  import context.system

  IO(Udp) ! Udp.Bind(self, local)
  //IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", 1301))

  var storage = Vector.empty[ByteString]
  var stored = 0L
  var transferred = 0L
  var closing = false
  val maxStored = 100000000L
  val highWatermark = maxStored * 5 / 10
  val lowWatermark = maxStored * 3 / 10
  var suspended = false
  case object Ack extends Event

  def receive = {
    case Udp.Bound(_) ⇒
      context.become(ready(sender()))

    case b@Bound(localAddress) =>
      context.parent ! b

    case CommandFailed(_: Bind) => context.stop(self)
  }

  def ready(send: ActorRef): Receive = {
    case PeerClosed => context.stop(self)

    case Udp.Received(data, remoteAddress) ⇒
      val ipAddress = remoteAddress.getAddress.getHostAddress
      val port = remoteAddress.asInstanceOf[InetSocketAddress].getPort
      log.info(s"we received ${data.utf8String} from IP Address: $ipAddress and port number: $port")\
      // как созадть connection: ActorRef ?
      Props(classOf[SimpleEchoHandler], null, new InetSocketAddress("localhost", 1300))
  }
}

object ClientUDP {
  def apply(local: InetSocketAddress, remote: InetSocketAddress) = Props(classOf[ClientUDP], local, remote)
}


