import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test ID: T-SRS-MG-003 and T-SRS-MG-004
 * Verifies: SRS-MG-003, SRS-MG-003.1, SRS-MG-003.2, SRS-MG-004, SRS-MG-004.1, SRS-MG-004.2
 * Description: Verify player-specific wall color permissions
 */
public class ColoredWallTraversalTest {
    private MazeModel maze;
    
    @Before
    public void setUp() {
        maze = new MazeModel();
    }
    
    @Test
    public void testPlayer1CanPassThroughBlueWall() {
        Position pos = new Position(5, 5);
        BlueWall blueWall = new BlueWall(pos);
        
        assertTrue("Player 1 should be able to pass through blue walls", 
                   blueWall.canPlayerPass(PlayerId.PLAYER_1));
    }
    
    @Test
    public void testPlayer2CanPassThroughRedWall() {
        Position pos = new Position(5, 5);
        RedWall redWall = new RedWall(pos);
        
        assertTrue("Player 2 should be able to pass through red walls", 
                   redWall.canPlayerPass(PlayerId.PLAYER_2));
    }
    
    @Test
    public void testPlayer1CannotPassThroughRedWall() {
        Position pos = new Position(5, 5);
        RedWall redWall = new RedWall(pos);
        
        assertFalse("Player 1 should NOT be able to pass through red walls", 
                    redWall.canPlayerPass(PlayerId.PLAYER_1));
    }
    
    @Test
    public void testPlayer2CannotPassThroughBlueWall() {
        Position pos = new Position(5, 5);
        BlueWall blueWall = new BlueWall(pos);
        
        assertFalse("Player 2 should NOT be able to pass through blue walls", 
                    blueWall.canPlayerPass(PlayerId.PLAYER_2));
    }
    
    @Test
    public void testBothPlayersCannotPassRegularWall() {
        Position pos = new Position(5, 5);
        RegularWall regularWall = new RegularWall(pos);
        
        assertFalse("Player 1 should NOT be able to pass through regular walls", 
                    regularWall.canPlayerPass(PlayerId.PLAYER_1));
        assertFalse("Player 2 should NOT be able to pass through regular walls", 
                    regularWall.canPlayerPass(PlayerId.PLAYER_2));
    }
    
    @Test
    public void testBothPlayersCanPassFloor() {
        Position pos = new Position(5, 5);
        Floor floor = new Floor(pos);
        
        assertTrue("Player 1 should be able to pass through floor", 
                   floor.canPlayerPass(PlayerId.PLAYER_1));
        assertTrue("Player 2 should be able to pass through floor", 
                   floor.canPlayerPass(PlayerId.PLAYER_2));
    }
    
    @Test
    public void testBothPlayersCanReachGoal() {
        Position pos = new Position(5, 5);
        Tile goalTile = new Tile(pos, TileType.GOAL);
        
        assertTrue("Player 1 should be able to reach goal", 
                   goalTile.canPlayerPass(PlayerId.PLAYER_1));
        assertTrue("Player 2 should be able to reach goal", 
                   goalTile.canPlayerPass(PlayerId.PLAYER_2));
    }
    
    @Test
    public void testPlayer1WallColorPermission() {
        Player player1 = new Player(PlayerId.PLAYER_1, WallColor.BLUE);
        
        assertTrue("Player 1 should be allowed through blue walls", 
                   player1.canPassWall(WallColor.BLUE));
        assertFalse("Player 1 should NOT be allowed through red walls", 
                    player1.canPassWall(WallColor.RED));
        assertTrue("Player 1 should be allowed through no-color walls", 
                   player1.canPassWall(WallColor.NONE));
    }
    
    @Test
    public void testPlayer2WallColorPermission() {
        Player player2 = new Player(PlayerId.PLAYER_2, WallColor.RED);
        
        assertTrue("Player 2 should be allowed through red walls", 
                   player2.canPassWall(WallColor.RED));
        assertFalse("Player 2 should NOT be allowed through blue walls", 
                    player2.canPassWall(WallColor.BLUE));
        assertTrue("Player 2 should be allowed through no-color walls", 
                   player2.canPassWall(WallColor.NONE));
    }
}
