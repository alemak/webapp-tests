package com.netaporter.pws.automation.shared.steps;

import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.pws.automation.shared.apiclients.ProductServiceAPIClient;
import com.netaporter.pws.automation.shared.steps.purchasePath.BasePurchasePathStep;
import com.netaporter.test.client.product.dsl.ProductDsl;
import com.netaporter.test.client.product.pojos.CestaItem;
import com.netaporter.test.utils.cucumber.steps.LegacyWebAppBaseStep;
import com.netaporter.test.utils.enums.SalesChannelEnum;
import com.netaporter.test.utils.messaging.ProductCollectorMessageSender;
import com.netaporter.test.utils.network.NetworkUtil;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
@Component
public class ProductServiceSteps extends BasePurchasePathStep {

    private static final String OLD_STOCK_LEVELS = "oldStockLevels";

    @Autowired
    ProductServiceAPIClient productServiceClient;

    @Autowired
    ProductCollectorMessageSender pCollMessageSender;

    @Given("^My device identifier and user agent are recorded against my basket items$")
    public void checkDeviceIdentifierIsRecordedAgainstTheBasket() {
        String basketId = getBasketIdFromCookie();
        Assert.assertNotNull(basketId);

        String ipAddress = NetworkUtil.getFirstNonLoopbackAddress(true, false).getHostAddress();
        List<CestaItem> basketItems = productDataAccess.getLegacyDBClient().findBasketItems(webBot.getRegionEnum(), basketId.toString());
        Assert.assertTrue(basketItems.size() > 0);

        for(CestaItem item : basketItems) {
            Assert.assertTrue("IP address stored in database doesn't match address of client", item.getIpAddress().contains(ipAddress));
            Assert.assertEquals(webBot.getUserAgent(), item.getUserAgent());
        }

    }

    @Given("^All products in my basket go out of stock$")
    public void forceProductInBasketOutOfStock() {
        String basketId = getBasketIdFromCookie();
        Assert.assertNotNull(basketId);
        List<CestaItem> basketItems = productDataAccess.getLegacyDBClient().findBasketItems(webBot.getRegionEnum(), basketId.toString());

        for(CestaItem item : basketItems) {
            recordCurrentStockLevelInSession(item.getSku());
            productDataAccess.getLegacyDBClient().updateStockLevel(webBot.getRegionEnum(), item.getSku(), 0);
        }
    }

    /*  prashant.ramcharan@net-a-porter.com
        make sure to have a scenarioSession data item called [shoppingBagItems] of type Map<String, Integer>
     */
    @When("^I want all items in my shopping bag to go (in|out of) stock$")
    public void allItemsInShoppingBagGoesOutOfStock(String inOrOutOfStock){
//        Map<String, Integer> shoppingBagItems = (Map<String, Integer>) scenarioSession.getData(ITEMS_ADDED_TO_BAG);
        HashMap<String, Integer> shoppingBagItems = getSkuAndStockFromScenarioSession();
        // put all items out of stock
        if (inOrOutOfStock.equals("out of")){
            for (String itemSKU : shoppingBagItems.keySet()){
                productDataAccess.getLegacyDBClient().updateStockLevel(webBot.getRegionEnum(), itemSKU, 0);
            }
            return;
        }

        // put all items in stock / back to original stock level
        for (String itemSKU : shoppingBagItems.keySet()){
            productDataAccess.getLegacyDBClient().updateStockLevel(webBot.getRegionEnum(), itemSKU, shoppingBagItems.get(itemSKU));
        }
    }

    @Given("^I want to reset the stock level of my basket item to its original level$")
    public void I_want_to_update_the_stock_level_of_my_reserved_item_to_its_original_level() throws Throwable {
        Map<String, Integer> oldStockLevels = (Map<String, Integer>) scenarioSession.getData(OLD_STOCK_LEVELS);

        if (oldStockLevels == null || oldStockLevels.isEmpty()) return;

        Set<String> skus = oldStockLevels.keySet();

        for(String sku : skus)
            productDataAccess.getLegacyDBClient().updateStockLevel(webBot.getRegionEnum(), sku, oldStockLevels.get(sku));
    }

    private void recordCurrentStockLevelInSession(String sku){
        Map<String, Integer> oldStockLevels = (Map<String, Integer>) scenarioSession.getData(OLD_STOCK_LEVELS);

        if (oldStockLevels == null) oldStockLevels = new HashMap<String, Integer>();

        Integer stockLevel = productDataAccess.getLegacyDBClient().getStockLevelForProductSku(webBot.getRegionEnum(), sku);
        oldStockLevels.put(sku, stockLevel);
        scenarioSession.putData(OLD_STOCK_LEVELS, oldStockLevels);
    }


