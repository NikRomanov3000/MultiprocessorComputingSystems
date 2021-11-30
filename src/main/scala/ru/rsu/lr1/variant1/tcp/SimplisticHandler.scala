package ru.rsu.lr1.variant1.tcp

import akka.actor.Actor

class SimplisticHandler extends Actor {

  import akka.io.Tcp._

  def receive = {
    case Received(data) => sender() ! Write(data)
    case PeerClosed => context.stop(self)
  }
}
