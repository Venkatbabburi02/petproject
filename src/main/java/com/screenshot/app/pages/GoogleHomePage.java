package com.screenshot.app.pages;

import com.screenshot.app.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.By;

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
    
    @FindBy(name = "btnI")
    private WebElement feelingLuckyButton;
    
    @FindBy(xpath = "//div[@id='SIvCob']")
    private WebElement googleLogo;
    
    @FindBy(id = "gb")
    private WebElement topNavigation;
    
    // Constructor
    public GoogleHomePage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Navigate to Google home page
     */
    public GoogleHomePage open() {
        navigateTo(PAGE_URL);
        waitForPageToLoad();
        return this;
    }
    
    /**
     * Wait for page to load completely
     */
    private void waitForPageToLoad() {
        waitForElementToBeVisible(By.name("q"));
        logger.info("Google home page loaded successfully");
    }
    
    /**
     * Enter search term in search box
     */
    public GoogleHomePage enterSearchTerm(String searchTerm) {
        safeSendKeys(By.name("q"), searchTerm);
        logger.info("Entered search term: {}", searchTerm);
        return this;
    }
    
    /**
     * Click search button
     */
    public GoogleSearchResultsPage clickSearchButton() {
        safeClick(searchButton);
        logger.info("Clicked search button");
        return new GoogleSearchResultsPage(driver);
    }
    
    /**
     * Click "I'm Feeling Lucky" button
     */
    public void clickFeelingLuckyButton() {
        safeClick(feelingLuckyButton);
        logger.info("Clicked 'I'm Feeling Lucky' button");
    }
    
    /**
     * Perform search operation
     */
    public GoogleSearchResultsPage search(String searchTerm) {
        enterSearchTerm(searchTerm);
        return clickSearchButton();
    }
    
    /**
     * Check if search box is displayed
     */
    public boolean isSearchBoxDisplayed() {
        return searchBox.isDisplayed();
    }
    
    /**
     * Check if Google logo is displayed
     */
    public boolean isGoogleLogoDisplayed() {
        return googleLogo.isDisplayed();
    }
    
    /**
     * Get search box element for screenshots
     */
    public WebElement getSearchBoxElement() {
        return searchBox;
    }
    
    /**
     * Get Google logo element
     */
    public WebElement getGoogleLogoElement() {
        return googleLogo;
    }
    
    /**
     * Clear search box
     */
    public GoogleHomePage clearSearchBox() {
        searchBox.clear();
        logger.info("Search box cleared");
        return this;
    }
    
    /**
     * Check if page is loaded
     */
    public boolean isPageLoaded() {
        return isSearchBoxDisplayed() && isGoogleLogoDisplayed();
    }
}