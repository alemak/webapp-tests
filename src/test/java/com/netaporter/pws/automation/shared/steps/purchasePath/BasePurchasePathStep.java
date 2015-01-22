package com.netaporter.pws.automation.shared.steps.purchasePath;

import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.pws.automation.nap.pages.NAPPurchasePathSignInPage;
import com.netaporter.pws.automation.shared.pages.purchasePath.*;
import com.netaporter.test.client.product.dsl.ProductDsl;
import com.netaporter.test.utils.messaging.ExporterMessageSender;
import com.netaporter.test.utils.pages.driver.WaitTime;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.jms.JMSException;
import java.util.*;

import static org.junit.Assert.assertTrue;

public abstract class BasePurchasePathStep extends BaseNapSteps {

    static Logger logger  = Logger.getLogger(BasePurchasePathStep.class);

    @Qualifier("NAPPurchasePathSignInPage")
    @Autowired
    protected NAPPurchasePathSignInPage napPurchasePathSignInPage;

    @Autowired
    protected PurchasePathShippingAddressPage purchasePathShippingAddressPage;

    @Autowired
    protected PurchasePathShippingMethodPage purchasePathShippingMethodPage;

    @Autowired
    protected PurchasePathBillingAddressPage purchasePathBillingAddressPage;

    @Autowired
    protected PurchasePathPaymentPage purchasePathPaymentPage;

    @Autowired
    protected PurchasePathConfirmationPage purchasePathConfirmationPage;

    @Autowired
    protected ExporterMessageSender exporterMessageSender;

    protected static final String ORDER_CONFIRMATION_NUMBER = "orderConfirmationNumber";
    protected static final String RETURNABLE_SKUS = "refundable skus purchased";

    /*
    Prashant.Ramcharan@net-a-porter.com
    Add a product(s) to your shopping bag without going through the DOM
    */
    private void addProductsDirectlyToShoppingBag(String baseUrl, String channel, Collection<String> SKUs) throws InterruptedException {
        for(String SKU : SKUs){
            webBot.getDriver().get(String.format("%s%s/api/basket/addsku/%s.json", baseUrl, channel, SKU).toLowerCase());
            webBot.waitExplicitly(WaitTime.ONE);
            System.out.println(SKU + (webBot.getPageSource().contains("Product added to basket") ? " was" : " was not") + " added successfully to the basket. \n");
        }
        webBot.getDriver().get(baseUrl);
    }

    protected boolean addProductDirectlyToShoppingBag(String baseUrl, String channel, String SKU) throws InterruptedException {
        webBot.getDriver().get(String.format("%s%s/api/basket/addsku/%s.json", baseUrl, channel, SKU).toLowerCase());
        webBot.waitExplicitly(WaitTime.ONE);
        logger.info("Trying to add sku "+ SKU +" directly to the shopping bag \n");
        return (webBot.getPageSource().contains("Product added to basket") || webBot.getPageSource().contains("PRODUCT_ADDED"));
    }


    protected void addProductsDirectlyToBagAndSaveStock(Integer productCount) throws InterruptedException {
        HashMap<String, Integer> skuAndStockMap;
        HashMap<String, Integer> skuAndStockAddedToBag = new HashMap<String, Integer>();
        for (int i = 1; i <= productCount; i++) {
            boolean isProductAddedToBagSuccessfully;
            String inStockSKU;
            do {
                skuAndStockMap = getInStockSkuAndStockMap(productCount);
                inStockSKU = skuAndStockMap.keySet().iterator().next();
                isProductAddedToBagSuccessfully = addProductDirectlyToShoppingBag(webBot.getBaseUrl(), System.getProperty("region"), inStockSKU);
                logger.info("SKU " + inStockSKU + (isProductAddedToBagSuccessfully ? " was " : " was not ") + "added to bag. \n");
            } while (!isProductAddedToBagSuccessfully);
            if (isProductAddedToBagSuccessfully) {
                skuAndStockAddedToBag.put(inStockSKU, skuAndStockMap.get(inStockSKU));
                scenarioSession.addCollectionData(ITEMS_ADDED_TO_BAG_WITH_STOCK, skuAndStockMap);
            }
        }
        assertTrue("Could not add "+productCount+" product(s) directly to bag",skuAndStockAddedToBag.keySet().size()==productCount);
    }

    protected void addProductsDirectlyToBag(int productCount) throws InterruptedException {
        ArrayList<String> itemsAddedToBag = new ArrayList<String>();
        //get 5 * the no. of skus so we maybe have enough in stock ones, stock info in solr and prodservAPI is unreliable
        List<String> inStockSKUs = getInStockSKUs(productCount * 5);

        attemptToAddSkusToBag(inStockSKUs, productCount);
    }

