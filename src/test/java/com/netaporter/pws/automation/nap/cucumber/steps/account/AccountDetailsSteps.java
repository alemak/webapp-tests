package com.netaporter.pws.automation.nap.cucumber.steps.account;

import com.netaporter.pws.automation.nap.components.RegisterAccountForm;
import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.pws.automation.nap.cucumber.steps.registration.RegistrationSteps;
import com.netaporter.pws.automation.shared.pojos.Customer;
import com.netaporter.pws.automation.shared.utils.CustomerDetailsUtil;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: c.choudhury@london.net-a-porter.com
 * Date: 29/01/2013
 * Time: 16:21
 * To change this template use File | Settings | File Templates.
 */
public class AccountDetailsSteps extends BaseNapSteps {

    private static final String NEW_PASSWORD_KEY = "newPassword";
    private static final String VALID_CUSTOMER_KEY = "validCustomer";

    private String newEmail;

    @When("^I change the user's email address$")
    public void I_change_the_user_s_email_address() throws Throwable {
        Customer registeredCustomer = (Customer) scenarioSession.getData(VALID_CUSTOMER_KEY);
        Integer customerId = productDataAccess.getLegacyDBClient().getCustomerIdByEmail(webBot.getRegionEnum(), registeredCustomer.getEmail());
        registeredCustomer.setId(customerId);

        newEmail = CustomerDetailsUtil.generateEmailAddress();

        accountDetailsPage.fillEditEmailAddress(newEmail);
        accountDetailsPage.submitChangeEmailAddressForm();
    }

    @Then("^the user logs in with old email address$")
    public void the_user_logs_in_with_old_email_address() throws Throwable {
        getCurrentNapPage().signOut();
        homePage.clickSignInLink();

        Customer registeredCustomer = (Customer) scenarioSession.getData(VALID_CUSTOMER_KEY);
        signInPage.signIn(registeredCustomer.getEmail(), registeredCustomer.getPassword());
    }

    @Then("^the user logs in with new email address$")
    public void the_user_logs_in_with_new_email_address() throws Throwable {
        Customer registeredCustomer = (Customer) scenarioSession.getData(VALID_CUSTOMER_KEY);

        homePage.clickSignInLink();
        signInPage.signIn(registeredCustomer.getEmail(), newEmail);
    }

    @Then("^the user sees authentication error message$")
    public void the_user_sees_authentication_error_message() throws Throwable {
        assertTrue(signInPage.getErrorContent().contains("unable to access your account"));
    }

    @When("^I attempt to change the user's firstname and lastname$")
    public void I_attempt_to_change_the_user_s_firstname_and_lastname() throws Throwable {
        String newFirstName = "new_fname";
        String newLastName = "new_lname";

        accountDetailsPage.changeFirstNameAndLastName(newFirstName, newLastName);

        Customer registeredCustomer = (Customer) scenarioSession.getData(VALID_CUSTOMER_KEY);
        registeredCustomer.setFname(newFirstName);
        registeredCustomer.setSurname(newLastName);
    }

    @Then("^the user details are successfully changed$")
    public void the_user_details_are_successfully_changed() throws Throwable {
        assertTrue(accountDetailsPage.isAccountChanged());
    }

    @Then("^the user's firstname and lastname have changed$")
    public void the_user_s_firstname_and_lastname_have_changed() throws Throwable {
        boolean isSignedIn = getCurrentNapPage().isSignedIn();
        assertTrue("Customer is not signed in", isSignedIn);

        Customer registeredCustomer = (Customer) scenarioSession.getData(VALID_CUSTOMER_KEY);
        StringBuilder expectedFullNameSB = new StringBuilder(registeredCustomer.getFname());
        expectedFullNameSB.append(" ");
        expectedFullNameSB.append(registeredCustomer.getSurname());

        assertEquals(expectedFullNameSB.toString(), getCurrentNapPage().getSignedInUserName());
    }

    @When("^I change the user's password$")
    public void I_change_the_user_s_password() throws Throwable {
        String newPassword = "new_password";

        accountDetailsPage.changePassword(newPassword);

        scenarioSession.putData(NEW_PASSWORD_KEY, newPassword);
    }

    @Then("^the regular user can login using new password$")
    public void the_regular_user_can_login_using_new_password() throws Throwable {
        Customer validCustomer = (Customer) scenarioSession.getData(VALID_CUSTOMER_KEY);
        String validCustomerEmail = validCustomer.getEmail();
        String newPassword = (String) scenarioSession.getData(NEW_PASSWORD_KEY);

        loginWithEmailAndPassword(validCustomerEmail, newPassword);

        String fullName = validCustomer.getFname() + " " + validCustomer.getSurname();

        assertEquals(fullName, getCurrentNapPage().getSignedInUserName());
    }

