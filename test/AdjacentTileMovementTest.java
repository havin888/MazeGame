import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test ID: T-SRS-MG-002
 * Verifies: SRS-MG-002, SRS-MG-002.1
 * Description: Verify that the system allows players to move to adjacent empty tiles
 */
public class AdjacentTileMovementTest {
    private GameController gameController;
    private RoundManager roundManager;
    
    @Before
    public void setUp() {
        gameController = new GameController();
        gameController.startGame("TestTeam");
        roundManager = gameController.getRoundManager();
    }
    
    @Test
    public void testPlayerCanMoveToAdjacentEmptyTile() {
        // Get initial position
        PlayerState player1State = roundManager.getPlayers().get(PlayerId.PLAYER_1);
        Position initialPos = player1State.getPosition();
        
        // Find an adjacent empty tile
        Direction validDirection = findValidMove(PlayerId.PLAYER_1, initialPos);
        
        if (validDirection != null) {
            gameController.onPlayerInput(PlayerId.PLAYER_1, validDirection);
            Position newPos = player1State.getPosition();
            
            // Verify position changed
            assertNotEquals("Position should change after valid move", initialPos, newPos);
            
            // Verify moved to adjacent tile (distance = 1)
            int distance = Math.abs(newPos.getX() - initialPos.getX()) + Math.abs(newPos.getY() - initialPos.getY());
            assertEquals("Should move exactly 1 tile", 1, distance);
        }
    }
    
    @Test
    public void testPlayerCannotMoveToWall() {
        PlayerState player1State = roundManager.getPlayers().get(PlayerId.PLAYER_1);
        Position initialPos = player1State.getPosition();
        
        // Find a direction with a wall
        Direction wallDirection = findWallDirection(PlayerId.PLAYER_1, initialPos);
        
        if (wallDirection != null) {
            gameController.onPlayerInput(PlayerId.PLAYER_1, wallDirection);
            Position newPos = player1State.getPosition();
            
            // Verify position did not change
            assertEquals("Player should not move into wall", initialPos, newPos);
        }
    }
    
    @Test
    public void testMoveOnlyOnetile() {
        PlayerState player1State = roundManager.getPlayers().get(PlayerId.PLAYER_1);
        Position initialPos = player1State.getPosition();
        
        // Try to move
        gameController.onPlayerInput(PlayerId.PLAYER_1, Direction.DOWN);
        Position newPos = player1State.getPosition();
        
        if (!initialPos.equals(newPos)) {
            // If moved, verify it's only 1 tile
            int distance = Math.abs(newPos.getX() - initialPos.getX()) + Math.abs(newPos.getY() - initialPos.getY());
            assertEquals("Should move exactly 1 tile, not multiple tiles", 1, distance);
        }
    }
    
    @Test
    public void testPositionUpdateAfterMove() {
        PlayerState player1State = roundManager.getPlayers().get(PlayerId.PLAYER_1);
        Position initialPos = new Position(player1State.getPosition().getX(), player1State.getPosition().getY());
        
        // Make a valid move
        Direction validDirection = findValidMove(PlayerId.PLAYER_1, initialPos);
        
        if (validDirection != null) {
            gameController.onPlayerInput(PlayerId.PLAYER_1, validDirection);
            Position updatedPos = player1State.getPosition();
            
            // Verify the player state was updated
            assertNotNull("Player position should not be null", updatedPos);
            assertNotEquals("Player position should be updated", initialPos, updatedPos);
        }
    }
    
    // Helper methods
    private Direction findValidMove(PlayerId playerId, Position from) {
        Direction[] directions = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
        MazeModel maze = roundManager.getMaze();
        
        for (Direction dir : directions) {
            int newX = from.getX();
            int newY = from.getY();
            
            switch (dir) {
                case UP: newY--; break;
                case DOWN: newY++; break;
                case LEFT: newX--; break;
                case RIGHT: newX++; break;
            }
            
            if (maze.isWalkable(playerId, newX, newY)) {
                return dir;
            }
        }
        return null;
    }
    
    private Direction findWallDirection(PlayerId playerId, Position from) {
        Direction[] directions = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
        MazeModel maze = roundManager.getMaze();
        
        for (Direction dir : directions) {
            int newX = from.getX();
            int newY = from.getY();
            
            switch (dir) {
                case UP: newY--; break;
                case DOWN: newY++; break;
                case LEFT: newX--; break;
                case RIGHT: newX++; break;
            }
            
            if (!maze.isWalkable(playerId, newX, newY)) {
                return dir;
            }
        }
        return null;
    }
}
