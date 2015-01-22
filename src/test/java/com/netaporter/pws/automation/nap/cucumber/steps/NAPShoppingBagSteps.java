package com.netaporter.pws.automation.nap.cucumber.steps;

import com.netaporter.pws.automation.shared.steps.purchasePath.BasePurchasePathStep;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

/**
 * Created by ocsiki on 16/06/2014.
 */
public class NAPShoppingBagSteps extends BasePurchasePathStep {

    @Then("^the NAP shopping bag overlay is displayed correctly$")
    public void the_NAP_shopping_bag_overlay_is_displayed_correctly() throws Throwable {
       assertTrue(productDetailsPage.isShoppingBagOverlayDisplayed());
    }

    @And("^the NAP shopping bag overlay contains the correct product$")
    public void the_NAP_shopping_bag_overlay_contains_the_correct_product() throws Throwable {
        String pidAddedToBag = getPidFromSku(getSkusFromScenarioSession().iterator().next());
        assertThat(productDetailsPage.getPidFromMiniShoppingBagOverlay(), is(pidAddedToBag));
    }

    @And("^I record the shipping price from the shopping bag$")
    public void I_record_the_shipping_price_from_the_shopping_bag() throws Throwable {
        scenarioSession.putData(SHIPPING_PRICE_FROM_SHOPPING_BAG_PAGE, shoppingBagPage.getShippingPrice());
    }

    @Then("^The product shows out of stock in the Shopping Bag page$")
    public void The_product_shows_out_of_stock_in_the_Shopping_Bag_page() throws Throwable {
        assertTrue("First product does not have the sold out message in the Shopping Bag", shoppingBagPage.isFirstProductSoldOut());
    }

    @Then("^the nonreturnable product warning message is displayed in the shopping bag page$")
    public void the_nonreturnable_product_warning_message_is_displayed_in_the_shopping_bag_page() throws Throwable {
        ArrayList<String> skusFromScenarioSession = getSkusFromScenarioSession();
        String nonReturnableSkus = skusFromScenarioSession.get(0);
        assertTrue("Nonreturnable warning message is not displayed for sku "+nonReturnableSkus, shoppingBagPage.isNonReturnableMessageDisplayedForSku(getPidFromSku(nonReturnableSkus)));
    }
}