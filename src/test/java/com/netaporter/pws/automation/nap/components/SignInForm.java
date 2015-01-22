package com.netaporter.pws.automation.nap.components;

import com.netaporter.test.utils.pages.component.AbstractPageComponent;
import org.openqa.selenium.By;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Form used to sign in to NAP account
 */
@Component
@Scope("cucumber-glue")
public class SignInForm extends AbstractPageComponent {

    private static final By USERNAME = By.cssSelector("#registered-customers input[name='j_username']");
    private static final By PASSWORD = By.cssSelector("#registered-customers input[name='j_password']");
    private static final By SUBMIT_BUTTON = By.cssSelector("#registered-submit input");

    public void typeUsername(String username) {
        webBot.type(USERNAME, username);
    }

    public void typePassword(String password) {
        webBot.type(PASSWORD, password);
    }

    public void submit() {
        webBot.findElement(SUBMIT_BUTTON).submit();
    }
}
