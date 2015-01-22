package com.netaporter.pws.automation.nap.cucumber.steps.wishlistv3;

import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.pws.automation.shared.apiclients.ProductServiceAPIClient;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.Price;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.WishlistV3Product;
import com.netaporter.test.client.product.ProductDataAccessInterface;
import com.netaporter.test.client.product.dsl.ProductDsl;
import cucumber.api.java.en.And;
import org.openqa.selenium.WebElement;
import static org.junit.Assert.assertTrue;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: a.michael@london.net-a-porter.com
 * Date: 27/06/2013
 * Time: 11:17AM
 * (C) DevilRacing666
 */
public class AddItemsSteps extends BaseNapSteps {

    @Autowired
    ProductDataAccessInterface dataaccess;

    @Autowired
    ProductServiceAPIClient productServiceAPIClient;

    @And("^I have 2 in stock skus from the same pid$")
    public void getTwoSkusFromOnePid() throws Throwable {
        List<String> skuList = new ArrayList<String>();
        if ((scenarioSession.getData("listOfSkus")) != null) {
            skuList = (List<String>) scenarioSession.getData("listOfSkus");
        }
        List<String> foundSkus = productServiceAPIClient.getInStockAndVisibleSkus(webBot.getRegion(), 1, ProductDsl.ProductAvailability.IN_STOCK,ProductDsl.ProductCategory.LINGERIE);
        skuList.addAll(foundSkus);
        scenarioSession.putData("listOfSkus",skuList);

        String pid = skuList.get(0).split("-")[0];
        productDetailsPage.goToProduct(pid);

        List<WebElement> sizeOptions = wishListV3Page.getSizeElementsFromPage();

        for ( WebElement sizeOption : sizeOptions ) {
            if ( (!(sizeOption.getText().contains("sold") || sizeOption.getText().contains("Choose")))
                    && (!(sizeOption.getAttribute("value").contains(skuList.get(0))))) {
                skuList.add(sizeOption.getAttribute("value"));
            }
        }
        //skuList = Lists.reverse(skuList);
        scenarioSession.putData("listOfSkus",skuList);
        System.out.println(skuList+"\n");
    }

    @And("^I wait (\\d+) seconds?$")
    public void waitSomeSeconds(Integer seconds) throws Throwable {
        System.out.println("############  PAUSED FOR "+seconds+" SECONDS  ############");
        Thread.sleep(seconds*1000);
    }

    @And("^I store the details of products? (\\d+) to (\\d+) from the https product page$")
    public void getProductDetailsFromHttpProductPage(Integer from, Integer to) throws Throwable {
        getProductDetailsFromProductPage(from, to, true, false);
    }

    @And("^I store the details of products? (\\d+) to (\\d+) from the product page$")
    public void getProductDetailsFromHttpsProductPage(Integer from, Integer to) throws Throwable {
        getProductDetailsFromProductPage(from, to, false, false);
    }

    @And("^I go to the product page for item (\\d+)$")
    public void goToProductDetailsPageForProduct(Integer product) throws Throwable {
        List<WishlistV3Product> singleWishlistProducts = new ArrayList();
        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");

        String pid = skuToPid(skuList.get(product-1));
        productDetailsPage.goToProduct(pid);

    }

    @And("^I store the details of products? (\\d+) to (\\d+) from the product page including the product name$")
    public void getProductDetailsFromHttpsProductPageIncludingName(Integer from, Integer to) throws Throwable {
        getProductDetailsFromProductPage(from, to, false, true);
    }

    @And("^I store the details of all my items from the product pages$")
    public void getProductDetailsOfAllItemsFromHttpsProductPage() throws Throwable {

        Map<ProductDsl.ProductCategory, String> skuMap = (LinkedHashMap<ProductDsl.ProductCategory, String>) scenarioSession.getData("listOfSkusAndCategories");
        getProductDetailsFromProductPage(1, skuMap.size(), true, false);
    }

