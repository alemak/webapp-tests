package com.netaporter.pws.automation.nap.cucumber.steps.wishlistv3;

import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.pws.automation.nap.pages.NAPProductDetailsPage;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.MenuItem;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.NavMenu;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.testng.AssertJUnit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: a.michael@london.net-a-porter.com
 * Date: 17/03/2014
 * Time: 9:29AM
 * (C) DevilRacing666
 */
public class ShareWishlistSteps extends BaseNapSteps {

    @Then("^the Manage wishlist button should not show$")
    public void isManageWishlistButtonAvailable() {
        wishListV3Page.doesManageWishlistButtonExist();
    }

    @And("^I select the Shared radio button on the Manage Wishlist popup and Save Changes for Wishlist (.+)$")
    public void clickSharedRadioButtonAndSave(String wishlistName) throws Throwable {
        wishListV3Page.clickSharedRadioButtonAndSave(wishlistName);
    }

    @And("^I should not see the Share Wish List button$")
    public void checkShareWishlistButtonNotAvailable() {
        AssertJUnit.assertFalse("Share wishlist button should not be available", wishListV3Page.isShareListButtonAvailable());
    }

    @And("^the privacy padlock is showing in the wishlist header$")
    public void privacyPadlockShowing() {
        assertTrue("Privacy padlock NOT showing", wishListV3Page.isCurrentWishlistPrivate());
    }

    @And("^the privacy padlock is NOT showing in the wishlist header$")
    public void privacyPadlockNotShowing() {
        assertFalse("Privacy padlock showing", wishListV3Page.isCurrentWishlistPrivate());
    }

    @And("^the wishlist nav menu should contain a privacy symbol on the wishlist with name (.+)$")
    public void checkMenuItemContainsPadlock(String listName) throws Throwable {
        //Thread.sleep(2000);
        MenuItem listMenuItem = wishListV3Page.getNavMenu().getFirstUserMenuItemWithName(listName);
        assertTrue("Menu item " + listName + " should have a padlock", listMenuItem.isPrivate());
    }

    @And("^the wishlist nav menu should NOT contain a privacy symbol on the wishlist with name (.+)$")
    public void checkMenuItemDoesNotContainsPadlock(String listName) throws Throwable {
        //Thread.sleep(2000);
        MenuItem listMenuItem = wishListV3Page.getNavMenu().getFirstUserMenuItemWithName(listName);
        assertFalse("Menu item " + listName + " should NOT have a padlock", listMenuItem.isPrivate());
    }

    @And("^the wishlist popup menu item called (.+) shows as Private$")
    public void checkMenuItemHasPadlock(String listName) throws Throwable {
        Thread.sleep(2000);
        MenuItem listMenuItem = productDetailsPage.returnNavMenu().getFirstUserMenuItemWithName(listName);
        assertTrue("Menu item " + listName + " should NOT have a padlock", listMenuItem.isPrivate());
    }

    @And("^the wishlist popup menu item called (.+) shows as Shared$")
    public void checkMenuItemDoesNotHavePadlock(String listName) throws Throwable {
        Thread.sleep(2000);
        MenuItem listMenuItem = productDetailsPage.returnNavMenu().getFirstUserMenuItemWithName(listName);
        assertFalse("Menu item " + listName + " should NOT have a padlock", listMenuItem.isPrivate());
    }

    @When("^I click the Share wish list button$")
    public void clickShareList() {
        wishListV3Page.clickShareList();
    }

    @And("I select the Get Link menu option$")
    public void clickGetLinkMenuItem() {
        wishListV3Page.clickGetLinkMenuItem();
    }

    @Then("^the Share Wish List popup should appear$")
    public void checkSharePopup() throws Throwable {
        wishListV3Page.isShareWishListPopupOpen();
    }

    @When("^I click the Share Wish List confirmation button in the Share Wishlist popup$")
    public void clickShareWishlistConfirmation() {
        wishListV3Page.clickShareConfirmationButtonInOverlay();
    }

    @Then("^the link in the overlay matches the address of the wishlist$")
    public void shareLinkUrlMatchesWishlistAddress() throws Throwable {
        assertTrue("Wishlist link does not contain correct URL",
                wishListV3Page.doesShareLinkInOverlayMatchWishlistUrl(scenarioSession.getData("wishlistID").toString()));
    }

    @And("^I click the close button in the get wishlist link popup$")
    public void clickCloseInShareWishlistPopup() {
        wishListV3Page.clickCloseButtonInGetLinkOverlay();
    }

}
