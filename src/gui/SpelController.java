package gui;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import domein.DomeinController;
import domein.Speler;
import domein.Vakje;
import dto.DominotegelDTO;
import dto.SpelerDTO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;

public class SpelController {

	@FXML
	private Label spelLabel;
	@FXML
	private VBox spelerInformatieContainer; // Voor speler info
	@FXML
	private VBox startKolom;
	@FXML
	private HBox tegelEnKleurBox;
	@FXML
	private VBox eindkolom;
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
	private AnchorPane root;
	@FXML
	private ImageView imageView;
	@FXML
	private Button skipRondeBtn;

	private DomeinController dc;
	private int aantalSpelers;
	private final WelkomKDController kdController;
	private List<DominotegelDTO> stapel;
	private Deque<DominotegelDTO> startKolomTegels;
	private Deque<DominotegelDTO> eindkolomTegels;
	private Deque<DominotegelDTO> gekozenTegelsStartkolom = new ArrayDeque<>();
	private List<DominotegelDTO> gekozenTegels = new ArrayList<>();
	private List<String> spelerKleuren;
	private Map<Integer, DominotegelDTO> spelerTegelMap = new HashMap<>();
	private Map<String, DominotegelDTO> spelerTegel = new TreeMap<>();
	private Map<SpelerDTO, Integer> indexSpelerDTO = new LinkedHashMap<>();
	private boolean isVolgendeRonde = false;
	private Map<ImageView, Integer> tegelRotaties = new HashMap<>();
	private Map<DominotegelDTO, ImageView> tegelViews = new HashMap<>();
	private Map<Integer, DominotegelDTO> tegelEnHoek = new HashMap<>();
	private Map<String, DominotegelDTO> spelerTegelsEindkolom = new HashMap<>();
	private List<Integer> hoeken = new ArrayList<>();
	private Set<Integer> startkolomSpelers = new HashSet<>();
	private Set<Integer> eindkolomSpelers = new HashSet<>();

	private List<Node> spelers;
	private List<SpelerDTO> spelersDTO = new ArrayList<>();

	private List<SpelerDTO> gekozenSpelers;

	private int huidigeSpelerIndex = 0; // Standaardwaarde die aangeeft dat nog geen speler is geselecteerd

	private int index = 0;
	private int teller = 0;
	private Map<String, DominotegelDTO> tegelsEindkolomSpelers = new HashMap<>();
	private boolean isEindeSpel;

	public SpelController() {
		dc = DomeinController.getInstance();
		kdController = new WelkomKDController();
		spelerKleuren = new ArrayList<>();
		eindkolomTegels = new ArrayDeque<>();
		startKolomTegels = new ArrayDeque<>();
		eindkolom = new VBox(5);
		tegelEnKleurBox = new HBox(5);
		gridGroen = new GridPane();
		gridBlauw = new GridPane();
		gridGeel = new GridPane();
		gridRoos = new GridPane();
		aantalSpelers = kdController.getAantalSpelersGekozen();
		gekozenSpelers = dc.getSpelers();
		isEindeSpel = dc.isEindeSpel();
	}

	public void initSpel() {
		shuffleSpelers();
		initialiseerMap();
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
		// tijdelijkeKolomSpelers = new ArrayList<>(gekozenDominotegels.getChildren());
		gridBlauw.setGridLinesVisible(true);
		gridGroen.setGridLinesVisible(true);
		gridGeel.setGridLinesVisible(true);
		gridRoos.setGridLinesVisible(true);

	}

	private void initialiseerMap() {

		for (int i = 0; i < spelers.size(); i++) {
			Node gekozenSpelerNode = spelers.get(i);
			String spelerInfo = (String) gekozenSpelerNode.getUserData();
			String naam = spelerInfo.split("-")[0].trim().toLowerCase();

			for (SpelerDTO dto : gekozenSpelers) {
				if (naam.equals(dto.gebruikersnaam().toLowerCase())) {
					indexSpelerDTO.put(dto, i);
					spelersDTO.add(dto);
				}
			}
		}
	}

	@FXML
	// Methode om de anchorpane & achtergrond responsive te maken
	private void initialize() {
		imageView.fitWidthProperty().bind(root.widthProperty());
		imageView.fitHeightProperty().bind(root.heightProperty());
		root.getStylesheets().add(getClass().getResource("/gui/style.css").toExternalForm());

	}

