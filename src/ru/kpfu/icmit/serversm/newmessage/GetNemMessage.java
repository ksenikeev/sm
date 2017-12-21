package ru.kpfu.icmit.serversm.newmessage;

import com.google.gson.Gson;
import ru.kpfu.icmit.serversm.DbWork;

import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Алмаз on 05.12.2017.
 * @author Sibgatullin

 */
public class GetNemMessage {
    public static String get(String message){
        Gson gson = new Gson();
        Map<String,String> map = new HashMap<>();
        map = gson.fromJson(message, map.getClass());
        String token = map.get("token");
        Statement st = null;
        String result= "";
        try {
            st = DbWork.initDb().createStatement();
            ResultSet rs = st.executeQuery(
                    "select id from users where seans_id='"+token+"'");
            String user_id="0";
            if (rs.next()) {
               user_id = rs.getString("id");
            }
            st.close();
            st = DbWork.initDb().createStatement();
            ResultSet rs1 = st.executeQuery(
                    "select * from messages  join users on fromuser = users.id where touser = "+user_id+" and readed = false");

            while (rs1.next()) {
                /* TODO в соответствии с изменениями в документации необходимо правильно сформировать
                    JSON объект по образцу
                    {"msglist":[сообщение1,...,сообщениеN]}
                    сообщение - объект по образцу
                    {"from":{"id":"код_отправителя", "name":"имя_отправителя"}, "content":"текст_сообщения", "date":"дата"}
                */
                result += "{\"from\":\""+rs1.getString("fromuser")+"\"";
                // получение имени отправителя rs1.getString("username")
                result += ",\"content\":\""+rs1.getString("content")+"\"";
                result += ",\"date\":\""+rs1.getString("datemsg")+"\"},";
            }
            if (!result.equals("")) {
                result = "{\"msglist\":["+result;
                result = result.substring(0, result.length() - 1);
                result += "]}";
            }
            st.close();

            st = DbWork.initDb().createStatement();
            st.execute(
                    "update messages set readed = 'true'");
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
