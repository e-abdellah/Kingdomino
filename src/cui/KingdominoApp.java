package cui;

import java.util.*;

import domein.DomeinController;
import domein.Dominotegel;
import domein.Spel;
import domein.Speler;
import dto.SpelerDTO;

public class KingdominoApp {

	private final DomeinController dc;
	private Scanner sc;
	private List<SpelerDTO> gekozenSpelers;
	private List<String> kleuren;
	private List<SpelerDTO> beschikbareSpelers;
	private Map<SpelerDTO, String> spelerKleurMap;
	private Spel spel = new Spel();

	public KingdominoApp(DomeinController dc) {
		sc = new Scanner(System.in);
		this.dc = dc;
		gekozenSpelers = new ArrayList<>();
		beschikbareSpelers = dc.geefOverzichtSpelers();
		spelerKleurMap = new HashMap<>();
		kleuren = dc.geefAlleKleuren();
	}

	public void start() {
		String[] menuKeuzes = { "Registreer nieuwe speler", "Start nieuw spel", "Afsluiten" };
		int keuze = maakMenuKeuze(menuKeuzes, "Kies één van de volgende opties: ");
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
		// Blijf in de lus totdat een geldige keuze is gemaakt
		while (true) {
			try {
				// Toon het menu
				System.out.printf("%n%s%n", hoofding);
				for (int i = 0; i < keuzes.length; i++) {
					System.out.printf("%d. %s%n", i + 1, keuzes[i]);
				}
				System.out.print("Jouw keuze: ");

				// Controleer of de invoer een integer is
				if (sc.hasNextInt()) {
					keuze = sc.nextInt();

					// Controleer of de keuze binnen het geldige bereik ligt
					if (keuze >= 1 && keuze <= keuzes.length) {
						break; // Geldige keuze, exit de lus
					} else {
						System.out.println("Ongeldige keuze. Kies een nummer tussen 1 en " + keuzes.length);
					}
				} else {
					// Ongeldige invoer (geen integer)
					System.out.println("Ongeldige invoer. Voer een nummer in.");
					sc.next(); // Consumeer de ongeldige invoer om oneindige lussen te voorkomen
				}
			} catch (Exception e) {
				// Vang eventuele uitzonderingen op en geef een foutmelding weer
				System.out.println("Er is een fout opgetreden. Probeer opnieuw.");
				sc.nextLine(); // Consumeer de newline om de scanner te resetten
			}
		}

		return keuze;
	}

	private void startSpel() {
		List<SpelerDTO> geregistreerdeSpelers = dc.geefOverzichtSpelers();

		if (geregistreerdeSpelers.size() < 3) {
			toonOverzicht();
			System.out.printf("%nEr zijn niet genoeg geregistreerde spelers om het spel te kunnen starten!%n");
			start();
	
		} else {
			//toonOverzicht();
			kiesSpeler();
			startKingdomino();
		}
	}

	/*private int vraagAantalSpelers() {
		int aantalSpelers;
		do {
			System.out.println("Hoeveel spelers willen er spelen? (3 of 4)");
			aantalSpelers = sc.nextInt();
		} while (aantalSpelers != 3 && aantalSpelers != 4);
		return aantalSpelers;
	}*/

	private void registreerSpeler() {

		try {

			sc.nextLine(); // Consumeer de newline
			System.out.print("Voer de naam van de speler in: (de spelersnaam moet minstens 6 karakters bevatten & mag niet enkel uit spaties bestaan): ");
			String naam = sc.nextLine(); // Lees de naam van de speler

			System.out.print("Voer het geboortejaar van de speler in: (de speler moet minstens 6 jaar oud zijn): ");
			int geboortejaar = sc.nextInt(); // Lees het geboortejaar

			dc.registreerSpeler(naam, geboortejaar); // Registreer de speler

			System.out.printf("%nSpeler %s werd succesvol geregistreerd!%n", naam);
		} catch (Exception e) {
			System.out.println("Er is een fout opgetreden bij het registreren van de speler. Probeer opnieuw.");
			sc.nextLine(); // Consumeer de newline om de scanner te resetten
		}

		start();
	}

