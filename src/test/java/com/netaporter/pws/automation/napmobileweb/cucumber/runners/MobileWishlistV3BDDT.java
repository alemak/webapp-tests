package com.netaporter.pws.automation.napmobileweb.cucumber.runners;

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
//        "classpath:com/netaporter/pws/napmobileweb/cucumber/features/WishListV3/MobileAddItems.feature",
//        "classpath:com/netaporter/pws/napmobileweb/cucumber/features/WishListV3/MobileCreateWishlist.feature",
//        "classpath:com/netaporter/pws/napmobileweb/cucumber/features/WishListV3/MobileDeleteWishlist.feature",
//        "classpath:com/netaporter/pws/napmobileweb/cucumber/features/WishListV3/MobileInternalUrls.feature",
        "classpath:com/netaporter/pws/napmobileweb/cucumber/features/WishListV3/MobileItemNavigation.feature",
        "classpath:com/netaporter/pws/napmobileweb/cucumber/features/WishListV3/MobileNavigation.feature",
        "classpath:com/netaporter/pws/napmobileweb/cucumber/features/WishListV3/MobilePageVerification.feature" //,
        //"classpath:com/netaporter/pws/napmobileweb/cucumber/features/WishListV3/MobileRenameWishlist.feature"
    },
    glue = {"com.netaporter.pws.automation.napmobileweb.cucumber.steps", "com.netaporter.test.utils.cucumber.glue"})
    public class MobileWishlistV3BDDT {
}
