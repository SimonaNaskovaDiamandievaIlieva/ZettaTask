package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class SetupWebDriver {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private SetupWebDriver() {}

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            driver.set(createDriver());
            driver.get().manage().window().maximize();
        }
        return driver.get();
    }

    public static WebDriver getExistingDriver() {
        return driver.get();
    }

    private static WebDriver createDriver() {

        String browser = Utils.getConfigPropertyByKey("browser");

        switch (browser.toLowerCase()) {

            case "chrome":
                WebDriverManager.chromedriver().setup();

                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--disable-notifications");

                return new ChromeDriver(chromeOptions);

            case "firefox":
                WebDriverManager.firefoxdriver().setup();

                FirefoxOptions firefoxOptions = new FirefoxOptions();

                return new FirefoxDriver(firefoxOptions);

            case "edge":
                WebDriverManager.edgedriver().setup();

                EdgeOptions edgeOptions = new EdgeOptions();

                return new EdgeDriver(edgeOptions);

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}