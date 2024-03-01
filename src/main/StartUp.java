//package main;
//
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;

//public class Main extends Application
//
//{
//	public static void main(String[] args) {
//		if (args.length == 1) {
//			Connectie.setSshPrivateKeyPath(args[0]);
//		}
//		launch(args);
//	}
//
//	@Override
//	public void start(Stage primaryStage) throws Exception {
//
////            try {
////                Connection connection = DatabaseConnection.getConnection();
////                System.out.println("Connected to database!");
////                connection.close();
////            } catch (SQLException e) {
////                System.out.println("Failed to connect to database: " + e.getMessage());
////            }
//
//		Parent root = FXMLLoader.load(getClass().getResource("/gui/welkomKD.FXML"));
//		Scene myScene = new Scene(root);
//		primaryStage.setScene(myScene);
//		primaryStage.setTitle("Kingdomino Registratie");
//		primaryStage.show();
//		primaryStage.setResizable(false);
//
//	}
//}

//public class Main extends Application {
//
//	private Stage primaryStage;
//	private Scene scene;
//	private VBox vbox;
//
//	public static void main(String[] args) {
//		launch(args);
//	}
//
//	@Override
//	public void start(Stage primaryStage) {
//		this.primaryStage = primaryStage;
//		try {
//			// Laad het FXML-bestand
//			Parent root = FXMLLoader.load(getClass().getResource("/gui/welkomKD.FXML"));
//
//			// Creëer de scene
//			Scene scene = new Scene(root, 1600, 1600);
//
//			// Stel het podium in
//			primaryStage.setTitle("Mijn JavaFX App");
//			primaryStage.setScene(scene);
//			primaryStage.show();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}

package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartUp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Laad het FXML-bestand
            Parent root = FXMLLoader.load(getClass().getResource("/gui/welkomKD.FXML"));

            // Creëer de scene
            Scene scene = new Scene(root, 800, 600);

            // Stel het podium in
            primaryStage.setTitle("JavaFX Toepassing met FXML");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

