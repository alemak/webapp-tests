package com.netaporter.pws.automation.shared.steps.purchasePath;

import com.netaporter.pws.automation.shared.components.PaymentForm;
import com.netaporter.pws.automation.shared.pojos.Address;
import com.netaporter.pws.automation.shared.utils.CardPaymentDetails;
import com.netaporter.test.utils.enums.CardType;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import static com.google.common.base.Strings.repeat;
import static com.netaporter.pws.automation.shared.utils.CardPaymentDetails.securityNumberLength;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;
@Component
public class PurchasePathPaymentSteps extends BasePurchasePathStep {

    @Given("^The payment page shows my shipping and billing addresses are (different|the same)$")
    public void assertShippingAndBillingAddressesAreDifferent(String different) {
        if("different".equals(different))
            assertThat(purchasePathPaymentPage.getShippingAddressSection(), not(equalTo(purchasePathPaymentPage.getBillingAddressSection())));
        else
            assertThat(purchasePathPaymentPage.getShippingAddressSection(), equalTo(purchasePathPaymentPage.getBillingAddressSection()));
    }

    @Given("^I fill in payment details using a valid (.*)$")
    public void completePaymentDetails(String cardType) {
        CardPaymentDetails cardPaymentDetails = CardPaymentDetails.getCardPaymentDetails(cardType);
        purchasePathPaymentPage.completePaymentDetails(cardPaymentDetails);
    }

    @Given("^I fill in the card number as (.*)$")
    public void completeCardNumber(String text) {
        purchasePathPaymentPage.setCardNumber(text);
        scenarioSession.putData("paymentCardNumber", text);
    }

    @When("I save the card number$")
    public void saveCardNumber(){
        String paymentCardNumber = scenarioSession.getData("paymentCardNumber");
        scenarioSession.putData("savedPaymentCardNumber", paymentCardNumber);
    }

    @Given("^I fill in the security number as (.*)$")
    public void completeCardSecurityNumber(String securityNumber) {
        purchasePathPaymentPage.setCardSecurityNumber(securityNumber);
    }

    @Given("^I fill in (the correct|an incorrect) security number$")
    public void completeCorrectOrIncorrectCardSecurityNumber(String input) {
        CardType cardType = purchasePathPaymentPage.getSelectedCardType();
        if ("the correct".equalsIgnoreCase(input)) {
            completeCardSecurityNumber(CardPaymentDetails.getCardPaymentDetails(cardType.name()).getPaymentDetails(webBot.getRegion()).getSecurityNumber());
        }
        else {
            completeCardSecurityNumber(repeat("1", securityNumberLength(cardType)));
        }
    }

    @Given("^I fill in the expiry month (.*) and expiry year (.*)$")
    public void completeCardExpiryDate(String month, String year) {
        purchasePathPaymentPage.setCardExpiryDate(month, year);
    }

    @Given("^I fill in the card holder name as (.*)$")
    public void completeCardHolderName(String text) {
        purchasePathPaymentPage.setCardHolderName(text);
    }

    @Given("^I fill in the card issue number (.*)$")
    public void completeCardIssueNumber(String text) {
        purchasePathPaymentPage.setIssueNumber(text);
    }

    @Given("^I select a card type of (.*)$")
    public void selectCardType(String cardType) {
        purchasePathPaymentPage.setCardType(cardType);
    }

    @Given("^I select (.*) as my payment method$")
    public void selectPaymentMethod(String paymentMethod) {
        purchasePathPaymentPage.setPaymentMethod(paymentMethod);
    }

    @Given("^I click on payment option (.*)$")
    public void choosePaymentOption(String paymentOption){
        purchasePathPaymentPage.setPaymentOption(paymentOption);
    }

    @Given("^I click to edit the shipping address from the payment page$")
    public void clickEditShippingAddress() {
        purchasePathPaymentPage.clickEditShippingAddress();
    }

    @Given("^I click to edit the billing address from the payment page$")
    public void clickEditBillingAddress() {
        purchasePathPaymentPage.clickEditBillingAddress();
    }

    @Given("^I change focus on the payment page$")
    public void changeFocusToForceJSerr() {
        purchasePathPaymentPage.changeFocus();
    }

    @Given("^I click purchase now from the payment page$")
    public void clickPurchaseNow() {
        purchasePathPaymentPage.clickPurchaseNow();
    }

