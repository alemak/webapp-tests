package com.netaporter.pws.automation.nap.cucumber.steps.apis.accountdetails;

import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.specification.ResponseSpecification;
import com.netaporter.pws.automation.nap.components.RegisterAccountForm;
import com.netaporter.pws.automation.nap.cucumber.steps.registration.RegistrationSteps;
import com.netaporter.pws.automation.shared.apiclients.AccountDetailsAPIClient;
import com.netaporter.pws.automation.shared.apiclients.pojos.WebappLoginInfo;
import com.netaporter.test.client.product.impl.HybridProductDataAccess;
import com.netaporter.test.utils.cucumber.glue.steps.BaseAPIStep;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNot.not;

public class AccountDetailsSteps extends BaseAPIStep {

    private static final String LOGIN_INFO = "loginInfo";

    @Autowired
    private AccountDetailsAPIClient webappServiceClient;

    @Autowired
    private HybridProductDataAccess dataAccess;

    @Given("^I log in with the user just registered via the API on (intl|am|apac)$")
    public void logInJustRegisteredUser(String channel) {
        WebappLoginInfo loginInfo = webappServiceClient.login(
                (String) scenarioSession.getData(RegistrationSteps.REGISTERED_EMAIL_ADDRESS),
                RegisterAccountForm.DEFAULT_REGISTRATION_PASSWORD,
                channel);

        scenarioSession.putData(LOGIN_INFO, loginInfo);
    }

    @Given("^I logout via the API on (intl|am|apac)$")
    public void logOutViaAPI(String channel) {
        WebappLoginInfo loginInfo = (WebappLoginInfo) scenarioSession.getData(LOGIN_INFO);
        if(loginInfo != null) {
            scenarioSession.putData(LOGIN_INFO, webappServiceClient.logout(loginInfo.getjSessionId(), channel));
        } else {
            scenarioSession.putData(LOGIN_INFO, webappServiceClient.logout());
        }


    }


    @Then("^the API response should contain the personal details supplied at registration$")
    public void responseContainsPersonalDetails() {
        getResponseSpecBuilder()
                .addResponseSpecification(personalDetailsSpec(RegisterAccountForm.DEFAULT_FIRST_NAME, RegisterAccountForm.DEFAULT_SURNAME))
                .expectBody("data.email", equalTo((String) scenarioSession.getData(RegistrationSteps.REGISTERED_EMAIL_ADDRESS)));
    }

    @Then("^the API response should contain a customer id and an account ID$")
    public void responseContainsACustomerAccountID() {
        String emailAdressRegistered = (String) scenarioSession.getData(RegistrationSteps.REGISTERED_EMAIL_ADDRESS);

        Integer customerID =  dataAccess.getLegacyDBClient().getCustomerIdByEmail(webBot.getRegionEnum(), emailAdressRegistered);
        String customerGlobalID = dataAccess.getLegacyDBClient().getCustomerGlobalIdByEmail(webBot.getRegionEnum(), emailAdressRegistered);

        getResponseSpecBuilder()
                .expectBody("data.account", equalTo(customerGlobalID))
                .expectBody("data.id", equalTo(customerID));

    }


    @Then("^the API response should not contain any personal details$")
    public void responseDoesNotContainPersonalInformation() {
        getResponseSpecBuilder().expectBody("data", nullValue());
    }

    @Then("^the API response should state the user is (SIGNED_IN|REMEMBERED)$")
    public void responseStatesSignedIn(String loggedinState) {
        getResponseSpecBuilder().expectBody("data.role", equalTo(loggedinState));
    }


    @Then("^the API response should state the user is not signed in$")
    public void responseStatesNotSignedIn() {
        getResponseSpecBuilder().expectBody("errors.USER_NOT_SIGNED_IN", not(nullValue())).expectBody("errors", not(nullValue()));
    }

    @Then("^the API response should contain the wishlist migrated (true|false) status$")
    public void userWishlistIsMigrated(boolean status) {
        getResponseSpecBuilder().expectBody("data.optedIn.wishlist", equalTo(status));
    }

    @Then("^the JSONP API response should contain the wishlist migrated (true|false) status$")
    public void userWishlistIsMigratedJSONP(boolean status) {
        // Bit raw for now.  As this is JSONP we can't easily parse the JSON out of the function call using rest-assured,
        // so just doing a basic string match.  This is covered in more depth in the pure-JSON version of this test
        getResponseSpecBuilder().expectContent(containsString("\"optedIn\":{\"wishlist\":" + status+ "}"));
    }


    @Then("^the API response contains no errors$")
    public void responseContainsNoErrors() {
        getResponseSpecBuilder().expectBody("errors", nullValue());
    }

    @When("^I access my account details via the API$")
    public void accessMyAccount() {
        webappServiceClient.getAccount(getLoginInfo(), getResponseSpecBuilder().build());
    }

    @When("^I access my account details via the JSONP API$")
    public void accessMyAccountJSONP() {
        webappServiceClient.getAccount(getLoginInfo(), getResponseSpecBuilder().build(), true);
    }

    @When("^I access my account details via the API without the session ID$")
    public void accessMyAccountWithoutJSessionID() {
        WebappLoginInfo loginInfo = getLoginInfo();
        loginInfo.setAsRemembered();
        scenarioSession.putData(LOGIN_INFO, loginInfo);

        accessMyAccount();
    }

    private ResponseSpecification personalDetailsSpec(String firstName, String surname) {
        return new ResponseSpecBuilder()
                .expectBody("data.firstname", equalTo(firstName))
                .expectBody("data.surname", equalTo(surname))
                .build();
    }

     private WebappLoginInfo getLoginInfo() {
        return (WebappLoginInfo) scenarioSession.getData(LOGIN_INFO);
    }

}
