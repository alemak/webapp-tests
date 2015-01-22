package com.netaporter.pws.automation.nap.pages;

import org.openqa.selenium.By;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: c.dawson@london.net-a-porter.com
 * Date: 14/08/2013
 * Time: 11:35
 * To change this template use File | Settings | File Templates.
 */
@Component
@Scope("cucumber-glue")
public class PageNotFoundPage extends AbstractNapPage {

    private static final String PAGE_NAME = "This page cannot be found";
    private static final String PATH = "";

    public PageNotFoundPage() {
        super(PAGE_NAME, PATH);
    }

    public Boolean verifyOnPageNotFoundPage() {
        By element = By.cssSelector("html body div#main div#content div#en.error-content h1");
        return webBot.findElement(element).getText().equals(PAGE_NAME.toUpperCase());
    }
}