    //not all skus returned by Solr/PS_API are actually in stock, so we get more than enough and then try until the required number of skus is added to bag
    protected void attemptToAddSkusToBag(List<String> inStockSKUs, int numberOfItemsToAdd) throws InterruptedException {
        ArrayList<String> itemsAddedToBag = new ArrayList<String>();
        for (String inStockSKU : inStockSKUs) {
            boolean isProductAddedToBagSuccessfully = addProductDirectlyToShoppingBag(webBot.getBaseUrl(), System.getProperty("region"), inStockSKU);
            logger.info("SKU " + inStockSKU + (isProductAddedToBagSuccessfully ? " was " : " was not ") + "added to bag. \n");

            if (isProductAddedToBagSuccessfully) {
                itemsAddedToBag.add(inStockSKU);
                scenarioSession.addCollectionData(ITEMS_ADDED_TO_BAG, inStockSKU);
                if (itemsAddedToBag.size() == numberOfItemsToAdd)
                    return;
            }
        }
        throw new IllegalStateException("Could not add " + numberOfItemsToAdd + " product(s) directly to bag");
    }

    protected List<String> getInStockSKUs(Integer productCount) {
//        switch back to API call when it provides reliable stock info
//        return productDataAccess.getInStockSKUs(webBot.getSalesChannelByBrandAndRegion(), productCount);

        //make product category an argument?
        List<String> inStockSolrSkus = hybridProductDataAccess.Solr_findSkus(webBot.getSalesChannelByBrandAndRegion(), ProductDsl.ProductCategory.CLOTHING, ProductDsl.ProductAvailability.IN_STOCK, 50);
        Collections.shuffle(inStockSolrSkus);
        List<String> inStockSkus = new ArrayList<String>();
        for (int i=0;i<productCount;i++)
            inStockSkus.add(inStockSolrSkus.get(i));
        return inStockSkus;
    }

    private HashMap<String, Integer> getInStockSkuAndStockMap(int numberOfSkus) {
        List<String> inStockSKUforCurrentRegion = getInStockSKUs(numberOfSkus);
        HashMap<String, Integer> inStockSkuWithMatchingStock = new HashMap<String, Integer>();
        for (String sku : inStockSKUforCurrentRegion) {
            Integer stockLevelForProductSku = productDataAccess.getLegacyDBClient().getStockLevelForProductSku(webBot.getRegionEnum(), sku);
            inStockSkuWithMatchingStock.put(sku, stockLevelForProductSku);
        }
        return inStockSkuWithMatchingStock;
    }
    protected String getPidFromSku(String sku) {
        return sku.substring(0, sku.indexOf("-"));
    }


    protected ArrayList<String> getSkusFromScenarioSession() {
        ArrayList itemsFromSession = scenarioSession.getData(ITEMS_ADDED_TO_BAG);
        if (null==itemsFromSession)
            throw new IllegalStateException("Session variable ITEMS_ADDED_TO_BAG was null");
        return itemsFromSession;
    }

    protected ArrayList<String> getReturnableSkusFromScenarioSession() {
        ArrayList itemsFromSession = scenarioSession.getData(RETURNABLE_SKUS);
        if (null==itemsFromSession)
            throw new IllegalStateException("Session variable RETURNABLE_SKUS was null");
        return itemsFromSession;
    }

    protected HashMap<String, Integer> getSkuAndStockFromScenarioSession() {
        ArrayList skuAndStockMapFromSession = scenarioSession.getData(ITEMS_ADDED_TO_BAG_WITH_STOCK);
        if (null==skuAndStockMapFromSession)
            throw new IllegalStateException("Session variable ITEMS_ADDED_TO_BAG was null");
        if (skuAndStockMapFromSession.get(0) instanceof HashMap)
            return (HashMap<String, Integer>) skuAndStockMapFromSession.get(0);
        throw new IllegalStateException("Invalid data format in ITEMS_ADDED_TO_BAG_WITH_STOCK");
    }

    protected void addPidDirectlyToBag(Integer pid) throws InterruptedException {
        List<String> skusForPid = productDataAccess.getLegacyDBClient().getSkusForPid(webBot.getRegionEnum(), pid.toString());
        boolean isSkuAddedToBagSuccessfully;
        for (String sku : skusForPid) {
            if(productDataAccess.Solr_isPidOrSkuInStock(webBot.getSalesChannelByBrandAndRegion(), sku)) {
                isSkuAddedToBagSuccessfully = addProductDirectlyToShoppingBag(webBot.getBaseUrl(), System.getProperty("region"), sku);
                if (isSkuAddedToBagSuccessfully)
                    return;
            }
        }
        throw new IllegalStateException("Could not add any of the skus for pid "+pid+" to bag");
    }

    protected void dispatchSingleSkuOrder(String orderConfirmationNumber, String sku) {
        try {
            exporterMessageSender.sendSingleSkuOrderDispatchedMessage(orderConfirmationNumber, sku);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    protected void dispatchThreeSkuOrder(String orderConfirmationNumber, ArrayList<String> skuList) {
        try {
            exporterMessageSender.sendThreeSkuOrderDispatchedMessage(orderConfirmationNumber, skuList);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void addAnItemIntoShoppingBag() throws InterruptedException {
        addProductsDirectlyToBag(1);
    }
}