package com.netaporter.pws.automation.shared.steps.wishlistv3;

import com.netaporter.pws.automation.shared.pojos.WishlistV3.WishlistV3Product;
import cucumber.api.java.en.Then;
import org.openqa.selenium.By;

import java.util.Collections;
import java.util.List;

import static com.netaporter.test.utils.assertion.objectcomparison.NapReflectionAssert.assertReflectionEqualsNAP;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Date: 17/10/2013
 * Time: 14:50
 */
public class SharedPageVerificationSteps extends SharedWishlistBaseSteps {

    @Then("^I should see the wishlist items ordered by date added descending without duplicates$")
    public void checkAllWishlistItemsOrder() throws Throwable {
        checkAllWishlistItemsOrder(1, ((List<WishlistV3Product>) scenarioSession.getData("singleWishlistProducts")).size());
    }

    @Then("^I should see the wishlist items ([0-9]+) to ([0-9]+) ordered by date added descending without duplicates$")
    public void checkAllWishlistItemsOrder(Integer from, Integer to) throws Throwable {
        List<WishlistV3Product> allWishlistProductsToCompare = (List<WishlistV3Product>) scenarioSession.getData("singleWishlistProducts");
        List<WishlistV3Product> allWishlistProductsOnPage = wishListV3Page.getAllWishlistV3Products();

        Collections.reverse(allWishlistProductsToCompare);  // descending date added order

        List<WishlistV3Product> subsetWishlistProductsToCompare = allWishlistProductsToCompare.subList(from-1, to);

        assertHowManyWishlistItemsOnPage(subsetWishlistProductsToCompare.size());

        assertReflectionEqualsNAP("Reverse chronological order check", subsetWishlistProductsToCompare, allWishlistProductsOnPage);

        Collections.reverse(allWishlistProductsToCompare);
    }

    @Then("^I should see ([0-9]+) wishlist items$")
    public void assertHowManyWishlistItemsOnPage(Integer expected) throws Throwable {
        assertThat("Not the expected number of wishlist items on the page", wishListV3Page.numberOfItemsShowingOnPage(), equalTo(expected));
    }
}
