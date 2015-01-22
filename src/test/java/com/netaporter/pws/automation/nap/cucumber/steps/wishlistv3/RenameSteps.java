package com.netaporter.pws.automation.nap.cucumber.steps.wishlistv3;

import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.testng.AssertJUnit;

import static org.junit.Assert.assertTrue;
import static org.testng.AssertJUnit.assertFalse;

/**
 * Created with IntelliJ IDEA.
 * User: J.Christian@net-a-porter.com
 * Date: 21/08/2013
 * Time: 09:12
 * To change this template use File | Settings | File Templates.
 */
public class RenameSteps extends BaseNapSteps {

    private static final String THREE_SPACES = "   ";

    @Then("^I should see a Manage Wish List Button$")
    public void checkManageWishlistButtonAvailable() {
        assertTrue("Manage wishlist button should be available", wishListV3Page.isManageListButtonAvailable());
    }

    @Then("^I should not see the Manage Wish List Button$")
    public void checkManageWishlistButtonNotAvailable() {
        assertFalse("Manage wishlist button should not be available", wishListV3Page.isManageListButtonAvailable());
    }

    @When("^I click the Manage Wish List button$")
    public void clickManageList() throws Throwable {
        webBot.waitForJQueryCompletion();
        Thread.sleep(1000);
        wishListV3Page.clickManageList();
    }

    @Then("^I should see the Manage Wish List overlay for wishlist named (.+)$")
    public void manageWishlistOverlayVisible(String wishlistName) throws Throwable {
        assertTrue("Manage wishlist overlay should be displayed with name: " + wishlistName,
                wishListV3Page.isManageWishlistPopupDisplayed(wishlistName));
    }

    @Then("^I should still see the Manage Wish List overlay with no name with the text field taking focus$")
    public void manageWishlistOverlayVisibleWithTextFieldInFocusNoName() throws Throwable {
        manageWishlistOverlayVisibleWithTextFieldInFocus("");
    }

    @Then("^I should still see the Manage Wish List overlay with whitespace as the name and with the text field taking focus$")
    public void manageWishlistOverlayVisibleWithTextFieldInFocusSpacesAsName() throws Throwable {
        manageWishlistOverlayVisibleWithTextFieldInFocus(THREE_SPACES);
    }

    @Then("^I should still see the Manage Wish List overlay with name (.+) with the text field taking focus$")
    public void manageWishlistOverlayVisibleWithTextFieldInFocus(String name) throws Throwable {
        assertTrue("Manage wishlist overlay should be displayed with name: " + name,
                wishListV3Page.isManageWishlistPopupDisplayed(name));

        assertTrue("Manage wishlist textfield should have focus", wishListV3Page.doesManageOverlayTextFieldHaveFocus());
    }


    @Then("^I should not see the Manage Wish List overlay$")
    public void manageWishlistOverlayNotVisible() throws Throwable {
        assertTrue("Manage wishlist overlay should not be displayed", wishListV3Page.isManageWishlistPopupNotDisplayed());
    }

    @When("^I click the Save Changes button in the Manage Wish List overlay$")
    public void clickSaveChangesListInOverlay() throws Throwable {
        wishListV3Page.clickConfirmationButtonInOverlay();
        wishListV3Page.waitForPopupToDisappear();
    }

    @When("^I click the Cancel button in the Manage Wishlist overlay$")
    public void clickCancelInOverlay() {
        wishListV3Page.clickCancelButtonInOverlay();
    }

    @When("^I click the X close button in the Manage Wishlist overlay$")
    public void clickXInOverlay() {
        wishListV3Page.clickXCloseInOverlay();
    }

    @When("^I click the glass pane outside the Manage Wishlist overlay$")
    public void clickGlassPaneOutsideOverlay() {
        wishListV3Page.clickGlassPaneCloseOverlay();
    }

    @And("^I specify a new wishlist name in the overlay as (.+)$")
    public void setNewWishlistNameInManageOverlay(String newName) throws Throwable {
        Thread.sleep(1500);
        wishListV3Page.enterNewNameInToManageWishlistOverlay(newName);
    }

    @And("^I specify an empty new wishlist name in the overlay$")
    public void setEmptyWishlistNameInManageOverlay() throws Throwable {
        Thread.sleep(1500);
        wishListV3Page.enterNewNameInToManageWishlistOverlay("");
    }

    @And("^I specify whitespace as the new wishlist name in the overlay$")
    public void setWhitespaceWishlistNameInManageOverlay() throws Throwable {
        Thread.sleep(1500);
        wishListV3Page.enterNewNameInToManageWishlistOverlay(THREE_SPACES);
    }


    @And("^I reload the current page$")
    public void reloadCurrentPage() {
        webBot.reload();
    }



}
