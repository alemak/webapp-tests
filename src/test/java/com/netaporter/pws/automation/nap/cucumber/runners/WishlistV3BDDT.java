package com.netaporter.pws.automation.nap.cucumber.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created with IntelliJ IDEA.
 * User: a.michael@london.net-a-porter.com
 * Date: 20/03/2013
 * Time: 2:53PM
 * (C) DevilRacing666
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = {
        "classpath:com/netaporter/pws/nap/cucumber/features/WishListV3/AllItems.feature",
        "classpath:com/netaporter/pws/nap/cucumber/features/WishListV3/AddItems.feature",
        "classpath:com/netaporter/pws/nap/cucumber/features/WishListV3/CreateWishlist.feature",
        "classpath:com/netaporter/pws/nap/cucumber/features/WishListV3/DeleteWishlist.feature",
        "classpath:com/netaporter/pws/nap/cucumber/features/WishListV3/InternalUrls.feature",
        "classpath:com/netaporter/pws/nap/cucumber/features/WishListV3/ItemNavigation.feature",
        "classpath:com/netaporter/pws/nap/cucumber/features/WishListV3/Navigation.feature",
        "classpath:com/netaporter/pws/nap/cucumber/features/WishListV3/PageVerification.feature",
        "classpath:com/netaporter/pws/nap/cucumber/features/WishListV3/ManageWishlist.feature",
        "classpath:com/netaporter/pws/nap/cucumber/features/WishListV3/SignOut.feature",
        "classpath:com/netaporter/pws/nap/cucumber/features/WishListV3/filter.feature"
    },
    glue = {"com.netaporter.pws.automation.nap.cucumber.steps", "com.netaporter.test.utils.cucumber.glue"})
    public class WishlistV3BDDT {
}
