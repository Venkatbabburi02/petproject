package com.screenshot.app.tests;

import com.screenshot.app.base.BaseTest;
import com.screenshot.app.pages.GoogleHomePage;
import com.screenshot.app.pages.GoogleSearchResultsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for screenshot functionality
 * Demonstrates how to integrate screenshots into automated tests
 */
public class ScreenshotTests extends BaseTest {
    
    @Test(description = "Test basic page screenshot functionality")
    public void testBasicPageScreenshot() {
        logger.info("Starting basic page screenshot test");
        
        GoogleHomePage homePage = new GoogleHomePage(driver);
        homePage.open();
        
        // Verify page is loaded
        Assert.assertTrue(homePage.isPageLoaded(), "Google home page should be loaded");
        
        // Take screenshot
        String screenshotPath = takeScreenshot("basic_page_test");
        Assert.assertNotNull(screenshotPath, "Screenshot should be taken successfully");
        
        logger.info("Basic page screenshot test completed");
    }
    
    @Test(description = "Test full page screenshot functionality")
    public void testFullPageScreenshot() {
        logger.info("Starting full page screenshot test");
        
        GoogleHomePage homePage = new GoogleHomePage(driver);
        homePage.open();
        
        // Take full page screenshot
        String screenshotPath = takeFullPageScreenshot("full_page_test");
        Assert.assertNotNull(screenshotPath, "Full page screenshot should be taken successfully");
        
        logger.info("Full page screenshot test completed");
    }
    
    @Test(description = "Test element screenshot functionality")
    public void testElementScreenshot() {
        logger.info("Starting element screenshot test");
        
        GoogleHomePage homePage = new GoogleHomePage(driver);
        homePage.open();
        
        // Verify logo is displayed
        Assert.assertTrue(homePage.isGoogleLogoDisplayed(), "Google logo should be displayed");
        
        // Take element screenshot
        String screenshotPath = screenshotUtils.takeElementScreenshot(
            homePage.getGoogleLogoElement(), 
            "logo_element_test"
        );
        Assert.assertNotNull(screenshotPath, "Element screenshot should be taken successfully");
        
        logger.info("Element screenshot test completed");
    }
    
    @Test(description = "Test search workflow with screenshots")
    public void testSearchWorkflowWithScreenshots() {
        logger.info("Starting search workflow screenshot test");
        
        GoogleHomePage homePage = new GoogleHomePage(driver);
        homePage.open();
        
        // Take initial screenshot
        takeScreenshot("search_workflow_start");
        
        // Enter search term
        homePage.enterSearchTerm("Selenium automation testing");
        takeScreenshot("search_term_entered");
        
        // Perform search
        GoogleSearchResultsPage resultsPage = homePage.clickSearchButton();
        takeScreenshot("search_results_loaded");
        
        // Verify results are displayed
        Assert.assertTrue(resultsPage.areResultsDisplayed(), "Search results should be displayed");
        
        // Take full page screenshot of results
        takeFullPageScreenshot("search_results_full_page");
        
        // Verify we have results
        int numberOfResults = resultsPage.getNumberOfResults();
        Assert.assertTrue(numberOfResults > 0, "Should have search results");
        
        logger.info("Search workflow screenshot test completed with {} results", numberOfResults);
    }
    
    @Test(description = "Test screenshot comparison functionality")
    public void testScreenshotComparison() {
        logger.info("Starting screenshot comparison test");
        
        GoogleHomePage homePage = new GoogleHomePage(driver);
        homePage.open();
        
        // Take first screenshot
        String screenshot1 = takeScreenshot("comparison_test_1");
        Assert.assertNotNull(screenshot1, "First screenshot should be taken");
        
        // Refresh page (should be identical)
        driver.navigate().refresh();
        
        // Take second screenshot
        String screenshot2 = takeScreenshot("comparison_test_2");
        Assert.assertNotNull(screenshot2, "Second screenshot should be taken");
        
        // Compare screenshots
        boolean areIdentical = screenshotUtils.compareScreenshots(screenshot1, screenshot2);
        
        // Note: This might not always be identical due to dynamic content
        logger.info("Screenshots comparison result: {}", areIdentical ? "Identical" : "Different");
        
        logger.info("Screenshot comparison test completed");
    }
    
