package com.netaporter.pws.automation.nap.cucumber.steps.productlisting;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.netaporter.pws.automation.nap.components.PageNavigationComponent;
import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.pws.automation.nap.cucumber.steps.productdetails.ProductDetailSteps;
import com.netaporter.pws.automation.nap.enums.ImageViewEnum;
import com.netaporter.pws.automation.nap.enums.ProductFilterEnum;
import com.netaporter.pws.automation.nap.pages.NAPAWSListingPage;
import com.netaporter.pws.automation.nap.pages.NAPProductListPage;
import com.netaporter.pws.automation.nap.pages.NAPSaleListingPage;
import com.netaporter.pws.automation.nap.pages.NapListingPageRegistry;
import com.netaporter.pws.automation.nap.util.SortOrder;
import com.netaporter.pws.automation.nap.util.SortOrderAssertion;
import com.netaporter.pws.automation.shared.pages.IProductListPage;
import com.netaporter.pws.automation.shared.pages.ListingPageRegistry.ListingPageType;
import com.netaporter.pws.automation.shared.pojos.Product;
import com.netaporter.pws.automation.shared.utils.Consts;
import com.netaporter.pws.automation.shared.utils.CountryAndCurrencyUtil;
import com.netaporter.pws.automation.shared.utils.LocaleUrlUtils;
import com.netaporter.pws.automation.shared.utils.WaitUtil;
import com.netaporter.test.utils.enums.RegionEnum;
import com.netaporter.test.utils.pages.exceptions.PageElementNotFoundException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: c.choudhury@london.net-a-porter.com
 * Date: 13/02/2013
 * Time: 17:30
 */

public class ProductListingSteps extends BaseNapSteps {

    private static final String SORT_ORDER = "sortOrder";

    private static final String OLD_PAGE_NUMBER_KEY = "1";

    private static final String LINK_NAME_KEY = "linkName";
    private static final String PAGE_LINK_NAME_KEY = "pageLinkName";
    private static final String LISTING_PAGE_TYPE = "generic_listing_page";

    private static final String FILTER_TYPE_KEY = "filter type";
    private static final String FILTER_SELECTED_NAME_KEY = "selected filter name";
    private static final String FILTER_SELECTED_ID_KEY = "selected filter id";
    private static final String BOOKMARK_DESIGNER_FILTER = "bookmarked designer filter";
    private static final String BOOKMARK_COLOR_FILTER = "bookmarked color filter";

    public static final String KEY_INITIAL_TIMESTAMP = "InitialTimestamp";
    public static final String INITIAL_NUMBER_OF_RESULTS = "initial number of results from header";

    private static final String NAV_LEVEL_3_KEY = "navLevel3";
    private static final String TOTAL_PAGE_NUMBER = "total page number";
    private static final String CURRENT_PAGE_NUMBER_KEY = "current page number";
    private static final int MAX_TRIES = 10;
    private static final String ALL_AVAILABLE_DESIGNERS_KEY = "a list of all designer filter entries";
    private static final String ALL_AVAILABLE_COLORS_KEY = "a list of all color filter entries" ;
    private static final String ALL_AVAILABLE_SIZES_KEY = "a list of all size filter entries";
    private static final String TOTAL_NUMBER_OF_SIZE_FACETS_VISIBLE_KEY = "totalNumberOfSizesVisible";
    private static final String LIST_OF_PRODUCT_PRICES_KEY = "a list of all product prices";
    private static final String HREF = "href";

    @Given("^I pick a (.*) category in the product listing page$")
    public void I_pick_a_category_in_the_product_listing_page(String category) {
        NAPProductListPage productListPage = (NAPProductListPage) ((NapListingPageRegistry)listingPageRegistry).createAndGoToAProductListPage(ListingPageType.CATEGORY, category, false);
        productListPage.closeDontMissOutPopup();
        if (webBot.getRegion().equals(RegionEnum.APAC.name()))
            {productListPage.closeApacWelcomeMessage();}
        scenarioSession.putData(PRODUCT_LISTING_PAGE_KEY, productListPage);
        scenarioSession.putData(LISTING_PAGE_TYPE, category);
    }

    @When("^I go to a product page from the selected filtered list$")
    public void I_go_to_a_product_page_from_the_selected_filtered_list() {
        String pid = getNapProductListPage().clickOnARandomInStockProduct();
        scenarioSession.putData(ProductDetailSteps.PRODUCT_KEY, pid);
    }

    @Then("^the color name should be included on that page$")
    public void the_color_name_should_be_included_on_that_page() throws Throwable {
        assertTrue(webBot.getPageSource().contains((String) scenarioSession.getData(FILTER_SELECTED_NAME_KEY)));
    }

    @And("^I filter by (color|Color|size|Size|designer|Designer)$")
    public void I_filter_by_filterType(ProductFilterEnum filterType) throws Throwable {
        IProductListPage productListPage = (IProductListPage) webBot.getCurrentPage();
        scenarioSession.putData(INITIAL_NUMBER_OF_RESULTS, productListPage.getNumberOfResultsFromHeader());

        Map.Entry<Set<String>, Set<String>> entry = productListPage.selectFilterItemRandomly(filterType);

        scenarioSession.putData(FILTER_SELECTED_ID_KEY, entry.getKey());
        scenarioSession.putData(FILTER_SELECTED_NAME_KEY, entry.getValue().toString().replaceAll("\\[|\\]",""));
        scenarioSession.putData(FILTER_TYPE_KEY, filterType);
    }

    @Given("^I am currently on a (designer|custom|category|boutiques|search) listing page$")
    public void I_am_currently_on_a_listing_page(ListingPageType listingPage) throws Throwable {
        NAPProductListPage productListPage = (NAPProductListPage) ((NapListingPageRegistry)listingPageRegistry).createAndGoToAProductListPage(listingPage, null, false);

        productListPage.closeDontMissOutPopup();
        if (webBot.getRegion()!=null && webBot.getRegion().equals("APAC"))
            productListPage.closeApacWelcomeMessage();

        scenarioSession.putData(LISTING_PAGE_TYPE, listingPage);
        saveListingPageToSession(productListPage);

        recordTimeStamp();
    }

    @Given("^I am currently on a (whats_new) AWS listing page$")
     public void I_am_currently_on_a_whats_new_AWS_listing_page(ListingPageType listingPage) throws Throwable {
        NAPAWSListingPage whatsNewListPage = (NAPAWSListingPage) ((NapListingPageRegistry)listingPageRegistry).createAndGoToAProductListPage(listingPage, null, false);
        whatsNewListPage.closeDontMissOutPopup();
        saveListingPageToSession(whatsNewListPage);

        recordTimeStamp();
    }

    private void saveListingPageToSession(IProductListPage napListingPage) {
        scenarioSession.putData(PRODUCT_LISTING_PAGE_KEY, napListingPage);
    }

    @When("^I randomly select a level2Category$")
    public void I_select_a_category() throws Throwable {
        getNapProductListPage().selectARandomSecondLevelCategory();
        Thread.sleep(500);
    }

    @When("^I sort by (Default|New In|Price High|Price Low|Discount High to Low|Discount Low)$")
    public void I_sort_by_Price(String sortOrderString) throws Throwable {
        SortOrder sortOrder = createSortOrder(sortOrderString);
        IProductListPage napProductListPage = (IProductListPage) webBot.getCurrentPage();

        //passing the transformList directly will cause a stale element exception
        Collection<Integer> productPricesInInitialOrder = new ArrayList<Integer>();
        if (scenarioSession.getData(LISTING_PAGE_TYPE)!=null && "Whats_New".equalsIgnoreCase(scenarioSession.getData(LISTING_PAGE_TYPE).toString()))
            napProductListPage.getPageNavigationComponent().selectLink(PageNavigationComponent.PageLinkOption.NEXT);

        productPricesInInitialOrder.addAll(napProductListPage.getAllProductPrices());
        scenarioSession.putData(LIST_OF_PRODUCT_PRICES_KEY, productPricesInInitialOrder);
        Thread.sleep(500);
        scenarioSession.putData(INITIAL_NUMBER_OF_RESULTS, napProductListPage.getNumberOfResultsFromHeader());
        scenarioSession.putData(SORT_ORDER, sortOrder);

        getNapProductListPage().sortBy(sortOrder);
    }

