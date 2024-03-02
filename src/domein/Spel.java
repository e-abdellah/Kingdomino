package domein;

import java.util.List;

public class Spel {

	private List<Speler> aantalSpelers;
	private int dominotegels;

	public Spel(List<Speler> aantalSpelers, int dominotegels) {
		setAantalSpelers(aantalSpelers);
		setDominotegels(dominotegels);
	}

	public Spel(List<Speler> aantalSpelers) {
		setAantalSpelers(aantalSpelers);
	}

	public List<Speler> getAantalSpelers() {
		return aantalSpelers;
	}

	private void setAantalSpelers(List<Speler> aantalSpelers) {
		if (aantalSpelers.size() < 3 || aantalSpelers.size() > 4)
			throw new IllegalArgumentException(
					"Het aantal spelers moet minstens 3 spelers en maximum 4 spelers bevatten");
		this.aantalSpelers = aantalSpelers;
	}

	public int getDominotegels() {
		return dominotegels;
	}

	private void setDominotegels(int dominotegels) {
		if (aantalSpelers.size() == 3)
			this.dominotegels = 36;
		else
			this.dominotegels = 48;
	}

}