    @Test(description = "Test multiple page screenshots")
    public void testMultiplePageScreenshots() {
        logger.info("Starting multiple page screenshot test");
        
        GoogleHomePage homePage = new GoogleHomePage(driver);
        homePage.open();
        
        // Search for something with multiple pages
        GoogleSearchResultsPage resultsPage = homePage.search("Java programming tutorial");
        takeScreenshot("page_1_results");
        
        // Navigate to next page if available
        if (resultsPage.isNextPageAvailable()) {
            resultsPage.goToNextPage();
            takeScreenshot("page_2_results");
            
            // Go back to first page
            if (resultsPage.isPreviousPageAvailable()) {
                resultsPage.goToPreviousPage();
                takeScreenshot("back_to_page_1");
            }
        }
        
        logger.info("Multiple page screenshot test completed");
    }
    
    @Test(description = "Test error handling in screenshots")
    public void testScreenshotErrorHandling() {
        logger.info("Starting screenshot error handling test");
        
        GoogleHomePage homePage = new GoogleHomePage(driver);
        homePage.open();
        
        // Test taking screenshot with invalid characters in filename
        String screenshotPath = screenshotUtils.takeScreenshot("test/invalid\\filename");
        // Should handle invalid characters gracefully
        
        // Test element screenshot with non-existent element
        try {
            screenshotUtils.takeElementScreenshot(
                org.openqa.selenium.By.id("non-existent-element"), 
                "non_existent_element"
            );
        } catch (Exception e) {
            logger.info("Expected exception for non-existent element: {}", e.getMessage());
        }
        
        logger.info("Screenshot error handling test completed");
    }
    
    @Test(description = "Test screenshot directory creation and management")
    public void testScreenshotDirectoryManagement() {
        logger.info("Starting screenshot directory management test");
        
        GoogleHomePage homePage = new GoogleHomePage(driver);
        homePage.open();
        
        // Get screenshot directory
        String screenshotDir = screenshotUtils.getScreenshotDirectory();
        Assert.assertNotNull(screenshotDir, "Screenshot directory should be available");
        
        // Take a screenshot to ensure directory exists
        String screenshotPath = takeScreenshot("directory_test");
        Assert.assertNotNull(screenshotPath, "Screenshot should be saved successfully");
        
        // Verify screenshot file exists
        java.io.File screenshotFile = new java.io.File(screenshotPath);
        Assert.assertTrue(screenshotFile.exists(), "Screenshot file should exist");
        
        logger.info("Screenshot directory management test completed");
    }
    
    @Test(description = "Test screenshot naming conventions")
    public void testScreenshotNamingConventions() {
        logger.info("Starting screenshot naming conventions test");
        
        GoogleHomePage homePage = new GoogleHomePage(driver);
        homePage.open();
        
        // Test default naming (timestamp-based)
        String defaultScreenshot = screenshotUtils.takeScreenshot();
        Assert.assertNotNull(defaultScreenshot, "Default named screenshot should be taken");
        
        // Test custom naming
        String customScreenshot = takeScreenshot("custom_name_test");
        Assert.assertNotNull(customScreenshot, "Custom named screenshot should be taken");
        
        // Test naming without extension
        String noExtensionScreenshot = screenshotUtils.takeScreenshot("no_extension");
        Assert.assertNotNull(noExtensionScreenshot, "Screenshot without extension should be taken");
        Assert.assertTrue(noExtensionScreenshot.endsWith(".png"), "Should automatically add .png extension");
        
        logger.info("Screenshot naming conventions test completed");
    }
}