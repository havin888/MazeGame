# Maze Game - Two Player Cooperative Game

## Overview
This is a two-player cooperative maze game where both players must navigate through three rounds of increasing difficulty (Easy, Medium, Hard) to win. Each player has unique abilities to pass through colored walls.

## Features
- **Two Players**: Player 1 (Blue) controlled by WASD keys, Player 2 (Red) controlled by Arrow keys
- **Colored Walls**: Player 1 can pass through blue walls, Player 2 can pass through red walls
- **Three Difficulty Levels**: Easy (10x10), Medium (15x15), Hard (25x25)
- **Time Tracking**: Complete all rounds as fast as possible
- **Scoreboard**: Top 10 fastest teams are saved to MySQL database

## Requirements
- Java JDK 17 or higher
- MySQL 8.0 or higher
- MySQL Connector/J 8.1.0 (JDBC driver)

## Setup Instructions

### 1. Database Setup
1. Start your MySQL server
2. Run the database setup script:
   ```bash
   mysql -u root -p < database_setup.sql
   ```
3. Update database credentials in `JDBCConnectionImpl.java` if needed:
   - Default: localhost:3306
   - Database: mazegame_db
   - Username: root
   - Password: (update as needed)

### 2. Compile the Project
```bash
javac -cp .:mysql-connector-j-8.1.0.jar *.java
```

### 3. Run the Game
```bash
java -cp .:mysql-connector-j-8.1.0.jar MazeGameGUI
```

On Windows, use semicolon (;) instead of colon (:):
```bash
javac -cp .;mysql-connector-j-8.1.0.jar *.java
java -cp .;mysql-connector-j-8.1.0.jar MazeGameGUI
```

## How to Play

### Game Rules
1. Both players start at the same position in the top-left corner
2. The goal (green tile) is located at the bottom-right corner
3. Players must work together to reach the goal before time runs out
4. Complete all three rounds to win

### Controls
- **Player 1 (Blue Circle)**:
  - W: Move Up
  - S: Move Down
  - A: Move Left
  - D: Move Right
  - Can pass through: Empty spaces, Blue walls, Goal

- **Player 2 (Red Circle)**:
  - ↑: Move Up
  - ↓: Move Down
  - ←: Move Left
  - →: Move Right
  - Can pass through: Empty spaces, Red walls, Goal

### Tiles
- **White**: Empty floor (both players can walk)
- **Gray**: Regular wall (neither player can pass)
- **Blue**: Blue wall (only Player 1 can pass)
- **Red**: Red wall (only Player 2 can pass)
- **Green**: Goal (both players must reach this)

### Winning
- Both players must reach the goal tile in each round
- Complete all three rounds (Easy, Medium, Hard) to win
- Your total time is recorded on the scoreboard

### Losing
- If time runs out before both players reach the goal
- The game returns to the main menu

## Project Structure
```
.
├── Direction.java           # Enum for movement directions
├── DifficultyLevel.java     # Enum for game difficulty
├── TileType.java            # Enum for tile types
├── WallColor.java           # Enum for wall colors
├── PlayerId.java            # Enum for player identification
├── GameState.java           # Enum for game states
├── Position.java            # Position class (x, y coordinates)
├── Tile.java                # Base tile class
├── Floor.java               # Floor tile
├── RegularWall.java         # Regular wall tile
├── BlueWall.java            # Blue wall tile
├── RedWall.java             # Red wall tile
├── Player.java              # Player entity
├── Team.java                # Team with two players
├── PlayerState.java         # Player position and state
├── MazeModel.java           # Maze data and generation
├── RoundResult.java         # Round completion data
├── ScoreRecord.java         # Score entry
├── GameTimer.java           # Timer for tracking time
├── RoundManager.java        # Manages game rounds
├── ScoreService.java        # Interface for score operations
├── ScoreServiceImpl.java    # Score service implementation
├── JDBCConnection.java      # Database connection interface
├── JDBCConnectionImpl.java  # JDBC implementation
├── GameController.java      # Main game controller
├── MazePanel.java           # Maze rendering component
├── MazeGameGUI.java         # Main GUI class
├── database_setup.sql       # Database initialization script
└── database.properties      # Configuration file
```

## Architecture
The game follows a three-layer architecture:
1. **GUI Layer**: Java Swing-based user interface
2. **Backend Layer**: Game logic, maze generation, player movement
3. **Database Layer**: MySQL database for scoreboard persistence

## Database Schema
```sql
CREATE TABLE SCOREBOARD (
    id INT AUTO_INCREMENT PRIMARY KEY,
    team_name VARCHAR(20) NOT NULL,
    completion_time BIGINT NOT NULL,
    play_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_completion_time (completion_time)
);
```

## Troubleshooting

### MySQL Connection Issues
- Ensure MySQL server is running
- Check database name, username, and password in `JDBCConnectionImpl.java`
- Verify MySQL JDBC driver is in classpath

### Compilation Errors
- Ensure JDK 17 or higher is installed
- Check that all `.java` files are in the same directory
- Include MySQL Connector/J in classpath

### Game Performance
- If the game runs slowly on large mazes (Hard mode), try reducing the maze size in `MazeModel.java`
- Adjust `CELL_SIZE` in `MazePanel.java` if the maze doesn't fit on screen

## Development Team
- Havin Yılmaz
- Nermin Tunçbilek
- Berrak Tozak
- Meryem Nur Nalbant
- Müjgan Selen Karakaş
- Berra Eğcin

## Course Information
- **Course**: CS320 – Software Engineering
- **Institution**: Ozyegin University, School of Engineering
- **Project**: Maze Game Software Development

## License
This is an academic project for educational purposes.
