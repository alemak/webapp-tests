package com.netaporter.pws.automation.nap.pages;

import com.netaporter.pws.automation.nap.pages.components.NAPPaymentForm;
import com.netaporter.pws.automation.shared.components.AddressComponent;
import com.netaporter.pws.automation.shared.components.PaymentForm;
import com.netaporter.pws.automation.shared.pojos.Address;
import com.netaporter.pws.automation.shared.pojos.Country;
import com.netaporter.pws.automation.shared.pojos.PaymentDetails;
import com.netaporter.pws.automation.shared.utils.CardPaymentDetails;
import com.netaporter.pws.automation.shared.utils.WaitUtil;
import com.netaporter.test.utils.enums.CardType;
import com.netaporter.test.utils.enums.RegionEnum;
import com.netaporter.test.utils.pages.driver.WaitTime;
import com.netaporter.test.utils.pages.exceptions.PageElementNotFoundException;
import org.apache.commons.collections.list.TreeList;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * User: x.qi@london.net-a-porter.com
 * Date: 11/10/2012
 */
@Component
@Scope("cucumber-glue")
public class PurchasePathFlowPage extends AbstractNapPage {
    // rename this class if required

    private static final String PAGE_NAME = "Purchase Path Sign In";
    private static final String PATH = "signinpurchasepath.nap";

    private static final By CHECKOUT_AS_GUEST_BUTTON_LOCATOR = By.className("test-guest-customer-signin");
    private static final By REGISTERED_USER_EMAIL_TEXTBOX_LOCATOR = By.className("test-registered-customer-email");
    private static final By REGISTERED_USER_PASSWORD_TEXTBOX_LOCATOR = By.className("test-registered-customer-password");
    private By SHIPPING_METHOD_NAME_IN_PAYMENT_SUMMARY_PAGE = By.cssSelector("#shipping_method>div>p:nth-child(3)");
    private By SHIPPING_METHOD_LOCATOR = By.xpath("//div[@id='shipping_method']/div/div/label");
    private By ITEM_TOTAL_IN_PAYMENT_PAGE_LOCATOR = By.xpath("//table[@class='totals_table']/tbody/tr[1]/td[3]");
    private By PRICES_IN_SHIPPING_OPTIONS_PAGE_LOCATOR = By.className("price");
    private By ORDER_ID_LOCATOR = By.xpath("//p[@id='purchase-notification-sub']/span");
    private By CARD_ERROR_LOCATOR = By.id("cardError");
    private By OUT_OF_STOCK_MESSAGE_LOCATOR = By.xpath("(//td[@class='error pad_description'])");
    private By AVAILABLE_CARD_BUTTONS_LOCATOR = By.name("cardType");
    private By CHANGE_SHIPPING_ADDRESS_BUTTON_LOCATOR = By.xpath("//a[@title='Change shipping address']");
    private By EDIT_SHIPPING_ADDRESS_LOCATOR = By.xpath("//a[@title='Edit shipping address']");
    private By SUBTOTAL_IN_PAYMENT_PAGE_LOCATOR = By.xpath(".//*[@id='subtotal_row']/td[2]");
    private By CONFIRMATION_FLOW_STATE_LOCATOR = By.className("confirmation");
    private By AUTHORISING_FLOW_STATE_LOCATOR = By.name("ACSframe");
    private By PAYMENT_FLOW_STATE_LOCATOR = By.className("payment");
    private By SHIPPING_FLOW_STATE_LOCATOR = By.className("shipping");
    private By SIGNIN_FLOW_STATE_LOCATOR = By.className("signin");
    private By PAY_NOW_LOCATOR = By.id("processPayment");
    private By PROCEED_TO_PURCHASE_LOCATOR = By.id("proceedToPurchase");
    private By NO_PASSWORD_LINK_LOCATOR = By.id("no_pass");
    private By YES_PASSWORD_LINK = By.id("yes_pass");
    private By GUEST_EMAIL_ADDRESS_TEXTBOX_LOCATOR = By.className("test-guest-customer-email");
    private By PASSWORD = By.name("j_password");
    private By MESSAGE_3D_SECURE = By.xpath("id('bankpage-one-text')/p[2]/span");
    private By SIGN_IN_NOW_BUTTON_LOCATOR = By.className("test-registered-customer-signin");
    private By FORGOTTEN_PASSWORD_LINK = By.xpath("//span[@class='forgot-pass-link']/a");
    private By NON_PROMOTION_PRICE_LOCATOR = By.className("no-promotion");


    private static final String[] PRICE_HEADER_NAMES = {"UNIT PRICE", "DUTIES", "TAX", "SUBTOTAL"};

