package com.netaporter.pws.automation.nap.cucumber.steps;

import cucumber.api.java.en.When;
import org.openqa.selenium.Dimension;

public class BrowserSteps extends BaseNapSteps {


    @When("^I resize the width to (\\d+)$")
    public void I_resize_the_width_to(int width) {
        webBot.getDriver().manage().window().setSize(new Dimension(width, 1000));
    }
}
