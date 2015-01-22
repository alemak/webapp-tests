package com.netaporter.pws.automation.nap.cucumber.steps.registration;

import com.jayway.restassured.response.Cookie;
import com.netaporter.pws.automation.nap.components.RegisterAccountForm;
import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.pws.automation.shared.enums.UserRole;
import com.netaporter.pws.automation.shared.pojos.Customer;
import com.netaporter.pws.automation.shared.utils.CustomerDetailsUtil;
import com.netaporter.test.utils.dataaccess.seaview.SeaviewConnectorUtil;
import com.netaporter.test.utils.enums.SalesChannelEnum;
import cucumber.api.Scenario;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.netaporter.pws.automation.nap.components.RegisterAccountForm.*;
import static org.junit.Assert.*;

public class RegistrationSteps extends BaseNapSteps {

    public static final String REGISTRATION_COOKIES = "registrationCookies";
    Scenario scenario;

    @Value("#{webDriverUtilConfig.baseUrl}")
    private String baseUrl;

    @Autowired
    SeaviewConnectorUtil seaviewConnectorUtil;

    @Given("^I am a registered user$")
    public void I_am_a_registered_user() throws Throwable {
        homePage.go();
        homePage.clickSignInLink();
        signInPage.clickRegisterNow();
        String emailAddress = registerNewAccountPage.enterValidRegistrationFormDetails();
        scenarioSession.putData(REGISTERED_EMAIL_ADDRESS, emailAddress);
        // store same way as MRP
        Customer customer = new Customer(DEFAULT_FIRST_NAME, DEFAULT_SURNAME, emailAddress, DEFAULT_REGISTRATION_PASSWORD, "United Kingdom");
        scenarioSession.putData("customer", customer);
        registerNewAccountPage.submitRegistrationForm();
        getCurrentNapPage().signOut();
    }

    @Given("^I am a seaview registered default user$")
    public void I_am_a_seaview_registered_default_user() throws Throwable {
        Customer customer = seaviewConnectorUtil.registerDefaultCustomer(webBot.getBrand(), webBot.getRegion(), Arrays.asList("USER"));
        scenarioSession.putData(REGISTERED_EMAIL_ADDRESS, customer.getEmail());
        scenarioSession.putData("customer", customer);
    }

    @Given("^I am a seaview registered \"([^\"]*)\" user$")
    public void I_am_a_seaview_registered_user(List<UserRole> arg1) throws Throwable {
        List<String> roles = new ArrayList<String>(arg1.size());
        for(UserRole role:arg1){
            roles.add(role.name());
        }
        Customer customer = seaviewConnectorUtil.registerDefaultCustomer(webBot.getBrand(), webBot.getRegion(), roles);
        scenarioSession.putData(REGISTERED_EMAIL_ADDRESS, customer.getEmail());
        scenarioSession.putData("customer", customer);
    }

    @When("^I signout$")
    public void I_signout() throws Throwable {
        webBot.waitForJQueryCompletion();
        getCurrentNapPage().signOut();
    }

	@Given("^there is another registered regular user$")
	public void there_is_another_registered_regular_user() throws Throwable {
        Customer otherUser = CustomerDetailsUtil.getArbitraryUser();
        scenarioSession.putData("validCustomer",seaviewConnectorUtil.registerCustomer(webBot.getBrand(), webBot.getRegion(), otherUser,Arrays.asList("USER")));
	}

    @Given("^I register for an account and logout on (intl|am|apac)$")
    public void I_register_for_an_account_and_logout(String region) throws Throwable {
        setRegion(region);
        I_am_a_registered_user();
    }

    @When("^I submit invalid details on the registration form$")
    public void I_submit_invalid_details_on_the_registration_form() throws Throwable {
        homePage.go();
        homePage.waitForSignInLinkToAppear();
        homePage.clickSignInLink();
        signInPage.clickRegisterNow();
        registerNewAccountPage.submitRegistrationForm();
    }