    private static final int MAXIMUM_ATTEMPTS_ON_PURCHASE = 3;

    private String securityNumberUsedForPurchase = null;

    @Autowired
    private AddressComponent addressComponent;

    public boolean isCardTypeSelectEnabled() {
        assertState(FlowState.PAYMENT);
        return webBot.findElement(NAPPaymentForm.CARD_TYPE).isEnabled();
    }

    public boolean isMaskedCreditCardNumberInputEnabled() {
        assertState(FlowState.PAYMENT);
        return webBot.findElement(PaymentForm.MASKED_CARD_NUMBER).isEnabled();
    }

    public boolean isCardholderNameInputEnabled() {
        assertState(FlowState.PAYMENT);
        return webBot.findElement(PaymentForm.CARD_NAME).isEnabled();
    }

    public boolean isExpiryDateMonthEnabled() {
        assertState(FlowState.PAYMENT);
        return webBot.findElement(PaymentForm.EXPIRY_MONTH).isEnabled();
    }

    public boolean isExpiryDateEnabled() {
        assertState(FlowState.PAYMENT);
        return webBot.findElement(NAPPaymentForm.SAVED_EXPIRY_DATE_LOCATOR).isEnabled();
    }

    public boolean isExpiryDateYearEnabled() {
        assertState(FlowState.PAYMENT);
        return webBot.findElement(PaymentForm.EXPIRY_YEAR).isEnabled();
    }

    public String getMaskedCreditCardNumber() {
        assertState(FlowState.PAYMENT);
        return getSavedCardNumber();
    }

    public String getCardHolderName() {
        assertState(FlowState.PAYMENT);
        return webBot.findElement(PaymentForm.CARD_NAME).getAttribute("value");
    }

    public String getExpiryDateMonth() {
        assertState(FlowState.PAYMENT);
        return webBot.getSelectElement(PaymentForm.EXPIRY_MONTH).getFirstSelectedOption().getText();
    }

    public String getExpiryDateYear() {
        assertState(FlowState.PAYMENT);
        return webBot.getSelectElement(PaymentForm.EXPIRY_YEAR).getFirstSelectedOption().getText();
    }

    public boolean isCreditCardNumberInputEnabled() {
        assertState(FlowState.PAYMENT);
        return webBot.findElement(PaymentForm.CARD_NUMBER).isEnabled();
    }

    public String getCreditCardNumber() {
        assertState(FlowState.PAYMENT);
        return webBot.findElement(PaymentForm.CARD_NUMBER).getAttribute("value");
    }

    public boolean isCvvEnabled() {
        assertState(FlowState.PAYMENT);
        return webBot.findElement(PaymentForm.SECURITY_NUMBER).isEnabled();
    }

    public void selectUseDifferentPaymentMethodLink() {
        assertState(FlowState.PAYMENT);
        webBot.click(NAPPaymentForm.CHANGE_CARD_LINK);
    }

    public Integer getOrderId() {
        String orderIdText = webBot.findElement(ORDER_ID_LOCATOR).getText();

        Assert.assertNotNull(orderIdText);
        return new Integer(orderIdText);
    }

    public List<TreeList> getSavedCreditCardsOptions() {
        WebElement availableCardsTable = webBot.findElement(NAPPaymentForm.AVAILABLE_CARDS_TABLE_LOCATOR);
        List<TreeList> listOfAvailableCardDetails = new ArrayList<TreeList>();

        final List<WebElement> availableCardTypes = availableCardsTable.findElements(NAPPaymentForm.SAVED_CARD_CODE_LOCATOR);
        final List<WebElement> availableCardNumbers = availableCardsTable.findElements(NAPPaymentForm.SAVED_CARD_NUMBER_LOCATOR);
        final List<WebElement> availableCardNames = availableCardsTable.findElements(NAPPaymentForm.SAVED_CARD_HOLDER_LOCATOR);
        final List<WebElement> availableCardExpiry = availableCardsTable.findElements(NAPPaymentForm.SAVED_EXPIRY_DATE_LOCATOR);

        //fail if the table of available card details doesn't have the same number of details for each card
        assertThat(availableCardTypes.size(), allOf(is(availableCardNumbers.size()), is(availableCardNames.size()), is(availableCardExpiry.size())));

        for (int i=0;i<availableCardTypes.size();i++) {
            String cardTypeTag = availableCardTypes.get(i).getAttribute("value");
            String cardType = cardTypeTag.substring(0, cardTypeTag.indexOf("-"));
            TreeList tempCardDetails = new TreeList();

            tempCardDetails.add(cardType);
            tempCardDetails.add(availableCardNumbers.get(i).getText());
            tempCardDetails.add(availableCardNames.get(i).getText());
            tempCardDetails.add(availableCardExpiry.get(i).getText());
            listOfAvailableCardDetails.add(i, tempCardDetails);
        }

        return listOfAvailableCardDetails;
    }

