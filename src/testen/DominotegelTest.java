package testen;

import domein.Dominotegel;
import domein.Vakje;


        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import static org.junit.jupiter.api.Assertions.*;

public class DominotegelTest {

    private Dominotegel dominotegel;

    @BeforeEach
    void setUp() {
        Vakje vakje1 = new Vakje(/* parameters voor vakje1 */);
        Vakje vakje2 = new Vakje(/* parameters voor vakje2 */);
        int getal = 20; // Kies een getal binnen de range voor testdoeleinden
        int kroon = 1;  // Gebaseerd op het getal, zouden we 1 kroon verwachten
        int zijde = 1;  // Een willekeurige waarde voor de zijde

        dominotegel = new Dominotegel(vakje1, vakje2, getal, kroon, zijde);
    }

    @Test
    void dominotegelCorrectAangemaakt() {
        assertEquals(20, dominotegel.getGetal());
        assertEquals(1, dominotegel.getKroon());
        assertEquals(1, dominotegel.getZijde());
        assertNotNull(dominotegel.getVoorkantFotoPad());
        assertNotNull(dominotegel.getAchterkantFotoPad());
        assertNotNull(dominotegel.getVakje1());
        assertNotNull(dominotegel.getVakje2());
    }

    @Test
    void fotoPadenCorrectGegenereerd() {
        // We controleren of de fotopaden correct zijn gegenereerd voor de voorkant en achterkant
        assertTrue(dominotegel.getVoorkantFotoPad().contains("tegel_20_voorkant.png"));
        assertTrue(dominotegel.getAchterkantFotoPad().contains("tegel_20_achterkant.png"));
    }

    @Test
    void kroonAantalCorrectGebaseerdOpGetal() {
        // We controleren of de kroon aantal correct wordt gezet gebaseerd op het getal van de tegel
        assertEquals(1, dominotegel.getKroon()); // Verwachten 1 kroon voor een getal van 20
        dominotegel.setGetal(45); // Zet een hoger getal en controleer de kroon weer
        assertEquals(3, dominotegel.getKroon()); // Verwachten 3 kronen voor een getal van 45
    }


}

