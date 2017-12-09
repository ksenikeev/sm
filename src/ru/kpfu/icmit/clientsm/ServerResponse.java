package ru.kpfu.icmit.clientsm;

import java.io.InputStream;
import java.util.Scanner;
/**
 * Класс для обработки ответа сервера
 * Svechnikova
 */
/* Пример:
   HTTP/1.1 200 OK
   Connection: close
   Date: Sat Nov 25 00:49:18 MSK 2017
   Server: ICMIT/0.0.1
   Content-Type: application/json
   Content-Lenght: 56

   {"token":"d9f42f86-bc4a-430b-8b10-e8c75dec5d0d","status":"success"}
*/
public class ServerResponse {
    public int responseCode;
    public String responseDescription;
    public String contentType;
    public int contentLength;
    public String content;

    public static ServerResponse parse(InputStream inputStream) {
        ServerResponse sr=new ServerResponse();
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        String response_msg = s.hasNext() ? s.next() : "";
        String[] response=response_msg.split("\n");
        int n=response.length;
        response[0]=response[0].replaceAll("[A-Z]","");
        response[0]=response[0].replaceAll("/1.1 ","");
        response[0]=response[0].replaceAll(" ","");
        sr.responseCode=Integer.valueOf(response[0]);
        if(sr.responseCode==200)
        {
            String[] dscr=response[n-1].split(",");
            //for (int i=0; i<msg.length; i++)
            //System.out.println(msg[i]);
            if(dscr[0].indexOf("error")!=0) {
                dscr[1]=dscr[1].replaceAll("\"description\":\"", "");
                dscr[1]=dscr[1].replaceAll("\"}","");
                sr.responseDescription=dscr[1];
            }
            else
                sr.responseDescription=null;
            sr.contentType=response[4].replaceAll("Content-Type: ", "");
            response[5]=response[5].replaceAll("Content-Lenght: ", "");
            sr.contentLength=Integer.valueOf(response[5]);
            if(sr.contentLength>0)
                sr.content=response[6];
        }
        return null;
    }

}