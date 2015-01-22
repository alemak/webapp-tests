package com.netaporter.pws.automation.nap.pages;

import com.netaporter.test.utils.pages.driver.WebDriverUtil;
import org.openqa.selenium.By;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: cucumber
 * Date: 30/10/13
 */
@Component
@Scope("cucumber-glue")
public class MySubscriptionPage extends AbstractNapPage {

    private By VIEW_DELIVERY_DATES_ELEMENT = By.xpath(".//*[@id='my-account']/div[2]/div[2]/p/a[3]");
    private By CHANGE_MY_DETAILS_ELEMENT = By.xpath(".//*[@id='my-account']/div[2]/div[2]/p/a[2]");
    private By REVIEW_MY_SUBSCRIPTION_ELEMENT = By.xpath(".//*[@id='my-account']/div[2]/div[2]/p/a[1]");

    public MySubscriptionPage() {
        super("My Subscription", "/subscription.nap");
    }

    public void clickReviewMySubscription() {

        webBot.click(REVIEW_MY_SUBSCRIPTION_ELEMENT);
        try {
            webBot.focusToOtherWindow();
        } catch (WebDriverUtil.WebDriverUtilException e) {
            e.printStackTrace();
        }
    }

    public void clickChangeMyDetails() {

        webBot.click(CHANGE_MY_DETAILS_ELEMENT);
        try {
            webBot.focusToOtherWindow();
        } catch (WebDriverUtil.WebDriverUtilException e) {
            e.printStackTrace();
        }
    }

    public void clickViewDeliveryDates() {

        webBot.click(VIEW_DELIVERY_DATES_ELEMENT);
        try {
            webBot.focusToOtherWindow();
        } catch (WebDriverUtil.WebDriverUtilException e) {
            e.printStackTrace();
        }
    }
}
