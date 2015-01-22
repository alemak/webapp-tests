package com.netaporter.pws.automation.napmobileweb.pages;

import com.netaporter.pws.automation.napmobileweb.components.MobileSignInForm;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Encapsulates the functionality on the Sign In page where a user can sign in or register
 */
@Component
@Scope("cucumber-glue")
public class MobileSignInPage extends AbstractMobileNapPage {

    private static final String PAGE_NAME = "Sign In";
    private static final String PATH = "signin.nap";
    private static final By REGISTER_NOW_LINK = By.cssSelector("#register-submit.button.button-primary");

    private static final String SIGN_IN_PAGE_TITLE = "div#signin_top2.section h1 span";

    @Autowired
    private MobileSignInForm signInForm;

    public MobileSignInPage() {
        super(PAGE_NAME, PATH);
    }

    public void clickRegisterNow() {
        webBot.findElement(REGISTER_NOW_LINK).click();
    }

    public void signIn(String username, String password) {
        signInForm.typeUsername(username);
        signInForm.typePassword(password);
        signInForm.submit();
    }

    public String getPageTitle(){
        return webBot.findElement(By.cssSelector(SIGN_IN_PAGE_TITLE)).getText();
    }

}
