package com.screenshot.app.models;

/**
 * Model class representing screenshot configuration
 */
public class ScreenshotConfig {
    
    private String name;
    private String url;
    private String browser;
    private boolean fullPage;
    private boolean elementScreenshot;
    private String elementSelector;
    private int windowWidth;
    private int windowHeight;
    
    // Default constructor
    public ScreenshotConfig() {
        this.fullPage = false;
        this.elementScreenshot = false;
        this.windowWidth = 1920;
        this.windowHeight = 1080;
    }
    
    // Constructor with basic parameters
    public ScreenshotConfig(String name, String url, String browser) {
        this();
        this.name = name;
        this.url = url;
        this.browser = browser;
    }
    
    // Full constructor
    public ScreenshotConfig(String name, String url, String browser, boolean fullPage, 
                           boolean elementScreenshot, String elementSelector, 
                           int windowWidth, int windowHeight) {
        this.name = name;
        this.url = url;
        this.browser = browser;
        this.fullPage = fullPage;
        this.elementScreenshot = elementScreenshot;
        this.elementSelector = elementSelector;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getBrowser() {
        return browser;
    }
    
    public void setBrowser(String browser) {
        this.browser = browser;
    }
    
    public boolean isFullPage() {
        return fullPage;
    }
    
    public void setFullPage(boolean fullPage) {
        this.fullPage = fullPage;
    }
    
    public boolean isElementScreenshot() {
        return elementScreenshot;
    }
    
    public void setElementScreenshot(boolean elementScreenshot) {
        this.elementScreenshot = elementScreenshot;
    }
    
    public String getElementSelector() {
        return elementSelector;
    }
    
    public void setElementSelector(String elementSelector) {
        this.elementSelector = elementSelector;
    }
    
    public int getWindowWidth() {
        return windowWidth;
    }
    
    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }
    
    public int getWindowHeight() {
        return windowHeight;
    }
    
    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }
    
    // Utility methods
    
    /**
     * Create a viewport screenshot configuration
     */
    public static ScreenshotConfig createViewportConfig(String name, String url, String browser) {
        return new ScreenshotConfig(name, url, browser, false, false, null, 1920, 1080);
    }
    
    /**
     * Create a full page screenshot configuration
     */
    public static ScreenshotConfig createFullPageConfig(String name, String url, String browser) {
        return new ScreenshotConfig(name, url, browser, true, false, null, 1920, 1080);
    }
    
    /**
     * Create an element screenshot configuration
     */
    public static ScreenshotConfig createElementConfig(String name, String url, String browser, String elementSelector) {
        return new ScreenshotConfig(name, url, browser, false, true, elementSelector, 1920, 1080);
    }
    
    /**
     * Validate the configuration
     */
    public boolean isValid() {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        if (url == null || url.trim().isEmpty()) {
            return false;
        }
        if (browser == null || browser.trim().isEmpty()) {
            return false;
        }
        if (elementScreenshot && (elementSelector == null || elementSelector.trim().isEmpty())) {
            return false;
        }
        return windowWidth > 0 && windowHeight > 0;
    }
    
    /**
     * Get screenshot file name based on configuration
     */
    public String getScreenshotFileName() {
        StringBuilder fileName = new StringBuilder(name);
        
        if (fullPage) {
            fileName.append("_fullpage");
        } else if (elementScreenshot) {
            fileName.append("_element");
        } else {
            fileName.append("_viewport");
        }
        
        fileName.append("_").append(browser);
        fileName.append("_").append(windowWidth).append("x").append(windowHeight);
        
        return fileName.toString();
    }
    
    @Override
    public String toString() {
        return String.format("ScreenshotConfig{name='%s', url='%s', browser='%s', fullPage=%s, " +
                           "elementScreenshot=%s, elementSelector='%s', windowSize=%dx%d}",
                           name, url, browser, fullPage, elementScreenshot, elementSelector, 
                           windowWidth, windowHeight);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        ScreenshotConfig that = (ScreenshotConfig) obj;
        
        return fullPage == that.fullPage &&
               elementScreenshot == that.elementScreenshot &&
               windowWidth == that.windowWidth &&
               windowHeight == that.windowHeight &&
               (name != null ? name.equals(that.name) : that.name == null) &&
               (url != null ? url.equals(that.url) : that.url == null) &&
               (browser != null ? browser.equals(that.browser) : that.browser == null) &&
               (elementSelector != null ? elementSelector.equals(that.elementSelector) : that.elementSelector == null);
    }
    
    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (browser != null ? browser.hashCode() : 0);
        result = 31 * result + (fullPage ? 1 : 0);
        result = 31 * result + (elementScreenshot ? 1 : 0);
        result = 31 * result + (elementSelector != null ? elementSelector.hashCode() : 0);
        result = 31 * result + windowWidth;
        result = 31 * result + windowHeight;
        return result;
    }
}