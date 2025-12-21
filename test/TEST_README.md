# Maze Game JUnit Test Suite

## Overview
This test suite contains comprehensive JUnit tests for all requirements specified in the Software Test Plan (MG-STP v1.7).

## Test Classes

### 1. MazeGenerationTest
- **Test ID**: T-SRS-MG-001
- **Verifies**: SRS-MG-001, SRS-MG-001.1
- **Tests**: Maze generation, dimensions, start/goal positions, goal accessibility

### 2. PlayerMovementTest
- **Test ID**: T-SRS-MG-001.1
- **Verifies**: SRS-MG-001.2, SRS-MG-001.3, SRS-MG-001.4
- **Tests**: WASD controls (Player 1), Arrow key controls (Player 2), movement in all directions

### 3. AdjacentTileMovementTest
- **Test ID**: T-SRS-MG-002
- **Verifies**: SRS-MG-002, SRS-MG-002.1
- **Tests**: Adjacent tile movement, wall collision, single-tile movement

### 4. ColoredWallTraversalTest
- **Test ID**: T-SRS-MG-003, T-SRS-MG-004
- **Verifies**: SRS-MG-003.1, SRS-MG-003.2, SRS-MG-004.1, SRS-MG-004.2
- **Tests**: Blue wall (Player 1), Red wall (Player 2), Regular walls, Goal accessibility

### 5. TimerFunctionalityTest
- **Test ID**: T-SRS-MG-005, T-SRS-MG-006
- **Verifies**: SRS-MG-005.1, SRS-MG-006.1
- **Tests**: Timer start, elapsed time tracking, time expiration, timer reset

### 6. DifficultyLevelTest
- **Test ID**: T-SRS-MG-007, T-SRS-MG-007.1
- **Verifies**: SRS-MG-007.1, SRS-MG-007.2
- **Tests**: Easy (10x10), Medium (15x15), Hard (25x25), difficulty progression, maze solvability

### 7. ScoreRecordingTest
- **Test ID**: T-SRS-MG-008, T-SRS-MG-008.1
- **Verifies**: SRS-MG-008.1, SRS-MG-008.2, SRS-MG-008.3
- **Tests**: Team name validation, 20-character limit, total time calculation

### 8. DatabaseOperationsTest
- **Test ID**: T-SRS-MG-009, T-SRS-MG-009.1
- **Verifies**: SRS-MG-009.1, SRS-MG-009.2
- **Tests**: Score saving, top 10 retrieval, sorting, duplicate team name detection

## Prerequisites

### 1. JUnit 4.13
Download from: https://github.com/junit-team/junit4/releases
Or add to Maven/Gradle:

**Maven:**
```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.2</version>
    <scope>test</scope>
</dependency>
```

**Gradle:**
```gradle
testImplementation 'junit:junit:4.13.2'
```

### 2. MySQL Database
- Ensure MySQL is running
- Database `mazegame_db` must exist
- Run `database_setup.sql` if not already done

### 3. MySQL Connector/J
- Version 8.1.0 must be in classpath

## Running Tests

### Option 1: Command Line (Individual Test)
```bash
# Compile all source files and tests
javac -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar:mysql-connector-j-8.1.0.jar *.java *Test.java

# Run a single test class
java -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar:mysql-connector-j-8.1.0.jar org.junit.runner.JUnitCore MazeGenerationTest
```

### Option 2: Command Line (All Tests via Suite)
```bash
# Compile
javac -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar:mysql-connector-j-8.1.0.jar *.java *Test.java

# Run test suite
java -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar:mysql-connector-j-8.1.0.jar org.junit.runner.JUnitCore MazeGameTestSuite
```

### Option 3: Using an IDE
**IntelliJ IDEA:**
1. Right-click on test class → Run 'TestClassName'
2. Or right-click on `MazeGameTestSuite` → Run

**Eclipse:**
1. Right-click on test class → Run As → JUnit Test
2. Or right-click on `MazeGameTestSuite` → Run As → JUnit Test

**VS Code:**
1. Install "Test Runner for Java" extension
2. Click the test icon next to test methods
3. Or use Command Palette → "Test: Run All Tests"

### Option 4: Windows Batch Script
```batch
@echo off
echo Compiling tests...
javac -cp .;junit-4.13.2.jar;hamcrest-core-1.3.jar;mysql-connector-j-8.1.0.jar *.java *Test.java

echo Running test suite...
java -cp .;junit-4.13.2.jar;hamcrest-core-1.3.jar;mysql-connector-j-8.1.0.jar org.junit.runner.JUnitCore MazeGameTestSuite

pause
```

### Option 5: Linux/Mac Shell Script
```bash
#!/bin/bash
echo "Compiling tests..."
javac -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar:mysql-connector-j-8.1.0.jar *.java *Test.java

echo "Running test suite..."
java -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar:mysql-connector-j-8.1.0.jar org.junit.runner.JUnitCore MazeGameTestSuite
```

## Test Results Interpretation

### Success Output:
```
JUnit version 4.13.2
........
Time: 0.123

OK (8 tests)
```

### Failure Output:
```
JUnit version 4.13.2
.F..
Time: 0.123
There was 1 failure:
1) testName(TestClass)
java.lang.AssertionError: Expected <value> but was <actual>
...

FAILURES!!!
Tests run: 4,  Failures: 1
```

## Important Notes

### Database Tests
- **DatabaseOperationsTest** requires a running MySQL database
- If MySQL is not available, these tests will fail
- Comment out `DatabaseOperationsTest.class` in `MazeGameTestSuite` to skip database tests

### Timing Tests
- Some tests use `Thread.sleep()` and may take longer to execute
- Timer-based tests may occasionally fail on very slow systems

### Test Isolation
- Each test should be independent
- Tests use `@Before` to set up fresh instances
- Database tests use unique team names to avoid conflicts

## Troubleshooting

### ClassNotFoundException
```
Make sure junit-4.13.2.jar and hamcrest-core-1.3.jar are in classpath
```

### Database Connection Errors
```
Check:
1. MySQL is running
2. Database mazegame_db exists
3. Credentials in JDBCConnectionImpl.java are correct
```

### Compilation Errors
```
Ensure all .java files are compiled together:
javac -cp .:junit-4.13.2.jar *.java *Test.java
```

## Coverage Summary

| Requirement | Test Class | Status |
|-------------|------------|--------|
| SRS-MG-001 | MazeGenerationTest | ✅ |
| SRS-MG-001.1-1.4 | PlayerMovementTest | ✅ |
| SRS-MG-002 | AdjacentTileMovementTest | ✅ |
| SRS-MG-003 | ColoredWallTraversalTest | ✅ |
| SRS-MG-004 | ColoredWallTraversalTest | ✅ |
| SRS-MG-005 | TimerFunctionalityTest | ✅ |
| SRS-MG-006 | TimerFunctionalityTest | ✅ |
| SRS-MG-007 | DifficultyLevelTest | ✅ |
| SRS-MG-008 | ScoreRecordingTest | ✅ |
| SRS-MG-009 | DatabaseOperationsTest | ✅ |

**Total**: 100% of SRS requirements covered by automated tests
