package gui;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import domein.DomeinController;
import dto.SpelerDTO;
import exceptions.GebruikersnaamInGebruikException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class WelkomKDController {
	private DomeinController dc = new DomeinController();
	private SpelerDTO dto;
	private List<SpelerDTO> spelers = new ArrayList<>();

	@FXML
	private ImageView imageView;

	@FXML
	private AnchorPane root;

	@FXML
	private Label titleLabel;

	@FXML
	private Button registreerBtn;

	@FXML
	private Button startBtn;

	@FXML
	private Button afsluitenBtn;

	@FXML
	private Button volgendeBtn;

	@FXML
	private Button nlBtn;

	@FXML
	private Button enBtn;

	@FXML
	private Button frBtn;

	private ResourceBundle resourceBundle;
	protected int aantalSpelersGekozen;
	List<String> beschikbareKleuren;
	List<SpelerDTO> geselecteerdeSpelers = new ArrayList<>();

	@FXML
	private void chooseDutch(ActionEvent event) {
		setLanguage("nl");
	}

	@FXML
	private void chooseEnglish(ActionEvent event) {
		setLanguage("en");
	}

	@FXML
	private void chooseFrench(ActionEvent event) {
		setLanguage("fr");
	}

	@FXML
	public void initialize() {
		setLanguage("nl"); //Stelt de standaard taal in op Nederlands
		//Past de achtergrond image aan op basis van de grootte vh scherm vd gebruiker
		imageView.fitWidthProperty().bind(root.widthProperty());
		imageView.fitHeightProperty().bind(root.heightProperty());
		imageView.setPreserveRatio(false);

	}

	public void setLanguage(String language) {
		Locale locale = new Locale(language);
		beschikbareKleuren = dc.geefKleurenInTaal(locale);
		resourceBundle = ResourceBundle.getBundle("utils.resource_bundle", locale);
		// Laad de juiste ResourceBundle op basis van de geselecteerde taal

		// Set de tekst van de knoppen vanuit de ResourceBundle
		registreerBtn.setText(resourceBundle.getString("registreerButton"));
		startBtn.setText(resourceBundle.getString("startButton"));
		afsluitenBtn.setText(resourceBundle.getString("exitButton"));
		// titleLabel.setText(resourceBundle.getString("welcomeMessage"));
	}

	@FXML
	private void registreerSpeler() {
		Dialog<Void> dialog = new Dialog<>();
		dialog.setTitle(resourceBundle.getString("nieuweSpelerRegistratieLabel"));
		dialog.setHeaderText(resourceBundle.getString("voerGegevensSpelerIn"));

		// Voeg knoppen toe.
		ButtonType registreerButtonType = new ButtonType(resourceBundle.getString("registreerSpelerButton"));
		dialog.getDialogPane().getButtonTypes().addAll(registreerButtonType, ButtonType.CANCEL);

		// Maak de invoervelden en foutmeldingen.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);

		TextField gebruikersnaam = new TextField();
		gebruikersnaam.setPromptText(resourceBundle.getString("naamSpelerLabel"));
		gebruikersnaam.setPrefWidth(200); // Zet de voorkeursbreedte
		gebruikersnaam.setMinWidth(TextField.USE_PREF_SIZE); // Gebruik voorkeursbreedte als minimale breedte
		gebruikersnaam.setMaxWidth(TextField.USE_PREF_SIZE); // Gebruik voorkeursbreedte als maximale breedte
		Label gebruikersnaamFout = new Label();
		gebruikersnaamFout.setStyle("-fx-text-fill: red;");

		TextField geboortejaar = new TextField();
		geboortejaar.setPromptText(resourceBundle.getString("geboortejaarSpelerLabel"));
		geboortejaar.setPrefWidth(200); // Zet de voorkeursbreedte
		geboortejaar.setMinWidth(TextField.USE_PREF_SIZE); // Gebruik voorkeursbreedte als minimale breedte
		geboortejaar.setMaxWidth(TextField.USE_PREF_SIZE); // Gebruik voorkeursbreedte als maximale breedte
		Label geboortejaarFout = new Label();
		geboortejaarFout.setStyle("-fx-text-fill: red;");

		grid.add(new Label(resourceBundle.getString("naamSpelerLabel")), 0, 0);
		grid.add(gebruikersnaam, 1, 0);
		grid.add(gebruikersnaamFout, 1, 1);
		grid.add(new Label(resourceBundle.getString("geboortejaarSpelerLabel")), 0, 2);
		grid.add(geboortejaar, 1, 2);
		grid.add(geboortejaarFout, 1, 3);

		dialog.getDialogPane().setContent(grid);

		// Zet de focus op het gebruikersnaam veld.
		Platform.runLater(gebruikersnaam::requestFocus);

		// Voeg een event filter toe om de registratieknop te controleren.
		dialog.getDialogPane().lookupButton(registreerButtonType).addEventFilter(ActionEvent.ACTION, event -> {
			// Reset foutmeldingen.
			gebruikersnaamFout.setText("");
			geboortejaarFout.setText("");

			// Valideer gebruikersnaam en geboortejaar.
			String naam = gebruikersnaam.getText();
			String jaarText = geboortejaar.getText();
			boolean validatieFout = false;

			if (naam.trim().isEmpty() || naam.length() < 6) {
				gebruikersnaamFout.setText(resourceBundle.getString("GebruikersnaamError"));
				validatieFout = true;
			}

			int jaar = 0;
			try {
				jaar = Integer.parseInt(jaarText);
			} catch (NumberFormatException e) {
				geboortejaarFout.setText(resourceBundle.getString("GeboortejaarGeenGetalError"));
				validatieFout = true;
			}

			if (jaar < 1920 || jaar > 2018) {
				geboortejaarFout.setText(resourceBundle.getString("GeboortejaarOngeldigError"));
				validatieFout = true;
			}

			// Alleen proberen te registreren als er tot nu toe geen validatiefouten zijn.
			if (!validatieFout) {
				try {
					dc.registreerSpeler(naam, jaar);
					// Registreer de nieuwe speler als er geen fouten zijn en toon succesmelding.
					showAlert(resourceBundle.getString("spelerSuccesvolGeregistreerd"), naam);
				} catch (GebruikersnaamInGebruikException ex) {
					gebruikersnaamFout.setText(resourceBundle.getString("spelerGebruikersnaamAlInGebruik"));
					event.consume(); // Voorkom dat het dialoog sluit.
				}
			} else {
				event.consume(); // Voorkom dat het dialoog sluit bij een validatiefout.
			}
		});

		dialog.showAndWait();
	}

	@FXML
	private void startSpel() {
		List<String> keuzes = Arrays.asList("3", "4");
		ChoiceDialog<String> aantalSpelersDialog = new ChoiceDialog<>("4", keuzes);
		aantalSpelersDialog.setTitle(resourceBundle.getString("aantalSpelersDialogTitel"));
		aantalSpelersDialog.setHeaderText(resourceBundle.getString("aantalSpelersDialogHeader"));
		aantalSpelersDialog.setContentText(resourceBundle.getString("aantalSpelersDialogKiesAantalSpelers"));

		Optional<String> aantalSpelersResultaat = aantalSpelersDialog.showAndWait();
		List<String> spelerEnKleurInformatie = new ArrayList<>(); // Verplaatst om zichtbaar te zijn buiten de lambda

		aantalSpelersResultaat.ifPresent(aantalSpelers -> {
			aantalSpelersGekozen = Integer.parseInt(aantalSpelers);
			List<String> spelersNamen = dc.geefOverzichtSpelers().stream().map(SpelerDTO::gebruikersnaam)
					.collect(Collectors.toList());
			Map<String, SpelerDTO> spelerMap = dc.geefOverzichtSpelers().stream()
					.collect(Collectors.toMap(SpelerDTO::gebruikersnaam, speler -> speler));

			for (int i = 1; i <= aantalSpelersGekozen; i++) {
				ChoiceDialog<String> spelerKeuzeDialog = new ChoiceDialog<>(spelersNamen.get(0), spelersNamen);
				spelerKeuzeDialog.setTitle(resourceBundle.getString("aantalSpelersDialogTitel"));
				spelerKeuzeDialog.setHeaderText(resourceBundle.getString("spelersKeuzeDialogHeader") + i);
				spelerKeuzeDialog.setContentText(resourceBundle.getString("spelersKeuzeDialogBeschikbareSpelers"));

				Optional<String> spelerKeuzeResultaat = spelerKeuzeDialog.showAndWait();
				spelerKeuzeResultaat.ifPresent(spelerNaam -> {
					ChoiceDialog<String> kleurDialog = new ChoiceDialog<>(beschikbareKleuren.get(0),
							beschikbareKleuren);
					//spelers.add(dto);
					if (spelerMap.containsKey(spelerNaam)) {
						geselecteerdeSpelers.add(spelerMap.get(spelerNaam));
					}
					kleurDialog.setTitle(resourceBundle.getString("kleurDialogTitel") + spelerNaam);
					kleurDialog.setHeaderText(resourceBundle.getString("kleurDialogHeader") + spelerNaam + ":");
					kleurDialog.setContentText(resourceBundle.getString("kleurDialogToonBeschikbareKleuren"));

					Optional<String> kleurResultaat = kleurDialog.showAndWait();
					kleurResultaat.ifPresent(kleur -> {
						spelerEnKleurInformatie.add(spelerNaam + " - " + kleur);
						spelersNamen.remove(spelerNaam);
						beschikbareKleuren.remove(kleur);
					});
				});
			}

			navigeerNaarSpel(spelerEnKleurInformatie);
		});
		dc.voegSpelersToe(geselecteerdeSpelers);
	}

	private void navigeerNaarSpel(List<String> spelerEnKleurInformatie) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/startSpel.FXML"));
			Parent root = loader.load();

			SpelController spelController = loader.getController();
			spelController.setAantalSpelers(aantalSpelersGekozen);
			spelController.setSpelerInfo(spelerEnKleurInformatie);
			spelController.initSpel(); // Dan initialiseer je het spel

			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("KingDomino");
			stage.setMaximized(true);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	

	@FXML
	private void afsluiten(Event event) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle(resourceBundle.getString("alertSetTitle"));
		alert.setContentText(resourceBundle.getString("alertSetContextText"));
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			System.out.println(resourceBundle.getString("kleurDialogToonBeschikbareKleuren"));
			Platform.exit();
		} else // Cancel
		{
			event.consume();
		}
	}

	@FXML
	private void volgende() {

	}

	private void showAlert(String title, String content) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}

	public int getAantalSpelersGekozen() {
		return aantalSpelersGekozen;
	}

	public List<SpelerDTO> getSpelers() {
		return spelers;
	}

}