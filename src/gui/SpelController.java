package gui;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import domein.DomeinController;
import domein.Dominotegel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class SpelController {

	@FXML
	private Label spelLabel;
	private DomeinController dc;

	@FXML
	private VBox spelerInformatieContainer; // Voor speler info

	@FXML
	private VBox dominotegelInformatieContainer; // Voor dominotegels
	private int aantalSpelers;
	private WelkomKDController kdController;
	private Deque<Dominotegel> stapel;
	private List<Dominotegel> startKolom;

	public SpelController() {
		// Je DomeinController initialiseren, indien nodig
		this.dc = new DomeinController();
		kdController = new WelkomKDController();
		aantalSpelers = kdController.getAantalSpelersGekozen();
		stapel = (Deque<Dominotegel>) dc.schudDominotegels(aantalSpelers);
	}

	@FXML
	public void initialize() {
		// Initialize je UI componenten indien nodig
		//		toonDominotegels();
	}

	public void setSpelerInformatie(List<String> spelerInformatie) {
		spelerInformatie.forEach(info -> {
			Label label = new Label(info);
			spelerInformatieContainer.getChildren().add(label);
		});
	}

	private void toonDominotegels() {
		List<Dominotegel> dominotegels = dc.schudDominotegels(3);
		for (Dominotegel tegel : dominotegels) {
			Image image = new Image(tegel.getVoorkantFotoPad());
			System.out.println(tegel.getVoorkantFotoPad());
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(100);
			imageView.setFitHeight(150);
			dominotegelInformatieContainer.getChildren().add(imageView);
		}
	}

	private void startRonde() {
		// TODO Plaats spelstapel in het midden

		// TODO Plaats per speler zijn kasteel op zijn starttegel

	}

	private void toonTegel(Label label) {
		// Methode om de label daadwerkelijk in  GUI te tonen
	}

	private void speelRonde() {
		Deque<Dominotegel> gekozenTegels = new ArrayDeque<>();

		// toont 3 of 4 tegels om te kiezen en dan verwijdert ze uit de stapel
		for (int i = 0; i < aantalSpelers; i++) {
			Dominotegel tegel = stapel.peek();
			Label tegelLabel = new Label(tegel.toString());
			toonTegel(tegelLabel);
			gekozenTegels.push(tegel);
			stapel.pop();
		}
		// Plaats de genomen tegels in de startkolom,gesorteerd volgens hun nummer van klein naar groot
		startKolom = dc.plaatsTegelsInStartkolom(gekozenTegels);
		// Plaats tegels met hun landschapszijde naar boven

		for (int i = 0; i < aantalSpelers; i++) {
			kiesTegelInStartKolom();

		}

		toonResultaatVanRonde();
	}

	private void toonResultaatVanRonde() {
		// Alert met: info van alle spelers + resterende tegels in stapel + startkolom

	}

	private Dominotegel kiesTegelInStartKolom() {
		// TODO: Implementeer de logica om de gebruiker een tegel uit de startkolom te laten kiezen.
		// Dialoog met de beschikbare tegels.

		Dominotegel gekozenTegel = null; // Placeholder voor de gekozen tegel

		boolean isTegelGekozen = false; // Flag om te controleren of de keuze gemaakt is

		while (!isTegelGekozen) {

			gekozenTegel = startKolom.get(0); // Default tegel
			// TODO speler kiest een tegel in GUI

			// Controleer of de gekozen tegel vrij is
			if (gekozenTegel != null && isTegelVrij(gekozenTegel)) {
				isTegelGekozen = true;
				startKolom.remove(gekozenTegel);
			} else {
				// TODO: Toon een bericht aan de gebruiker dat de gekozen tegel niet vrij is
				// en vraag om een andere tegel te kiezen.
			}
		}

		// TODO: Implementeer de logica om de gekozen tegel in de GUI te tonen met de landschapszijde naar boven.
		// Dit kan bijvoorbeeld het updaten van de weergave van de tegel zijn om deze te markeren als gekozen.

		return gekozenTegel; // Return de uiteindelijk gekozen en gevalideerde tegel
	}

	private boolean isTegelVrij(Dominotegel tegel) {
		return !startKolom.contains(tegel);

	}

}
