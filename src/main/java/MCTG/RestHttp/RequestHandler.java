package MCTG.RestHttp;

import MCTG.Users.UserHandling;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RequestHandler {

    public static void pathsHandling(String path, String method, String payload, Socket client) throws IOException {
        UserHandling user = new UserHandling();
        ResponseHandler responser = new ResponseHandler();

            if(path.equals("/users")){

                int number = user.registration(payload);

                if(number == 0)
                    client.getOutputStream().write(responser.responseRegistrationPOST().getBytes(StandardCharsets.UTF_8));
                else if (number == 1)
                    client.getOutputStream().write(responser.responseErrorRegistrationPOST().getBytes(StandardCharsets.UTF_8));
            }
            else if(path.equals("/sessions")){
                int number = user.login(payload);

                if(number == 0)
                    client.getOutputStream().write(responser.responseLoginPOST().getBytes(StandardCharsets.UTF_8));
                else if (number == 1)
                    client.getOutputStream().write(responser.responseErrorLoginPOST().getBytes(StandardCharsets.UTF_8));
            }
    }
}
