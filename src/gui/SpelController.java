package gui;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import domein.DomeinController;
import domein.Dominotegel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SpelController {

	@FXML
	private Label spelLabel;
	private DomeinController dc;

	@FXML
	private VBox spelerInformatieContainer; // Voor speler info

	@FXML
	private HBox dominotegelInformatieContainer; // Voor dominotegels

	private HBox tegelEnKleurBox;

	private int aantalSpelers;
	private WelkomKDController kdController;
	private List<Dominotegel> stapel;
	private List<Dominotegel> startKolom;
	private Deque<Dominotegel> gekozenTegels;
	private List<Dominotegel> list = new ArrayList<>();
	private List<String> spelerKleuren;
	private final Random rand = new Random();
	private final Set<Integer> gekozenSpelerIndices = new HashSet<>();
	private Map<Integer, Dominotegel> spelerTegelMap = new HashMap<>();

	private ResourceBundle resourceBundle;
	@FXML
	private ImageView stapelRugzijdeImageView;
	private int huidigeSpelerIndex = -1; // Standaardwaarde die aangeeft dat nog geen speler is geselecteerd

	private Locale locale;

	@FXML
	private Button startRondeBtn;

	public SpelController() {
		dc = new DomeinController();
		kdController = new WelkomKDController();
		aantalSpelers = kdController.getAantalSpelersGekozen();
		startKolom = new ArrayList<>();
		gekozenTegels = new ArrayDeque<>();
		spelerKleuren = new ArrayList<>();
	}

	public void initSpel() {
		setSpelerInfo(spelerKleuren);
		stapel = dc.dominotegels(aantalSpelers);
		speelRonde(false);
		toonRugzijdeStapel();
		Platform.runLater(() -> {// zorgt ervoor dat dit pas te zien is wanneer het scherm volledig is geladen
			toonWelkomPopup();
		});
	}

	//	public void setSpelerInformatie(List<String> spelerInformatie) {
	//		spelerInformatie.forEach(info -> {
	//			Label label = new Label(info);
	//			spelerInformatieContainer.getChildren().add(label);
	//		});
	//	}

	public void setSpelerInfo(List<String> spelerEnKleurInformatie) {
		spelerEnKleurInformatie.forEach(info -> {
			String[] parts = info.split("-");
			if (parts.length == 2) {
				String playerName = parts[0];
				String colorValue = parts[1];
				System.out.println(colorValue.toLowerCase());

				// Create a label for the player name
				Label label = new Label(playerName);

				// Create a circle with the player's color
				Circle circle = new Circle(10); // Circle with radius 10
				switch (colorValue.toLowerCase().trim()) {
				case "groen" -> circle.setFill(Color.GREEN);
				case "geel" -> circle.setFill(Color.YELLOW);
				case "roos" -> circle.setFill(Color.PINK);
				case "blauw" -> circle.setFill(Color.BLUE);
				default -> circle.setFill(Color.WHITE);
				}

				spelerKleuren.add(colorValue);

				HBox hbox = new HBox();
				hbox.getChildren().addAll(circle, label);

				// Set UserData to HBox to store player information
				hbox.setUserData(info); // Gebruik de volledige string "info" als UserData

				spelerInformatieContainer.getChildren().add(hbox);
			}
		});
	}

	public void toonWelkomPopup() {
		Alert welkomAlert = new Alert(Alert.AlertType.INFORMATION);
		welkomAlert.setTitle("Welkom bij Kingdomino!");
		welkomAlert.setHeaderText(null);
		welkomAlert.setContentText(
				"Welkom bij het spel! Klik op 'Kies Tegel' om het spel te beginnen. Herhaal dit voor elke speler!");
		welkomAlert.showAndWait();
	}

	public void toonTegels(Deque<Dominotegel> dominotegels) {
		dominotegelInformatieContainer.getChildren().clear();

		for (Dominotegel tegel : dominotegels) {
			// Maak en configureer de ImageView voor de tegel...
			ImageView tegelImageView = new ImageView(new Image(tegel.getVoorkantFotoPad()));
			tegelImageView.setFitWidth(100);
			tegelImageView.setFitHeight(50);

			// Maak een nieuwe HBox voor elke tegel...
			HBox tegelBox = new HBox(tegelImageView);
			tegelBox.setUserData(tegel); // Gebruik de Dominotegel als identifier

			// Voeg de HBox toe aan de container...
			dominotegelInformatieContainer.getChildren().add(tegelBox);
		}
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
			// ImageView voorkantImageView = new ImageView(new
			// Image(tegel.getVoorkantFotoPad()));
			voorkantImageView.setFitWidth(100);
			voorkantImageView.setFitHeight(50);
			voorkantImageView.setOnMouseClicked(event -> kiesTegel(tegel));

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

	private void kiesTegel(Dominotegel tegel) {
		System.out.println(huidigeSpelerIndex);
		if (list.contains(tegel)) {
			// Toon een bericht dat deze tegel al gekozen is
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Tegel Al Gekozen");
			alert.setHeaderText(null);
			alert.setContentText("Deze tegel is al gekozen. Kies een andere tegel.");
			alert.showAndWait();
		}
		// Veronderstellend dat 'huidigeSpelerIndex' correct is ingesteld op de index van de huidige speler
		if (list.contains(tegel)) {
			// Bericht dat de tegel al gekozen is
		} else if (tegel != null) {
			list.add(tegel);
			// Voeg de tegel toe aan de map met de huidige spelerindex
			spelerTegelMap.put(huidigeSpelerIndex, tegel);

			// Haal de kleur op van de huidige speler
			String kleurCode = spelerKleuren.get(huidigeSpelerIndex);
			Color spelerKleur;

			switch (kleurCode.toLowerCase().trim()) {
			case "groen":
				spelerKleur = Color.GREEN;
				break;
			case "geel":
				spelerKleur = Color.YELLOW;
				break;
			case "roos":
				spelerKleur = Color.PINK;
				break;
			case "blauw":
				spelerKleur = Color.BLUE;
				break;
			default:
				spelerKleur = Color.WHITE;
			}

			Circle kleurIndicator = new Circle(10);
			kleurIndicator.setFill(spelerKleur);
			kleurIndicator.setStroke(Color.BLACK);

			Image tegelAfbeelding = new Image(tegel.getVoorkantFotoPad());
			ImageView tegelImageView = new ImageView(tegelAfbeelding);
			tegelImageView.setFitWidth(100);
			tegelImageView.setFitHeight(50);

			tegelEnKleurBox = new HBox(5, kleurIndicator, tegelImageView);
			dominotegelInformatieContainer.getChildren().add(tegelEnKleurBox);

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Tegel Gekozen");
			alert.setContentText("U heeft tegel " + tegel + " gekozen.");
			alert.showAndWait(); 
		} else {
			System.out.println("Selecteer een tegel");
		}

	}

	public void toonRugzijdeStapel() {
		if (!stapel.isEmpty()) {
			Dominotegel bovensteTegel = stapel.get(0); // Verkrijg de bovenste tegel zonder deze te verwijderen
			bovensteTegel.genereerFotoPaden(); // Zorg ervoor dat de paden gegenereerd zijn
			Image rugzijdeImage = new Image(bovensteTegel.getAchterkantFotoPad());
			stapelRugzijdeImageView.setImage(rugzijdeImage);
		} else {
			stapelRugzijdeImageView.setImage(null);
		}
	}

	public void speelRonde(boolean toonBeideZijden) {
		gekozenTegels = new ArrayDeque<>();

		// Simuleer het selecteren van tegels voor de speelronde
		for (int i = 0; i < aantalSpelers; i++) {

			Dominotegel tegel = stapel.get(0); // Haal de bovenste tegel van de stapel
			stapel.remove(0);
			gekozenTegels.offer(tegel);
		}

		// Toon de geselecteerde tegels in de GUI
		if (toonBeideZijden) {
			toonTegelsMetBeideZijden(new ArrayList<>(gekozenTegels)); // Toon tegels met beide zijden
		} else {
			toonTegels(gekozenTegels); // Toon tegels met enkel de achterkant
		}

		// Plaats de genomen tegels in de startkolom, gesorteerd volgens hun nummer met
		// hun landschapszijde naar boven
		// startKolom = dc.plaatsTegelsInStartkolom(gekozenTegels);
		// Plaats tegels met hun landschapszijde naar boven

		// for (int i = 0; i < aantalSpelers; i++) {
		// kiesTegelInStartKolom();
		// }

		// toonResultaatVanRonde();
	}

	private void toonResultaatVanRonde() {
		// Alert met: info van alle spelers + resterende tegels in stapel + startkolom

	}

	private Dominotegel kiesTegelInStartKolom() {
		if (startKolom.isEmpty()) {
			// Toon een bericht dat er geen tegels zijn om uit te kiezen of handel dit
			// anderszins af
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Geen Tegels Beschikbaar");
			alert.setHeaderText(null);
			alert.setContentText("Er zijn momenteel geen tegels beschikbaar om uit te kiezen.");
			alert.showAndWait();
			return null; // Terugkeren aangezien er niets te kiezen valt
		}

		List<String> keuzes = startKolom.stream().map(Dominotegel::toString).collect(Collectors.toList());

		ChoiceDialog<String> dialog = new ChoiceDialog<>(keuzes.get(0), keuzes);
		dialog.setTitle("Kies een tegel");
		dialog.setHeaderText("Selecteer een tegel uit de startkolom:");
		dialog.setContentText("Beschikbare tegels:");

		// Toon de dialog en wacht op de gebruikersinput
		Optional<String> resultaat = dialog.showAndWait();
		Dominotegel gekozenTegel = null;

		if (resultaat.isPresent()) {
			String gekozenTegelString = resultaat.get();
			// Vind de overeenkomende Dominotegel in je startKolom gebaseerd op de keuze
			for (Dominotegel tegel : startKolom) {
				if (tegel.toString().equals(gekozenTegelString)) {
					gekozenTegel = tegel;
					break;
				}
			}
		}

		if (gekozenTegel != null) {
			// Voer hier acties uit met de gekozen tegel
			System.out.println("Je hebt gekozen: " + gekozenTegel);

		}

		return gekozenTegel;
	}

	private boolean isTegelVrij(Dominotegel tegel) {
		return !startKolom.contains(tegel);

	}

	public void setAantalSpelers(int aantal) {
		this.aantalSpelers = aantal;
	}

	public List<Dominotegel> getStapel() {
		return stapel;
	}

	@FXML
	private void handleStartRondeBtnAction(ActionEvent event) {
		if (gekozenTegels == null || gekozenTegels.isEmpty()) {
			System.out.println("Er zijn geen tegels beschikbaar om te tonen.");
			return;
		}
		if (spelerInformatieContainer.getChildren().isEmpty()) {
			System.out.println("Er zijn geen spelers.");
			return;
		}

		// Verhoog de index voor de volgende speler
		huidigeSpelerIndex = (huidigeSpelerIndex + 1) % spelerInformatieContainer.getChildren().size();

		Node geselecteerdeNode = spelerInformatieContainer.getChildren().get(huidigeSpelerIndex);
		String spelerInfo = (String) geselecteerdeNode.getUserData();

		// Toon een pop-upvenster met de geselecteerde spelerinformatie
		toonTegelsMetBeideZijden(new ArrayList<>(gekozenTegels));

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Begin van de Ronde");
		alert.setHeaderText(null);
		alert.setContentText("Het is aan uw beurt speler: " + spelerInfo + " \nKies één van de beschikbare tegels.");
		alert.showAndWait();
	}

}