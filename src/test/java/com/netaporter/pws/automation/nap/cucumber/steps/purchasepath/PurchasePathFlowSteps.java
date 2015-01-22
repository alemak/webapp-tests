package com.netaporter.pws.automation.nap.cucumber.steps.purchasepath;

import com.netaporter.pws.automation.nap.components.RegisterAccountForm;
import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.pws.automation.nap.pages.NAPProductListPage;
import com.netaporter.pws.automation.nap.pages.PurchasePathFlowPage.FlowState;
import com.netaporter.pws.automation.nap.util.SortOrder;
import com.netaporter.pws.automation.shared.pojos.Address;
import com.netaporter.pws.automation.shared.pojos.PaymentDetails;
import com.netaporter.pws.automation.shared.pojos.Product;
import com.netaporter.pws.automation.shared.steps.purchasePath.BasePurchasePathStep;
import com.netaporter.pws.automation.shared.utils.CardPaymentDetails;
import com.netaporter.pws.automation.shared.utils.CustomerDetailsUtil;
import com.netaporter.pws.automation.shared.utils.MoneyAssertion;
//import com.netaporter.pws.automation.shared.utils.PaymentField;
import com.netaporter.test.client.product.pojos.SearchableProduct;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.collections.list.TreeList;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;

import java.util.*;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * User: x.qi@london.net-a-porter.com
 * Date: 12/10/2012
 */
public class PurchasePathFlowSteps extends BasePurchasePathStep {

    public static final String SAVED_CREDIT_CARDS_SESSION_DATA = "savedCreditCards";
    private static final String STATE = "state";
    private static final String POST_CODE = "postCode";
    public static final String PRODUCTS = "products";
    private static final String OLD_STOCK_LEVELS = "oldStockLevels";
    private static final String EXPECTED_VERTEX_CALCULATED_TAX = "the vertex calculated tax";

    @After(value = "@resetProductStockLevel")
    public void resetProductStockLevel() {
        HashMap<String, Integer> skuAndStockFromScenarioSession = getSkuAndStockFromScenarioSession();

        for (String sku : skuAndStockFromScenarioSession.keySet()) {
            productDataAccess.getLegacyDBClient().updateStockLevel(webBot.getRegionEnum(), sku, skuAndStockFromScenarioSession.get(sku));
        }
    }

    @Then("^My selected shipping method is (.*)$")
    public void selected_shipping_method_is(String expectedShippingMethod) throws Throwable {
        assertEquals(expectedShippingMethod, purchasePathFlowPage.getSelectedShippingOption());
    }

    @Then("^I (should|should not) have a shipping method option type of (.*)$")
    public void check_shipping_method_option_type(String shouldHave, String shippingMethodType) throws Throwable {
        boolean positiveCheck = (shouldHave.equals("should")) ? true : false;
        assertEquals(positiveCheck, purchasePathFlowPage.getAllShippingOptionTypes().contains(shippingMethodType));
    }

    @Given("^I have several items in the Shopping Bag$")
    public void I_have_several_items_in_the_Shopping_Bag() throws Throwable {
        addProductsDirectlyToBag(3);
        shoppingBagPage.go();
    }

    @Given("^I add a product to my shopping bag$")
    public void I_add_a_product_to_my_shopping_bag() throws Throwable {
        addProductsDirectlyToBag(1);
        shoppingBagPage.go();
    }

    @Given("^I add a wedding dress to my shopping bag$")
    public void add_wedding_dress() throws InterruptedException {
        add_product_with_keyword_title("wedding", "dress");
    }

    @Given("^I add a product from with keyword (.*) and title (.*) to my shopping bag$")
    public void add_product_with_keyword_title(String keyword, String title) throws InterruptedException {
        SearchableProduct searchableProduct = productDataAccess.getLegacyDBClient().getRandomAvailableSearchableProductsByKeywordAndTitle(webBot.getRegionEnum(), 1, keyword, title).get(0);
        List<String> skusForPid = productDataAccess.getLegacyDBClient().getSkusForPid(webBot.getRegionEnum(), searchableProduct.getId().toString());
        boolean isSkuInStock = false;
        for (String sku : skusForPid) {
            try {
                isSkuInStock = productDataAccess.isSkuInStock(webBot.getSalesChannelByBrandAndRegion(), sku);
            } catch (NullPointerException ignored) {
                }
            if (isSkuInStock) {
                addProductDirectlyToShoppingBag(webBot.getBaseUrl(), System.getProperty("region"), sku);
                break;
            }
        }
        assertTrue("Failed to add product with keywords "+keyword+title+"to basket", isSkuInStock);
        shoppingBagPage.go();
    }

	@Given("^I add an expensive product to my shopping bag$")
    public void I_add_an_expensive_product_to_my_shopping_bag() throws Throwable {
		addAnExpensiveItemIntoShoppingBag();
		shoppingBagPage.go();
}

