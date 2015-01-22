package com.netaporter.pws.automation.nap.components;

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
public class RegisterAccountForm extends AbstractPageComponent {

    public static final String DEFAULT_REGISTRATION_PASSWORD = "123456";
    public static final String DEFAULT_FIRST_NAME = "Test";
    public static final String DEFAULT_SURNAME = "User";

    private static By FORM_ID = By.id("register-form");
    public static By FORM_EMAIL_ADDRESS_ID = By.id("emailad");
    private static By FORM_FIRST_NAME_ID = By.id("firstname");
    private static By FORM_SURNAME_ID = By.id("surname");
    private static By FORM_PASSWORD_ID = By.id("password");
    private static By FORM_CONFIRM_PASSWORD_ID = By.id("confpassword");
    private static By FORM_COUNTRY_ID = By.id("country");
    private static By FORM_SUBMIT_LINK_ID = By.id("submitregister");
    private static By FORM_ERROR_CLASS_NAME = By.className("error");

	public void enterRegistrationFormDetails(String email, String password, String firstName, String surname) {
		webBot.type(FORM_EMAIL_ADDRESS_ID, email);
        webBot.type(FORM_FIRST_NAME_ID, firstName);
        webBot.type(FORM_SURNAME_ID, surname);
        webBot.type(FORM_PASSWORD_ID, password);
        webBot.type(FORM_CONFIRM_PASSWORD_ID, password);
	}

    public void enterRegistrationFormDetails(String email, String password, String confirmPassword, String firstName, String surname) {
        webBot.findElement(FORM_EMAIL_ADDRESS_ID).sendKeys(email);
        webBot.findElement(FORM_FIRST_NAME_ID).sendKeys(firstName);
        webBot.findElement(FORM_SURNAME_ID).sendKeys(surname);
        webBot.findElement(FORM_PASSWORD_ID).sendKeys(password);
        webBot.findElement(FORM_CONFIRM_PASSWORD_ID).sendKeys(confirmPassword);
    }


    public void enterPartialRegistrationFormDetails(String password, String firstName, String surname) {
        webBot.type(FORM_FIRST_NAME_ID, firstName);
        webBot.type(FORM_SURNAME_ID, surname);
        webBot.type(FORM_PASSWORD_ID, password);
        webBot.type(FORM_CONFIRM_PASSWORD_ID, password);
    }

    public String enterValidRegistrationFormDetails() {
		String emailAddress = CustomerDetailsUtil.generateEmailAddress();
		String password = DEFAULT_REGISTRATION_PASSWORD;

		enterRegistrationFormDetails(emailAddress, password, DEFAULT_FIRST_NAME, DEFAULT_SURNAME);
        return emailAddress;
    }

    public String enterInvalidEmailAddressOnRegistration() {
        String emailAddress = "invalidemail.com()";
        String password = DEFAULT_REGISTRATION_PASSWORD;

        enterRegistrationFormDetails(emailAddress, password, DEFAULT_FIRST_NAME, DEFAULT_SURNAME);
        return emailAddress;
    }
	public void submit() {
        webBot.click(FORM_SUBMIT_LINK_ID);
    }

    public boolean isRegistrationFormErrorDisplayed() {
        WebElement formError = webBot.findElement(FORM_ERROR_CLASS_NAME);
        return formError.getText().length() > 0;
    }

    public void enterCompletedRegistrationFormDetails() {
        String password = DEFAULT_REGISTRATION_PASSWORD;
        enterPartialRegistrationFormDetails(password, DEFAULT_FIRST_NAME, DEFAULT_SURNAME);
    }

    public void enterExistingEmailAddressOnRegistration(String email){
        String password = DEFAULT_REGISTRATION_PASSWORD;
        enterRegistrationFormDetails(email, password, DEFAULT_FIRST_NAME, DEFAULT_SURNAME);
    }

    public void enterBlankFirstName(){
        String emailAddress = CustomerDetailsUtil.generateEmailAddress();
        String password = DEFAULT_REGISTRATION_PASSWORD;
        enterRegistrationFormDetails(emailAddress,password, "", DEFAULT_SURNAME);
    }

    public void enterIncorrectPasswordLength(){
        String emailAddress = CustomerDetailsUtil.generateEmailAddress();
        String password = "12";
        enterRegistrationFormDetails(emailAddress, password, DEFAULT_FIRST_NAME, DEFAULT_SURNAME);
    }

    public void enterNonMatchingPasswords  (){
        String emailAddress = CustomerDetailsUtil.generateEmailAddress();
        String password = DEFAULT_REGISTRATION_PASSWORD;
        String confirmPassword = "cole";
        enterRegistrationFormDetails(emailAddress,password,confirmPassword, DEFAULT_FIRST_NAME, DEFAULT_SURNAME);
    }

}
