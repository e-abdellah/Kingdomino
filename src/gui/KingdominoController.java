package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//import domein.Ontwikkelingskaart;
import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class KingdominoController {

    @FXML
    private GridPane gridpaneNiveau1;
    @FXML
    private GridPane gridpaneNiveau2;
    @FXML
    private GridPane gridpaneNiveau3;
    @FXML
    private GridPane gridpaneEdelen;

    public void initializeGame(List<TextField> spelerTextFields) {
        // Maakt lijsten aan met ImageUrls voor elk niveau
        List<String> niveau1ImageUrls = generateImageUrls("spelOntwikkelingskaartenNiveau1", 5, "Ontwikkelingskaart");
        List<String> niveau2ImageUrls = generateImageUrls("spelOntwikkelingskaartenNiveau2", 10, "Ontwikkelingskaart");
        List<String> niveau3ImageUrls = generateImageUrls("spelOntwikkelingskaartenNiveau3", 10, "Ontwikkelingskaart");
        List<String> edeleImageUrls = generateImageUrls("spelEdelen", spelerTextFields.size() + 1, "Edele");

        // Shuffelen van de afbeeldings-URL's
        shuffleImageUrls(niveau1ImageUrls);
        shuffleImageUrls(niveau2ImageUrls);
        shuffleImageUrls(niveau3ImageUrls);
        shuffleImageUrls(edeleImageUrls);

        // Instellen van de afbeeldingen voor elk gridpane
        setImagesForGridPane(gridpaneNiveau1, niveau1ImageUrls);
        setImagesForGridPane(gridpaneNiveau2, niveau2ImageUrls);
        setImagesForGridPane(gridpaneNiveau3, niveau3ImageUrls);
        setImagesForGridPane(gridpaneEdelen, edeleImageUrls);
    }

    private List<String> generateImageUrls(String folder, int numImages, String prefix) {
        List<String> imageUrls = new ArrayList<>();
        String pathPrefix = "/images/" + folder + "/" + prefix;

        // Genereer de image URLS gebaseerd op de meegegeven folder & het aantal images in die folder
        for (int i = 1; i <= numImages; i++) {
            String imageUrl = getClass().getResource(pathPrefix + i + ".png").toString();
            imageUrls.add(imageUrl);
        }

        return imageUrls;
    }

    private void shuffleImageUrls(List<String> imageUrls) {
        // Willekeurig ordenen van de afbeeldings-URL's
        Collections.shuffle(imageUrls);
    }

    private void setImagesForGridPane(GridPane gridPane, List<String> imageUrls) {
        ObservableList<Node> children = gridPane.getChildren();
        int index = 0;

        // Instellen van de afbeeldingen voor elke ImageView in de gridpane
        for (Node child : children) {
            if (child instanceof ImageView) {
                ImageView imageView = (ImageView) child;
                if (index < imageUrls.size()) {
                    String imageUrl = imageUrls.get(index);
                    imageView.setImage(new Image(imageUrl));
                    index++;
                } else {
                    break; // Verlaat de loop als alle images zijn geplaatst
                }
            }
        }
    }

    public void setSpelers(List<TextField> spelerTextFields) {
        // Implementatie van setSpelers-methode
    }
}
