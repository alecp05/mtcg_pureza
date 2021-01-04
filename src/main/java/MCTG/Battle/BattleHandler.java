package MCTG.Battle;

import MCTG.Cards.Card;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BattleHandler {

    public static String getPlayersName(List<String> headers){
        String authToken = headers.get(2);
        //splitting the Token in order to get Name
        String[] arrOfStr = authToken.split(" ",3);
        String[] arrOfStr2 = arrOfStr[2].split("-",2);
        String playersName = arrOfStr2[0];

        return playersName;
    }

    public static ArrayList<Card> setDeck(String playerName){
        ArrayList<Card> deckOfPlayer = new ArrayList<Card>();

        ResultSet myRs = null;
        try(Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM decks WHERE username = ?;");
        ){
            statement.setString(1,playerName);
            myRs = statement.executeQuery();

            while(myRs.next()) {
                String carId = myRs.getString(2);
                String cardName = myRs.getString(3);
                int damage = myRs.getInt(4);
                String elementType = myRs.getString(5);
                String cardType = myRs.getString(6);

                Card tempCard = new Card(carId,cardName,damage,elementType,cardType);

                deckOfPlayer.add(tempCard);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return deckOfPlayer;
    }

    public static int checkWinner(int first, int second){
        if(second == 0)
            return 1;
        else if(first == 0)
            return 2;
        else return 3;
    }

    public static boolean checkCardType(Card cardOne, Card cardTwo){
        if(cardOne.getCardType().equals("monster") && cardTwo.getCardType().equals("monster"))
            return false;
        return true;
    }

    public static int checkEffectiveness(Card cardOne, Card cardTwo){
        if(cardOne.getElementType().equals("water") && cardTwo.getElementType().equals("fire"))
            return 1;
        else if(cardOne.getElementType().equals("fire") && cardTwo.getElementType().equals("water"))
            return 2;
        else if(cardOne.getElementType().equals("fire") && cardTwo.getElementType().equals("regular"))
            return 3;
        else if(cardOne.getElementType().equals("regular") && cardTwo.getElementType().equals("fire"))
            return 4;
        else if(cardOne.getElementType().equals("regular") && cardTwo.getElementType().equals("water"))
            return 5;
        else if(cardOne.getElementType().equals("water") && cardTwo.getElementType().equals("regular"))
            return 6;
        return 7;
    }

    public static int checkSpecialities(Card cardOne, Card cardTwo){
        if(cardOne.getCardName().contains("Goblin") && cardTwo.getCardName().contains("Dragon"))
            return 1;
        else if(cardOne.getCardName().contains("Dragon") && cardTwo.getCardName().contains("Goblin"))
            return 2;
        else if(cardOne.getCardName().contains("Ork") && cardTwo.getCardName().contains("Wizzard"))
            return 3;
        else if(cardOne.getCardName().contains("Wizzard") && cardTwo.getCardName().contains("Ork"))
            return 4;
        else if(cardOne.getCardName().contains("Knight") && cardTwo.getCardName().contains("WaterSpell"))
            return 5;
        else if(cardOne.getCardName().contains("WaterSpell") && cardTwo.getCardName().contains("Knight"))
            return 6;
        else if(cardOne.getCardName().contains("Spell") && cardTwo.getCardName().contains("Kraken"))
            return 7;
        else if(cardOne.getCardName().contains("Kraken") && cardTwo.getCardName().contains("Spell"))
            return 8;
        else if(cardOne.getCardName().contains("Dragon") && cardTwo.getCardName().contains("FireElve"))
            return 9;
        else if(cardOne.getCardName().contains("FireElve") && cardTwo.getCardName().contains("Dragon"))
            return 10;
        return 11;
    }

    public static String letsBattle(String PlayerOne, String PlayerTwo){

        int countRounds = 0;

        String battleLog = "\n\nLET THE BATTLE BEGIN:\n" + PlayerOne + " vs "+ PlayerTwo + "\n";

        //set deck of both Players
        ArrayList<Card> deckPlayerOne = setDeck(PlayerOne);
        ArrayList<Card> deckPlayerTwo = setDeck(PlayerTwo);

        int limitPlays = 0;
        int winner = 0;

        while(limitPlays != 100){

            //check winner and return int
            winner = checkWinner(deckPlayerOne.size(), deckPlayerTwo.size());

            //show how many card the players have
            battleLog = battleLog + "\n----------------------------------------\n\n";
            battleLog = battleLog + PlayerOne + " has " +deckPlayerOne.size() + " Cards left\n" +PlayerTwo + "has  " +deckPlayerTwo.size() + " Cards left\n\n";

            //break if a player has no more cards left
            if (deckPlayerOne.size()==0 || deckPlayerTwo.size() ==0)
                break;

            //set random numbers for random cardPick each round
            Random rand1 = new Random();
            Random rand2 = new Random();
            int randomNumbOne = rand1.nextInt(deckPlayerOne.size());
            int randomNumbTwo = rand2.nextInt(deckPlayerTwo.size());

            battleLog = battleLog + PlayerOne + ": " + deckPlayerOne.get(randomNumbOne).getCardName() + " Damage: " + deckPlayerOne.get(randomNumbOne).getDamage() + " \n";
            battleLog = battleLog + PlayerTwo + ": " + deckPlayerTwo.get(randomNumbTwo).getCardName() + " Damage: " + deckPlayerTwo.get(randomNumbTwo).getDamage() + " \n";

            //save dmg temporarily
            int dmgOne = deckPlayerOne.get(randomNumbOne).getDamage();
            int dmgTwo = deckPlayerTwo.get(randomNumbTwo).getDamage();

            //check cardType -> pure monsterFight? spellCards included?
            boolean checkCardType = checkCardType(deckPlayerOne.get(randomNumbOne),deckPlayerTwo.get(randomNumbTwo));
            if(checkCardType){
                //effectiveness
                int intEffectiveness = checkEffectiveness(deckPlayerOne.get(randomNumbOne),deckPlayerTwo.get(randomNumbTwo));
                //set effectiveness
                //double the dmg
                if(intEffectiveness == 1 || intEffectiveness == 3 || intEffectiveness == 5){
                    deckPlayerOne.get(randomNumbOne).setDamage(dmgOne*2);
                    deckPlayerTwo.get(randomNumbTwo).setDamage(dmgTwo/2);
                }
                //halve the dmg
                else if(intEffectiveness == 2 || intEffectiveness == 4 || intEffectiveness == 6){
                    deckPlayerOne.get(randomNumbOne).setDamage(dmgOne/2);
                    deckPlayerTwo.get(randomNumbTwo).setDamage(dmgTwo*2);
                }
            }

            //check Specialities
            int checkSpeciality = checkSpecialities(deckPlayerOne.get(randomNumbOne),deckPlayerTwo.get(randomNumbTwo));
            //dmg is zeroed
            if(checkSpeciality == 1 || checkSpeciality == 3 || checkSpeciality == 5 || checkSpeciality == 7 || checkSpeciality == 9){
                deckPlayerOne.get(randomNumbOne).setDamage(0);
            }
            else if(checkSpeciality == 2 || checkSpeciality == 4 || checkSpeciality == 6 || checkSpeciality == 8 || checkSpeciality == 10){
                deckPlayerTwo.get(randomNumbTwo).setDamage(0);
            }


            //show dmg with or without effectiveness
            battleLog = battleLog + "--->   " + deckPlayerOne.get(randomNumbOne).getDamage() + " vs " + deckPlayerTwo.get(randomNumbTwo).getDamage() + "\n";

            //battle
            if (deckPlayerOne.get(randomNumbOne).getDamage() > deckPlayerTwo.get(randomNumbTwo).getDamage()) {
                battleLog = battleLog + deckPlayerOne.get(randomNumbOne).getCardName() + " defeats " + deckPlayerTwo.get(randomNumbTwo).getCardName() + "\n\n";

                //setting dmg back
                deckPlayerOne.get(randomNumbOne).setDamage(dmgOne);
                deckPlayerTwo.get(randomNumbTwo).setDamage(dmgTwo);

                //add card to winners deck of this round and remove from opponents deck
                deckPlayerOne.add(deckPlayerTwo.get(randomNumbTwo));
                deckPlayerTwo.remove(randomNumbTwo);
            }
            else if(deckPlayerOne.get(randomNumbOne).getDamage() < deckPlayerTwo.get(randomNumbTwo).getDamage()){
                battleLog = battleLog + deckPlayerTwo.get(randomNumbTwo).getCardName() + " defeats " + deckPlayerOne.get(randomNumbOne).getCardName() + "\n\n";

                //setting dmg back
                deckPlayerOne.get(randomNumbOne).setDamage(dmgOne);
                deckPlayerTwo.get(randomNumbTwo).setDamage(dmgTwo);

                //add card to winners deck of this round and remove from opponents deck
                deckPlayerTwo.add(deckPlayerOne.get(randomNumbOne));
                deckPlayerOne.remove(randomNumbOne);
            }else{
                battleLog = battleLog  + "That round was a DRAW...\n\n";
            }

            limitPlays++;
            countRounds++;

        }

        //determine winner by returned int
        if(winner == 1){

            battleLog = battleLog + "\nTHE WINNER IS... " + PlayerOne + "\nRounds played: " + countRounds + "\n";
            updateStats(PlayerOne,true);
            updateStats(PlayerTwo,false);
        }
        else if(winner == 2){

            battleLog = battleLog + "\nTHE WINNER IS... " + PlayerTwo + "\nRounds played: " + countRounds + "\n";
            updateStats(PlayerTwo,true);
            updateStats(PlayerOne,false);
        }
        else
            battleLog = battleLog + "\nTHIS BATTLE WAS A DRAW..." + "\nRounds played: " + countRounds + "\n";

        return battleLog;

    }

    public static void updateStats(String playerName, boolean winOrLose){

        int wins = 0;
        int losses = 0;
        int elo = 0;

        ResultSet myRs = null;
        try(Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE lower(username) LIKE ?;");
        ){
            statement.setString(1,playerName + "%");
            myRs = statement.executeQuery();

            while(myRs.next()) {
                elo = myRs.getInt(4);
                wins = myRs.getInt(7);
                losses = myRs.getInt(8);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        if(winOrLose){
            wins = wins + 1;
            elo = elo + 3;
        }else {
            losses = losses +1;
            elo = elo - 5;
        }

        try(Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET elo = ?, wins = ?, losses = ? WHERE lower(username) LIKE ?;");
        ){
            statement.setInt(1, elo);
            statement.setInt(2, wins);
            statement.setInt(3, losses);
            statement.setString(4,playerName + "%");
            statement.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
