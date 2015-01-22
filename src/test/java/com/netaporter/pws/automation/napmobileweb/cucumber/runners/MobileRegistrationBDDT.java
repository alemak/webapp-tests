package com.netaporter.pws.automation.napmobileweb.cucumber.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:com/netaporter/pws/napmobileweb/cucumber/features/account/registration.feature", tags = "@nap @mobileweb",
        glue = {"com.netaporter.pws.automation.napmobileweb.cucumber.steps", "com.netaporter.test.utils.cucumber.glue"})
public class MobileRegistrationBDDT {
}
