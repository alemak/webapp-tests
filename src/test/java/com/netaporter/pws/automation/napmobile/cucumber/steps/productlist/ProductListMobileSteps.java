package com.netaporter.pws.automation.napmobile.cucumber.steps.productlist;

import com.netaporter.pws.automation.napmobile.cucumber.steps.BaseNapMobileSteps;
import com.netaporter.pws.automation.napmobile.util.SortOrder;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.assertTrue;

public class ProductListMobileSteps extends BaseNapMobileSteps {

    @Then("^product list will be sorted in (.*) order$")
    public void product_list_will_be_sorted_in_order(SortOrder sortOrder) throws Throwable {
        assertTrue(
            "Order = " + productlistMobileComponent.productListSorted(sortOrder),
            productlistMobileComponent.productListSorted(sortOrder)
        );
    }

    @Then("^product list will only display selected designer|designers$")
    public void product_list_will_only_display_selected_designer() throws Throwable {
        Collection<String> selectedDesigners = new HashSet<String>();
        Integer count = (Integer) scenarioSession.getData("selectedDesignersCount");
        for (int i = 1; i <= count; i++) {
            String designer = (String) scenarioSession.getData("selectedDesigners" + Integer.toString(i));
            selectedDesigners.add(designer.toUpperCase());
        }
            assertTrue(
                "Selected Designers = "
                        + selectedDesigners.toString()
                        + " Displayed Designers = "
                        + productlistMobileComponent.designersDisplayedInProductListing().toString(),
                productlistMobileComponent.onlySelectedDesignersAreDisplayed(selectedDesigners)
        );
    }

    @Given("^I navigate to the sport mobile landing page directly$")
    public void I_navigate_to_the_sport_mobile_landing_page_directly() throws Throwable {
          napsportLandingMobilePage.go();
    }

    @Then("^the URL contains the (.*) name$")
    public void the_URL_contains_the_Sport_name(String category) throws Throwable {
        Thread.sleep(2000);
        assertTrue("Current URL doesn't contain " + category, webBot.getCurrentUrl().contains(category));
    }

    @And("^I click on a product from the mobile listing page$")
    public void I_click_on_a_product_from_the_mobile_listing_page() throws Throwable {
        shopBagsMobilePage.clickARandomProduct();
    }
}
