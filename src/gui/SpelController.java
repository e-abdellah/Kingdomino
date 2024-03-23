package gui;

import java.util.List;

import domein.DomeinController;
import domein.Dominotegel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class SpelController {

	@FXML
	private Label spelLabel;
	private DomeinController dc;

	@FXML
	private VBox spelerInformatieContainer; // Voor speler info

	@FXML
	private VBox dominotegelInformatieContainer; // Voor dominotegels

	public SpelController() {
		// Je DomeinController initialiseren, indien nodig
		this.dc = new DomeinController();
	}

	@FXML
	public void initialize() {
		// Initialize je UI componenten indien nodig
		//		toonDominotegels();
	}

	public void setSpelerInformatie(List<String> spelerInformatie) {
		spelerInformatie.forEach(info -> {
			Label label = new Label(info);
			spelerInformatieContainer.getChildren().add(label);
		});
	}

	private void toonDominotegels() {
		List<Dominotegel> dominotegels = dc.schudDominotegelsAantal(3); // Veronderstelt dat je een methode hebt om de lijst van tegels te krijgen
		for (Dominotegel tegel : dominotegels) {
			Image image = new Image(tegel.getVoorkantFotoPad());
			System.out.println(tegel.getVoorkantFotoPad());
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(100); // Stel een passende breedte in
			imageView.setFitHeight(150); // Stel een passende hoogte in
			dominotegelInformatieContainer.getChildren().add(imageView);
		}
	}

}
