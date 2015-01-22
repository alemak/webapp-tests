package com.netaporter.pws.automation.nap.cucumber.steps.wishlistv3;

import com.jayway.restassured.response.Response;
import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.pws.automation.shared.apiclients.WoasAuthManager;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.MenuItem;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.NavMenu;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.WishlistV3Product;
import com.netaporter.pws.automation.shared.steps.CommonWishlistSteps;
import com.netaporter.wishlist.woas.client.commands.WoasAuth;
import com.netaporter.wishlist.woas.client.pojos.Wishlist;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Collections;
import java.util.List;

import static com.netaporter.pws.automation.nap.util.RegexMatcher.matches;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static junit.framework.TestCase.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created with IntelliJ IDEA.
 * User: a.michael@london.net-a-porter.com
 * Date: 27/06/2013
 * Time: 11:23AM
 * (C) DevilRacing666
 */
public class NavigationSteps extends BaseNapSteps {


    @And("^I view that specific wishlist via its direct url$")
    public void goToUserWishlistViaURL() throws Throwable {

        if(scenarioSession.getData("currentWishlistName").equals("All Items")) {
            wishListV3Page.goToAllItemsPage();
        } else {
            Wishlist wishlist = (Wishlist) scenarioSession.getData("UserWishlist");
            wishListV3Page.goToWishlistListID(wishlist.getId());
        }
        wishListV3Page.waitForLoadingPaneToDisappear();
        scenarioSession.putData("currentWishlistURL", webBot.getCurrentUrl());
    }

    @And ("^I browse to the default wishlist page$")
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

    @And("^I click the wishlist navigation menu item called (.+)$")
    public void clickMenuItem(String name) throws Throwable {
        wishListV3Page.clickMenuElementByText(name);
        wishListV3Page.waitForLoadingPaneToDisappear();
        scenarioSession.putData("currentWishlistURL", webBot.getCurrentUrl());
        scenarioSession.putData("currentWishlistName", name);
        wishListV3Page.setPath("wishlist/");
    }

    @Then("^I should be able to navigate to one of the lists I created$")
    public void navigateToAllLists() throws Throwable {
        List<Wishlist> userLists = (List<Wishlist>)scenarioSession.getData(CommonWishlistSteps.USER_LISTS_CREATED);
        wishListV3Page.clickMenuElementByText(userLists.get(1).getName());
        wishListV3Page.waitForLoadingPaneToDisappear();
        scenarioSession.putData("currentWishlistName", userLists.get(1).getName());
    }

    @Then("^I should be able to navigate to the All Items page$")
    public void navigateToAllItemsPage() throws Throwable {
        wishListV3Page.clickMenuElementByText("ALL ITEMS");
        wishListV3Page.waitForLoadingPaneToDisappear();
        scenarioSession.putData("currentWishlistName", "All Items");
    }

    @Then("^I navigate to the v3 Closet page on the wishlist menu$")
    public void navigateToClosetPage() throws Throwable {
        wishListV3Page.clickMenuElementByText("CLOSET");
        wishListV3Page.waitForLoadingPaneToDisappear();
        scenarioSession.putData("currentWishlistName", "Closet");
    }

    @And("^I navigate to the All Items page for wishlist$")
    public void navigateDirectlyToAllItemsPage() throws Throwable {
        wishListV3Page.goToAllItemsPage();
        wishListV3Page.waitForLoadingPaneToDisappear();
        scenarioSession.putData("currentWishlistName", "All Items");
        scenarioSession.putData("currentWishlistURL", webBot.getCurrentUrl());
        assertTrue("Not on the All Items page",webBot.getCurrentUrl().endsWith("wishlist/all-items"));
        Thread.sleep(1500);
    }

    @And("^I navigate to the Closet page for wishlist$")
    public void navigateDirectlyToClosetPage() throws Throwable {
        wishListV3Page.goToClosetPage();
        wishListV3Page.waitForLoadingPaneToDisappear();
        scenarioSession.putData("currentWishlistName", "Closet");
        scenarioSession.putData("currentWishlistURL", webBot.getCurrentUrl());
        assertTrue("Not on the Closet page",webBot.getCurrentUrl().endsWith("wishlist/closet"));
    }

    @And("^I navigate to the Alerts page for wishlist$")
    public void navigateDirectlyToAlertsPage() throws Throwable {
        wishListV3Page.goToAlertsPage();
        wishListV3Page.waitForLoadingPaneToDisappear();
        scenarioSession.putData("currentWishlistName", "Alerts");
        scenarioSession.putData("currentWishlistURL", webBot.getCurrentUrl());
        assertTrue("Not on the All Items page",webBot.getCurrentUrl().endsWith("wishlist/alerts"));
    }