    @Given("^My selected packaging is (.*)$")
    public void check_selected_packaging_option(String packagingOption) {
        assertTrue(purchasePathPaymentPage.getPackagingOption().contains(packagingOption));
    }

    @Given("^The payment page field (card|name|cvs|expiry|issueNumber) has a mandatory field error$")
    public void checkMandatoryFieldError(String field) {
        if(webBot.getBrand().equalsIgnoreCase("NAP"))
            assertTrue(purchasePathPaymentPage.isErrorDisplayedForId(field));
        else
            assertTrue(purchasePathPaymentPage.getMandatoryFieldErrors().contains(field));
    }

    @Given("^The payment page drop down field (month|year) has a mandatory field error$")
    public void checkMandatoryDropDownFieldError(String field) {
        String htmlSelectName = "";
        if(field.equals("month")) htmlSelectName = "creditCard.expiryMonth";
        else htmlSelectName = "creditCard.expiryYear";
        assertTrue(purchasePathPaymentPage.getMandatoryDropDownFieldErrors().contains(htmlSelectName.toLowerCase()));
    }

    @Given("^All payment page mandatory fields have an error$")
    public void checkAllMandatoryFieldErrors() {
        checkMandatoryFieldError("card_number");
        checkMandatoryFieldError("cardholders_name");
        checkMandatoryFieldError("security_number");
        checkMandatoryFieldError("expiry_month");
    }

    @Given("^The product is marked as sold out$")
    public void theProductIsSoldOut() {
        assertTrue(purchasePathPaymentPage.isProductSoldOutMessageDisplayed());
    }

    @Given("^The product that is no. (.*) on the list, has a (.*)% discount$")
    public void checkProductInListDiscountValue(Integer productListPlacing, String expectedDiscount) {
        expectedDiscount = expectedDiscount + "%";
        String discount = (String) purchasePathPaymentPage.getProductItemsList().getOrderItems().get(--productListPlacing).get("discount");
        if(discount == null) // the outnet have a different label for promotions !!!!!
            discount = (String) purchasePathPaymentPage.getProductItemsList().getOrderItems().get(productListPlacing).get("promotion");
        assertEquals(expectedDiscount, discount);
    }

    @Given("^The product that is no. (.*) on the list, has no discount$")
    public void checkProductInListDiscountValueNotVisible(Integer productListPlacing) {
        String discount = (String)
                purchasePathPaymentPage.getProductItemsList().getOrderItems().get(--productListPlacing).get("discount");
        if (discount != null)
            assertEquals(discount, "");
        else
            assertNull(discount);
    }

    @Given("^The product that is no. (.*) on the list, has the correct tax applied$")
    public void checkProductInListTaxValue(Integer productListPlacing) {

        int productIndex = --productListPlacing;
        Double expectedTaxRate = (Double) scenarioSession.getData("taxCalculationValue");

        List<Map> orderItems = purchasePathPaymentPage.getProductItemsList().getOrderItems();

        String unitPriceStr = (String) orderItems.get(productIndex).get("unit price");
        String taxStr = (String) orderItems.get(productIndex).get("tax");
        assertNotNull(unitPriceStr);
        assertNotNull(taxStr);

        // trim off currency symbol, remove comma's and convert
        Double unitPrice = new Double(unitPriceStr.substring(1).replaceAll(",",""));
        Double actualTax = new Double(taxStr.substring(1).replaceAll(",",""));
        Double expectedTax = unitPrice * expectedTaxRate;

        boolean result = expectedTax == actualTax ? true : Math.abs(expectedTax - actualTax) < 0.1;
        assertTrue("Expected tax rate not applied", result);
    }

    @Given("^Free shipping (is|is not) applied$")
    public void checkFreeShippingIsApplied(String condition) {
        if("is".equals(condition))
            assertEquals("Free shipping is not applied: ", "0.00", purchasePathPaymentPage.getShippingCost().toString());
        else
            assertNotEquals("Free shipping is incorrectly applied: ", "0.00", purchasePathPaymentPage.getShippingCost().toString());
    }

