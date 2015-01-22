package com.netaporter.pws.automation.nap.cucumber.steps.admin;

import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.pws.automation.nap.cucumber.steps.purchasepath.PurchasePathFlowSteps;
import com.netaporter.pws.automation.shared.pojos.PaymentDetails;
import com.netaporter.pws.automation.shared.utils.CardPaymentDetails;
import com.netaporter.pws.automation.shared.utils.CreditCardDetailsDTO;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class UserCreditCardSteps extends BaseNapSteps {

	@When("([\\d]+) credit card details should be displayed")
	@SuppressWarnings("unchecked")
	public void credit_card_details_should_be_displayed(String numberOfCards) throws Throwable {
		List<CreditCardDetailsDTO> creditCards = userCreditCardsPage.getDisplayedCardNumbers();
		//Check the correct number of cards are being displayed
		assertTrue(creditCards.size() == new Integer(numberOfCards));
		Collection<PaymentDetails> cardHistory = (Collection<PaymentDetails>) scenarioSession.getData(PurchasePathFlowSteps.SAVED_CREDIT_CARDS_SESSION_DATA);

		//Verify that all expected cards are displayed
		for(PaymentDetails paymentHistory : cardHistory) {
			boolean found = false;
			String expectedDisplayNameForCardType = paymentHistory.getCardType().toUpperCase();
			String cardNumber = paymentHistory.getCardNumber();

			for(CreditCardDetailsDTO displayedCard : creditCards) {
				boolean matchingType = displayedCard.getType().equals(expectedDisplayNameForCardType);
				boolean matchingNumber = displayedCard.getNumber().equals(maskCardNumber(cardNumber));
				if(matchingNumber && matchingType) {
					found = true;
				}
			}

			assertTrue("Card with type: '" + expectedDisplayNameForCardType + "' and number: '" + cardNumber + "'", found);
		}
	}

	@When("^delete a saved credit card detail$")
	public void delete_a_saved_credit_card_detail() throws Throwable {
		Collection<PaymentDetails> cardHistory = scenarioSession.getData(PurchasePathFlowSteps.SAVED_CREDIT_CARDS_SESSION_DATA);

		assertFalse(cardHistory.isEmpty());
		List<PaymentDetails> historyCopy = new ArrayList<PaymentDetails>(cardHistory);
		Collections.shuffle(historyCopy);

		PaymentDetails randomOption = historyCopy.get(0);

		userCreditCardsPage.deleteCardDetails(randomOption.getCardType(), maskCardNumber(randomOption.getCardNumber()));
		scenarioSession.putData("deletedDetails", randomOption);
	}

	@Then("^deleted credit card detail are not displayed$")
	public void deleted_credit_card_detail_are_not_displayed() throws Throwable {
		PaymentDetails deletedCard = scenarioSession.getData("deletedDetails");
		assertNotNull(deletedCard);

		List<CreditCardDetailsDTO> creditCards = userCreditCardsPage.getDisplayedCardNumbers();

		String deletedType = deletedCard.getCardType();
		String deletedNumber = maskCardNumber(deletedCard.getCardNumber());

		//Verify that all expected cards are displayed
		for(CreditCardDetailsDTO displayedCard : creditCards) {
			if(deletedType.equals(displayedCard.getType()) && deletedNumber.equals(displayedCard.getNumber())) {
				fail("Deleted credit card still present");
			}
		}
	}

	private String maskCardNumber(String cardNumber) {
        int cardNumberLength = cardNumber.length();
        return "xxxx xxxx xxxx " + cardNumber.substring(cardNumberLength -4, cardNumberLength);
	}
}
