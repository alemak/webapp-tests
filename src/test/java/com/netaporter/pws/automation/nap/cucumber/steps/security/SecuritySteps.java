package com.netaporter.pws.automation.nap.cucumber.steps.security;

import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.pws.automation.nap.pages.AbstractNapPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * User: x.qi@london.net-a-porter.com
 * Date: 15/02/2013
 */
public class SecuritySteps extends BaseNapSteps {

    @When("^I try to access a secured page: (.*)$")
    public void I_try_to_access_a_securedPage_page(String relativePath) throws Throwable {
		String address = webBot.getRegion().toLowerCase()+"/"+relativePath;
        webBot.goToPage(new AbstractNapPage("anyPageName", address) {
        });
    }

    @Then("^I am taken to the (error|signin|signinPurchasePath) page$")
    public void I_am_taken_to_the_errorPage_page(String page) throws Throwable {
        if ("error".equals(page)) {
            webBot.findElement(By.className("error-content"));
        } else if("signin".equals(page)) {
            webBot.findElement(By.id("signin_top2"));
        }
        else {
            webBot.findElement(By.name("LoginPurchasePath"));
        }
    }

    @And("^The current url should be http$")
    public void url_is_http() throws Throwable {
        String url = webBot.getCurrentUrl();
        assertThat("The url '" + url + "' looks like it's https and not http" , url.substring(0, 5), equalTo("http:"));
    }

    @And("^The current url should be https$")
    public void url_is_https() throws Throwable {
        String url = webBot.getCurrentUrl();
        assertThat("The url '" + url + "' looks like it's http and not https" , url.substring(0, 5), equalTo("https"));
    }

    @And("^the (.*) does not exist in the HTML of the page$")
    public void the_pageLink_does_not_exist_in_the_HTML_of_the_page(String pageLink) throws Throwable {

        final String pageSource = webBot.getPageSource();

        assertThat(pageSource, not(containsString(pageLink)));

    }
}
