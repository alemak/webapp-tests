package com.netaporter.pws.automation.nap.cucumber.steps.wishlistv3;


import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.pws.automation.napmobileweb.cucumber.steps.wishlistv3.MobileAddItemsSteps;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.WishlistV3Product;
import com.netaporter.wishlist.woas.client.pojos.Wishlist;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.annotation.Nullable;
import javax.sql.rowset.Predicate;
import java.util.Collections;
import java.util.List;

import static com.netaporter.test.utils.assertion.objectcomparison.NapReflectionAssert.assertReflectionEqualsNAP;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created with IntelliJ IDEA.
 * User: a.michael@london.net-a-porter.com
 * Date: 27/06/2013
 * Time: 11:21AM
 * (C) DevilRacing666
 */
public class PageVerificationSteps extends BaseNapSteps {

    public static final String SINGLE_SIZE = "One size";

    @Then("^I should see the correct wishlist page title$")
    public void checkWishListPageTitle() throws Throwable {
        checkWishListPageTitle((String) scenarioSession.getData("currentWishlistName"));
    }

    @Then("^I should see the correct custom wishlist page title$")
    public void checkCustomWishListPageTitle() throws Throwable {
        checkCustomWishListPageTitle((String) scenarioSession.getData("currentWishlistName"));
    }

    @Then("^I should see the wishlist page title called (.*)$")
    public void checkWishListPageTitle(String wishlistPageTitle) throws Throwable {
        String title;
//        if(wishlistPageTitle.equals("All Items") || wishlistPageTitle.equals("Alerts") || wishlistPageTitle.equals("Closet")) {
            title = wishlistPageTitle + " | NET-A-PORTER.COM";
//        } else {
//            title = wishlistPageTitle + " | Wish List | NET-A-PORTER.COM";
//        }
        assertTrue(isElementPresent(By.cssSelector("title")));
        assertEquals("Wishlist Page Title", title, wishListV3Page.getPageTitle());
    }

    @Then("^I should see the custom wishlist page title in (.*) called (.*)$")
    public void checkCustomWishListPageTitle(String language, String wishlistPageTitle) throws Throwable {
        if (language.equalsIgnoreCase("french")) {
            checkWishListPageTitle(wishlistPageTitle + " | Wish List");
        } else if (language.equalsIgnoreCase("german")) {
            checkWishListPageTitle(wishlistPageTitle + " | Wunschliste");
        } else if (language.equalsIgnoreCase("chinese")) {
            checkWishListPageTitle(wishlistPageTitle + " | 愿望清单");
        } else {
            checkWishListPageTitle(wishlistPageTitle + " | Wish List");
        }
    }

    @Then("^I should see the custom wishlist page title called (.*)$")
    public void checkCustomWishListPageTitle(String wishlistPageTitle) throws Throwable {
        checkCustomWishListPageTitle("english", wishlistPageTitle);
    }

    @And("^I should see the correct wishlist header$")
    public void checkWishListHeader() throws Throwable {
        checkWishListHeader((String) scenarioSession.getData("currentWishlistName"));
    }

    @And("^I should see the wishlist header called (.*)$")
    public void checkWishListHeader(String wishlistHeader) throws Throwable {
        assertEquals("Incorrect Wishlist Header", wishlistHeader.toUpperCase(), wishListV3Page.wishlistHeaderTitle().getText());
    }

    @And("^I should see the site furniture header in (.*)$")
    public void checkPageHeaderLanguage(String language) throws Throwable {
        assertThat("Incorrect page language", language, equalToIgnoringCase(wishListV3Page.getLanguageName()));
    }

    @And("^I should see the site furniture header for the country (.*)$")
    public void checkPageHeaderCountry(String country) throws Throwable {
        assertThat("Incorrect page header country", country, equalToIgnoringCase(wishListV3Page.getCountryName()));
    }

    @And("^I should see the correct empty wishlist message$")
    public void checkEmptyWishListMessage() throws Throwable {
        Thread.sleep(1000);
        assertEquals("Empty Wishlist Text",
                "This Wish List is currently empty\n" +
                        "Want to keep track of all your favorite pieces? Simply select your size from the product page and click on 'Add to Wish List'",
                wishListV3Page.wishlistContent().getText());
    }

    @And("^I should see the correct empty closet message$")
    public void checkEmptyClosetMessage() throws Throwable {
        for(int i=0 ; i<10 ; i++) {
            if(wishListV3Page.wishlistContent().getText().contains("empty")) {
                i = 10;
            }else{
                Thread.sleep(1000);
            }
        }
        assertEquals("Empty Closet Text",
                "Your Closet is currently empty\n" +
                        "When you place an order, your puchases will appear here\n" +
                        "You can then add an item from your Closet to a Wish List so you can create or plan your future outfits",
                wishListV3Page.wishlistContent().getText());
    }

    @Then("^I should see those items in the correct chronological order with the correct details$")
    public void checkWishlistItemsOrder() throws Throwable {
        List<WishlistV3Product> singleWishlistProductsToCompare = (List<WishlistV3Product>) scenarioSession.getData("singleWishlistProducts");
        List<WishlistV3Product> singleWishlistProductsOnPage = wishListV3Page.getAllWishlistV3Products();

        assertThat("Not the expected number of wishlist items on the page", singleWishlistProductsToCompare.size(), equalTo(singleWishlistProductsOnPage.size()));

        //currently the next step can fail due to discrepancies in the case of the 'size' field. Therefore we convert all sizes string to lowercase beforehand
        //singleWishlistProductsOnPage = convertSizeToLowercase(singleWishlistProductsOnPage);
        //singleWishlistProductsToCompare = convertSizeToLowercase(singleWishlistProductsToCompare);

        Collections.reverse(singleWishlistProductsToCompare);  // descending date added order
        cleanOutSizesWeCannotCompare(singleWishlistProductsToCompare, singleWishlistProductsOnPage);
        assertReflectionEqualsNAP("Reverse chronological order check", singleWishlistProductsToCompare, singleWishlistProductsOnPage);
        Collections.reverse(singleWishlistProductsToCompare);
    }

