package domein;

import java.util.Random;

public class Vakje {
	private Landschap landschap;

	public Vakje(Landschap landschap) {
		this.landschap = getRandomLandschap();
	}

	public Landschap getLandschap() {
		return landschap;
	}

	private Landschap getRandomLandschap() {
		// Krijg een array van alle waarden in de enum Landschap
		Landschap[] landschappen = Landschap.values();

		// Krijg de lengte van de enum
		int numLandschappen = landschappen.length;

		// Genereer een willekeurig getal tussen 0 (inclusief) en het aantal
		// landschappen (exclusief)
		Random random = new Random();
		int randomIndex = random.nextInt(numLandschappen);

		// Geef het willekeurig geselecteerde landschap terug
		return landschappen[randomIndex];
	}
}
