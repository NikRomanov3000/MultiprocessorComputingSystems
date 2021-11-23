package ru.rsu.lr1.tcp

import java.io.ObjectInputStream
import java.net.Socket
import ru.rsu.lr1.tcp.DateMessage

object ClientTCP {
  def main(args: Array[String]): Unit = {
    try { // Создается объект Socket 
      // для соединения с сервером 
      val port = 1500;
      val clientSocket = new Socket("localhost", port)
      // Получаем ссылку на поток, связанный с сокетом 
      val in = new ObjectInputStream(clientSocket.getInputStream)
      // Извлекаем объект из входного потока
      val dateMessage = in.readObject.asInstanceOf[DateMessage]
      clientSocket.close()
      // Выводим полученные данные в консоль
      System.out.println(dateMessage.getMessage)
      System.out.println(dateMessage.getDate)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }
}

