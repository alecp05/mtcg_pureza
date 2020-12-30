package MCTG.Users;

public class User {
    private String Username;
    private String Password;
    private int Coins;
    private int Elo;
    private String Bio;
    private String Image;

    public User(String name, String password){
        this.Username = name;
        this.Password = password;
        this.Coins = 20;
        this.Elo = 100;
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

    public int getElo() {
        return Elo;
    }

    public String getBio() {
        return Bio;
    }

    public String getImage() {
        return Image;
    }
}
