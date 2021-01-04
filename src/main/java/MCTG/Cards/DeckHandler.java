package MCTG.Cards;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;

import java.sql.*;
import java.util.List;

public class DeckHandler {

    public static int checkDeck(List<String> headers){
        String authToken;
        authToken = headers.get(2);

        System.out.println(authToken);

        //splitting the Token in order to get Name
        String[] arrOfStr = authToken.split(" ",3);
        String[] arrOfStr2 = arrOfStr[2].split("-",2);
        String nameTemp = arrOfStr2[0];
        System.out.println("User: "+nameTemp);

        //check if User has a Deck
        int cardsAmount= 0;

        ResultSet myRs = null;
        try(Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
            PreparedStatement statement = connection.prepareStatement("SELECT Count(cardId) AS amount FROM decks WHERE username = ?;");
        ){
            statement.setString(1, nameTemp);
            myRs = statement.executeQuery();

            while(myRs.next()) {
                int counter = myRs.getInt(1);
                cardsAmount = counter;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        System.out.println("Amount of Cards: " + cardsAmount);
        if(cardsAmount == 0)
            return 1;

        return 0;
    }

    public static int configureCardsInDeck(String payload,List<String> headers) throws JsonProcessingException {
        String authToken = headers.get(3);

        System.out.println(authToken);

        //splitting the Token in order to get Name
        String[] arrOfStr = authToken.split(" ",3);
        String[] arrOfStr2 = arrOfStr[2].split("-",2);
        String nameTemp = arrOfStr2[0];
        System.out.println("User: "+nameTemp);

        //splitting the jsonArray into IDs
        JSONArray jsonArray = new JSONArray(payload);

        //check if input has 4 cards
        int countCards = jsonArray.length();
        if(countCards == 4){

            //check if these 4 cards belongs to the User
            int checkCounter = 0;

            for(int j = 0; j<=3;j++){
                String cardID = jsonArray.get(j).toString();

                //find a packageId
                ResultSet myRs = null;
                try(Connection connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
                    PreparedStatement statement1 = connection.prepareStatement("SELECT cardid FROM stackcards WHERE username =?;");
                ){
                    statement1.setString(1,nameTemp);
                    myRs = statement1.executeQuery();

                    while(myRs.next()) {
                        if(myRs.getString(1).equals(cardID))
                            checkCounter++;
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            if(checkCounter == 4){

                for(int j = 0; j<=3;j++) {
                    String cardID = jsonArray.get(j).toString();

                    //saving Data from stackCards into deckDatabase
                    ResultSet myRs01 = null;
                    try (Connection connection = DriverManager.getConnection(
                            "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
                         PreparedStatement statement01 = connection.prepareStatement("SELECT * FROM stackcards WHERE cardid = ?;")
                    ) {
                        statement01.setString(1, cardID);
                        myRs01 = statement01.executeQuery();

                        while (myRs01.next()) {

                            String carId = myRs01.getString(2);
                            String cardName = myRs01.getString(3);
                            int damage = myRs01.getInt(4);
                            String elementType = myRs01.getString(5);
                            String cardType = myRs01.getString(6);

                            Card card1 = new Card(carId, cardName, damage, elementType, cardType);

                            //insert into stackCardsDatabase
                            PreparedStatement statement4 = connection.prepareStatement("INSERT INTO decks VALUES(?,?,?,?,?,?);");
                            statement4.setString(1, nameTemp);
                            statement4.setString(2, card1.getCardId());
                            statement4.setString(3, card1.getCardName());
                            statement4.setInt(4, card1.getDamage());
                            statement4.setString(5, card1.getElementType());
                            statement4.setString(6, card1.getCardType());
                            statement4.execute();

                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                System.out.println("The chosen Cards are now in Users Deck");
                return 0;
            }else{
                System.out.println("Wrong User!!!");
                return 1;
            }

        }else{
            System.out.println("Input has not enough cards");
            return 2;
        }
    }

    public static String showDeck(List<String> headers){
        String allCards = null;
        int counter = 0;

        String authToken = headers.get(2);
        //splitting the Token in order to get Name
        String[] arrOfStr = authToken.split(" ",3);
        String[] arrOfStr2 = arrOfStr[2].split("-",2);
        String nameTemp = arrOfStr2[0];

        //save cards from a user into string
        ResultSet myRs = null;
        try(Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
            PreparedStatement statement02 = connection.prepareStatement("SELECT * FROM decks WHERE username = ?;")
        ){
            statement02.setString(1, nameTemp);
            myRs = statement02.executeQuery();
            while(myRs.next()) {
                String carId = myRs.getString(2);
                String cardName = myRs.getString(3);
                int damage = myRs.getInt(4);
                String elementType = myRs.getString(5);
                String cardType = myRs.getString(6);


                ObjectMapper mapper = new ObjectMapper();
                Card oneCard = new Card(carId,cardName,damage,elementType,cardType);
                // pretty print
                String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(oneCard);
                if(counter == 0) {
                    allCards = nameTemp + " Deck:\n" + json;
                    counter++;
                }
                else {
                    allCards = allCards + json;
                }

            }

        } catch (SQLException | JsonProcessingException ex) {
            ex.printStackTrace();
        }

        return allCards;
    }

    public static String showDeckInPlain(List<String> headers){
        String allCards = null;
        int counter = 0;

        String authToken = headers.get(2);
        //splitting the Token in order to get Name
        String[] arrOfStr = authToken.split(" ",3);
        String[] arrOfStr2 = arrOfStr[2].split("-",2);
        String nameTemp = arrOfStr2[0];

        //save cards from a user into string
        ResultSet myRs = null;
        try(Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
            PreparedStatement statement02 = connection.prepareStatement("SELECT * FROM decks WHERE username = ?;")
        ){
            statement02.setString(1, nameTemp);
            myRs = statement02.executeQuery();
            while(myRs.next()) {
                String carId = myRs.getString(2);
                String cardName = myRs.getString(3);
                int damage = myRs.getInt(4);
                String elementType = myRs.getString(5);
                String cardType = myRs.getString(6);

                String together = "CardId: " + carId + "\nCardName: " + cardName + "\nDamage: " + damage + "\nElementType: " + elementType + "\nCardType: " + cardType;

                if(counter == 0) {
                    allCards = "\n" + nameTemp + " Deck:\n" + together;
                    counter++;
                }
                else {
                    allCards = allCards + "\n\n" + together;
                }

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return allCards;
    }


}