	private void toonOverzicht() {
		List<SpelerDTO> beschikbaar = dc.geefOverzichtSpelers();
		System.out.println("Beschikbare spelers:");
		for (int i = 0; i < beschikbaar.size(); i++) {
			System.out.printf("%d. %s (%d)%n", i + 1, beschikbaar.get(i).gebruikersnaam(),
					beschikbaar.get(i).geboortejaar());
		}
	}

//	// Methode om de resterende spelers af te drukken
//	public void drukResterendeSpelersAf(List<SpelerDTO> beschikbareSpelers, int gekozenSpelerIndex) {
//		System.out.println("Resterende spelers:");
//		for (int i = 0; i < beschikbareSpelers.size(); i++) {
//			if (i != gekozenSpelerIndex) {
//				System.out.printf("%d. %s (%d)%n", i + 1, beschikbareSpelers.get(i).gebruikersnaam(),
//						beschikbareSpelers.get(i).geboortejaar());
//			}
//		}
//	}

	private void kiesSpeler() {
		// Controleer of het maximaal aantal spelers al is bereikt
		if (gekozenSpelers.size() >= 4) {
			System.out.println("Maximaal aantal spelers is al bereikt.");
			return;
		}

		System.out.printf("%nBeschikbare spelers:%n");
		for (int i = 0; i < beschikbareSpelers.size(); i++) {
			System.out.printf("%d. %s (%d)%n", i + 1, beschikbareSpelers.get(i).gebruikersnaam(),
					beschikbareSpelers.get(i).geboortejaar());
		}

		int gekozenSpelerIndex;
		do {
			try {
				System.out.print("Kies een speler (nummer): ");
				gekozenSpelerIndex = sc.nextInt() - 1;
			} catch (InputMismatchException e) {
				System.out.println("Ongeldige invoer. Voer een geldig nummer in.");
				sc.nextLine(); // Consumeer de ongeldige invoer
				gekozenSpelerIndex = -1; // Zet de index op een ongeldige waarde om de lus opnieuw te laten lopen
			}
		} while (gekozenSpelerIndex < 0 || gekozenSpelerIndex >= beschikbareSpelers.size());

		SpelerDTO gekozenSpeler = beschikbareSpelers.get(gekozenSpelerIndex);

		// Controleer of de speler al is gekozen
		if (gekozenSpelers.contains(gekozenSpeler)) {
			System.out.println("Deze speler is al gekozen. Kies een andere speler.");
			kiesSpeler(); // Vraag de gebruiker om een andere speler te kiezen
			return;
		}

		// Voeg de gekozen speler toe aan de lijst en verwijder deze uit de lijst van
		// beschikbare spelers
		gekozenSpelers.add(gekozenSpeler);
		spel.getAantalSpelers().add(gekozenSpeler);

		String gekozenKleur;
		do {
			System.out.print("Kies een kleur: ");
			for (String kleur : kleuren) {
				System.out.print(kleur + " ");
			}
			System.out.println();

			gekozenKleur = sc.next().toUpperCase();

			if (!kleuren.contains(gekozenKleur)) {
				System.out.println("Ongeldige kleur, kies een andere");
			}

		} while (!kleuren.contains(gekozenKleur));

		spelerKleurMap.put(gekozenSpeler, gekozenKleur);
		beschikbareSpelers.remove(gekozenSpeler);
		kleuren.remove(gekozenKleur.toUpperCase());
		System.out.println("Speler " + gekozenSpeler.gebruikersnaam() + " werd toegevoegd aan het spel met kleur " + gekozenKleur);

		do {
			System.out.println("Wil je nog een speler toevoegen? (ja/nee):\nMinstens 3 spelers");
			String keuze = sc.next();
			if (keuze.equalsIgnoreCase("ja")) {
//					drukResterendeSpelersAf(beschikbareSpelers, gekozenSpelerIndex);
				kiesSpeler(); // Blijf spelers toevoegen indien gewenst
			}
			if (keuze.equalsIgnoreCase("nee"))
				if (gekozenSpelers.size() < 3) {
					System.out.printf("%nJe moet minstens 3 spelers kiezen om het spel te kunnen starten.%n");
					kiesSpeler();
				} else {
					break;
				}

		} while (gekozenSpelers.size() < 3);

	}

