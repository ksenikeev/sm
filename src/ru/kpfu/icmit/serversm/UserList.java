package ru.kpfu.icmit.serversm;


import com.google.gson.Gson;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class UserList
{   public static String get(String token){
        Statement st=null;
        String result = "";
        try{
            st = DbWork.initDb().createStatement();
            ResultSet rs;// = st.executeQuery("'select * from users where sean_id=" + token + "'");
            //if(rs.next()){
                {
                rs = st.executeQuery("select * from users");
                result = "{\"usrlist\":[";
                while(rs.next()){
                    result += "{\"id\":\"" + rs.getString("id") + "\", \"name\":\"" + rs.getString("username") + "\"},";
                }
                result = result.substring(0,result.length()-2);
                result += "]}";
                System.out.println(result);
            }
//            else{
//                result = "{\"status\":\"error\"}";
//            }
            st.close();
        }
        catch(SQLException e){
            result = "{\"status\":\"error\"}";
            e.printStackTrace();
        }
        return result;
    }
}
