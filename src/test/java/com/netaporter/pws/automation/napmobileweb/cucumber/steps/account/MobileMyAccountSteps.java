package com.netaporter.pws.automation.napmobileweb.cucumber.steps.account;

import com.netaporter.pws.automation.napmobileweb.cucumber.steps.BaseMobileNapSteps;
import cucumber.api.java.en.When;

/**
 * Created with IntelliJ IDEA.
 * User: J.Christian@net-a-porter.com
 * Date: 14/10/2013
 * Time: 11:48
 * To change this template use File | Settings | File Templates.
 */
public class MobileMyAccountSteps extends BaseMobileNapSteps {

    @When("^I go to the mobile my account page$")
    public void go_to_my_account_page() throws Throwable {
        myAccountPage.go();
    }


    @When("^I click on the wishlist link on the mobile My Account page$")
    public void clickWishlistLink() throws Throwable {
        myAccountPage.clickWishlistLink();
    }
}
