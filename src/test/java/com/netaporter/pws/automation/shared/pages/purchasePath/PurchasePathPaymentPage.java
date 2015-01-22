package com.netaporter.pws.automation.shared.pages.purchasePath;

import com.netaporter.pws.automation.shared.utils.HtmlScrapingUtils;
import com.netaporter.pws.automation.shared.components.PaymentForm;
import com.netaporter.pws.automation.shared.components.ProductItemsListComponent;
import com.netaporter.pws.automation.shared.pojos.Address;
import com.netaporter.pws.automation.shared.pojos.PaymentDetails;
import com.netaporter.pws.automation.shared.utils.CardPaymentDetails;
import com.netaporter.test.client.product.pojos.CestaItem;
import com.netaporter.test.utils.enums.CardType;
import com.netaporter.test.utils.pages.AbstractPage;
import com.netaporter.test.utils.pages.driver.WaitTime;
import com.netaporter.test.utils.pages.exceptions.PageElementNotFoundException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public abstract class PurchasePathPaymentPage extends AbstractPage {

    @Autowired
    protected ProductItemsListComponent productItemsListComponent;

    public ProductItemsListComponent getProductItemsList() {
        return productItemsListComponent;
    }

    public PurchasePathPaymentPage() {
        super("Purchase path payment", "");
    }

    public void populatePaymentCardDetails(String cardTypeId, String cardNumber){}

    public void setCardNumber(String text) {
        webBot.type(PaymentForm.CARD_NUMBER, text);
    }

    public void setCardSecurityNumber(String text) {
        webBot.type(PaymentForm.SECURITY_NUMBER, text);
    }

    public void setCardExpiryDate(String month, String year) {
        webBot.selectElementText(PaymentForm.EXPIRY_MONTH, month);
        webBot.selectElementText(PaymentForm.EXPIRY_YEAR, year);
    }

    public void setCardHolderName(String text) {
        webBot.type(PaymentForm.CARD_NAME, text);
    }

    public void setCardType(String cardType) {
        webBot.selectElementText(PaymentForm.CARD_TYPE, cardType);
    }

    //this only works on the saved cards form
    public CardType getSelectedCardType() {
        WebElement cardTypeElement = webBot.getElement(By.cssSelector(".saved-card-button input:checked"));
        assertNotNull("could not find selected card type", cardTypeElement);
        //saved card names seem to look like AMEX-6 or VISA-1, need to remove trailing non-letter characters before returning them
        return cardTypeElementValueToCardType(cardTypeElement.getAttribute("value").replaceAll("[^A-Za-z]+$", ""));
    }

    private CardType cardTypeElementValueToCardType(String cardTypeAsString) {
        if (cardTypeAsString.equals("VISA")) {
            return CardType.VISA_CREDIT_CARD;
        }
        else if (cardTypeAsString.equals("MASTERCARD")) {
            return CardType.MASTER_CARD;
        }
        else if (cardTypeAsString.equals("AMEX")) {
            return CardType.AMERICAN_EXPRESS;
        }
        else if (cardTypeAsString.equals("JCB")) {
            return CardType.JCB;
        }
        else if (cardTypeAsString.equals("MAESTRO")) {
            return CardType.MAESTRO;
        }
        else {
            throw new AssertionError("unknown card type: " + cardTypeAsString);
        }
    }

    public void setPaymentMethod(String paymentMethod) {
        //TODO: this method is subject to change when the web dev work to include the Paypal option is done.
        webBot.selectByText(By.id("creditCard.cardType"), paymentMethod);
    }

    public void setIssueNumber(String issueNumber) {
        webBot.type(PaymentForm.ISSUE_NUMBER, issueNumber);
    }

    public void setPaymentOption(String paymentOption) {
        String PAYMENT_OPTION_ID = PaymentForm.getPaymentOption(paymentOption);
        webBot.clickAndWait(By.id(PAYMENT_OPTION_ID), WaitTime.ONE);
    }

    public void completePaymentDetails(CardPaymentDetails cardType) {
        PaymentDetails paymentDetails = cardType.getPaymentDetails(webBot.getRegion());
        webBot.findElement(By.id(PaymentForm.getPaymentOption(cardType.getType().name()))).click();
        webBot.type(PaymentForm.CARD_NUMBER, paymentDetails.getCardNumber());
        webBot.type(PaymentForm.CARD_NAME, paymentDetails.getCardholderName());
        webBot.type(PaymentForm.SECURITY_NUMBER, paymentDetails.getSecurityNumber());
        webBot.selectElementText(PaymentForm.EXPIRY_MONTH, paymentDetails.getExpiryMonth());
        webBot.selectElementText(PaymentForm.EXPIRY_YEAR, paymentDetails.getExpiryYear());
        if (paymentDetails.getIssueNumber() != null)
            webBot.type(PaymentForm.ISSUE_NUMBER, paymentDetails.getIssueNumber());
    }

    public boolean isProductSoldOutMessageDisplayed() {
        WebElement outOfStockElement = webBot.findElement(By.xpath("(//td[@class='error pad_description'])"));
        return outOfStockElement.getText().contains("WE DO NOT HAVE THE QUANTITY OF STOCK NECESSARY TO FULFILL THIS ORDER");
    }

    public void changeFocus() {
        // just click on the search bar to force any JS validation errors
        webBot.findElement(By.id("search")).click();
    }

    public void clickPurchaseNow() {
        webBot.findElement(By.id("processPayment"), WaitTime.FOUR).click();
    }

    public String getPackagingOption() {
        WebElement e = webBot.findElement(By.cssSelector("div.packaging-options-payment p"));
        return e.getText().split("\n")[0];
    }

    public BigDecimal getShippingCost() {
        return getSubtotal("shipping");
    }

    public BigDecimal getStoreCreditAmount() {
        return getSubtotal("store-credit");
    }

    public BigDecimal getSubtotal(String key) {
        List<WebElement> shippingInformation = webBot.findElements(By.cssSelector("table.totals_table tr"));
        Map<String,BigDecimal> shippingInformationMap = new HashMap<String, BigDecimal>(shippingInformation.size());

        for (WebElement row : shippingInformation){
            String name = row.findElement(By.className("total-description")).getText().replaceAll(" ","-");
            String amount = row.findElement(By.className("total-amount")).getText().replaceAll(",","");
            BigDecimal value = HtmlScrapingUtils.decimalNumberStringRepresentationToBigDecimal(amount);
            shippingInformationMap.put(name.toLowerCase(), value);
        }

        return shippingInformationMap.get(key);
    }

    public abstract List<String> getShippingAddressSection();

    public abstract List<String> getBillingAddressSection();

    public abstract List<String> getPidsFromPaymentSummaryPage();

    public void clickEditShippingAddress() {
        webBot.findElement(By.xpath("//a[@title='Change shipping address']")).click();
    }

    public void clickEditBillingAddress() {
        webBot.findElement(By.xpath("//a[@title='Edit billing address']")).click();
    }

    public String getCardTypeErrorMessage() {
        return webBot.findElement(By.id("selectCardError")).getText();
    }

    public String getCardNumberErrorMessage() {
        return webBot.findElement(By.id("cardError")).getText();
    }

    public String getCardNameErrorMessage() {
        return webBot.findElement(By.id("nameError")).getText();
    }

    public String getCardSecurityNumberErrorMessage() {
        return webBot.findElement(By.id("cvsError")).getText();
    }

    public String getCardExpiryDateErrorMessage() {
        return webBot.findElement(By.id("expiryError")).getText();
    }

    public abstract List<CestaItem> getOrderItems();


    public BigDecimal getTotalAmount() {
        String totalAmount = webBot.findElement(By.cssSelector("tr#subtotal_row td.total-amount")).getText();
        return HtmlScrapingUtils.decimalNumberStringRepresentationToBigDecimal(totalAmount);
    }

    public abstract List<String> getShippingAddress();

    public WebElement getOrderSummaryElement() {
        WebElement orderSummarySection = webBot.findElement(By.id("payment_page_body"));
        return orderSummarySection;
    }

    public boolean is3DSecurePageDisplayed() {
        WebElement pageTitle = webBot.findElement(By.id("bankpage-one-text"));
        return pageTitle.getText().contains("3D Secure Registered cards");
    }

    public void fail3DSecureAuthentication() {
        //Using the datacash simulator click the not authenticated button ACSframe
        selectACSSimulatorButton("Not Authenticated");
    }

    public void pass3DSecureAuthentication() throws Exception {
        //Using the datacash simulator click the not authenticated button ACSframe
        selectACSSimulatorButton("Authenticated");
    }

    private void selectACSSimulatorButton(String buttonName) {
        webBot.switchToIFrame(By.name("ACSframe"));
        WebElement acsButton = webBot.findElement(By.xpath("//input[@value='" + buttonName + "']"));
        acsButton.click();
    }

    public void useDifferentPaymentMethod() {
        webBot.clickAndWaitForJQueryCompletion(By.className("different-card-link"));
    }

    public void changeAndEditShippingAddress(Address address) {
        webBot.click(By.partialLinkText("Change shipping address"));
        webBot.click(By.partialLinkText("Edit shipping address"));

        webBot.selectElementText(By.id("country"), address.getCountry());
        webBot.type(By.id("address.address1"), address.getAddress1());
        webBot.type(By.id("address.address2"), address.getAddress2() == null ? "" : address.getAddress2());
        webBot.type(By.id("address.towncity"), address.getTownOrCity());
        webBot.selectElementText(By.id("state"), address.getProvinceOrTerritoryOrState());
        webBot.type(By.id("address.postcode"), address.getPostcode());
        webBot.type(By.id("address.work"), address.getDaytimePhoneNumber());
    }

    public Boolean isBackToSavedCardDisplayed(String language) {
        String langSpecificLinkText = "Back to saved card";

        if (language.toLowerCase().equals("french")){
            langSpecificLinkText = "Utiliser ma carte sauvegardée";
        }
        if (language.toLowerCase().equals("german")){
            langSpecificLinkText = "Zurück zur gespeicherten Kreditkarte";
        }
        if (language.toLowerCase().equals("chinese")){
            langSpecificLinkText = "返回已储存的付款卡信息";
        }

       return webBot.isElementPresent(By.linkText(langSpecificLinkText), 1);
    }

    public void returnToSavedCards() {
        webBot.clickAndWaitForJQueryCompletion(By.xpath("//*[@id='payment_header']/a"));
    }

    public boolean hasPaymentError() {
        Boolean isErrorVisible = webBot.executeJavascript("$('.error').is(\":visible\")");
        return isErrorVisible;
    }

    public boolean hasOrderNumberDisplayed(){
        String orderSummaryText = webBot.executeJavascript("$('#purchase-notification-sub').text()");
        return orderSummaryText.trim().startsWith("Your order number is");
    }

    public boolean isErrorDisplayedForId(String field) {
        return false;
    }

    public boolean isSecurityNumberTextBoxDisplayed() {
        WebElement securityNumberElement;
        try {
            securityNumberElement = webBot.findElement(PaymentForm.SECURITY_NUMBER, WaitTime.FOUR);
        }
        catch (PageElementNotFoundException e) {
            return false;
        }
        return securityNumberElement.isDisplayed();
    }

    public void closeDontMissOutPopup() {
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement dontMissOutPopupElement;
        try {
            dontMissOutPopupElement = webBot.findElement(By.className("email_signup_popup_close"), WaitTime.TWO);
        }
        catch (PageElementNotFoundException e) {
            //element has disappeared, skip the rest
            return;
        }

        try {
            webBot.executeScript("arguments[0].scrollIntoView(true);", dontMissOutPopupElement);
            dontMissOutPopupElement.click();
        }
        catch (WebDriverException e) {
            try {
                //popup sometimes needs a while to crawl into its final position on the page
                Thread.sleep(700);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            webBot.executeScript("arguments[0].scrollIntoView(true);", dontMissOutPopupElement);
            dontMissOutPopupElement.click();
        }
        try {
            webBot.waitExplicitly(WaitTime.TWO);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isNonReturnableMessageDisplayedForSku(String pid) {
        return productItemsListComponent.isNonrefundableWarningMessageDisplayedForPid(pid);
    }
}