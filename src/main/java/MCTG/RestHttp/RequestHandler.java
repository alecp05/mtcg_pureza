package MCTG.RestHttp;

import MCTG.Shop.PackageHandler;
import MCTG.Users.UserHandling;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class RequestHandler {

    public static void pathsHandling(String path, String method, String payload, List<String> headers, Socket client) throws IOException {
        UserHandling user = new UserHandling();
        PackageHandler packages = new PackageHandler();
        ResponseHandler responder = new ResponseHandler();

            //USER REGISTRATION AND LOGIN
            if(path.equals("/users") && method.equals("POST")){

                int number = user.registration(payload);

                if(number == 0)
                    client.getOutputStream().write(responder.responseRegistrationPOST().getBytes(StandardCharsets.UTF_8));
                else if (number == 1)
                    client.getOutputStream().write(responder.responseErrorRegistrationPOST().getBytes(StandardCharsets.UTF_8));
            }
            else if(path.equals("/sessions")&& method.equals("POST")){
                int number = user.login(payload);

                if(number == 0)
                    client.getOutputStream().write(responder.responseLoginPOST().getBytes(StandardCharsets.UTF_8));
                else if (number == 1)
                    client.getOutputStream().write(responder.responseErrorLoginPOST().getBytes(StandardCharsets.UTF_8));
            }
            //PACKAGES
            else if(path.equals("/packages")&& method.equals("POST")){
                System.out.println(headers.get(3));
                //check Token, if its admin
                if(headers.get(3).contains("admin-mtcgToken")){
                    int number = packages.addPackages(payload);
                    if(number == 0)
                        client.getOutputStream().write(responder.responseAddPackagePOST().getBytes(StandardCharsets.UTF_8));
                    else if (number == 1)
                        client.getOutputStream().write(responder.responseErrorAddPackagePOST().getBytes(StandardCharsets.UTF_8));
                }else{
                    //if not admin
                    client.getOutputStream().write(responder.responseErrorTokenPackagePOST().getBytes(StandardCharsets.UTF_8));
                }

            }
            else if(path.equals("/transactions/packages")&& method.equals("POST")){
                String authToken = headers.get(3);
                int number = packages.buyPackages(payload,authToken);

                if(number == 0)
                    client.getOutputStream().write(responder.responseBuyingPackagePOST().getBytes(StandardCharsets.UTF_8));
                else if (number == 1)
                    client.getOutputStream().write(responder.responseErrorBuyingPackagePOST().getBytes(StandardCharsets.UTF_8));

            }

    }
}
