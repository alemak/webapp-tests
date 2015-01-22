package com.netaporter.pws.automation.napmobileweb.cucumber.steps.registration;

import com.netaporter.pws.automation.napmobileweb.cucumber.steps.BaseMobileNapSteps;
import com.netaporter.pws.automation.nap.cucumber.steps.registration.RegistrationSteps;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: J.Christian@net-a-porter.com
 * Date: 30/08/2013
 * Time: 09:12
 * To change this template use File | Settings | File Templates.
 */
public class MobileRegistrationSteps extends BaseMobileNapSteps {

    @When("^I submit valid details on the mobile registration form$")
    public void I_submit_valid_details_on_the_registration_form() throws Throwable {
        homePage.go();
        homePage.waitForSignInLinkToExist();
        homePage.waitForSignInLinkToBeVisible();
        homePage.clickSignInLink();
        signInPage.clickRegisterNow();
        String emailAddress = registerNewAccountPage.enterValidRegistrationFormDetails();
        scenarioSession.putData(RegistrationSteps.REGISTERED_EMAIL_ADDRESS, emailAddress);
        System.out.println(emailAddress);
        registerNewAccountPage.submitRegistrationForm();
        assertTrue(homePage.isSignedIn());
        System.out.println(emailAddress);
    }

    @Then("^I should be signed in on the mobile site$")
    public void I_should_be_signed_in() throws Throwable {
        boolean isSignedIn = getCurrentMobileNapPage().isSignedIn();
        assertTrue(isSignedIn);
    }

    @Then("^I should see the mobile thank you for registering message$")
    public void I_should_see_the_thank_you_for_registering_message() throws Throwable {
        boolean isThankYouPageDisplayed = registerNewAccountPage.isThankYouForRegisteringMessageDisplayed();
        assertTrue(isThankYouPageDisplayed);
    }
}
