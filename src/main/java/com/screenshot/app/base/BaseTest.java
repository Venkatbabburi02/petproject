package com.screenshot.app.base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.screenshot.app.utils.DriverManager;
import com.screenshot.app.utils.ScreenshotUtils;
import com.screenshot.app.utils.ConfigurationManager;

/**
 * Base test class that provides common test setup and teardown functionality
 */
public abstract class BaseTest {
    
    protected WebDriver driver;
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);
    protected ScreenshotUtils screenshotUtils;
    protected ConfigurationManager config;
    
    @BeforeMethod
    @Parameters({"browser"})
    public void setUp(String browser) {
        logger.info("Setting up test with browser: {}", browser);
        
        // Initialize configuration
        config = ConfigurationManager.getInstance();
        
        // Initialize driver
        driver = DriverManager.getDriver(browser);
        
        // Initialize screenshot utility
        screenshotUtils = new ScreenshotUtils(driver);
        
        // Configure browser window
        driver.manage().window().maximize();
        
        logger.info("Test setup completed successfully");
    }
    
    @AfterMethod
    public void tearDown() {
        logger.info("Starting test teardown");
        
        if (driver != null) {
            DriverManager.quitDriver();
            logger.info("WebDriver session ended");
        }
        
        logger.info("Test teardown completed");
    }
    
    /**
     * Take screenshot with default name
     */
    protected String takeScreenshot() {
        return screenshotUtils.takeScreenshot();
    }
    
    /**
     * Take screenshot with custom name
     */
    protected String takeScreenshot(String screenshotName) {
        return screenshotUtils.takeScreenshot(screenshotName);
    }
    
    /**
     * Take full page screenshot
     */
    protected String takeFullPageScreenshot() {
        return screenshotUtils.takeFullPageScreenshot();
    }
    
    /**
     * Take full page screenshot with custom name
     */
    protected String takeFullPageScreenshot(String screenshotName) {
        return screenshotUtils.takeFullPageScreenshot(screenshotName);
    }
}