package com.netaporter.pws.automation.napmobileweb.cucumber.steps.wishlistv3;


import com.netaporter.pws.automation.napmobileweb.cucumber.steps.BaseMobileNapSteps;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.WishlistV3Product;
import com.netaporter.test.utils.pages.IPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebElement;

import java.util.Collections;
import java.util.List;

import static com.netaporter.test.utils.assertion.objectcomparison.NapReflectionAssert.assertReflectionEqualsNAP;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: a.michael@london.net-a-porter.com
 * Date: 27/06/2013
 * Time: 11:21AM
 * (C) DevilRacing666
 */
public class MobilePageVerificationSteps extends BaseMobileNapSteps {

    @Then("^I should see the correct mobile wishlist page title$")
    public void checkWishListPageTitle() throws Throwable {
        String wishlistPageTitle = (String) scenarioSession.getData("currentWishlistName")+" | Wish List | NET-A-PORTER.COM";
        assertEquals("Wishlist Page Title", wishlistPageTitle, wishListV3Page.getTitle().replace("\u00a0", " "));
    }

    @Then("^I should see the correct mobile wishlist page title called (.*)$")
    public void checkWishListPageTitle(String wishlistPageTitle) throws Throwable {
        String title = wishlistPageTitle+" | Wish List | NET-A-PORTER.COM";
        assertEquals("Wishlist Page Title", title, wishListV3Page.getTitle().replace("\u00a0", " "));
    }

    @And("^I should see the correct mobile wishlist header$")
    public void checkWishListHeader() throws Throwable {
        String wishlistHeader = (String) scenarioSession.getData("currentWishlistName");
        assertEquals("Incorrect Wishlist Header", wishlistHeader.toUpperCase(), wishListV3Page.wishlistHeaderTitle().getText());
    }

    @And("^I should see the correct mobile wishlist header called (.*)$")
    public void checkWishListHeader(String wishlistHeader) throws Throwable {
        assertEquals("Incorrect Wishlist Header", wishlistHeader.toUpperCase(), wishListV3Page.wishlistHeaderTitle().getText());
    }

    @And("^I should see the correct empty mobile wishlist message$")
    public void checkEmptyWishListMessage() throws Throwable {
        for(int i=0 ; i<10 ; i++) {
            if (wishListV3Page.wishlistContent().getText().contains("empty")) {
                i=10;
            } else {
                Thread.sleep(1000);
            }
        }
        assertEquals("Empty Wishlist Text",
                "This Wish List is currently empty\n" +
                        "Want to keep track of all your favorite pieces? Simply select your size from the product page and click on 'Add to Wish List'",
                wishListV3Page.wishlistContent().getText());
    }

    @Then("^I should see those items in the correct chronological order with the correct details in the mobile wishlist$")
    public void checkWishlistItemsOrder() throws Throwable {
        List<WishlistV3Product> singleWishlistProductsToCompare = (List<WishlistV3Product>) scenarioSession.getData("singleWishlistProducts");
        List<WishlistV3Product> singleWishlistProductsOnPage = wishListV3Page.getAllWishlistV3Products();

        assertThat("Not the expected number of wishlist items on the page", singleWishlistProductsToCompare.size(), equalTo(singleWishlistProductsOnPage.size()));

        Collections.reverse(singleWishlistProductsToCompare);  // descending date added order
        assertReflectionEqualsNAP("Reverse chronological order check", singleWishlistProductsToCompare, singleWishlistProductsOnPage);
        Collections.reverse(singleWishlistProductsToCompare);
    }

    @And("^I should see the correct thumbnails for each item on the mobile page$")
    public void checkThumbnails() throws Throwable {
        List<WishlistV3Product> singleWishlistProducts = (List<WishlistV3Product>) scenarioSession.getData("singleWishlistProducts");
        List<WebElement> thumbnails = wishListV3Page.getWishlistThumbnails();

        Collections.reverse(singleWishlistProducts);

        for ( int i = 0 ; i < singleWishlistProducts.size() ; i++) {
            WishlistV3Product singleProduct = singleWishlistProducts.get(i);
            WebElement thumbnail = thumbnails.get(i);
            assertTrue("Wishlist page thumbnail missing: " + thumbnail, thumbnail.getAttribute("src").contains(singleProduct.getPid().toString()));
        }
        Collections.reverse(singleWishlistProducts);
    }

