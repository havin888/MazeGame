public class Team {
    private String teamName;
    private Player player1;
    private Player player2;
    private long totalTime;
    
    public Team(String teamName) {
        this.teamName = teamName;
        this.player1 = new Player(PlayerId.PLAYER_1, WallColor.BLUE);
        this.player2 = new Player(PlayerId.PLAYER_2, WallColor.RED);
        this.totalTime = 0;
    }
    
    public String getTeamName() {
        return teamName;
    }
    
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    
    public Player getPlayer1() {
        return player1;
    }
    
    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }
    
    public Player getPlayer2() {
        return player2;
    }
    
    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }
    
    public long getTotalTime() {
        return totalTime;
    }
    
    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }
    
    public void addRoundTime(long millis) {
        this.totalTime += millis;
    }
}
