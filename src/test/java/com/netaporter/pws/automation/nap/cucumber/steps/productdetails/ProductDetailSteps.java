package com.netaporter.pws.automation.nap.cucumber.steps.productdetails;

import com.netaporter.productservice.api.APIClientUtils.pojos.ProductDetails;
import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.pws.automation.nap.cucumber.steps.shopping.NAPShoppingSteps;
import com.netaporter.pws.automation.shared.pojos.Product;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.MenuItem;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.NavMenu;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.WishlistV3Product;
import com.netaporter.pws.automation.shared.utils.WaitUtil;
import com.netaporter.test.client.product.dsl.ProductDsl;
import com.netaporter.test.client.product.pojos.SearchableProduct;
import com.netaporter.test.utils.enums.SalesChannelEnum;
import com.netaporter.test.utils.enums.WebsiteEnum;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;


/**
 * Created with IntelliJ IDEA.
 * User: cucumber
 * Date: 26/06/13
 * Time: 13:10
 */
public class ProductDetailSteps extends BaseNapSteps {

    private static final String VOUCHER_TYPE = "printed or virtual gift voucher";
    private static final String SELECTED_VOUCHER_PRICE = "printed or virtual gift price";
    public static final String PRODUCT_KEY = "product";
    private static final String GOOGLE_PID = "active google pid";
    private static final String COUNTRY_COOKIE_KEY = "country_iso";
    private static final String LANGUAGE_COOKIE_KEY = "lang_iso";
    private static final String DEVICE_COOKIE_KEY = "deviceType";

    @Then("^the locale links are displayed$")
    public void the_locale_links_are_displayed() throws Throwable {
        assertTrue(productDetailsPage.isTheLocaleLinkDisplayed());
     }

    @Then("^the canonical link is displayed$")
    public void the_canonical_link_is_displayed() throws Throwable {
        final Product canonicalProduct = (Product) scenarioSession.getData(NAPShoppingSteps.CANONICAL_PRODUCT_KEY);
        assertTrue("Canonical is not found for pid: "+ canonicalProduct.getPid(), productDetailsPage.isTheCanonicalLinkDisplayed());
    }

    @When("^I click on the view full size image link$")
    public void I_click_on_the_view_full_size_image_links() throws Throwable{
        productDetailsPage.clickViewFullSizeImage();
   }

    @Then("^the full size image overlay appears$")
    public void the_full_size_image_overlay_appears() throws Throwable{
        assertNotNull("Full size image overlay was not displayed", productDetailsPage.getFullSizeImageOverlay());
    }

    @And("^I select size of the product$")
    public void I_select_size_of_the_product() throws Throwable {
        productDetailsPage.clickSizeDropdown();
    }

    @Then("^the (Size & Fit|Editor's Notes) accordion is (open|closed)$")
    public void the_sizeandfit_accordion_is_open(String accordionName, String state) throws Throwable {
        assertThat(productDetailsPage.getAccordionClass(accordionName), containsString(state));
    }

    @And("^I select sku (\\d+)'s size and click the new Add to Wish List button$")
    public void clickAddWithSizeSelected(int itemNumber) throws Throwable {
        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");
        productDetailsPage.selectSKU(skuList.get(itemNumber-1));
        Thread.sleep(1500);
        productDetailsPage.clickAddToWishList();
    }

    @And("^I click the new Add to Wish List button$")
    public void clickAddToWishlist() throws Throwable {
        productDetailsPage.clickAddToWishList();
    }

    @And("^sku number (\\d+)'s size has been pre chosen in the Wish List popup$")
    public void checkPreSelectedSize(Integer itemNumber) throws Throwable {
        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");
        String sku = skuList.get(itemNumber - 1);
        assertEquals("Sku has not been pre-selected: ", sku, productDetailsPage.checkPopupWhichSkuSelected(sku));
    }

    @And("^I select the size for sku (\\d+) in the new Add to Wish List popup$")
    public void selectSkuInPopup(int itemNumber) throws Throwable {
        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");
        String sku = skuList.get(itemNumber - 1);
        productDetailsPage.selectSizeBySkuInWishlistPopup(sku);
        Thread.sleep(1000);
    }

