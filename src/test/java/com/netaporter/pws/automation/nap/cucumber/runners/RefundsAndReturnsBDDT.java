package com.netaporter.pws.automation.nap.cucumber.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by ocsiki on 31/12/14.
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:com/netaporter/pws/nap/cucumber/features/NAPPurch/PurchasePath/RefundsAndReturns.feature", tags = "@nap",
        glue = {"com.netaporter.pws.automation.nap.cucumber.steps",
                "com.netaporter.test.utils.cucumber.glue",
                "com.netaporter.pws.automation.shared.steps"})
public class RefundsAndReturnsBDDT {
}