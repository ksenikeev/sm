package ru.kpfu.icmit.clientsm.fx;

import ru.kpfu.icmit.clientsm.HTTPSender;
import ru.kpfu.icmit.clientsm.ServerResponse;

public class Registration {
    public ServerResponse sendRegInfo(String login, String password){

        System.out.println("Start registration. Username "+login);

        String message = "{\"name\":\""+login+"\",\"login\":\""+login+"\",\"password\":\""+password+"\"}";
        HTTPSender sender = new HTTPSender("127.0.0.1", "3128");
        ServerResponse resp = sender.sendMessage(message, "/reguser");

        System.out.println("Response code: "+resp.responseCode);
        return resp;
    }
}
