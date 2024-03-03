package cui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import domein.DomeinController;
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

	public void start() {
		String[] menuKeuzes = { "Registreer nieuwe speler", "Start nieuwe spel", "Afsluiten" };
		int keuze = maakMenuKeuze(menuKeuzes, "Wat kies je? ");
		while (keuze != 3) {
			switch (keuze) {
			case 1 -> registreerSpeler();
			case 2 -> startSpel();
//			case 3 -> Afsluiten();

			}
			keuze = sc.nextInt();
		}
		System.out.printf("%nTot een volgende keer!");
	}

	private void startSpel() {
		// TODO
	}

	private int maakMenuKeuze(String[] keuzes, String hoofding) {
		int keuze = 0;
		do {
			System.out.printf("%n%s%n", hoofding);
			for (int i = 0; i < keuzes.length; i++) {
				System.out.printf("%d. %s%n", i + 1, keuzes[i]);
			}
			System.out.print("Jouw keuze: ");
			keuze = sc.nextInt();
		} while (keuze < 1 || keuze > keuzes.length);
		return keuze;
	}

	private int vraagAantalSpelers() {
		int aantalSpelers;
		do {
			System.out.println("Hoeveel spelers willen er spelen? (3 of 4)");
			aantalSpelers = sc.nextInt();
		} while (aantalSpelers != 3 && aantalSpelers != 4);
		return aantalSpelers;
	}

	private void registreerSpeler() {

		sc.nextLine(); // Consumeer de newline
		System.out.print("Geef de spelersnaam: ");
		String naam = sc.nextLine(); // Lees de naam van de speler

		System.out.print("Geef het geboortejaar: ");
		int geboortejaar = sc.nextInt(); // Lees het geboortejaar

		dc.registreerSpeler(naam, geboortejaar); // Registreer de speler

		System.out.println("U bent succesvol geregistreert");
	}

}
