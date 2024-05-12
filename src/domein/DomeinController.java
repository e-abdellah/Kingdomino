package domein;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import dto.DominotegelDTO;
import dto.SpelerDTO;
import exceptions.GebruikersnaamInGebruikException;

/**
 * De {@code DomeinController} klasse beheert de spellogica en de staat van een spel.
 * Het biedt methoden voor het beheren van spelers, het afhandelen van speelbeurten, en het volgen van de voortgang van het spel.
 */

public class DomeinController {

	private final SpelerRepository spelerRepository;
	private final Spel spel;
	private boolean isEindeSpel;

	private List<SpelerDTO> spelers = new ArrayList<>();

	private static DomeinController instance;

	public DomeinController() {
		spelerRepository = new SpelerRepository();
		spel = new Spel();
	}

	public static DomeinController getInstance() {
		if (instance == null) {
			instance = new DomeinController();
		}
		return instance;
	}

	public void berekenEindeSpel() {
		isEindeSpel = spel.isEindeSpel();
	}

	public void skip() {
		spel.skip();
	}

	public void voegSpelersToe(List<SpelerDTO> spelerDTOS) {
		for (SpelerDTO speler : spelerDTOS) {
			spel.voegSpelersToe(new Speler(speler.gebruikersnaam(), speler.geboortejaar(), speler.aantalGewonnen(),
					speler.aantalGespeeld(), speler.koninkrijk(), speler.scores(), speler.isWinnaar()));
		}
		spelers = spelerDTOS;
	}

	public List<SpelerDTO> getSpelers() {
		return spelers;
	}

	public void registreerSpeler(String gebruikersnaam, int geboortejaar) throws GebruikersnaamInGebruikException {
		Speler nieuweSpeler = new Speler(gebruikersnaam, geboortejaar);
		spelerRepository.voegToe(nieuweSpeler);
	}

	public List<SpelerDTO> geefOverzichtSpelers() {
		// Haalt een lijst van alle spelers op uit de spelerRepository.
		List<Speler> spelers = spelerRepository.geefAlleSpelers();

		// Initialiseert een nieuwe ArrayList om de SpelerDTO objecten op te slaan.
		List<SpelerDTO> overzicht = new ArrayList<>();

		// Loopt door elke speler in de lijst 'spelers'.
		for (Speler s : spelers) {
			// Creëert een nieuwe SpelerDTO object voor elke speler, inclusief diverse
			// attributen van de speler,
			// en voegt deze toe aan de lijst 'overzicht'.
			overzicht.add(new SpelerDTO(s.getGebruikersnaam(), s.getGeboortejaar(), s.getAantalGewonnen(),
					s.getAantalGespeeld(), s.getKoninkrijk(), s.getScores(), s.isWinnaar()));
		}

		// Retourneert de lijst met SpelerDTO objecten.
		return overzicht;
	}

	public boolean isEindeSpel() {
		return isEindeSpel;
	}

	public List<DominotegelDTO> dominotegels(int aantalSpelers) {
		spel.schudDominotegels(aantalSpelers);

		List<Dominotegel> list = spel.getDominotegels();
		List<DominotegelDTO> dtos = new ArrayList<>();

		for (Dominotegel d : list) {
			dtos.add(new DominotegelDTO(d.getVakje1(), d.getVakje2(), d.getGetal(), d.getKroon(), d.toString(),
					d.getVoorkantFotoPad(), d.getAchterkantFotoPad()));
		}
		return dtos;
	}

	public List<DominotegelDTO> geefDominotegels(int aantalSpelers) {
		// Roep een methode aan om de dominotegels te schudden, gebaseerd op het aantal
		// spelers.
		spel.schudDominotegels(aantalSpelers);

		// Initialiseert een nieuwe ArrayList om de DominotegelDTO objecten op te slaan.
		List<DominotegelDTO> dominotegelDTOS = new ArrayList<>();

		// Haalt een lijst van Dominotegels op van het spel.
		List<Dominotegel> tegels = spel.getDominotegels();

		// Loopt door elke dominotegel in de lijst 'tegels'.
		for (Dominotegel d : tegels) {
			// Creëert een nieuwe DominotegelDTO object voor elke dominotegel, inclusief
			// diverse attributen van de tegel,
			// en voegt deze toe aan de lijst 'dominotegelDTOS'.
			dominotegelDTOS.add(new DominotegelDTO(d.getVakje1(), d.getVakje2(), d.getGetal(), d.getKroon(),
					d.toString(), d.getVoorkantFotoPad(), d.getAchterkantFotoPad()));
		}

		// Retourneert de lijst met DominotegelDTO objecten.
		return dominotegelDTOS;
	}

	public List<DominotegelDTO> geefTegels(int aantal) {
		// Creëert een nieuwe lijst voor het opslaan van DominotegelDTO objecten.
		List<DominotegelDTO> dominotegelDTOS = new ArrayList<>();

		// Roept de methode geefTegels van het object 'spel' aan om een lijst van
		// Dominotegels te krijgen, gebaseerd op het opgegeven aantal.
		for (Dominotegel d : spel.geefTegels(aantal)) {
			// Voegt een nieuwe DominotegelDTO toe aan de lijst. Deze DTO bevat details over
			// de dominotegel,
			// zoals de vakjes, het getal op de tegel, het aantal kronen en de string
			// representatie van de tegel.
			dominotegelDTOS.add(new DominotegelDTO(d.getVakje1(), d.getVakje2(), d.getGetal(), d.getKroon(),
					d.toString(), d.getVoorkantFotoPad(), d.getAchterkantFotoPad()));
		}

		// Retourneert de voltooide lijst met DominotegelDTO's.
		return dominotegelDTOS;
	}

