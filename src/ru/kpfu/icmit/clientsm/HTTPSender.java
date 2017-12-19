package ru.kpfu.icmit.clientsm;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
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
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    /**
     * Метод отправляет http сообщение на сервер
     * @param message JSON объект
     * @param requestPath путь на сервере для обработки http запроса
     * @return
     */
    public ServerResponse sendMessage(String message, String requestPath) {
        ServerResponse result = null;

        // Создаем сокет для отправки и приема сообщения на сервер
        try (Socket socket = new Socket()) {
            // Подключаемся к серверу, используя заданный адрес и порт
            socket.connect(new InetSocketAddress(InetAddress.getByName(serverAddress), Integer.parseInt(serverPort)));

            // Привязываем к переменным os и is выходной и входной потоки сокета
            try (OutputStream os = socket.getOutputStream();
                 InputStream is = socket.getInputStream()) {

                //Сообщение для отправки на сервер
                String msg = message;
                // Преобразуем сообщение в массив байт для отправки на сервер через выходной поток сокета
                byte[] msg_bytes = msg.getBytes();

                // Готовим буфер - массив из 4 байт для отправки длины сообщения
                int size = msg_bytes.length;
                int messagesize = 0;

                String hdr = "POST " + requestPath + " HTTP/1.1\r\n" +
                        "Host: " + serverAddress + ":" + serverPort + "\r\n" ;

                if (message != null && !message.equals("")) {
                    messagesize = message.getBytes().length;
                    hdr = hdr +
                            "Content-Type: application/json" + "\r\n"+
                            "Content-Length: " + messagesize + "\r\n" + "\r\n" +
                            message;
                } else {
                    hdr = hdr + "\r\n";
                }

                System.out.println(hdr);

                //Отправляем на сервер само сообщение
                os.write(hdr.getBytes());

                ResponseReader rr = new ResponseReader();
                result = rr.read(is);
            }
        } catch (UnknownHostException e) {
            System.out.println("Неизвестный адрес!");
        } catch (ConnectException e) {
            System.out.println("Не смогли подключиться к серверу!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /** Отправляем на сервер запрос новых сообщений и парсим их
     * @autor Oleksiv
     */
    public List<ClientMessage> getMessages(String token) {
        String msg = "{\"token\": \"" + token + "\"}";
        String requestPath = "/getnewmsg";
        //Отправка на сервер запроса
        ServerResponse sr = sendMessage(msg, requestPath);
        if (sr==null || sr.responseCode!=200) {
            System.out.println("getMessages: Error server response!");
            return null;
        }
        if(sr.content!=null && !sr.content.trim().equals("")) {
            String content = sr.content;
            Gson gson = new Gson();
            PClientMessage pcm = gson.fromJson(content, PClientMessage.class);
            return pcm.msglist;
        } else {
            return null;
        }
    }

    /** Модель JSON объекта, содержащего массив сообщений {"msglist":[сообщение1,...,сообщениеN]}
     @autor Oleksiv
    */
    class PClientMessage {
        ArrayList<ClientMessage> msglist;
    }

    /**
     * Метод для передачи параметров аутентификации
     * @return
     * @autor Bikusheva
     */
    public ServerResponse sendLoginInfo(String login, String password){
        System.out.println("Start login"+login);
        //Формирование JSON объекта который будет отправлен на сервер
        String message = "{\"login\":\""+login+"\",\"password\":\""+password+"\"}";

        //Отправляем сообщение message на сервер с указанием ресурса "/reguser" который должен обработать наше сообщение
        ServerResponse resp = sendMessage(message, "/login");
        System.out.println("Response code: "+resp.responseCode);
        // Возвращаем ответ сервера для дальнейшего анализа
        return resp;
    }


    /**
     * Метод для передачи данных о регистрации абонента
     * @autor Enikeev
     */
    public ServerResponse sendRegistration(String login, String password) {
        System.out.println("Start registration. Username "+login);
        //Формирование JSON объекта который будет отправлен на сервер
        String message = "{\"name\":\""+login+"\",\"login\":\""+login+"\",\"password\":\""+password+"\"}";

        //Отправляем сообщение message на сервер с указанием ресурса "/reguser" который должен обработать наше сообщение
        ServerResponse resp = sendMessage(message, "/reguser");

        System.out.println("Response code: "+resp.responseCode);
        // Возвращаем ответ сервера для дальнейшего анализа
        return resp;
    }

    /**
     * Метод для запроса всех абонентов
     * @param token
     * @return ArrayList<Abonent>
     */
    public ArrayList<Abonent> getAbonentList(String token){
        System.out.println("Request abonent list");
        String message = "{\"token\":\""+token+"\"}";
        ServerResponse resp = sendMessage(message, "/getusers");
        if (resp.responseCode!=200) {
            return null;
        }
        Gson gson = new Gson();
        Abonents result = gson.fromJson(resp.content, Abonents.class);
        return result.usrlist;
    }

    class Abonents{
        ArrayList<Abonent> usrlist;
    }
}
