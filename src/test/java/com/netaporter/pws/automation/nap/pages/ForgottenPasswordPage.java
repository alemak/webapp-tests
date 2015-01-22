package com.netaporter.pws.automation.nap.pages;

import com.netaporter.test.utils.pages.driver.WaitTime;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * Encapsulates the functionality on the Forgotten Password page where a user can generate an email to reset their password
 */
@Component
@Scope("cucumber-glue")
public class ForgottenPasswordPage extends AbstractNapPage {

    private static final String PAGE_NAME = "Forgotten Password";
    private static final String PATH = "forgottenpassword.nap";

    private By SUBMIT_EMAIL_ADDRESS_LINK = By.xpath("//*[@id='forgottenPasswordForm']/input");
    private By FORGOTTEN_PASSWORD_FORM = By.xpath("//*[@id='forgottenPasswordForm']");
    private By EMAIL_ADDRESS_FIELD = By.id("emailAddr");
    private By EMAIL_CONFIRMATION_MESSAGE = By.id("forgot-password-top");
    private By FORGOTTEN_PASSWORD_INSTRUCTIONS = By.id("forgot-instrucions");

    public ForgottenPasswordPage() {
        super(PAGE_NAME, PATH);
    }

    public void populateEmailAddressField(String emailAddress){
        webBot.type(EMAIL_ADDRESS_FIELD, emailAddress);
    }

    public void clickSubmitEmail() {
        webBot.click(SUBMIT_EMAIL_ADDRESS_LINK);
    }

    public boolean isForgottenPasswordPage() {
        WebElement passwordFormElement = webBot.findElement(FORGOTTEN_PASSWORD_FORM, WaitTime.FOUR);
        boolean isPasswordFormAvailable = passwordFormElement!=null;

        WebElement submitEmailElement = webBot.findElement(SUBMIT_EMAIL_ADDRESS_LINK, WaitTime.FOUR);
        boolean isSubmitEmailAvailable = submitEmailElement!=null;

       return  isPasswordFormAvailable && isSubmitEmailAvailable;
    }

    public boolean isDisplayingEmailSentConfirmation() {
        WebElement emailConfirmationMessage = webBot.findElement(EMAIL_CONFIRMATION_MESSAGE);
        boolean isEmailConfirmationMessage = emailConfirmationMessage!=null;
    
        WebElement passwordInstructionsDisplayed = webBot.findElement(FORGOTTEN_PASSWORD_INSTRUCTIONS);
        boolean isPasswordInstructionsDisplayed = passwordInstructionsDisplayed!=null;

        return isEmailConfirmationMessage && isPasswordInstructionsDisplayed;
    }

    // add a validation to check the forgotten password page for any 'invalid email address' error messages being displayed
   }