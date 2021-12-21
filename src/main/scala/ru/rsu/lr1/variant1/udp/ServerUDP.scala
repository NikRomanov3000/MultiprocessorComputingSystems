package ru.rsu.lr1.variant1.udp

import akka.actor.{Actor, ActorLogging, ActorRef, Cancellable, Props}
import akka.io.{IO, Udp}
import akka.util.ByteString

import java.net.InetSocketAddress
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class ServerUDP(local: InetSocketAddress, remote: InetSocketAddress) extends Actor with ActorLogging {

  import context.system

  IO(Udp) ! Udp.Bind(self, local)
  val filePath = "src/main/scala/ru/rsu/lr1/variant1/data.txt"
  val listOfMessage: List[String] = io.Source.fromFile(filePath).getLines().toList
  var iterator = 0
  var defaultMessage: String = "defaultMessage"
  val scheduleCancellable: Cancellable = system.scheduler.scheduleWithFixedDelay(0.seconds, 1.second, self, defaultMessage)

  def receive = {
    case Udp.Bound(_) ⇒
      context.become(ready(sender()))
  }

  def ready(send: ActorRef): Receive = {
    case msg: String ⇒
      send ! Udp.Send(ByteString(listOfMessage(iterator)), remote)
      iterator = iterator + 1;
  }
}

object ServerUDP {
  def apply(local: InetSocketAddress, remote: InetSocketAddress) = Props(classOf[ServerUDP], local, remote)
}
