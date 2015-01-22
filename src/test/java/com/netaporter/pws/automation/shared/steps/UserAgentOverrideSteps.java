package com.netaporter.pws.automation.shared.steps;

import com.netaporter.test.utils.cucumber.steps.LegacyWebAppBaseStep;
import cucumber.api.java.After;
import cucumber.api.java.Before;


/**
 * Created with IntelliJ IDEA.
 * User: a.makarenko
 * Date: 10/23/13
 * Time: 9:49 AM
 * Allows the change of webdriver user agent on scenario level
 */
public class UserAgentOverrideSteps extends LegacyWebAppBaseStep {

    public static final String IPHONE_UA_OVERRIDE ="Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16";
    static String uaOverride;
    @Before("@userAgentOverride=iphone")
    public void beforeIphoneScenario(){
        uaOverride = webBot.getWebDriverFactory().getUserAgentOverride();
        webBot.quit();
        webBot.getWebDriverFactory().setUserAgentOverride(IPHONE_UA_OVERRIDE);
        webBot.getWebDriverFactory().resetDriver();
        webBot.getWebDriverFactory().getDriver(webBot.getBaseUrl());
    }
    @After("@userAgentOverride=iphone")
    public void afterIphonelScenario(){
        restoreUserAgent();
    }
    private void restoreUserAgent() {
          webBot.getWebDriverFactory().setUserAgentOverride(uaOverride);
    }
}
