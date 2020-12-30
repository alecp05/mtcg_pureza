package MCTG;

import MCTG.Cards.Card;

import java.util.LinkedList;

public class Player {
    private String username;
    private String password;
    //private Card[] cardStack;
    private LinkedList<Card> cardStack;
    private int coins;
    //private Card[] deck;
    private LinkedList<Card> deck;
    private String elo;

    public Player(String username){
        this.username = username;
        this.coins = 100;
        this.elo = "Bronze";
    }

    public String getUsername() {
        return username;
    }

    public int getCoins() {
        return coins;
    }

    public String getElo() {
        return elo;
    }

    public void setDeck(LinkedList<Card> deck1) {
        this.deck = deck1;
    }

    public void showCards(){
       /*for(int i=0; i<this.deck.length; i++){
           System.out.println(this.deck[i].getCardName());
       }

        for (Card element : this.deck){
            System.out.println(element.getCardName() + "\n");
        }*/

    }
}
