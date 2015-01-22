package com.netaporter.pws.automation.napmobile.pages.whatsnew;

import com.netaporter.pws.automation.napmobile.pages.AbstractNapMobilePage;
import com.netaporter.test.utils.pages.regionalisation.RegionaliseWithRegionName;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class WhatsNewMobilePage extends AbstractNapMobilePage {

    private static final String NAME = "What's New Mobile";
    private static final String PATH = "Shop/Whats-New";

    /* Page Objects
    --------------------------------------*/

    /* Constructor
    --------------------------------------*/
    public WhatsNewMobilePage() {
        super(NAME, PATH);
        setRegionalisePathBehavior(new RegionaliseWithRegionName());
    }

    /* Getters
    --------------------------------------*/

    /* Helpers
    --------------------------------------*/
    public boolean at() {
        return titleMobileComponent.titleMainHeadingText().equals("WHAT'S NEW");
    }
}
