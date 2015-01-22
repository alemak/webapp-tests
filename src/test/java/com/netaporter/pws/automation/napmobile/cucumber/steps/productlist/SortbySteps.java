package com.netaporter.pws.automation.napmobile.cucumber.steps.productlist;

import com.netaporter.pws.automation.napmobile.cucumber.steps.BaseNapMobileSteps;
import com.netaporter.pws.automation.napmobile.util.SortBy;
import com.netaporter.pws.automation.napmobile.util.SortByPair;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.util.List;

public class SortbySteps extends BaseNapMobileSteps {

    @Then("^sort by will display (.*)$")
    public void sort_by_will_display(String option) throws Throwable {
        Assert.assertTrue("Option = " + sortByMobileComponent.sortBySelectFirstSelectedOptionText(),
                sortByMobileComponent.sortBySelectFirstSelectedOptionText().equals(option)
        );
    }

    @When("^I apply sort by (.*)$")
    public void  I_apply_sort_by(SortBy sortBy) throws Throwable {
        sortByMobileComponent.applySortBySelectOptionByVisibleText(sortBy);
    }

    @Then("^sort by will contain (\\d+) sort options$")
    public void sort_by_will_contain_sort_options(int options) throws Throwable {
        Assert.assertTrue(
                "Options = " + sortByMobileComponent.sortBySelectOptionsSize(),
                sortByMobileComponent.sortBySelectOptionsSize() == options
        );
    }

    @Then("^sort by option (\\d+) will have a value of (.*)$")
    public void sort_by_will_contain_sort_options(Integer option, String value) throws Throwable {
        Assert.assertTrue(
                "Option " + option + " = " + sortByMobileComponent.sortBySelectOptionsIndexText(option-1),
                sortByMobileComponent.sortBySelectOptionsIndexText(option-1).equals(value)
        );
    }

    @Then("^sort by select will have the following:$")
    public void sort_by_select_will_have_the_following(List<SortByPair> pairs) throws Throwable {
        for (SortByPair pair : pairs) {
            sort_by_will_contain_sort_options(pair.getKey(), pair.getValue());
        }
    }
}
