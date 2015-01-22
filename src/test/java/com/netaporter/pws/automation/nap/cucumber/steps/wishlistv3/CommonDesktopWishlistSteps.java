package com.netaporter.pws.automation.nap.cucumber.steps.wishlistv3;

import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

/**
 * Date: 01/07/2013
 * Time: 17:31
 */
public class CommonDesktopWishlistSteps extends BaseNapSteps {

    @When("^I click some whitespace on the page$")
    public void clickOnWhitespace() {
        wishListV3Page.clickOnWhitespace();
    }

    @Then("^the header wishlist link should be visible$")
    public void the_wishlist_link_should_be_visible() throws Throwable {
        assertTrue(headerContent.isHeaderWishlistLinkVisible());
    }

    @When("^I click the top nav wishlist hyperlink$")
    public void I_select_the_wishlist_hyperlink$() throws Throwable{
        headerContent.clickHeaderWishlistLink();
    }

    @When("^I click the wishlist link on the header$")
    public void I_select_the_wishlist_link_on_the_header() throws Throwable {
        headerContent.clickHeaderWishlistLink();
        wishListV3Page.waitForLoadingPaneToDisappear();
        scenarioSession.putData("currentWishlistName", "All Items");
        scenarioSession.putData("currentWishlistURL", webBot.getCurrentUrl());
    }
}
