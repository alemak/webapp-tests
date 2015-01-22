package com.netaporter.pws.automation.napmobileweb.cucumber.steps.shopping;

import com.netaporter.pws.automation.napmobileweb.cucumber.steps.BaseMobileNapSteps;
import com.netaporter.pws.automation.napmobileweb.pages.MobileShoppingBagPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class MobileShoppingBagSteps extends BaseMobileNapSteps {

    @Autowired
    MobileShoppingBagPage mobileShoppingBagPage;

    // not really shopping bag step but leave this here for the time being....
    @Given("^I am on the homepage$")
    public void goToHomepage() throws Throwable {
        homePage.go();
    }

    @When("^I click purchase now from the shopping bag page$")
    public void clickProceedToPurchase() throws Throwable {
        mobileShoppingBagPage.clickProceedToPurchase();
    }

    @When("^I go to the shopping bag page$")
    public void goToShoppingBagPage() throws Throwable {
        mobileShoppingBagPage.go();
    }

}
