package com.netaporter.pws.automation.nap.cucumber.steps.wishlistv3;

import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.WishlistV3Product;
import cucumber.api.java.en.And;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: a.michael
 * Date: 23/05/2014
 * Time: 9:31AM
 * (C) DevilRacing666
 */
public class ClosetSteps extends BaseNapSteps {

    @And("^I add product (\\d+) to my shopping bag$")
    public void addToShoppingBag(int item) throws Throwable {
        List<WishlistV3Product> singleWishlistProducts = new ArrayList();
        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");

        productDetailsPage.addIntoShoppingBag(skuList.get(item-1));
        wishListV3Page.waitForShoppingBagPopupToBeDisplayed();
        wishListV3Page.waitForShoppingBagPopupToNotBeDisplayed();

    }

    @And("^I click 'Proceed to Purchase' on the product details page$")
    public void clickProceedToPurchasePDP() {
        productDetailsPage.clickProceedToPurchase();
    }
}
