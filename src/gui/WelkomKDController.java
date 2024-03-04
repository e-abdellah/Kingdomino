package gui;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import domein.DomeinController;
import dto.SpelerDTO;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
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
		showAlert("Registreer nieuwe speler", "Actie nog niet geïmplementeerd");
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
