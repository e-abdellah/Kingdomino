package domein;

/**
 * De {@code Vakje} klasse representeert een individuele tegel of vakje in een
 * spelbord. Elk vakje heeft een specifiek landschapstype, een aantal kronen dat
 * zijn waarde bepaalt, en coördinaten binnen het spelbord.
 */
public class Vakje {
	private Landschap landschap;
	private int aantalKronen;

	private int x;
	private int y;

	/**
	 * Standaard constructor voor een leeg vakje zonder specifieke eigenschappen.
	 */
	public Vakje() {
	}

	/**
	 * Constructor voor een vakje met volledige specificatie van landschap, aantal
	 * kronen en coördinaten.
	 *
	 * @param landschap    Het type landschap van het vakje.
	 * @param aantalKronen Het aantal kronen op het vakje, wat de waarde verhoogt.
	 * @param x            De x-coördinaat van het vakje op het spelbord.
	 * @param y            De y-coördinaat van het vakje op het spelbord.
	 */
	// Correcte constructor x and y
	public Vakje(Landschap landschap, int aantalKronen, int x, int y) {
		this.landschap = landschap;
		setAantalKronen(aantalKronen);
		this.x = x;
		this.y = y;
	}

	/**
	 * Constructor voor een vakje met alleen een landschapstype.
	 *
	 * @param landschap Het type landschap van het vakje.
	 */
	public Vakje(Landschap landschap) {
		this.landschap = landschap;
	}

	/**
	 * Constructor voor een vakje met landschap en aantal kronen.
	 *
	 * @param landschap    Het type landschap van het vakje.
	 * @param aantalKronen Het aantal kronen op het vakje.
	 */
	public Vakje(Landschap landschap, int aantalKronen) {
		this.landschap = landschap;
		setAantalKronen(aantalKronen);
	}

	/**
	 * Constructor voor een vakje met aantal kronen en landschap.
	 *
	 * @param aantalKronen Het aantal kronen op het vakje.
	 * @param landschap    Het type landschap van het vakje.
	 */
	public Vakje(int aantalKronen, Landschap landschap) {
		this.landschap = landschap;
		setAantalKronen(aantalKronen);
	}

	/**
	 * Geeft de x-coördinaat van het vakje.
	 *
	 * @return De x-coördinaat.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Geeft de y-coördinaat van het vakje.
	 *
	 * @return De y-coördinaat.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Stelt de x-coördinaat van het vakje in.
	 *
	 * @param x De nieuwe x-coördinaat.
	 */
	public final void setX(int x) {
		this.x = x;
	}

	/**
	 * Stelt de y-coördinaat van het vakje in.
	 *
	 * @param y De nieuwe y-coördinaat.
	 */
	public final void setY(int y) {
		this.y = y;
	}

	/**
	 * Geeft het landschap van het vakje.
	 *
	 * @return Het type landschap.
	 */
	public Landschap getLandschap() {
		return landschap;
	}

	/**
	 * Geeft een stringrepresentatie van het vakje, met informatie over landschap,
	 * aantal kronen, en coördinaten.
	 *
	 * @return Een beschrijvende string van het vakje.
	 */
	@Override
	public String toString() {
		return "[Landschap=" + landschap + ", Kronen=" + aantalKronen + ", X=" + x + ", Y=" + y + "]";
	}

	/**
	 * Geeft het aantal kronen op het vakje.
	 *
	 * @return Het aantal kronen.
	 */
	public int getAantalKronen() {
		return aantalKronen;
	}

	/**
	 * Stelt het aantal kronen van het vakje in. Gooit een IllegalArgumentException
	 * als het aantal negatief is.
	 *
	 * @param aantalKronen Het nieuwe aantal kronen.
	 * @throws IllegalArgumentException als het aantal kronen negatief is.
	 */
	public void setAantalKronen(int aantalKronen) {
		// Basic validation for aantalKronen
		if (aantalKronen < 0) {
			throw new IllegalArgumentException("Aantal kronen kan niet negatief zijn.");
		}
		this.aantalKronen = aantalKronen;
	}
}
