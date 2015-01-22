package com.netaporter.pws.automation.napmobile.pages.purchasePath;

import com.netaporter.pws.automation.napmobile.pages.AbstractNapMobilePage;
import org.openqa.selenium.By;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by ocsiki on 07/08/2014.
 */
@Component
@Scope("cucumber-glue")
public class NAPPurchasePathSignInMobilePage extends AbstractNapMobilePage {

    private static final String PAGE_NAME = "Sign In Purchase Path Mobile Page";
    private static final String PAGE_URL = "/signinpurchasepath.nap";

    private By USER_NAME_LOCATOR = By.name("j_username");
    private By SIGN_IN_BUTTON_LOCATOR = By.className("purchase-path-sign-in");

    public NAPPurchasePathSignInMobilePage() {
        super(PAGE_NAME, PAGE_URL);
    }
    public NAPPurchasePathSignInMobilePage(String pageName, String path) {
        super(pageName, path);
    }

    @Override
    public boolean at() {
        return false;
    }


    public void signInAsGuest() {
        webBot.findElements(USER_NAME_LOCATOR).get(0).sendKeys("test12345@hotmails.com");
        webBot.findElements(SIGN_IN_BUTTON_LOCATOR).get(0).click();
    }
}
