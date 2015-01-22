package com.netaporter.pws.automation.shared.steps;


import com.netaporter.pws.automation.shared.pojos.Customer;
import com.netaporter.test.utils.cucumber.steps.LegacyWebAppBaseStep;
import com.netaporter.test.utils.enums.RegionEnum;
import cucumber.api.java.en.Given;

import java.text.DecimalFormat;

public class CustomerCreditsSteps extends LegacyWebAppBaseStep {

    @Given("^I have store credit to the amount of (.*) (.*)$")
    public void giveCustomerStoreCredit(Double amount, String currency) {
        Customer customer = scenarioSession.getData("customer");
        if (customer == null) {
            throw new AssertionError("no customer set up in session");
        }

        int customerId = productDataAccess.getLegacyDBClient().getCustomerIdByEmail(RegionEnum.valueOf(webBot.getRegion()),customer.getEmail());
        productDataAccess.getLegacyDBClient().giveCustomerStoreCredit(RegionEnum.valueOf(webBot.getRegion()),customerId, currency, amount);
        scenarioSession.putData("store-credit", new DecimalFormat("#.00").format(amount));
    }

}
