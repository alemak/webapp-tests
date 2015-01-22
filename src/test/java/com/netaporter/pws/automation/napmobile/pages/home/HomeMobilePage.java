package com.netaporter.pws.automation.napmobile.pages.home;

import com.netaporter.pws.automation.napmobile.pages.AbstractNapMobilePage;
import com.netaporter.test.utils.pages.regionalisation.RegionaliseWithRegionName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@Scope("cucumber-glue")
public class HomeMobilePage extends AbstractNapMobilePage {

    private static final String NAME = "Home Mobile";
    private static final String PATH = "home.nap";

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
    public HomeMobilePage() {
        super(NAME, PATH);
        setRegionalisePathBehavior(new RegionaliseWithRegionName());
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
}
