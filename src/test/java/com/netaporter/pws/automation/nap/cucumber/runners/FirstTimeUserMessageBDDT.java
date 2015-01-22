package com.netaporter.pws.automation.nap.cucumber.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:com/netaporter/pws/nap/cucumber/features/ApacMessages/FirstTimeUserMessage.feature", tags = "@nap",
        glue = {"com.netaporter.pws.automation.nap.cucumber.steps",
                "com.netaporter.pws.automation.shared.cucumber.glue",
                "com.netaporter.pws.automation.shared.steps"})
public class FirstTimeUserMessageBDDT {

}
