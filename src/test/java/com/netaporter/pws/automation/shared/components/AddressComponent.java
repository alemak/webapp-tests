package com.netaporter.pws.automation.shared.components;

import com.google.common.base.Predicate;
import com.netaporter.pws.automation.shared.pojos.Address;
import com.netaporter.test.utils.pages.component.AbstractPageComponent;
import com.netaporter.test.utils.pages.driver.WaitTime;
import com.netaporter.test.utils.pages.exceptions.PageElementNotFoundException;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("cucumber-glue")
public class AddressComponent extends AbstractPageComponent {

    public static final By TITLE_SELECT = By.id("titleSelect");
    public static final By FIRST_NAME = By.id("address.firstName");
    public static final By LAST_NAME = By.id("address.lastName");
    public static final By ADDRESS_1 = By.id("address.address1");
    public static final By ADDRESS_2 = By.id("address.address2");
    public static final By TOWN_CITY = By.id("address.towncity");
    public static final By PROVINCE_TERRITORY_COUNTY = By.name("address.county");
    public static final By PROVINCE_TERRITORY_STATE = By.name("address.state");
    public static final By AREA = By.id("state");
    public static final By POSTCODE_LOCATOR = By.id("address.postcode");
    public static final By DAY_PHONE = By.id("address.work");
    public static final By EVENING_PHONE = By.id("eveningphone");
    public static final By COUNTRY_SELECT = By.name("address.countryLookup");
    public static final By CONTINUE = By.name("_eventId_proceedToPurchase");
    public static final By BILLING_DIFF = By.id("billing_diff");
    public static final By VALIDATION_ERROR_ELEMENT = By.className("error");
    public static final By SUBMIT = By.name("saveAddress");

    public void fillInAddress(Address napAddress) {

        webBot.type(FIRST_NAME, "myFirstName");
        webBot.type(LAST_NAME, "myLastName");
        webBot.selectElementText(COUNTRY_SELECT, napAddress.getCountry());

        waitUntilPageIsFullyRefreshedAfterSelectingItemFromDropDown();

        webBot.type(ADDRESS_1, napAddress.getAddress1());
        webBot.type(ADDRESS_2, napAddress.getAddress2());

        if (StringUtils.isNotBlank(napAddress.getProvinceOrTerritoryOrState())) {
            webBot.selectElementText(PROVINCE_TERRITORY_STATE, napAddress.getProvinceOrTerritoryOrState());

            waitUntilPageIsFullyRefreshedAfterSelectingItemFromDropDown();
        }

        if (StringUtils.equalsIgnoreCase(napAddress.getCountry(), "Hong Kong")) {
            if (StringUtils.isNotBlank(napAddress.getArea())) {
                webBot.selectByText(AREA, napAddress.getArea());

                waitUntilPageIsFullyRefreshedAfterSelectingItemFromDropDown();
            }
        }
        else {
            webBot.type(TOWN_CITY, napAddress.getTownOrCity());
            webBot.type(POSTCODE_LOCATOR, napAddress.getPostcode());
        }
        webBot.type(DAY_PHONE, napAddress.getDaytimePhoneNumber());
    }

    private void waitUntilPageIsFullyRefreshedAfterSelectingItemFromDropDown() {
        waitForElementToBeVisible(FIRST_NAME);
        waitForElementToBeVisible(LAST_NAME);
        waitForElementToBeVisible(COUNTRY_SELECT);
        waitForElementToBeVisible(ADDRESS_1);
        waitForElementToBeVisible(ADDRESS_2);
        waitForElementToBeVisible(DAY_PHONE);
    }

    public void completeFirstName(String firstName) {
        webBot.type(AddressComponent.FIRST_NAME, firstName);
    }

    public void completeLastName(String lastName) {
        webBot.type(AddressComponent.LAST_NAME, lastName);
    }

    public void selectCountry(String country) {
        webBot.selectElementText(AddressComponent.COUNTRY_SELECT, country);
    }

    public void completeAddressLine1(String addressLine1) {
        webBot.type(AddressComponent.ADDRESS_1, addressLine1);
    }

    public void completeAddressLine2(String addressLine2) {
        webBot.type(AddressComponent.ADDRESS_2, addressLine2);
    }

    public void completeTown(String town) {
        webBot.type(AddressComponent.TOWN_CITY, town);
    }

    public void completeCountyProvince(String countyProvince) {
        webBot.setText(AddressComponent.PROVINCE_TERRITORY_COUNTY, countyProvince);
    }

    public void completePostCode(String postcode) {
        webBot.type(AddressComponent.POSTCODE_LOCATOR, postcode);
    }

    public void completeTelephoneNumber(String telephoneNumber) {
        webBot.type(AddressComponent.DAY_PHONE, telephoneNumber);
    }

    public void completeMobileNumber(String mobileNumber) {
        webBot.type(AddressComponent.EVENING_PHONE, mobileNumber);
    }

    public void selectState(String state) {
        webBot.selectElementText(AddressComponent.PROVINCE_TERRITORY_STATE, state);
    }

    public String getSelectedState() {
        return webBot.getSelectElement(AddressComponent.PROVINCE_TERRITORY_STATE).getFirstSelectedOption().getText().trim();
    }

    public String getStateLabel() {
        WebElement e;
        try {
            e = webBot.findElement(By.xpath("//label[@for='county']"), WaitTime.THREE);
        } catch(PageElementNotFoundException exp) {
            e = webBot.findElement(By.xpath("//label[@for='state']"), WaitTime.THREE);
        }

        return e.getText().replace('*',' ').trim();
    }

    public WebElement getSMSMessage() {
        WebElement dayPhoneDiv = webBot.findElement(By.xpath("//label[@for='dayphone']/ancestor::div[1]"));
        WebElement smsMessage = ((RemoteWebElement) dayPhoneDiv).findElementByXPath(".//span[@class='additional_info']");
        return smsMessage;
    }


    public WebElement getPostcodeLabel() {
        return webBot.findElement(By.xpath("//label[@for='postcode']"));
    }

    public WebElement getPostcodeField() {
        return webBot.findElement(POSTCODE_LOCATOR, WaitTime.FOUR);
    }

   public WebElement getStateField() {
        return webBot.findElement(PROVINCE_TERRITORY_STATE, WaitTime.FOUR);
    }

   public WebElement getCountyField() {
        return webBot.findElement(PROVINCE_TERRITORY_COUNTY, WaitTime.FOUR);
    }

    public WebElement getTownField() {
        return webBot.findElement(TOWN_CITY, WaitTime.FOUR);
    }

    public void clickContinue(){
        webBot.click(AddressComponent.CONTINUE);
    }

    public WebElement getFirstname() {
        return webBot.findElement(FIRST_NAME, WaitTime.FOUR);
    }

    public WebElement getLastname() {
        return webBot.findElement(LAST_NAME, WaitTime.FOUR);
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

}