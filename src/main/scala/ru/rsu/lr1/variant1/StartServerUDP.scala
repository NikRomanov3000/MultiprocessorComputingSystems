package ru.rsu.lr1.variant1

import akka.actor.{ActorRef, ActorSystem}

import java.net.InetSocketAddress

object StartServerUDP extends App {
  val system = ActorSystem("ActorSystem")
  val remote = new InetSocketAddress("localhost", 5005)
  val local = new InetSocketAddress("localhost", 5115)

  val udp: ActorRef = system.actorOf(ServerUDP(local, remote), name = "Udp")
}
