package com.screenshot.app.pages;

import com.screenshot.app.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.Keys;

/**
 * Page Object for Google Home Page
 */
public class GoogleHomePage extends BasePage {
    
    // Page URL
    private static final String PAGE_URL = "https://www.google.com";
    
    // Web Elements using Page Factory
    @FindBy(name = "q")
    private WebElement searchBox;
    
    @FindBy(name = "btnK")
    private WebElement searchButton;
    
    @FindBy(xpath = "//img[@alt='Google']")
    private WebElement googleLogo;
    
    @FindBy(id = "hplogo")
    private WebElement homepageLogo;
    
    public GoogleHomePage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Open Google homepage
     */
    public void open() {
        driver.get(PAGE_URL);
        logger.info("Opened Google homepage: {}", PAGE_URL);
    }
    
    /**
     * Search for a term
     */
    public void search(String searchTerm) {
        waitForElementToBeVisible(searchBox);
        safeSendKeys(searchBox, searchTerm);
        searchBox.sendKeys(Keys.ENTER);
        logger.info("Searched for: {}", searchTerm);
    }
    
    /**
     * Click search button
     */
    public void clickSearchButton() {
        safeClick(searchButton);
        logger.info("Clicked search button");
    }
    
    /**
     * Get the Google logo element
     */
    public WebElement getGoogleLogoElement() {
        try {
            return waitForElementToBeVisible(googleLogo);
        } catch (Exception e) {
            // Try alternative logo element
            return waitForElementToBeVisible(homepageLogo);
        }
    }
    
    /**
     * Check if Google logo is displayed
     */
    public boolean isGoogleLogoDisplayed() {
        try {
            return isElementDisplayed(googleLogo) || isElementDisplayed(homepageLogo);
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Get search box element
     */
    public WebElement getSearchBox() {
        return waitForElementToBeVisible(searchBox);
    }
    
    /**
     * Check if search box is displayed
     */
    public boolean isSearchBoxDisplayed() {
        return isElementDisplayed(searchBox);
    }
    
    /**
     * Implementation of abstract method from BasePage
     */
    @Override
    public boolean isPageLoaded() {
        try {
            return isSearchBoxDisplayed() && (isGoogleLogoDisplayed() || getPageTitle().contains("Google"));
        } catch (Exception e) {
            logger.error("Error checking if page is loaded: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Get page URL
     */
    public String getPageUrl() {
        return PAGE_URL;
    }
}