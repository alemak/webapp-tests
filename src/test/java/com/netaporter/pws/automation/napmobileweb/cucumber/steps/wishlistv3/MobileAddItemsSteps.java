package com.netaporter.pws.automation.napmobileweb.cucumber.steps.wishlistv3;

import com.netaporter.pws.automation.napmobileweb.cucumber.steps.BaseMobileNapSteps;
import com.netaporter.pws.automation.shared.apiclients.ProductServiceAPIClient;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.Price;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.WishlistV3Product;
import com.netaporter.test.client.product.ProductDataAccessInterface;
import com.netaporter.test.client.product.dsl.ProductDsl;
import cucumber.api.java.en.And;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: a.michael@london.net-a-porter.com
 * Date: 27/06/2013
 * Time: 11:17AM
 * (C) DevilRacing666
 */
public class MobileAddItemsSteps extends BaseMobileNapSteps {

    @Autowired
    ProductDataAccessInterface dataaccess;

    @Autowired
    ProductServiceAPIClient productServiceAPIClient;

    public static final String SINGLE_SIZE = "One size";

    @And("^I have 2 in stock skus from the same pid on the mobile product page$")
    public void getTwoSkusFromOnePid() throws Throwable {
        List<String> skuList = new ArrayList<String>();
        if ((scenarioSession.getData("listOfSkus")) != null) {
            skuList = (List<String>) scenarioSession.getData("listOfSkus");
        }

        List<String> foundSkus = productServiceAPIClient.getInStockAndVisibleSkus(webBot.getRegion(), 1, ProductDsl.ProductAvailability.IN_STOCK,ProductDsl.ProductCategory.LINGERIE);
        skuList.addAll(foundSkus);

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


    @And("^I store the details of products? (\\d+) to (\\d+) from the http mobile product page$")
    public void getProductDetailsFromHttpProductPage(Integer from, Integer to) throws Throwable {
        getProductDetailsFromMobileProductPage(from, to, false);
    }

    @And("^I store the details of products? (\\d+) to (\\d+) from the mobile product page$")
    public void getProductDetailsFromHttpsProductPage(Integer from, Integer to) throws Throwable {
        getProductDetailsFromMobileProductPage(from, to, true);
    }

    @And("^I should see a 'Please select a size' error message on the mobile product details page$")
    public void assertWishlistSelectSizeError() throws Throwable {
        assertThat("", productDetailsPage.getWishlistSelectSizeError(), equalTo("Please select a size"));
    }


    private void getProductDetailsFromMobileProductPage(Integer from, Integer to, Boolean https) throws Throwable {

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
            String currentUrl = webBot.getCurrentUrl();
            String pid = sku.split("-")[0];

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
                        if ( (currentUrl.startsWith("http:") || !currentUrl.endsWith("/product/" + pid)) && !proxyError()) {
                            productDetailsPage.goToProduct(pid);
                        }
                    } else {
                        if ( (currentUrl.startsWith("https:") || !currentUrl.endsWith("/product/" + pid)) && !proxyError()) {
                            productDetailsPage.goToProductHTTP(pid);
                        }
                    }
                    waitForPage++;

                    if(!iAmOnThe404ErrorPage()) {
                        waitForPage = 20;
                    }

                }
                if (!(waitForPage == 20)) {
                    assertTrue("Not on the pid page for "+pid+".  URL is "+webBot.getCurrentUrl(), !iAmOnThe404ErrorPage());
                }

                wlproduct.setPid(Integer.parseInt(productDetailsPage.getPid()));
                wlproduct.setDesignerName(productDetailsPage.getDesignerName());

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

            System.out.println("Storing details of sku " + sku);
            singleWishlistProducts.add(wlproduct);
        }
        scenarioSession.putData("singleWishlistProducts", singleWishlistProducts);
    }

    private void updateSizeInfo(WishlistV3Product wlproduct, String sku, Map<String, String> skuToSizeMap) {
        if (skuToSizeMap != null) {
            String prodDetailsSize = skuToSizeMap.get(sku);
            if (prodDetailsSize == null) {
                wlproduct.setSize(null);
            } else if ((prodDetailsSize.equals(sku))
                    || prodDetailsSize.equalsIgnoreCase("One size")) {
                wlproduct.setSize("");
            } else {
                // Removing spaces due to inconsistencies across site
                wlproduct.setSize(prodDetailsSize.replace(" ", ""));
            }
        }
    }


    @And("^I store the details of all my items from the mobile product pages$")
    public void getProductDetailsOfAllItemsFromHttpsProductPage() throws Throwable {

        Map<ProductDsl.ProductCategory, String> skuMap = (LinkedHashMap<ProductDsl.ProductCategory, String>) scenarioSession.getData("listOfSkusAndCategories");
        getProductDetailsFromMobileProductPage(1, skuMap.size(), true);
    }



}
