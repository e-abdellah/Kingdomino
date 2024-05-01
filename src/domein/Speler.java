package domein;

import java.util.ArrayList;
import java.util.List;

public class Speler {
	private String gebruikersnaam;
	private int geboortejaar;
	private int aantalGewonnen, aantalGespeeld;
	private static final int MAX_LENGTE = 2;

	private Vakje[][] koninkrijk = new Vakje[2 * MAX_LENGTE + 1][2 * MAX_LENGTE + 1];
	private List<Integer> scores = new ArrayList<>();

	protected static final int MAX_GEBOORTEJAAR = 1924;
	protected static final int MIN_GEBOORTEJAAR = 2018;
	// protected static final int MAX_LENGTE = 2;

	private String kleur;
	private int starttegel = 1;

	private boolean isWinnaar;
	// private int koning = 1;
	// private int kasteel = 1;

	public Vakje[][] getKoninkrijk() {
		return koninkrijk;
	}

	public void setKoninkrijk(Vakje[][] koninkrijk) {
		this.koninkrijk = koninkrijk;
		this.koninkrijk[MAX_LENGTE][MAX_LENGTE] = new Vakje(Landschap.KASTEEL);
	}

	public Speler(String gebruikersnaam, int geboortejaar) {
		setGebruikersnaam(gebruikersnaam);
		setGeboortejaar(geboortejaar);
	}

	public Speler(String gebruikersnaam) {
		setGebruikersnaam(gebruikersnaam);
	}

	public Speler(String gebruikersnaam, int geboortejaar, int aantalGewonnen, int aantalGespeeld,
			Vakje[][] koninkrijk) {
		setGebruikersnaam(gebruikersnaam);
		setGeboortejaar(geboortejaar);
		setAantalGewonnen(aantalGewonnen);
		setAantalGespeeld(aantalGespeeld);
		setKoninkrijk(koninkrijk);
		setStarttegel(starttegel);
		setScores();
	}

	public Speler(String gebruikersnaam, int geboortejaar, int aantalGewonnen, int aantalGespeeld, Vakje[][] koninkrijk,
			List<Integer> scores, boolean isWinnaar) {
		setGebruikersnaam(gebruikersnaam);
		setGeboortejaar(geboortejaar);
		setAantalGewonnen(aantalGewonnen);
		setAantalGespeeld(aantalGespeeld);
		setKoninkrijk(koninkrijk);
		setScores();
		setIsWinnaar(isWinnaar);

	}

	public Speler(String gebruikersnaam, int geboortejaar, int aantalGewonnen, int aantalGespeeld, String kleur) {
		setGebruikersnaam(gebruikersnaam);
		setGeboortejaar(geboortejaar);
		setAantalGewonnen(aantalGewonnen);
		setAantalGespeeld(aantalGespeeld);
		setKleur(kleur);
		setScores();

	}

	public Speler(String gebruikersnaam, int geboortejaar, int aantalGewonnen, int aantalGespeeld) {
		setGebruikersnaam(gebruikersnaam);
		setGeboortejaar(geboortejaar);
		setAantalGewonnen(aantalGewonnen);
		setAantalGespeeld(aantalGespeeld);
		setStarttegel(starttegel);
		setScores();
	}

	public String getGebruikersnaam() {
		return gebruikersnaam;
	}

	public final void setGebruikersnaam(String gebruikersnaam) {
		if (gebruikersnaam == null || gebruikersnaam.trim().isEmpty()// Controleert of de opgegeven gebruikersnaam null
																		// is, leeg, of niet tussen interval 6-15
				|| !gebruikersnaam.matches("[a-zA-Z0-9 ]{6,15}")) {// {// ipv -> // gebruikersnaam.length() < 6) {
			throw new IllegalArgumentException("Ongeldige gebruikersnaam");
		}
		this.gebruikersnaam = gebruikersnaam;

	}

	public boolean isWinnaar() {
		return isWinnaar;
	}

	public final void setIsWinnaar(boolean isWinnaar) {
		this.isWinnaar = isWinnaar;
	}

	public List<Integer> getScores() {
		return scores;
	}

	public final void setScores() {
		this.scores = berekenScore();
	}

	public int getGeboortejaar() {
		return geboortejaar;
	}

