package MCTG;

import MCTG.Enums.CardType;
import MCTG.Enums.ElementType;

public class Card {
    private String cardName;
    private int damage;
    private ElementType elementType;
    private CardType cardType;

    public Card(){
        this.cardName = "monster1";
        this.damage = 30;
        this.elementType = ElementType.fire;
        this.cardType = CardType.monster;
    }

    public String getCardName() { return cardName; }

    public CardType getCardType() {
        return cardType;
    }

    public ElementType getElementType() {
        return elementType;
    }

    public int getDamage() {
        return damage;
    }
}
