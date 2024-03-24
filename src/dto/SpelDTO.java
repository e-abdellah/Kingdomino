package dto;

import domein.Dominotegel;
import domein.Speler;

import java.util.List;

public record SpelDTO(List<Speler> spelers, List<Dominotegel> kolom) {

}
