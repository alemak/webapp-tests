package com.netaporter.pws.automation.nap.cucumber.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created with IntelliJ IDEA.
 * User: m.sun@london.net-a-porter.com
 * Date: 18/10/2012
 * Time: 14:04
 * To change this template use File | Settings | File Templates.
 */


@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:com/netaporter/pws/nap/cucumber/features/NAPPurch/PurchasePath/HKMaestroPayment.feature",
        glue = {"com.netaporter.pws.automation.nap.cucumber.steps", "com.netaporter.test.utils.cucumber.glue", "com.netaporter.pws.automation.shared.steps"})
public class HKMaestroPaymentBDDT {
}
