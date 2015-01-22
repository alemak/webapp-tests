package com.netaporter.pws.automation.shared.steps;

import com.netaporter.pws.automation.shared.pages.ICustomerReservationsPage;
import com.netaporter.pws.automation.shared.pages.IProductDetailsPage;
import com.netaporter.test.client.product.impl.HybridProductDataAccess;
import com.netaporter.test.client.product.pojos.CestaItem;
import com.netaporter.test.client.product.pojos.SimpleReservation;
import com.netaporter.test.utils.cucumber.steps.LegacyWebAppBaseStep;
import com.netaporter.test.utils.pages.IPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class CustomerReservationSteps extends LegacyWebAppBaseStep {

    public static final String OLD_STOCK_LEVELS = "oldStockLevels";
    public static final String RESERVED_SKU = "reserved-sku";

    /*@Autowired
    public LegacyWebAppDatabaseClient database;*/

    @Autowired
    public HybridProductDataAccess hybridProductDataAccess;

    @When("^I add a reserved product to my shopping bag$")
    public void I_add_a_reserved_product_to_my_shopping_bag() {
        ICustomerReservationsPage customerReservationsPage = (ICustomerReservationsPage)lookupPage("Customer Reservations");
        String sku = (String) scenarioSession.getData("reserved-sku");
        webBot.goToRegionalisedPage((IPage) customerReservationsPage);
        customerReservationsPage.addReservedItemToShoppingBag(sku);
    }

    @When("^I add a product to my shopping bag with the same sku as the reserved item$")
    public void I_add_a_product_to_my_shopping_bag_with_the_same_sku_as_the_reserved_item(){
        String sku = (String) scenarioSession.getData("reserved-sku");
        String pid = sku.split("-")[0];
        IProductDetailsPage productDetailsPage = (IProductDetailsPage) lookupPage("Product Details");
        productDetailsPage.goToProduct(pid);
        productDetailsPage.addIntoShoppingBag(sku);
    }

    @When("^I add a product identified as (.*) to my shopping bag with a different sku to the reserved item$")
    public void I_add_a_product_to_my_shopping_bag_with_a_different_sku_to_the_reserved_item(String productKey) {
        String reserved_sku = (String) scenarioSession.getData("reserved-sku");
        String newSku = "";
        int count = 0;

        do {
            Map<String, Object> productDetails = hybridProductDataAccess.getLegacyDBClient().findInStockProduct(webBot.getRegionEnum());
            newSku = (String) productDetails.get("sku");
            count++;
        }
        while (newSku.equals(reserved_sku) && count < 10);

        String pid = newSku.split("-")[0];
        IProductDetailsPage productDetailsPage = (IProductDetailsPage) lookupPage("Product Details");
        productDetailsPage.goToProduct(pid);
        productDetailsPage.addIntoShoppingBag(newSku);

        recordCurrentStockLevelInSession(newSku);
        scenarioSession.putData(productKey, newSku);
    }

    @Given("^I reserve a product with a quantity of (.*)$")
    public void I_reserve_a_product_with_the_given_quantity(Integer reservedQuantity) throws Throwable {
        I_reserve_a_product_with_the_given_quantity_and_key(RESERVED_SKU, reservedQuantity);
    }

    @Given("^I reserve a product identified as (.*) with a quantity of (.*)$")
    public void I_reserve_a_product_with_the_given_quantity_and_key(String reservedProductKey, Integer reservedQuantity) throws Throwable {
        String inStockSKUForCurrentRegion = hybridProductDataAccess.findInStockProduct(webBot.getSalesChannelByBrandAndRegion()).get("sku").toString();
        Integer stockLevelForProductSku = productDataAccess.getLegacyDBClient().getStockLevelForProductSku(webBot.getRegionEnum(), inStockSKUForCurrentRegion);

        int customerId = getRegisteredCustomerId();

        //reserve the item by decrementing the stock level and adding a new record in the simple_reservation table
        productDataAccess.getLegacyDBClient().makeReservation(webBot.getRegionEnum(), customerId, inStockSKUForCurrentRegion, reservedQuantity);
        productDataAccess.getLegacyDBClient().updateStockLevel(webBot.getRegionEnum(), inStockSKUForCurrentRegion, stockLevelForProductSku - reservedQuantity);

        recordCurrentStockLevelInSession(inStockSKUForCurrentRegion);
        scenarioSession.putData(reservedProductKey, inStockSKUForCurrentRegion);
    }


    @Given("^I verify that the added product is a reserved item$")
    public void I_verify_that_the_product_is_available_in_the_shopping_bag_as_a_reserved_item() throws Throwable {
        Integer customerId = getRegisteredCustomerId();
        String sku = (String) scenarioSession.getData(RESERVED_SKU);

        SimpleReservation customerReservation = productDataAccess.getLegacyDBClient().getCustomerReservation(webBot.getRegionEnum(), customerId, sku);
        int reservedQuantityFromReservationTable = customerReservation.getReservedQuantity();

        String basketId = getBasketIdFromCookie();
        List<CestaItem> basketItems = productDataAccess.getLegacyDBClient().findBasketItems(webBot.getRegionEnum(), basketId);
        //check that the basket is not empty before continuing
        assertNotNull("Basket was empty, the reserved product was not added to bag. Failing test",basketItems);

        CestaItem cestaItem = basketItems.get(0);

        int actualReservedQuantityInBasketItemTable = cestaItem.getReservedQuantity();
        int quantityInBasketItemTable = cestaItem.getQuantity();

        int expectedReservedQuantity = (quantityInBasketItemTable <= reservedQuantityFromReservationTable) ? quantityInBasketItemTable : reservedQuantityFromReservationTable;

        Assert.assertEquals(expectedReservedQuantity, actualReservedQuantityInBasketItemTable);
    }


    @Then("^The reserved product is redeemed$")
    public void The_reserved_product_is_redeemed() {
        String sku = (String) scenarioSession.getData(RESERVED_SKU);
        Integer customerId = getRegisteredCustomerId();

        SimpleReservation productReservation = productDataAccess.getLegacyDBClient().getCustomerReservation(webBot.getRegionEnum(), customerId, sku);
        int reservedQuantity = productReservation.getReservedQuantity();
        int redeemedQuantity = productReservation.getRedeemedQuantity();
        Assert.assertEquals(reservedQuantity, redeemedQuantity);
    }

    @Then("^The reserved product is redeemed (.*) times$")
    public void The_reserved_product_is_redeemed_so_many_times(Integer expectedRedeemedQuantity) {
        String sku = (String) scenarioSession.getData(RESERVED_SKU);
        Integer customerId = getRegisteredCustomerId();

        SimpleReservation productReservation = productDataAccess.getLegacyDBClient().getCustomerReservation(webBot.getRegionEnum(), customerId, sku);
        Integer redeemedQuantity = productReservation.getRedeemedQuantity();
        Assert.assertEquals(expectedRedeemedQuantity, redeemedQuantity);
    }

    @Then("^The reserved product is not redeemed$")
    public void The_reserved_product_is_not_redeemed() {
        String sku = (String) scenarioSession.getData(RESERVED_SKU);
        Integer customerId = getRegisteredCustomerId();

        SimpleReservation customerReservation = productDataAccess.getLegacyDBClient().getCustomerReservation(webBot.getRegionEnum(), customerId, sku);
        int redeemedQuantity = customerReservation.getRedeemedQuantity();
        Assert.assertEquals(0, redeemedQuantity);
    }

    @Then("^The stock level remains unchanged for the reserved item$")
    public void The_stock_level_remains_unchanged_for_the_reserved_item() {
        The_stock_level_is_decremented_by_the_specified_quantity_for_the_given_product(0, RESERVED_SKU);
    }

    @Then("^I verify that the stock level is decremented by (.*) for the reserved item$")
    public void The_stock_level_is_decremented_by_the_specified_quantity_for_the_reserved_item(int quantity) {
        The_stock_level_is_decremented_by_the_specified_quantity_for_the_given_product(quantity, RESERVED_SKU);
    }

    @Then("^I verify that the stock level is decremented by (.*) for product identified as (.*)$")
    public void The_stock_level_is_decremented_by_the_specified_quantity_for_the_given_product(int quantity, String productSku) {
        String sku = (String) scenarioSession.getData(productSku);

        Integer oldStockLevel = (Integer) ((Map)scenarioSession.getData(OLD_STOCK_LEVELS)).get(sku);
        Integer newStockLevel = productDataAccess.getLegacyDBClient().getStockLevelForProductSku(webBot.getRegionEnum(), sku);
        Assert.assertEquals(quantity, oldStockLevel - newStockLevel);
    }

    @Then("^I verify that the basket item quantity is (.*)$")
    public void I_verify_the_basket_item_quantity(Integer quantity) {
        String  basketId = getBasketIdFromCookie();

        List<CestaItem> basketItems = productDataAccess.getLegacyDBClient().findBasketItems(webBot.getRegionEnum(), basketId);
        CestaItem basketItem = basketItems.get(0);
        Assert.assertEquals(quantity, basketItem.getQuantity());
    }

    @Then("^I verify that the basket item reserved quantity column is (.*)$")
    public void I_verify_the_basket_item_reserved_quantity_column(Integer reservedQuantity) {
        String basketId = getBasketIdFromCookie();

        List<CestaItem> basketItems = productDataAccess.getLegacyDBClient().findBasketItems(webBot.getRegionEnum(), basketId);
        CestaItem basketItem = basketItems.get(0);
        Assert.assertEquals(reservedQuantity, basketItem.getReservedQuantity());
    }

    @When("^My Reservation expires$")
    public void My_reservation_expires() throws Throwable {
        String sku = (String) scenarioSession.getData(RESERVED_SKU);
        Integer customerId = getRegisteredCustomerId();

        SimpleReservation customerReservation = productDataAccess.getLegacyDBClient().getCustomerReservation(webBot.getRegionEnum(), customerId, sku);
        Integer reservedQuantity = customerReservation.getReservedQuantity();
        Integer redeemedQuantity = customerReservation.getRedeemedQuantity();

        Integer stockLevel = productDataAccess.getLegacyDBClient().getStockLevelForProductSku(webBot.getRegionEnum(), sku);
        productDataAccess.getLegacyDBClient().updateStockLevel(webBot.getRegionEnum(), sku, stockLevel + reservedQuantity - redeemedQuantity);
        productDataAccess.getLegacyDBClient().expireCustomerReservation(webBot.getRegionEnum(), customerId, sku);
        recordCurrentStockLevelInSession(sku);
    }

    @When("^My reserved item goes out of stock$")
    public void My_reserved_item_goes_out_of_stock() throws Throwable {
        String sku = (String) scenarioSession.getData(RESERVED_SKU);
        recordCurrentStockLevelInSession(sku);
        productDataAccess.getLegacyDBClient().updateStockLevel(webBot.getRegionEnum(), sku, 0);
    }

    @When("^I want to reset the stock level of my reserved item to its original level$")
    public void I_want_to_update_the_stock_level_of_my_reserved_item_to_its_original_level() throws Throwable {
        Map<String, Integer> oldStockLevels = (Map<String, Integer>) scenarioSession.getData(OLD_STOCK_LEVELS);

        if (oldStockLevels == null || oldStockLevels.isEmpty()) return;

        String reserved_sku = (String) scenarioSession.getData(RESERVED_SKU);
        int oldStockLevel = oldStockLevels.get(reserved_sku);
        productDataAccess.getLegacyDBClient().updateStockLevel(webBot.getRegionEnum(), reserved_sku, oldStockLevel);
    }

    private void recordCurrentStockLevelInSession(String sku){
        Map<String, Integer> oldStockLevels = (Map<String, Integer>) scenarioSession.getData(OLD_STOCK_LEVELS);

        if (oldStockLevels == null) oldStockLevels = new HashMap<String, Integer>();

        Integer stockLevel = productDataAccess.getLegacyDBClient().getStockLevelForProductSku(webBot.getRegionEnum(), sku);
        oldStockLevels.put(sku, stockLevel);
        scenarioSession.putData(OLD_STOCK_LEVELS, oldStockLevels);
    }

    private int getRegisteredCustomerId() {
        String emailAddress = (String) scenarioSession.getData(REGISTERED_EMAIL_ADDRESS);
        return productDataAccess.getLegacyDBClient().getCustomerIdByEmail(webBot.getRegionEnum(), emailAddress);
    }

}
