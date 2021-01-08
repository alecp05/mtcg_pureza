import MCTG.Cards.CardHandler;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class CardHandlingTest {

    @Test
    public void testShowCards_shouldBeInDatabase() throws IOException {

        CardHandler cardTest = new CardHandler();

        List<String> headers = new ArrayList<String>();
        headers.add("User-Agent: curl/7.55.1");
        headers.add("Accept: */*");
        headers.add("Authorization: Basic kienboec-mtcgToken");

        String noMessage = "";
        assertFalse(cardTest.showCards(headers) == noMessage, "The returned String is not empty");
    }
}

