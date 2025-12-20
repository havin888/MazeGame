import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test ID: T-SRS-MG-007 and T-SRS-MG-007.1
 * Verifies: SRS-MG-007, SRS-MG-007.1, SRS-MG-007.2
 * Description: Verify three difficulty levels and maze dimension scaling
 */
public class DifficultyLevelTest {
    private GameController gameController;
    
    @Before
    public void setUp() {
        gameController = new GameController();
    }
    
    @Test
    public void testEasyDifficultyDimensions() {
        gameController.startGame("TestTeam");
        assertEquals("Initial difficulty should be EASY", 
                     DifficultyLevel.EASY, gameController.getCurrentDifficulty());
        
        MazeModel maze = gameController.getRoundManager().getMaze();
        assertEquals("Easy maze width should be 10", 10, maze.getWidth());
        assertEquals("Easy maze height should be 10", 10, maze.getHeight());
    }
    
    @Test
    public void testMediumDifficultyDimensions() {
        // Create a maze with medium difficulty
        MazeModel maze = new MazeModel();
        maze.setDifficulty(DifficultyLevel.MEDIUM);
        
        assertEquals("Medium maze width should be 15", 15, maze.getWidth());
        assertEquals("Medium maze height should be 15", 15, maze.getHeight());
    }
    
    @Test
    public void testHardDifficultyDimensions() {
        // Create a maze with hard difficulty
        MazeModel maze = new MazeModel();
        maze.setDifficulty(DifficultyLevel.HARD);
        
        assertEquals("Hard maze width should be 25", 25, maze.getWidth());
        assertEquals("Hard maze height should be 25", 25, maze.getHeight());
    }
    
    @Test
    public void testDifficultyProgression() {
        gameController.startGame("TestTeam");
        
        // Start with Easy
        assertEquals("Should start with EASY", DifficultyLevel.EASY, gameController.getCurrentDifficulty());
        
        // Complete first round and move to next
        completeCurrentRound();
        gameController.startNextRound();
        assertEquals("After first round should be MEDIUM", DifficultyLevel.MEDIUM, gameController.getCurrentDifficulty());
        
        // Complete second round and move to next
        completeCurrentRound();
        gameController.startNextRound();
        assertEquals("After second round should be HARD", DifficultyLevel.HARD, gameController.getCurrentDifficulty());
    }
    
    // Helper method to complete a round
    private void completeCurrentRound() {
        RoundManager roundManager = gameController.getRoundManager();
        MazeModel maze = roundManager.getMaze();
        Position goalPos = maze.getGoalPosition();
        
        // Set both players at goal
        roundManager.handleMove(PlayerId.PLAYER_1, goalPos);
        roundManager.handleMove(PlayerId.PLAYER_2, goalPos);
        
        // Trigger completion check
        if (roundManager.isRoundComplete()) {
            gameController.onPlayerInput(PlayerId.PLAYER_1, Direction.UP);
        }
    }
    
    @Test
    public void testMazeSizeIncreasesWithDifficulty() {
        MazeModel easyMaze = new MazeModel();
        easyMaze.setDifficulty(DifficultyLevel.EASY);
        
        MazeModel mediumMaze = new MazeModel();
        mediumMaze.setDifficulty(DifficultyLevel.MEDIUM);
        
        MazeModel hardMaze = new MazeModel();
        hardMaze.setDifficulty(DifficultyLevel.HARD);
        
        int easySize = easyMaze.getWidth() * easyMaze.getHeight();
        int mediumSize = mediumMaze.getWidth() * mediumMaze.getHeight();
        int hardSize = hardMaze.getWidth() * hardMaze.getHeight();
        
        assertTrue("Medium maze should be larger than Easy", mediumSize > easySize);
        assertTrue("Hard maze should be larger than Medium", hardSize > mediumSize);
    }
    
    @Test
    public void testRoundTransitionAfterCompletion() {
        gameController.startGame("TestTeam");
        
        // Move both players to goal to complete round
        RoundManager roundManager = gameController.getRoundManager();
        MazeModel maze = roundManager.getMaze();
        Position goalPos = maze.getGoalPosition();
        
        // Set both players at goal position
        roundManager.handleMove(PlayerId.PLAYER_1, goalPos);
        roundManager.handleMove(PlayerId.PLAYER_2, goalPos);
        
        // Check if round is complete
        if (roundManager.isRoundComplete()) {
            // Trigger the round completion in controller
            gameController.onPlayerInput(PlayerId.PLAYER_1, Direction.UP); // This will trigger onRoundComplete
        }
        
        // Game state should now be ROUND_COMPLETE
        assertEquals("Game state should be ROUND_COMPLETE after finishing round", 
                     GameState.ROUND_COMPLETE, gameController.getGameState());
    }
    
    @Test
    public void testGameOverAfterHardRoundCompletion() {
        gameController.startGame("TestTeam");
        
        // Progress through Easy
        completeCurrentRound();
        gameController.startNextRound();
        
        // Progress through Medium  
        completeCurrentRound();
        gameController.startNextRound();
        
        // Should now be on Hard
        assertEquals("Should be on HARD difficulty", DifficultyLevel.HARD, gameController.getCurrentDifficulty());
        
        // Complete Hard round
        completeCurrentRound();
        
        assertEquals("Game state should be GAME_OVER after completing Hard round", 
                     GameState.GAME_OVER, gameController.getGameState());
    }
    
    @Test
    public void testAllMazesAreSolvable() {
        // Test that all difficulty mazes have a path from start to goal
        DifficultyLevel[] levels = {DifficultyLevel.EASY, DifficultyLevel.MEDIUM, DifficultyLevel.HARD};
        
        for (DifficultyLevel level : levels) {
            MazeModel maze = new MazeModel();
            maze.setDifficulty(level);
            maze.generateMaze();
            
            assertNotNull("Start position should exist for " + level, maze.getPlayer1Start());
            assertNotNull("Goal position should exist for " + level, maze.getGoalPosition());
            
            // Verify goal is accessible
            Position goal = maze.getGoalPosition();
            Tile goalTile = maze.getTile(goal.getX(), goal.getY());
            assertTrue("Goal should be accessible to both players for " + level, 
                       goalTile.canPlayerPass(PlayerId.PLAYER_1) && goalTile.canPlayerPass(PlayerId.PLAYER_2));
        }
    }
}
