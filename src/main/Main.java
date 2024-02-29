package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import persistentie.Connectie;

public class Main extends Application

{
	public static void main(String[] args) {
		if (args.length == 1) {
			Connectie.setSshPrivateKeyPath(args[0]);
		}
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

//            try {
//                Connection connection = DatabaseConnection.getConnection();
//                System.out.println("Connected to database!");
//                connection.close();
//            } catch (SQLException e) {
//                System.out.println("Failed to connect to database: " + e.getMessage());
//            }

//    	welkomController root2 = new welkomController();
		Parent root = FXMLLoader.load(getClass().getResource("/gui/welkom.FXML"));
		Scene myScene = new Scene(root);
		primaryStage.setScene(myScene);
		primaryStage.setTitle("Kingdomino Registratie");
		primaryStage.show();
		primaryStage.setResizable(false);

	}
}