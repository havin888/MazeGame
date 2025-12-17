public class RegularWall extends Tile {

    public RegularWall(Position position) {
        super(position, TileType.REGULAR_WALL);
    }

    @Override
    public boolean canPlayerPass(PlayerId playerId) {
        return false;
    }
}
