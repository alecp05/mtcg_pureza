package MCTG.Shop;

import MCTG.Cards.Card;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;

import java.io.IOException;
import java.sql.*;

public class PackageHandler {

    //adding package as admin
    public static int addPackages(String payload) throws IOException {
        int maxId = 0;

        //find a packageId
        ResultSet myRs = null;
        try(Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
            PreparedStatement statement2 = connection.prepareStatement("SELECT max(packageid) AS maximum FROM packages")
        ){
            myRs = statement2.executeQuery();

            while(myRs.next()) {
                Integer name = myRs.getInt(1);
                maxId = name+1;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        //all JsonObjects in an Array
        JSONArray jsonAr = new JSONArray(payload);

        for(int i=0; i<=4;i++){
            String jsonString = jsonAr.get(i).toString();

            ObjectMapper objectMapper = new ObjectMapper();


            JsonNode jsonNode = objectMapper.readTree(jsonString);
            String cardIdTemp = jsonNode.get("Id").asText();
            Integer damageTemp = jsonNode.get("Damage").asInt();
            String nameTemp = jsonNode.get("Name").asText();
            String cardTypeTemp;
            String elementTypeTemp;

            if(nameTemp.contains("Spell")){
                cardTypeTemp = "spell";
            }else{
                cardTypeTemp = "monster";
            }

            if(nameTemp.contains("Water")){
                elementTypeTemp = "water";
            }else if(nameTemp.contains("Fire")){
                elementTypeTemp = "fire";
            }else {
                elementTypeTemp = "regular";
            }

            System.out.println(cardIdTemp + damageTemp + nameTemp +maxId);

            //insert in Database
            try(Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
                PreparedStatement statement = connection.prepareStatement("INSERT INTO packages VALUES(?,?,?,?,?,?);")
            ){
                statement.setInt(1, maxId);
                statement.setString(2, cardIdTemp);
                statement.setString(3, nameTemp);
                statement.setInt(4,damageTemp);
                statement.setString(5, elementTypeTemp);
                statement.setString(6, cardTypeTemp);
                statement.execute();
            } catch (SQLException ex) {
                ex.printStackTrace();
                return 1;
            }
        }


        return 0;
    }

    //buying packages as user
    public static int buyPackages(String payload, String authToken) throws IOException {
        System.out.println(authToken);

        int coinsAmount = 0;

        //splitting the Token in order to get Name
        String[] arrOfStr = authToken.split(" ",3);
        String[] arrOfStr2 = arrOfStr[2].split("-",2);
        String nameTemp = arrOfStr2[0];
        System.out.println("User: "+nameTemp);


        //how much money does the User have? -> saved into coinsAmount
        ResultSet myRs = null;
        try(Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ?;")
        ){
            statement.setString(1, nameTemp);
            myRs = statement.executeQuery();
            while(myRs.next()) {
                int coins = myRs.getInt(3);
                coinsAmount = coins;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        System.out.println("Coins: "+coinsAmount);
        if(coinsAmount < 5){
            System.out.println("User has not enough coins...");
            return 1;
        }

        //check if there are still packages in Database
        int minId= 0;
        ResultSet myRs2 = null;
        try(Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
            PreparedStatement statement2 = connection.prepareStatement("SELECT min (packageid) AS maximum FROM packages")
        ){
            myRs2 = statement2.executeQuery();

            while(myRs2.next()) {
                Integer count = myRs2.getInt(1);
                minId = count;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        if(minId == 0){
            System.out.println("There are no packages left to buy...");
            return 1;
        }

        System.out.println(minId);
        //saving Data from PackageDatabase into stackCardsDatabase
        ResultSet myRs3 = null;
        try(Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
            PreparedStatement statement3 = connection.prepareStatement("SELECT * FROM packages WHERE packageid = ?;")
        ){
            statement3.setInt(1, minId);
            myRs3 = statement3.executeQuery();

            while(myRs3.next()) {

                String carId = myRs3.getString(2);
                String cardName = myRs3.getString(3);
                int damage = myRs3.getInt(4);
                String elementType = myRs3.getString(5);
                String cardType = myRs3.getString(6);

                Card card1 = new Card(carId,cardName,damage,elementType,cardType);

                //insert into stackCardsDatabase
                PreparedStatement statement4 = connection.prepareStatement("INSERT INTO stackCards VALUES(?,?,?,?,?,?);");
                statement4.setString(1, nameTemp);
                statement4.setString(2, card1.getCardId());
                statement4.setString(3, card1.getCardName());
                statement4.setInt(4, card1.getDamage());
                statement4.setString(5, card1.getElementType());
                statement4.setString(6, card1.getElementType());
                statement4.execute();

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        //deleting bought package from packageDatabase
        try(Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
            PreparedStatement statement5 = connection.prepareStatement("DELETE FROM packages WHERE packageId = ?;")
        ){
            statement5.setInt(1, minId);
            statement5.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        //updating coins in userDatabase
        coinsAmount = coinsAmount - 5;
        try(Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
            PreparedStatement statement6 = connection.prepareStatement("UPDATE users SET coins = ? WHERE username = ?;");
        ){
            statement6.setInt(1, coinsAmount);
            statement6.setString(2, nameTemp);
            statement6.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return 0;
    }
}
