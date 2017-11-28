package ru.kpfu.icmit.clientsm;

import java.io.InputStream;

/**
 * Класс для обработки ответа сервера
 */
public class ServerResponse {
    public int responseCode;
    public String responseDescription;
    public int contentLength;
    public String contentType;
    public String content;

    public static ServerResponse parse(InputStream inputStream) {
        return null;
    }
}
