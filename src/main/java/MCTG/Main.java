package MCTG;

import java.sql.SQLOutput;
import java.util.Arrays;

public class Main {
    public static void main(String[] args){
        System.out.println("Welcome to MONSTER TRADING CARDS GAME - MTCG!");

        Player player1 = new Player("Alec");
        Card[] cardsplayer1 = {new Card()};
        player1.setDeck(cardsplayer1);

        System.out.println("Player: " +player1.getUsername());
        System.out.println("Deck: ");
        player1.showcards();


        //System.out.println(player1.getCoins());
        //Card firemonster = new Card();
        //System.out.println(firemonster.getCardName());
    }
}
