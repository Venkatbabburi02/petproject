package com.screenshot.app;

import com.screenshot.app.pages.GoogleHomePage;
import com.screenshot.app.utils.DriverManager;
import com.screenshot.app.utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main Screenshot Application
 * Demonstrates various screenshot capabilities using Selenium and Page Object Model
 */
public class ScreenshotApp {
    
    private static final Logger logger = LogManager.getLogger(ScreenshotApp.class);
    
    public static void main(String[] args) {
        logger.info("Starting Screenshot Application");
        
        WebDriver driver = null;
        ScreenshotUtils screenshotUtils = null;
        
        try {
            // Initialize WebDriver in headless mode for compatibility
            driver = DriverManager.initializeDriver("chrome", true);
            screenshotUtils = new ScreenshotUtils(driver);
            
            // Create page objects
            GoogleHomePage homePage = new GoogleHomePage(driver);
            
            // Demo 1: Basic page screenshot
            logger.info("Demo 1: Taking basic page screenshot");
            homePage.open();
            Thread.sleep(2000); // Wait for page to load
            
            if (homePage.isPageLoaded()) {
                String basicScreenshot = screenshotUtils.takeScreenshot("google-homepage");
                logger.info("Basic screenshot saved: {}", basicScreenshot);
            }
            
            // Demo 2: Full page screenshot
            logger.info("Demo 2: Taking full page screenshot");
            String fullPageScreenshot = screenshotUtils.takeFullPageScreenshot("google-homepage");
            logger.info("Full page screenshot saved: {}", fullPageScreenshot);
            
            // Demo 3: Element screenshot (Google logo)
            logger.info("Demo 3: Taking element screenshot");
            if (homePage.isGoogleLogoDisplayed()) {
                String elementScreenshot = screenshotUtils.takeElementScreenshot(
                    homePage.getGoogleLogoElement(), "google-logo"
                );
                logger.info("Element screenshot saved: {}", elementScreenshot);
            }
            
            // Demo 4: Search and screenshot results
            logger.info("Demo 4: Search and screenshot results");
            homePage.search("selenium webdriver");
            Thread.sleep(3000); // Wait for search results
            
            String searchResultsScreenshot = screenshotUtils.takeScreenshot("search-results");
            logger.info("Search results screenshot saved: {}", searchResultsScreenshot);
            
            // Demo 5: Screenshot with custom directory
            logger.info("Demo 5: Screenshot with custom directory");
            String customDirScreenshot = screenshotUtils.takeScreenshot("custom-location", "screenshots/custom");
            logger.info("Custom directory screenshot saved: {}", customDirScreenshot);
            
            logger.info("Screenshot Application completed successfully!");
            logger.info("Check the 'screenshots' folder for all generated screenshots.");
            
        } catch (Exception e) {
            logger.error("Application execution failed", e);
        } finally {
            // Cleanup
            if (driver != null) {
                DriverManager.quitDriver();
            }
        }
    }
}