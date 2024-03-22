package main;

import domein.Speler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ScoreTest {
    public static void main(String[] args){
        Speler speler;
        final int MAX_LENGTE = 5;

        String[][][] koninkrijk = new String[MAX_LENGTE][MAX_LENGTE][2];

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
        koninkrijk[2][2][1] = "0";
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

        speler = new Speler("avatar", 2003, 4, 25, koninkrijk);
        System.out.println(speler.getScores().toString());

    }
}
