import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ScoreServiceImpl implements ScoreService {
    private JDBCConnection dbConnection;
    
    public ScoreServiceImpl() {
        this.dbConnection = new JDBCConnectionImpl();
    }
    
    public ScoreServiceImpl(JDBCConnection connection) {
        this.dbConnection = connection;
    }
    
    public JDBCConnection getDbConnection() {
        return dbConnection;
    }
    
    public void setDbConnection(JDBCConnection dbConnection) {
        this.dbConnection = dbConnection;
    }
    
    @Override
    public void saveScore(String teamName, long timeMillis) {
        try {
            dbConnection.startConnection();
            
            String sql = "INSERT INTO SCOREBOARD (team_name, completion_time) VALUES (?, ?)";
            Object[] params = {teamName, timeMillis};
            
            dbConnection.executeUpdate(sql, params);
            
            dbConnection.endConnection();
        } catch (Exception e) {
            System.err.println("Error saving score: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    public boolean teamNameExists(String teamName) {
        try {
            dbConnection.startConnection();
            
            String sql = "SELECT COUNT(*) as count FROM SCOREBOARD WHERE team_name = ?";
            Object[] params = {teamName};
            ResultSet rs = dbConnection.executeQuery(sql, params);
            
            boolean exists = false;
            if (rs != null && rs.next()) {
                int count = rs.getInt("count");
                exists = (count > 0);
            }
            
            if (rs != null) {
                rs.close();
            }
            
            dbConnection.endConnection();
            
            return exists;
        } catch (Exception e) {
            System.err.println("Error checking team name: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public List<ScoreRecord> getTopScores() {
        List<ScoreRecord> scores = new ArrayList<>();
        
        try {
            dbConnection.startConnection();
            
            String sql = "SELECT team_name, completion_time FROM SCOREBOARD ORDER BY completion_time ASC LIMIT 10";
            
            ResultSet rs = dbConnection.executeQuery(sql, null);
            
            while (rs != null && rs.next()) {
                String teamName = rs.getString("team_name");
                long completionTime = rs.getLong("completion_time");
                scores.add(new ScoreRecord(teamName, completionTime));
            }
            
            if (rs != null) {
                rs.close();
            }
            
            dbConnection.endConnection();
        } catch (Exception e) {
            System.err.println("Error retrieving scores: " + e.getMessage());
            e.printStackTrace();
        }
        
        return scores;
    }
}
