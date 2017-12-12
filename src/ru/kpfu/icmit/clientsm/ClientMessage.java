package ru.kpfu.icmit.clientsm;

import java.util.*;
import java.text.*;

/**
 * Класс, моделирующий JSON объект-сообщение
 * {"from":{"id":"","name":""}, "to":{"id":"","name":""}, "content":"", "datemsg":"yyyy-MM-dd HH:mm:ss", "status":""}
 *
 * @autor Zubairova
 */
public class ClientMessage {

    public Abonent from;

    public Abonent to;

    public String content;

    public String status;

    public String datemsg;

    public static String cm2JSON (ClientMessage cm) {
        if (cm != null) {
            return "{\"from\":{\"id\":\"" + cm.from.id + "\",\"name\":\"" + cm.from.name + "\"}, " +
                    "\"to\":{\"id\":\"" + cm.to.id + "\",\"name\":\"" + cm.to.name + "\"}, " +
                    "\"content\":\"" + cm.content + "\", " +
                    "\"datemsg\":\"" + cm.datemsg + "\", " +
                    "\"status\":\""+cm.status+"\"}";
        } else
            return null;
    }
    public static void main(String args[]) {
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        String str = args.length == 0 ? "yyyy-MM-dd HH:mm:ss" : args[0];
        Date parsingDate;
        try {
            parsingDate = ft.parse(str);
            System.out.println(parsingDate);
        }catch (ParseException e) {
            System.out.println("datemsg: " + str);
        }
    }
}
