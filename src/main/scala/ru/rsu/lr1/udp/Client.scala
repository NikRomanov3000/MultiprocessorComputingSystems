package ru.rsu.lr1.udp

import java.net.{DatagramPacket, InetAddress, MulticastSocket}
import java.util.Calendar

object Client {
  private var address = null
  private var buffer = null
  private var packet = null
  private var str = null
  private val port = 1502;
  private val socket = new MulticastSocket(port);

  @throws[Exception]
  def main(arg: Array[String]): Unit = {
    System.out.println("Ожидание сообщения от сервера...")
    try { // Создание объекта MulticastSocket, чтобы получать
      // данные от группы, используя номер порта 1502
      //var socket = new MulticastSocket(1502)
      val address = InetAddress.getByName("233.0.0.1")
      // Регистрация клиента в группе
      socket.joinGroup(address)
      while ( {
        true
      }) {
        val buffer = new Array[Byte](256)
        val packet = new DatagramPacket(buffer, buffer.length)
        // Получение данных от сервера
        socket.receive(packet)
        val str = new String(packet.getData)
        System.out.println("Получено сообщение: " + str.trim + " время получения: " + Calendar.getInstance.getTime)
      }
    } catch {
      case e: Exception =>
        e.printStackTrace()
    } finally try { // Удаление клиента из группы
      socket.leaveGroup(address)
      // Закрытие сокета
      socket.close()
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }
}
