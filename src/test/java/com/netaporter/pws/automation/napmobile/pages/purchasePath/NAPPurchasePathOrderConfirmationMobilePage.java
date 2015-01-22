package com.netaporter.pws.automation.napmobile.pages.purchasePath;

import com.netaporter.pws.automation.napmobile.pages.AbstractNapMobilePage;
import com.netaporter.test.utils.pages.driver.WaitTime;
import org.openqa.selenium.By;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by ocsiki on 07/08/2014.
 */
@Component
@Scope("cucumber-glue")
public class NAPPurchasePathOrderConfirmationMobilePage extends AbstractNapMobilePage {

    private static final String PAGE_NAME = "Order Confirmation Mobile Page";
    private static final String PAGE_URL = "/orderconfirmation.nap";

    private By SHIPPING_METHOD_INFORMATION_LOCATOR = By.id("confirmation_delivery");
    private By SHIPPING_INFORMATION_PLUS_BUTTON = By.className("mobile-accordion-control");
    private By THANK_YOU_MESSAGE_LOCATOR = By.id("purchase-notification-main");

    public NAPPurchasePathOrderConfirmationMobilePage() {
        super(PAGE_NAME, PAGE_URL);
    }

    public NAPPurchasePathOrderConfirmationMobilePage(String pageName, String path) {
        super(pageName, path);
    }

    @Override
    public boolean at() {
        //todo: implement this
        return false;
    }

    public String getThankYouMessage() {
        return webBot.findElement(THANK_YOU_MESSAGE_LOCATOR).getText().trim();
    }

    public String getDisplayedShippingMethod() throws InterruptedException {
        Thread.sleep(2000);
        webBot.findElements(SHIPPING_INFORMATION_PLUS_BUTTON, WaitTime.FOUR).get(1).click();
        Thread.sleep(2000);
        String text = webBot.findElement(SHIPPING_METHOD_INFORMATION_LOCATOR).findElement(By.xpath("p")).getText();
        return text.substring(0, text.indexOf("(")).trim();
    }
}