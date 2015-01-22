package com.netaporter.pws.automation.napmobileweb.cucumber.steps.wishlistv3;

import com.jayway.restassured.response.Response;
import com.netaporter.pws.automation.napmobileweb.cucumber.steps.BaseMobileNapSteps;
import com.netaporter.pws.automation.shared.apiclients.WoasAuthManager;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.MenuItem;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.NavMenu;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.WishlistV3Product;
import com.netaporter.pws.automation.shared.steps.CommonWishlistSteps;
import com.netaporter.wishlist.woas.client.commands.WoasAuth;
import com.netaporter.wishlist.woas.client.pojos.Wishlist;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static com.netaporter.pws.automation.nap.util.RegexMatcher.matches;
import static org.junit.Assert.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: a.michael@london.net-a-porter.com
 * Date: 27/06/2013
 * Time: 11:23AM
 * (C) DevilRacing666
 */
public class MobileNavigationSteps extends BaseMobileNapSteps {

    @And("^I view that specific mobile wishlist via its direct url$")
    public void goToUserWishlistViaURL() throws Throwable {
        Wishlist wishlist = (Wishlist) scenarioSession.getData("UserWishlist");
        wishListV3Page.goToWishlistListID(wishlist.getId());
        scenarioSession.putData("currentWishlistURL", webBot.getCurrentUrl());
    }

    @And ("^I view one of those mobile lists$")
    public void gotToAList() throws Throwable {
        List<Wishlist> userLists = (List<Wishlist>)scenarioSession.getData(CommonWishlistSteps.USER_LISTS_CREATED);
        wishListV3Page.goToWishlistListID(userLists.get(0).getId());
        scenarioSession.putData("currentWishlistName", userLists.get(0).getName());
    }

    @And("^I navigate to the mobile All Items page$")
    public void navigateDirectlyToAllItemsPage() throws Throwable {
        wishListV3Page.goToAllItemsPage();
        scenarioSession.putData("currentWishlistName", "All Items");
    }

    @Then("^I should be able to navigate to one of the mobile lists I created$")
    public void navigateToAllLists() throws Throwable {
        List<Wishlist> userLists = (List<Wishlist>)scenarioSession.getData(CommonWishlistSteps.USER_LISTS_CREATED);
        wishListV3Page.clickMenuElementByText(userLists.get(1).getName());
        scenarioSession.putData("currentWishlistName", userLists.get(1).getName());
    }

    @Then("^I should be able to navigate to the mobile All Items page$")
    public void navigateToAllItemsPage() throws Throwable {
        wishListV3Page.clickMenuElementByText("All Items");
        scenarioSession.putData("currentWishlistName", "All Items");
    }

    @Then("^I should be able to navigate to the mobile Default Wishlist page$")
    public void navigateToDefaultWishlistPage() throws Throwable {
        wishListV3Page.clickMenuElementByText("Wish List");
        scenarioSession.putData("currentWishlistName", "Wish List");
    }

    @And ("^I browse to the default mobile wishlist page$")
    public void goToDefaultList() throws Throwable {
        // TODO until we have an easy way to reference the default wishlist, we'll need to hop to a known alias and then hop to default
        Wishlist wishlistDetails = new Wishlist();

//        wishListV3Page.goToAllItemsPage();
//        wishListV3Page.goToDefaultWishlist();
        scenarioSession.putData("currentWishlistName", "Wish List");
        wishlistDetails.setName("Wish List");
        WoasAuth woasAuth = WoasAuthManager.getAuth(scenarioSession, webBot);
        Response res = woasAPIClient.getWishlistsOwnedByMe(woasAuth);
        wishlistDetails.setId(res.getBody().jsonPath().getJsonObject("data[0].id").toString());
        scenarioSession.putData("UserWishlist", wishlistDetails);
        goToUserWishlistViaURL();
    }

    @And("^The first menu item should be for the mobile all items page$")
    public void checkDefaultWishlistMenuItem() throws Throwable {
        NavMenu navMenu = wishListV3Page.getNavMenu();
        MenuItem firstMenuItem = navMenu.getAllMenuItems().getFirst();
        assertThat("First nav menu item should be all items", firstMenuItem.getText(), equalTo("ALL ITEMS"));
        assertThat("First nav menu item should link to 'All Items' link", firstMenuItem.getHref(), endsWith("/wishlist/all-items"));
    }

    @And("^The second menu item should be for the mobile alerts page$")
    public void checkAlertsMenuItem() throws Throwable {
        NavMenu navMenu = wishListV3Page.getNavMenu();
        MenuItem menuItem = navMenu.getAllMenuItems().get(1);
        assertThat("Second nav menu item should be alerts", menuItem.getText(), equalTo("ALERTS"));
        assertThat("Second nav menu item should link to 'Alerts' link", menuItem.getHref(), endsWith("/wishlist/alerts"));
    }

    @And("^The third menu item should be for the mobile closet page$")
    public void checkClosetMenuItem() throws Throwable {
        NavMenu navMenu = wishListV3Page.getNavMenu();
        MenuItem menuItem = navMenu.getAllMenuItems().get(2);

        assertThat("Third nav menu item should be closet", menuItem.getText(), equalTo("CLOSET"));
        assertThat("Second nav menu item should link to 'Closet", menuItem.getHref(), endsWith("/wishlist/closet"));
    }

    @And("^The fourth menu item should be for the mobile default wishlist$")
    public void checkAllItemsMenuItemIsSecondLast() throws Throwable {
        NavMenu navMenu = wishListV3Page.getNavMenu();
        MenuItem secondMenuItem = navMenu.getAllMenuItems().get(3);

        assertThat("Second nav menu item should be the default list", secondMenuItem.getText(), equalTo("WISH LIST"));
        assertThat("Second nav menu item should link to default wishlist", secondMenuItem.getHref(), matches(".*/wishlist/[A-Za-z0-9]{24}$"));
    }

