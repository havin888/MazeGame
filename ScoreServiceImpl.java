import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ScoreServiceImpl implements ScoreService {
    private List<ScoreRecord> scores;
    private JDBCConnection dbConnection;
    
    public ScoreServiceImpl() {
        this.scores = new ArrayList<>();
        this.dbConnection = null; // Would be initialized with actual connection
    }
    
    public JDBCConnection getDbConnection() {
        return dbConnection;
    }
    
    public void setDbConnection(JDBCConnection dbConnection) {
        this.dbConnection = dbConnection;
    }
    
    @Override
    public void saveScore(String teamName, long timeMillis) {
        ScoreRecord record = new ScoreRecord(teamName, timeMillis);
        scores.add(record);
        
        // Would save to database via dbConnection
        if (dbConnection != null) {
            // dbConnection.executeUpdate(sql, params);
        }
    }
    
    @Override
    public List<ScoreRecord> getTopScores() {
        // Sort scores by time (lower is better)
        Collections.sort(scores, new Comparator<ScoreRecord>() {
            @Override
            public int compare(ScoreRecord s1, ScoreRecord s2) {
                return Long.compare(s1.getTotalTime(), s2.getTotalTime());
            }
        });
        
        // Return top scores (e.g., top 10)
        int limit = Math.min(10, scores.size());
        return new ArrayList<>(scores.subList(0, limit));
    }
}
