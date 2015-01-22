package com.netaporter.pws.automation.shared.steps.purchasePath;

import com.netaporter.pws.automation.shared.components.PaymentForm;
import com.netaporter.pws.automation.shared.steps.ProductServiceSteps;
import com.netaporter.pws.automation.shared.utils.CardPaymentDetails;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static com.netaporter.pws.automation.shared.steps.purchasePath.PurchasePathAddressSteps.BILLING;
import static com.netaporter.pws.automation.shared.steps.purchasePath.PurchasePathAddressSteps.SHIPPING;

public class PurchasePathAggregatedSteps extends BasePurchasePathStep {
    @Autowired
    protected PurchasePathSignInSteps purchasePathSignInSteps;
    @Autowired
    protected PurchasePathAddressSteps purchasePathAddressSteps;
    @Autowired
    protected PurchasePathPackagingOptionSteps purchasePathPackagingOptionSteps;
    @Autowired
    protected PurchasePathShippingOptionSteps purchasePathShippingOptionSteps;
    @Autowired
    protected PurchasePathPaymentSteps purchasePathPaymentSteps;
    @Autowired
    protected PurchasePathOrderConfirmationSteps purchasePathOrderConfirmationSteps;
    @Autowired
    protected ProductServiceSteps productServiceSteps;

    @Given("^I complete a purchase anonymously and my order is confirmed$")
    public void completeAnonymousPurchase() {
        purchasePathSignInSteps.anonymousSignIn();
        purchasePathAddressSteps.fillUKLondonAddress(SHIPPING);
        purchasePathAddressSteps.clickProceedToPurchaseFromAddressPage(SHIPPING);
        purchasePathShippingOptionSteps.clickProceedToPurchaseFromShippingPage();
        purchasePathPaymentSteps.assertShippingAndBillingAddressesAreDifferent("the same");
        purchasePathPaymentSteps.completePaymentDetails("VISA_CREDIT_CARD");
        purchasePathPaymentSteps.clickPurchaseNow();
        purchasePathOrderConfirmationSteps.orderIsConfirmed();
    }

    @Given("^I complete a purchase anonymously with different shipping, billing addresses and my order is confirmed$")
    public void completeAnonymousPurchaseWithDifferentShippingBillingAddresses() {
        purchasePathSignInSteps.anonymousSignIn();
        purchasePathAddressSteps.fillUKLondonAddress(SHIPPING);
        purchasePathAddressSteps.selectDifferentBillingAddress();
        purchasePathAddressSteps.clickProceedToPurchaseFromAddressPage(SHIPPING);
        purchasePathShippingOptionSteps.clickProceedToPurchaseFromShippingPage();
        purchasePathAddressSteps.fillUKNonLondonAddress(BILLING);
        purchasePathAddressSteps.clickProceedToPurchaseFromAddressPage(BILLING);
        purchasePathPaymentSteps.assertShippingAndBillingAddressesAreDifferent("different");
        purchasePathPaymentSteps.completePaymentDetails("VISA_CREDIT_CARD");
        purchasePathPaymentSteps.clickPurchaseNow();
        purchasePathOrderConfirmationSteps.orderIsConfirmed();
    }

    @Given("^I complete a purchase with default customer and my order is confirmed$")
    public void completeRegisteredPurchase() {
        purchasePathSignInSteps.defaultCustomerSignIn();
        completeRegisteredLoggedInPurchase();
    }

    @Given("^I complete a purchase logged in as default customer and my order is confirmed$")
    public void completeRegisteredLoggedInPurchase() {
        purchasePathAddressSteps.fillUKLondonAddress(SHIPPING);
        purchasePathAddressSteps.clickProceedToPurchaseFromAddressPage(SHIPPING);
        purchasePathShippingOptionSteps.clickProceedToPurchaseFromShippingPage();
        purchasePathPaymentSteps.assertShippingAndBillingAddressesAreDifferent("the same");
        purchasePathPaymentSteps.completePaymentDetails("VISA_CREDIT_CARD");
        purchasePathPaymentSteps.clickPurchaseNow();
        purchasePathOrderConfirmationSteps.orderIsConfirmed();
    }

    @Given("^I complete a purchase logged in as default customer without entering CCDs and my order is confirmed$")
    public void completeRegisteredLoggedInPurchaseWithoutCCDs() {
        purchasePathAddressSteps.fillUKLondonAddress(SHIPPING);
        purchasePathAddressSteps.clickProceedToPurchaseFromAddressPage(SHIPPING);
        purchasePathShippingOptionSteps.clickProceedToPurchaseFromShippingPage();
        purchasePathPaymentSteps.assertShippingAndBillingAddressesAreDifferent("the same");
        purchasePathPaymentSteps.clickPurchaseNow();
        purchasePathOrderConfirmationSteps.orderIsConfirmed();
    }

