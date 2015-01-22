package com.netaporter.pws.automation.nap.cucumber.steps.account;


import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.Matchers;

import static org.junit.Assert.*;

public class MyAccountSteps extends BaseNapSteps {

    @Then("^I am on the my account page$")
	public void i_am_on_my_account_page() throws Throwable {
		assertTrue(myAccountPage.isPageDisplayed());
	}


    @And("^MySubscriptions link is displayed$")
    public void MySubscriptions_link_is_displayed() throws Throwable {
        if (isSubscriptionOn()){
            assertTrue("My Subscriptions link is not displayed in the My Account page!",myAccountPage.isMySubscriptionsLinkDisplayed());
        }
        else{
            assertFalse("My Subscriptions link is displayed even though it should not be!", myAccountPage.isMySubscriptionsLinkDisplayed());
        }
    }

    @When("^I select the MySubscriptions link$")
    public void I_select_the_MySubscriptions_link() throws Throwable {
      //  assumeTrue("MySubscriptions is not enabled in the db, skipping the rest of the steps", (Boolean) scenarioSession.getData(IS_MY_SUBSCRIPTIONS_ENABLED));
        if (isSubscriptionOn())
            myAccountPage.clickMySubscriptionsLink();
    }


    @Then("^I can see the porter subscription$")
    public void I_can_see_the_porter_subscription() throws Throwable {
        Thread.sleep(500);
        if (isSubscriptionOn()){
            assertThat(webBot.getCurrentUrl(), Matchers.containsString("portersubscription"));
        }
    }

    @Then("^I click on the wishlist title link on the My Account page$")
    public void clickWishlistTitleLink() throws Throwable {
        myAccountPage.clickWishlistTitleLink();
    }

    @Then("^I click on the wishlist description link on the My Account page$")
    public void clickWishlistDescriptionLink() throws Throwable {
        myAccountPage.clickWishlistDescriptionLink();
    }

    @And("^I click on (.*) subscription link$")
    public void I_click_on_desired_subscription_link(String subscriptionLink) throws Throwable {

        if ("review".equalsIgnoreCase(subscriptionLink)) {
            mySubscriptionPage.clickReviewMySubscription();

        }
        else if ("change".equalsIgnoreCase(subscriptionLink)) {
            mySubscriptionPage.clickChangeMyDetails();

           }
        else if ("view".equalsIgnoreCase(subscriptionLink)) {
            mySubscriptionPage.clickViewDeliveryDates();
        }
        else throw new IllegalStateException("Wrong subscription link specified");
     }
}