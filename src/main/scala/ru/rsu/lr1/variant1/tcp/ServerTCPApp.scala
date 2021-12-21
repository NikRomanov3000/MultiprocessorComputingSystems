package ru.rsu.lr1.variant1.tcp

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import scala.io.StdIn

object ServerTCPApp extends App {
  val config = ConfigFactory.parseString("akka.loglevel = DEBUG")
  implicit val system: ActorSystem = ActorSystem("EchoServer", config)

  system.actorOf(Props(classOf[EchoManager], classOf[EchoHandler]), "echo")
  //system.actorOf(Props(classOf[EchoManager], classOf[SimpleEchoHandler]), "SimpleEchoHandler")

  println("Press enter to exit...")
  StdIn.readLine()
  system.terminate()

}
