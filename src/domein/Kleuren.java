package domein;

import java.util.ArrayList;
import java.util.List;

public enum Kleuren {

	GROEN, BLAUW, ROOS, GEEL;
	
    public static List<String> getKleurLijst() {
        List<String> kleurLijst = new ArrayList<>();
        
        // Itereer over de waarden van de enum en voeg ze toe aan de lijst
        for (Kleuren kleur : values()) {
            kleurLijst.add(kleur.name());
        }
        
        return kleurLijst;
    }

}
