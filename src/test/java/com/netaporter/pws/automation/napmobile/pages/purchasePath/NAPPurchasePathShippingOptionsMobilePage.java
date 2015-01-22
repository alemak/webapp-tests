package com.netaporter.pws.automation.napmobile.pages.purchasePath;

import com.netaporter.pws.automation.napmobile.pages.AbstractNapMobilePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by ocsiki on 07/08/2014.
 */
@Component
@Scope("cucumber-glue")
public class NAPPurchasePathShippingOptionsMobilePage extends AbstractNapMobilePage {


    private static final String PAGE_NAME = "Shipping Options Mobile Page";
    private static final String PAGE_URL = "/purchasepath.nap";

    private By CUTOFF_MESSAGE_LOCATOR = By.className("arrange_delivery");
    private By SHIPPING_METHODS_LOCATOR = By.className("field_row");
    private By PROCEED_TO_PURCHASE_BUTTON_LOCATOR = By.id("proceedToPurchase");

    public NAPPurchasePathShippingOptionsMobilePage() {
        super(PAGE_NAME, PAGE_URL);
    }


    public NAPPurchasePathShippingOptionsMobilePage(String pageName, String path) {
        super(pageName, path);
    }

    @Override
    public boolean at() {
        return false;
    }


    public void clickProceedToPurchase() {
        webBot.click(PROCEED_TO_PURCHASE_BUTTON_LOCATOR);
    }

    public String[] getAvailableShippingMethods() throws InterruptedException {
        Thread.sleep(4000);
        List<WebElement> shippingMethodsElements = getShippingMethodsElements();
        int numberOfShippingMethodsOnPage = shippingMethodsElements.size();
        String[] shippingOptions = new String[numberOfShippingMethodsOnPage];
        for (int i=0;i< numberOfShippingMethodsOnPage;i++) {
            String shippingOptionText = shippingMethodsElements.get(i).findElement(By.xpath("label")).getText().trim();
            int separatorIndex = shippingOptionText.indexOf(",");
            String shippingOptionName = null;
            if (separatorIndex != -1)
                shippingOptionName = shippingOptionText.substring(0, separatorIndex);
            else
                shippingOptionName = shippingOptionText;
            shippingOptions[i] = shippingOptionName;
        }
        return shippingOptions;
    }

    public List<WebElement> getDeliveryMessageElements() {
        return webBot.findElements(CUTOFF_MESSAGE_LOCATOR);
    }

    public void selectShippingMethod(String shippingMethod) {
        List<WebElement> shippingMethodsElements = getShippingMethodsElements();
        for (WebElement shippingMethodsElement : shippingMethodsElements) {
            if(shippingMethodsElement.findElement(By.xpath("label")).getText().trim().equals(shippingMethod))
                shippingMethodsElement.findElement(By.xpath("input")).click();
        }
    }

    private List<WebElement> getShippingMethodsElements() {
        return webBot.findElements(SHIPPING_METHODS_LOCATOR);
    }
}
