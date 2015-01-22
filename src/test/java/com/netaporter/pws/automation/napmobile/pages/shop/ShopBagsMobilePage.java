package com.netaporter.pws.automation.napmobile.pages.shop;

import com.netaporter.pws.automation.napmobile.pages.BaseNapListingMobilePage;
import com.netaporter.test.utils.pages.regionalisation.RegionaliseWithRegionName;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class ShopBagsMobilePage extends BaseNapListingMobilePage {

    private static final String NAME = "Shop Bags Mobile";
    private static final String PATH = "Shop/Bags/All";

    /* Page Objects
    --------------------------------------*/

    /* Constructor
    --------------------------------------*/
    public ShopBagsMobilePage() {
        super(NAME, PATH);
        setRegionalisePathBehavior(new RegionaliseWithRegionName());
    }

    /* Getters
    --------------------------------------*/

    /* Helpers
    --------------------------------------*/
    public boolean at() {
        return titleMobileComponent.titleMainHeadingText().equals("BAGS");
    }
}
