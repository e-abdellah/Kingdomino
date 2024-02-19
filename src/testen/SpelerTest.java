package testen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import domein.Speler;

class SpelerTest 
{
	private Speler speler;

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

}
