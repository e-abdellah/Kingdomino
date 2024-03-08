package domein;

public enum Landschap {
	BOS("Bos"), GRASLAND("Grasland"), WATER("Water"), WOESTIJN("Woestijn"), STEENGROEVE("Steengroeve");

	private final String naam;

	Landschap(String naam) {
		this.naam = naam;
	}

	public String getNaam() {
		return naam;
	}
}
