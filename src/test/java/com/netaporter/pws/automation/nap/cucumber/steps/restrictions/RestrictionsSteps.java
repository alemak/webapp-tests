package com.netaporter.pws.automation.nap.cucumber.steps.restrictions;

import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.test.client.product.dsl.ProductDsl;
import com.netaporter.test.utils.enums.RegionEnum;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: c.choudhury@london.net-a-porter.com
 * Date: 07/02/2013
 * Time: 14:48
 * To change this template use File | Settings | File Templates.
 */
public class RestrictionsSteps extends BaseNapSteps {

    public static final String RESTRICTED_COUNTRY_KEY = "restrictedCountry";
    public static final String RESTRICTION_TYPE_KEY = "restrictionType";
    public static final String PID_KEY = "pid";
    private static final By PRODUCTS = By.cssSelector("#product-list .product-images a");

    @Given("^I visit a (.*) restricted (.*) product details page$")
    public void I_visit_a_Cites_restricted_python_bag_product_details_page(String restrictionType, String productType){
        pyhtonBagSearchResultPage.go();

        String restrictedShippingCountry = null;

        List<WebElement> elements = webBot.findElements(PRODUCTS);

        // product listing page if return more than one results
        for (WebElement productOnPage : elements) {
            String pid = pyhtonBagSearchResultPage.extractPid(productOnPage);
            restrictedShippingCountry = productDataAccess.getLegacyDBClient().getRestrictedShippingCountry(webBot.getRegionEnum(), restrictionType, pid, getCountryLookupRegion());
            if(restrictedShippingCountry!=null){
                putRestrictionsDataIntoSession(restrictionType, restrictedShippingCountry, pid);
                productDetailsPage.goToProduct(pid);
                break;
            }
        }

        // product detail page for only one result
        if (elements.isEmpty()) {
            String pid = productDetailsPage.getPidFromUrl();
            restrictedShippingCountry = productDataAccess.getLegacyDBClient().getRestrictedShippingCountry(webBot.getRegionEnum(), restrictionType, pid, getCountryLookupRegion());
            if(restrictedShippingCountry!=null){
                putRestrictionsDataIntoSession(restrictionType, restrictedShippingCountry, pid);
            }
        }

        if (restrictedShippingCountry==null) {
            throw new IllegalStateException("No country found in this region has" + restrictionType + " restrictions for python bag");
        }

    }


    @Given("^I go to a country that has a (.*) restriction$")
    public void I_go_to_a_country_that_has_a_HAZMAT_restriction(String restriction) throws Throwable {
       Map.Entry<String, String> pidAndCountry = productDataAccess.getLegacyDBClient().findRestrictedProductPidAndCountryForRegion(webBot.getRegionEnum(), restriction, getCountryLookupRegion());

       if (pidAndCountry == null) {
           throw new IllegalStateException("No country found in this region has" + restriction + " restriction.");
       }

       scenarioSession.putData(PID_KEY, pidAndCountry.getKey());

       changeCountryPage.go();
       changeCountryPage.switchToCountry(pidAndCountry.getValue());
    }


    @When("^I view an instock hazmat restricted product details page$")
    public void I_view_an_instock_hazmat_restricted_product_details_page() throws Throwable {
        String pid = (String) scenarioSession.getData(PID_KEY);
        productDetailsPage.goToProduct(pid);
    }

    private void putRestrictionsDataIntoSession(String restrictionType, String restrictedShippingCountry, String pid) {
        scenarioSession.putData(RESTRICTED_COUNTRY_KEY, restrictedShippingCountry);
        scenarioSession.putData(RESTRICTION_TYPE_KEY, restrictionType);
        scenarioSession.putData(PID_KEY, pid);
    }

    private String getCountryLookupRegion() {
        String countryLookupRegion;

        if(webBot.getRegion().equals(RegionEnum.AM.name())){
            countryLookupRegion = RegionEnum.AM.name();
        }else if(webBot.getRegion().equals(RegionEnum.INTL.name())){
            countryLookupRegion = RegionEnum.INTL.name();
        }else {
            countryLookupRegion = RegionEnum.APAC.name();
        }
        return countryLookupRegion;
    }

