package cui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;
import domein.Dominotegel;
import domein.Spel;
import domein.Speler;
import dto.SpelerDTO;
import dto.SpelDTO;

public class KingdominoApp {

	private final DomeinController dc;
	private Scanner sc;
	private List<SpelerDTO> gekozenSpelers;
	private List<String> kleuren;
	private List<SpelerDTO> beschikbareSpelers;
	private Map<SpelerDTO, String> spelerKleurMap;
	ResourceBundle messages = null;
	private SpelDTO spelDTO;
	private List<Dominotegel> dominotegels;


	public KingdominoApp(DomeinController dc) {
		sc = new Scanner(System.in);
		this.dc = dc;
		gekozenSpelers = new ArrayList<>();
		beschikbareSpelers = dc.geefOverzichtSpelers();
		spelerKleurMap = new HashMap<>();
		kleuren = DomeinController.geefAlleKleuren();
		kiesTaal();
	}

	public void start() {

		String[] menuKeuzes = {messages.getString("registreerSpeler"), messages.getString("startNieuwSpel"),
				messages.getString("afsluiten")};

		int keuze = maakMenuKeuze(menuKeuzes, messages.getString("kiesOptie"));
		while (keuze != 6) {
			switch (keuze) {
				case 1 -> registreerSpeler();
				case 2 -> startSpel();

			}
			keuze = sc.nextInt();
		}
		System.out.printf("%s", messages.getString("afsluitenBericht"));

		Dominotegel tegel = new Dominotegel();
		for (Dominotegel tegel1 : dominotegels) {
			tegel1.genereerFotoPaden();
			System.out.println("Voorkant foto pad: " + tegel1.getVoorkantFotoPad());
			System.out.println("Achterkant foto pad: " + tegel1.getAchterkantFotoPad());
		}
	}

	private void speelSpel() {
	}

	private void speelRonde() {
	}

	private void speelBeurt() {
	}

