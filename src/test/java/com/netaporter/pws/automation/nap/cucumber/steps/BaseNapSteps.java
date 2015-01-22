package com.netaporter.pws.automation.nap.cucumber.steps;

import com.netaporter.pws.automation.nap.components.*;
import com.netaporter.pws.automation.nap.pages.*;
import com.netaporter.pws.automation.nap.pages.AbstractNapPage;
import com.netaporter.pws.automation.shared.pojos.Product;
import com.netaporter.test.client.product.impl.HybridProductDataAccess;
import com.netaporter.test.utils.cucumber.steps.LegacyWebAppBaseStep;
import com.netaporter.test.utils.pages.driver.WaitTime;
import com.netaporter.test.utils.pages.exceptions.PageElementNotFoundException;
import com.netaporter.wishlist.woas.client.WoasClient;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.fail;

/**
 * Base class for all Net-A-Porter step definitions.
 *
 * For convenience put all Page Objects in here so that they are available all concrete step definition classes.
 *
 * DO NOT put any Cucumber JVM specific annotations in this class.
 */
public abstract class BaseNapSteps extends LegacyWebAppBaseStep {

    @Autowired
    protected HomePage homePage;
    @Autowired
    protected SignInPage signInPage;
    @Autowired
    protected RegisterNewAccountPage registerNewAccountPage;
    @Autowired
    protected ShoppingBagPage shoppingBagPage;
    @Autowired
    protected NAPProductListPage accessoriesPage;
    @Autowired
    protected NAPProductListPage bagsPage;
    @Autowired
    protected NAPProductListPage sandalsPage;
    @Autowired
    protected NAPProductListPage clothingPage;
    @Autowired
    protected NAPProductListPage pyhtonBagSearchResultPage;
    @Autowired
    protected NAPProductDetailsPage productDetailsPage;
    @Autowired
    protected ChangeCountryPage changeCountryPage;
    @Autowired
    protected PurchasePathFlowPage purchasePathFlowPage;
    @Autowired
    protected CustomerOrdersPage customerOrdersPage;
    @Autowired
    protected AccountDetailsPage accountDetailsPage;
    @Autowired
    protected CustomerAddressesPage customerAddressesPage;
    @Autowired
    protected EmailUpdatesRegistrationPage emailUpdateRegistrationPage;
    @Autowired
    protected AddressBookPage addressBookPage;
    @Autowired
    protected MyAccountPage myAccountPage;
	@Autowired
	protected CustomerSearchPage customerSearchPage;
	@Autowired
	protected UserCreditCardsPage userCreditCardsPage;
    @Autowired
    protected ForgottenPasswordPage forgottenPasswordPage;
    @Autowired
    protected SearchPage searchPage;
    @Autowired
    protected WishListV3Page wishListV3Page;
    @Autowired
    protected CustomerReservationsPage customerReservationsPage;
    @Autowired
    protected PageNotFoundPage pageNotFoundPage;
    @Autowired
    protected SetupSessionPage setupSessionPage;
    @Autowired
    protected MySubscriptionPage mySubscriptionPage;
    @Autowired
    protected InvalidSearchPage invalidSearchPage;


    // Note: we don't use these directly, but added them in to stop Spring to being clever.
    @Autowired
    protected NAPProductListPage backInStockListingPage;
    @Autowired
    protected NAPProductListPage whatsNewListingPage;
    @Autowired
    protected DesignerAToZPage designerAToZPage;

    @Autowired
    protected HeaderComponent headerContent;
    @Autowired
    protected HeaderComponent headerComponent;
    @Autowired
    protected WoasClient woasAPIClient;
    @Autowired
    protected MyFavouriteDesignersPage myFavouriteDesignersPage;
    @Autowired
    protected NAPSaleLandingPage napSaleLandingPage;
    @Autowired
    protected TopNavComponent topNavComponent;
    @Autowired
    protected NAPSportLandingPage napSportLandingPage;
    @Autowired
    protected FooterComponent footerComponent;
    @Autowired
    protected LiveChatComponent liveChatComponent;
    @Autowired
    protected ChangeCountryComponent changeCountryComponent;
    @Autowired
    protected ChangeLanguageComponent changeLanguageComponent;