    @Then("^the new Wish List popup should appear$")
    public void checkWishlistPopup() throws Throwable {
        assertTrue("Wishlist popup did not appear", productDetailsPage.wishlistPopupHasAppeared());
//        assertTrue("Wishlist popup title incorrect", productDetailsPage.getWishlistPopupHeader().equals("ADD TO WISH LIST"));
    }

    @Then("^the new Wish List popup should disappear$")
    public void checkWishlistPopupGone() throws Throwable {
        assertTrue("Wishlist popup did not disappear", productDetailsPage.wishlistPopupHasDisappeared());
        Thread.sleep(1000); //Give the wishlist some time to update itself post-closing of this
    }

    @And("^I click the close button on the wishlist popup$")
    public void clickCloseOnWishlistPopup() throws Throwable {
        productDetailsPage.clickCloseButtonOnWishlistPopup();
    }

    @And("^I click Add to Wish List on the popup$")
    public void clickAddToWishlistOnPopup() throws Throwable {
        assertTrue("Wishlist popup did not appear", productDetailsPage.wishlistPopupHasAppeared());
        productDetailsPage.clickAddToWishListOnPopup();
    }

    @And("^I click Move to Wish List on the popup$")
    public void clickMoveToWishlistOnPopup() throws Throwable {
        assertTrue("Wishlist popup did not appear", productDetailsPage.wishlistPopupHasAppeared());
        shoppingBagPage.clickMoveToWishListOnPopup();
    }

    @And("^I select '(.+)' from the Add to Wishlist popup wishlist menu$")
    public void selectCreateNewFromPopup(String name) throws Throwable {
        productDetailsPage.selectPopupWishlistMenuItem(name);
    }

    @Then("^the wishlist (.+) should be automatically selected$")
    public void checkWishlistPopupSelectedWishlist(String name) throws Throwable {
        int i=0;
        while( i<10 ) {
            if(!productDetailsPage.checkWishlistPopupSelectedWishlistName().equals(name)) {
                i++;
                Thread.sleep(1000);
            } else {
                i=10;
            }
        }
        assertEquals("Wishlist " + name + " not selected. ", name, productDetailsPage.checkWishlistPopupSelectedWishlistName());
    }

    @Then("^I should see a 'Please select a size' error message in the popup$")
    public void selectSizeInPopupError() throws Throwable {
        assertEquals("Please select a size", productDetailsPage.wishlistPopupErrorMessage());
    }

    @And("^I enter a new wishlist name of (.+) in the create box and click create$")
    public void enterNewWishlistNameInPopup(String newName) throws Throwable {
        productDetailsPage.enterNewWishlistNameInPopup(newName);
        Thread.sleep(100);
        productDetailsPage.pressCreateButtonOnWishlistPopup();
        Thread.sleep(300);
    }

    @And("^I click the 'VIEW WISH LIST' link$")
    public void clickViewWishListLink() throws Throwable {
        productDetailsPage.clickViewWishList();
    }

    @And("^the new wishlist popup does not have a size selector$")
    public void checkSizeSelectorInPopupDoesNotExist() throws Throwable {
            assertFalse("Size selector should not be present", productDetailsPage.isSizeSelectorInWishlistPopup());
    }

    @And("^The wishlist popup menu items custom lists should appear in alphabetical order$")
    public void checkMenuItemsRightOrder() throws Throwable {
        Thread.sleep(1500);
        NavMenu navMenu = productDetailsPage.getNavMenu();

        List<MenuItem> menuItemsSorted = new ArrayList<MenuItem>(navMenu.getUserPopupMenuItems());
        Collections.sort(menuItemsSorted, new MenuItem.AlphabeticalComparator());

        assertThat("Popup Wishlist Menu Items not alphabetical", navMenu.getUserPopupMenuItems(), equalTo(menuItemsSorted));
    }

    @And("^the new Wish List popup should contain the correct product details of item ([0-9]+)$")
    public void checkWishlistPopupDetails(Integer itemNumber) throws Throwable {
        List<WishlistV3Product> singleWishlistProducts = new ArrayList();
        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");

        Thread.sleep(1500);
        String pid = skuToPid(skuList.get(itemNumber-1));

        if (scenarioSession.getData("singleWishlistProducts") != null ) {
            singleWishlistProducts = (List<WishlistV3Product>) scenarioSession.getData("singleWishlistProducts");
        }
        WishlistV3Product wishlistV3Product = singleWishlistProducts.get(itemNumber-1);
        assertTrue("Image not found", productDetailsPage.isProductImagePresentInWishlistPopup(pid));
        assertEquals(wishlistV3Product.getDesignerName(),productDetailsPage.getWishlistPopupDesignerName().toUpperCase());
        assertEquals(wishlistV3Product.getPriceString(), productDetailsPage.getWishlistPopupListedPrice());
        assertEquals(scenarioSession.getData("productDescription"), productDetailsPage.getWishlistPopupProductDescription());
    }

