package com.netaporter.pws.automation.nap.cucumber.steps.country;

import com.netaporter.pws.automation.nap.components.ChangeCountryComponent;
import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.pws.automation.nap.pages.NAPProductDetailsPage;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;

/**
 * Created by s.joshi on 07/11/2014.
 */
public class CountryAndChannelSteps extends BaseNapSteps {

    @Then("^change country drop down list appears$")
    public void change_country_dropdown(){
        assertTrue("Country overlay is not visible", changeCountryComponent.isChangeCountryOverlayVisible());
    }

    @Then("^All supported countries are listed$")
    public void All_supported_countries_are_listed() throws Throwable {
        assertTrue("Some countries are missing","171".equals(changeCountryComponent.getTotalCountOfCountries()));
    }

    @Then("^the (.*) icon is visible$")
    public void the_countryflag_icon_is_visible(String flagName) throws Throwable {
        assertTrue("Flag icon is missing", changeCountryComponent.getFlagName().contains("flag-" + flagName));
    }

    @And("^the currency changes to one corresponding with the chosen (.*)$")
    public void the_currency_changes_to_one_corresponding_with_the_chosen_country(String country) throws Throwable {
        if (country.equalsIgnoreCase("Germany")){
            assertTrue(productDetailsPage.getGirdleCurrencyCode().equals("EUR"));
        }else if (country.equalsIgnoreCase("Nepal")){
            assertTrue(productDetailsPage.getGirdleCurrencyCode().equals("GBP"));
        }else if (country.equalsIgnoreCase("France")){
            assertTrue(productDetailsPage.getGirdleCurrencyCode().equals("EUR"));
        }
    }

    @When("^I switch my country to (.*)$")
    public void I_change_country_to_(String country) throws Throwable {
        changeCountryComponent.switchToCountry(country);
    }

    @Then("^country change message (.*)$")
    public void country_change_message_visibility(boolean visible) throws Throwable {

        assertEquals(visible, changeCountryComponent.isChangeCountryMessageDisplayed());
    }

    @And("^I confirm to change country$")
    public void I_confirm_to_change_country() throws Throwable {
        changeCountryComponent.clickChangeCountryConfirmation();
    }
}
