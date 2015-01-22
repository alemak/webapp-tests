package com.netaporter.pws.automation.shared.steps;

import com.google.common.collect.Lists;
import com.jayway.restassured.response.Cookies;
import com.jayway.restassured.response.Response;
import com.netaporter.pws.automation.nap.cucumber.steps.registration.RegistrationSteps;
import com.netaporter.pws.automation.shared.apiclients.WoasAuthManager;
import com.netaporter.test.client.product.dsl.ProductDsl;
import com.netaporter.test.utils.cucumber.steps.LegacyWebAppBaseStep;
import com.netaporter.wishlist.woas.client.WoasClient;
import com.netaporter.wishlist.woas.client.commands.AddItem;
import com.netaporter.wishlist.woas.client.commands.CreateWishlist;
import com.netaporter.wishlist.woas.client.commands.Region;
import com.netaporter.wishlist.woas.client.commands.WoasAuth;
import com.netaporter.wishlist.woas.client.exceptions.WoasException;
import com.netaporter.wishlist.woas.client.pojos.Wishlist;
import com.netaporter.wishlist.woas.client.pojos.WishlistItem;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import org.openqa.selenium.Cookie;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: J.Christian@net-a-porter.com
 * Date: 03/09/2013
 * Time: 15:09
 * To change this template use File | Settings | File Templates.
 */
public class CommonWishlistSteps extends LegacyWebAppBaseStep {

    public static final String USER_LISTS_CREATED = "USER_LISTS_CREATED";

    @Autowired
    protected WoasClient woasAPIClient;


    /**
     * Add items to wishlist slowly
     * Wishlist service, only stores added dates with second resolution.
     * For some tests we have to wait for 1 second between addItemToWishlist calls, otherwise they will be considered added at the
     * same time and their order is undefined.
     */
    @When("^I add skus? ([0-9]+) to ([0-9]+) to my wishlist via the WOAS API$")
    public void slowlyAddItemsToWishlist(Integer from, Integer to) throws Throwable {
        addItemsToWishlist(from, to, true);
    }

    /**
     * Add items to wishlist quickly. Order will be undefined
     */
    @When("^I quickly add skus? ([0-9]+) to ([0-9]+) to my wishlist via the WOAS API$")
    public void quicklyItemsToWishlist(Integer from, Integer to) throws Throwable {
        addItemsToWishlist(from, to, false);
    }

    @When("^I quickly add each of my items to my wishlist via the WOAS API$")
    public void quicklyAddOneItemOfEachCategoryToWishlist() throws Throwable {

        Wishlist wishlist = (Wishlist)scenarioSession.getData("UserWishlist");
        Map<ProductDsl.ProductCategory, String> skuMap = (LinkedHashMap<ProductDsl.ProductCategory, String>) scenarioSession.getData("listOfSkusAndCategories");

        for (String sku: skuMap.values()) {
            addItem(sku, wishlist, true);
        }
    }

    @When("^I quickly add each of my items to my default wishlist via the WOAS API$")
    public void quicklyAddOneItemOfEachCategoryToDefaultWishlist() throws Throwable {

        Map<ProductDsl.ProductCategory, String> skuMap = (LinkedHashMap<ProductDsl.ProductCategory, String>) scenarioSession.getData("listOfSkusAndCategories");

        Wishlist defaultWishlist = new Wishlist();
        defaultWishlist.setId(getDefaultWishlistId());
        scenarioSession.putData("UserWishlist", defaultWishlist);
        for (String sku: skuMap.values()) {
            addItem(sku, defaultWishlist, true);
        }
    }

    public void addItemsToWishlist(Integer from, Integer to, boolean delay) throws Throwable {

        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");
        Wishlist wishlist = (Wishlist)scenarioSession.getData("UserWishlist");

        long startTime = System.currentTimeMillis();
        for (Integer i = from - 1; i <= to - 1; i = i + 1 ) {
            String sku = skuList.get(i);
            System.out.println("Adding sku "+sku+" to wishlist "+wishlist.getName());
            addItem(sku,wishlist, delay);
        }
        System.out.println(">>>>>>>>>> Adding " + ((to-from)+1) + " skus to wishlist via woas api: " + (System.currentTimeMillis() - startTime) + "ms");
    }

    @And("^I create a new wishlist via the WOAS API called (.*)$")
    public Wishlist createWishlistViaWOAS(String wishlistName) throws Throwable {
        Wishlist newWishlist = woasAPIClient.createWishlist(WoasAuthManager.getAuth(scenarioSession, webBot), new CreateWishlist(wishlistName));

        //TODO: Duplication in scenarioSession. We could just use UserWishlist everywhere
        scenarioSession.putData("UserWishlist", newWishlist);
        scenarioSession.putData("currentWishlistName", newWishlist.getName());
        scenarioSession.putData("wishlistName", newWishlist.getName());
        scenarioSession.putData("wishlistID", newWishlist.getId());

        return newWishlist;
    }

    @And("^I create a new empty wishlist via the WOAS API$")
    public void createEmptyWishlistViaWOAS() throws Throwable {
        createWishlistViaWOAS("Empty WL " + System.currentTimeMillis());
    }

    @When("^I create several wishlists$")
    public void createSeveralWishlists() throws Throwable {
        List<Wishlist> userWishlists = new ArrayList<Wishlist>();

        List<String> wishlistsNames = Lists.newArrayList("Vacation", "My Empty Wishlist", "Aardvark");

        for(String name: wishlistsNames) {
            Wishlist wishlist = woasAPIClient.createWishlist(WoasAuthManager.getAuth(scenarioSession, webBot), new CreateWishlist(name));
            userWishlists.add(wishlist);
        }

        scenarioSession.putData(USER_LISTS_CREATED, userWishlists);
    }

    private void addItem(String sku, Wishlist wishlist, boolean delay) {
        try {
            List<WishlistItem> addedSkus = new ArrayList<WishlistItem>();

            WishlistItem wishlistItem = woasAPIClient.addItemToWishlist(WoasAuthManager.getAuth(scenarioSession, webBot),
                    wishlist.getId(), new AddItem(sku));


            if ((scenarioSession.getData("addedSkus")) != null) {
                addedSkus = (List<WishlistItem>) scenarioSession.getData("addedSkus");
            }

            addedSkus.add(wishlistItem);
            scenarioSession.putData("addedSkus", addedSkus);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>  Wishlist Item ID sent from WOAS = "+wishlistItem.getId());
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  Number of sku's added = "+addedSkus.size()+"\n\n\n\n");
            //Wishlist service, only stores added dates with second resolution.
            //We have to wait for 1 second between addItemToWishlist calls, otherwise they will be considered added at the
            //same time and their order is undefined.
            try {
                Thread.sleep(delay? 1000l: 5l);
            } catch (InterruptedException e) {}
        } catch (WoasException e) {
            throw new RuntimeException("Woas error adding sku " + sku + ". Check logs for request " + e.getError().getRequest(), e);
        }
    }

    public String getDefaultWishlistId() {

        Response res = woasAPIClient.getWishlistsOwnedByMe(WoasAuthManager.getAuth(scenarioSession, webBot));

        return res.getBody().jsonPath().getJsonObject("data[0].id");
    }
}
