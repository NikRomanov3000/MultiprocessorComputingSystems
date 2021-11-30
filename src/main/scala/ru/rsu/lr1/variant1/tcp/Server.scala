package ru.rsu.lr1.variant1.tcp

import akka.actor.{Actor, Props}
import akka.io.{IO, Tcp}

import java.net.InetSocketAddress

class Server{
/*
  import Tcp._
  import context.system

  IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", 1300))

  def receive = {
    case b@Bound(localAddress) =>
      context.parent ! b

    case CommandFailed(_: Bind) => context.stop(self)

    case c@Connected(remote, local) =>
      val handler = context.actorOf(Props[SimplisticHandler]())
      val connection = sender()
      connection ! Register(handler)
  }
 */
}