    @When("^I submit valid details on the registration form$")
    public void I_submit_valid_details_on_the_registration_form() throws Throwable {
        homePage.go();
        homePage.waitForSignInLinkToAppear();
        homePage.clickSignInLink();
        signInPage.clickRegisterNow();
        String emailAddress = registerNewAccountPage.enterValidRegistrationFormDetails();
        scenarioSession.putData(REGISTERED_EMAIL_ADDRESS, emailAddress);
        registerNewAccountPage.submitRegistrationForm();
    }

    @And("^I fill in the registration form with valid details and click sign up$")
    public void fillInRegistrationFormAndSubmit() throws Throwable {
        String emailAddress = registerNewAccountPage.enterValidRegistrationFormDetails();
        scenarioSession.putData(REGISTERED_EMAIL_ADDRESS, emailAddress);
        registerNewAccountPage.submitRegistrationForm();
    }

    @And("^I click 'Register Now' on the sign in page$")
    public void clickRegisterNowOnSignInPage() throws Throwable {
        signInPage.clickRegisterNow();
    }

    @When("I submit the completed registration details for the partial user$")
    public void I_submit_the_completed_registration_details_for_the_partial_user() throws Throwable{
        registerNewAccountPage.enterCompletedRegistrationFormDetails();
        registerNewAccountPage.submitRegistrationForm();
    }
    
    @When("^I register a new account with my previously entered email address$")
    public void I_submit_previous_email_address() throws Throwable {
        homePage.go();
        homePage.clickSignInLink();
        signInPage.clickRegisterNow();
        registerNewAccountPage.enterValidRegistrationFormDetails();
        String anonEmail = (String) scenarioSession.getData(CustomerDetailsUtil.ANONYMOUS_EMAIL_KEY);
        webBot.type(RegisterAccountForm.FORM_EMAIL_ADDRESS_ID, anonEmail);
        registerNewAccountPage.submitRegistrationForm();
    }

    @When("^I register for email updates$")
    public void I_register_for_email_updates() throws Throwable {
        emailUpdateRegistrationPage.go();
        emailUpdateRegistrationPage.closeDontMissOutPopup();
        emailUpdateRegistrationPage.enterValidRegistrationDetails();
        emailUpdateRegistrationPage.submitRegistrationForm();
    }

    @Then("^I should see an error message$")
    public void I_should_see_error_messages_for_each_required_field() throws Throwable {
        assertTrue(registerNewAccountPage.isRegistrationFormErrorDisplayed());
    }

    @Then("^I should see the thank you for registering message$")
    public void I_should_see_the_thank_you_for_registering_message() throws Throwable {
        assertTrue(registerNewAccountPage.isThankYouForRegisteringMessageDisplayed());
    }

    @Then("^I should be signed in$")
    public void I_should_be_signed_in() throws Throwable {
        assertTrue(getCurrentNapPage().isSignedIn());
    }

	@Then("^switched user is signed in$")
    public void switched_user_should_be_signed_in() throws Throwable {
        boolean isSignedIn = getCurrentNapPage().isSignedIn();
		Customer switchedCustomer = (Customer) scenarioSession.getData("validCustomer");
		assertTrue(isSignedIn);
        Thread.sleep(500);
		assertEquals(switchedCustomer.getFname() + " " + switchedCustomer.getSurname(), getCurrentNapPage().getSignedInUserName());
    }

	@Then("^I am not logged in$")
    public void I_am_not_logged_in() throws Throwable {
        boolean isSignedIn = getCurrentNapPage().isSignedIn();
        assertFalse(isSignedIn);
    }

    @Given("^there is another EIP registered user$")
    public void there_is_another_EIP_registered_user() throws Throwable {
        Customer otherUser = CustomerDetailsUtil.getArbitraryUser();
        scenarioSession.putData("validCustomer",seaviewConnectorUtil.registerCustomer(webBot.getBrand(), webBot.getRegion(), otherUser,Arrays.asList("USER","LOOKBOOK")));
    }

    @Given("^I am a signed in user on NAP website$")
    public void I_am_a_signed_in_user_on_NAP_website() throws Throwable {
       I_submit_valid_details_on_the_registration_form();
    }

