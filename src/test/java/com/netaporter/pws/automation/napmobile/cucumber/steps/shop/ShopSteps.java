package com.netaporter.pws.automation.napmobile.cucumber.steps.shop;

import com.netaporter.pws.automation.napmobile.cucumber.steps.BaseNapMobileSteps;
import com.netaporter.pws.automation.napmobile.util.Category;
import com.netaporter.pws.automation.shared.utils.WaitUtil;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class ShopSteps extends BaseNapMobileSteps {

    @Then("^the (.*) accordion control will display (.*)$")
    public void the_accordion_control_will_display(Category category, String control) throws Throwable {
        Assert.assertTrue(
                "Control = " + shopMobilePage.accordionControlText(category),
                shopMobilePage.accordionControlText(category).equals(control)
        );
    }

    @And("^the (.*) sub-list will be displayed$")
    public void the_sub_list_will_be_displayed(Category category) throws Throwable {
        Assert.assertTrue(
                "Content = " + shopMobilePage.subListDisplayed(category),
                shopMobilePage.subListDisplayed(category)
        );
    }

    @And("^the (.*) sub-list will not be displayed$")
    public void the_sub_list_will_not_be_displayed(Category category) throws Throwable {
        Assert.assertFalse(
                "Content = " + shopMobilePage.subListDisplayed(category),
                shopMobilePage.subListDisplayed(category)
        );
    }

    @When("^I click the (.*) accordion control|control again$")
    public void I_click_the_accordion_control(Category category) throws Throwable {
        shopMobilePage.accordionControlClick(category);
       }

    @Then("^the (.*) sub-list links will be displayed$")
    public void the_sub_list_links_will_be_displayed(Category category) throws Throwable {
        Map<String, List<String>> results = shopMobilePage.subListLinksDisplayed(category);
        Assert.assertTrue(
                "Expected = "
                        + results.get("subListValuesText").toString()
                        + " Actual = "
                        +  results.get("subListItemsText").toString(),
                results.get("subListValuesText").equals(results.get("subListItemsText"))
        );
    }

    @And("^I click the (.*) (.*) link$")
    public void I_click_the_link(Category category, String subList) throws Throwable {
        shopMobilePage.subListLinkClick(category, subList);
       }
}