    @And("^I add multi sized item (\\d+) to the shopping bag$")
    public void addSkuToBag(int itemNumber) throws Throwable {
        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");
        productDetailsPage.selectSKU(skuList.get(itemNumber-1));
        productDetailsPage.clickAddToShoppingBagButton();
        Thread.sleep(2000);

    }

    @And("^I add single sized item (\\d+) to the shopping bag$")
    public void addSkuToBagSingleSize(int itemNumber) throws Throwable {
        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");
//        productDetailsPage.selectSKU(skuList.get(itemNumber-1));
        productDetailsPage.clickAddToShoppingBagButton();

    }

    @Then("^item (\\d+) should be in the shopping bag (\\d+) times?$")
    public void checkBagForPid(int itemNumber, int numberOfTimesExpected) throws Throwable {
        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");
        String sku = skuList.get(itemNumber - 1);

        shoppingBagPage.go();

        SearchableProduct searchableProduct = new SearchableProduct(1,"TEMP","TEMP");
        Product product = new Product(searchableProduct, sku);

        int numberOfTimesActual = shoppingBagPage.isProductInShoppingBagTimes(product);

        assertEquals("Expected sku " + (itemNumber) + " (" + sku + ") to exist " + numberOfTimesExpected + " times:"
                , numberOfTimesExpected, numberOfTimesActual);
    }

    @Then("^item (\\d+) (should|should not) be in the shopping bag$")
    public void checkBagForSku(int itemNumber, String should) throws Throwable {
        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");
        String sku = skuList.get(itemNumber - 1);

        shoppingBagPage.go();

        SearchableProduct searchableProduct = new SearchableProduct(1,"TEMP","TEMP");
        Product product = new Product(searchableProduct, sku);

        if (should.equals("should")) {
            assertTrue("Expected sku " + (itemNumber) + " (" + sku + ") to exist in basket",
                    shoppingBagPage.isSkuInShoppingBag(product.getSku()));
        } else {
            assertFalse("Expected sku " + (itemNumber) + " (" + sku + ") to not exist in basket",
                    shoppingBagPage.isSkuInShoppingBag(product.getSku()));

        }
    }


    @When("^I goto multiple size product details page$")
    public void I_goto_multiple_size_product_details_page() throws Throwable {
        boolean isInStockProduct = false;

        do {
            Map<String,Object> randomMultiSizedProducts = hybridProductDataAccess.findRandomMultiSizedProduct(SalesChannelEnum.valueOf("NAP_"+webBot.getRegion()));
            Object pid = randomMultiSizedProducts.get("search_prod_id");
            productDetailsPage.goToProduct(String.valueOf(pid));
            if (productDetailsPage.isInStock())
                //this only checks that the buttons are there, it may still return an out of stock product if all sizes are soldout/unavailable
                isInStockProduct = true;
        }
        while (!isInStockProduct);
    }

    @Then("^I am taken to the correct (IN_STOCK|SOLD_OUT) product details page$")
    public void I_am_taken_to_the_correct_product_details_page(String availability) throws Throwable {
        String pid = (String) scenarioSession.getData(SKU_OR_PID_KEY);
        assertThat(productDetailsPage.getPidFromUrl(), is(pid));
        if ("SOLD_OUT".equals(availability)) {
            productDetailsPage.clickViewProductDetailLink();
            //need to wait for the product details to be displayed
            WaitUtil.waitFor(1000);
        }
        assertThat(productDetailsPage.getPidFromPage(), is(Integer.valueOf(pid)));
    }

    @Then("^the Back to results link is displayed$")
    public void the_Back_to_results_link_is_displayed() throws Throwable {
        assertTrue("back to results link is not displayed ", productDetailsPage.isBackToResultsLinkDisplayed());
    }

