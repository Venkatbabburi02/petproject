package com.screenshot.app.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for taking screenshots with various options
 */
public class ScreenshotUtils {
    
    private static final Logger logger = LogManager.getLogger(ScreenshotUtils.class);
    private final WebDriver driver;
    private final String screenshotDirectory;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    
    public ScreenshotUtils(WebDriver driver) {
        this.driver = driver;
        this.screenshotDirectory = System.getProperty("user.dir") + "/screenshots/";
        createScreenshotDirectory();
    }
    
    /**
     * Create screenshot directory if it doesn't exist
     */
    private void createScreenshotDirectory() {
        File directory = new File(screenshotDirectory);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                logger.info("Screenshot directory created: {}", screenshotDirectory);
            } else {
                logger.error("Failed to create screenshot directory: {}", screenshotDirectory);
            }
        }
    }
    
    /**
     * Take a standard viewport screenshot with default naming
     */
    public String takeScreenshot() {
        String timestamp = LocalDateTime.now().format(DATE_FORMAT);
        String screenshotName = "screenshot_" + timestamp + ".png";
        return takeScreenshot(screenshotName);
    }
    
    /**
     * Take a standard viewport screenshot with custom name
     */
    public String takeScreenshot(String screenshotName) {
        try {
            // Ensure screenshot name has .png extension
            if (!screenshotName.endsWith(".png")) {
                screenshotName += ".png";
            }
            
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            byte[] screenshotBytes = takesScreenshot.getScreenshotAs(OutputType.BYTES);
            
            String filePath = screenshotDirectory + screenshotName;
            File screenshotFile = new File(filePath);
            
            FileUtils.writeByteArrayToFile(screenshotFile, screenshotBytes);
            
            logger.info("Screenshot saved: {}", filePath);
            return filePath;
            
        } catch (Exception e) {
            logger.error("Failed to take screenshot: {}", screenshotName, e);
            return null;
        }
    }
    
    /**
     * Take full page screenshot with default naming
     */
    public String takeFullPageScreenshot() {
        String timestamp = LocalDateTime.now().format(DATE_FORMAT);
        String screenshotName = "fullpage_screenshot_" + timestamp + ".png";
        return takeFullPageScreenshot(screenshotName);
    }
    
    /**
     * Take full page screenshot with custom name
     */
    public String takeFullPageScreenshot(String screenshotName) {
        try {
            // Ensure screenshot name has .png extension
            if (!screenshotName.endsWith(".png")) {
                screenshotName += ".png";
            }
            
            JavascriptExecutor js = (JavascriptExecutor) driver;
            
            // Get the total height and width of the page
            Long totalHeight = (Long) js.executeScript("return document.body.scrollHeight");
            Long totalWidth = (Long) js.executeScript("return document.body.scrollWidth");
            Long viewportHeight = (Long) js.executeScript("return window.innerHeight");
            Long viewportWidth = (Long) js.executeScript("return window.innerWidth");
            
            logger.info("Page dimensions - Total: {}x{}, Viewport: {}x{}", 
                       totalWidth, totalHeight, viewportWidth, viewportHeight);
            
            // If page fits in viewport, take a regular screenshot
            if (totalHeight <= viewportHeight) {
                return takeScreenshot(screenshotName);
            }
            
            // Create a composite image for full page screenshot
            BufferedImage fullPageImage = new BufferedImage(
                totalWidth.intValue(), 
                totalHeight.intValue(), 
                BufferedImage.TYPE_INT_RGB
            );
            
            // Calculate number of screenshots needed
            int numberOfScreenshots = (int) Math.ceil((double) totalHeight / viewportHeight);
            
            for (int i = 0; i < numberOfScreenshots; i++) {
                // Scroll to the position
                int scrollPosition = i * viewportHeight.intValue();
                js.executeScript("window.scrollTo(0, " + scrollPosition + ")");
                
                // Wait for scroll to complete
                Thread.sleep(500);
                
                // Take screenshot
                TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
                byte[] screenshotBytes = takesScreenshot.getScreenshotAs(OutputType.BYTES);
                BufferedImage partialImage = ImageIO.read(new ByteArrayInputStream(screenshotBytes));
                
                // Calculate the actual height of this segment
                int segmentHeight = Math.min(viewportHeight.intValue(), 
                                           totalHeight.intValue() - scrollPosition);
                
                // Draw this segment onto the full page image
                fullPageImage.getGraphics().drawImage(
                    partialImage, 
                    0, 
                    scrollPosition, 
                    viewportWidth.intValue(), 
                    scrollPosition + segmentHeight, 
                    0, 
                    0, 
                    viewportWidth.intValue(), 
                    segmentHeight, 
                    null
                );
                
                logger.debug("Captured segment {} of {}", i + 1, numberOfScreenshots);
            }
            
            // Scroll back to top
            js.executeScript("window.scrollTo(0, 0)");
            
            // Save the full page image
            String filePath = screenshotDirectory + screenshotName;
            File outputFile = new File(filePath);
            ImageIO.write(fullPageImage, "PNG", outputFile);
            
            logger.info("Full page screenshot saved: {}", filePath);
            return filePath;
            
        } catch (Exception e) {
            logger.error("Failed to take full page screenshot: {}", screenshotName, e);
            return null;
        }
    }
    
    /**
     * Take screenshot of a specific element
     */
    public String takeElementScreenshot(WebElement element, String screenshotName) {
        try {
            // Ensure screenshot name has .png extension
            if (!screenshotName.endsWith(".png")) {
                screenshotName += ".png";
            }
            
            byte[] screenshotBytes = element.getScreenshotAs(OutputType.BYTES);
            String filePath = screenshotDirectory + screenshotName;
            File screenshotFile = new File(filePath);
            
            FileUtils.writeByteArrayToFile(screenshotFile, screenshotBytes);
            
            logger.info("Element screenshot saved: {}", filePath);
            return filePath;
            
        } catch (Exception e) {
            logger.error("Failed to take element screenshot: {}", screenshotName, e);
            return null;
        }
    }
    
    /**
     * Take screenshot of element by locator
     */
    public String takeElementScreenshot(By locator, String screenshotName) {
        try {
            WebElement element = driver.findElement(locator);
            return takeElementScreenshot(element, screenshotName);
        } catch (Exception e) {
            logger.error("Failed to find element for screenshot: {}", locator, e);
            return null;
        }
    }
    
    /**
     * Compare two screenshots (basic pixel comparison)
     */
    public boolean compareScreenshots(String screenshot1Path, String screenshot2Path) {
        try {
            BufferedImage img1 = ImageIO.read(new File(screenshot1Path));
            BufferedImage img2 = ImageIO.read(new File(screenshot2Path));
            
            if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
                logger.info("Screenshots have different dimensions");
                return false;
            }
            
            for (int x = 0; x < img1.getWidth(); x++) {
                for (int y = 0; y < img1.getHeight(); y++) {
                    if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
                        logger.info("Screenshots differ at pixel ({}, {})", x, y);
                        return false;
                    }
                }
            }
            
            logger.info("Screenshots are identical");
            return true;
            
        } catch (IOException e) {
            logger.error("Error comparing screenshots", e);
            return false;
        }
    }
    
    /**
     * Get screenshot directory path
     */
    public String getScreenshotDirectory() {
        return screenshotDirectory;
    }
}