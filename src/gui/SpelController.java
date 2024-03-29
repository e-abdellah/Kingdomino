package gui;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.stream.Collectors;

import domein.DomeinController;
import domein.Dominotegel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
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
	@FXML
	private ImageView stapelRugzijdeImageView;

	private Locale locale;

	@FXML
	private Button startRondeBtn;

	public SpelController() {
		dc = new DomeinController();
		kdController = new WelkomKDController();
		aantalSpelers = kdController.getAantalSpelersGekozen();
		startKolom = new ArrayList<>(); 
	    gekozenTegels = new ArrayDeque<>(); 
	}
	

	public void initSpel() {
		// Verplaats logica die afhankelijk is van aantalSpelers en andere init-waarden hier
		stapel = dc.schudDominotegels(aantalSpelers);
		speelRonde(false);
		toonRugzijdeStapel();
		startKolom = new ArrayList<>();
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
		//	ImageView voorkantImageView = new ImageView(new Image(tegel.getVoorkantFotoPad()));
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
	    // Controleer of de tegel al gekozen is
	    if (gekozenTegels.contains(tegel)) {
	        // Toon een bericht dat deze tegel al gekozen is
	        Alert alert = new Alert(Alert.AlertType.WARNING);
	        alert.setTitle("Tegel Al Gekozen");
	        alert.setHeaderText(null);
	        alert.setContentText("Deze tegel is al gekozen. Kies een andere tegel.");
	        alert.showAndWait();
	    } else {
	        // Markeer de tegel als gekozen en voeg toe aan de lijst
	        gekozenTegels.add(tegel);

	        // Toon een bevestiging aan de gebruiker
	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle("Tegel Gekozen");
	        alert.setHeaderText(null);
	        alert.setContentText("Je hebt tegel " + tegel + " gekozen.");
	        alert.showAndWait();
	        System.out.println("Gekozen tegel: " + tegel.toString());

	        // Voeg hier eventuele verdere acties toe, zoals het bijwerken van de GUI
	    }
	}


	
	public void toonRugzijdeStapel() {
	    if (!stapel.isEmpty()) {
	        Dominotegel bovensteTegel = stapel.peek(); // Verkrijg de bovenste tegel zonder deze te verwijderen
	        bovensteTegel.genereerFotoPaden(); // Zorg ervoor dat de paden gegenereerd zijn
	        Image rugzijdeImage = new Image(bovensteTegel.getAchterkantFotoPad());
	        stapelRugzijdeImageView.setImage(rugzijdeImage);
	    } else {
	        // Optioneel: toon een standaardafbeelding of verberg de ImageView als de stapel leeg is
	        stapelRugzijdeImageView.setImage(null); // Of een standaardafbeelding
	    }
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
		  if (startKolom.isEmpty()) {
		        // Toon een bericht dat er geen tegels zijn om uit te kiezen of handel dit anderszins af
		        Alert alert = new Alert(Alert.AlertType.INFORMATION);
		        alert.setTitle("Geen Tegels Beschikbaar");
		        alert.setHeaderText(null);
		        alert.setContentText("Er zijn momenteel geen tegels beschikbaar om uit te kiezen.");
		        alert.showAndWait();
		        return null; // Terugkeren aangezien er niets te kiezen valt
		    }
	    List<String> keuzes = startKolom.stream()
	                                     .map(Dominotegel::toString)
	                                     .collect(Collectors.toList());
	    
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
	        // Mogelijk wil je hier de logica toevoegen om de tegel als 'gekozen' te markeren of te verwijderen
	    }

	    return gekozenTegel;
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
		  // Bepaal een willekeurige speler die mag beginnen
	    Random rand = new Random();
	    int randomSpelerIndex = rand.nextInt(spelerInformatieContainer.getChildren().size());
	    Label geselecteerdeSpelerLabel = (Label) spelerInformatieContainer.getChildren().get(randomSpelerIndex);
	    String spelerInfo = geselecteerdeSpelerLabel.getText();

	    // Toon een pop-upvenster met de geselecteerde spelerinformatie
	    Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    alert.setTitle("Begin van de Ronde");
	    alert.setHeaderText(null);
	    alert.setContentText("Speler die mag beginnen: " + spelerInfo);

	    alert.showAndWait();
		toonTegelsMetBeideZijden(new ArrayList<>(gekozenTegels)); // Gebruik startKolom die al gegenereerd is
		kiesTegelInStartKolom();
	}

}