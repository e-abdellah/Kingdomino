package domein;
import java.util.Comparator;
import dto.SpelerDTO;
import java.util.List;

public class ScoreComparator implements Comparator<SpelerDTO> {
    private final DomeinController dc;

    public ScoreComparator(DomeinController dc) {
        this.dc = dc;
    }

    @Override
    public int compare(SpelerDTO o1, SpelerDTO o2) {
        List<Integer> scoresO1 = dc.berekenScore(o1);
        List<Integer> scoresO2 = dc.berekenScore(o2);

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



