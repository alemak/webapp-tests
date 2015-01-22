package com.netaporter.pws.automation.nap.pages;

import com.netaporter.pws.automation.nap.components.RegisterAccountForm;
import com.netaporter.pws.automation.shared.pojos.Customer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Encapsulates the functionality on the Registration page where a user can create a new customer account
 */
@Component
@Scope("cucumber-glue")
public class RegisterNewAccountPage extends AbstractNapPage {

    private static final String PAGE_NAME = "Register New Account";
    private static final String PATH = "lightRegistration.nap";
    private By THANK_YOU_FOR_REGISTERING_MESSAGE = By.id("thankyoupage");
    private static final By REGISTRATION_FORM_ELEMENT = By.id("register-form");
    private By EMAIL_SENT_MESSAGE = By.id("forgot-password-top");
    private By COMPLETE_YOUR_REGISTRATION_MESSAGE = By.id("sp-instructions");

    @Autowired
    private RegisterAccountForm registerAccountForm;

    public RegisterNewAccountPage() {
        super(PAGE_NAME, PATH);
    }

    /**
     *
     * @return the email address used in the registration
     */
    public String enterValidRegistrationFormDetails() {
        return registerAccountForm.enterValidRegistrationFormDetails();
    }

    public void enterCompletedRegistrationFormDetails(){
        registerAccountForm.enterCompletedRegistrationFormDetails();
    }

	public void enterRegistrationFormDetails(Customer customer) {
		registerAccountForm.enterRegistrationFormDetails(customer.getEmail(), customer.getPassword(), customer.getFname(), customer.getSurname());
	}

    public void submitRegistrationForm() {
        closeDontMissOutPopup();
        registerAccountForm.submit();
    }

    public boolean isRegistrationFormErrorDisplayed() {
        return registerAccountForm.isRegistrationFormErrorDisplayed();
    }

    public boolean isThankYouForRegisteringMessageDisplayed() {
        WebElement thankYouMessage = webBot.findElement(THANK_YOU_FOR_REGISTERING_MESSAGE);
        return thankYouMessage.getText().length() > 0;
    }

    public boolean isRegistrationFormPage() {
        WebElement registrationForm = webBot.findElement(REGISTRATION_FORM_ELEMENT);
        return registrationForm.getText().length() > 0;
    }
    
     public boolean isCompleteRegistrationEmailSent() {
        WebElement emailConfirmationMessage = webBot.findElement(EMAIL_SENT_MESSAGE);
        boolean isEmailConfirmationMessage = emailConfirmationMessage!=null;

        WebElement passwordInstructionsDisplayed = webBot.findElement(COMPLETE_YOUR_REGISTRATION_MESSAGE);
        boolean isPasswordInstructionsDisplayed = passwordInstructionsDisplayed!=null;

        return isEmailConfirmationMessage && isPasswordInstructionsDisplayed;
    }

    public void enterBlankFirstName() {
        registerAccountForm.enterBlankFirstName();
    }

    public void enterIncorrectPasswordLength(){
      registerAccountForm.enterIncorrectPasswordLength();
    }

    public String enterInvalidEmailAddressOnRegistration(){
        return registerAccountForm.enterInvalidEmailAddressOnRegistration();
    }

    public void enterExistingEmailAddressOnRegistration(String email){
        registerAccountForm.enterExistingEmailAddressOnRegistration(email);
    }
    public void enterNonMatchingPasswords (){
        registerAccountForm.enterNonMatchingPasswords();
    }

}
