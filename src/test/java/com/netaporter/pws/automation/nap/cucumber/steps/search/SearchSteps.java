package com.netaporter.pws.automation.nap.cucumber.steps.search;

import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.pws.automation.shared.pages.IProductListPage;
import com.netaporter.pws.automation.shared.pojos.Product;
import com.netaporter.test.client.product.dsl.ProductDsl;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;

import java.util.*;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;

/**
 * User: x.qi@london.net-a-porter.com
 * Date: 12/02/2013
 */
public class SearchSteps extends BaseNapSteps {

    private static final String INSTOCK = "IN_STOCK";
    protected static final String SEARCH_KEYWORD = "search keyword";


    private static final Set<By> SEARCH_RESULTS_ELEMENTS = new HashSet<By>();

    static {
        By productListingPage = By.id("product-list-menu");
        By landingPageResult = By.xpath(".//*[@id='wedding-menu']");

        SEARCH_RESULTS_ELEMENTS.add(productListingPage);
        SEARCH_RESULTS_ELEMENTS.add(landingPageResult);
    }

    @When("^I search for a (.*) keyword$")
    public void I_search_for_a_Tops_keyword(String keyword) throws Throwable {
        getCurrentNapPage().search(keyword);
    }

    //responsive search
    @When("^I type for the (.*) keyword in search box$")
    public void I_type_for_the_keyword_in_search_box(String keyword) throws Throwable {
        searchPage.responsiveSearch(keyword);
        scenarioSession.putData(SEARCH_KEYWORD, keyword);
    }

    @Then("^I see expected search results$")
    public void I_see_expected_results() throws Throwable {
        for(By by: SEARCH_RESULTS_ELEMENTS){
            if(doesElementExist(by)){
                return;
            }
        }
        fail("Can't find expected results");
    }

    @Then("^I see the invalid search page$")
    public void I_see_the_invalid_search_page() throws Throwable {
        assertTrue("Invalid search message is not displayed", invalidSearchPage.isErrorMessageDisplayed());
    }

    @Then("^I see the workwear shop page$")
    public void I_see_the_workwear_shop_page() throws Throwable {
        assertTrue(webBot.getCurrentUrl().contains("workwear"));
    }

    @Given("^I find an (IN_STOCK|SOLD_OUT) product pid in product listing page$")
    public void I_find_an_instock_product_pid_in_product_listing_page(ProductDsl.ProductAvailability instockOrSoldOut) throws Throwable {
        List<String> solrSkuList = productDataAccess.Solr_findSkus(webBot.getSalesChannelByBrandAndRegion(), ProductDsl.ProductCategory.BAGS, instockOrSoldOut, 1);
        if(solrSkuList.size()==0){
            throw new AssertionError("No "+instockOrSoldOut+" Product Found");
        }
        Product productToSearch = new Product(null, solrSkuList.get(0));
        scenarioSession.putData(SKU_OR_PID_KEY, productToSearch.getPid());
    }

    private String findSearchingPid(String instockOrSoldOut) {
        boolean isInStock = isInStockStringToBool(instockOrSoldOut);

        Set<IProductListPage> productListPages = new HashSet<IProductListPage>();

        for (int i = 0; i < 20; i++) {
            IProductListPage napProductListPage = goToAnyProductListPage();

            if (!productListPages.contains(napProductListPage)) {
                productListPages.add(napProductListPage);
                napProductListPage.go();

                // Get PIDs on Page
                List<String> pids = napProductListPage.getAllProductsPids();

				//List returned by getAllProductPids doesn't support shuffle
				List<String> pidsArrayList = new ArrayList<String>(pids);

                //Mix up the order as to not always pick the same product
                Collections.shuffle(pidsArrayList);
                for (String pid : pidsArrayList) {
                    if (productDataAccess.Solr_isPidOrSkuInStock(webBot.getSalesChannelByBrandAndRegion(), pid) == isInStock) {
                        return pid;
                    }
                }

            }
        }

        return null;
    }

    @When("^I search for the pid$")
    public void I_search_for_the_pid() throws Throwable {
        Object data = scenarioSession.getData(SKU_OR_PID_KEY);

        if (data != null) {
            getCurrentNapPage().search((String) data);
        } else {
            System.out.println("WARNING: No pid found previously, so I'm not going to search for it");
        }

    }

    @Then("^the product page shows (SOLD_OUT|IN_STOCK)")
    public void the_product_page_shows_soldout(String stockInfo) throws Throwable {
        if (isInStockStringToBool(stockInfo)) {
            assertTrue("Product is not in stock in the product details page", productDetailsPage.isInStock());
        }
        else {
            assertFalse("Product is not sold out in product details page", productDetailsPage.isInStock());
        }
    }

    private boolean isInStockStringToBool(String stockInfo) {
        return INSTOCK.equals(stockInfo);
    }

    @Then("^the (.*) page is displayed$")
    public void the_correct_page_is_displayed(String pageName) throws Throwable {
        assertThat(webBot.getTitle(), containsString("Shop "+pageName+" at NET-A-PORTER"));
    }

    @And("^I click on first auto suggest option$")
    public void I_click_on_first_auto_suggest_option() throws Throwable {
        searchPage.clickAutoSuggestionResult();
    }

    @Then("^search listing page is displayed$")
    public void search_listing_page_is_displayed() throws Throwable {
        String keyword = scenarioSession.getData(SEARCH_KEYWORD);

        String designer = searchPage.getDesignerName();
        int designerId = productDataAccess.getApiClientFacade().getBrandId(webBot.getSalesChannelByBrandAndRegion(), designer);

        assertTrue("Search listing page is not displayed",webBot.getCurrentUrl().contains("designerFilter="+designerId+"&keywords="+keyword));
    }

    @Then("^no search keyword match display$")
    public void no_search_keyword_match_display() throws Throwable {
        searchPage.verifyNoResultAutoSuggestMessage();
    }

}