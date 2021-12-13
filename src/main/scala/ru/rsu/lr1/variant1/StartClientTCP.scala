package ru.rsu.lr1.variant1

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import ru.rsu.lr1.variant1.tcp.{EchoHandler, EchoManager, SimpleEchoHandler}

import scala.io.StdIn

object StartClientTCP extends App {
  val config = ConfigFactory.parseString("akka.loglevel = DEBUG")
  implicit val system: ActorSystem = ActorSystem("EchoServer", config)

  system.actorOf(Props(classOf[EchoManager], classOf[SimpleEchoHandler]), "clientTCP")
  //system.actorOf(Props(classOf[EchoManager], classOf[SimpleEchoHandler]), "simple")

  println("Press enter to exit...")
  StdIn.readLine()
  system.terminate()
}
