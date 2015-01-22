package com.netaporter.pws.automation.nap.cucumber.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * User: e.portin
 * Date: 18/07/14
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:com/netaporter/pws/nap/cucumber/features/NAPFIND/SEO/LocaleUrlInListingPages.feature", tags = "@nap",
        glue = {"com.netaporter.pws.automation.nap.cucumber.steps",
                "com.netaporter.test.utils.cucumber.glue",
                "com.netaporter.pws.automation.shared.steps"})
public class LocaleUrlInListingPagesBDDT {
}
