package pages;

import testData.TestDataGenerator;
import core.UserActions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

public class GuestDetailsPage extends BasePage {

    private final TestDataGenerator testData;

    public GuestDetailsPage(UserActions actions,
                            TestDataGenerator testData) {

        super(actions);
        this.testData = testData;
    }

    private final By firstNamePlaceholder = By.xpath("//input[@data-testid='user-details-firstname']");
    private final By lastNamePlaceholder = By.xpath("//input[@data-testid='user-details-lastname']");
    private final By emailPlaceholder = By.xpath("//input[@data-testid='user-details-email']");
    private final By phoneNumberPlaceholder = By.xpath("//input[@data-testid='phone-number-input']");
    private final By countryDropdown =
            By.xpath("//select[@data-testid='user-details-cc1']");
    private final By finalDetailsButton = By.xpath("//button[@data-popover-content-id='bp-submit-popover']");

    public void fillGuestDetails() {
        actions.type(firstNamePlaceholder, testData.firstName());
        actions.type(lastNamePlaceholder, testData.lastName());
        actions.type(emailPlaceholder, testData.email());
        selectCountry();
        actions.type(phoneNumberPlaceholder, testData.phone());
    }

    public void selectCountry() {

        Select select = new Select(actions.waitForPresent(countryDropdown));
        select.selectByVisibleText("Bulgaria");
    }
    public void clickFinalDetailsButton(){
        actions.click(finalDetailsButton);
    }

}
