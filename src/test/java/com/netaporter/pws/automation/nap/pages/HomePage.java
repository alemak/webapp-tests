package com.netaporter.pws.automation.nap.pages;

import com.netaporter.pws.automation.nap.components.HeaderComponent;
import com.netaporter.test.utils.pages.driver.WaitTime;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.junit.Assert.assertTrue;

/**
 * Encapsulates the functionality on the Home page
 */
@Component
@Scope("cucumber-glue")
public class HomePage extends AbstractNapPage {

    private static final String PAGE_NAME = "Home";
    private static final String PATH = "home.nap";

    private By SIGN_IN_LINK = By.cssSelector("#header-sign-in a[target='_top']");

    @Autowired
    private HeaderComponent headerContent;

    public HomePage() {
        super(PAGE_NAME, PATH);
    }

    public void clickSignInLink() {
        try {
            waitForSignInLinkToAppear();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        webBot.click(SIGN_IN_LINK);
    }

    public void waitForSignInLinkToAppear() throws Throwable {
        waitForElementToNotExist(By.cssSelector("#header-sign-in[style='display: none;']"));
        waitForElementToExist(By.cssSelector("#header-sign-in"));
        waitForElementToBeVisible("#header-sign-in");
        if (!webBot.findElement(SIGN_IN_LINK, WaitTime.FIVE).isDisplayed()) {
            throw new Throwable("Sign in link not visible");
        }
    }

    public void verifyHomePage() {
        assertTrue("Actual homepage title: " +webBot.getTitle(), webBot.getTitle().equalsIgnoreCase("NET-A-PORTER.COM | Luxury Designer Fashion | Women's designer clothes, shoes, bags & accessories"));
    }

    @Override
    public boolean isPageRegionalised() {
        return true;
    }

}