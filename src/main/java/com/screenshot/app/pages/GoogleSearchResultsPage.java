package com.screenshot.app.pages;

import com.screenshot.app.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.By;
import java.util.List;

/**
 * Page Object for Google Search Results Page
 */
public class GoogleSearchResultsPage extends BasePage {
    
    // Web Elements using Page Factory
    @FindBy(name = "q")
    private WebElement searchBox;
    
    @FindBy(id = "search")
    private WebElement searchResultsContainer;
    
    @FindBy(xpath = "//div[@id='result-stats']")
    private WebElement resultStats;
    
    @FindBy(xpath = "//h3[contains(@class, 'LC20lb')]")
    private List<WebElement> searchResultTitles;
    
    @FindBy(xpath = "//div[@class='yuRUbf']//a")
    private List<WebElement> searchResultLinks;
    
    @FindBy(id = "pnnext")
    private WebElement nextButton;
    
    @FindBy(id = "pnprev")
    private WebElement previousButton;
    
    // Constructor
    public GoogleSearchResultsPage(WebDriver driver) {
        super(driver);
        waitForPageToLoad();
    }
    
    /**
     * Wait for search results page to load
     */
    private void waitForPageToLoad() {
        waitForElementToBeVisible(By.id("search"));
        logger.info("Google search results page loaded successfully");
    }
    
    /**
     * Get number of search results displayed
     */
    public int getNumberOfResults() {
        int count = searchResultTitles.size();
        logger.info("Found {} search results", count);
        return count;
    }
    
    /**
     * Get search result stats text
     */
    public String getResultStatsText() {
        if (isElementPresent(By.xpath("//div[@id='result-stats']"))) {
            String stats = resultStats.getText();
            logger.info("Search stats: {}", stats);
            return stats;
        }
        return "Result stats not found";
    }
    
    /**
     * Click on a search result by index
     */
    public void clickSearchResult(int index) {
        if (index >= 0 && index < searchResultLinks.size()) {
            WebElement resultLink = searchResultLinks.get(index);
            safeClick(resultLink);
            logger.info("Clicked on search result at index: {}", index);
        } else {
            logger.error("Invalid search result index: {}", index);
        }
    }
    
    /**
     * Get search result title by index
     */
    public String getSearchResultTitle(int index) {
        if (index >= 0 && index < searchResultTitles.size()) {
            String title = searchResultTitles.get(index).getText();
            logger.info("Search result title at index {}: {}", index, title);
            return title;
        }
        logger.error("Invalid search result index: {}", index);
        return "";
    }
    
    /**
     * Perform a new search from results page
     */
    public GoogleSearchResultsPage searchAgain(String searchTerm) {
        clearSearchBox();
        enterSearchTerm(searchTerm);
        clickSearchButton();
        return this;
    }
    
    /**
     * Enter search term in search box
     */
    public GoogleSearchResultsPage enterSearchTerm(String searchTerm) {
        safeSendKeys(By.name("q"), searchTerm);
        logger.info("Entered search term: {}", searchTerm);
        return this;
    }
    
    /**
     * Click search button
     */
    public GoogleSearchResultsPage clickSearchButton() {
        // Use Enter key or find search button
        searchBox.submit();
        logger.info("Submitted search");
        return new GoogleSearchResultsPage(driver);
    }
    
    /**
     * Clear search box
     */
    public GoogleSearchResultsPage clearSearchBox() {
        searchBox.clear();
        logger.info("Search box cleared");
        return this;
    }
    
    /**
     * Click next page button
     */
    public GoogleSearchResultsPage goToNextPage() {
        if (isElementPresent(By.id("pnnext"))) {
            safeClick(nextButton);
            logger.info("Navigated to next page");
            return new GoogleSearchResultsPage(driver);
        } else {
            logger.warn("Next button not available");
            return this;
        }
    }
    
    /**
     * Click previous page button
     */
    public GoogleSearchResultsPage goToPreviousPage() {
        if (isElementPresent(By.id("pnprev"))) {
            safeClick(previousButton);
            logger.info("Navigated to previous page");
            return new GoogleSearchResultsPage(driver);
        } else {
            logger.warn("Previous button not available");
            return this;
        }
    }
    
    /**
     * Check if search results are displayed
     */
    public boolean areResultsDisplayed() {
        return searchResultsContainer.isDisplayed() && !searchResultTitles.isEmpty();
    }
    
    /**
     * Get search results container element for screenshots
     */
    public WebElement getSearchResultsContainer() {
        return searchResultsContainer;
    }
    
    /**
     * Get current search term from search box
     */
    public String getCurrentSearchTerm() {
        return searchBox.getAttribute("value");
    }
    
    /**
     * Check if next page is available
     */
    public boolean isNextPageAvailable() {
        return isElementPresent(By.id("pnnext"));
    }
    
    /**
     * Check if previous page is available
     */
    public boolean isPreviousPageAvailable() {
        return isElementPresent(By.id("pnprev"));
    }
}