    @Given("^I have (\\d+) (IN_STOCK|LOW_STOCK|SOLD_OUT|ON_SALE) and visible (.+) skus? that I force to have similar stock in the db$")
    public void getInStockAndVisibleSkus(Integer numberOfSkus, ProductDsl.ProductAvailability productAvailability,
                                         ProductDsl.ProductCategory category) throws Throwable {

        List<String> previouslyFoundSkuList = Collections.EMPTY_LIST;
        if ((scenarioSession.getData("listOfSkus")) != null) {
            previouslyFoundSkuList = (List<String>) scenarioSession.getData("listOfSkus");
        }

        long start = System.currentTimeMillis();
        List<String> foundSkus = productServiceClient.getInStockAndVisibleSkus(webBot.getRegion(), numberOfSkus, productAvailability, category);
        System.out.println(">>>>>>>>>> TIME FOR " + numberOfSkus + " skus = " + (System.currentTimeMillis() - start) + "ms");

        if (( foundSkus.size()) != numberOfSkus) {
            int extraSkusNeeded = numberOfSkus - foundSkus.size();

            List<String> extraSkus = createSomeSkus(extraSkusNeeded, productAvailability, category, previouslyFoundSkuList);
            foundSkus.addAll(extraSkus);

            // Still not enough?
            if (foundSkus.size() != numberOfSkus) {
               Assert.fail("Not enough SKU's found matching criteria " + productAvailability + " and " + category + ".  Found " + foundSkus.size() + "/" + numberOfSkus);
            }

        }

        // To limit the impact of db and product service stock levels going out of sync, for testing robustness purposes
        // we're forcing the stock level in the db to a value suitable for the requested availability
        updateStockInDB(productAvailability, foundSkus);

        List<String> aggregateSkuList = new ArrayList<String>();
        aggregateSkuList.addAll(previouslyFoundSkuList);
        aggregateSkuList.addAll(foundSkus);

        scenarioSession.putData("listOfSkus", aggregateSkuList);

    }

    @Given("^I have a visible (.+) sku (IN_STOCK|LOW_STOCK|SOLD_OUT|ON_SALE|NOT_UPLOADED) on INTL and (IN_STOCK|LOW_STOCK|SOLD_OUT|ON_SALE|NOT_UPLOADED) on AM and (IN_STOCK|LOW_STOCK|SOLD_OUT|ON_SALE|NOT_UPLOADED) on APAC that I force to have similar stock in the db for the current channel$")
    public void getSkuWithAvailability(ProductDsl.ProductCategory category,
                                       ProductDsl.ProductAvailability intlProductAvailability,
                                       ProductDsl.ProductAvailability amProductAvailability,
                                       ProductDsl.ProductAvailability apacProductAvailability) throws Throwable {

        List<String> previouslyFoundSkuList = Collections.EMPTY_LIST;
        if ((scenarioSession.getData("listOfSkus")) != null) {
            previouslyFoundSkuList = (List<String>) scenarioSession.getData("listOfSkus");
        }

        ProductDsl.ProductChannelAvailability intlChanAvail = new ProductDsl.ProductChannelAvailability(SalesChannelEnum.NAP_INTL, intlProductAvailability);
        ProductDsl.ProductChannelAvailability amChanAvail = new ProductDsl.ProductChannelAvailability(SalesChannelEnum.NAP_AM, amProductAvailability);
        ProductDsl.ProductChannelAvailability apacChanAvail = new ProductDsl.ProductChannelAvailability(SalesChannelEnum.NAP_APAC, apacProductAvailability);

        long start = System.currentTimeMillis();
        String foundSku = productServiceClient.getSKUWithChannelAvailability(category, previouslyFoundSkuList, intlChanAvail, amChanAvail, apacChanAvail);
        System.out.println(">>> Time to retrieve SKU: " + (System.currentTimeMillis()-start) + "ms");

        if (webBot.getRegion().equals("APAC")) {
            updateStockInDB(apacProductAvailability, foundSku);
        } else if (webBot.getRegion().equals("AM")) {
            updateStockInDB(amProductAvailability, foundSku);
        } else {
            // Assume INTL
            updateStockInDB(intlProductAvailability, foundSku);
        }

        addToSession(previouslyFoundSkuList, foundSku);
    }

