/**
 * Created by kriso on 05.12.2017.
 */
package src.ru.kpfu.icmit.serversm;

import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Login {
    public static String get(String message, OutputStream os){
        Gson gson = new Gson();
        Map<String,String> map = new HashMap<>();
        map = gson.fromJson(message, map.getClass());
        String login = map.get("login");
        String password = map.get("password");
        Statement st = null;
        String result= "";
        try {
            st = src.ru.kpfu.icmit.serversm.DbWork.initDb().createStatement();
            ResultSet rs = st.executeQuery(
                    "'select * from users where username 'param_username' + 'and login='"+login+"' and password='"+password+"'");
            if(rs.next()) {
                String user_id = rs.getString("id");
                UUID token = UUID.randomUUID();
                st.execute("update users set seans_id='" + token +"' where id ='"
                        +user_id+ "'");
                result = "{\"token\":\""+token+"\",\"status\":\"success\"}";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
