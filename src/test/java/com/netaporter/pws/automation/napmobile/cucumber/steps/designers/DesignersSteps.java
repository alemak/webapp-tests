package com.netaporter.pws.automation.napmobile.cucumber.steps.designers;

import com.netaporter.pws.automation.napmobile.cucumber.steps.BaseNapMobileSteps;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

public class DesignersSteps extends BaseNapMobileSteps {

    @When("^I apply designers by category (.*)$")
    public void I_apply_designers_by_category(String value) {
        designersMobilePage.designersByCategorySelectOptionByVisibleText(value);
    }

    @Then("^designers by category will display (.*)$")
    public void designers_by_category_will_display(String option) throws Throwable {
        Assert.assertTrue(
                "Option = " + designersMobilePage.designersByCategorySelectFirstSelectedOptionText(),
                designersMobilePage.designersByCategorySelectFirstSelectedOptionText().equals(option)
        );
    }
}
