package com.netaporter.pws.automation.nap.cucumber.steps.wishlistv3;

import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;
import static org.testng.AssertJUnit.assertFalse;

/**
 * Created with IntelliJ IDEA.
 * User: J.Christian@net-a-porter.com
 * Date: 19/08/2013
 * Time: 14:59
 * To change this template use File | Settings | File Templates.
 */
public class DeleteWishlistSteps extends BaseNapSteps {

    @Then("^I should see a Delete Wish List Link")
    public void checkDeleteWishlistLinkAvailable() throws Throwable {
        assertTrue("Delete wishlist Link should be available", wishListV3Page.isDeleteListLinkAvailable());
    }

    @Then("^I should not see a Delete Wish List Link")
    public void checkDeleteWishlistLinkNotAvailable() throws Throwable {
        assertFalse("Delete wishlist Link should not be available", wishListV3Page.isDeleteListLinkAvailable());
    }

    @When("^I click the Delete Wish List Link")
    public void clickDeleteList() throws Throwable {
        wishListV3Page.clickDeleteList();
        wishListV3Page.waitForPopupToAppear();

    }

    @Then("^I should see the Delete Wish List confirmation overlay for wishlist named (.+)$")
    public void deleteWishlistOverlayVisible(String wishlistName) {
        assertTrue("Delete wishlist overlay should be displayed", wishListV3Page.isDeleteWishlistPopupDisplayed(wishlistName));
    }

    @When("^I click the Delete Wish List button in the Delete Wish List overlay$")
    public void clickDeleteListInOverlay() throws Throwable {
        wishListV3Page.clickConfirmationButtonInDeleteOverlay();
        wishListV3Page.waitForPopupToDisappear();
    }

    @When("^I click the Cancel button in the Delete Wishlist overlay$")
    public void clickCancelInOverlay() {
        wishListV3Page.clickCancelButtonInDeleteOverlay();

    }

    @When("^I click the X close button in the Delete Wishlist overlay$")
    public void clickXInOverlay() {
        wishListV3Page.clickXCloseInDeleteOverlay();
    }

    @When("^I click the glass pane outside the Delete Wishlist overlay$")
    public void clickGlassPaneOutsideOverlay() {
        wishListV3Page.clickGlassPaneCloseOverlay();
    }




}

