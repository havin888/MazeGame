import java.util.List;
import java.util.ArrayList;

public class GameController {
    private RoundManager roundManager;
    private ScoreService scoreService;
    private Team team;
    private GameState gameState;
    private DifficultyLevel currentDifficulty;
    private List<RoundResult> roundResults;
    
    public GameController() {
        this.roundManager = new RoundManager();
        this.scoreService = new ScoreServiceImpl();
        this.team = null;
        this.gameState = GameState.MENU;
        this.currentDifficulty = DifficultyLevel.EASY;
        this.roundResults = new ArrayList<>();
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
    
    public GameState getGameState() {
        return gameState;
    }
    
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
    
    public DifficultyLevel getCurrentDifficulty() {
        return currentDifficulty;
    }
    
    public void startGame(String teamName) {
        this.team = new Team(teamName);
        this.currentDifficulty = DifficultyLevel.EASY;
        this.roundResults.clear();
        gameState = GameState.PLAYING;
        roundManager.startRound(currentDifficulty);
    }
    
    public void stopGame() {
        roundManager.stopRound();
        gameState = GameState.MENU;
    }

    //Check comments for test ideas
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
        if (roundManager.isRoundComplete()) {
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
        roundManager.stopRound();
        RoundResult result = roundManager.getRoundResult();
        roundResults.add(result);
        
        // Add time to team's total
        if (team != null) {
            team.addRoundTime(result.getElapsedMillis());
        }
        
        // Check if all rounds are complete
        if (currentDifficulty == DifficultyLevel.HARD) {
            gameState = GameState.GAME_OVER;
        } else {
            gameState = GameState.ROUND_COMPLETE;
            // Don't advance difficulty yet - it will be done in startNextRound()
        }
    }
    
    public void onTimerExpired() {
        roundManager.stopRound();
        gameState = GameState.GAME_OVER;
    }
    
    private void advanceDifficulty() {
        switch (currentDifficulty) {
            case EASY:
                currentDifficulty = DifficultyLevel.MEDIUM;
                break;
            case MEDIUM:
                currentDifficulty = DifficultyLevel.HARD;
                break;
            case HARD:
                // Already at max difficulty
                break;
        }
    }
    
    public void startNextRound() {
        if (gameState == GameState.ROUND_COMPLETE) {
            // Advance difficulty BEFORE starting the next round
            advanceDifficulty();
            gameState = GameState.PLAYING;
            roundManager.startRound(currentDifficulty);
        }
    }
    
    public long getMaxTimeForDifficulty() {
        // Time limit in milliseconds (60 seconds = 1 minute per round)
        return 60000;
    }
    
    public void saveTeamScore(String teamName) {
        if (team != null) {
            scoreService.saveScore(teamName, team.getTotalTime());
        }
    }
    
    public List<ScoreRecord> getTopScores() {
        return scoreService.getTopScores();
    }
    
    public long calculateTotalTime() {
        long total = 0;
        for (RoundResult result : roundResults) {
            total += result.getElapsedMillis();
        }
        return total;
    }
}
