package main;

import cui.KingdominoApp;
import domein.DomeinController;
import persistentie.Connectie;

public class StartUp {

	public static void main(String[] args) {

		new KingdominoApp(new DomeinController()).startSpel();

		if (args.length == 1) {
			Connectie.setSshPrivateKeyPath(args[0]);
		}
	}
}
