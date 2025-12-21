public class RoundResult {
    private DifficultyLevel difficulty;
    private long elapsedMillis;
    private boolean completed;
    
    public RoundResult(DifficultyLevel difficulty, long elapsedMillis, boolean completed) {
        this.difficulty = difficulty;
        this.elapsedMillis = elapsedMillis;
        this.completed = completed;
    }
    
    public DifficultyLevel getDifficulty() {
        return difficulty;
    }
    
    public void setDifficulty(DifficultyLevel difficulty) {
        this.difficulty = difficulty;
    }
    
    public long getElapsedMillis() {
        return elapsedMillis;
    }
    
    public void setElapsedMillis(long elapsedMillis) {
        this.elapsedMillis = elapsedMillis;
    }
    
    public boolean isCompleted() {
        return completed;
    }
    
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
