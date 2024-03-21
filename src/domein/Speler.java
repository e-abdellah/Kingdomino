package domein;

import java.util.ArrayList;
import java.util.List;

public class Speler {
	private String gebruikersnaam;
	private int geboortejaar;
	private int aantalGewonnen, aantalGespeeld;

	private String[][][] koninkrijk;
	private List<Integer> scores;

	protected static final int MAX_GEBOORTEJAAR = 1924;
	protected static final int MIN_GEBOORTEJAAR = 2018;

	private String kleur;
	private int starttegel = 1;
	private int score;
	//	private int koning = 1;
	//	private int kasteel = 1;
	protected static final int MAX_LENGTE = 6;

	public String[][][] getKoninkrijk() {
		return koninkrijk;
	}

	public void setKoninkrijk(String[][][] koninkrijk) {
		this.koninkrijk = koninkrijk;
	}

	public Speler(String gebruikersnaam, int geboortejaar) {
		setGebruikersnaam(gebruikersnaam);
		setGeboortejaar(geboortejaar);
	}

	public Speler(String gebruikersnaam, int geboortejaar, int aantalGewonnen, int aantalGespeeld) {
		setGebruikersnaam(gebruikersnaam);
		setGeboortejaar(geboortejaar);
		setAantalGewonnen(aantalGewonnen);
		setAantalGespeeld(aantalGespeeld);

		setStarttegel(starttegel);
	}

	public Speler(String gebruikersnaam, int geboortejaar, int aantalGewonnen, int aantalGespeeld, String kleur) {
		setGebruikersnaam(gebruikersnaam);
		setGeboortejaar(geboortejaar);
		setAantalGewonnen(aantalGewonnen);
		setAantalGespeeld(aantalGespeeld);
		setKleur(kleur);

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

	private void setAantalGewonnen(int aantalGewonnen) {
		this.aantalGewonnen = aantalGewonnen;
	}

	public int getAantalGespeeld() {
		return aantalGespeeld;
	}

	private void setAantalGespeeld(int aantalGespeeld) {
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

	private List<Integer> berekenScore() {
		List<Integer> returnwaarde = new ArrayList<>();
		int score = 0;
		int maxgebied = 0;
		int maxkronen = 0;
		String[][][] koninkrijk = deepCopy3DStringArray(this.koninkrijk);
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

	private List<Integer> berekenScoreRecursief(int x, int y, String[][][] koninkrijk) {
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

	public final void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return score;
	}

}
