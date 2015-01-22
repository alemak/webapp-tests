package com.netaporter.pws.automation.shared.pages.purchasePath;


import org.openqa.selenium.By;

public class PurchasePathShippingAddressPage extends PurchasePathAddressPage {

    public PurchasePathShippingAddressPage() {
        super("Purchase path shipping address", "");
    }

    public void setUseShippingAddressAsBillingAddress() {
        webBot.findElement(By.id("billing_same")).click();
    }

    public void setEnterSeperateBillingAddress() {
        webBot.findElement(By.id("billing_diff")).click();
    }

}