package com.netaporter.pws.automation.napmobile.cucumber.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:com/netaporter/pws/napmobile/cucumber/features/Designers.feature",
        glue = {"com.netaporter.pws.automation.napmobile.cucumber.steps",
                "com.netaporter.test.utils.cucumber.glue"})
public class DesignersBDDT {
}
