import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MazeModel {
    private Map<Position, Tile> tiles;
    private DifficultyLevel difficulty;
    private int width;
    private int height;
    private Position player1Start;
    private Position player2Start;
    private Position goalPosition;
    
    public MazeModel() {
        this.tiles = new HashMap<>();
        this.difficulty = DifficultyLevel.EASY;
        this.width = 10;
        this.height = 10;
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
        // set dimensions based on dificulty
        switch (difficulty) {
            case EASY:
                this.width = 10;
                this.height = 10;
                break;
            case MEDIUM:
                this.width = 15;
                this.height = 15;
                break;
            case HARD:
                this.width = 25;
                this.height = 25;
                break;
        }
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public Position getPlayer1Start() {
        return player1Start;
    }
    
    public Position getPlayer2Start() {
        return player2Start;
    }
    
    public Position getGoalPosition() {
        return goalPosition;
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
    
    public void generateMaze() {
        tiles.clear();
        Random random = new Random();
        
        // initialize all tiles as regular walls
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles.put(new Position(x, y), new RegularWall(new Position(x, y)));
            }
        }
        
        // using DFS to carve paths
        Stack<Position> stack = new Stack<>();
        Position start = new Position(1, 1);
        tiles.put(start, new Floor(start));
        stack.push(start);
        
        int[] dx = {0, 0, 2, -2};
        int[] dy = {2, -2, 0, 0};
        
        while (!stack.isEmpty()) {
            Position current = stack.peek();
            List<Integer> directions = new ArrayList<>();
            
            for (int i = 0; i < 4; i++) {
                int nx = current.getX() + dx[i];
                int ny = current.getY() + dy[i];
                
                if (nx > 0 && nx < width - 1 && ny > 0 && ny < height - 1) {
                    Tile tile = getTile(nx, ny);
                    if (tile instanceof RegularWall) {
                        directions.add(i);
                    }
                }
            }
            
            if (!directions.isEmpty()) {
                int dir = directions.get(random.nextInt(directions.size()));
                int nx = current.getX() + dx[dir];
                int ny = current.getY() + dy[dir];
                int mx = current.getX() + dx[dir] / 2;
                int my = current.getY() + dy[dir] / 2;
                
                tiles.put(new Position(mx, my), new Floor(new Position(mx, my)));
                tiles.put(new Position(nx, ny), new Floor(new Position(nx, ny)));
                stack.push(new Position(nx, ny));
            } else {
                stack.pop();
            }
        }
        
        player1Start = new Position(1, 1);
        player2Start = new Position(1, 1);
        
        goalPosition = new Position(width - 2, height - 2);
        
        tiles.put(goalPosition, new Floor(goalPosition));
        
        for (int dgy = -1; dgy <= 1; dgy++) {
            for (int dgx = -1; dgx <= 1; dgx++) {
                int gx = goalPosition.getX() + dgx;
                int gy = goalPosition.getY() + dgy;
                if (gx > 0 && gx < width - 1 && gy > 0 && gy < height - 1) {
                    Position pos = new Position(gx, gy);
                    Tile tile = getTile(gx, gy);
                    // only convert to floor if it's a regular wall
                    if (tile instanceof RegularWall) {
                        tiles.put(pos, new Floor(pos));
                    }
                }
            }
        }
        
        int coloredWallCount = (int) (width * height * 0.08); // reduced from 0.1
        int attempts = 0;
        int maxAttempts = coloredWallCount * 10;
        
        while (coloredWallCount > 0 && attempts < maxAttempts) {
            attempts++;
            int x = random.nextInt(width - 2) + 1;
            int y = random.nextInt(height - 2) + 1;
            Position pos = new Position(x, y);
            Tile tile = getTile(x, y);
            
            int distToStart = Math.abs(x - player1Start.getX()) + Math.abs(y - player1Start.getY());
            int distToGoal = Math.abs(x - goalPosition.getX()) + Math.abs(y - goalPosition.getY());
            
            if (tile instanceof RegularWall && distToStart > 3 && distToGoal > 3) {
                if (random.nextBoolean()) {
                    tiles.put(pos, new BlueWall(pos));
                } else {
                    tiles.put(pos, new RedWall(pos));
                }
                coloredWallCount--;
            }
        }
        
        // creates a special goal tile
        Tile goalTile = new Tile(goalPosition, TileType.GOAL);
        tiles.put(goalPosition, goalTile);
    }
}