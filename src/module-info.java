module Kingdomino_g36 {
	exports persistentie;
	exports cui;
	exports gui;
	exports main;
	exports domein;
	exports testen;
	exports dto;
	exports exceptions;

	requires java.sql;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires jsch;
	requires org.junit.jupiter.api;
	requires org.junit.jupiter.params;
	opens gui to javafx.fxml;
}