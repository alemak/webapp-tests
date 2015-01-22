package com.netaporter.pws.automation.napmobile.pages.shop;

import com.netaporter.pws.automation.napmobile.pages.AbstractNapMobilePage;
import com.netaporter.test.utils.pages.regionalisation.RegionaliseWithRegionName;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class ShopCorsetryMobilePage extends AbstractNapMobilePage {

    private static final String NAME = "Shop Corsetry Mobile";
    private static final String PATH = "Shop/Lingerie/Corsetry";

    /* Page Objects
    --------------------------------------*/

    /* Constructor
    --------------------------------------*/
    public ShopCorsetryMobilePage() {
        super(NAME, PATH);
        setRegionalisePathBehavior(new RegionaliseWithRegionName());
    }

    /* Getters
    --------------------------------------*/

    /* Helpers
    --------------------------------------*/
    public boolean at() {
        return titleMobileComponent.titleMainHeadingText().equals("LINGERIE")
                && titleMobileComponent.titleSubListText().equals("CORSETRY");
    }
}
