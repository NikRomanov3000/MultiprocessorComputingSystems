package ru.rsu.lr1.variant1.udp

import akka.actor.{ActorRef, ActorSystem}

import java.net.InetSocketAddress

object StartClientUDP extends App {
  val system = ActorSystem("RemoteActorSystem")
  val local = new InetSocketAddress("localhost", 5005)
  val remote = new InetSocketAddress("localhost", 5115)

  val udp: ActorRef = system.actorOf(ClientUDP(local, remote), name = "Udp")
}
