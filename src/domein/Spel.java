package domein;

import static domein.Landschap.AARDE;
import static domein.Landschap.BOS;
import static domein.Landschap.GRAS;
import static domein.Landschap.MIJN;
import static domein.Landschap.WATER;
import static domein.Landschap.ZAND;

import java.util.*;
import java.util.stream.Collectors;

import dto.DominotegelDTO;
import dto.SpelerDTO;

public class Spel {

	private List<SpelerDTO> aantalSpelers = new ArrayList<>();

	private List<Dominotegel> dominotegels;
	private List<Speler> spelers = new ArrayList<>();
	private boolean isGeschud = false;

	protected static final int MAX_LENGTE = 6;

	public Spel(List<SpelerDTO> aantalSpelers, List<Dominotegel> dominotegels, Set<Integer> getallen,
			List<Dominotegel> startKolom, boolean isEindeSpel) {
		setAantalSpelers(aantalSpelers);
		getallen = new HashSet<>(36);
		dominotegels = new ArrayList<>();
		genereerAantalDominotegels();
	}

	public final void voegSpelersToe(Speler speler) {
		spelers.add(speler);
	}

	public Spel(List<SpelerDTO> aantalSpelers, List<Dominotegel> dominotegels) {
		setAantalSpelers(aantalSpelers);
		setDominotegels(dominotegels);
		Set<Integer> getallen = new HashSet<>(36);
	}

	public Spel() {
		setAantalSpelers(aantalSpelers);
	}

	public List<Speler> getSpelers() {
		return spelers;
	}

	public List<SpelerDTO> getAantalSpelers() {
		return aantalSpelers;
	}

	private void setAantalSpelers(List<SpelerDTO> aantalSpelers2) {
		//		if (aantalSpelers.size() < 3 || aantalSpelers.size() > 4)
		//			throw new IllegalArgumentException(
		//					"Het aantal spelers moet minstens 3 spelers en maximum 4 spelers bevatten");
		this.aantalSpelers = aantalSpelers2;
	}

	public List<Dominotegel> getDominotegels() {
		return dominotegels;
	}

	public void setDominotegels(List<Dominotegel> dominotegels) {
		if (aantalSpelers.size() == 3)
			this.dominotegels = new ArrayList<>(36);
		if (aantalSpelers.size() == 4)
			this.dominotegels = new ArrayList<>(48);

	}