    private SortOrder createSortOrder(String sortOrderString) {
        return SortOrder.valueOf(sortOrderString.replace(' ', '_').toUpperCase());
    }


    @Then("^the products are displayed in correct order$")
    public void the_products_are_displayed_in_correct_price_order() throws Throwable {

        // Note: without delay I got
        // org.openqa.selenium.StaleElementReferenceException: By is no longer attached to the DOM (WARNING: The server did not provide any stacktrace information)
        // TODO: better way to fix this -- close pop up
        Thread.sleep(3000);
        SortOrder sortOrder = (SortOrder) scenarioSession.getData(SORT_ORDER);

        List<Integer> integers = null;

        switch (sortOrder) {
            case PRICE_HIGH:
            case PRICE_LOW:
                integers = getNapProductListPage().getAllProductPrices();
                break;
            case DISCOUNT_HIGH_TO_LOW:
            case DISCOUNT_LOW:
                integers = getNapProductListPage().getAllProductPercentages();
                break;
            default:
                fail("Don't support this order yet in assertion: " + sortOrder);
        }
        assertTrue("Products are not sorted in correct order: " + sortOrder, SortOrderAssertion.assertIntegerValuesInCorrectOrders(sortOrder, integers));
    }

    @And("^the sorting is lost$")
    public void the_sorting_is_lost() throws Throwable {
        List<Integer> priceValues = getNapProductListPage().getAllProductPrices();
        assertThat(priceValues, is(equalTo(scenarioSession.getData(LIST_OF_PRODUCT_PRICES_KEY))));
    }

    @Then("^the URL is updated with the correct sorting parameter$")
    public void the_URL_is_updated_with_the_correct_sorting_parameter() throws Throwable {
        SortOrder sortOrder = (SortOrder) scenarioSession.getData(SORT_ORDER);

        if (SortOrder.DEFAULT != sortOrder) {
            String sortOrderClass = sortOrder.getOrderValue();
            assertTrue("Price order in url does not match expected outcome: " + sortOrderClass, webBot.getCurrentUrl().matches(".*[sortBy=|sortOrder=]"+sortOrderClass+".*"));
        }
        else {
            assertFalse(webBot.currentUrlContains("sortBy"));
        }
    }

    @And("^the sort code should be removed from the url$")
    public void the_sort_code_should_be_removed_from_the_url() throws Throwable {
        SortOrder sortOrder = (SortOrder) scenarioSession.getData(SORT_ORDER);

        String sortOrderClass = sortOrder.getOrderValue();

        assertFalse("The following sort code was not removed from the URL: " + sortOrderClass, webBot.getCurrentUrl().matches("sortBy=" + sortOrderClass));
    }


    private void recordTimeStamp() {
        String pageRefreshTimeStamp = getNapProductListPage().getPageRefreshTimeStamp();

        scenarioSession.putData(KEY_INITIAL_TIMESTAMP, pageRefreshTimeStamp);
    }

    @Then("^the page is fully refreshed$")
    public void the_page_is_fully_refreshed() throws Throwable {
        String currentTimeStamp = getNapProductListPage().getPageRefreshTimeStamp();

        assertNotEquals("Page has not refreshed fully as the pageRefreshTimeStamp has not been changed", currentTimeStamp, scenarioSession.getData(KEY_INITIAL_TIMESTAMP));
    }

    @Then("^the page is partly refreshed$")
    public void the_page_is_partly_refreshed() throws Throwable {
        String pageRefreshTimeStamp = getNapProductListPage().getPageRefreshTimeStamp();
        String partialUpdateTimeStamp = getNapProductListPage().getPartialUpdateTimeStamp();

        assertNotEquals("Page is not partly refreshed as both timestamp are the same", pageRefreshTimeStamp, partialUpdateTimeStamp);
        assertEquals("Page is fully refreshed refreshed as the pageRefreshTimeStamp has been changed", pageRefreshTimeStamp, scenarioSession.getData(KEY_INITIAL_TIMESTAMP));
    }

    @Then("^the page is not partly or fully refreshed$")
    public void the_page_is_not_partly_or_fully_refreshed() throws Throwable {
        String pageRefreshTimeStamp = getNapProductListPage().getPageRefreshTimeStamp();
        String partialUpdateTimeStamp = getNapProductListPage().getPartialUpdateTimeStamp();

        assertEquals("Should not have partial update", pageRefreshTimeStamp, partialUpdateTimeStamp);
        assertEquals("Should not have full page refresh", pageRefreshTimeStamp, scenarioSession.getData(KEY_INITIAL_TIMESTAMP));
    }

    @Then("^the URL will be updated with the (color|Color|size|Size|designer|Designer) filter$")
    public void the_URL_will_be_updated_with_the_filter(ProductFilterEnum productFilter) throws Throwable {
        assertFiltersInUrl(true, webBot.getCurrentUrl(), productFilter.getUrlPrefix());
    }

    private void assertFiltersInUrl(boolean isPresent, String currentUrl, String urlPrefix) {
        String message = isPresent ? "is missing": "was not removed";
         try{
            String designerFilterParameters = currentUrl.substring(currentUrl.indexOf(urlPrefix));
             for (String filterValue : (Set<String>) scenarioSession.getData(FILTER_SELECTED_ID_KEY)) {
                 assertEquals("Filter " + message + " from URL", isPresent, designerFilterParameters.contains(filterValue.replace(" ", "_")));
             }
         }
         catch (StringIndexOutOfBoundsException e){
            if (isPresent){
                fail("Cannot find "+urlPrefix+" in the current URL. Filtering may have been removed.");
            }
         }
    }

    @Then("^I see the page change accordingly$")
    public void I_see_the_page_change_accordingly() throws Throwable {

        PageNavigationComponent.PageLinkOption pageLinkOption = (PageNavigationComponent.PageLinkOption) scenarioSession.getData(PAGE_LINK_NAME_KEY);

        switch (pageLinkOption)

        {
            case NEXT: {
                int currentPageNumber = getPageNavigation().getCurrentPageNumber();
                int expectedPageNumber = getStoredPageNumber() + 1;

                assertEquals("Page number was not incremented", expectedPageNumber, currentPageNumber);
            }
            break;
            case PREVIOUS: {
                int currentPageNumber = getPageNavigation().getCurrentPageNumber();
                int expectedPageNumber = getStoredPageNumber() - 1;

                assertEquals("Page number was not decremented", expectedPageNumber, currentPageNumber);
            }
            break;
//            case VIEW_ALL: {
//                pagination_isVisibleOrNot("invisible");
//            }
            //break;
            default:
                fail("unsupport pageLinkOption: " + pageLinkOption);
                break;
        }

    }

    private int getStoredPageNumber() {
        return  (Integer) scenarioSession.getData(CURRENT_PAGE_NUMBER_KEY);
    }

    @And("^the (color|Color|Designer|designer|size|Size) code should be removed from the url$")
    public void the_color_code_should_be_removed_from_the_url(ProductFilterEnum filterType) throws Throwable {
        assertFiltersInUrl(false, webBot.getCurrentUrl(), filterType.getUrlPrefix());
    }

    @When("^the URL will be updated with the page number$")
    public void the_URL_will_be_updated_with_the_page_number() throws Throwable {

        String currentUrl = webBot.getCurrentUrl();

        PageNavigationComponent.PageLinkOption pageLinkOption = (PageNavigationComponent.PageLinkOption) scenarioSession.getData(PAGE_LINK_NAME_KEY);

        if (PageNavigationComponent.PageLinkOption.NEXT.equals(pageLinkOption)) {

            Integer expectedPageNumber = getStoredPageNumber() + 1;

            assertTrue("The current URL does not contain the page number: " + scenarioSession.getData(OLD_PAGE_NUMBER_KEY), currentUrl.contains("pn=" + (expectedPageNumber.toString()) + "&"));
        } else if (PageNavigationComponent.PageLinkOption.PREVIOUS.equals(pageLinkOption)) {

            Integer expectedPageNumber = getStoredPageNumber() - 1;

            assertTrue("The current URL does not contain the page number: " + scenarioSession.getData(OLD_PAGE_NUMBER_KEY), currentUrl.contains("pn=" + (expectedPageNumber.toString()) + "&"));
        }

    }

