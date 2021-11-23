package ru.rsu.lr1.tcp

import java.io.ObjectOutputStream
import java.net.ServerSocket
import java.net.Socket
import java.util.Calendar
import ru.rsu.lr1.tcp.DateMessage
import ru.rsu.lr1.tcp.ServerTCP.serverSocket

object ServerTCP {
  var serverSocket: ServerSocket = null;
  def main(args: Array[String]): Unit = { // Запуск сервера
    new ServerTCP
  }
}

class ServerTCP()

  extends Thread {
  try { // Создается объект ServerSocket, который получает
    // запросы клиента на порт 1500
    val port = 1500;
    serverSocket = new ServerSocket(port)
    System.out.println("Starting the server ")
    // Запускаем процесс
    start()
  } catch {
    case e: Exception =>
      e.printStackTrace()
  }
  // Объявляется ссылка
  // на объект - сокет сервера


  /**
   * Запуск процесса
   */
  override def run(): Unit = {
    try while ( {
      true
    }) { // Ожидание запросов соединения от клиентов
      val clientSocket = serverSocket.accept()
      System.out.println("Connection accepted from " + clientSocket.getInetAddress.getHostAddress)
      // Получение выходного потока,
      // связанного с объектом Socket
      val out = new ObjectOutputStream(clientSocket.getOutputStream)
      // Создание объекта для передачи клиентам
      var dateMessage = new DateMessage(Calendar.getInstance.getTime, "Текущая дата/время на сервере")
      // Запись объекта в выходной поток
      out.writeObject(dateMessage)
      out.close()
    }
    catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }
}

