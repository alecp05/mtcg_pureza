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

    public void showCards(){
       /*for(int i=0; i<this.deck.length; i++){
           System.out.println(this.deck[i].getCardName());
       }

        for (Card element : this.deck){
            System.out.println(element.getCardName() + "\n");
        }*/

    }
}
