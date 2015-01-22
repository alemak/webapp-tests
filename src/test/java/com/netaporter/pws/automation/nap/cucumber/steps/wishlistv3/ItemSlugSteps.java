package com.netaporter.pws.automation.nap.cucumber.steps.wishlistv3;

import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created with IntelliJ IDEA.
 * User: a.michael@london.net-a-porter.com
 * Date: 14/11/2013
 * Time: 12:18PM
 * (C) DevilRacing666
 */
public class ItemSlugSteps extends BaseNapSteps {

    @Then("^item (\\d+) should have a '(Sold Out|On Sale|Low Stock|No Slug)' slug$")
    public void checkSlugCorrect(Integer itemNumber, String expectedSlug) {
        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");
        String sku = skuList.get(itemNumber - 1);
        String actualSlug = wishListV3Page.getSlugForPid(skuToPid(sku));
        assertEquals("Incorrect slug for sku " + sku +" | ", expectedSlug.toLowerCase(), actualSlug.toLowerCase());
    }

    @Then("^the customer care overlay is displayed$")
    public void isCustomerCareOverlayDisplayed() throws Throwable {
        assertTrue(wishListV3Page.customerCareOverlayIsVisible());
    }

}
