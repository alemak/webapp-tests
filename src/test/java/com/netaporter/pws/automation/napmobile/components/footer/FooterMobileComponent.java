package com.netaporter.pws.automation.napmobile.components.footer;

import com.netaporter.pws.automation.napmobile.components.AbstractNapMobilePageComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("cucumber-glue")
public class FooterMobileComponent extends AbstractNapMobilePageComponent {

    /* Page Objects
    --------------------------------------*/
    By bySiteSearch = By.id("site-search");

    By bySiteSearchInput = By.id("search");

    By bySiteSearchButton = By.className("search-button");

    By byMobileFooterNavLinks = By.cssSelector("#mobile-footer-nav ul li a");

    By byFooterBasketQuantity = By.cssSelector("#footer-basket a:nth-child(1)");

    By byFooterBasketLink = By.cssSelector("#footer-basket a:nth-child(2)");

    By byFooterBasketLinkImage = By.cssSelector("#footer-basket a:nth-child(2) img");

    By byMobileFooterCountryLink = By.id("country_select");

    By byMobileFooterCurrency = By.cssSelector("#mobileFooter div:nth-child(1) span:last-child");

    By byMobileFooterLanguageLink = By.cssSelector("#mobileFooter div:nth-child(2) a");

    By byMobileFooterShowUsername = By.id("show-username");

    By byMobileFooterSignOutLink = By.cssSelector("#header-inject-isauth a");

    By byMobileFooterSignInLink = By.cssSelector("#header-inject-notauth a");

    By byMobileFooterMyAccountLink = By.cssSelector("#mobileFooter div:nth-child(3) a");

    By byMobileFooterContactUsLink = By.cssSelector("#mobileFooter div:nth-child(4) a:nth-child(1)");

    By byMobileFooterClassicSiteLink = By.cssSelector("#mobileFooter div:nth-child(4) a:nth-child(2)");

    By byMobileFooterAppsLink = By.cssSelector("#mobileFooter div:nth-child(4) a:nth-child(3)");

    By byMobileFooterPrivacyAndCookiePolicyLink = By.cssSelector("#mobileFooter div:nth-child(5) a");

    By byMobileFooterCopyright = By.cssSelector("#mobileFooter div:nth-child(6)");

    /* Constructor
    --------------------------------------*/
    public FooterMobileComponent() {}

    /* Getters
    --------------------------------------*/
    public By getBySiteSearch() {
        return bySiteSearch;
    }

    public WebElement getSiteSearch() {
        return webBot.getDriver().findElement(getBySiteSearch());
    }

    public By getBySiteSearchInput() {
        return bySiteSearchInput;
    }

    public WebElement getSiteSearchInput() {
        return webBot.getDriver().findElement(getBySiteSearchInput());
    }

    public By getBySiteSearchButton() {
        return bySiteSearchButton;
    }

    public WebElement getSiteSearchButton() {
        return webBot.getDriver().findElement(getBySiteSearchButton());
    }

    public By getByMobileFooterNavLinks() {
        return byMobileFooterNavLinks;
    }

    public List<WebElement> getMobileFooterNavLinks() {
        return webBot.getDriver().findElements(getByMobileFooterNavLinks());
    }

    public By getByFooterBasketQuantity() {
        return byFooterBasketQuantity;
    }

    public WebElement getFooterBasketQuantity() {
        return webBot.getDriver().findElement(getByFooterBasketQuantity());
    }

    public By getByFooterBasketLink() {
        return byFooterBasketLink;
    }

    public WebElement getFooterBasketLink() {
        return webBot.getDriver().findElement(getByFooterBasketLink());
    }

    public By getByFooterBasketLinkImage() {
        return byFooterBasketLinkImage;
    }

    public WebElement getFooterBasketLinkImage() {
        return webBot.getDriver().findElement(getByFooterBasketLinkImage());
    }

    public By getByMobileFooterCountryLink() {
        return byMobileFooterCountryLink;
    }

    public WebElement getMobileFooterCountryLink() {
        return webBot.getDriver().findElement(getByMobileFooterCountryLink());
    }

    public By getByMobileFooterCurrency() {
        return byMobileFooterCurrency;
    }

    public WebElement getMobileFooterCurrency() {
        return webBot.getDriver().findElement(getByMobileFooterCurrency());
    }

    public By getByMobileFooterLanguageLink() {
        return byMobileFooterLanguageLink;
    }

    public WebElement getMobileFooterLanguageLink() {
        return webBot.getDriver().findElement(getByMobileFooterLanguageLink());
    }

    public By getByMobileFooterShowUsername() {
        return byMobileFooterShowUsername;
    }

    public WebElement getMobileFooterShowUsername() {
        return webBot.getDriver().findElement(getByMobileFooterShowUsername());
    }

    public By getByMobileFooterSignOutLink() {
        return byMobileFooterSignOutLink;
    }

    public WebElement getMobileFooterSignOutLink() {
        return webBot.getDriver().findElement(getByMobileFooterSignOutLink());
    }

    public By getByMobileFooterSignInLink() {
        return byMobileFooterSignInLink;
    }

    public WebElement getMobileFooterSignInLink() {
        return webBot.getDriver().findElement(getByMobileFooterSignInLink());
    }

    public By getByMobileFooterMyAccountLink() {
        return byMobileFooterMyAccountLink;
    }

    public WebElement getMobileFooterMyAccountLink() {
        return webBot.getDriver().findElement(getByMobileFooterMyAccountLink());
    }

    public By getByMobileFooterContactUsLink() {
        return byMobileFooterContactUsLink;
    }

    public WebElement getMobileFooterContactUsLink() {
        return webBot.getDriver().findElement(getByMobileFooterContactUsLink());
    }

    public By getByMobileFooterClassicSiteLink() {
        return byMobileFooterClassicSiteLink;
    }

    public WebElement getMobileFooterClassicSiteLink() {
        return webBot.getDriver().findElement(getByMobileFooterClassicSiteLink());
    }

    public By getByMobileFooterAppsLink() {
        return byMobileFooterAppsLink;
    }

    public WebElement getMobileFooterAppsLink() {
        return webBot.getDriver().findElement(getByMobileFooterAppsLink());
    }

    public By getByMobileFooterPrivacyAndCookiePolicyLink() {
        return byMobileFooterPrivacyAndCookiePolicyLink;
    }

    public WebElement getMobileFooterPrivacyAndCookiePolicyLink() {
        return webBot.getDriver().findElement(getByMobileFooterPrivacyAndCookiePolicyLink());
    }

    public By getByMobileFooterCopyright() {
        return byMobileFooterCopyright;
    }

    public WebElement getMobileFooterCopyright() {
        return webBot.getDriver().findElement(getByMobileFooterCopyright());
    }

    /* Actions
    --------------------------------------*/
    public void inputSiteSearchTermAndSubmit(String searchTerm) {
        getSiteSearchInput().sendKeys(searchTerm);
        getSiteSearchButton().submit();
    }
}
