package main;

import java.util.Scanner;

import cui.KingdominoApp;
import domein.DomeinController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class StartUp extends Application {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in); // Scanner voor het lezen van gebruikersinvoer
		while (true) {
			System.out.println("Selecteer de applicatie modus:");
			System.out.println("1. CUI App"); // Keuze voor de CUI applicatie
			System.out.println("2. GUI App"); // Keuze voor de GUI applicatie
			System.out.print("Voer uw keuze in (1 of 2): ");

			try {
				int keuze = scanner.nextInt(); // Probeert een geheel getal te lezen

				if (keuze == 1) {
					System.out.println("CUI App wordt gestart..."); // Bericht voor starten CUI app
					new KingdominoApp(new DomeinController()).start(); // Start de CUI applicatie
					break; // Verlaat de lus na het starten van de CUI app
				} else if (keuze == 2) {
					System.out.println("GUI App wordt gestart..."); // Bericht voor starten GUI app
					launch(args); // Start de GUI applicatie
					break; // Verlaat de lus na het starten van de GUI app
				} else {
					System.out.println("\nOngeldige keuze. Probeer het opnieuw."); // Bericht voor ongeldige keuze
				}
			} catch (Exception e) { // Vang elke vorm van uitzondering
				System.out.println("Ongeldige invoer. Probeer het opnieuw."); // Bericht voor ongeldige invoer
				scanner.nextLine(); // Maakt de invoerbuffer leeg
			}
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/WelkomKD.fxml"));
		Scene scene = new Scene(loader.load());
		primaryStage.setScene(scene);
		primaryStage.setTitle("KingDomino");
		primaryStage.setMaximized(true);
		primaryStage.show();

	}

}
