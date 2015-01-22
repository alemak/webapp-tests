package com.netaporter.pws.automation.nap.cucumber.steps.footer;

import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Created by s.joshi on 03/09/2014.
 */
public class FooterSteps extends BaseNapSteps {

    private static final String FOOTER_LINK = "footer link";

    @When("^I click on (.*) in the Footer$")
    public void I_click_on(String footerLink) throws Throwable {
        footerComponent.clickFooterLink(footerLink);
        scenarioSession.putData(FOOTER_LINK, footerLink);
    }

    @Then("^respective pages are visible$")
    public void respective_pages_are_visible() throws Throwable {
        String footerLink = scenarioSession.getData(FOOTER_LINK);
        footerComponent.isCorrectFooterPageVisible(footerLink);
    }
}
