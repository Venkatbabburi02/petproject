package com.screenshot.app.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.Duration;

/**
 * Base page class that provides common functionality for all page objects
 */
public abstract class BasePage {
    
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final Logger logger = LogManager.getLogger(BasePage.class);
    private static final int DEFAULT_TIMEOUT = 10;
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Wait for element to be visible
     */
    protected WebElement waitForElementToBeVisible(By locator) {
        logger.debug("Waiting for element to be visible: {}", locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    /**
     * Wait for element to be clickable
     */
    protected WebElement waitForElementToBeClickable(By locator) {
        logger.debug("Waiting for element to be clickable: {}", locator);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    /**
     * Wait for element to be clickable
     */
    protected WebElement waitForElementToBeClickable(WebElement element) {
        logger.debug("Waiting for element to be clickable: {}", element);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    
    /**
     * Safe click method with wait
     */
    protected void safeClick(By locator) {
        WebElement element = waitForElementToBeClickable(locator);
        element.click();
        logger.info("Clicked on element: {}", locator);
    }
    
    /**
     * Safe click method with wait for WebElement
     */
    protected void safeClick(WebElement element) {
        waitForElementToBeClickable(element);
        element.click();
        logger.info("Clicked on element: {}", element);
    }
    
    /**
     * Safe send keys method with wait
     */
    protected void safeSendKeys(By locator, String text) {
        WebElement element = waitForElementToBeVisible(locator);
        element.clear();
        element.sendKeys(text);
        logger.info("Entered text '{}' into element: {}", text, locator);
    }
    
    /**
     * Get current page title
     */
    public String getPageTitle() {
        String title = driver.getTitle();
        logger.info("Current page title: {}", title);
        return title;
    }
    
    /**
     * Get current URL
     */
    public String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        logger.info("Current URL: {}", url);
        return url;
    }
    
    /**
     * Navigate to URL
     */
    public void navigateTo(String url) {
        logger.info("Navigating to URL: {}", url);
        driver.get(url);
    }
    
    /**
     * Check if element is present
     */
    protected boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Scroll to element
     */
    protected void scrollToElement(WebElement element) {
        ((org.openqa.selenium.JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollIntoView(true);", element);
        logger.debug("Scrolled to element: {}", element);
    }
}