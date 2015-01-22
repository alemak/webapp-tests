package com.netaporter.pws.automation.nap.cucumber.steps.wishlistv3;

import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.pws.automation.shared.apiclients.WoasAuthManager;
import com.netaporter.wishlist.woas.client.commands.CreateWishlist;
import com.netaporter.wishlist.woas.client.pojos.Wishlist;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import org.apache.commons.lang3.StringUtils;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created with IntelliJ IDEA.
 * User: a.michael@london.net-a-porter.com
 * Date: 27/06/2013
 * Time: 11:28AM
 * (C) DevilRacing666
 */
public class CreateWishlistSteps extends BaseNapSteps {

    private String suggestedWishlist;

    @When ("^I click the create wishlist menu item$")
    public void clickCreateWishlist() throws Throwable {
        wishListV3Page.clickMenuElementByText("Create New");
    }

    @When("^I click the wish list menu and count the menu items$")
    public void clickWishlistMenu() throws Throwable {
        wishListV3Page.clickWishlistMenu();
        scenarioSession.putData("wishlistMenuItems", wishListV3Page.countMenuItems());
    }

    @And("^the number of wishlist menu items does not increase$")
    public void checkNumberOfMenuItemsDoesntIncrease() throws Throwable {
        Integer numberOfItemsInitially = (Integer)scenarioSession.getData("wishlistMenuItems");
        Integer numberOfItemsNow = wishListV3Page.countMenuItems();
        assertEquals("Number of wishlist items has changed",numberOfItemsInitially,numberOfItemsNow);
    }

    @Then("^The create wishlist form should be visible$")
    public void createWishlistFormShouldBeVisible() throws Throwable {
        boolean visible = wishListV3Page.isCreateWishlistFormVisible();
        assertThat("Create wishlist form was invisible", visible, equalTo(true));
    }

    @Then("^The create wishlist form should not be visible$")
    public void createWishlistFormShouldNotBeVisible() throws Throwable {
        boolean visible = wishListV3Page.isCreateWishlistFormVisible();
        assertThat("Create wishlist form was visible", visible, equalTo(false));
    }

    @When ("^I click to submit the create wishlist form with name (.+)$")
    public void submitCreateWishlistFormWithClick(String listName) throws Throwable {
        wishListV3Page.submitCreateListFormWithClick(listName);

        if(StringUtils.isNotEmpty(listName)) {
            listName = listName.length() > 24? listName.substring(0, 24): listName;
            scenarioSession.putData("currentWishlistName", listName);
        }
    }

    @When ("^I click to submit the create wishlist form with no name$")
    public void submitCreateWishlistFormWithClick() throws Throwable {
        submitCreateWishlistFormWithClick("");
    }

    @When ("^I press enter to submit the create wishlist form with no name$")
    public void submitCreateWishlistFormWithEnter() throws Throwable {
        submitCreateWishlistFormWithEnter("");
    }

    @When ("^I press enter to submit the create wishlist form with name (.+)$")
    public void submitCreateWishlistFormWithEnter(String listName) throws Throwable {
        wishListV3Page.submitCreateListFormWithEnter(listName);

        if(StringUtils.isNotEmpty(listName)) {
            listName = listName.length() > 24? listName.substring(0, 24): listName;
            scenarioSession.putData("currentWishlistName", listName);
        }
    }

    @When ("^I press enter to submit the create wishlist form 30 times with different names$")
    public void submitCreate100WishlistsFormWithEnter() throws Throwable {
        String listName = "Wishlist ";
        for (int i=1; i<31 ; i++) {
            wishListV3Page.submitCreateListFormWithEnter(listName+i);
        }
    }

    @When ("^I press enter to submit the create wishlist form with whitespace for the name$")
    public void submitWhitespaceNameForCreateWishlistFormWithEnter() throws Throwable {
        wishListV3Page.submitCreateListFormWithEnter(" ");
    }

    @When ("^I submit the create wishlist form with no name$")
    public void submitNoNameForCreateWishlistFormWithEnter() throws Throwable {
        wishListV3Page.submitCreateListFormWithEnter("");
    }

    @When("^I click the suggested wishlist name '(.+)'$")
    public void I_click_the_suggested_wish_list_name_Vacation(String suggestedWishlist) throws Throwable {
        wishListV3Page.clickSuggestedWishlistName(suggestedWishlist);
    }

    @And("^the suggested wishlist name '(.+)' is no longer listed$")
    public void the_suggested_wishlist_name_Vacation_is_no_longer_listed(String suggestedWishlist) throws Throwable {
        // Express the Regexp above with the code you wish you had
        assertFalse("Suggest wishlist '"+suggestedWishlist+"' still exists", wishListV3Page.doesSuggestedWishlistNameExist(suggestedWishlist));
    }

    @Then("^the suggested wishlists list disappears$")
    public void the_suggested_wishlists_list_disappears() throws Throwable {
        assertFalse("Wishlist suggestion list still visible.",wishListV3Page.doesSuggestedWishlistListExist());
    }

    //todo not sure why below step has been pasted into here as it's already in commonwishliststeps class - have commented out for now
//    @And("^I create a new wishlist via the WOAS API called (.*)$")
//    public Wishlist createWishlistViaWOAS(String wishlistName) throws Throwable {
//        Wishlist newWishlist = woasAPIClient.createWishlist(WoasAuthManager.getAuth(scenarioSession, webBot), new CreateWishlist(wishlistName));
//
//        //TODO: Duplication in scenarioSession. We could just use UserWishlist everywhere
//        scenarioSession.putData("UserWishlist", newWishlist);
//        scenarioSession.putData("currentWishlistName", newWishlist.getName());
//        scenarioSession.putData("wishlistName", newWishlist.getName());
//        scenarioSession.putData("wishlistID", newWishlist.getId());
//
//        return newWishlist;
//    }


//    @And("^I create a new wishlist via the WOAS API called (.*)$")
//    public Wishlist createWishlistViaWOAS(String wishlistName) throws Throwable {
//        Wishlist newWishlist = woasAPIClient.createWishlist(WoasAuthManager.getAuth(scenarioSession, webBot), new CreateWishlist(wishlistName));
//
//        //TODO: Duplication in scenarioSession. We could just use UserWishlist everywhere
//        scenarioSession.putData("UserWishlist", newWishlist);
//        scenarioSession.putData("currentWishlistName", newWishlist.getName());
//        scenarioSession.putData("wishlistName", newWishlist.getName());
//        scenarioSession.putData("wishlistID", newWishlist.getId());
//
//        return newWishlist;
//    }


}
