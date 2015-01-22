package com.netaporter.pws.automation.shared.steps.purchasePath;

import com.netaporter.pws.automation.shared.pojos.UserOption;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;

import cucumber.api.java.en.Then;
import org.junit.Assert;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.junit.Assert.*;
@Component
public class PurchasePathShippingOptionSteps extends BasePurchasePathStep {

    private List shippingOptionDetails;

    @Given("^I click proceed to purchase from the shipping options page$")
    public void clickProceedToPurchaseFromShippingPage() {
        purchasePathShippingMethodPage.clickProceedToPurchase();
    }

    @Given("^I click to edit the shipping address from the shipping options page$")
    public void clickEditShippingAddress() {
        purchasePathShippingMethodPage.clickEditShippingAddress();
    }

    @Then("^Shipping type (.*) (is|is not) available$")
    public void check_shipping_method_option_type(String shippingMethodType, String isAvailable) throws Throwable {
        boolean positiveCheck = (isAvailable.equals("is")) ? true : false;
        assertEquals(positiveCheck, purchasePathShippingMethodPage.getAllShippingOptionTypes().contains(shippingMethodType));
    }

    // allow for partial names by using .startsWith instead of a string comparison
    @Given("^Shipping option (.*) is available$")
    public void shippingOptionIsAvailable(String shippingOptionName) {
        List<UserOption> shippingOptions = purchasePathShippingMethodPage.getShippingOptions();

        for(UserOption shippingOption : shippingOptions) {
            if(shippingOption.getOptionName().startsWith(shippingOptionName)) {
                return;
            }
        }
        fail("Shipping option not available");
    }

    // allow for partial names by using .startsWith instead of a string comparison
    @Given("^Shipping option (.*) is not available$")
    public void shippingOptionIsNotAvailable(String shippingOptionName) {
        List<UserOption> shippingOptions = purchasePathShippingMethodPage.getShippingOptions();

        if(shippingOptions.size()==0)
            fail("No shipping options found");

        for(UserOption shippingOption : shippingOptions) {
            if(shippingOption.getOptionName().startsWith(shippingOptionName)) {
                fail("Shipping option " + shippingOptionName + " should not have been present");
            }
        }
    }

    @Given("^I select to use shipping option (.*)$")
    public void selectShippingOption(String optionName) {
        purchasePathShippingMethodPage.setSelectedShippingOption(optionName);
        Assert.assertTrue(purchasePathShippingMethodPage.getSelectedShippingOption().startsWith(optionName));
    }

    @Then("^Free shipping is not applied to any shipping options$")
    public void checkShippingOptionCostIsNotFree() {
        List<UserOption> shippingOptions = purchasePathShippingMethodPage.getShippingOptions();
        for(UserOption shippingOption : shippingOptions) {
            assertFalse(shippingOption.isFree());
        }
    }

    @Then("^Free shipping is applied to shipping option (.*)$")
    public void checkFreeShippingIsAppliedToShippingOption(List<String> shippingOptionNames) {
        List<UserOption> shippingOptions = purchasePathShippingMethodPage.getShippingOptions();
        assertNotNull(shippingOptions);

        for(UserOption shippingOption : shippingOptions) {
            String shippingOptionName = shippingOption.getOptionName().split(",")[0];

            if(shippingOptionNames.contains(shippingOptionName)) {
                assertTrue(shippingOption.isFree());
            } else {
                assertFalse(shippingOption.isFree());
            }
        }
    }

    @And("^I save the shipping option details$")
    public void saveShippingOptionDetails() {
        shippingOptionDetails = purchasePathShippingMethodPage.getShippingOptions();
    }

    @Then("^The shipping sku is (.*)$")
    public void checkShippingSKU(String shippingSKU){
        String actualShippingSKU = purchasePathShippingMethodPage.getSelectedShippingMethodSKU();
        assertEquals(shippingSKU,actualShippingSKU);
    }

    @Then("^The shipping cost is (.*)$")
    public void checkShippingCost(String shippingCost){
        String actualShippingCost = ((UserOption) shippingOptionDetails.get(0)).getCost();
        assertEquals(shippingCost,actualShippingCost);
    }

    @Then("^The shipping option notes contains (.*)$")
    public void checkShippingDescription(String shippingNotes){
        String actualShippingNotes = purchasePathShippingMethodPage.getShippingNotes();
        assertEquals(shippingNotes,actualShippingNotes);
    }

}