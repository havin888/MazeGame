#!/bin/bash

echo "========================================"
echo "Maze Game - JUnit Test Suite"
echo "========================================"
echo ""

echo "Compiling source and test files..."
javac -cp ".:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar:lib/mysql-connector-j-8.1.0.jar" \
     src/*.java test/*.java

if [ $? -eq 0 ]; then
    echo "[OK] Compilation successful!"
    echo ""
    
    echo "Running JUnit tests..."
    java -cp ".:src:test:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar:lib/mysql-connector-j-8.1.0.jar" \
         org.junit.runner.JUnitCore MazeGameTestSuite
else
    echo "[ERROR] Compilation failed."
    exit 1
fi

echo ""