    @Autowired
    protected HybridProductDataAccess hybridProductDataAccess;

    @Autowired
    protected GoogleGlassPage googleGlassPage;

    protected static final String SKU_OR_PID_KEY = "product pid or sku";
    protected static final String PRODUCT_LISTING_PAGE_KEY = "productListingPage";
    protected static final String CONDITION_KEY = "holds condition for assume";
    protected static final String IS_MY_SUBSCRIPTIONS_ENABLED = "false";
    protected static final String ITEMS_ADDED_TO_BAG = "sku list";
    protected static final String ITEMS_ADDED_TO_BAG_WITH_STOCK = "sku and stock map";
    protected static final String SHIPPING_PRICE_FROM_SHOPPING_BAG_PAGE = "shippingPriceFromShoppingBagPage";

    public static String skuToPid(String sku) {
        return sku.split("-")[0];
    }

    public AbstractNapPage getCurrentNapPage() {
        return (AbstractNapPage)webBot.getCurrentPage();
    }

    public void addInStockItemToBasket(NAPProductDetailsPage pdp) {
        String inStockSKU = getInStockSKU(pdp);
        if (inStockSKU != null) {
            pdp.addIntoShoppingBag(inStockSKU);
        } else {
            fail("Could not find an in-stock SKU on product details page to complete test: " + webBot.getCurrentUrl());
        }
    }

    private String getInStockSKU(NAPProductDetailsPage pdp) {
        List<String> skus = pdp.getSKUs();
        for(String sku: skus) {
            if (hybridProductDataAccess.getLegacyDBClient().isPidOrSkuInStock(webBot.getRegionEnum(), sku)) {
                return sku;

            }
        }
        return null;
    }

    public Product addAnyInStockProductToShoppingBag(NAPProductListPage plp, NAPProductListPage.PriceRequirement priceRequirement, int threshold) {
        List<String> matchingPids = plp.getPIDsMatching(priceRequirement, threshold);
        for(String pid : matchingPids) {
            if(productDataAccess.Solr_isPidOrSkuInStock(webBot.getSalesChannelByBrandAndRegion(), pid)) {
                productDetailsPage.goToProduct(pid);
                List<String> skus = productDetailsPage.getSKUs();
                for(String sku:skus) {
                    if (productDataAccess.Solr_isPidOrSkuInStock(webBot.getSalesChannelByBrandAndRegion(), sku)) {
                        //add the pid or sku to the session for reporting purposes
                        scenarioSession.putData(SKU_OR_PID_KEY, sku);
                        return productDetailsPage.addIntoShoppingBag(sku);
                    }
                }
            }
        }
        throw new IllegalStateException("Could not find suitable product to for priceRequirement: " + priceRequirement + " " + threshold + " , so can't continue the test.");
    }

    public boolean isSaleOn(){
        return hybridProductDataAccess.getLegacyDBClient().isSaleOnCheckByDate(webBot.getRegionEnum());
    }

    public void isCorrectDestinationListingPage(String listingPageType){
        final String currentUrl = webBot.getCurrentUrl();
        assertThat(currentUrl, containsString("/"+listingPageType));
        assertThat(webBot.getCurrentPage().getTitle(), containsString(listingPageType));
    }

    public boolean isSubscriptionOn(){
        return hybridProductDataAccess.getLegacyDBClient().isSubscriptionOn(webBot.getRegionEnum());
    }


    public boolean isBeautyLinkOn(){
       return hybridProductDataAccess.getLegacyDBClient().isBeautyLinkOn(webBot.getRegionEnum());

    }

    protected boolean isElementPresent(By locator) {
       try {
           webBot.findElement(locator, WaitTime.TEN);
       }
       catch (PageElementNotFoundException e) {
           return false;
       }
       return true;
    }

    public boolean iAmOnThe404ErrorPage() {
        if (doesElementExist(By.cssSelector("[id=en] h1"),WaitTime.ONE)) {
            List <WebElement> heading = webBot.findElements(By.cssSelector("[id=en] h1"));
            if(heading.size() > 0 && heading.get(0).toString().equalsIgnoreCase("This page cannot be found")) {
                return true;
            }
        }
        return false;
    }
}