	private void addAnExpensiveItemIntoShoppingBag() {
        NAPProductListPage napProductListPage = (NAPProductListPage) goToAnyProductListPage();
        napProductListPage.sortBy(SortOrder.PRICE_HIGH);

		/*
		 *	price treshold of 700 is the current highest sales tax duties as found in the db using the following query:
		 *	select * from country_saletax_and_duties_rule r join country_saletax_and_duties_rule_param p on r.id = p. country_saletax_and_duties_rule_id \G
		 */
        Product product = addAnyInStockProductToShoppingBag((NAPProductListPage)getCurrentNapPage(), NAPProductListPage.PriceRequirement.ABOVEOREQUAL, 700);
		setUpAddedProduct(product);

	}

	private void setUpAddedProduct(Product product) {
		Set<Product> products = (Set<Product>) scenarioSession.getData(PRODUCTS);
		if (products == null) products = new HashSet<Product>();
		products.add(product);
		scenarioSession.putData(PRODUCTS, products);

		Map<String, Integer> oldStockLevels = (Map<String, Integer>) scenarioSession.getData(OLD_STOCK_LEVELS);
		if (oldStockLevels == null) oldStockLevels = new HashMap<String, Integer>();
		Integer oldStockLevel = productDataAccess.getLegacyDBClient().getStockLevelForProductSku(webBot.getRegionEnum(), product.getSku());
		oldStockLevels.put(product.getSku(), oldStockLevel);
		scenarioSession.putData(OLD_STOCK_LEVELS, oldStockLevels);
	}

	@Given("^I have purchased several items$")
    public void I_have_purchased_several_items() throws Throwable {
        I_have_several_items_in_the_Shopping_Bag();

        shoppingBagPage.clickProcceedToPurchase();
        purchasePathFlowPage.checkoutInSignedInState();
        purchasePathFlowPage.clickProceedToPurchaseFromShippingOptionPage();
        purchasePathFlowPage.purchase(CardPaymentDetails.VISA_CREDIT, true);

        purchasePathFlowPage.checkFlowState(FlowState.CONFIRMATION);

        scenarioSession.putData(ORDER_CONFIRMATION_NUMBER, purchasePathFlowPage.getOrderConfirmationNumber());
    }

    @Given("^I have purchased several items with an address from (.*)$")
    public void I_have_purchase_several_items_with_address_from(String country) throws Throwable {
        I_have_several_items_in_the_Shopping_Bag();
        shoppingBagPage.signOut();
        signInPage.go();
        signInPage.signIn((String) scenarioSession.getData(REGISTERED_EMAIL_ADDRESS), RegisterAccountForm.DEFAULT_REGISTRATION_PASSWORD);
        shoppingBagPage.go();
        shoppingBagPage.clickProcceedToPurchase();
        purchasePathFlowPage.checkout();
        purchasePathFlowPage.fillAddressAndContinue(country);
        I_am_on_Payment_page();
        I_am_on_Order_Confirmation_page();
    }

    @When("^I go to Shipping page as an anonymous user$")
    public void I_am_on_Shipping_page() throws Throwable {
        I_go_to_Shipping_address_page_as_an_anonymous_user_without_filling_address();
        purchasePathFlowPage.fillAddressAndContinue();
    }

    @When("^I proceed to purchase as an anonymous user$")
    public void I_proceed_to_purchase_as_an_anonymous_user() throws Throwable {
        I_go_to_Shipping_address_page_as_an_anonymous_user_without_filling_address();
    }

    @Given("^I go to Shipping address page as an anonymous user without filling address$")
    public void I_go_to_Shipping_address_page_as_an_anonymous_user_without_filling_address() throws Throwable {
        shoppingBagPage.clickProcceedToPurchase();

        String anonymousEmail = CustomerDetailsUtil.generateEmailAddress();
        scenarioSession.putData(CustomerDetailsUtil.ANONYMOUS_EMAIL_KEY,anonymousEmail);

        purchasePathFlowPage.checkoutAnonymously(anonymousEmail);
    }

    @When("^I click Forgotten password link$")
    public void click_Forgotten_Password_Link() throws Throwable {
        purchasePathFlowPage.goToForgottenPasswordLink();
    }

    @When("^I go to Shipping page as a signed in user and enter my address$")
    public void I_am_on_Shipping_page_as_a_signed_in_user() throws Throwable {
        shoppingBagPage.clickProcceedToPurchase();
        purchasePathFlowPage.checkoutInSignedInState();
    }