    //Note: possible refactoring
    @Then("^the URL is updated with the correct (View all|product|Product|outfit|Outfit) parameter$")
    public void the_URL_is_updated_with_the_correct_view_parameter(String viewParameter) throws Throwable {
        String currentUrl = webBot.getCurrentUrl();

        if ("View all".equalsIgnoreCase(viewParameter)) {
            assertThat(currentUrl, containsString("npp=view_all"));
        }

        if ("product".equalsIgnoreCase(viewParameter) || "outfit".equalsIgnoreCase(viewParameter)) {
            assertThat(currentUrl, containsString("image_view=" + viewParameter.toLowerCase()));
        }
    }

    @When("^I click the (product|outfit|Product|Outfit) of imageview link$")
    public void I_click_the_link(ImageViewEnum imageView) throws Throwable {
        scenarioSession.putData(LINK_NAME_KEY, imageView);
        PageNavigationComponent pageNavigationComponent = new PageNavigationComponent(webBot);
        scenarioSession.putData(OLD_PAGE_NUMBER_KEY, pageNavigationComponent.getCurrentPageNumber());
        getNapProductListPage().selectImageViewLink(imageView);
    }

    @And("^pagination is (visible|invisible)$")
    public void pagination_isVisibleOrNot(String visibleOrInvisible) throws Throwable {
        boolean isDisplayed = "visible".equals(visibleOrInvisible);

        assertEquals("pagination is not " + visibleOrInvisible, isDisplayed, getPageNavigation().isPaginationDisplayed());
    }


    @When("^the number of products is (\\d+) pagination is (visible|invisible)$")
    public void the_number_of_products_is_less_than(int numberOfProducts , String visibleOrInvisible) throws Throwable {
        NAPAWSListingPage productListPage = (NAPAWSListingPage) webBot.getCurrentPage();
        int numberofresults= productListPage.getNumberOfResultsFromHeader();
        pagination_isVisibleOrNot(visibleOrInvisible);


    }

    @And("^the correct (product|outfit|Product|Outfit) image is displayed$")
    public void the_correct_image_is_displayed(ImageViewEnum imageView) throws Throwable {
        List<WebElement> productImages = webBot.findElements(ImageViewEnum.PRODUCT_IMAGE_CSS_CLASS_LOCATOR);

        for (WebElement productImage : productImages) {

            //assert that images are taken from the cache, could be refactored into a separate test
            assertThat(productImage.findElement(By.xpath("a/img")).getAttribute("data-src"), containsString("//cache.net-a-porter.com/images/products/"));
            checkProductImageUrl(imageView, productImage);
        }
    }

    private void checkProductImageUrl(ImageViewEnum imageView, WebElement productImage) {
        for (String urlSegment : imageView.getUrlSegments()) {
            if (productImage.findElement(By.xpath("a/img")).getAttribute("data-src").contains(urlSegment))
                {
                    return;
                }
        }
        fail(imageView + " view is not displayed, the url is " + productImage.findElement(By.xpath("a/img")).getAttribute("data-src"));
    }

    @Then("^the correct rollover image (.*) is loaded$")
    public void the_correct_image_parameter_is_loaded(String parameter) throws Throwable {
        List<WebElement> productImages = webBot.findElements(ImageViewEnum.PRODUCT_IMAGE_CSS_CLASS_LOCATOR);

        for (WebElement productImage : productImages) {
            assertThat(productImage.findElement(By.xpath("a/img")).getAttribute("data-src"), containsString(parameter));
        }
    }

    @Given("^I am on a multiple-page (designer|custom|category|boutiques|search) listing page$")
    public void I_am_on_a_multiple_page_product_listing_page(ListingPageType listingPageType) throws Throwable {
        final NAPProductListPage productListPage = (NAPProductListPage) ((NapListingPageRegistry)listingPageRegistry).createAndGoToAProductListPage(listingPageType, null, true);

        doThisAfterListingPageCreation(listingPageType, productListPage);
    }

    private void doThisAfterListingPageCreation(ListingPageType listingPageType, IProductListPage productListPage) {
        productListPage.closeDontMissOutPopup();

        scenarioSession.putData(LISTING_PAGE_TYPE, listingPageType);
        scenarioSession.putData(PRODUCT_LISTING_PAGE_KEY, productListPage);
        scenarioSession.putData(CURRENT_PAGE_NUMBER_KEY, productListPage.getCurrentPageNumber());

        scenarioSession.putData(TOTAL_PAGE_NUMBER, productListPage.getPageNavigationComponent().getPageCount());

        //recordTimeStamp();
    }


    @Given("^I am on a multiple-page (whats_new) AWS listing page$")
    public void I_am_on_a_multiple_page_whats_new_AWS_listing_page(ListingPageType listingPageType) throws Throwable {
        final NAPAWSListingPage productListPage = (NAPAWSListingPage) ((NapListingPageRegistry)listingPageRegistry).createAndGoToAProductListPage(listingPageType, null, true);
        doThisAfterListingPageCreation(listingPageType, productListPage);
    }


    @Given("^I am on a multiple-page (designer|custom|whats_new|category) listing page of (\\d+) pages or more$")
    public void I_am_on_a_multiple_page_category_listing_page_of_pages_or_more(ListingPageType listingPageType, int pageCount) throws Throwable {
        for (int i = 0; i < 10; i ++) {
            I_am_on_a_multiple_page_product_listing_page(listingPageType);

            if (getPageNavigation().getPageCount() >= pageCount) {
                return;
            }
        }
        throw new IllegalStateException("Can't find " + listingPageType + " of more than " + pageCount + "pages");
    }

    @Then("^I am on the first page of products$")
    public void I_am_on_the_first_page_of_products() throws Throwable {
        final IProductListPage productListingPage = (IProductListPage) scenarioSession.getData(PRODUCT_LISTING_PAGE_KEY);
        final PageNavigationComponent pageNavigationComponent = productListingPage.getPageNavigationComponent();
        if (pageNavigationComponent.isPaginationDisplayed())
            assertEquals("We are not on the first page of products", 1, productListingPage.getCurrentPageNumber());
    }

    @Then("^the price order is not changed$")
    public void the_price_order_is_not_changed() throws Throwable {
        the_URL_is_updated_with_the_correct_sorting_parameter();
        the_products_are_displayed_in_correct_price_order();
    }

    @Then("^the filter is preserved$")
    public void the_filter_is_preserved() throws Throwable {
        ProductFilterEnum filterType = (ProductFilterEnum) scenarioSession.getData(FILTER_TYPE_KEY);

        the_URL_will_be_updated_with_the_filter(filterType);

        Set<String> expectedSelectedFilters = (Set<String>) scenarioSession.getData(FILTER_SELECTED_ID_KEY);
        IProductListPage productListPage = getNapProductListPage();

        assertExpectedFilterSelected(productListPage, filterType, expectedSelectedFilters);

        if (ProductFilterEnum.DESIGNER.equals(filterType)) {
            //  assert designer for the product listing
            final Set<String> actualDesignerIds = productListPage.getDesignerIds();

            expectedSelectedFilters = (Set<String>) scenarioSession.getData(FILTER_SELECTED_ID_KEY);

            assertEquals("Designer mismatch!!!", expectedSelectedFilters, actualDesignerIds);
        }
    }

    private void assertExpectedFilterSelected(IProductListPage productListPage, ProductFilterEnum filterType, Set<String> expectedDesignerNames) {
        List<String> selectedFilters = productListPage.getFilters(filterType, true);

        assertEquals("Selected filter mismatch!!!", expectedDesignerNames, Sets.newHashSet(selectedFilters));
    }

    @And("^I select all filters of (color|Color|Designer|designer|size|Size) by creating a new Url$")
    public void I_select_all_filters_of_filterType_by_creating_a_new_Url(ProductFilterEnum productFilter) throws Throwable {
        scenarioSession.putData(FILTER_TYPE_KEY, productFilter);

        IProductListPage napProductListPage = getNapProductListPage();

        Map.Entry<Set<String>, Set<String>> entry = napProductListPage.selectAllItemsForFilterByGoingToUrlContainsAllFilterItemValues(productFilter);

        scenarioSession.putData(FILTER_SELECTED_ID_KEY, entry.getKey());
        scenarioSession.putData(FILTER_SELECTED_NAME_KEY, entry.getValue());
    }

