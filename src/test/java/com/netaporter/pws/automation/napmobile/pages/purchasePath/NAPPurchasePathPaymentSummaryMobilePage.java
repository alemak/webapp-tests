package com.netaporter.pws.automation.napmobile.pages.purchasePath;

import com.netaporter.pws.automation.napmobile.pages.AbstractNapMobilePage;
import com.netaporter.pws.automation.napmobile.util.GenericUtils;
import com.netaporter.pws.automation.shared.components.PaymentForm;
import com.netaporter.pws.automation.shared.pojos.PaymentDetails;
import com.netaporter.pws.automation.shared.utils.CardPaymentDetails;
import org.openqa.selenium.By;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by ocsiki on 07/08/2014.
 */
@Component
@Scope("cucumber-glue")
public class NAPPurchasePathPaymentSummaryMobilePage extends AbstractNapMobilePage {

    private static final String PAGE_NAME = "Payment Summary Mobile Page";
    private static final String PAGE_URL = "/purchasepath.nap";

    private By SHIPPING_METHOD_INFORMATION_LOCATOR = By.className("shipping_method");
    private By SHIPPING_DETAILS_PLUS_BUTTON_LOCATOR = By.className("mobile-accordion-control");
    private By PAY_NOW_BUTTON_LOCATOR = By.name("_eventId_processPayment");

    public NAPPurchasePathPaymentSummaryMobilePage() {
        super(PAGE_NAME, PAGE_URL);
    }


    public NAPPurchasePathPaymentSummaryMobilePage(String pageName, String path) {
        super(pageName, path);
    }

    @Override
    public boolean at() {
        //todo: implement this
        return false;
    }

    public void payBy(String cardType) {
        CardPaymentDetails cardPaymentDetails = CardPaymentDetails.getCardPaymentDetails(cardType);
        PaymentDetails paymentDetails = cardPaymentDetails.getPaymentDetails(webBot.getRegion());
        webBot.click(By.id(paymentDetails.getCardType().toLowerCase()));
        webBot.type(PaymentForm.CARD_NUMBER, paymentDetails.getCardNumber());
        webBot.type(PaymentForm.CARD_NAME, paymentDetails.getCardholderName() + GenericUtils.createRandomString(5));
        webBot.type(PaymentForm.SECURITY_NUMBER, paymentDetails.getSecurityNumber());
        webBot.selectElementText(PaymentForm.EXPIRY_MONTH, paymentDetails.getExpiryMonth());
        webBot.selectElementText(PaymentForm.EXPIRY_YEAR, paymentDetails.getExpiryYear());
        if (paymentDetails.getIssueNumber() != null)
            webBot.type(PaymentForm.ISSUE_NUMBER, paymentDetails.getIssueNumber());

        webBot.click(PAY_NOW_BUTTON_LOCATOR);
    }

    public String getDisplayedShippingMethod() throws InterruptedException {
       Thread.sleep(2000);
       webBot.findElements(SHIPPING_DETAILS_PLUS_BUTTON_LOCATOR).get(1).click();
       Thread.sleep(2000);
       String shippingOptionText = webBot.findElement(SHIPPING_METHOD_INFORMATION_LOCATOR).findElement(By.xpath("p")).getText().trim();
       return shippingOptionText.replaceAll("[^a-zA-Z ]","").trim();
    }
}
