package com.netaporter.pws.automation.napmobile.pages.sporter;

import com.netaporter.pws.automation.napmobile.pages.AbstractNapMobilePage;
import com.netaporter.test.utils.pages.regionalisation.RegionaliseWithRegionName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("cucumber-glue")
public class NapSportLandingMobilePage extends AbstractNapMobilePage {

    private static final String NAME = "Sport Mobile";
    private static final String PATH = "/Shop/Sport";
    private static final By GOLF_MOBILE_ELEMENT_KEY =By.xpath("html/body/div[1]/div/div[3]/div[2]/div[8]/a/img") ;
    private static final By RUN_MOBILE_ELEMENT_KEY = By.xpath("html/body/div[1]/div/div[3]/div[2]/div[2]/a/img");
    private static final By GYMCROSSFIT_MOBILE_ELEMENT_KEY = By.xpath("html/body/div[1]/div/div[3]/div[2]/div[1]/a/img");
    private static final By YOGADANCE_MOBILE_ELEMENT_KEY = By.xpath("html/body/div[1]/div/div[3]/div[2]/div[3]/a/img");
    private static final By TENNIS_MOBILE_ELEMENT_KEY = By.xpath("html/body/div[1]/div/div[3]/div[2]/div[4]/a/img");
    private static final By EQUESTRIAN_MOBILE_ELEMENT_KEY = By.xpath("html/body/div[1]/div/div[3]/div[2]/div[6]/a/img");
    private static final By ACCESSORIES_MOBILE_ELEMENT_KEY = By.xpath("html/body/div[1]/div/div[3]/div[2]/div[9]/a/img");
    private static final By APRES_MOBILE_ELEMENT_KEY = By.xpath("html/body/div[1]/div/div[3]/div[2]/div[7]/a/img");
    private static final By OUTDOOR_MOBILE_ELEMENT_KEY = By.xpath("html/body/div[1]/div/div[3]/div[2]/div[10]/a/img");
    private static final By SAILING_MOBILE_ELEMENT_KEY = By.xpath("html/body/div[1]/div/div[3]/div[2]/div[11]/a/div[1]");
    private static final By SKI_MOBILE_ELEMENT_KEY = By.xpath("html/body/div[1]/div/div[3]/div[2]/div[12]/a/div[1]");
    private static final By ALLSPORTSWEAR_MOBILE_ELEMENT_KEY = By.xpath("html/body/div[1]/div/div[3]/div[1]/div/div/a");


    /* Page Objects
    --------------------------------------*/
    By byAdTopLink = By.cssSelector(".ad-top a");

    By byAdTopLinkImage = By.cssSelector(".ad-top a img");

    By byHomeLinks = By.className("home-links");

    By byShopListLinks = By.cssSelector(".shop-list li a");

    By byAdSpaceLinks = By.cssSelector(".ad-space a");

    By byAdSpaceLinkImages = By.cssSelector(".ad-space a img");

    /* Constructor
    --------------------------------------*/
    public NapSportLandingMobilePage() {
        super(NAME, PATH);
        setRegionalisePathBehavior(new RegionaliseWithRegionName());
    }


    @Override
    public boolean isPageRegionalised() {
        return false;
    }




    /* Getters
    --------------------------------------*/
    public By getByAdTopLink() {
        return byAdTopLink;
    }
    public WebElement getAdTopLink() {
        return webBot.getDriver().findElement(getByAdTopLink());
    }

    public By getByAdTopLinkImage() {
        return byAdTopLinkImage;
    }

    public WebElement getAdTopLinkImage() {
        return webBot.getDriver().findElement(getByAdTopLinkImage());
    }

    public By getByHomeLinks() {
        return byHomeLinks;
    }

    public WebElement getHomeLinks() {
        return webBot.getDriver().findElement(getByHomeLinks());
    }

    public By getByShopListLinks() {
        return byShopListLinks;
    }

    public List<WebElement> getShopListLinks() {
        return webBot.getDriver().findElements(getByShopListLinks());
    }

    By getByAdSpaceLinks() {
        return byAdSpaceLinks;
    }

    public List<WebElement> getAdSpaceLinks() {
        return webBot.getDriver().findElements(getByAdSpaceLinks());
    }

    By getByAdSpaceLinkImages() {
        return byAdSpaceLinkImages;
    }

    public List<WebElement> getAdSpaceLinkImages() {
        return webBot.getDriver().findElements(getByAdSpaceLinkImages());
    }

    /* Helpers
    --------------------------------------*/
    public boolean at() {
        return getHomeLinks().isDisplayed();
    }

    public NapSportListingMobilePage clickAndReturnPage(String link) {
        if ("Golf".equalsIgnoreCase(link))
            clickIfElementIsVisibleInLandingPage(GOLF_MOBILE_ELEMENT_KEY);
        else if ("Run".equalsIgnoreCase(link))
            clickIfElementIsVisibleInLandingPage(RUN_MOBILE_ELEMENT_KEY);
        else if ("Gym/Crossfit".equalsIgnoreCase(link))
            clickIfElementIsVisibleInLandingPage(GYMCROSSFIT_MOBILE_ELEMENT_KEY);
        else if ("Yoga/Dance".equalsIgnoreCase(link))
            clickIfElementIsVisibleInLandingPage(YOGADANCE_MOBILE_ELEMENT_KEY);
        else if ("Tennis".equalsIgnoreCase(link))
            clickIfElementIsVisibleInLandingPage(TENNIS_MOBILE_ELEMENT_KEY);
        else if ("Equestrian".equalsIgnoreCase(link))
            clickIfElementIsVisibleInLandingPage(EQUESTRIAN_MOBILE_ELEMENT_KEY);
        else if ("Accessories".equalsIgnoreCase(link))
            clickIfElementIsVisibleInLandingPage(ACCESSORIES_MOBILE_ELEMENT_KEY);
        else if ("Après".equalsIgnoreCase(link))
            clickIfElementIsVisibleInLandingPage(APRES_MOBILE_ELEMENT_KEY);
        else if ("Outdoor".equalsIgnoreCase(link))
            clickIfElementIsVisibleInLandingPage(OUTDOOR_MOBILE_ELEMENT_KEY);
        else if ("Sailing".equalsIgnoreCase(link))
            clickIfElementIsVisibleInLandingPage(SAILING_MOBILE_ELEMENT_KEY);
        else if ("Ski".equalsIgnoreCase(link))
            clickIfElementIsVisibleInLandingPage(SKI_MOBILE_ELEMENT_KEY);
        else if ("All_Sportswear".equalsIgnoreCase(link))
            clickIfElementIsVisibleInLandingPage(ALLSPORTSWEAR_MOBILE_ELEMENT_KEY);
        else
          throw new UnsupportedOperationException("Invalid mobile sport category specified: "+link);

        NapSportListingMobilePage napSportListingMobilePage = new NapSportListingMobilePage("Sport Listing Page", "/"+link);
        napSportListingMobilePage.setWebBot(webBot);
        return napSportListingMobilePage;

    }

    private boolean clickIfElementIsVisibleInLandingPage(By elementKey) {
        if (!webBot.isElementPresent(elementKey, 3))
            throw new UnsupportedOperationException("Waited for 3 seconds but element " +elementKey+ "was not visible to click");
        webBot.findElement(elementKey).click();
        return true;
    }
}
