package com.netaporter.pws.automation.napmobile.pages.shop;

import com.netaporter.pws.automation.napmobile.pages.AbstractNapMobilePage;
import com.netaporter.test.utils.pages.regionalisation.RegionaliseWithRegionName;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class  ShopShortsMobilePage extends AbstractNapMobilePage {

    private static final String NAME = "Shop Shorts Mobile";
    private static final String PATH = "Shop/Clothing/Activewear/Shorts";

    /* Page Objects
    --------------------------------------*/

    /* Constructor
    --------------------------------------*/
    public ShopShortsMobilePage() {
        super(NAME, PATH);
        setRegionalisePathBehavior(new RegionaliseWithRegionName());
    }

    /* Getters
    --------------------------------------*/

    /* Helpers
    --------------------------------------*/
    public boolean at() {
        return titleMobileComponent.titleMainHeadingText().equals("CLOTHING")
                && titleMobileComponent.titleSubListText().equals("SHORTS");
    }
}