    @When("^I go to Shipping page and use separate billing address$")
    public void I_am_on_Shipping_page_and_use_separate_billing_address() throws Throwable {
        shoppingBagPage.clickProcceedToPurchase();

        String anonymousEmail = CustomerDetailsUtil.generateEmailAddress();
        purchasePathFlowPage.checkoutAnonymously(anonymousEmail);
        scenarioSession.putData(CustomerDetailsUtil.ANONYMOUS_EMAIL_KEY,anonymousEmail);
        purchasePathFlowPage.fillAddressWithDifferentBillingAndContinue();
    }

    @When("^I proceed to purchase$")
    public void I_proceed_to_purchase() throws Throwable {
        shoppingBagPage.clickProcceedToPurchase();
    }

    @When("^I (?:continue to the Shipping page|am taken to the (?:Shipping|Payment) page)$")
    public void I_continue_to_the_first_purchase_path_page() throws Throwable {
        purchasePathFlowPage.checkout();
    }

    @When("^I continue to the Shipping page as an anonymous user$")
    public void I_continue_to_the_shipping_page_as_an_anonymous_user() throws Throwable {
        String anonymousEmail = CustomerDetailsUtil.generateEmailAddress();
        purchasePathFlowPage.checkoutAnonymously(anonymousEmail);
    }

    @When("^I continue to the Shipping page with express checkout$")
    public void I_proceed_with_express_checkout() throws Throwable {
        String emailAddress = (String) scenarioSession.getData("registeredEmailAddress");
        purchasePathFlowPage.expressCheckout(emailAddress, "123456");
    }

    @When("^I enter an address from (.*)$")
    public void I_enter_an_address_from(String country) throws Throwable {
        purchasePathFlowPage.fillAddressAndContinue(country);
    }

    @When("^I enter an (US Premier|US Midwest|US Alaska|France|Germany|Belgium|United Kingdom Non Premier|United Kingdom Premier|United Kingdom Standard|Australia Next Day|Australia Express)$")
    public void I_enter_a_specified_address(String addressType) throws Throwable {
        if ("US Premier".equals(addressType))
            purchasePathFlowPage.fillPremierAddressAndContinue();
        else if ("US Midwest".equals(addressType))
            purchasePathFlowPage.fillUSAddressAndContinue("Kansas","67536");
        else if ("US Alaska".equals(addressType))
            purchasePathFlowPage.fillUSAddressAndContinue("Alaska", "99501");
        else if ("France".equalsIgnoreCase(addressType))
            purchasePathFlowPage.fillAddressAndContinue(Address.createAnotherAddress("France"), true);
        else if ("Germany".equalsIgnoreCase(addressType))
            purchasePathFlowPage.fillAddressAndContinue(Address.createAnotherAddress("Germany"), true);
        else if ("Belgium".equalsIgnoreCase((addressType)))
            purchasePathFlowPage.fillAddressAndContinue(Address.createAnotherAddress("Belgium"), true);
        else if ("United Kingdom Non Premier".equalsIgnoreCase(addressType))
            purchasePathFlowPage.fillAddressAndContinue(Address.createUKNonLondonAddress(), true);
        else if ("United Kingdom Premier".equalsIgnoreCase(addressType))
            purchasePathFlowPage.fillAddressAndContinue(Address.createNAPAddress(), true);
        else if ("United Kingdom Standard".equalsIgnoreCase(addressType))
            purchasePathFlowPage.fillAddressAndContinue(Address.createUKStandardAddress(), true);
        else if ("Australia Next Day".equalsIgnoreCase(addressType))
            purchasePathFlowPage.fillAddressAndContinue(Address.createAUNextDayAddress(), true);
        else if ("Australia Express".equalsIgnoreCase(addressType))
            purchasePathFlowPage.fillAddressAndContinue(Address.createAUExpressAddress(), true);
    }

    @When("^I choose to change my shipping method$")
    public void I_choose_to_change_my_shipping_method() {
        purchasePathFlowPage.clickChangeShippingMethod();
    }

    @Given("^I fill the address page form with (.*) (.*) shipping address$")
    public void I_fill_the_address_page_form_with_NY_shipping_address(String state, String postCode) throws Throwable {
        purchasePathFlowPage.fillUSAddressAndContinue(state, postCode);
        scenarioSession.putData(STATE, state);
        scenarioSession.putData(POST_CODE, postCode);
    }

    @Given("^I fill in shipping address with (.*) and (.*) and (.*) in the address form$")
    public void I_fill_in_shipping_address_with_United_States_and_New_York_and_in_the_address_form(String country, String state, String postCode) {
        purchasePathFlowPage.fillAddressAndContinue(Address.createAnotherAddress(country, state, postCode), true);
    }

    @When("^I proceed to the Payment page$")
    public void I_am_on_Payment_page() throws Throwable {
        purchasePathFlowPage.clickProceedToPurchaseFromShippingOptionPage();
    }

