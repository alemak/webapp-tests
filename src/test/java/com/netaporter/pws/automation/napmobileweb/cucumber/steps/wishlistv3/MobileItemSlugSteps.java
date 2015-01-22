package com.netaporter.pws.automation.napmobileweb.cucumber.steps.wishlistv3;

import com.netaporter.pws.automation.napmobileweb.cucumber.steps.BaseMobileNapSteps;
import cucumber.api.java.en.Then;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: a.michael@london.net-a-porter.com
 * Date: 18/11/2013
 * Time: 12:26PM
 * (C) DevilRacing666
 */
public class MobileItemSlugSteps extends BaseMobileNapSteps {

    @Then("^mobile item (\\d+) should have a '(Sold Out|On Sale|Low Stock|No Slug)' slug$")
    public void checkSlugCorrect(Integer itemNumber, String expectedSlug) {
        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");
        String sku = skuList.get(itemNumber - 1);
        String actualSlug = wishListV3Page.getSlugForPid(skuToPid(sku));
        assertEquals("Incorrect slug for sku " + sku +" | ", expectedSlug.toLowerCase(), actualSlug.toLowerCase());
    }
}