    public void changeSelectedOption() {
        List<WebElement> cardOptionButtons = webBot.findElements(NAPPaymentForm.SAVED_CARD_CODE_LOCATOR);
        for (WebElement cardOptionButton : cardOptionButtons) {
            if (cardOptionButton.getAttribute("checked") == null) {
                cardOptionButton.click();
                return;
            }
        }
    }

    public String getSubTotalCurrencyInPaymentOrConfirmationPage() {
        return webBot.findElement(SUBTOTAL_IN_PAYMENT_PAGE_LOCATOR).getText();
    }

    public String getSavedCardHolderName() {
        return webBot.findElement(NAPPaymentForm.SAVED_CARD_HOLDER_LOCATOR).getText();
    }

    public String getSavedExpiryDate() {
        return webBot.findElement(NAPPaymentForm.SAVED_EXPIRY_DATE_LOCATOR).getText();
    }

    public String getSavedCardNumber() {
        return webBot.findElement(NAPPaymentForm.SAVED_CARD_NUMBER_LOCATOR).getText();
    }

    public String getCvvNumber() {
        return webBot.findElement(PaymentForm.SECURITY_NUMBER).getText();
    }

    public List<String> getAvailableCreditCards() {
        List<WebElement> availableCardButtonElements = webBot.findElements(AVAILABLE_CARD_BUTTONS_LOCATOR, WaitTime.FOUR);
        List<String> availableCardNames= new ArrayList<String>();
        for (WebElement availableCardButtonElement : availableCardButtonElements) {
            availableCardNames.add(availableCardButtonElement.getAttribute("title"));
        }
        return availableCardNames;
    }

    public List<String> getDesignerNamesFromConfirmationPage() {
        List<WebElement> designerNameElements = webBot.getDriver().findElements(By.className("product-manufacturer"));
        List<String> designerNames = new ArrayList<String>();
        for (WebElement designerNameElement : designerNameElements) {
            designerNames.add(designerNameElement.getText().trim());
        }
        if (designerNames.size()!=0)
            return designerNames;
        else
            throw new IllegalStateException("List of designer names in confirmation page is empty.");

    }

    public void selectShippingMethodByName(String shippingOption) {
        List<WebElement> shippingMethodElements = webBot.findElements(SHIPPING_METHOD_LOCATOR, WaitTime.FOUR);
        for (WebElement shippingMethodElement : shippingMethodElements) {
            if(shippingOption.equalsIgnoreCase(shippingMethodElement.getText())) {
                webBot.findElement(By.cssSelector("[id$='" + shippingMethodElement.getAttribute("for") + "']"), WaitTime.FOUR).click();
                return;
            }
        }
        throw new RuntimeException("Could not click on shipping method "+shippingOption);
    }

    public String getShippingMethodOnOrderConfirmationPage() {
        assertState(FlowState.CONFIRMATION);
        return webBot.findElement(By.cssSelector("#shipping_method>div>p"), WaitTime.FOUR).getText();
    }

    public String getNextDayCutOffMessage() {
        List<WebElement> messageElements;
        try{
            messageElements = webBot.findElements(By.className("arrange_delivery"),WaitTime.FOUR);
        }catch (PageElementNotFoundException e){
            return "false";
        }
        if (!messageElements.isEmpty())
            return messageElements.get(messageElements.size()-1).getText();
        else
            return "false";
    }

    public enum FlowState {
        SIGN_IN,
        SHIPPING_ADDRESS,
        SHIPPING_OPTION,
        PAYMENT,
        AUTHORISING,
        CONFIRMATION
    }


    public PurchasePathFlowPage() {
        super(PAGE_NAME, PATH);
    }

    public void checkout() {
        if(webBot.isElementPresent(By.id("paymentDetailsForm"), 4)) {
            assertState(FlowState.PAYMENT);
        } else if(webBot.isElementPresent(By.id("shippingOptionsCommand"), 4)) {
            assertState(FlowState.SHIPPING_OPTION);
        } else {
            assertState(FlowState.SHIPPING_ADDRESS);
        }
    }

    public void checkoutAnonymously(String anonEmailAddress) {
        assertState(FlowState.SIGN_IN);

        enterGuestEmailAddress(anonEmailAddress);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clickCheckoutAsGuest();
    }

