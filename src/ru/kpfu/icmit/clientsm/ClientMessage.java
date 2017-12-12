package ru.kpfu.icmit.clientsm;

import com.google.gson.Gson;

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

   public static ClientMessage JSON2cm (String jSON) {
        	Gson cm = new Gson ();
        	
			ClientMessage result = cm.fromJson(jSON, ClientMessage.class);
        	return result;
        }
}