	private void updateGridBorderKleur(String gridBorderSpelerKleur) {

		gridGroen.getStyleClass().clear();
		gridBlauw.getStyleClass().clear();
		gridGeel.getStyleClass().clear();
		gridRoos.getStyleClass().clear();

		gridGroen.getStyleClass().add("grid-inactive");
		gridBlauw.getStyleClass().add("grid-inactive");
		gridGeel.getStyleClass().add("grid-inactive");
		gridRoos.getStyleClass().add("grid-inactive");

		switch (gridBorderSpelerKleur) {
		case "groen":
			gridGroen.getStyleClass().add("grid-green");
			break;
		case "blauw":
			gridBlauw.getStyleClass().add("grid-blue");
			break;
		case "geel":
			gridGeel.getStyleClass().add("grid-yellow");
			break;
		case "roos":
			gridRoos.getStyleClass().add("grid-pink");
			break;
		default:
			break;
		}
	}

	private void shuffleSpelers() {
		// Sla de huidige volgorde op in de 'spelers' lijst
		spelers = new ArrayList<>(spelerInformatieContainer.getChildren());

		// Shuffle de 'spelers' lijst om een nieuwe volgorde te krijgen
		Collections.shuffle(spelers);

		// Update de UI met de nieuwe volgorde
		spelerInformatieContainer.getChildren().clear();
		spelerInformatieContainer.getChildren().addAll(spelers);
	}

	// Methode om correct aantal kingdoms te tonen
	public void setupAantalKingdoms() {
		// Zet de aangemaakte grids onzichtbaar

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

		for (String info : spelerEnKleurInformatie) {
			String[] parts = info.split("-");
			if (parts.length == 2) {
				String naam = parts[0].trim();
				String kleur = parts[1].trim();

				// Create a label for the player name
				Label label = new Label(naam);
				label.setStyle("-fx-text-fill: white; -fx-opacity: 0.95");

				// Create a circle with the player's color
				Circle circle = new Circle(10); // Circle with radius 10
				circle.setFill(getColorForName(kleur.toLowerCase().trim())); // Use a method to get the color

				spelerKleuren.add(kleur); // Store player color in the list

				// Hboxes with player information for the game screen
				HBox hbox = new HBox(60, circle, label);
				hbox.setAlignment(Pos.CENTER_LEFT);
				hbox.setPadding(new Insets(5, 10, 60, 10));
				hbox.setStyle(
						"-fx-border-color: black; -fx-border-width: 1; -fx-background-color: rgba(0, 0, 0, 0.1); -fx-border-radius: 5;");
				hbox.setMaxWidth(Double.MAX_VALUE);
				hbox.setUserData(info);
				spelerInformatieContainer.getChildren().add(hbox);
			}
		}
	}

	private Color getColorForName(String colorName) {
		return switch (colorName) {
		case "groen", "green" -> Color.GREEN;
		case "geel", "yellow" -> Color.YELLOW;
		case "roos", "pink" -> Color.PINK;
		case "blauw", "blue" -> Color.BLUE;
		default -> Color.WHITE;
		};
	}

	public void toonWelkomPopup() {
		showAlert("Welkom bij Kingdomino!", "Welkom bij het spel! Klik op 'Kies Tegel' om het spel te beginnen.",
				AlertType.INFORMATION);
	}

	private void toonEindSpelResultaten() {
		dc.berekenWinnaars();

		final String[] scores = { "Scores van alle spelers:\n" }; // Using an array to hold the string because it needs to be effectively final

		List<Speler> dtos = dc.refreshSpeler();

		dtos.stream().forEach(s -> {
			scores[0] += s.getGebruikersnaam() + " met " + s.getAantalGewonnen() + " spelletjes gewonnen en "
					+ s.getAantalGespeeld() + " spelletjes gespeeld met een score van " + s.berekenScore().get(0) + "\n";
		});

		showAlert("Het spel is gedaan", scores[0], AlertType.INFORMATION);

		//		showAlert("Winnaar",
		//				"De winnar is" + dc.geefScore().getGebruikersnaam() + "met score + " + s.berekenScore(),
		//				AlertType.INFORMATION);
	}

