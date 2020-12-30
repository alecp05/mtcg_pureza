package MCTG.RestHttp;

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
}
