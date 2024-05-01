package cui;

import java.util.*;

import domein.DomeinController;
import domein.Vakje;
import dto.DominotegelDTO;
import dto.SpelerDTO;

public class KingdominoApp {

	private final DomeinController dc;
	private Scanner sc;
	private List<SpelerDTO> gekozenSpelers;
	private List<String> kleuren;
	private List<SpelerDTO> beschikbareSpelers;
	private Map<SpelerDTO, String> spelerKleurMap;
	ResourceBundle messages = null;
	private List<DominotegelDTO> dominotegels;
	private List<String> testkleuren;

	public KingdominoApp(DomeinController dc) {
		this.dc = dc;
		sc = new Scanner(System.in);
		gekozenSpelers = new ArrayList<>();
		beschikbareSpelers = dc.geefOverzichtSpelers();
		spelerKleurMap = new HashMap<>();
		kiesTaal();

	}

	public void start() {

		String[] menuKeuzes = { messages.getString("registreerSpeler"), messages.getString("startNieuwSpel"),
				messages.getString("afsluiten") };

		int keuze = maakMenuKeuze(menuKeuzes, messages.getString("kiesOptie"));
		while (keuze != 6) {
			switch (keuze) {
			case 1 -> registreerSpeler();
			case 2 -> startSpel();

			}
			keuze = sc.nextInt();
		}
		System.out.printf("%s", messages.getString("afsluitenBericht"));
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
			messages = ResourceBundle.getBundle("utils.resource_bundle", locale);
			kleuren = dc.geefKleurenInTaal(locale);
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
		// Haalt een lijst van SpelerDTO's op die de beschikbare spelers
		// vertegenwoordigen.
		List<SpelerDTO> beschikbaar = dc.geefOverzichtSpelers();

		// Gebruikt resource bundle
		System.out.printf("%n%s%n", messages.getString("toonOverzichtBeschikbareSpelers"));

		// Loopt door de lijst van beschikbare spelers en drukt informatie over elke
		// speler af.
		for (int i = 0; i < beschikbaar.size(); i++) {
			// Toont de gebruikersnaam en het geboortejaar van elke speler.
			System.out.printf("%d. %s (%d)%n", i + 1, beschikbaar.get(i).gebruikersnaam(),
					beschikbaar.get(i).geboortejaar());
		}
	}

	// // Methode om de resterende spelers af te drukken
	// public void drukResterendeSpelersAf(List<SpelerDTO> beschikbareSpelers, int
	// gekozenSpelerIndex) {
	// System.out.println("Resterende spelers:");
	// for (int i = 0; i < beschikbareSpelers.size(); i++) {
	// if (i != gekozenSpelerIndex) {
	// System.out.printf("%d. %s (%d)%n", i + 1,
	// beschikbareSpelers.get(i).gebruikersnaam(),
	// beschikbareSpelers.get(i).geboortejaar());
	// }
	// }
	// }

