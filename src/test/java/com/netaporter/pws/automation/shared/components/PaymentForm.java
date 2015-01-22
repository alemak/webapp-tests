package com.netaporter.pws.automation.shared.components;

import com.netaporter.test.utils.enums.CardType;
import org.openqa.selenium.By;

public class PaymentForm {

    public static final By CARD_NUMBER = By.id("card_number");
	public static final By MASKED_CARD_NUMBER = By.id("creditCard.maskedCardNumber");
    public static final By ERROR_CARD_NUMBER = By.xpath("//ul[@class='error']/li[1]");
    public static final By CARD_TYPE = By.id("card_type");
    public static final By CARD_NAME = By.id("cardholders_name");
    public static final By SECURITY_NUMBER = By.id("security_number");
	public static final By EXPIRY_MONTH = By.id("expiry_month");
    public static final By EXPIRY_YEAR = By.id("expiryYear");
    public static final By ISSUE_NUMBER = By.id("issue_number");
	public static final By SAVE_CARD_OPTION = By.id("keep_card");

	public static String getExpectedNameForSavedCardType(CardType type) {
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
		throw new RuntimeException("Unexpected card type");
	}

	public static String getExpectedNameForCardType(CardType type) {
		switch(type) {
			case AMERICAN_EXPRESS:
				return "American Express";
			case JCB:
				//Untested
				return "JCB";
			case MAESTRO_3D_SECURE:
			case MAESTRO:
				//Untested
				return "Maestro";
			case VISA_CREDIT_CARD:
				return "Visa";
			case MASTER_CARD:
				return "MasterCard";
			case VISA_DEBIT:
				return "Visa Debit / Delta";
			case VISA_ELECTRON:
				return "Visa Electron";
		}
		throw new IllegalArgumentException("Unexpected card type");
	}

    public static String getPaymentOption(String type) {
        if (type.equals("AMERICAN_EXPRESS")) {
            return "amex";
        } else if (type.equals("JCB")) {
            return "jcb";
        } else if (type.equals("MAESTRO_3D_SECURE") || type.equals("MAESTRO")) {
            return "maestro";
        } else if (type.equals("VISA_CREDIT_CARD")) {
            return "visa";
        } else if (type.equals("MASTER_CARD")) {
            return "mastercard";
        } else if (type.equals("VISA_DEBIT")) {
            return "visa";
        } else if (type.equals("VISA_ELECTRON")) {
            return "visa";
        } else if (type.equals("PAYPAL")) {
            return "paypal";
        } else if (type.equals("DISCOVER")) {
            return "discover";
        }
        throw new IllegalArgumentException("Unexpected payment option");
    }
}
