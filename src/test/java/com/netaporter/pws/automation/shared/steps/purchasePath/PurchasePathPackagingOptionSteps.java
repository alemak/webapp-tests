package com.netaporter.pws.automation.shared.steps.purchasePath;

import com.netaporter.pws.automation.shared.pojos.UserOption;
import com.netaporter.pws.automation.shared.utils.IPromotionsAPI;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class PurchasePathPackagingOptionSteps extends BasePurchasePathStep {

    List<Integer> promotions = new ArrayList<Integer>();

    @Autowired
    IPromotionsAPI promotionsAPI;

    @Before("@packaging")
    public void disableExistingPromotions()  {
        promotions = promotionsAPI.disableAllEnabledPromotions();
    }

    @After("@packaging")
    public void enablePreviouslyDisabledPromotions() {
        promotionsAPI.enablePromotions(promotions);
    }

    // allow for partial names by using .startsWith instead of a string comparison
    @Given("^Packaging option (.*) (is|is not) available$")
    public void packagingOptionIsAvailable(String optionName, String shouldBePresent) {
        boolean positiveCheck = "is".equals(shouldBePresent);
        boolean found = false;
        List<String> xs = purchasePathShippingMethodPage.getAvailablePackagingOptions();

        for(String s : xs) {
            if(s.startsWith(optionName)) {
                found = true;
                break;
            }
        }

        Assert.assertEquals(positiveCheck, found);
    }

    @Given("^I select to use packaging option (.*)$")
    public void selectPackagingOption(String optionName) {
        purchasePathShippingMethodPage.setSelectedPackagingOption(optionName);
        Assert.assertTrue(purchasePathShippingMethodPage.getSelectedPackagingOption().startsWith(optionName));
    }

    @Given("^The image for the packaging options is a (bag|box)$")
    public void checkPackagingOptionImage(String expectedPackagingType) {
        for(UserOption o : purchasePathShippingMethodPage.getAvailablePackagingUserOptions()) {
            Assert.assertTrue(o.getUrl().contains(expectedPackagingType));
        }
    }

}