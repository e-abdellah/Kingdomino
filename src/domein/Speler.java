package domein;

import dto.SpelerDTO;

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
	//	protected static final int MAX_LENGTE = 2;

	private String kleur;
	private int starttegel = 1;

	private boolean isWinnaar;
	//	private int koning = 1;
	//	private int kasteel = 1;

	public Vakje[][] getKoninkrijk() {
		return koninkrijk;
	}

	public void setKoninkrijk(Vakje[][] koninkrijk) {
		this.koninkrijk = koninkrijk;
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

	public Speler(String gebruikersnaam, int geboortejaar, int aantalGewonnen, int aantalGespeeld, Vakje[][] koninkrijk, List<Integer> scores, boolean isWinnaar) {
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
		if (gebruikersnaam == null || gebruikersnaam.trim().isEmpty()
				|| !gebruikersnaam.matches("[a-zA-Z0-9 ]{6,15}")) {// {// ipv -> // gebruikersnaam.length() < 6) {
			throw new IllegalArgumentException("Ongeldige gebruikersnaam");
		}
		this.gebruikersnaam = gebruikersnaam;

	}

	public boolean isWinnaar(){return isWinnaar;}
	public final void setIsWinnaar(boolean isWinnaar){this.isWinnaar = isWinnaar;}

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

	public int getStarttegel() {
		return starttegel;
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
	/*Uitleg score berekening
	Eerst word gelooped over het veld beginnende linksboven, checkt elk vakje en kijkt wat er ligt, indien er iets ligt
	gaat hij in de 2e functie waar hij rond zich checkt of er nog liggen met hetzelfde landschap, indien wel roept hij zichzelf op
	en checkt hij op dat vakje alles, eens hiermee rond backtraced hij voor degene die hij misste. Elk gecheckt vakje word op null gezet
	hierdoor wordt niets dubbel geteld.
	*/
	private List<Integer> berekenScore() {
		List<Integer> returnwaarde = new ArrayList<>();
		int score = 0;
		int maxgebied = 0;
		int maxkronen = 0;
		Vakje[][] koninkrijk = deepCopy2DVakjeArray(this.koninkrijk);
		for (int i = 0; i <= 2 * MAX_LENGTE; i++) {
			for (int j = 0; j <= 2 * MAX_LENGTE; j++) {
				if (koninkrijk[i][j] != null) {
					int tempgebied, tempkroon;
					List<Integer> temp;
					temp = berekenScoreRecursief(i, j, koninkrijk);
					tempgebied = temp.get(0);
					tempkroon = temp.get(1);
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

	private List<Integer> berekenScoreRecursief(int x, int y, Vakje[][] koninkrijk) {
		List<Integer> score = new ArrayList<>();
		int aantal = 1;
		int kronen = koninkrijk[x][y].getAantalKronen();
		Vakje huidigVak = koninkrijk[x][y];

		Landschap landschap = huidigVak.getLandschap();
		koninkrijk[x][y] = null;

		try{
			if(koninkrijk[x + 1][y] != null && koninkrijk[x + 1][y].getLandschap().equals(landschap)) {
				List<Integer> temp;
				temp = berekenScoreRecursief(x + 1, y, koninkrijk);
				aantal += temp.get(0);
				kronen += temp.get(1);
			}
		}catch(IndexOutOfBoundsException ignored){}
		try{
			if(koninkrijk[x - 1][y] != null && koninkrijk[x - 1][y].getLandschap().equals(landschap)) {
				List<Integer> temp;
				temp = berekenScoreRecursief(x - 1, y, koninkrijk);
				aantal += temp.get(0);
				kronen += temp.get(1);
			}
		}catch(IndexOutOfBoundsException ignored){}
		try{
			if(koninkrijk[x][y + 1] != null && koninkrijk[x][y + 1].getLandschap().equals(landschap)) {
				List<Integer> temp;
				temp = berekenScoreRecursief(x, y + 1, koninkrijk);
				aantal += temp.get(0);
				kronen += temp.get(1);
			}
		}catch(IndexOutOfBoundsException ignored){}
		try{
			if(koninkrijk[x][y - 1] != null && koninkrijk[x][y - 1].getLandschap().equals(landschap)) {
				List<Integer> temp;
				temp = berekenScoreRecursief(x, y - 1, koninkrijk);
				aantal += temp.get(0);
				kronen += temp.get(1);
			}
		}catch(IndexOutOfBoundsException ignored){}

		score.add(aantal);
		score.add(kronen);
		return score;
	}

	public void plaatsTegel(List<Vakje> vakjes){
		for(Vakje vakje : vakjes){
			koninkrijk[vakje.getY()][vakje.getX()] = vakje;
		}
	}

	public static Vakje[][] deepCopy2DVakjeArray(Vakje[][] original) {
		if (original == null) {
			return null;
		}

		Vakje[][] copy = new Vakje[original.length][];
		for (int i = 0; i < original.length; i++) {
			if (original[i] == null) {
				copy[i] = null;
				continue;
			}

			copy[i] = new Vakje[original[i].length];
			for (int j = 0; j < original[i].length; j++) {
				// Assuming Vakje has a copy constructor. Replace with vakje.clone() if a clone method is used.
				copy[i][j] = original[i][j] != null ? new Vakje(original[i][j].getLandschap(), original[i][j].getAantalKronen()) : null;
			}
		}
		return copy;
	}

	public boolean kanPlaatsen(Dominotegel tegel, int x, int y, String richting) {
		int dx = 0, dy = 0;
		switch (richting) {
			case "boven":
				dy = -1;
				break;
			case "onder":
				dy = 1;
				break;
			case "links":
				dx = -1;
				break;
			case "rechts":
				dx = 1;
				break;
			default:
				System.out.println("Onbekende richting");
				return false;
		}

		// bereken voor 2e vakje
		int x2 = x + dx;
		int y2 = y + dy;

		// check voor out of bounds
		if (x < 0 || x >= koninkrijk.length || y < 0 || y >= koninkrijk[0].length ||
				x2 < 0 || x2 >= koninkrijk.length || y2 < 0 || y2 >= koninkrijk[0].length) {
			System.out.println("Positie is buiten de grenzen.");
			return false;
		}

		// check of het reeds bezet is
		if (koninkrijk[x][y] != null || koninkrijk[x2][y2] != null) {
			System.out.println("Positie is al bezet.");
			return false;
		}

		if((x == MAX_LENGTE && y == MAX_LENGTE)||(x2 == MAX_LENGTE && y2 == MAX_LENGTE)){return false;}

		return true;
	}


}