    @When("^I am on Order Confirmation page$")
    public void I_am_on_Order_Confirmation_page() throws Throwable {
        purchasePathFlowPage.purchase(CardPaymentDetails.VISA_CREDIT, true);
        purchasePathFlowPage.checkFlowState(FlowState.CONFIRMATION);
    }

    @When("^I pay by (.*)$")
    public void I_pay_by(String cardType) {
        CardPaymentDetails cardPaymentDetails = CardPaymentDetails.getCardPaymentDetails(cardType);
        purchasePathFlowPage.purchase(cardPaymentDetails, true);
    }

//    @When("^I pay by ([^:]+):$")
//    public void payWithCard(String cardTypeName, Map<String,String> values) {
//        PaymentDetails defaultPaymentDetails = CardPaymentDetails.getCardPaymentDetails(cardTypeName).getPaymentDetails(webBot.getRegion());
//
//        PaymentDetails actualPaymentDetails = new PaymentDetails(
//                defaultPaymentDetails.getCardType(),
//                PaymentField.cardNumber.fromOrElse(values, defaultPaymentDetails.getCardNumber()),
//                PaymentField.cardholderName.fromOrElse(values, defaultPaymentDetails.getCardholderName()),
//                PaymentField.securityNumber.fromOrElse(values, defaultPaymentDetails.getSecurityNumber()),
//                PaymentField.expiryMonth.fromOrElse(values, defaultPaymentDetails.getExpiryMonth()),
//                PaymentField.expiryYear.fromOrElse(values, defaultPaymentDetails.getExpiryYear()),
//                PaymentField.issueNumber.fromOrElse(values, defaultPaymentDetails.getIssueNumber())
//        );
//
//        purchasePathFlowPage.purchase(actualPaymentDetails, true);
//    }

    @Given("^I attempt to pay by (.*)$")
    public void I_attempt_to_pay_by_MAESTRO_D_SECURE(String cardType) throws Throwable {
        CardPaymentDetails cardPaymentDetails = CardPaymentDetails.getCardPaymentDetails(cardType);
        purchasePathFlowPage.purchase(cardPaymentDetails, false);
    }

    @When("^I use an invalid card to pay$")
    public void I_pay_by_an_invalid_card() {
        purchasePathFlowPage.purchaseWithAnInvalidCard();
    }

    @When("^Order Confirmation page is displayed$")
    public void Order_Confirmation_page_is_displayed() throws Throwable {
        purchasePathFlowPage.checkFlowState(FlowState.CONFIRMATION);
        Integer orderId = purchasePathFlowPage.getOrderId();
        scenarioSession.putData("latestOrderId", orderId);
    }

    @Given("^3d secure payment page is displayed$")
    public void ThreeD_Secure_payment_page_is_displayed() throws Throwable {
        purchasePathFlowPage.is3DSecurePage();
    }

    @When("^3d secure payment is not authorised$")
    public void ThreeD_Secure_Payment_Is_Not_Authorised() throws Throwable {
        purchasePathFlowPage.fail3DSecureAuthentication();
    }

    @When("^3d secure payment is authorised$")
    public void ThreeD_Secure_Payment_Is_Authorised() throws Throwable {
        purchasePathFlowPage.pass3DSecureAuthentication();
    }

    @Then("^I should see an error message on the payment page$")
    public void Payment_Details_Page_Showing_Error() throws Throwable {
        purchasePathFlowPage.checkFlowState(FlowState.PAYMENT);
        assertTrue("Error message is not displayed", isElementPresent(purchasePathFlowPage.ERROR));
    }

    @When("^I click on Edit shipping address from Shipping options page$")
    public void I_change_shipping_address() throws Throwable {
        purchasePathFlowPage.clickEditShippingAddress();
    }

    @When("^I click on Add shipping address from Shipping options page$")
    public void I_click_add_shipping_address() throws Throwable {
        purchasePathFlowPage.clickAddShippingAddress();
    }

    @When("^I click on Change shipping address link from Payment page$")
    public void I_click_change_shipping_address() throws Throwable {
        purchasePathFlowPage.clickChangeShippingAddress();
    }

    @When("^I change billing address$")
    public void I_change_billing_address() throws Throwable {
        purchasePathFlowPage.fillBillingAddressAndContinue();
    }

    @Given("^I am on the purchase path signin page$")
    public void __am_on_the_purchase_path_sign_in_page() throws Throwable {
        shoppingBagPage.clickProcceedToPurchase();
        shoppingBagPage.closeDontMissOutPopup();
    }


    @When("^the item goes sold out$")
    public void makeProductSoldOut() {
        HashMap<String, Integer> skuAndStockFromScenarioSession = getSkuAndStockFromScenarioSession();
        for (String sku : skuAndStockFromScenarioSession.keySet()) {
            productDataAccess.getLegacyDBClient().updateStockLevel(webBot.getRegionEnum(), sku, 0);
        }
    }

