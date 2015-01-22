package com.netaporter.pws.automation.napmobile.cucumber.steps.productlist;

import com.netaporter.pws.automation.napmobile.cucumber.steps.BaseNapMobileSteps;
import com.netaporter.pws.automation.napmobile.util.Pagination;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

public class PaginationSteps extends BaseNapMobileSteps {

    @Given("^I have (\\d+) products$")
    public void I_have_products(int products) throws Throwable {
        scenarioSession.putData("paginationProducts", products);
    }

    @When("^pagination value has been set to (\\d+)$")
    public void pagination_value_has_been_set_to(int pagination) throws Throwable {
        scenarioSession.putData("paginationSetting", pagination);
    }

    @When("^I click on pagination (.*)$")
    public void I_click_on_pagination(Pagination pagination) {
        paginationMobileComponent.directionClick(pagination);
    }

    @Then("^I expect the correct number of pages in the pagination$")
    public void I_expect_the_correct_number_of_pages_in_the_pagination() throws Throwable {
        int results = titleMobileComponent.numberOfResultsFromPage();
        int setting = (Integer) scenarioSession.getData("paginationSetting");
        int pages;
        if (results <= setting) {
            pages = 1;
        } else {
            pages = paginationMobileComponent.totalPages();
        }
        Assert.assertTrue(
                "Actual pages = "
                        + pages
                        + " Expected pages = "
                        + paginationMobileComponent.numberOfPages(results, setting),
                paginationMobileComponent.numberOfPages(results, setting) == pages
        );
    }

    @Then("^I will be at page number (\\d+)$")
    public void I_will_be_at_page_number(int pageNumber) throws Throwable {
        Assert.assertTrue(
                "Current page = " + paginationMobileComponent.currentPage(),
                paginationMobileComponent.currentPage() == pageNumber
        );
    }

    @Then("^I expect the last page to have (\\d+) products$")
    public void I_expect_the_last_page_to_have_products(int lastPage) throws Throwable {
        int products = (Integer) scenarioSession.getData("paginationProducts");
        int setting = (Integer) scenarioSession.getData("paginationSetting");
        Assert.assertTrue(
                "Pages = " + paginationMobileComponent.numberOfPages(products, setting),
                paginationMobileComponent.numberOfProductsOnLastPage(products, setting) == lastPage
        );
    }
}
