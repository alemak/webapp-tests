package com.netaporter.pws.automation.nap.cucumber.steps.wishlistv3;

import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import cucumber.api.java.en.Then;

import java.util.Set;

import static org.apache.commons.lang3.StringUtils.join;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: a.michael@london.net-a-porter.com
 * Date: 27/06/2013
 * Time: 11:32AM
 * (C) DevilRacing666
 */
public class SecuritySteps extends BaseNapSteps {



    @Then("^The page source should not contain any internal WOAS URLs$")
    public void checkInternalWoasReferences() throws Throwable {
        Set<String> hasInternalWoasUrls = wishListV3Page.findInternalWoasUrls();

        assertTrue(
                "Page source for wishlist page has internal WOAS urls. Found: \n - " + join(hasInternalWoasUrls, "\n - ") + "\n",
                hasInternalWoasUrls.isEmpty()
        );
    }

//    /**
//     * TODO: When we have implemented create wishlist, we can make this more dynamic. Register a new account and create our own wishlist
//     */
//    @Given("^I am a remembered on (intl|am|apac) with remember token (.*)$")
//    public void I_am_a_rememberedAs(String channelName, String rememberToken) throws Throwable {
//        setChannel(channelName);
//        webBot.addCookie("remembered_7070", rememberToken);
//    }


}