	public void berekenWinnaars() {
		spel.berekenWinnaars();
	}

	public List<Speler> refreshSpeler() {

		return spel.getSpelers();
	}
	
	public List<SpelerDTO> refreshSpelerDTO() {
        List<SpelerDTO> dtos = new ArrayList<>();
        for (Speler s : spel.getSpelers()) {
            dtos.add(new SpelerDTO(s.getGebruikersnaam(), s.getGeboortejaar(), s.getAantalGewonnen(),
                    s.getAantalGespeeld(), s.getKoninkrijk(), s.getScores(), s.isWinnaar()));
        }

        return dtos;
    }

	public Speler geefScore() {
		Map<Speler, List<Integer>> map = spel.geefScores();

		Speler speler = null;

		for (Map.Entry<Speler, List<Integer>> entry : map.entrySet()) {
			speler = entry.getKey();
		}

		return speler;
	}

	public List<String> geefKleurenInTaal(Locale locale) {
		// Laadt een ResourceBundle voor kleuren, gebaseerd op de opgegeven locale.
		ResourceBundle colors = ResourceBundle.getBundle("utils.resource_bundle", locale);

		// Initialiseert een nieuwe lijst om de vertaalde kleurnamen op te slaan.
		List<String> kleurTaal = new ArrayList<>();

		// Doorloopt elke kleur gedefinieerd in de enumeratie Kleuren.
		for (Kleuren kleur : Kleuren.values()) {
			// Voegt de vertaalde naam van de kleur toe aan de lijst, zoals gedefinieerd in
			// de ResourceBundle.
			kleurTaal.add(colors.getString(kleur.name()));
		}
		// Retourneert de lijst met vertaalde kleurnamen.
		return kleurTaal;
	}

	public List<Dominotegel> plaatsTegelsInStartkolom() {
		return spel.plaatsTegelsInStartkolom();
	}

	public List<DominotegelDTO> geefKolom() {
		// Initialiseert een nieuwe ArrayList om de DominotegelDTO objecten op te slaan.
		List<DominotegelDTO> kolom = new ArrayList<>();

		// Roept een methode op het 'spel'-object aan om een lijst van dominotegels te
		// krijgen die aan het begin of eind van een kolom liggen.
		for (Dominotegel d : spel.geefBeginOfEindKolom()) {
			// Voor elke dominotegel, creëert een nieuwe DominotegelDTO die verschillende
			// eigenschappen van de dominotegel bevat,
			// zoals de vakjes, het getal, het aantal kronen en de string representatie van
			// de tegel.
			kolom.add(new DominotegelDTO(d.getVakje1(), d.getVakje2(), d.getGetal(), d.getKroon(), d.toString(),
					d.getVoorkantFotoPad(), d.getAchterkantFotoPad()));
		}
		// Retourneert de voltooide lijst met DominotegelDTO's.
		return kolom;
	}

	public void plaatsTegel(DominotegelDTO tegelDTO, int y, int x, int hoek, SpelerDTO spelerDTO) {
		// Doorloopt de lijst van dominotegels die wordt opgehaald gebaseerd op het
		// aantal spelers in het spel.
		for (Dominotegel tegel : spel.getDominotegels()) {
			// Controleert of de vakjes van de dominotegel overeenkomen met die in de
			// gegeven DominotegelDTO.
			if (tegel.getVakje1() == tegelDTO.vakje1() && tegel.getVakje2() == tegelDTO.vakje2()) {
				// Roept de methode plaatsTegel aan op het 'spel'-object met de gevonden
				// dominotegel, coördinaten en spelerDTO.
				spel.plaatsTegel(tegel, y, x, hoek, spelerDTO);
			}
		}
	}

	public boolean kanPlaatsen(DominotegelDTO tegelDTO, int y, int x, int hoek, SpelerDTO spelerDTO) {
		// Doorloopt alle dominotegels die relevant zijn voor het huidige aantal spelers
		// in het spel.
		for (Dominotegel tegel : spel.getDominotegels()) {
			// Controleert of de vakjes van de dominotegel overeenkomen met die in de
			// gegeven DominotegelDTO.
			// Als dat niet zo is, zou hier een gepaste 'equals'-methode moeten worden
			// gebruikt.
			if (tegel.getVakje1() == tegelDTO.vakje1() && tegel.getVakje2() == tegelDTO.vakje2()) {
				// Roept de plaatsingscontrole van het spel aan met de gevonden dominotegel,
				// coördinaten, en spelerDTO.
				return spel.kanPlaatsen(tegel, y, x, hoek, spelerDTO);
			}
		}
		// Retourneert false als er geen overeenkomende dominotegel gevonden is.
		return false;
	}

}