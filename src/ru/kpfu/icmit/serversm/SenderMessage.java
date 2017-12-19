
package ru.kpfu.icmit.serversm;

import com.google.gson.Gson;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @autor Adilgereyev
 */
public class SenderMessage {

    public static boolean sendMsg(String message){
        Gson cm = new Gson();
        Message n = cm.fromJson(message, Message.class);
        Statement st = null;
        try {
            st = DbWork.initDb().createStatement();
            ResultSet rs = st.executeQuery(
                    "select id from users where sean_id='"+n.token+"'");
            String user_id="0";
            if (rs.next()) {
                user_id = rs.getString("id");
            }
            st = DbWork.initDb().createStatement();
            st.execute("insert into messages (fromuser, touser,content,datamsg) values (" +user_id+","+n.abonent+",'"+
                    n.content+"','"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"')");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 1. Проверка токена пользователя

        return false;
    }
    class Message{
        String token;
        String abonent;
        String content;

    }
}