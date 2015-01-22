package com.netaporter.pws.automation.napmobile.pages;

import com.netaporter.test.utils.pages.driver.WaitTime;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by ocsiki on 07/08/2014.
 */
@Component
@Scope("cucumber-glue")
public class NAPShoppingBagMobilePage extends AbstractNapMobilePage {

    private static final String PAGE_NAME = "Mobile Shopping Bag";
    private static final String PAGE_URL = "/shoppingbag.nap";

    private By PROCEED_TO_PURCHASE_BUTTON_LOCATOR = By.className("button-primary");


    public NAPShoppingBagMobilePage(String pageName, String path) {
        super(pageName, path);
    }

    public NAPShoppingBagMobilePage() {
        super(PAGE_NAME, PAGE_URL);
    }

    @Override
    public boolean at() {
        return false;
    }


    public void clickProceedToPurchase() throws InterruptedException {
        Thread.sleep(2000);
        webBot.findElement(PROCEED_TO_PURCHASE_BUTTON_LOCATOR, WaitTime.FOUR).findElement(By.xpath("a")).click();
    }

    public String getShoppingBasketUrl() {
        return webBot.getCurrentUrl();
    }
}