    public void clickCheckoutAsGuest() {
        webBot.findElement(CHECKOUT_AS_GUEST_BUTTON_LOCATOR, WaitTime.FOUR).click();
    }

    public void expressCheckout(String emailAddress, String password) {
        assertState(FlowState.SIGN_IN);

        clearRegisteredUserEmailAddress();
        enterRegisteredCustomerEmailAddress(emailAddress);
        enterRegisteredUserPassword(password);
        clickSignInNow();
    }

    private void enterRegisteredUserPassword(String password) {
        webBot.findElement(REGISTERED_USER_PASSWORD_TEXTBOX_LOCATOR, WaitTime.FOUR).sendKeys(password);
    }

    private void enterRegisteredCustomerEmailAddress(String emailAddress) {
        webBot.findElement(REGISTERED_USER_EMAIL_TEXTBOX_LOCATOR, WaitTime.FOUR).sendKeys(emailAddress);
    }

    public void goToForgottenPasswordLink() {
        assertState(FlowState.SIGN_IN);
        webBot.click(FORGOTTEN_PASSWORD_LINK);
    }

    public void checkoutInSignedInState() {
        fillAddressAndContinue();
    }

    public void checkoutInSignedInState(String country) {
        fillAddressAndContinue(country);
    }

    public void fillAddressAndContinue() {
        if (webBot.getRegion().equals(RegionEnum.INTL.name())){
            fillAddressAndContinue(Address.createNAPAddress(), true);
        }
        else if (webBot.getRegion().equals(RegionEnum.AM.name())){
            fillAddressAndContinue(Address.createUSPremierAddress(), true);
        }
        else{
            fillAddressAndContinue(Address.createAnotherAddress(), true);
        }
    }

    public void fillUSAddressAndContinue(String state, String postCode) {
        Address usAddress = Address.createAnotherAddress("United States");
        usAddress.setPostcode(postCode);
        usAddress.setProvinceOrTerritoryOrState(state);
        fillAddressAndContinue(usAddress, true);
    }

    public void fillAnotherAddressAndContinue() {
        fillAddressAndContinue(Address.createAnotherAddress(), true);
    }

    public void fillAddressWithDifferentBillingAndContinue() {
        fillAddressAndContinue(Address.createNAPAddress(), false);
    }

    public void fillAddressAndContinue(String country) {
        if (country.equals(Country.USA.getName())) {
            fillAddressAndContinue(Address.createUSNonPremierAddress(), true);
        }
        else if (country.equals(Country.UNITED_KINGDOM.getName())){
            fillAddressAndContinue(Address.createNAPAddress(),true);
        }
        else if (country.equals(Country.HONG_KONG.getName())) {
            fillAddressAndContinue(Address.createHKPremierAddress(), true);
        }
        else {
            fillAddressAndContinue(Address.createAnotherAddress(country), true);
        }
    }

    public void fillPremierAddressAndContinue() {
        fillAddressAndContinue(Address.createUSPremierAddress(), true);
    }

    public void fillAddressAndContinue(Address address, boolean sameBillingAddr) {
        assertState(FlowState.SHIPPING_ADDRESS);

        addressComponent.fillInAddress(address);

        if (!sameBillingAddr)
            webBot.click(AddressComponent.BILLING_DIFF);

        webBot.findElement(AddressComponent.CONTINUE, WaitTime.FOUR).click();
    }

    public void fillBillingAddressAndContinue() {
        assertState(FlowState.PAYMENT);

        if (webBot.getRegion().equals(RegionEnum.AM.name())) {
            addressComponent.fillInAddress(Address.createUSNonPremierAddress());
        } else if (webBot.getRegion().equals(RegionEnum.INTL.name())) {
            addressComponent.fillInAddress(Address.createUKNonLondonAddress());
        } else {
            addressComponent.fillInAddress(Address.createAnotherAddress());
        }
        webBot.click(AddressComponent.CONTINUE);
    }

    public List<String> getAllPricesInShippingOptionPage() {
        assertState(FlowState.SHIPPING_OPTION);

        List<String> prices = new ArrayList<String>();

        List<WebElement> priceElements = webBot.findElements(PRICES_IN_SHIPPING_OPTIONS_PAGE_LOCATOR);

        for (WebElement priceElement : priceElements) {
            prices.add(priceElement.getText());
        }
        return prices;
    }

    public void clickProceedToPurchaseFromShippingOptionPage() {
        assertState(FlowState.SHIPPING_OPTION);
        webBot.findElement(PROCEED_TO_PURCHASE_LOCATOR, WaitTime.FOUR).click();
    }


