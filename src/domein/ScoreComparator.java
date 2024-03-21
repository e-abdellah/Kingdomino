package domein;
import java.util.Comparator;
import dto.SpelerDTO;
import java.util.List;

public class ScoreComparator implements Comparator<Speler> {
    private Spel spel;
    @Override
    public int compare(Speler o1, Speler o2) {
        List<Integer> scoresO1 = o1.getScores();
        List<Integer> scoresO2 = o2.getScores();

        // Compare score
        int scoreCompare = Integer.compare(scoresO1.get(0), scoresO2.get(0));
        if (scoreCompare != 0) return scoreCompare;

        // Compare gebied
        int gebiedCompare = Integer.compare(scoresO1.get(1), scoresO2.get(1));
        if (gebiedCompare != 0) return gebiedCompare;

        // Compare kronen
        return Integer.compare(scoresO1.get(2), scoresO2.get(2));
    }
}



