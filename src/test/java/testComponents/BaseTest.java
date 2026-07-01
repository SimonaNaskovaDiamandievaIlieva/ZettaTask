package testComponents;

import core.SetupWebDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import testData.TestDataGenerator;
import core.UserActions;
import core.Utils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.*;

import java.io.File;
import java.io.IOException;

public abstract class BaseTest {

    protected WebDriver driver;
    protected UserActions actions;
    protected TestDataGenerator testData;

    protected HomePage homePage;
    protected SearchResultsPage searchResultsPage;
    protected HotelDetailsPage hotelDetailsPage;
    protected GuestDetailsPage guestDetailsPage;
    protected PaymentPage paymentPage;

    @BeforeMethod
    public void setUp() {

        driver = SetupWebDriver.getDriver();
        driver.get(Utils.getConfigPropertyByKey("base.url"));

        actions = new UserActions(driver, Utils.getTimeout());
        testData = new TestDataGenerator();
        homePage = new HomePage(actions);


        homePage.acceptCookies();
        homePage.clickDismissButton();

        searchResultsPage = new SearchResultsPage(actions);
        hotelDetailsPage = new HotelDetailsPage(actions);
        guestDetailsPage = new GuestDetailsPage(actions, testData);
        paymentPage = new PaymentPage(actions);
    }

    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot)driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File file = new File(System.getProperty("user.dir")+"\\reports\\" + testCaseName + ".png");
        FileUtils.copyFile(source, file);
        return System.getProperty("user.dir")+"\\reports\\" + testCaseName + ".png";
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        SetupWebDriver.quitDriver();
    }
}