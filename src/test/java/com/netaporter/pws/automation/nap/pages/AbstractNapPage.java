package com.netaporter.pws.automation.nap.pages;

import com.netaporter.pws.automation.nap.components.HeaderComponent;
import com.netaporter.test.utils.pages.AbstractPage;
import com.netaporter.test.utils.pages.driver.WaitTime;
import com.netaporter.test.utils.pages.exceptions.PageElementNotFoundException;
import com.netaporter.test.utils.pages.regionalisation.RegionaliseWithRegionAndCountrySlashLanguage;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.junit.Assert.fail;

/**
 * Base class for pages that are specific to Net-A-Porter.
 * Also for Net-A-Porter specific pages which have shared properties and behaviour.
 */
public abstract class AbstractNapPage extends AbstractPage {

    private By SPORT_TOPNAV_ELEMENT = By.id("sport") ;
    protected By SIGNED_IN_USERNAME_LOCATOR = By.id("user-full-name");
    private By COUNTRY_IN_HEADER_LOCATOR = By.xpath(".//*[@id='account-info']/ul[1]/li[1]/a");
    private By COUNTRY_HEADER_LOCATOR = By.className("country-name");  //new country header element
    protected By SEARCH_BOX_LOCATOR = By.id("search");
    protected By SEARCH_BUTTON_LOCATOR = By.className("magnify");
    public By ERROR = By.cssSelector(".error");
    private By EMAIL_SIGNUP_POPUP = By.className("email_signup_popup_close");
    private By HEADER_SIGN_IN_LOCATOR = By.cssSelector("#header-sign-in");
    private By CLOSE_APAC_WELCOME_MESSAGE_BUTTON_ELEMENT = By.cssSelector(".locale-message-content .close");
    private By SALE_TOPNAV_ELEMENT = By.id("sale-btn");

    @Autowired
    private HeaderComponent headerComponent;

    @PostConstruct
    public void addDisableForseeCookie() {
        webBot.addCookie("fsr.s", "%7B%22v2%22%3A-2%2C%22v1%22%3A-1%2C%22rid%22%3A%22d1159f3-80328478-b722-5d0d-09874%22%2C%22to%22%3A2.2%2C%22c%22%3A%22http%3A%2F%2Freltesta.dave.net-a-porter.com%2F%22%2C%22pv%22%3A6%2C%22lc%22%3A%7B%22d0%22%3A%7B%22v%22%3A6%2C%22s%22%3Atrue%2C%22e%22%3A1%7D%7D%2C%22cd%22%3A0%2C%22cp%22%3A%7B%22Country%22%3A%22GB%22%2C%22Language%22%3A%22en%22%2C%22Region%22%3A%22intl%22%7D%2C%22f%22%3A1380809884032%2C%22sd%22%3A0%2C%22l%22%3A%22en%22%2C%22i%22%3A-1%7D");
    }

    @PostConstruct
    public void addDisableTopNavDemoFeature() {
        webBot.addCookie("topNavDemo", "false");
    }

    public AbstractNapPage(String pageName, String path) {
        super(pageName, path);
        setRegionalisePathBehavior(new RegionaliseWithRegionAndCountrySlashLanguage());
    }

    public AbstractNapPage(String pageName, String path, List<String> params) {
        super(pageName, path, params);
    }

    public String getCountryName() {
        return webBot.getElementText(COUNTRY_IN_HEADER_LOCATOR);
    }

    public String getCountryNameResp() {
        return webBot.getElementText(COUNTRY_HEADER_LOCATOR);
    }

    public String getLanguage(){
        return webBot.getLanguage();
    }
    public String getLanguageName() {
        WebElement languageSelect = webBot.findElement(By.xpath(".//*[@id='account-info']/ul[1]/li[3]/a"));
        return languageSelect.getText();
    }

    public boolean isSignedIn() {
        return !webBot.findElement(HEADER_SIGN_IN_LOCATOR, WaitTime.FOUR).isDisplayed();
    }

	public String getSignedInUserName() throws Throwable {
		Assert.assertTrue(isSignedIn());
        //wait before trying to get the username, to make sure it is displayed on the page
        Thread.sleep(500);
		return webBot.findElement(SIGNED_IN_USERNAME_LOCATOR, WaitTime.FOUR).getText();
	}

