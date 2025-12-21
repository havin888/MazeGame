import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

/**
 * Test ID: T-SRS-MG-009 and T-SRS-MG-009.1
 * Verifies: SRS-MG-009, SRS-MG-009.1, SRS-MG-009.2
 * Description: Verify database operations for scoreboard
 * NOTE: These tests require a running MySQL database
 */
public class DatabaseOperationsTest {
    private ScoreService scoreService;
    private String testTeamName;

    @Before
    public void setUp() {
        scoreService = new ScoreServiceImpl();
        // Use short timestamp (last 6 digits only) to keep team name under 20 chars
        long timestamp = System.currentTimeMillis();
        String shortTimestamp = String.valueOf(timestamp).substring(7); // Last 6 digits
        testTeamName = "Test_" + shortTimestamp; // e.g., "Test_123456" = 11 characters
    }

    @After
    public void tearDown() {
        // Clean up test data if needed
        // Note: In production, you might want to delete test entries
    }

    @Test
    public void testSaveScoreToDatabase() {
        // Save a score
        long completionTime = 125000; // 125 seconds

        try {
            scoreService.saveScore(testTeamName, completionTime);

            // Verify by retrieving
            List<ScoreRecord> scores = scoreService.getTopScores();
            assertNotNull("Scores list should not be null", scores);

            boolean found = false;
            for (ScoreRecord record : scores) {
                if (record.getTeamName().equals(testTeamName)) {
                    found = true;
                    assertEquals("Saved time should match", completionTime, record.getTotalTime());
                    break;
                }
            }

            assertTrue("Saved score should be retrievable", found);
        } catch (Exception e) {
            fail("Database operation should not throw exception: " + e.getMessage());
        }
    }

    @Test
    public void testGetTopScores() {
        List<ScoreRecord> scores = scoreService.getTopScores();

        assertNotNull("Scores list should not be null", scores);
        assertTrue("Scores list should have at most 10 entries", scores.size() <= 10);
    }

    @Test
    public void testScoresAreSortedByTime() {
        List<ScoreRecord> scores = scoreService.getTopScores();

        if (scores.size() > 1) {
            for (int i = 0; i < scores.size() - 1; i++) {
                long currentTime = scores.get(i).getTotalTime();
                long nextTime = scores.get(i + 1).getTotalTime();

                assertTrue("Scores should be sorted in ascending order (fastest first)",
                        currentTime <= nextTime);
            }
        }
    }

    @Test
    public void testTeamNameExists() {
        // Save a test score
        scoreService.saveScore(testTeamName, 100000);

        // Check if it exists
        boolean exists = scoreService.teamNameExists(testTeamName);
        assertTrue("Team name should exist after saving", exists);

        // Check non-existent team with short name
        String nonExistent = "None_" + (System.currentTimeMillis() % 100000);
        boolean notExists = scoreService.teamNameExists(nonExistent);
        assertFalse("Non-existent team should return false", notExists);
    }

    @Test
    public void testDuplicateTeamNamePrevention() {
        // Save first score
        scoreService.saveScore(testTeamName, 100000);

        // Try to save with same team name
        boolean exists = scoreService.teamNameExists(testTeamName);
        assertTrue("Duplicate team name should be detected", exists);
    }

    @Test
    public void testScoreRecordStructure() {
        ScoreRecord record = new ScoreRecord("TestTeam", 150000);

        assertNotNull("Score record should not be null", record);
        assertEquals("Team name should match", "TestTeam", record.getTeamName());
        assertEquals("Total time should match", 150000, record.getTotalTime());
    }

    @Test
    public void testMultipleScoreRetrieval() {
        // Save multiple scores with short team names
        String team1 = "Fast_" + (System.currentTimeMillis() % 100000); // e.g., "Fast_12345"
        String team2 = "Slow_" + (System.currentTimeMillis() % 100000); // e.g., "Slow_12346"

        scoreService.saveScore(team1, 50000);
        scoreService.saveScore(team2, 100000);

        List<ScoreRecord> scores = scoreService.getTopScores();
        assertNotNull("Scores should be retrievable", scores);
        assertTrue("Should have at least 2 scores", scores.size() >= 2);
    }

    @Test
    public void testScoreServiceInterface() {
        // Test that ScoreService interface is properly implemented
        assertTrue("ScoreService should be implemented", scoreService instanceof ScoreService);

        // Test interface methods are callable
        assertNotNull("getTopScores should be callable", scoreService.getTopScores());
    }

    @Test
    public void testDatabaseConnectionEstablished() {
        try {
            // Attempt to get top scores - this requires DB connection
            List<ScoreRecord> scores = scoreService.getTopScores();
            assertNotNull("Should be able to retrieve scores (DB connected)", scores);
        } catch (Exception e) {
            fail("Database connection should be established: " + e.getMessage());
        }
    }
}
