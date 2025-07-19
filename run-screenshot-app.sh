#!/bin/bash

# Selenium Screenshot App Runner Script
# This script provides easy commands to run different parts of the application

echo "============================================="
echo "     Selenium Screenshot App Runner"
echo "============================================="

# Function to display usage
usage() {
    echo "Usage: $0 [OPTION]"
    echo ""
    echo "Options:"
    echo "  demo              Run the screenshot demo (recommended for first time)"
    echo "  app               Run the main screenshot application"
    echo "  test              Run all tests"
    echo "  test-chrome       Run tests with Chrome browser"
    echo "  test-firefox      Run tests with Firefox browser"
    echo "  test-headless     Run tests in headless mode"
    echo "  compile           Compile the project"
    echo "  clean             Clean and compile the project"
    echo "  help              Show this help message"
    echo ""
    echo "Examples:"
    echo "  $0 app             # Run main application"
    echo "  $0 test            # Run all tests"
    echo "  $0 test-headless   # Run tests in headless mode"
    echo ""
}

# Function to check if Maven is installed
check_maven() {
    if ! command -v mvn &> /dev/null; then
        echo "Error: Maven is not installed or not in PATH"
        echo "Please install Maven and try again"
        exit 1
    fi
}

# Function to create directories
create_directories() {
    echo "Creating necessary directories..."
    mkdir -p screenshots logs reports
    echo "Directories created successfully"
}

# Main execution based on argument
case "$1" in
    "demo")
        echo "Running Screenshot Demo..."
        check_maven
        create_directories
        mvn compile exec:java -Dexec.mainClass="com.screenshot.app.demo.ScreenshotDemo"
        ;;
    "app")
        echo "Running Screenshot Application..."
        check_maven
        create_directories
        mvn exec:java -Dexec.mainClass="com.screenshot.app.ScreenshotApp"
        ;;
    "test")
        echo "Running all tests..."
        check_maven
        create_directories
        mvn test
        ;;
    "test-chrome")
        echo "Running tests with Chrome browser..."
        check_maven
        create_directories
        mvn test -Dbrowser=chrome
        ;;
    "test-firefox")
        echo "Running tests with Firefox browser..."
        check_maven
        create_directories
        mvn test -Dbrowser=firefox
        ;;
    "test-headless")
        echo "Running tests in headless mode..."
        check_maven
        create_directories
        mvn test -Dbrowser=chrome-headless
        ;;
    "compile")
        echo "Compiling the project..."
        check_maven
        mvn compile
        ;;
    "clean")
        echo "Cleaning and compiling the project..."
        check_maven
        mvn clean compile
        ;;
    "help")
        usage
        ;;
    *)
        echo "Error: Invalid option '$1'"
        echo ""
        usage
        exit 1
        ;;
esac

echo ""
echo "============================================="
echo "Execution completed!"
echo ""
echo "Generated files:"
echo "  Screenshots: ./screenshots/"
echo "  Logs:        ./logs/"
echo "  Reports:     ./reports/"
echo "============================================="