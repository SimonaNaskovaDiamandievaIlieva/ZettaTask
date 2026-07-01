package core;

import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class UserActions {

    @Getter
    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final int SHORT_WAIT = 5;
    private static final String SCREENSHOTS_DIR = "screenshots/";

    public UserActions(WebDriver driver, int timeoutSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    }

    public WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitForPresent(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public List<WebElement> waitForVisibilityOfAllElements(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public void click(By locator) {

        WebElement element = waitForClickable(locator);

        try {
            element.click();
        } catch (ElementClickInterceptedException e) {

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({block: 'center'});", element);

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", element);
        }
    }

    public void type(By locator, String text) {

        WebElement element = waitForClickable(locator);

        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", element);
        }

        element.clear();
        element.sendKeys(text);
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);
    }

    public void switchToNewTab() {

        String currentTab = driver.getWindowHandle();
        Set<String> allTabs = driver.getWindowHandles();

        for (String tab : allTabs) {
            if (!tab.equals(currentTab)) {
                driver.switchTo().window(tab);
                return;
            }
        }

        throw new RuntimeException("No new tab found to switch to");
    }

    public boolean isElementVisible(By locator) {
        try {
            WebDriverWait shortWait =
                    new WebDriverWait(driver, Duration.ofSeconds(SHORT_WAIT));

            WebElement element =
                    shortWait.until(ExpectedConditions.visibilityOfElementLocated(locator));

            return element.isDisplayed();

        } catch (TimeoutException e) {
            return false;
        }
    }

    public void takeScreenshot(String fileName) throws IOException {

        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        File target = new File(SCREENSHOTS_DIR + fileName + ".png");

        FileUtils.copyFile(source, target);
    }

    public void clickIfPresent(By locator, int timeoutSeconds) {
        try {
            WebDriverWait shortWait =
                    new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));

            WebElement el =
                    shortWait.until(ExpectedConditions.elementToBeClickable(locator));

            el.click();

        } catch (TimeoutException ignored) {
        }
    }

    public void switchToFrame(By frameLocator) {
        waitForVisible(frameLocator);
        driver.switchTo().frame(waitForPresent(frameLocator));
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public void waitForPageToLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(d -> ((JavascriptExecutor) d)
                        .executeScript("return document.readyState")
                        .equals("complete"));
    }

    public boolean hasAtLeastNElements(By locator, int expectedCount) {
        try {
            List<WebElement> elements = waitForVisibilityOfAllElements(locator);
            return elements != null && elements.size() >= expectedCount;
        } catch (TimeoutException e) {
            return false;
        }
    }
}