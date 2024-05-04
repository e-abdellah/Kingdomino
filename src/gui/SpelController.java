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
	private VBox startKolom; // Voor dominotegels
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
	private Button rotateBtn;
	@FXML
	private Button acceptBtn;
	@FXML
	private AnchorPane root;
	@FXML
	private ImageView imageView;
	@FXML
	private Button skipRondeBtn;

	private DomeinController dc;
	private int aantalSpelers;
	private final WelkomKDController kdController;
	private List<Dominotegel> stapel;
	private Deque<Dominotegel> startKolomTegels;
	private Deque<Dominotegel> eindkolomTegels;
	private Deque<Dominotegel> gekozenTegelsStartkolom = new ArrayDeque<>();
	private List<Dominotegel> gekozenTegels = new ArrayList<>();
	private List<String> spelerKleuren;
	private Map<Integer, Dominotegel> spelerTegelMap = new HashMap<>();
	private Map<String, Dominotegel> spelerTegel = new TreeMap<>();
	private Map<SpelerDTO, Integer> indexSpelerDTO = new LinkedHashMap<>();
	private boolean isEersteClick = true; // Flag to track if it's the first button click
	private boolean isVolgendeRonde = false; // This flag will determine the button state
	private List<String> strings;
	private Map<ImageView, Integer> tegelRotaties = new HashMap<>();
	private Map<Dominotegel, ImageView> tegelViews = new HashMap<>();
	private Map<Integer, Dominotegel> tegelEnHoek = new HashMap<>();
	private List<Integer> hoeken = new ArrayList<>();

	private Set<Integer> startkolomSpelers = new HashSet<>();
	private Set<Integer> eindkolomSpelers = new HashSet<>();

	private List<Node> spelers;
	private List<Node> tijdelijkeKolomSpelers;
	private List<SpelerDTO> spelersDTO = new ArrayList<>();

	private List<SpelerDTO> gekozenSpelers;

	private int huidigeSpelerIndex = 0; // Standaardwaarde die aangeeft dat nog geen speler is geselecteerd
	// private int tempSpelerIndex;

	private int index = 0;
	private Dominotegel geselecteerdeTegel;
	private boolean eindkolomTegelGeklikkd = false;

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
		tijdelijkeKolomSpelers = new ArrayList<>(gekozenDominotegels.getChildren());
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
				//				initialiseGridPane(gridGroen);
				break;
			case "blauw":
				gridBlauw.setVisible(true);
				//				initialiseGridPane(gridBlauw);
				break;
			case "geel":
				gridGeel.setVisible(true);
				//				initialiseGridPane(gridGeel);
				break;
			case "roos":
				gridRoos.setVisible(true);
				//				initialiseGridPane(gridRoos);
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
		gridBlauw.setGridLinesVisible(false);
		gridGroen.setGridLinesVisible(false);
		gridGeel.setGridLinesVisible(false);
		gridRoos.setGridLinesVisible(false);

		// Use the new method to transfer children
		transferChildren(gekozenDominotegelsEindKolom, gekozenDominotegels, gekozenTegelsStartkolom);

		// Nieuwe tegels voor de ronde
		speelRonde(true, eindkolomTegels); // Tegels voor de eindkolom
		// toonEindkolom(new ArrayList<>(eindkolomTegels));

		toonRugzijdeStapel();
	}

	private void transferChildren(VBox source, VBox target, Deque<Dominotegel> targetTegels) {
		List<Node> children = new ArrayList<>(source.getChildren());

		// Clear the source to detach all children
		source.getChildren().clear();

		// Prepare to populate the Deque with Dominotegel objects
		for (Node child : children) {
			// Assuming each child Node has its associated Dominotegel stored as user data
			Dominotegel tegel = (Dominotegel) child.getUserData();
			if (tegel != null) {
				targetTegels.offer(tegel);
			}
		}

		// Add all detached children to the target
		target.getChildren().addAll(children);
	}

	public void speelRonde(boolean toonBeideZijden, Deque<Dominotegel> list) {
		for (int i = 0; i < aantalSpelers; i++) {
			Dominotegel tegel = stapel.get(0); // Haal de bovenste tegel van de stapel
			stapel.remove(0);
			list.offer(tegel);
		}
		if (stapel.isEmpty()) {
			showAlert("Einde spel", "Alle dominotegels zijn geplaats. \nKingdomino is beeindigt",
					AlertType.INFORMATION);
			return;
		}

		if (isVolgendeRonde && toonBeideZijden) {
			huidigeSpelerIndex = 0;
			toonEindkolom(new ArrayList<>(list));
			return;
		}

		// Toon de geselecteerde tegels in de GUI
		if (toonBeideZijden) {
			toonStartKolom(new ArrayList<>(list)); // Toon tegels met beide zijden
		} else {
			toonStartKolom(new ArrayList<>()); // Toon tegels met beide zijden
		}

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

		VBox vboxVoorkant = new VBox(20); // Een beetje spacing voor esthetiek
		// VBox vboxAchterkant = new VBox(5);

		// Sorteer de lijst met dominotegels op het getal attribuut voordat je ze toont
		List<Dominotegel> gesorteerdeTegels = dominotegels.stream()
				.sorted(Comparator.comparingInt(Dominotegel::getGetal)).collect(Collectors.toList());
		for (Dominotegel tegel : gesorteerdeTegels) {
			Image voorkantImage = new Image(tegel.getVoorkantFotoPad());
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

	public void toonEindkolom(List<Dominotegel> dominotegels) {
		Collections.sort(spelers,
				Comparator.comparing(speler -> spelerTegel.get(speler.getUserData().toString()).getGetal()));

		HBox hbox = new HBox(10); // Gebruik een kleine spacing tussen de VBoxen

		VBox vboxVoorkant = new VBox(20); // Een beetje spacing voor esthetiek
		// VBox vboxAchterkant = new VBox(5);

		// Sorteer de lijst met dominotegels op het getal attribuut voordat je ze toont
		List<Dominotegel> gesorteerdeTegels = dominotegels.stream()
				.sorted(Comparator.comparingInt(Dominotegel::getGetal)).collect(Collectors.toList());
		for (Dominotegel tegel : gesorteerdeTegels) {
			Image voorkantImage = new Image(tegel.getVoorkantFotoPad());
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

	private void clickOpTegel(Dominotegel tegel, boolean isStartKolom, int spelerIndex,
			boolean eindkolomTegelGeklikkd) {
		if (handleNullTegel(tegel) || tegelIsAlGekozen(tegel)) {
			return; // Als de tegel null is of al gekozen, stop de methode hier
		}
		this.geselecteerdeTegel = tegel; // Sla de geklikte tegel op als de huidig geselecteerde tegel

		// Haal de spelerNode op uit de geshuffelde lijst
		Node gekozenSpelerNode = spelers.get(huidigeSpelerIndex);
		String spelerInfo = (String) gekozenSpelerNode.getUserData();
		gekozenSpelerNode.getUserData().toString().split("-");
		String kleurCode = spelerInfo.split("-")[1].trim().toLowerCase();
		Color spelerKleur = getColorForName(kleurCode);

		if (!isStartKolom) {
			showAlert("Dominotegel plaatsten",
					"Draai uw tegel door erop te klikken\nVervolgens plaats de tegel in uw koninkrijk",
					AlertType.INFORMATION);
			toonTegelEnKleur(tegel, spelerKleur, isStartKolom, spelerIndex, true);
			//			kiesTegel(tegel, isStartKolom);
		}
		if (isStartKolom) {
			toonTegelEnKleur(tegel, spelerKleur, isStartKolom, spelerIndex, false);
			kiesTegel(tegel, isStartKolom);
		}

	}

	private void kiesTegel(Dominotegel tegel, boolean isStartKolom) {
		voegGekozenTegelToe(tegel);

		Node gekozenSpelerNode = spelers.get(huidigeSpelerIndex);
		String spelerInfo = (String) gekozenSpelerNode.getUserData();

		// Voeg de tegel toe aan de map met spelertegels
		spelerTegel.put(spelerInfo, tegel);
		strings = spelerTegel.keySet().stream().collect(Collectors.toList());
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
			showAlert("Tegel Al Gekozen", "Deze tegel is al gekozen. Kies een andere tegel.", AlertType.WARNING);
			return true;
		}
		return false;
	}

	private void voegGekozenTegelToe(Dominotegel tegel) {
		gekozenTegels.add(tegel);
		spelerTegelMap.put(huidigeSpelerIndex, tegel);
	}

	private void toonTegelEnKleur(Dominotegel tegel, Color spelerKleur, boolean isStartKolom, int spelerIndex,
			boolean eindkolomTegelNietGeklikkd) {
		// Gebruik de map om de ImageView op te halen
		ImageView tegelImageView = tegelViews.get(tegel);
		// Aanmaken of hergebruiken van de ImageView voor de dominotegel
		if (tegelImageView == null) {
			tegelImageView = new ImageView(new Image(tegel.getVoorkantFotoPad()));
			tegelImageView.setFitWidth(90);
			tegelImageView.setFitHeight(45);
			// Voeg de ImageView toe aan de map
			tegelViews.put(tegel, tegelImageView);
		}

		// Zet de afbeelding van de tegel
		Image tegelAfbeelding = new Image(tegel.getVoorkantFotoPad());
		tegelImageView.setImage(tegelAfbeelding);
		tegelImageView.setFitWidth(90);
		tegelImageView.setFitHeight(45);

		//Maakt de image aan die de koningen toont op de gekozen tegels
		String imagePath = getKoningBestandsnaam(spelerKleur);
		Image colorImage = new Image(getClass().getResourceAsStream(imagePath));
		ImageView kleurIndicator = new ImageView(colorImage);
		kleurIndicator.setFitWidth(30); // Adjust size as needed
		kleurIndicator.setFitHeight(30);

		VBox container;

		if (isStartKolom) {
			container = gekozenDominotegels;
		} else
			container = gekozenDominotegelsEindKolom;

		// Stel klik-handler in voor de tegel
		if (!isStartKolom && !eindkolomTegelNietGeklikkd) {
			container.getChildren().remove(tegelEnKleurBox);
			tegelImageView.setOnMouseClicked(event -> {
				rotate(tegel);
			});

		}

		// Creëer de ImageView voor de tegel

		if (isStartKolom && !isVolgendeRonde) {
			startkolomSpelers.add(huidigeSpelerIndex);
		} else if (!isStartKolom && !isVolgendeRonde) {
			eindkolomSpelers.add(huidigeSpelerIndex);
			//			rotate(tegel);
			plaatsAlleGekozenTegels(startKolomTegels, huidigeSpelerIndex);

		} else {
			if (isVolgendeRonde && gekozenTegelsStartkolom.isEmpty()) {
				for (Node child : gekozenDominotegels.getChildren()) {
					if (child instanceof HBox) {
						Dominotegel nieuwTegel = (Dominotegel) ((HBox) child).getUserData();
						if (nieuwTegel != null) {
							gekozenTegelsStartkolom.offer(nieuwTegel);
						}
					}
				}
			}
			plaatsAlleGekozenTegels(gekozenTegelsStartkolom, huidigeSpelerIndex);
		}

		// Creëer de HBox voor de tegel en de spelerkleur
		tegelEnKleurBox = new HBox(5, kleurIndicator, tegelImageView);
		System.out.println(tegelEnKleurBox);

		// Voeg de HBox toe aan de juiste container
		//		VBox container = isStartKolom ? gekozenDominotegels : gekozenDominotegelsEindKolom;

		tegelEnKleurBox.setUserData(tegel);
		container.getChildren().add(tegelEnKleurBox);
	}

	private void rotate(Dominotegel tegel) {
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
			//			tegelView.setRotationAxis();

			// Forceer een layout update
			tegelView.getParent().requestLayout();
			System.out.println("Rotated " + tegel + " to " + huidigeHoek + " degrees.");
			tegelEnHoek.put(huidigeHoek, tegel);
			hoeken.add(huidigeHoek);
		} else {
			System.out.println("No ImageView found for " + tegel);
		}
	}

	//	private Dominotegel getGeselecteerdeTegel() {
	//		Node gekozenSpelerNode = spelers.get(huidigeSpelerIndex);
	//		String spelerInfo = (String) gekozenSpelerNode.getUserData();
	//		String kleurCode = spelerInfo.split("-")[1].trim().toLowerCase();
	//		Color spelerKleur = getColorForName(kleurCode);
	//
	//		String kleur = spelerKleuren.get(huidigeSpelerIndex);
	//
	//		if (spelerKleur.toString() == kleur) {
	//			return Arrays.asList(startKolomTegels).get(huidigeSpelerIndex).getFirst();
	//			//			return startKolomTegels.stream().filter(spelers.get(huidigeSpelerIndex)).collect(Collectors.toList());
	//		}
	//		return null;
	//	}

	@FXML
	private void handleAcceptBtnAction(ActionEvent event) {
		// Bevestig de rotatie en ga verder met de volgende stap in het spel
		//		plaatsAlleGekozenTegels(startKolomTegels, huidigeSpelerIndex);
		// Update spelstatus of voer andere noodzakelijke acties uit
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

	//	private Color getSpelerKleur(int spelerIndex) {
	//		String kleurCode = spelerKleuren.get(spelerIndex).toLowerCase().trim();
	//
	//		switch (kleurCode) {
	//		case "groen":
	//			return Color.GREEN;
	//		case "geel":
	//			return Color.YELLOW;
	//		case "roos":
	//			return Color.PINK;
	//		case "blauw":
	//			return Color.BLUE;
	//		default:
	//			return Color.WHITE;
	//		}
	//	}

	private void updateSpelStatus(boolean isStartKolom) {

		if (isStartKolom && isVolgendeRonde == false) {
			startkolomSpelers.add(huidigeSpelerIndex);
			if (startkolomSpelers.size() >= aantalSpelers) {
				startKolom.getChildren().clear();
				huidigeSpelerIndex = -1; // Reset voor de eindkolom
				toonEindkolom(new ArrayList<>(eindkolomTegels));
				toonRugzijdeStapel();
				isVolgendeRonde = true;
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
			Dominotegel bovensteTegel = stapel.get(0); // Verkrijg de bovenste tegel zonder deze te verwijderen
			bovensteTegel.genereerFotoPaden(); // Zorg ervoor dat de paden gegenereerd zijn
			Image rugzijdeImage = new Image(bovensteTegel.getAchterkantFotoPad());
			stapelRugzijdeImageView.setImage(rugzijdeImage);
		} else {
			stapelRugzijdeImageView.setImage(null);
		}
	}

	private void plaatsAlleGekozenTegels(Deque<Dominotegel> dominotegels, int spelerIndex) {
		int index = 0;

		if (!dominotegels.isEmpty()) {
			Dominotegel eenTegel = dominotegels.pop(); // Haal de volgende tegel uit de deque

			Color spelerKleur = getKleur(spelerIndex); // Methode om de kleur van de speler te krijgen
			GridPane doelGridPane = bepaalDoelGridPane(spelerKleur);

			if (doelGridPane != null) {
				//				int[] startPos = { 0, 0 }; // Example starting position, it won't be used because autoFill is false
				//								int[] positie = vraagTegelPositie(index, false, startPos); // Adjusted call
				//								int[] positie = vraagTegelPositie(); // Adjusted call
				//				if (positie != null) {
				//					plaatsTegelInGrid(tegel, doelGridPane, positie[0], positie[1]);
				//				}
				initialiseGridPane(doelGridPane);
			} else {
				System.out.println("Geen geldige GridPane gevonden voor speler met kleur: " + spelerKleur);
			}
		}
	}

	private void initialiseGridPane(GridPane gridPane) {
		int numRows = gridPane.getRowConstraints().size();
		int numColumns = gridPane.getColumnConstraints().size();

		for (int row = 0; row < numRows; row++) {
			for (int column = 0; column < numColumns; column++) {
				final int finalRow = row; // Create final copies of row
				final int finalColumn = column; // and column for use in lambda

				Pane pane = new Pane();
				pane.setOnMouseClicked(event -> handleTegelPlaatsing(event, gridPane, finalRow, finalColumn));
				gridPane.add(pane, finalColumn, finalRow);
				pane.prefWidthProperty().bind(gridPane.widthProperty().divide(numColumns));
				pane.prefHeightProperty().bind(gridPane.heightProperty().divide(numRows));
			}
		}
	}

	private void handleTegelPlaatsing(MouseEvent event, GridPane gekliktGridPane, int rij, int kolom) {
		Node gekozenSpelerNode = spelers.get(huidigeSpelerIndex);
		String spelerInfo = (String) gekozenSpelerNode.getUserData();
		Color spelerKleur = getColorForName(spelerInfo.split("-")[1].trim().toLowerCase());

		GridPane spelerGridPane = bepaalDoelGridPane(spelerKleur);

		if (spelerGridPane != gekliktGridPane) {
			showAlert("Verkeerde Grid", "Je kunt je tegel alleen in je eigen grid plaatsen.", AlertType.WARNING);
		} else {
			plaatsTegelInGrid(geselecteerdeTegel, gekliktGridPane, rij, kolom);
			geselecteerdeTegel = null; // Reset na plaatsing
		}
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
		// Alert met: info van alle spelers + resterende tegels in stapel + startkolom

		showAlert("Resultaat van de ronde",
				"De ronde is afgelopen.\n\n" + "Spelers: "
						+ spelersDTO.stream().map(SpelerDTO::gebruikersnaam).collect(Collectors.toList()) + "\n\n"
						+ "Resterende tegels in de stapel: " + stapel.size(),
				AlertType.INFORMATION);

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
			int firstIndex = indexSpelerDTO.values().iterator().next();
			clickOpTegel(null, true, firstIndex, false);
			toonStartKolom(new ArrayList<>(startKolomTegels)); // Display the start column with tegels
			isEersteClick = false;
			startRondeButton.setDisable(true); // Deactiveer de knop
		}

	}

	private void showAlert(String title, String message, AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public boolean kanPlaatsen(DominotegelDTO tegelDTO, int y, int x, String richting, SpelerDTO spelerDTO) {
		// voorlopig
		// dc.kanPlaatsen(tegelDTO, y, x, richting, null);

		return false;
	}

	private void plaatsTegelInGrid(Dominotegel tegel, GridPane doelGridPane, int rij, int kolom) {
		Node gekozenSpelerNode = spelers.get(huidigeSpelerIndex);
		String spelerInfo = (String) gekozenSpelerNode.getUserData();
		String kleurCode = spelerInfo.split("-")[1].trim().toLowerCase();

		// Ophalen van de juiste Dominotegel en bijbehorende ImageView
		Dominotegel eentegel = tegel;
		ImageView tegelImageView = null;
		Color koning = null;

		if (!hoeken.isEmpty() && spelerTegel.containsKey(spelerInfo)
				&& kleurCode.equals(spelerInfo.split("-")[1].trim().toLowerCase())) {
			eentegel = tegelEnHoek.get(hoeken.get(0)); // Aanname: tegelEnHoek mapt index naar tegel
			tegelImageView = tegelViews.get(eentegel); // Aanname: tegelViews mapt Dominotegel naar ImageView
			hoeken.clear();
		}

		else if (hoeken.isEmpty()) {
			System.out.println("2de if");
			for (Map.Entry<String, Dominotegel> entry : spelerTegel.entrySet()) {

				String kleur = entry.getKey();
				kleur = kleur.split("-")[1].trim().toLowerCase();
				Dominotegel t = entry.getValue();
				if (kleur.equals(kleurCode)) {
					eentegel = t;
					tegelImageView = tegelViews.get(eentegel);
				}
			}
		}

		if (eentegel != null && tegelImageView != null) {
			System.out.println(eentegel);
			// Stel de afbeelding en grootte in van de ImageView
			tegelImageView.setImage(new Image(eentegel.getVoorkantFotoPad()));
			tegelImageView.setFitWidth(90);
			tegelImageView.setFitHeight(45);

			// Pas de opgeslagen rotatie toe
			Integer hoek = tegelRotaties.get(tegelImageView);
			if (hoek != null) {
				tegelImageView.setRotate(hoek);
			}

			// Voeg de ImageView toe aan de GridPane
			doelGridPane.add(tegelImageView, kolom, rij);
			System.out.println("Tegel geplaatst op rij: " + rij + ", kolom: " + kolom);
		} else {
			System.out.println("Geen geldige tegel of ImageView gevonden voor speler: " + spelerInfo);
		}

		System.out.println(tegelEnKleurBox);
		tegelEnKleurBox.getChildren().clear();
		gekozenDominotegels.getChildren().remove(tegelEnKleurBox);
		// Aanroepen van een methode die aangeeft dat de tegel is gekozen
		kiesTegel(tegel, false);
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
		} else {
			return null; // Or handle unexpected color
		}
	}

	@FXML
	private void handleVolgendeRondeBtn() {
		isVolgendeRonde = true;

		ronde();

		// Maak een nieuw Alert-venster
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Kies opnieuw een tegel");
		alert.setHeaderText(null);
		alert.setContentText("Kies opnieuw een tegel uit de eindkolom");

		// Toon het venster
		alert.showAndWait();
	}

	public boolean isTegelCorrectGeplaatst(Dominotegel tegel, int i, int y) {
		return false;
	}

	public String bepaalWinnaar() {
		return null;
	}

	public void setSpelers(List<SpelerDTO> spelers) {
		spelersDTO = spelers;
	}

	@FXML
	private void handleSkipRondeBtnAction(ActionEvent event) {
		//		int[] startPositie = { 0, 0 }; // Startpositie in 0-index formaat
		//
		//		for (Node speler : spelers) {
		//			if (!startKolomTegels.isEmpty()) {
		//				// Verkrijg de juiste GridPane gebaseerd op de kleur van de speler
		//				Color spelerKleur = getSpelerKleur(spelers.indexOf(speler)); // Verondersteld dat spelers een geordende lijst is
		//				GridPane doelGridPane = bepaalDoelGridPane(spelerKleur);
		//
		//				// Plaats de tegel in de grid op de startpositie
		//				plaatsTegelInGrid(startKolomTegels.pop(), doelGridPane, startPositie[0], startPositie[1]);
		//			}
		//		}
		ronde(); // Advance to the next round
	}

}