    @And("^I should see the correct size for each item in the mobile wishlist$")
    public void checkSizes() throws Throwable {
        List<WishlistV3Product> singleWishlistProducts = (List<WishlistV3Product>) scenarioSession.getData("singleWishlistProducts");
        List<WebElement> sizes = wishListV3Page.getWishlistSizes();
        Collections.reverse(singleWishlistProducts);

        for ( int i = 0 ; i < singleWishlistProducts.size() ; i++) {
            WishlistV3Product singleProduct = singleWishlistProducts.get(i);
            WebElement size = sizes.get(i);

//            if (singleProduct.getSize().equals(MobileAddItemsSteps.SINGLE_SIZE)) {
//                boolean singleSizedItemMatches = (size.getText().equalsIgnoreCase(MobileAddItemsSteps.SINGLE_SIZE) || size.getText().equals("n/a"));
//                assertTrue("Wishlist page single sized item size incorrect.  Was: " + size.getText(), singleSizedItemMatches);
//            } else {

            if (singleProduct.getSize() != null) {
                // Ignore spaces in sizes as this is inconsistent across the site
                assertThat("Wishlist size matches that from product details page" + singleProduct.getPid(), size.getText().replace(" ", ""), equalTo(singleProduct.getSize()));
            }
        }
        Collections.reverse(singleWishlistProducts);
    }

    @Then("^I should see the mobile wishlist items ordered by date added descending without duplicates$")
    public void checkAllWishlistItemsOrder() throws Throwable {
        List<WishlistV3Product> allWishlistProductsToCompare = (List<WishlistV3Product>) scenarioSession.getData("singleWishlistProducts");
        List<WishlistV3Product> allWishlistProductsOnPage = wishListV3Page.getAllWishlistV3Products();

        assertThat("Not the expected number of wishlist items on the page", allWishlistProductsToCompare.size(), equalTo(allWishlistProductsOnPage.size()));

        Collections.reverse(allWishlistProductsToCompare);  // descending date added order
        assertReflectionEqualsNAP("Reverse chronological order check", allWishlistProductsToCompare, allWishlistProductsOnPage);
        Collections.reverse(allWishlistProductsToCompare);
    }


    @And("^I should be on the mobile wishlist page called (.+)$")
    public void assert_current_wishlist_page(String wishlistName) {
        if(wishlistName.toLowerCase().contains("all items") || wishlistName.toLowerCase().contains("all-items")) {
            assertThat("The actual wishlist page doesn't match the expected wishlist page.", webBot.getCurrentUrl(), containsString("all-items"));
        } else {
            String wishlistId = woasSteps.getWishlistId(wishlistName);
            if (! wishlistId.isEmpty()) {
                assertThat("The actual wishlist page doesn't match the expected wishlist page.", webBot.getCurrentUrl(), equalTo(webBot.getBaseUrl() + "wishlist/" + wishlistId));
            } else {
                fail("The wishlist called '" + wishlistName + "' does not appear to exist when WOAS was queried");
            }
        }
    }


    @And("^I should not be on any mobile wishlist page$")
    public void assert_not_wishlist_page() {
        assertThat("The current page seems to be a wishlist page when it shouldn't be.", webBot.getCurrentUrl(), is(not(containsString("/wishlist"))));
    }

    @When("^I should be on the same mobile url as before I updated my country and language preferences$")
    public void on_same_url_before_updating_preferences() throws Throwable {
        assertThat("The actual URL doesn't match the expected URL.", webBot.getCurrentUrl(), equalTo(scenarioSession.getData("currentWishlistURL")));
    }


    @And("^I should see the delete item button for each item on the mobile page$")
    public void deleteItemButtonOnEachItem() {
        assertThat("Expect each wishlist item to have a remove item button", wishListV3Page.isRemoveItemButtonAvailableOnEachItem());
    }

    @And("^I should see the Add to bag button for each item on the mobile page$")
    public void addToShoppingBagButtonOnEachItem() {
        assertThat("Expect each wishlist item to have an Add to Bag button", wishListV3Page.isAddToBagButtonAvailableOnEachItem());
    }