    @When("^I select the Back to results link$")
    public void I_select_the_Back_to_results_link() throws Throwable {
        productDetailsPage.clickBackToResultsLink();
    }

    @When("^I click on view wishlist$")
    public void I_click_on_view_wishlist() throws Throwable {
       productDetailsPage.clickViewWishlist();
    }

    @And ("^I navigate to product page$")
    public void I_navigate_to_sold_out_product_page(){
        ArrayList soldOutPids = scenarioSession.getData("listOfSkus");
        String soldOutPid = (String) soldOutPids.get(0);
        String[] parts = soldOutPid.split("-");
        String pid = parts[0];
        productDetailsPage.goToProduct(pid);
        scenarioSession.putData(PRODUCT_KEY, pid);
    }

    @Then ("^product information should be (hidden|shown)$")
    public void product_information_should_be_shown_or_hidden(String visibility){
            assertTrue("Detail product information should be " + visibility + " but it is not", productDetailsPage.isFullProductInformationDisplayed(visibility));
    }

    @Then ("^collapse mode product information are shown$")
    public void  collapse_mode_product_information_are_shown(){
        assertTrue("sold out product information is not displayed in sold out page", productDetailsPage.isMinimumProductInformationDisplayed());
    }

     @And ("^I click on (view|hide) product detail link$")
    public void I_click_on_view_product_detail_link(String displayStatus) throws InterruptedException {
         if (displayStatus.equals("view")) {
            productDetailsPage.clickViewProductDetailLink();
         }else {
            productDetailsPage.clickHideProductDetailLink();
         }
         Thread.sleep(500);
    }

    @Then ("^YMAL product information are displayed$")
    public void YMAL_product_information_are_displayed(){
        assertTrue("Missing YMAL information in sold out page", productDetailsPage.isYmalProductInfoDisplayed());
    }

    @And ("^I click on random YMAL product$")
    public void I_click_on_random_YMAL_product() throws InterruptedException {
        String pid = productDetailsPage.clickRandomYmalProduct();
        scenarioSession.putData(SKU_OR_PID_KEY, pid);
    }

    @When ("^I select the add to wishlist button on the sold out page$")
    public void I_select_the_add__to_wishlist_button_on_the_sold_out_page() throws Throwable {
        productDetailsPage.addSoldOutItemToWishList();
    }

     @When ("^I click on the expanded product details page add to wishlist$")
     public void I_click_on_the_expanded_product_details_page_add_to_wishlist() throws InterruptedException{
        productDetailsPage.expandedProductDetailsPageAddToWishlist();
        }

    @Given("^I sign in with the correct live test user$")
    public void I_sign_in_with_the_correct_live_test_user() throws Throwable {
        homePage.go();
        homePage.clickSignInLink();
        signInPage.signIn("naptech7@gmail.com","netaporter");
        Thread.sleep(500);
    }

    @Then("^I should see (ymal|outfit) module$")
    public void I_should_see_outfit_module(String module) throws Throwable {
        String product = scenarioSession.getData(PRODUCT_KEY);
        if (module.equals("outfit")) {
            assertTrue("Outfit module for pid "+product+" should be visible but it is not", productDetailsPage.isOutfitModuleDisplayed());
        }else if (module.equals("ymal")) {
            assertTrue("YMAL module for pid "+product+" should be visible but it is not", productDetailsPage.isYmalModuleDisplayed());
        }
    }

    @And("^I should see designer, title and price on each (ymal|outfit) pid$")
    public void I_should_see_designer_title_and_price_on_each_outfit_pid(String module) throws Throwable {
        String product = scenarioSession.getData(PRODUCT_KEY);
        if (module.equals("outfit")) {
            assertTrue("Outfit module for pid "+product+" should be visible but it is not", productDetailsPage.isOutfitPidInfoDisplayed());
        }else if (module.equals("ymal")) {
            assertTrue("YMAL module for pid "+product+" should be visible but it is not", productDetailsPage.isYmalPidInfoDisplayed());
        }
    }

    @And("^I click on (.*) voucher$")
    public void I_click_on_voucherType_voucher(String voucher) throws Throwable {
        scenarioSession.putData(VOUCHER_TYPE, voucher);
        productDetailsPage.clickVoucher(voucher);
    }

    @When("^I add selected voucher in shopping bag$")
    public void I_add_selected_voucher_in_shopping_bag() throws Throwable {
        productDetailsPage.addVoucherToShoppingBag();
    }

