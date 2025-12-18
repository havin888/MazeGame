import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MazePanel extends JPanel {
    private GameController gameController;
    private static final int CELL_SIZE = 20;
    
    public MazePanel(GameController gameController) {
        this.gameController = gameController;
        setBackground(Color.BLACK);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        MazeModel maze = gameController.getRoundManager().getMaze();
        Map<PlayerId, PlayerState> players = gameController.getRoundManager().getPlayers();
        
        if (maze == null) {
            return;
        }
        
        int width = maze.getWidth();
        int height = maze.getHeight();
        
        int offsetX = (getWidth() - width * CELL_SIZE) / 2;
        int offsetY = (getHeight() - height * CELL_SIZE) / 2;
        
        // draws maze
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile tile = maze.getTile(x, y);
                if (tile != null) {
                    drawTile(g, tile, offsetX + x * CELL_SIZE, offsetY + y * CELL_SIZE);
                }
            }
        }
        
        // draws players
        if (players != null) {
            PlayerState p1 = players.get(PlayerId.PLAYER_1);
            if (p1 != null) {
                drawPlayer(g, p1, offsetX, offsetY, Color.CYAN);
            }
            
            PlayerState p2 = players.get(PlayerId.PLAYER_2);
            if (p2 != null) {
                drawPlayer(g, p2, offsetX, offsetY, Color.RED);
            }
        }
    }
    
    private void drawTile(Graphics g, Tile tile, int x, int y) {
        TileType type = tile.getType();
        
        switch (type) {
            case FLOOR:
                g.setColor(Color.WHITE);
                g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                break;
                
            case REGULAR_WALL:
                g.setColor(Color.GRAY);
                g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                break;
                
            case BLUE_WALL:
                g.setColor(Color.BLUE);
                g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                break;
                
            case RED_WALL:
                g.setColor(new Color(200, 0, 0));
                g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                break;
                
            case GOAL:
                g.setColor(Color.GREEN);
                g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                // draws star pattern for goal
                g.setColor(Color.YELLOW);
                int cx = x + CELL_SIZE / 2;
                int cy = y + CELL_SIZE / 2;
                g.drawLine(cx - 5, cy, cx + 5, cy);
                g.drawLine(cx, cy - 5, cx, cy + 5);
                break;
        }
        
        // draws grid border
        g.setColor(Color.BLACK);
        g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
    }
    
    private void drawPlayer(Graphics g, PlayerState playerState, int offsetX, int offsetY, Color color) {
        Position pos = playerState.getPosition();
        int x = offsetX + pos.getX() * CELL_SIZE;
        int y = offsetY + pos.getY() * CELL_SIZE;
        
        // draws player as a circle
        g.setColor(color);
        g.fillOval(x + 3, y + 3, CELL_SIZE - 6, CELL_SIZE - 6);
        
        // draws outline
        g.setColor(Color.BLACK);
        g.drawOval(x + 3, y + 3, CELL_SIZE - 6, CELL_SIZE - 6);
        
        // if player reached goal, draw a checkmark
        if (playerState.hasReachedGoal()) {
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 12));
            g.drawString("✓", x + 5, y + 15);
        }
    }
}
