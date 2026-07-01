package pages;

import core.UserActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class HotelDetailsPage extends BasePage {

    public HotelDetailsPage(UserActions actions) {
        super(actions);
    }

    private final By roomCards = By.xpath(
            "//tr[contains(@class,'js-rt-block-row') and contains(@class,'hprt-table-row')]"
    );

    private final By roomSelectDropdown = By.xpath(
            ".//select[contains(@class,'js-hprt-nos-select')]"
    );

    private final By reserveButton =
            By.id("hp_book_now_button");

    private static final String ROOM_QUANTITY = "1";

    private final By priceLocator = By.xpath(
            ".//td[contains(@class,'hprt-table-cell-price')]//div[@aria-hidden='true']/span"
    );

    private int extractPrice(WebElement room) {

        WebElement priceElement = room.findElement(priceLocator);

        String priceText = priceElement.getText();

        return Integer.parseInt(priceText.replaceAll("[^0-9]", ""));
    }

    public void selectCheapestRoomAndReserve() {

        List<WebElement> rooms =
                actions.waitForVisibilityOfAllElements(roomCards);

        WebElement cheapestRoom = null;
        int minPrice = Integer.MAX_VALUE;

        for (WebElement room : rooms) {

            int price = extractPrice(room);

            if (price <= minPrice) {
                minPrice = price;
                cheapestRoom = room;
            }
        }

        if (cheapestRoom == null) {
            throw new RuntimeException("No rooms found");
        }

        actions.scrollToElement(cheapestRoom);

        WebElement dropdown = cheapestRoom.findElement(roomSelectDropdown);

        Select select = new Select(dropdown);
        select.selectByValue(ROOM_QUANTITY);
    }

    public void clickReserveButton() {
        actions.click(reserveButton);
    }

    public void waitForHotelPageToLoad() {
        actions.waitForVisible(roomCards);
    }

    public boolean isLoaded() {
        return actions.hasAtLeastNElements(roomCards, 1);
    }
}