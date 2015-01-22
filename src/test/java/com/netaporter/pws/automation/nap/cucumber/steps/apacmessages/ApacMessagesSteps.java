package com.netaporter.pws.automation.nap.cucumber.steps.apacmessages;

import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.test.utils.pages.driver.WaitTime;
import com.netaporter.test.utils.pages.exceptions.PageElementNotFoundException;
import cucumber.api.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

/**
 * User: x.qi@london.net-a-porter.com
 * Date: 24/01/2013
 */
public class ApacMessagesSteps extends BaseNapSteps {

    @Then("^I (true|false) first time user message$")
    public void I_shouldSee_first_time_user_message(Boolean shouldSee) throws Throwable {
        try {
            webBot.findElement(By.className("locale-message"), WaitTime.TWO);
        }
        catch (PageElementNotFoundException e) {
            if (shouldSee.equals(true))
                fail("APAC first time user message was not displayed");
            return;
        }
        if (shouldSee.equals(false)) {
            fail("APAC first time user message was incorrectly displayed");
        }
    }

    @Then("^The apacFirstTimeUserMessageHasBeenShown cookie (true|false)$")
    public void The_apacFirstTimeUserMessageHasBeenShown_has_been_set(boolean shouldBeSet) throws Throwable {
        Cookie apacFirstTimeUserMessageHasBeenShown = webBot.getCookie("apacFirstTimeUserMessageHasBeenShown");
        assertThat(shouldBeSet, is(apacFirstTimeUserMessageHasBeenShown!=null));
    }
}
