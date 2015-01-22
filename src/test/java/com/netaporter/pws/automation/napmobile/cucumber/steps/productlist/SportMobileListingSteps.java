package com.netaporter.pws.automation.napmobile.cucumber.steps.productlist;

import com.netaporter.pws.automation.napmobile.cucumber.steps.BaseNapMobileSteps;
import com.netaporter.pws.automation.napmobile.pages.sporter.NapSportListingMobilePage;
import com.netaporter.pws.automation.shared.utils.WaitUtil;
import cucumber.api.java.en.When;

/**
 * Created by a.mosincat on 29/07/2014.
 */
public class SportMobileListingSteps extends BaseNapMobileSteps{


    private static final String PRODUCT_LISTING_MOBILE_PAGE_KEY = "productListingPage";
    private static final String CONDITION_KEY = "holds condition for assume";

    @When("^I click the (.*) from the sport mobile landing page$")
    public void I_click_the_link_from_the_sport_mobile_landing_page(String link) throws Throwable {
       NapSportListingMobilePage napSportListingMobilePage = napsportLandingMobilePage.clickAndReturnPage(link);
        webBot.setCurrentPage(napSportListingMobilePage);
        WaitUtil.waitFor(500);
        scenarioSession.putData(PRODUCT_LISTING_MOBILE_PAGE_KEY, napSportListingMobilePage);
        scenarioSession.putData(CONDITION_KEY, true);
    }
}
