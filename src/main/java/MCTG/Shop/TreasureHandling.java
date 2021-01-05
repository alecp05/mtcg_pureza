package MCTG.Shop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.*;
import java.util.List;

public class TreasureHandling {

    //User gets BonusCoins / TreasureBox for the amount of wins e.g. 20 Wins, 50 Wins...
    public static int getTreasure(String payload, List<String> headers) throws JsonProcessingException {

        String authToken = headers.get(3);
        //splitting the Token in order to get Name
        String[] arrOfStr = authToken.split(" ", 3);
        String[] arrOfStr2 = arrOfStr[2].split("-", 2);
        String nameTemp = arrOfStr2[0];

        //get wins from message
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(payload);
        int winsCount = jsonNode.get("Wins").asInt();

        System.out.println(winsCount);

        //get UserCoins
        int usersCoins = 0;

        //check if User (from Token) wins match the wins given in message
        //set integer winsCount from Database
        int winsCountFromData = 0;
        ResultSet myRs = null;
        try(Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
            PreparedStatement statement1 = connection.prepareStatement("SELECT wins, coins FROM users WHERE username =?;");
        ){
            statement1.setString(1,nameTemp);
            myRs = statement1.executeQuery();

            while(myRs.next()) {
                winsCountFromData = myRs.getInt(1);
                usersCoins = myRs.getInt(2);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        if(winsCountFromData!=winsCount){
            return 2;
        }

        //check if User already got his Loot/ his BonusCoins for the Wins
        int countUser = 0;
        ResultSet myRs2 = null;
        try(Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
            PreparedStatement statement1 = connection.prepareStatement("SELECT * FROM treasure WHERE username =? AND wins = ?;");
        ){
            statement1.setString(1,nameTemp);
            statement1.setInt(2,winsCount);
            myRs2 = statement1.executeQuery();

            while(myRs2.next()) {
                String tempUserName = myRs2.getString(2);
                if(tempUserName.equals(nameTemp))
                    countUser++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        //Give the User Coins if he didn't took it already
        if(countUser==0){
            int bonusCoins = 0;

            if(winsCount == 20)
                bonusCoins = 20;
            else if(winsCount == 50)
                bonusCoins = 50;
            else if(winsCount == 100)
                bonusCoins = 100;
            else if(winsCount == 150)
                bonusCoins = 150;
            else if(winsCount == 1)
                bonusCoins = 2;

            try(Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
                PreparedStatement statement = connection.prepareStatement("UPDATE users SET coins = ? WHERE userName = ?;");
            ){
                statement.setInt(1, usersCoins + bonusCoins);
                statement.setString(2, nameTemp);
                statement.execute();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            try(Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
                PreparedStatement statement2 = connection.prepareStatement("INSERT INTO treasure VALUES (?,?,?,?);");
            ){
                statement2.setString(1, nameTemp + bonusCoins);
                statement2.setString(2, nameTemp);
                statement2.setInt(3, winsCount);
                statement2.setInt(4,bonusCoins);
                statement2.execute();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return 0;
        }

        return 1;

    }
}
