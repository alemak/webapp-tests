package com.netaporter.pws.automation.napmobile.cucumber.steps.common;

import com.netaporter.pws.automation.napmobile.cucumber.steps.BaseNapMobileSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Assert;

public class CommonSteps extends BaseNapMobileSteps {

    @Given("^I navigate to the (.*) page$")
    public void I_navigate_to_the_page(String page) throws Throwable {
        if (webBot.getDriver().getWindowHandles().size() > 1) {
            webBot.getDriver().switchTo().window("1");
        }
        lookupPage(page).go();
    }

    @Then("^the page title should contain (.*)$")
    public void the_page_title_should_contain(String title) throws Throwable {
        Assert.assertTrue(
                "Page Title = " + webBot.getDriver().getTitle(),
                webBot.getDriver().getTitle().contains(title)
        );
    }

    @Then("^page url will end with (.*)$")
    public void page_url_will_end_with(String url) throws Throwable {
        Assert.assertTrue(
                "Url = " + webBot.getCurrentUrl(),
                webBot.getCurrentUrl().endsWith(url)
        );
    }
}
