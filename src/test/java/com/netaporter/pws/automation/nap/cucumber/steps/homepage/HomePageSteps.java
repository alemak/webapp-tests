package com.netaporter.pws.automation.nap.cucumber.steps.homepage;

import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: J.Christian@net-a-porter.com
 * Date: 20/08/2013
 * Time: 11:21
 * To change this template use File | Settings | File Templates.
 */
public class HomePageSteps extends BaseNapSteps {

    @And("^I am currently on the home page$")
    public void currentlyOnHomePage() {
        // Not really happy with this assert.   Really could do with the page objects asserting they are on the correct page
        assertTrue("Expect to be on homepage", webBot.getCurrentUrl().endsWith(homePage.getPath()));
    }


    @Then("^I am on the home page$")
    public void I_am_on_the_home_page() throws Throwable {
        homePage.verifyHomePage();
    }
}