    /**
     * Hacky method to clear out wishlist item sizes for products we could not retreive a size for from the product
     * details page.  E.g. Sold out items
     * @param singleWishlistProductsToCompare
     * @param singleWishlistProductsOnPage
     */
    private void cleanOutSizesWeCannotCompare(List<WishlistV3Product> singleWishlistProductsToCompare, List<WishlistV3Product> singleWishlistProductsOnPage){
        for (int i=0; i<singleWishlistProductsToCompare.size(); i++) {
            // Big assumption that the lists are in the same order... double checking PIDs match as a precaution
            if (singleWishlistProductsOnPage.get(i).getPid().equals(singleWishlistProductsToCompare.get(i).getPid())) {
                if (singleWishlistProductsToCompare.get(i).getSize() == null) {
                    singleWishlistProductsOnPage.get(i).setSize(null);
                }
            }
        }
    }

    @Then("^I should see items (\\d+) to (\\d+) in the correct chronological order with the correct details$")
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

    @And("^I should not see items (\\d+) to (\\d+) on the wishlist page$")
    public void checkItemsNotInWishlist(int from, int to) throws Throwable {

        List<WishlistV3Product> singleWishlistProductsToCompare = (List<WishlistV3Product>) scenarioSession.getData("singleWishlistProducts");
        singleWishlistProductsToCompare = singleWishlistProductsToCompare.subList(from-1, to-1);

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

    @And("^I should see the correct thumbnails for each item$")
    public void checkThumbnails() throws Throwable {
        List<WishlistV3Product> singleWishlistProducts = (List<WishlistV3Product>) scenarioSession.getData("singleWishlistProducts");
        List<WebElement> thumbnails = wishListV3Page.getWishlistThumbnails();

        Collections.reverse(singleWishlistProducts);

        for ( int i = 0 ; i < singleWishlistProducts.size() ; i++) {
            WishlistV3Product singleProduct = singleWishlistProducts.get(i);
            WebElement thumbnail = thumbnails.get(i);
            String nextSrc = thumbnail.getAttribute("src");
            assertTrue("Wishlist page thumbnail missing.  " +
                            "Expected PID " + singleProduct.getPid() + " in src, but got thumbnail.src='" + nextSrc + "'.  " +
                    "Thumbnail:" + thumbnail,
                    nextSrc.contains(singleProduct.getPid().toString()));
        }
        Collections.reverse(singleWishlistProducts);
    }

    @And("^I should see the correct size for each item$")
    public void checkSizes() throws Throwable {
        List<WishlistV3Product> singleWishlistProducts = (List<WishlistV3Product>) scenarioSession.getData("singleWishlistProducts");
        List<String> sizes = wishListV3Page.getWishlistSizes();
        Collections.reverse(singleWishlistProducts);

        for ( int i = 0 ; i < singleWishlistProducts.size() ; i++) {
            WishlistV3Product singleProduct = singleWishlistProducts.get(i);
            String size = sizes.get(i);

            if (singleProduct.getSize() != null) {
                // Removing spaces in sizes as this is inconsistent across the site
                assertThat("Incorrect size displayed for product " + singleProduct.getPid() + ". Does not match what's displayed on the product page",
                        size.replace(" ", ""), equalTo(singleProduct.getSize()));
            }
        }
        Collections.reverse(singleWishlistProducts);
    }

    @And("^I should be on the wishlist page called (.*)$")
    public void assert_current_wishlist_page(String wishlistName) throws Throwable {
        webBot.waitUntil(new com.google.common.base.Predicate<WebDriver>() {
            @Override
            public boolean apply(@Nullable WebDriver input) {
                return webBot.currentUrlContains("/wishlist/");
            }
        });

        if(wishlistName.toLowerCase().contains("all items") || wishlistName.toLowerCase().contains("all-items")) {
            assertTrue("The actual wishlist page doesn't match the expected wishlist page.", allItemsUrlIsCorrect());
        } else {
            assertThat("The actual wishlist page doesn't match the expected wishlist page.", webBot.getCurrentUrl(), equalTo(scenarioSession.getData("currentWishlistURL")));
        }
    }

    private boolean allItemsUrlIsCorrect() {
        String currentURL = webBot.getCurrentUrl();
        if(currentURL.contains("all-items") || currentURL.endsWith("/wishlist/")) {
            return true;
        }
        return false;
    }

    @And("^I should not be on any wishlist page$")
    public void assert_not_wishlist_page() {
        assertThat("The current page seems to be a wishlist page when it shouldn't be.", webBot.getCurrentUrl(), is(not(containsString("/wishlist"))));
    }

    @When("^I should be on the same url as before I updated my country and language preferences$")
    public void on_same_url_before_updating_preferences() throws Throwable {
        assertThat("The actual URL doesn't match the expected URL.", webBot.getCurrentUrl(), equalTo(scenarioSession.getData("currentWishlistURL")));
    }

    @And ("^I should be on This Page Cannot Be Found page$")
    public void I_should_be_on_the_page_not_found_page() throws Throwable {
        pageNotFoundPage.verifyOnPageNotFoundPage();
    }


    private List<WishlistV3Product> convertSizeToLowercase(List<WishlistV3Product> items) {

        for(int i=0; i < items.size(); i++) {
            if (items.get(i).getSize() != null) {
                items.get(i).setSize(items.get(i).getSize().toLowerCase());
            }
        }
        return items;
    }
}
