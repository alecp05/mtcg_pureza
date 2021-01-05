package MCTG.Shop;

import MCTG.Cards.Card;
import MCTG.Cards.TradeCard;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.*;
import java.util.List;

public class TradingHandler {

        public static String showTradingDeals(){
            String allTradingCards = null;
            int counter = 0;

            //save cards from a user into string
            ResultSet myRs = null;
            try(Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
                PreparedStatement statement1 = connection.prepareStatement("SELECT * FROM trading;")
            ){
                myRs = statement1.executeQuery();

                while(myRs.next()) {
                    String tradeId = myRs.getString(1);
                    String userName = myRs.getString(2);
                    String carId = myRs.getString(3);
                    String cardName = myRs.getString(4);
                    int damage = myRs.getInt(5);
                    String elementType = myRs.getString(6);
                    String cardType = myRs.getString(7);
                    String requiredType = myRs.getString(8);
                    int requiredDamage = myRs.getInt(9);


                    ObjectMapper mapper = new ObjectMapper();
                    TradeCard tradeCard = new TradeCard(tradeId,userName,carId,cardName,damage,elementType,cardType,requiredType, requiredDamage);
                    // pretty print
                    String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tradeCard);
                    if(counter == 0) {
                        allTradingCards = json;
                        counter++;
                    }
                    else {
                        allTradingCards = allTradingCards + json;
                    }
                }

            } catch (SQLException | JsonProcessingException ex) {
                ex.printStackTrace();
            }

            if(allTradingCards == null){
                allTradingCards = "No Trading deals available...\n";
            }

            return allTradingCards;
        }

        public static int createTradingDeal(List<String> headers, String payload) throws JsonProcessingException {
            String authToken = headers.get(3);
            //splitting the Token in order to get Name
            String[] arrOfStr = authToken.split(" ",3);
            String[] arrOfStr2 = arrOfStr[2].split("-",2);
            String nameTemp = arrOfStr2[0];

            //save information of the Card the User wants to trade
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(payload);
            String tradeId = jsonNode.get("Id").asText();
            String cardId = jsonNode.get("CardToTrade").asText();
            String requiredType = jsonNode.get("Type").asText();
            int requiredDamage = jsonNode.get("MinimumDamage").asInt();


            //check if cardId belongs to the given Token User
            boolean trueOrFalse = false;
            ResultSet myRs = null;
            try(Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
                PreparedStatement statement = connection.prepareStatement("SELECT username FROM stackCards WHERE cardid = ?;");
            ){
                statement.setString(1, cardId);
                myRs = statement.executeQuery();

                while(myRs.next()) {
                    String tempName = myRs.getString(1);
                    if(tempName.equals(nameTemp)){
                        trueOrFalse = true;
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            if(trueOrFalse){

                String cardN = null;
                int dmg = 0;
                String elementT = null;
                String cardT = null;

                //get CardInfo of StackCardsDatabase
                ResultSet myRs2 = null;
                try(Connection connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
                    PreparedStatement statement2 = connection.prepareStatement("SELECT * FROM stackCards WHERE cardid = ?;");
                ){
                    statement2.setString(1, cardId);
                    myRs2 = statement2.executeQuery();

                    while(myRs2.next()) {
                        String tempName = myRs2.getString(1);
                        cardN = myRs2.getString(3);
                        dmg = myRs2.getInt(4);
                        elementT = myRs2.getString(5);
                        cardT = myRs2.getString(6);

                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }



                //insert in TradingDatabase
                try(Connection connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
                    PreparedStatement statement = connection.prepareStatement("INSERT INTO trading VALUES(?,?,?,?,?,?,?,?,?);")
                ){
                    statement.setString(1, tradeId);
                    statement.setString(2, nameTemp);
                    statement.setString(3, cardId);
                    statement.setString(4, cardN);
                    statement.setInt(5, dmg);
                    statement.setString(6, elementT);
                    statement.setString(7, cardT);
                    statement.setString(8, requiredType);
                    statement.setInt(9, requiredDamage);
                    statement.execute();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                return 0;
            }else
                return 1;
        }


        public static int deleteDeal(String path, List<String> headers){

            String authToken = headers.get(2);
            //splitting the Token in order to get Name
            String[] arrOfStr = authToken.split(" ",3);
            String[] arrOfStr2 = arrOfStr[2].split("-",2);
            String nameTemp = arrOfStr2[0];


            String[] pathSplit = path.split("/",3);
            String tradeId = pathSplit[2];


            //check if tradeId from Path belongs to the given Token User
            boolean checkIfTrue = false;
            ResultSet myRs = null;
            try(Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM trading WHERE username = ?;");
            ){
                statement.setString(1, nameTemp);
                myRs = statement.executeQuery();

                while(myRs.next()) {
                    String tempCardId = myRs.getString(1);
                    if(tempCardId.equals(tradeId)){
                        checkIfTrue = true;
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            if(checkIfTrue) {
                //Delete from trading Database
                try(Connection connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
                    PreparedStatement statement = connection.prepareStatement("DELETE FROM trading WHERE tradeid = ?;");
                ){
                    statement.setString(1, tradeId);
                    statement.execute();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                return 0;
            }else
                return 1;
        }

        public static int letsTrade(String path, List<String> headers, String payload){

            String authToken = headers.get(3);
            //splitting the Token in order to get Name
            String[] arrOfStr = authToken.split(" ",3);
            String[] arrOfStr2 = arrOfStr[2].split("-",2);
            String nameTemp = arrOfStr2[0];

            String[] pathSplit = path.split("/",3);
            String tradeId = pathSplit[2];

            //check if tradeId from Path belongs to the given Token User
            boolean checkName = true;
            ResultSet myRs = null;
            try(Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM trading WHERE tradeid = ?;");
            ){
                statement.setString(1, tradeId);
                myRs = statement.executeQuery();

                while(myRs.next()) {
                    String tempName = myRs.getString(2);
                    if(tempName.equals(nameTemp)){
                        checkName = false;
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            if(!checkName){
                System.out.println("not able to trade");
                return 1;
            }else
            {
                String cardId = payload.replace("\"", "");

                String tradingPartnerName = null;
                String tradingPartnerCardId = null;



                try(Connection connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
                    PreparedStatement statement = connection.prepareStatement("SELECT * FROM trading WHERE tradeid = ?;");
                ){
                    statement.setString(1, tradeId);
                    myRs = statement.executeQuery();

                    while(myRs.next()) {
                        tradingPartnerName = myRs.getString(2);
                        tradingPartnerCardId = myRs.getString(3);

                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                System.out.println(tradeId + " " + tradingPartnerCardId +  " " + cardId + "lol");


                //updating both usernames in stackCards
                try(Connection connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
                    PreparedStatement statement2 = connection.prepareStatement("UPDATE stackcards SET username = ? WHERE cardid = ?;");
                ){
                    statement2.setString(1, tradingPartnerName);
                    statement2.setString(2, cardId);
                    statement2.execute();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                try(Connection connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
                    PreparedStatement statement2 = connection.prepareStatement("UPDATE stackcards SET username = ? WHERE cardid = ?;");
                ){
                    statement2.setString(1, nameTemp);
                    statement2.setString(2, tradingPartnerCardId);
                    statement2.execute();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


                //Delete from trading Database
                try(Connection connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
                    PreparedStatement statement = connection.prepareStatement("DELETE FROM trading WHERE tradeid = ?;");
                ){
                    statement.setString(1, tradeId);
                    statement.execute();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                return 0;
            }
        }

}