    @And("^The last menu item should be the mobile create list button$")
    public void checkCreateListItemIsLast() throws Throwable {
        NavMenu navMenu = wishListV3Page.getNavMenu();

        MenuItem createListMenuItem = navMenu.getAllMenuItems().getLast();
        assertThat("Last nav menu item is not 'Create New'", createListMenuItem.getText(), equalTo("CREATE NEW"));

        boolean createMenuItemVisible = wishListV3Page.isCreateNewListTextDisplayed();
        assertThat("Create New Wishlist Menu Item is not visible", createMenuItemVisible, is(true));
    }

    @And("^The mobile menu items names for the user lists should appear in alphabetical order$")
    public void checkMenuItemsRightOrder() throws Throwable {
        NavMenu navMenu = wishListV3Page.getNavMenu();

        List<MenuItem> menuItemsSorted = navMenu.getUserMenuItems();
        Collections.sort(menuItemsSorted, new MenuItem.AlphabeticalComparator());

        assertThat("User Wishlist Menu Items not alphabetical", navMenu.getUserMenuItems(), equalTo(menuItemsSorted));
    }

    @And("^clicking the default wishlist menu item should go to the mobile default wishlist$")
    public void checkDefaultMenuItem() throws Throwable {
        WebElement defaultWlMenuItem = wishListV3Page.getNavMenuElementByText("WISH LIST");
        defaultWlMenuItem.click();
        assertThat("Should be on default wishlist page", wishListV3Page.wishlistHeaderTitle().getText(), equalTo("WISH LIST"));
    }

    @And("^clicking a user list menu item should go to the appropriate mobile list$")
    public void checkUserListNavigation() throws Throwable {
        List<Wishlist> userLists = (List<Wishlist>)scenarioSession.getData(CommonWishlistSteps.USER_LISTS_CREATED);
        WebElement wishlistMenuItem = wishListV3Page.getMenuElementByWishlistId(userLists.get(0).getId());

        wishlistMenuItem.click();
        waitForPageTitle(userLists.get(0).getName());

        assertThat("Should be on user wishlist page", wishListV3Page.wishlistHeaderTitle().getText(), equalTo(userLists.get(0).getName().toUpperCase()));
    }

    @And("^clicking the all items menu item should go to the mobile all items page$")
    public void checkAllItemsMenuItem() throws Throwable {
        WebElement allItemsMenuItem = wishListV3Page.getMenuElementAllItems();

        allItemsMenuItem.click();
        assertThat("Should be on all items page", webBot.getCurrentUrl(), endsWith("/wishlist/all-items"));
    }

    @And("^Clicking the mobile wishlist product thumbnail navigates through to the product details page$")
    public void checkProductLink() throws Throwable {

        List<WishlistV3Product> allWishlistProductsOnPage = wishListV3Page.getAllWishlistV3Products();

        //Click the thumbnail
        WishlistV3Product prod = allWishlistProductsOnPage.get(0);
        wishListV3Page.clickProductThumbnail(prod.getPid());
        assertThat("We should be on the product page", productDetailsPage.getPid(), equalTo(prod.getPid().toString()));
    }

    @And("^Clicking the mobile wishlist product designer name navigates through to the product details page$")
    public void checkDesignerLink() throws Throwable {

        List<WishlistV3Product> allWishlistProductsOnPage = wishListV3Page.getAllWishlistV3Products();

        //Click the DESIGNER
        WishlistV3Product prod = allWishlistProductsOnPage.get(0);
        wishListV3Page.clickProductDesigner(prod.getPid());
        assertThat("We should be on the product page", productDetailsPage.getPid(), equalTo(prod.getPid().toString()));
    }

    private void waitForPageTitle(String name) throws Throwable{
        int timeout = 0;
        System.out.print("\nWaiting for new DOM");
        while(!(wishListV3Page.getTitle().toUpperCase().contains(name.toUpperCase()))) {
            System.out.print(".");
            timeout++;
            if (timeout >= 100) {
                fail("Page title '"+name+"' did not show. Got this: "+wishListV3Page.getTitle().toUpperCase());
            }
            Thread.sleep(100);
        }
        System.out.println();
    }

    @And ("^I am sent to the mobile all items wish list page$")
    public void checkOnTheAllItemsWishlist() throws Throwable {
        checkOnTheSpecificWishlist("All Items");
    }

    @And ("^I am on the mobile wish list page called (.+) and it is selected in the nav bar$")
    public void checkOnTheSpecificWishlist(String wishlist) throws Throwable {
        assertEquals("Expect wishlist name", wishlist.toUpperCase(), wishListV3Page.getWishlistName());

        // TODO Implement as desktop version
        // scenarioSession.putData("currentWishlistName", wishlist);
        // checkOnlyCurrentListIsTicked();
    }

    @Then("^I am taken to the legacy mobile wish list page$")
    public void I_am_taken_to_the_wish_list() throws Throwable {
        assertTrue(wishListV3Page.isLegacyWishlistPage());
    }


    @When("^I click the wishlist link on the mobile header$")
    public void I_select_the_wishlist_link_on_the_header() throws Throwable {
        wishListV3Page.clickHeaderWishlistLink();
    }

    @And("^I select the mobile wishlist called (.*) from the wishlist navigation menu$")
    public void selectWishlistFromMenu(String name) throws Throwable {

        wishListV3Page.clickMenuElementByText(name);
        wishListV3Page.waitForElementToNotExist(By.cssSelector(".layout-product-list.is-loading"));
        scenarioSession.putData("currentWishlistName", name);
    }

}
