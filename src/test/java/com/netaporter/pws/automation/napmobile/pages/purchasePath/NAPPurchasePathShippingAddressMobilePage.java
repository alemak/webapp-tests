package com.netaporter.pws.automation.napmobile.pages.purchasePath;

import com.netaporter.pws.automation.napmobile.pages.AbstractNapMobilePage;
import org.openqa.selenium.By;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by ocsiki on 07/08/2014.
 */
@Component
@Scope("cucumber-glue")
public class NAPPurchasePathShippingAddressMobilePage extends AbstractNapMobilePage {

    private static final String PAGE_NAME = "Shipping Address Purchase Path Mobile Page";
    private static final String PAGE_URL = "/purchasepath.nap";

    private By PURCHASE_NOW_BUTTON_LOCATOR = By.id("purchasenow");

    public NAPPurchasePathShippingAddressMobilePage() {
        super(PAGE_NAME, PAGE_URL);
    }


    public NAPPurchasePathShippingAddressMobilePage(String pageName, String path) {
        super(pageName, path);
    }

    @Override
    public boolean at() {
        return false;
    }

    public void clickContinue() {
        webBot.click(PURCHASE_NOW_BUTTON_LOCATOR);

    }
}
