package com.netaporter.pws.automation.napmobileweb.cucumber.steps.wishlistv3;

import com.netaporter.pws.automation.napmobileweb.cucumber.steps.BaseMobileNapSteps;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.WishlistV3Product;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: a.michael@london.net-a-porter.com
 * Date: 12/11/2013
 * Time: 4:44PM
 * (C) DevilRacing666
 */
public class MobileDeleteItemSteps extends BaseMobileNapSteps {

    @When("^I delete item (\\d+) from my mobile wishlist$")
    public void deleteItem(Integer itemNumber) throws Throwable {
        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");
        String pid = skuToPid(skuList.get(itemNumber - 1));
        Thread.sleep(1000);
        wishListV3Page.clickProductRemoveFirstTime(pid);
        Thread.sleep(1000);
        wishListV3Page.clickProductRemoveAgain();
        Thread.sleep(1000);
    }

    @When("^I highlight item (\\d+) for deletion from my mobile wishlist$")
    public void highlightForDeleteItem(Integer itemNumber) throws Throwable {
        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");
        String pid = skuToPid(skuList.get(itemNumber - 1));
        wishListV3Page.highlightProductRemove(pid);
        Thread.sleep(1500);
    }

    @Then("^item (\\d+) should be highlighted for deletion$")
    public void checkForHighlightedDelete(Integer itemNumber) throws Throwable {
        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");
        String pid = skuToPid(skuList.get(itemNumber - 1));
        assertTrue("Is not highlighted for deletion "+pid, wishListV3Page.isHighlightedForProductRemove(pid));
    }

    @And("^wishlist item (\\d+) is not on the mobile page$")
    public void checkPidIsNotOnPage(Integer itemNumber) throws Throwable {
        List<WishlistV3Product> singleWishlistProducts = new ArrayList();
        if (scenarioSession.getData("singleWishlistProducts") != null ) {
            singleWishlistProducts = (List<WishlistV3Product>) scenarioSession.getData("singleWishlistProducts");
        }
        String pid = singleWishlistProducts.get(itemNumber-1).getPid().toString();
        assertTrue("This pid should not be on the page yet! "+pid,!(wishListV3Page.doesPidExistsOnPage(pid)));
    }

    @Then("^wishlist item (\\d+) should be on the mobile page$")
    public void checkPidIsOnPage(Integer itemNumber) throws Throwable {
        List<WishlistV3Product> singleWishlistProducts = new ArrayList();
        if (scenarioSession.getData("singleWishlistProducts") != null ) {
            singleWishlistProducts = (List<WishlistV3Product>) scenarioSession.getData("singleWishlistProducts");
        }
        String pid = singleWishlistProducts.get(itemNumber-1).getPid().toString();
        assertTrue("This pid should be on the page! "+pid,wishListV3Page.doesPidExistsOnPage(pid));
    }
}