    @And("^I click on Proceed to Purchase in the Voucher PDP$")
    public void I_click_on_Proceed_to_Purchase_in_the_voucher_PDP() throws Throwable {
        productDetailsPage.clickVoucherProceedToPurchase();
    }

    @And("^I select random voucher price and fill out required detail$")
    public void I_select_random_voucher_price_and_fill_detail() throws Throwable {
        String voucherType = scenarioSession.getData(VOUCHER_TYPE);
        String voucherPrice = productDetailsPage.selectRandomVoucherPrice();
        scenarioSession.putData(SELECTED_VOUCHER_PRICE,voucherPrice);
        productDetailsPage.fillVoucherInformation(voucherType);
    }

    @Then("^I should see selected voucher in shopping bag$")
    public void I_should_see_selected_voucher_in_shopping_bag() throws Throwable {
        String voucherPrice = scenarioSession.getData(SELECTED_VOUCHER_PRICE);
        String voucherType = scenarioSession.getData(VOUCHER_TYPE);
        productDetailsPage.correctVoucherAdded(voucherPrice, voucherType);
    }

    @And("^I should see that product in the Wish list page$")
    public void I_should_see_that_product_in_the_Wish_list_page() throws Throwable {
        Product product = (Product) scenarioSession.getData(PRODUCT_KEY);
        String sku = product.getSku();
        assertTrue("Could not find product in Wish List page" + sku, productDetailsPage.isSkuInWishList(sku));
    }

    @Then("^Single size (.*) analytics are fired$")
    public void single_size_analytics_are_fired(String stockDetail) throws Throwable {
        String pid = scenarioSession.getData(PRODUCT_KEY);
        productDetailsPage.isSingleSizeAnalyticsFired(stockDetail, pid);
    }

    @Then("^Multiple size (.*) analytics are fired$")
    public void multiple_size_analytics_are_fired(String stockDetail) throws Throwable {
        String pid = scenarioSession.getData(PRODUCT_KEY);
        productDetailsPage.isMultipleSizeAnalyticsFired(stockDetail, pid);
    }

    @And("^I select random skus from size drop down$")
    public void I_select_random_skus_from_size_drop_down() throws Throwable {
        List<String> skus = productDetailsPage.getSKUs();
        Collections.shuffle(skus);
        scenarioSession.putData(SKU_OR_PID_KEY, skus.get(0));
        productDetailsPage.selectSKU(skus.get(0));
    }

    @Then("^Selected size analytics are fired$")
    public void selected_size_analytics_are_fired() throws Throwable {
        String sku = scenarioSession.getData(SKU_OR_PID_KEY);
        productDetailsPage.isSelectedSizeAnalyticFired(sku);
    }

    @Then("^Analytic for completely sold out multi-size item are fired$")
    public void Multiple_size_sold_out_analytics_are_fired() throws Throwable {
       String pid =  scenarioSession.getData(PRODUCT_KEY);
        productDetailsPage.isMultipleCompletelySoldOutAnalyticFired(pid);
    }

