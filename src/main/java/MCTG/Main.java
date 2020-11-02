package MCTG;

import MCTG.Cards.Dragon;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        System.out.println("Welcome to MONSTER TRADING CARDS GAME - MTCG!");

        Scanner myObj = new Scanner(System.in);
        System.out.println("Please enter a Username: ");
        String userName = myObj.nextLine();

        Player player1 = new Player(userName);
        Card[] cardsplayer1 = {new Card()};
        player1.setDeck(cardsplayer1);

        System.out.println("Player: " +player1.getUsername());
        System.out.println("Deck: ");
        player1.showcards();

        Dragon dragon1 = new Dragon();
        System.out.println(dragon1.getElementType());



        //System.out.println(player1.getCoins());
        //Card firemonster = new Card();
        //System.out.println(firemonster.getCardName());
    }
}
