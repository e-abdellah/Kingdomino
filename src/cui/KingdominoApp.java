package cui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import domein.DomeinController;
import domein.Dominotegel;
import domein.Kleuren;
import domein.Speler;
import domein.Vakje;
import dto.SpelerDTO;

public class KingdominoApp {

	private final DomeinController dc;
	private Scanner sc;
	private final Map<String, String> gekozenSpelersMetKleur;
	private Dominotegel dominotegel = new Dominotegel();
	private List<String> kleuren;

	List<Vakje> vakjes = dominotegel.getVakjes();

	public KingdominoApp(DomeinController dc) {
		sc = new Scanner(System.in);
		this.dc = dc;
		gekozenSpelersMetKleur = new HashMap<>();
		kleuren = dc.geefAlleKleuren();
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
//		for (int i = 0; i < vakjes.size(); i++) {
//			System.out.println("Vakje " + (i + 1) + ": " + vakjes.get(i).getLandschap());
//		}

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

		try {

			sc.nextLine(); // Consumeer de newline
			System.out.print("Geef de spelersnaam(minstens 6 karakters, niet enkel spaties): ");
			String naam = sc.nextLine(); // Lees de naam van de speler

			System.out.print("Geef het geboortejaar(minstens 6 jaar oud): ");
			int geboortejaar = sc.nextInt(); // Lees het geboortejaar

			dc.registreerSpeler(naam, geboortejaar); // Registreer de speler

			System.out.println("U bent succesvol geregistreerd");
		} catch (Exception e) {
			System.out.println("Er is een fout opgetreden bij het registreren van de speler. Probeer opnieuw.");
			sc.nextLine(); // Consumeer de newline om de scanner te resetten
		}

		start();
	}

	private void toonOverzicht() {
		List<SpelerDTO> overzicht = dc.geefOverzichtSpelers();

		if (overzicht.isEmpty()) {
			System.out.printf("Er zijn nog geen geregistreerde spelers!%n%n");
			start();
		} else
			for (SpelerDTO dto : overzicht)
				System.out.printf("Speler %s van %d %n", dto.gebruikersnaam(), dto.geboortejaar());
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
            try {
                System.out.print("Kies een speler (nummer): ");
                gekozenSpelerIndex = sc.nextInt() - 1;
            } catch (InputMismatchException e) {
                System.out.println("Ongeldige invoer. Voer een geldig nummer in.");
                sc.nextLine(); // Consumeer de ongeldige invoer
                gekozenSpelerIndex = -1; // Zet de index op een ongeldige waarde om de lus opnieuw te laten lopen
            }
        } while (gekozenSpelerIndex < 0 || gekozenSpelerIndex >= beschikbareSpelers.size());

        String gekozenSpelerGebruikersnaam = beschikbareSpelers.get(gekozenSpelerIndex).gebruikersnaam();

        // Controleer of de speler al is gekozen
        if (gekozenSpelersMetKleur.containsKey(gekozenSpelerGebruikersnaam)) {
            System.out.println("Deze speler is al gekozen. Kies een andere speler.");
            kiesSpeler(); // Vraag de gebruiker om een andere speler te kiezen
            return;
        }

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

        }while(!kleuren.contains(gekozenKleur));


        gekozenSpelersMetKleur.put(gekozenSpelerGebruikersnaam, gekozenKleur);
        kleuren.remove(gekozenKleur.toUpperCase());
        System.out.println("Speler " + gekozenSpelerGebruikersnaam + " toegvoegd met kleur " + gekozenKleur);

        do {
            System.out.println("Wil je nog een speler toevoegen? (ja/nee):\nMinstens 3 spelers");
            String keuze = sc.next();
            if (keuze.equalsIgnoreCase("ja")) {
//					drukResterendeSpelersAf(beschikbareSpelers, gekozenSpelerIndex);
                kiesSpeler(); // Blijf spelers toevoegen indien gewenst
            }
            if (keuze.equalsIgnoreCase("nee"))
                if (gekozenSpelersMetKleur.size() < 3) {
                    System.out.println("Je moet minimaal 3 spelers kiezen.");
                    kiesSpeler();
                } else {
                    startKingdomino();
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
