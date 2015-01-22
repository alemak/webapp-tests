package com.netaporter.pws.automation.napmobileweb.cucumber.steps.productdetails;

import com.netaporter.pws.automation.napmobileweb.cucumber.steps.BaseMobileNapSteps;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.AddedToWishlistPopup;
import com.netaporter.pws.automation.shared.steps.CommonWishlistSteps;
import com.netaporter.wishlist.woas.client.pojos.Wishlist;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.netaporter.test.utils.assertion.objectcomparison.NapReflectionAssert.assertReflectionEqualsNAP;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertTrue;


/**
 * Created with IntelliJ IDEA.
 * User: cucumber
 * Date: 26/06/13
 * Time: 13:10
 * To change this template use File | Settings | File Templates.
 */
public class MobileProductDetailSteps extends BaseMobileNapSteps {

    private static final By CHOOSE_SIZE = By.xpath(".//*[@id='sku']");
    private static By fullSizeImageOverlay = By.id("overlay_image_holder");

    @Then("^the locale links are displayed$")
    public void the_locale_links_are_displayed() throws Throwable {
           assertTrue(productDetailsPage.isTheLocaleLinkDisplayed());

     }

    @Then("^the canonical link is displayed$")
    public void the_canonical_link_is_displayed() throws Throwable {
        assertTrue("Canonical is not found", productDetailsPage.isTheCanonicalLinkDisplayed());

    }

    @When("^I click on the view full size image link$")
    public void I_click_on_the_view_full_size_image_links() throws Throwable{
    webBot.findElement(By.id("full-size-image-link")).click();
   }

    @Then("^the full size image overlay appears$")
    public void the_full_size_image_overlay_appears() throws Throwable{
        webBot.findElement(By.id("overlay_image_holder")).isDisplayed() ;
        assertTrue(isFullSizeImageDisplayed());
    }

    private boolean isFullSizeImageDisplayed()
    {
        return doesElementExist(fullSizeImageOverlay);
    }

    @And("^I select size of the product$")
    public void I_select_size_of_the_product() throws Throwable {
        webBot.findElement(CHOOSE_SIZE).click();
    }

    @And("^I select sku ([0-9]+)'s size and click the new mobile Add to Wish List button$")
    public void clickAddWithSizeSelected(int itemNumber) throws Throwable {
        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");
        productDetailsPage.selectItemSizeClickNewAddToWishlist(skuList.get(itemNumber - 1));
    }

    @And("^I select sku ([0-9]+)'s size and click the new mobile Add to Wish List button as a non signed in user$")
    public void clickAddWithSizeSelectedNotSignedIn(int itemNumber) throws Throwable {
        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");
        productDetailsPage.selectItemSizeClickNewAddToWishlistNotSignedIn(skuList.get(itemNumber - 1));
    }

    @And("^I click the new mobile Add to Wish List button$")
    public void clickAddToWishlist() throws Throwable {
        productDetailsPage.clickAddToWishlistButton();
    }

    @And("^I choose to add item ([0-9]+) to the mobile wishlist called (.*)$")
    public void addToWishlist(int item, String wishlist) throws Throwable {
        clickAddWithSizeSelected(item);
        productDetailsPage.selectWishlistToAddItemTo(wishlist);
        scenarioSession.putData("currentWishlistName", wishlist);
    }

    @And("^I select the wishlist called (.*) to add item ([0-9]+) to$")
    public void addToWishlist(String wishlist, int item) throws Throwable {
        productDetailsPage.selectWishlistToAddItemTo(wishlist);
        scenarioSession.putData("currentWishlistName", wishlist);
    }

    @And("^I choose to add item ([0-9]+) to the mobile default wishlist$")
    public void chooseToDefaultWishlist(int item) throws Throwable {
        Thread.sleep(2500);
        clickAddWithSizeSelected(item);
        Thread.sleep(2500);
        productDetailsPage.selectDefaultWishlist();
        scenarioSession.putData("currentWishlistName", "WISH LIST");
    }

    @And("^I choose to add item ([0-9]+) to the mobile default wishlist when not signed in$")
    public void chooseToDefaultWishlistNonSignedIn(int item) throws Throwable {
        clickAddWithSizeSelectedNotSignedIn(item);
//        productDetailsPage.selectDefaultWishlist();
    }

    @And("^a list of my mobile wishlists are displayed with the default wishlist first$")
    public void assertWishlistsDisplayed() throws Throwable {

        List<Wishlist> userLists = (List<Wishlist>)scenarioSession.getData(CommonWishlistSteps.USER_LISTS_CREATED);
        List<String> expWishlistNames = new ArrayList<String>();

        for (Wishlist wishlist : userLists) {
            expWishlistNames.add(wishlist.getName());
        }
        expWishlistNames.add("WISH LIST");
        Collections.reverse(expWishlistNames);

        List<String> actualWishlistNames = productDetailsPage.getAvailableWishlists();

        assertThat("The wishlist names displayed in mobile Add To Wishlist panel do not match the expected ones",
                actualWishlistNames, equalTo(actualWishlistNames));
    }

