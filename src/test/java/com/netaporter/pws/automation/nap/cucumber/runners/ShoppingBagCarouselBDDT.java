package com.netaporter.pws.automation.nap.cucumber.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created with IntelliJ IDEA.
 * User: sjoshi
 * Date: 24/07/13
 * Time: 10:07
 * To change this template use File | Settings | File Templates.
 */

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:com/netaporter/pws/nap/cucumber/features/NAPPurch/PurchasePath/ShoppingBagCarousel.feature", tags = "@nap",
        glue = {"com.netaporter.pws.automation.nap.cucumber.steps", "com.netaporter.test.utils.cucumber.glue", "com.netaporter.pws.automation.shared.steps"})
public class ShoppingBagCarouselBDDT {
}