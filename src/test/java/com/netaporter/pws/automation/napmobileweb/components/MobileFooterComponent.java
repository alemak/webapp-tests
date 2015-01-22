package com.netaporter.pws.automation.napmobileweb.components;

import com.netaporter.test.utils.pages.component.AbstractPageComponent;
import org.openqa.selenium.By;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: c.dawson@london.net-a-porter.com
 * Date: 30/12/2013
 * Time: 11:18
 * To change this template use File | Settings | File Templates.
 */
@Component
@Scope("cucumber-glue")
public class MobileFooterComponent extends AbstractPageComponent {

    private static final String SIGN_OUT_LINK = " div#mobileFooter div span#header-inject-isauth a";

    public void clickSignOut() {
        webBot.findElement(By.cssSelector(SIGN_OUT_LINK)).click();
    }
}