    @Then("^I should be able to navigate to the Default Wishlist page$")
    public void navigateToDefaultWishlistPage() throws Throwable {
        wishListV3Page.clickMenuElementByText("Wish List");
        wishListV3Page.waitForLoadingPaneToDisappear();
        scenarioSession.putData("currentWishlistName", "Wish List");
    }

    @And ("^I view one of those lists$")
    public void gotToAList() throws Throwable {
        List<Wishlist> userLists = (List<Wishlist>)scenarioSession.getData(CommonWishlistSteps.USER_LISTS_CREATED);
        wishListV3Page.goToWishlistListID(userLists.get(1).getId());
        wishListV3Page.waitForLoadingPaneToDisappear();
        scenarioSession.putData("currentWishlistName", userLists.get(1).getName());
    }

    @Then("^The correct menu item should be ticked in the menu$")
    public void checkOnlyCurrentListIsTicked() throws Throwable {
        NavMenu navMenu = wishListV3Page.getNavMenu();

        String currentWishlist = (String) scenarioSession.getData("currentWishlistName");
        MenuItem itemForCurrentList = null;
        for(MenuItem menuItem: navMenu.getAllMenuItems()) {
            if(currentWishlist.equalsIgnoreCase(menuItem.getText())) {
                itemForCurrentList = menuItem;
                assertThat("Wishlist navigation menu item for current list not ticked", menuItem.isTicked(), is(true));
            }else{
                assertThat("Incorrect wishlist navigation menu item is ticked. Expected: " + currentWishlist + " Actual: " + menuItem.getText(),
                        menuItem.isTicked(), is(false));
            }
        }

        assertThat("Cannot find menu item for current list", itemForCurrentList, notNullValue());
    }

    @And ("^I am sent to the default wish list page$")
    public void checkOnTheDefaultWishlist() throws Throwable {
        checkOnTheSpecificWishlist("Wish List");
    }

    @And ("^I am sent to the all items wish list page$")
    public void checkOnTheAllItemsWishlist() throws Throwable {
        checkOnTheSpecificWishlist("All Items");
    }


    @And ("^I am on the wish list page called (.+) and it is selected in the nav bar$")
    public void checkOnTheSpecificWishlist(String wishlist) throws Throwable {
        wishListV3Page.waitForLoadingPaneToDisappear();
        assertEquals("Expect wishlist name", wishlist.toUpperCase(), wishListV3Page.getWishlistName());
        scenarioSession.putData("currentWishlistName", wishlist);
        checkOnlyCurrentListIsTicked();
    }


    @And("^I click the first wishlist product thumbnail$")
    public void checkProductLink() throws Throwable {
        Thread.sleep(1000); // TODO Improve this.  Just giving it a second to load for now
        webBot.waitForJQueryCompletion();

        List<WishlistV3Product> allWishlistProductsOnPage = wishListV3Page.getAllWishlistV3Products();

        //Click the thumbnail
        WishlistV3Product prod = allWishlistProductsOnPage.get(0);

        wishListV3Page.clickProductThumbnail(prod.getPid());
        scenarioSession.putData("CLICKED_PID", prod.getPid());

        Thread.sleep(1000); // TODO Improve this.  Just giving it a second to load for now
    }

    @And("^I click the first wishlist product designer name")
    public void checkProductDesignerName() throws Throwable {
        List<WishlistV3Product> allWishlistProductsOnPage = wishListV3Page.getAllWishlistV3Products();

        //Click the thumbnail
        WishlistV3Product prod = allWishlistProductsOnPage.get(0);

        wishListV3Page.clickProductDesigner(prod.getPid());
        scenarioSession.putData("CLICKED_PID", prod.getPid());

        Thread.sleep(1000); // TODO Improve this.  Just giving it a second to load for now
    }

    @And("^Product links to the product details should be real links that can be opened in a new tab$")
    public void checkProductLinkNewTab() throws Throwable {
        List<String> imageProductLinks = wishListV3Page.getProductLinksOnImage();
        List<String>skuList = (List<String>) scenarioSession.getData("listOfSkus");

        //Expect one product link per wishlist item
        assertEquals("Expecting " + skuList.size() + " image links to product page", skuList.size(), imageProductLinks.size());
        for(String imageProductLink: imageProductLinks) {
            assertThat("Link should start with http: "+imageProductLink, imageProductLink, startsWith("http"));
        }
    }

    @And("^Designer links to the product details should be real links that can be opened in a new tab$")
    public void checkDesignerLinkNewTab() throws Throwable {
        List<String> designerProductLinks = wishListV3Page.getProductLinksOnDesigner();
        List<String>skuList = (List<String>) scenarioSession.getData("listOfSkus");

        //Expect one designer link per wishlist item
        assertEquals("Expecting " + skuList.size() + " designer links to product page", skuList.size(), designerProductLinks.size());
        for(String designerProductLink: designerProductLinks) {
            assertThat("Link should start with http", designerProductLink, startsWith("http"));
        }
    }

