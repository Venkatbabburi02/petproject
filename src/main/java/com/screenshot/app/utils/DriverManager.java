package com.screenshot.app.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

/**
 * Driver Manager class to handle WebDriver instances
 */
public class DriverManager {
    
    private static final Logger logger = LogManager.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    
    /**
     * Initialize WebDriver based on browser type
     */
    public static WebDriver initializeDriver(String browserName) {
        return initializeDriver(browserName, false);
    }
    
    /**
     * Initialize WebDriver with headless option
     */
    public static WebDriver initializeDriver(String browserName, boolean headless) {
        WebDriver driver = null;
        
        try {
            switch (browserName.toLowerCase()) {
                case "chrome":
                    driver = createChromeDriver(headless);
                    break;
                case "firefox":
                    driver = createFirefoxDriver(headless);
                    break;
                default:
                    logger.warn("Browser '{}' not supported. Defaulting to Chrome.", browserName);
                    driver = createChromeDriver(headless);
            }
            
            // Set common driver configurations
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            
            setDriver(driver);
            logger.info("WebDriver initialized successfully: {} (headless: {})", browserName, headless);
            
        } catch (Exception e) {
            logger.error("Failed to initialize WebDriver: {}", e.getMessage());
            throw new RuntimeException("WebDriver initialization failed", e);
        }
        
        return driver;
    }
    
    /**
     * Create Chrome driver
     */
    private static WebDriver createChromeDriver(boolean headless) {
        WebDriverManager.chromedriver().setup();
        
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless");
        }
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        
        return new ChromeDriver(options);
    }
    
    /**
     * Create Firefox driver
     */
    private static WebDriver createFirefoxDriver(boolean headless) {
        WebDriverManager.firefoxdriver().setup();
        
        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments("--headless");
        }
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        
        return new FirefoxDriver(options);
    }
    
    /**
     * Set driver in ThreadLocal
     */
    public static void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }
    
    /**
     * Get driver from ThreadLocal
     */
    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }
    
    /**
     * Quit driver and remove from ThreadLocal
     */
    public static void quitDriver() {
        WebDriver driver = getDriver();
        if (driver != null) {
            try {
                driver.quit();
                logger.info("WebDriver quit successfully");
            } catch (Exception e) {
                logger.error("Error while quitting WebDriver: {}", e.getMessage());
            } finally {
                driverThreadLocal.remove();
            }
        }
    }
}