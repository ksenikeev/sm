package ru.kpfu.icmit.clientsm;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

/**
 * Класс реализует:
 * 1) функции по сохранению отправленных и прочитанных сообщений в файл.
 * 2) функции по считыванию отправленных и прочитанных сообщений из файла.
 * <p>
 * Формат файла: текстовый файл в кодировке UTF-8.
 * Каждая строка содержит отдельное сообщение в виде JSON объекта
 * {"from":{"id":"","name":""}, "to":{"id":"","name":""}, "content":"", "datemsg":"yyyy-MM-dd HH:mm:ss", "status":""}
 * TODO необходимо подготовить класс, моделирующий такой JSON объект (прототип ClientMessage)
 */
public class StoreMessages {

   /** Метод для чтения сохраненных сообщений из файла */
public static List<ClientMessage> readMessages(String fileName){
Gson gson = new Gson();
List<ClientMessage> cml= new ArrayList<ClientMessage>();

try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(
                new FileInputStream(fileName), StandardCharsets.UTF_8))){
    String line;
    while ((line = reader.readLine()) != null) {
    	cml.add(gson.fromJson(line, ClientMessage.class));
        System.out.println(line);
    }
} catch (IOException e) {
    // log error
}
return cml;
}
    /**
     * Метод для записи сообщений в файл (архивирование при завершении программы
     * @autor Габутдинова
     */
    public static void writeMessages(List<ClientMessage> msgs, String fileName) {
        Gson gson = new Gson();

        try (FileWriter writer = new FileWriter("history.joc")) {
            for (ClientMessage cm : msgs) {
                String msg = gson.toJson(cm);
                writer.write(msg + "\n");
            }
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
