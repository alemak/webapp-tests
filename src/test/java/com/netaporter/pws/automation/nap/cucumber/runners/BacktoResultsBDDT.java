package com.netaporter.pws.automation.nap.cucumber.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * User: a.mosincat@london.net-a-porter.com
 * Date: 04/12/2013
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:com/netaporter/pws/nap/cucumber/features/NAPEVAL/BacktoResults.feature", tags = "@napeval",
        glue = {"com.netaporter.pws.automation.nap.cucumber.steps", "com.netaporter.test.utils.cucumber.glue", "com.netaporter.pws.automation.shared.steps"})
public class BacktoResultsBDDT {
}


