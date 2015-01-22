package com.netaporter.pws.automation.napmobileweb.cucumber.steps.signin;

import com.netaporter.pws.automation.napmobileweb.cucumber.steps.BaseMobileNapSteps;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: c.dawson@london.net-a-porter.com
 * Date: 30/12/2013
 * Time: 10:51
 * To change this template use File | Settings | File Templates.
 */
public class MobileSignInSteps extends BaseMobileNapSteps {

    private static final String REGISTERED_EMAIL_ADDRESS = "registeredEmailAddress";
    private static final String CORRECT_PASSWORD = "123456";

    @When("^I sign out of the mobile site$")
    public void sign_out() throws Throwable {
        homePage.go();
        Thread.sleep(1000);
        homePage.clickSignOutLink();
    }

    @Then("^I am taken to the mobile NAP SIGN IN page$")
    public void verify_SignIn_Page_Displayed() throws Throwable {
        setCurrentPage(signInPage);
        assertThat("Error asserting expected page title for the sign in page", signInPage.getPageTitle(), equalTo("SIGN IN"));
    }

    @When("^I sign in with the correct details on the mobile Sign In page$")
    public void I_sign_in_with_the_correct_details_On_SignIn_Page() throws Throwable {
        String emailAddress = (String) scenarioSession.getData(REGISTERED_EMAIL_ADDRESS);
        signInPage.signIn(emailAddress, CORRECT_PASSWORD);
    }
}