    @And ("^I browse to a non-existant mobile wishlist page$")
    public void goToNonExistantDefaultList() throws Throwable {
        wishListV3Page.goToWishlistListID("madeupstring");
    }

    @Then("^I should see the mobile wishlist error page$")
    public void verifyOnTheWishlistErrorPage() {
        assertThat("Should be on wishlist error page", wishListV3Page.isWishlistErrorPage());
    }

    @Then("^I should see mobile items (\\d+) to (\\d+) in the correct chronological order with the correct details$")
    public void checkSpecificWishlistItemsOrder(int from, int to) throws Throwable {

        List<WishlistV3Product> singleWishlistProductsToCompare = (List<WishlistV3Product>) scenarioSession.getData("singleWishlistProducts");
        singleWishlistProductsToCompare = singleWishlistProductsToCompare.subList(from-1, to);

        List<WishlistV3Product> singleWishlistProductsOnPage = wishListV3Page.getAllWishlistV3Products();

        assertThat("Not the expected number of wishlist items on the page", singleWishlistProductsToCompare.size(), equalTo(singleWishlistProductsOnPage.size()));

        //currently the next step can fail due to discrepancies in the case of the 'size' field. Therefore we convert all sizes string to lowercase beforehand
        singleWishlistProductsOnPage = convertSizeToLowercase(singleWishlistProductsOnPage);
        singleWishlistProductsToCompare = convertSizeToLowercase(singleWishlistProductsToCompare);

        Collections.reverse(singleWishlistProductsToCompare);  // descending date added order
        assertReflectionEqualsNAP("Reverse chronological order check", singleWishlistProductsToCompare, singleWishlistProductsOnPage);
        Collections.reverse(singleWishlistProductsToCompare);
    }

    @And("^I should not see items (\\d+) to (\\d+) on the mobile wishlist page$")
    public void checkItemsNotInWishlist(int from, int to) throws Throwable {

        List<WishlistV3Product> singleWishlistProductsToCompare = (List<WishlistV3Product>) scenarioSession.getData("singleWishlistProducts");
        singleWishlistProductsToCompare = singleWishlistProductsToCompare.subList(from-1, to);

        List<WishlistV3Product> singleWishlistProductsOnPage = wishListV3Page.getAllWishlistV3Products();

        //currently the next step can fail due to discrepancies in the case of the 'size' field. Therefore we convert all sizes string to lowercase beforehand
        singleWishlistProductsOnPage = convertSizeToLowercase(singleWishlistProductsOnPage);
        singleWishlistProductsToCompare = convertSizeToLowercase(singleWishlistProductsToCompare);

        //check that each item expected not to be in the wishlist is not on the wishlist page
        for (WishlistV3Product expItem : singleWishlistProductsToCompare) {

            for (WishlistV3Product actItem : singleWishlistProductsOnPage)
                if (expItem.getPid().equals(actItem.getPid())) {
                    if (expItem.getSize() != null && expItem.getSize().equals(actItem.getSize())) {
                        fail("The product with PID " + expItem.getPid() + " and size " + expItem.getSize() + " was found on the wishlist page when it shouldn't have been");
                    }
                }
        }
    }

    private List<WishlistV3Product> convertSizeToLowercase(List<WishlistV3Product> items) {

        for(int i=0; i < items.size(); i++) {
            if (items.get(i).getSize() != null) {
                items.get(i).setSize(items.get(i).getSize().toLowerCase());
            }
        }
        return items;
    }

    @When("^I browse to the legacy mobile wish list page on INTL$")
    public void browseToLegacyMobileWishlist() {
        webBot.goToPage(new IPage() {
            @Override
            public String getPath() {
                return "intl/wishlist.nap";
            }


            // Dummy implementation
            @Override public void go() { }
            @Override public void goWithParams(String params) { }
            @Override public String getTitle() { return null; }
            @Override public List<String> getErrorMessages() { return null; }
            @Override public List<String> getMandatoryFieldErrors() { return null; }
            @Override public List<String> getMandatoryDropDownFieldErrors() { return null; }
            @Override public boolean isPageRegionalised() { return false; }
            @Override public String getRegionalisedPath() { return null;  }
        });
    }

}
