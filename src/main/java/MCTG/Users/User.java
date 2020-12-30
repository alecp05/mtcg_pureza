package MCTG.Users;

import MCTG.Card;

import java.util.LinkedList;

public class User {
    private String Username;
    private String Password;
    private int Coins;
    private String Elo;
    private String Bio;
    private String Image;

    public User(String name, String password){
        this.Username = name;
        this.Password = password;
        this.Coins = 20;
        this.Elo = "Bronze";
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

    public int getCoins() {
        return Coins;
    }

    public String getElo() {
        return Elo;
    }

    public String getBio() {
        return Bio;
    }

    public String getImage() {
        return Image;
    }
}
