import MCTG.Battle.BattleHandler;
import MCTG.Cards.Card;
import MCTG.Cards.DeckHandler;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BattleHandlingTest {

    //CHECK EFFECTIVENESS
    @Test
    public void testCheckEffectiveness_shouldBeNumber2() throws IOException {

        Card cardOne = new Card("1","TestDragon",30,"fire","monster");
        Card cardTwo = new Card("2","TestWaterspell",30,"water","spell");

        BattleHandler battleTest = new BattleHandler();

        //fire vs water -> return 2
        assertTrue(battleTest.checkEffectiveness(cardOne,cardTwo) == 2, "fire vs water -> return 2");
    }

    @Test
    public void testCheckEffectiveness2_shouldBeNumber6() throws IOException {

        Card cardOne = new Card("1","TestKraken",30,"water","monster");
        Card cardTwo = new Card("2","TestGoblin",30,"regular","monster");

        BattleHandler battleTest = new BattleHandler();

        //water vs normal/regular -> return 6
        assertTrue(battleTest.checkEffectiveness(cardOne,cardTwo) == 6, "water vs normal/regular -> return 6");
    }

    //CHECK SPECIALITIES
    @Test
    public void testCheckSpecialities_shouldBeNumber4() throws IOException {

        Card cardOne = new Card("1","TestWizzard",30,"regular","monster");
        Card cardTwo = new Card("2","TestOrk",30,"regular","monster");

        BattleHandler battleTest = new BattleHandler();

        //wizzard vs ork -> return 4
        assertTrue(battleTest.checkSpecialities(cardOne,cardTwo) == 4, "wizzard vs ork -> return 4");
    }

    @Test
    public void testCheckSpecialities_shouldBeNumber9() throws IOException {

        Card cardOne = new Card("1","TestDragon",30,"regular","monster");
        Card cardTwo = new Card("2","TestFireElve",30,"fire","monster");

        BattleHandler battleTest = new BattleHandler();

        //dragon vs fireElve -> return 9
        assertTrue(battleTest.checkSpecialities(cardOne,cardTwo) == 9, "dragon vs fireElve -> return 9");
    }

    @Test
    public void testCheckSpecialities_shouldBeNumber5() throws IOException {

        Card cardOne = new Card("1","TestKnight",30,"regular","monster");
        Card cardTwo = new Card("2","TestWaterSpell",30,"water","spell");

        BattleHandler battleTest = new BattleHandler();

        //knight vs waterSpell -> return 5
        assertTrue(battleTest.checkSpecialities(cardOne,cardTwo) == 5, "knight vs waterSpell -> return 5");
    }
}


