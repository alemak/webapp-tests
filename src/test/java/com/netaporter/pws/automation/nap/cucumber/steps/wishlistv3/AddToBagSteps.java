package com.netaporter.pws.automation.nap.cucumber.steps.wishlistv3;

import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.pws.automation.shared.pojos.Product;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.WishlistV3Product;
import com.netaporter.test.client.product.pojos.SearchableProduct;
import cucumber.api.java.en.*;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: a.michael@london.net-a-porter.com
 * Date: 01/08/2013
 * Time: 10:25AM
 * (C) DevilRacing666
 */
public class AddToBagSteps extends BaseNapSteps {

    @When("^I click on add to bag on wishlist item (\\d+)$")
    public void addToBag(int itemNumber) throws Throwable {
        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");

        String pid = skuToPid(skuList.get(itemNumber-1));

        Thread.sleep(1000);
        wishListV3Page.clickProductAddToBag(pid);

        wishListV3Page.waitForShoppingBagPopupToBeDisplayed();
        wishListV3Page.waitForShoppingBagPopupToNotBeDisplayed();
    }

    @And("^I click on add to bag on all items$")
    public void clickAllAddToBag() throws Throwable {
        wishListV3Page.clickAllAddToBag();
    }

    @When("^I click on add to bag for wishlist item (\\d+) and wait for the overlay to show$")
    public void addToBagWithoutWait(int itemNumber) throws Throwable {
        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");

        String pid = skuToPid(skuList.get(itemNumber-1));

        Thread.sleep(1000);
        wishListV3Page.clickProductAddToBag(pid);

        int timeout = 0;

        while(!wishListV3Page.isShoppingBagPopupDisplayed()) {
            Thread.sleep(100);
            timeout = timeout + 1;
            if (timeout >= 100) {
                fail("Shopping Bag Popup did not appear.");
            }
        }
    }

    @Then("^no items should have the add to bag button$")
    public void checkAddToBagButtons() throws Throwable {
        assertFalse("Add To Bag icons incorrectly present on SOLD OUT items.",wishListV3Page.isAddToBagIconPresentOnPage());
    }

    @Then("^wishlist item (\\d+) should be in the shopping bag (\\d+) times?$")
    public void checkBagForPid(int itemNumber, int numberOfTimesExpected) throws Throwable {
        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");
        String sku = skuList.get(itemNumber-1);

        shoppingBagPage.go();

        SearchableProduct searchableProduct = new SearchableProduct(1,"TEMP","TEMP");
        Product product = new Product(searchableProduct, sku);

        int numberOfTimesActual = shoppingBagPage.isProductInShoppingBagTimes(product);

        assertEquals("Expected sku " + (itemNumber) + " (" + sku + ") to exist " + numberOfTimesExpected + " times:"
                ,numberOfTimesExpected,numberOfTimesActual);
    }

    @Then("^wishlist item (\\d+) should be in the shopping bag less than (\\d+) times?$")
    public void checkBagForPidLessThan(int itemNumber, int numberOfTimesExpected) throws Throwable {
        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");
        String sku = skuList.get(itemNumber-1);

        shoppingBagPage.go();

        SearchableProduct searchableProduct = new SearchableProduct(1,"TEMP","TEMP");
        Product product = new Product(searchableProduct, sku);

        int numberOfTimesActual = shoppingBagPage.isProductInShoppingBagTimes(product);
        assertTrue("Expected sku " + (itemNumber) + " (" + sku + ") to exist less than " + numberOfTimesExpected + " times:"
                ,numberOfTimesExpected > numberOfTimesActual);
    }

    @Then("^the recently added overlay should appear with the details of item (\\d+) in it$")
    public void checkMiniBagPopupAppears(int itemNumber) throws Throwable {
        List<WishlistV3Product> wishlistV3Products = (List<WishlistV3Product>) scenarioSession.getData("singleWishlistProducts");
        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");
        String pid = skuToPid(skuList.get(itemNumber-1));
        String size = wishlistV3Products.get(itemNumber-1).getSize();

        assertThat("Pid "+pid+" is not in the shopping bag overlay", wishListV3Page.isPidInshoppingBagPopup(pid,size));
    }

    @And("^the shopping bag icon shows (\\d+) items?$")
    public void shoppingBagIconValue(Integer expectedValue) throws Throwable {
        Integer actualValue = wishListV3Page.numberOfItemsInMiniBag();

        int i = 0;
        while(actualValue.toString().length() == 0 && i < 10) {
            Thread.sleep(1000);
            i++;
            actualValue = wishListV3Page.numberOfItemsInMiniBag();
        }

        assertEquals("Shopping bag icon shows incorrect value. " +
                        "Expected " + expectedValue + " but has " + actualValue, actualValue, expectedValue);
    }

}
