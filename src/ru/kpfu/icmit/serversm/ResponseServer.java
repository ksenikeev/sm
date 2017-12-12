package ru.kpfu.icmit.serversm;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

/**
 * Created by AAVahrusheva on 05.12.2017.
 */
public class ResponseServer {
    public static void send(OutputStream os, String message){

        String msg="HTTP/1.1 200 OK"+"\r\n"+
                "Connection: close"+"\r\n"+
                "Date: "+new Date()+"\r\n"+
                "Server: ICMIT/0.0.1"+"\r\n";
        if (message.length()>0){
            msg=msg+"Content-Type: application/json"+"\r\n"+
                    "Content-Length: "+ message.getBytes().length+"\r\n"+
                    "\r\n"+
                    message;
        } else{
            msg=msg+"\r\n";
        }
        try {
            os.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Response: "+msg);
    }
}
