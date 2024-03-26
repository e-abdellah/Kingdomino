package domein;

import static domein.Landschap.AARDE;
import static domein.Landschap.BOS;
import static domein.Landschap.GRAS;
import static domein.Landschap.MIJN;
import static domein.Landschap.WATER;
import static domein.Landschap.ZAND;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.Comparator;

import dto.SpelerDTO;

public class Spel {

	private List<SpelerDTO> aantalSpelers = new ArrayList<>();
	private Set<Integer> getallen;
	private List<Dominotegel> dominotegels;
	private List<Speler> spelers;
	private List<Dominotegel> stapelDominotegels; // Stapel met alle dominotegels, gesorteerd op nummer
	private List<Dominotegel> startKolom; // Startkolom met dominotegels voor deze ronde
	private List<Dominotegel> eindKolom; // Eindkolom met dominotegels voor de volgende ronde
	protected static final int MAX_LENGTE = 6;

	public Spel(List<SpelerDTO> aantalSpelers, List<Dominotegel> dominotegels, Set<Integer> getallen) {
		setAantalSpelers(aantalSpelers);
		getallen = new HashSet<>(36);
		dominotegels = new ArrayList<>();
		genereerAantalDominotegels();
	}

	public Spel(List<SpelerDTO> aantalSpelers, List<Dominotegel> dominotegels) {
		setAantalSpelers(aantalSpelers);
		setDominotegels(dominotegels);
		getallen = new HashSet<>(36);
	}

	public Spel() {
		setAantalSpelers(aantalSpelers);
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
		else
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

	protected List<Dominotegel> schudDominotegels(int aantalSpelers) {
		if (dominotegels == null) {
			// Log een fout, initialiseer de lijst, of gooi een exception
			dominotegels = new ArrayList<>();
			genereerAantalDominotegels(); // Mogelijke actie
		}

		Collections.shuffle(dominotegels); // Schud de lijst met dominotegels

		// Controleer het aantal spelers en bepaal het aantal benodigde tegels
		int aantalBenodigdeTegels = (aantalSpelers == 3) ? 36 : 48;

		// Retourneer een sublist met het vereiste aantal tegels
		return new ArrayList<>(dominotegels.subList(0, aantalBenodigdeTegels - 1));
	}

	protected List<Dominotegel> schudDominotegelsAantal(int aantal) {
		if (dominotegels == null) {
			// Log een fout, initialiseer de lijst, of gooi een exception
			dominotegels = new ArrayList<>();
			genereerAantalDominotegels(); // Mogelijke actie
		}

		Collections.shuffle(dominotegels); // Schud de lijst met dominotegels

		// Controleer het aantal spelers en bepaal het aantal benodigde tegels
		int aantalBenodigdeTegels = aantal;

		// Retourneer een sublist met het vereiste aantal tegels
		return new ArrayList<>(dominotegels.subList(0, aantalBenodigdeTegels - 1));
	}

	public HashMap<Speler, List<Integer>> geefScores() {
		HashMap<Speler, List<Integer>> spelerScores = new LinkedHashMap<>();
		for (Speler speler : spelers) {
			spelerScores.put(speler, speler.getScores());
		}
		return spelerScores;
	}

	public List<Integer> berekenScore(SpelerDTO speler) {
		List<Integer> returnwaarde = new ArrayList<>();
		int score = 0;
		int maxgebied = 0;
		int maxkronen = 0;
		String[][][] koninkrijk = deepCopy3DStringArray(speler.koninkrijk());
		for (int i = 0; i <= 2 * MAX_LENGTE; i++) {
			for (int j = 0; j <= 2 * MAX_LENGTE; j++) {
				if (koninkrijk[i][j][0] != null) {
					int tempgebied, tempkroon;
					tempgebied = berekenScoreRecursief(i, j, koninkrijk).get(0);
					tempkroon = berekenScoreRecursief(i, j, koninkrijk).get(1);
					score += tempgebied * tempkroon;
					if (tempgebied > maxgebied) {
						maxgebied = tempgebied;
					}
					if (tempkroon > maxkronen) {
						maxkronen = tempkroon;
					}
				}
			}
		}
		returnwaarde.add(score);
		returnwaarde.add(maxgebied);
		returnwaarde.add(maxkronen);
		return returnwaarde;
	}

	public List<Integer> berekenScoreRecursief(int x, int y, String[][][] koninkrijk) {
		List<Integer> score = new ArrayList<>();
		int aantal = 1;
		int kronen = Integer.parseInt(koninkrijk[x][y][1]);
		String huidigVak = koninkrijk[x][y][0];
		koninkrijk[x][y][0] = null;
		for (int j = -1; j <= 1; j++) {
			for (int k = -1; k <= 1; k++) {
				if ((j == 0 && k == 0) || (j == -1 && k == 1) || (j == 1 && k == -1) || (j == 1 && k == 1)
						|| (j == -1 && k == -1)) {
					continue;
				}
				if (koninkrijk[x + j][y + k][0].equals(huidigVak)) {
					aantal += berekenScoreRecursief(x + j, y + k, koninkrijk).get(0);
					kronen += berekenScoreRecursief(x + j, y + k, koninkrijk).get(1);
				}
			}
		}
		score.add(aantal);
		score.add(kronen);
		return score;
	}

	private static String[][][] deepCopy3DStringArray(String[][][] original) {
		if (original == null) {
			return null;
		}

		String[][][] copy = new String[original.length][][];
		for (int i = 0; i < original.length; i++) {
			if (original[i] == null) {
				continue;
			}

			copy[i] = new String[original[i].length][];
			for (int j = 0; j < original[i].length; j++) {
				if (original[i][j] == null) {
					continue;
				}

				copy[i][j] = new String[original[i][j].length];
				System.arraycopy(original[i][j], 0, copy[i][j], 0, original[i][j].length);
			}
		}

		return copy;
	}

	private boolean isEindeSpel() {
		while (!dominotegels.isEmpty()) {
			speelRonde();
			return false;
		}
		return true;
	}

	private void speelRonde() {
		vulEindKolomAan();
		while (!elkeKoningInEindKolom()) {
			Speler volgendeSpeler = bepaalVolgendeSpeler();
			// Hier implementeer je de logica voor de speler om zijn beurt te spelen
		}
		verplaatsTegelsNaarStartKolom();
	}

	private void speelBeurt(Speler speler) {
		// Hier implementeer je de logica voor de speler om zijn beurt te spelen
	}

	private void vulEindKolomAan() {
	/*	eindKolom.clear(); // Zorg dat de eindkolom leeg is voor nieuwe tegels
		for (int i = 0; i < aantalSpelers.size(); i++) {
			eindKolom.add(stapelDominotegels.remove(0)); // Neem de bovenste tegel van de stapel
		}
		eindKolom.sort(Comparator.comparing(Dominotegel::getNummer));*/
	}

	private void verplaatsTegelsNaarStartKolom() {
		startKolom.addAll(eindKolom);
		eindKolom.clear(); // Maak de eindkolom leeg na het verplaatsen
	}

	private boolean elkeKoningInEindKolom() {
/*		for (Dominotegel tegel : eindKolom) {
			if (tegel.getKoning() == null) {
				return false;
			}
		}
		return true;*/


		return true;
	}

	private Speler bepaalVolgendeSpeler() {
		return null;
	}




	public void sorteerOpScore() {
		// TODO Auto-generated method stub

	}

}
