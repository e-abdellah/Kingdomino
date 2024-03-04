package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SpelController {

    @FXML
    private Label spelLabel;

    // Je kunt hier je actie-methoden toevoegen, afhankelijk van wat je in het spel wilt doen

    @FXML
    private void initialize() {
        // Voeg hier eventuele initialisatielogica toe
        spelLabel.setText("Welkom bij het spel!");
    }
}

