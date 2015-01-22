package com.netaporter.pws.automation.nap.cucumber.steps.wishlistv3;

import com.netaporter.pws.automation.shared.components.AlertGenerator;
import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.pws.automation.nap.cucumber.steps.registration.RegistrationSteps;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.thoughtworks.selenium.SeleneseTestBase.assertTrue;

/**
 * Created by j.christian on 24/04/2014.
 */
public class AlertSteps extends BaseNapSteps {

    @Autowired
    AlertGenerator alertGenerator;

    @And("^I insert an alert in to the database for item ([0-9]+) with alert type (BACK|LOW|SALE|SALE_BACK|SALE_LOW)$")
    public void insertAlerts(Integer itemId, String alertType) throws Throwable {
        Integer customerId = productDataAccess.getLegacyDBClient().getCustomerIdByEmail(webBot.getRegionEnum(), (String) scenarioSession.getData(RegistrationSteps.REGISTERED_EMAIL_ADDRESS));

        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");


        AlertGenerator.AlertAT alert = AlertGenerator.AlertAT.generateSimpleAlert(
                customerId, skuList.get(itemId-1), AlertGenerator.AlertAT.PRODUCT_ALERT_TYPES.valueOf(alertType));

        alertGenerator.insertAlertIntoDb(webBot.getRegionEnum(), alert);
    }

    @And("^the wishlist item in position (\\d+) has the slug '(.+)'$")
    public void wishlistItemOrderHasTheCorrectSlug(int position, String slug) throws Throwable {

        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");

        String slugOnPageForSku = wishListV3Page.getSlugForItemInPosition(position);
        assertTrue("Unexpected slug for item in position "+position+
                ".  Expected:"+slug+" Actual:"+slugOnPageForSku, slug.equals(slugOnPageForSku));

    }

    @And("^wishlist item (\\d+) has the slug '(.+)'$")
    public void wishlistItemHasTheCorrectSlug(int item, String slug) throws Throwable {

        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");

        String slugOnPageForSku = wishListV3Page.getSlugForPid(skuToPid(skuList.get(item-1)));
        assertTrue("Unexpected slug for sku "+skuList.get(item-1)+
                ".  Expected:"+slug+" Actual:"+slugOnPageForSku, slug.equals(slugOnPageForSku));

    }

}
