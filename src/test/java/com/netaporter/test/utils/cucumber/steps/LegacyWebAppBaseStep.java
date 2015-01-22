package com.netaporter.test.utils.cucumber.steps;

import com.netaporter.pws.automation.nap.pages.NAPProductDetailsPage;
import com.netaporter.pws.automation.shared.pages.IProductDetailsPage;
import com.netaporter.pws.automation.shared.pages.IProductListPage;
import com.netaporter.pws.automation.shared.pages.IWishlistV3Page;
import com.netaporter.pws.automation.shared.pages.ListingPageRegistry;
import com.netaporter.pws.automation.shared.pojos.Customer;
import com.netaporter.pws.automation.shared.pojos.Product;
import com.netaporter.test.client.product.impl.HybridProductDataAccess;
import com.netaporter.test.utils.enums.RegionEnum;
import com.netaporter.test.utils.enums.SalesChannelEnum;
import com.netaporter.test.utils.pages.IPageRegistryListener;
import com.netaporter.test.utils.pages.driver.WaitTime;
import com.netaporter.test.utils.pages.exceptions.PageElementNotFoundException;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: J.Christian@net-a-porter.com
 * Date: 23/03/2013
 * Time: 00:22
 * To change this template use File | Settings | File Templates.
 */

public abstract class LegacyWebAppBaseStep extends BaseStepWithWebDriver {
    static Logger logger  = Logger.getLogger(LegacyWebAppBaseStep.class);

    private static final String SHOPPING_BASKET_COOKIE_NAME = "shopping_basket";
    public static final String REGISTERED_EMAIL_ADDRESS = "registeredEmailAddress";

   /* @Autowired
    public LegacyWebAppDatabaseClient database;*/

    @Autowired
    public HybridProductDataAccess productDataAccess;

    @Autowired
    public Customer defaultCustomer;

    @Autowired
    public IPageRegistryListener listingPageRegistry;

    /*protected void setRegion(String regionName) {
        database.setRegion(regionName);
        //database.setDbName(getDatabaseNamePrefix() + webBot.getChannel());
        super.setRegion(regionName);
    }*/

    protected String getBasketIdFromCookie() {
        Cookie cookie = webBot.getCookie(SHOPPING_BASKET_COOKIE_NAME);

        return (cookie == null) ? null : cookie.getValue();
    }

    public IProductListPage lookupProductListPage(String pageName) {
        return ((ListingPageRegistry)getListingPageRegistry()).lookupProductListPage(pageName);
    }

    public IProductListPage goToAnyProductListPage() {
        return ((ListingPageRegistry)getListingPageRegistry()).anyProductListPage();
    }

    public IProductListPage anyMultipleSizedProductListPage() {
        return ((ListingPageRegistry)getListingPageRegistry()).anyMultipleSizedProductListPage();
    }

    public Product addAnyInStockItemFromAnyProductListPageIntoShoppingBag() {
        goToAnyProductListPage();
        return addAnyInStockItemFromCurrentProductListPageIntoShoppingBag();
    }

    public Product addAnyInStockItemFromCurrentProductListPageIntoShoppingBag() {
        Product product;
        IProductListPage plp = ((IProductListPage) webBot.getCurrentPage());

        //try no more than 10 products (30 for NAP)
        if(webBot.getBrand().equals("NAP")){
            product = Solr_checkStockAndAddToShoppingBag(plp, 30);
        }
        else{
            product = checkStockAndAddToShoppingBag(plp, 10);
        }
       if (product != null)
            return product;

        throw new RuntimeException("NO INSTOCK or NON-RESTRICTED PRODUCT_KEY FOUND ON GIVEN LISTING PAGE: " + webBot.getCurrentUrl());
    }

    public Product addAnyInStockItemFromProductListPageIntoShoppingBag(IProductListPage plp) {
       return addAnyInStockItemFromProductListPageIntoShoppingBag(plp, null);
    }

    public Product addAnyInStockItemFromProductListPageIntoShoppingBag(IProductListPage plp, String countryName) {
            Product product = checkStockAndAddToShoppingBag(plp);
            if(product!=null){
                return product;
            }
        throw new RuntimeException("NO INSTOCK or NON-RESTRICTED PRODUCT_KEY FOUND ON GIVEN LISTING PAGE: " + plp.getPageName());
    }


