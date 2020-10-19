package MCTG;

import java.util.Arrays;

public class Player {
    private String username;
    private String password;
    private Card[] cardStack;
    private int coins;
    private Card[] deck;
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

    public void setDeck(Card[] deck) {
        this.deck = deck;
    }

    public void showcards(){
       for(int i=0; i<this.deck.length; i++){
           System.out.println(this.deck[i].getCardName());
       }
    }
}
