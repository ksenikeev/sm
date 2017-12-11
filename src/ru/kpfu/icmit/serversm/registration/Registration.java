package ru.kpfu.icmit.serversm.registration;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import ru.kpfu.icmit.serversm.DbWork;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class Registration {

    /**
     * Регистрация в системе нового пользователя
     * @param bodyMessage = {"name":"UserName","login":"user1","password":"pwd"}
     */
    public static RegistrationResp makeRegistration(String bodyMessage){
        RegistrationResp resp = new RegistrationResp();
        Gson gson = new Gson();
        NewUser newUser=null;
        try {
            newUser = gson.fromJson(bodyMessage, NewUser.class);
        } catch (JsonSyntaxException e){
            resp.status=false;
            resp.description="Ошибка обработки JSON ("+bodyMessage+")";
            return resp;        }
        if (newUser==null){
            resp.status=false;
            resp.description="Данные для регистрации не достаточны";
            return resp;        }
        if (newUser.name==null || newUser.login==null || newUser.password==null){
            resp.status=false;
            resp.description="Данные для регистрации не достаточны";
            return resp;
        }

        Connection connection = DbWork.initDb();
        try (Statement st = connection.createStatement()){
            //Проверим наличие пользователя с таким именем или логином
            ResultSet r = st.executeQuery("select id from users where username = '"+newUser.name+
                    "' OR login='"+newUser.login+"'");
            if( r!=null && r.next()){
                resp.status=false;
                resp.description="Указанные имя или логин уже используются";
                return resp;
            }
            String token = UUID.randomUUID().toString();
            st.execute("insert into users (username,login,password,seans_id) values ('"+
                newUser.name+"','"+newUser.login+"','"+newUser.password+"','"+ token +"')");
            resp.status = true;
            resp.token = token;
        } catch (SQLException e) {
            resp.status=false;
            resp.description="Ошибка БД: "+e.getSQLState();
            e.printStackTrace();
        }
        return resp;
    }

    class NewUser{
        String name;
        String login;
        String password;
    }
}
