package com.netaporter.pws.automation.nap.cucumber.steps.country;


import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.test.utils.enums.RegionEnum;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.model.CucumberScenario;

import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.Matchers.equalToIgnoringCase;

public class CountryAndChannelCommonSteps extends BaseNapSteps {

    private static final String COUNTRY_COOKIE_KEY = "country_iso";
    private static final String LANGUAGE_COOKIE_KEY = "lang_iso";
    private static final String DEVICE_COOKIE_KEY = "deviceType";

    @Given("^I have selected (.*) from the (am|intl|apac) Channel$")
    public void I_have_selected_a_Country_from_Region(String country, RegionEnum region) throws Throwable {
        setRegion(region.name());
        System.setProperty("region", region.name());
        changeCountryPage.go();
        changeCountryPage.switchToCountry(country, region);
    }

    @Given("^I am on change country page$")
    public void I_am_on_change_country_page() throws Throwable {
        changeCountryPage.go();
    }

    @When("^I update location to (.*) in my preferences$")
    public void I_update_my_location_preference_to_(String country) throws Throwable {
        accountDetailsPage.clickUpdatePreferences();
        accountDetailsPage.switchToCountry(country);
    }

    @When("^I change my country to (.*)$")
    public void I_change_country_to_(String country) throws Throwable {
        changeCountryPage.switchToCountry(country);
    }

    @When("^I update my country preference to use country (.*)$")
    public void I_change_default_country_to_(String country) throws Throwable{
        changeCountryPage.updateCountryPreferences(System.getProperty("region"), country);
        scenarioSession.putData("country", country);
    }

    @When("^I change language to (.*)$")
    public void I_change_language_to_(String language) throws Throwable{
        changeCountryPage.changeLanguageTo(language);
        scenarioSession.putData("language", language);
    }

    @When("^I change my language to (English|French|German|Chinese)$")
    public void I_change_my_language_to_(String language) throws Throwable {
        changeCountryPage.switchToLanguage(language);
        scenarioSession.putData("language", language);
    }

    @Then("^My new location is (.*)$")
    public void My_location_is_(String country) throws Throwable {
        String locationAndLanguage = accountDetailsPage.getLocationAndLanguage();
        assertTrue("Location and language are " + locationAndLanguage, locationAndLanguage.contains(country));
    }


    @Given("^I stash the webpage path I'm currently on$")
    public void I_stash_the_page_Im_currently_on() {
         scenarioSession.putData("PATH_STASHED", getPathFromUrl(webBot.getCurrentUrl()));
    }

    @Then("^I am returned to the webpage path that I stashed$")
    public void I_am_returned_to_the_page_I_stashed() {
        String expectedPath = (String)scenarioSession.getData("PATH_STASHED");
        String actualPath = getPathFromUrl(webBot.getCurrentUrl());
        assertEquals("Expected to be on path: " + expectedPath + "  Actual Path: " + actualPath,
                expectedPath, actualPath);
    }

    @Then("^the country displayed in the top nav is (.*)$")
    public void I_should_be_switched_to_(String country) throws Throwable {
        assertEquals(country, getCurrentNapPage().getCountryName());
    }

    @Given("^I am on any country of the APAC website$")
    public void I_am_on_any_country_of_the_APAC_website() throws Throwable {
        setRegion(RegionEnum.APAC.name());
        changeCountryPage.go();
        changeCountryPage.switchToAnyCountry(RegionEnum.APAC.name());
    }

    @Given("^I am on NAP website without signing in$")
    public void I_am_on_NAP_website_without_signing_in() throws Throwable {
        homePage.go();
    }

    @When("^I open the My Preferences overlay by clicking the change country link$")
    public void I_open_the_My_Preferences_overlay_by_clicking_the_change_country_link() throws Throwable {
        headerComponent.clickChangeCountryLink();
        changeCountryPage.switchToMyPreferencesOverlay();
    }

    @When("^I open the My Preferences overlay by clicking the change language link$")
    public void I_open_the_My_Preferences_overlay_by_clicking_the_change_language_link() throws Throwable {
        headerComponent.clickChangeLanguageLink();
        changeCountryPage.switchToMyPreferencesOverlay();
    }

    private String getPathFromUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            return url.getPath();
        } catch (MalformedURLException e) {
            throw new AssertionError("Expected URL but got:" + webBot.getCurrentUrl() + " Exception: " + e.getMessage());
        }
    }

    @And("^the (country|language|deviceType) url parameter should be (.*)$")
    public void the_desired_url_parameter_should_be_correct(String desiredParameter, String parameterCode) throws Throwable {

       int beginIndex = 0;

       if ("country".equalsIgnoreCase(desiredParameter))
            beginIndex = webBot.getCurrentUrl().indexOf(".com/")+4;
       else if ("language".equalsIgnoreCase(desiredParameter))
            beginIndex = webBot.getCurrentUrl().indexOf(".com/")+7;
       else if ("deviceType".equalsIgnoreCase(desiredParameter))
            beginIndex = webBot.getCurrentUrl().indexOf(".com/")+10;
       else
            throw new UnsupportedOperationException("Unsupported cookie type: "+parameterCode+". Please choose between country, language or device");

        assertThat(webBot.getCurrentUrl().substring(beginIndex), startsWith("/"+parameterCode));

    }

    @Given("^I set the (country|language|deviceType) cookie value to (.*)$")
    public void I_set_the_desired_cookie_value_to(String cookieType, String cookieValue) throws Throwable {

       if ("country".equalsIgnoreCase(cookieType))
            webBot.addCookie(COUNTRY_COOKIE_KEY, cookieValue);
       else if ("language".equalsIgnoreCase(cookieType))
            webBot.addCookie(LANGUAGE_COOKIE_KEY, cookieValue);
       else if ("deviceType".equalsIgnoreCase(cookieType))
            webBot.addCookie(DEVICE_COOKIE_KEY, cookieValue);
       else
            throw new UnsupportedOperationException("Unsupported cookie type: "+cookieType+". Please choose between country, language or deviceType");
    }

    @Then("^the (country|language) locale is (.*)$")
    public void the_desired_locale_is(String locale, String localeCode) throws Throwable {

       if ("country".equalsIgnoreCase(locale))
            assertThat(localeCode, is(equalToIgnoringCase(getCurrentNapPage().getCountryName())));
       else if ("language".equalsIgnoreCase(locale))
            assertThat(localeCode, is(equalToIgnoringCase(getCurrentNapPage().getLanguageName())));
       else
          throw new UnsupportedOperationException("Unsupported locale: "+locale+". Please choose between country or language");
    }

    @Given("^I set country to (.*)$")
    public void I_set_country_to_country(String country) {
        webBot.setCountry(country);
    }
}