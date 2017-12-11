package ru.kpfu.icmit.serversm;

import com.google.gson.Gson;

public class Registration {

    /**
     * Регистрация в системе нового пользователя
     * @param bodyMessage = {"name":"UserName","login":"user1","password":"pwd"}
     */
    public static void makeRegistration(String bodyMessage){
        Gson gson = new Gson();
        NewUser newUser = gson.fromJson(bodyMessage, NewUser.class);
        if (newUser.name==null || newUser.login==null || newUser.password==null){
            
            return;
        }
    }

    class NewUser{
        String name;
        String login;
        String password;
    }
}
