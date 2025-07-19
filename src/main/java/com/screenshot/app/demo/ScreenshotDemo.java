package com.screenshot.app.demo;

import com.screenshot.app.pages.GoogleHomePage;
import com.screenshot.app.utils.DriverManager;
import com.screenshot.app.utils.ScreenshotUtils;
import com.screenshot.app.utils.ConfigurationManager;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Demo class to showcase screenshot functionality
 * This class demonstrates how to use the screenshot app with real examples
 */
public class ScreenshotDemo {
    
    private static final Logger logger = LogManager.getLogger(ScreenshotDemo.class);
    
    public static void main(String[] args) {
        logger.info("Starting Screenshot Demo Application");
        
        WebDriver driver = null;
        ScreenshotUtils screenshotUtils = null;
        
        try {
            // Initialize configuration
            ConfigurationManager config = ConfigurationManager.getInstance();
            
            // Initialize driver
            String browser = config.getProperty("default.browser", "chrome");
            driver = DriverManager.getDriver(browser);
            screenshotUtils = new ScreenshotUtils(driver);
            
            // Demo 1: Basic page screenshot
            logger.info("Demo 1: Taking basic page screenshot");
            GoogleHomePage homePage = new GoogleHomePage(driver);
            homePage.open();
            
            String basicScreenshot = screenshotUtils.takeScreenshot("google-homepage-basic");
            logger.info("Basic screenshot saved: " + basicScreenshot);
            
            // Demo 2: Full page screenshot
            logger.info("Demo 2: Taking full page screenshot");
            String fullPageScreenshot = screenshotUtils.takeFullPageScreenshot("google-homepage-fullpage");
            logger.info("Full page screenshot saved: " + fullPageScreenshot);
            
            // Demo 3: Element screenshot (Google logo)
            logger.info("Demo 3: Taking element screenshot");
            if (homePage.isGoogleLogoDisplayed()) {
                String elementScreenshot = screenshotUtils.takeElementScreenshot(
                    homePage.getGoogleLogoElement(), "google-logo"
                );
                logger.info("Element screenshot saved: " + elementScreenshot);
            }
            
            // Demo 4: Screenshot with custom dimensions
            logger.info("Demo 4: Taking screenshot with custom dimensions");
            driver.manage().window().setSize(new org.openqa.selenium.Dimension(800, 600));
            Thread.sleep(1000); // Wait for resize
            String customSizeScreenshot = screenshotUtils.takeScreenshot("google-homepage-800x600");
            logger.info("Custom size screenshot saved: " + customSizeScreenshot);
            
            // Demo 5: Search and screenshot results
            logger.info("Demo 5: Search and screenshot results");
            driver.manage().window().maximize();
            homePage.search("selenium webdriver");
            Thread.sleep(2000); // Wait for results to load
            
            String searchResultsScreenshot = screenshotUtils.takeFullPageScreenshot("search-results-selenium");
            logger.info("Search results screenshot saved: " + searchResultsScreenshot);
            
            logger.info("Screenshot Demo completed successfully!");
            logger.info("Check the 'screenshots' directory for all captured images");
            
        } catch (Exception e) {
            logger.error("Error during screenshot demo: " + e.getMessage(), e);
        } finally {
            // Cleanup
            if (driver != null) {
                DriverManager.quitDriver();
                logger.info("WebDriver session closed");
            }
        }
    }
}