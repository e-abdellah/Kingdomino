package domein;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import dto.SpelDTO;
import dto.SpelerDTO;
import exceptions.GebruikersnaamInGebruikException;

public class DomeinController {

	private final SpelerRepository spelerRepository;
	private final Spel spel;

	public DomeinController() {
		spelerRepository = new SpelerRepository();
		spel = new Spel();
	}

	public void voegSpelersToe(List<SpelerDTO> spelerDTOS) {
		for (SpelerDTO speler : spelerDTOS) {
			spel.voegSpelersToe(new Speler(speler.gebruikersnaam(), speler.geboortejaar(), speler.aantalGewonnen(),
					speler.aantalGespeeld(), speler.koninkrijk(), speler.scores(), speler.isWinnaar()));
		}
	}

	public void registreerSpeler(String gebruikersnaam, int geboortejaar) throws GebruikersnaamInGebruikException {
		Speler nieuweSpeler = new Speler(gebruikersnaam, geboortejaar);
		spelerRepository.voegToe(nieuweSpeler);
	}

	public List<SpelerDTO> geefOverzichtSpelers() {
		List<Speler> spelers = spelerRepository.geefAlleSpelers();
		List<SpelerDTO> overzicht = new ArrayList<>();

		for (Speler s : spelers) {
			overzicht.add(new SpelerDTO(s.getGebruikersnaam(), s.getGeboortejaar(), s.getAantalGewonnen(),
					s.getAantalGespeeld(), s.getKoninkrijk(), s.getScores(), s.isWinnaar()));
		}
		return overzicht;
	}

	public SpelDTO geefSpelDTO() {
		List<List<Dominotegel>> kolommen = new ArrayList<>();
		for (int i = 0; i < 12; i++) {
			kolommen.add(spel.geefBeginOfEindKolom());
		}
		return new SpelDTO(kolommen, spel.isEindeSpel());
	}

	public static List<String> geefAlleKleuren() {
		List<Kleuren> kleuren = Arrays.asList(Kleuren.values());
		return kleuren.stream().map(Enum::toString).toList();
	}

	public List<Dominotegel> schudDominotegels(int aantalSpelers) {
		return spel.schudDominotegels(aantalSpelers);
	}

	public List<Dominotegel> geefTegels(int aantal) {
		return spel.geefTegels(aantal);
	}

	public void berekenWinnaars() {
		spel.berekenWinnaars();
	}

	public List<String> geefKleurenInTaal(Locale locale) {
		ResourceBundle colors = ResourceBundle.getBundle("utils.resource_bundle", locale);
		List<String> kleurTaal = new ArrayList<>();
		for (Kleuren kleur : Kleuren.values()) {
			kleurTaal.add(colors.getString(kleur.name()));
		}
		return kleurTaal;
	}

	public List<Dominotegel> plaatsTegelsInStartkolom(Deque<Dominotegel> gekozenTegels) {
		return spel.plaatsTegelsInStartkolom(gekozenTegels);
	}

}
