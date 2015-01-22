package com.netaporter.pws.automation.shared.steps.wishlistv3;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Date: 17/10/2013
 * Time: 09:43
 */
public class SharedPaginationSteps extends SharedWishlistBaseSteps {

    @Then("^I should not see the view more button$")
    public void assertNoViewMoreButton() {
        assertFalse("Should not be able to see view more button", wishListV3Page.isViewMoreButtonAvailable());
    }

    @Then("^I should see the view more button$")
    public void assertViewMoreButton() {
        assertTrue("Should be able to see view more button", wishListV3Page.isViewMoreButtonAvailable());
    }

    @When("^I click the view more button$")
    public void clickViewMoreButton() throws Throwable{
        wishListV3Page.clickViewMoreButtonAvailable();
    }
}