    @Then("^the regular user cannot login with the old password$")
    public void the_regular_user_cannot_login_with_the_old_password() throws Throwable {
        Customer validCustomer = (Customer) scenarioSession.getData(VALID_CUSTOMER_KEY);
        String validCustomerEmail = validCustomer.getEmail();
        String oldPassword = validCustomer.getPassword();

        loginWithEmailAndPassword(validCustomerEmail, oldPassword);

        assertTrue(signInPage.getErrorContent().contains("unable to access your account"));
    }

    private void loginWithEmailAndPassword(String validCustomerEmail, String password) {
        homePage.clickSignInLink();
        signInPage.signIn(validCustomerEmail, password);
    }

    @Given("^I am on the My Address Book page$")
    public void I_am_on_the_My_Address_Book_page() {
        // hazmat tests were failing using .goToPage
        webBot.goToRegionalisedPage(customerAddressesPage);
    }

    @Then("^I add a new shipping address from (.*)$")
    public void I_enter_a_shipping_address_from(String country) throws Throwable {
        customerAddressesPage.clickNewShippingAddress();
        customerAddressesPage.fillAddress(country);
        customerAddressesPage.saveAddress();
    }

    @Then("^I add a new billing address from (.*)$")
    public void I_enter_a_billing_address_from(String country) throws Throwable {
        customerAddressesPage.clickNewBillingAddress();
        customerAddressesPage.fillAddress(country);
        customerAddressesPage.saveAddress();
    }

    @Given("^I have a shipping address for (.*) saved$")
    public void I_have_a_shipping_address_for_country_saved(String country) throws Throwable{
        I_am_on_the_My_Address_Book_page();
        I_enter_a_shipping_address_from(country);

    }

    @Given("^I have a billing address for (.*) saved$")
    public void I_have_a_billing_address_for_country_saved(String country) throws Throwable{
        I_am_on_the_My_Address_Book_page();
        I_enter_a_billing_address_from(country);
    }

    @And("^I add new shipping addresses from (.*)$")
    public void I_add_new_shipping_addresses_from_United_Kingdom(String country) throws Throwable {
        I_enter_a_shipping_address_from(country);
        I_enter_a_shipping_address_from(country);

    }

    @And("^I add new billing addresses from (.*)$")
    public void I_add_new_billing_addresses_from_United_Kingdom(String country) throws Throwable {
        I_enter_a_billing_address_from(country);
        I_enter_a_billing_address_from(country);
    }

    @And("^I change my email address$")
    public void I_change_my_email_address() throws Throwable {
        accountDetailsPage.enterCurrentPasswordToModifyEmail(RegisterAccountForm.DEFAULT_REGISTRATION_PASSWORD);
        String newEmail = CustomerDetailsUtil.generateEmailAddress();

        accountDetailsPage.fillEditEmailAddress(newEmail);
        accountDetailsPage.submitChangeEmailAddressForm();
    }

    @When("^I try to login with my old email address$")
    public void I_try_to_login_with_my_old_email_address() throws Throwable {
        String oldEmail = scenarioSession.getData(RegistrationSteps.REGISTERED_EMAIL_ADDRESS);
        String password = RegisterAccountForm.DEFAULT_REGISTRATION_PASSWORD;

        homePage.clickSignInLink();
        signInPage.signIn(oldEmail, password);
    }

    @Then("^an error is displayed and the user is not signed in$")
    public void an_error_is_displayed_and_the_user_is_not_signed_in() throws Throwable {
        assertThat(signInPage.getErrorContent(), containsString("unable to access your account"));
        assertFalse(signInPage.isSignedIn());
    }

    @And("^I change my password$")
    public void I_change_my_password() throws Throwable {
        accountDetailsPage.enterCurrentPasswordToModifyPassword(RegisterAccountForm.DEFAULT_REGISTRATION_PASSWORD);
        String newPassword = "new_password";

        accountDetailsPage.changePassword(newPassword);
        scenarioSession.putData(NEW_PASSWORD_KEY, newPassword);
    }

    @And("^I attempt to change my firstname and lastname$")
    public void I_attempt_to_change_my_firstname_and_lastname() throws Throwable {
        String newFirstName = "new_fname";
        String newLastName = "new_lname";

        accountDetailsPage.changeFirstNameAndLastName(newFirstName, newLastName);
    }

    @When("^I try to login using my old password$")
    public void I_try_to_login_using_my_old_password() throws Throwable {
        String Email = scenarioSession.getData(RegistrationSteps.REGISTERED_EMAIL_ADDRESS);
        String oldpassword = RegisterAccountForm.DEFAULT_REGISTRATION_PASSWORD;

        homePage.clickSignInLink();
        signInPage.signIn(Email, oldpassword);
    }

    @Then("^the account details have changed$")
    public void the_account_details_have_changed() throws Throwable {
        assertThat(accountDetailsPage.getCurrentFirstName(), containsString("new_fname"));
        assertThat(accountDetailsPage.getCurrentLastName(), containsString("new_lname"));
    }
}