    @When("^I go to a bookmarked (designer|custom|category|whats_new) listing URL$")
    public void I_go_to_a_bookmarked_product_listing_URL(ListingPageType listingPageType) throws Throwable {

        I_am_on_a_multiple_page_product_listing_page(listingPageType);

        if (!ListingPageType.DESIGNER.equals(listingPageType)) {
            I_select_all_filters_of_filterType_by_creating_a_new_Url(ProductFilterEnum.DESIGNER);
            final Set<String> designerFilter = (Set<String>) scenarioSession.getData(FILTER_SELECTED_ID_KEY);
            scenarioSession.putData(BOOKMARK_DESIGNER_FILTER, designerFilter);
        }
        if (ListingPageType.DESIGNER.equals(listingPageType)) {
            I_select_all_filters_of_filterType_by_creating_a_new_Url(ProductFilterEnum.COLOR);
            final Set<String> colorFilter = (Set<String>) scenarioSession.getData(FILTER_SELECTED_NAME_KEY);
            scenarioSession.putData(BOOKMARK_COLOR_FILTER, colorFilter);
        }
        I_sort_by_Price(SortOrder.PRICE_HIGH.toString());
        scenarioSession.putData(SORT_ORDER, SortOrder.PRICE_HIGH);

        I_click_the_linkName_link_of_page_navigation("Next");
        scenarioSession.putData(OLD_PAGE_NUMBER_KEY, 2);

        final String bookmarkedUrl = webBot.getCurrentUrl();

        NAPProductListPage productListPage = (NAPProductListPage) ((NapListingPageRegistry)listingPageRegistry).lookupProductListPage(Consts.GENERIC_LISTING_PAGE_NAME);

        productListPage.setPath(bookmarkedUrl.substring(bookmarkedUrl.lastIndexOf("/Shop")));

        productListPage.go();
    }

    @And("^the page number is (\\d)$")
    public void the_page_number_is_correct(int pageNumber) throws Throwable {
        NAPProductListPage productListPage = (NAPProductListPage) ((NapListingPageRegistry)listingPageRegistry).lookupProductListPage(Consts.GENERIC_LISTING_PAGE_NAME);
        final int currentPageNumber = productListPage.getCurrentPageNumber();

        assertEquals("Retrieved page number does not match stored page number", pageNumber, currentPageNumber);
    }

    @Then("^the correct filters are selected$")
    public void the_correct_filters_are_selected() throws Throwable {

        NAPProductListPage productListPage = (NAPProductListPage) ((NapListingPageRegistry)listingPageRegistry).lookupProductListPage(Consts.GENERIC_LISTING_PAGE_NAME);
        Thread.sleep(500);

        if (!ListingPageType.DESIGNER.equals(scenarioSession.getData(LISTING_PAGE_TYPE))) {
            assertEquals("Designer filters do not match", scenarioSession.getData(BOOKMARK_DESIGNER_FILTER), Sets.newHashSet(productListPage.getFilters(ProductFilterEnum.DESIGNER, true)));
        }
        if (ListingPageType.DESIGNER.equals(scenarioSession.getData(LISTING_PAGE_TYPE))) {
            assertEquals("Color filters do not match", scenarioSession.getData(BOOKMARK_COLOR_FILTER), Sets.newHashSet(productListPage.getFilters(ProductFilterEnum.COLOR, true)));
        }
    }

    @Then("^only products from that designer should be displayed$")
    public void only_products_from_that_designer_should_be_displayed() throws Throwable {

        Set<String> selectedFilteredDesigner = new HashSet<String>();

        try {
        selectedFilteredDesigner = (Set<String>) scenarioSession.getData(FILTER_SELECTED_NAME_KEY);
        }
        catch (ClassCastException e) {
            String selectedDesigner = (String) scenarioSession.getData(FILTER_SELECTED_NAME_KEY);
            selectedFilteredDesigner.add(selectedDesigner);
        }

        final Set<String> allProductDesignersInPage = getNapProductListPage().getAllProductDesigners();

        assertThat(selectedFilteredDesigner, is(equalTo(allProductDesignersInPage)));
    }

    private IProductListPage getNapProductListPage() {
        return (IProductListPage) scenarioSession.getData(PRODUCT_LISTING_PAGE_KEY);
    }

    @Then("^the bookmarked page is opened correctly$")
    public void the_bookmarked_page_is_opened_correctly() throws Throwable {
        the_products_are_displayed_in_correct_price_order();
        the_correct_filters_are_selected();
        the_page_number_is_correct((Integer) (scenarioSession.getData(OLD_PAGE_NUMBER_KEY)));
    }

    @Given("^I goto (.*) in the (.*) product listing page$")
    public void I_goto_a_subcategory_in_the_category_product_listing_page(String navLevel2, String navLevel1) throws Throwable {
        NAPProductListPage napProductListPage = new NAPProductListPage(Consts.GENERIC_LISTING_PAGE_NAME, "Shop/"+navLevel1.replace(" ", "_")+"/"+navLevel2.replace(" ", "_"));

//        NAPProductListPage napProductListPage = (NAPProductListPage) listingPageRegistry.createAndGoToAProductListPage(navLevel1, navLevel2, false);
        scenarioSession.putData(PRODUCT_LISTING_PAGE_KEY, napProductListPage);
        scenarioSession.putData(LISTING_PAGE_TYPE, navLevel1);
        napProductListPage.setWebBot(webBot);

        napProductListPage.go();

        napProductListPage.closeDontMissOutPopup();
        napProductListPage.closeApacWelcomeMessage();

        recordTimeStamp();
    }

    private void saveNumberOfFacetsInSession(IProductListPage napProductListPage) {
        scenarioSession.putData(ALL_AVAILABLE_DESIGNERS_KEY, napProductListPage.getFilters(ProductFilterEnum.DESIGNER, false).size());
        scenarioSession.putData(ALL_AVAILABLE_COLORS_KEY, napProductListPage.getFilters(ProductFilterEnum.COLOR, false).size());

        try {
            scenarioSession.putData(ALL_AVAILABLE_SIZES_KEY, napProductListPage.getFilters(ProductFilterEnum.SIZE, false).size());
        }
        catch (PageElementNotFoundException pageElementNotFoundException){
            System.out.print("\nThere are no size filters on this listing page\n");
            }
    }

    @When("^I randomly select (\\d) navigation level3 categories$")
    public void I_randomly_select_two_navigation_level_categories(int count) throws Throwable {
        NAPProductListPage napProductListPage = (NAPProductListPage) webBot.getCurrentPage();
        scenarioSession.putData(INITIAL_NUMBER_OF_RESULTS, napProductListPage.getNumberOfResultsFromHeader());

        Set<String> selectedLevel3 = getNapProductListPage().randomlySelectLevel3CategoryOfSelectedLevel2Category(count);

        scenarioSession.putData(NAV_LEVEL_3_KEY, selectedLevel3);
        Thread.sleep(100);
    }

    @When("^the Url only contains required level3 categories$")
    public void the_Url_only_contains_required_level_categories() throws Throwable {
        // Note: create utils for this
        URL currentUrl = new URL(webBot.getCurrentUrl());
        List<NameValuePair> nameValuePairs = URLEncodedUtils.parse(currentUrl.getQuery(), Charset.defaultCharset(), '&');
        Set<String> actualValues = null;

        for (NameValuePair nameValuePair : nameValuePairs) {
            if (nameValuePair.getName().equalsIgnoreCase("level3Filter"))
                actualValues = Sets.newHashSet(nameValuePair.getValue().split(";"));
        }

        Set<String> expectedNavLevel3 = (Set<String>) scenarioSession.getData(NAV_LEVEL_3_KEY);
        assertNotNull("No level 3 categories exist in the url: " + currentUrl);
        assertEquals(expectedNavLevel3, actualValues);
    }

    @When("^only required level3 categories are selected in the left navigation$")
    public void only_required_level_categories_are_selected_in_the_left_navigation() throws Throwable {
        Set<String> actualNavLevel3 = getNapProductListPage().getSelectedLevel3Categories();

        Set<String> expectedNavLevel3 = (Set<String>) scenarioSession.getData(NAV_LEVEL_3_KEY);

        assertEquals(expectedNavLevel3, actualNavLevel3);
    }

