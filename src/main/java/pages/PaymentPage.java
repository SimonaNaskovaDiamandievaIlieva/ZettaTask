package pages;

import core.UserActions;
import org.openqa.selenium.By;

import java.io.IOException;

public class PaymentPage extends BasePage {

    public PaymentPage(UserActions actions) {
        super(actions);
    }

    private final By paymentIframe =
            By.xpath("//iframe[@title='Payment']");

    private final By cardNumberInput =
            By.xpath("//input[contains(@id,'card-number') or contains(@name,'card-number')]");

    private final By cardNumberLabel =
            By.xpath("//label[contains(.,'Card number')]");

    private final By noPaymentReservationButton =
            By.xpath("//span[contains(text(),'Book with commitment to pay')]");

    public void switchToPaymentIframe() {
        actions.switchToFrame(paymentIframe);
    }

    public void switchBack() {
        actions.switchToDefaultContent();
    }

    private boolean isCardPaymentVisible() {
        return actions.isElementVisible(cardNumberInput)
                || actions.isElementVisible(cardNumberLabel);
    }

    private boolean isNoPaymentReservationButtonVisible() {
        return actions.isElementVisible(noPaymentReservationButton);
    }

    public boolean handlePaymentFlowAndValidate() throws IOException {

        boolean hasIframe = actions.isElementVisible(paymentIframe);

        if (hasIframe) {

            switchToPaymentIframe();

            boolean valid = isCardPaymentVisible();

            actions.takeScreenshot("payment_page");

            switchBack();

            return valid;
        }

        boolean fallbackValid = isNoPaymentReservationButtonVisible();

        actions.takeScreenshot("payment_page_fallback");

        return fallbackValid;
    }
}