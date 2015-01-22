package com.netaporter.pws.automation.shared.steps;

import com.netaporter.test.utils.cucumber.steps.LegacyWebAppBaseStep;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class ValidationMessageSteps extends LegacyWebAppBaseStep {

    @Given("^An error message is displayed containing the text (.*)$")
    public void errorMessageExistsOnAddressPage(String errorText) {
        List<String> errorMessages = webBot.getCurrentPage().getErrorMessages();
        boolean found = false;

        for(String s : errorMessages)
            if(s.contains(errorText)) {
                found = true;
                break;
            }
        assertTrue("Messages on page are: "+formatErrorMessageForLogging(errorMessages)+". \n Expected was: "+errorText,found);
    }

    private String formatErrorMessageForLogging(List<String> errorMessages) {
        StringBuilder sb = new StringBuilder();
        for (String errorMessage : errorMessages) {
            sb.append(errorMessage);
            sb.append("\n");
        }
        return sb.toString();
    }


    @Given("^An error message is displayed$")
    public void errorMessageOnPurchasePathSignIn() {
        assertNotSame(0, webBot.getCurrentPage().getErrorMessages().size());
    }

    @Given("^There is a field (.*) that has a mandatory field error$")
    public void genericMandatoryFieldErrorCheck(String field) {
        assertTrue(webBot.getCurrentPage().getMandatoryFieldErrors().contains(field));
    }

    @Given("^There is a drop down field (.*) that has a mandatory field error$")
    public void genericMandatoryDropDownFieldErrorCheck(String field) {
        assertTrue(webBot.getCurrentPage().getMandatoryDropDownFieldErrors().contains(field));
    }

    @And("^The (Givenchy Local|Givenchy International|Local|INTL International|AM International|APAC International|TON AM International) customer care phone number displayed in the error message is correct$")
    public void checkPaymentFailureCustomerCarePhoneNumber(String customerPhoneNumberType) {
        String expectedPhoneNumber = "";
        String customerCareNumberDisplayed = webBot.getCurrentPage().getErrorMessages().get(1);
        Map<String,String> expectedCustomerCareNumbers = productDataAccess.getLegacyDBClient().getCustomerCarePhoneNumber(webBot.getRegionEnum());

        if ("Givenchy Local".equals(customerPhoneNumberType)) {
            expectedPhoneNumber = expectedCustomerCareNumbers.get("GIVENCHY_CUSTOMER_CARE_PHONE_LOCAL");
            System.out.println(expectedPhoneNumber);
        }
        else if ("Givenchy International".equals(customerPhoneNumberType)) {
             expectedPhoneNumber = expectedCustomerCareNumbers.get("GIVENCHY_CUSTOMER_CARE_PHONE_INTERNATIONAL");
             System.out.println(expectedPhoneNumber);
        }
        else if ("Local".equals(customerPhoneNumberType)) {
            expectedPhoneNumber = expectedCustomerCareNumbers.get("CUSTOMER_CARE_PHONE");
            System.out.println(expectedPhoneNumber);
        }
        else if ("INTL International".equals(customerPhoneNumberType)) {
            expectedPhoneNumber = expectedCustomerCareNumbers.get("CUSTOMER_CARE_PHONE_NON_GB");
            System.out.println(expectedPhoneNumber);
        }
        else if ("AM International".equals(customerPhoneNumberType)) {
            expectedPhoneNumber = expectedCustomerCareNumbers.get("CUSTOMER_CARE_PHONE");
            System.out.println(expectedPhoneNumber);
        }
        else if ("APAC International".equals(customerPhoneNumberType)) {
            expectedPhoneNumber = expectedCustomerCareNumbers.get("CUSTOMER_CARE_PHONE");
            System.out.println(expectedPhoneNumber);
        }
        else if ("TON AM International".equals(customerPhoneNumberType)) {
            expectedPhoneNumber = expectedCustomerCareNumbers.get("CUSTOMER_CARE_PHONE_NON_US");
            System.out.println(expectedPhoneNumber);
        }
        // TODO: matcher to extract phone number not compatible with OUTNET phone number, needs to be changed
        Matcher m = Pattern.compile("(\\(?\\+?\\d+\\)?\\s?(NAP)?\\s?)+").matcher(customerCareNumberDisplayed);
        if (m.find()) {
            String foundPhoneNumber = m.group().trim();
            System.out.println(foundPhoneNumber);
            assertEquals("The number is not the same", expectedPhoneNumber, foundPhoneNumber);
        }

    }

}
