package com.netaporter.pws.automation.napmobile.cucumber.runners;

/**
 * Created by ocsiki on 05/08/2014.
 */
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:com/netaporter/pws/napmobile/cucumber/features/PurchasePath/GuestCheckoutMobile.feature",
        glue = {"com.netaporter.pws.automation.napmobile.cucumber.steps",
                "com.netaporter.test.utils.cucumber.glue"
        })
public class GuestCheckoutMobileBDDT {
}
