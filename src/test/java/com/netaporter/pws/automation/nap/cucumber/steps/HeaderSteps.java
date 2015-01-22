package com.netaporter.pws.automation.nap.cucumber.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class HeaderSteps extends BaseNapSteps {


    @When("^I click on NAP logo or strap line$")
    public void I_click_on_NAP_logo_or_strap_line() {
        headerComponent.clickLogo();
    }

    @Then("^I should see NAP logo$")
    public void I_should_see_NAP_logo() {
        headerComponent.verifyLogoIsVisible();
    }

    @And("^The strapline should be (.*)$")
    public void The_strapline_should_be_visible(Boolean visible) {
        headerComponent.verifyStraplineVisibility(visible);
    }

    @Then("^(.*) and (.*) are (.*) in the header$")
    public void Country_and_currency_are_visible_in_the_header(String country, String currency, Boolean visible) {
        if (visible) {
            headerComponent.verifyCountryIsCorrect(country);
            headerComponent.verifyCurrencyIsCorrect(currency);
        }
        headerComponent.verifyCountryCurrencyVisibility(visible);
    }

    @When("^I click the change country$")
    public void I_click_the_change_country() throws Throwable {
        headerComponent.clickChangeCountryLinkResp();
    }

    @And("^My country is now (.*)$")
    public void My_country_is_now_country(String country) {
        headerComponent.verifyCountryIsCorrect(country);
    }

    @Then("^I am provided with the correct customer service number (.*)$")
    public void I_am_provided_with_the_correct_customer_service_number_contactNumber(String phoneNumber) throws Throwable {
        headerComponent.verifyCustomerCareNumber(phoneNumber);
    }
}

