package ru.kpfu.icmit.clientsm;

import java.util.List;

/**
 * Класс реализует:
 * 1) функции по сохранению отправленных и прочитанных сообщений в файл.
 * 2) функции по считыванию отправленных и прочитанных сообщений из файла.
 *
 * Формат файла: текстовый файл в кодировке UTF-8.
 * Каждая строка содержит отдельное сообщение в виде JSON объекта
 * {"from":{"id":"","name":""}, "to":{"id":"","name":""}, "content":"", "datemsg":"yyyy-MM-dd HH:mm:ss", "status":""}
   TODO необходимо подготовить класс, моделирующий такой JSON объект (прототип ClientMessage)
 */
public class StoreMessages {

    /** Метод для чтения сохраненных сообщений из файла */
    public static List<ClientMessage> readMessages(String fileName){
        //TODO реализовать
        return null;
    }

    /** Метод для записи сообщений в файл */
    public static void writeMessages(List<ClientMessage> msgs, String fileName){
        //TODO реализовать
    }
}
