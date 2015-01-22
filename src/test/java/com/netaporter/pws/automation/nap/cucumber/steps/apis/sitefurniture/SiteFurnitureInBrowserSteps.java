package com.netaporter.pws.automation.nap.cucumber.steps.apis.sitefurniture;

import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.test.utils.pages.IPage;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: J.Christian@net-a-porter.com
 * Date: 15/05/2013
 * Time: 17:02
 */
public class SiteFurnitureInBrowserSteps extends BaseNapSteps {

    @Value("#{webDriverUtilConfig.baseUrl}")
    private String baseUrl;

    @When("^I access the site furniture api via the browser$")
    public void I_access_the_site_furniture_api() throws Throwable {
        final String url = SiteFurnitureSteps.getSiteFurniturePath(null);

        // Seems overkill to create an IPage for an API call, so just creating an anon instance
        webBot.goToPage(new IPage() {
            @Override
            public String getPath() {
                return url;
            }

            // Dummy implementation
            @Override public void go() { }
            @Override public void goWithParams(String params) { }
            @Override public String getTitle() { return null; }
            @Override public List<String> getErrorMessages() { return null; }
            @Override public List<String> getMandatoryFieldErrors() { return null; }
            @Override public List<String> getMandatoryDropDownFieldErrors() { return null; }
            @Override public boolean isPageRegionalised() { return false; }
            @Override public String getRegionalisedPath() { return null;  }
        });
    }
}
