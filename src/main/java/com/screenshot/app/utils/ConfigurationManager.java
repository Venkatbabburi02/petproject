package com.screenshot.app.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration Manager to handle application properties
 */
public class ConfigurationManager {
    
    private static final Logger logger = LogManager.getLogger(ConfigurationManager.class);
    private static ConfigurationManager instance;
    private Properties properties;
    private static final String CONFIG_FILE = "config.properties";
    
    private ConfigurationManager() {
        loadProperties();
    }
    
    /**
     * Get singleton instance
     */
    public static ConfigurationManager getInstance() {
        if (instance == null) {
            synchronized (ConfigurationManager.class) {
                if (instance == null) {
                    instance = new ConfigurationManager();
                }
            }
        }
        return instance;
    }
    
    /**
     * Load properties from config file
     */
    private void loadProperties() {
        properties = new Properties();
        
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (inputStream != null) {
                properties.load(inputStream);
                logger.info("Configuration loaded from {}", CONFIG_FILE);
            } else {
                logger.warn("Configuration file {} not found, using default values", CONFIG_FILE);
                setDefaultProperties();
            }
        } catch (IOException e) {
            logger.error("Error loading configuration file: {}", CONFIG_FILE, e);
            setDefaultProperties();
        }
    }
    
    /**
     * Set default properties
     */
    private void setDefaultProperties() {
        properties.setProperty("default.browser", "chrome");
        properties.setProperty("default.timeout", "10");
        properties.setProperty("default.url", "https://www.google.com");
        properties.setProperty("screenshot.directory", "./screenshots/");
        properties.setProperty("reports.directory", "./reports/");
        properties.setProperty("headless.mode", "false");
        properties.setProperty("window.width", "1920");
        properties.setProperty("window.height", "1080");
        
        logger.info("Default configuration properties set");
    }
    
    /**
     * Get property value by key
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    /**
     * Get property value with default fallback
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    /**
     * Get integer property value
     */
    public int getIntProperty(String key, int defaultValue) {
        try {
            String value = properties.getProperty(key);
            return value != null ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            logger.warn("Invalid integer value for property {}, using default: {}", key, defaultValue);
            return defaultValue;
        }
    }
    
    /**
     * Get boolean property value
     */
    public boolean getBooleanProperty(String key, boolean defaultValue) {
        try {
            String value = properties.getProperty(key);
            return value != null ? Boolean.parseBoolean(value) : defaultValue;
        } catch (Exception e) {
            logger.warn("Invalid boolean value for property {}, using default: {}", key, defaultValue);
            return defaultValue;
        }
    }
    
    /**
     * Set property value
     */
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
        logger.debug("Property set: {} = {}", key, value);
    }
    
    // Convenience methods for common properties
    
    public String getDefaultBrowser() {
        return getProperty("default.browser", "chrome");
    }
    
    public int getDefaultTimeout() {
        return getIntProperty("default.timeout", 10);
    }
    
    public String getDefaultUrl() {
        return getProperty("default.url", "https://www.google.com");
    }
    
    public String getScreenshotDirectory() {
        return getProperty("screenshot.directory", "./screenshots/");
    }
    
    public String getReportsDirectory() {
        return getProperty("reports.directory", "./reports/");
    }
    
    public boolean isHeadlessMode() {
        return getBooleanProperty("headless.mode", false);
    }
    
    public int getWindowWidth() {
        return getIntProperty("window.width", 1920);
    }
    
    public int getWindowHeight() {
        return getIntProperty("window.height", 1080);
    }
}