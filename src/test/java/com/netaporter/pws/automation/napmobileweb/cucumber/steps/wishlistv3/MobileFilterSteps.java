package com.netaporter.pws.automation.napmobileweb.cucumber.steps.wishlistv3;

import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.pws.automation.napmobileweb.cucumber.steps.BaseMobileNapSteps;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.WishlistV3Product;
import com.netaporter.test.client.product.dsl.ProductDsl;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.*;

import static com.netaporter.test.utils.assertion.objectcomparison.NapReflectionAssert.assertReflectionEqualsNAP;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created with IntelliJ IDEA.
 * User: c.dawson@london.net-a-porter.com
 * Date: 06/11/2013
 * Time: 12:11
 * To change this template use File | Settings | File Templates.
 */
public class MobileFilterSteps extends BaseMobileNapSteps {


    private static final String emptyFilteredAllItemsText = "You do not have items from this category in your Wish Lists\n" +
            "Try selecting a different category";

    private static final String emptyFilteredWishlistText = "You do not have items from this category in this Wish List\n" +
            "Try selecting a different category";


    @When("^I filter my mobile wishlist items by (ALL|ACCESSORIES|BAGS|BEAUTY|CLOTHING|LINGERIE|SHOES)$")
    public void filterItemsByCategory(String filter) throws Throwable {

        wishListV3Page.clickFilterCategory(filter);

        int i=0;
        while(webBot.exists(null, By.cssSelector(".layout-product-list.is-loading"))) {
            //wait a max of 10 seconds
            if(i != 10) {
                Thread.sleep(1000);
            } else {
                fail("Took too long to load the filtered items");
            }
            i++;
        }
        Thread.sleep(5000);
    }

    @And("^the mobile filter option (ALL|ACCESSORIES|BAGS|BEAUTY|CLOTHING|LINGERIE|SHOES) is selected$")
    public void  assertFilterOptionSelected(String filter) throws Throwable {

        List<WebElement>filterOptions = wishListV3Page.getFilterElements();

        String selectedText = "";
        for (WebElement option : filterOptions) {
            if (option.isSelected()) {
                selectedText = option.getText();
            }
        }

        assertThat("The expected selected filter option did not match the actual selected filter option",
                selectedText, equalTo(filter));
    }

    @And("^the correct text is displayed for the empty filtered mobile wishlist$")
    public void assertEmptyFilteredWishlistText() throws Throwable {
        assertThat("The expected empty filtered wish list text did not match the actual text",
                wishListV3Page.getTextForEmptyWishlistWhenFiltered().getText() , equalTo(emptyFilteredWishlistText));
    }

    @And("^the correct text is displayed for the empty filtered All Items mobile list$")
    public void assertEmptyFilteredAllItemsText() throws Throwable {
        assertThat("The expected empty filtered wish list text did not match the actual text",
                wishListV3Page.getTextForEmptyWishlistWhenFiltered().getText() , equalTo(emptyFilteredAllItemsText));
    }

    @And("^The mobile filter menu bar is displayed correctly$")
    public void assertFilterMenuBar() throws Throwable {
        List<WebElement> filterElements = wishListV3Page.getFilterElements();

        List<String> filters = new ArrayList<String>();
        for (WebElement filter: filterElements) {
            filters.add(filter.getText());
        }

        String[] expFilters = {"All", "Accessories", "Bags", "Beauty", "Clothing", "Lingerie", "Shoes"};
        assertThat("The expected filter options did not match the actual filter options", filters, hasItems(expFilters));

        assertThat("The FilterBy web element text was not correct", wishListV3Page.getFilterByWebElement().getText().equals("FilterBy"));
    }


    @And("^Filtering by each mobile filter category only displays items in that category$")
    public void assertAllFilterCategoriesItems() throws Throwable{

        Map<ProductDsl.ProductCategory, String> skuMap = (LinkedHashMap<ProductDsl.ProductCategory, String>) scenarioSession.getData("listOfSkusAndCategories");

        for (ProductDsl.ProductCategory category : skuMap.keySet()) {

            filterItemsByCategory(category.toString());

            //check selected filter option is selected
            assertFilterOptionSelected(category.toString());

            List<WishlistV3Product> singleWishlistProductsToCompare = (List<WishlistV3Product>) scenarioSession.getData("singleWishlistProducts");

            //check the current category sku matches one of the pids stored in the scenario session
            String pid = "";
            WishlistV3Product wishlistItem = null;
            //get the product to compare
            for (WishlistV3Product item : singleWishlistProductsToCompare) {

                 if (skuMap.get(category).contains(item.getPid().toString())) {
                     wishlistItem = item;
                     break;
                 }
            }

            assertThat("The sku '" + skuMap.get(category) + "' did not match any of the pids in the stored list of wishlistv3products", wishlistItem, notNullValue());

            //now check that product is on the page
            List<WishlistV3Product> singleWishlistProductsOnPage = wishListV3Page.getAllWishlistV3Products();
            assertThat("Not the expected number of wishlist items on the page", singleWishlistProductsOnPage.size(), equalTo(1));

            List<WishlistV3Product> expectedWishlistItemList = new ArrayList<WishlistV3Product>();
            expectedWishlistItemList.add(wishlistItem);

            //currently the next step can fail due to discrepancies in the case of the 'size' field. Therefore we convert all sizes string to lowercase beforehand
            singleWishlistProductsOnPage = convertSizeToLowercaseAndRemoveSpaces(singleWishlistProductsOnPage);
            expectedWishlistItemList = convertSizeToLowercaseAndRemoveSpaces(expectedWishlistItemList);

            //assert the two lists
            assertReflectionEqualsNAP("Reverse chronological order check", expectedWishlistItemList, singleWishlistProductsOnPage);
            Collections.reverse(singleWishlistProductsToCompare);
        }
    }

    @And("^each mobile filter option is underlined after clicking it$")
    public void assertAllFiltersSelected() throws  Throwable{

        for (ProductDsl.ProductCategory category : ProductDsl.ProductCategory.values()) {

            String categoryString = category.toString().substring(0, 1) + category.toString().substring(1).toLowerCase();
            filterItemsByCategory(categoryString);
            assertFilterOptionSelected(categoryString);
        }

    }


    private List<WishlistV3Product> convertSizeToLowercaseAndRemoveSpaces(List<WishlistV3Product> items) {

        for(int i=0; i < items.size(); i++) {
            if (items.get(i).getSize() != null) {
                items.get(i).setSize(items.get(i).getSize().toLowerCase().replace(" ",""));
            }
        }

        return items;
    }




}