    @Then("^I should see all costs are priced in (.*)$")
    public void I_should_see_all_costs_are_priced_correctly(String currency) throws Throwable {
        String subTotal = purchasePathFlowPage.getSubTotalCurrencyInPaymentOrConfirmationPage();
        Thread.sleep(1000);
        Assert.assertTrue(currency.contains(subTotal));
    }

    @Then("^I should see all payment costs are priced in (.*)$")
    public void I_should_see_all_payment_costs_are_priced_correctly(String currency) throws Throwable {
        String subTotal = purchasePathFlowPage.getSubTotalCurrencyInPaymentOrConfirmationPage();
        Assert.assertEquals(currency, subTotal);
    }

    @Then("^I should see the shipping cost is priced in (.*)$")
    public void I_should_see_the_shipping_cost_is_priced_correctly(String currency) throws Throwable {
        List<String> allPrices = purchasePathFlowPage.getAllPricesInShippingOptionPage();
        String currencySymbol = currency.substring(currency.length() - 1);
        MoneyAssertion.assertCorrectCurrencies(currencySymbol, allPrices);
    }

    @Then("^Maestro is a payment option: (true|false)$")
    public void MaestroIsAPaymentOption(boolean isAnOption) {
        List<String> paymentOptions = purchasePathFlowPage.getAvailableCreditCards();
        for (String paymentOption : paymentOptions) {
            if (isAnOption) {
                if (paymentOption.equalsIgnoreCase("MAESTRO"))
                    return;
                }
            else {
                if (paymentOption.equalsIgnoreCase("MAESTRO"))
                    fail("Payment option contains MAESTRO when it should not: "+paymentOption);
           }
        }
    }

    @Then("^the product should be marked as sold out$")
    public void the_product_should_be_marked_as_sold_out() {
        assertTrue(purchasePathFlowPage.isProductSoldOutMessageDisplayed());
    }

    @Then("^Customer pays duty: (true|false)$")
    public void customer_pays_duty(boolean payDuty) {
        List<String> duties = purchasePathFlowPage.getAllDutiesInPaymentPage();
        final String productSkuOrPid = (String) scenarioSession.getData(BaseNapSteps.SKU_OR_PID_KEY);
        for (String duty : duties) {
            MoneyAssertion.assertPayDuty(payDuty, duty, productSkuOrPid);
        }
    }

    @When("^I choose to deliver to (.*) for the order when checking out$")
    public void I_choose_to_deliver_to_a_country_for_the_order_when_checking_out(String country) throws Throwable {
        I_have_several_items_in_the_Shopping_Bag();

        shoppingBagPage.clickProcceedToPurchase();

        String anonymousEmail = CustomerDetailsUtil.generateEmailAddress();
        scenarioSession.putData(CustomerDetailsUtil.ANONYMOUS_EMAIL_KEY, anonymousEmail);

        purchasePathFlowPage.checkoutAnonymously(anonymousEmail);

        purchasePathFlowPage.fillAddressAndContinue(country);
    }

    @Then("^I will be offered (.*) shipping method$")
    public void I_will_be_offered_a_option_shipping_method(String option) throws Throwable {
        String shippingMethod = purchasePathFlowPage.getShippingMethod();
        assertEquals(option, shippingMethod);
    }

    @Then("^Its shipping sku is (.*)$")
    public void Its_shipping_sku_is_(String shippingSku) throws Throwable {
        String myShippingSku = purchasePathFlowPage.getShippingSku(1);
        assertEquals(shippingSku, myShippingSku);
    }

    @Then("^Its shipping sku are (.*) and (.*) and (.*)$")
    public void Its_shipping_skus_are_(String shippingSku1, String shippingSku2, String shippingSku3) throws Throwable {

        if (shippingSku2 == null)
            assertEquals(shippingSku1, purchasePathFlowPage.getShippingSku(1));

        if (shippingSku2 != null && !shippingSku2.isEmpty()) {
            assertEquals(shippingSku1, purchasePathFlowPage.getShippingSku(1));
            assertEquals(shippingSku2, purchasePathFlowPage.getShippingSku(2));
        }

        if (shippingSku3 != null && !shippingSku3.isEmpty()) {
            assertEquals(shippingSku3, purchasePathFlowPage.getShippingSku(3));
        }
    }

    @Given("^a customer has saved their credit card details$")
    public void a_customer_has_saved_their_credit_card_details() throws Throwable {
        addItemToBagAndGoToPaymentPage();
        purchasePathFlowPage.purchase(CardPaymentDetails.VISA_CREDIT, true);
        scenarioSession.addCollectionData(SAVED_CREDIT_CARDS_SESSION_DATA, CardPaymentDetails.VISA_CREDIT.getPaymentDetails(webBot.getRegion()));
        scenarioSession.removeData(ITEMS_ADDED_TO_BAG);
        scenarioSession.removeData(ITEMS_ADDED_TO_BAG_WITH_STOCK);

        purchasePathFlowPage.checkFlowState(FlowState.CONFIRMATION);
    }

