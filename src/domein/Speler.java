package domein;

public class Speler {
	private String gebruikersnaam;
	private int geboortejaar;
	private int aantalGewonnen, aantalGespeeld;
	private static final int MAX_GEBOORTEJAAR = 1924, MIN_GEBOORTEJAAR = 2018;

	private int starttegel = 1;
	private int koning = 1;
	private int kasteel = 1;

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
		setKoning(koning);
		setKasteel(kasteel);

	}

	public String getGebruikersnaam() {
		return gebruikersnaam;
	}

	private void setGebruikersnaam(String gebruikersnaam) {
		if (gebruikersnaam == null || gebruikersnaam.isBlank() || !gebruikersnaam.matches("[a-zA-Z0-9]+")) {
			throw new IllegalArgumentException("Ongeldige gebruikersnaam");
		}
		this.gebruikersnaam = gebruikersnaam;
	}

	public int getGeboortejaar() {
		return geboortejaar;
	}

	private void setGeboortejaar(int geboortejaar) {
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

	public int getKoning() {
		return koning;
	}

	private void setKoning(int koning) {
		this.koning = koning;
	}

	public int getKasteel() {
		return kasteel;
	}

	private void setKasteel(int kasteel) {
		this.kasteel = kasteel;
	}

}
