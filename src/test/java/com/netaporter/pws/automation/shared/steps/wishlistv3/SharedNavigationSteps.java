package com.netaporter.pws.automation.shared.steps.wishlistv3;

import com.jayway.restassured.response.Response;
import com.netaporter.pws.automation.shared.apiclients.WoasAuthManager;
import com.netaporter.wishlist.woas.client.commands.WoasAuth;
import com.netaporter.wishlist.woas.client.pojos.Wishlist;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

/**
 * Date: 17/10/2013
 * Time: 17:15
 */
public class SharedNavigationSteps extends SharedWishlistBaseSteps {

    @Then("^I navigate to the Default Wishlist page$")
    public void navigateToDefaultWishlistPage() throws Throwable {

        if (wishListV3Page.getWishlistName().toUpperCase().equals("WISH LIST")) {
            webBot.reload();
        } else {
            wishListV3Page.clickMenuElementByText("Wish List");
            scenarioSession.putData("currentWishlistName", "Wish List");
        }
        int i=0;
        while (!wishListV3Page.getWishlistName().toUpperCase().equals("WISH LIST") || i < 5) {
            Thread.sleep(1000);
            i++;
        }
    }
}