    @When("^I randomly un-select (\\d+) level3 category$")
    public void I_randomly_un_select_level_category(int count) throws Throwable {

        NAPProductListPage napProductListPage = (NAPProductListPage) webBot.getCurrentPage();
        scenarioSession.putData(INITIAL_NUMBER_OF_RESULTS, napProductListPage.getNumberOfResultsFromHeader());
        Set<String> unselected = getNapProductListPage().randomlyUnselectLevel3CategoryOfSelectedLevel2Category(count);

        Set<String> previousSelected = (Set<String>) scenarioSession.getData(NAV_LEVEL_3_KEY);

        scenarioSession.putData(NAV_LEVEL_3_KEY, Sets.difference(previousSelected, unselected));
    }

    @When("^I randomly select a different level2 navigation category$")
    public void I_randomly_select_a_different_level2_navigation_category() throws Throwable {
        NAPProductListPage napProductListPage = (NAPProductListPage) webBot.getCurrentPage();
        scenarioSession.putData(INITIAL_NUMBER_OF_RESULTS, napProductListPage.getNumberOfResultsFromHeader());
        getNapProductListPage().randomlySelectOneUnselectedLevel2Category();
    }

    @And("^I randomly select a multiple-page different level2 navigation category$")
    public void I_randomly_select_a_multiple_page_different_level2_navigation_category() throws Throwable {
        //try 5 times to get a level2 navcat that has multiple pages
        int i = 5;
        I_randomly_select_a_different_level2_navigation_category();
        PageNavigationComponent pageNavigationComponent = getPageNavigation();

        do {
            if (!pageNavigationComponent.isPaginationDisplayed()) {
                i--;
                I_randomly_select_a_different_level2_navigation_category();
            }
            else {
                break;
            }
        }
        while(i>0);
    }

    @When("^the subcategory selection is cleared$")
    public void the_subcategory_selection_is_cleared() throws Throwable {
        Set<String> selectedLevel3Categories = getNapProductListPage().getSelectedLevel3Categories();

        assertTrue("subcategory selection is not cleared", selectedLevel3Categories.isEmpty());
    }


    @And("^the page header is updated with the number of products$")
    public void the_page_header_is_updated_with_the_number_of_products() throws Throwable {

        IProductListPage napProductListPage = (IProductListPage) webBot.getCurrentPage();
          if (scenarioSession.getData(FILTER_TYPE_KEY) != null)
          {
              assertNotEquals("Number of results from header has not changed", (int) (Integer) scenarioSession.getData(INITIAL_NUMBER_OF_RESULTS), napProductListPage.getNumberOfResultsFromHeader());
              assertNotEquals("Number of results in the header is 0, probably a bug", napProductListPage.getNumberOfResultsFromHeader(), 0);
          }
        else if (scenarioSession.getData(SORT_ORDER) != null)
        {
            assertEquals("Number of results has changed!", (int) (Integer) scenarioSession.getData(INITIAL_NUMBER_OF_RESULTS), napProductListPage.getNumberOfResultsFromHeader());
            assertNotEquals("Number of results in the header is 0, probably a bug", napProductListPage.getNumberOfResultsFromHeader(), 0);

        }
         else if (scenarioSession.getData(NAV_LEVEL_3_KEY) != null) {
              assertNotEquals("Number of results from header has not changed", (int) (Integer) scenarioSession.getData(INITIAL_NUMBER_OF_RESULTS), napProductListPage.getNumberOfResultsFromHeader());
          }
     }

    @Given("^I select all the navigation level 3 categories$")
    public void I_select_all_the_navigation_level_categories() throws Throwable {

        final IProductListPage napProductListPage = getNapProductListPage();

        scenarioSession.putData(NAV_LEVEL_3_KEY, napProductListPage.selectAllLevel3CategoryOfSelectedLevel2Category());
    }

    @Then("^I have (.*) in sorting drop-down$")
    public void I_have_Default_New_In_High_Low_in_sorting_drop_down(String orderList) throws Throwable {

        List<SortOrder> actualSortOptions = getNapProductListPage().getSortingDropdownOptions();
        List<SortOrder> expectedOptions = new ArrayList<SortOrder>();
        for (String string : orderList.split(",")) {
            expectedOptions.add(SortOrder.valueOf(string.trim().replace(" ", "_").toUpperCase()));
        }
        assertEquals(expectedOptions, actualSortOptions);
    }

    @When("^I sort by a randomly selected order$")
    public void I_sort_by_a_randomly_selected_order() throws Throwable {
        SortOrder selectedOrder = getNapProductListPage().randomlySelectSortingOrder(SortOrder.DEFAULT);

        scenarioSession.putData(SORT_ORDER, selectedOrder);
        scenarioSession.putData(INITIAL_NUMBER_OF_RESULTS, getNapProductListPage().getNumberOfResultsFromHeader());
    }

    @When("^I am currently on a (Clothing|Bags|Shoes|Lingerie|Accessories|Search) sale listing page$")
    public void I_am_currently_on_a_sale_listing_page(String listingPageType) throws Throwable {
        NAPSaleListingPage saleListingPage = (NAPSaleListingPage) ((NapListingPageRegistry)listingPageRegistry).createAndGoToAProductListPage(ListingPageType.SALE, listingPageType, false);

        scenarioSession.putData(PRODUCT_LISTING_PAGE_KEY, saleListingPage);

        recordTimeStamp();
    }

    @And("^I am currently on a multiple page (Clothing|Bags|Shoes|Lingerie|Accessories|Search) sale listing page$")
    public void I_am_currently_on_a_multiple_page_category_sale_listing_page(String listingPageType) throws Throwable {
        NAPSaleListingPage saleListingPage = (NAPSaleListingPage) ((NapListingPageRegistry)listingPageRegistry).createAndGoToAProductListPage(ListingPageType.SALE, listingPageType, true);

        scenarioSession.putData(PRODUCT_LISTING_PAGE_KEY, saleListingPage);

        recordTimeStamp();
    }

    @Then("^the correct sorting option is selected in the drop-down$")
    public void the_correct_sorting_option_is_selected_in_the_drop_down() throws Throwable {
        SortOrder actualSortOrder = getNapProductListPage().getSelectedSortingDropdownOption();

        SortOrder expectedOrder = (SortOrder) scenarioSession.getData(SORT_ORDER);

        assertEquals(expectedOrder, actualSortOrder);
    }

    @When("^I am on a multiple page product listing page of (\\d+) pages$")
    public void I_am_on_a_multiple_page_product_listing_page_of_x_pages(int totalPageNumber) throws Throwable {
        int actualTotalPageNumber;
        NAPProductListPage productListPage;
        int totalTries = MAX_TRIES;
        do {
            if (totalTries-- <= 0)
            {
                throw new IllegalStateException("Can't find product listing page contains equal or less than " + totalPageNumber + " pages");
            }
            productListPage = (NAPProductListPage) ((NapListingPageRegistry)listingPageRegistry).createAndGoToAProductListPage(ListingPageType.CATEGORY, null, true);
            actualTotalPageNumber = productListPage.selectFiltersOfCeilPageNumberNew(ProductFilterEnum.COLOR, totalPageNumber);
        }
        while (actualTotalPageNumber > totalPageNumber);

        scenarioSession.putData(INITIAL_NUMBER_OF_RESULTS, productListPage.getNumberOfResultsFromHeader());
        scenarioSession.putData(TOTAL_PAGE_NUMBER, actualTotalPageNumber);
        scenarioSession.putData(PRODUCT_LISTING_PAGE_KEY, productListPage);
        scenarioSession.putData(CURRENT_PAGE_NUMBER_KEY, getNapProductListPage().getCurrentPageNumber());
    }