    @Given("^a customer has saved their 3d secure credit card details$")
    public void a_customer_has_saved_their_3d_credit_card_details() throws Throwable {
        addItemToBagAndGoToPaymentPage();
        purchasePathFlowPage.purchase(CardPaymentDetails.MAESTRO_3D_SECURE, true);
        scenarioSession.addCollectionData(SAVED_CREDIT_CARDS_SESSION_DATA, CardPaymentDetails.MAESTRO_3D_SECURE.getPaymentDetails(webBot.getRegion()));
        purchasePathFlowPage.pass3DSecureAuthentication();
        scenarioSession.removeData(ITEMS_ADDED_TO_BAG);
        scenarioSession.removeData(ITEMS_ADDED_TO_BAG_WITH_STOCK);

        purchasePathFlowPage.checkFlowState(FlowState.CONFIRMATION);
    }

    private void addItemToBagAndGoToPaymentPage() throws InterruptedException {
        addAnItemIntoShoppingBag();
        shoppingBagPage.go();
        shoppingBagPage.clickProcceedToPurchase();
        purchasePathFlowPage.checkoutInSignedInState();
        purchasePathFlowPage.clickProceedToPurchaseFromShippingOptionPage();
        purchasePathFlowPage.selectSaveCreditCard();
    }

    /**
     * This step makes a purchase as the registered customer, ensuring the 'save credit card details' option is not selected
     *
     * @throws Throwable
     */
    @Given("^a customer who has not saved their credit card details$")
    public void a_customer_has_not_saved_their_credit_card_details() throws Throwable {
        addAnItemIntoShoppingBag();
        shoppingBagPage.go();
        shoppingBagPage.clickProcceedToPurchase();

        purchasePathFlowPage.checkoutInSignedInState();
        purchasePathFlowPage.clickProceedToPurchaseFromShippingOptionPage();
        purchasePathFlowPage.deselectSaveCreditCard();
        purchasePathFlowPage.purchase(CardPaymentDetails.VISA_CREDIT, true);

        purchasePathFlowPage.checkFlowState(FlowState.CONFIRMATION);
    }

    @Given("^I proceed to the payment page using express checkout$")
    public void proceed_to_the_payment_page_using_express_checkout() throws Throwable {
        shoppingBagPage.go();
        shoppingBagPage.clickProcceedToPurchase();
        purchasePathFlowPage.checkFlowState(FlowState.PAYMENT);
    }

    @Then("^the credit card details should have been remembered$")
    public void the_credit_card_details_should_have_been_remembered() throws Throwable {

        @SuppressWarnings("checked")
        ArrayList<PaymentDetails> savedCardDetails = scenarioSession.getData(SAVED_CREDIT_CARDS_SESSION_DATA);
        PaymentDetails firstSavedPaymentDetails = savedCardDetails.get(0);
        assertCreditCardDetails(firstSavedPaymentDetails);
    }

    private void assertCreditCardDetails(PaymentDetails firstSavedPaymentDetails) {
        assertThat(firstNCharactersOf(8, purchasePathFlowPage.getSavedCardHolderName()),
                is(equalTo(firstSavedPaymentDetails.getCardholderName())));

        String savedCardNumber = firstSavedPaymentDetails.getCardNumber();
        String cardNumberFromPage = purchasePathFlowPage.getSavedCardNumber();
        assertTrue("Saved card number " + savedCardNumber + " does not match the card number from the page " + cardNumberFromPage, savedCardNumber.regionMatches(savedCardNumber.length() - 4, cardNumberFromPage, cardNumberFromPage.length()-4, 4));

        assertThat(firstSavedPaymentDetails.getExpiryDate(), is(equalTo(purchasePathFlowPage.getSavedExpiryDate())));
    }

    private String firstNCharactersOf(int n, String s) {
        return s.substring(0, Math.min(n, s.length()));
    }

    @Then("^I verify the last 4 digits of the payment card number is displayed$")
    public void verifyPaymentCardNumberIsDisplayed(){
        String savedPaymentCardNumber = scenarioSession.getData("savedPaymentCardNumber");
        if (savedPaymentCardNumber == null){
            // this is set when entering the initial card number
            savedPaymentCardNumber = scenarioSession.getData("paymentCardNumber");
        }
        String last4Digits = savedPaymentCardNumber.substring(savedPaymentCardNumber.trim().length() - 4);
        assertTrue(purchasePathFlowPage.getSavedCardNumber().contains(last4Digits));
    }

