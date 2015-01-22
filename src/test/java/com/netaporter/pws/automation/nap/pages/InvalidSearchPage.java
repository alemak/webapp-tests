package com.netaporter.pws.automation.nap.pages;

import com.netaporter.test.utils.pages.exceptions.PageElementNotFoundException;
import org.openqa.selenium.By;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.junit.Assert.fail;

/**
 * Created by ocsiki on 27/03/2014.
 */
@Component
@Scope("cucumber-glue")
public class InvalidSearchPage extends AbstractNapPage {

    private static final String PAGE_NAME = "Invalid Search";
    private static final String PATH = "/Shop/Search?keywords=qwerty";

    private By NO_SEARCH_RESULTS_ERROR_LOCATOR = By.id("no_search_results");

    public InvalidSearchPage() {
        super(PAGE_NAME, PATH);
    }

    public boolean isErrorMessageDisplayed() {
        try {
            webBot.findElement(NO_SEARCH_RESULTS_ERROR_LOCATOR);
        }
        catch (PageElementNotFoundException e) {
            fail("Invalid search page is not displayed");
        }
        return true;
    }
}
