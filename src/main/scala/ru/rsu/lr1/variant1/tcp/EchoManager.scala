package ru.rsu.lr1.variant1.tcp

import akka.actor.{Actor, ActorLogging, Props, SupervisorStrategy}
import akka.io.{IO, Tcp}

import java.net.InetSocketAddress

//EchoManager - class
class EchoManager(handlerClass: Class[_]) extends Actor with ActorLogging {

  import Tcp._
  import context.system

  // there is not recovery for broken connections
  override val supervisorStrategy = SupervisorStrategy.stoppingStrategy

  // bind to the listen port; the port will automatically be closed once this actor dies
  override def preStart(): Unit = {
    IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", 1300))
  }

  // do not restart
  override def postRestart(thr: Throwable): Unit = context.stop(self)

  def receive = {
    case Bound(localAddress) =>
      log.info("listening on port {}", localAddress.getPort)

    case Tcp.Received(data) ⇒
      log.info(s"WE  RECEIVED  ${data.utf8String} BY FUCKING TCP");

    case CommandFailed(Bind(_, local, _, _, _)) =>
      log.warning(s"cannot bind to [$local]")
      context.stop(self)

    //#echo-manager
    case Connected(remote, local) =>
      log.info("received connection from {}", remote)
      val handler = context.actorOf(Props(handlerClass, sender(), remote))
      sender() ! Register(handler, keepOpenOnPeerClosed = true)
    //#echo-manager
  }
}
