package testen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import domein.Spel;
import domein.Speler;

public class ScoreTest {
    private Spel spel;
    private Speler speler;
    private final static int MAX_LENGTE = 5;

    private String[][][] koninkrijk = new String[MAX_LENGTE][MAX_LENGTE][1];

    void maakKoninkrijk(){
        koninkrijk[0][0][0] = "W";
        koninkrijk[0][1][0] = "W";
        koninkrijk[0][2][0] = "W";
        koninkrijk[0][3][0] = "W";
        koninkrijk[0][4][0] = "W";
        koninkrijk[1][0][0] = "Z";
        koninkrijk[1][1][0] = "Z";
        koninkrijk[1][2][0] = "Z";
        koninkrijk[1][3][0] = "Z";
        koninkrijk[1][4][0] = "Z";
        koninkrijk[2][0][0] = "G";
        koninkrijk[2][1][0] = "G";
        koninkrijk[2][2][0] = null;
        koninkrijk[2][3][0] = "G";
        koninkrijk[2][4][0] = "G";
        koninkrijk[3][0][0] = "W";
        koninkrijk[3][1][0] = "W";
        koninkrijk[3][2][0] = "W";
        koninkrijk[3][3][0] = "W";
        koninkrijk[3][4][0] = "W";
        koninkrijk[4][0][0] = "G";
        koninkrijk[4][1][0] = "G";
        koninkrijk[4][2][0] = "G";
        koninkrijk[4][3][0] = "G";
        koninkrijk[4][4][0] = "G";

        koninkrijk[0][0][1] = "0";
        koninkrijk[0][1][1] = "0";
        koninkrijk[0][2][1] = "1";
        koninkrijk[0][3][1] = "1";
        koninkrijk[0][4][1] = "0";
        koninkrijk[1][0][1] = "2";
        koninkrijk[1][1][1] = "0";
        koninkrijk[1][2][1] = "3";
        koninkrijk[1][3][1] = "0";
        koninkrijk[1][4][1] = "0";
        koninkrijk[2][0][1] = "0";
        koninkrijk[2][1][1] = "0";
        koninkrijk[2][2][1] = "1";
        koninkrijk[2][3][1] = "1";
        koninkrijk[2][4][1] = "0";
        koninkrijk[3][0][1] = "0";
        koninkrijk[3][1][1] = "1";
        koninkrijk[3][2][1] = "0";
        koninkrijk[3][3][1] = "0";
        koninkrijk[3][4][1] = "1";
        koninkrijk[4][0][1] = "0";
        koninkrijk[4][1][1] = "0";
        koninkrijk[4][2][1] = "0";
        koninkrijk[4][3][1] = "0";
        koninkrijk[4][4][1] = "0";


    }
    @Test
    void testBerekenScore(){
        spel.berekenScore(speler);
        Assertions.assertEquals();
    }


}
