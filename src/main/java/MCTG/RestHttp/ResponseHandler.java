package MCTG.RestHttp;

import MCTG.Cards.Card;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

public class ResponseHandler {
    public String responseRegistrationPOST(){
        String response = "POST Request SUCCESS\nUser is now in the Database";
        String httpResponse = "HTTP/1.1 201 Created\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 201 \r\n"
                + "Content-Lenght: 61 \r\n\r\n" + response;
        return httpResponse;
    }

    public String responseErrorRegistrationPOST(){
        String httpResponse = "HTTP/1.1 404 not Found\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 404 \r\n"
                + "Content-Lenght: 16 \r\n\r\n" + "Already Exists! Or Name is already been used!";
        return httpResponse;
    }

    public String responseLoginPOST(){
        String response = "POST Request SUCCESS\nUser is now logged in";
        String httpResponse = "HTTP/1.1 202 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 201 \r\n"
                + "Content-Lenght: 61 \r\n\r\n" + response;
        return httpResponse;
    }

    public String responseErrorLoginPOST(){
        String httpResponse = "HTTP/1.1 404 not Found\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 404 \r\n"
                + "Content-Lenght: 16 \r\n\r\n" + "Could not Log in.. Try again";
        return httpResponse;
    }
    public String responseAddPackagePOST(){
        String response = "POST Request SUCCESS\nPackage has been created";
        String httpResponse = "HTTP/1.1 202 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 201 \r\n"
                + "Content-Lenght: 61 \r\n\r\n" + response;
        return httpResponse;
    }
    public String responseErrorAddPackagePOST(){
        String httpResponse = "HTTP/1.1 400 Bad Request\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 404 \r\n"
                + "Content-Lenght: 16 \r\n\r\n" + "Could add create Package.. Try again";
        return httpResponse;
    }
    public String responseErrorTokenPackagePOST(){
        String httpResponse = "HTTP/1.1 403 Forbidden\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 404 \r\n"
                + "Content-Lenght: 32 \r\n\r\n" + "You are not allowed to add Packages/ Wrong Token...";
        return httpResponse;
    }
    public String responseBuyingPackagePOST(){
        String response = "POST Request SUCCESS\nPackage is now in Users collection";
        String httpResponse = "HTTP/1.1 202 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 201 \r\n"
                + "Content-Lenght: 61 \r\n\r\n" + response;
        return httpResponse;
    }
    public String responseErrorBuyingPackagePOST(){
        String httpResponse = "HTTP/1.1 404 not Found\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 404 \r\n"
                + "Content-Lenght: 32 \r\n\r\n" + "User has no coins left OR no more Packages are available...";
        return httpResponse;
    }

    public String responseShowCardsGet(){
        String httpResponse = "HTTP/1.1 202 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 201 \r\n"
                + "Content-Lenght: 0 \r\n\r\n";
        return httpResponse;
    }
    public String responseErrorShowCardsDecksGet(){
        String httpResponse = "HTTP/1.1 404 not Found\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 404 \r\n"
                + "Content-Lenght: 32 \r\n\r\n" + "Failed... No Token given...";
        return httpResponse;
    }
    public String responseErrorShowDecksNULLGet(){
        String httpResponse = "HTTP/1.1 404 not Found\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 404 \r\n"
                + "Content-Lenght: 32 \r\n\r\n" + "The user has no Cards in his Deck...";
        return httpResponse;
    }

    public String responseConfDeckPUT(){
        String httpResponse = "HTTP/1.1 202 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 201 \r\n"
                + "Content-Lenght: 0 \r\n\r\n" + "User chosen Cards are now is Users Deck...";
        return httpResponse;
    }

    public String responseError1ConfDeckPUT(){
        String httpResponse = "HTTP/1.1 404 not Found\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 404 \r\n"
                + "Content-Lenght: 32 \r\n\r\n" + "The wrong Token has been given, User does not have those cards...";
        return httpResponse;
    }

    public String responseError2ConfDeckPUT(){
        String httpResponse = "HTTP/1.1 404 not Found\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 404 \r\n"
                + "Content-Lenght: 32 \r\n\r\n" + "Users Input has not enough cards to be saved in Deck (4 Cards needed)...";
        return httpResponse;
    }

    public String responseUserDataGET(){
        String httpResponse = "HTTP/1.1 202 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 201 \r\n"
                + "Content-Lenght: 0 \r\n\r\n" + "The Token has been approved... This is the Users Data:\n";
        return httpResponse;
    }

    public String responseErrorUserDataGETAndPOST(){
        String httpResponse = "HTTP/1.1 404 not Found\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 404 \r\n"
                + "Content-Lenght: 32 \r\n\r\n" + "The wrong Token has been given...";
        return httpResponse;
    }

    public String responseUserDataPUT(){
        String httpResponse = "HTTP/1.1 202 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 201 \r\n"
                + "Content-Lenght: 0 \r\n\r\n" + "Users Data has been edited and updated\n";
        return httpResponse;
    }

    public String responseErrorStats(){
        String httpResponse = "HTTP/1.1 404 not Found\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 404 \r\n"
                + "Content-Lenght: 32 \r\n\r\n" + "No Token is given... Try again";
        return httpResponse;
    }

    public String responseStatsGET(){
        String httpResponse = "HTTP/1.1 202 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 201 \r\n"
                + "Content-Lenght: 32 \r\n\r\n" + "This is the Users Stats: \n";
        return httpResponse;
    }

    public String responseScoreGET(){
        String httpResponse = "HTTP/1.1 202 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 201 \r\n"
                + "Content-Lenght: 32 \r\n\r\n" + "This is the Scoreboard: \n";
        return httpResponse;
    }

    public String testingJson() throws JsonProcessingException {
        JSONObject jo = new JSONObject();
        jo.put("firstName", "John");
        jo.put("lastName", "Doe");

        JSONArray ja = new JSONArray();
        ja.put(jo);

        ObjectMapper mapper = new ObjectMapper();
        Card staff = new Card("hey","lol",12,"heys","his");
        // pretty print
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(staff);
        String json2 = json + json;

        System.out.println(json);

        String httpResponse = "HTTP/1.1 404 not Found\r\n"
                + "Content-Type: application/json\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 404 \r\n"
                + "Content-Lenght: 32 \r\n\r\n" + json2;
        return httpResponse;
    }

    public String responseErrorPATH(){
        String httpResponse = "HTTP/1.1 404 not Found\r\n"
                + "Content-Type: text/html\r\n"
                + "Accept-Ranges: bytes \r\n"
                + "Server: Alec \r\n"
                + "Status: 404 \r\n"
                + "Content-Lenght: 32 \r\n\r\n" + "INVALID PATH... Try again";
        return httpResponse;
    }

}
