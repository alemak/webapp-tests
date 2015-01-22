package com.netaporter.pws.automation.nap.cucumber.steps.navigation;

import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.pws.automation.nap.pages.NAPProductListPage;
import com.netaporter.pws.automation.nap.pages.NapListingPageRegistry;
import com.netaporter.pws.automation.shared.utils.WaitUtil;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * User: o_csiki
 * Date: 29/04/13
 */
public class NavigationSteps extends BaseNapSteps {

    private static final String IS_BEAUTY_LINK_ENABLED = "false";

    @When("^I select the (back|forward) button on the browser$")
    public void I_select_the_navigation_button_on_the_browser(String navigationDirection) throws Throwable {

        if ("forward".equals(navigationDirection)) {
            webBot.navigateForward();
        }
        else if ("back".equals(navigationDirection)) {
            webBot.navigateBack();
        }
        else {
            throw new Exception("Invalid navigation direction specified");
        }
        WaitUtil.waitForSpinnerToAppearAndDisappear(webBot);
    }

    @Then("^(.*) is present in the TopNav bar$")
    public void DesiredLink_is_present_in_the_TopNav_bar(String topNavLink) throws Throwable {
        if ("Sale".equalsIgnoreCase(topNavLink)) {
            topNavComponent.isSaleDisplayedInTopNav();
        }
        else if ("Lingerie".equalsIgnoreCase(topNavLink)) {
            topNavComponent.isLingerieDisplayedInTopNav();
        }
        else if ("Clothing".equalsIgnoreCase(topNavLink)) {
            topNavComponent.isClothingDisplayedInTopNav();
        }
        else if ("Beauty".equalsIgnoreCase(topNavLink)) {
            topNavComponent.isBeautyLinkDisplayedInTopNav();
        }
        else if ("Whats New".equalsIgnoreCase(topNavLink)) {
            topNavComponent.isWhatsNewDisplayedInTopNav();
        }
        else if ("Sport".equalsIgnoreCase(topNavLink)) {
            topNavComponent.isSporterDisplayedInTopNav();
        }
    }

    @And("^(.*) is between (.*) and (.*)")
    public void DesiredLink_is_between_PreviousLink_and_NextLink(String linkName, String linkNamePrevious, String linkNameNext) throws Throwable {
        topNavComponent.checkThatLinkIsBetweenPrevLinkAndNextLinkInTopNav(linkName, linkNamePrevious, linkNameNext);
        Thread.sleep(2000);
    }

    @And("^(.*) is removed from the TopNav bar$")
    public void DesiredLink_is_removed_from_the_TopNav_bar(String linkName) throws Throwable {
        assertTrue(topNavComponent.isLinkRemovedFromTopNav(linkName));
    }

    @When("^I click the (.*) link from the TopNav$")
    public void I_click_the_DesiredLink_link_from_the_TopNav(String linkName) throws Throwable {
        createTopNavLinkWebElement(linkName).click();
        Thread.sleep(3000);
    }

    @When("^I hover over the (.*) link from the TopNav$")
    public void I_hover_over_a_link_from_the_TopNav(String linkName) throws Throwable {
        Actions action = new Actions(webBot.getDriver());
        action.moveToElement(createTopNavLinkWebElement(linkName)).build().perform();
        Thread.sleep(2000);
    }

    @When("^I hover over the Whats New link in the TopNav$")
    public void I_hover_over_the_whats_new_link_in_the_TopNav() throws Throwable {
        I_hover_over_a_link_from_the_TopNav("whats");
    }

    private WebElement createTopNavLinkWebElement(String linkNameToFind) {
        return webBot.findElement(By.xpath(".//*[@id='top-nav-btn-links']/li[@id='" + linkNameToFind.toLowerCase() + "']"));
    }

    @When("^(.*) is active in the web dB$")
    public void Field_is_active_in_the_web_dB(String field) throws Throwable {

        if ("sale".equalsIgnoreCase(field)){
            assertTrue("Sale is not active in the webDB", isSaleOn());
            }
        else if  ("MySubscription".equalsIgnoreCase(field)){
            scenarioSession.putData(IS_MY_SUBSCRIPTIONS_ENABLED, isSubscriptionOn());
        }
        else if ("Beauty Link".equalsIgnoreCase(field)) {
            scenarioSession.putData(IS_BEAUTY_LINK_ENABLED, isBeautyLinkOn());
            assertTrue("Beauty Link is not active in the webDB", isBeautyLinkOn());
        }
    }

