-- MySQL Database Setup for Maze Game
-- Create database
CREATE DATABASE IF NOT EXISTS mazegame_db;
USE mazegame_db;

-- Create SCOREBOARD table
CREATE TABLE IF NOT EXISTS SCOREBOARD (
    id INT AUTO_INCREMENT PRIMARY KEY,
    team_name VARCHAR(20) NOT NULL,
    completion_time BIGINT NOT NULL,
    play_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_completion_time (completion_time)
);

-- Insert some sample data for testing
INSERT INTO SCOREBOARD (team_name, completion_time) VALUES
('SpeedRunners', 125000),
('MazeMasters', 138000),
('QuickSolvers', 142000),
('TeamAlpha', 156000),
('BetaTesters', 167000),
('GammaForce', 178000),
('DeltaSquad', 189000),
('EpsilonCrew', 195000),
('ZetaWarriors', 203000),
('EtaLegends', 215000);