    @Given("^I go to a country that product is restricted in$")
    public void I_go_to_a_country_that_product_is_restricted_in() {
        changeCountryPage.go();
        String restrictedShippingCountry = (String) scenarioSession.getData(RESTRICTED_COUNTRY_KEY);
        changeCountryPage.switchToCountry(restrictedShippingCountry);
        String pid = (String) scenarioSession.getData(PID_KEY);
        productDetailsPage.goToProduct(pid);
    }

    @Given("^I go to a country that product is not restricted in$")
    public void I_go_to_a_the_country_that_product_is_not_restricted_in() throws Throwable {
        changeCountryPage.go();
        String restrictionType = (String) scenarioSession.getData(RESTRICTION_TYPE_KEY);
        String pid = (String) scenarioSession.getData(PID_KEY);

        String nonRestrictedShippingCountry = productDataAccess.getLegacyDBClient().getNonRestrictedShippingCountry(webBot.getRegionEnum(), restrictionType, pid, getCountryLookupRegion());
        changeCountryPage.switchToCountry(nonRestrictedShippingCountry);
        productDetailsPage.goToProduct(pid);
    }

    @Then("^a warning is displayed on the product details page$")
    public void a_warning_is_displayed_on_the_product_details_page()  {
        assertTrue(productDetailsPage.hasShippingRestrictionWarningMsg());
    }

    @Then("^a warning should not be displayed on the product details page$")
    public void a_warning_should_not_be_displayed_on_the_product_details_page() {
        assertFalse(productDetailsPage.hasShippingRestrictionWarningMsg());
    }

    @When("^I add a product into my shopping bag from the product details page$")
    public void I_add_a_product_into_my_shopping_bag_from_the_product_details_page() {
        addInStockItemToBasket(productDetailsPage);
    }

    @Then("^a warning is displayed on my shopping bag page$")
    public void a_warning_is_displayed_on_my_shopping_bag_page() {

        assertTrue(shoppingBagPage.doesFirstProductInShoppingBagHaveShippingRestriction());
    }

    @Then("^a warning is not displayed on my shopping bag page$")
    public void a_warning_is_not_displayed_on_my_shopping_bag_page() throws Throwable {
        assertFalse(shoppingBagPage.doesFirstProductInShoppingBagHaveShippingRestriction());
    }

    @When("^I ship to restricted country$")
    public void I_ship_to_restricted_country()  {
        shoppingBagPage.clickProcceedToPurchase();
        String restrictedCountry = (String) scenarioSession.getData(RESTRICTED_COUNTRY_KEY);
        purchasePathFlowPage.checkoutInSignedInState(restrictedCountry);
    }

    @Then("^a warning is displayed in the payment page$")
    public void a_warning_is_displayed_in_the_payment_page() {
        assertTrue(purchasePathFlowPage.isShippingRestrictionWarningDisplayed());

        assertTrue(purchasePathFlowPage.ishShippingHeaderWarningDisplayed());

    }

    @Then("^a warning is not displayed in the payment page$")
    public void a_warning_is_not_displayed_in_the_payment_page() {
        assertFalse(purchasePathFlowPage.isShippingRestrictionWarningDisplayed());

        assertFalse(purchasePathFlowPage.ishShippingHeaderWarningDisplayed());

    }

    @When("^I arrive at Hazmat restricted product detail page for country code (.*)$")
    public void I_arrive_at_Hazmat_restricted_product_detail_page_for_country_code_RU(String countryCode) throws Throwable {
        List<String> skus = productDataAccess.getApiClientFacade().findSkus(webBot.getSalesChannelByBrandAndRegion(), ProductDsl.ProductCategory.BAGS, ProductDsl.ProductAvailability.IN_STOCK, 1);
        String[] pid = skus.get(0).split("-");

        productDataAccess.getLegacyDBClient().restrictProductAsHazmat(webBot.getRegionEnum(), pid[0], countryCode);
        productDetailsPage.goToProduct(pid[0]);

    }
}