	private void genereerAantalDominotegels() { //cijfer achterkant, aantal kronen, (0 = 0kronen, 1 = kronen links, 2 = kronen rechts)
		dominotegels.add(new Dominotegel(new Vakje(ZAND), new Vakje(ZAND), 1, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(ZAND), new Vakje(ZAND), 2, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(BOS), new Vakje(BOS), 3, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(BOS), new Vakje(BOS), 4, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(BOS), new Vakje(BOS), 5, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(BOS), new Vakje(BOS), 6, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(WATER), new Vakje(WATER), 7, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(WATER), new Vakje(WATER), 8, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(WATER), new Vakje(WATER), 9, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(GRAS), new Vakje(GRAS), 10, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(GRAS), new Vakje(GRAS), 11, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(AARDE), new Vakje(AARDE), 12, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(ZAND), new Vakje(BOS), 13, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(ZAND), new Vakje(WATER), 14, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(ZAND), new Vakje(GRAS), 15, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(ZAND), new Vakje(AARDE), 16, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(BOS), new Vakje(WATER), 17, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(BOS), new Vakje(GRAS), 18, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(ZAND), new Vakje(WATER), 19, 1, 1));
		dominotegels.add(new Dominotegel(new Vakje(ZAND), new Vakje(BOS), 20, 1, 1));
		dominotegels.add(new Dominotegel(new Vakje(ZAND), new Vakje(GRAS), 21, 1, 1));
		dominotegels.add(new Dominotegel(new Vakje(ZAND), new Vakje(AARDE), 22, 1, 1));
		dominotegels.add(new Dominotegel(new Vakje(ZAND), new Vakje(MIJN), 23, 1, 1));
		dominotegels.add(new Dominotegel(new Vakje(BOS), new Vakje(ZAND), 24, 1, 1));
		dominotegels.add(new Dominotegel(new Vakje(BOS), new Vakje(ZAND), 25, 1, 1));
		dominotegels.add(new Dominotegel(new Vakje(BOS), new Vakje(ZAND), 26, 1, 1));
		dominotegels.add(new Dominotegel(new Vakje(BOS), new Vakje(ZAND), 27, 1, 1));
		dominotegels.add(new Dominotegel(new Vakje(BOS), new Vakje(WATER), 28, 1, 1));
		dominotegels.add(new Dominotegel(new Vakje(BOS), new Vakje(GRAS), 29, 1, 1));
		dominotegels.add(new Dominotegel(new Vakje(WATER), new Vakje(ZAND), 30, 1, 1));
		dominotegels.add(new Dominotegel(new Vakje(WATER), new Vakje(ZAND), 31, 1, 1));
		dominotegels.add(new Dominotegel(new Vakje(WATER), new Vakje(BOS), 32, 1, 1));
		dominotegels.add(new Dominotegel(new Vakje(WATER), new Vakje(BOS), 33, 1, 1));
		dominotegels.add(new Dominotegel(new Vakje(WATER), new Vakje(BOS), 34, 1, 1));
		dominotegels.add(new Dominotegel(new Vakje(WATER), new Vakje(BOS), 35, 1, 1));
		dominotegels.add(new Dominotegel(new Vakje(ZAND), new Vakje(GRAS), 36, 1, 2));
		dominotegels.add(new Dominotegel(new Vakje(WATER), new Vakje(GRAS), 37, 1, 2));
		dominotegels.add(new Dominotegel(new Vakje(ZAND), new Vakje(AARDE), 38, 1, 2));
		dominotegels.add(new Dominotegel(new Vakje(GRAS), new Vakje(AARDE), 39, 1, 2));
		dominotegels.add(new Dominotegel(new Vakje(MIJN), new Vakje(ZAND), 40, 1, 1));
		dominotegels.add(new Dominotegel(new Vakje(ZAND), new Vakje(GRAS), 41, 2, 2));
		dominotegels.add(new Dominotegel(new Vakje(WATER), new Vakje(GRAS), 42, 2, 2));
		dominotegels.add(new Dominotegel(new Vakje(ZAND), new Vakje(AARDE), 43, 2, 2));
		dominotegels.add(new Dominotegel(new Vakje(GRAS), new Vakje(AARDE), 44, 2, 2));
		dominotegels.add(new Dominotegel(new Vakje(MIJN), new Vakje(ZAND), 45, 2, 1));
		dominotegels.add(new Dominotegel(new Vakje(AARDE), new Vakje(MIJN), 46, 2, 2));
		dominotegels.add(new Dominotegel(new Vakje(AARDE), new Vakje(MIJN), 47, 2, 2));
		dominotegels.add(new Dominotegel(new Vakje(ZAND), new Vakje(MIJN), 48, 3, 2));

	}

	public void schudDominotegels(int aantalSpelers) {
		if (dominotegels == null || dominotegels.isEmpty()) {
			// Log een fout, initialiseer de lijst, of gooi een exception
			dominotegels = new ArrayList<>();
			genereerAantalDominotegels(); // Mogelijke actie
		}

		// Controleer het aantal spelers en bepaal het aantal benodigde tegels
		int aantalBenodigdeTegels = (aantalSpelers == 3) ? 36 : 48;

		if(!isGeschud){
			Collections.shuffle(dominotegels); // Schud de lijst met dominotegels
			isGeschud = true; // lijst moet maar 1 keer geschud worden

			// Retourneer een sublist met het vereiste aantal tegels
			if(aantalSpelers == 3){
				dominotegels = dominotegels.subList(0, 36);
			}
		}
	}

