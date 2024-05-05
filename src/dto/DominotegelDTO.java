package dto;

import domein.Vakje;

public record DominotegelDTO(Vakje vakje1, Vakje vakje2, int getal, int kroon, String tegel, String voorkantFotoPad,
		String achterkantFotoPad) {
}
