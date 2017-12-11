package ru.kpfu.icmit.clientsm;

import com.google.gson.Gson;
import java.util.ArrayList;

/**
 * Класс отрабатывает регистрацию пользователя на стороне клиента
 */
public class Registration {

    //Отправка регистрационных данных и прием ответа от сервера
    public static ServerResponse sendRegInfo(String login, String password){

        System.out.println("Start registration. Username "+login);
        //Формирование JSON объекта который будет отправлен на сервер
        String message = "{\"name\":\""+login+"\",\"login\":\""+login+"\",\"password\":\""+password+"\"}";

        HTTPSender sender = new HTTPSender("127.0.0.1", "3128");
        //Отправляем сообщение message на сервер с указанием ресурса "/reguser" который должен обработать наше сообщение
        ServerResponse resp = sender.sendMessage(message, "/reguser");

        System.out.println("Response code: "+resp.responseCode);
        // Возвращаем ответ сервера для дальнейшего анализа
        return resp;
    }

    //После успешной регистрации запрашиваем список всех абонентов системы (чтобы было кому писать)
    public static ArrayList<Abonent> getAbonentList(String token){
        System.out.println("Request abonent list");
        String message = "{\"token\":\""+token+"\"}";
        HTTPSender sender = new HTTPSender("127.0.0.1", "3128");
        ServerResponse resp = sender.sendMessage(message, "/getusers");
        if (resp.responseCode!=200) return null;
        Gson gson = new Gson();
        Abonents result = gson.fromJson(resp.content, Abonents.class);
        return result.usrlist;
    }

    /** Тест */
    public static void main(String[] args ){
        Gson gson = new Gson();
        // Пример сообщения, которое может прийти от сервер
        String msg = "{\"usrlist\":[{\"id\":\"1\", \"name\":\"User1\"},{\"id\":\"2\", \"name\":\"User2\"},{\"id\":\"3\", \"name\":\"User3\"}]}";
        Abonents result = gson.fromJson(msg, Abonents.class);
        for(int i=0;i< result.usrlist.size();i++){
            System.out.println(result.usrlist.get(i).name);
        }
    }

    class Abonents{
        ArrayList<Abonent> usrlist;
    }
}
