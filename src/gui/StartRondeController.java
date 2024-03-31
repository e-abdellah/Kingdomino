package gui;

import java.util.Deque;
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
		stapel = spelController.getStapel();
	}

	public void initSpel() {
		toonDominotegels((List<Dominotegel>) stapel);
	}

	public void toonDominotegels(List<Dominotegel> dominotegels) {
		spelController.toonTegelsMetBeideZijden(dominotegels);
	}

}
