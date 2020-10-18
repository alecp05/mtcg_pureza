package MCTG;

public class Main {
    public static void main(String[] args){
        System.out.println("Hello World!");
        Player player1 = new Player("Alec");
        System.out.println(player1.getCoins());
        Card firemonster = new Card();
        System.out.println(firemonster.getCardType());
    }
}
