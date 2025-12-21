import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * MazeGame Test Suite
 * Runs all test classes for the Maze Game application
 * 
 * To run all tests, execute this class with JUnit
 */
@RunWith(Suite.class)
@SuiteClasses({
    MazeGenerationTest.class,
    PlayerMovementTest.class,
    AdjacentTileMovementTest.class,
    ColoredWallTraversalTest.class,
    TimerFunctualityTest.class,
    DifficultyLevelTest.class,
    ScoreRecordingTest.class,
    DatabaseOperationsTest.class
})
public class MazeGameTestSuite {
    // This class remains empty
    // It is used only as a holder for the @RunWith and @SuiteClasses annotations
}
