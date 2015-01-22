package com.netaporter.pws.automation.nap.cucumber.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created with IntelliJ IDEA.
 * User: x.qi@london.net-a-porter.com
 * Date: 01/05/2013
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:com/netaporter/pws/nap/cucumber/features/NAPPurch/ShippingRestrictions/hazmat.feature", tags = "@nap",
        glue = {"com.netaporter.pws.automation.nap.cucumber.steps", "com.netaporter.test.utils.cucumber.glue", "com.netaporter.pws.automation.shared.steps"})
public class HazmatBDDT {
}
