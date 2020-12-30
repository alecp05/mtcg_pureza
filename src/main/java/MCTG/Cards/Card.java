package MCTG.Cards;

import MCTG.Enums.CardType;
import MCTG.Enums.ElementType;

public class Card {
    private int Id;
    private String Name;
    private int Damage;
    private ElementType elementType;
    private CardType cardType;

    /*public Card(int Id, String Name, int Damage){
        this.Id = Id;
        this.Name = Name;
        this.Damage = Damage;
    }*/
    public Card(){
        super();
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public int getDamage() {
        return Damage;
    }


}
