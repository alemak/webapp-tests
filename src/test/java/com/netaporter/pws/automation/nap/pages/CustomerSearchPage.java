package com.netaporter.pws.automation.nap.pages;

import com.netaporter.test.utils.pages.driver.WaitTime;
import com.netaporter.test.utils.pages.exceptions.PageElementNotFoundException;
import org.openqa.selenium.By;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class CustomerSearchPage extends AbstractNapPage {

    private By SUBMIT_BUTTON_LOCATOR = By.xpath("//form[@id='switch-user']/input[2]");
    private By CUSTOMER_EMAIL = By.id("email");
    private By ADMIN_SEARCH_LOCATOR = By.id("admin-search");

    public CustomerSearchPage() {
		super("Customer Search", "admin.nap");
	}

	public boolean isPageDisplayed() {
        try {
            return webBot.findElement(ADMIN_SEARCH_LOCATOR, WaitTime.TWO).isDisplayed();
        }
        catch (PageElementNotFoundException e) {
            return false;
        }
	}

	public void switchToUser(String email) {
		webBot.type(CUSTOMER_EMAIL, email);
		webBot.findElement(SUBMIT_BUTTON_LOCATOR).click();
	}
}
