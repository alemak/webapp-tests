package com.netaporter.pws.automation.nap.cucumber.steps.signin;

import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.pws.automation.shared.pojos.Customer;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.log4j.Logger;

import static org.junit.Assert.*;

public class SignInSteps extends BaseNapSteps {

    static Logger logger  = Logger.getLogger(SignInSteps.class);

    private static final String REGISTERED_EMAIL_ADDRESS = "registeredEmailAddress";
    private static final String CORRECT_PASSWORD = "123456";
    private static final String WRONG_PASSWORD = "wrongPassword";

    @Given("^I am not signed in$")
    public void i_am_not_signed_in() throws Throwable {
        assertFalse(getCurrentNapPage().isSignedIn());
    }

    @When("^I sign in with unregistered details$")
    public void I_sign_in_with_unregistered_details() throws Throwable {
        homePage.go();
        homePage.clickSignInLink();
        signInPage.signIn("notregistered@notregistered.com", CORRECT_PASSWORD);
    }

    @When("^I sign in with the wrong password$")
    public void I_sign_in_with_the_wrong_password() throws Throwable {
        homePage.go();
        homePage.clickSignInLink();
        String emailAddress = (String) scenarioSession.getData(REGISTERED_EMAIL_ADDRESS);
        signInPage.signIn(emailAddress, WRONG_PASSWORD);
    }

    @When("^I sign in with the wrong password in the purchase path$")
    public void I_sign_in_with_the_wrong_password_in_the_purchase_path() throws Throwable {
        String emailAddress = scenarioSession.getData(REGISTERED_EMAIL_ADDRESS);
        purchasePathFlowPage.expressCheckout(emailAddress, WRONG_PASSWORD);
    }

    @Given("^I try to sign in with incorrect password (\\d+) times$")
    public void I_try_to_sign_in_with_incorrect_password_times(int loginAttempts) throws Throwable {
        homePage.clickSignInLink();
        String emailAddress = (String) scenarioSession.getData(REGISTERED_EMAIL_ADDRESS);

        signInPage.signIn(emailAddress, WRONG_PASSWORD);

        for (int i = 1; i < loginAttempts; i++) {
            Thread.sleep(1000);
            signInPage.signIn(emailAddress, WRONG_PASSWORD);
        }
    }

    @When("^I sign in with the correct details$")
    public void I_sign_in_with_the_correct_details() throws Throwable {
        homePage.go();
        homePage.clickSignInLink();
        String emailAddress = scenarioSession.getData(REGISTERED_EMAIL_ADDRESS);
        signInPage.signIn(emailAddress, CORRECT_PASSWORD);
        logger.info("\n Signed in with customer with email address "+ emailAddress+". \n");
        assertTrue("Failed to sign in with the previously created user", homePage.isSignedIn());
    }

    @When("^I sign in with the correct details on the Sign In page$")
    public void I_sign_in_with_the_correct_details_On_SignIn_Page() throws Throwable {
        String emailAddress = (String) scenarioSession.getData(REGISTERED_EMAIL_ADDRESS);
        signInPage.signIn(emailAddress, CORRECT_PASSWORD);
    }

    @Then("^I should see an error message on the sign in page$")
    public void I_should_see_an_error_message_on_the_sign_in_page() throws Throwable {
        assertTrue(signInPage.isDisplayingSignInErrorMessage());
    }

    @When("^I am logged in$")
    public void I_am_logged_in() throws Throwable {
        homePage.go();
        homePage.clickSignInLink();
        String existingUser = (String) scenarioSession.getData(REGISTERED_EMAIL_ADDRESS);

        signInPage.signIn(existingUser, CORRECT_PASSWORD);
    }

    @Then("^I am (true|false) signed in$")
    public void I_am_signed_in(boolean isSignedIn) throws Throwable {
        homePage.go();
        assertEquals(isSignedIn, homePage.isSignedIn());
    }

	@When("^I sign out$")
	public void sign_out() throws Throwable {
		homePage.go();
		homePage.signOut();
	}

	@When("^I sign in as a valid user$")
	public void sign_in_as_a_valid_user() throws Throwable {
		homePage.go();
        homePage.clickSignInLink();
		Customer customer = scenarioSession.getData("validCustomer");
        signInPage.signIn(customer.getEmail(), customer.getPassword());
	}

    @Given("^I am signed in as (.*) with password (.*)$")
    public void sign_in_as_specific_user(String username, String password) throws Throwable {
        homePage.go();
        homePage.clickSignInLink();
        signInPage.signIn(username, password);
        scenarioSession.putData(REGISTERED_EMAIL_ADDRESS, username);
    }

