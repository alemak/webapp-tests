package com.netaporter.pws.automation.shared.utils;

import com.google.common.base.Predicate;
import com.netaporter.test.utils.pages.driver.WebDriverUtil;
import org.junit.Assert;
import org.openqa.selenium.*;

import javax.annotation.Nullable;

/**
 * User: x.qi@london.net-a-porter.com
 * Date: 10/06/2013
 */
public class WaitUtil {


    public static void clickWebElementAndWaitForSpinnerToAppearAndDisappear(WebElement webElement, WebDriverUtil webDriverUtil) {
        webElement.click();

        waitForSpinnerToAppearAndDisappear(webDriverUtil);
    }

    public static void waitForSpinnerToAppearAndDisappear(WebDriverUtil webDriverUtil) {
        waitFor(500);

        int maxWaitTimes = 10;

        boolean waitAgain = true;
        do {
            try {
                webDriverUtil.waitUntil(new Predicate<WebDriver>() {
                    @Override
                    public boolean apply(@Nullable WebDriver webDriver) {
                        try {
                            webDriver.findElement(By.id("floatingBarsG"));
                        } catch (org.openqa.selenium.NoSuchElementException e) {
                            return true;
                        }
                        return false;
                    }
                });
                waitAgain = false;
            }
            catch (RuntimeException e) {
                System.out.println("Page load not finished, so wait again...");
                if (--maxWaitTimes < 0) {
                    throw new RuntimeException(e);
                }

            }
        } while (waitAgain);

    }

    public static void waitFor(int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
