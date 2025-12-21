public class Player {
    private PlayerId playerId;
    private WallColor allowedWallColor;
    
    public Player(PlayerId playerId, WallColor allowedWallColor) {
        this.playerId = playerId;
        this.allowedWallColor = allowedWallColor;
    }
    
    public PlayerId getPlayerId() {
        return playerId;
    }
    
    public void setPlayerId(PlayerId playerId) {
        this.playerId = playerId;
    }
    
    public WallColor getAllowedWallColor() {
        return allowedWallColor;
    }
    
    public void setAllowedWallColor(WallColor allowedWallColor) {
        this.allowedWallColor = allowedWallColor;
    }
    
    public boolean canPassWall(WallColor wall) {
        return wall == WallColor.NONE || wall == this.allowedWallColor;
    }
}
