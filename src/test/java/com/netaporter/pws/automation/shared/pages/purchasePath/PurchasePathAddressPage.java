package com.netaporter.pws.automation.shared.pages.purchasePath;

import com.netaporter.pws.automation.shared.components.AddressComponent;
import com.netaporter.pws.automation.shared.pojos.Address;

import com.netaporter.test.utils.pages.AbstractPage;

import com.netaporter.test.utils.pages.driver.WaitTime;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class PurchasePathAddressPage extends AbstractPage {

    @Autowired
    private AddressComponent addressComponent;

    private static final By PROCEED_TO_PURCHASE_LOCATOR = By.name("_eventId_proceedToPurchase");

    public PurchasePathAddressPage(String pageName, String path) {
        super(pageName, path);
    }

    public void completeAddress(Address address) {
        addressComponent.fillInAddress(address);
    }

    public void clickProceedToPurchase() {
        webBot.findElement(PROCEED_TO_PURCHASE_LOCATOR, WaitTime.FOUR).click();
    }
}