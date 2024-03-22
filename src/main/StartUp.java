package main;

import cui.KingdominoApp;
import domein.DomeinController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartUp extends Application {

        public static void main(String[] args) {

                new KingdominoApp(new DomeinController()).start();
                launch(args);

        }

        @Override
        public void start(Stage primaryStage) throws Exception {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/welkomKD.fxml"));
                Scene scene = new Scene(loader.load());
                primaryStage.setTitle("Kingdomino App");
                primaryStage.setScene(scene);
                primaryStage.show();
        }
}
