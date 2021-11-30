package ru.rsu.lr1.variant1

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import ru.rsu.lr1.variant1.tcp.ClientTCP

import scala.io.StdIn

object StartClientTCP extends App {
  val config = ConfigFactory.parseString("akka.loglevel = DEBUG")
  implicit val system: ActorSystem = ActorSystem("EchoServer", config)

  system.actorOf(Props(classOf[ClientTCP], classOf[ClientTCP]), "clientTCP")
  //system.actorOf(Props(classOf[EchoManager], classOf[SimpleEchoHandler]), "simple")

  println("Press enter to exit...")
  StdIn.readLine()
  system.terminate()
}