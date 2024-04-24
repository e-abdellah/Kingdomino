package domein;

import java.util.*;
import java.util.stream.Collectors;

import dto.DominotegelDTO;
import dto.SpelerDTO;
import exceptions.GebruikersnaamInGebruikException;

public class DomeinController {

	private final SpelerRepository spelerRepository;
	private final Spel spel;
	private boolean isEindeSpel;


	public DomeinController() {
		spelerRepository = new SpelerRepository();
		spel = new Spel();
	}
	public void berekenEindeSpel(){
		isEindeSpel = spel.isEindeSpel();
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
	public boolean isEindeSpel(){
		return isEindeSpel;
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
			dominotegelDTOS.add(new DominotegelDTO(dominotegel.getVakje1(), dominotegel.getVakje2(), dominotegel.getGetal(), dominotegel.getKroon(), dominotegel.toString()));
		}
		return dominotegelDTOS;
	}

	public List<DominotegelDTO> geefTegels(int aantal) {
		List<DominotegelDTO> dominotegelDTOS = new ArrayList<>();
		for(Dominotegel dominotegel : spel.geefTegels(aantal)){
			dominotegelDTOS.add(new DominotegelDTO(dominotegel.getVakje1(), dominotegel.getVakje2(), dominotegel.getGetal(), dominotegel.getKroon(), dominotegel.toString()));
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
		for(Dominotegel dominotegel : spel.geefBeginOfEindKolom()){
			kolom.add(new DominotegelDTO(dominotegel.getVakje1(), dominotegel.getVakje2(), dominotegel.getGetal(), dominotegel.getKroon(), dominotegel.toString()));
		}
		return kolom;
	}

	public void plaatsTegel(DominotegelDTO tegelDTO, int y, int x, int y2, int x2, SpelerDTO spelerDTO){
		for(Dominotegel tegel : dominotegels(spel.getSpelers().size())){
			if(tegel.getVakje1() == tegelDTO.vakje1() && tegel.getVakje2() == tegelDTO.vakje2()){
				spel.plaatsTegel(tegel, y, x, y2, x2, spelerDTO);
			}
		}
	}

	public boolean kanPlaatsen(DominotegelDTO tegelDTO, int y, int x, int y2, int x2, SpelerDTO spelerDTO) {
		for(Dominotegel tegel : dominotegels(spel.getSpelers().size())){
			if(tegel.getVakje1() == tegelDTO.vakje1() && tegel.getVakje2() == tegelDTO.vakje2()){
				return spel.kanPlaatsen(tegel, y, x, y2, x2, spelerDTO);
			}
		}
        return false;
    }
}