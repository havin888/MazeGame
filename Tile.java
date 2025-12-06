public class Tile {
    private Position position;
    private TileType type;
    
    public Tile(Position position, TileType type) {
        this.position = position;
        this.type = type;
    }
    
    public Position getPosition() {
        return position;
    }
    
    public void setPosition(Position position) {
        this.position = position;
    }
    
    public TileType getType() {
        return type;
    }
    
    public void setType(TileType type) {
        this.type = type;
    }
    
    public boolean canPlayerPass(PlayerId playerId) {
        switch (type) {
            case FLOOR:
            case GOAL:
                return true;
            case REGULAR_WALL:
                return false;
            case BLUE_WALL:
                return playerId == PlayerId.PLAYER_1;
            case RED_WALL:
                return playerId == PlayerId.PLAYER_2;
            default:
                return false;
        }
    }
}
