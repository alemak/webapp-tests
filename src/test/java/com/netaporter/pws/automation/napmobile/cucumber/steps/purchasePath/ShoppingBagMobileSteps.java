package com.netaporter.pws.automation.napmobile.cucumber.steps.purchasePath;

import com.netaporter.pws.automation.napmobile.cucumber.steps.BaseNapMobileSteps;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import static org.junit.Assert.assertTrue;

/**
 * Created by ocsiki on 05/08/2014.
 */
public class ShoppingBagMobileSteps extends BaseNapMobileSteps {

    @And("^I click proceed to purchase from the mobile shopping bag page$")
    public void I_click_proceed_to_purchase_from_the_mobile_shopping_bag_page() throws Throwable {
        napShoppingBagMobilePage.clickProceedToPurchase();
    }

    @And("^I click the shopping bag icon from the mobile product details page$")
    public void I_click_the_shopping_bag_icon_from_the_mobile_product_details_page() throws Throwable {
//        napProductDetailsMobilePage.clickShoppingBagIcon();
        napShoppingBagMobilePage.clickShoppingBagIcon();

    }

    @Then("^I am on shopping basket page$")
    public void I_am_on_shopping_basket_page() throws Throwable {
        assertTrue("Shopping bag page is not displayed",napShoppingBagMobilePage.getShoppingBasketUrl().contains("/shoppingbag.nap"));
    }
}