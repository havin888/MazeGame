public class Floor extends Tile {
    
    public Floor(Position position) {
        super(position, TileType.FLOOR);
    }
    
    @Override
    public boolean canPlayerPass(PlayerId playerId) {
        return true;
    }
}