    public List<String> getAllPricesInPaymentOrConfirmationPage() {
        return getAllPricesOfShoppingBagItems();
    }

    private List<String> getAllPricesOfShoppingBagItems() {
        List<String> prices = new ArrayList<String>();

        for (String priceHeaderName : PRICE_HEADER_NAMES) {
            List<WebElement> priceElements = webBot.findElements(By.xpath("//table[@class='shopping_bag_items']/tbody/tr/td[" + getColumnIndex(priceHeaderName) + "]"));
            for (WebElement priceElement : priceElements) {
                prices.add(priceElement.getText());
            }
        }

        return prices;
    }

    public List<String> getAllSubtotals() {
        List<String> prices = new ArrayList<String>();

        List<WebElement> priceElements = webBot.findElements(By.xpath("//table[@class='shopping_bag_items']/tbody/tr/td[" + getColumnIndex(PRICE_HEADER_NAMES[3]) + "]"));
        for (WebElement priceElement : priceElements) {
            prices.add(priceElement.getText());
        }
        return prices;
    }

    public void purchase(CardPaymentDetails cardDetails, boolean isRetryIfFails) {
        purchase(cardDetails == null ? null : cardDetails.getPaymentDetails(webBot.getRegion()), isRetryIfFails);
    }

    public void purchase(PaymentDetails paymentDetails, boolean isRetryIfFails) {
        for (int i = 0; i < MAXIMUM_ATTEMPTS_ON_PURCHASE; i++) {
            if (paymentDetails != null) {
                fillCardDetails(paymentDetails);
            }

            if (isRetryIfFails) {
                saveSecurityNumberFromPaymentForm();
                if (purchaseWithResult()) {
                    return;
                }
                System.out.println("Transaction failed: this is probably due to quick succession use of the same credit card.\n I will try again in 90 seconds");
                WaitUtil.waitFor(90000);
            } else {
                purchaseNow();
                return;
            }
        }

        fail("I've already tried " + MAXIMUM_ATTEMPTS_ON_PURCHASE + " times on " + paymentDetails + ". So can't continue the test");
    }

    public void purchaseWithAnInvalidCard() {
        //page does not allow card numbers of less than 16 digits, changed to a 16 digit invalid card number
        fillCardDetails(new PaymentDetails("Visa", "1000630000000071", "TestName", "111", "02", "19"));
    }

    public List<String> getAllPricesInConfirmationPage() {
        assertState(FlowState.CONFIRMATION);

        return getAllPricesOfShoppingBagItems();
    }


    public String getOrderConfirmationNumber() {
        assertState(FlowState.CONFIRMATION);

        WebElement element = webBot.findElement(By.xpath("//p[@id='purchase-notification-sub']/span"));

        return element.getText();
    }

    public List<String> getPaymentOptions() {
        assertState(FlowState.PAYMENT);

        return getAllPaymentOptions();
    }

    public boolean is3DSecurePage() {
        assertState(FlowState.AUTHORISING);
        WebElement el = webBot.findElement(MESSAGE_3D_SECURE, WaitTime.FOUR);
        return el.getText().contains("3D Secure Registered cards");
    }

    public boolean isProductSoldOutMessageDisplayed() {
        assertState(FlowState.PAYMENT);
        WebElement outOfStockElement = webBot.findElement(OUT_OF_STOCK_MESSAGE_LOCATOR);
        return outOfStockElement.getText().contains("WE DO NOT HAVE THE QUANTITY OF STOCK NECESSARY TO FULFILL THIS ORDER");
    }

    public boolean isPaymentAuthorizationErrorDisplayed() {
        assertState(FlowState.PAYMENT);
        WebElement errorElement = webBot.findElement(CARD_ERROR_LOCATOR, WaitTime.FOUR);
        return errorElement.getText().contains("Your card number is invalid");
    }

    public void clickChangeShippingMethod() {
        assertState(FlowState.PAYMENT);

        webBot.click(By.cssSelector("a[title='Change shipping method']"));
    }

    public void clickEditShippingAddress() {
        assertState(FlowState.SHIPPING_OPTION);
        webBot.click(EDIT_SHIPPING_ADDRESS_LOCATOR);
    }

    public void clickAddShippingAddress() {
        assertState(FlowState.SHIPPING_OPTION);
        webBot.click(By.xpath("//a[@title='Add shipping address']"));
    }

    public void clickChangeShippingAddress() {
        assertState(FlowState.PAYMENT);
        webBot.click(CHANGE_SHIPPING_ADDRESS_BUTTON_LOCATOR);
    }

