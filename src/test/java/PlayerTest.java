import MCTG.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    Player player1;

    @BeforeEach
    public void setup(){
       player1 = new Player("FirstPlayer");
    }

    @Test
    public void testGetCoins_shouldBe100(){

        assertTrue(player1.getCoins() == 100, "The new Player should have 100 Coins");
    }

    @Test
    public void testGetElo_shouldBeBronze(){

        assertTrue(player1.getElo() == "Bronze", "The new Player should has to be Bronze");
    }

}
