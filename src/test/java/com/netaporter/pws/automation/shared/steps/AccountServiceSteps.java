package com.netaporter.pws.automation.shared.steps;

import com.netaporter.test.utils.cucumber.steps.LegacyWebAppBaseStep;
import cucumber.api.java.en.Then;


import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: r.li@london.net-a-porter.com
 * Date: 31/05/2013
 * Time: 16:27
 * To change this template use File | Settings | File Templates.
 */
public class AccountServiceSteps extends LegacyWebAppBaseStep {
    @Then("^My default language is stored as (.*)$")
    public void My_default_language_is_stored(String expectedLanguage) throws Throwable {
        Integer orderId=(Integer)scenarioSession.getData("latestOrderId");
        String storedLanguage= productDataAccess.getLegacyDBClient().getCustomerLanguageFromOrderid (webBot.getRegionEnum(), orderId);
        assertTrue(expectedLanguage.compareToIgnoreCase(storedLanguage)==0);
    }


}

