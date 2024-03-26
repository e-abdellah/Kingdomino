package dto;

import java.util.List;

public record SpelerDTO(String gebruikersnaam, int geboortejaar, int aantalGewonnen, int aantalGespeeld, String[][][] koninkrijk, List<Integer> scores, boolean isWinnaar) {

    public Object getGebruikersnaam() {
        return gebruikersnaam;
    }
}
