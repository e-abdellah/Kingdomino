package domein;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
					s.getAantalGespeeld(), s.getKleur(), s.getKoninkrijk()));
		}
		return overzicht;
	}
	public SpelDTO geefSpelDTO(){
        return new SpelDTO(spel.getSpelers(), spel.geefBeginOfEindKolom());
	}
	public void voegSpelerToe(String gebruikersnaam){
		spel.voegSpelerToe(new Speler(gebruikersnaam));
	}

	public static List<String> geefAlleKleuren() {
		List<Kleuren> kleuren = Arrays.asList(Kleuren.values());
		return kleuren.stream().map(Enum::toString).collect(Collectors.toList());
	}

	/*public List<Dominotegel> schudDominotegels(int aantalSpelers) {
		return spel.schudDominotegels(aantalSpelers);
	}

	public List<Dominotegel> schudDominotegelsAantal(int aantal) {
		return spel.schudDominotegelsAantal(aantal);
	}

	public HashMap<Speler, List<Integer>> geefScores() {
		return spel.geefScores();
	}

	public void sorteerOpScore() {
		spel.sorteerOpScore();
	}*/

}
