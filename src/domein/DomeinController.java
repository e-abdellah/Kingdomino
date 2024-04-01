package domein;

import java.util.*;
import java.util.stream.Collectors;

import dto.DominotegelDTO;
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
		return new SpelDTO(spel.isEindeSpel());
	}

	public static List<String> geefAlleKleuren() {
		List<Kleuren> kleuren = Arrays.asList(Kleuren.values());
		return kleuren.stream().map(Enum::toString).collect(Collectors.toList());
	}

	public List<Dominotegel> dominotegels(int aantalSpelers) {
		spel.schudDominotegels(aantalSpelers);
		return spel.getDominotegels();
	}

	public List<DominotegelDTO> geefDominotegels(int aantalSpelers) {
		spel.schudDominotegels(aantalSpelers);
		List<DominotegelDTO> dominotegelDTOS = new ArrayList<>();
		List<Dominotegel> tegels = spel.getDominotegels();
		for(Dominotegel dominotegel : tegels){
			dominotegelDTOS.add(new DominotegelDTO(dominotegel));
		}
		return dominotegelDTOS;
	}

	public List<DominotegelDTO> geefTegels(int aantal) {
		List<DominotegelDTO> dominotegelDTOS = new ArrayList<>();
		for(Dominotegel dominotegel : spel.geefTegels(aantal)){
			dominotegelDTOS.add(new DominotegelDTO(dominotegel));
		}
        return dominotegelDTOS;
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

	public List<Dominotegel> plaatsTegelsInStartkolom() {
		return spel.plaatsTegelsInStartkolom();
	}

	public List<DominotegelDTO> geefKolom(){
		List<DominotegelDTO> kolom = new ArrayList<>();
		for(Dominotegel tegel : spel.geefBeginOfEindKolom()){
			kolom.add(new DominotegelDTO(tegel));
		}
		return kolom;
	}

	public void plaatsTegel(DominotegelDTO tegelDTO, int x, int y, String richting, SpelerDTO spelerDTO){
		spel.plaatsTegel(tegelDTO.tegel(), x, y, richting, spelerDTO);
	}

}