    @Then("^I should see all page numbers$")
    public void I_should_see_all_page_numbers() throws Throwable {
        PageNavigationComponent pageNavigationComponent = getPageNavigation();
        assertTrue("Pagination is not visible", pageNavigationComponent.isPaginationDisplayed());

        List<String> pageNumbers = pageNavigationComponent.getAllPageNumbers();

        int totalPageNumber = (Integer) scenarioSession.getData(TOTAL_PAGE_NUMBER);
        List<String> expectedPageNumbers = new ArrayList<String>();
        for(int i = 1; i <= totalPageNumber; i++) {
            expectedPageNumbers.add(i+"");
        }

        assertEquals(expectedPageNumbers, pageNumbers);
    }

    @Then("^all the page numbers have links except the current page number$")
    public void all_the_page_numbers_have_links_except_the_current_page_number() throws Throwable {
        PageNavigationComponent pageNavigationComponent = getPageNavigation();

        List<String> links = pageNavigationComponent.getAllLinksForPageNumber();
        List<String> allPageNumbers = pageNavigationComponent.getAllPageNumbers();


        int currentPageNumber = pageNavigationComponent.getCurrentPageNumber();
        int totalPageNumber = allPageNumbers.size();


        // better way of doing assertions ?
        for(int pageNumberIndex = 0, linkNumberIndex = 0; pageNumberIndex < totalPageNumber; pageNumberIndex++) {
            String pageNumber = allPageNumbers.get(pageNumberIndex);

            // "..." does not have page link
            if (!PageNavigationComponent.DOTDOTDOT.equals(pageNumber)) {

                String currentLink = links.get(linkNumberIndex);

                if (currentPageNumber == Integer.valueOf(pageNumber)) {
                    assertTrue("The link (" + currentLink + ") is wrong for " + pageNumber + ". " + links, currentLink.isEmpty());
                }
                else {
                    assertFalse("The link (" + currentLink+ ") is wrong for " + pageNumber + ". " + links, currentLink.isEmpty());
                }

                linkNumberIndex++;
            }
        }
    }

    private PageNavigationComponent getPageNavigation() {
        return getNapProductListPage().getPageNavigationComponent();
    }

    @Then("^I should see a summary of (.*)$")
    public void I_should_see_a_summary_of_M(String pageSummary) throws Throwable {
        PageNavigationComponent pageNavigationComponent = getPageNavigation();

        assertTrue("Pagination is not visisble", pageNavigationComponent.isPaginationDisplayed());
        assertEquals(createExpectedSummary(pageSummary), pageNavigationComponent.getAllPageNumbers());
    }

    private List<String> createExpectedSummary(String pageSummary) {
        List<String> expectedSummary = Lists.newArrayList(pageSummary.split(" "));

        String s = expectedSummary.get(expectedSummary.size() - 1);
        if ("$T".equals(s)) {
            expectedSummary.remove(s);

            expectedSummary.add(scenarioSession.getData(TOTAL_PAGE_NUMBER)+"");
        }

        return expectedSummary;
    }


    @When("^I navigate to the (First|Last|Third|Third Last) page$")
    public void I_navigate_to_the_First_page(String firstLast) throws Throwable {
        PageNavigationComponent pageNavigationComponent = getPageNavigation();

//        if ("WHATS_NEW".equals(scenarioSession.getData(LISTING_PAGE_TYPE).toString()))
//            pageNavigationComponent.selectLink(PageNavigationComponent.PageLinkOption.NEXT);

        int currentPageNumber = pageNavigationComponent.gotoPage(PageNavigationComponent.PageNumberOption.valueOf(firstLast.replace(" ", "_").toUpperCase()));

        scenarioSession.putData(CURRENT_PAGE_NUMBER_KEY, currentPageNumber);
    }

    @Then("^the (Previous|Next|View 60|view 60|View all|View All) link is (selected$|unselected)$")
    public void the_Previous_link_is_disabled(String pageLinkOptionString, String selectedUnselected) throws Throwable {
        PageNavigationComponent.PageLinkOption pageLinkOption = PageNavigationComponent.PageLinkOption.valueOf(pageLinkOptionString.toUpperCase().replace(" ", "_"));

        PageNavigationComponent pageNavigationComponent = getPageNavigation();

        boolean isEnabled = "selected".equals(selectedUnselected);

        assertEquals(isEnabled, pageNavigationComponent.isDisplayed(pageLinkOption));
    }


    @When("^I click the (Previous|Next|View 60|view 60|View all|View All) link of page navigation$")
    public void I_click_the_linkName_link_of_page_navigation(String pageLinkOptionString) throws Throwable {
        PageNavigationComponent.PageLinkOption pageLinkOption = PageNavigationComponent.PageLinkOption.valueOf(pageLinkOptionString.toUpperCase().replace(" ", "_"));

        PageNavigationComponent pageNavigationComponent = getPageNavigation();

        try {
            if ("WHATS_NEW".equals(scenarioSession.getData(LISTING_PAGE_TYPE).toString()))
                pageNavigationComponent.selectLink(PageNavigationComponent.PageLinkOption.NEXT);
        }
        catch (NullPointerException ignored){
        }

        if (!PageNavigationComponent.PageLinkOption.NEXT.equals(pageLinkOption) && !PageNavigationComponent.PageLinkOption.NEXT.equals(pageLinkOption)) {
            int currentPageNumber = pageNavigationComponent.getCurrentPageNumber();
            scenarioSession.putData(CURRENT_PAGE_NUMBER_KEY, currentPageNumber);
        }

        pageNavigationComponent.selectLink(pageLinkOption);

        scenarioSession.putData(PAGE_LINK_NAME_KEY, pageLinkOption);
    }

    @Then("^I should see the first and the last three page numbers$")
    public void I_should_see_the_last_three_page_numbers() throws Throwable {
        PageNavigationComponent pageNavigationComponent = getPageNavigation();

        assertTrue("Pagination is not visible", pageNavigationComponent.isPaginationDisplayed());
        assertEquals(createExpectedPageNumbers(pageNavigationComponent), pageNavigationComponent.getAllPageNumbers());
    }

    private List<String> createExpectedPageNumbers(PageNavigationComponent pageNavigationComponent) {
        int pageCount = pageNavigationComponent.getPageCount();

        if (pageCount > 6) {
            return Lists.newArrayList("1", "…", (pageCount - 2) + "", (pageCount - 1) + "", pageCount + "");
        }
        else {
            List<String> expectedPageNumbers = new ArrayList<String>();
            for(int i = 1; i <= pageCount; i++) {
                  expectedPageNumbers.add(i+"");
            }

            return expectedPageNumbers;
        }
    }


    @When("^I select (color|Color|size|Size|designer|Designer) filter to goto a single page listing$")
    public void I_select_designer_filter_to_goto_a_single_page_listing(ProductFilterEnum productFilter) throws Throwable {
        IProductListPage napProductListPage = getNapProductListPage();

        String originalPath = napProductListPage.getPath();

        int tries = MAX_TRIES;

        while (napProductListPage.isMultiplePages())
        {
            if (tries-- <= 0) {
                throw new IllegalStateException("Can't get to a single page listing");
            }

            reloadPage(napProductListPage, originalPath);

            napProductListPage.randomlySelectOneItem(productFilter);
        };
    }

    private void reloadPage(IProductListPage napProductListPage, String originalPath) {
        napProductListPage.setPath(originalPath);
        napProductListPage.go();
        WaitUtil.waitForSpinnerToAppearAndDisappear(webBot);
    }


    @Given("^I navigate to any non-First page$")
    public void I_navigate_to_any_non_First_page() throws Throwable {

        PageNavigationComponent pageNavigationComponent = getPageNavigation();

        if ("WHATS_NEW".equals(scenarioSession.getData(LISTING_PAGE_TYPE).toString()))
            pageNavigationComponent.selectLink(PageNavigationComponent.PageLinkOption.NEXT);

        pageNavigationComponent.gotoAnyNonFirstPage();

        assertNotEquals(1, pageNavigationComponent.getCurrentPageNumber());
    }


    @Then("^pagination should return to page 1$")
    public void pagination_should_return_to_page() throws Throwable {
        assertEquals(1, getPageNavigation().getCurrentPageNumber());
    }

    @Then("^(.*) filter is open$")
    public void Filter_is_open(ProductFilterEnum filterType) throws Throwable {
        IProductListPage napProductListPage = getNapProductListPage();
         if (ProductFilterEnum.COLOR.equals(filterType)){
        assertTrue("Color filter is not open", napProductListPage.isFilterOpen(ProductFilterEnum.COLOR));
         }
         else if (ProductFilterEnum.SIZE.equals(filterType)) {
          assertTrue("Size filter is not open", napProductListPage.isFilterOpen(ProductFilterEnum.SIZE));
        }
    }

