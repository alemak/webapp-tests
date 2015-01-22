package com.netaporter.pws.automation.shared.steps;

import com.netaporter.pws.automation.shared.components.PaymentForm;
import com.netaporter.pws.automation.shared.pages.paypal.LoginPage;
import com.netaporter.pws.automation.shared.pages.paypal.PayPalReviewPage;
import com.netaporter.pws.automation.shared.pages.paypal.PaypalBasePage;
import com.netaporter.pws.automation.shared.pages.purchasePath.PurchasePathPaymentPage;
import com.netaporter.pws.automation.shared.utils.WaitUtil;
import com.netaporter.test.client.product.pojos.CestaItem;
import com.netaporter.test.utils.cucumber.steps.LegacyWebAppBaseStep;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class PayPalSteps extends LegacyWebAppBaseStep {

    @Autowired
    PayPalReviewPage payPalReviewPage;

    @Autowired
    PaypalBasePage paypalBasePage;

    @Autowired
    LoginPage loginPage;

    @Autowired
    PurchasePathPaymentPage purchasePathPaymentPage;

    @Given("^I am on the Paypal login page$")
    public void confirmCurrentPageIsPaypalLogin() {
        // this method assumes that we're coming from the payment page
        // retry functionality added due to unstable Paypal sandbox

        int maxAttempts = 3;
        int attempt = 0;

        while (attempt < maxAttempts) {
            if(loginPage.getTitle().toLowerCase().contains("paypal"))
                return;

            purchasePathPaymentPage.clickPurchaseNow();
            attempt++;
            WaitUtil.waitFor(1000);
        }

        if (attempt == maxAttempts){
            throw new RuntimeException("The test was unable to pay with PayPal after " + maxAttempts + " attempts. Please execute manually.");
        }
    }

    @Given("^I login to Paypal with user email (.*) and password (.*)$")
    public void enterPaypalUserDetails(String userEmail, String password) throws InterruptedException {
        loginPage.enterUserDetails(userEmail, password);
        loginPage.clickLogInButton();

        scenarioSession.putData("paypalEmail", userEmail);
        scenarioSession.putData("paypalPassword", password);
    }

    @Given("^I click on the Pay Now button to confirm the Paypal payment$")
    public void clickPayNowButton() throws InterruptedException {
        payPalReviewPage.payNow();

        if (purchasePathPaymentPage.hasPaymentError()){
            retryPayPalPayment(2);
        }
    }

    @Given("^The postal address in my Paypal account is the same as in the order summary on the payment page")
    public void checkPostalAddress() {
        List<String> expectedShippingAddress = (List<String>) scenarioSession.getData("ShippingAddress");
        List<String> actualShippingAddress = payPalReviewPage.getShippingAddress();
        assertThat("Shipping address does not match", actualShippingAddress, equalTo(expectedShippingAddress));
    }

    @Given("^The postal address in my Paypal account contains the following character (.*)")
    public void checkPostalAddressIncludingSpecialCharacters(String expectedCharacter) {
        List<String> actualShippingAddress = payPalReviewPage.getShippingAddress();

        Boolean expectedCharacterMatched = false;
        for(String addressLine : actualShippingAddress){
            if (addressLine.contains(expectedCharacter)){
                expectedCharacterMatched = true;
            }
        }
        assertTrue("The postal address in PayPal should show the character: ".concat(expectedCharacter), expectedCharacterMatched);
    }

    @Given("^The total amount in my Paypal account is the same as in the order summary on the payment page$")
    public void checkOrderFinalAmount() {
        assertEquals("Value not the same", scenarioSession.getData("TotalAmount"), payPalReviewPage.getTotalAmount());
    }

    @Given("^The store credit amount applied in my Paypal account is the same as in the order summary on the payment page$")
    public void checkStoreCreditAmount() {
        assertEquals("Value not the same", "-".concat(scenarioSession.getData("store-credit").toString()), payPalReviewPage.getStoreCreditAmountInPaypal().toString());
    }

    @Given("^My registered email address is pre-populated on the Paypal login page$")
    public void checkRegisteredContactEmail() {
        String expectedCustomerEmail = scenarioSession.getData("registeredEmailAddress").toString();
        String actualCustomerEmail = loginPage.getContactEmail();
        assertEquals("Customer email not the same",expectedCustomerEmail,actualCustomerEmail);
    }

    @Given("^I am logged in to PayPal$")
    public void checkUserLoggedInToPaypal() {
        Assert.assertTrue(payPalReviewPage.isActiveAndDisplayed(), "Pay Pal - Review your Information Page is not displayed. Refer to screenshot.");
    }

    @Given("^I cancel my PayPal payment$")
    public void cancelPaypalPayment() throws InterruptedException {
        paypalBasePage.clickCancelPaymentButton();
    }

    @Given("The postage and packaging amount in my Paypal account is the same as in the order summary on the payment page$")
    public void checkPaypalPostageAndPackagingAmount() {
        BigDecimal expectedShippingCost = (BigDecimal) scenarioSession.getData("ShippingCost");
        BigDecimal actualShippingCost = payPalReviewPage.getPaypalPostageAndPackagingAmount();
        assertEquals("Shipping costs are not the same", expectedShippingCost, actualShippingCost);
    }

    @Given("^The list of items in my Paypal account are the same as in the order summary on the payment page$")
    public void checkShownItemsAgainstAgainstOnesHeldInSession() {
        List<CestaItem> orderItemsShownInPaypal = payPalReviewPage.getOrderItems();
        List<CestaItem> orderItemsShownInWebsitePaymentPage = (List<CestaItem>) scenarioSession.getData("OrderItems");

        assertEquals(orderItemsShownInPaypal.size(), orderItemsShownInWebsitePaymentPage.size());

        for (int i = 0; i < orderItemsShownInPaypal.size(); i++) {
            CestaItem paypalItem = orderItemsShownInPaypal.get(i);
            CestaItem websitePaymentPageItem = orderItemsShownInWebsitePaymentPage.get(i);

            assertNotNull(paypalItem);
            assertNotNull(websitePaymentPageItem);

            if (!paypalItem.isVoucherItem()) {
//                uncomment when there is a way to get tooltip text from paypal sandbox page
                assertThat(paypalItem.getProductName().toUpperCase(), endsWith(websitePaymentPageItem.getProductName().toUpperCase()));
            }
            else {
                Boolean matchedItem = false;
                String orderedItemDes = null;
                for(CestaItem orderedItem : orderItemsShownInWebsitePaymentPage){
                    orderedItemDes = orderedItem.getProductName();
                    if (paypalItem.getProductName().contains(orderedItemDes)){
                        matchedItem = true;
                    }
                }
                assertTrue("PayPal's list of items does not show the ordered item: " + orderedItemDes, matchedItem);
            }

            assertEquals("Item quantity not the same", paypalItem.getQuantity(), websitePaymentPageItem.getQuantity());
            assertEquals("Subtotal amount not the same", paypalItem.getSubtotal(), ensureDecimalNumber(websitePaymentPageItem.getSubtotal()));
            assertEquals("Not a voucher item", paypalItem.isVoucherItem(), websitePaymentPageItem.isVoucherItem());


            if (!paypalItem.isVoucherItem()) {
//                assertNotNull(paypalItem.getDesignerName());
//                assertThat(paypalItem.getDesignerName().toUpperCase(), startsWith(websitePaymentPageItem.getDesignerName().toUpperCase()));

                assertNotNull(paypalItem.getColor());
                assertThat(paypalItem.getColor().toUpperCase(),startsWith(websitePaymentPageItem.getColor().toUpperCase()));

                assertNotNull(paypalItem.getSize());
                assertThat(paypalItem.getSize().toUpperCase(), endsWith(websitePaymentPageItem.getSize().toUpperCase()));
            }
        }
    }

    @Then("^I should see a Paypal shipping error message$")
    public void I_should_see_a_Paypal_shipping_error_message() {
        assertTrue("Paypal error message not displayed", purchasePathPaymentPage.hasPaymentError());
        boolean found = false;

        for (String error : purchasePathPaymentPage.getErrorMessages()) {
            if(error.contains("PayPal has been unable to confirm your shipping address"))
                found = true;
        }

        assertTrue(found);
    }

    private BigDecimal ensureDecimalNumber(BigDecimal number) {
        if (number.scale() < 2) {
            return number.setScale(2);
        }
        return number;
    }

    private void retryPayPalPayment(Integer maxAttempts) throws InterruptedException {
        Integer attempt = 0;
        while (attempt < maxAttempts){
            // make sure that we really need to retry payment and that the order isn't already created.
            if (purchasePathPaymentPage.hasOrderNumberDisplayed()){
                return;
            }

            String paypalEmail = scenarioSession.getData("paypalEmail");
            String paypalPassword = scenarioSession.getData("paypalPassword");

            purchasePathPaymentPage.populatePaymentCardDetails(PaymentForm.getPaymentOption("PAYPAL"), null);
            purchasePathPaymentPage.clickPurchaseNow();
            enterPaypalUserDetails(paypalEmail, paypalPassword);
            payPalReviewPage.payNow();

            if (purchasePathPaymentPage.hasPaymentError()){
                attempt = attempt + 1;
            }
        }
        if (attempt == maxAttempts){
            throw new RuntimeException("The test was unable to pay with PayPal after " + maxAttempts + " attempts. Please execute manually.");
        }
    }
}