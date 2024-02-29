//Kopieer dit in module.info.java voor projecten ZONDER FXML
//Package main bevat klasse met startmethode

//module projectZonderFXML {
//	requires javafx.base;
//	requires javafx.controls;
//	requires javafx.graphics;
//	requires org.junit.jupiter.params;
//	requires java.sql;
//
//	exports main to javafx.graphics;
//	exports ui;
//	exports gui;
//	exports domein;
//	exports testen;
//}

//Kopieer dit in module.info.java voor projecten MET FXML

module projectMetFXML {
	requires javafx.base;
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires java.sql;
//	requires org.junit.jupiter.api;
	requires org.junit.jupiter.params;
	requires jsch;

	exports main to javafx.graphics;
	exports domein;
	exports gui;
	exports exceptions;

	opens gui to javafx.fxml;
}
