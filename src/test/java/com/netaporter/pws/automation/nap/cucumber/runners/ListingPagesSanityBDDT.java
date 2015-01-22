package com.netaporter.pws.automation.nap.cucumber.runners;

/**
 * Created by ocsiki on 16/06/2014.
 */
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:com/netaporter/pws/nap/cucumber/features/LiveSanitySuite/ListingPagesSanity.feature", tags = "@napfind",
        glue = {"com.netaporter.pws.automation.nap.cucumber.steps",
                "com.netaporter.test.utils.cucumber.glue",
                "com.netaporter.pws.automation.shared.steps"})
public class ListingPagesSanityBDDT {
}