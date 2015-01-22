package com.netaporter.pws.automation.shared.steps.purchasePath;

import com.netaporter.pws.automation.shared.pojos.Customer;
import com.netaporter.pws.automation.shared.utils.CustomerDetailsUtil;
import cucumber.api.java.en.Given;
import org.springframework.stereotype.Component;

@Component
public class PurchasePathSignInSteps extends BasePurchasePathStep {

    @Given("^I sign in anonymously within the purchase path$")
    public void anonymousSignIn() {
        String emailAddress = CustomerDetailsUtil.generateEmailAddress();
        napPurchasePathSignInPage.enterGuestEmailAddress(emailAddress);
        scenarioSession.putData("registeredEmailAddress", emailAddress);
        napPurchasePathSignInPage.clickCheckoutAsAGuest();
    }

    @Given("^I sign in anonymously with email (.*) within the purchase path$")
    public void anonymousSignInWithEmail(String email) {
        napPurchasePathSignInPage.enterGuestEmailAddress(email);
        napPurchasePathSignInPage.clickCheckoutAsAGuest();
    }

    @Given("^I sign in email (.*) and password (.*) within the purchase path$")
    public void signInWithEmailPassword(String email, String password) {
        napPurchasePathSignInPage.enterRegisteredUserEmailAddress(email);
        napPurchasePathSignInPage.enterPassword(password);
        napPurchasePathSignInPage.clickSignInNow();
    }

    @Given("^I sign in using the default customer within the purchase path$")
    public void defaultCustomerSignIn() {
        Customer customer = (Customer) scenarioSession.getData("customer");
        napPurchasePathSignInPage.enterRegisteredUserEmailAddress(customer.getEmail());
        napPurchasePathSignInPage.enterPassword(customer.getPassword());
        napPurchasePathSignInPage.clickSignInNow();
    }

    @Given("^I sign in anonymously using the default customer within the purchase path$")
    public void defaultAnonymousCustomerSignIn() {
        Customer customer = (Customer) scenarioSession.getData("customer");
        napPurchasePathSignInPage.enterGuestEmailAddress(customer.getEmail());
        napPurchasePathSignInPage.clickCheckoutAsAGuest();
    }

    @Given("^I have sign in anonymously within the purchase path$")
    public void anonymousSignedIn() {
        String emailAddress = CustomerDetailsUtil.generateEmailAddress();
        napPurchasePathSignInPage.enterGuestEmailAddress(emailAddress);
        napPurchasePathSignInPage.clickSignInNow();

        scenarioSession.putData("generatedEmail", emailAddress);
    }
}