    @Given("^I navigate to the sign in page")
    public void signInPage() throws Throwable {
        homePage.go();
        homePage.clickSignInLink();
    }

    @Given("^I am on the sign in page")
    public void onTheSignInPage() throws Throwable {
        signInPage.verifyPage();
    }


    @When("^I click Change Password")
    public void changePassword() throws Throwable {
        signInPage.clickChangePassword();
    }

    @When("^I submit my registered email address")
    public void submitEmailAddress() throws Throwable {

        //no validation for email address because live website does not validate
        //Will be implemented properly when we check against the 'forgotten password' email
        assertTrue(forgottenPasswordPage.isForgottenPasswordPage());
        forgottenPasswordPage.populateEmailAddressField(scenarioSession.getData(REGISTERED_EMAIL_ADDRESS).toString());
        forgottenPasswordPage.clickSubmitEmail();
    }

    @Then("^I should see an email sent confirmation message$")
    public void I_should_see_an_email_sent_confirmation_message() throws Throwable {
        assertTrue(forgottenPasswordPage.isDisplayingEmailSentConfirmation());
    }

    @Then("^the user is logged in$")
    public void the_user_is_logged_in() throws Throwable {
        boolean isSignedIn = getCurrentNapPage().isSignedIn();
        assertTrue(isSignedIn);

        Customer registeredCustomer = (Customer) scenarioSession.getData("validCustomer");
        StringBuilder expectedFullNameSB = new StringBuilder(registeredCustomer.getFname());
        expectedFullNameSB.append(" ");
        expectedFullNameSB.append(registeredCustomer.getSurname());

        assertEquals(expectedFullNameSB.toString(), getCurrentNapPage().getSignedInUserName());
    }


    @Then("^My account is locked: (true|false)$")
    public void My_account_isLocked(boolean isLock) throws Throwable {
        I_sign_in_with_the_correct_details();

        I_am_signed_in(!isLock);
    }

    @Given("^I am on the homepage$")
    public void goToHomepage() throws Throwable {
       homePage.go();
    }


    @Then("^I am on the NAP SIGN IN page$")
    public void verify_I_am_on_the_signin_page() throws Throwable {
        assertTrue(signInPage.verifyPage());
    }

    @Then("^I am taken to the NAP SIGN IN page$")
    public void verify_SignIn_Page_Displayed() throws Throwable {
        setCurrentPage(signInPage);
        verify_I_am_on_the_signin_page();
    }


    @Then("^I directly hit the signin URL with query params redirect = (.*) and httpsRedirect = (true|false|blank)$")
    public void verify_SignIn_Page_Displayed(String redirect, String httpsRedirect) throws Throwable {


        if (redirect.equals("blank")){
            redirect = "";
        }
        if (httpsRedirect.equals("blank")){
            httpsRedirect = "";
        }
        String params = "redirect="+redirect+"&httpsRedirect="+httpsRedirect;
        for(int i=0 ; i<3; i++) {
            webBot.goToRegionalisedPageWithParams(signInPage, params);
            Thread.sleep(3300);
            if(signInPage.verifyPage()) {
                i=10;
            };
        }

        assertTrue(signInPage.verifyPage());
    }

    @When("^I do not provide an email$")
    public void I_do_not_provide_an_email() throws Throwable {
        purchasePathFlowPage.expressCheckout("", WRONG_PASSWORD);

    }

    @When("^I do not provide a password$")
    public void I_do_not_provide_a_password() throws Throwable {
        String emailAddress = (String) scenarioSession.getData(REGISTERED_EMAIL_ADDRESS);
        purchasePathFlowPage.expressCheckout(emailAddress, "");
    }


    @And("^I do not provide an email for anonymous checkout$")
    public void I_do_not_provide_an_email_for_anonymous_checkout() throws Throwable {
        purchasePathFlowPage.checkoutAnonymously("");
    }

    @And("^I provide an unknown email server$")
    public void I_provide_an_unknown_email_server() throws Throwable {
        purchasePathFlowPage.checkoutAnonymously("test@testunknowndomain.com");
    }

    @And("^I check the email address confirmation check box$")
    public void I_check_the_email_address_confirmation_check_box() throws Throwable {
        signInPage.checkUnknownEmailProviderCheckbox();
    }

    @And("^I checkout as an anonymous user$")
    public void I_checkout_as_an_anonymous_user() throws Throwable {
        purchasePathFlowPage.clickCheckoutAsGuest();
        purchasePathFlowPage.fillAddressAndContinue();
    }
}