    @Then("^the credit card details should not have been remembered$")
    public void the_credit_card_details_should_not_have_been_remembered() throws Throwable {
        assertTrue(purchasePathFlowPage.isCreditCardNumberInputEnabled());
        assertTrue(purchasePathFlowPage.isCardholderNameInputEnabled());
        assertTrue(purchasePathFlowPage.isCvvEnabled());

        assertTrue(StringUtils.isBlank(purchasePathFlowPage.getCreditCardNumber()));
        assertTrue(StringUtils.isBlank(purchasePathFlowPage.getCardHolderName()));
        assertTrue(StringUtils.isBlank(purchasePathFlowPage.getCvvNumber()));

        String expiryMonth = purchasePathFlowPage.getExpiryDateMonth();
        //The default value of the month drop down should be a word, not a number
        assertTrue(expiryMonth.matches("\\D*"));

        //The default value of the year drop down should be a word, not a number
        String expiryYear = purchasePathFlowPage.getExpiryDateYear();
        assertTrue(expiryYear.matches("\\D*"));
    }

    @When("^I select the use different payment method link$")
    public void select_the_use_different_payment_method_link() throws Throwable {
        purchasePathFlowPage.selectUseDifferentPaymentMethodLink();
    }

    @When("^I complete the purchase without entering details$")
    public void complete_the_purchase() throws Throwable {
        purchasePathFlowPage.purchase((PaymentDetails)null, true);
    }

    @When("^I attempt to purchase without entering details$")
    public void I_attempt_to_purchase_without_entering_details() throws Throwable {
        purchasePathFlowPage.proceedToPurchase();
    }

    @When("^I deselect the save credit cards box$")
    public void deselect_the_save_credit_card_box() throws Throwable {
        purchasePathFlowPage.deselectSaveCreditCard();
    }

    @Then("^Payment authorization error message is displayed$")
    public void Payment_authorization_error() {
        assertTrue("Payment error message is not displayed", purchasePathFlowPage.isPaymentAuthorizationErrorDisplayed());
    }

    @Then("^Product stock is not reduced$")
    public void Product_stock_is_not_reduced() {
        HashMap<String, Integer> skuAndStockLevelMapFromSession = getSkuAndStockFromScenarioSession();

        for (String sku : skuAndStockLevelMapFromSession.keySet()) {
            Integer currentStockLevel = productDataAccess.getLegacyDBClient().getStockLevelForProductSku(webBot.getRegionEnum(), sku);
            assertEquals("Product "+sku+" stock was reduced when it shouldn't have been", skuAndStockLevelMapFromSession.get(sku), currentStockLevel);
        }
    }

    @Then("^change card drop down should display all stored cards$")
    public void change_card_drop_down_should_display_all_stored_cards() throws Throwable {
        List<TreeList> availableCreditCardsOptions = purchasePathFlowPage.getSavedCreditCardsOptions();

        @SuppressWarnings("unchecked")
        List<PaymentDetails> cardHistory = (List<PaymentDetails>) scenarioSession.getData(PurchasePathFlowSteps.SAVED_CREDIT_CARDS_SESSION_DATA);

        assertTrue(availableCreditCardsOptions.size() == cardHistory.size());

        List<PaymentDetails> cardHistoryCopy = new ArrayList<PaymentDetails>(cardHistory);

        for (PaymentDetails cardHistoryDetails : cardHistory) {
            for (TreeList availableCreditCardsOption : availableCreditCardsOptions) {
                if (cardHistoryDetails.getCardType().equalsIgnoreCase(availableCreditCardsOption.get(0).toString())) {
                    String savedCardNumber = cardHistoryDetails.getCardNumber();
                    String retrievedCardNumber = availableCreditCardsOption.get(1).toString();
                    assertTrue(savedCardNumber.regionMatches(savedCardNumber.length() - 4, retrievedCardNumber, retrievedCardNumber.length() - 4, 4));
//                    generated test cardholder name contains a random string, ignoring it for the test
                    assertThat(availableCreditCardsOption.get(2).toString(), containsString(cardHistoryDetails.getCardholderName()));
                    assertThat(cardHistoryDetails.getExpiryDate(), is(availableCreditCardsOption.get(3)));
                    cardHistoryCopy.remove(cardHistoryDetails);
                }
            }
        }

        assertTrue("Some payment details were not remembered: "+cardHistoryCopy, cardHistoryCopy.isEmpty());
    }

    @When("^change the selected stored card details$")
    public void change_the_selected_stored_card_details() throws Throwable {
        purchasePathFlowPage.changeSelectedOption();
    }

    private String constructMaskedNumber(String cardNumber) {
        return "xxxxxxxxxxxx" + cardNumber.substring(12, 16);
    }

