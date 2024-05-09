package domein;

public class Vakje {
	private Landschap landschap;
	private int aantalKronen;

	private int x;
	private int y;

	public Vakje() {
	}

	// Correcte constructor x and y
	public Vakje(Landschap landschap, int aantalKronen, int x, int y) {
		this.landschap = landschap;
		setAantalKronen(aantalKronen);
		this.x = x;
		this.y = y;
	}

	public Vakje(Landschap landschap) {
		this.landschap = landschap;
	}

	public Vakje(Landschap landschap, int aantalKronen) {
		this.landschap = landschap;
		setAantalKronen(aantalKronen);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public final void setX(int x) {
		this.x = x;
	}

	public final void setY(int y) {
		this.y = y;
	}

	public Landschap getLandschap() {
		return landschap;
	}

	@Override
	public String toString() {
		return "[Landschap=" + landschap + ", Kronen=" + aantalKronen + ", X=" + x + ", Y=" + y + "]";
	}

	public int getAantalKronen() {
		return aantalKronen;
	}

	public void setAantalKronen(int aantalKronen) {
		// Basic validation for aantalKronen
		if (aantalKronen < 0) {
			throw new IllegalArgumentException("Aantal kronen kan niet negatief zijn.");
		}
		this.aantalKronen = aantalKronen;
	}
}