    //TODO: refactor this
    @Then("^all other facets except (.*) facets are still displayed$")
    public void all_other_facets_except_filterType_facets_are_still_displayed(ProductFilterEnum filterType) throws Throwable {

        switch (filterType) {
            case DESIGNER:   {
                compareFilterFacets(ProductFilterEnum.COLOR, (Integer) scenarioSession.getData(ALL_AVAILABLE_COLORS_KEY));
                int numberOfSizeFacets = 0;
                try{
                    numberOfSizeFacets = (Integer) scenarioSession.getData(ALL_AVAILABLE_SIZES_KEY);
                }
                catch (NullPointerException npe){
                    break;
                }
                compareFilterFacets(ProductFilterEnum.SIZE, numberOfSizeFacets);
                break;
            }
            case COLOR: {
                compareFilterFacets(ProductFilterEnum.DESIGNER, (Integer) scenarioSession.getData(ALL_AVAILABLE_DESIGNERS_KEY));
                int numberOfSizeFacets = 0;
                try{
                    numberOfSizeFacets = (Integer) scenarioSession.getData(ALL_AVAILABLE_SIZES_KEY);
                }
                catch (NullPointerException npe){
                    break;
                }
                compareFilterFacets(ProductFilterEnum.SIZE, numberOfSizeFacets);
                break;
            }
            case SIZE: {
                compareFilterFacets(ProductFilterEnum.COLOR, (Integer) scenarioSession.getData(ALL_AVAILABLE_COLORS_KEY));
                compareFilterFacets(ProductFilterEnum.DESIGNER, (Integer) scenarioSession.getData(ALL_AVAILABLE_DESIGNERS_KEY));
                break;
            }
        }
    }

    private void compareFilterFacets(ProductFilterEnum filterType, int initialFilterElements){
        int currentFilterElements = 0;
        try{
            currentFilterElements = getNapProductListPage().getFilters(filterType, false).size();
            }
        catch (PageElementNotFoundException e) {
            System.err.println("There are no "+filterType+" filters on this page");
        }
        assertEquals("Number of filter facets is not the same, some " + filterType + " facets were hidden:", currentFilterElements, initialFilterElements);
    }

    @Then("^all filter facets are still displayed$")
    public void all_filter_facets_are_still_displayed() throws Throwable {
        compareFilterFacets(ProductFilterEnum.COLOR, (Integer) scenarioSession.getData(ALL_AVAILABLE_COLORS_KEY));
        compareFilterFacets(ProductFilterEnum.DESIGNER, (Integer) scenarioSession.getData(ALL_AVAILABLE_DESIGNERS_KEY));
        compareFilterFacets(ProductFilterEnum.SIZE, (Integer) scenarioSession.getData(ALL_AVAILABLE_SIZES_KEY));
    }

    @And("^I save the current filter facets$")
    public void I_save_the_current_filter_facets() throws Throwable {
        saveNumberOfFacetsInSession(getNapProductListPage());
    }

    @Then("^drop down is displayed$")
    public void drop_down_is_displayed() throws Throwable {
        IProductListPage napProductListPage = getNapProductListPage();
        if ((Integer) scenarioSession.getData(INITIAL_NUMBER_OF_RESULTS) >60) {
            assertTrue(napProductListPage.isDropDownDisplayed());
        }
        else{
            assertFalse(napProductListPage.isDropDownDisplayed());
        }
    }

    @And("^the products are displayed in the (£|\\$|€) currency$")
    public void the_products_are_displayed_in_the_correct_currency(String currency) throws Throwable {
        NAPSaleListingPage napSaleListingPage = (NAPSaleListingPage) scenarioSession.getData(PRODUCT_LISTING_PAGE_KEY);
        HashMap<String, String> pidAndCurrencyMap = napSaleListingPage.getPidAndCurrencyMap();
        for (String pid : pidAndCurrencyMap.keySet()) {
            assertTrue("PID:" + pid + " with currency " + pidAndCurrencyMap.get(pid) + " does not match expected currency " + currency,
                    currency.equalsIgnoreCase(pidAndCurrencyMap.get(pid)));
        }
    }

    @And("^the listing page has at least one product$")
    public void the_listing_page_has_at_least_one_product() throws Throwable {

        IProductListPage napProductListPage = (IProductListPage) scenarioSession.getData(PRODUCT_LISTING_PAGE_KEY);
        assertThat(napProductListPage.getAllProductsPids().size(), greaterThanOrEqualTo(1));
    }

    @When("^I click on the (.*) in the lefthand nav$")
    public void I_click_on_the_subcategory_in_the_lefthand_nav(String subCategory) throws Throwable {

        NAPSaleListingPage napSaleListingPage = (NAPSaleListingPage) scenarioSession.getData(PRODUCT_LISTING_PAGE_KEY);
        scenarioSession.putData(CONDITION_KEY, napSaleListingPage.clickOnSecondLevelCategory(subCategory));
        recordTimeStamp();
    }

    @And("^the url contains the (.*) and (.*) parameters$")
    public void the_url_contains_the_category_and_subcategory_parameters(String category, String subCategory) throws Throwable {
        String currentUrl = webBot.getCurrentUrl();

        assertThat(currentUrl, containsString("/" + category));
        assertThat(currentUrl, containsString(subCategory));
    }

    @When("^I select (.*) from size filter$")
    public void I_select_sizescheme_from_size_filter(String sizeScheme) {
        getNapProductListPage().changeSizeSchemeDropDownTo(sizeScheme);
        scenarioSession.putData("totalNumberOfSizesVisible", getNapProductListPage().getNumberOfSizesVisible());
    }

    @And("^size scheme should have same number of sizes$")
    public void size_scheme_should_have_same_number_of_sizes(){
        assertThat(getNapProductListPage().getNumberOfSizesVisible(), is((Object)scenarioSession.getData(TOTAL_NUMBER_OF_SIZE_FACETS_VISIBLE_KEY)));
    }

    @And("^the URL is updated with correct (.*) param$")
    public void the_url_is_updated_with_correct_size_scheme(String sizeSchemeCode){
        String currentUrl = webBot.getCurrentUrl();
        assertThat(currentUrl, containsString("sizeScheme=" + sizeSchemeCode));
    }

    @And("^I select (.*) from left navigation$")
    public void I_select_shoes_from_left_navigation(String category) throws Throwable {
        getNapProductListPage().selectFirstLevelCategoryFromLeftHandNav(category);
    }

    @And ("^size scheme drop down is visible$")
    public void size_scheme_drop_down_is_visible() {
        assertNotNull("Size scheme dropdown is not displayed", getNapProductListPage().getSizeSchemeDropdown());
    }

    @When("^I go to (.*) level3 category$")
    public void I_go_to_level_Category_category(String level3Category) throws Throwable {
        getNapProductListPage().selectLevel3Category(level3Category);
    }

    @When("^I mouseover the first product image$")
    public void I_mouseover_the_first_product_image() throws Throwable {
        getNapProductListPage().mouseOverTheFirstImage();
    }

    @Then("^the correct rollover image (.*) is loaded for the first product$")
    public void the_correct_rollover_image_parameter_is_loaded_for_the_first_product(String param) throws Throwable {
        assertThat(getNapProductListPage().getFirstProductImage(), containsString(param));
    }

    @Then("^I am taken to the AZ Designers page$")
    public void I_am_taken_to_the_AZ_Designer_page() throws Throwable {
    assertTrue("Url does not contain AZ Designers, this may not be the Designers landing page", webBot.getCurrentUrl().contains("AZDesigners"));
    }

