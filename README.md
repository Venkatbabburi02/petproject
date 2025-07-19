# Selenium Screenshot App

A comprehensive screenshot application built with Java and Selenium WebDriver, following the Page Object Model design pattern. This application demonstrates various screenshot capabilities including full page screenshots, element screenshots, and screenshot comparison functionality.

## Features

- **Multiple Screenshot Types**
  - Standard viewport screenshots
  - Full page screenshots (entire scrollable content)
  - Element-specific screenshots
  - Screenshot comparison functionality

- **Page Object Model Architecture**
  - Clean separation of page logic and test logic
  - Reusable page components
  - Maintainable test structure

- **Multi-Browser Support**
  - Chrome (headless and standard)
  - Firefox (headless and standard)
  - Edge
  - Safari (macOS only)

- **Advanced Features**
  - Automatic WebDriver management
  - Configurable timeouts and settings
  - Comprehensive logging
  - Parallel test execution support
  - Screenshot directory management

## Project Structure

```
selenium-screenshot-app/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── screenshot/
│   │               └── app/
│   │                   ├── base/
│   │                   │   ├── BasePage.java
│   │                   │   └── BaseTest.java
│   │                   ├── pages/
│   │                   │   ├── GoogleHomePage.java
│   │                   │   └── GoogleSearchResultsPage.java
│   │                   ├── utils/
│   │                   │   ├── ConfigurationManager.java
│   │                   │   ├── DriverManager.java
│   │                   │   └── ScreenshotUtils.java
│   │                   └── ScreenshotApp.java
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── screenshot/
│       │           └── app/
│       │               └── tests/
│       │                   └── ScreenshotTests.java
│       └── resources/
│           ├── config.properties
│           ├── log4j2.xml
│           └── testng.xml
├── screenshots/
├── logs/
├── reports/
├── pom.xml
└── README.md
```

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- Chrome/Firefox/Edge browsers installed
- Internet connection for downloading WebDriver binaries

## Installation

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd selenium-screenshot-app
   ```

2. **Install dependencies:**
   ```bash
   mvn clean install
   ```

3. **Verify installation:**
   ```bash
   mvn compile
   ```

## Usage

### Running the Main Application

Execute the main screenshot demonstration:

```bash
mvn exec:java -Dexec.mainClass="com.screenshot.app.ScreenshotApp"
```

### Running Tests

Execute all tests:
```bash
mvn test
```

Execute specific test class:
```bash
mvn test -Dtest=ScreenshotTests
```

Execute with specific browser:
```bash
mvn test -Dbrowser=firefox
```

Execute in headless mode:
```bash
mvn test -Dbrowser=chrome-headless
```

### Using TestNG XML

Run tests using TestNG configuration:
```bash
mvn test -DsuiteXmlFile=src/test/resources/testng.xml
```

## Configuration

### config.properties

Configure application settings in `src/test/resources/config.properties`:

```properties
# Browser settings
default.browser=chrome
headless.mode=false

# Timeouts
default.timeout=10
page.load.timeout=30

# Directories
screenshot.directory=./screenshots/
reports.directory=./reports/

# Window settings
window.width=1920
window.height=1080
```

### Browser Options

Supported browser values:
- `chrome` - Standard Chrome
- `chrome-headless` - Headless Chrome
- `firefox` - Standard Firefox
- `firefox-headless` - Headless Firefox
- `edge` - Microsoft Edge
- `safari` - Safari (macOS only)

## Code Examples

### Basic Screenshot Usage

```java
// Initialize WebDriver and ScreenshotUtils
WebDriver driver = DriverManager.getDriver("chrome");
ScreenshotUtils screenshotUtils = new ScreenshotUtils(driver);

// Take viewport screenshot
String screenshotPath = screenshotUtils.takeScreenshot("my_screenshot");

// Take full page screenshot
String fullPagePath = screenshotUtils.takeFullPageScreenshot("full_page");