	private void ronde() {
		// Reset de ronde omgeving
		startKolom.getChildren().clear();
		eindkolom.getChildren().clear();
		gekozenDominotegels.getChildren().clear();
		startKolomTegels.clear();
		eindkolomTegels.clear();
		gekozenTegels.clear();
		startkolomSpelers.clear();
		eindkolomSpelers.clear();
		startRondeBtn.setDisable(false);
		volgendeRondeBtn.setDisable(true);

		// Clear grids' visibility for new round
		gridBlauw.setGridLinesVisible(false);
		gridGroen.setGridLinesVisible(false);
		gridGeel.setGridLinesVisible(false);
		gridRoos.setGridLinesVisible(false);

		transferChildren(gekozenDominotegelsEindKolom, gekozenDominotegels, gekozenTegelsStartkolom);

		if (!stapel.isEmpty()) {

			speelRonde(true, eindkolomTegels);
		}
		toonRugzijdeStapel();
	}

	private void transferChildren(VBox source, VBox target, Deque<DominotegelDTO> targetTegels) {
		List<Node> children = new ArrayList<>(source.getChildren());

		// Clear the source to detach all children
		source.getChildren().clear();

		// Prepare to populate the Deque with DominotegelDTO objects
		for (Node child : children) {
			// Assuming each child Node has its associated DominotegelDTO stored as user
			// data
			DominotegelDTO tegel = (DominotegelDTO) child.getUserData();
			if (tegel != null) {
				targetTegels.offer(tegel);
			}
		}

		// Add all detached children to the target
		target.getChildren().addAll(children);
	}

	public void speelRonde(boolean toonBeideZijden, Deque<DominotegelDTO> list) {
		int aantalTeTrekkenTegels = Math.min(aantalSpelers, stapel.size()); // Only draw as many tiles as available

		for (int i = 0; i < aantalTeTrekkenTegels; i++) {
			DominotegelDTO tegel = stapel.remove(0); // Remove the tile from the stack
			list.offer(tegel);
		}

		// Check if the stack is empty after drawing the tiles
		if (stapel.isEmpty()) {
			isEindeSpel = true;
			showAlert("Einde spel", "Alle dominotegels zijn geplaatst. \nKingdomino is beëindigd",
					AlertType.INFORMATION);
			toonEindSpelResultaten();
			return;
		}

		// Only proceed to show the dominos if there are tiles left or if it's part of the ongoing round logic
		if (!isVolgendeRonde || toonBeideZijden) {
			if (isVolgendeRonde) {
				huidigeSpelerIndex = 0;
				toonEindkolom(new ArrayList<>(list)); // Show tiles for the end column
			} else {
				toonStartKolom(new ArrayList<>(list)); // Show tiles for the start column
			}
		} else {
			// This else part can be used to handle scenarios where you don't want to show any tiles but need to perform other updates
		}
	}

	public void toonTegels(Deque<DominotegelDTO> dominotegels) {
		startKolom.getChildren().clear();

		for (DominotegelDTO tegel : dominotegels) {
			// Maak en configureer de ImageView voor de tegel...
			ImageView tegelImageView = new ImageView(new Image(tegel.voorkantFotoPad()));
			tegelImageView.setFitWidth(90);
			tegelImageView.setFitHeight(45);

			// Maak een nieuwe HBox voor elke tegel...
			HBox tegelBox = new HBox(tegelImageView);
			tegelBox.setUserData(tegel); // Gebruik de DominotegelDTO als identifier

			// Voeg de HBox toe aan de container...
			startKolom.getChildren().add(tegelBox);
		}
	}

	public void toonStartKolom(List<DominotegelDTO> dominotegels) {
		HBox hbox = new HBox(10); // Gebruik een kleine spacing tussen de VBoxen

		VBox vboxVoorkant = new VBox(20); // Een beetje spacing voor esthetiek
		// VBox vboxAchterkant = new VBox(5);

		// Sorteer de lijst met dominotegels op het getal attribuut voordat je ze toont
		List<DominotegelDTO> gesorteerdeTegels = dominotegels.stream()
				.sorted(Comparator.comparingInt(DominotegelDTO::getal)).collect(Collectors.toList());
		for (DominotegelDTO tegel : gesorteerdeTegels) {
			Image voorkantImage = new Image(tegel.voorkantFotoPad());
			ImageView voorkantImageView = new ImageView(voorkantImage);
			voorkantImageView.setFitWidth(90);
			voorkantImageView.setFitHeight(45);

			// Toevoegen van een event handler voor muisklikken
			voorkantImageView.setOnMouseClicked(event -> {
				clickOpTegel(tegel, true, indexSpelerDTO.get(spelersDTO.get(index)), false);
			});

			vboxVoorkant.getChildren().add(voorkantImageView);
			index++;
		}
		index = 0;

		hbox.getChildren().addAll(vboxVoorkant /* , vboxAchterkant */);
		startKolom.getChildren().clear();
		startKolom.getChildren().add(hbox);
	}

