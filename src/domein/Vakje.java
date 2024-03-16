package domein;

public class Vakje {
	private Landschap landschap;

	public Vakje() {
	}

	public Vakje(Landschap landschap) {
		this.landschap = landschap;
	}

	public Landschap getLandschap() {
		return landschap;
	}

	@Override
	public String toString() {
		return "[Landschap=" + landschap + "]";
	}

}
