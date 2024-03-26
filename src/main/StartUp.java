package main;

import cui.KingdominoApp;
import domein.DomeinController;
import gui.TaalKeuzeController; // Importeer de TaalKeuzeController-klasse
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartUp extends Application {

	public static void main(String[] args) {
		//new KingdominoApp(new DomeinController()).start();
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/taalKeuze.fxml"));
		Scene scene = new Scene(loader.load());
		primaryStage.setTitle("Taalkeuze"); // Standaard titel
		primaryStage.setScene(scene);
		primaryStage.show();

		// Voeg de referentie naar het hoofdvenster toe aan de TaalKeuzeController
		TaalKeuzeController controller = loader.getController();
		controller.setStage(primaryStage);
	}
}