	public void toonEindkolom(List<DominotegelDTO> dominotegels) {
		Collections.sort(spelers,
				Comparator.comparing(speler -> spelerTegel.get(speler.getUserData().toString()).getal()));

		HBox hbox = new HBox(10); // Gebruik een kleine spacing tussen de VBoxen

		VBox vboxVoorkant = new VBox(20); // Een beetje spacing voor esthetiek
		// VBox vboxAchterkant = new VBox(5);

		// Sorteer de lijst met dominotegels op het getal attribuut voordat je ze toont
		List<DominotegelDTO> gesorteerdeTegels = dominotegels.stream()
				.sorted(Comparator.comparingInt(DominotegelDTO::getal)).collect(Collectors.toList());
		for (DominotegelDTO tegel : gesorteerdeTegels) {
			Image voorkantImage = new Image(tegel.voorkantFotoPad());
			ImageView voorkantImageView = new ImageView(voorkantImage);
			voorkantImageView.setFitWidth(90);
			voorkantImageView.setFitHeight(45);
			voorkantImageView.setOnMouseClicked(event -> {
				clickOpTegel(tegel, false, indexSpelerDTO.get(spelersDTO.get(index)), true);

			});

			vboxVoorkant.getChildren().add(voorkantImageView);
			index++;
		}
		index = 0;

		hbox.getChildren().addAll(vboxVoorkant/* , vboxAchterkant */);
		eindkolom.getChildren().clear();
		eindkolom.getChildren().add(hbox);
	}

	private void clickOpTegel(DominotegelDTO tegel, boolean isStartKolom, int spelerIndex,
			boolean eindkolomTegelGeklikkd) {
		if (handleNullTegel(tegel) || tegelIsAlGekozen(tegel)) {
			return; // Als de tegel null is of al gekozen, stop de methode hier
		}

		// Haal de spelerNode op uit de geshuffelde lijst
		Node gekozenSpelerNode = spelers.get(huidigeSpelerIndex);
		String spelerInfo = (String) gekozenSpelerNode.getUserData();
		gekozenSpelerNode.getUserData().toString().split("-");
		String kleurCode = spelerInfo.split("-")[1].trim().toLowerCase();
		Color spelerKleur = getColorForName(kleurCode);

		updateGridBorderKleur(kleurCode);
		if (!isStartKolom) {
			showAlert("Dominotegel plaatsten",
					"Draai uw tegel door erop te klikken\nVervolgens plaats de tegel in uw koninkrijk",
					AlertType.INFORMATION);
			toonTegelEnKleur(tegel, spelerKleur, isStartKolom, spelerIndex, true);

			if (!isVolgendeRonde) {
				spelerTegelsEindkolom.put(spelerInfo, tegel);
			}
			if (teller == 2) {
				spelerTegelsEindkolom.put(spelerInfo, tegel);
			}

		}
		if (isStartKolom) {
			toonTegelEnKleur(tegel, spelerKleur, isStartKolom, spelerIndex, false);
			kiesTegel(tegel, isStartKolom);
		}

	}

	private void kiesTegel(DominotegelDTO tegel, boolean isStartKolom) {
		voegGekozenTegelToe(tegel);

		Node gekozenSpelerNode = spelers.get(huidigeSpelerIndex);
		String spelerInfo = (String) gekozenSpelerNode.getUserData();

		// Voeg de tegel toe aan de map met spelertegels
		spelerTegel.put(spelerInfo, tegel);
		updateSpelStatus(isStartKolom);
	}

	private boolean handleNullTegel(DominotegelDTO tegel) {
		if (tegel == null) {
			vraagVolgendeSpeler();
			return true;
		}
		return false;
	}

