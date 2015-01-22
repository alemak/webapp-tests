package com.netaporter.pws.automation.nap.cucumber.steps.productlisting;

import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.pws.automation.nap.pages.NAPAWSListingPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static junit.framework.TestCase.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: a.mosincat@net-a-porter.com
 * Date: 24/06/2014
 * Time: 17:30
 */

public class SportListingSteps extends BaseNapSteps {


    private static final String NAV_LEVEL_3_KEY_ELEMENT = "navLevel3";

    @And("^I navigate to the sport landing page directly$")
    public void I_navigate_to_the_sport_landing_page_directly() throws Throwable {
        napSportLandingPage.go();
        napSportLandingPage.closeDontMissOutPopup();
    }

    @Then("^I am taken to the sport landing page$")
    public void I_am_taken_to_the_sport_landing_page() throws Throwable {
        assertTrue("Url does not end with sport, this may not be the sport landing page", webBot.getCurrentUrl().contains("Sport"));
    }

    @When("^I navigate to the sport landing page using the TopNav$")
    public void I_navigate_to_the_sport_landing_page_using_the_TopNav() throws Throwable {
     homePage.clickSportTopNavLink();
     napSportLandingPage.closeDontMissOutPopup();
    }

    @When("^I click the (.*) from the sport landing page$")
    public void I_click_the_link_from_the_sport_landing_page(String link) throws Throwable {
        NAPAWSListingPage NAPAWSListingPage = napSportLandingPage.clickLinkAndReturnPage(link);
        webBot.setCurrentPage(NAPAWSListingPage);
        scenarioSession.putData(PRODUCT_LISTING_PAGE_KEY, NAPAWSListingPage);
        scenarioSession.putData(CONDITION_KEY, true);
        Thread.sleep(2000);
    }


    @And("^I select (.*) sport navigation level3 categories$")
    public void I_select_all_the_sport_navigation_level3_categories(String level3) throws Throwable {
        NAPAWSListingPage NAPAWSListingPage = (NAPAWSListingPage) scenarioSession.getData(PRODUCT_LISTING_PAGE_KEY);
        NAPAWSListingPage.selectAllLevel3CategoriesOfSelectedLevel2Category(level3);
      }

    @And("^I force to display app layout$")
    public void I_force_to_display_app_layout() throws Throwable {
        Thread.sleep(1000);
        String girdleAppLayout = webBot.getCurrentUrl() + "&chromeless=true";
        webBot.getDriver().get(girdleAppLayout);
        Thread.sleep(1000);
    }
}
