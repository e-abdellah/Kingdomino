package dto;

import domein.Dominotegel;
import domein.Speler;

import java.util.List;

public record SpelDTO(List<List<Dominotegel>> kolommen, boolean eindeSpel) {


}
