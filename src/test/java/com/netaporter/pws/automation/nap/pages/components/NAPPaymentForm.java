package com.netaporter.pws.automation.nap.pages.components;

import com.netaporter.pws.automation.shared.components.PaymentForm;
import org.openqa.selenium.By;

/**
 * Created by ocsiki on 12/02/2014.
 */
public class NAPPaymentForm extends PaymentForm {

    public static final By SAVED_CARD_CODE_LOCATOR = By.name("cardToken");
    public static final By SAVED_CARD_HOLDER_LOCATOR = By.className("saved-card-name");
    public static final By SAVED_EXPIRY_DATE_LOCATOR = By.className("saved-card-expiry");
    public static final By SAVED_CARD_NUMBER_LOCATOR = By.className("saved-card-number");
    public static final By AVAILABLE_CARDS_TABLE_LOCATOR = By.className("saved-card-list");
    public static final By CARD_TYPE = By.id("card");
    public static final By CHANGE_CARD_LINK = By.className("different-card-link");
    public static final By MASKED_CARD_NUMBER = By.className("saved-card-number");
}
