package testComponents;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import core.ExtentReporterNG;
import core.SetupWebDriver;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

public class Listeners implements ITestListener {

    ExtentTest test;
    ExtentReports extent = ExtentReporterNG.getReportObject();

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        test.log(Status.FAIL, result.getThrowable());
        WebDriver driver = SetupWebDriver.getExistingDriver();

        if (driver == null) {
            test.log(Status.WARNING, "Driver is null - screenshot skipped");
            return;
        }

        try {
            if (result.getInstance() instanceof BaseTest) {

                String filePath = ((BaseTest) result.getInstance())
                        .getScreenshot(result.getMethod().getMethodName(), driver);

                test.addScreenCaptureFromPath(filePath);
            } else {
                test.log(Status.WARNING, "BaseTest instance not found - screenshot skipped");
            }

        } catch (IOException e) {
            test.log(Status.WARNING, "Screenshot failed: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}