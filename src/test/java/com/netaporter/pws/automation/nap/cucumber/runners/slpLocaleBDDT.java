package com.netaporter.pws.automation.nap.cucumber.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * User: o_csiki
 * Date: 17/10/13
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:com/netaporter/pws/nap/cucumber/features/NAPFIND/SaleListingPage/slpLocale.feature", tags = "@napfind",
        glue = {"com.netaporter.pws.automation.nap.cucumber.steps",
                "com.netaporter.test.utils.cucumber.glue",
                "com.netaporter.pws.automation.shared.steps"
        })
public class slpLocaleBDDT {
}