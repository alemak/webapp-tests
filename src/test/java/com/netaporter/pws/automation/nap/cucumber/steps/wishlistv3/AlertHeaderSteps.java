package com.netaporter.pws.automation.nap.cucumber.steps.wishlistv3;

import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class AlertHeaderSteps extends BaseNapSteps {

    @Then("^I should see that my Wish List alerts are empty$")
    public void wish_list_alerts_are_empty() throws Throwable {
        assertTrue(headerContent.isWishListAlertEmpty());
    }


    @Then("^wish list alert box should not be displayed$")
    public void wish_list_alert_box_should_not_be_displayed() throws Throwable {
        assertFalse(headerContent.isWishListAlertPresent());
    }

    @When("^I hover over my Wish List alerts$")
    public void I_hover_on_my_Wish_List_alerts() throws Throwable {
        headerContent.hoverOverWishListAlertBuble();
    }

    @When("^I click the wishlist hyperlink on the drop down$")
    public void I_select_the_wishlist_hyperlink_on_the_drop_down() throws Throwable {
        headerContent.clickWishlistLinkOnDropdown();
    }

    @Then("^I am taken to the wish list page$")
    public void I_am_taken_to_the_wish_list() throws Throwable {
        String currentUrl = webBot.getCurrentUrl();
        assertTrue(currentUrl.contains("wishlist/"));
    }

}