    protected List<String> getPidsOnCurrentListingPage(IProductListPage plp) {
        List<String> pids = plp.getAllProductsPids();
        Collections.shuffle(pids, new Random(new Date().getTime()));
        return pids;
    }

    private Product checkStockAndAddToShoppingBag(IProductListPage plp) {
       //Nap uses Solr to search for products
       if(webBot.getBrand().equals("NAP")){
           return Solr_checkStockAndAddToShoppingBag(plp, null);
       }
       else{
           return checkStockAndAddToShoppingBag(plp, null);
       }

    }

    private Product Solr_checkStockAndAddToShoppingBag(IProductListPage plp, Integer maxNumOfProductsToTry) {
        SalesChannelEnum channelEnum = webBot.getSalesChannelByBrandAndRegion();
        List<String> pids = getPidsOnCurrentListingPage(plp);
        int c = 0;
        for(String pid : pids) {
            if ((maxNumOfProductsToTry != null) && c++ >= maxNumOfProductsToTry) break;
            logger.debug("Checking in the DB if PID " + pid + " is in stock..");
            if (productDataAccess.Solr_isPidOrSkuInStock(channelEnum, pid) && !productDataAccess.getLegacyDBClient().isPidInShippingRestrictionsTable(RegionEnum.valueOf(webBot.getRegion()), pid)) {
                logger.debug("PID " + pid + "is in stock and unrestricted");
                IProductDetailsPage pdp = plp.gotoProductDetailsPage(pid);
                List<String> skus = pdp.getSKUs();
                for(String sku : skus) {
                    //TODO investigate if we can use the so_ prefix to get soldout skus directly without other db/solr queries
                    //sold out skus have so_ before the actual number, need to remove this
                    String formattedSku = sku.substring(sku.indexOf("_")+1);
                    logger.debug("Checking in the DB if SKU " + sku + " is in stock..");
                    if (productDataAccess.Solr_isPidOrSkuInStock(channelEnum, formattedSku)) {
                        logger.debug("SKU " + sku + " is in stock");
                        Product productAddedToBag = pdp.addIntoShoppingBag(sku);
                        if (productAddedToBag != null)
                            return productAddedToBag;
                    }
                    else logger.debug("SKU " +  sku +" out of stock");
                }
 /*
                // TODO: this reflects the old page logic, but maybe flawed.  Assumes db is up to date with stock
                // We've not found an in-stock SKU, even though the PID is apparently in-stock according to DB.
                // Failing fast rather than iterating over other PIDs
                // return null;

                // Notes: in our current usages, we don't want to fail fast. we could introduce a flag to indicate
                // whether we want to fail fast in the future
                System.out.println("Hidden products with pid: " + pid);
            }*/

//            if(productDataAccess.API_isPidInStock(SalesChannelEnum.valueOf(webBot.getChannel()), pid)){
//                IProductDetailsPage pdp = plp.gotoProductDetailsPage(pid);
//                List<String> skus = pdp.getSKUs();
//                for(String sku : skus) {
//                    if (productDataAccess.isSkuInStock(SalesChannelEnum.valueOf(webBot.getChannel()),sku)) {
//                        return pdp.addIntoShoppingBag(sku);
//                    }
//                }
            }
            else {
                logger.debug("PID " +  pid +" out of stock or restricted");
            }
        }
        return null;
    }
    private Product checkStockAndAddToShoppingBag(IProductListPage plp, Integer maxNumOfProductsToTry) {
        List<String> pids = getPidsOnCurrentListingPage(plp);
        int c = 0;
        for(String pid : pids) {
            if ((maxNumOfProductsToTry != null) && c++ >= maxNumOfProductsToTry) break;
            logger.debug("Checking in the DB if PID " + pid + " is in stock..");
            if (productDataAccess.getLegacyDBClient().isPidOrSkuInStock(RegionEnum.valueOf(webBot.getRegion()),pid)) {
                logger.debug("PID " + pid + "is in stock");
                IProductDetailsPage pdp = plp.gotoProductDetailsPage(pid);
                List<String> skus = pdp.getSKUs();
                for(String sku : skus) {
                    logger.debug("Checking in the DB if SKU " + sku + " is in stock..");
                    if (productDataAccess.getLegacyDBClient().isPidOrSkuInStock(RegionEnum.valueOf(webBot.getRegion()),sku)) {
                        logger.debug("SKU " + sku + "is in stock");
                        return pdp.addIntoShoppingBag(sku);
                    }
                    else logger.debug("SKU " +  sku +" out of stock");
                }
 /*
                // TODO: this reflects the old page logic, but maybe flawed.  Assumes db is up to date with stock
                // We've not found an in-stock SKU, even though the PID is apparently in-stock according to DB.
                // Failing fast rather than iterating over other PIDs
                // return null;

                // Notes: in our current usages, we don't want to fail fast. we could introduce a flag to indicate
                // whether we want to fail fast in the future
                System.out.println("Hidden products with pid: " + pid);
            }*/

//            if(productDataAccess.API_isPidInStock(SalesChannelEnum.valueOf(webBot.getChannel()), pid)){
//                IProductDetailsPage pdp = plp.gotoProductDetailsPage(pid);
//                List<String> skus = pdp.getSKUs();
//                for(String sku : skus) {
//                    if (productDataAccess.isSkuInStock(SalesChannelEnum.valueOf(webBot.getChannel()),sku)) {
//                        return pdp.addIntoShoppingBag(sku);
//                    }
//                }
            }
            else {
                logger.debug("PID " +  pid +" out of stock");
            }
        }
        return null;
    }


