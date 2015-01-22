package com.netaporter.pws.automation.nap.cucumber.steps.shopping;

import com.netaporter.pws.automation.shared.steps.purchasePath.BasePurchasePathStep;
import com.netaporter.test.utils.pages.IPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.openqa.selenium.Cookie;

import java.util.Map;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class LinkShareSteps extends BasePurchasePathStep {

    private static final String SITE_ID = "nap-test";
    private static final String LINKSHARE_SITE_ID_COOKIE_NAME = "linkshare_siteID";
    private static final String LINKSHARE_TIME_ENTERED_COOKIE_NAME = "linkshare_time_entered";

  @Given("I visit (.*) page with an affiliate Id")
    public void I_visit_a_page_with_an_affiliate_Id(String page) {
      IPage pageInstance = lookupPage(page);
      pageInstance.goWithParams("siteID="+SITE_ID);
    }

   @Given("^a linkshare cookie is stored in my browser$")
    public void a_linkshare_cookie_is_stored_in_my_browser() throws Throwable {

       boolean foundLinkShareSiteIdCookie = false;
       boolean foundLinkShareTimeCookie = false;

       Set<Cookie> cookies = webBot.getCookies();
       for (Cookie cookie : cookies) {
           if (LINKSHARE_SITE_ID_COOKIE_NAME.equals(cookie.getName())) {
               foundLinkShareSiteIdCookie = true;
               assertThat(SITE_ID, is(cookie.getValue()));
           }
           if (LINKSHARE_TIME_ENTERED_COOKIE_NAME.equals(cookie.getName())) {
               foundLinkShareTimeCookie = true;
           }
       }
       assertTrue(foundLinkShareSiteIdCookie);
       assertTrue(foundLinkShareTimeCookie);
    }

    @Then("an affiliate order line should be created")
    public void an_affiliate_order_line_should_be_created() throws InterruptedException {
       assertNotNull(scenarioSession.getData("latestOrderId"));

       boolean tryAgain;
       Map affiliateOrderLine;
       int i = 0;

           do{
               try{
                   affiliateOrderLine = productDataAccess.getLegacyDBClient().getAffiliateOrderlinesForOrderId(webBot.getRegionEnum(), scenarioSession.getData("latestOrderId").toString()).get(0);
                   tryAgain = true;
                   assertNotNull(affiliateOrderLine);
                   assertEquals(getSkusFromScenarioSession().iterator().next(), affiliateOrderLine.get("sku"));
                   assertEquals(1, affiliateOrderLine.get("quantity"));
                   assertEquals(SITE_ID, affiliateOrderLine.get("site_id"));
                   break;
               }
               catch(IndexOutOfBoundsException e){
                   //if the dB is too slow to respond, we will need to wait and recheck to get the order
                   Thread.sleep(200);
                   tryAgain = false;
                   i++;
               }
           } while (!tryAgain && i <=10);
    }
}