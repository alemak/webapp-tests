package com.netaporter.pws.automation.shared.steps;

import com.netaporter.test.utils.cucumber.steps.LegacyWebAppBaseStep;
import cucumber.api.java.en.Then;
import org.junit.Assert;

public class OrderServiceSteps extends LegacyWebAppBaseStep {

    @Then("^the source of the order has been stored as (.*)-(.*)$")
    public void Verify_that_the_source_of_order_has_been_stored_against_the_order(String appName, String appVersion) throws Throwable {
        Integer orderId = (Integer) scenarioSession.getData("latestOrderId");
        String sourceAppNameOfOrder = productDataAccess.getLegacyDBClient().getSourceAppNameOfOrder(webBot.getRegionEnum(), orderId);
        String sourceAppVersionOfOrder = productDataAccess.getLegacyDBClient().getSourceAppVersionOfOrder(webBot.getRegionEnum(), orderId);

        appName = changeStringToNullIfNecessary(appName);
        appVersion = changeStringToNullIfNecessary(appVersion);

        Assert.assertEquals(appName, sourceAppNameOfOrder);
        Assert.assertEquals(appVersion, sourceAppVersionOfOrder);
    }

    private String changeStringToNullIfNecessary(String str) {
        if (str.equalsIgnoreCase("null")) {
            str = null;
        }
        return str;
    }

}
