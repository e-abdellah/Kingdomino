package domein;

import java.util.Random;

public enum Landschap {
	BOS, GRASLAND, WATER, WOESTIJN, STEENGROEVE;

	// Methode om een willekeurig landschap te krijgen
	public static Landschap getRandomLandschap() {
		// Maak een array van alle landschappen
		Landschap[] landschappen = Landschap.values();

		// Kies een willekeurig landschap
		Random random = new Random();
		int index = random.nextInt(landschappen.length);
		return landschappen[index];
	}
}
