package domein;

import java.util.List;

import exceptions.GebruikersnaamInGebruikException;
import persistentie.SpelerMapper;

public class SpelerRepository {

	private final SpelerMapper mapper;

	public SpelerRepository() {
		mapper = new SpelerMapper();
	}

	public void voegToe(Speler speler) {
		// Controle of de gebruikersnaam al bestaat
		if (bestaatSpeler(speler.getGebruikersnaam())) {
			throw new GebruikersnaamInGebruikException();
		}

		// Controleer de gebruikersnaam en geboortejaar van de speler
		try {
			speler.setGebruikersnaam(speler.getGebruikersnaam());
			speler.setGeboortejaar(speler.getGeboortejaar());
		} catch (IllegalArgumentException e) {
			// Als er een ongeldige gebruikersnaam of geboortejaar is, gooi dan de
			// uitzondering
			System.out.println("Gebruikersnaam of geboortejaar is fout");
			throw e;
		}

		// Als er geen problemen zijn, voeg dan de speler toe
		mapper.voegToe(speler);
	}

	private boolean bestaatSpeler(String gebruikersnaam) {
		return mapper.geefSpeler(gebruikersnaam) != null;
	}

	public List<Speler> geefAlleSpelers() {
		return mapper.geefAlleSpelers();
	}

}
