public class RedWall extends Tile {

    public RedWall(Position position) {
        super(position, TileType.RED_WALL);
    }

    @Override
    public boolean canPlayerPass(PlayerId playerId) {
        return playerId == PlayerId.PLAYER_2;
    }
}
