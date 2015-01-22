package com.netaporter.pws.automation.napmobile.cucumber.steps.productlist;

import com.netaporter.pws.automation.napmobile.cucumber.steps.BaseNapMobileSteps;
import com.netaporter.pws.automation.napmobile.util.Category;
import com.netaporter.pws.automation.shared.utils.WaitUtil;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class FilterSteps extends BaseNapMobileSteps {




    @Then("^the accordion link heading will display (.*)$")
    public void the_accordion_link_heading_will_display(String heading) throws Throwable {
        Assert.assertTrue(
                "Heading = " + filterMobileComponent.accordionLinkHeaderText(),
                filterMobileComponent.accordionLinkHeaderText().equals(heading)
        );
    }

    @Then("^the accordion control will display (.*)$")
    public void the_accordion_control_will_display(String control) throws Throwable {
        Assert.assertTrue(
                "Control = " + filterMobileComponent.accordionControlText(),
                filterMobileComponent.accordionControlText().equals(control)
        );
    }

    @When("^I click the accordion control|control again$")
    public void I_click_the_accordion_control() throws Throwable {
        WaitUtil.waitFor(500);
        //filterMobileComponent.accordionControlClickFromDifferentPage();
        filterMobileComponent.accordionControlClick();
            }

    @And("^the accordion content will be displayed$")
    public void the_accordion_content_will_be_displayed() throws Throwable {
        Assert.assertTrue(
                "Content = " + filterMobileComponent.accordionContentDisplayed(),
                filterMobileComponent.accordionContentDisplayed()
        );
    }

    @And("^the accordion content will not be displayed$")
    public void the_accordion_content_will_not_be_displayed() throws Throwable {
        Assert.assertFalse(
                "Content = " + filterMobileComponent.accordionContentDisplayed(),
                filterMobileComponent.accordionContentDisplayed()
        );
    }

    @Then("^category filter will contain at least (\\d+) options$")
    public void category_filter_will_contain_at_least_options(int options) throws Throwable {
        I_click_the_accordion_control();
        Assert.assertTrue(
                "Options = " + filterMobileComponent.categoryFilterSelectOptionsSize(),
                filterMobileComponent.categoryFilterSelectOptionsSize() >= options
        );
    }

    @Then("^category filter will contain valid category values$")
    public void category_filter_will_contain_valid_category_values() throws Throwable {
        I_click_the_accordion_control();
        Assert.assertTrue(
                "Invalid = " + filterMobileComponent.categoryFilterSelectOptionsContainValidCategoryValues().toString(),
                filterMobileComponent.categoryFilterSelectOptionsContainValidCategoryValues().isEmpty()
        );
    }

    @Then("^category filter will display (.*)$")
    public void category_filter_will_display(String option) throws Throwable {
        I_click_the_accordion_control();
        Assert.assertTrue(
                "Options = " + filterMobileComponent.categoryFilterAllSelectedOptionText().toString(),
                filterMobileComponent.categoryFilterAllSelectedOptionText().contains(option)
        );
    }

    @When("^I apply category (.*)$")
    public void  I_apply_category(Category category) throws Throwable {
        I_click_the_accordion_control();
        filterMobileComponent.applyCategoryFilterOptionByVisibleText(category);
    }

    @When("^I apply sub-list (.*)$")
    public void  I_apply_sub_list(String subList) throws Throwable {
        I_click_the_accordion_control();
        filterMobileComponent.applySubListFilterOptionByVisibleText(subList);
    }

    @When("^I apply sub-list-child (.*)$")
    public void  I_apply_sub_list_child(String subListChild) throws Throwable {
        I_click_the_accordion_control();
        filterMobileComponent.applySubListChildFilterOptionByVisibleText(subListChild);
    }

    @When("^I apply sub-list-sibling (.*)$")
    public void  I_apply_sub_list_sibling(String subListSibling) throws Throwable {
        I_click_the_accordion_control();
        WaitUtil.waitFor(6000);
        filterMobileComponent.applySubListSiblingFilterOptionByVisibleText(subListSibling);
    }

    @When("^I apply sub-list-parent (.*)$")
    public void  I_apply_sub_list_parent(String subListParent) throws Throwable {
        I_click_the_accordion_control();
        filterMobileComponent.applySubListParentFilterOptionByVisibleText(subListParent);
    }

    @When("^I apply colour select option|options:$")
    public void I_apply_colour_select_options(List<Integer> options) throws Throwable {
        I_click_the_accordion_control();
        WaitUtil.waitFor(5000);
        filterMobileComponent.colourFilterDeselectAllOptions();
        List<String> colours = filterMobileComponent.applyColourFilterOptionByIndex(options);
        scenarioSession.putData("selectedColoursCount", colours.size());
        int count = 1;
        for (String colour : colours) {
            scenarioSession.putData("selectedColours" + Integer.toString(count), colour);
            count++;
        }
    }

    @Then("^colour filter will contain at least (\\d+) options$")
    public void colour_filter_will_contain_at_least_options(int options) throws Throwable {
        I_click_the_accordion_control();
        Assert.assertTrue(
                "Options = " + filterMobileComponent.colourFilterSelectOptionsSize(),
                filterMobileComponent.colourFilterSelectOptionsSize() >= options
        );
    }

    @Then("^colour filter allows multiple select$")
    public void colour_filter_allows_multiple_select() throws Throwable {
        I_click_the_accordion_control();
        Assert.assertTrue(
                "Multiple = " + filterMobileComponent.colourFilterIsMultipleSelect(),
                filterMobileComponent.colourFilterIsMultipleSelect()
        );
    }

    @Then("^colour filter will display (.*)$")
    public void colour_filter_will_display(String option) throws Throwable {
        I_click_the_accordion_control();
        WaitUtil.waitFor(2000);
        Assert.assertTrue("Option = " + filterMobileComponent.colourFilterFirstSelectedOptionText(),
                filterMobileComponent.colourFilterFirstSelectedOptionText().equals(option)
        );
    }

    @Then("^the colour filter|filters will be included in the url$")
    public void the_colour_filter_will_be_included_in_the_url() throws Throwable {
        Collection<String> selectedColours = new HashSet<String>();
        Integer count = (Integer) scenarioSession.getData("selectedColoursCount");
        for (int i = 1; i <= count; i++) {
            String colour = (String) scenarioSession.getData("selectedColours" + Integer.toString(i));
            selectedColours.add(colour);
        }
        Assert.assertTrue(
                "Selected Colours = "
                        + selectedColours.toString()
                        + " Url Colours = "
                        + filterMobileComponent.coloursInUrl().toString(),
                filterMobileComponent.onlySelectedColoursAreInUrl(selectedColours)
        );
    }

    @When("^I apply designer select option|options:$")
    public void I_apply_designer_select_options(List<Integer> options) throws Throwable {
        I_click_the_accordion_control();
        filterMobileComponent.designerFilterDeselectAllOptions();
        List<String> designers = filterMobileComponent.applyDesignerFilterOptionByIndex(options);
        scenarioSession.putData("selectedDesignersCount", designers.size());
        int count = 1;
        for (String designer : designers) {
            scenarioSession.putData("selectedDesigners" + Integer.toString(count), designer);
            count++;
        }
    }

    @Then("^designer filter will contain at least (\\d+) options$")
    public void designer_filter_will_contain_at_least_options(int options) throws Throwable {
        I_click_the_accordion_control();
        Assert.assertTrue(
                "Options = " + filterMobileComponent.designerFilterSelectOptionsSize(),
                filterMobileComponent.designerFilterSelectOptionsSize() >= options
        );
    }

    @Then("^designer filter allows multiple select$")
    public void designer_filter_allows_multiple_select() throws Throwable {
        I_click_the_accordion_control();
        Assert.assertTrue(
                "Multiple = " + filterMobileComponent.designerFilterIsMultipleSelect(),
                filterMobileComponent.designerFilterIsMultipleSelect()
        );
    }

    @Then("^designer filter will display (.*)$")
    public void designer_filter_will_display(String option) throws Throwable {
        I_click_the_accordion_control();
        Assert.assertTrue(
                "Option = " + filterMobileComponent.designerFilterFirstSelectedOptionText(),
                filterMobileComponent.designerFilterFirstSelectedOptionText().equals(option)
        );
    }

    @Then("^size filter will display (.*)$")
    public void size_filter_will_display(String option) throws Throwable {
        I_click_the_accordion_control();
        WaitUtil.waitFor(2000);
         Assert.assertTrue(
                "Option = " + filterMobileComponent.sizeFilterFirstSelectedOptionText(),
                filterMobileComponent.sizeFilterFirstSelectedOptionText().equals(option)
        );
    }

    @Then("^size filter will contain at least (\\d+) options$")
    public void size_filter_will_contain_at_least_options(int options) throws Throwable {
        I_click_the_accordion_control();
        Assert.assertTrue(
                "Options = " + filterMobileComponent.sizeFilterSelectOptionsSize(),
                filterMobileComponent.sizeFilterSelectOptionsSize() >= options
        );
    }

    @Then("^size filter allows multiple select$")
    public void size_filter_allows_multiple_select() throws Throwable {
        I_click_the_accordion_control();
        Assert.assertTrue(
                "Multiple = " + filterMobileComponent.sizeFilterIsMultipleSelect(),
                filterMobileComponent.sizeFilterIsMultipleSelect()
        );
    }

    @When("^I apply size select option|options:$")
    public void I_apply_size_select_options(List<Integer> options) throws Throwable {
        I_click_the_accordion_control();
        filterMobileComponent.sizeFilterDeselectAllOptions();
        List<String> sizes = filterMobileComponent.applySizeFilterOptionByIndex(options);
        scenarioSession.putData("selectedSizesCount", sizes.size());
        int count = 1;
        for (String size : sizes) {
            scenarioSession.putData("selectedSizes" + Integer.toString(count), size);
            count++;
        }
    }

    @Then("^the size filter|filters will be included in the url$")
    public void the_size_filter_will_be_included_in_the_url() throws Throwable {
        Collection<String> selectedSizes = new HashSet<String>();
        Integer count = (Integer) scenarioSession.getData("selectedSizesCount");
        for (int i = 1; i <= count; i++) {
            String size = (String) scenarioSession.getData("selectedSizes" + Integer.toString(i));
            selectedSizes.add(size);
        }
        Assert.assertTrue(
                "Selected Sizes = "
                        + selectedSizes.toString()
                        + " Url Sizes = "
                        + filterMobileComponent.sizesInUrl().toString(),
                filterMobileComponent.onlySelectedSizesAreInUrl(selectedSizes)
        );
    }

    @And("^I reset the filter$")
    public void I_reset_the_filter() throws Throwable {
        filterMobileComponent.filterResetClick();
    }
}
