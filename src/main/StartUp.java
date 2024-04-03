package main;

import java.util.Scanner;

import cui.KingdominoApp;
import domein.DomeinController;
import gui.TaalKeuzeController; // Importeer de TaalKeuzeController-klasse
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class StartUp extends Application {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("Selecteer de applicatie modus:");
			System.out.println("1. CUI App");
			System.out.println("2. GUI App");
			System.out.print("Voer uw keuze in (1 of 2): ");

			try {
				int keuze = scanner.nextInt(); // Try to read an integer input

				if (keuze == 1) {
					System.out.println("CUI App wordt gestart...");
					new KingdominoApp(new DomeinController()).start();
					break; // Exit the loop after starting the CUI app
				} else if (keuze == 2) {
					System.out.println("GUI App wordt gestart...");
					launch(args); // Start the GUI application
					break; // Exit the loop after starting the GUI app
				} else {
					System.out.println("\nOngeldige keuze. Probeer het opnieuw.");
				}
			} catch (Exception e) { // Catch any type of Exception
				System.out.println("Ongeldige invoer. Probeer het opnieuw.");
				scanner.nextLine(); // Clear the input buffer
			}
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/taalKeuze.fxml"));
	    Scene scene = new Scene(loader.load());
	    primaryStage.setScene(scene);
	    TaalKeuzeController controller = loader.getController();
	    controller.setStage(primaryStage); // This will also maximize the stage
	    primaryStage.show();
	    
	}

}
