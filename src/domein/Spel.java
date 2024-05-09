package domein;

import static domein.Landschap.AARDE;
import static domein.Landschap.BOS;
import static domein.Landschap.GRAS;
import static domein.Landschap.MIJN;
import static domein.Landschap.WATER;
import static domein.Landschap.ZAND;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import dto.SpelerDTO;

public class Spel {

	private List<SpelerDTO> aantalSpelers = new ArrayList<>();

	private List<Dominotegel> dominotegels;
	private List<Speler> spelers = new ArrayList<>();
	private boolean isGeschud = false;

	protected static final int MAX_LENGTE = 4;

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
		// if (aantalSpelers.size() < 3 || aantalSpelers.size() > 4)
		// throw new IllegalArgumentException(
		// "Het aantal spelers moet minstens 3 spelers en maximum 4 spelers bevatten");
		this.aantalSpelers = aantalSpelers2;
	}

	public List<Dominotegel> getDominotegels() {
		return dominotegels;
	}

	public void setDominotegels(List<Dominotegel> dominotegels) {
		// Controleert of het aantal spelers gelijk is aan 3.
		if (aantalSpelers.size() == 3)
			// Initialiseert de lijst 'dominotegels' met een capaciteit voor 36 tegels,
			this.dominotegels = new ArrayList<>(36);

		// Controleert of het aantal spelers gelijk is aan 4.
		if (aantalSpelers.size() == 4)
			// Initialiseert de lijst 'dominotegels' met een capaciteit voor 48 tegels,
			this.dominotegels = new ArrayList<>(48);
	}

	private void genereerAantalDominotegels() { // cijfer achterkant, aantal kronen, (0 = 0kronen, 1 = kronen links, 2 =
												// kronen rechts)
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

		if (!isGeschud) {
			Collections.shuffle(dominotegels); // Schud de lijst met dominotegels
			isGeschud = true; // lijst moet maar 1 keer geschud worden

			// Retourneer een sublist met het vereiste aantal tegels
			if (aantalSpelers == 3) {
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

	// Plaats de genomen tegels in de startkolom, gesorteerd volgens hun nummer met
	// hun landschapszijde naar boven
	public List<Dominotegel> plaatsTegelsInStartkolom() {
		return geefTegels(aantalSpelers.size()).stream().sorted(Comparator.comparing(Dominotegel::getGetal))
				.collect(Collectors.toList());
	}

	public HashMap<Speler, List<Integer>> geefScores() {
		// Creëert een nieuwe LinkedHashMap, waarin de volgorde van invoer van spelers
		// behouden blijft.
		HashMap<Speler, List<Integer>> spelerScores = new LinkedHashMap<>();

		// Doorloopt elke speler in de lijst 'spelers'.
		for (Speler speler : spelers) {
			// Voegt de speler toe aan de map 'spelerScores', met als waarde de lijst van
			// scores van die speler.
			spelerScores.put(speler, speler.getScores());
		}

		// Retourneert de map die alle spelers en hun respectievelijke scores bevat.
		return spelerScores;
	}

	public boolean isEindeSpel() {
		return dominotegels.isEmpty();
	}

	public void sorteerOpScore() {
		spelers.sort(new utils.ScoreComparator());

	}

	public List<Dominotegel> geefBeginOfEindKolom() {
		// Bepaalt het aantal spelers, dat gebruikt wordt om het aantal tegels te
		// bepalen.
		int aantal = spelers.size();

		// Print het huidige aantal tegels in de lijst 'dominotegels'.
		System.out.println(dominotegels.size());

		// Creëert een nieuwe ArrayList om de begin- of eindkolom van tegels op te
		// slaan.
		List<Dominotegel> kolom = new ArrayList<>();

		// Voegt het overeenkomstige aantal tegels toe aan 'kolom' en verwijdert ze uit
		// 'dominotegels'.
		for (int i = 0; i < aantal; i++) {
			kolom.add(dominotegels.get(0)); // Voegt de eerste tegel toe aan 'kolom'.
			dominotegels.remove(0); // Verwijdert de eerste tegel uit 'dominotegels'.
		}

		// Sorteert de 'kolom' op basis van het getal op elke tegel.
		kolom.sort(Comparator.comparingInt(Dominotegel::getGetal));

		// Retourneert de gesorteerde lijst van tegels.
		return kolom;
	}

	public void berekenWinnaars() {
		// Sorteert de lijst van spelers op score in afnemende volgorde.
		sorteerOpScore();

		// Haalt de speler met de hoogste score op, die nu de eerste in de lijst is.
		Speler topSpeler = spelers.get(0);

		// Haalt een map op met elke speler gekoppeld aan hun scorelijst.
		HashMap<Speler, List<Integer>> spelerScores = geefScores();

		// Haalt de scorelijst op van de speler met de hoogste score.
		List<Integer> topScores = spelerScores.get(topSpeler);

		// Loopt door elke speler in de lijst.
		for (Speler speler : spelers) {
			// Verhoogt het aantal gespeelde spellen voor elke speler met één.
			speler.setAantalGespeeld(speler.getAantalGespeeld() + 1);

			// Haalt de scores op voor de huidige speler.
			List<Integer> scores = spelerScores.get(speler);

			// Controleert of de huidige speler dezelfde scores heeft als de topSpeler.
			if (scores.get(0).equals(topScores.get(0)) && scores.get(1).equals(topScores.get(1))
					&& scores.get(2).equals(topScores.get(2))) {
				// Stelt de huidige speler in als winnaar.
				speler.setIsWinnaar(true);
				// Verhoogt het aantal gewonnen spellen voor de winnaar met één.
				speler.setAantalGewonnen(speler.getAantalGewonnen() + 1);
			}
		}
	}

	public void plaatsTegel(Dominotegel tegel, int y, int x, int hoek, SpelerDTO spelerDTO) {
		// Creëert een nieuwe lijst om de vakjes van de dominotegel in op te slaan.
		List<Vakje> vakjes = new ArrayList<>();

		// Stelt de x- en y-coördinaten in voor het eerste vakje van de dominotegel.
		tegel.getVakje1().setX(x);
		tegel.getVakje1().setY(y);
		System.out.println(hoek + "y:" + y + " x: " + x);

		int dx = 0, dy = 0;
		switch (hoek) {
		case 90:
			dx = +1;
			break;
		case 270:
			dx = -1;
			break;
		case 180:
			dy = -1;
			break;
		default:
			dy = 1;
			break;
		}

		// bereken voor 2e vakje
		int x2 = x + dx;
		int y2 = y + dy;

		System.out.println("y2: " + y2 + "x2: " + x2);

		// Stelt de x- en y-coördinaten in voor het tweede vakje van de dominotegel.
		tegel.getVakje2().setX(x2);
		tegel.getVakje2().setY(y2);

		// Voegt de twee vakjes van de dominotegel toe aan de lijst 'vakjes'.
		vakjes.add(tegel.getVakje1());
		vakjes.add(tegel.getVakje2());

		// Doorloopt elke speler in de lijst 'spelers'.
		for (Speler speler : spelers) {
			// Controleert of de gebruikersnaam van de speler overeenkomt met die van
			// 'spelerDTO'.
			if (Objects.equals(speler.getGebruikersnaam(), spelerDTO.gebruikersnaam())) {
				// Roept de methode 'plaatsTegel' op de gevonden speler aan met de lijst
				// 'vakjes'.
				speler.plaatsTegel(vakjes);
			}
		}
	}

	public boolean kanPlaatsen(Dominotegel tegel, int y, int x, int hoek, SpelerDTO spelerDTO) {
		// Initieel wordt 'kan' ingesteld op false, wat betekent dat de tegel niet
		// geplaatst kan worden
		boolean kan = false;

		// Loopt door elke speler in de lijst 'spelers'.
		for (Speler speler : spelers) {
			// Controleert of de gebruikersnaam van de speler overeenkomt met die van de
			// spelerDTO.
			if (Objects.equals(speler.getGebruikersnaam(), spelerDTO.gebruikersnaam())) {
				// Roept de methode 'kanPlaatsen' aan op de gevonden speler, die bepaalt of de
				// tegel geplaatst kan worden op de aangegeven posities.
				kan = speler.kanPlaatsen(tegel, y, x, hoek);
			}
		}

		// Retourneert de waarde van 'kan', true als de tegel geplaatst kan worden,
		// anders false.
		return kan;
	}

}