    @Given("^Card type has error message (.*)$")
    public void checkCardTypeErrorMessage(String errorMsg) {
        String actualErrorMsg = purchasePathPaymentPage.getCardTypeErrorMessage();
        assertFalse("There should have been an error", actualErrorMsg.isEmpty());
        assertEquals("Error message is not the same", errorMsg, actualErrorMsg);
    }

    @Given("^Card number has error message (.*)$")
    public void checkCardNumberErrorMessage(String errorMsg) {
        String actualErrorMsg = purchasePathPaymentPage.getCardNumberErrorMessage();
        assertFalse("There should have been an error", actualErrorMsg.isEmpty());
        assertEquals("Error message is not the same",errorMsg,actualErrorMsg);

    }

    @Given("^Card name has error message (.*)$")
    public void checkCardNameErrorMessage(String errorMsg) {
        String actualErrorMsg = purchasePathPaymentPage.getCardNameErrorMessage();
        assertFalse("There should have been an error", actualErrorMsg.isEmpty());
        assertEquals("Error message is not the same", errorMsg, actualErrorMsg);
    }

    @Given("^Card security number has error message (.*)$")
    public void checkSecurityNumberErrorMessage(String errorMsg) {
        String actualErrorMsg = purchasePathPaymentPage.getCardSecurityNumberErrorMessage();
        assertFalse("There should have been an error", actualErrorMsg.isEmpty());
        assertEquals("Error message is not the same",errorMsg,actualErrorMsg);
    }

    @Given("^Card expiry date has error message (.*)$")
    public void checkCardExpiryDateErrorMessage(String errorMsg) {
        String actualErrorMsg = purchasePathPaymentPage.getCardExpiryDateErrorMessage();
        assertFalse("There should have been an error", actualErrorMsg.isEmpty());
        assertEquals("Error message is not the same",errorMsg,actualErrorMsg);
    }

    @Given("^I review the total amount to pay$")
    public void reviewTotalAmount() {
        scenarioSession.putData("TotalAmount", purchasePathPaymentPage.getTotalAmount());
    }

    @Given("I review the total postage and packaging costs to pay$")
    public void reviewShippingCost() {
        scenarioSession.putData("ShippingCost", purchasePathPaymentPage.getShippingCost());
    }

    @Given("^I review the items in the order summary$")
    public void reviewOrderItems() {
        scenarioSession.putData("OrderItems", purchasePathPaymentPage.getOrderItems());
    }

    @Given("^I review the store credit amount$")
    public void reviewStoreCreditAmount() {
        scenarioSession.putData("StoreCreditAmount", purchasePathPaymentPage.getStoreCreditAmount());
    }

    @Given("^I review the chosen shipping address$")
    public void reviewShippingAddress() {
        scenarioSession.putData("ShippingAddress", purchasePathPaymentPage.getShippingAddress());
    }

    @Given("I verify that I am on the payment order summary page$")
    public void verifyOnPaymentOrderSummary() {
        assertNotNull("Not on the payment order summary page", purchasePathPaymentPage.getOrderSummaryElement());
    }

    @Given("^The 3D secure payment page is displayed$")
    public void check3DSecurePaymentPageIsDisplayed() throws Throwable {
        purchasePathPaymentPage.is3DSecurePageDisplayed();
    }

    @When("^The 3D secure payment is not authorised$")
    public void set3DSecurePaymentIsNotAuthorised() throws Throwable {
        purchasePathPaymentPage.fail3DSecureAuthentication();
    }

    @When("^The 3D secure payment is authorised$")
    public void set3DSecurePaymentIsAuthorised() throws Throwable {
        purchasePathPaymentPage.pass3DSecureAuthentication();
    }

    @When("^I use a different payment method$")
    public void useDifferentPaymentMethod() throws Throwable {
        purchasePathPaymentPage.useDifferentPaymentMethod();
    }

    @When("^I change my shipping address to an invalid US shipping address$")
    public void changeShippingAddressToInvalidUSAddress(){
        Address invalidUsAddress = new Address();
        invalidUsAddress.setAddress1("1 US Test Street");
        invalidUsAddress.setTownOrCity("Chicago");
        invalidUsAddress.setProvinceOrTerritoryOrState("Texas");
        invalidUsAddress.setPostcode("1234");
        invalidUsAddress.setDaytimePhoneNumber("555123000");
        invalidUsAddress.setCountry(Address.getTranslatedUSCountry((String)scenarioSession.getData("language")));

        purchasePathPaymentPage.changeAndEditShippingAddress(invalidUsAddress);
        purchasePathShippingAddressPage.clickProceedToPurchase();
        purchasePathShippingMethodPage.clickProceedToPurchase();
    }