    public List<String> getAllDutiesInPaymentPage() {
        assertState(FlowState.PAYMENT);

        List<String> duties = new ArrayList<String>();

        List<WebElement> dutyElements = webBot.findElements(By.xpath("//table[@class='shopping_bag_items']/tbody/tr/td[" + getColumnIndex(PRICE_HEADER_NAMES[1]) + "]"));

        for (WebElement dutyElement : dutyElements) {
            duties.add(dutyElement.getText());
        }

        return duties;
    }

    public String getShippingMethodOnPaymentPage() {
        assertState(FlowState.PAYMENT);
        WebElement element = webBot.findElement(SHIPPING_METHOD_NAME_IN_PAYMENT_SUMMARY_PAGE);
        return element.getText();
    }

    public List<String> getAllAvailableShippingMethods() {
        WebElement shippingMethodElement = webBot.findElement(By.id("shipping_method"));
        List<WebElement> elements = shippingMethodElement.findElements(By.name("selectedShippingMethodId"));

        List<String> shippingMethods = new ArrayList<String>();

        for (WebElement element : elements) {
            shippingMethods.add(element.getAttribute("value"));
        }

        return shippingMethods;
    }

    public String getShippingMethodSKU(String shippingMethodName) {
        WebElement shippingMethodLabel = webBot.findElement(By.xpath("//label[contains(text(),'" + shippingMethodName + "')]"), WaitTime.FOUR);
        return shippingMethodLabel.getAttribute("for");
    }

    public String getTopErrorMessageOnPaymentPage() {
        WebElement element = webBot.findElement(By.xpath("//div[@id='payment_page_body']/ul[@class='error']/li[@class='error']/a[@class='error']"));
        return element.getText();
    }

    public List<String> getTaxesInPaymentOrConfirmationPage() {
        int taxIndex = getColumnIndex(PRICE_HEADER_NAMES[2]);

        List<String> taxes = new ArrayList<String>();

        for (WebElement dutyElement : webBot.findElements(By.xpath("//table[@class='shopping_bag_items']/tbody/tr/td[" + taxIndex + "]"))) {
            taxes.add(dutyElement.getText());
        }

        return taxes;
    }

    private int getColumnIndex(String columnName) {
        List<WebElement> columnHeaders = webBot.findElements(By.xpath("//table[@class='shopping_bag_items']/thead/tr/th"));

        int columnIndex = 1;
        for (WebElement columnHeader : columnHeaders) {

            if (columnHeader.getText().contains(columnName)) {
                break;
            }
            columnIndex++;
        }
        return columnIndex;
    }


    public String getShippingMethod() {
        WebElement element = webBot.findElement(SHIPPING_METHOD_LOCATOR);

        return element.getText();
    }

    public String getShippingSku(int sortOrder) {
        WebElement element = webBot.findElement(By.xpath("//div[@id='shipping_method']/div[" + sortOrder + "]/div/label"));

        return element.getAttribute("for");
    }

    public void fail3DSecureAuthentication() {
        //Using the datacash simulator click the not authenticated button ACSframe
        selectACSSimulatorButton("Not Authenticated");
    }

    public void pass3DSecureAuthentication() throws Exception {
        //Using the datacash simulator click the authenticated button ACSframe
        selectACSSimulatorButton("Authenticated");
    }

    private void selectACSSimulatorButton(String buttonName) {
        WebElement ACSSimulatorFrame = webBot.findElement(By.name("ACSframe"));
        webBot.getDriver().switchTo().frame(ACSSimulatorFrame);
        WebElement acsButton = webBot.findElement(By.xpath("//input[@value='" + buttonName + "']"));
        acsButton.click();
        webBot.getDriver().switchTo().defaultContent();
    }

    public void selectSaveCreditCard() {
        WebElement saveCardOption = webBot.findElement(PaymentForm.SAVE_CARD_OPTION, WaitTime.FOUR);
        if (!saveCardOption.isSelected()) {
            saveCardOption.click();
        }
    }

    public void deselectSaveCreditCard() {
        WebElement saveCardOption = webBot.findElement(PaymentForm.SAVE_CARD_OPTION, WaitTime.FOUR);
        if (saveCardOption.isSelected()) {
            saveCardOption.click();
        }
    }

    public List<String> getAllShippingOptionTypes() {
        assertState(FlowState.SHIPPING_OPTION);
        List<String> optionTypes = new ArrayList<String>();
        WebElement shippingMethodDiv = webBot.findElement(By.id("shipping_method"));

        List<WebElement> xs = shippingMethodDiv.findElements(By.cssSelector("h4"));
        for(WebElement x : xs)
            optionTypes.add(x.getText().trim());

        return optionTypes;
    }