	private boolean tegelIsAlGekozen(DominotegelDTO tegel) {
		if (gekozenTegels.contains(tegel)) {
			showAlert("Tegel Al Gekozen", "Deze tegel is al gekozen. Kies een andere tegel.", AlertType.WARNING);
			return true;
		}
		return false;
	}

	private void voegGekozenTegelToe(DominotegelDTO tegel) {
		gekozenTegels.add(tegel);
		spelerTegelMap.put(huidigeSpelerIndex, tegel);
	}

	private void toonTegelEnKleur(DominotegelDTO tegel, Color spelerKleur, boolean isStartKolom, int spelerIndex,
			boolean eindkolomTegelNietGeklikkd) {

		Node gekozenSpelerNode = spelers.get(huidigeSpelerIndex);
		String spelerInfo = (String) gekozenSpelerNode.getUserData();

		// Gebruik de map om de ImageView op te halen
		ImageView tegelImageView = tegelViews.get(tegel);
		// Aanmaken of hergebruiken van de ImageView voor de dominotegel
		if (tegelImageView == null) {
			tegelImageView = new ImageView(new Image(tegel.voorkantFotoPad()));
			tegelImageView.setFitWidth(90);
			tegelImageView.setFitHeight(45);
			// Voeg de ImageView toe aan de map
			tegelViews.put(tegel, tegelImageView);
		}

		// Zet de afbeelding van de tegel
		Image tegelAfbeelding = new Image(tegel.voorkantFotoPad());
		tegelImageView.setImage(tegelAfbeelding);
		tegelImageView.setFitWidth(90);
		tegelImageView.setFitHeight(45);

		// Maakt de image aan die de koningen toont op de gekozen tegels
		String imagePath = getKoningBestandsnaam(spelerKleur);
		Image colorImage = new Image(getClass().getResourceAsStream(imagePath));
		ImageView kleurIndicator = new ImageView(colorImage);
		kleurIndicator.setFitWidth(30); // Adjust size as needed
		kleurIndicator.setFitHeight(30);

		tegelEnKleurBox = new HBox(5, kleurIndicator, tegelImageView);
		tegelEnKleurBox.setUserData(tegel);

		if (isStartKolom) {
			gekozenDominotegels.getChildren().add(tegelEnKleurBox);
		} else {
			gekozenDominotegelsEindKolom.getChildren().add(tegelEnKleurBox);
			tegelsEindkolomSpelers.put(spelerInfo, tegel);
		}

		// Stel klik-handler in voor de tegel
		// if (!eindkolomTegelNietGeklikkd || isVolgendeRonde) {
		tegelImageView.setOnMouseClicked(event -> {
			rotate(tegel);
		});

		// }

		if (isVolgendeRonde) {
			if (isVolgendeRonde && startKolomTegels.isEmpty()) {
				System.out.println("SK tegels waren leeg");
				for (Node child : gekozenDominotegels.getChildren()) {
					if (child instanceof HBox) {
						DominotegelDTO nieuwTegel = (DominotegelDTO) ((HBox) child).getUserData();
						if (nieuwTegel != null) {
							startKolomTegels.offer(nieuwTegel);
						}
					}
				}
			}
			plaatsAlleGekozenTegels(startKolomTegels, huidigeSpelerIndex, tegel);
		}

		if (isStartKolom && !isVolgendeRonde) {
			startkolomSpelers.add(huidigeSpelerIndex);
		}

		if (!isStartKolom && !isVolgendeRonde) {
			eindkolomSpelers.add(huidigeSpelerIndex);
			plaatsAlleGekozenTegels(startKolomTegels, huidigeSpelerIndex, tegel);

		}
	}

	private void rotate(DominotegelDTO tegel) {
		ImageView tegelView = tegelViews.get(tegel);
		if (tegelView != null) {
			Integer huidigeHoek = tegelRotaties.getOrDefault(tegelView, 0);
			huidigeHoek = (huidigeHoek + 90) % 360;
			tegelRotaties.put(tegelView, huidigeHoek);
			tegelView.setRotate(huidigeHoek);

			// Stel pivot points in
			Rotate rotation = new Rotate();
			rotation.setPivotX(tegelView.getFitWidth() / 2);
			rotation.setPivotY(tegelView.getFitHeight() / 2);
			// tegelView.setRotationAxis();

			// Forceer een layout update
			tegelView.getParent().requestLayout();
			System.out.println("Rotated " + tegel + " to " + huidigeHoek + " degrees.");
			tegelEnHoek.put(huidigeHoek, tegel);
			hoeken.add(huidigeHoek);
			if (!hoeken.isEmpty())
				hoeken.set(0, huidigeHoek);
		} else {
			System.out.println("No ImageView found for " + tegel);
		}
	}

