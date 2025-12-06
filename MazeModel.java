import java.util.Map;
import java.util.HashMap;

public class MazeModel {
    private Map<Position, Tile> tiles;
    private DifficultyLevel difficulty;
    
    public MazeModel() {
        this.tiles = new HashMap<>();
        this.difficulty = DifficultyLevel.EASY;
    }
    
    public Map<Position, Tile> getTiles() {
        return tiles;
    }
    
    public void setTiles(Map<Position, Tile> tiles) {
        this.tiles = tiles;
    }
    
    public DifficultyLevel getDifficulty() {
        return difficulty;
    }
    
    public void setDifficulty(DifficultyLevel difficulty) {
        this.difficulty = difficulty;
    }
    
    public Tile getTile(int x, int y) {
        return tiles.get(new Position(x, y));
    }
    
    public boolean isWalkable(PlayerId playerId, int x, int y) {
        Tile tile = getTile(x, y);
        if (tile == null) {
            return false;
        }
        return tile.canPlayerPass(playerId);
    }
}
