package testen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import domein.Landschap;
import domein.Vakje;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import domein.Spel;
import domein.Speler;

public class ScoreTest {
    private Speler speler;
    private final static int MAX_LENGTE = 5;

    private Vakje[][] koninkrijk = new Vakje[MAX_LENGTE][MAX_LENGTE];
    private Vakje[][] koninkrijk1 = new Vakje[MAX_LENGTE][MAX_LENGTE];

    @BeforeEach
    void maakKoninkrijk(){
        koninkrijk[0][0] = new Vakje(Landschap.WATER, 0);
        koninkrijk[0][1] = new Vakje(Landschap.WATER, 1);
        koninkrijk[0][2] = new Vakje(Landschap.WATER, 1);
        koninkrijk[0][3] = new Vakje(Landschap.WATER, 0);
        koninkrijk[0][4] = new Vakje(Landschap.WATER, 0);
        koninkrijk[1][0] = new Vakje(Landschap.ZAND, 2);
        koninkrijk[1][1] = new Vakje(Landschap.ZAND, 0);
        koninkrijk[1][2] = new Vakje(Landschap.ZAND, 3);
        koninkrijk[1][3] = new Vakje(Landschap.ZAND, 0);
        koninkrijk[1][4] = new Vakje(Landschap.ZAND, 0);
        koninkrijk[2][0] = new Vakje(Landschap.GRAS, 0);
        koninkrijk[2][1] = new Vakje(Landschap.GRAS, 0);
        koninkrijk[2][2] = null;
        koninkrijk[2][3] = new Vakje(Landschap.GRAS, 1);
        koninkrijk[2][4] = new Vakje(Landschap.GRAS, 0);
        koninkrijk[3][0] = new Vakje(Landschap.WATER, 0);
        koninkrijk[3][1] = new Vakje(Landschap.WATER, 1);
        koninkrijk[3][2] = new Vakje(Landschap.WATER, 0);
        koninkrijk[3][3] = new Vakje(Landschap.WATER, 0);
        koninkrijk[3][4] = new Vakje(Landschap.WATER, 1);
        koninkrijk[4][0] = new Vakje(Landschap.GRAS, 0);
        koninkrijk[4][1] = new Vakje(Landschap.GRAS, 0);
        koninkrijk[4][2] = new Vakje(Landschap.GRAS, 0);
        koninkrijk[4][3] = new Vakje(Landschap.GRAS, 0);
        koninkrijk[4][4] = new Vakje(Landschap.GRAS, 0);

        koninkrijk1[0][0] = null;
        koninkrijk1[0][1] = null;
        koninkrijk1[0][2] = new Vakje(Landschap.WATER, 1);
        koninkrijk1[0][3] = new Vakje(Landschap.WATER, 1);
        koninkrijk1[0][4] = new Vakje(Landschap.WATER, 0);
        koninkrijk1[1][0] = null;
        koninkrijk1[1][1] = new Vakje(Landschap.ZAND, 3);
        koninkrijk1[1][2] = new Vakje(Landschap.ZAND, 0);
        koninkrijk1[1][3] = null;
        koninkrijk1[1][4] = new Vakje(Landschap.ZAND, 0);
        koninkrijk1[2][0] = new Vakje(Landschap.GRAS, 0);
        koninkrijk1[2][1] = new Vakje(Landschap.GRAS, 0);
        koninkrijk1[2][2] = null;
        koninkrijk1[2][3] = new Vakje(Landschap.GRAS, 1);
        koninkrijk1[2][4] = new Vakje(Landschap.GRAS, 0);
        koninkrijk1[3][0] = null;
        koninkrijk1[3][1] = new Vakje(Landschap.WATER, 1);
        koninkrijk1[3][2] = new Vakje(Landschap.WATER, 0);
        koninkrijk1[3][3] = new Vakje(Landschap.WATER, 0);
        koninkrijk1[3][4] = null;
        koninkrijk1[4][0] = new Vakje(Landschap.GRAS, 0);
        koninkrijk1[4][1] = new Vakje(Landschap.GRAS, 0);
        koninkrijk1[4][2] = null;
        koninkrijk1[4][3] = new Vakje(Landschap.GRAS, 0);
        koninkrijk1[4][4] = new Vakje(Landschap.GRAS, 0);


    }
    @Test
    void testBerekenScore(){
        speler = new Speler("avatar", 2003, 4, 25, koninkrijk);
        Assertions.assertEquals(speler.getScores().get(0), 47);
    }

    @Test
    void testBerekenScore1(){
        speler = new Speler("avatar", 2003, 4, 25, koninkrijk1);
        Assertions.assertEquals(speler.getScores().get(0), 17);
    }


}
