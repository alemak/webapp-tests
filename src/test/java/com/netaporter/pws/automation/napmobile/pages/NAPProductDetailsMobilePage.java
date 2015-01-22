package com.netaporter.pws.automation.napmobile.pages;

import org.openqa.selenium.By;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by ocsiki on 07/08/2014.
 */
@Component
@Scope("cucumber-glue")
public class NAPProductDetailsMobilePage extends AbstractNapMobilePage {

    private static final String PAGE_NAME = "Product Details Mobile Page";
    private static final String PAGE_PATH = "/product";

    private By ADD_TO_SHOPPING_BAG_BUTTON_LOCATOR = By.name("shoppingBag");

    public NAPProductDetailsMobilePage(String pageName, String path) {
        super(pageName, path);
    }

    public NAPProductDetailsMobilePage() {
        super(PAGE_NAME, PAGE_PATH);
    }

    @Override
    public boolean at() {
        return false;
    }


    public void clickAddToShoppingBag() {
        webBot.findElement(ADD_TO_SHOPPING_BAG_BUTTON_LOCATOR).click();
    }
}
