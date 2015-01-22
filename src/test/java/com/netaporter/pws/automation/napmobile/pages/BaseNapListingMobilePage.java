package com.netaporter.pws.automation.napmobile.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Collections;
import java.util.List;

/**
 * Created by ocsiki on 07/08/2014.
 */
public abstract class BaseNapListingMobilePage extends AbstractNapMobilePage {

    private By PRODUCT_LOCATOR = By.className("product-row");

    public BaseNapListingMobilePage(String pageName, String path) {
        super(pageName, path);
    }

    @Override
    public boolean at() {
        return false;
    }

    public void clickARandomProduct() {
        List<WebElement> productList = webBot.findElements(PRODUCT_LOCATOR);
        Collections.shuffle(productList);
        productList.get(0).findElement(By.xpath("a")).click();
    }
}
