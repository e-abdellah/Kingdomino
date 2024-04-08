package testen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domein.Dominotegel;
import gui.SpelController;
import org.junit.jupiter.api.Test;



public class SpelControllerTest {

    /*
    @Test
    public void testIsTegelCorrectGeplaatst() {
        SpelController controller = new SpelController();
        Dominotegel tegel = new Dominotegel(1, 2, "voorkant.jpg", "achterkant.jpg");
        assertTrue(controller.isTegelCorrectGeplaatst(tegel, 0, 0));
    }
    */

    @Test
    public void testBepaalWinnaar() {
        SpelController controller = new SpelController();
        assertEquals("Speler X", controller.bepaalWinnaar());
    }
}
