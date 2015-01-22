package com.netaporter.pws.automation.shared.steps;

import com.netaporter.pws.automation.shared.pages.IProductDetailsPage;
import com.netaporter.pws.automation.shared.pojos.Product;
import com.netaporter.pws.automation.shared.steps.purchasePath.BasePurchasePathStep;
import com.netaporter.test.client.product.pojos.SearchableProduct;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
/**
 * Created with IntelliJ IDEA.
 * User: christinakos
 * Date: 18/06/13
 * Time: 11:50
 * To change this template use File | Settings | File Templates.
 */
public class ShoppingSteps extends BasePurchasePathStep {


    @When("^I add (.*) in stock products directly to my bag$")
    public void iAddProductsDirectlyToBag(int productCount) throws InterruptedException {
        addProductsDirectlyToBag(productCount);
    }

    @When("^I add (.*) in stock products directly to my bag and save the stock$")
    public void iAddProductsDirectlyToBagAndSaveStock(int productCount) throws InterruptedException {
        addProductsDirectlyToBagAndSaveStock(productCount);
    }

    @Given("^I add a random product to my bag$")
    public void addRandomProductToBag() throws Throwable {
        int pid = Integer.parseInt(productDataAccess.getLegacyDBClient().findInStockProduct(webBot.getRegionEnum()).get("id").toString());
        addPidDirectlyToBag(pid);
    }

    @Given("^I add a product with a sales tax override for country code (.*) to my shopping bag$")
    public void addProductWithTaxOverride(String countryCode) throws InterruptedException {
        Map<String, Object> results = productDataAccess.getLegacyDBClient().findInStockProductWithCountrySalesTaxByCountry(webBot.getRegionEnum(), countryCode);
        Integer pid = (Integer) results.get("id");
        // we need a double here as other tests use double not float
        Double taxOverrideValue = new Double((Float)results.get("tax_rate"));
        assertNotNull(pid);
        scenarioSession.putData("taxCalculationValue", taxOverrideValue);
        addPidDirectlyToBag(pid);
    }

    @Given("^I add a product that has no sales tax override for country code (.*) to my shopping bag$")
    public void addProductWithoutTaxOverride(String countryCode) throws InterruptedException {
        Integer pid = productDataAccess.getLegacyDBClient().findInStockProductWithoutCountrySalesTaxByCountry(webBot.getRegionEnum(), countryCode);
        assertNotNull(pid);

        Double countryVatRate = productDataAccess.getLegacyDBClient().getVatRateByCountryCode(webBot.getRegionEnum(), countryCode);
        assertNotNull(countryVatRate);
        scenarioSession.putData("taxCalculationValue", countryVatRate);

        addPidDirectlyToBag(pid);
    }

    @Given("^I add a product from designer (.*) to my shopping bag$")
    public void add_product_from_designer(String designerName) throws InterruptedException {
        SearchableProduct searchableProduct = productDataAccess.getLegacyDBClient().getRandomAvailableSearchableProductsByDesigner(webBot.getRegionEnum(), 1, designerName).get(0);
        addPidDirectlyToBag(searchableProduct.getId());
    }

    @Given("^I add a product identified as (.*) to my bag$")
    public void addProductIdentifiedInSessionFromKey(String key) throws Throwable {
        Integer pid = (Integer) scenarioSession.getData(key);
        addPidDirectlyToBag(pid);
    }

    @Given("^I add a product priced (over|under) the basket threshold (.*) to my shopping bag$")
    public void addSpecificPricedPidToBag(String condition, BigDecimal thresholdValue) throws InterruptedException {
        addPidDirectlyToBag(productDataAccess.getLegacyDBClient().findInStockProductWithPriceThreshold(webBot.getRegionEnum(), thresholdValue, condition.equals("over")));
    }
}
