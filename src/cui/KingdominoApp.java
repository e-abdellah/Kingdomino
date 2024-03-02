package cui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import domein.DomeinController;
import domein.Spel;
import domein.Speler;

public class KingdominoApp {

	private final DomeinController dc;
	private Scanner sc;

	public KingdominoApp(DomeinController dc) {
		sc = new Scanner(System.in);
		this.dc = dc;
	}

	public void startSpel() {
		System.out.println("Welkom bij het spel!");

		int aantalSpelers = vraagAantalSpelers();
		List<Speler> spelers = new ArrayList<>();

		for (int i = 0; i < aantalSpelers; i++) {
			voegSpelerToe(); // Speler toevoegen aan de lijst
		}

		Spel spel = new Spel(spelers);
		// spel.initialiseerSpel();

		// Nu kan het spel worden gestart
	}

	private int vraagAantalSpelers() {
		int aantalSpelers;
		do {
			System.out.println("Hoeveel spelers willen spelen? (3 of 4)");
			aantalSpelers = sc.nextInt();
		} while (aantalSpelers != 3 && aantalSpelers != 4);
		return aantalSpelers;
	}

	private void voegSpelerToe() {
		sc.nextLine(); // Consumeer de newline
		System.out.print("Geef de spelersnaam: ");
		String naam = sc.nextLine(); // Lees de naam van de speler

		System.out.print("Geef de geboortejaar: ");
		int geboortejaar = sc.nextInt(); // Lees het geboortejaar

		dc.registreerSpeler(naam, geboortejaar); // Registreer de speler
	}

}
