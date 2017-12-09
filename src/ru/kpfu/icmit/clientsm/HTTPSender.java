package ru.kpfu.icmit.clientsm;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.UUID;

import javax.swing.JOptionPane;

/**
 * Класс должен реализовать функции отправки и приема сообщений от сервера
 * по протоколу HTTP
 */
public class HTTPSender {

    private String serverAddress;
    private String serverPort;

    // Конструктор класса, инициализирующий переменные которые хранят адрес и порт сервера
    public HTTPSender(String serverAddress, String serverPort) {
        this.serverAddress = "127.0.0.1";
        this.serverPort = "3128";
    }

    /**
     * Метод отправляет сообщение на сервер
     * @param message
     * @param requestPath
     * @return
     */
    public ServerResponse sendMessage(String message, String requestPath){
        //TODO реализовать
        ServerResponse result = null;

        // Создаем сокет для отправки и приема сообщения на сервер
        try(Socket socket = new Socket()){
            // Подключаемся к серверу, используя заданный адрес и порт
            socket.connect(new InetSocketAddress(InetAddress.getByName(serverAddress),Integer.parseInt(serverPort)));

            // Привязываем к переменным os и is выходной и входной потоки сокета
            try(OutputStream os = socket.getOutputStream();
                InputStream is = socket.getInputStream()){

                //Сообщение для отправки на сервер
                String msg = message;
                // Преобразуем сообщение в массив байт для отправки на сервер через выходной поток сокета
                byte[] msg_bytes = msg.getBytes();

                // Готовим буфер - массив из 4 байт для отправки длины сообщения
                int size = msg_bytes.length;
                int messagesize = 0;

                String hdr = null;
                String hrd = "POST /" + requestPath + " HTTP/1.1\r\n" +
                        "Host: " + serverAddress + ":" + serverPort + "\r\n" +
                        "Content-Type: application/json" + "\r\n";

                if (message != null && !message.equals("")){
                    messagesize = message.getBytes().length;
                    hdr = hdr + "Content-Length: " + messagesize + "\r\n" + "\r\n" +
                            message;
                }
                else {
                    hdr = hdr + "\r\n";
                }

                //Отправляем на сервер само сообщение
                os.write(hdr.getBytes());

                ResponseReader rr = new ResponseReader();
                result = rr.read(is);
            }
        } catch (UnknownHostException e) {
            System.out.println("Неизвестный адрес!");;
        } catch (ConnectException e){
            System.out.println("Не смогли подключиться к серверу!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private int byte4_2int(byte[] response_size) {
        // TODO Auto-generated method stub
        return 0;
    }

    private byte[] int2byte4(int length) {
        // TODO Auto-generated method stub
        return null;
    }

    /** Метод для запроса новых сообщений с сервера */
    public List<ClientMessage> getMessages(String message, String requestPath){
        //TODO реализовать
        return null;
    }

    /**
     * Метод для передачи параметров аутентификации
     * @param message
     * @param requestPath
     * @return
     */
    public UUID sendLogin(String message, String requestPath){
        //TODO реализовать
        return null;
    }

    /**
     * Метод для передачи данных о регистрации абонента
     * @param message
     * @param requestPath
     * @return
     */
    public boolean sendRegistration(String message, String requestPath){
        //TODO реализовать
        return false;
    }

}
