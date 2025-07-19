package com.screenshot.app.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
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
    private static final String SCREENSHOT_DIR = "screenshots/";
    private static final String DATE_FORMAT = "yyyy-MM-dd_HH-mm-ss";
    private WebDriver driver;
    
    public ScreenshotUtils(WebDriver driver) {
        this.driver = driver;
        createScreenshotDirectory();
    }
    
    /**
     * Create screenshot directory if it doesn't exist
     */
    private void createScreenshotDirectory() {
        File dir = new File(SCREENSHOT_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    /**
     * Take a basic screenshot of the current viewport
     */
    public String takeScreenshot(String fileName) {
        try {
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            byte[] screenshot = takesScreenshot.getScreenshotAs(OutputType.BYTES);
            
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
            String fullFileName = fileName + "_" + timestamp + ".png";
            String filePath = SCREENSHOT_DIR + fullFileName;
            
            FileUtils.writeByteArrayToFile(new File(filePath), screenshot);
            logger.info("Screenshot saved: {}", filePath);
            
            return filePath;
        } catch (IOException e) {
            logger.error("Failed to take screenshot: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * Take a full page screenshot (including scrollable content)
     */
    public String takeFullPageScreenshot(String fileName) {
        try {
            // Get page dimensions
            JavascriptExecutor js = (JavascriptExecutor) driver;
            Long totalHeight = (Long) js.executeScript("return Math.max(document.body.scrollHeight, document.documentElement.scrollHeight)");
            Long viewportHeight = (Long) js.executeScript("return window.innerHeight");
            
            // If page fits in viewport, take regular screenshot
            if (totalHeight <= viewportHeight) {
                return takeScreenshot(fileName + "_fullpage");
            }
            
            // Take multiple screenshots and stitch them together
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            
            // Reset scroll position
            js.executeScript("window.scrollTo(0, 0)");
            Thread.sleep(500);
            
            // Take first screenshot
            byte[] firstScreenshot = takesScreenshot.getScreenshotAs(OutputType.BYTES);
            BufferedImage fullImage = ImageIO.read(new ByteArrayInputStream(firstScreenshot));
            
            int currentScroll = 0;
            int scrollStep = viewportHeight.intValue() - 100; // Overlap for seamless stitching
            
            while (currentScroll < totalHeight - viewportHeight) {
                currentScroll += scrollStep;
                js.executeScript("window.scrollTo(0, " + currentScroll + ")");
                Thread.sleep(500);
                
                byte[] screenshot = takesScreenshot.getScreenshotAs(OutputType.BYTES);
                BufferedImage currentImage = ImageIO.read(new ByteArrayInputStream(screenshot));
                
                // Create new image with combined height
                BufferedImage combinedImage = new BufferedImage(
                    fullImage.getWidth(),
                    fullImage.getHeight() + currentImage.getHeight() - 100, // Subtract overlap
                    BufferedImage.TYPE_INT_RGB
                );
                
                // Draw existing image
                combinedImage.getGraphics().drawImage(fullImage, 0, 0, null);
                // Draw new image below with overlap
                combinedImage.getGraphics().drawImage(currentImage, 0, fullImage.getHeight() - 100, null);
                
                fullImage = combinedImage;
            }
            
            // Save the full page screenshot
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
            String fullFileName = fileName + "_fullpage_" + timestamp + ".png";
            String filePath = SCREENSHOT_DIR + fullFileName;
            
            ImageIO.write(fullImage, "PNG", new File(filePath));
            logger.info("Full page screenshot saved: {}", filePath);
            
            // Reset scroll position
            js.executeScript("window.scrollTo(0, 0)");
            
            return filePath;
            
        } catch (Exception e) {
            logger.error("Failed to take full page screenshot: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * Take screenshot of a specific element
     */
    public String takeElementScreenshot(WebElement element, String fileName) {
        try {
            byte[] screenshot = element.getScreenshotAs(OutputType.BYTES);
            
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
            String fullFileName = fileName + "_element_" + timestamp + ".png";
            String filePath = SCREENSHOT_DIR + fullFileName;
            
            FileUtils.writeByteArrayToFile(new File(filePath), screenshot);
            logger.info("Element screenshot saved: {}", filePath);
            
            return filePath;
        } catch (IOException e) {
            logger.error("Failed to take element screenshot: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * Take screenshot with custom directory
     */
    public String takeScreenshot(String fileName, String customDir) {
        try {
            // Create custom directory if it doesn't exist
            File dir = new File(customDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            byte[] screenshot = takesScreenshot.getScreenshotAs(OutputType.BYTES);
            
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
            String fullFileName = fileName + "_" + timestamp + ".png";
            String filePath = customDir + "/" + fullFileName;
            
            FileUtils.writeByteArrayToFile(new File(filePath), screenshot);
            logger.info("Screenshot saved: {}", filePath);
            
            return filePath;
        } catch (IOException e) {
            logger.error("Failed to take screenshot: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * Compare two screenshots and highlight differences
     */
    public boolean compareScreenshots(String screenshot1Path, String screenshot2Path) {
        try {
            BufferedImage img1 = ImageIO.read(new File(screenshot1Path));
            BufferedImage img2 = ImageIO.read(new File(screenshot2Path));
            
            if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
                logger.warn("Screenshots have different dimensions");
                return false;
            }
            
            for (int x = 0; x < img1.getWidth(); x++) {
                for (int y = 0; y < img1.getHeight(); y++) {
                    if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
                        return false;
                    }
                }
            }
            
            return true;
        } catch (IOException e) {
            logger.error("Failed to compare screenshots: {}", e.getMessage());
            return false;
        }
    }
}