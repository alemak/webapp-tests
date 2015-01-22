package com.netaporter.pws.automation.nap.cucumber.steps.livechat;

import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.test.utils.pages.driver.WaitTime;
import com.netaporter.test.utils.pages.exceptions.PageElementNotFoundException;

import cucumber.api.PendingException;
import cucumber.api.java.en.*;
import org.openqa.selenium.By;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
/**
 * Created by sjoshi on 06/10/14.
 */
public class LiveChatSteps extends BaseNapSteps{
    @When("^I force liveChat to appear$")
    public void I_force_liveChat_to_appear()throws Throwable
    {
        liveChatComponent.forceLiveChatToAppear();
    }

    @When("^live (.*) is initiated")
    public void liveChat_window_is_shown (String chatWindow) throws Throwable{
        assertTrue(liveChatComponent.isComponentActivated(chatWindow));

    }

    @When("^I select to load chat session$")
    public void I_select_to_load_chat_session() throws Throwable{
        liveChatComponent.loadChatSession();
    }

    @When("^I force and load new chat session$")
    public void I_force_and_load_new_chat_session() throws Throwable{
        liveChatComponent.forceAndLoadNewChatSession();

    }

    @When("^I select the close chat window button$")
    public void I_select_the_close_chat_window_button() throws Throwable{
        liveChatComponent.closeChatWindowButton();
    }

    @When ("^I minimise the chat window$")
    public void I_minimise_the_chat_window() throws Throwable{
        liveChatComponent.minimiseChatWindow();
    }

    @Then("^I select (yes|no) to close chat$")
    public void I_select_closeChatOptions_to_close_chat(String closeChatOptions) throws Throwable {
        liveChatComponent.closeChatOptionsToCloseChat(closeChatOptions);
    }

    @When("^text box to enter chat should be visible$")
    public void text_box_to_enter_chat_should_be_visible() throws InterruptedException {
        Thread.sleep(2000);
        assertTrue("Chat input text is not visible",liveChatComponent.isChatTextBoxVisible());
    }

    @Then ("^chat window is minimised$")
    public void chat_window_is_minimised(){
        assertTrue("Chat window is not minimise",liveChatComponent.isChatMinimised());
    }

    @Then("^live chat icon is visible$")
    public void live_chat_icon_is_visible() throws Throwable {
        assertTrue("Chat icon is not visible",liveChatComponent.isChatIconVisible());
    }

    @And("^I switch to original window$")
    public void I_switch_to_original_window() throws Throwable {
       webBot.closePopUpWindow();
    }
}

