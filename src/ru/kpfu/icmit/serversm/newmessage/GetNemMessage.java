package ru.kpfu.icmit.serversm;

import com.google.gson.Gson;

import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Алмаз on 05.12.2017.
 */
public class GetNemMessage {
    public static String get(String message, OutputStream os){
        Gson gson = new Gson();
        Map<String,String> map = new HashMap<>();
        map = gson.fromJson(message, map.getClass());
        String token = map.get("token");
        Statement st = null;
        String result= "";
        try {
            st = DbWork.initDb().createStatement();
            ResultSet rs = st.executeQuery(
                    "select id from users where sean_id='"+token+"'");
            String user_id="0";
            if (rs.next()) {
               user_id = rs.getString("id");
            }
            st = DbWork.initDb().createStatement();
            ResultSet rs1 = st.executeQuery(
                    "select * from messages where touser = "+user_id+" and readed = false");
            result = "{\"msglist\":[";
            while (rs.next()) {
                result += "{\"from\":\""+rs.getString("fromuser");
                result += "{\"content\":\""+rs.getString("content text");
                result += "{\"date\":\""+rs.getString("datemsg");
                System.out.println(rs.getString("login") + "-" + rs.getString("password"));
            }
            result += "]}";
        } catch (SQLException e) {
            e.printStackTrace();
        }







        return result;
    }
}
