package com.netaporter.pws.automation.napmobileweb.pages.purchasePath;

import com.netaporter.pws.automation.nap.pages.NAPPurchasePathPaymentPage;
import org.openqa.selenium.By;


public class MobileNAPPurchasePathPaymentPage extends NAPPurchasePathPaymentPage {

    @Override
    public String getCardTypeErrorMessage() {
        return webBot.findElement(By.id("selectCardErrorMobile")).getText();
    }

    @Override
    public String getCardNumberErrorMessage() {
        return webBot.findElement(By.id("cardErrorMobile")).getText();
    }

    @Override
    public String getCardNameErrorMessage() {
        return webBot.findElement(By.id("nameErrorMobile")).getText();
    }

    @Override
    public String getCardSecurityNumberErrorMessage() {
        return webBot.findElement(By.id("cvsErrorMobile")).getText();
    }

    @Override
    public String getCardExpiryDateErrorMessage() {
        return webBot.findElement(By.id("expiryErrorMobile")).getText();
    }

}
