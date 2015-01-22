package com.netaporter.pws.automation.nap.cucumber.steps;

import cucumber.api.java.en.When;

public class CommonSteps extends BaseNapSteps {

	@When("^I go to (.*) page$")
    public void I_go_to_page(String page) {
        lookupPage(page).go();
    }

    @When("^I sign out from the current page$")
    public void signOutFromCurrentPage() throws Throwable {
        getCurrentNapPage().signOut();
    }
}