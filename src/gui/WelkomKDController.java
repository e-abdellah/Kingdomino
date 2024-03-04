package gui;

import java.util.Optional;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class WelkomKDController {

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
		showAlert("Volgende", "Actie nog niet geïmplementeerd");
	}

	private void showAlert(String title, String content) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}
}
