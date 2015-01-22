package com.netaporter.pws.automation.napmobile.cucumber.steps.productlist;

import com.netaporter.pws.automation.napmobile.cucumber.steps.BaseNapMobileSteps;
import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import org.junit.Assert;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class TitleSteps extends BaseNapMobileSteps {

    @Then("^the page main heading will display (.*)$")
    public void the_page_main_heading_will_display(String heading) throws Throwable {
        Assert.assertTrue(
                "Heading = " + titleMobileComponent.titleMainHeadingText(),
                titleMobileComponent.titleMainHeadingText().equals(heading)
        );
    }

    @Then("^the page heading will display at least (\\d+) result|results$")
    public void the_page_heading_will_display_at_least_results(int results) throws Throwable {
        Assert.assertTrue(
                "Results = " + titleMobileComponent.numberOfResultsFromHeading(),
                titleMobileComponent.numberOfResultsFromHeading() >= results
        );
    }

    @Then("^the page sub-heading will display at least (\\d+) result|results$")
    public void the_page_sub_heading_will_display_at_least_results(int results) throws Throwable {
        Assert.assertTrue(
                "Results = " + titleMobileComponent.numberOfResultsFromSubHeading(),
                titleMobileComponent.numberOfResultsFromSubHeading() >= results
        );
    }

    @Then("^The page title should contain NET-A-PORTER.COM$")
    public void The_page_title_should_contain_NET_A_PORTER_COM() throws Throwable {
       assertThat(webBot.getTitle(), containsString("NET-A-PORTER"));
    }
}


