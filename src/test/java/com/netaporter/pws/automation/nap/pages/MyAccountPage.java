package com.netaporter.pws.automation.nap.pages;

import com.netaporter.test.utils.pages.driver.WaitTime;
import com.netaporter.test.utils.pages.exceptions.PageElementNotFoundException;
import org.openqa.selenium.By;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: c.choudhury@london.net-a-porter.com
 * Date: 06/12/2012
 * Time: 16:38
 * To change this template use File | Settings | File Templates.
 */

@Component
@Scope("cucumber-glue")
public class MyAccountPage extends AbstractNapPage {

    private static final String PAGE_NAME = "My Account";
    private static final String PATH = "myaccount.nap";

    private By MANAGE_ACCOUNT_LOCATOR = By.id("manage-account");
    private By MY_SUBSCRIPTIONS_KEY = By.xpath(".//*[@id='my-subscription']/h2/a");
    public static final String WISHLIST_TITLE_CSS_SELECTOR = "#wish-list h2 a";
    public static final String WISHLIST_DESCRIPTION_CSS_SELECTOR = "#wish-list p a";

    public MyAccountPage() {
        super(PAGE_NAME, PATH);
    }

	public boolean isPageDisplayed() {
		try {
            return webBot.findElement(MANAGE_ACCOUNT_LOCATOR, WaitTime.TWO).isDisplayed();
        }
        catch (PageElementNotFoundException e) {
            return false;
        }
	}

    public boolean isMySubscriptionsLinkDisplayed() {
        try {
            return webBot.findElement(MY_SUBSCRIPTIONS_KEY, 2).isDisplayed();
        }
        catch(PageElementNotFoundException e){
            return false;
        }
    }

    public void clickWishlistTitleLink() {
        webBot.findElement(By.cssSelector(WISHLIST_TITLE_CSS_SELECTOR)).click();
    }

    public void clickWishlistDescriptionLink() {
        webBot.findElement(By.cssSelector(WISHLIST_DESCRIPTION_CSS_SELECTOR)).click();
    }

    public void clickMySubscriptionsLink() {
        webBot.click(MY_SUBSCRIPTIONS_KEY);
    }
}


