public class PlayerState {
    private Player player;
    private Position position;
    private boolean reachedGoal;
    
    public PlayerState(Player player, Position position) {
        this.player = player;
        this.position = position;
        this.reachedGoal = false;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public Position getPosition() {
        return position;
    }
    
    public void setPosition(Position position) {
        this.position = position;
    }
    
    public boolean isReachedGoal() {
        return reachedGoal;
    }
    
    public void setReachedGoal(boolean reachedGoal) {
        this.reachedGoal = reachedGoal;
    }
    
    public boolean hasReachedGoal() {
        return reachedGoal;
    }
}
