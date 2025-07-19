# Quick Start Guide - Selenium Screenshot App

This guide will help you get the screenshot app running in under 5 minutes.

## Prerequisites

- Java 11 or higher
- Maven 3.6+
- Chrome browser (for default setup)

## Quick Setup

1. **Clone and Navigate**
   ```bash
   cd selenium-screenshot-app
   ```

2. **Compile the Project**
   ```bash
   mvn clean compile
   ```

3. **Run the Demo**
   ```bash
   # Linux/Mac
   ./run-screenshot-app.sh demo
   
   # Windows
   run-screenshot-app.bat demo
   ```

## What the Demo Does

The demo will automatically:
- Open Google homepage
- Take a basic screenshot
- Take a full page screenshot
- Take an element screenshot (Google logo)
- Perform a search and screenshot results
- Save all screenshots to the `screenshots/` directory

## Alternative Running Methods

### Run Tests
```bash
# Run all tests
mvn test

# Run with specific browser
mvn test -Dbrowser=firefox
mvn test -Dbrowser=chrome-headless
```

### Run Main Application
```bash
mvn exec:java -Dexec.mainClass="com.screenshot.app.ScreenshotApp"
```

### Run Demo Directly
```bash
mvn exec:java -Dexec.mainClass="com.screenshot.app.demo.ScreenshotDemo"
```

## Directory Structure After Running

```
selenium-screenshot-app/
├── screenshots/          # All captured screenshots
├── logs/                # Application logs
├── reports/             # Test reports
└── target/              # Compiled classes
```

## Customization

Edit `src/test/resources/config.properties` to customize:
- Default browser
- Screenshot directory
- Timeout values
- Window dimensions

## Common Issues

1. **Chrome driver not found**: The app automatically downloads WebDrivers using WebDriverManager
2. **Permission denied**: Make sure the run script is executable: `chmod +x run-screenshot-app.sh`
3. **Java version**: Ensure Java 11+ is installed: `java -version`

## Next Steps

- Check the `README.md` for detailed documentation
- Explore the Page Object Model structure in `src/main/java/com/screenshot/app/pages/`
- Add your own page objects and tests
- Customize screenshot settings in the configuration files