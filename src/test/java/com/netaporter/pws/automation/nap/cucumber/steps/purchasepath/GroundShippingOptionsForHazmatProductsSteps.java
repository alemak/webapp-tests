package com.netaporter.pws.automation.nap.cucumber.steps.purchasepath;

import com.netaporter.pws.automation.shared.pojos.Product;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GroundShippingOptionsForHazmatProductsSteps extends ProductRestrictionsSteps {

    @Autowired
    private PurchasePathFlowSteps purchasePathFlowSteps;
    @Resource
    private Set<String> validAMGroundShippingSkus;
    @Resource
    private Set<String> validAMPremierShippingSkus;

    @Resource
    private Set<String> validAMNextDayShippingSkus;

    @After
    public void afterScenario() {
        removeHazmatRestriction();
    }

    @Given("^I have added a Hazmat product restricted for country code ([A-Za-z]{2}) to my shopping bag$")
    public void I_have_added_a_Hazmat_product_restricted_for_country_code_to_my_shopping_bag(String countryCode) throws Throwable {

        List<String> inStockSkus = getInStockSKUs(15);
        boolean isProductAddedSuccessfullyToBag;
        int count = 0;
        String inStockSku;
        do {
            inStockSku = inStockSkus.get(count);
            productDataAccess.getLegacyDBClient().restrictProductAsHazmat(webBot.getRegionEnum(), getPidFromSku(inStockSku), countryCode);
            isProductAddedSuccessfullyToBag = addProductDirectlyToShoppingBag(webBot.getBaseUrl(), System.getProperty("region"), inStockSku);
            count++;
        } while (!isProductAddedSuccessfullyToBag && count < inStockSkus.size());

        scenarioSession.putData(ITEMS_ADDED_TO_BAG, Arrays.asList(inStockSku));

        saveHazmatRestrictionInScenarioSession(new Product(null, inStockSku), countryCode);
    }

    @Then("^I should only be able to select Ground shipping methods$")
    public void I_should_only_be_able_to_select_Ground_shipping() {
        List<String> shippingMethods = purchasePathFlowPage.getAllAvailableShippingMethods();
        assertTrue("Found more than the expected skus on the Shipping Options page. Should only have ground skus.", validAMGroundShippingSkus.containsAll(shippingMethods));
    }

    @Then("^I should only be able to select Ground shipping methods including Premier$")
    public void I_should_only_be_able_to_select_Ground_and_Premier_shipping() {
        List<String> shippingMethods = purchasePathFlowPage.getAllAvailableShippingMethods();
        assertTrue("No shipping methods were found on the shipping options page", shippingMethods.size() > 0);

        boolean isAtLeastOneGroundShippingMethodIncluded = CollectionUtils.intersection(shippingMethods, validAMGroundShippingSkus).size() > 0;
        boolean isAtLeastOnePremierShippingMethodIncluded = CollectionUtils.intersection(shippingMethods, validAMPremierShippingSkus).size() > 0;
        boolean isNextDayShippingMethodIncluded = CollectionUtils.intersection(shippingMethods, validAMNextDayShippingSkus).size() > 0;

        assertTrue(isAtLeastOneGroundShippingMethodIncluded && isAtLeastOnePremierShippingMethodIncluded);
        assertFalse("Next Day Shipping option should not be available", isNextDayShippingMethodIncluded);
    }

    @Then("^the default shipping method should be a Ground shipping method$")
    public void the_default_shipping_method_should_be_a_Ground_shipping_method() {
        String savedShippingMethod = purchasePathFlowPage.getShippingMethodOnPaymentPage();

        purchasePathFlowSteps.I_choose_to_change_my_shipping_method();

        //shipping method from payment page contains price, need to strip it out
        String savedShippingMethodName = savedShippingMethod.replaceAll("[^A-Za-z ]","").trim();

        String sku = purchasePathFlowPage.getShippingMethodSKU(savedShippingMethodName);

        assertTrue("Shipping method present in the Payment Summary page did not have a ground-only sku: "+sku, validAMGroundShippingSkus.contains(sku));

    }

    @Then("^I should be able to select (at least|at most|exactly) ([0-9]*) shipping method(?:s)*")
    public void I_should_be_able_to_select_at_least_x_shipping_methods(String comparison, int x) {
        int numMethods = purchasePathFlowPage.getAllAvailableShippingMethods().size();
        if (comparison.equals("at least")) {
            assertTrue(numMethods >= x);
        } else if (comparison.equals("at most")) {
            assertTrue(numMethods <= x);
        } else {
            assertTrue(numMethods == x);
        }
    }
}