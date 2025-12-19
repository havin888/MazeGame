import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test ID: T-SRS-MG-008 and T-SRS-MG-008.1
 * Verifies: SRS-MG-008, SRS-MG-008.1, SRS-MG-008.2, SRS-MG-008.3
 * Description: Verify team name input and total time calculation
 */
public class ScoreRecordingTest {
    private GameController gameController;
    private Team team;

    @Before
    public void setUp() {
        gameController = new GameController();
    }

    @Test
    public void testTeamNameInputValidation() {
        String validName = "TestTeam123";
        assertTrue("Team name under 20 characters should be valid", validName.length() <= 20);

        gameController.startGame(validName);
        assertNotNull("Team should be created", gameController.getTeam());
        assertEquals("Team name should match input", validName, gameController.getTeam().getTeamName());
    }

    @Test
    public void testTeamNameMaxLength() {
        String maxLengthName = "12345678901234567890"; // Exactly 20 characters
        assertEquals("Test name should be 20 characters", 20, maxLengthName.length());

        gameController.startGame(maxLengthName);
        assertEquals("Should accept 20 character name", maxLengthName, gameController.getTeam().getTeamName());
    }

    @Test
    public void testTotalTimeCalculation() {
        gameController.startGame("TestTeam");
        Team team = gameController.getTeam();

        // Simulate round completions with different times
        team.addRoundTime(10000); // 10 seconds
        team.addRoundTime(15000); // 15 seconds
        team.addRoundTime(20000); // 20 seconds

        long totalTime = team.getTotalTime();
        assertEquals("Total time should be sum of all rounds", 45000, totalTime);
    }

    @Test
    public void testTimeCalculationFromRoundResults() {
        gameController.startGame("TestTeam");

        // Add round results
        RoundResult round1 = new RoundResult(DifficultyLevel.EASY, 12000, true);
        RoundResult round2 = new RoundResult(DifficultyLevel.MEDIUM, 18000, true);
        RoundResult round3 = new RoundResult(DifficultyLevel.HARD, 25000, true);

        long total = round1.getElapsedMillis() + round2.getElapsedMillis() + round3.getElapsedMillis();
        assertEquals("Total should be sum of all round times", 55000, total);
    }

    @Test
    public void testTeamCreationOnGameStart() {
        gameController.startGame("WinningTeam");

        Team team = gameController.getTeam();
        assertNotNull("Team should be created on game start", team);
        assertEquals("Team name should be set correctly", "WinningTeam", team.getTeamName());
        assertEquals("Initial total time should be 0", 0, team.getTotalTime());
    }

    @Test
    public void testRoundTimeAccumulation() {
        gameController.startGame("TestTeam");
        Team team = gameController.getTeam();

        long initialTime = team.getTotalTime();
        assertEquals("Initial time should be 0", 0, initialTime);

        team.addRoundTime(5000);
        assertEquals("Time should accumulate", 5000, team.getTotalTime());

        team.addRoundTime(7000);
        assertEquals("Time should continue accumulating", 12000, team.getTotalTime());
    }

    @Test
    public void testCalculateTotalTimeFromController() {
        gameController.startGame("TestTeam");

        // Initial total time should be 0
        assertEquals("Initial calculated total time should be 0", 0, gameController.calculateTotalTime());
    }

    @Test
    public void testTeamNameNotNull() {
        gameController.startGame("ValidTeam");
        Team team = gameController.getTeam();

        assertNotNull("Team name should not be null", team.getTeamName());
        assertFalse("Team name should not be empty", team.getTeamName().isEmpty());
    }
}