    public void addAnyLowInStockItemFromAnyProductListPageIntoWishList() throws Throwable {
        goToAnyProductListPage();
        IProductListPage plp = ((IProductListPage) webBot.getCurrentPage());
        addAnyLowInStockItemFromProductListPageIntoWishList(plp);
    }


    public void addAnyLowInStockItemFromProductListPageIntoWishList(IProductListPage plp) throws Throwable {

        List<String> pids = getPidsOnCurrentListingPage(plp);

        for(String pid : pids) {
            if (productDataAccess.getLegacyDBClient().isItemLowInStock(RegionEnum.valueOf(webBot.getRegion()),pid)){
                IProductDetailsPage pdp = plp.gotoProductDetailsPage(pid);

                List<String> skus = pdp.getSKUs();
                for(String sku : skus) {
                    if (productDataAccess.getLegacyDBClient().isPidOrSkuInStock(RegionEnum.valueOf(webBot.getRegion()),sku)) {
                        pdp.addIntoWishlist(sku);
                        return;
                    }
                }
            }
        }

    }

    public String getAnyInStockPIDFromProductListPage(IProductListPage plp) {
        List<String> pids = plp.getAllProductsPids();
        Collections.shuffle(pids);
        for(String pid : pids) {
            if (productDataAccess.Solr_isPidOrSkuInStock(webBot.getSalesChannelByBrandAndRegion(), pid)) {
                return pid;
            }
        }
        return null;
    }

    public void addAnyInStockItemFromAnyProductListPageIntoWishlist() {
        goToAnyProductListPage();

        IProductListPage plp = ((IProductListPage) webBot.getCurrentPage());
        List<String> pids = plp.getAllProductsPids();
        for(String pid : pids) {
            if (productDataAccess.getLegacyDBClient().isPidOrSkuInStock(RegionEnum.valueOf(webBot.getRegion()),pid)) {
                NAPProductDetailsPage pdp = (NAPProductDetailsPage) plp.gotoProductDetailsPage(pid);
                List<String> skus;
                try {
                    skus = pdp.getSKUs();
                } catch (PageElementNotFoundException e) {
                    //skipping this product if it is unavailable (webbot will not be able to find the sku element)
                    continue;
                }
                for (String sku : skus) {
                    if (productDataAccess.getLegacyDBClient().isPidOrSkuInStock(RegionEnum.valueOf(webBot.getRegion()),sku)) {
                        pdp.addIntoWishlist(sku);
                        return;
                    }
                }

                // TODO: this reflects the old page logic, but maybe flawed.  Assumes db is up to date with stock
                // We've not found an in-stock SKU, even though the PID is apparently in-stock according to DB.
                // Failing fast rather than iterating over other PIDs
                return;
            }
        }
    }

    protected boolean doesElementExist(By locator) {
        try {
            webBot.findElement(locator);
        } catch (PageElementNotFoundException e) {
            return false;
        }
        return true;
    }

    protected boolean doesElementExist(By locator, WaitTime waitTime) {
        try {
            webBot.findElement(locator,waitTime);
        } catch (PageElementNotFoundException e) {
            return false;
        }
        return true;
    }

    private IPageRegistryListener getListingPageRegistry() {
        return listingPageRegistry;
    }

    public IWishlistV3Page getWishlistPageForDevice() {
        return null;
    }
}
