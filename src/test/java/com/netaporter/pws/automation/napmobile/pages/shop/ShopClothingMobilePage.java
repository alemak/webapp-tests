package com.netaporter.pws.automation.napmobile.pages.shop;

import com.netaporter.pws.automation.napmobile.pages.AbstractNapMobilePage;
import com.netaporter.test.utils.pages.regionalisation.RegionaliseWithRegionName;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class ShopClothingMobilePage extends AbstractNapMobilePage {

    private static final String NAME = "Shop Clothing Mobile";
    private static final String PATH = "Shop/Clothing/All";

    /* Page Objects
    --------------------------------------*/

    /* Constructor
    --------------------------------------*/
    public ShopClothingMobilePage() {
        super(NAME, PATH);
        setRegionalisePathBehavior(new RegionaliseWithRegionName());
    }

    /* Getters
    --------------------------------------*/

    /* Helpers
    --------------------------------------*/
    public boolean at() {
        return titleMobileComponent.titleMainHeadingText().equals("CLOTHING");
    }
}
