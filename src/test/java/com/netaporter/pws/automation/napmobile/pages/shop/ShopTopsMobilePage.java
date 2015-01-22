package com.netaporter.pws.automation.napmobile.pages.shop;

import com.netaporter.pws.automation.napmobile.pages.AbstractNapMobilePage;
import com.netaporter.test.utils.pages.regionalisation.RegionaliseWithRegionName;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class  ShopTopsMobilePage extends AbstractNapMobilePage {

    private static final String NAME = "Shop Tops Mobile";
    private static final String PATH = "Shop/Clothing/Activewear/Tops";

    /* Page Objects
    --------------------------------------*/

    /* Constructor
    --------------------------------------*/
    public ShopTopsMobilePage() {
        super(NAME, PATH);
        setRegionalisePathBehavior(new RegionaliseWithRegionName());
    }

    /* Getters
    --------------------------------------*/

    /* Helpers
    --------------------------------------*/
    public boolean at() {
        return titleMobileComponent.titleMainHeadingText().equals("CLOTHING")
                && titleMobileComponent.titleSubListText().equals("TOPS");
    }
}
