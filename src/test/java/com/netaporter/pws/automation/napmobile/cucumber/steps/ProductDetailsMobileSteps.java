package com.netaporter.pws.automation.napmobile.cucumber.steps;

import cucumber.api.java.en.And;

/**
 * Created by ocsiki on 05/08/2014.
 */
public class ProductDetailsMobileSteps extends BaseNapMobileSteps{


    @And("^I add the product to the shopping bag from the mobile product details page$")
    public void I_add_the_product_to_the_shopping_bag_from_the_mobile_product_details_page() throws Throwable {
        napProductDetailsMobilePage.clickAddToShoppingBag();
    }


}