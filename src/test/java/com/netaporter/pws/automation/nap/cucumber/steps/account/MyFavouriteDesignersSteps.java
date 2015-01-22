package com.netaporter.pws.automation.nap.cucumber.steps.account;

import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: o_csiki
 * Date: 13/09/13
 */
public class MyFavouriteDesignersSteps extends BaseNapSteps{

    public static final String SELECTED_FAVOURITE_DESIGNER_KEY = "selectedFavouriteDesigner";

    @And("^I select one favourite designer$")
    public void I_select_one_favourite_designer() {

        List<WebElement> designerCheckboxes = myFavouriteDesignersPage.getAllListedDesignerElements();
        Collections.shuffle(designerCheckboxes);

        final WebElement selectedDesignerId = designerCheckboxes.get(0);
        List<String> selectedDesignerNames = new ArrayList<String>();
        selectedDesignerNames.add(selectedDesignerId.findElement(By.xpath("../label")).getText());

        scenarioSession.putData(SELECTED_FAVOURITE_DESIGNER_KEY, selectedDesignerNames);

        selectedDesignerId.click();
        myFavouriteDesignersPage.clickSubmitButton();
    }

    @Then("^my favourite designer selection is kept$")
    public void my_favourite_designer_selection_is_kept() throws Throwable {

        List<String> selectedDesignerNames = scenarioSession.getData(SELECTED_FAVOURITE_DESIGNER_KEY);
        List<String> designerNamesFromPage = myFavouriteDesignersPage.getSelectedDesignerName();
        Collections.sort(selectedDesignerNames);
        Collections.sort(designerNamesFromPage);

        assertThat(selectedDesignerNames.size(), is(designerNamesFromPage.size()));
        for (int i =0;i<selectedDesignerNames.size();i++) {
            assertThat(designerNamesFromPage.get(i), equalToIgnoringCase(selectedDesignerNames.get(i)));
        }
    }
}