    private String constructDropDownOption(CardPaymentDetails paymentHistory) {
        String cardDisplayName = purchasePathFlowPage.getDisplayNameForSavedCard(paymentHistory.getType());
        String cardNumber = paymentHistory.getPaymentDetails(webBot.getRegion()).getCardNumber();
        int cardNumberLength = cardNumber.length();
        String finalFourDigits = cardNumber.substring(cardNumberLength - 4, cardNumberLength);

        return cardDisplayName + "-" + finalFourDigits;
    }

    //this is used to work around items with discounted prices (the discount does not show up on the Order Confirmation page and the vertex tax is calculated incorrectly)
    @Then("^in the Confirmation page the product is being taxed correctly$")
    public void in_the_Confirmation_page_the_product_is_being_taxed_correctly() throws Throwable {
        String sku = getSkusFromScenarioSession().get(0);
        Double actualTaxDouble = Double.valueOf(purchasePathFlowPage.getTaxesInPaymentOrConfirmationPage().get(0).substring(1));

        MoneyAssertion.assertVertexEquals(sku, (Double) scenarioSession.getData(EXPECTED_VERTEX_CALCULATED_TAX), actualTaxDouble);
    }

    @Then("^the Item total calculation is correct$")
    public void the_Item_total_is_correct() throws Throwable {
        List<String> subTotals = purchasePathFlowPage.getAllSubtotals();
        String itemTotal = purchasePathFlowPage.getItemTotalInPaymentPage();

        MoneyAssertion.assertCalculatedSumIsTheSameAsGivenTotal(subTotals, itemTotal);
    }

    @Given("^I fill in my payment details using a valid credit card (.*)$")
    public void I_fill_in_my_payment_details_using_a_valid_credit_card_VISA_CREDIT_CARD(String cardType) {
        purchasePathFlowPage.fillCardDetails(CardPaymentDetails.getCardPaymentDetails(cardType));
    }

    @When("^I proceed to purchase on Payment page using a valid credit card (.*)$")
    public void I_proceed_to_purchase_on_Payment_page_using_a_valid_credit_card_VISA_CREDIT_CARD(String cardType) {
        purchasePathFlowPage.purchase(CardPaymentDetails.getCardPaymentDetails(cardType), true);
    }

    @Then("^I should see product sold out message in shopping bag page$")
    public void I_should_see_product_sold_out_message_in_shopping_bag_page() throws Throwable {
        assertTrue("Current url does not end with shoppingbag.nap: "+webBot.getCurrentUrl(), webBot.getCurrentUrl().endsWith("shoppingbag.nap"));
        assertTrue("Error message is not displayed in Shopping Bag page", shoppingBagPage.isErrorMessageDisplayed());
    }

    @And("^I proceed to the payment page$")
    public void I_proceed_to_the_payment_page() throws Throwable {
        shoppingBagPage.go();
        shoppingBagPage.clickProcceedToPurchase();
        purchasePathFlowPage.clickProceedToPurchaseFromShippingOptionPage();
    }

    @When("^I choose to use a different payment type$")
    public void I_choose_to_use_a_different_payment_type() throws Throwable {
        purchasePathFlowPage.selectUseDifferentPaymentMethodLink();
    }

    @And("^I edit the shipping address in the purchase path$")
    public void I_edit_the_shipping_address_in_the_purchase_path() throws Throwable {
        I_click_change_shipping_address();
        I_change_shipping_address();
        I_fill_in_shipping_address_with_United_States_and_New_York_and_in_the_address_form("United States", "New York", "10001");
    }

    @And("^I have successfully purchased an item using a different billing address$")
    public void I_have_successfully_purchased_an_item_using_a_different_billing_address() throws Throwable {
        addProductsDirectlyToBag(1);
        shoppingBagPage.go();
        shoppingBagPage.clickProcceedToPurchase();
        purchasePathFlowPage.fillAddressWithDifferentBillingAndContinue();
        purchasePathFlowPage.clickProceedToPurchaseFromShippingOptionPage();
        purchasePathFlowPage.fillBillingAddressAndContinue();
        purchasePathFlowPage.purchase(CardPaymentDetails.VISA_DEBIT, true);
    }

    @Then("^I am on the payment summary page$")
    public void I_am_on_the_payment_summary_page() throws Throwable {
        purchasePathFlowPage.assertState(FlowState.PAYMENT);
    }

    @And("^I dispatch the order$")
    public void I_dispatch_the_order() throws Throwable {
        ArrayList<String> skusFromScenarioSession = getSkusFromScenarioSession();

        String orderConfirmationNumber = scenarioSession.getData(ORDER_CONFIRMATION_NUMBER);

        if (skusFromScenarioSession.size()==1)
            dispatchSingleSkuOrder(orderConfirmationNumber, skusFromScenarioSession.get(0));
        else
            dispatchThreeSkuOrder(orderConfirmationNumber, skusFromScenarioSession);
    }
}