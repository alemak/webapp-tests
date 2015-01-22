package com.netaporter.pws.automation.nap.pages;

import com.netaporter.pws.automation.shared.pojos.PaymentDetails;
import com.netaporter.pws.automation.shared.utils.CreditCardDetailsDTO;
import com.netaporter.test.utils.enums.CardType;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("cucumber-glue")
public class UserCreditCardsPage extends AbstractNapPage {

    private static final By CARD_TYPE_INPUT = By.id("card_type");
    private By SAVE_CREDIT_CARD_DETAILS_LOCATOR = By.className("save");
    private By CARD_NUMBER_INPUT = By.id("card_number");
	private By CARD_HOLDER_NAME_INPUT = By.id("cardholders_name");
	private By CARD_EXPIRY_MONTH = By.id("expiry_month");
	private By CARD_EXPIRY_YEAR = By.id("expiryYear");
	private By TABLE_ROWS = By.cssSelector("#saved-cards table tr");

	public UserCreditCardsPage() {
		super("User Credit Cards", "updatecards.nap");
	}

	public String getExpectedDisplayNameForCardType(CardType type) {
		switch(type) {
			case AMERICAN_EXPRESS:
				return "AMEX";
			case JCB:
				//Untested
				return "JCB";
			case MAESTRO_3D_SECURE:
			case MAESTRO:
				//Untested
				return "MAESTRO";
			case VISA_CREDIT_CARD: 
				return "VISA";
			case MASTER_CARD:
				return "MASTERCARD";
			case VISA_DEBIT:
				return "DELTA";
			case VISA_ELECTRON:
				return "ELECTRON";
		}
		throw new IllegalArgumentException("Unexpected card type");
	}

	public List<CreditCardDetailsDTO> getDisplayedCardNumbers() {
		List<CreditCardDetailsDTO> displayedCardNumbers = new ArrayList<CreditCardDetailsDTO>();

		List<WebElement> tableRows = webBot.findElements(TABLE_ROWS);

		for(int i = 1; i < tableRows.size(); i++) {
			WebElement tableRow = tableRows.get(i);
			CreditCardDetailsDTO details = new CreditCardDetailsDTO(
					tableRow.findElement(By.cssSelector("td:nth-child(2)")).getText(), tableRow.findElement(By.cssSelector("td:nth-child(3)")).getText());
			displayedCardNumbers.add(details);
		}
		return displayedCardNumbers;
	}

	public void saveCardDetails(PaymentDetails paymentDetails) {
        webBot.click(By.id(paymentDetails.getCardType()));
        webBot.type(CARD_NUMBER_INPUT, paymentDetails.getCardNumber());
        webBot.type(CARD_HOLDER_NAME_INPUT, paymentDetails.getCardholderName());
        webBot.selectElementText(CARD_EXPIRY_MONTH, paymentDetails.getExpiryMonth());
        webBot.selectElementText(CARD_EXPIRY_YEAR, paymentDetails.getExpiryYear());
		webBot.click(SAVE_CREDIT_CARD_DETAILS_LOCATOR);
	}

	public void deleteCardDetails(String type, String number) {
		List<WebElement> tableRows = webBot.findElements(TABLE_ROWS);

		WebElement paymentElement = null;

		for(int i = 1; i < tableRows.size(); i++) {
			WebElement tableRow = tableRows.get(i);
			if(tableRow.findElement(By.xpath("td[2]")).getText().equalsIgnoreCase(type) && tableRow.findElement(By.xpath("td[3]")).getText().equals(number)) {
				paymentElement = tableRow;
				break;
			}
		}

		Assert.assertNotNull(paymentElement);

		paymentElement.findElement(By.xpath("td[4]/form[@id='updateCreditCardsForm']/input[@type='image']")).click();
	}
}
