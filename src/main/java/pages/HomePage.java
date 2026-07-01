package pages;

import core.UserActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class HomePage extends BasePage {

    public HomePage(UserActions actions) {
        super(actions);
    }

    private final By destination =
            By.xpath("//input[@name='ss' and @placeholder='Where are you going?']");

    private final By options =
            By.xpath("//*[@data-testid='autocomplete-results']//li[@role='option']");

    private final By searchButton =
            By.xpath("//button[@type='submit']");

    private final By nextMonthBtn =
            By.xpath("//button[@aria-label='Next month']");

    private final By day =
            By.cssSelector("span[data-date]");

    private final By calendarContainer =
            By.xpath("//nav[@data-testid='datepicker-tabs']");

    private final By occupancyDropdown =
            By.xpath("//button[@data-testid='occupancy-config']");

    private final By occupancyLabel =
            By.xpath("//button[@data-testid='occupancy-config']//span[@data-compressable='label']");

    private final By acceptCookiesButton =
            By.xpath("//button[text()='Accept']");

    private final By dismissButton =
            By.xpath("//button[contains(@aria-label,'Dismiss')]");

    public void clickSearchButton() {
        actions.click(searchButton);
    }

    public void clickDismissButton() {
        actions.waitForVisible(dismissButton);
        actions.click(dismissButton);
    }

    public void enterDestination(String text) {
        actions.type(destination, text);
    }

    public void selectDestination(String input, String expected) {
        enterDestination(input);
        actions.waitForPresent(options);
        selectFromDropdown(expected);
    }

    private void selectFromDropdown(String expectedValue) {

        String expected = expectedValue.trim().toLowerCase();

        List<WebElement> items =
                actions.waitForVisibilityOfAllElements(options);

        for (WebElement item : items) {

            if (item.getText().trim().toLowerCase().contains(expected)) {
                item.click();
                return;
            }
        }

        throw new RuntimeException("Option not found: " + expectedValue);
    }

    private void goToMonth(LocalDate targetDate) {

        YearMonth targetMonth = YearMonth.from(targetDate);

        for (int i = 0; i < 24; i++) {

            List<WebElement> days = actions.getDriver().findElements(day);

            for (WebElement d : days) {

                String value = d.getDomAttribute("data-date");

                if (value != null && value.startsWith(targetMonth.toString())) {
                    return;
                }
            }

            actions.waitForClickable(nextMonthBtn).click();
        }

        throw new RuntimeException("Month not found for: " + targetDate);
    }

    private void selectDay(LocalDate date) {

        By dayLocator =
                By.cssSelector("span[data-date='" + date + "']");

        actions.click(dayLocator);
    }

    public void selectDates(LocalDate checkIn, LocalDate checkOut) {

        actions.waitForClickable(calendarContainer).click();

        goToMonth(checkIn);
        selectDay(checkIn);

        goToMonth(checkOut);
        selectDay(checkOut);
    }

    public void openOccupancyDropdownAndAssertAdults(int expectedAdults) {

        actions.click(occupancyDropdown);

        String labelText =
                actions.waitForVisible(occupancyLabel).getText().toLowerCase();

        String expected = expectedAdults + " adult";

        if (!labelText.contains(expected)) {
            throw new AssertionError(
                    "Expected adults: " + expectedAdults +
                            " but UI shows: " + labelText
            );
        }
    }

    public void acceptCookies() {
        actions.click(acceptCookiesButton);
    }
}