package com.netaporter.pws.automation.nap.cucumber.steps;

import com.netaporter.pws.automation.shared.steps.purchasePath.BasePurchasePathStep;
import cucumber.api.java.en.Then;

import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

/**
 * Created by ocsiki on 18/06/2014.
 */
public class NAPPurchasePathPaymentSteps extends BasePurchasePathStep {


    @Then("^the product is displayed correctly in the Payment page$")
    public void the_product_is_displayed_correctly_in_the_Payment_page() throws Throwable {
        List<String> pidsInPaymentPage = purchasePathPaymentPage.getPidsFromPaymentSummaryPage();
        String sku = getSkusFromScenarioSession().get(0);
        assertThat(pidsInPaymentPage.size(), is(1));
        assertThat(pidsInPaymentPage, hasItem(getPidFromSku(sku)));
    }

    @Then("^the security number textbox is displayed$")
    public void the_security_number_textbox_is_displayed() throws Throwable {
        assertTrue("Security Number text box is not displayed on the Payment Page.", purchasePathPaymentPage.isSecurityNumberTextBoxDisplayed());
    }

    @Then("^the nonreturnable product warning message is displayed in the payment summary page$")
    public void the_nonreturnable_product_warning_message_is_displayed_in_the_payment_summary_page() throws Throwable {
        String nonReturnableSku = getSkusFromScenarioSession().get(0);
        String pidFromSku = getPidFromSku(nonReturnableSku);
        assertTrue("The nonreturnable warning message is not displayed on the payment summary page for sku " +nonReturnableSku, purchasePathPaymentPage.isNonReturnableMessageDisplayedForSku(pidFromSku));
    }
}
