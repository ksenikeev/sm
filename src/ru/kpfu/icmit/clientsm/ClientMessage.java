package ru.kpfu.icmit.clientsm;

/**
 * Класс, моделирующий JSON объект-сообщение
 * {"from":{"id":"","name":""}, "to":{"id":"","name":""}, "content":"", "datemsg":"yyyy-MM-dd HH:mm:ss", "status":""}
 */
//  Зубаирова
public class ClientMessage {

    Abonent from;
    Abonent to;
    String content;
    String datemsg;
    String status;
    // ...
}
