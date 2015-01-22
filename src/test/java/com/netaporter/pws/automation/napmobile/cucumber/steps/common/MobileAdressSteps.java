package com.netaporter.pws.automation.napmobile.cucumber.steps.common;

import com.netaporter.pws.automation.napmobile.cucumber.steps.BaseNapMobileSteps;
import com.netaporter.pws.automation.shared.pojos.Address;
import cucumber.api.java.en.And;

/**
 * Created by ocsiki on 07/08/2014.
 */
public class MobileAdressSteps extends BaseNapMobileSteps {

    @And("^I add an (UK Premier) address on mobileweb$")
    public void I_add_an_intl_address_on_mobileweb(String address) throws Throwable {
        if ("UK Premier".equalsIgnoreCase(address))
            addressComponent.fillInAddress(Address.createNAPAddress());
    }
}
