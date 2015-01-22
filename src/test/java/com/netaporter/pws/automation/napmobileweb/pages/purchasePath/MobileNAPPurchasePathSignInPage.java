package com.netaporter.pws.automation.napmobileweb.pages.purchasePath;

import com.netaporter.pws.automation.nap.pages.NAPPurchasePathSignInPage;
import org.openqa.selenium.By;

public class MobileNAPPurchasePathSignInPage extends NAPPurchasePathSignInPage {

    @Override
    public void clickSignIn() {
        webBot.findElement(By.className("button")).click();
    }

}
