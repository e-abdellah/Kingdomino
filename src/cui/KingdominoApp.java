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
	private List<Speler> spelers;

	public KingdominoApp(DomeinController dc) {
		sc = new Scanner(System.in);
		this.dc = dc;
		this.spelers = new ArrayList<>();
	}

	public void startSpel() {
		System.out.println("Welkom bij KingDomino!");

		int aantalSpelers = vraagAantalSpelers();
		for (int i = 0; i < aantalSpelers; i++) {
			voegSpelerToe(); // Speler toevoegen aan de lijst
		}

		if (spelers.size() < 3 || spelers.size() > 4) {
			System.out.println("Het aantal spelers moet minstens 3 spelers en maximum 4 spelers bevatten");
			return; // We kunnen het spel niet starten, stop hier
		}

		Spel spel = new Spel(spelers); // Maak het Spel-object alleen als het juiste aantal spelers is toegevoegd
		// spel.initialiseerSpel();

		// Nu kan het spel worden gestart
	}

	private int vraagAantalSpelers() {
		int aantalSpelers;
		do {
			System.out.println("Hoeveel spelers willen er spelen? (3 of 4)");
			aantalSpelers = sc.nextInt();
		} while (aantalSpelers != 3 && aantalSpelers != 4);
		return aantalSpelers;
	}

	private void voegSpelerToe() {
		if (spelers.size() >= 4) {
			System.out.println("Maximaal aantal spelers bereikt!");
			return;
		}

		sc.nextLine(); // Consumeer de newline
		System.out.print("Geef de spelersnaam: ");
		String naam = sc.nextLine(); // Lees de naam van de speler

		System.out.print("Geef het geboortejaar: ");
		int geboortejaar = sc.nextInt(); // Lees het geboortejaar

		dc.registreerSpeler(naam, geboortejaar); // Registreer de speler
	}

}
