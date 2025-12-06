import java.util.Map;
import java.util.HashMap;

public class RoundManager {
    private MazeModel maze;
    private Map<PlayerId, PlayerState> players;
    
    public RoundManager() {
        this.maze = new MazeModel();
        this.players = new HashMap<>();
    }
    
    public MazeModel getMaze() {
        return maze;
    }
    
    public void setMaze(MazeModel maze) {
        this.maze = maze;
    }
    
    public Map<PlayerId, PlayerState> getPlayers() {
        return players;
    }
    
    public void setPlayers(Map<PlayerId, PlayerState> players) {
        this.players = players;
    }
    
    public void startRound(DifficultyLevel difficulty) {
        // Initialize maze based on difficulty
        maze.setDifficulty(difficulty);
        
        // Reset player states
        players.clear();
        // Initialize player positions based on maze
    }
    
    public boolean handleMove(PlayerId playerId, Position newPos) {
        PlayerState playerState = players.get(playerId);
        if (playerState == null) {
            return false;
        }
        
        // Check if the move is valid
        if (maze.isWalkable(playerId, newPos.getX(), newPos.getY())) {
            playerState.setPosition(newPos);
            
            // Check if player reached goal
            Tile tile = maze.getTile(newPos.getX(), newPos.getY());
            if (tile != null && tile.getType() == TileType.GOAL) {
                playerState.setReachedGoal(true);
            }
            
            return true;
        }
        
        return false;
    }
    
    public boolean isRoundComplete() {
        for (PlayerState playerState : players.values()) {
            if (!playerState.hasReachedGoal()) {
                return false;
            }
        }
        return players.size() > 0;
    }
    
    public RoundResult getRoundResult() {
        // This would be called by GameController to get the result
        return null; // Implementation depends on timer integration
    }
}
