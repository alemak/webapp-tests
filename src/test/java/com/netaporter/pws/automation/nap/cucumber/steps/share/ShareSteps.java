package com.netaporter.pws.automation.nap.cucumber.steps.share;

import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * User: x.qi@london.net-a-porter.com
 * Date: 15/02/2013
 */
public class ShareSteps extends BaseNapSteps {

    @When("^I click the Tell-a-friend button$")
    public void I_click_the_Tell_a_friend_button() throws Throwable {
        productDetailsPage.clickTellAFriendButton();
    }

    @Then("^the Tell-a-friend page is open$")
    public void the_Tell_a_friend_page_is_open() throws Throwable {
        assertTrue("Tell-a-friend overlay is not displayed", productDetailsPage.isTellAFriendOverlayDisplayed());
    }

    @When("^I fill the whole form and submit the completed tell a friend page$")
    public void I_submit_the_completed_tell_a_friend_page() throws Throwable {
        productDetailsPage.switchToTellAFriendOverlay();
        productDetailsPage.fillWholeTellAFriendFormAndSubmit();
    }

    @Then("^the submission is successful$")
    public void the_submission_is_successful() throws Throwable {
        assertTrue("Submission successfull message is not displayed", productDetailsPage.isTellAFriendSubmissionSuccessful());
    }

    @Then("^the Tell-a-friend page is open with the users details already filled$")
    public void the_Tell_a_friend_page_is_open_with_the_users_details_already_filled() throws Throwable {
        productDetailsPage.switchToTellAFriendOverlay();

        String registeredEmailAddress = (String) scenarioSession.getData("registeredEmailAddress");

        assertEquals(registeredEmailAddress, productDetailsPage.getExistingTellAFriendEMail());
        assertEquals("Test", productDetailsPage.getExistingTellAFriendName());
    }


    @When("^I fill the remaining info and submit the completed tell a friend page$")
    public void I_fill_the_remaining_info_and_submit_the_completed_tell_a_friend_page() throws Throwable {
        productDetailsPage.fillTellAFriendEmailSubjectAndSubmit();
    }
}
