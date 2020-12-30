package MCTG;

import MCTG.Cards.Dragon;
import MCTG.RestHttp.RequestContext;
import MCTG.RestHttp.RequestHandler;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args){
        System.out.println("Welcome to MONSTER TRADING CARDS GAME - MTCG!");

        /*Scanner myObj = new Scanner(System.in);
        System.out.println("Please enter a Username: ");
        String userName = myObj.nextLine();

        Player player1 = new Player(userName);
        LinkedList<Card> currentDeck = new LinkedList<Card>();
        Card testingCard = new Card();
        currentDeck.add(testingCard);
        currentDeck.add(testingCard);
        player1.setDeck(currentDeck);

        System.out.println("Player: " +player1.getUsername());
        System.out.println("Deck: ");
        player1.showCards();

        Dragon dragon1 = new Dragon();
        System.out.println(dragon1.getElementType());*/



        //System.out.println(player1.getCoins());
        //Card firemonster = new Card();
        //System.out.println(firemonster.getCardName());



        ServerSocket serverSocket;
        try {
            //ready to listen incoming connection on port 8080
            serverSocket = new ServerSocket(8080);
            System.out.println("Listening for connection on port 8080 ...");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try {
            while (true) {
                //accepting incoming connection
                Socket socket = serverSocket.accept();
                handleRequest(socket);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleRequest(Socket client) throws IOException{

        //read the content of the InputStream with BufferedReader
        BufferedReader breader = new BufferedReader(new InputStreamReader(client.getInputStream()));

        StringBuilder build = new StringBuilder();
        String lines;
        StringBuilder content = new StringBuilder();

        //only HTTP Form Terms
        while (!(lines = breader.readLine()).isBlank()) {
            build.append(lines + "\r\n");
        }
        String request = build.toString();
        System.out.println(request);

        RequestContext requestClient = new RequestContext(request);
        requestClient.printContexts();

        //--------------------------------
        //which Request is being received
        String usedMethod = requestClient.getMethod();


            System.out.println(requestClient.getPath());

            int counter = Integer.parseInt(requestClient.getContentLength());
            int value;
            //saving payload by characters
            for (int j = 0; j < counter; j++) {
                value = breader.read();
                content.append((char) value);
            }
            //payload being set
            requestClient.setPayload(content.toString());

            System.out.println("Message:");
            System.out.println(requestClient.getPayload());

        RequestHandler handlingR = new RequestHandler();
        handlingR.pathsHandling(requestClient.getPath(),requestClient.getMethod(),requestClient.getPayload(),client);


        client.close();
    }

}
