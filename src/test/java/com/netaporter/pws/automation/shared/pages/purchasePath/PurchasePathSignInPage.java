package com.netaporter.pws.automation.shared.pages.purchasePath;

import com.netaporter.test.utils.pages.AbstractPage;
import org.openqa.selenium.By;

public abstract class PurchasePathSignInPage extends AbstractPage {

    public PurchasePathSignInPage() {
        super("Purchase path sign In", "");
    }

    public void enterEmailAddress(String text) {
        webBot.setText(By.name("j_username"), text);
    }

    public String getEmailAddress() {
        return webBot.findElement(By.name("j_username")).getAttribute("value");
    }

    public void enterPassword(String text) {
        webBot.findElement(By.name("j_password")).sendKeys(text);
    }

    public abstract void clickSignIn();

    public abstract void setYesIhavePassword();

    public abstract void setAnonymousUser();
}