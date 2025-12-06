public class ScoreRecord {
    private String teamName;
    private long totalTime;
    
    public ScoreRecord(String teamName, long totalTime) {
        this.teamName = teamName;
        this.totalTime = totalTime;
    }
    
    public String getTeamName() {
        return teamName;
    }
    
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    
    public long getTotalTime() {
        return totalTime;
    }
    
    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }
}