	public List<Dominotegel> geefTegels(int aantal) {
		if (dominotegels == null || dominotegels.isEmpty()) {
			// Log een fout, initialiseer de lijst, of gooi een exception
			dominotegels = new ArrayList<>();
			genereerAantalDominotegels(); // Zorgt dat de lijst met dominotegels wordt gegenereerd
		}

		Collections.shuffle(dominotegels); // Schud de lijst met dominotegels

		// Zorg ervoor dat je niet meer tegels vraagt dan beschikbaar zijn
		int aantalBenodigdeTegels = Math.min(aantal, dominotegels.size());

		// Maak een nieuwe lijst van de eerste 'aantalBenodigdeTegels' tegels
		List<Dominotegel> opgehaaldeTegels = new ArrayList<>(dominotegels.subList(0, aantalBenodigdeTegels));

		// Verwijder deze tegels uit de oorspronkelijke lijst
		dominotegels.removeAll(opgehaaldeTegels);

		// Retourneer de opgehaalde tegels
		return opgehaaldeTegels;
	}

	// Plaats de genomen tegels in de startkolom, gesorteerd volgens hun nummer met hun landschapszijde naar boven
	public List<Dominotegel> plaatsTegelsInStartkolom() {
		return geefTegels(aantalSpelers.size()).stream().sorted(Comparator.comparing(Dominotegel::getGetal))
				.collect(Collectors.toList());
	}

	public HashMap<Speler, List<Integer>> geefScores() {
		HashMap<Speler, List<Integer>> spelerScores = new LinkedHashMap<>();
		for (Speler speler : spelers) {
			spelerScores.put(speler, speler.getScores());
		}
		return spelerScores;
	}

	public boolean isEindeSpel() {
		return dominotegels.isEmpty();
	}

	public void sorteerOpScore() {
		spelers.sort(new utils.ScoreComparator());

	}

	public List<Dominotegel> geefBeginOfEindKolom() {
		int aantal = spelers.size();
		System.out.println(dominotegels.size());
		List<Dominotegel> kolom = new ArrayList<>();
		for (int i = 0; i < aantal; i++) {
			kolom.add(dominotegels.get(0));
			dominotegels.remove(0);
		}
		kolom.sort(Comparator.comparingInt(Dominotegel::getGetal));
		return kolom;
	}

	public void berekenWinnaars() {
		sorteerOpScore();
		Speler topSpeler = spelers.get(0);
		HashMap<Speler, List<Integer>> spelerScores = geefScores();
		List<Integer> topScores = spelerScores.get(topSpeler);

		for (Speler speler : spelers) {
			speler.setAantalGespeeld(speler.getAantalGespeeld() + 1);
			List<Integer> scores = spelerScores.get(speler);
			if (scores.get(0).equals(topScores.get(0)) && scores.get(1).equals(topScores.get(1))
					&& scores.get(2).equals(topScores.get(2))) {
				speler.setIsWinnaar(true);
				speler.setAantalGewonnen(speler.getAantalGewonnen() + 1);
			}
		}
	}

	public void plaatsTegel(Dominotegel tegel, int y, int x, String richting, SpelerDTO spelerDTO){
		List<Vakje> vakjes = new ArrayList<>();
		List<Integer> pos = new ArrayList<>(List.of(0, 0));

		switch (richting) {
			case "boven": // Up
				pos.set(1, pos.get(1) - 1);
				break;
			case "onder": // Down
				pos.set(1, pos.get(1) + 1);
				break;
			case "links": // Left
				pos.set(0, pos.get(0) - 1);
				break;
			case "rechts": // Right
				pos.set(0, pos.get(0) + 1);
				break;
			default:
				System.out.println("Onbekende richting"); // Unknown direction
				break;
		}

		tegel.getVakje1().setX(x);
		tegel.getVakje1().setY(y);
		tegel.getVakje2().setX(x + pos.get(0));
		tegel.getVakje2().setY(y + pos.get(1));
		vakjes.add(tegel.getVakje1());
		vakjes.add(tegel.getVakje2());
		for(Speler speler : spelers){
			if(Objects.equals(speler.getGebruikersnaam(), spelerDTO.gebruikersnaam())){
				speler.plaatsTegel(vakjes);
			}
		}

	}

	public boolean kanPlaatsen(Dominotegel tegel, int y, int x, String richting, SpelerDTO spelerDTO) {
		boolean kan = false;
		for(Speler speler : spelers){
			if(Objects.equals(speler.getGebruikersnaam(), spelerDTO.gebruikersnaam())){
				kan = speler.kanPlaatsen(tegel, y, x, richting);
			}
		}
		return  kan;
	}
}
