package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class WelkomKDController {

    @FXML
    private VBox root;

    @FXML
    private Label titleLabel;

    @FXML
    private Button registreerButton;

    @FXML
    private Button startSpelButton;

    @FXML
    private Button afsluitenButton;

    // Je kunt hier je actie-methoden toevoegen, bijvoorbeeld:

    @FXML
    private void registreerSpeler() {
        // Voeg hier de logica toe voor het registreren van een speler
        showAlert("Registreer nieuwe speler", "Actie nog niet geïmplementeerd");
    }

    @FXML
    private void startSpel() {
        // Voeg hier de logica toe voor het starten van een spel
        showAlert("Start nieuwe spel", "Actie nog niet geïmplementeerd");
    }

    @FXML
    private void afsluiten() {
        // Voeg hier de logica toe voor het afsluiten van de applicatie
        showAlert("Afsluiten", "Actie nog niet geïmplementeerd");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
