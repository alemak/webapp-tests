package com.netaporter.pws.automation.nap.pages;

import org.openqa.selenium.By;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * User: a.mosincat
 * Date: 24/06/14
 */
@Component
@Scope("cucumber-glue")
public class NAPSportLandingPage extends AbstractNapPage {


    private static final By GYMCROSSFIT_ELEMENT_KEY = By.xpath("html/body/div[2]/div[3]/div[2]/div[3]/a/img");
    private static final By RUN_ELEMENT_KEY = By.xpath("html/body/div[2]/div[3]/div[2]/div[2]/a/img");
    private static final By YOGADANCE_ELEMENT_KEY = By.xpath("html/body/div[2]/div[3]/div[2]/div[4]/a/img");
    private static final By TENNIS_ELEMENT_KEY = By.xpath("html/body/div[2]/div[3]/div[2]/div[6]/a/div/span");
    private static final By SWIMSURF_ELEMENT_KEY = By.xpath("html/body/div[2]/div[3]/div[2]/div[8]/a/img");
    private static final By EQUESTRIAN_ELEMENT_KEY = By.xpath("html/body/div[2]/div[3]/div[2]/div[7]/a/img");
    private static final By APRES_ELEMENT_KEY = By.xpath("html/body/div[2]/div[3]/div[2]/div[10]/a/img");
    private static final By GOLF_ELEMENT_KEY = By.xpath("html/body/div[2]/div[3]/div[2]/div[9]/a/img");
    private static final By ACCESSORIES_ELEMENT_KEY = By.xpath("html/body/div[2]/div[3]/div[2]/div[12]/a/img");
    private static final By OUTDOOR_ELEMENT_KEY = By.xpath("html/body/div[2]/div[3]/div[2]/div[11]/a/img");
    private static final By SAILING_ELEMENT_KEY = By.xpath("html/body/div[2]/div[3]/div[2]/div[5]/a/img");
    private static final By SKI_ELEMENT_KEY = By.xpath("html/body/div[2]/div[3]/div[2]/div[1]/a/img");
    private static final By ALLSPORTSWEAR_ELEMENT_KEY = By.xpath("html/body/div[2]/div[3]/div[1]/div/div/a");

    public NAPSportLandingPage() {
        super("NAP Sport Landing Page", "Shop/Sport");
    }

    @Override
    public boolean isPageRegionalised() {
        return false;
    }

    public NAPAWSListingPage clickLinkAndReturnPage(String link) {
        if ("Gym/Crossfit".equalsIgnoreCase(link))
            clickIfElementIsVisibleInLandingPage(GYMCROSSFIT_ELEMENT_KEY);
        else if ("Run".equalsIgnoreCase(link))
            clickIfElementIsVisibleInLandingPage(RUN_ELEMENT_KEY);
        else if ("Yoga/Dance".equalsIgnoreCase(link))
            clickIfElementIsVisibleInLandingPage(YOGADANCE_ELEMENT_KEY);
        else if ("Tennis".equalsIgnoreCase(link))
            clickIfElementIsVisibleInLandingPage(TENNIS_ELEMENT_KEY);
        else if ("Swim/Surf".equalsIgnoreCase(link))
            clickIfElementIsVisibleInLandingPage(SWIMSURF_ELEMENT_KEY);
        else if ("Equestrian".equalsIgnoreCase(link))
            clickIfElementIsVisibleInLandingPage(EQUESTRIAN_ELEMENT_KEY);
        else if ("Après".equalsIgnoreCase(link))
            clickIfElementIsVisibleInLandingPage(APRES_ELEMENT_KEY);
        else if ("Golf".equalsIgnoreCase(link))
            clickIfElementIsVisibleInLandingPage(GOLF_ELEMENT_KEY);
        else if ("Accessories".equalsIgnoreCase(link))
            clickIfElementIsVisibleInLandingPage(ACCESSORIES_ELEMENT_KEY);
        else if ("Outdoor".equalsIgnoreCase(link))
            clickIfElementIsVisibleInLandingPage(OUTDOOR_ELEMENT_KEY);
        else if ("Sailing".equalsIgnoreCase(link))
            clickIfElementIsVisibleInLandingPage(SAILING_ELEMENT_KEY);
        else if ("Ski".equalsIgnoreCase(link))
            clickIfElementIsVisibleInLandingPage(SKI_ELEMENT_KEY);
        else if ("All_Sportswear".equalsIgnoreCase(link))
            clickIfElementIsVisibleInLandingPage(ALLSPORTSWEAR_ELEMENT_KEY);
        else
            throw new UnsupportedOperationException("Invalid sport subcategory specified: "+link);

        NAPAWSListingPage NAPAWSListingPage = new NAPAWSListingPage(link+" Sport Listing Page", "/"+link);
        NAPAWSListingPage.setWebBot(webBot);
        return NAPAWSListingPage;
    }

    protected boolean clickIfElementIsVisibleInLandingPage(final By element) {
        if (!webBot.isElementPresent(element,3))
            throw new UnsupportedOperationException("Waited for 3 seconds but element "+element+" was not visible to click");
        webBot.findElement(element).click();
        return true;
    }

}
