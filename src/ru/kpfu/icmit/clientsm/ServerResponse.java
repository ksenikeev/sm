package ru.kpfu.icmit.clientsm;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * Класс для обработки ответа сервера
 * @autor Svechnikova
 */
/* Пример:
   HTTP/1.1 200 OK
   Connection: close
   Date: Sat Nov 25 00:49:18 MSK 2017
   Server: ICMIT/0.0.1
   Content-Type: application/json
   Content-Length: 56

   {"token":"d9f42f86-bc4a-430b-8b10-e8c75dec5d0d","status":"success"}
*/
public class ServerResponse {
    public int responseCode;
    public String responseDescription;
    public String contentType;
    public int contentLength;
    public String content;
    public static ServerResponse parse(ArrayList<String> header) {
        ServerResponse sr=new ServerResponse();
        String codecontent=header.get(0);
        String[] code=codecontent.split(" ");
        if(code.length>3) {

            for(int i=3; i<code.length; i++)
                code[2]=code[2]+" "+code[i];
        }
        sr.responseCode=Integer.valueOf(code[1]);
        sr.responseDescription=code[2];
        HashMap<String,String> response = new HashMap<>();
        for(int i = 1; i<header.size(); i++) {
            String s = header.get(i);
            int k = s.indexOf(':');
            if (k>0) {
                String s1 = s.substring(0, k).toUpperCase();
                String s2 = s.substring(k + 1).trim();
                response.put(s1, s2);
            }
        }
        sr.contentType=response.get("CONTENT-TYPE");
        if (response.get("CONTENT-LENGTH")!=null && response.get("CONTENT-LENGTH").length()>0)
            sr.contentLength=Integer.valueOf(response.get("CONTENT-LENGTH"));
        return sr;
    }

    public static void main(String[] args){
        ArrayList<String> header = new ArrayList<>();
        header.add("HTTP/1.1 200 OK");
        header.add("Connection: close");
        header.add("Date: Sat Nov 25 00:49:18 MSK 2017");
        header.add("Server: ICMIT/0.0.1");
        header.add("Content-Type: application/json");
        header.add("Content-Length: 56");
        ServerResponse response=ServerResponse.parse(header);
        System.out.println(response.responseCode);
        System.out.println(response.responseDescription);
        System.out.println(response.contentType);
        System.out.println(response.contentLength);
    }
}