    @And("^the 'Create New' wishlist option is displayed$")
    public void assertCreateNewWishlistOptionAvailable() {
        WebElement element = productDetailsPage.getCreateNewWishlistElement();
        assertThat("The 'CREATE NEW' wishlist text wasn't displayed correctly", "CREATE NEW", equalTo(element.getText()));
    }


    @And("^the mobile ADD TO WISHLIST button's text changes to 'SELECT A LIST'$")
    public void assertSelectAListText() throws Throwable {
        String actualText = productDetailsPage.getSelectAListButton().getAttribute("value");
        assertThat("The 'ADD TO WISHLIST' button did not seem to change text to 'SELECT A LIST'", actualText.toUpperCase(), equalTo("SELECT A LIST"));
    }

    @And("^clicking the 'SELECT A LIST' button reverts the button text back to 'ADD TO WISHLIST'$")
    public void assertAddToWishlistButtonText() throws Throwable {
        productDetailsPage.getSelectAListButton().click();

        String actualText = productDetailsPage.getAddToListButton().getAttribute("value");
        assertThat("The 'ADD TO WISH LIST' button did not seem to change text to 'ADD TO WISH LIST'", actualText.toUpperCase(), equalTo("ADD TO WISH LIST"));
    }

    @And("^the Added To Wishlist Pop Up appears with the correct details for item ([0-9]+)$")
    public void assertAddedToWishlistPopupDetails(int itemNumber) throws Throwable {

        String wishlistId = woasSteps.getWishlistId(scenarioSession.getData("currentWishlistName").toString());

        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");
        AddedToWishlistPopup expPopupDetails = new AddedToWishlistPopup(wishlistId, skuToPid(skuList.get(itemNumber-1)), webBot.getBaseUrl());
        AddedToWishlistPopup actualPopupDetails = productDetailsPage.getItemAddedPopupDetails();

        assertReflectionEqualsNAP("Assert Added To Wishlist popup details", expPopupDetails, actualPopupDetails);
    }

    @And("^and I can close the Added To Wishlist popup successfully$")
    public void closeAddedToWishlistPopup() throws Throwable {
        productDetailsPage.closeAddedToWishlistPopup();
    }

    @And("^I click the 'Added To Wishlist' link on the mobile Added To Wishlist popup$")
    public void clickAddedToWishlistLink() throws Throwable {
        productDetailsPage.clickAddedToWishlistPopupLink();
    }

    @And("^I click the 'Show Wishlist' link on the mobile Added To Wishlist popup$")
    public void clickShowWishlistLink() throws Throwable {
        productDetailsPage.clickShowWishlistPopupLink();
    }

    @And("^the mobile product details page is displayed for item (\\d+)$")
    public void assertCorrectProductDetailsPage(int itemNumber) throws Throwable {

        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");

        String pid = skuToPid(skuList.get(itemNumber - 1));
        assertThat("The current product details page is for the wrong pid", productDetailsPage.getPid(), equalTo(pid));
    }

    @And("^I create a new wishlist called (.*) and hit enter$")
    public void createNewWishlist(String wishlistName) throws Throwable {
        productDetailsPage.createNewWishlist(wishlistName);
        Wishlist wishlist = new Wishlist();
        wishlist.setName(wishlistName);
        String wishlistNames = productDetailsPage.getAvailableWishlists().toString();
        Integer i=0;
        while(!wishlistNames.contains(wishlistName.toUpperCase()) && i < 10) {
            Thread.sleep(1000);
            wishlistNames = productDetailsPage.getAvailableWishlists().toString();
            i++;
        }

    }

    @And("^the list of mobile wishlists are in alphabetical order$")
    public void assertWishlistAreAlphabetical() throws Throwable {

        List<String> wishlistNames = productDetailsPage.getAvailableWishlists();
        List<String> wishlistNamesSorted = productDetailsPage.getAvailableWishlists();

        //remove default wishlist
        wishlistNames.remove(0);
        wishlistNamesSorted.remove(0);
        //sort the expected results for comparison later
        Collections.sort(wishlistNamesSorted);

        assertThat("User Wishlist names not alphabetical", wishlistNames, Matchers.equalTo(wishlistNamesSorted));
    }

    @And("^the wishlist called (.*) is in the list of mobile wishlists$")
    public void checkForWishlist(String name) throws Throwable {

        Thread.sleep(1000);
        List<String> wishlistNames = productDetailsPage.getAvailableWishlists();
        assertThat("Specified wishlist name was not found in the displayed list of wishlists", wishlistNames, (Matcher) hasItem((equalToIgnoringCase(name))));
    }

    @And("^the wishlist called (.*) is in the list of mobile wishlists twice$")
    public void checkForWishlistTwice(String name) throws Throwable {

        Thread.sleep(1000);
        List<String> wishlistNames = productDetailsPage.getAvailableWishlists();
        String[] names = new String[] {name.toUpperCase(), name.toUpperCase()};
        assertThat("Specified wishlist name was not found in the displayed list of wishlists", wishlistNames, (Matcher) hasItems(names));
    }

}




