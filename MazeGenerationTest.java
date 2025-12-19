import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test ID: T-SRS-MG-001
 * Verifies: SRS-MG-001, SRS-MG-001.1
 * Description: Verify that upon the start of a new game, the system shall generate a two-player maze.
 */
public class MazeGenerationTest {
    private GameController gameController;
    
    @Before
    public void setUp() {
        gameController = new GameController();
    }
    
    @Test
    public void testMazeGenerationOnGameStart() {
        // Start game with team name
        gameController.startGame("TestTeam");
        
        // Verify maze is generated
        MazeModel maze = gameController.getRoundManager().getMaze();
        assertNotNull("Maze should be generated", maze);
        
        // Verify maze has tiles
        assertNotNull("Maze should have tiles", maze.getTiles());
        assertFalse("Maze tiles should not be empty", maze.getTiles().isEmpty());
    }
    
    @Test
    public void testMazeGenerationForEasyDifficulty() {
        gameController.startGame("TestTeam");
        MazeModel maze = gameController.getRoundManager().getMaze();
        
        assertEquals("Easy difficulty should have 10x10 dimensions", 10, maze.getWidth());
        assertEquals("Easy difficulty should have 10x10 dimensions", 10, maze.getHeight());
        assertEquals("Initial difficulty should be EASY", DifficultyLevel.EASY, maze.getDifficulty());
    }
    
    @Test
    public void testMazeHasStartAndGoalPositions() {
        gameController.startGame("TestTeam");
        MazeModel maze = gameController.getRoundManager().getMaze();
        
        assertNotNull("Maze should have player 1 start position", maze.getPlayer1Start());
        assertNotNull("Maze should have player 2 start position", maze.getPlayer2Start());
        assertNotNull("Maze should have goal position", maze.getGoalPosition());
    }
    
    @Test
    public void testGoalPositionIsAccessible() {
        gameController.startGame("TestTeam");
        MazeModel maze = gameController.getRoundManager().getMaze();
        
        Position goal = maze.getGoalPosition();
        Tile goalTile = maze.getTile(goal.getX(), goal.getY());
        
        assertNotNull("Goal tile should exist", goalTile);
        assertTrue("Goal should be accessible to Player 1", goalTile.canPlayerPass(PlayerId.PLAYER_1));
        assertTrue("Goal should be accessible to Player 2", goalTile.canPlayerPass(PlayerId.PLAYER_2));
    }
}
