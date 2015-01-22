package com.netaporter.pws.automation.napmobile.cucumber.steps.purchasePath;

import com.netaporter.pws.automation.napmobile.cucumber.steps.BaseNapMobileSteps;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.core.Is;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by ocsiki on 05/08/2014.
 */
public class PurchasePathMobileSteps extends BaseNapMobileSteps {

    @And("^I sign in as guest in the mobile purchase path$")
    public void I_sign_in_in_the_mobile_purchase_path() throws Throwable {
        napPurchasePathSignInMobilePage.signInAsGuest();
    }

    @And("^I continue to the mobile shipping options page$")
    public void I_continue_to_the_mobile_shipping_options_page() throws Throwable {
        napPurchasePathShippingAddressMobilePage.clickContinue();
    }

    @And("^I click proceed to purchase on the mobile shipping options page$")
    public void I_click_proceed_to_purchase_on_the_mobile_shipping_options_page() throws Throwable {
        napPurchasePathShippingOptionsMobilePage.clickProceedToPurchase();
    }

    @Then("^I see the order confirmation message on the confirmation page$")
    public void I_am_on_the_mobile_order_confirmation_page() throws Throwable {
        assertThat(napPurchasePathOrderConfirmationMobilePage.getThankYouMessage(), is("Thank you for your order."));
    }

    @And("^I pay on mobileweb by (VISA_DEBIT)$")
    public void I_pay_on_mobileweb_by_card_type(String cardType) throws Throwable {
        napPurchasePathPaymentSummaryMobilePage.payBy(cardType);
    }

    @Then("^I see the (.*) on the mobile shipping options page$")
    public void I_see_the_shippingMethods_on_the_mobile_shipping_options_page(ArrayList<String> shippingOptions) throws Throwable {
        String[] allShippingOptionsOnPage = napPurchasePathShippingOptionsMobilePage.getAvailableShippingMethods();
        assertThat(allShippingOptionsOnPage.length, Is.is(shippingOptions.size()));
        for (int i=0;i<allShippingOptionsOnPage.length;i++) {
            assertThat(allShippingOptionsOnPage[i], Is.is(shippingOptions.get(i)));
        }

    }

    @And("^the cutoff message is displayed in the mobile shipping options page$")
    public void the_cutoff_message_is_displayed_in_the_mobile_shipping_options_page() throws Throwable {
        List<WebElement> deliveryMessageElements = napPurchasePathShippingOptionsMobilePage.getDeliveryMessageElements();
        assertThat(deliveryMessageElements.size(), is(2));
        assertThat(deliveryMessageElements.get(1).getText(), containsString("Next Business Day orders placed after 3pm on Friday will be delivered on Tuesday"));
    }

    @When("^I select the (Next Business Day) shipping method on the mobile page$")
    public void I_select_the_Next_Day_shipping_method_on_the_mobile_page(String shippingMethod) throws Throwable {
        napPurchasePathShippingOptionsMobilePage.selectShippingMethod(shippingMethod);
    }

    @Then("^the (Next Business Day) shipping method is displayed on the mobile payment summary page$")
    public void the_shippingMethod_is_displayed_on_the_mobile_payment_summary_page(String expectedShippingMethod) throws Throwable {
        assertThat(napPurchasePathPaymentSummaryMobilePage.getDisplayedShippingMethod(), is(expectedShippingMethod));
    }

    @And("^the (Next Business Day) is displayed on the mobile confirmation page$")
    public void the_shippingMethod_is_displayed_on_the_mobile_confirmation_page(String expectedShippingMethod) throws Throwable {
        assertThat(napPurchasePathOrderConfirmationMobilePage.getDisplayedShippingMethod(), is(expectedShippingMethod));

    }
}
