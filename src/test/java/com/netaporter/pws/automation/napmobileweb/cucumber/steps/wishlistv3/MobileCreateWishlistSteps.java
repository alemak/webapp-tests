package com.netaporter.pws.automation.napmobileweb.cucumber.steps.wishlistv3;

import com.netaporter.pws.automation.napmobileweb.cucumber.steps.BaseMobileNapSteps;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: a.michael@london.net-a-porter.com
 * Date: 27/06/2013
 * Time: 11:28AM
 * (C) DevilRacing666
 */
public class MobileCreateWishlistSteps extends BaseMobileNapSteps {

    public static final String USER_LISTS_CREATED = "USER_LISTS_CREATED";

    @When ("^I click the create wishlist menu item in the mobile wishlist$")
    public void clickCreateWishlist() throws Throwable {
        wishListV3Page.clickMenuElementByText("Create New");
    }

    @Then("^The create wishlist form should be visible in the mobile wishlist$")
    public void createWishlistFormShouldBeVisible() throws Throwable {
        boolean visible = wishListV3Page.isCreateWishlistFormVisible();
        assertThat("Create wishlist form was invisible", visible, equalTo(true));
    }

    @Then("^The create wishlist form should not be visible in the mobile wishlist$")
    public void createWishlistFormShouldNotBeVisible() throws Throwable {
        boolean visible = wishListV3Page.isCreateWishlistFormVisible();
        assertThat("Create wishlist form was visible", visible, equalTo(false));
    }

    @When ("^I click to submit the create wishlist form with name (.+) in the mobile wishlist$")
    public void submitCreateWishlistFormWithClick(String listName) throws Throwable {
        wishListV3Page.submitCreateListFormWithClick(listName);

        if(StringUtils.isNotEmpty(listName)) {
            listName = listName.length() > 24? listName.substring(0, 24): listName;
            scenarioSession.putData("currentWishlistName", listName);
        }
    }

    @When ("^I click to submit the create wishlist form with no name in the mobile wishlist$")
    public void submitCreateWishlistFormWithClick() throws Throwable {
        submitCreateWishlistFormWithClick("");
    }

    @When ("^I press enter to submit the create wishlist form with no name in the mobile wishlist$")
    public void submitCreateWishlistFormWithEnter() throws Throwable {
        submitCreateWishlistFormWithEnter("");
    }

    @When ("^I press enter to submit the create wishlist form with name (.+) in the mobile wishlist$")
    public void submitCreateWishlistFormWithEnter(String listName) throws Throwable {
        wishListV3Page.submitCreateListFormWithEnter(listName);

        if(StringUtils.isNotEmpty(listName)) {
            listName = listName.length() > 24? listName.substring(0, 24): listName;
            scenarioSession.putData("currentWishlistName", listName);
        }
    }

}
