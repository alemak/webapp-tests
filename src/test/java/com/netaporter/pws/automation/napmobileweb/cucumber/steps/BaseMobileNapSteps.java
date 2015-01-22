package com.netaporter.pws.automation.napmobileweb.cucumber.steps;

import com.netaporter.pws.automation.napmobileweb.pages.*;
import com.netaporter.pws.automation.shared.steps.api.wishlistv3.WoasSteps;
import com.netaporter.test.utils.cucumber.steps.LegacyWebAppBaseStep;
import com.netaporter.test.utils.pages.driver.WaitTime;
import com.netaporter.wishlist.woas.client.WoasClient;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Base class for all Net-A-Porter Mobile step definitions.
 *
 * For convenience put all Page Objects in here so that they are available all concrete step definition classes.
 *
 * DO NOT put any Cucumber JVM specific annotations in this class.
 */
public abstract class BaseMobileNapSteps extends LegacyWebAppBaseStep {

    @Autowired
    protected MobileHomePage homePage;
    @Autowired
    protected MobileSignInPage signInPage;
    @Autowired
    protected MobileRegisterNewAccountPage registerNewAccountPage;
    @Autowired
    protected MobileWishListV3Page wishListV3Page;
    @Autowired
    protected MobileNAPProductDetailsPage productDetailsPage;
    @Autowired
    protected MobileMyAccountPage myAccountPage;
    @Autowired
    protected WoasClient woasAPIClient;
    @Autowired
    protected MobileSetupSessionPage setupSessionPage;
    @Autowired
    protected WoasSteps woasSteps;

    public AbstractMobileNapPage getCurrentMobileNapPage() {
        return (AbstractMobileNapPage)webBot.getCurrentPage();
    }

    public static String skuToPid(String sku) {
        return sku.split("-")[0];
    }

    public boolean iAmOnThe404ErrorPage() {
        if (doesElementExist(By.cssSelector("[id=en] h1"), WaitTime.ONE)) {
            List<WebElement> heading = webBot.findElements(By.cssSelector("[id=en] h1"));
            if(heading.size() > 0 && heading.get(0).toString().equalsIgnoreCase("This page cannot be found")) {
                return true;
            }
        }
        return false;
    }

    public boolean proxyError() {
        if (webBot.findElement(By.cssSelector("body")).getText().contains("Proxy Error")) {
            return true;
        }
        return false;
    }
}
