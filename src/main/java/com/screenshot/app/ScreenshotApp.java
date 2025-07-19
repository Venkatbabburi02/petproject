package com.screenshot.app;

import com.screenshot.app.base.BasePage;
import com.screenshot.app.pages.GoogleHomePage;
import com.screenshot.app.pages.GoogleSearchResultsPage;
import com.screenshot.app.utils.DriverManager;
import com.screenshot.app.utils.ScreenshotUtils;
import com.screenshot.app.utils.ConfigurationManager;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main Screenshot Application
 * Demonstrates various screenshot capabilities using Selenium and Page Object Model
 */
public class ScreenshotApp {
    
    private static final Logger logger = LogManager.getLogger(ScreenshotApp.class);
    private WebDriver driver;
    private ScreenshotUtils screenshotUtils;
    private ConfigurationManager config;
    
    public static void main(String[] args) {
        ScreenshotApp app = new ScreenshotApp();
        app.run();
    }
    
    /**
     * Main application execution method
     */
    public void run() {
        logger.info("Starting Screenshot Application");
        
        try {
            initialize();
            demonstrateScreenshotCapabilities();
        } catch (Exception e) {
            logger.error("Application execution failed", e);
        } finally {
            cleanup();
        }
        
        logger.info("Screenshot Application completed");
    }
    
    /**
     * Initialize application components
     */
    private void initialize() {
        logger.info("Initializing application components");
        
        // Load configuration
        config = ConfigurationManager.getInstance();
        
        // Initialize WebDriver
        String browser = config.getDefaultBrowser();
        driver = DriverManager.getDriver(browser);
        driver.manage().window().maximize();
        
        // Initialize screenshot utility
        screenshotUtils = new ScreenshotUtils(driver);
        
        logger.info("Application initialization completed");
    }
    
    /**
     * Demonstrate various screenshot capabilities
     */
    private void demonstrateScreenshotCapabilities() {
        logger.info("Starting screenshot demonstrations");
        
        // Create page objects
        GoogleHomePage homePage = new GoogleHomePage(driver);
        
        // Demo 1: Basic page screenshot
        logger.info("=== Demo 1: Basic Page Screenshot ===");
        homePage.open();
        screenshotUtils.takeScreenshot("google_home_page");
        
        // Demo 2: Full page screenshot
        logger.info("=== Demo 2: Full Page Screenshot ===");
        screenshotUtils.takeFullPageScreenshot("google_home_full_page");
        
        // Demo 3: Element screenshot
        logger.info("=== Demo 3: Element Screenshot ===");
        if (homePage.isGoogleLogoDisplayed()) {
            screenshotUtils.takeElementScreenshot(
                homePage.getGoogleLogoElement(), 
                "google_logo_element"
            );
        }
        
        // Demo 4: Search functionality with screenshots
        logger.info("=== Demo 4: Search Process Screenshots ===");
        homePage.enterSearchTerm("Selenium WebDriver");
        screenshotUtils.takeScreenshot("search_term_entered");
        
        GoogleSearchResultsPage resultsPage = homePage.clickSearchButton();
        screenshotUtils.takeScreenshot("search_results_page");
        
        // Demo 5: Full page screenshot of search results
        logger.info("=== Demo 5: Full Page Search Results ===");
        screenshotUtils.takeFullPageScreenshot("search_results_full_page");
        
        // Demo 6: Element screenshot of search results
        logger.info("=== Demo 6: Search Results Element Screenshot ===");
        if (resultsPage.areResultsDisplayed()) {
            screenshotUtils.takeElementScreenshot(
                resultsPage.getSearchResultsContainer(),
                "search_results_container"
            );
        }
        
        // Demo 7: Multiple page navigation screenshots
        logger.info("=== Demo 7: Navigation Screenshots ===");
        if (resultsPage.isNextPageAvailable()) {
            resultsPage.goToNextPage();
            screenshotUtils.takeScreenshot("search_results_page_2");
        }
        
        // Demo 8: Different search term
        logger.info("=== Demo 8: Different Search Screenshots ===");
        resultsPage.searchAgain("Java Page Object Model");
        screenshotUtils.takeScreenshot("new_search_results");
        screenshotUtils.takeFullPageScreenshot("new_search_full_page");
        
        // Demo 9: Screenshot comparison example
        logger.info("=== Demo 9: Screenshot Comparison ===");
        String screenshot1 = screenshotUtils.takeScreenshot("comparison_test_1");
        
        // Navigate to home and back to create a different state
        GoogleHomePage newHomePage = new GoogleHomePage(driver);
        newHomePage.open();
        newHomePage.search("Java Page Object Model");
        
        String screenshot2 = screenshotUtils.takeScreenshot("comparison_test_2");
        
        // Compare screenshots
        if (screenshot1 != null && screenshot2 != null) {
            boolean areIdentical = screenshotUtils.compareScreenshots(screenshot1, screenshot2);
            logger.info("Screenshots comparison result: {}", areIdentical ? "Identical" : "Different");
        }
        
        logger.info("Screenshot demonstrations completed successfully");
        
        // Print screenshot directory info
        logger.info("All screenshots saved to: {}", screenshotUtils.getScreenshotDirectory());
    }
    
    /**
     * Cleanup resources
     */
    private void cleanup() {
        logger.info("Cleaning up resources");
        
        if (driver != null) {
            DriverManager.quitDriver();
        }
        
        logger.info("Cleanup completed");
    }
    
    /**
     * Demonstrate headless mode screenshots
     */
    public void runHeadlessDemo() {
        logger.info("Starting headless screenshot demonstration");
        
        try {
            // Initialize in headless mode
            driver = DriverManager.getDriver("chrome-headless");
            screenshotUtils = new ScreenshotUtils(driver);
            
            GoogleHomePage homePage = new GoogleHomePage(driver);
            homePage.open();
            
            screenshotUtils.takeScreenshot("headless_google_home");
            screenshotUtils.takeFullPageScreenshot("headless_google_full");
            
            GoogleSearchResultsPage resultsPage = homePage.search("Headless browser testing");
            screenshotUtils.takeScreenshot("headless_search_results");
            
            logger.info("Headless demonstration completed");
            
        } catch (Exception e) {
            logger.error("Headless demonstration failed", e);
        } finally {
            if (driver != null) {
                DriverManager.quitDriver();
            }
        }
    }
    
    /**
     * Custom screenshot workflow for specific use case
     */
    public void customScreenshotWorkflow(String searchTerm, String[] screenshotNames) {
        logger.info("Starting custom screenshot workflow for: {}", searchTerm);
        
        try {
            initialize();
            
            GoogleHomePage homePage = new GoogleHomePage(driver);
            homePage.open();
            
            if (screenshotNames.length > 0) {
                screenshotUtils.takeScreenshot(screenshotNames[0]);
            }
            
            GoogleSearchResultsPage resultsPage = homePage.search(searchTerm);
            
            if (screenshotNames.length > 1) {
                screenshotUtils.takeScreenshot(screenshotNames[1]);
            }
            
            if (screenshotNames.length > 2) {
                screenshotUtils.takeFullPageScreenshot(screenshotNames[2]);
            }
            
            logger.info("Custom workflow completed");
            
        } catch (Exception e) {
            logger.error("Custom workflow failed", e);
        } finally {
            cleanup();
        }
    }
}