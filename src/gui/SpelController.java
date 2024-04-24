package gui;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import domein.DomeinController;
import domein.Dominotegel;
import dto.DominotegelDTO;
import dto.SpelerDTO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SpelController {

	@FXML
	private Label spelLabel;
	@FXML
	private VBox spelerInformatieContainer; // Voor speler info
	@FXML
	private VBox startKolom; // Voor dominotegels
	@FXML
	private HBox tegelEnKleurBox;
	@FXML
	private HBox eindkolom;
	@FXML
	private ImageView stapelRugzijdeImageView;
	@FXML
	private Button startRondeBtn;
	@FXML
	private Button volgendeRondeBtn;
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
	@FXML
	private Button rotateBtn;
	@FXML
	private Button acceptBtn;
	@FXML
	private AnchorPane root;
	@FXML
	private ImageView imageView;

	private DomeinController dc;
	private int aantalSpelers;
	private final WelkomKDController kdController;
	private List<Dominotegel> stapel;
	private Deque<Dominotegel> startKolomTegels;
	private Deque<Dominotegel> eindkolomTegels;
	private List<Dominotegel> gekozenTegels = new ArrayList<>();
	private List<String> spelerKleuren;
	private Map<Integer, Dominotegel> spelerTegelMap = new HashMap<>();
	private boolean isEersteClick = true; // Flag to track if it's the first button click
	private boolean isVolgendeRonde = false; // This flag will determine the button state

	private Set<Integer> startkolomSpelers = new HashSet<>();
	private Set<Integer> eindkolomSpelers = new HashSet<>();

	private List<Node> spelers;

	private List<SpelerDTO> gekozenSpelers;

	private int huidigeSpelerIndex = 0; // Standaardwaarde die aangeeft dat nog geen speler is geselecteerd
	private int tempSpelerIndex;

	public SpelController() {
		dc = new DomeinController();
		kdController = new WelkomKDController();
		aantalSpelers = kdController.getAantalSpelersGekozen();
		spelerKleuren = new ArrayList<>();
		eindkolomTegels = new ArrayDeque<>();
		startKolomTegels = new ArrayDeque<>();
		eindkolom = new HBox(5);
		tegelEnKleurBox = new HBox(5);
		gridGroen = new GridPane();
		gridBlauw = new GridPane();
		gridGeel = new GridPane();
		gridRoos = new GridPane();
		gekozenSpelers = dc.getSpelers();

	}

	public void initSpel() {
		setupAantalKingdoms();
		setSpelerInfo(spelerKleuren);
		stapel = dc.dominotegels(aantalSpelers);
		speelRonde(true, startKolomTegels);
		toonRugzijdeStapel();
		Platform.runLater(() -> {// zorgt ervoor dat dit pas te zien is wanneer het scherm volledig is geladen
			toonWelkomPopup();
		});

		speelRonde(false, eindkolomTegels);
		startRondeBtn.setDisable(false); // Zet de knop op actief
		volgendeRondeBtn.setDisable(true); // Zet de knop op actief
		rotateBtn.setDisable(true);

		gridBlauw.setGridLinesVisible(true);
		gridGroen.setGridLinesVisible(true);
		gridGeel.setGridLinesVisible(true);
		gridRoos.setGridLinesVisible(true);

		spelers = new ArrayList<>(spelerInformatieContainer.getChildren());
		Collections.shuffle(spelers);
	}

	@FXML
	//Methode om de anchorpane & achtergrond responsive te maken
	private void initialize() {
		imageView.fitWidthProperty().bind(root.widthProperty());
		imageView.fitHeightProperty().bind(root.heightProperty());

	}

	//Methode om correct aantal kingdoms te tonen
	public void setupAantalKingdoms() {
		int playerCount = aantalSpelers;
		//Zet de aangemaakte grids onzichtbaar
		gridGroen.setVisible(false);
		gridBlauw.setVisible(false);
		gridGeel.setVisible(false);
		gridRoos.setVisible(false);

		for (String color : spelerKleuren) {
			switch (color.toLowerCase().trim()) {
			case "groen":
				gridGroen.setVisible(true);
				break;
			case "blauw":
				gridBlauw.setVisible(true);
				break;
			case "geel":
				gridGeel.setVisible(true);
				break;
			case "roos":
				gridRoos.setVisible(true);
				break;
			}
		}
	}

	public void setSpelerInfo(List<String> spelerEnKleurInformatie) {
		spelerEnKleurInformatie.forEach(info -> {
			String[] parts = info.split("-");
			if (parts.length == 2) {
				String naam = parts[0];
				String kleur = parts[1];

				// Create a label for the player name
				Label label = new Label(naam);
				label.setStyle("-fx-text-fill: white; -fx-opacity: 0.95");
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
				//Hboxes met de speler informatie voor in het spel scherm
				HBox hbox = new HBox(60, circle, label);
				hbox.setAlignment(Pos.CENTER_LEFT);
				hbox.setPadding(new Insets(5, 10, 60, 10));
				hbox.setStyle(
						"-fx-border-color: black; -fx-border-width: 1; -fx-background-color: rgba(0, 0, 0, 0.1); -fx-border-radius: 5;");
				hbox.setMaxWidth(Double.MAX_VALUE);
				hbox.setUserData(info);
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
		startKolom.getChildren().clear();

		for (Dominotegel tegel : dominotegels) {
			// Maak en configureer de ImageView voor de tegel...
			ImageView tegelImageView = new ImageView(new Image(tegel.getVoorkantFotoPad()));
			tegelImageView.setFitWidth(90);
			tegelImageView.setFitHeight(45);

			// Maak een nieuwe HBox voor elke tegel...
			HBox tegelBox = new HBox(tegelImageView);
			tegelBox.setUserData(tegel); // Gebruik de Dominotegel als identifier

			// Voeg de HBox toe aan de container...
			startKolom.getChildren().add(tegelBox);
		}
	}

	public void toonStartKolom(List<Dominotegel> dominotegels) {
		HBox hbox = new HBox(10); // Gebruik een kleine spacing tussen de VBoxen

		VBox vboxVoorkant = new VBox(5); // Een beetje spacing voor esthetiek
		// VBox vboxAchterkant = new VBox(5);

		// Sorteer de lijst met dominotegels op het getal attribuut voordat je ze toont
		List<Dominotegel> gesorteerdeTegels = dominotegels.stream()
				.sorted(Comparator.comparingInt(Dominotegel::getGetal)).collect(Collectors.toList());

		//		for (Dominotegel tegel : gesorteerdeTegels) {
		//			Image voorkantImage = new Image(tegel.getVoorkantFotoPad());
		//			ImageView voorkantImageView = new ImageView(voorkantImage);
		//			// ImageView voorkantImageView = new ImageView(new
		//			// Image(tegel.getVoorkantFotoPad()));
		//			voorkantImageView.setFitWidth(150);
		//			voorkantImageView.setFitHeight(75);
		//			voorkantImageView.setOnMouseClicked(event -> kiesTegel(tegel, true));
		//			// Image achterkantImage = new Image(tegel.getAchterkantFotoPad());
		//			// ImageView achterkantImageView = new ImageView(achterkantImage);
		//			// achterkantImageView.setFitWidth(100);
		//			// achterkantImageView.setFitHeight(50);
		//
		//			vboxVoorkant.getChildren().add(voorkantImageView);
		//			// vboxAchterkant.getChildren().add(achterkantImageView);
		//		}
		for (Dominotegel tegel : gesorteerdeTegels) {
			Image voorkantImage = new Image(tegel.getVoorkantFotoPad());
			ImageView voorkantImageView = new ImageView(voorkantImage);
			voorkantImageView.setFitWidth(90);
			voorkantImageView.setFitHeight(45);

			// Toevoegen van een event handler voor muisklikken
			voorkantImageView.setOnMouseClicked(event -> {
				kiesTegel(tegel, true);
				toonSpelerKleurOpTegel(tegel, voorkantImageView);
			});

			vboxVoorkant.getChildren().add(voorkantImageView);
		}

		hbox.getChildren().addAll(vboxVoorkant /* , vboxAchterkant */);
		startKolom.getChildren().clear();
		startKolom.getChildren().add(hbox);
	}

	private void toonSpelerKleurOpTegel(Dominotegel tegel, ImageView imageView) {
		Color spelerKleur = getSpelerKleur(tempSpelerIndex); // Haal de kleur van de speler op
 
		// Maak een cirkel met de kleur van de speler
		Circle kleurIndicator = new Circle(10, spelerKleur);
		kleurIndicator.setStroke(Color.BLACK);

		// Gebruik een StackPane om de Circle bovenop de ImageView te plaatsen
		StackPane stackPane = new StackPane();
		stackPane.getChildren().add(imageView); // Voeg de ImageView toe aan StackPane
		stackPane.getChildren().add(kleurIndicator); // Voeg de kleurIndicator toe

		StackPane.setAlignment(kleurIndicator, Pos.TOP_RIGHT); // Positioneer de Circle

		// Vervang de ImageView met StackPane alleen als het nog niet eerder gedaan is
		if (!(imageView.getParent() instanceof StackPane)) {
			VBox parent = (VBox) imageView.getParent();
			int index = parent.getChildren().indexOf(imageView);
			parent.getChildren().remove(imageView);
			parent.getChildren().add(index, stackPane);
		}
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
			voorkantImageView.setFitWidth(90);
			voorkantImageView.setFitHeight(45);
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

		if (handleNullTegel(tegel) || tegelIsAlGekozen(tegel)) {
			return; // Als de tegel null is of al gekozen, stop de methode hier
		}

		tempSpelerIndex = huidigeSpelerIndex; // Leg de huidige spelerindex vast
		voegGekozenTegelToe(tegel);
		toonTegelEnSpelerKleur(tegel, isStartKolom); // Gebruik tempSpelerIndex binnen deze methode

		// Toevoegen van de huidige spelerindex aan de respectievelijke set
		if (isStartKolom) {
			startkolomSpelers.add(huidigeSpelerIndex);
		} else {
			eindkolomSpelers.add(huidigeSpelerIndex);
		}

		//		int[] positie = vraagTegelPositie(); // Veronderstel dat je een dialoogvenster toont
		//		if (positie != null) {
		//			plaatsTegelInGrid(tegel, positie[0], positie[1]);
		//		}

		//		showAlert("Draai tegel", "Je kan nu uw tegel draaien");
		//rotate();
		//		rotateImageView(tegel);
		//rotateTegel(rotateBtn);

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
		Color spelerKleur = getSpelerKleur(tempSpelerIndex); // Gebruik de tijdelijk opgeslagen index
		Circle kleurIndicator = new Circle(10, spelerKleur);
		kleurIndicator.setStroke(Color.BLACK);

		Image tegelAfbeelding = new Image(tegel.getVoorkantFotoPad());
		ImageView tegelImageView = new ImageView(tegelAfbeelding);
		tegelImageView.setFitWidth(90);
		tegelImageView.setFitHeight(45);

		HBox tegelEnKleurBox = new HBox(5, kleurIndicator, tegelImageView);

		VBox container = isStartKolom ? gekozenDominotegels : gekozenDominotegelsEindKolom;
		container.getChildren().add(tegelEnKleurBox);

		// Alert alert = new Alert(Alert.AlertType.INFORMATION);
		// alert.setTitle("Tegel Gekozen");
		// alert.setContentText("U heeft tegel " + tegel + " gekozen.");
		// alert.showAndWait();
	}

	private Color getSpelerKleur(int huidigeSpelerIndex) {
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
		String spelerInfo = null;
		for (int i = 0; i < spelers.size(); i++) {
			Node gekozenSpeler = spelers.get(i);
			spelerInfo = (String) gekozenSpeler.getUserData();

		}

		if (isStartKolom) {
			startkolomSpelers.add(huidigeSpelerIndex);
			if (startkolomSpelers.size() >= aantalSpelers) {
				toonEindkolom(new ArrayList<>(eindkolomTegels));
				toonRugzijdeStapel();
				isVolgendeRonde = true;
				huidigeSpelerIndex = 0; // Reset voor de eindkolom
			}
		} else {
			eindkolomSpelers.add(huidigeSpelerIndex);
			if (eindkolomSpelers.size() >= aantalSpelers) {
				plaatsAlleGekozenTegels(startKolomTegels, spelerInfo);
				startVolgendeRonde();
				volgendeRondeBtn.setDisable(false);
				return; // Voorkomt het dubbel aanroepen van vraagVolgendeSpeler.
			}
		}

		// Update de spelerindex cyclisch na elke actie
		huidigeSpelerIndex = (huidigeSpelerIndex + 1) % spelers.size();
		vraagVolgendeSpeler();
	}

	private void vraagVolgendeSpeler() {
		if (spelers.isEmpty()) {
			resetSpelersLijst(); // Vul de spelerslijst opnieuw als alle spelers geweest zijn
		}

        // Haal de huidige spelerinfo op basis van huidigeSpelerIndex
		Node gekozenSpeler = spelers.get(huidigeSpelerIndex);
		String spelerInfo = (String) gekozenSpeler.getUserData();

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Kies een Tegel");
		if (startkolomSpelers.size() == aantalSpelers) {
			alert.setContentText("Het is aan uw beurt speler: " + spelerInfo
					+ "\nKies één van de beschikbare tegels uit de eindkolom.");
		} else {
			alert.setContentText("Het is aan uw beurt speler: " + spelerInfo
					+ "\nKies één van de beschikbare tegels uit de startkolom.");
		}
		alert.showAndWait();
	}

	private void resetSpelersLijst() {
		spelers = new ArrayList<>(spelerInformatieContainer.getChildren());
		Collections.shuffle(spelers); // Shuffle om een nieuwe volgorde te garanderen
		startkolomSpelers.clear();
		eindkolomSpelers.clear();
	}

	private void startVolgendeRonde() {
		// Reset the game state for the next round, including clearing tracking sets
		startRondeBtn.setDisable(false);
		isVolgendeRonde = false;
		isEersteClick = true;
		huidigeSpelerIndex = 0; // Optionally reset to the first player
		startkolomSpelers.clear();
		eindkolomSpelers.clear();
		gekozenTegels.clear();
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
			//toonTegels(list); // Toon tegels met enkel de achterkant
		}

	}

	private void plaatsAlleGekozenTegels(Deque<Dominotegel> dominotegels, String spelerInfo) {
		for (Map.Entry<Integer, Dominotegel> entry : spelerTegelMap.entrySet()) {
			if (dominotegels.isEmpty())
				break; // Stop als er geen tegels meer zijn

			int spelerIndex = entry.getKey();
			Dominotegel tegel = dominotegels.pop(); // Haal de volgende tegel uit de deque

			Color spelerKleur = getSpelerKleur(spelerIndex); // Methode om de kleur van de speler te krijgen
			GridPane doelGridPane = bepaalDoelGridPane(spelerKleur);

			if (doelGridPane != null) {
				int[] positie = vraagTegelPositie(spelerInfo);
				if (positie != null) {
					plaatsTegelInGrid(tegel, doelGridPane, positie[0], positie[1]);
				}
			} else {
				System.out.println("Geen geldige GridPane gevonden voor speler met kleur: " + spelerKleur);
			}
		}
	}

	/*public void speelRonde(boolean isEersteRonde) {
		// Vul de eindkolom aan volgens DR_VUL_AAN
		if (isEersteRonde) {
			eindkolomTegels.clear();
			eindkolomTegels.addAll(stapel.subList(0, aantalSpelers));
			// Verwijder de toegevoegde tegels uit de stapel
			stapel.removeAll(eindkolomTegels);
		}
	
		// Bepaal de volgende speler aan de beurt volgens DR_RONDE_BEURT
		String volgendeSpelerInfo = (String) spelerInformatieContainer.getChildren().get(huidigeSpelerIndex).getUserData();
		System.out.println("Het is aan de beurt van speler: " + volgendeSpelerInfo);
	
		// Toon een overzicht volgens DR_SPEL_SITUATIE
		toonSpelSituatie();
		// De speler speelt zijn beurt
	// Hier voeg je logica toe om de speler zijn beurt te laten spelen, bijvoorbeeld door een tegel te kiezen en te plaatsen
	
	// Zolang niet elke koning in de eindkolom staat, keer terug naar stap 3
	while (!alleKoningenInEindkolom()) {
	    // Bepaal de volgende speler
	    huidigeSpelerIndex = (huidigeSpelerIndex + 1) % spelerInformatieContainer.getChildren().size();
	    volgendeSpelerInfo = (String) spelerInformatieContainer.getChildren().get(huidigeSpelerIndex).getUserData();
	    System.out.println("Het is aan de beurt van speler: " + volgendeSpelerInfo);
	    // Toon een overzicht volgens DR_SPEL_SITUATIE
	    toonSpelSituatie();
	    // De speler speelt zijn beurt
	    // Hier voeg je opnieuw logica toe om de speler zijn beurt te laten spelen
	}
	
	// Het systeem verplaatst elke dominotegel en zijn bijhorende koning van de eindkolom naar de startkolom
	Iterator<Dominotegel> iterator = eindkolomTegels.iterator();
	while (iterator.hasNext()) {
	    Dominotegel tegel = iterator.next();
	    startKolom.offer(tegel);
	    iterator.remove();
	} }
		*/

	private void toonResultaatVanRonde() {
		// Alert met: info van alle spelers + resterende tegels in stapel + startkolom
		Alert resultaatAlert = new Alert(Alert.AlertType.INFORMATION);
		resultaatAlert.setTitle("Resultaat van de ronde");
		resultaatAlert.setHeaderText(null);
		resultaatAlert.setContentText("De ronde is afgelopen. Hier is het resultaat van de ronde:\n\n" + "Spelers: "
				+ spelerInformatieContainer.getChildren() + "\n\n" + "Resterende tegels in de stapel: " + stapel.size()
				+ "\n\n" + "Startkolom: " + startKolomTegels);
		resultaatAlert.showAndWait();

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
			toonStartKolom(new ArrayList<>(startKolomTegels)); // Display the start column with tegels
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

	private int[] vraagTegelPositie(String spelerInfo) {
		while (true) { // Begin een oneindige lus die breekt wanneer geldige invoer wordt ontvangen
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Tegel Plaatsen");

			dialog.setHeaderText("Speler " + spelerInfo
					+ "Geef de rij en kolom in waar je de tegel wilt plaatsen, gescheiden door een komma (bijv. 1,2)");
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

	public boolean kanPlaatsen(DominotegelDTO tegelDTO, int y, int x, String richting, SpelerDTO spelerDTO) {
		//voorlopig
//		dc.kanPlaatsen(tegelDTO, y, x, richting, null);

		return false;
	}

	private void plaatsTegelInGrid(Dominotegel tegel, GridPane doelGridPane, int rij, int kolom) {
		Image tegelAfbeelding = new Image(tegel.getVoorkantFotoPad());
		ImageView tegelImageView = new ImageView(tegelAfbeelding);
		tegelImageView.setFitWidth(90); // Pas de grootte aan aan jouw grid
		tegelImageView.setFitHeight(45);

		GridPane doelPane = bepaalDoelGridPane(getSpelerKleur(huidigeSpelerIndex));
		doelGridPane.add(tegelImageView, kolom, rij);
		rotateBtn.setDisable(false);
	}

	private GridPane bepaalDoelGridPane(Color spelerKleur) {
		if (spelerKleur.equals(Color.GREEN)) {
			return gridGroen;
		} else if (spelerKleur.equals(Color.BLUE)) {
			return gridBlauw;
		} else if (spelerKleur.equals(Color.YELLOW)) {
			return gridGeel;
		} else if (spelerKleur.equals(Color.PINK)) {
			return gridRoos;
		} else {
			return null; // Or handle unexpected color
		}
	}

	private void rotate() {

	}

	@FXML
	private void rotateImageView(ActionEvent event) {
		Button clickedButton = (Button) event.getSource(); // Get the button that was clicked
		rotateTegel(clickedButton);
	}

	private void rotateTegel(Button clickedButton) {
		String buttonText = clickedButton.getText();

		// Get the currently selected tegel
		HBox selectedTegelBox = (HBox) startKolom.getChildren().get(huidigeSpelerIndex);
		ImageView selectedTegelImageView = (ImageView) selectedTegelBox.getChildren().get(0); // Assuming the ImageView is the second child

		// Rotate the tegel image based on the button clicked
		switch (buttonText) {
		case "rotate":
			// Rotate the image 90 degrees clockwise
			selectedTegelImageView.setRotate(selectedTegelImageView.getRotate() + 90);
			break;
		case "accept":
			// Accept the rotation and disable further rotation
			clickedButton.setDisable(true); // Disable the rotate button
			// Optionally, you may want to perform additional actions here, such as placing the tegel in the grid
			break;
		}
	}

	@FXML
	private void handleVolgendeRondeBtn(ActionEvent event) {

		//		//		toonStartKolom(new ArrayList<>());
		//		toonEindkolom(new ArrayList<>());
		//		gekozenDominotegels = new VBox();
		//		gekozenDominotegels.getChildren().clear();
		//		tegelEnKleurBox = new HBox();
		//
		//		toonTegelEnSpelerKleur(null, true);
		//
		//		System.out.println(gekozenDominotegels.getChildren());
		//		System.out.println(tegelEnKleurBox.getChildren());
		//
		//		startKolom.getChildren().clear();
		//
		//		//		System.out.println("op  handleVolgendeRondeBtn");
		//		//		System.out.println(startKolom);
		//		//		System.out.println(eindkolomTegels);
		//
		//		// Maak de nieuwe startkolom met de tegels die in de eindkolom (gekozenTegels) waren
		//		startKolom.clear(); // Zorg dat de startkolom leeg is
		//
		//		startKolom.addAll(eindkolomTegels); // Voeg de gekozen tegels toe aan de startkolom
		//		gekozenTegels.clear(); // Maak de lijst van gekozen tegels leeg voor de volgende ronde
		//
		//		// Bereid de nieuwe eindkolom voor
		//		eindkolomTegels.clear(); // Maak de eindkolom leeg voor de volgende ronde
		//		// Hier zou je de logica toevoegen om nieuwe tegels aan de eindkolom toe te voegen, indien nodig
		//
		//		// Toon de startkolom als de nieuwe set tegels die beschikbaar zijn om te kiezen
		//		toonStartKolom(new ArrayList<>(startKolom));
		//
		//		// Eventuele verdere initialisaties voor de nieuwe ronde
		//		speelRonde(false, startKolom);
	}

	public boolean isTegelCorrectGeplaatst(Dominotegel tegel, int i, int y) {
		return false;
	}

	public String bepaalWinnaar() {
		return null;
	}
}