    public String getSelectedShippingOption() {
        String[][] allShippingOptions = getAllShippingOptions();
        for (String[] allShippingOption : allShippingOptions) {
            if (allShippingOption[0].equalsIgnoreCase("true"))
                return allShippingOption[1];
        }
        return null;
    }

    public String[][] getAllShippingOptions() {
        assertState(FlowState.SHIPPING_OPTION);

        WebElement shippingDiv = webBot.findElement(By.id("shipping_method"));
        List<WebElement> shippingOptions = shippingDiv.findElements(By.cssSelector("div.field_row"));
        String [][] options = new String[shippingOptions.size()][2];

        for(int i=0;i<shippingOptions.size();i++) {
            String optionId = shippingOptions.get(i).findElement(By.cssSelector(".field_col>label")).getText().replace(",","");
            String checked = shippingOptions.get(i).findElement(By.cssSelector("input")).getAttribute("checked");
            String selected = (checked != null) ? "true" : "false";
            options[i][0] = selected;
            options[i][1] = optionId;
        }
        return options;
    }

    public String getChosenPackagingOption() {
        assertState(FlowState.PAYMENT);
        WebElement e = webBot.findElement(By.cssSelector("div.packaging-options-payment p"));
        String optionText = e.getText().split("\n\n")[0];
        return optionText;
    }

    public List<String> getAllPackagingOptions() {
        assertState(FlowState.SHIPPING_OPTION);
        List<String> options = new ArrayList<String>();

        WebElement packagingOptions = webBot.findElement(By.id("packaging_options_info"));
        List<WebElement> optionElements = packagingOptions.findElements(By.cssSelector("div div.packaging_option_description label"));
        for(WebElement e : optionElements)
            options.add(e.getText());

        return options;
    }

    public void selectPackagingOption(String optionName) throws PageElementNotFoundException {
        assertState(FlowState.SHIPPING_OPTION);
        boolean found = false;

        WebElement packagingOptions = webBot.findElement(By.id("packaging_options_info"));
        List<WebElement> optionElements = packagingOptions.findElements(By.cssSelector("div.packaging_option"));
        for(WebElement e : optionElements) {
            String thisOption = e.findElement(By.cssSelector("div.packaging_option_description label")).getText().trim();
            if(optionName.compareTo(thisOption) == 0) {
                found = true;
                e.findElement(By.cssSelector("span input")).click();
                break;
            }
        }

        if(!found)
            throw new PageElementNotFoundException("Could not find packaging option " + optionName + " on page");
    }

    private List<String> getAllPaymentOptions() {
        List<String> options = new ArrayList<String>();

        WebElement paymentElement = webBot.findElement(By.id("card_type"));
        List<WebElement> optionElements = paymentElement.findElements(By.tagName("option"));
        for (WebElement optionElement : optionElements) {
            options.add(optionElement.getAttribute("value"));
        }

        return options;
    }

    public void fillCardDetails(CardPaymentDetails details) {
        fillCardDetails(details.getPaymentDetails(webBot.getRegion()));
    }

    public void fillCardDetails(PaymentDetails paymentDetails) {
        //PSP service will reject cards that have been entered too quickly,
        // so running as a suite may cause this to fail so 90sec delay built in
        // Note: delay removed as we'll retry if fails
        closeDontMissOutPopup();
        webBot.click(By.id(paymentDetails.getCardType().toLowerCase()));
        webBot.type(PaymentForm.CARD_NUMBER, paymentDetails.getCardNumber());
        webBot.type(PaymentForm.CARD_NAME, paymentDetails.getCardholderName() + createRandomString(5));
        webBot.type(PaymentForm.SECURITY_NUMBER, paymentDetails.getSecurityNumber());
        webBot.selectElementText(PaymentForm.EXPIRY_MONTH, paymentDetails.getExpiryMonth());
        webBot.selectElementText(PaymentForm.EXPIRY_YEAR, paymentDetails.getExpiryYear());
        if (paymentDetails.getIssueNumber() != null)
            webBot.type(PaymentForm.ISSUE_NUMBER, paymentDetails.getIssueNumber());
    }


    private String createRandomString(int length) {
        Random random = new Random(new Date().getTime());
        String result = "";
        for(int i = 0; i < length; i++) {
            char newChar = (char) ('a' + random.nextInt(26));

            result += newChar;
        }

        return result;
    }