    @Then("^I reach the (.*) listing page$")
    public void I_reach_the_DesiredLink_page(String listingPageType) throws Throwable {
        isCorrectDestinationListingPage(listingPageType);
    }

    @Then("^if Sale is enabled in the weDB the Sale button is present in the TopNav in the correct place$")
    public void if_Sale_is_enabled_in_the_weDB_the_Sale_button_is_present_in_the_TopNav_in_the_correct_place() throws Throwable {
        if (isSaleOn()){
           DesiredLink_is_present_in_the_TopNav_bar("Sale");
           DesiredLink_is_between_PreviousLink_and_NextLink("Sale-btn", "the_left_edge", "Whats");
        }
        else{
           DesiredLink_is_removed_from_the_TopNav_bar("Sale");
        }
    }

    @Given("^I visit the home page$")
    public void I_visit_the_home_page() throws Throwable {
        homePage.go();
    }

    @Given("^I visit the home page on the (am|intl|apac) website$")
    public void I_visit_the_home_page_on_the_channel_website(String regionName) throws Throwable {
        setRegion(regionName.toUpperCase());
        homePage.go();
    }

    @Then("^the (Whats|Clothing) TopNav dropdown is displayed$")
    public void the_TopNav_dropdown_is_displayed(String category) throws Throwable {
        assertTrue("TopNav dropdown is not displayed", topNavComponent.isTopNavDropDownDisplayed(category));
    }

    @Then("^the Whats New TopNav dropdown is displayed$")
    public void the_Whats_New_TopNav_dropdown_is_displayed() throws Throwable {
        the_TopNav_dropdown_is_displayed("Whats");
    }

    @When("^I click the first link in the (WhatsNew|Clothing) TopNav dropdown$")
    public void I_click_the_desired_link_in_the_TopNav_dropdown(String topNavCategory) throws Throwable {
        if ("WhatsNew".equalsIgnoreCase(topNavCategory))
            topNavComponent.clickFirstLinkInWhatsNewTopNavDropdwn();
        else if ("Clothing".equalsIgnoreCase(topNavCategory))
            topNavComponent.clickLinkInClothingDropdown();
    }

    @Then("^I am taken to the NAP (.*) listing page$")
    public void I_am_taken_to_the_NAP_expected_listing_page(String expectedPageName) throws Throwable {
        Thread.sleep(3000);
        assertTrue("Current URL does not contain the "+expectedPageName+" parameter. Current URL:"+webBot.getCurrentUrl(), webBot.currentUrlContains(expectedPageName));
        if ("Whats_new".equalsIgnoreCase(expectedPageName)) {
            NAPProductListPage napProductListPage = (NAPProductListPage) ((NapListingPageRegistry)listingPageRegistry).lookupProductListPage("whatsNewListing");
            assertThat(napProductListPage.getListingPageNameFromHeader(), containsString("What's new"));
        }
    }

    @And("^I should see service message banner$")
    public void I_should_see_service_message_banner() throws Throwable {
        assertTrue("Service message is not visible", webBot.findElement(By.cssSelector(".service-banner.service-message-1")).getAttribute("style").contains("margin-top"));
    }

    @And("^Girdle attributes are present$")
    public void Girdle_attributes_are_present() throws Throwable {
        assertFalse("data currency symbol attribute is missing", webBot.findElement(By.xpath("html/body")).getAttribute("data-currency-symbol").isEmpty());
        assertFalse("data currency code attribute is missing",webBot.findElement(By.xpath("html/body")).getAttribute("data-currency-code").isEmpty());

        if (webBot.getCurrentUrl().contains("chromeless")) {
            assertTrue("data layout attribute is missing", webBot.findElement(By.xpath("html/body")).getAttribute("data-layout-id").equalsIgnoreCase("desktop-app-default"));
        } else if (webBot.getCurrentUrl().contains("Sport")){
            assertTrue("data layout attribute is missing", webBot.findElement(By.xpath("html/body")).getAttribute("data-layout-id").equalsIgnoreCase("desktop-default"));
        } else {
            assertTrue("data layout attribute is missing", webBot.findElement(By.xpath("html/body")).getAttribute("data-layout-id").equalsIgnoreCase("desktop-full-width"));
        }
    }
}