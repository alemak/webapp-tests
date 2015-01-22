package com.netaporter.pws.automation.shared.steps;

import com.netaporter.pws.automation.shared.components.AddressComponent;
import com.netaporter.test.utils.cucumber.steps.LegacyWebAppBaseStep;
import com.netaporter.test.utils.pages.exceptions.PageElementNotFoundException;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class AddressSteps extends LegacyWebAppBaseStep {

    @Autowired
    AddressComponent addressComponent;

    @Given("^I fill out the first name field with (.*)$")
    public void fillInFirstName(String firstName) {
        addressComponent.completeFirstName(firstName);
    }

    @Given("^I fill out the last name field with (.*)$")
    public void fillInLastName(String lastName) {
        addressComponent.completeLastName(lastName);
    }

    @Given("^I select country (.*) on the address form$")
    public void selectCountry(String country) {
        addressComponent.selectCountry(country);
    }

    @Given("^I fill out the first address line field with (.*)$")
    public void fillInAddressLine1(String addressLine1) {
        addressComponent.completeAddressLine1(addressLine1);
    }

    @Given("^I fill out the second address line field with (.*)$")
    public void fillInAddressLine2(String addressLine2) {
        addressComponent.completeAddressLine2(addressLine2);
    }

    @Given("^I fill out the telephone field on the address form with (.*)$")
    public void fillInTelephoneField(String telephoneNumber) {
        addressComponent.completeTelephoneNumber(telephoneNumber);
    }
    @Given("^I fill out the mobile field on the address form with (.*)$")
    public void fillInMobileField(String mobileNumber) {
        addressComponent.completeMobileNumber(mobileNumber);
    }

    @Given("^I fill out the town field with (.*)$")
    public void fillInTown(String town) {
        addressComponent.completeTown(town);
    }

    @Given("^I fill out the state field with (.*)$")
    public void fillInState(String state) {
        addressComponent.selectState(state);
    }

    @Given("^I select a random state from country (.*) on the address form$")
    public void selectRandomTown(String country) {
        String state = productDataAccess.getLegacyDBClient().getStateByCountry(webBot.getRegionEnum(), country);
        assertNotNull("No state was found", state);
        addressComponent.selectState(state);
    }

    @Then("^The label displayed for state is (.*)$")
    public void getStateLabel(String labelForState) {
        assertThat(labelForState, is(addressComponent.getStateLabel()));
    }

    @Then("^For country (.*), I can select a random state from the available list of states$")
    public void getStateByCountry(String country) {
        String state = productDataAccess.getLegacyDBClient().getStateByCountry(webBot.getRegionEnum(), country);
        assertNotNull(state);
        addressComponent.selectState(state);
        assertThat(state, is(addressComponent.getSelectedState()));
    }

    @Then("^The postcode field (is|is not) present$")
    public void isPostcodeFieldPresent(String condition) {
        isFieldPresent("postcode", condition);
    }

    @Then("^The town field (is|is not) present$")
    public void isTownFieldPresent(String condition) {
        isFieldPresent("town", condition);
    }

    @Then("^The state field (is|is not) present$")
    public void isStateFieldPresent(String condition) {
        isFieldPresent("state", condition);
    }

    @Then("^The county field (is|is not) present$")
    public void isCountyFieldPresent(String condition) {
        isFieldPresent("county", condition);
    }

    @Then("^I click on the continue button$")
    public void clickOnContinueButton() {
        addressComponent.clickContinue();
    }




    private void isFieldPresent(String fieldName, String condition) {

        WebElement fieldElement = null;

        try {

            if (fieldName.equals("county")) {
                fieldElement = addressComponent.getCountyField();
            }

            else if (fieldName.equals("postcode")) {
                fieldElement = addressComponent.getPostcodeField();
            }

            else if (fieldName.equals("town")) {
                fieldElement = addressComponent.getTownField();
            }

            else if (fieldName.equals("state")) {
                fieldElement = addressComponent.getStateField();
            }

        } catch (PageElementNotFoundException e) {
            }

        if ("is".equals(condition)) {
            assertTrue(fieldName+" is not present or not displayed", fieldElement != null && fieldElement.isDisplayed());
        } else {
            assertTrue(fieldName+" is not hidden", fieldElement == null || !fieldElement.isDisplayed());
        }
    }

    @Then("^The SMS messaging is displayed$")
    public void isSMSMessagingDisplayed() {
        String expectedSmsText = "Provide your mobile number to receive SMS order status updates for NET-A-PORTER PREMIER deliveries";

        WebElement smsMessage = addressComponent.getSMSMessage();
        assertNotNull("No SMS text was found", smsMessage);
        assertEquals("SMS text is not as expected", expectedSmsText, smsMessage.getText());
    }
}
