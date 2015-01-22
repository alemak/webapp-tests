package com.netaporter.pws.automation.napmobileweb.pages;

import com.netaporter.pws.automation.napmobileweb.components.MobileFooterComponent;
import com.netaporter.test.utils.pages.driver.WebDriverUtil;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Encapsulates the functionality on the Home page
 */
@Component
@Scope("cucumber-glue")
public class MobileHomePage extends AbstractMobileNapPage {

    private static final String PAGE_NAME = "Home";
    private static final String PATH = "home.nap";
    private static final String CSS_SELECTOR_SIGN_IN_LINK = "#mobileFooter div span#header-inject-notauth a";
    private static final By SIGN_IN_LINK = By.cssSelector(CSS_SELECTOR_SIGN_IN_LINK);


    @Autowired
    protected MobileFooterComponent footerComponent;

    public MobileHomePage() {
        super(PAGE_NAME, PATH);
    }

    public void clickSignInLink() {
        try {
            // Sign in link is AJAX enabled so need to wait
            Thread.sleep(1200);
        } catch (InterruptedException e) {

        }
        webBot.findElement(SIGN_IN_LINK).click();
    }

    public void waitForSignInLinkToExist() throws Throwable {
        waitForElementToExist(By.cssSelector(CSS_SELECTOR_SIGN_IN_LINK));
    }

    public void waitForSignInLinkToBeVisible() throws Throwable {
        waitForElementToBeVisible(CSS_SELECTOR_SIGN_IN_LINK);
    }

    public void clickSignOutLink() {
        footerComponent.clickSignOut();
    }


}
