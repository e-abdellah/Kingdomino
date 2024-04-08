package dto;

import domein.Dominotegel;
import domein.Vakje;

import java.util.List;

public record DominotegelDTO(Vakje vakje1, Vakje vakje2, int getal, int kroon, String tegel) {
}
