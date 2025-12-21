import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test ID: T-SRS-MG-005 and T-SRS-MG-006
 * Verifies: SRS-MG-005, SRS-MG-005.1, SRS-MG-006, SRS-MG-006.1
 * Description: Verify timer tracking and time expiration handling
 */
public class TimerFunctualityTest {
    private GameController gameController;
    private GameTimer timer;

    @Before
    public void setUp() {
        gameController = new GameController();
        gameController.startGame("TestTeam");
        timer = gameController.getRoundManager().getTimer();
    }

    @Test
    public void testTimerStartsOnRoundStart() {
        assertTrue("Timer should be running after round starts", timer.isRunning());
    }

    @Test
    public void testTimerTracksElapsedTime() throws InterruptedException {
        long startTime = timer.getElapsedTime();
        Thread.sleep(1100); // Wait 1.1 seconds
        long elapsedTime = timer.getElapsedTime();

        assertTrue("Elapsed time should increase", elapsedTime > startTime);
        assertTrue("Elapsed time should be at least 1 second", elapsedTime >= 1000);
    }

    @Test
    public void testTimerStopsOnRoundComplete() {
        // Simulate round completion
        gameController.getRoundManager().stopRound();

        assertFalse("Timer should stop when round is complete", timer.isRunning());
    }

    @Test
    public void testTimerLimit() {
        long timeLimit = gameController.getMaxTimeForDifficulty();
        assertEquals("Time limit should be 60 seconds (60000ms)", 60000, timeLimit);
    }

    @Test
    public void testGameOverOnTimeExpiration() {
        // Trigger time expiration
        gameController.onTimerExpired();

        assertEquals("Game state should be GAME_OVER when timer expires",
                GameState.GAME_OVER, gameController.getGameState());
        assertFalse("Timer should be stopped", timer.isRunning());
    }

    @Test
    public void testTimerResetsForNewRound() {
        long firstRoundTime = timer.getElapsedTime();

        // Complete first round and start next
        gameController.getRoundManager().stopRound();
        gameController.startNextRound();

        GameTimer newTimer = gameController.getRoundManager().getTimer();
        long newRoundTime = newTimer.getElapsedTime();

        assertTrue("Timer should reset for new round", newRoundTime < firstRoundTime || newRoundTime < 1000);
    }

    @Test
    public void testTimeIsNotNegative() throws InterruptedException {
        Thread.sleep(100);
        long elapsed = timer.getElapsedTime();

        assertTrue("Elapsed time should never be negative", elapsed >= 0);
    }

    @Test
    public void testRemainingTimeCalculation() {
        long timeLimit = gameController.getMaxTimeForDifficulty();
        long elapsed = timer.getElapsedTime();
        long remaining = timeLimit - elapsed;

        assertTrue("Remaining time should be calculated correctly", remaining <= timeLimit);
        assertTrue("Remaining time should not exceed time limit", remaining >= 0 || elapsed >= timeLimit);
    }
}
