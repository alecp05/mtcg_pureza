package MCTG.Cards;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.*;
import java.util.List;

public class CardHandler {

    public static String showCards(List<String> headers){
        String allCards = null;
        int counter = 0;

        String authToken = headers.get(2);
        System.out.println(authToken);

        //splitting the Token in order to get Name
        String[] arrOfStr = authToken.split(" ",3);
        String[] arrOfStr2 = arrOfStr[2].split("-",2);
        String nameTemp = arrOfStr2[0];
        System.out.println("User: "+nameTemp);

        //save cards from a user into string
        ResultSet myRs = null;
        try(Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM stackCards WHERE username = ?;")
        ){
            statement.setString(1, nameTemp);
            myRs = statement.executeQuery();
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
                    allCards = nameTemp + " Cards:\n" + json;
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
}
