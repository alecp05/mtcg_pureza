package MCTG;

public class Card {
    private int damage;
    private ElementType elementType;
    private CardType cardType;

    public Card(){
        this.damage = 30;
        this.elementType = ElementType.fire;
        this.cardType = CardType.monster;
    }

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
