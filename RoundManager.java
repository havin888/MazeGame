import java.util.Map;
import java.util.HashMap;

public class RoundManager {
    private MazeModel maze;
    private Map<PlayerId, PlayerState> players;
    private GameTimer timer;
    private int currentRound;
    
    public RoundManager() {
        this.maze = new MazeModel();
        this.players = new HashMap<>();
        this.timer = new GameTimer();
        this.currentRound = 1;
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
    
    public GameTimer getTimer() {
        return timer;
    }
    
    public int getCurrentRound() {
        return currentRound;
    }
    
    public void startRound(DifficultyLevel difficulty) {
        maze.setDifficulty(difficulty);
        maze.generateMaze();

        players.clear();

        Player player1 = new Player(PlayerId.PLAYER_1, WallColor.BLUE);
        PlayerState ps1 = new PlayerState(player1, new Position(maze.getPlayer1Start().getX(), maze.getPlayer1Start().getY()));
        players.put(PlayerId.PLAYER_1, ps1);

        Player player2 = new Player(PlayerId.PLAYER_2, WallColor.RED);
        PlayerState ps2 = new PlayerState(player2, new Position(maze.getPlayer2Start().getX(), maze.getPlayer2Start().getY()));
        players.put(PlayerId.PLAYER_2, ps2);

        timer.start();
        currentRound++;
    }
    
    public boolean handleMove(PlayerId playerId, Position newPos) {
        PlayerState playerState = players.get(playerId);
        if (playerState == null) {
            return false;
        }

        if (maze.isWalkable(playerId, newPos.getX(), newPos.getY())) {
            playerState.setPosition(newPos);
            
            // Check if player reached goal
            Position goal = maze.getGoalPosition();
            if (newPos.getX() == goal.getX() && newPos.getY() == goal.getY()) {
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
    
    public void stopRound() {
        timer.stop();
    }
    
    public RoundResult getRoundResult() {
        return new RoundResult(maze.getDifficulty(), timer.getElapsedTime(), isRoundComplete());
    }
}
