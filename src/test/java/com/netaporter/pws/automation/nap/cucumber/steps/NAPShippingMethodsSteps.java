package com.netaporter.pws.automation.nap.cucumber.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;

/**
 * Created by ocsiki on 04/07/2014.
 */
public class NAPShippingMethodsSteps extends BaseNapSteps {

    @Then("^I should see (.+) shipping methods$")
    public void I_should_see_Premier_Daytime_Premier_Evening_Next_Day_and_Standard_shipping_methods(ArrayList<String> shippingOptions) throws Throwable {
        //expected shipping methods need to be in the same order as on the website
        String[][] allShippingOptionsOnPage = purchasePathFlowPage.getAllShippingOptions();
        assertThat(allShippingOptionsOnPage.length, is(shippingOptions.size()));
        for (int i=0;i<allShippingOptionsOnPage.length;i++) {
            assertThat(allShippingOptionsOnPage[i][1], is(shippingOptions.get(i)));
        }
    }

    @When("^I select the (.*) NAP shipping method$")
    public void I_select_the_desiredShippingMethod_NAP_shipping_method(String shippingMethod) throws Throwable {
        purchasePathFlowPage.selectShippingMethodByName(shippingMethod);
    }

    @Then("^the shipping method from the NAP payment summary page is (.*)$")
    public void the_shipping_method_from_the_NAP_payment_summary_page_is_Premier(String shippingMethod) throws Throwable {
        assertThat(purchasePathFlowPage.getShippingMethodOnPaymentPage(), containsString(shippingMethod));
    }

    @Then("^the shipping options from the NAP order confirmation page is (.*)$")
    public void the_shipping_options_from_the_NAP_order_confirmation_page_is_Premier(String shippingMethod) throws Throwable {
       assertThat(purchasePathFlowPage.getShippingMethodOnOrderConfirmationPage(), containsString(shippingMethod));
    }

    @And("^I should see (.*) cut off message$")
    public void I_should_see_NextDayCutOffMessage_cut_off_message(String message) throws Throwable {
        if(message.equalsIgnoreCase("Empty Message")) {
            assertThat(purchasePathFlowPage.getNextDayCutOffMessage(), containsString("false"));
        }else{
            assertThat(purchasePathFlowPage.getNextDayCutOffMessage(), containsString(message));
        }

    }

    @Then("^the shipping price from the shopping bag is the lowest shipping price from the shipping options page$")
    public void the_shipping_price_from_the_shopping_bag_is_the_lowest_shipping_price_from_the_shipping_options_page() throws Throwable {
        List<String> shippingMethodPricesOnShippingOptionsPage = purchasePathFlowPage.getShippingMethodPricesOnShippingOptionsPage();
        Float priceFromShoppingBag = Float.parseFloat(scenarioSession.<String>getData(SHIPPING_PRICE_FROM_SHOPPING_BAG_PAGE));
        int lowestPrice = Integer.parseInt(shippingMethodPricesOnShippingOptionsPage.get(0).replaceAll("[^0-9.]",""));

        for (String shippingMethodPrice : shippingMethodPricesOnShippingOptionsPage) {
            int priceInteger = Integer.parseInt(shippingMethodPrice.replaceAll("[^0-9.]", ""));
            if (priceInteger <lowestPrice)
               lowestPrice = priceInteger;
        }
        assertThat(priceFromShoppingBag.intValue(), is(lowestPrice));
    }
}