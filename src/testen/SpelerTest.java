package testen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import domein.Speler;

class SpelerTest 
{
	private Speler speler;
    private static final int MAX_GEBOORTEJAAR = 1924;
    private static final int MIN_GEBOORTEJAAR = 2018;
	
	@Test
	void maakSpeler_alleGegevensCorrect_maaktObject() 
	{
		speler = new Speler("avatar", 2003, 4,25);
		Assertions.assertEquals("avatar", speler.getGebruikersnaam());
		Assertions.assertEquals(2003, speler.getGeboortejaar());
		Assertions.assertEquals(4, speler.getAantalGewonnen());
		Assertions.assertEquals(25, speler.getAantalGespeeld());
	}
	
	@Test
	void maakSpeler_correcteGebruikersnaamGeboortejaar_maaktObject() 
	{
		speler = new Speler("avatar", 2003);
		Assertions.assertEquals("avatar", speler.getGebruikersnaam());
		Assertions.assertEquals(2003, speler.getGeboortejaar());
		Assertions.assertEquals(0, speler.getAantalGewonnen());
		Assertions.assertEquals(0, speler.getAantalGespeeld());
	}
	
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { "a", "aB", "abc", "abCd", "ABCDE", " ", "   ", "\t", "ABC13", "a1234",
    		"12345", "1", "%^%$^&*", "%^% $^&*"})
    void maakGebruiker_fouteGebruikersnaam_WerptException(String gebruikersnaam) {
        assertThrows(IllegalArgumentException.class, () -> new Speler(gebruikersnaam, 0));
        // wordt gecontrolleerd of de gebruikersnaam null or leeg is + als speciale
        // tekens bevat
    }

    @ParameterizedTest
    @ValueSource(strings = { "abcdef", "ABCDGF", "HBCJBHCJSBbckdkcsdv", "a bcs dcefw 1213", "123456789", "123456  8"})
    void maakGebruiker_juisteGebruikersnaam_MaakGebruiker(String gebruikersnaam) throws Exception {
        Speler s = new Speler(gebruikersnaam, 0);
        assertEquals(gebruikersnaam, s.getGebruikersnaam());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(ints = { 0, -9, 9, -19, 99, -99, -145, 999, 1756, MIN_GEBOORTEJAAR - 1, 2200, MAX_GEBOORTEJAAR + 1,
            20043 })
    void maakGeboortejaar_fouteGeboortejaar_WerptException(int geboortejaar) {
        assertThrows(IllegalArgumentException.class, () -> new Speler("", geboortejaar));
        // wordt gecontrollerd of de geboortejaar is ouder dan 1924 of jonger dan 2018
    }

    @ParameterizedTest
    @ValueSource(ints = { MIN_GEBOORTEJAAR, 1924, 2000, 2017, MAX_GEBOORTEJAAR })
    void maakGeboortejaar_juisteGeboortejaar_MaakSpeler(int geboortejaar) throws Exception {
        Speler s = new Speler("", geboortejaar);
        assertEquals(geboortejaar, s.getGeboortejaar());
    }

}
1