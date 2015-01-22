package com.netaporter.pws.automation.napmobileweb.pages;

import com.netaporter.pws.automation.napmobileweb.components.MobileRegisterAccountForm;
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
public class MobileRegisterNewAccountPage extends AbstractMobileNapPage {

    private static final String PAGE_NAME = "Register New Account";
    private static final String PATH = "lightRegistration.nap";
    private static final By THANK_YOU_FOR_REGISTERING_MESSAGE = By.id("thankyoupage");

    @Autowired
    private MobileRegisterAccountForm registerAccountForm;

    public MobileRegisterNewAccountPage() {
        super(PAGE_NAME, PATH);
    }

    /**
     *
     * @return the email address used in the registration
     */
    public String enterValidRegistrationFormDetails() {
        return registerAccountForm.enterValidRegistrationFormDetails();
    }

    public void submitRegistrationForm() {
        registerAccountForm.submit();
    }

    public boolean isThankYouForRegisteringMessageDisplayed() {
        WebElement thankYouMessage = webBot.findElement(THANK_YOU_FOR_REGISTERING_MESSAGE);
        return thankYouMessage.getText().length() > 0;
    }

}
