package com.netaporter.pws.automation.nap.components;

import com.google.common.base.Predicate;
import com.netaporter.pws.automation.shared.utils.WaitUtil;
import com.netaporter.test.utils.pages.component.AbstractPageComponent;
import com.netaporter.test.utils.pages.exceptions.PageElementNotFoundException;
import org.openqa.selenium.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.testng.AssertJUnit.assertTrue;

/**
 * The header found at the top of all the pages
 */
@Component
@Scope("cucumber-glue")
public class HeaderComponent extends AbstractPageComponent {

	private static final By FRAME_NAME = By.name("signInFrame");
    private static final By SIGN_OUT = By.xpath("//ul[@id='dd_links_holder']/li[2]/a");

	private static final By SHOW_OPTIONS = By.xpath("//li[@id='account-dropdown']/div[3]");

    private By ACCOUNT_DROPDOWN = By.className("acc_dd_click_area");

    public static final By ELEMENT_HEADER_WISHLIST_LINK = By.cssSelector("#wish-list-link");

    private static final By WISH_LIST_ALERT = By.xpath(".//*[@id='wish_list_alert']");
    private static final By WISH_LIST_ALERT_MESSAGE = By.xpath(".//*[@id='alerts-dropdown-inner']/h3");
    private static final By WISHLIST_ALERT_BUBBLE = By.id("bubble");
    private static final By HEADER_SIGN_IN = By.cssSelector("LI#header-sign-in");

    private static final By CHANGE_LOCATION = By.cssSelector("li.country-select a");
    private static final By CHANGE_COUNTRY = By.className("country-name");  //new change selection element

    private static final By CHANGE_LANGUAGE = By.cssSelector("#header .country-select .country_select");

    private static final By LOGO_LOCATOR = By.cssSelector(".logo a");
    private static final By STRAPLINE_LOCATOR = By.cssSelector(".logo-strapline .strapline");

    private static final By SITE_PREFERENCES_LOCATOR = By.cssSelector(".site-preferences");
    private static final By COUNTRY_CODE_LOCATOR = By.cssSelector(".country-name");
    private static final By CURRENCY_CODE_LOCATOR = By.cssSelector(".currency-code span:nth-child(2)");
    private static final By CUSTOMER_CARE_LOCATOR = By.className("customer-care");

    public void getFocus() {
        webBot.switchToIFrame(FRAME_NAME);
    }

    public void clickSignOut() throws Throwable {
        webBot.click(ACCOUNT_DROPDOWN);
        try {
        waitSecondsForElementToBeVisible(SIGN_OUT, 5); }
        catch (TimeoutException e) {
            webBot.click(ACCOUNT_DROPDOWN);
        }
		webBot.click(SIGN_OUT);

        waitSecondsForElementToBeVisible(HEADER_SIGN_IN, 30);
    }

    public void clickWishListAlert(){
        webBot.findElement(WISH_LIST_ALERT).click();
    }

    public boolean isWishListAlertEmpty() {
        WebElement emptyWishListAlertMessage = webBot.findElement(WISH_LIST_ALERT_MESSAGE);
        boolean isWishListAlertEmpty = emptyWishListAlertMessage.getText().contains("0 new Wish list");

        return isWishListAlertEmpty;
    }

    public boolean isWishListAlertLowInStock() {
        WebElement lowInStockWishListAlertMessage = webBot.findElement(WISH_LIST_ALERT_MESSAGE);

        boolean isWishListAlertLowInStock = lowInStockWishListAlertMessage.getText().contains("1 NEW Wish list");

        return isWishListAlertLowInStock;
    }

    public boolean isHeaderWishlistLinkVisible() throws Throwable {
        WebElement headerWishListLInk = webBot.findElements(ELEMENT_HEADER_WISHLIST_LINK).get(0);

        int i=0;
        while (!headerWishListLInk.isDisplayed() && i < 10) {
            i++;
            Thread.sleep(1000);
            headerWishListLInk = webBot.findElements(ELEMENT_HEADER_WISHLIST_LINK).get(0);
        }

        return headerWishListLInk.isDisplayed() ;
    }