	private void kiesSpeler() {
		// Controleert of het maximum aantal spelers al is bereikt
		if (gekozenSpelers.size() >= 4) {
			System.out.printf("%s", messages.getString("maximaalAantalSpelersBereikt"));
			return;
		}

		// Toont een overzicht van beschikbare spelers
		System.out.printf("%n%s%n", messages.getString("toonOverzichtBeschikbareSpelers"));
		for (int i = 0; i < beschikbareSpelers.size(); i++) {
			System.out.printf("%d. %s (%d)%n", i + 1, beschikbareSpelers.get(i).gebruikersnaam(),
					beschikbareSpelers.get(i).geboortejaar());
		}

		// Laat de gebruiker een speler kiezen
		int gekozenSpelerIndex = -1;
		do {
			try {
				System.out.printf("%s", messages.getString("kiesSpelerViaNummer"));
				gekozenSpelerIndex = sc.nextInt() - 1; // Leest een index van de console
			} catch (InputMismatchException e) {
				System.out.printf("%s%n", messages.getString("ongeldigeMenuKeuzeGeenInteger"));
				sc.nextLine(); // Verwerkt incorrecte invoer
				gekozenSpelerIndex = -1;
			}
		} while (gekozenSpelerIndex < 0 || gekozenSpelerIndex >= beschikbareSpelers.size());

		// Verkrijgt de gekozen speler en controleert of deze al geselecteerd is
		SpelerDTO gekozenSpeler = beschikbareSpelers.get(gekozenSpelerIndex);
		if (gekozenSpelers.contains(gekozenSpeler)) {
			System.out.printf("%s%n", messages.getString("spelerAlGekozen"));
			kiesSpeler();
			return;
		}

		// Voegt de gekozen speler toe aan de lijst van geselecteerde spelers
		gekozenSpelers.add(gekozenSpeler);
		beschikbareSpelers.remove(gekozenSpeler);

		// Laat de gebruiker een kleur kiezen voor de gekozen speler
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

		// Bewaart de gekozen kleur voor de speler
		spelerKleurMap.put(gekozenSpeler, gekozenKleur);
		kleuren.remove(gekozenKleur);

		// Vraagt of de gebruiker nog een speler wil toevoegen
		System.out.printf((messages.getString("spelerToegevoegd")) + "%n", gekozenSpeler.gebruikersnaam(),
				gekozenKleur);
		do {
			System.out.printf("%s%n", messages.getString("nogEenSpelerToevoegenVraag"));
			String keuze = sc.next();
			if (isPositiefAntwoord(keuze)) {
				kiesSpeler();
			} else if (isNegatiefAntwoord(keuze)) {
				if (gekozenSpelers.size() < 3) {
					System.out.printf("%n%s%n", messages.getString("nogNietGenoegSpelersOmSpelTeStarten"));
					kiesSpeler();
				} else {
					break;
				}
			} else {
				System.out.printf("%s%n", messages.getString("ongeldigeInvoer"));
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
		dc.voegSpelersToe(gekozenSpelers);
		dominotegels = dc.geefDominotegels(aantalSpelers);

		System.out.println("Het spel heeft " + aantalDominotegels + " dominotegels.");

		// Toon overzicht per speler inclusief hun gekozen kleur
		for (SpelerDTO spelerDTO : gekozenSpelers) {
			String gekozenKleur = spelerKleurMap.get(spelerDTO);
			System.out.println("Speler: " + spelerDTO.gebruikersnaam() + ", gekozen kleur: " + gekozenKleur);
		}

		Collections.shuffle(gekozenSpelers);// random volgorde van spelers genereren
		List<DominotegelDTO> startKolom = new ArrayList<>();
		int beurtTeller = 1;
		Map<SpelerDTO, DominotegelDTO> spelerTegel = new HashMap<>();
		System.out.println();
		do {
			System.out.printf("Begin van ronde %d%n%n", beurtTeller++);
			if (startKolom.isEmpty()) {
				startKolom = dc.geefKolom();
				toonKolom(startKolom);
			}
			if (spelerTegel.isEmpty())
				spelerTegel = keuzeKolom(startKolom, aantalSpelers, gekozenSpelers);

			System.out.println("Startkolom:");
			toonKolomMetSpeler(startKolom, spelerTegel);

			// UC4
			System.out.println("Eindkolom:");
			List<DominotegelDTO> eindKolom = dc.geefKolom();
			toonKolom(eindKolom);

			// volgorde bepalen voor keuze tegel eindkolom
			List<SpelerDTO> volgorde = getSpelerDTOS(spelerTegel);

			Map<SpelerDTO, DominotegelDTO> eindSpelerTegel = keuzeKolom(startKolom, aantalSpelers, volgorde);
			// toonKolomMetSpeler(eindKolom, eindTegelSpeler);

			// UC5
			System.out.println(volgorde);
			for (SpelerDTO speler : volgorde) {
				int x = 0, y = 0;
				int y2 = 0, x2 = 0;
				String richting = "";
				boolean kan;
				System.out.printf("Speler %s met kleur %s leg je tegel%n", speler.gebruikersnaam(),
						spelerKleurMap.get(speler));
				System.out.println("Geef de y en x coördinaat van het linkse vakje:");
				do {
					try {
						do {
							y = sc.nextInt();
						} while (y < 0 || y > 4);
						do {
							x = sc.nextInt();
						} while (x < 0 || x > 4);
					} catch (InputMismatchException e) {
						System.out.println("Ongeldige locatie");
					}
					System.out.println("Richting:");
					List<Integer> pos = new ArrayList<>(List.of(y, x));
					do {
						richting = sc.next();
						switch (richting) {
						case "boven": // Up
							pos.set(1, pos.get(1) - 1);
							break;
						case "onder": // Down
							pos.set(1, pos.get(1) + 1);
							break;
						case "links": // Left
							pos.set(0, pos.get(0) - 1);
							break;
						case "rechts": // Right
							pos.set(0, pos.get(0) + 1);
							break;
						default:
							System.out.println("Onbekende richting"); // Unknown direction
							break;
						}
						// pos naar x2 en y2 en omzetten naar spel
					} while (!pos.equals(new ArrayList<>(List.of(y, x))));
					y2 = pos.get(1);
					x2 = pos.get(0);
					kan = dc.kanPlaatsen(spelerTegel.get(speler), y, x, y2, x2, speler);
					if (!kan) {
						System.out.println("Kies een vrije plaats binnen het speelveld");
					}
				} while (!kan);
				dc.plaatsTegel(spelerTegel.get(speler), y, x, y2, x2, speler);

			}
			startKolom = eindKolom;
			spelerTegel = eindSpelerTegel;
			toonKoninkrijk();
			dc.berekenEindeSpel();

		} while (!dc.isEindeSpel());
		dc.berekenWinnaars();
		for (SpelerDTO speler : gekozenSpelers) {
			if (speler.isWinnaar()) {
				System.out.printf("%s met %d spelletjes gewonnen en %d spelletjes gespeeld", speler.gebruikersnaam(),
						speler.aantalGewonnen(), speler.aantalGespeeld());
			}
		}

	}

	private List<SpelerDTO> getSpelerDTOS(Map<SpelerDTO, DominotegelDTO> spelerTegel) {
		// Maakt een nieuwe lijst aan vanuit de reeds gekozen spelers.
		List<SpelerDTO> volgorde = new ArrayList<>(gekozenSpelers);

		// Sorteert de lijst van spelers gebaseerd op de waarde van hun bijbehorende
		// dominotegel
		volgorde.sort(new Comparator<SpelerDTO>() {
			@Override
			public int compare(SpelerDTO speler1, SpelerDTO speler2) {
				DominotegelDTO tegel1 = spelerTegel.get(speler1); // Haalt de dominotegel op voor speler1.
				DominotegelDTO tegel2 = spelerTegel.get(speler2); // Haalt de dominotegel op voor speler2.

				// Vergelijkt de dominotegels op basis van hun getallen
				return Integer.compare(tegel1.getal(), tegel2.getal());
			}
		});

		return volgorde; // Retourneert de gesorteerde lijst van speler-DTO's.
	}

	private Map<SpelerDTO, DominotegelDTO> keuzeKolom(List<DominotegelDTO> startKolom, int aantalSpelers,
			List<SpelerDTO> volgordeSpelers) {
		// Lijst om bij te houden welke tegels nog beschikbaar zijn om gekozen te worden
		List<Integer> tegels = new ArrayList<>();
		for (int i = 1; i <= aantalSpelers; i++) {
			tegels.add(i);
		}

		int i = 0; // index voor de huidige speler in de volgorde
		Map<SpelerDTO, DominotegelDTO> tegelSpeler = new HashMap<>();

		do {
			// Haalt de huidige speler op en toont deze met de corresponderende kleur
			SpelerDTO currentSpeler = volgordeSpelers.get(i);
			String spelerNaam = currentSpeler.gebruikersnaam();
			String spelerKleur = spelerKleurMap.get(currentSpeler);
			System.out.printf("%nSpeler %s met kleur %s, welke tegel kies je? ", spelerNaam, spelerKleur);

			int keuze; // De keuze van de speler voor de tegel
			while (true) {
				// Validatie dat de input een integer is
				while (!sc.hasNextInt()) {
					sc.next(); // Verwerpt ongeldige input
					System.out.printf("Fout antwoord, kies een getal tussen 1 en %d dat vrij is:%n", aantalSpelers);
				}
				keuze = sc.nextInt(); // Leest de keuze van de speler
				// Controleert of de gekozen tegel geldig en beschikbaar is
				if (keuze >= 1 && keuze <= aantalSpelers && tegels.contains(keuze)) {
					break;
				} else {
					System.out.printf("Fout antwoord, kies een getal tussen 1 en %d dat vrij is:%n", aantalSpelers);
				}
			}
			// Koppelt de gekozen tegel aan de speler in de map
			tegelSpeler.put(volgordeSpelers.get(i), startKolom.get(keuze - 1));
			i++;
			Integer keuzeVerwijderen = keuze; // Bereidt voor om de gekozen tegel te verwijderen
			tegels.remove(keuzeVerwijderen); // Verwijdert de gekozen tegel uit de lijst van beschikbare tegels
		} while (i < aantalSpelers); // Herhaalt tot alle spelers hebben gekozen

		return tegelSpeler; // Retourneert de map met spelers gekoppeld aan hun gekozen tegels
	}

	private void toonKoninkrijk() {
		// Ga door elke speler in de lijst van gekozen spelers
		for (SpelerDTO spelerDTO : gekozenSpelers) {
			// Haal de gekozen kleur van de speler op
			String gekozenKleur = spelerKleurMap.get(spelerDTO);
			// Toon de gebruikersnaam van de speler en de gekozen kleur
			System.out.println("Speler: " + spelerDTO.gebruikersnaam() + ", gekozen kleur: " + gekozenKleur);
			// Haal het koninkrijk van de speler op
			Vakje[][] koninkrijk = spelerDTO.koninkrijk();
			// Loop door elk vakje in het koninkrijk
			for (int x = 0; x < koninkrijk.length; x++) {
				for (int y = 0; y < koninkrijk[x].length; y++) {
					// Controleer of het huidige vakje de starttegel is
					if (x == koninkrijk.length / 2 && y == koninkrijk.length / 2) {
						// Toon "starttegel" op de positie van de starttegel
						System.out.printf("%10s", "starttegel");
					} else {
						// Toon het landschap op het huidige vakje, als het vakje niet leeg is
						System.out.printf("%10s", koninkrijk[x][y] != null ? koninkrijk[x][y].getLandschap() : "");
					}
				}
				System.out.println(); // Na elke rij van vakjes, een nieuwe regel toevoegen
			}
		}
	}

	private void toonKolom(List<DominotegelDTO> kolom) {
		for (DominotegelDTO tegel : kolom) {
			System.out.println(tegel);
		}
	}

	private void toonKolomMetSpeler(List<DominotegelDTO> kolom, Map<SpelerDTO, DominotegelDTO> spelerTegel) {
		for (SpelerDTO speler : gekozenSpelers) {
			System.out.printf("%s %s%n", spelerTegel.get(speler).tegel(), spelerKleurMap.get(speler));
		}
	}
}
