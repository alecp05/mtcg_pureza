package MCTG.Shop;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;

import java.io.IOException;
import java.sql.*;

public class PackageHandler {
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
}