    @Given("^I complete a purchase with default customer with different shipping, billing addresses and my order is confirmed$")
    public void completeRegisteredPurchaseWithDifferentShippingBillingAddresses() {
        purchasePathSignInSteps.defaultCustomerSignIn();
        purchasePathAddressSteps.fillUKLondonAddress(SHIPPING);
        purchasePathAddressSteps.selectDifferentBillingAddress();
        purchasePathAddressSteps.clickProceedToPurchaseFromAddressPage(SHIPPING);
        purchasePathShippingOptionSteps.clickProceedToPurchaseFromShippingPage();
        purchasePathAddressSteps.fillUKNonLondonAddress(BILLING);
        purchasePathAddressSteps.clickProceedToPurchaseFromAddressPage(BILLING);
        purchasePathPaymentSteps.assertShippingAndBillingAddressesAreDifferent("different");
        purchasePathPaymentSteps.completePaymentDetails("VISA_CREDIT_CARD");
        purchasePathPaymentSteps.clickPurchaseNow();
        purchasePathOrderConfirmationSteps.orderIsConfirmed();
    }

    @Given("^I complete a purchase logged in as default customer and with different shipping, billing addresses and my order is confirmed$")
    public void completeRegisteredLoggedInPurchaseWithDifferentShippingBillingAddresses() {
        purchasePathAddressSteps.fillUKLondonAddress(SHIPPING);
        purchasePathAddressSteps.selectDifferentBillingAddress();
        purchasePathAddressSteps.clickProceedToPurchaseFromAddressPage(SHIPPING);
        purchasePathShippingOptionSteps.clickProceedToPurchaseFromShippingPage();
        purchasePathAddressSteps.fillUKNonLondonAddress(BILLING);
        purchasePathAddressSteps.clickProceedToPurchaseFromAddressPage(BILLING);
        purchasePathPaymentSteps.assertShippingAndBillingAddressesAreDifferent("different");
        purchasePathPaymentSteps.completePaymentDetails("VISA_CREDIT_CARD");
        purchasePathPaymentSteps.clickPurchaseNow();
        purchasePathOrderConfirmationSteps.orderIsConfirmed();
    }

    @Given("^My product goes out of stock during the purchase path for anonymous user$")
    public void productGoesOutOfStock() {
        purchasePathSignInSteps.anonymousSignIn();
        purchasePathAddressSteps.fillUKLondonAddress(SHIPPING);
        purchasePathAddressSteps.clickProceedToPurchaseFromAddressPage(SHIPPING);
        purchasePathShippingOptionSteps.clickProceedToPurchaseFromShippingPage();
        purchasePathPaymentSteps.completePaymentDetails("VISA_CREDIT_CARD");
        productServiceSteps.forceProductInBasketOutOfStock();
    }

    @Given("^I attempt to purchase anonymously and reach the payment page$")
    public void attemptAnonymousPurchaseAndReachPaymentPage() {
        purchasePathSignInSteps.anonymousSignIn();
        purchasePathAddressSteps.fillUKLondonAddress(SHIPPING);
        purchasePathAddressSteps.clickProceedToPurchaseFromAddressPage(SHIPPING);
        purchasePathShippingOptionSteps.clickProceedToPurchaseFromShippingPage();
    }

    @Given("^I attempt to purchase anonymously using an invalid US shipping address and reach the payment page$")
    public void attemptAnonymousPurchaseAndReachPaymentPageUsingInvalidUSAddress() {
        purchasePathSignInSteps.anonymousSignIn();
        purchasePathAddressSteps.fillInvalidUSShippingAddress();
        purchasePathAddressSteps.clickProceedToPurchaseFromAddressPage(SHIPPING);
        purchasePathShippingOptionSteps.clickProceedToPurchaseFromShippingPage();
    }

    @Given("^I attempt to purchase anonymously with a missing postcode in my shipping address and reach the payment page$")
    public void attemptAnonymousWithAMissingPostcodeAndPurchaseAndReachPaymentPageUsingInvalidUSAddress() {
        purchasePathSignInSteps.anonymousSignIn();
        purchasePathAddressSteps.fillShippingAddressWithMissingPostCode((String) scenarioSession.getData("country"));
        purchasePathAddressSteps.clickProceedToPurchaseFromAddressPage(SHIPPING);
        purchasePathShippingOptionSteps.clickProceedToPurchaseFromShippingPage();
    }

    @Given("^I review my order summary details on the payment page$")
    public void reviewOrderSummaryDetails() {
        purchasePathPaymentSteps.reviewOrderItems();
        purchasePathPaymentSteps.reviewShippingAddress();
        purchasePathPaymentSteps.reviewShippingCost();
        purchasePathPaymentSteps.reviewTotalAmount();
    }
}