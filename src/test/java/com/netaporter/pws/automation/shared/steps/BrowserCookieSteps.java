package com.netaporter.pws.automation.shared.steps;

import com.netaporter.test.utils.cucumber.steps.LegacyWebAppBaseStep;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.fail;

public class BrowserCookieSteps extends LegacyWebAppBaseStep {

    private static final String MOBILE_APP_NAME = "mobileAppName";
    private static final String MOBILE_APP_VERSION = "mobileAppVersion";

    @When("^I have a cookie set with name (.*) and the value is (.*)$")
    public void addCookie(String cookieName, String cookieValue) {
        webBot.addCookie(cookieName, cookieValue);
    }

    @When("^I clear my cookies$")
    public void clearAllCookies() {
        webBot.clearCookies();
    }

    @When("^I use a mobile device with the following app (.*)-(.*)$")
    public void simulateMobileDevice(String appName, String appVersion) {
        addCookie(MOBILE_APP_NAME, appName);
        addCookie(MOBILE_APP_VERSION, appVersion);
    }

    @When("^I delete the cookie called (.*)$")
    public void deleteCookieNamed(String cookieName) {
        webBot.deleteCookieNamed(cookieName);
    }


    @When("^I delete the JSESSIONID cookie$")
    public void deleteJsessionIdCookie() {
        deleteCookieNamed("JSESSIONID_" + webBot.getRegion());
    }

    @Then("^The cookie called (.*) has the value (.*)$")
    public void assertCookieValue(String cookieName, String cookieValue) {

        try {
            Cookie cookie = webBot.getCookie(cookieName);
            assertThat("The value of the cookie called '" + cookieName + "' was incorrect", cookie.getValue(), equalTo(cookieValue));
        } catch (Exception e) {
            fail("The cookie called '" + cookieName + "' does not exist");
        }
    }

    @When("^The cookie called (.*) has a value that is not null$")
    public void assertCookieValueNotNull(String cookieName) {

        try {
            Cookie cookie = webBot.getCookie(cookieName);
            assertThat("The value of the cookie called '" + cookieName + "' was incorrect", cookie.getValue(), notNullValue());
        } catch (Exception e) {
            fail("The cookie called '" + cookieName + "' does not exist");
        }
    }

}