    @Given("^I have an (IN_STOCK|LOW_STOCK|SOLD_OUT|ON_SALE) and visible (.+) sku on all channels that I force to have similar stock in the db for the current channel$")
    public void getSkuWithAvailabilityOnAllChannels(ProductDsl.ProductAvailability productAvailability, ProductDsl.ProductCategory category) throws Throwable {
        getSkuWithAvailability(category, productAvailability, productAvailability, productAvailability);
    }

    private void updateStockInDB(ProductDsl.ProductAvailability productAvailability, String sku) {
        List<String> skus = new ArrayList<String>();
        skus.add(sku);
        updateStockInDB(productAvailability, skus);
    }

    private void addToSession(List<String> previouslyFoundSkuList, String foundSku) {
        List<String> aggregateSkuList = new ArrayList<String>();
        aggregateSkuList.addAll(previouslyFoundSkuList);
        aggregateSkuList.add(foundSku);

        scenarioSession.putData("listOfSkus", aggregateSkuList);
    }

    private void updateStockInDB(ProductDsl.ProductAvailability productAvailability, List<String> foundSkus) {
        // To limit the impact of db and product service stock levels going out of sync, for testing robustness purposes
        // we're forcing the stock level in the db to a value suitable for the requested availability
        if (!productAvailability.equals(ProductDsl.ProductAvailability.NOT_UPLOADED)) {
            int forcedStock = 5; // Default to 5 for in stock
            if (productAvailability.equals(ProductDsl.ProductAvailability.LOW_STOCK)) {
                forcedStock = 1;
            } else if (productAvailability.equals(ProductDsl.ProductAvailability.SOLD_OUT)) {
                forcedStock = 0;
            }

            productDataAccess.getLegacyDBClient().updateStockLevel(webBot.getRegionEnum(), foundSkus, forcedStock);
        }
    }

    private List<String> createSomeSkus(int extraSkusNeeded, ProductDsl.ProductAvailability productAvailability,
                                        ProductDsl.ProductCategory category, List<String> skusToIgnore) throws Throwable {

        if (productAvailability.equals(ProductDsl.ProductAvailability.ON_SALE)) {
            List<String> inStockSkus = productServiceClient.getInStockAndVisibleSkus(webBot.getRegion(), extraSkusNeeded,
                    ProductDsl.ProductAvailability.IN_STOCK, category, skusToIgnore);

            for (String nextSku : inStockSkus) {
                pCollMessageSender.sendDiscountNAPIntlMessage(BaseNapSteps.skuToPid(nextSku));
            }

            // Need to wait for these to become on Sale
            List<String> onSaleSkus = Collections.EMPTY_LIST;
            for (int i=0; i<20; i++) {
                onSaleSkus = productServiceClient.getInStockAndVisibleSkus(webBot.getRegion(), extraSkusNeeded,
                        productAvailability, category, skusToIgnore);
                if (onSaleSkus.size() == extraSkusNeeded) {
                    break;
                }
                System.out.println("[" + i + "] Waiting for skus.  Got " + onSaleSkus.size() + "/" + extraSkusNeeded);
                Thread.sleep(3000);
            }
            System.out.println("Got " + onSaleSkus.size() + "/" + extraSkusNeeded);
            return onSaleSkus;

        } else {
            System.out.println("TODO: implement createSomeSkus for: " + productAvailability + " and " + category);
        }

        return Collections.EMPTY_LIST;
    }

    @Given("^I have a list of in stock skus of one sku per product category$")
    public void getSkusAllCategories() throws Throwable {

        Map<ProductDsl.ProductCategory, String> skuMap = new LinkedHashMap<ProductDsl.ProductCategory, String>();
        List<String> skuList = new ArrayList<String>();
        if ((scenarioSession.getData("listOfSkusAndCategories")) != null) {
            skuMap = (LinkedHashMap<ProductDsl.ProductCategory, String>) scenarioSession.getData("listOfSkusAndCategories");
        }

        for (ProductDsl.ProductCategory category : ProductDsl.ProductCategory.values()) {
            List<String> foundSkus = productServiceClient.getInStockAndVisibleSkus(webBot.getRegion(), 1, ProductDsl.ProductAvailability.IN_STOCK, category);
            skuMap.put(category, foundSkus.get(0));
            skuList.add(foundSkus.get(0));
        }

        scenarioSession.putData("listOfSkusAndCategories",skuMap);
        scenarioSession.putData("listOfSkus", skuList);
    }

}
