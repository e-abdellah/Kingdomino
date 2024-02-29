package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import domein.Speler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class welkomController extends Pane {

	private List<TextField> spelerTextFields = new ArrayList<>();
	private List<DatePicker> spelerGeboortedatums = new ArrayList<>();
	private List<Speler> spelers = new ArrayList<>();
	private int aantalSpelers = 0;
	private String gebruikersNaam;
	private String geboorteJaar;

	@FXML
	private VBox mainLayout;

	// Voeg een nieuwe speler toe wanneer op de knop wordt geklikt
	@FXML
	public void voegSpelerToe(Event event) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Speler toevoegen");
		dialog.setHeaderText(null);
		dialog.setContentText("Voer de naam van de speler in:");

		Optional<String> result = dialog.showAndWait();
		result.ifPresent(name -> {
			// Voeg hier je logica toe om de speler toe te voegen
			System.out.println("Speler toegevoegd: " + name);
		});
	}

	// Verwijder de laatst toegevoegde speler wanneer op de knop wordt geklikt
	@FXML
	public void verwijderSpeler(ActionEvent event) {
		if (aantalSpelers > 1) {
			mainLayout.getChildren().remove(mainLayout.getChildren().size() - 1);
			aantalSpelers--;
		}
	}

	// Schakel naar de volgende scene wanneer op de knop wordt geklikt
	@FXML
	public void switchToKingdomino(ActionEvent event) throws IOException {
		// Voeg hier je bestaande code toe om naar de volgende scene te schakelen
	}

	public void handleAfsluiten(Event event) // <5>
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
}
