package domein;

public class Vakje {
	private Landschap landschap;
	private int aantalKronen;

	public Vakje() {
	}

	public Vakje(Landschap landschap) {
		this.landschap = landschap;
	}

	public Vakje(Landschap landschap, int aantalKronen) {
		this.landschap = landschap;
		this.aantalKronen = aantalKronen;
	}

	public Landschap getLandschap() {
		return landschap;
	}

	@Override
	public String toString() {
		return "[Landschap=" + landschap + "]";
	}

	public int getAantalKronen() {
		return aantalKronen;
	}

	public void setAantalKronen(int aantalKronen) {
		this.aantalKronen = aantalKronen;
	}

}