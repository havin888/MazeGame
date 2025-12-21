import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test ID: T-SRS-MG-001.1
 * Verifies: SRS-MG-001, SRS-MG-001.2, SRS-MG-001.3, SRS-MG-001.4
 * Description: Verify that players can move using WASD (Player 1) and Arrow keys (Player 2)
 */
public class PlayerMovementTest {
    private GameController gameController;
    
    @Before
    public void setUp() {
        gameController = new GameController();
        gameController.startGame("TestTeam");
    }
    
    @Test
    public void testPlayer1MoveUp() {
        Position initialPos = gameController.getRoundManager().getPlayers().get(PlayerId.PLAYER_1).getPosition();
        gameController.onPlayerInput(PlayerId.PLAYER_1, Direction.UP);
        Position newPos = gameController.getRoundManager().getPlayers().get(PlayerId.PLAYER_1).getPosition();
        
        // If move was valid, position should change
        if (gameController.getRoundManager().getMaze().isWalkable(PlayerId.PLAYER_1, initialPos.getX(), initialPos.getY() - 1)) {
            assertEquals("Player 1 should move up", initialPos.getY() - 1, newPos.getY());
        }
    }
    
    @Test
    public void testPlayer1MoveDown() {
        Position initialPos = gameController.getRoundManager().getPlayers().get(PlayerId.PLAYER_1).getPosition();
        gameController.onPlayerInput(PlayerId.PLAYER_1, Direction.DOWN);
        Position newPos = gameController.getRoundManager().getPlayers().get(PlayerId.PLAYER_1).getPosition();
        
        if (gameController.getRoundManager().getMaze().isWalkable(PlayerId.PLAYER_1, initialPos.getX(), initialPos.getY() + 1)) {
            assertEquals("Player 1 should move down", initialPos.getY() + 1, newPos.getY());
        }
    }
    
    @Test
    public void testPlayer1MoveLeft() {
        Position initialPos = gameController.getRoundManager().getPlayers().get(PlayerId.PLAYER_1).getPosition();
        gameController.onPlayerInput(PlayerId.PLAYER_1, Direction.LEFT);
        Position newPos = gameController.getRoundManager().getPlayers().get(PlayerId.PLAYER_1).getPosition();
        
        if (gameController.getRoundManager().getMaze().isWalkable(PlayerId.PLAYER_1, initialPos.getX() - 1, initialPos.getY())) {
            assertEquals("Player 1 should move left", initialPos.getX() - 1, newPos.getX());
        }
    }
    
    @Test
    public void testPlayer1MoveRight() {
        Position initialPos = gameController.getRoundManager().getPlayers().get(PlayerId.PLAYER_1).getPosition();
        gameController.onPlayerInput(PlayerId.PLAYER_1, Direction.RIGHT);
        Position newPos = gameController.getRoundManager().getPlayers().get(PlayerId.PLAYER_1).getPosition();
        
        if (gameController.getRoundManager().getMaze().isWalkable(PlayerId.PLAYER_1, initialPos.getX() + 1, initialPos.getY())) {
            assertEquals("Player 1 should move right", initialPos.getX() + 1, newPos.getX());
        }
    }
    
    @Test
    public void testPlayer2MoveUp() {
        Position initialPos = gameController.getRoundManager().getPlayers().get(PlayerId.PLAYER_2).getPosition();
        gameController.onPlayerInput(PlayerId.PLAYER_2, Direction.UP);
        Position newPos = gameController.getRoundManager().getPlayers().get(PlayerId.PLAYER_2).getPosition();
        
        if (gameController.getRoundManager().getMaze().isWalkable(PlayerId.PLAYER_2, initialPos.getX(), initialPos.getY() - 1)) {
            assertEquals("Player 2 should move up", initialPos.getY() - 1, newPos.getY());
        }
    }
    
    @Test
    public void testPlayer2MoveDown() {
        Position initialPos = gameController.getRoundManager().getPlayers().get(PlayerId.PLAYER_2).getPosition();
        gameController.onPlayerInput(PlayerId.PLAYER_2, Direction.DOWN);
        Position newPos = gameController.getRoundManager().getPlayers().get(PlayerId.PLAYER_2).getPosition();
        
        if (gameController.getRoundManager().getMaze().isWalkable(PlayerId.PLAYER_2, initialPos.getX(), initialPos.getY() + 1)) {
            assertEquals("Player 2 should move down", initialPos.getY() + 1, newPos.getY());
        }
    }
    
    @Test
    public void testPlayer2MoveLeft() {
        Position initialPos = gameController.getRoundManager().getPlayers().get(PlayerId.PLAYER_2).getPosition();
        gameController.onPlayerInput(PlayerId.PLAYER_2, Direction.LEFT);
        Position newPos = gameController.getRoundManager().getPlayers().get(PlayerId.PLAYER_2).getPosition();
        
        if (gameController.getRoundManager().getMaze().isWalkable(PlayerId.PLAYER_2, initialPos.getX() - 1, initialPos.getY())) {
            assertEquals("Player 2 should move left", initialPos.getX() - 1, newPos.getX());
        }
    }
    
    @Test
    public void testPlayer2MoveRight() {
        Position initialPos = gameController.getRoundManager().getPlayers().get(PlayerId.PLAYER_2).getPosition();
        gameController.onPlayerInput(PlayerId.PLAYER_2, Direction.RIGHT);
        Position newPos = gameController.getRoundManager().getPlayers().get(PlayerId.PLAYER_2).getPosition();
        
        if (gameController.getRoundManager().getMaze().isWalkable(PlayerId.PLAYER_2, initialPos.getX() + 1, initialPos.getY())) {
            assertEquals("Player 2 should move right", initialPos.getX() + 1, newPos.getX());
        }
    }
    
    @Test
    public void testPlayersStartAtSamePosition() {
        Position p1Pos = gameController.getRoundManager().getPlayers().get(PlayerId.PLAYER_1).getPosition();
        Position p2Pos = gameController.getRoundManager().getPlayers().get(PlayerId.PLAYER_2).getPosition();
        
        assertEquals("Players should start at same X position", p1Pos.getX(), p2Pos.getX());
        assertEquals("Players should start at same Y position", p1Pos.getY(), p2Pos.getY());
    }
}
