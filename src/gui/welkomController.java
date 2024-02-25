package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import domein.Speler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Locale;


public class welkomController extends Pane {


    // Lijst van TextFields om gebruikersnamen van spelers op te slaan
    // Extra lijst met daarin de geboortedatums van de spelers
    private List<TextField> spelerTextFields = new ArrayList<>();
    private List<DatePicker> spelerGeboortedatums = new ArrayList<>();
    List<Speler> spelers = new ArrayList<Speler>();

    // FXML elementen uit het FXML bestand
    @FXML
    private VBox mainLayout;
    @FXML
    private TextField gebruikersnaamSpeler1;
    @FXML
    private DatePicker geboortedatumSpeler1;
    @FXML
    private TextField gebruikersnaamSpeler2;
    @FXML
    private DatePicker geboortedatumSpeler2;
    @FXML
    private Button voegSpelerToeButton;
    @FXML
    private Button verwijderSpelerButton;
    @FXML
    private Button volgendeButton;
    @FXML
    private Label labelBirthdate = new Label();
    @FXML
    private Label labelUsername  = new Label();
    
    public void initialize() {
        voegSpelerToeButton.setVisible(false);
        verwijderSpelerButton.setVisible(false);
        volgendeButton.setVisible(false);
        
    }
    
    @FXML
    private Text welkomText;


    @FXML
    private Button nlButton;
    
    @FXML
    private Button enButton;

    // Huidige aantal spelers in het spel
    private int aantalSpelers = 0;

    DatePicker playerBirthdate;
    TextField playerName;
    
    ResourceBundle bundle;
    
//   Strings die de waarde krijgen via gekozen resource bundle;
    String error = "";
    String gebruikersNaam ="";
    String geboorteDatum ="";
    String gevonden = "";
    String nietGevonden ="";
    String isJongste ="";
    String minsteTwee ="";
    String speler ="";
    
   

    // Methode om de taal naar Nederlands te veranderen
    @FXML
    private void switchToNederlands(ActionEvent event) {
    	ResourceBundle bundle = ResourceBundle.getBundle("gui/Bundle_nl_BEL");
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("welkom.fxml"), bundle);
    	voegSpelerToeButton.setText(bundle.getString("voegSpelerToe"));
    	verwijderSpelerButton.setText(bundle.getString("verwijderSpeler"));
    	volgendeButton.setText(bundle.getString("volgende"));
    	labelBirthdate.setText(bundle.getString("geboorteDatum"));
    	labelUsername.setText(bundle.getString("gebruikersNaam"));
    	welkomText.setText(bundle.getString("invoerText"));
    	
        error = bundle.getString("error");
        gebruikersNaam = bundle.getString("gebruikersNaam");
        geboorteDatum = bundle.getString("geboorteDatum");
        gevonden = bundle.getString("gevonden");
        nietGevonden = bundle.getString("nietGevonden");
        isJongste = bundle.getString("isJongste");
        minsteTwee = bundle.getString("minsteTwee");
        speler = bundle.getString("speler");
        
