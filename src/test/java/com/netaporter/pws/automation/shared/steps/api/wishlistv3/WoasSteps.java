package com.netaporter.pws.automation.shared.steps.api.wishlistv3;

import com.jayway.restassured.response.Response;
import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.pws.automation.shared.apiclients.WoasAuthManager;
import com.netaporter.test.utils.cucumber.glue.steps.BaseAPIStep;
import com.netaporter.wishlist.woas.client.commands.WoasAuth;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.junit.Assert.fail;

/**
 * Created with IntelliJ IDEA.
 * User: c.dawson@london.net-a-porter.com
 * Date: 23/12/2013
 * Time: 14:15
 * To change this template use File | Settings | File Templates.
 */
@Component
@Scope("cucumber-glue")
public class WoasSteps extends BaseAPIStep{

    public String getWishlistId(String wishlist) {

        WoasAuth woasAuth = WoasAuthManager.getAuth(scenarioSession, webBot);
        Response res = woasAPIClient.getWishlistsOwnedByMe(woasAuth);
        res.getBody().jsonPath().getJsonObject("data[0].id").toString();

        Integer total = res.getBody().jsonPath().getJsonObject("total");

        String name = "";
        String id = "";
        for (int i=0; i<total; i++) {

            name = res.getBody().jsonPath().getJsonObject("data["+i+"].name").toString();
            if(name.equals(wishlist)) {
                id = res.getBody().jsonPath().getJsonObject("data["+i+"].id").toString();
                break;
            }
        }

        if (name.isEmpty()) {
            fail("The wishlist called '" + wishlist + "' was not returned in your list of wishlists from WOAS");
        }

        return id;
    }
}
