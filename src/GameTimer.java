public class GameTimer {
    private long startTime;
    private boolean isRunning;
    
    public GameTimer() {
        this.startTime = 0;
        this.isRunning = false;
    }
    
    public long getStartTime() {
        return startTime;
    }
    
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    
    public boolean isRunning() {
        return isRunning;
    }
    
    public void setRunning(boolean running) {
        isRunning = running;
    }
    
    public void start() {
        this.startTime = System.currentTimeMillis();
        this.isRunning = true;
    }
    
    public void stop() {
        this.isRunning = false;
    }
    
    public long getElapsedTime() {
        return System.currentTimeMillis() - startTime;
    }
}