    private void getProductDetailsFromProductPage(Integer from, Integer to, Boolean https, Boolean includeProductName) throws Throwable {
        List<WishlistV3Product> singleWishlistProducts = new ArrayList();
        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");

        if (scenarioSession.getData("singleWishlistProducts") != null ) {
            singleWishlistProducts = (List<WishlistV3Product>) scenarioSession.getData("singleWishlistProducts");
        }

        Map<String, WishlistV3Product> pidToProductMap = new HashMap<String, WishlistV3Product>();
        Map<String, Map<String,String>> pidToSizeInfoMap = new HashMap<String, Map<String, String>>();

        for (Integer i = from - 1; i <= to - 1; i = i + 1 ) {
            String sku = skuList.get(i);

            WishlistV3Product wlproduct;
            String pid = sku.split("-")[0];
            String currentUrl = webBot.getCurrentUrl();
//            System.out.println("\n\nHTTPS?="+https+"   CURRENTURL="+currentUrl);

            if (pidToProductMap.containsKey(pid)) {
                // Already been to this page.  Clone the result
                wlproduct = pidToProductMap.get(pid).clone();

                // clear the size info for this sku
                wlproduct.setSize(null);
            } else {
                // First time we've encountered this PID
                wlproduct = new WishlistV3Product();

                int waitForPage=0;
                while (waitForPage < 10) {
                    if (https) {
                        if (currentUrl.startsWith("http:") || !currentUrl.endsWith("/product/" + pid)) {
                            productDetailsPage.goToProduct(pid);
                        }
                    } else {
                        if (currentUrl.startsWith("https:") || !currentUrl.endsWith("/product/" + pid)) {
                            productDetailsPage.goToProductHTTP(pid);
                        }
                    }

                    waitForPage++;

                    if(!iAmOnThe404ErrorPage() || currentUrl.endsWith("/product/" + pid)) {
                        waitForPage = 20;
                    }

                }
                if (!(waitForPage == 20)) {
                    assertTrue("Not on the pid page for "+pid+".  URL is "+webBot.getCurrentUrl(), !iAmOnThe404ErrorPage());
                    assertTrue("Not on the pid page for "+pid+".  URL is "+webBot.getCurrentUrl(), currentUrl.endsWith("/product/" + pid));
                }

                if (productDetailsPage.viewProductDetailsLinkIsVisible()) {
                    productDetailsPage.clickViewProductDetailLink();
                }

                wlproduct.setPid(Integer.parseInt(pid));
                wlproduct.setDesignerName(productDetailsPage.getDesignerName());

                if (includeProductName) {
                    wlproduct.setProductName(productDetailsPage.getProductName());
                }

                wlproduct.setPriceString(productDetailsPage.getListedPriceString());
                String currencySymbol = "" + wlproduct.getPriceString().replaceAll("[0-9a-zA-Z\\.,]", "").trim().charAt(0);

                if (wlproduct.getPriceString().contains("Now")) {
                    // Item discounted

                    // Store the discounted price
                    String discountedPriceString = productDetailsPage.getListedDiscountedPrice().replace(",", "");
                    Price discountedPrice = new Price();
                    discountedPrice.setCurrencySymbol(currencySymbol);
                    discountedPrice.setValue(Double.parseDouble(discountedPriceString.replace(currencySymbol, "")));
                    wlproduct.setDiscountedPrice(discountedPrice);

                    // store the original price
                    String originalPriceString = productDetailsPage.getListedOriginalPrice().replace(",", "");
                    Price originalPrice = new Price();
                    originalPrice.setCurrencySymbol(currencySymbol);
                    originalPrice.setValue(Double.parseDouble(originalPriceString.replace(currencySymbol, "")));
                    wlproduct.setOriginalPrice(originalPrice);

                    // Override to match the way W/L presents this
                    wlproduct.setPriceString(productDetailsPage.getListedOriginalPrice() + " " + productDetailsPage.getListedDiscountedPrice());

                } else {
                    // Item not discounted

                    // store the current price as the original price
                    String currentPriceString = productDetailsPage.getListedPriceString().replace(",", "");
                    Price currentPrice = new Price();
                    currentPrice.setCurrencySymbol(currencySymbol);
                    currentPrice.setValue(Double.parseDouble(currentPriceString.replace(currencySymbol, "")));
                    wlproduct.setOriginalPrice(currentPrice);
                }

                pidToSizeInfoMap.put(pid, productDetailsPage.getAllSizeInfo());
                pidToProductMap.put(pid, wlproduct);
            }

            updateSizeInfo(wlproduct, sku, pidToSizeInfoMap.get(pid));

            scenarioSession.putData("productDescription", productDetailsPage.getProductDescription());
            System.out.println("Storing details of sku "+sku+" - "+(i+1)+"/"+to);
            singleWishlistProducts.add(wlproduct);
        }
        scenarioSession.putData("singleWishlistProducts", singleWishlistProducts);
    }

    private void updateSizeInfo(WishlistV3Product wlproduct, String sku, Map<String, String> skuToSizeMap) {
        if (skuToSizeMap != null) {
            String prodDetailsSize = null;

            for (Map.Entry<String, String> entry : skuToSizeMap.entrySet())
            {
                if (entry.getKey().contains(sku)) {
                    prodDetailsSize = entry.getValue();
                }
            }

            if (prodDetailsSize == null) {
                wlproduct.setSize(null);
            } else if (prodDetailsSize.equals(sku)) {
                wlproduct.setSize("");
            } else if (prodDetailsSize.equals("One Size") || prodDetailsSize.equals("onesize")
                       || prodDetailsSize.equals("One size")) {
                wlproduct.setSize("");
            } else {
                // Removing spaces due to inconsistencies across site
                wlproduct.setSize(prodDetailsSize.split(" -")[0].trim().replace(" ", ""));
            }
        }
    }


}
