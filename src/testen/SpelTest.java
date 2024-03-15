package testen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import domein.Spel;
import domein.Speler;

class SpelTest {

	private static final int MAX_AANTALSPELERS = 4, MIN_AANTALSPELERS = 3;

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(ints = { 0, MAX_AANTALSPELERS + 1, MIN_AANTALSPELERS - 1, 10 })
	void voegSpelers_fouteAantalspelers_WerptException(List<Speler> spelers) {
		assertThrows(IllegalArgumentException.class, () -> new Spel(spelers, List.of()));
		// er wordt gecontrollerd of de aantal spelers niet correct is
	}

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(ints = { MAX_AANTALSPELERS, MIN_AANTALSPELERS })
	void voegSpelersToe_juisteAantalspelers_maakSpel(List<Speler> spelers) {
		assertEquals(spelers, new Spel(spelers, List.of()));
		// er wordt gecontrollerd of de aantal spelers correct is

	}

}
