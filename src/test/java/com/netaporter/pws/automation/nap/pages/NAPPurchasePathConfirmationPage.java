package com.netaporter.pws.automation.nap.pages;

import com.netaporter.pws.automation.shared.pages.purchasePath.PurchasePathConfirmationPage;
import com.netaporter.test.utils.pages.driver.WaitTime;
import org.openqa.selenium.By;

public class NAPPurchasePathConfirmationPage extends PurchasePathConfirmationPage {

    private static final String PAGE_NAME = "NAP order confirmation page";
    private static final String PAGE_PATH = "orderconfirmation.nap";

    public static final By ORDER_NUMBER_LOCATOR = By.cssSelector("p#purchase-notification-sub span");

    public NAPPurchasePathConfirmationPage() {
        super(PAGE_NAME, PAGE_PATH);
    }

    @Override
    public Integer getOrderNumber() {
        String orderText = webBot.findElement(ORDER_NUMBER_LOCATOR, WaitTime.FOUR).getText();
        return Integer.parseInt(orderText);
    }

}