package ru.kpfu.icmit.clientsm;

import java.util.List;
import java.util.UUID;

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
     * Метод отправляет сообщение на сервер
     * @param message
     * @param requestPath
     * @return
     */
    public ServerResponse sendMessage(String message, String requestPath){
        //TODO реализовать
        
        return null;
    }

    /** Метод для запроса новых сообщений с сервера */
    public List<ClientMessage> getMessages(String message, String requestPath){
        //TODO реализовать
        return null;
    }

    /** Метод для запроса всех сообщений с сервера */
    public List<ClientMessage> getAllMessages(String message, String requestPath){
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