	// kiesTaal methode om eenmalig een taal te kunnen kiezen bij het opstarten van
	// het spel
	private void kiesTaal() {

		boolean geldigeTaalKeuze = false;
		Locale locale = null;
		while (!geldigeTaalKeuze) {
			// Taal keuze
			System.out.println("Kies uw taal // Choose your language // Choisissez votre langue: ");
			System.out.println("1. English");
			System.out.println("2. Nederlands");
			System.out.println("3. Français");

			try {
				int taalKeuze = sc.nextInt();
				sc.nextLine();

				// Switch om de taalkeuze te kunnen invoeren, na invoer wordt locale ingesteld
				switch (taalKeuze) {
					case 1 -> {
						locale = new Locale("en", "EN");
						geldigeTaalKeuze = true;
					}
					case 2 -> {
						locale = new Locale("nl", "NL");
						geldigeTaalKeuze = true;
					}
					case 3 -> {
						locale = new Locale("fr", "FR");
						geldigeTaalKeuze = true;
					}
					default -> System.out.println(
							"Entrée incorrecte. Veuillez choisir 1 ou 2 pour choisir votre langue.// Foutieve invoer, Voer 1 of 2 in om uw taal te kiezen. // Wrong input, Choose 1 or 2 to choose your language.");
				}
				// InputMismatchException om ervoor te zorgen dat enkel de cijfers die bij een
				// taal horen ingevoerd kunnen worden
			} catch (InputMismatchException e) {
				System.out.println(
						"Entrée incorrecte. Veuillez choisir 1 ou 2 pour choisir votre langue.// Foutieve invoer, Voer 1 of 2 in om uw taal te kiezen. // Wrong input, Choose 1 or 2 to choose your language.");
				sc.next();
			}
		}

		try {
			// Past het pad aan op basis van de gekozen taal zodat de juiste resource bundle
			// ingeladen kan worden
			messages = ResourceBundle.getBundle("cui.resource_bundle", locale);
		} catch (MissingResourceException e) {
			System.err.println("Error loading resource bundle: " + e.getMessage());
		}
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
				System.out.printf("%s ", messages.getString("jouwKeuzePrompt"));

				// Controleer of de invoer een integer is
				if (sc.hasNextInt()) {
					keuze = sc.nextInt();

					// Controleer of de keuze binnen het geldige bereik ligt
					if (keuze >= 1 && keuze <= keuzes.length) {
						break; // Geldige keuze, exit de lus
					} else {
						System.out.printf("%n%s %d%n ", messages.getString("ongeldigeMenuKeuze"), keuzes.length);
					}
				} else {
					// Ongeldige invoer (geen integer)
					System.out.printf("%n%s%n", messages.getString("ongeldigeMenuKeuzeGeenInteger"));
					sc.next(); // Consumeer de ongeldige invoer om oneindige lussen te voorkomen
				}
			} catch (Exception e) {
				// Vang eventuele uitzonderingen op en geef een foutmelding weer
				System.out.printf("%n%s%n", messages.getString("ongeldigeMenuKeuzeUitzonderingen"));
				sc.nextLine(); // Consumeer de newline om de scanner te resetten
			}
		}

		return keuze;
	}

	private void startSpel() {
		List<SpelerDTO> geregistreerdeSpelers = dc.geefOverzichtSpelers();

		if (geregistreerdeSpelers.size() < 3) {
			toonOverzicht();
			System.out.printf("%n%s%n", messages.getString("nietGenoegGeregistreerdeSpelers"));
			start();

		} else {
			// toonOverzicht();
			kiesSpeler();
			startKingdomino();
		}
	}

	private void registreerSpeler() {

		try {

			sc.nextLine(); // Consumeer de newline
			System.out.printf("%s ", messages.getString("voerNaamSpelerIn"));
			String naam = sc.nextLine(); // Lees de naam van de speler

			System.out.printf("%s ", messages.getString("voerGeboorteJaarSpelerIn"));
			int geboortejaar = sc.nextInt(); // Lees het geboortejaar

			dc.registreerSpeler(naam, geboortejaar); // Registreer de speler

			System.out.printf("%n%s %s%n", naam, messages.getString("spelerSuccesvolGeregistreerd"));
		} catch (Exception e) {
			System.out.printf("%n%s", messages.getString("spelerOnsuccesvolGeregistreerd"));
			sc.nextLine(); // Consumeer de newline om de scanner te resetten
		}

		start();
	}

	private void toonOverzicht() {
		List<SpelerDTO> beschikbaar = dc.geefOverzichtSpelers();
		System.out.printf("%n%s%n", messages.getString("toonOverzichtBeschikbareSpelers"));
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
			System.out.printf("%s", messages.getString("maximaalAantalSpelersBereikt"));
			return;
		}

		System.out.printf("%n%s%n", messages.getString("toonOverzichtBeschikbareSpelers"));
		for (int i = 0; i < beschikbareSpelers.size(); i++) {
			System.out.printf("%d. %s (%d)%n", i + 1, beschikbareSpelers.get(i).gebruikersnaam(),
					beschikbareSpelers.get(i).geboortejaar());
		}

		int gekozenSpelerIndex;
		do {
			try {
				System.out.printf("%s", messages.getString("kiesSpelerViaNummer"));
				gekozenSpelerIndex = sc.nextInt() - 1;
			} catch (InputMismatchException e) {
				System.out.printf("%s%n", messages.getString("ongeldigeMenuKeuzeGeenInteger"));
				sc.nextLine(); // Consumeer de ongeldige invoer
				gekozenSpelerIndex = -1; // Zet de index op een ongeldige waarde om de lus opnieuw te laten lopen
			}
		} while (gekozenSpelerIndex < 0 || gekozenSpelerIndex >= beschikbareSpelers.size());

		SpelerDTO gekozenSpeler = beschikbareSpelers.get(gekozenSpelerIndex);

		// Controleer of de speler al is gekozen
		if (gekozenSpelers.contains(gekozenSpeler)) {
			System.out.printf("%s%n", messages.getString("spelerAlGekozen"));
			kiesSpeler(); // Vraag de gebruiker om een andere speler te kiezen
			return;
		}

		// Voeg de gekozen speler toe aan de lijst en verwijder deze uit de lijst van
		// beschikbare spelers
		gekozenSpelers.add(gekozenSpeler);
		dominotegels = dc.schudDominotegels(gekozenSpelers.size());
		spelDTO = dc.geefSpelDTO();

		String gekozenKleur;
		do {
			System.out.printf("%s ", messages.getString("kiesKleurSpeler"));
			for (String kleur : kleuren) {
				System.out.print(kleur + " ");
			}
			System.out.println();

			gekozenKleur = sc.next().toUpperCase();

			if (!kleuren.contains(gekozenKleur)) {
				System.out.printf("%s%n", messages.getString("kiesKleurSpelerOngeldig"));
			}

		} while (!kleuren.contains(gekozenKleur));

		spelerKleurMap.put(gekozenSpeler, gekozenKleur);
		beschikbareSpelers.remove(gekozenSpeler);
		kleuren.remove(gekozenKleur.toUpperCase());
		System.out.printf((messages.getString("spelerToegevoegd")) + "%n", gekozenSpeler.gebruikersnaam(), gekozenKleur);

		do {
			System.out.printf("%s%n", messages.getString("nogEenSpelerToevoegenVraag"));
			String keuze = sc.next();
			// Checkt of het antwoord positief ("ja" of "yes" of negatief "nee" of "no" is,
			// dit hangt af van de gekozen taal
			if (isPositiefAntwoord(keuze)) {
				kiesSpeler(); // Blijf spelers toevoegen indien gewenst
			} else if (isNegatiefAntwoord(keuze)) {
				if (gekozenSpelers.size() < 3) {
					System.out.printf("%n%s%n", messages.getString("nogNietGenoegSpelersOmSpelTeStarten"));
					kiesSpeler();
				} else {
					break;
				}
			} else {
				System.out.printf("%s%n", messages.getString("ongeldigeInvoer")); // Zorg dat je een bericht hebt voor
				// ongeldige invoer
			}
		} while (gekozenSpelers.size() < 3);

	}

	// Methodes om ervoor te zorgen dat je ja of nee kan antwoorden in de gekozen
	// taal (NL of EN)
	private boolean isPositiefAntwoord(String input) {
		String positief = messages.getString("ja").trim().toLowerCase();
		return input.trim().toLowerCase().equals(positief);
	}

	private boolean isNegatiefAntwoord(String input) {
		String negatief = messages.getString("nee").trim().toLowerCase();
		return input.trim().toLowerCase().equals(negatief);
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
		int indexKolom = 0;
		List<Dominotegel> startKolom = spelDTO.kolommen().get(indexKolom++);
		for (Dominotegel tegel : startKolom) {
			System.out.println(tegel);
		}
		/*
		 * for (Dominotegel tegel: geschuddeDominotegels) { System.out.println(tegel);
		 * // Aanroepen van toString() methode van Dominotegel }
		 */

		Collections.shuffle(gekozenSpelers);// random volgorde van spelers genereren

		List<Integer> tegels = new ArrayList<>();
		for (int i = 1; i <= aantalSpelers; i++) {
			tegels.add(i);
		}
		int i = 0;
		Map<Dominotegel, SpelerDTO> tegelSpeler = new HashMap<>();
		do {
			// Toont de huidige speler met zijn corresponderende kleur nadat ze geshuffled
			// werden
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
					System.out.printf("Fout antwoord, kies een getal tussen 1 en %d dat vrij is:%n",
							gekozenSpelers.size());
				}
			}
			tegelSpeler.put(startKolom.get(keuze - 1), gekozenSpelers.get(i));
			i++;
			Integer keuzeVerwijderen = keuze;
			tegels.remove(keuzeVerwijderen);
		} while (i < aantalSpelers);

		for (SpelerDTO spelerDTO : gekozenSpelers) {
			String gekozenKleur = spelerKleurMap.get(spelerDTO);
			System.out.println("Speler: " + spelerDTO.gebruikersnaam() + ", gekozen kleur: " + gekozenKleur);
			System.out.println("Nog te implementeren (koninkrijk is leeg atm)");
		}
		for (Dominotegel tegel : startKolom) {
			System.out.printf("%s %s%n", tegel, spelerKleurMap.get(tegelSpeler.get(tegel)));
		}

		if (spelDTO.eindeSpel()) {
			dc.berekenWinnaars();
			for (SpelerDTO speler : gekozenSpelers) {
				if (speler.isWinnaar()) {
					System.out.printf("%s met %d spelletjes gewonnen en %d spelletjes gespeeld",
							speler.gebruikersnaam(), speler.aantalGewonnen(), speler.aantalGespeeld());
				}
			}
		}

			/*kijk wie gelijk is met winnaar
			System.out.println("Winnaar(s):");
			boolean nietWinnaarGevonden = false;
			for (Speler speler : spelDTO.spelers()) {
				List<Integer> scores = spelerScores.get(speler);
				String scoreDetails = String.format(" - Score: %d, Gebied: %d, Kronen: %d", scores.get(0),
						scores.get(1), scores.get(2));
				if (scores.get(0).equals(topScores.get(0)) && scores.get(1).equals(topScores.get(1))
						&& scores.get(2).equals(topScores.get(2)) && !nietWinnaarGevonden) {
					System.out.printf("Speler %s met een %s%n", speler.getGebruikersnaam(), scoreDetails);

					// Now check for ties //het zou beter werken als we Speler ipv SpelerDTO gebruiken
					System.out.println("Winnaar(s):");
					boolean foundNonWinner = false;
					for (Speler speler1 : spelDTO.spelers()) {
						List<Integer> score = spelerScores.get(speler1);
						String scoreDetail = String.format(" - Score: %d, Gebied: %d, Kronen: %d", scores.get(0),
								scores.get(1), scores.get(2));
						if (scores.get(0).equals(topScores.get(0)) && scores.get(1).equals(topScores.get(1))
								&& scores.get(2).equals(topScores.get(2)) && !foundNonWinner) {
							System.out.printf("Speler %s met een %s", speler.getGebruikersnaam(),
									scoreDetails); // This player is a winner
						} else {
							boolean nietWinnaarGevonden = false;
							if (!nietWinnaarGevonden) {
								System.out.println("Niet-winnaar(s):");
							}
							System.out.printf("Speler %s met %s%n", speler.getGebruikersnaam(), scoreDetails);
							System.out.printf("Speler %s met %s", speler.getGebruikersnaam(),
									scoreDetails);
						}
					}*/
	}
}




