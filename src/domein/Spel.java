package domein;

import static domein.Landschap.AARDE;
import static domein.Landschap.BOS;
import static domein.Landschap.GRAS;
import static domein.Landschap.WATER;
import static domein.Landschap.ZAND;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Spel {

	private List<Speler> aantalSpelers;
	private Set<Integer> getallen;
	private List<Dominotegel> dominotegels;

	public Spel(List<Speler> aantalSpelers, List<Dominotegel> dominotegels, Set<Integer> getallen) {
		setAantalSpelers(aantalSpelers);
		getallen = new HashSet<>(36);

	}

	public Spel(List<Speler> aantalSpelers, List<Dominotegel> dominotegels) {
		setAantalSpelers(aantalSpelers);
		setDominotegels(dominotegels);
		getallen = new HashSet<>(36);
	}

	public Spel(List<Speler> aantalSpelers) {
		setAantalSpelers(aantalSpelers);
	}

	public List<Speler> getAantalSpelers() {
		return aantalSpelers;
	}

	private void setAantalSpelers(List<Speler> aantalSpelers) {
//		if (aantalSpelers.size() < 3 || aantalSpelers.size() > 4)
//			throw new IllegalArgumentException(
//					"Het aantal spelers moet minstens 3 spelers en maximum 4 spelers bevatten");
		this.aantalSpelers = aantalSpelers;
	}

	public List<Dominotegel> getDominotegels() {
		return dominotegels;
	}

	public void setDominotegels(List<Dominotegel> dominotegels) {
		if (aantalSpelers.size() == 3)
			this.dominotegels = new ArrayList<>(36);
		else
			this.dominotegels = new ArrayList<>(48);

	}

	private void genereerAantalDominotegels() {
		dominotegels.add(new Dominotegel(new Vakje(ZAND), new Vakje(ZAND), 1, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(ZAND), new Vakje(ZAND), 2, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(BOS), new Vakje(BOS), 3, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(BOS), new Vakje(BOS), 4, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(BOS), new Vakje(BOS), 5, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(BOS), new Vakje(BOS), 6, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(WATER), new Vakje(WATER), 7, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(WATER), new Vakje(WATER), 8, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(WATER), new Vakje(WATER), 9, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(GRAS), new Vakje(GRAS), 10, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(GRAS), new Vakje(GRAS), 11, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(AARDE), new Vakje(AARDE), 12, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(ZAND), new Vakje(BOS), 13, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(ZAND), new Vakje(WATER), 14, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(ZAND), new Vakje(GRAS), 15, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(ZAND), new Vakje(AARDE), 16, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(BOS), new Vakje(WATER), 17, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(BOS), new Vakje(GRAS), 18, 0, 0));
		dominotegels.add(new Dominotegel(new Vakje(ZAND), new Vakje(WATER), 19, 1, 1));
	}

}
