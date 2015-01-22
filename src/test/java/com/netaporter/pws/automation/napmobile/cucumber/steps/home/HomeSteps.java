package com.netaporter.pws.automation.napmobile.cucumber.steps.home;

import com.netaporter.pws.automation.napmobile.cucumber.steps.BaseNapMobileSteps;
import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertTrue;

public class HomeSteps extends BaseNapMobileSteps {

    @Then("^Logo link is displayed$")
    public void logo_link_should_appear() {
        headerMobileComponent.getHeaderLogoLink();
    }


    @Then("^Logo link image is displayed$")
    public void Logo_link_image_is_displayed() throws Throwable {
        headerMobileComponent.getHeaderLogoLinkImage();
    }


}