    @And("^The first menu item should be for the all items page$")
    public void checkAllItemsWishlistMenuItem() throws Throwable {
        NavMenu navMenu = wishListV3Page.getNavMenu();
        MenuItem firstListMenuItem = navMenu.getAllMenuItems().getFirst();
        assertThat("First nav menu item does not have 'All Items' name", firstListMenuItem.getText(), equalTo("ALL ITEMS"));
        assertThat("First nav menu item does not have 'All Items' link", firstListMenuItem.getHref(), endsWith("/wishlist/all-items"));
    }

    @And("^The second menu item should be for alerts$")
    public void checkAlertsMenuItem() throws Throwable {
        NavMenu navMenu = wishListV3Page.getNavMenu();
        MenuItem secondListMenuItem = navMenu.getAllMenuItems().get(1);

        assertThat("Second nav menu item is not alerts", secondListMenuItem.getText(), equalTo("ALERTS"));
        assertThat("Second nav menu item does not link to alerts", secondListMenuItem.getHref(), endsWith("/wishlist/alerts"));
    }

    @And("^The fourth menu item should be for the default wishlist$")
    public void checkDefaultWishlistMenuItem() throws Throwable {
        NavMenu navMenu = wishListV3Page.getNavMenu();
        MenuItem menuItem = navMenu.getAllMenuItems().get(3);

        assertThat("Fourth nav menu item is not default wishlist", menuItem.getText(), equalTo("Wish List"));
        assertThat("Fourth nav menu item does not link to default wishlist", menuItem.getHref(), matches(".*/wishlist/[A-Za-z0-9]{24}$"));
    }

    @And("^The last menu item should be the create list button$")
    public void checkCreateListItemIsLast() throws Throwable {
        NavMenu navMenu = wishListV3Page.getNavMenu();

        MenuItem createListMenuItem = navMenu.getAllMenuItems().getLast();
        assertThat("Last nav menu item is not 'Name and Save...'", createListMenuItem.getText(), equalTo("Name and save a new list"));

        boolean createMenuItemVisible = wishListV3Page.isCreateNewListTextDisplayed();
        boolean createMenuFormVisible = wishListV3Page.isCreateWishlistFormVisible();
        assertThat("Create New Wishlist Menu Item is not visible", createMenuItemVisible, is(true));
        assertThat("Create New Wishlist Form is not visible", createMenuFormVisible, is(true));
    }

    @And("^the nav menu should now contain that wishlist$")
    public void checkMenuContainsWishlist() throws Throwable {
        String listName = scenarioSession.getData("currentWishlistName").toString();
        NavMenu nav = wishListV3Page.getNavMenu();
        MenuItem listMenuItem = nav.getFirstUserMenuItemWithName(listName);

        assertThat("Menu item " + listName + " does not exist", listMenuItem, notNullValue());
        assertThat("Menu item " + listName + " does not have a wishlist href", listMenuItem.getHref(), matches(".*/wishlist/[A-Za-z0-9]{24}$"));
    }

    @And("^the nav menu should now contain the wishlist (.+)$")
    public void checkMenuContainsSpecificWishlist(String listName) throws Throwable {
        NavMenu nav = wishListV3Page.getNavMenu();
        MenuItem listMenuItem = nav.getFirstUserMenuItemWithName(listName);

        assertThat("Menu item " + listName + " does not exist", listMenuItem, notNullValue());
        assertThat("Menu item " + listName + " does not have a wishlist href", listMenuItem.getHref(), matches(".*/wishlist/[A-Za-z0-9]{24}$"));
    }

    @And("^the nav menu should now contain (\\d+) wishlists? with the name (.*)$")
    public void checkMenuContainsMultipleWishlistsWithTheSameName(int expectedCount, String name) throws Throwable {
        NavMenu menu = wishListV3Page.getNavMenu();

        int count = 0;
        for(MenuItem item: menu.getUserMenuItems()) {
            if(item.getText().equals(name)) {
                count++;
            }
        }
        assertEquals("Expected " + expectedCount + " menu items called " + name, expectedCount, count);
    }

    @And("^The menu items names for the user lists should appear in alphabetical order$")
    public void checkMenuItemsRightOrder() throws Throwable {
        NavMenu navMenu = wishListV3Page.getNavMenu();

        List<MenuItem> menuItemsSorted = navMenu.getUserMenuItems();
        Collections.sort(menuItemsSorted, new MenuItem.AlphabeticalComparator());

        assertThat("User Wishlist Menu Items not alphabetical", navMenu.getUserMenuItems(), equalTo(menuItemsSorted));
    }