	public final void setGeboortejaar(int geboortejaar) {
		if (geboortejaar < MAX_GEBOORTEJAAR || geboortejaar > MIN_GEBOORTEJAAR) {
			throw new IllegalArgumentException(
					String.format("Ongeldig geboortejaar. Het geboortejaar moet tussen %d en %d liggen.",
							MIN_GEBOORTEJAAR, MAX_GEBOORTEJAAR));
		}
		this.geboortejaar = geboortejaar;
	}

	public int getAantalGewonnen() {
		return aantalGewonnen;
	}

	public final void setAantalGewonnen(int aantalGewonnen) {
		this.aantalGewonnen = aantalGewonnen;
	}

	public int getAantalGespeeld() {
		return aantalGespeeld;
	}

	public final void setAantalGespeeld(int aantalGespeeld) {
		this.aantalGespeeld = aantalGespeeld;
	}

	private void setStarttegel(int starttegel) {
		this.starttegel = starttegel;
	}

	public String getKleur() {
		return kleur;
	}

	public final void setKleur(String kleur) {
		this.kleur = kleur;
	}

	/*
	 * Uitleg score berekening Eerst word gelooped over het veld beginnende
	 * linksboven, checkt elk vakje en kijkt wat er ligt, indien er iets ligt gaat
	 * hij in de 2e functie waar hij rond zich checkt of er nog liggen met hetzelfde
	 * landschap, indien wel roept hij zichzelf op en checkt hij op dat vakje alles,
	 * eens hiermee rond backtraced hij voor degene die hij misste. Elk gecheckt
	 * vakje word op null gezet hierdoor wordt niets dubbel geteld.
	 */
	private List<Integer> berekenScore() {
		// Een lijst die zal worden geretourneerd met de totale score, grootste gebied,
		// en hoogste aantal kronen.
		List<Integer> returnwaarde = new ArrayList<>();

		// Initialisatie van de totale score.
		int score = 0;

		// Variabelen om het grootste gebied en het grootste aantal kronen bij te
		// houden.
		int maxgebied = 0;
		int maxkronen = 0;

		// Maakt een diepe kopie van de 2D-array 'koninkrijk' om wijzigingen tijdens het
		// proces te vermijden.
		Vakje[][] koninkrijk = deepCopy2DVakjeArray(this.koninkrijk);

		// Dubbele lus om elk vakje in het koninkrijk te doorlopen.
		for (int i = 0; i <= 2 * MAX_LENGTE; i++) {
			for (int j = 0; j <= 2 * MAX_LENGTE; j++) {
				// Controleert of het vakje op positie (i, j) niet null is.
				if (koninkrijk[i][j] != null) {
					// Variabelen om tijdelijk de gebiedsgrootte en het aantal kronen op te slaan.
					int tempgebied, tempkroon;
					List<Integer> temp;

					// Roept een recursieve functie aan die de score berekent voor het gebied
					// beginnend bij (i, j).
					temp = berekenScoreRecursief(i, j, koninkrijk);
					tempgebied = temp.get(0);
					tempkroon = temp.get(1);

					// Bereken de score voor het huidige gebied en voegt het toe aan de totale
					// score.
					score += tempgebied * tempkroon;

					// Update de maximale gebiedsgrootte indien nodig.
					if (tempgebied > maxgebied) {
						maxgebied = tempgebied;
					}

					// Update het maximale aantal kronen indien nodig.
					if (tempkroon > maxkronen) {
						maxkronen = tempkroon;
					}
				}
			}
		}

		// Voegt de berekende waarden toe aan de retourlijst.
		returnwaarde.add(score);
		returnwaarde.add(maxgebied);
		returnwaarde.add(maxkronen);

		// Retourneert de lijst met scores, maximale gebiedsgrootte, en maximale aantal
		// kronen.
		return returnwaarde;
	}

