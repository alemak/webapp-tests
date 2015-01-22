package com.netaporter.pws.automation.nap.pages;

import com.netaporter.pws.automation.nap.components.RegisterAccountForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Encapsulates the functionality on the Email Updates registration page
 */
@Component
@Scope("cucumber-glue")
public class EmailUpdatesRegistrationPage extends AbstractNapPage {

    private static final String PAGE_NAME = "Email Update Registration";
    private static final String PATH = "emailUpdatesRegistration.nap";

    @Autowired
    private RegisterAccountForm registerAccountForm;

    public EmailUpdatesRegistrationPage() {
        super(PAGE_NAME, PATH);
    }

    /**
     *
     * @return the email address used in the registration
     */
    public String enterValidRegistrationDetails() {
        return registerAccountForm.enterValidRegistrationFormDetails();
    }

    public void submitRegistrationForm() {
        registerAccountForm.submit();
    }
}