    @When("^I add any NAP product from the current listing page to the shopping bag$")
    public void I_add_any_NAP_product_from_the_current_listing_page_to_the_shopping_bag() throws Throwable {
        getNapProductListPage().clickOnARandomInStockProduct();
        List<String> listOfSkus = productDetailsPage.getSKUs();
        String skuToAddToBag = null;
        //sold out skus start with so_, so a check for a number on the first digit will avoid trying to add a sold out sku to bag
        for (String sku : listOfSkus) {
            if (Character.isDigit(sku.charAt(0))) {
               skuToAddToBag = sku;
               break;
            }
        }
        assertNotNull("Could not find an appropriate sku to add to bag for product with first sku "+listOfSkus.get(0), skuToAddToBag);
        scenarioSession.putData(ITEMS_ADDED_TO_BAG, skuToAddToBag);
        productDetailsPage.addIntoShoppingBag(skuToAddToBag);
    }

    @And("^I select the first designer$")
    public void I_select_the_first_designer() throws Throwable {
        designerAToZPage.clickOnFirstDesignerInAZPage();
    }

    @Then("^I am taken to that designer page$")
    public void I_am_taken_to_that_designer_page() throws Throwable {
        String pageTitle = designerAToZPage.getTitle().toLowerCase().trim();
        assertThat("Designer title", pageTitle, not(isEmptyOrNullString()));
    }

    @Then("^each product should have designer, description and price information$")
    public void each_product_should_have_designer_description_and_price_information() throws Throwable {
        NAPProductListPage listingPage = (NAPProductListPage) scenarioSession.getData(PRODUCT_LISTING_PAGE_KEY);
        List<String> allProductsPids = listingPage.getAllProductsPids();
        List<String> allProductDesignersWithDuplicates = listingPage.getAllProductDesignersWithDuplicates();
        List<Integer> allProductPrices = listingPage.getAllProductPrices();
        List<String> allProductDescriptions = listingPage.getAllProductDescriptions();

        assertThat(allProductsPids.size(), allOf(is(allProductDescriptions.size()), is(allProductDesignersWithDuplicates.size()), is(allProductPrices.size())));

        for (int i=0;i<allProductsPids.size();i++) {
           assertThat(allProductsPids.get(i), not(isEmptyOrNullString()));
           assertThat(allProductDescriptions.get(i), not(isEmptyOrNullString()));
           assertThat(allProductDesignersWithDuplicates.get(i), not(isEmptyOrNullString()));
           assertThat(allProductPrices.get(i).toString(), not(isEmptyOrNullString()));
        }
    }

    @And("^the products are displayed in the correct currency$")
    public void the_products_are_displayed_in_the_correct_currency() throws Throwable {
        NAPProductListPage listingPage = (NAPProductListPage) scenarioSession.getData(PRODUCT_LISTING_PAGE_KEY);
        String countryName = listingPage.getCountryName();
        String currencyForCountryName = CountryAndCurrencyUtil.getCurrencyCodeForCountryName(countryName);
        List<String> allProductCurrencies = listingPage.getAllProductCurrencies();
        for (String productCurrency : allProductCurrencies) {
            assertThat(productCurrency, is(currencyForCountryName));
        }
    }


    @When("^I add any NAP product from the current listing page to the Wish list$")
    public void I_add_any_NAP_product_from_the_current_listing_page_to_the_Wish_list() throws Throwable {
        getNapProductListPage().clickOnARandomProduct();
        List<String> listOfSkus = productDetailsPage.getSKUs();
        String skuToAddToWishList = null;
        //sold out skus start with so_, so a check for a number on the first digit will avoid trying to add a sold out sku to wish list
        for (String sku : listOfSkus) {
            if (Character.isDigit(sku.charAt(0))) {
                skuToAddToWishList = sku;
                break;
            }
        }
        assertNotNull("Could not find an appropriate sku to add to Wish list for product with first sku "+listOfSkus.get(0), skuToAddToWishList);
        scenarioSession.putData(ProductDetailSteps.PRODUCT_KEY, new Product(null, skuToAddToWishList));
        productDetailsPage.addIntoWishlist(skuToAddToWishList);
    }


    @And("^I am currently on a (All_Sportswear|Gym_and_Cross-Fit|Tennis|Run|Yoga_and_Dance|Swim_and_Surf|Equestrian|Après|Golf|Accessories|Outdoor|Sailing|Ski) (sport) listing page$")
    public void I_am_currently_on_a_sport_listing_page(String sportCategoryPage, ListingPageType listingPageType) throws Throwable {
        NAPAWSListingPage sportListingPage = (NAPAWSListingPage) ((NapListingPageRegistry)listingPageRegistry).createAndGoToAProductListPage(listingPageType, sportCategoryPage, false);
        scenarioSession.putData(PRODUCT_LISTING_PAGE_KEY, sportListingPage);

    }

    @Then("^the URL contains the correct (.*) name$")
    public void The_url_contains_the_correct_subcategory_name(String category) throws Throwable{
        assertTrue("Current URL does not contain " + category, webBot.getCurrentUrl().contains(category.replace(" ", "_")));
    }

    @And("^I visit a (.*) url$")
    public void I_visit_a_url(String url) throws Throwable {
        NAPProductListPage napProductListPage = new NAPProductListPage("pageName", url);
        webBot.goToPage(napProductListPage);
        Thread.sleep(2000);
    }

    @Then("^after all redirects the url should end with (.*)$")
    public void after_all_redirects_the_url_should_end_with_route(String route) throws Throwable {
        assertTrue("Current url (" + webBot.getCurrentUrl() + ") contains " + route, webBot.currentUrlContains(route));
    }

    @And("^an error page is not shown$")
    public void an_error_page_is_not_shown() throws Throwable {
        assertFalse("Error Page is not showing", webBot.isElementPresent(By.cssSelector(".error-content")));
    }

    @And("^the page title should contain (.*)$")
    public void the_page_title_should_contain_page_title(String pageTitle) throws Throwable {
        assertThat(webBot.getTitle(), containsString(pageTitle));
    }
    
    @And("^I navigate to sold out page with all sizes sold out$")
    public void I_navigate_to_sold_out_page_with_all_sizes_sold_out() throws Throwable {
//        String outOfStockPid = productDataAccess.getApiClientFacade().getOutOfStockClothingProduct(SalesChannelEnum.getByWebsiteAndRegion(WebsiteEnum.valueOf(webBot.getBrand()), RegionEnum.valueOf(webBot.getRegion())));
        String pid = getNapProductListPage().clickOnARandomOutOfStockProduct();
        scenarioSession.putData(ProductDetailSteps.PRODUCT_KEY, pid);

    }
    @Given("^I navigate to the sports landing page using a url param with country (.*) language (.*) and deviceType (.*)$")
    public void I_navigate_to_the_sports_landing_page_using_a_url_param_with_country_countryCode_language_languageCode_and_deviceType_d(String countryValue, String languageValue, String deviceType) throws Throwable {
        webBot.goToPageWithPathParams(napSportLandingPage, countryValue+"/"+languageValue+"/"+deviceType+"/");
        napSportLandingPage.closeDontMissOutPopup();
    }

    @And("^product list are displayed$")
    public void product_list_are_displayed() throws Throwable {
        assertTrue(webBot.findElements(By.cssSelector(".products>li")).size() >0);
    }

    @Given("^I randomly select a sport category$")
    public void i_randomly_select_a_sport_category() throws Throwable {
        List<WebElement> elements = webBot.getElements(By.className("test-category-link"));
        if (elements == null || elements.isEmpty()) {
            fail("No test-category link found, something is wrong");
        }

        int elementIndex = new Random(new Date().getTime()).nextInt(elements.size());
        WebElement webElement = elements.get(elementIndex);
        webElement.click();
    }

    // note: exceptions can be found in LocaleUrlUtils.containsValidLink
    @Then("^All nap links contain locale with a few exceptions$")
    public void all_internal_links_contain_locale() throws Throwable {
        StringBuilder resultString = new StringBuilder();
        int wrongLinkCount = 0;

        for (WebElement element: webBot.getElements(By.tagName("a"))) {
            String hrefLink = extractHref(element);
            if (hrefLink != null && !LocaleUrlUtils.containsValidLink(hrefLink)) {
                wrongLinkCount ++;
                resultString.append(extractHref(element)).append(" - ");
                resultString.append(element.getText()).append('\n');
            }
        }

        if (wrongLinkCount != 0) {
            fail("Contains "+ wrongLinkCount +" invalid links as following \n"+ resultString.toString());
        }
    }

    private String extractHref(WebElement element) {
        return element.getAttribute(HREF);
    }


}