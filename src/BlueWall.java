public class BlueWall extends Tile {
    
    public BlueWall(Position position) {
        super(position, TileType.BLUE_WALL);
    }
    
    @Override
    public boolean canPlayerPass(PlayerId playerId) {
        return playerId == PlayerId.PLAYER_1;
    }
}
