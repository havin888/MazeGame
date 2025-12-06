import java.util.List;

public class GameController {
    private RoundManager roundManager;
    private ScoreService scoreService;
    private Team team;
    private GameTimer gameTimer;
    private GameState gameState;
    
    public GameController() {
        this.roundManager = new RoundManager();
        this.scoreService = new ScoreServiceImpl();
        this.team = null;
        this.gameTimer = new GameTimer();
        this.gameState = GameState.MENU;
    }
    
    public RoundManager getRoundManager() {
        return roundManager;
    }
    
    public void setRoundManager(RoundManager roundManager) {
        this.roundManager = roundManager;
    }
    
    public ScoreService getScoreService() {
        return scoreService;
    }
    
    public void setScoreService(ScoreService scoreService) {
        this.scoreService = scoreService;
    }
    
    public Team getTeam() {
        return team;
    }
    
    public void setTeam(Team team) {
        this.team = team;
    }
    
    public GameTimer getGameTimer() {
        return gameTimer;
    }
    
    public void setGameTimer(GameTimer gameTimer) {
        this.gameTimer = gameTimer;
    }
    
    public GameState getGameState() {
        return gameState;
    }
    
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
    
    public void startGame() {
        if (team == null) {
            throw new IllegalStateException("Team must be set before starting game");
        }
        gameState = GameState.PLAYING;
        gameTimer.start();
    }
    
    public void stopGame() {
        gameTimer.stop();
        gameState = GameState.MENU;
    }
    
    public void onPlayerInput(PlayerId playerId, Direction direction) {
        if (gameState != GameState.PLAYING) {
            return;
        }
        
        // Get current player position
        PlayerState playerState = roundManager.getPlayers().get(playerId);
        if (playerState == null) {
            return;
        }
        
        Position currentPos = playerState.getPosition();
        Position newPos = calculateNewPosition(currentPos, direction);
        
        // Try to move the player
        boolean moved = roundManager.handleMove(playerId, newPos);
        
        // Check if round is complete
        if (moved && roundManager.isRoundComplete()) {
            onRoundComplete();
        }
    }
    
    private Position calculateNewPosition(Position current, Direction direction) {
        int x = current.getX();
        int y = current.getY();
        
        switch (direction) {
            case UP:
                y--;
                break;
            case DOWN:
                y++;
                break;
            case LEFT:
                x--;
                break;
            case RIGHT:
                x++;
                break;
        }
        
        return new Position(x, y);
    }
    
    private void onRoundComplete() {
        gameTimer.stop();
        long elapsedTime = gameTimer.getElapsedTime();
        
        // Add time to team's total
        if (team != null) {
            team.addRoundTime(elapsedTime);
        }
        
        gameState = GameState.ROUND_COMPLETE;
    }
    
    public void saveTeamScore(String teamName) {
        if (team != null) {
            scoreService.saveScore(teamName, team.getTotalTime());
        }
    }
    
    public List<ScoreRecord> getTopScores() {
        return scoreService.getTopScores();
    }
}
