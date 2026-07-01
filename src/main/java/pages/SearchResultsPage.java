package pages;

import core.UserActions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.List;

public class SearchResultsPage extends BasePage {

    public SearchResultsPage(UserActions actions) {
        super(actions);
    }

    private final By maxSlider = By.xpath("(//div[@role='none'])[2]");

    private final By sliderGroup =
            By.xpath("//input[@type='range']/ancestor::div[@role='group']");

    private final By popularFilters =
            By.xpath("//div[@data-filters-group='popular']//label");

    private final By propertyCards =
            By.xpath("//div[@data-testid='property-card']");

    private static final int MAX_SLIDER_ATTEMPTS = 3;

    private static final int SLIDER_BASE_PRICE = 300;
    private static final int SLIDER_RANGE = 270;
    private static final int SLIDER_OFFSET = 5;

    public void setMaxPrice(int maxPrice) {

        for (int attempt = 1; attempt <= MAX_SLIDER_ATTEMPTS; attempt++) {

            try {

                actions.waitForPageToLoad();

                WebElement handle = actions.waitForPresent(maxSlider);

                new Actions(actions.getDriver())
                        .scrollToElement(handle)
                        .perform();

                WebElement group = actions.waitForPresent(sliderGroup);

                int width = group.getRect().getWidth();

                int moveX =
                        (maxPrice - SLIDER_BASE_PRICE) * width / SLIDER_RANGE + SLIDER_OFFSET;

                handle = actions.waitForPresent(maxSlider);

                new Actions(actions.getDriver())
                        .moveToElement(handle)
                        .clickAndHold()
                        .pause(Duration.ofMillis(100))
                        .moveByOffset(moveX, 0)
                        .pause(Duration.ofMillis(300))
                        .release()
                        .perform();

                actions.waitForPresent(By.xpath("//input[@value='" + maxPrice + "']"));
                actions.waitForVisible(
                        By.xpath("//button[contains(@data-testid, 'filter:price')]")
                );

                return;

            } catch (StaleElementReferenceException e) {

                if (attempt == MAX_SLIDER_ATTEMPTS) {
                    throw e;
                }
            }
        }
    }

    public void selectPopularFilter(String filterName) {

        List<WebElement> filters =
                actions.waitForVisibilityOfAllElements(popularFilters);

        for (WebElement filter : filters) {

            if (filter.getText().contains(filterName)) {
                filter.click();
                return;
            }
        }

        throw new RuntimeException("Popular filter not found: " + filterName);
    }

    public void selectHotelByIndex(int index) {

        List<WebElement> cards =
                actions.waitForVisibilityOfAllElements(propertyCards);

        if (index < 0 || index >= cards.size()) {
            throw new IllegalArgumentException(
                    "Invalid hotel index: " + index + ", available: " + cards.size()
            );
        }

        WebElement hotel = cards.get(index);

        actions.scrollToElement(hotel);
        hotel.click();
    }

    public void waitForResultsToLoad() {
        actions.waitForVisible(propertyCards);
    }

    public boolean hasResults() {
        return actions.hasAtLeastNElements(propertyCards, 1);
    }
}