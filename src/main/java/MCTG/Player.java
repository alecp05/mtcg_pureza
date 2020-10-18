package MCTG;

public class Player {
    private String username;
    private String password;
    private Card[] cardStack;
    private int coins;
    private Card[] deck;
    private int elo;

    public Player(String username){
        this.username = username;
        this.coins = 100;
        this.elo = 5;
    }

    public String getUsername() {
        return username;
    }

    public int getCoins() {
        return coins;
    }

    public int getElo() {
        return elo;
    }
}
