package com.netaporter.pws.automation.nap.cucumber.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@CucumberOptions(features = "classpath:com/netaporter/pws/nap/cucumber/features/NAPPurch/Promotions/Promotions.feature", tags = "@nap",
        glue = {"com.netaporter.pws.automation.nap.cucumber.steps",
                "com.netaporter.test.utils.cucumber.glue",
                "com.netaporter.pws.automation.shared.steps"})
@RunWith(Cucumber.class)
public class PromotionsBDDT {
}