package com.netaporter.pws.automation.nap.cucumber.steps.shopping;

import com.netaporter.pws.automation.nap.components.RegisterAccountForm;
import com.netaporter.pws.automation.nap.cucumber.steps.account.MyFavouriteDesignersSteps;
import com.netaporter.pws.automation.nap.cucumber.steps.registration.RegistrationSteps;
import com.netaporter.pws.automation.nap.pages.NAPProductListPage;
import com.netaporter.pws.automation.nap.pages.ShoppingBagPage;
import com.netaporter.pws.automation.nap.util.SortOrder;
import com.netaporter.pws.automation.shared.pojos.Product;
import com.netaporter.pws.automation.shared.steps.purchasePath.BasePurchasePathStep;
import com.netaporter.pws.automation.shared.utils.CardPaymentDetails;
import com.netaporter.pws.automation.shared.utils.CountryAndCurrencyUtil;
import com.netaporter.pws.automation.shared.utils.MoneyAssertion;
import com.netaporter.test.client.product.dsl.ProductDsl;
import com.netaporter.test.client.product.pojos.SearchableProduct;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NAPShoppingSteps extends BasePurchasePathStep {

    private static final String PRODUCT_CATEGORY = "productCategory";
    private static final String PRODUCT_SUB_CATEGORY = "productSubCategory";
    private static final String CAROUSEL_SKU_KEY = "sku";
    private static final String CLOTHING = "Clothing";
    private static final String ACCESSORIES = "Accessories";
    public static final String CANONICAL_PRODUCT_KEY = "canonical product";


    @Given("^I am on any Product Listing page$")
    public void I_am_on_any_product_listing_page() throws Throwable {
        goToAnyProductListPage();
    }

    @When("^I add any in stock product to the shopping bag$")
    public void I_add_any_in_stock_product_to_the_shopping_bag() throws Throwable {
        Product product = addAnyInStockItemFromCurrentProductListPageIntoShoppingBag();
        scenarioSession.putData(ITEMS_ADDED_TO_BAG, product.getSku());
    }

    @Then("^I should see that product in the shopping bag$")
    public void I_should_see_that_product_in_the_shopping_bag() throws Throwable {
        shoppingBagPage.go();
        Collection<String> skusFromScenarioSession = getSkusFromScenarioSession();
        assertSkusInShoppingBag(skusFromScenarioSession);
    }

    private void assertSkusInShoppingBag(Collection<String> skusFromSession) {
        for (String sku : skusFromSession) {
            assertTrue("Could not find product in shopping bag" + sku, shoppingBagPage.isSkuInShoppingBag(sku));
        }
    }

    @When("^I should have (.*) items in my shopping bag$")
    public void checkCountItemsInShoppingBag(int expectedItemsCount) {
        assertEquals(expectedItemsCount, shoppingBagPage.getNumberOfItemsInShoppingBag());
    }


    @Given("^I add on in stock Clothing product with price (aboveOrEqual|below|dontcare) \\D+{1}(\\d+) to the shopping bag$")
    public void I_add_on_in_stock_Clothing_product_with_price_aboveOrEqual_$_to_the_shopping_bag(NAPProductListPage.PriceRequirement priceRequirement, int threshold) throws Throwable {
        NAPProductListPage iProductListPage = (NAPProductListPage)lookupProductListPage(CLOTHING);
        iProductListPage.go();

        //need to sort in order to make sure there are products below $110 in the page
        if (priceRequirement.equals(NAPProductListPage.PriceRequirement.BELOW)){
            iProductListPage.sortBy(SortOrder.PRICE_LOW);
            }

        Product product = addAnyInStockProductToShoppingBag(iProductListPage, priceRequirement, threshold);

        addProductRelatedScenarioSessionData(product, CLOTHING, "");
    }

    @Given("^I add one in stock (.*) with price (aboveOrEqual|below|dontcare) \\D{1}(\\d+) to the shopping bag$")
    public void I_add_one_in_stock_Hats_with_price_below_$_to_the_shopping_bag(String subCategory, String priceRequirement, int threshold) throws Throwable {
        NAPProductListPage iProductListPage = (NAPProductListPage)lookupProductListPage(ACCESSORIES);

        iProductListPage.go(subCategory);
        iProductListPage.closeDontMissOutPopup();

        //added sorting to make sure the test can find cheap products on the first page
        iProductListPage.sortBy(SortOrder.PRICE_LOW);

        Product product = addAnyInStockProductToShoppingBag(iProductListPage, NAPProductListPage.PriceRequirement.valueOf(priceRequirement.toUpperCase()), threshold);

        addProductRelatedScenarioSessionData(product, ACCESSORIES, subCategory);
    }

    @Given("^I add a product to the shopping bag multiple times with different sizes$")
    public void I_add_a_product_to_the_shopping_bag_multiple_times_with_different__sizes() throws Throwable {
        //getInStockSKUs returns clothing skus which are assumed to have multiple sizes
        List<String> skusForPid;
        List<String> inStockSku = getInStockSKUs(5);
        int count = 0;
        do {
            String multipleSizedPid = getPidFromSku(inStockSku.get(count));
            skusForPid = productDataAccess.getLegacyDBClient().getSkusForPid(webBot.getRegionEnum(), multipleSizedPid);
            count++;
        } while (skusForPid.size() < 3 && count < 5);

        count = 0;
        for (String sku : skusForPid) {
            boolean isSkuInStock = false;
            try {
                isSkuInStock = productDataAccess.isSkuInStock(webBot.getSalesChannelByBrandAndRegion(), sku);
            } catch (NullPointerException ignored){
                }
            if (isSkuInStock) {
                boolean isSkuAddedToBagCorrectly = addProductDirectlyToShoppingBag(webBot.getBaseUrl(), System.getProperty("region"), sku);
                if (isSkuAddedToBagCorrectly) {
                    scenarioSession.addCollectionData(ITEMS_ADDED_TO_BAG, sku);
                    count++;
                }
            }
            if (count == 3)
                break;
        }
        assertTrue("Could only add " + count + " items to the bag instead of required number 3", count == 3);
    }

    @Then("^the Item total calculation is correct in the shopping bag$")
    public void the_Item_total_is_correct_in_the_shopping_bag() throws Throwable {
        List<String> unitPrices = shoppingBagPage.getUnitPrices();
        String itemTotal = shoppingBagPage.getItemTotal();

        MoneyAssertion.assertCalculatedSumIsTheSameAsGivenTotal(unitPrices, itemTotal);
    }

    @Given("^I navigate to a product details page$")
    public void I_navigate_to_a_product_details_page() throws Throwable {
        NAPProductListPage productListPage = (NAPProductListPage) goToAnyProductListPage();
        productDetailsPage.goToProduct(getAnyInStockPIDFromProductListPage(productListPage));
    }

    @Given("^I navigate to an instock PDP$")
    public void I_navigate_to_an_instock_product_detail_page() throws Throwable {
        List<String> inStockSkus = productDataAccess.Solr_findSkus(webBot.getSalesChannelByBrandAndRegion(), ProductDsl.ProductCategory.CLOTHING, ProductDsl.ProductAvailability.IN_STOCK, 1);
        String inStockSku = inStockSkus.get(0);
        Integer stockLevelForProductSku = productDataAccess.getLegacyDBClient().getStockLevelForProductSku(webBot.getRegionEnum(), inStockSku);
        HashMap<String, Integer> skuAndStockMap = new HashMap<String, Integer>();
        skuAndStockMap.put(inStockSku, stockLevelForProductSku);

        scenarioSession.addCollectionData(ITEMS_ADDED_TO_BAG_WITH_STOCK, skuAndStockMap);

        productDetailsPage.goToProduct(getPidFromSku(inStockSku));
    }

    @And("^I goto a canonical product detail page$")
    public void I_navigate_to_a_canonical_product_detail_page() throws Throwable {
        Product canonicalProduct = getCanonicalProduct();

        scenarioSession.putData(CANONICAL_PRODUCT_KEY, canonicalProduct);

        productDetailsPage.go(canonicalProduct);
    }

    public  Product getCanonicalProduct() {
        String canonicalPid = productDataAccess.getLegacyDBClient().getCanonicalPid(webBot.getRegionEnum());
        return new Product(null, canonicalPid);
    }

    @When("^I try to add the item into bag$")
    public void I_try_to_add_the_item_into_bag() throws Throwable {
        HashMap<String, Integer> skuAndStockFromScenarioSession = getSkuAndStockFromScenarioSession();

        productDetailsPage.addIntoShoppingBag(skuAndStockFromScenarioSession.keySet().iterator().next());
    }


    @Then("^I should see product sold out message$")
    public void I_should_see_product_sold_out_message() throws Throwable {
        assertTrue("Sold Out message is not displayed", productDetailsPage.isSoldOutErrorDisplayed());
    }

    private void addProductRelatedScenarioSessionData(Product product, String productCategory, String productSubCategory) {
        scenarioSession.putData(ITEMS_ADDED_TO_BAG, product.getSku());
        scenarioSession.putData(PRODUCT_CATEGORY, productCategory);
        scenarioSession.putData(PRODUCT_SUB_CATEGORY, productSubCategory);
    }

    @When("^I view my shopping bag$")
    public void I_view_my_shopping_bag() {
        shoppingBagPage.go();
    }

    @When("^I view my active shopping bag$")
    public void I_view_my_active_shopping_bag() {
        shoppingBagPage.viewShoppingBag();
    }

   // @Then("^The product shows out of stock in the Shopping Bag page$")
    //public void The_product_shows_out_of_stock_in_the_Shopping_Bag_page() {
     //   Assert.assertTrue(shoppingBagPage.isFirstProductInShoppingBagOutOfStock());
    //}

    @Then("^The Shopping Bag page is displayed$")
    public void The_Shopping_Bag_page_is_displayed() {
        ShoppingBagPage currentPage = (ShoppingBagPage) webBot.getCurrentPage();
        String pageTitle = currentPage.getPageName();
        assertTrue(pageTitle.startsWith("Shopping Bag"));
    }

    @When ("^I select add to bag in carousel product list$")
    public void I_select_add_to_bag_in_carousel_product_list() throws InterruptedException {
        //get all carousel items
        List<String> allVisibleCarouselPids = shoppingBagPage.getAllVisibleCarouselPids();
        //find in stock item in the carousel
        boolean isVisiblePidInStock = false;
        String inStockVisiblePid = null;
        for (String visibleCarouselPid : allVisibleCarouselPids) {
            if (productDataAccess.isPidOrSkuInStock(webBot.getSalesChannelByBrandAndRegion(), visibleCarouselPid)) {
                shoppingBagPage.addCarouselProductToBag(visibleCarouselPid);
                isVisiblePidInStock = true;
                inStockVisiblePid = visibleCarouselPid;
                break;
            }

        }
        //if we couldn't find in stock item in the first items in the carousel, click right arrow and check other pids
        int i = 1;
        while (!isVisiblePidInStock && i < 6) {
            shoppingBagPage.clickRightAccordionArrowInCarousel();
            String lastCarouselPid = shoppingBagPage.getCarouselProductPidByIndex(i);
            if (productDataAccess.isPidOrSkuInStock(webBot.getSalesChannelByBrandAndRegion(), lastCarouselPid)) {
                shoppingBagPage.clickAddToBagOnCarouselProductByIndex(i);
                isVisiblePidInStock = true;
                inStockVisiblePid = lastCarouselPid;
                break;
            }
            i++;
        }
        //fail if we still can't find an instock carousel product
        if (!isVisiblePidInStock)
            fail("Could not find in stock pid in the carousel");

        //return sku
        scenarioSession.putData(CAROUSEL_SKU_KEY, shoppingBagPage.getSkuFromPidInCarousel(inStockVisiblePid));
    }

    @When ("^I select add to bag in carousel product list without checking stock$")
    public void I_select_add_to_bag_in_carousel_product_list_without_checking_stock() throws InterruptedException {
        String sku = shoppingBagPage.addARandomCarouselProductToBag();
        scenarioSession.putData(CAROUSEL_SKU_KEY, sku);
    }

    @When ("^I click on any product inside carousel list$")
    public void I_click_on_any_product_inside_carousel_list(){
        String sku = shoppingBagPage.clickAnyProductInCarousel();
        scenarioSession.putData(CAROUSEL_SKU_KEY, sku);
    }

    @Then ("^I should see the promo parameter in the URL$")
    public void I_should_see_the_promo_parameter_in_the_URL(){
        String currentURL = webBot.getCurrentUrl();
        String sku = (String) scenarioSession.getData(CAROUSEL_SKU_KEY);
        String pid = sku.substring(0, sku.indexOf("-"));
        assertTrue("Current URL does not contain the promo parameter", currentURL.contains("cm_sp=basket--%20"+pid+"%20--"));
    }

    @Then("^I should see products in the upsell custom list$")
    public void I_should_see_products_in_the_upsell_custom_list()  {
        assertTrue("Upsell custom list is not visible", shoppingBagPage.isCarouselVisible());
    }

    @Then ("^I should see added carousel product in the shopping bag$")
    public void I_should_see_added_carousel_product_in_the_shopping_bag() throws Throwable {
        String sku = (String) scenarioSession.getData(CAROUSEL_SKU_KEY);
        assertTrue("Sku "+sku+" was not present in the shopping bag", shoppingBagPage.isSkuInShoppingBag(sku));
    }

    @And ("^I should be on the Shopping Bag page$")
    public void I_should_be_on_the_shopping_bag_page() throws Throwable {
        assertThat("Page name is not 'Shopping Bag'",shoppingBagPage.getPageTitleFromPage(), is(shoppingBagPage.getPageName().toUpperCase()));
    }

    @When("^I click move item 1 to wishlist$")
    public void clickMoveItemToWishlist() {
        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");
        String sku = skuList.get(0);
        shoppingBagPage.clickAddProductToWishlist(sku);
    }

    @When("^I login on the form for move to wishlist$")
    public void loginAgainForMoveToWishlist() {
        String emailAdressRegistered = (String) scenarioSession.getData(RegistrationSteps.REGISTERED_EMAIL_ADDRESS);
        String password = RegisterAccountForm.DEFAULT_REGISTRATION_PASSWORD;
        shoppingBagPage.loginOnMoveToWishlistForm(emailAdressRegistered, password);

        // Can take a little bit for this overlay to do it's thing
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }

    @Then("^I select the product from the shopping bag$")
    public void I_select_the_product_from_the_shopping_bag() throws Throwable {
        shoppingBagPage.clickProductInShoppingBag();
    }

    @Given("^I add a NAP product priced (over|under) the basket threshold (.*) to my shopping bag$")
    public void addSpecificPricedPidToBag(String condition, BigDecimal thresholdValue) throws InterruptedException {
        Integer pid = null;
        String countryCode = CountryAndCurrencyUtil.getCountryCodeForCountryName(getCurrentNapPage().getCountryName());

        do {
            if ("over".equals(condition)) {
                pid = productDataAccess.getLegacyDBClient().findInStockProductWithPriceThreshold(webBot.getRegionEnum(), thresholdValue, countryCode, true);
            }
            else if ("under".equals(condition)) {
                pid = productDataAccess.getLegacyDBClient().findInStockProductWithPriceThreshold(webBot.getRegionEnum(), thresholdValue, countryCode, false);
            }
            if (pid==null) {
                throw new RuntimeException("Could not find in stock pid priced "+condition+" "+thresholdValue+" in the webDB.");
            }
        } while (!productDataAccess.Solr_isPidOrSkuInStock(webBot.getSalesChannelByBrandAndRegion(), pid.toString()));

        List<String> skusForPid = productDataAccess.getLegacyDBClient().getSkusForPid(webBot.getRegionEnum(), pid.toString());
        Collections.shuffle(skusForPid);
        for (String sku : skusForPid) {
            boolean isSkuAddedSuccessfullyToBag = addProductDirectlyToShoppingBag(webBot.getBaseUrl(), System.getProperty("region"), sku);
            if (isSkuAddedSuccessfullyToBag)
                return;
        }
        throw new IllegalStateException("Could not add any of the skus for pid "+pid+" to bag, probably due to stock mismatch");
    }

    @Deprecated
    //use method that checks stock without going to PDP instead
    private Product addPidToBag(Integer pid) {
        Product product = null;

        productDetailsPage.goToProduct(pid.toString());
        assertThat(webBot.getCurrentUrl(), containsString("product"));
        List<String> skus = productDetailsPage.getSKUs();
        //if we weren't able to get skus from the PDP we'll just skip this product, it was probably a sold out page
        if (skus.size()==0)
            return null;
        for(String sku:skus) {
            String formattedSku = sku.substring(sku.indexOf("_")+1);
            if (productDataAccess.Solr_isPidOrSkuInStock(webBot.getSalesChannelByBrandAndRegion(), formattedSku)) {
                product = productDetailsPage.addIntoShoppingBag(sku);
                if (product!=null) {
                    return product;
                }
            }
        }
        return null;
    }

    @Given("^I add a NAP product from designer (.*) to my shopping bag$")
    public void add_product_from_designer(String designerName) throws InterruptedException {
        //get 10 products from the designer, and try to add their skus until one is actually added to bag
        List<SearchableProduct> searchableProducts = productDataAccess.getLegacyDBClient().getRandomAvailableSearchableProductsByDesigner(webBot.getRegionEnum(), 10, designerName);
        Collections.shuffle(searchableProducts);
        boolean isSkuAddedSuccessfullyToBag;
        int productCounter;
        for (productCounter=0;productCounter<10;productCounter++) {
            List<String> skuList = productDataAccess.getLegacyDBClient().getSkusForPid(webBot.getRegionEnum(), searchableProducts.get(productCounter).getId().toString());
            for (String sku : skuList) {
                boolean isSkuInStock = false;
                try {
                    isSkuInStock = productDataAccess.isSkuInStock(webBot.getSalesChannelByBrandAndRegion(), sku);
                } catch (NullPointerException ignored) {
                }
                if (isSkuInStock) {
                    isSkuAddedSuccessfullyToBag = addProductDirectlyToShoppingBag(webBot.getBaseUrl(), System.getProperty("region"), sku);
                    if (isSkuAddedSuccessfullyToBag)
                        return;
                }
            }
        }
        throw new IllegalStateException("Could not add a product by designer "+designerName+" to bag.");
    }

    @When("^I click the NAP shopping bag icon$")
    public void I_click_the_NAP_shopping_bag_icon() throws Throwable {
        getCurrentNapPage().clickShoppingBagIconResp();
    }

    @And("^I remove product from my shopping bag$")
    public void removeProduct() {
        String sku = getSkusFromScenarioSession().get(0);
        shoppingBagPage.removeProductFromBag(sku);
    }

    @Then("^The shopping bag link icon displays the number of items$")
    public void The_shopping_bag_link_icon_displays_the_number_of_items() throws Throwable {
        Collection<String> skusFromScenarioSession = getSkusFromScenarioSession();
        int itemNumber = skusFromScenarioSession.size();
        assertTrue(String.valueOf(itemNumber).equals(shoppingBagPage.getBasketCountNumber()));
    }

    @Given("^I have successfully purchased an item$")
    public void I_have_successfully_purchased_an_item() throws Throwable {
        addAnItemIntoShoppingBag();
        goToShoppingBagAndPurchaseAddedItems();
    }

    private void goToShoppingBagAndPurchaseAddedItems() {
        shoppingBagPage.go();
        shoppingBagPage.clickProcceedToPurchase();
        purchasePathFlowPage.checkoutInSignedInState();
        purchasePathFlowPage.clickProceedToPurchaseFromShippingOptionPage();
        purchasePathFlowPage.purchase(CardPaymentDetails.VISA_CREDIT, true);

        scenarioSession.putData(ORDER_CONFIRMATION_NUMBER, purchasePathFlowPage.getOrderConfirmationNumber());
        scenarioSession.putData(MyFavouriteDesignersSteps.SELECTED_FAVOURITE_DESIGNER_KEY, purchasePathFlowPage.getDesignerNamesFromConfirmationPage());
    }
}