    @And("^clicking the default wishlist menu item should go to the default wishlist$")
    public void checkDefaultMenuItem() throws Throwable {
        WebElement defaultWlMenuItem = wishListV3Page.getNavMenuElementByText("Wish List");
        defaultWlMenuItem.click();
        wishListV3Page.waitForLoadingPaneToDisappear();
        assertThat("Should be on default wishlist page", wishListV3Page.wishlistHeaderTitle().getText(), equalTo("WISH LIST"));
    }

    @And("^clicking a user list menu item should go to the appropriate list$")
    public void checkUserListNavigation() throws Throwable {
        List<Wishlist> userLists = (List<Wishlist>)scenarioSession.getData(CommonWishlistSteps.USER_LISTS_CREATED);
        WebElement wishlistMenuItem = wishListV3Page.getMenuElementByWishlistId(userLists.get(0).getId());

        wishlistMenuItem.click();
        wishListV3Page.waitForLoadingPaneToDisappear();
        assertThat("Should be on user wishlist page", wishListV3Page.wishlistHeaderTitle().getText(), equalTo(userLists.get(0).getName().toUpperCase()));
    }

    @And("^clicking the all items menu item should go to the all items page$")
    public void checkAllItemsMenuItem() throws Throwable {
        WebElement allItemsMenuItem = wishListV3Page.getMenuElementAllItems();

        allItemsMenuItem.click();
        wishListV3Page.waitForLoadingPaneToDisappear();
        assertThat("Should be on all items page", webBot.getCurrentUrl(), endsWith("/wishlist/all-items"));
    }

    @And("^I store the current wishlist details$")
    public void storeWishlistDetails() throws Throwable {
        Wishlist wishlist = new Wishlist();

        if(scenarioSession.getData("UserWishlist") != null) {
            wishlist = (Wishlist) scenarioSession.getData("UserWishlist");
        }

        wishlist.setId(wishListV3Page.getWishlistId());
        wishlist.setName(wishListV3Page.getWishlistName());
        scenarioSession.putData("UserWishlist",wishlist);

        System.out.println("WishlistID="+wishListV3Page.getWishlistId());
        System.out.println("WishlistName="+wishListV3Page.getMenuElementByWishlistId(wishListV3Page.getWishlistId()).getText());
    }

    @And("^I can access the stored wishlist$")
    public void checkStoredWishlistIsAccessible() throws Throwable {
        navigateToStoredPage();
        checkOnTheSpecificWishlist(getStoredWishlistName());
    }


    @And("^I cannot access the stored wishlist$")
    public void checkStoredWishlistIsNotAccessible() throws Throwable {
        navigateToStoredPage();
        wishListV3Page.isErrorPage();
    }

    private void navigateToStoredPage() {
        Wishlist wishlist = (Wishlist) scenarioSession.getData("UserWishlist");
        if (wishlist == null) {
            fail("Expected a stored wishlist from a previous step");
        }

        wishListV3Page.goToWishlistListID(wishlist.getId());
    }

    private String getStoredWishlistName() {
        Wishlist wishlist = (Wishlist) scenarioSession.getData("UserWishlist");
        if (wishlist == null) {
            fail("Expected a stored wishlist from a previous step");
        }
        return wishlist.getName();
    }

    @And("^the nav menu should contain that wishlist with name (.+)$")
    public void checkMenuContainsWishlist(String listName) throws Throwable {
        MenuItem listMenuItem = wishListV3Page.getNavMenu().getFirstUserMenuItemWithName(listName);
        assertNotNull("Menu item " + listName + " should exist", listMenuItem);
    }

    @And("^the nav menu should not contain that wishlist with name (.+)$")
    public void checkMenuDoesNotContainWishlist(String listName) throws Throwable {
        //Thread.sleep(2000);
        MenuItem listMenuItem = wishListV3Page.getNavMenu().getFirstUserMenuItemWithName(listName);
        assertNull("Menu item " + listName + " should not exist", listMenuItem);
    }

    @And("^the nav menu should not contain a wishlist with no name$")
    public void checkMenuDoesNotContainEmptyNamedWishlist() throws Throwable {
        MenuItem listMenuItem = wishListV3Page.getNavMenu().getFirstUserMenuItemWithName("");
        assertNull("Empty Menu item not should exist", listMenuItem);
    }

    @And ("^I browse to a non-existant wishlist page$")
    public void goToNonExistantDefaultList() throws Throwable {
        wishListV3Page.goToWishlistListID("madeupstring");
    }

    @Then("^I should see the wishlist error page$")
    public void verifyOnTheWishlistErrorPage() {
        assertThat("Should be on wishlist error page", wishListV3Page.isWishlistErrorPage());
    }

}
