package gui;

import java.util.List;

import domein.DomeinController;
import domein.Dominotegel;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

public class StartRondeController {

	@FXML
	private HBox dominotegelInformatieContainer;

	private SpelController spelController;

	private DomeinController dc;

	private List<Dominotegel> stapel;

	public StartRondeController() {
		dc = new DomeinController();
		spelController = new SpelController();
		
	}

	public void initSpel() {
		toonDominotegels(stapel);
	}

	public void toonDominotegels(List<Dominotegel> dominotegels) {
		
	}

}
