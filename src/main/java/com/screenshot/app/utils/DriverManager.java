package com.screenshot.app.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

/**
 * Driver Manager class to handle WebDriver instances with ThreadLocal support
 */
public class DriverManager {
    
    private static final Logger logger = LogManager.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final int IMPLICIT_WAIT_TIMEOUT = 10;
    private static final int PAGE_LOAD_TIMEOUT = 30;
    
    /**
     * Get WebDriver instance for the current thread
     */
    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }
    
    /**
     * Set WebDriver instance for the current thread
     */
    public static void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }
    
    /**
     * Initialize WebDriver based on browser type
     */
    public static WebDriver getDriver(String browserName) {
        WebDriver driver = null;
        
        try {
            switch (browserName.toLowerCase()) {
                case "chrome":
                    driver = createChromeDriver();
                    break;
                case "firefox":
                    driver = createFirefoxDriver();
                    break;
                case "edge":
                    driver = createEdgeDriver();
                    break;
                case "safari":
                    driver = createSafariDriver();
                    break;
                case "chrome-headless":
                    driver = createChromeDriverHeadless();
                    break;
                case "firefox-headless":
                    driver = createFirefoxDriverHeadless();
                    break;
                default:
                    logger.error("Browser not supported: {}", browserName);
                    throw new IllegalArgumentException("Browser not supported: " + browserName);
            }
            
            // Configure timeouts
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_TIMEOUT));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PAGE_LOAD_TIMEOUT));
            
            setDriver(driver);
            logger.info("WebDriver initialized successfully for browser: {}", browserName);
            
        } catch (Exception e) {
            logger.error("Failed to initialize WebDriver for browser: {}", browserName, e);
            throw new RuntimeException("Failed to initialize WebDriver", e);
        }
        
        return driver;
    }
    
    /**
     * Create Chrome driver with options
     */
    private static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--remote-allow-origins=*");
        return new ChromeDriver(options);
    }
    
    /**
     * Create Chrome driver in headless mode
     */
    private static WebDriver createChromeDriverHeadless() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--remote-allow-origins=*");
        return new ChromeDriver(options);
    }
    
    /**
     * Create Firefox driver with options
     */
    private static WebDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("dom.webnotifications.enabled", false);
        options.addPreference("dom.push.enabled", false);
        return new FirefoxDriver(options);
    }
    
    /**
     * Create Firefox driver in headless mode
     */
    private static WebDriver createFirefoxDriverHeadless() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        options.addPreference("dom.webnotifications.enabled", false);
        options.addPreference("dom.push.enabled", false);
        return new FirefoxDriver(options);
    }
    
    /**
     * Create Edge driver with options
     */
    private static WebDriver createEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        return new EdgeDriver(options);
    }
    
    /**
     * Create Safari driver
     */
    private static WebDriver createSafariDriver() {
        return new SafariDriver();
    }
    
    /**
     * Quit WebDriver and remove from ThreadLocal
     */
    public static void quitDriver() {
        WebDriver driver = getDriver();
        if (driver != null) {
            try {
                driver.quit();
                logger.info("WebDriver quit successfully");
            } catch (Exception e) {
                logger.error("Error while quitting WebDriver", e);
            } finally {
                driverThreadLocal.remove();
            }
        }
    }
}