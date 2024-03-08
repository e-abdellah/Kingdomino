package domein;

import java.util.ArrayList;
import java.util.List;

public class Dominotegel {

	private List<Vakje> vakjes = new ArrayList<>(2);

	public Dominotegel() {
		// Voeg twee willekeurige vakjes toe aan de lijst van vakjes
		vakjes.add(new Vakje(Landschap.getRandomLandschap()));
		vakjes.add(new Vakje(Landschap.getRandomLandschap()));
	}

	public List<Vakje> getVakjes() {
		return vakjes;
	}
}
