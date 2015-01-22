package com.netaporter.pws.automation.nap.pages;

import com.netaporter.pws.automation.nap.components.SignInForm;
import com.netaporter.test.utils.pages.driver.WaitTime;
import com.netaporter.test.utils.pages.exceptions.PageElementNotFoundException;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Encapsulates the functionality on the Sign In page where a user can sign in or register
 */
@Component
@Scope("cucumber-glue")
public class SignInPage extends AbstractNapPage {

    private static final String PAGE_NAME = "Sign In";
    private static final String PATH = "signin.nap";

    private By REGISTER_NOW_LINK_LOCATOR = By.cssSelector("#register-submit a");
    private By CHANGE_PASSWORD_LINK_LOCATOR = By.xpath("//*[@id='forgotten-submit']");
    private By SIGN_IN_TITLE_LOCATOR = By.cssSelector("#top_left_purchase_path_progress h1 span");
    private By ERROR_ELEMENT_LOCATOR = By.xpath(".//*[@id='content']/ul");
    private By UKNOWN_EMAIL_PROVIDER_CHECKBOX = By.id("passwordcheckbox");

    @Autowired
    private SignInForm signInForm;

    public SignInPage() {
        super(PAGE_NAME, PATH);
    }

    public void clickRegisterNow() {
        webBot.click(REGISTER_NOW_LINK_LOCATOR);
    }

    public void signIn(String username, String password) {
        signInForm.typeUsername(username);
        signInForm.typePassword(password);
        signInForm.submit();
    }

    public boolean isDisplayingSignInErrorMessage() {
        try {
            webBot.findElement(ERROR, WaitTime.FOUR);
        }
        catch(PageElementNotFoundException e) {
            return false;
        }
        return true;
    }

    public void clickChangePassword() {
        webBot.click(CHANGE_PASSWORD_LINK_LOCATOR);
    }

    public String getErrorContent() {
        return webBot.getElementText(ERROR_ELEMENT_LOCATOR);
    }

    public boolean verifyPage() throws Throwable {
        if(doesElementExist(SIGN_IN_TITLE_LOCATOR)) {
            return webBot.getElementText(SIGN_IN_TITLE_LOCATOR).equals("SIGN IN");
        };
        return false;
//        waitForElementToExist(By.cssSelector("#top_left_purchase_path_progress h1 span"));
    }

    public boolean checkUnknownEmailProviderCheckbox() {
        try {
            webBot.click(UKNOWN_EMAIL_PROVIDER_CHECKBOX);
        }
        catch(PageElementNotFoundException e) {
            return false;
        }
        return true;
    }
}
