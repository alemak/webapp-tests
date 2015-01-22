package com.netaporter.pws.automation.nap.cucumber.steps.productlisting;

import com.google.common.base.Predicate;
import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.pws.automation.nap.pages.NAPSaleListingPage;
import com.netaporter.pws.automation.shared.pages.IProductListPage;
import com.netaporter.test.utils.pages.exceptions.PageElementNotFoundException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.annotation.Nullable;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;

/**
 * User: o_csiki
 * Date: 10/10/13
 */
public class SaleListingSteps extends BaseNapSteps {

    protected static final By PRODUCT_LIST_TITLE_ELEMENT_KEY = By.cssSelector(".product-list-title>h1");
    private static final By PRODUCT_LIST_PAGE_TITLE_SELECTOR = By.cssSelector(".product-list-title>h1");

    @And("^I navigate to the sale landing page using the TopNav$")
    public void I_navigate_to_the_sale_landing_page() throws Throwable {
        homePage.clickSaleTopNavLink();
        napSaleLandingPage.closeDontMissOutPopup();
    }

    @Given("^I navigate to the sale landing page directly$")
    public void I_navigate_to_the_sale_landing_page_directly() throws Throwable {
        napSaleLandingPage.go();
        napSaleLandingPage.closeDontMissOutPopup();
    }

    @When("^I click the (.*) link from the sale landing page$")
    public void I_click_the_desired_link_from_the_sale_landing_page(String section) throws Throwable {
        NAPSaleListingPage napSaleListingPage = napSaleLandingPage.clickSectionAndReturnPage(section);
        webBot.setCurrentPage(napSaleListingPage);
        scenarioSession.putData(PRODUCT_LISTING_PAGE_KEY, napSaleListingPage);
        scenarioSession.putData(CONDITION_KEY, true);

        recordTimeStamp();
    }

    @Given("^I navigate to the sale landing page using a url param with country (.*) language (.*) and deviceType (.*)$")
    public void I_navigate_to_the_sale_landing_page_using_a_url_param_with_parameters(String countryValue, String languageValue, String deviceType) throws Throwable {
       webBot.goToPageWithPathParams(napSaleLandingPage, countryValue+"/"+languageValue+"/"+deviceType);
       napSaleLandingPage.closeDontMissOutPopup();
    }

    @When("^the first (\\d+) sale products are displayed$")
    public void the_first_numberOf_sale_products_are_displayed(int numberOfProducts) throws Throwable {

        NAPSaleListingPage napSaleListingPage = (NAPSaleListingPage) scenarioSession.getData(PRODUCT_LISTING_PAGE_KEY);
        assertThat(napSaleListingPage.getAllProductsPids().size(), is(numberOfProducts));
    }

    @Then("^the (.*) for each should be displayed$")
    public void the_information_for_each_should_be_displayed(String information) throws Throwable {

        NAPSaleListingPage napSaleListingPage = (NAPSaleListingPage) scenarioSession.getData(PRODUCT_LISTING_PAGE_KEY);

        if ("original price".equalsIgnoreCase(information)) {
            assertThat(napSaleListingPage.getAllProductsPids().size(), is(napSaleListingPage.getAllOriginalPrices().size()));
            assertThat(napSaleListingPage.getAllOriginalPrices(), not(hasItem("0")));
        }
        if ("discount".equalsIgnoreCase(information)) {
            assertThat(napSaleListingPage.getAllProductsPids().size(), is(napSaleListingPage.getAllDiscountValues().size()));
            assertThat(napSaleListingPage.getAllDiscountValues(), not(hasItem("0")));
        }
        else if ("name".equalsIgnoreCase(information))
            assertThat(napSaleListingPage.getAllProductsPids().size(), is(napSaleListingPage.getAllProductNames().size()));
        else if ("designer".equalsIgnoreCase(information))
            assertThat(napSaleListingPage.getAllProductsPids().size(), is(napSaleListingPage.getAllProductDesignersWithDuplicates().size()));
        else if ("markdown price".equalsIgnoreCase(information)) {
            assertThat(napSaleListingPage.getAllProductsPids().size(), is(napSaleListingPage.getAllMarkdownPrices().size()));
            assertThat(napSaleListingPage.getAllMarkdownPrices(), not(hasItem("0")));
        }
    }

    @Then("^I am on the (.*) sale listing page$")
    public void I_am_on_the_desired_category_sale_listing_page(String category) throws Throwable {

        //test will be ignored if the category was not visible and thus not clicked
      //  assumeThat((Boolean) scenarioSession.getData(CONDITION_KEY), is(true));

        webBot.waitUntil(new Predicate<WebDriver>() {
            @Override
            public boolean apply(@Nullable WebDriver webDriver) {
                try{webDriver.findElement(PRODUCT_LIST_PAGE_TITLE_SELECTOR);}
                catch (PageElementNotFoundException e){
                    return false;
                }
                return true;
            }
        }, 2);

        WebElement listingPageTitleElement = webBot.findElement(PRODUCT_LIST_TITLE_ELEMENT_KEY);

        assertThat(listingPageTitleElement.getText(), equalToIgnoringCase(category));
    }


    @When("^I click on a random product from the listing page$")
    public void I_click_on_a_random_product_from_the_listing_page() throws Throwable {

        IProductListPage napListingPage = (IProductListPage) scenarioSession.getData(PRODUCT_LISTING_PAGE_KEY);
        scenarioSession.putData(SKU_OR_PID_KEY, napListingPage.clickOnARandomProduct());
    }


    private void recordTimeStamp() {
        String pageRefreshTimeStamp = getPageRefreshTimeStamp();

        scenarioSession.putData(ProductListingSteps.KEY_INITIAL_TIMESTAMP, pageRefreshTimeStamp);
    }

    private String getPageRefreshTimeStamp() {
        WebElement pageRefreshWebElement = webBot.findElement(By.className("pageRefreshTimeStamp"));
        return pageRefreshWebElement.getAttribute("data-timestamp");
    }

    @When("^I click (Sale_Home|Clothing|Shoes|Bags|Lingerie|Accessories) from the sale navigation$")
    public void I_click_target_category_from_the_sale_navigation(String category) throws Throwable {
        NAPSaleListingPage napSaleListingPage = (NAPSaleListingPage) scenarioSession.getData(PRODUCT_LISTING_PAGE_KEY);
        napSaleListingPage.clickCategoryFromSaleNavigation(category);
    }

    @Then("^I am taken to the sale landing page$")
    public void I_am_taken_to_the_sale_landing_page() throws Throwable {
        assertTrue("Url does not end with sale, this may not be the sale landing page", webBot.getCurrentUrl().endsWith("Sale/All"));
        assertTrue("Clothing element is not visible, this may not be the sale landing page", napSaleLandingPage.isElementVisible("Clothing"));
    }
}