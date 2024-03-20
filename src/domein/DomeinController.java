package domein;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import dto.SpelerDTO;

public class DomeinController {

	private final SpelerRepository spelerRepository;
	private final Spel spel;

	public DomeinController() {
		spelerRepository = new SpelerRepository();
		spel = new Spel();
	}

	public void registreerSpeler(String gebruikersnaam, int geboortejaar) {
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

	public static List<String> geefAlleKleuren() {
		List<Kleuren> kleuren = Arrays.asList(Kleuren.values());
		return kleuren.stream().map(Enum::toString).collect(Collectors.toList());
	}

	public List<Dominotegel> schudDominotegels(int aantalSpelers) {
		return spel.schudDominotegels(aantalSpelers);

	}

	public List<Integer> berekenScore(SpelerDTO speler){
		return spel.berekenScore(speler);
	}

	public void sorteerOpScore(List<SpelerDTO> spelers){
		spelers.sort(new ScoreComparator());
	}


}
