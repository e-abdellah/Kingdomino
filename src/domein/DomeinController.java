package domein;

import java.util.*;
import java.util.stream.Collectors;

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
	public SpelDTO geefSpelDTO(){
		List<List<Dominotegel>> kolommen = new ArrayList<>();
		for(int i = 0; i < 12; i++){
			kolommen.add(spel.geefBeginOfEindKolom());
		}
		return new SpelDTO(kolommen, spel.isEindeSpel());
	}
	public static List<String> geefAlleKleuren() {
		List<Kleuren> kleuren = Arrays.asList(Kleuren.values());
		return kleuren.stream().map(Enum::toString).collect(Collectors.toList());
	}
	public List<Dominotegel> schudDominotegels(int aantalSpelers) {
		return spel.schudDominotegels(aantalSpelers);
	}
	public void berekenWinnaars(){
		spel.berekenWinnaars();
	}

}
