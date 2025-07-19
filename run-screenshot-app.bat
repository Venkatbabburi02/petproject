@echo off
REM Selenium Screenshot App Runner Script for Windows
REM This script provides easy commands to run different parts of the application

echo =============================================
echo      Selenium Screenshot App Runner
echo =============================================

if "%1"=="" goto usage
if "%1"=="help" goto usage

REM Create directories
if not exist "screenshots" mkdir screenshots
if not exist "logs" mkdir logs
if not exist "reports" mkdir reports

if "%1"=="demo" goto run_demo
if "%1"=="app" goto run_app
if "%1"=="test" goto run_test
if "%1"=="test-chrome" goto run_test_chrome
if "%1"=="test-firefox" goto run_test_firefox
if "%1"=="test-headless" goto run_test_headless
if "%1"=="compile" goto compile
if "%1"=="clean" goto clean
goto invalid_option

:run_demo
echo Running Screenshot Demo...
mvn compile exec:java -Dexec.mainClass="com.screenshot.app.demo.ScreenshotDemo"
goto end

:run_app
echo Running Screenshot Application...
mvn exec:java -Dexec.mainClass="com.screenshot.app.ScreenshotApp"
goto end

:run_test
echo Running all tests...
mvn test
goto end

:run_test_chrome
echo Running tests with Chrome browser...
mvn test -Dbrowser=chrome
goto end

:run_test_firefox
echo Running tests with Firefox browser...
mvn test -Dbrowser=firefox
goto end

:run_test_headless
echo Running tests in headless mode...
mvn test -Dbrowser=chrome-headless
goto end

:compile
echo Compiling the project...
mvn compile
goto end

:clean
echo Cleaning and compiling the project...
mvn clean compile
goto end

:usage
echo Usage: %0 [OPTION]
echo.
echo Options:
echo   demo              Run the screenshot demo (recommended for first time)
echo   app               Run the main screenshot application
echo   test              Run all tests
echo   test-chrome       Run tests with Chrome browser
echo   test-firefox      Run tests with Firefox browser
echo   test-headless     Run tests in headless mode
echo   compile           Compile the project
echo   clean             Clean and compile the project
echo   help              Show this help message
echo.
echo Examples:
echo   %0 demo            # Run screenshot demo
echo   %0 app             # Run main application
echo   %0 test            # Run all tests
echo   %0 test-headless   # Run tests in headless mode
echo.
goto end

:invalid_option
echo Error: Invalid option '%1'
echo.
goto usage

:end
echo.
echo =============================================
echo Execution completed!
echo.
echo Generated files:
echo   Screenshots: .\screenshots\
echo   Logs:        .\logs\
echo   Reports:     .\reports\
echo =============================================
pause