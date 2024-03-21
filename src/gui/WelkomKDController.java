package gui;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import domein.DomeinController;
import dto.SpelerDTO;
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
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class WelkomKDController {
	private DomeinController dc = new DomeinController();

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
	private void registreerSpeler() {
		Dialog<Void> dialog = new Dialog<>();
		dialog.setTitle("Nieuwe Speler Registratie");
		dialog.setHeaderText("Voer de gegevens van de nieuwe speler in:");

		// Voeg knoppen toe.
		ButtonType registreerButtonType = new ButtonType("Registreer");
		dialog.getDialogPane().getButtonTypes().addAll(registreerButtonType, ButtonType.CANCEL);

		// Maak de invoervelden en foutmeldingen.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);

		TextField gebruikersnaam = new TextField();
		gebruikersnaam.setPromptText("Gebruikersnaam");
		Label gebruikersnaamFout = new Label();
		gebruikersnaamFout.setStyle("-fx-text-fill: red;");

		TextField geboortejaar = new TextField();
		geboortejaar.setPromptText("Geboortejaar");
		Label geboortejaarFout = new Label();
		geboortejaarFout.setStyle("-fx-text-fill: red;");

		grid.add(new Label("Gebruikersnaam:"), 0, 0);
		grid.add(gebruikersnaam, 1, 0);
		grid.add(gebruikersnaamFout, 1, 1);
		grid.add(new Label("Geboortejaar:"), 0, 2);
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
				gebruikersnaamFout.setText("Gebruikersnaam moet minstens 6 tekens lang zijn.");
				validatieFout = true;
			}

			int jaar = 0;
			try {
				jaar = Integer.parseInt(jaarText);
				if (jaar < 1920 || jaar > 2018) {
					geboortejaarFout.setText("Ongeldig geboortejaar.");
					validatieFout = true;
				}
			} catch (NumberFormatException e) {
				geboortejaarFout.setText("Geboortejaar moet een getal zijn.");
				validatieFout = true;
			}

			// Als er een validatiefout is, consumeer het event zodat het dialoogvenster open blijft.
			if (validatieFout) {
				event.consume();
			} else {
				// Registreer de nieuwe speler als er geen fouten zijn.
				dc.registreerSpeler(naam, jaar);
				showAlert("Registratie Voltooid", "De nieuwe speler is succesvol geregistreerd.");
			}
		});

		dialog.showAndWait();
	}

	@FXML
	private void startSpel() {
		showAlert("Start nieuwe spel", "Actie nog niet geïmplementeerd");
	}

	//    @FXML
	//    private void afsluiten() {
	//        showAlert("Afsluiten", "Actie nog niet geïmplementeerd");
	//    }
	@FXML
	private void afsluiten(Event event) // <5>
	{
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Bevestig");
		alert.setContentText("Wil je de applicatie afsluiten?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			System.out.println("We sluiten het venster en dus... ook de applicatie");
			Platform.exit();
		} else // Cancel
		{
			event.consume();
		}
	}

	@FXML
	private void volgende() {
		// Haal de lijst met spelers op
		List<SpelerDTO> spelers = dc.geefOverzichtSpelers();

		// Toon een Alert met de lijst met spelers
		StringBuilder alertContent = new StringBuilder("Spelers:\n");
		for (SpelerDTO speler : spelers) {
			alertContent.append(String.format("- %s (%d)\n", speler.gebruikersnaam(), speler.geboortejaar()));
		}

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Spelersoverzicht");
		alert.setHeaderText(null);
		alert.setContentText(alertContent.toString());
		alert.showAndWait();

		// Navigeer naar het spelFXML
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/startSpel.FXML"));
			Parent root = loader.load();
			Stage stage = new Stage();
			stage.setTitle("Spel");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void showAlert(String title, String content) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}
}
