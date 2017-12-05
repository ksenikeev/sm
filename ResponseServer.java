import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by AAVahrusheva on 05.12.2017.
 */
public class ResponseServer {
    public void send(OutputStream OS, String message){
        String msg="HTTP/1.1 200 OK"+"\r\n"+" Connection: close"+
                "\r\n"+"Date: Tue Dec 5 14:49:18 MSK 2017"+"\r\n"+
                "Server: ICMIT/0.0.1"+"\r\n";
                if (message.length()>0){
                    msg=msg+"Content-Type: application/json"+"\r\n"+"Content-Lenght: "+ message.getBytes().length+"\r\n"+"\r\n"+message;
                    try {
                        OS.write(msg.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
    }
}
