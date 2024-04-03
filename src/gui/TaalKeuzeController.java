package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import gui.WelkomKDController;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class TaalKeuzeController {

	// ResourceBundle voor het laden van vertalingen
	private ResourceBundle bundle;
	private Stage stage; // Referentie naar het hoofdvenster
	private WelkomKDController welkomkdController;

	// Koppel de FXML-elementen aan de controller
	@FXML
	private Button englishButton;

	@FXML
	private Button dutchButton;

	@FXML
	private Button frenchButton;

	@FXML
	private void chooseEnglish() {
		// Kies Engels
		setLanguage("en", "EN");
		navigateToWelcomePage("welkomKD.fxml");
		welkomkdController.setLanguage("en");

	}

	@FXML
	private void chooseDutch() {
		// Kies Nederlands
		setLanguage("nl", "NL");
		navigateToWelcomePage("welkomKD.fxml");
		welkomkdController.setLanguage("nl");
	}

	@FXML
	private void chooseFrench() {
		// Kies Frans
		setLanguage("fr", "FR");
		navigateToWelcomePage("welkomKD.fxml");
		welkomkdController.setLanguage("fr");
	}

	private void setLanguage(String language, String country) {
		// Stel de taal in
		Locale locale = new Locale(language, country);
		bundle = ResourceBundle.getBundle("utils.resource_bundle", locale);
		// Update de UI
		updateUI();

		// Stel de titel van het venster in op basis van de gekozen taal
		if (stage != null) {
			stage.setTitle(bundle.getString("welkomSchermTitel"));
		}
	}

	private void updateUI() {
		// Implementeer deze methode om de teksten in de UI bij te werken met de nieuwe
		// taal
		// Voorbeeld: label.setText(bundle.getString("kiesOptie"));

	}

	private void navigateToWelcomePage(String fxmlFileName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
			Parent root = loader.load();
			stage.setScene(new Scene(root));
			// Verkrijg een referentie naar de WelkomKDController
			welkomkdController = loader.getController();
			// Stel het stage in voor de WelkomKDController
			// welkomkdController.setStage(stage);

			// Stel de taal in op de WelkomKDController
			welkomkdController.setLanguage("en"); // Stel de gewenste taal in
		} catch (IOException e) {
			e.printStackTrace(); // Behandel de fout afhankelijk van de behoefte van je applicatie
		}
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}