    public boolean isWishListAlertDisplayed() {
        try{
            webBot.findElement(WISHLIST_ALERT_BUBBLE);
        }
        catch (PageElementNotFoundException wishListAlertDisplayed){
            return false;
        }
        return true;
    }

    public void hoverOverWishListAlertBuble() {
        try{
            WebElement bubble = webBot.findElement(WISHLIST_ALERT_BUBBLE);
            webBot.mouseOver(bubble);
        }
        catch(PageElementNotFoundException exception)
        {
            fail("Wishlist bubble was not displayed, so can't hover over it. Could be that the product stock is different between prodserv and webapp db.");
        }


        WaitUtil.waitFor(1000);
    }

    public void clickWishlistLinkOnDropdown() {
        webBot.findElement(By.className("view-wishlist")).click();
    }


    public void clickHeaderWishlistLink() {
        waitForElementToBeVisible(ELEMENT_HEADER_WISHLIST_LINK);

        // This is dynamically injected, so may be detatched from DOM, so retry
        for (int i=0; i<20; i++){
            try {
                webBot.findElement(ELEMENT_HEADER_WISHLIST_LINK).click();
                return;
            } catch (StaleElementReferenceException e) {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e1) {
                }
            }
        }
    }

    public boolean isWishListAlertPresent() {
        return webBot.isElementPresent(WISHLIST_ALERT_BUBBLE, 5);
    }

    public void clickChangeCountryLink() {
        webBot.click(CHANGE_LOCATION);
    }

    public void clickChangeCountryLinkResp() {
        webBot.click(CHANGE_COUNTRY);
    }

    public void clickChangeLanguageLink() {
        webBot.findElement(CHANGE_LANGUAGE).click();
    }

    //todo these should be made available in testing utils
    public void waitForElementToBeVisible(By locator) {
        waitSecondsForElementToBeVisible(locator, 30);
    }

    public void waitSecondsForElementToBeVisible(By locator, int maxSeconds) {
        webBot.waitUntil(elementDisplayed(locator), maxSeconds);
    }

    private Predicate<WebDriver> elementDisplayed(final By locator) {
        return new Predicate<WebDriver>() {
            @Override public boolean apply(WebDriver driver) {
                return webBot.findElement(locator).isDisplayed();
            }
        };
    }

    public void clickLogo() {
        webBot.findElement(LOGO_LOCATOR).click();
    }

    public void verifyLogoIsVisible() {
        webBot.findElement(LOGO_LOCATOR);
    }

    public void verifyStraplineVisibility(Boolean visible) {
        assertEquals("Strapline visibility is incorrect", visible, webBot.findElement(STRAPLINE_LOCATOR).isDisplayed());
    }

    public void verifyCountryIsCorrect(String country) {
        assertEquals("Country Code is incorrect", country, webBot.findElement(COUNTRY_CODE_LOCATOR).getText());
    }

    public void verifyCurrencyIsCorrect(String currency) {
        assertEquals("Currency code is incorrect", currency, webBot.getElement(CURRENCY_CODE_LOCATOR).getText());
    }

    public void verifyCountryCurrencyVisibility(Boolean visible) {
        assertEquals("Country and currency visibility is incorrect", visible, webBot.getElement(SITE_PREFERENCES_LOCATOR).isDisplayed());
    }

    public void verifyCustomerCareNumber(String phoneNumber) {
        String[] customerCareNumber = webBot.findElement(CUSTOMER_CARE_LOCATOR).getText().split(":");
        assertTrue("Actual customer care number displayed was:" + customerCareNumber[1] + " but expected was: " +phoneNumber,customerCareNumber[1].trim().equals(phoneNumber));
    }
}