	private List<Integer> berekenScoreRecursief(int x, int y, Vakje[][] koninkrijk) {
		// Creëert een lijst om het aantal vakjes in het gebied en het aantal kronen op
		// te slaan.
		List<Integer> score = new ArrayList<>();
		int aantal = 1; // Begint met 1 omdat het huidige vakje meetelt.
		int kronen = koninkrijk[x][y].getAantalKronen(); // Haalt het aantal kronen op het huidige vakje op.

		Vakje huidigVak = koninkrijk[x][y]; // Huidige vakje wordt opgeslagen voor verdere referentie.
		Landschap landschap = huidigVak.getLandschap(); // Haalt het landschapstype van het huidige vakje op.
		koninkrijk[x][y] = null; // Zet het huidige vakje op null om te voorkomen dat het opnieuw bezocht wordt.

		// Werken aangrenzende vakjes in alle vier de richtingen: oost, west, noord, en
		// zuid.
		try {
			if (koninkrijk[x + 1][y] != null && koninkrijk[x + 1][y].getLandschap().equals(landschap)) {
				List<Integer> temp = berekenScoreRecursief(x + 1, y, koninkrijk);
				aantal += temp.get(0); // Voegt het aantal vakjes toe aan 'aantal'.
				kronen += temp.get(1); // Voegt het aantal kronen toe aan 'kronen'.
			}
		} catch (IndexOutOfBoundsException ignored) {

		}

		try {
			if (koninkrijk[x - 1][y] != null && koninkrijk[x - 1][y].getLandschap().equals(landschap)) {
				List<Integer> temp = berekenScoreRecursief(x - 1, y, koninkrijk);
				aantal += temp.get(0);
				kronen += temp.get(1);
			}
		} catch (IndexOutOfBoundsException ignored) {
		}

		try {
			if (koninkrijk[x][y + 1] != null && koninkrijk[x][y + 1].getLandschap().equals(landschap)) {
				List<Integer> temp = berekenScoreRecursief(x, y + 1, koninkrijk);
				aantal += temp.get(0);
				kronen += temp.get(1);
			}
		} catch (IndexOutOfBoundsException ignored) {
		}

		try {
			if (koninkrijk[x][y - 1] != null && koninkrijk[x][y - 1].getLandschap().equals(landschap)) {
				List<Integer> temp = berekenScoreRecursief(x, y - 1, koninkrijk);
				aantal += temp.get(0);
				kronen += temp.get(1);
			}
		} catch (IndexOutOfBoundsException ignored) {
		}

		// Voegt het totale aantal vakjes en kronen toe aan de scorelijst.
		score.add(aantal);
		score.add(kronen);

		// Retourneert de lijst met het totale aantal vakjes en het totale aantal kronen
		// in dit gebied.
		return score;
	}

	public void plaatsTegel(List<Vakje> vakjes) {
		// Doorloopt elk vakje in de meegegeven lijst 'vakjes'.
		for (Vakje vakje : vakjes) {
			// Plaatst elk vakje in de 2D-array 'koninkrijk' op de coördinaten die bepaald
			// worden door de Y en X waarden van het vakje.
			koninkrijk[vakje.getY()][vakje.getX()] = vakje;
		}
	}

	public static Vakje[][] deepCopy2DVakjeArray(Vakje[][] original) {
		// Controleert of de oorspronkelijke array null is, zo ja, dan retourneert het
		// null.
		if (original == null) {
			return null;
		}

		// Initialiseert een nieuwe 2D-array van Vakje-objecten met dezelfde lengte als
		// het origineel.
		Vakje[][] copy = new Vakje[original.length][];
		for (int i = 0; i < original.length; i++) {
			// Controleert of de subarray null is, zo ja, dan wordt de corresponderende
			// subarray in de kopie ook op null gezet.
			if (original[i] == null) {
				copy[i] = null;
				continue;
			}

			// Creëert een nieuwe subarray voor i-de index met dezelfde lengte als de
			// originele subarray.
			copy[i] = new Vakje[original[i].length];
			for (int j = 0; j < original[i].length; j++) {
				// Creëert een nieuw Vakje-object alleen als het huidige Vakje niet null is,
				// waarbij aangenomen wordt dat Vakje een geschikte constructor heeft.
				copy[i][j] = original[i][j] != null
						? new Vakje(original[i][j].getLandschap(), original[i][j].getAantalKronen())
						: null;
			}
		}
		// Retourneert de diep gekopieerde 2D-array.
		return copy;
	}

