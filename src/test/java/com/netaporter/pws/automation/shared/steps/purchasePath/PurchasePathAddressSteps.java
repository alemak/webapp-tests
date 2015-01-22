package com.netaporter.pws.automation.shared.steps.purchasePath;

import com.netaporter.pws.automation.shared.pages.purchasePath.PurchasePathAddressPage;
import com.netaporter.pws.automation.shared.pojos.Address;
import com.netaporter.pws.automation.shared.pojos.Country;
import cucumber.api.java.en.Given;
import org.springframework.stereotype.Component;

@Component
public class PurchasePathAddressSteps extends BasePurchasePathStep {

    public static final String SHIPPING = "shipping";
    public static final String BILLING = "billing";

    @Given("^I click proceed to purchase from the (shipping|billing) address page$")
    public void clickProceedToPurchaseFromAddressPage(String pageName) {
        getAddressPage(pageName).clickProceedToPurchase();
    }

    @Given("^I choose to use a separate billing address$")
    public void selectDifferentBillingAddress() {
        purchasePathShippingAddressPage.setEnterSeperateBillingAddress();
    }

    @Given("^I fill out a UK London (shipping|billing) address$")
    public void fillUKLondonAddress(String pageName) {
        getAddressPage(pageName).completeAddress(Address.createNAPAddress());
    }

    @Given("^I fill out a UK London (shipping|billing) address in (.*)$")
    public void fillUKLondonAddress(String pageName, String language) {
       // need to get a translated copy (for dropdown list) of "United Kingdom" if our language preferences is French, Chinese or German
        Address address = Address.createNAPAddress();
        address.setCountry(Address.getTranslatedUKCountry(language));
        getAddressPage(pageName).completeAddress(address);
    }

    @Given("^I fill out a UK non London (shipping|billing) address$")
    public void fillUKNonLondonAddress(String pageName) {
        getAddressPage(pageName).completeAddress(Address.createUKNonLondonAddress());
    }

    @Given("^I fill out a (shipping|billing) address for country (.*)$")
    public void fillAddressForCountry(String pageName, String countryName) {
        getAddressPage(pageName).completeAddress(Address.createAnotherAddress(countryName));
    }

    @Given("^I fill in the (shipping|billing) address with country (.*) and state (.*) and postcode (.*) in the address form$")
    public void fillSpecificAddress(String pageName, String countryName, String state, String postcode) {
        getAddressPage(pageName).completeAddress(Address.createAnotherAddress(countryName, state, postcode));
    }

    @Given("^I fill out a HK non Premier (shipping|billing) address$")
    public void fillHKNonPremierAddress(String pageName) {
        getAddressPage(pageName).completeAddress(Address.createHKNonPremierAddress());
    }

    @Given("^I fill out a HK Premier (shipping|billing) address$")
    public void fillHKPremierAddress(String pageName) {
        getAddressPage(pageName).completeAddress(Address.createHKPremierAddress());
    }

    @Given("^I fill out a US NY (shipping|billing) address$")
    public void fillUSNYAddress(String pageName) {
        getAddressPage(pageName).completeAddress(Address.createUSPremierAddress());
    }

    @Given("^I fill out a US non NY (shipping|billing) address$")
    public void fillUSNonNYAddress(String pageName) {
        getAddressPage(pageName).completeAddress(Address.createUSNonPremierAddress());
    }

    @Given("^I fill out an invalid US shipping address$")
    public void fillInvalidUSShippingAddress() {
        Address invalidUsAddress = new Address();
        invalidUsAddress.setAddress1("1 US Test Street");
        invalidUsAddress.setAddress2("");
        invalidUsAddress.setTownOrCity("Chicago");
        invalidUsAddress.setProvinceOrTerritoryOrState("Texas");
        invalidUsAddress.setPostcode("1234");
        invalidUsAddress.setDaytimePhoneNumber("555123000");
        if (webBot.getDriver().getCurrentUrl().contains("net-a-porter")){
            invalidUsAddress.setCountry(Address.getTranslatedUSCountry((String)scenarioSession.getData("language")));
        } else {
            invalidUsAddress.setCountry(Country.USA.getName());
        }
        getAddressPage(SHIPPING).completeAddress(invalidUsAddress);
    }

    @Given("^I fill out a shipping address with a missing postcode$")
    public void fillShippingAddressWithMissingPostCode(String country) {
        Address missingPostCodeAddress = new Address();
        missingPostCodeAddress.setAddress1("1 Test Street");
        missingPostCodeAddress.setAddress2("");
        missingPostCodeAddress.setTownOrCity("Testerville");
        missingPostCodeAddress.setProvinceOrTerritoryOrState("");
        missingPostCodeAddress.setPostcode("");
        missingPostCodeAddress.setDaytimePhoneNumber("0315551234");
        missingPostCodeAddress.setCountry(country);

        getAddressPage(SHIPPING).completeAddress(missingPostCodeAddress);
    }

    @Given("^I fill out a UK London address that has a (.*) shipping postcode$")
    public void fillUKLondonPremierShippingPostcodeAddress(String shippingOption) {
        String premierPostCodePrefix = productDataAccess.getLegacyDBClient().findPostCodePrefixForShippingOption(webBot.getRegionEnum(), shippingOption);
        purchasePathShippingAddressPage.completeAddress(
                Address.createAnotherAddress(Country.UNITED_KINGDOM.getName(), "London", premierPostCodePrefix + " 1AA"));
    }

    @Given("^I fill out a UK London address that is (.*) and has a (.*) shipping postcode$")
    public void fillUKLondonPremierNominatedDayShippingPostcodeAddress(String shippingOption, String shippingMethodType) {
        String premierPostCodePrefix = productDataAccess.getLegacyDBClient().findPostcodeByShippingOptionAndShippingMethodType(webBot.getRegionEnum(), shippingOption, shippingMethodType);
        purchasePathShippingAddressPage.completeAddress(
                Address.createAnotherAddress(Country.UNITED_KINGDOM.getName(), "London", premierPostCodePrefix + " 1AA"));
    }

    @Given("^I fill out a UK London address that does not have a London premier shipping postcode$")
    public void fillAddressForCountryAndPostcode() {
        purchasePathShippingAddressPage.completeAddress(Address.createAnotherAddress(Country.UNITED_KINGDOM.getName(), "London", "GG1 1AA"));
    }

    private PurchasePathAddressPage getAddressPage(String pageName) {
        PurchasePathAddressPage ret = null;

        if(pageName.toLowerCase().trim().equals(SHIPPING))
            ret = (PurchasePathAddressPage) purchasePathShippingAddressPage;
        else if(pageName.toLowerCase().trim().equals(BILLING))
            ret = (PurchasePathAddressPage) purchasePathBillingAddressPage;

        return ret;
    }
}