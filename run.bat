@echo off
REM Maze Game - Compile and Run Script for Windows

echo ========================================
echo Maze Game - Setup and Run
echo ========================================
echo.

REM Check if MySQL connector is available
if not exist "mysql-connector-j-8.1.0.jar" (
    echo WARNING: mysql-connector-j-8.1.0.jar not found!
    echo Please download it from: https://dev.mysql.com/downloads/connector/j/
    echo and place it in the current directory.
    echo.
    pause
)

REM Compile
echo Compiling Java files...
javac -cp .;mysql-connector-j-8.1.0.jar *.java

if %ERRORLEVEL% EQU 0 (
    echo [OK] Compilation successful!
    echo.
    
    REM Run the game
    echo Starting Maze Game...
    echo.
    java -cp .;mysql-connector-j-8.1.0.jar MazeGameGUI
) else (
    echo [ERROR] Compilation failed. Please check the error messages above.
    pause
    exit /b 1
)
