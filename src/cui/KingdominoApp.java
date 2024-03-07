package cui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import domein.DomeinController;
import domein.Kleuren;
import domein.Speler;
import dto.SpelerDTO;

public class KingdominoApp {

	private final DomeinController dc;
	private Scanner sc;
	private List<Speler> spelers;
	private final Map<String, String> gekozenSpelersMetKleur;
	private final List<String> kleuren;

	public KingdominoApp(DomeinController dc) {
		sc = new Scanner(System.in);
		this.dc = dc;
		this.spelers = new ArrayList<>();
		gekozenSpelersMetKleur = new HashMap<>();
		this.kleuren = Kleuren.getKleurLijst();

	}

	public void start() {
		String[] menuKeuzes = { "Registreer nieuwe speler", "Start nieuwe spel", "Afsluiten" };
		int keuze = maakMenuKeuze(menuKeuzes, "Wat kies je? ");
		while (keuze != 3) {
			switch (keuze) {
			case 1 -> registreerSpeler();
			case 2 -> startSpel();

			}
			keuze = sc.nextInt();
		}
		System.out.printf("%nTot een volgende keer!");
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

	private void startSpel() {
		List<SpelerDTO> geregistreerdeSpelers = dc.geefOverzichtSpelers();

		if (geregistreerdeSpelers.size() < 3) {
			System.out.println("Er zijn niet genoeg geregistreerde spelers");
			System.out.println("Geregistreerde spelers:");
			toonOverzicht();
		} else {
			toonOverzicht();
			kiesSpeler();
			startKingdomino();
		}
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

		System.out.println("U bent succesvol geregistreerd");

		start();
	}

	private void toonOverzicht() {
		List<SpelerDTO> overzicht = dc.geefOverzichtSpelers();

		if (overzicht.isEmpty())
			System.out.printf("collectie is leeg%n%n");
		else
			for (SpelerDTO dto : overzicht)
				System.out.printf("Speler %s van %d %n", dto.gebruikersnaam(), dto.geboortejaar());
	}

	private void kiesSpeler() {
		// Controleer of het maximaal aantal spelers al is bereikt
		if (gekozenSpelersMetKleur.size() >= 4) {
			System.out.println("Maximaal aantal spelers is al bereikt.");
			return;
		}

		List<SpelerDTO> beschikbareSpelers = dc.geefOverzichtSpelers();
		System.out.println("Beschikbare spelers:");
		for (int i = 0; i < beschikbareSpelers.size(); i++) {
			System.out.printf("%d. %s (%d)%n", i + 1, beschikbareSpelers.get(i).gebruikersnaam(),
					beschikbareSpelers.get(i).geboortejaar());
		}

		int gekozenSpelerIndex;
		do {
			System.out.print("Kies een speler (nummer): ");
			gekozenSpelerIndex = sc.nextInt() - 1;
		} while (gekozenSpelerIndex < 0 || gekozenSpelerIndex >= beschikbareSpelers.size());

		String gekozenSpelerGebruikersnaam = beschikbareSpelers.get(gekozenSpelerIndex).gebruikersnaam();

		// Controleer of de speler al is gekozen
		if (gekozenSpelersMetKleur.containsKey(gekozenSpelerGebruikersnaam)) {
			System.out.println("Deze speler is al gekozen. Kies een andere speler.");
			kiesSpeler(); // Vraag de gebruiker om een andere speler te kiezen
			return;
		}

		String gekozenKleur = "";
		// sc.nextLine(); // Consumeer de newline
		do {
			System.out.print("Kies een kleur: ");
			System.out.println(kleuren);

			gekozenKleur = sc.next();
			sc.nextLine();
		} while (!kleuren.contains(gekozenKleur.toUpperCase()));

		// Voeg de speler toe aan de lijst van gekozen spelers met de opgegeven kleur
		gekozenSpelersMetKleur.put(gekozenSpelerGebruikersnaam, gekozenKleur);

		System.out.println("Speler " + gekozenSpelerGebruikersnaam + " toegevoegd met kleur " + gekozenKleur);

		do {
			System.out.println("Wil je nog een speler toevoegen? (ja/nee):\nMinstens 3 spelers");
			String keuze = sc.nextLine();
			if (keuze.equalsIgnoreCase("ja")) {
				kiesSpeler(); // Blijf spelers toevoegen indien gewenst
			}
		} while (gekozenSpelersMetKleur.size() < 3);

	}

	private void startKingdomino() {
		int aantalSpelers = gekozenSpelersMetKleur.size();

		int aantalDominotegels = (aantalSpelers == 3) ? 36 : 48;

		System.out.println("Het spel heeft " + aantalDominotegels + " dominotegels.");

		// Toon overzicht per speler
		for (Map.Entry<String, String> entry : gekozenSpelersMetKleur.entrySet()) {
			String speler = entry.getKey();
			String kleur = entry.getValue();

			System.out.println(
					"Speler: " + speler + ", Kasteel: " + kleur + ", Starttegel: " + kleur + ", Koning: " + kleur);
		}
	}

}
