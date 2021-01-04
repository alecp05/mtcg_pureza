package MCTG;

import MCTG.Cards.Card;

import java.util.ArrayList;

public class Player {
    private String username;
    private ArrayList<Card> deck;

    public String getUsername() {
        return username;
    }

    public void setDeck(ArrayList<Card> deck1) {
        this.deck = deck1;
    }

}
