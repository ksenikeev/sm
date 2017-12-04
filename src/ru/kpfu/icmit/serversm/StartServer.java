package ru.kpfu.icmit.serversm;

import java.net.ServerSocket;

/**
 * Класс создает серверный сокет, запускает цикл на прослушивание порта
 * Новое подключение обрабатывается в классе-потоке SMServer,
 * конструктору которого передается сокет соединения с клиентом (его возвращает метод server.accept())
 */
public class StartServer {
    public static void main(String args[]){
        try(ServerSocket server = new ServerSocket(3128);){
            System.out.println("server SM is started");

            while(true) {
                // ждём нового подключения, после чего запускаем обработку клиента
                new SMServer(server.accept());
            }
        } catch(Exception e){
            System.out.println("init error: "+e);
        }
    }

}
