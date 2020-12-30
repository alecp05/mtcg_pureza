package MCTG.Users;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.sql.*;

public class UserHandling {

    public static int registration(String payload) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(payload);
        String usernameTemp = jsonNode.get("Username").asText();
        String passwordTemp = jsonNode.get("Password").asText();

        User user1 = new User(usernameTemp,passwordTemp);

        ResultSet myRs = null;

        String compareUser;
        int count = 0;

        try(Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
            PreparedStatement statement2 = connection.prepareStatement("SELECT * FROM users")
        ){
            myRs = statement2.executeQuery();

            while(myRs.next()) {

                String name = myRs.getString(1);
                if(name.equals(usernameTemp)) {
                    count++;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        if(count==0){
            try(Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
                PreparedStatement statement = connection.prepareStatement("INSERT INTO users VALUES(?,?,?,?);")
            ){
                //statement.setInt(1,13);
                statement.setString(1, user1.getUsername());
                statement.setString(2, user1.getPassword());
                statement.setInt(3, user1.getCoins());
                statement.setString(4, user1.getElo());
                statement.execute();
                return 0;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }else {
            return 1;
        }
        return 1;
    };

    public static int login(String payload) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(payload);
        String usernameTemp = jsonNode.get("Username").asText();
        String passwordTemp = jsonNode.get("Password").asText();

        int counter = 0;
        ResultSet myRs = null;
        try(Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/mtcgDatabase", "postgres", "alecUser");
            PreparedStatement statement2 = connection.prepareStatement("SELECT * FROM users")
        ){
            myRs = statement2.executeQuery();

            while(myRs.next()) {
                String name = myRs.getString(1);
                String pwd = myRs.getString(2);
                System.out.println(name+pwd);
                if(name.equals(usernameTemp) && pwd.equals(passwordTemp)) {
                    counter++;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        if(counter == 1){
            return 0;
        }else{
            return 1;
        }

    };
}