	public boolean kanPlaatsen(Dominotegel tegel, int y, int x, int y2, int x2) {

		// Controleert of de voorgestelde x- en y-coördinaten binnen de grenzen van het
		// 'koninkrijk' vallen.
		if (x < 0 || x >= koninkrijk.length || y < 0 || y >= koninkrijk[0].length || x2 < 0 || x2 >= koninkrijk.length
				|| y2 < 0 || y2 >= koninkrijk[0].length) {
			return false; // Retourneert false als een van de coördinaten buiten het bereik valt.
		}

		// Controleert of de voorgestelde vakjes voor de tegel al bezet zijn.
		if (koninkrijk[y][x] != null || koninkrijk[y2][x2] != null) {
			return false; // Retourneert false als een van de vakjes al bezet is.
		}

		// Haalt de vakjes van de dominotegel op.
		Vakje vakje1 = tegel.getVakje1();
		Vakje vakje2 = tegel.getVakje2();

		// Controleert of de omliggende vakjes een bepaalde conditie voldoen.
		// De exacte voorwaarden worden niet beschreven, maar de methode
		// 'checkOmliggende' verwerkt deze.
		if (checkOmliggende(vakje1, y, x) && checkOmliggende(vakje2, y2, x2)) {
			return false; // Retourneert false als de tegel niet voldoet aan de voorwaarden van de
							// omliggende vakjes.
		}

		// Controleert of het plaatsen van de tegel niet zou resulteren in een rij of
		// kolom die langer is dan 5.
		if (!isMaxVijfLang(koninkrijk, y, true) || !isMaxVijfLang(koninkrijk, y2, true)
				|| !isMaxVijfLang(koninkrijk, x, false) || !isMaxVijfLang(koninkrijk, x2, false)) {
			return false; // Retourneert false als het plaatsen de maximale lengte van rijen of kolommen
							// zou overschrijden.
		}

		return true; // Retourneert true als aan alle voorwaarden is voldaan.
	}

	private boolean checkOmliggende(Vakje vakje, int y, int x) {
		// Verkrijgt het landschapstype van het gegeven vakje.
		Landschap landschap = vakje.getLandschap();

		// Definieert richtingen voor het controleren van omliggende vakjes: rechts,
		// links, omlaag, omhoog.
		int[][] directions = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

		// Loopt door elke richting om omliggende vakjes te controleren.
		for (int[] dir : directions) {
			int newY = y + dir[0];
			int newX = x + dir[1];

			// Controleert of de nieuwe coördinaten binnen de grenzen van het 'koninkrijk'
			// vallen.
			if (newX >= 0 && newX < koninkrijk.length && newY >= 0 && newY < koninkrijk[newX].length) {
				Vakje adjacentVakje = koninkrijk[newX][newY];
				// Controleert of het aangrenzende vakje niet null is en of het landschapstype
				// overeenkomt met dat van het gegeven vakje
				// of dat het een kasteel is.
				if (adjacentVakje != null && (adjacentVakje.getLandschap().equals(landschap)
						|| adjacentVakje.getLandschap().equals(Landschap.KASTEEL))) {
					return false; // Retourneert false als aan een van de voorwaarden is voldaan.
				}
			}
		}
		return true; // Retourneert true als geen van de omliggende vakjes een overeenkomend
						// landschapstype heeft.
	}

	private static boolean isMaxVijfLang(Vakje[][] koninkrijk, int index, boolean isRij) {
		// Initialisatie van de minimum en maximum indices tot extreme waarden.
		int minI = Integer.MAX_VALUE;
		int maxI = Integer.MIN_VALUE;

		// Doorloopt alle vakjes in de rij of kolom, afhankelijk van de waarde van
		// 'isRij'.
		for (int i = 0; i < koninkrijk.length; i++) {
			// Selecteert het vakje op basis van de waarde van 'isRij'.
			Vakje huidig = isRij ? koninkrijk[index][i] : koninkrijk[i][index];

			// Als het huidige vakje niet null is, update dan de minI en maxI waarden.
			if (huidig != null) {
				minI = Math.min(minI, i); // Vindt de kleinste index van een niet-null vakje.
				maxI = Math.max(maxI, i); // Vindt de grootste index van een niet-null vakje.
			}
		}

		// Retourneert true als de afstand tussen de verste niet-null vakjes minder is
		// dan vijf.
		return (maxI - minI < 5);
	}

}