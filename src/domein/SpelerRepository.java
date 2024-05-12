package domein;

import java.util.List;

import exceptions.GebruikersnaamInGebruikException;
import persistentie.SpelerMapper;

/**
 * De SpelerRepository klasse beheert de interacties tussen de domeinobjecten van spelers en de databron.
 * Deze klasse gebruikt een SpelerMapper om de dataopslag te abstracteren en verzorgt het toevoegen van spelers
 * en het ophalen van spelerinformatie.
 */
public class SpelerRepository {

	private final SpelerMapper mapper;

	/**
	 * Constructor voor SpelerRepository.
	 * Initialiseert een nieuwe instantie van SpelerMapper.
	 */
	public SpelerRepository() {
		mapper = new SpelerMapper();
	}

	/**
	 * Voegt een nieuwe speler toe aan de databron.
	 * Controleert eerst of de gebruikersnaam al bestaat in de databron.
	 * Als de gebruikersnaam al bestaat, wordt een GebruikersnaamInGebruikException gegooid.
	 * Controleert de geldigheid van de gebruikersnaam en het geboortejaar van de speler.
	 * 
	 * @param speler De speler die toegevoegd moet worden.
	 * @throws GebruikersnaamInGebruikException als de gebruikersnaam al in gebruik is.
	 * @throws IllegalArgumentException als de gebruikersnaam of het geboortejaar ongeldig zijn.
	 */
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

	/**
	 * Controleert of een speler met de opgegeven gebruikersnaam al bestaat in de databron.
	 * 
	 * @param gebruikersnaam De gebruikersnaam van de speler om te controleren.
	 * @return true als de speler bestaat, anders false.
	 */
	private boolean bestaatSpeler(String gebruikersnaam) {
		return mapper.geefSpeler(gebruikersnaam) != null;
	}

	/**
	* Geeft een lijst van alle spelers uit de databron.
	* 
	* @return Een lijst van Speler objecten.
	*/
	public List<Speler> geefAlleSpelers() {
		return mapper.geefAlleSpelers();
	}

}