    @Then("^Default analytics are fired$")
    public void Default_analytics_are_fired() throws Throwable {
        String pid = scenarioSession.getData(PRODUCT_KEY);
        ProductDetails pd = productDataAccess.getApiClientFacade().getProductDetails(SalesChannelEnum.getByWebsiteAndRegion(WebsiteEnum.valueOf(webBot.getBrand()),region),pid);
        String expectedDesigner = Normalizer.normalize(pd.getDesignerName(), Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        String expectedTitle = Normalizer.normalize(pd.getTitle(), Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        String NormDesignerName = expectedDesigner.replaceAll("[^\\w \\.\\,\\-\\|]","");
        String NormTitleName = expectedTitle.replaceAll("[^\\w \\.\\,\\-\\|]","");

        List<String> names = productDataAccess.getApiClientFacade().getCategoryNames(webBot.getSalesChannelByBrandAndRegion(), pd.getCategories());

        productDetailsPage.isDefaultAnalyticFired(NormDesignerName, NormTitleName, pid, names);
    }

    @And("^added wishlist item should be on the page$")
    public void added_wishlist_item_should_be_on_the_page() throws Throwable {
        String pid = scenarioSession.getData(PRODUCT_KEY);
        productDetailsPage.isProductAddedInWishlist(pid);
    }

    @And ("^I navigate to (.*) product detail page$")
    public void I_navigate_to_product_detail_page(String pid) throws InterruptedException {
        productDetailsPage.goToProduct(pid);
        Thread.sleep(1000);
        assertThat(webBot.getCurrentUrl(), containsString("/en/d/product"));
        scenarioSession.putData(PRODUCT_KEY, pid);
    }

    @When("^I click the Add to Basket Button$")
    public void I_click_on_glass_add_to_bag() throws Throwable {
        productDetailsPage.clickAddToBag();
    }

    @Then("^I should see mini bag overlay with correct information$")
    public void I_should_see_mini_bag_overlay_with_correct_information() throws Throwable {
        String pid = scenarioSession.getData(PRODUCT_KEY);
        ProductDetails pd = productDataAccess.getApiClientFacade().getProductDetails(SalesChannelEnum.getByWebsiteAndRegion(WebsiteEnum.valueOf(webBot.getBrand()),region),pid);
        String expectedDesigner = Normalizer.normalize(pd.getDesignerName(), Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        String expectedTitle = Normalizer.normalize(pd.getTitle(), Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        String NormDesignerName = expectedDesigner.replaceAll("[^\\w \\.\\,\\-\\|]","");
        String NormTitleName = expectedTitle.replaceAll("[^\\w \\.\\,\\-\\|]","");
        int expectedPrice = pd.getPrice().intValue();

        productDetailsPage.miniShoppingBagInfoDisplayed(NormDesignerName, NormTitleName, pid, expectedPrice);
    }

    @And("^shopping bag counter increases$")
    public void shopping_bag_counter_increases() throws Throwable {
        productDetailsPage.shoppingCounterIncrement();
    }

    @When("^I click proceed to purchase in mini bag$")
    public void I_click_on_proceed_to_purchase_in_mini_bag() throws Throwable {
        productDetailsPage.clickProceedToPurchaseMiniShoppingBag();
    }

    @Then("^I should see Google Glass product in the shopping bag$")
    public void I_should_see_Google_Glass_product_in_the_shopping_bag() throws Throwable {
        String product = scenarioSession.getData(GOOGLE_PID);
        shoppingBagPage.isSkuInShoppingBag(product);
    }

    @When("^I click Sign in link in app page$")
    public void I_click_Sign_in_link_in_app_page() throws Throwable {
        productDetailsPage.clickSignInLink();
    }

    @When("^I click NAP logo$")
    public void I_click_NAP_logo() throws Throwable {
        productDetailsPage.clickNapLogo();
    }

    @When("^I navigate to (.*) product detail page for category (.*)$")
    public void i_navigate_to_product_detail_page(ProductDsl.ProductAvailability stockAvailable, ProductDsl.ProductCategory category) throws Throwable {

        List<String> skus = productDataAccess.getApiClientFacade().findSkus(webBot.getSalesChannelByBrandAndRegion(), category, stockAvailable, 1);
        String[] pid = skus.get(0).split("-");
        productDetailsPage.goToProduct(pid[0]);
        assertThat(webBot.getCurrentUrl(), containsString("/product/"));
        scenarioSession.putData(PRODUCT_KEY, pid[0]);
    }

    @When("^I navigate to (.*) product page for category (.*) using url parameters with country (.*) language (.*) and device (.*)$")
    public void I_navigate_to_product_page_using_url_parameteres_with_country_countryCode_language_languageCode_and_device_deviceCode(ProductDsl.ProductAvailability stockAvailable, ProductDsl.ProductCategory category, String countryValue, String languageValue, String deviceType) throws Throwable {
        webBot.addCookie(COUNTRY_COOKIE_KEY, countryValue);
        webBot.addCookie(LANGUAGE_COOKIE_KEY, languageValue);
        webBot.addCookie(DEVICE_COOKIE_KEY, deviceType);

        List<String> skus = productDataAccess.getApiClientFacade().findSkus(webBot.getSalesChannelByBrandAndRegion(), category, stockAvailable, 1);
        String pid = productDetailsPage.getPidFromSku(skus.get(0));
        productDetailsPage.goToProduct(pid);
        assertThat(webBot.getCurrentUrl(), containsString("/product/"));
        scenarioSession.putData(PRODUCT_KEY, pid);
    }
}
