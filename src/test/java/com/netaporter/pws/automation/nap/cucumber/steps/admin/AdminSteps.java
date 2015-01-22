package com.netaporter.pws.automation.nap.cucumber.steps.admin;

import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.pws.automation.nap.cucumber.steps.purchasepath.PurchasePathFlowSteps;
import com.netaporter.pws.automation.shared.pojos.Customer;
import com.netaporter.pws.automation.shared.pojos.PaymentDetails;
import com.netaporter.pws.automation.shared.utils.CardPaymentDetails;
import com.netaporter.pws.automation.shared.utils.CustomerDetailsUtil;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

public class AdminSteps extends BaseNapSteps {

	@Then("^the customer search form is displayed$")
	public void the_customer_search_form_is_displayed() throws Throwable {
		assertTrue(customerSearchPage.isPageDisplayed());
	}

	@When("^I switch to a valid user$")
	public void switch_to_a_valid_user() throws Throwable {
		Customer validCustomer = (Customer)scenarioSession.getData("validCustomer");
		customerSearchPage.switchToUser(validCustomer.getEmail());
	}

    @Given("^I switch to a valid partial user$")
    public void I_switch_to_a_valid_partial_user() throws Throwable {
        String anonEmail = (String)scenarioSession.getData(CustomerDetailsUtil.ANONYMOUS_EMAIL_KEY);
        customerSearchPage.switchToUser(anonEmail);
    }
	@When("^I save 2 new credit card details$")
	public void save_multiple_new_credit_card_details() throws Throwable {
		userCreditCardsPage.go();

        PaymentDetails amexPaymentDetailsForChannel = CardPaymentDetails.AMERICAN_EXPRESS.getPaymentDetails(webBot.getRegion());
        userCreditCardsPage.saveCardDetails(amexPaymentDetailsForChannel);
		scenarioSession.addCollectionData(PurchasePathFlowSteps.SAVED_CREDIT_CARDS_SESSION_DATA, amexPaymentDetailsForChannel);

        PaymentDetails mastercardPaymentDetailsForChannel = CardPaymentDetails.MASTER_CARD.getPaymentDetails(webBot.getRegion());
        userCreditCardsPage.saveCardDetails(mastercardPaymentDetailsForChannel);
        scenarioSession.addCollectionData(PurchasePathFlowSteps.SAVED_CREDIT_CARDS_SESSION_DATA, mastercardPaymentDetailsForChannel);
	}
}
