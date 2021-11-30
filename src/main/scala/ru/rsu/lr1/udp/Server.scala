package ru.rsu.lr1.udp

import ru.rsu.lr1.udp.Server

import java.io.{BufferedReader, File, IOException}
import java.net.{DatagramPacket, DatagramSocket, InetAddress}
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors
import scala.io.StdIn.readLine

object Server {
  @throws[Exception]
  def main(arg: Array[String]): Unit = { // Запуск сервера
    new Server
  }
}

class Server @throws[IOException]
() {
  System.out.println("Sending messages...")
  // Создается объект DatagramSocket, чтобы
  // принимать запросы клиента
  val socket = new DatagramSocket();
  val port = 1502;
  val filePath = "src/main/scala/ru/rsu/lr1/variant1/data.txt"
  val address = InetAddress.getByName("233.0.0.1")
  // Вызов метода transmit(), чтобы передавать сообщение всем
  // клиентам, зарегистрированным в группе
  transmit()

  def transmit(): Unit = {
    try { // создается входной поток, чтобы принимать
      // данные с консоли
      //in = new BufferedReader(new Nothing(System.in))
      while ( {
        true
      }) {
        val listOfMessage: List[String] =io.Source.fromFile(filePath).getLines().toList

        for (str <- listOfMessage){
          val buffer = str.getBytes();
          // Посылка пакета датаграмм на порт номер 1502
          val packet = new DatagramPacket(buffer, buffer.length, address, port)
          //Посылка сообщений всем клиентам в группе
          //val socket = new DatagramSocket();
          socket.send(packet)
          TimeUnit.SECONDS.sleep(10)
        }
      }
    } catch {
      case e: Exception =>
        e.printStackTrace()
    } finally try { // Закрытие потока и сокета
      socket.close()
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }
}

