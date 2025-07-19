# 📸 Selenium Screenshot App

A comprehensive **Java + Selenium** screenshot application following the **Page Object Model (POM)** design pattern.

## 🚀 Quick Start

### Run the Application
```bash
# Compile and run the screenshot demo
mvn clean compile exec:java
```

This will:
- Open Google homepage in headless Chrome
- Take multiple types of screenshots
- Save all screenshots to `screenshots/` folder

## 📁 Project Structure

```
selenium-screenshot-app/
├── src/main/java/com/screenshot/app/
│   ├── base/BasePage.java           # Base class for page objects
│   ├── pages/GoogleHomePage.java    # Google homepage page object  
│   ├── utils/DriverManager.java     # WebDriver management
│   ├── utils/ScreenshotUtils.java   # Screenshot functionality
│   └── ScreenshotApp.java           # Main application
├── src/test/resources/log4j2.xml    # Logging configuration
├── screenshots/                     # Generated screenshots
└── pom.xml                          # Maven dependencies
```

## 🎯 Features

- ✅ **Standard Screenshots** - Current viewport
- ✅ **Full Page Screenshots** - Entire scrollable content  
- ✅ **Element Screenshots** - Specific web elements
- ✅ **Page Object Model** - Clean architecture
- ✅ **Headless Browser Support** - Works without GUI
- ✅ **Automatic WebDriver Management** - No manual setup

## 📊 Output

Screenshots are saved with timestamps:
```
screenshots/
├── google-homepage_2025-01-19_14-30-45.png
├── google-homepage_fullpage_2025-01-19_14-30-47.png
├── google-logo_element_2025-01-19_14-30-48.png
└── search-results_2025-01-19_14-30-51.png
```

## 🔧 How It Works

1. **DriverManager** - Sets up Chrome browser in headless mode
2. **ScreenshotUtils** - Handles all screenshot operations
3. **GoogleHomePage** - Page Object Model for Google interactions
4. **ScreenshotApp** - Main class that demonstrates functionality

## 💡 Usage Examples

```java
// Basic screenshot
screenshotUtils.takeScreenshot("my-page");

// Full page screenshot (with scrolling)
screenshotUtils.takeFullPageScreenshot("full-page");

// Element screenshot
screenshotUtils.takeElementScreenshot(element, "logo");
```

Perfect for:
- 🔍 **Visual Testing** - Compare UI changes
- 📊 **Documentation** - Generate website screenshots
- 🐛 **Bug Reporting** - Capture error states
- 📈 **Monitoring** - Track visual changes over time