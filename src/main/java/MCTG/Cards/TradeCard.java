package MCTG.Cards;

public class TradeCard {

    private String tradeId;
    private String username;
    private String cardId;
    private String cardName;
    private int damage;
    private String elementType;
    private String cardType;
    private String requiredType;
    private int requiredDamage;

    public TradeCard(String tradeId, String username, String Id, String Name, int Damage, String eType, String cType, String requiredType, int requiredDamage){

        this.tradeId = tradeId;
        this.username = username;
        this.cardId = Id;
        this.cardName = Name;
        this.damage = Damage;
        this.elementType = eType;
        this.cardType = cType;
        this.requiredType = requiredType;
        this.requiredDamage = requiredDamage;
    }

    public String getTradeId() {
        return tradeId;
    }

    public String getUsername() {
        return username;
    }

    public String getCardId() {
        return cardId;
    }

    public String getCardName() {
        return cardName;
    }

    public int getDamage() {
        return damage;
    }

    public String getElementType() {
        return elementType;
    }

    public String getCardType() {
        return cardType;
    }

    public String getRequiredType() {
        return requiredType;
    }

    public int getRequiredDamage() {
        return requiredDamage;
    }
}