    public void signOut() throws Throwable {
        headerComponent.clickSignOut();
    }

    protected boolean doesElementExist(By element) {
        try{
            WebElement webElement = webBot.findElement(element);
            return webElement!=null;
        }catch (PageElementNotFoundException e){
            return false;
        }
    }

    public void search(String keyword) {
        webBot.type(SEARCH_BOX_LOCATOR, keyword);
        webBot.click(SEARCH_BUTTON_LOCATOR);
    }

	public void closeDontMissOutPopup() {
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement dontMissOutPopupElement;
        try {
            dontMissOutPopupElement = webBot.findElement(EMAIL_SIGNUP_POPUP, WaitTime.TWO);
        }
        catch (PageElementNotFoundException e) {
            //element has disappeared, skip the rest
            return;
        }

        try {
            webBot.executeScript("arguments[0].scrollIntoView(true);", dontMissOutPopupElement);
            dontMissOutPopupElement.click();
        }
        catch (WebDriverException e) {
            try {
                //popup sometimes needs a while to crawl into its final position on the page
                Thread.sleep(700);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            webBot.executeScript("arguments[0].scrollIntoView(true);", dontMissOutPopupElement);
            dontMissOutPopupElement.click();
        }
        try {
            webBot.waitExplicitly(WaitTime.TWO);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void closeApacWelcomeMessage(){
        WebElement closePopupButton;
        try{
            closePopupButton = webBot.findElement(CLOSE_APAC_WELCOME_MESSAGE_BUTTON_ELEMENT, WaitTime.FOUR);
            closePopupButton.click();
        }
        catch ( PageElementNotFoundException e)
        {
            System.out.println("There was no apac welcome message to close, or the locator has changed");
        }
        catch ( ElementNotVisibleException ignored)
        {

        }
        //added sleep to take into account the time it takes for the popup to disappear
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
    Waits for a specific element to exist for up to 10 seconds (e.g. searchType=By.cssSelector(".popup-window"))
     */
    public void waitForElementToExist(By searchType) throws Throwable{
        System.out.println("Waiting for element to exist "+searchType);
        for(int i = 0; i <= 100 ; i++) {
            if (webBot.exists(null, searchType)) {
                System.out.println("Found");
                return;
            } else {
                Thread.sleep(100);
            }
            System.out.print(".");
        }
        fail("Could not find element: "+searchType);
    }

    /*
    Waits for a specific element to not exist for up to 10 seconds (e.g. searchType=By.cssSelector(".popup-window"))
     */
    public void waitForElementToNotExist(By searchType) throws Throwable{
        System.out.println("Waiting for element to not exist "+searchType);
        for(int i = 0; i <= 100 ; i++) {
            if (!(webBot.exists(null, searchType))) {
                System.out.println();
                return;
            } else {
                Thread.sleep(100);
            }
            System.out.print(".");
        }
        fail("By did not fail to exist: "+searchType);
    }

    /*
    Waits for a specific element to be visible for up to 10 seconds
     */
    public void waitForElementToBeVisible(String cssSelector) throws Throwable{
        // Fail fast if the element doesn't exist
        if (!webBot.exists(null, By.cssSelector(cssSelector))) {
            fail("Could not determine if element is visible as it does not exist: " + cssSelector);
        }
        long startTime = System.currentTimeMillis();

        System.out.println("Waiting for element to appear "+cssSelector);
        for(int i = 0; i <= 100 ; i++) {
            // find it each time to prevent selenium reference going stale
            WebElement e = webBot.findElement(By.cssSelector(cssSelector));
            if (e.isDisplayed()) {
                System.out.println("By is visible");
                return;
            } else {
                Thread.sleep(100);
            }
            System.out.print(".");
        }
        long estimatedTime = System.currentTimeMillis() - startTime;
        fail(cssSelector+" - did not become visible after "+estimatedTime+" milliseconds");
    }

    /*
    Waits for a specific element to not be visible for up to 10 seconds
    */
    public void waitForElementToNotBeVisible(String cssSelector) throws Throwable{
        // Return fast if the element doesn't exist
        if (!webBot.exists(null, By.cssSelector(cssSelector))) {
            return;
        }

        System.out.println("Waiting for element to disappear "+cssSelector);
        for(int i = 0; i <= 100 ; i++) {
            // find it each time to prevent selenium reference going stale
            WebElement e = webBot.findElement(By.cssSelector(cssSelector));
            if (!e.isDisplayed()) {
                System.out.println("By is not visible");
                return;
            } else {
                Thread.sleep(100);
            }
            System.out.print(".");
        }
        fail("By did not become invisible: "+cssSelector);
    }

    //todo: move this from the AbstractPage as this does not appear on all pages (only home page and listing pages)
    public void clickSaleTopNavLink() {
        webBot.click(SALE_TOPNAV_ELEMENT);
    }

    public void clickShoppingBagIcon() {
        webBot.click(By.id("header-shopping-bag"));
    }

    public void clickShoppingBagIconResp() {
        webBot.click(By.cssSelector(".shopping-basket a"));
    }

    public void clickSportTopNavLink() {
        webBot.click(SPORT_TOPNAV_ELEMENT);

    }

    public String getAnalyticEventTag(){
        String events =(String)((JavascriptExecutor)webBot.getDriver()).executeScript("return s.events;");
        return events;
    }

    public String getAnalyticProductTag(){
        String productsString =(String)((JavascriptExecutor)webBot.getDriver()).executeScript("return s.products;");
        String[] parts = productsString.split(";");
        String productId = parts[1];
        return productId;
    }

    public String getAnalyticPageTypeTag(){
        String pageType =(String)((JavascriptExecutor)webBot.getDriver()).executeScript("return s.prop5;");
        return pageType;
    }

    public String getAnalyticPageNameTag(){
        String pageName =(String)((JavascriptExecutor)webBot.getDriver()).executeScript("return s.pageName;");
        return pageName;
    }

    public String getAnalyticDesignerNameTag(){
        String designerName =(String)((JavascriptExecutor)webBot.getDriver()).executeScript("return s.prop1;");
        return designerName;
    }

    public String getAnalyticTitleTag(){
        String name =(String)((JavascriptExecutor)webBot.getDriver()).executeScript("return s.prop4;");
        return name;
    }

    public String getAnalyticDeviceTag(){
        String device =(String)((JavascriptExecutor)webBot.getDriver()).executeScript("return s.prop30;");
        return device;
    }

    public String getAnalyticProductFindingTag(){
        String productFindingTag =(String)((JavascriptExecutor)webBot.getDriver()).executeScript("return s.eVar13;");
        return productFindingTag;
    }

    public String getAnalyticNavLevel1(){
        String NavigationLevel1 =(String)((JavascriptExecutor)webBot.getDriver()).executeScript("return s.Departments;");
        return NavigationLevel1;
    }

    public String getAnalyticNavLevel2(){
        String NavigationLevel2 =(String)((JavascriptExecutor)webBot.getDriver()).executeScript("return s.prop3;");
        return NavigationLevel2;
    }

    public String getAnalyticNavLevel3(){
        String NavigationLevel3 =(String)((JavascriptExecutor)webBot.getDriver()).executeScript("return s.prop13;");
        return NavigationLevel3;
    }

    public String getAnalyticSizeStockLowLevelTag(){
        String SizeStockTag =(String)((JavascriptExecutor)webBot.getDriver()).executeScript("return s.eVar46;");
        return SizeStockTag;
    }

    public String getAnalyticSizeStockHighLevelTag(){
        String SizeStockTag =(String)((JavascriptExecutor)webBot.getDriver()).executeScript("return s.eVar47;");
        return SizeStockTag;
    }

    public String getAnalyticRegionTag(){
        String regionTag =(String)((JavascriptExecutor)webBot.getDriver()).executeScript("return s.prop20;");
        return regionTag;
    }

    public String getAnalyticCountryTag(){
        String countryTag =(String)((JavascriptExecutor)webBot.getDriver()).executeScript("return s.eVar21;");
        return countryTag;
    }

    public String getAnalyticLanguageTag(){
        String languageTag =(String)((JavascriptExecutor)webBot.getDriver()).executeScript("return s.prop21;");
        return languageTag;
    }



}