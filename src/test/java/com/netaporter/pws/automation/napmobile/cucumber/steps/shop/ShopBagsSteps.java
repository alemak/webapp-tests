package com.netaporter.pws.automation.napmobile.cucumber.steps.shop;

import com.netaporter.pws.automation.napmobile.cucumber.steps.BaseNapMobileSteps;
import cucumber.api.java.en.Then;
import org.junit.Assert;

public class ShopBagsSteps extends BaseNapMobileSteps {

    @Then("^I will be at the shop Bags page$")
    public void I_will_be_at_the_shop_bags_page() throws Throwable {
        Assert.assertTrue(
                "Page heading = " + titleMobileComponent.titleMainHeadingText(),
                shopBagsMobilePage.at()
        );
    }
}
