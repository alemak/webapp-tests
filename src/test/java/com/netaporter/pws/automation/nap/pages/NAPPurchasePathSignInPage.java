package com.netaporter.pws.automation.nap.pages;

import com.netaporter.pws.automation.shared.pages.purchasePath.PurchasePathSignInPage;
import com.netaporter.test.utils.pages.driver.WaitTime;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: christinakos
 * Date: 11/06/13
 * Time: 16:52
 * To change this template use File | Settings | File Templates.
 */
@Component
@Scope("cucumber-glue")
public class NAPPurchasePathSignInPage extends PurchasePathSignInPage {

    private static final By CHECKOUT_AS_A_GUEST_BUTTON_LOCATOR = By.className("test-guest-customer-signin");
    private static final By SIGN_IN_NOW_BUTTON_LOCATOR = By.className("test-registered-customer-signin");
    private static final By REGISTERED_CUSTOMER_PASSWORD_TEXTBOX_LOCATOR = By.className("test-registered-customer-password");
    private static final By REGISTERED_CUSTOMER_EMAIL_TEXTBOX_LOCATOR = By.className("test-registered-customer-email");
    private static final By GUEST_EMAIL_TEXTBOX_LOCATOR = By.className("test-guest-customer-email");

    public void clickCheckoutAsAGuest() {
        try {
            webBot.waitExplicitly(WaitTime.TWO);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        webBot.findElement(CHECKOUT_AS_A_GUEST_BUTTON_LOCATOR, WaitTime.FOUR).click();
        try {
            webBot.waitExplicitly(WaitTime.TWO);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clickSignInNow() {
        webBot.click(SIGN_IN_NOW_BUTTON_LOCATOR);
    }

    public void enterGuestEmailAddress(String text) {
        webBot.setText(GUEST_EMAIL_TEXTBOX_LOCATOR, text);
    }

    public void enterRegisteredUserEmailAddress(String text) {
        webBot.setText(REGISTERED_CUSTOMER_EMAIL_TEXTBOX_LOCATOR, text);
    }

    public void enterRegisteredUserPassword(String text) {
        webBot.setText(REGISTERED_CUSTOMER_PASSWORD_TEXTBOX_LOCATOR, text);
    }

    public String getRegisteredUserEmailAddress() {
        return webBot.findElement(REGISTERED_CUSTOMER_EMAIL_TEXTBOX_LOCATOR).getAttribute("value");
    }


    public String getGuestEmailAddress() {
        return webBot.findElement(GUEST_EMAIL_TEXTBOX_LOCATOR).getAttribute("value");
    }

    @Override
    public void clickSignIn() {
        //not used anymore in NAP purchase path
    }

    @Override
    public void setYesIhavePassword() {
        //not used anymore in NAP purchase path
    }

    @Override
    public void setAnonymousUser() {
        //not used anymore in NAP purchase path
    }
}
