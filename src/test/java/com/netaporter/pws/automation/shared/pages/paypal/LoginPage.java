package com.netaporter.pws.automation.shared.pages.paypal;

import org.openqa.selenium.By;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("cucumber-glue")
public class LoginPage extends PaypalBasePage {

    private static final String PAGE_NAME = "Paypal login";
    private static final String PATH = "";

    private By EMAIL_FIELD_LOCATOR = By.id("login_email");
    private By PASSWORD_FIELD_LOCATOR = By.id("login_password");
    private By LOGIN_TITLE_LOCATOR = By.id("loginTitle");
    private By LOGIN_BUTTON_LOCATOR = By.id("submitLogin");

    public LoginPage() {
        super(PAGE_NAME, PATH);
    }

    public void enterUserDetails(String userEmail, String password) {
        webBot.type(EMAIL_FIELD_LOCATOR, userEmail);
        webBot.type(PASSWORD_FIELD_LOCATOR, password);
    }

    public void clickLogInButton() throws InterruptedException {
        checkPositionOfElementAndClick(LOGIN_BUTTON_LOCATOR);
    }

    public String getContactEmail() {
        return webBot.getElementValue(EMAIL_FIELD_LOCATOR);
    }

    public String getLoginTitle() {
        return webBot.getElementText(LOGIN_TITLE_LOCATOR);
    }
}
