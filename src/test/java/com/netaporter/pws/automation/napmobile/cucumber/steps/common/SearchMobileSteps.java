package com.netaporter.pws.automation.napmobile.cucumber.steps.common;

import com.netaporter.pws.automation.napmobile.cucumber.steps.BaseNapMobileSteps;
import cucumber.api.PendingException;
import cucumber.api.java.en.When;

/**
 * Created by a.mosincat on 13/08/2014.
 */
public class SearchMobileSteps extends BaseNapMobileSteps{


    @When("^I search for a (.*)$")
    public void I_search_for_a_keyword(String keyword) throws Throwable {
      getCurrentNapPage().search(keyword);

    }
}
