package com.netaporter.pws.automation.napmobileweb.components;

import com.netaporter.pws.automation.shared.utils.CustomerDetailsUtil;
import com.netaporter.test.utils.pages.component.AbstractPageComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Form used to register a new customer account
 */
@Component
@Scope("cucumber-glue")
public class MobileRegisterAccountForm extends AbstractPageComponent {

    public static final String DEFAULT_REGISTRATION_PASSWORD = "123456";
    public static final String DEFAULT_FIRST_NAME = "Test";
    public static final String DEFAULT_SURNAME = "User";

    private static By FORM_EMAIL_ADDRESS_ID = By.id("emailad");
    private static By FORM_FIRST_NAME_ID = By.id("firstname");
    private static By FORM_SURNAME_ID = By.id("surname");
    private static By FORM_PASSWORD_ID = By.id("password");
    private static By FORM_CONFIRM_PASSWORD_ID = By.id("confpassword");
    private static By FORM_SUBMIT_LINK_ID = By.id("submitregister");

	public void enterRegistrationFormDetails(String email, String password, String firstName, String surname) {
		webBot.findElement(FORM_EMAIL_ADDRESS_ID).sendKeys(email);
        webBot.findElement(FORM_FIRST_NAME_ID).sendKeys(firstName);
        webBot.findElement(FORM_SURNAME_ID).sendKeys(surname);
        webBot.findElement(FORM_PASSWORD_ID).sendKeys(password);
        webBot.findElement(FORM_CONFIRM_PASSWORD_ID).sendKeys(password);
	}

    public String enterValidRegistrationFormDetails() {
		String emailAddress = CustomerDetailsUtil.generateEmailAddress();
		String password = DEFAULT_REGISTRATION_PASSWORD;

		enterRegistrationFormDetails(emailAddress, password, DEFAULT_FIRST_NAME, DEFAULT_SURNAME);
        return emailAddress;
    }

    public void submit() {
        WebElement submitLink = webBot.findElement(FORM_SUBMIT_LINK_ID);
        submitLink.click();
    }


}
