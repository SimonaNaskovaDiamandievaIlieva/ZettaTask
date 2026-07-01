package uiTest;

import org.testng.Assert;
import org.testng.annotations.Test;
import testComponents.BaseTest;
import testData.BookingTestData;
import java.io.IOException;

public class BookingTest extends BaseTest {

    @Test(groups = {"regression"}, retryAnalyzer = testComponents.Retry.class)
    public void searchHotelOnBooking() throws IOException {

        homePage.selectDestination(
                BookingTestData.CITY_INPUT,
                BookingTestData.CITY_EXPECTED
        );

        homePage.selectDates(
                BookingTestData.CHECK_IN,
                BookingTestData.CHECK_OUT
        );

        homePage.openOccupancyDropdownAndAssertAdults(
                BookingTestData.ADULTS
        );

        homePage.clickSearchButton();

        searchResultsPage.waitForResultsToLoad();
        Assert.assertTrue(
                searchResultsPage.hasResults(),
                "Search results were not loaded"
        );

        searchResultsPage.setMaxPrice(BookingTestData.MAX_PRICE);
        searchResultsPage.selectPopularFilter("Hotels");
        searchResultsPage.selectHotelByIndex(BookingTestData.HOTEL_INDEX);

        actions.switchToNewTab();

        hotelDetailsPage.waitForHotelPageToLoad();

        Assert.assertTrue(
                hotelDetailsPage.isLoaded(),
                "Hotel details page did not load"
        );

        hotelDetailsPage.selectCheapestRoomAndReserve();
        hotelDetailsPage.clickReserveButton();

        guestDetailsPage.fillGuestDetails();
        guestDetailsPage.clickFinalDetailsButton();

        boolean paymentValid = paymentPage.handlePaymentFlowAndValidate();
        Assert.assertTrue(
                paymentValid,
                "Payment validation failed: no payment UI or fallback option visible"
        );
    }
}