    private void purchaseNow() {
        if (getSecurityNumberFromPaymentForm().length()==0)
            webBot.setText(NAPPaymentForm.SECURITY_NUMBER, securityNumberUsedForPurchase);
        webBot.findElement(PAY_NOW_LOCATOR, WaitTime.FOUR).click();
    }


    private boolean purchaseWithResult() {
        purchaseNow();
        try {
            assertState(FlowState.CONFIRMATION);
        }
        catch (AssertionError e) {
            try {
                assertState(FlowState.AUTHORISING);
            }
            catch (IllegalStateException e1) {
                return false;
            }
        }
        return true;
    }

    public void assertState(FlowState expectedState) {
        try {
            checkFlowState(expectedState);
        } catch (PageElementNotFoundException e) {
            throw new IllegalStateException("You're in the wrong state. Expected: " + expectedState);
        }
    }

    private void clearRegisteredUserEmailAddress() {
        webBot.clear(REGISTERED_USER_EMAIL_TEXTBOX_LOCATOR);
    }

    private void clickSignInNow() {
        webBot.findElement(SIGN_IN_NOW_BUTTON_LOCATOR, WaitTime.FOUR).click();
    }

    private void enterGuestEmailAddress(String emailAddress) {
        webBot.type(GUEST_EMAIL_ADDRESS_TEXTBOX_LOCATOR, emailAddress);
    }

    public void checkFlowState(FlowState expectedFlowState) {
        String activeStateElementText = webBot.findElement(By.className("active"), WaitTime.FOUR).getText();
        switch (expectedFlowState) {

            case CONFIRMATION:
                assertThat(activeStateElementText, is("CONFIRMATION"));
                break;
            case PAYMENT:
                assertThat(activeStateElementText, is("PAYMENT"));
                break;
            case AUTHORISING:
                try {
                    webBot.findElement(AUTHORISING_FLOW_STATE_LOCATOR, WaitTime.FOUR);
                }
                catch (PageElementNotFoundException e) {
                    throw new IllegalStateException("You are in the wrong state. Expected Authorising");
                }
                break;
            case SHIPPING_ADDRESS:
            case SHIPPING_OPTION:
                assertThat(activeStateElementText, is("SHIPPING"));
                break;
            case SIGN_IN:
                assertThat(activeStateElementText, is("SIGN IN"));
                break;
        }
    }

    public String getDisplayNameForSavedCard(CardType type) {
        return PaymentForm.getExpectedNameForSavedCardType(type);
    }

    public String getDisplayNameForCard(CardType type) {
        return PaymentForm.getExpectedNameForCardType(type);
    }


    public boolean isShippingRestrictionWarningDisplayed() {
        assertState(FlowState.PAYMENT);
        return doesElementExist(By.xpath(".//*[@id='order_summary_section']/table/tbody/tr/td[2]/a"));
    }

    public boolean ishShippingHeaderWarningDisplayed() {
        assertState(FlowState.PAYMENT);
        return doesElementExist(By.xpath(".//*[@id='paymentDetailsForm']/ul/li/a"));
    }

    public void proceedToPurchase() {
        assertState(FlowState.PAYMENT);
        purchaseNow();
    }

    public String getItemTotalInPaymentPage() {
        return webBot.findElement(ITEM_TOTAL_IN_PAYMENT_PAGE_LOCATOR).getText().trim();
    }

    public void saveSecurityNumberFromPaymentForm() {
        String securityNumberFromPage = getSecurityNumberFromPaymentForm();
        if (!securityNumberFromPage.equalsIgnoreCase("0") && securityNumberFromPage.length()>0)
            securityNumberUsedForPurchase = securityNumberFromPage;
    }

    private String getSecurityNumberFromPaymentForm() {
        String securityNumber = "0";
        try {
            securityNumber =  webBot.findElement(NAPPaymentForm.SECURITY_NUMBER, WaitTime.TWO).getAttribute("value");
        }
        catch (PageElementNotFoundException e) {
            return "0";
        }
        if(securityNumber!=null){
            return securityNumber;
        }
        return "0";
    }

    //this method will only return prices that are not subject to a free shipping promotion
    public List<String> getShippingMethodPricesOnShippingOptionsPage() {
        List<String> priceStrings = new ArrayList<String>();
        List<WebElement> priceElements = webBot.findElements(NON_PROMOTION_PRICE_LOCATOR);
        for (WebElement priceElement : priceElements) {
             priceStrings.add(priceElement.getText());
        }
        if(priceStrings.size()!=0)
            return priceStrings;
        fail("Could not get shipping method prices from Shipping Options page");
        return null;
    }

    private String textOf(@Nullable WebElement element) {
        return (element == null) ? "" : element.getText();
    }
}