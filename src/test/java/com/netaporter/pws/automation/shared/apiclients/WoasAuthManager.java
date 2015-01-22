package com.netaporter.pws.automation.shared.apiclients;

import com.jayway.restassured.response.Cookies;
import com.netaporter.pws.automation.nap.cucumber.steps.registration.RegistrationSteps;
import com.netaporter.test.utils.cucumber.ScenarioSession;
import com.netaporter.test.utils.pages.driver.WebDriverUtil;
import com.netaporter.wishlist.woas.client.commands.Region;
import org.openqa.selenium.Cookie;

/**
 * Created with IntelliJ IDEA.
 * User: J.Christian@net-a-porter.com
 * Date: 12/09/2013
 * Time: 17:27
 * To change this template use File | Settings | File Templates.
 */
public class WoasAuthManager {

    public static com.netaporter.wishlist.woas.client.commands.WoasAuth getAuth(ScenarioSession scenarioSession, WebDriverUtil webBot) {
        if (scenarioSession.getData(RegistrationSteps.REGISTRATION_COOKIES) != null) {
            return woasAuthFromQuickRegistration(scenarioSession);
        } else {
            return woasAuthFromBrowser(webBot);
        }
    }

    private static com.netaporter.wishlist.woas.client.commands.WoasAuth woasAuthFromBrowser(WebDriverUtil webBot) {
        Cookie channelCookie = webBot.getCookie("channel");
        String regionString = channelCookie.getValue().toUpperCase();

        return woasAuth(Region.valueOf(regionString),
                webBot.getCookie("JSESSIONID_" + regionString).getValue(),
                webBot.getCookie("remembered_7070").getValue());
    }

    private static com.netaporter.wishlist.woas.client.commands.WoasAuth woasAuthFromQuickRegistration(ScenarioSession scenarioSession) {
        Cookies cookies = (Cookies) scenarioSession.getData(RegistrationSteps.REGISTRATION_COOKIES);
        String regionString = cookies.getValue("channel").toUpperCase();

        return woasAuth(Region.valueOf(regionString),
                cookies.getValue("JSESSIONID_" + regionString),
                cookies.getValue("remembered_7070"));
    }

    private static com.netaporter.wishlist.woas.client.commands.WoasAuth woasAuth(Region region, String jsessionid, String remembered) {
        //create new WOAS Auth with channel, auth token and remember token
        com.netaporter.wishlist.woas.client.commands.WoasAuth woasAuth = new com.netaporter.wishlist.woas.client.commands.WoasAuth(region);
        woasAuth.setAuthToken(jsessionid);
        woasAuth.setRememberToken(remembered);

        return woasAuth;
    }

}
