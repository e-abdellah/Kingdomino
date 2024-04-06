package gui;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import domein.DomeinController;
import domein.Dominotegel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SpelController {

	@FXML
	private Label spelLabel;
	@FXML
	private VBox spelerInformatieContainer; // Voor speler info
	@FXML
	private VBox dominotegelInformatieContainer; // Voor dominotegels
	@FXML
	private HBox tegelEnKleurBox;
	@FXML
	private HBox eindkolom;
	@FXML
	private ImageView stapelRugzijdeImageView;
	@FXML
	private Button startRondeBtn;
	@FXML
	private VBox gekozenDominotegels;
	@FXML
	private VBox gekozenDominotegelsEindKolom;
	@FXML
	private GridPane gridGroen;
	@FXML
	private GridPane gridBlauw;
	@FXML
	private GridPane gridGeel;
	@FXML
	private GridPane gridRoos;

	private DomeinController dc;
	private int aantalSpelers;
	private final WelkomKDController kdController;
	private List<Dominotegel> stapel;
	private Deque<Dominotegel> startKolom;
	private Deque<Dominotegel> eindkolomTegels;
	private List<Dominotegel> gekozenTegels = new ArrayList<>();
	private List<String> spelerKleuren;
	private Map<Integer, Dominotegel> spelerTegelMap = new HashMap<>();
	private boolean isEersteClick = true; // Flag to track if it's the first button click
	private boolean isVolgendeRonde = false; // This flag will determine the button state

	private Set<Integer> startkolomSpelers = new HashSet<>();
	private Set<Integer> eindkolomSpelers = new HashSet<>();

	// private final Random rand = new Random();

	private ResourceBundle resourceBundle;
	private int huidigeSpelerIndex = 0; // Standaardwaarde die aangeeft dat nog geen speler is geselecteerd

	private Locale locale;

	public SpelController() {
		dc = new DomeinController();
		kdController = new WelkomKDController();
		aantalSpelers = kdController.getAantalSpelersGekozen();
		spelerKleuren = new ArrayList<>();
		eindkolomTegels = new ArrayDeque<>();
		startKolom = new ArrayDeque<>();
		eindkolom = new HBox(5);
		tegelEnKleurBox = new HBox(5);
		gridGroen = new GridPane();
		gridBlauw = new GridPane();
		gridGeel = new GridPane();
		gridRoos = new GridPane();

	}

	public void initSpel() {
		setSpelerInfo(spelerKleuren);
		stapel = dc.dominotegels(aantalSpelers);
		speelRonde(true, startKolom);
		toonRugzijdeStapel();
		Platform.runLater(() -> {// zorgt ervoor dat dit pas te zien is wanneer het scherm volledig is geladen
			toonWelkomPopup();
		});

		speelRonde(false, eindkolomTegels);
		startRondeBtn.setDisable(false); // Zet de knop op actief

	}

	public void setSpelerInfo(List<String> spelerEnKleurInformatie) {
		spelerEnKleurInformatie.forEach(info -> {
			String[] parts = info.split("-");
			if (parts.length == 2) {
				String naam = parts[0];
				String kleur = parts[1];
				System.out.println(kleur.toLowerCase());

				// Create a label for the player name
				Label label = new Label(naam);

				// Create a circle with the player's color
				Circle circle = new Circle(10); // Circle with radius 10
				switch (kleur.toLowerCase().trim()) {
				case "groen", "green" -> circle.setFill(Color.GREEN);
				case "geel", "yellow" -> circle.setFill(Color.YELLOW);
				case "roos", "pink" -> circle.setFill(Color.PINK);
				case "blauw", "blue" -> circle.setFill(Color.BLUE);
				default -> circle.setFill(Color.WHITE);
				}

				spelerKleuren.add(kleur);

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
		welkomAlert.setContentText("Welkom bij het spel! Klik op 'Kies Tegel' om het spel te beginnen.");
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

	public void toonStartKolom(List<Dominotegel> dominotegels) {
		HBox hbox = new HBox(10); // Gebruik een kleine spacing tussen de VBoxen

		VBox vboxVoorkant = new VBox(5); // Een beetje spacing voor esthetiek
		// VBox vboxAchterkant = new VBox(5);

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
			voorkantImageView.setOnMouseClicked(event -> kiesTegel(tegel, true));
			// Image achterkantImage = new Image(tegel.getAchterkantFotoPad());
			// ImageView achterkantImageView = new ImageView(achterkantImage);
			// achterkantImageView.setFitWidth(100);
			// achterkantImageView.setFitHeight(50);

			vboxVoorkant.getChildren().add(voorkantImageView);
			// vboxAchterkant.getChildren().add(achterkantImageView);
		}

		hbox.getChildren().addAll(vboxVoorkant /* , vboxAchterkant */);
		dominotegelInformatieContainer.getChildren().clear();
		dominotegelInformatieContainer.getChildren().add(hbox);
	}

	public void toonEindkolom(List<Dominotegel> dominotegels) {
		HBox hbox = new HBox(10); // Gebruik een kleine spacing tussen de VBoxen

		VBox vboxVoorkant = new VBox(5); // Een beetje spacing voor esthetiek
		// VBox vboxAchterkant = new VBox(5);

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
			voorkantImageView.setOnMouseClicked(event -> kiesTegel(tegel, false));

			// Image achterkantImage = new Image(tegel.getAchterkantFotoPad());
			// ImageView achterkantImageView = new ImageView(achterkantImage);
			// achterkantImageView.setFitWidth(100);
			// achterkantImageView.setFitHeight(50);

			vboxVoorkant.getChildren().add(voorkantImageView);
			// vboxAchterkant.getChildren().add(achterkantImageView);
		}

		hbox.getChildren().addAll(vboxVoorkant/* , vboxAchterkant */);
		eindkolom.getChildren().clear();
		eindkolom.getChildren().add(hbox);
	}

	private void kiesTegel(Dominotegel tegel, boolean isStartKolom) {
		// Check of de huidige speler al een keuze heeft gemaakt
		if ((isStartKolom && startkolomSpelers.contains(huidigeSpelerIndex))
				|| (!isStartKolom && eindkolomSpelers.contains(huidigeSpelerIndex))) {
			// Toon foutmelding
			showAlert("Al Gekozen", "Je hebt al een tegel gekozen uit deze kolom. Wacht op de volgende ronde.");
			return; // Vroegtijdig beëindigen van de methode
		}

		System.out.println(huidigeSpelerIndex);

		if (handleNullTegel(tegel))
			return;
		if (tegelIsAlGekozen(tegel))
			return;

		voegGekozenTegelToe(tegel);
		toonTegelEnSpelerKleur(tegel, isStartKolom);

		// Toevoegen van de huidige spelerindex aan de respectievelijke set
		if (isStartKolom) {
			startkolomSpelers.add(huidigeSpelerIndex);
		} else {
			eindkolomSpelers.add(huidigeSpelerIndex);
		}

		// Veronderstel dat je een dialoogvenster toont om rij en kolom te krijgen
		if (isStartKolom) {
			int[] positie = vraagTegelPositie(); // Deze methode toont een dialoogvenster en retourneert de gekozen
													// positie als een array [rij, kolom]
			if (positie != null) {
				plaatsTegelInGrid(tegel, positie[0], positie[1]);
			}
		}
		updateSpelStatus(isStartKolom);

	}

	private boolean handleNullTegel(Dominotegel tegel) {
		if (tegel == null) {
			vraagVolgendeSpeler();
			return true;
		}
		return false;
	}

	private boolean tegelIsAlGekozen(Dominotegel tegel) {
		if (gekozenTegels.contains(tegel)) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Tegel Al Gekozen");
			alert.setContentText("Deze tegel is al gekozen. Kies een andere tegel.");
			alert.showAndWait();
			return true;
		}
		return false;
	}

	private void voegGekozenTegelToe(Dominotegel tegel) {
		gekozenTegels.add(tegel);
		spelerTegelMap.put(huidigeSpelerIndex, tegel);
	}

	private void toonTegelEnSpelerKleur(Dominotegel tegel, boolean isStartKolom) {
		Color spelerKleur = getSpelerKleur(huidigeSpelerIndex);
		Circle kleurIndicator = new Circle(10, spelerKleur);
		kleurIndicator.setStroke(Color.BLACK);

		Image tegelAfbeelding = new Image(tegel.getVoorkantFotoPad());
		ImageView tegelImageView = new ImageView(tegelAfbeelding);
		tegelImageView.setFitWidth(100);
		tegelImageView.setFitHeight(50);

		HBox tegelEnKleurBox = new HBox(5, kleurIndicator, tegelImageView);

		VBox container = isStartKolom ? gekozenDominotegels : gekozenDominotegelsEindKolom;
		container.getChildren().add(tegelEnKleurBox);

		// Alert alert = new Alert(Alert.AlertType.INFORMATION);
		// alert.setTitle("Tegel Gekozen");
		// alert.setContentText("U heeft tegel " + tegel + " gekozen.");
		// alert.showAndWait();
	}

	public Color getSpelerKleur(int huidigeSpelerIndex) {
		String kleurCode = spelerKleuren.get(huidigeSpelerIndex).toLowerCase().trim();
		switch (kleurCode) {
		case "groen":
			return Color.GREEN;
		case "geel":
			return Color.YELLOW;
		case "roos":
			return Color.PINK;
		case "blauw":
			return Color.BLUE;
		default:
			return Color.WHITE;
		}
	}

	private void updateSpelStatus(boolean isStartKolom) {
		if (isStartKolom) {
			startkolomSpelers.add(huidigeSpelerIndex);
			if (startkolomSpelers.size() >= aantalSpelers) {
				toonEindkolom(new ArrayList<>(eindkolomTegels));
				toonRugzijdeStapel();
				isVolgendeRonde = true;
				// Reset huidigeSpelerIndex voor de volgende kolom als dat nodig is.
				huidigeSpelerIndex = 0; // Dit hangt af van je spelregels.
			}
		} else {
			eindkolomSpelers.add(huidigeSpelerIndex);
			if (eindkolomSpelers.size() >= aantalSpelers) {
				System.out.println("einde eindkolom");
				plaatsTegel();
				startVolgendeRonde();
				return; // Voorkomt het dubbel aanroepen van vraagVolgendeSpeler.
			}
		}

		// Verplaats dit naar het einde om te zorgen dat we altijd naar de volgende
		// speler gaan.
		huidigeSpelerIndex = (huidigeSpelerIndex + 1) % spelerInformatieContainer.getChildren().size();
		vraagVolgendeSpeler();
	}

	private void vraagVolgendeSpeler() {
		// Check if we need to increment huidigeSpelerIndex
		if (isEersteClick || startkolomSpelers.size() == aantalSpelers && !isVolgendeRonde) {
			huidigeSpelerIndex = (huidigeSpelerIndex + 1) % spelerInformatieContainer.getChildren().size();
		}

		Node selecteerdeNode = spelerInformatieContainer.getChildren().get(huidigeSpelerIndex);
		String spelerInfo = (String) selecteerdeNode.getUserData();

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Kies een Tegel");
		// alert.setHeaderText(null);
		if (startkolomSpelers.size() == aantalSpelers) {
			// System.out.println("We zijn in de eerste deel van de conditie");
			alert.setContentText("Het is aan uw beurt speler: " + spelerInfo
					+ "\nKies één van de beschikbare tegels uit de eindkolom.");
		} else {
			// System.out.println("We zijn in de tweede deel van de conditie");
			alert.setContentText("Het is aan uw beurt speler: " + spelerInfo
					+ "\nKies één van de beschikbare tegels uit de startkolom.");
		}

		alert.showAndWait();

		// If it's the start of the round, then we just prompted the first player
		if (isEersteClick) {
			isEersteClick = false;
		}
	}

	private void startVolgendeRonde() {
		// Reset the game state for the next round, including clearing tracking sets
		startRondeBtn.setDisable(false);
		isVolgendeRonde = false;
		isEersteClick = true;
		huidigeSpelerIndex = 0; // Optionally reset to the first player
		startkolomSpelers.clear();
		eindkolomSpelers.clear();
		gekozenTegels.clear(); // Assuming you want to reset the selections for a new round
		Alert volgendeRondeAlert = new Alert(Alert.AlertType.INFORMATION);
		volgendeRondeAlert.setTitle("Volgende Ronde");
		volgendeRondeAlert.setHeaderText(null);
		volgendeRondeAlert.setContentText("De volgende ronde is begonnen.");
		volgendeRondeAlert.showAndWait();
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

	public void speelRonde(boolean toonBeideZijden, Deque<Dominotegel> list) {
		list.clear(); // Maak de Deque leeg

		// Simuleer het selecteren van tegels voor de speelronde
		for (int i = 0; i < aantalSpelers; i++) {
			Dominotegel tegel = stapel.get(0); // Haal de bovenste tegel van de stapel
			stapel.remove(0);
			list.offer(tegel);
		}

		// Toon de geselecteerde tegels in de GUI
		if (toonBeideZijden) {
			toonStartKolom(new ArrayList<>(list)); // Toon tegels met beide zijden
		} else {
			toonTegels(list); // Toon tegels met enkel de achterkant
		}

	}

	private void toonResultaatVanRonde() {
		// Alert met: info van alle spelers + resterende tegels in stapel + startkolom
	}

	public void setAantalSpelers(int aantal) {
		this.aantalSpelers = aantal;
	}

	public List<Dominotegel> getStapel() {
		return stapel;
	}

	@FXML
	private void handleStartRondeBtnAction(ActionEvent event) {
		Button startRondeButton = (Button) event.getSource(); // Get the button that was clicked

		if (spelerInformatieContainer.getChildren().isEmpty()) {
			System.out.println("Er zijn geen spelers.");
			return;
		}

		// If it's the first click and not yet the next round, begin the first selection
		// phase
		if (isEersteClick && !isVolgendeRonde) {
			toonStartKolom(new ArrayList<>(startKolom)); // Display the start column with tegels
			isEersteClick = false;
			kiesTegel(null, true); // Initiate the first player's turn to choose a tegel
			startRondeButton.setDisable(true); // Deactiveer de knop
		}

		// If the button is clicked when it's already the next round
		// if (!isEersteClick && isVolgendeRonde && eindkolomSpelers.size() >=
		// aantalSpelers) {
		else {
			System.out.println("btn clicked");
			startRondeButton.setText("Start Ronde");
			startVolgendeRonde();
		}
	}

	private int[] vraagTegelPositie() {
		while (true) { // Begin een oneindige lus die breekt wanneer geldige invoer wordt ontvangen
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Tegel Plaatsen");
			dialog.setHeaderText(
					"Geef de rij en kolom in waar je de tegel wilt plaatsen, gescheiden door een komma (bijv. 1,2)");
			Optional<String> result = dialog.showAndWait();

			if (result.isPresent()) {
				try {
					String[] parts = result.get().split(",");
					if (parts.length != 2) { // Controleer of er precies twee delen zijn na het splitsen
						showAlert("Ongeldige Input", "Voer alstublieft twee getallen in gescheiden door een komma.");
						continue; // Ga terug naar het begin van de lus voor nieuwe invoer
					}
					int rij = Integer.parseInt(parts[0].trim()) - 1; // -1 omdat GridPane indexen bij 0 beginnen
					int kolom = Integer.parseInt(parts[1].trim()) - 1;
					// Voeg hier eventueel extra validatie toe om te controleren of de coördinaten
					// binnen de verwachte grenzen vallen
					return new int[] { rij, kolom };
				} catch (NumberFormatException e) {
					// Toon foutmelding als de input niet correct is
					showAlert("Foutieve Input", "Voer alstublieft geldige getallen in gescheiden door een komma.");
				}
			} else {
				// Gebruiker heeft het dialoogvenster geannuleerd; retourneer null of handel dit
				// scenario op een andere manier af
				return null;
			}
		}
	}

	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private void plaatsTegelInGrid(Dominotegel tegel, int rij, int kolom) {
		Image tegelAfbeelding = new Image(tegel.getVoorkantFotoPad());
		ImageView tegelImageView = new ImageView(tegelAfbeelding);
		tegelImageView.setFitWidth(100); // Pas de grootte aan aan jouw grid
		tegelImageView.setFitHeight(50);

		// Aanname: je weet al in welke GridPane deze moet (bijv. op basis van de
		// huidige speler)
		// Dit is een voorbeeld, vervang gridGroen door de juiste grid op basis van
		// speler/logica
		GridPane doelGridPane = bepaalDoelGridPane(getSpelerKleur(huidigeSpelerIndex));
		doelGridPane.add(tegelImageView, kolom, rij);
	}

	private GridPane bepaalDoelGridPane(Color spelerKleur) {
		if (spelerKleur.equals(Color.BLUE)) {
			return gridBlauw;
		} else if (spelerKleur.equals(Color.GREEN)) {
			return gridGroen;
		} else if (spelerKleur.equals(Color.YELLOW)) {
			return gridGeel;
		} else if (spelerKleur.equals(Color.PINK)) {
			return gridRoos;
		} else {
			return null; // Of een default GridPane als fallback
		}
	}

	private void plaatsTegel() {
		System.out.println("plaats tegel");
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Plaats tegel");
		alert.setContentText("Speler met kleur " + null + " verplaats uw gekozen tegel in uw koninkrijk.");
		alert.showAndWait();
	}

}