package main;

import cui.KingdominoApp;
import domein.DomeinController;

public class StartUp {

	public static void main(String[] args) {

		new KingdominoApp(new DomeinController()).start();
		
		

	}
}
