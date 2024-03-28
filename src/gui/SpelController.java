package gui;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.stream.Collectors;

import domein.DomeinController;
import domein.Dominotegel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SpelController {

	@FXML
	private Label spelLabel;
	private DomeinController dc;

	@FXML
	private VBox spelerInformatieContainer; // Voor speler info

	@FXML
	private HBox dominotegelInformatieContainer; // Voor dominotegels
	private int aantalSpelers;
	private WelkomKDController kdController;
	private Deque<Dominotegel> stapel;
	private List<Dominotegel> startKolom;
	private Deque<Dominotegel> gekozenTegels;

	private ResourceBundle resourceBundle;

	private Locale locale;

	@FXML
	private Button startRondeBtn;

	public SpelController() {
		dc = new DomeinController();
		kdController = new WelkomKDController();
		aantalSpelers = kdController.getAantalSpelersGekozen();
	}

	public void initSpel() {
		// Verplaats logica die afhankelijk is van aantalSpelers en andere init-waarden hier
		stapel = dc.schudDominotegels(aantalSpelers);
		speelRonde(false);
	}

	public void setSpelerInformatie(List<String> spelerInformatie) {
		spelerInformatie.forEach(info -> {
			Label label = new Label(info);
			spelerInformatieContainer.getChildren().add(label);
		});
	}

	public void toonTegels(Deque<Dominotegel> dominotegels) {
		VBox vbox = new VBox();

		for (Dominotegel tegel : dominotegels) {
			// Gebruik achterkantFotoPad in plaats van voorkantFotoPad
			Image image = new Image(tegel.getAchterkantFotoPad());
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(200);
			imageView.setFitHeight(100);

			vbox.getChildren().add(imageView);
		}

		dominotegelInformatieContainer.getChildren().clear();
		dominotegelInformatieContainer.getChildren().add(vbox);
	}

	public void toonTegelsMetBeideZijden(List<Dominotegel> dominotegels) {
		HBox hbox = new HBox(10); // Gebruik een kleine spacing tussen de VBoxen

		VBox vboxVoorkant = new VBox(5); // Een beetje spacing voor esthetiek
		VBox vboxAchterkant = new VBox(5);

		// Sorteer de lijst met dominotegels op het getal attribuut voordat je ze toont
		List<Dominotegel> gesorteerdeTegels = dominotegels.stream()
				.sorted(Comparator.comparingInt(Dominotegel::getGetal)).collect(Collectors.toList());

		for (Dominotegel tegel : gesorteerdeTegels) {
			Image voorkantImage = new Image(tegel.getVoorkantFotoPad());
			ImageView voorkantImageView = new ImageView(voorkantImage);
			voorkantImageView.setFitWidth(100);
			voorkantImageView.setFitHeight(50);

			Image achterkantImage = new Image(tegel.getAchterkantFotoPad());
			ImageView achterkantImageView = new ImageView(achterkantImage);
			achterkantImageView.setFitWidth(100);
			achterkantImageView.setFitHeight(50);

			vboxVoorkant.getChildren().add(voorkantImageView);
			vboxAchterkant.getChildren().add(achterkantImageView);
		}

		hbox.getChildren().addAll(vboxVoorkant, vboxAchterkant);
		dominotegelInformatieContainer.getChildren().clear();
		dominotegelInformatieContainer.getChildren().add(hbox);
	}

	public void speelRonde(boolean toonBeideZijden) {
		gekozenTegels = new ArrayDeque<>();

		// Simuleer het selecteren van tegels voor de speelronde
		for (int i = 0; i < aantalSpelers; i++) {

			Dominotegel tegel = stapel.pop(); // Haal de bovenste tegel van de stapel
			gekozenTegels.offer(tegel);
		}

		// Toon de geselecteerde tegels in de GUI
		if (toonBeideZijden) {
			toonTegelsMetBeideZijden(new ArrayList<>(gekozenTegels)); // Toon tegels met beide zijden
		} else {
			toonTegels(gekozenTegels); // Toon tegels met enkel de achterkant
		}

		// Plaats de genomen tegels in de startkolom, gesorteerd volgens hun nummer met hun landschapszijde naar boven
		//		startKolom = dc.plaatsTegelsInStartkolom(gekozenTegels);
		// Plaats tegels met hun landschapszijde naar boven

		//		for (int i = 0; i < aantalSpelers; i++) {
		//			kiesTegelInStartKolom();
		//		}

		//toonResultaatVanRonde();
	}

	private void toonResultaatVanRonde() {
		// Alert met: info van alle spelers + resterende tegels in stapel + startkolom

	}

	private Dominotegel kiesTegelInStartKolom() {
		// TODO: Implementeer de logica om de gebruiker een tegel uit de startkolom te
		// laten kiezen.
		// Dialoog met de beschikbare tegels.
		Scanner scanner = new Scanner(System.in);

		Dominotegel gekozenTegel = null; // Placeholder voor de gekozen tegel

		boolean isTegelGekozen = false; // Flag om te controleren of de keuze gemaakt is

		while (!isTegelGekozen) {

			// gekozenTegel = startKolom.get(0); // Default tegel
			// TODO speler kiest een tegel in GUI
			// Veronderstelt dat startKolom een List of Deque van Dominotegels is
			System.out.println("Beschikbare tegels in de startkolom:");
			for (int i = 0; i < startKolom.size(); i++) {
				System.out.println(i + ": " + startKolom.get(i).toString()); // Zorg ervoor dat Dominotegel een zinvolle
																				// toString() heeft
			}

			System.out.println("Kies een tegelnummer:");
			int gekozenIndex = scanner.nextInt(); // Dit is CLI logica, in GUI zou je een andere manier van selecteren
													// hebben

			// Controleer of de gekozen tegel vrij is
			if (gekozenTegel != null && isTegelVrij(gekozenTegel)) {
				isTegelGekozen = true;
				startKolom.remove(gekozenTegel);
			} else {
				// TODO: Toon een bericht aan de gebruiker dat de gekozen tegel niet vrij is
				System.out.println("De gekozen tegel is niet vrij. Kies een andere tegel.");

				// en vraag om een andere tegel te kiezen.
				System.out.println("Kies een andere tegelnummer:");
				gekozenIndex = scanner.nextInt();
			}
		}

		// TODO: Implementeer de logica om de gekozen tegel in de GUI te tonen met de
		// landschapszijde naar boven.
		// Dit kan bijvoorbeeld het updaten van de weergave van de tegel zijn om deze te
		// markeren als gekozen.

		return gekozenTegel; // Return de uiteindelijk gekozen en gevalideerde tegel
	}

	private boolean isTegelVrij(Dominotegel tegel) {
		return !startKolom.contains(tegel);

	}

	public void setAantalSpelers(int aantal) {
		this.aantalSpelers = aantal;
	}

	public Deque<Dominotegel> getStapel() {
		return stapel;
	}

	@FXML
	private void handleStartRondeBtnAction(ActionEvent event) {
		if (gekozenTegels == null || gekozenTegels.isEmpty()) {
			// startKolom is null of leeg. Afhandelen van deze situatie.
			// Bijvoorbeeld, toon een foutmelding of initialiseer startKolom met lege waarden.
			System.out.println("Er zijn geen tegels beschikbaar om te tonen.");
			return; // Stop de methode om verdere fouten te voorkomen.
		}
		toonTegelsMetBeideZijden(new ArrayList<>(gekozenTegels)); // Gebruik startKolom die al gegenereerd is
	}

}