    @Then("^the user is partially registered$")
    public void the_user_is_partially_registered() throws Throwable {
        String anonEmail = (String) scenarioSession.getData(CustomerDetailsUtil.ANONYMOUS_EMAIL_KEY);
        String encodedPassword = productDataAccess.getLegacyDBClient().getCustomerEncodedPassword(webBot.getRegionEnum(), anonEmail);
        assertTrue (encodedPassword == null);
    }

    @When("^I am on Register New Account page$")
    public void I_am_on_Register_New_Account_page() throws Throwable {
        boolean isRegistrationPageDisplayed = registerNewAccountPage.isRegistrationFormPage();
        assertTrue(isRegistrationPageDisplayed);
    }

    @Then("^I am a fully registered user$")
    public void I_am_a_fully_registered_user() throws Throwable {
        String anonEmail = (String) scenarioSession.getData(CustomerDetailsUtil.ANONYMOUS_EMAIL_KEY);
        String encodedPassword = productDataAccess.getLegacyDBClient().getCustomerEncodedPassword(webBot.getRegionEnum(), anonEmail);
        assertTrue (encodedPassword != null);
    }
    @Then("^I should see an email confirmation sent to my email address$")
    public void email_confirmation_sent() throws Throwable {
        assertTrue(registerNewAccountPage.isCompleteRegistrationEmailSent());
    }

    @When("^I submit a blank first name on the registration form$")
    public void I_submit_a_blank_first_name_on_the_registration_form() {
        registerNewAccountPage.enterBlankFirstName();
        registerNewAccountPage.submitRegistrationForm();
    }

    @When("^I submit an invalid email address on the registration form$")
    public void I_submit_an_invalid_email_address_on_the_registration_form()throws Throwable {
        registerNewAccountPage.enterInvalidEmailAddressOnRegistration();
        registerNewAccountPage.submitRegistrationForm();
    }

    @When ("^I submit an existing customer email")
    public void I_submit_an_existing_customer_email() throws Throwable{
        Customer validCustomer = (Customer) scenarioSession.getData("validCustomer");

        registerNewAccountPage.enterExistingEmailAddressOnRegistration(validCustomer.getEmail());
        registerNewAccountPage.submitRegistrationForm();
    }

    @Given("^I submit an invalid password$")
    public void I_submit_an_invalid_password(){
        registerNewAccountPage.enterIncorrectPasswordLength();
        registerNewAccountPage.submitRegistrationForm();
    }

    @When("^I submit non matching passwords$")
    public void I_submit_non_matching_passwords(){
        registerNewAccountPage.enterNonMatchingPasswords();
        registerNewAccountPage.submitRegistrationForm();
    }

    @Given("^I am a signed in user on (intl|am|apac)$")
    public void I_am_a_signed_in_user_on_intl(String regionName) throws Throwable {
        setRegion(regionName);

        if ("intl".equals(regionName)) {
            setChannel(SalesChannelEnum.NAP_INTL);
            webBot.addCookie("country_iso", "GB");
            webBot.addCookie("channel", "intl");
        } else if ("am".equals(regionName)) {
            setChannel(SalesChannelEnum.NAP_AM);
            webBot.addCookie("country_iso", "US");
            webBot.addCookie("channel", "am");
        } else if ("apac".equals(regionName)) {
            setChannel(SalesChannelEnum.NAP_APAC);
            webBot.addCookie("country_iso", "HK");
            webBot.addCookie("channel", "apac");
        }

        I_submit_valid_details_on_the_registration_form();
    }

    private org.openqa.selenium.Cookie convertCookie(Cookie cookie) {
        return new org.openqa.selenium.Cookie(cookie.getName(), cookie.getValue(), cookie.getDomain(), cookie.getPath(), cookie.getExpiryDate(), cookie.isSecured() );
    }


    @And("^I create an account with already existing address$")
    public void I_create_an_account_with_already_existing_address() throws Throwable {
        registerNewAccountPage.enterExistingEmailAddressOnRegistration((String) scenarioSession.getData(REGISTERED_EMAIL_ADDRESS));
        registerNewAccountPage.submitRegistrationForm();

    }


}