		voegSpelerToeButton.setVisible(true);
        verwijderSpelerButton.setVisible(true);
        volgendeButton.setVisible(true);

        
    }

    // Methode om de taal naar Engels te veranderen
    @FXML
    private void switchToEngels(ActionEvent event) {
        ResourceBundle bundle = ResourceBundle.getBundle("gui/Bundle");
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/welkom.fxml"), bundle);
    	voegSpelerToeButton.setText(bundle.getString("voegSpelerToe"));
    	verwijderSpelerButton.setText(bundle.getString("verwijderSpeler"));
     	volgendeButton.setText(bundle.getString("volgende"));
    	labelBirthdate.setText(bundle.getString("geboorteDatum"));
    	labelUsername.setText(bundle.getString("gebruikersNaam"));
    	welkomText.setText(bundle.getString("invoerText"));

        error = bundle.getString("error");
        gebruikersNaam = bundle.getString("gebruikersNaam");
        geboorteDatum = bundle.getString("geboorteDatum");
        gevonden = bundle.getString("gevonden");
        nietGevonden = bundle.getString("nietGevonden");
        isJongste = bundle.getString("isJongste");
        minsteTwee = bundle.getString("minsteTwee");
        speler = bundle.getString("speler");
        
		voegSpelerToeButton.setVisible(true);
        verwijderSpelerButton.setVisible(true);
        volgendeButton.setVisible(true);


    }
    @FXML
    // Methode om een nieuwe speler toe te voegen aan de aangemaakte lijst
    private void voegSpelerToe() {

        if (aantalSpelers < 4) {
        	labelUsername = new Label(gebruikersNaam);
            playerName = new TextField();
            gebruikersnaamSpeler1 = playerName;
            gebruikersnaamSpeler2 = playerName;
            HBox usernameLayout = new HBox(labelUsername, playerName);
            usernameLayout.setSpacing(10);

            labelBirthdate = new Label(geboorteDatum);
            playerBirthdate = new DatePicker();
            geboortedatumSpeler1 = playerBirthdate;
            geboortedatumSpeler2 = playerBirthdate;
            HBox birthdateLayout = new HBox(labelBirthdate, playerBirthdate);
            birthdateLayout.setSpacing(13);

            VBox playerLayout = new VBox(usernameLayout, birthdateLayout);
            playerLayout.setSpacing(10);
            playerLayout.setPadding(new Insets(35, 0, 10, 0));

            mainLayout.getChildren().add(playerLayout);
            aantalSpelers++;
            spelerTextFields.add(playerName);
            spelerGeboortedatums.add(playerBirthdate);
        }
    }

    @FXML
    // Methode om de laatst toegevoegde speler uit de aangemaakte lijst te verwijderen
    private void verwijderSpeler() {
        if (aantalSpelers > 1) {
            mainLayout.getChildren().remove(aantalSpelers - 1);
            aantalSpelers--;
        }
    }

    // Variabelen om een nieuw scherm aan te maken
    private Stage stage;
    private Scene myScene;
    private Parent root;
    



    @FXML
 // Methode om over te schakelen naar het volgende scherm wanneer er op de 'volgende' knop gedrukt wordt
 private void switchToSplendor(ActionEvent event) throws IOException {

     KingdominoController controller = null;

     // Gaat na of de gebruikersnamen & de geboortedatums die opgeslagen werden valid zijn wanneer men op de 'volgende' button klikt
     boolean validData = true;
     for (int i = 0; i < spelerTextFields.size(); i++) {
         String name = spelerTextFields.get(i).getText().trim();
         if (name.isEmpty() || !name.matches("^[A-Z][a-zA-Z_ ]*$")) {
             validData = false;
             break;
         }
         LocalDate birthdate = spelerGeboortedatums.get(i).getValue();
         if (birthdate == null || birthdate.plusYears(6).isAfter(LocalDate.now())) {
             validData = false;
             break;
         }
     }

     if (!validData) {
         Alert alert = new Alert(AlertType.ERROR);
         alert.setTitle("Error");
         alert.setHeaderText(null);
         alert.setContentText(error);
         alert.showAndWait();
         return;
     }

  // Methode om de jongste speler te bepalen
     LocalDate youngestBirthdate = null;
     String youngestPlayerName = "";
     for (int i = 0; i < spelerTextFields.size(); i++) {
         LocalDate birthdate = spelerGeboortedatums.get(i).getValue();
         if (birthdate != null && (youngestBirthdate == null || birthdate.isAfter(youngestBirthdate))) {
             youngestBirthdate = birthdate;
             youngestPlayerName = spelerTextFields.get(i).getText();
         }
     }

        

        // Gaat na of de aangemaakte lijst van spelers minstens 2 spelers bevat.
        if (spelerTextFields.size() >= 2) {
            // Als dit zo is, kan de volgende scene ingeladen worden en kan er gechecked worden of de aangemaakte spelers effectief bestaan in de database

            // Maakt een connectie met de database
            Connection connection = null;
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sdpdb", "root", "root");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Voert een SELECT query uit om na te gaan of de spelers effectief in de database zitten
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {
                for (int i = 0; i < spelerTextFields.size(); i++) {
                    String name = spelerTextFields.get(i).getText().trim();
                    LocalDate birthdate = spelerGeboortedatums.get(i).getValue();

                    String query = "SELECT * FROM speler WHERE gebruikersnaam = ? AND geboortejaar = ?";
                    statement = connection.prepareStatement(query);
                    statement.setString(1, name);
                    statement.setDate(2, java.sql.Date.valueOf(birthdate));

                    resultSet = statement.executeQuery();
                    // Als de spelers in de database zitten, wordt er een gepaste melding gegeven en kan men door gaan naar het volgende scherm
                    if (resultSet.next()) {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText(null);
                        alert.setContentText(speler + " " + name + " " + gevonden);
                        alert.showAndWait();
                    // Als de spelers niet in de database zitten, wordt er een gepaste melding gegeven en moet men terugkeren
                    } else {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText(speler + " " + name + " " + nietGevonden);
                        alert.showAndWait();
                        return;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (resultSet != null) resultSet.close();
                    if (statement != null) statement.close();
                    if (connection != null) connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            
            showStartMessage(youngestPlayerName);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/splendor.fxml"));
            Parent root = loader.load();
            controller = loader.getController();
            controller.initializeGame(spelerTextFields);
            // Roept de gridpane met fxid gridpane uit het FXML bestand op en voegt de opgeslagen spelers hieraan toe,
            // zodat deze spelers dynamisch weergegeven kunnen worden in het volgende scherm
            GridPane gridpaneSpelers = (GridPane) root.lookup("#gridpane");
            for (int i = 0; i < spelerTextFields.size(); i++) {
                Label nameLabel = new Label("Speler " + (i + 1) + ":");
                nameLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: gold;");
                Label usernameLabel = new Label(spelerTextFields.get(i).getText());
                usernameLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: gold;");
                // Dit voegt de naam & gebruikersnaam labels dynamisch toe aan de gridpane cellen die hierboven werden aangemaakt, startende bij cel 0
                gridpaneSpelers.add(nameLabel, 0, i);
                gridpaneSpelers.add(usernameLabel, 1, i);
            }
            // Lijst met spelers wordt doorgegeven aan de volgende scene
            controller.setSpelers(spelerTextFields);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        	scene.setUserData(bundle);

            stage.show();
        } else {
            // Als dit niet zo is, dan wordt er een gepaste melding weergegeven
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(minsteTwee);
            alert.showAndWait();
        }
    }
    private void showStartMessage(String youngestPlayerName) {
        String message = youngestPlayerName + " " + isJongste;
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Game Start");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @FXML
    private void handleAfsluiten(ActionEvent event) {
        // Voeg hier code toe om de applicatie af te sluiten
        System.exit(0); // Of gebruik Platform.exit() voor JavaFX-toepassingen
    }
}
