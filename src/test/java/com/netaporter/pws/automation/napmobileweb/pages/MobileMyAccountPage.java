package com.netaporter.pws.automation.napmobileweb.pages;

import org.openqa.selenium.By;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: J.Christian@net-a-porter.com
 * Date: 14/10/2013
 * Time: 11:23
 * To change this template use File | Settings | File Templates.
 */
@Component
@Scope("cucumber-glue")
public class MobileMyAccountPage extends AbstractMobileNapPage {

    private static final String PAGE_NAME = "My Account";
    private static final String PATH = "myaccount.nap";

    public static final String WISHLIST_CSS_SELECTOR = "#wish-list h2 a";

    public MobileMyAccountPage() {
        super(PAGE_NAME, PATH);
    }

    public void clickWishlistLink() {
        webBot.findElement(By.cssSelector(WISHLIST_CSS_SELECTOR)).click();
    }

}
