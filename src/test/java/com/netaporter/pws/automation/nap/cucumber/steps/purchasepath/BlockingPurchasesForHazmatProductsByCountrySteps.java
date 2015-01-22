package com.netaporter.pws.automation.nap.cucumber.steps.purchasepath;

import com.netaporter.pws.automation.nap.pages.PurchasePathFlowPage;
import cucumber.api.java.After;
import cucumber.api.java.en.Then;

import static org.junit.Assert.assertTrue;

public class BlockingPurchasesForHazmatProductsByCountrySteps extends ProductRestrictionsSteps {

    @After
    public void afterScenario() {
        removeHazmatRestriction();
    }

    @Then("^I should be blocked from purchasing and prompted with an shipping restriction message$")
    public void I_should_be_blocked_from_purchasing_and_prompted_with_an_shipping_restriction_message() throws Throwable {
        purchasePathFlowPage.assertState(PurchasePathFlowPage.FlowState.PAYMENT);
        String errorMessage = purchasePathFlowPage.getTopErrorMessageOnPaymentPage();
        assertTrue(errorMessage.contains("Some items can not be shipped to your chosen destination."));
    }
}