	private void startKingdomino() {
		int aantalSpelers = gekozenSpelers.size();
		int aantalDominotegels = (aantalSpelers == 3) ? 36 : 48;

		System.out.println("Het spel heeft " + aantalDominotegels + " dominotegels.");

		// Toon overzicht per speler inclusief hun gekozen kleur
		for (SpelerDTO spelerDTO : gekozenSpelers) {
			String gekozenKleur = spelerKleurMap.get(spelerDTO);
			System.out.println("Speler: " + spelerDTO.gebruikersnaam() + ", gekozen kleur: " + gekozenKleur);
		}

		// Print geschudde dominotegels
		List<Dominotegel> geschuddeDominotegels = dc.schudDominotegels(aantalSpelers); // Aangepast om aantalSpelers// door te geven

		List<Dominotegel> startKolom = new ArrayList<>();
		for (int i = 0; i < aantalSpelers; i++){
			startKolom.add(geschuddeDominotegels.get(0));
			geschuddeDominotegels.remove(0);
		}
		startKolom.sort(Comparator.comparingInt(Dominotegel::getGetal));

		for (Dominotegel tegel : startKolom) {
			System.out.println(tegel);
		}
		/*for (Dominotegel tegel : geschuddeDominotegels) {
			System.out.println(tegel); // Aanroepen van toString() methode van Dominotegel
		}*/

		Collections.shuffle(gekozenSpelers);//random volgorde van spelers genereren

		List<Integer> tegels = new ArrayList<>();
		for (int i = 1; i <= aantalSpelers; i++) {
			tegels.add(i);
		}
		int i = 0;
		Map<Dominotegel, SpelerDTO> TegelSpeler = new HashMap<>();
		do {
			//Toont de huidige speler met zijn corresponderende kleur nadat ze geshuffled werden
			SpelerDTO currentSpeler = gekozenSpelers.get(i);
			String spelerNaam = currentSpeler.gebruikersnaam();
			String spelerKleur = spelerKleurMap.get(currentSpeler);
			System.out.printf("%nSpeler %s met kleur %s, welke tegel kies je? ", spelerNaam, spelerKleur);

			int keuze;
			while (true) {
				while (!sc.hasNextInt()) {
					sc.next();
					System.out.printf("Fout antwoord, kies een getal tussen 1 en %d dat vrij is:%n", aantalSpelers);
				}
				keuze = sc.nextInt();
				if (keuze >= 1 && keuze <= aantalSpelers && tegels.contains(keuze)) {
					break;
				} else {
					System.out.printf("Fout antwoord, kies een getal tussen 1 en %d dat vrij is:%n", gekozenSpelers.size());
				}
			}
			TegelSpeler.put(startKolom.get(keuze-1), gekozenSpelers.get(i));
			i++;
			Integer keuzeVerwijderen = keuze;
			tegels.remove(keuzeVerwijderen);
		}while(i < aantalSpelers);

		for (SpelerDTO spelerDTO : gekozenSpelers) {
			String gekozenKleur = spelerKleurMap.get(spelerDTO);
			System.out.println("Speler: " + spelerDTO.gebruikersnaam() + ", gekozen kleur: " + gekozenKleur);
			System.out.println("Nog te implementeren (koninkrijk is leeg atm)");
		}
		for (Dominotegel tegel : startKolom) {
			System.out.printf("%s %s%n",tegel, spelerKleurMap.get(TegelSpeler.get(tegel)));
		}
	}

}
