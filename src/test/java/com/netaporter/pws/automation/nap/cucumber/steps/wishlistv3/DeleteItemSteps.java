package com.netaporter.pws.automation.nap.cucumber.steps.wishlistv3;

import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
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
 * Date: 18/09/2013
 * Time: 2:53PM
 * (C) DevilRacing666
 */
public class DeleteItemSteps extends BaseNapSteps {

    @When("^I delete item (\\d+) from my wishlist$")
    public void deleteItem(Integer itemNumber) throws Throwable {
        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");
        String pid = skuToPid(skuList.get(itemNumber - 1));
        boolean skipCheck = false;
        Thread.sleep(2500);
        
        if (wishListV3Page.numberOfItemsShowingOnPage().equals(1)) {
            skipCheck = true;
        }

        wishListV3Page.clickProductRemove(pid);

        if (!skipCheck) {
            int i = 0;
            while (wishListV3Page.doesPidExistsOnPage(pid) && i < 10) {
                Thread.sleep(1000);
                i++;
            }
        } else {
            Thread.sleep(2500);
        }

    }

    @And("^wishlist item (\\d+) is not on the page$")
    public void checkPidIsNotOnPage(Integer itemNumber) throws Throwable {
        List<WishlistV3Product> singleWishlistProducts = new ArrayList();
        if (scenarioSession.getData("singleWishlistProducts") != null ) {
            singleWishlistProducts = (List<WishlistV3Product>) scenarioSession.getData("singleWishlistProducts");
        }
        String pid = singleWishlistProducts.get(itemNumber-1).getPid().toString();
        assertTrue("This pid should not be on the page yet! "+pid,!(wishListV3Page.doesPidExistsOnPage(pid)));
    }

    @Then("^wishlist item (\\d+) should be on the page$")
    public void checkPidIsOnPage(Integer itemNumber) throws Throwable {

        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");

        String pid = skuToPid(skuList.get(itemNumber-1));
        int i = 0;
        while(!wishListV3Page.doesPidExistsOnPage(pid) && i < 10) {
            Thread.sleep(1000);
            i++;
        }
        assertTrue("This pid should be on the page! "+pid,wishListV3Page.doesPidExistsOnPage(pid));
    }
}
