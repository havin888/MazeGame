import java.util.List;

public interface ScoreService {
    void saveScore(String teamName, long timeMillis);
    List<ScoreRecord> getTopScores();
}