	private String getKoningBestandsnaam(Color color) {
		if (color.equals(Color.GREEN)) {
			return "/imgs/kings_green.png";
		} else if (color.equals(Color.BLUE)) {
			return "/imgs/kings_blue.png";
		} else if (color.equals(Color.YELLOW)) {
			return "/imgs/kings_yellow.png";
		} else if (color.equals(Color.PINK)) {
			return "/imgs/kings_pink.png";
		} else {
			return "default_image.png";

		}
	}

	private void updateSpelStatus(boolean isStartKolom) {

		if (isStartKolom && isVolgendeRonde == false) {
			startkolomSpelers.add(huidigeSpelerIndex);
			if (startkolomSpelers.size() >= aantalSpelers) {
				System.out.println(startkolomSpelers.size() + "#" + aantalSpelers);
				startKolom.getChildren().clear();
				huidigeSpelerIndex = -1; // Reset voor de eindkolom
				toonEindkolom(new ArrayList<>(eindkolomTegels));
				toonRugzijdeStapel();
			}
		} else {
			eindkolomSpelers.add(huidigeSpelerIndex);
			if (eindkolomSpelers.size() >= aantalSpelers) {
				// startVolgendeRonde();
				toonResultaatVanRonde();
				showAlert("Volgende ronde", "Deze ronde is klaar. Klik op de volgende ronde knop",
						AlertType.INFORMATION);
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
		Node gekozenSpeler = spelers.get(huidigeSpelerIndex);
		String spelerInfo = (String) gekozenSpeler.getUserData();
		String spelerNaam = spelerInfo.split("-")[0].trim();
		String kleurCode = spelerInfo.split("-")[1].trim().toLowerCase();
		Color spelerKleur = getColorForName(kleurCode);
		updateGridBorderKleur(kleurCode);

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Kies een Tegel");
		alert.setGraphic(new Circle(10, spelerKleur)); // Set the graphic manually here
		if (startkolomSpelers.size() == aantalSpelers || isVolgendeRonde) {
			alert.setContentText("Het is aan uw beurt speler: " + spelerNaam
					+ "\nKies één van de beschikbare tegels uit de eindkolom.");
		} else {
			alert.setContentText("Het is aan uw beurt speler: " + spelerNaam
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

	public void toonRugzijdeStapel() {
		if (!stapel.isEmpty()) {
			DominotegelDTO bovensteTegel = stapel.get(0);
			Image rugzijdeImage = new Image(bovensteTegel.achterkantFotoPad());
			stapelRugzijdeImageView.setImage(rugzijdeImage);
		} else {
			stapelRugzijdeImageView.setImage(null);
		}
	}

	private void plaatsAlleGekozenTegels(Deque<DominotegelDTO> dominotegels, int spelerIndex, DominotegelDTO tegel) {
		Color spelerKleur = getKleur(spelerIndex); // Methode om de kleur van de speler te krijgen
		GridPane doelGridPane = bepaalDoelGridPane(spelerKleur);
		initialiseGridPane(doelGridPane, spelerIndex, tegel);

	}

	private void initialiseGridPane(GridPane gridPane, int spelerIndex, DominotegelDTO tegel) {
		int numRows = gridPane.getRowConstraints().size();
		int numColumns = gridPane.getColumnConstraints().size();

		for (int rij = 0; rij < numRows; rij++) {
			for (int kolom = 0; kolom < numColumns; kolom++) {
				final int finalRow = rij; // Create final copies of row
				final int finalColumn = kolom; // and column for use in lambda

				Pane pane = new Pane();
				pane.setOnMouseClicked(event -> handleTegelPlaatsing(event, gridPane, finalRow, finalColumn,
						spelerIndex, tegel, imageView));

				gridPane.add(pane, finalColumn, finalRow);
				pane.prefWidthProperty().bind(gridPane.widthProperty().divide(numColumns));
				pane.prefHeightProperty().bind(gridPane.heightProperty().divide(numRows));
			}
		}
	}

	private void handleTegelPlaatsing(MouseEvent event, GridPane gekliktGridPane, int rij, int kolom, int spelerIndex,
			DominotegelDTO tegel, ImageView tegelImageView) {
		Node gekozenSpelerNode = spelers.get(huidigeSpelerIndex);
		String spelerInfo = (String) gekozenSpelerNode.getUserData();
		Color spelerKleur = getColorForName(spelerInfo.split("-")[1].trim().toLowerCase());
		String kleurCode = spelerInfo.split("-")[1].trim().toLowerCase();

		GridPane spelerGridPane = bepaalDoelGridPane(spelerKleur);

		if (!hoeken.isEmpty() && spelerTegel.containsKey(spelerInfo)
				&& kleurCode.equals(spelerInfo.split("-")[1].trim().toLowerCase())) {
			tegel = tegelEnHoek.get(hoeken.get(0));
			tegelImageView = tegelViews.get(tegel);
		} else if (hoeken.isEmpty()) {
			for (Map.Entry<String, DominotegelDTO> entry : spelerTegel.entrySet()) {
				String kleur = entry.getKey().split("-")[1].trim().toLowerCase();
				DominotegelDTO t = entry.getValue();
				if (kleur.equals(kleurCode)) {
					tegel = t;
					tegelImageView = tegelViews.get(tegel);
				}
			}
		}

		ImageView newTegelImg = new ImageView();

		if (tegelImageView != null) {
			// Maak een kopie van de ImageView
			newTegelImg.setImage(tegelImageView.getImage());
			newTegelImg.setFitWidth(tegelImageView.getFitWidth());
			newTegelImg.setFitHeight(tegelImageView.getFitHeight());

			// Pas de opgeslagen rotatie toe op de kopie
			Integer hoek = tegelRotaties.get(tegelImageView);
			if (hoek != null) {
				newTegelImg.setRotate(hoek);
				if (hoek == 90 || hoek == 270) {
					// Aanpassing voor rotatie, verschuif de afbeelding -0.25 naar links en 0.5 naar
					// beneden
					newTegelImg.setTranslateX(-0.25 * newTegelImg.getFitWidth());
					newTegelImg.setTranslateY(0.5 * newTegelImg.getFitHeight());
				}

			}
		}

		if (spelerGridPane != gekliktGridPane) {
			showAlert("Verkeerde Grid", "Je kunt je tegel alleen in je eigen grid plaatsen.", AlertType.WARNING);
		} else {

			SpelerDTO gevondenSpeler = dc.refreshSpelerDTO().get(0);
			Map<SpelerDTO, Integer> temp = new HashMap<>();
			for (Map.Entry<SpelerDTO, Integer> entry : indexSpelerDTO.entrySet()) {

				SpelerDTO tempDTO = null;

				for (SpelerDTO dto : dc.refreshSpelerDTO()) {

					if (dto.gebruikersnaam() == entry.getKey().gebruikersnaam()) {
						temp.put(dto, entry.getValue());
						tempDTO = dto;
					}
				}

				if (spelerIndex == entry.getValue()) {
					gevondenSpeler = tempDTO;
				}
			}
			// System.out.println(
			// kanPlaatsen(spelerTegel.get(spelerInfo), kolom, rij, hoeken.get(spelerIndex),
			// gevondenSpeler) + ":"
			// + kolom + ":" + rij);
			if (kanPlaatsen(tegel, kolom, rij, !hoeken.isEmpty() ? hoeken.get(0) : 0, gevondenSpeler)) {
				plaatsTegelInGrid(tegel, gekliktGridPane, rij, kolom, newTegelImg);
				dc.plaatsTegel(tegel, kolom, rij, !hoeken.isEmpty() ? hoeken.get(0) : 0, gevondenSpeler);
				Vakje[][] koninkrijk = gevondenSpeler.koninkrijk();

				for (int xx = 0; xx < koninkrijk.length; xx++) {
					for (int yy = 0; yy < koninkrijk[xx].length; yy++) {
						System.out.printf("%10s",
								koninkrijk[xx][yy] != null ? koninkrijk[xx][yy].getAantalKronen() : "");
					}
					System.out.println(); // Na elke rij van vakjes, een nieuwe regel toevoegen

				}
				hoeken.clear();
			}

		}
	}

	public boolean kanPlaatsen(DominotegelDTO tegel, int y, int x, int hoek, SpelerDTO spelerDTO) {

		return dc.kanPlaatsen(tegel, y, x, hoek, spelerDTO);
	}

	private void plaatsTegelInGrid(DominotegelDTO tegel, GridPane doelGridPane, int rij, int kolom,
			ImageView newTegelImg) {
		Node gekozenSpelerNode = spelers.get(huidigeSpelerIndex);
		String spelerInfo = (String) gekozenSpelerNode.getUserData();

		if (newTegelImg != null) {
			doelGridPane.add(newTegelImg, kolom, rij); // rji en kolom = ints
			System.out.println("Tegel geplaatst op rij: " + rij + ", kolom: " + kolom);
		} else {
			System.out.println("Geen geldige tegel of ImageView gevonden voor speler: " + spelerInfo + "tegel: " + tegel
					+ "Nimg: " + newTegelImg);
		}

		kiesTegel(tegel, false);

		spelerTegel.put(spelerInfo, tegelsEindkolomSpelers.get(spelerInfo));

	}

	private GridPane bepaalDoelGridPane(Color spelerKleur) {
		if (spelerKleur.equals(Color.GREEN)) {
			gridGroen.setGridLinesVisible(true);
			return gridGroen;
		} else if (spelerKleur.equals(Color.BLUE)) {
			gridBlauw.setGridLinesVisible(true);
			return gridBlauw;
		} else if (spelerKleur.equals(Color.YELLOW)) {
			gridGeel.setGridLinesVisible(true);
			return gridGeel;
		} else if (spelerKleur.equals(Color.PINK)) {
			gridRoos.setGridLinesVisible(true);
			return gridRoos;
		}
		return null;
	}

	private Color getKleur(int spelerIndex) {

		Node gekozenSpelerNode = spelers.get(spelerIndex);
		String spelerInfo = (String) gekozenSpelerNode.getUserData();
		String kleurCode = spelerInfo.split("-")[1].trim().toLowerCase();

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

	private void toonResultaatVanRonde() {
		showAlert("Resultaat van de ronde",
				"De ronde is afgelopen.\n\n" + "Spelers: "
						+ spelersDTO.stream().map(SpelerDTO::gebruikersnaam).collect(Collectors.toList()) + "\n\n"
						+ "Resterende tegels in de stapel: " + stapel.size(),
				AlertType.INFORMATION);

	}

	public void setAantalSpelers(int aantal) {
		this.aantalSpelers = aantal;
	}

	public List<DominotegelDTO> getStapel() {
		return stapel;
	}

	@FXML
	private void handleStartRondeBtnAction(ActionEvent event) {
		Button startRondeButton = (Button) event.getSource(); // Get the button that was clicked

		if (!spelers.isEmpty()) {
			Node eersteSpelerKleur = spelers.get(0); // Assuming the first player starts
			String eersteSpelerInfo = (String) eersteSpelerKleur.getUserData();
			String firstPlayerColor = eersteSpelerInfo.split("-")[1].trim().toLowerCase();
			updateGridBorderKleur(firstPlayerColor);
		}

		if (!isVolgendeRonde) {
			int i = indexSpelerDTO.values().iterator().next();
			clickOpTegel(null, true, i, false);
			toonStartKolom(new ArrayList<>(startKolomTegels)); // Display the start column with tegels
			startRondeButton.setDisable(true); // Deactiveer de knop
		}
	}

	private void showAlert(String title, String message, AlertType alertType) { // helper meth
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
	}

	@FXML
	private void handleVolgendeRondeBtn() {
		isVolgendeRonde = true;
		teller++;
		ronde();
		updateGridBorderKleur("");
		showAlert("Kies opnieuw een tegel", "Kies opnieuw een tegel uit de eindkolom", AlertType.INFORMATION);
	}

	public void setSpelers(List<SpelerDTO> spelers) {
		spelersDTO = spelers;
	}

	@FXML
	private void handleSkipRondeBtnAction(ActionEvent event) {
		ronde(); // Advance to the next round
	}

}