// Take element screenshot
WebElement element = driver.findElement(By.id("logo"));
String elementPath = screenshotUtils.takeElementScreenshot(element, "logo");
```

### Page Object Model Usage

```java
// Create page object
GoogleHomePage homePage = new GoogleHomePage(driver);

// Navigate and interact
homePage.open()
        .enterSearchTerm("Selenium WebDriver")
        .clickSearchButton();

// Take screenshots during workflow
screenshotUtils.takeScreenshot("search_completed");
```

### Test Integration

```java
@Test
public void testScreenshotFunctionality() {
    GoogleHomePage homePage = new GoogleHomePage(driver);
    homePage.open();
    
    // Take screenshot using inherited method
    takeScreenshot("test_start");
    
    // Perform actions and take more screenshots
    GoogleSearchResultsPage resultsPage = homePage.search("test query");
    takeFullPageScreenshot("search_results");
    
    // Assert test conditions
    Assert.assertTrue(resultsPage.areResultsDisplayed());
}
```

## Key Classes

### ScreenshotUtils

Core utility class providing screenshot functionality:

- `takeScreenshot()` - Standard viewport screenshot
- `takeFullPageScreenshot()` - Complete page screenshot
- `takeElementScreenshot()` - Element-specific screenshot
- `compareScreenshots()` - Screenshot comparison

### DriverManager

WebDriver management with ThreadLocal support:

- Multi-browser support
- Automatic driver setup
- Thread-safe driver instances
- Configurable browser options

### BasePage

Abstract base class for page objects:

- Common page interactions
- Wait strategies
- Element finding methods
- Logging integration

### BaseTest

Abstract base class for tests:

- WebDriver setup/teardown
- Screenshot utilities
- Configuration management
- Test lifecycle management

## Screenshot Types

### 1. Viewport Screenshots
Captures the visible area of the browser window.

### 2. Full Page Screenshots
Captures the entire scrollable content of the page by:
- Calculating total page dimensions
- Taking multiple viewport screenshots
- Stitching them together into a single image

### 3. Element Screenshots
Captures specific web elements only.

### 4. Screenshot Comparison
Pixel-by-pixel comparison of two screenshots.

## Logging

Comprehensive logging is configured using Log4j2:

- **Console Output** - Real-time execution feedback
- **Application Logs** - General application events
- **Test Logs** - Test execution details
- **Screenshot Logs** - Screenshot operation details
- **WebDriver Logs** - Browser interaction logs

Log files are created in the `logs/` directory.

## Best Practices

1. **Use Page Object Model** - Keep page logic separate from test logic
2. **Take Screenshots at Key Points** - Capture important state changes
3. **Use Descriptive Names** - Make screenshot names meaningful
4. **Handle Failures Gracefully** - Include error handling for screenshot operations
5. **Clean Up Resources** - Always quit WebDriver instances
6. **Use Configuration** - Externalize settings in properties files

## Troubleshooting

### Common Issues

1. **WebDriver Binary Not Found**
   - Solution: WebDriverManager automatically downloads drivers
   - Ensure internet connection is available

2. **Screenshots Not Saving**
   - Check file permissions for screenshots directory
   - Verify disk space availability

3. **Element Not Found**
   - Use explicit waits in BasePage methods
   - Check element locators

4. **Browser Not Starting**
   - Verify browser installation
   - Check browser compatibility with WebDriver version

### Debug Mode

Enable debug logging by setting log level to DEBUG in `log4j2.xml`:

```xml
<Logger name="com.screenshot.app" level="DEBUG">
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Implement changes following the existing patterns
4. Add tests for new functionality
5. Update documentation
6. Submit a pull request

## Dependencies

- **Selenium WebDriver** - Browser automation
- **TestNG** - Testing framework
- **WebDriverManager** - Automatic driver management
- **Apache Commons IO** - File operations
- **Log4j2** - Logging framework
- **Jackson** - JSON processing

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For questions and support:
- Check the documentation above
- Review the example code in the test classes
- Check the logs for detailed error information
- Open an issue for bug reports or feature requests