    @Then("^I verify the error message (.*) is displayed on the payment page")
    public void verifyErrorMessageIsDisplayedOnThePaymentPage(String expectedErrorMessage){
        List<String> errors = purchasePathPaymentPage.getErrorMessages();

        Boolean errorDisplayed = false;
        for (String error : errors){
            if (error.contains(expectedErrorMessage.trim())){
                errorDisplayed = true;
            }
            if (errorDisplayed) return;
        }
        fail("The error message: ".concat(expectedErrorMessage).concat(" should have been displayed."));
    }

    @Then("^I verify a payment error is displayed on the payment page")
    public void verifyErrorMessageIsDisplayedOnThePaymentPage(){
        assertTrue("A payment error should be displayed on the payment page", purchasePathPaymentPage.hasPaymentError());
    }

    @Then("^I verify the option to go back to saved cards '(.*)' displayed to the user$")
    public void verifyBackToSavedCardLink(String linkDisplay){
        String language = (String) scenarioSession.getData("language");
        assertTrue(linkDisplay.toLowerCase().equals("is") ? purchasePathPaymentPage.isBackToSavedCardDisplayed(language)
                : purchasePathPaymentPage.isBackToSavedCardDisplayed(language) == false);
    }

    @When("^I return to my saved cards$")
    public void returnToSavedCards(){
        purchasePathPaymentPage.returnToSavedCards();
    }

    /*
     Prashant.Ramcharan@net-a-porter.com - 20/02/2014
     Populates card details
    */
    @When("^I populate the card details using payment card (.*)$")
    public void populatePaymentCardDetails(String cardType){
        purchasePathPaymentPage.closeDontMissOutPopup();
        HashMap<String, Stack<String>> paymentCardCollection = CardPaymentDetails.getPaymentCardCollection();

        String paymentCardId = PaymentForm.getPaymentOption(cardType);
        String cardNumber = paymentCardCollection.get(paymentCardId).pop();
        purchasePathPaymentPage.populatePaymentCardDetails(paymentCardId, cardNumber);
    }

    /*
     Prashant.Ramcharan@net-a-porter.com - 20/02/2014
     Populates card details -> make payment
    */
    @When("^I make payment using payment card (.*)$")
    public void populatePaymentCardDetailsAndPay(String cardType){
        populatePaymentCardDetails(cardType);
        purchasePathPaymentPage.clickPurchaseNow();
    }

    /*
      Prashant.Ramcharan@net-a-porter.com - 20/02/2014
      Populates card details -> make payment -> verify if payment is successful (if not then try again using a new card)
    */
    @When("^I pay with payment card (.*) and verify the payment was successful$")
    public void populatePaymentCardDetailsAndPayAndVerifyPayment(String cardType){
        HashMap<String, Stack<String>> paymentCardCollection = CardPaymentDetails.getPaymentCardCollection();

        Integer maxAttempts = paymentCardCollection.size();
        while(maxAttempts > 0){
            String paymentCardId = PaymentForm.getPaymentOption(cardType);
            String cardNumber = paymentCardCollection.get(paymentCardId).pop();
            scenarioSession.putData("paymentCardNumber", cardNumber);

            purchasePathPaymentPage.populatePaymentCardDetails(paymentCardId, cardNumber);
            purchasePathPaymentPage.clickPurchaseNow();
            maxAttempts = purchasePathPaymentPage.hasPaymentError() ? (maxAttempts - 1) : 0;
        }
    }

    /*
     Prashant.Ramcharan@net-a-porter.com
     Assuming the customer has a saved card
    */
    @When("^I verify the payment was successful and retry payment if not successful using my saved card")
    public void verifyPaymentWasSuccessfulAndRetry(){
        Integer maxAttempts;
        maxAttempts = purchasePathPaymentPage.hasPaymentError() ? 2 : 0;

        while(maxAttempts > 0){
            purchasePathPaymentPage.clickPurchaseNow();
            maxAttempts = purchasePathPaymentPage.hasPaymentError() ? (maxAttempts - 1) : 0;
        }
    }
}