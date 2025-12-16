#!/bin/bash

# Maze Game - Compile and Run Script

echo "========================================"
echo "Maze Game - Setup and Run"
echo "========================================"
echo ""

# Check if MySQL connector is available
if [ ! -f "mysql-connector-j-8.1.0.jar" ]; then
    echo "WARNING: mysql-connector-j-8.1.0.jar not found!"
    echo "Please download it from: https://dev.mysql.com/downloads/connector/j/"
    echo "and place it in the current directory."
    echo ""
    read -p "Press Enter to continue anyway or Ctrl+C to abort..."
fi

# Compile
echo "Compiling Java files..."
javac -cp .:mysql-connector-j-8.1.0.jar *.java

if [ $? -eq 0 ]; then
    echo "✓ Compilation successful!"
    echo ""
    
    # Run the game
    echo "Starting Maze Game..."
    echo ""
    java -cp .:mysql-connector-j-8.1.0.jar MazeGameGUI
else
    echo "✗ Compilation failed. Please check the error messages above."
    exit 1
fi
