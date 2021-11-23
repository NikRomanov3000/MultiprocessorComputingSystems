package ru.rsu.lr1.variant1

import java.io.{BufferedReader, IOException}
import java.net.{DatagramPacket, DatagramSocket, InetAddress}
import scala.io.StdIn.readLine

object TestServer {
  @throws[Exception]
  def main(arg: Array[String]): Unit = { // Запуск сервера
    //new TestServer
  }
}

class TestServer @throws[IOException]
() {
  System.out.println("Sending messages")
  // Создается объект DatagramSocket, чтобы
  // принимать запросы клиента
  val socket = new DatagramSocket();
  val port = 1502;
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
        System.out.println("Введите строку для передачи клиентам: ")
        val str = readLine();
        val buffer = str.getBytes();
        val address = InetAddress.getByName("233.0.0.1")
        // Посылка пакета датаграмм на порт номер 1502
        val packet = new DatagramPacket(buffer, buffer.length, address, port)
        //Посылка сообщений всем клиентам в группе
        //val socket = new DatagramSocket();
        socket.send(packet)
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


