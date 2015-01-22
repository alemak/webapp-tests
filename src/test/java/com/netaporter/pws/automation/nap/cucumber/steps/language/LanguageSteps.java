package com.netaporter.pws.automation.nap.cucumber.steps.language;

import com.netaporter.pws.automation.nap.components.ChangeLanguageComponent;
import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.testng.AssertJUnit.assertTrue;

/**
 * Created by s.joshi on 14/11/2014.
 */
public class LanguageSteps extends BaseNapSteps {

    public static final String LANGUAGE_KEY = "language";

    @When ("^I click the language link$")
    public void click_language_link(){
        changeLanguageComponent.clickLanguageLink();
    }

    @When("^I switch my language to (.*)$")
    public void I_switch_my_language_to_language(String language) throws Throwable {
        changeLanguageComponent.switchToLanguage(language);
        scenarioSession.putData(LANGUAGE_KEY, language);
    }

    @Then("^a drop down list appears with four languages$")
    public void a_drop_down_list_appears_with_four_languages() throws Throwable {
        assertTrue("Language drop down is missing or supported languages are not displayed",changeLanguageComponent.isLanguageDropdownWithLanguageDisplayed());
    }

    @Then("^correct language label is displayed on header in (.*) page$")
    public void correct_language_label_is_displayed_in_header_and_url(String pageType) throws Throwable {
        String language = scenarioSession.getData(LANGUAGE_KEY);
        assertTrue("Correct language is not displayed in header", changeLanguageComponent.isCorrectLanguageDisplayedInHeader(language, pageType));
        assertTrue("Correct language is not displayed in body tag", changeLanguageComponent.isCorrectLanguageDisplayedInHeader(language));
    }
}
