package com.netaporter.pws.automation.shared.pages.purchasePath;

import com.netaporter.pws.automation.shared.components.ProductItemsListComponent;
import com.netaporter.test.utils.pages.AbstractPage;
import com.netaporter.test.utils.pages.driver.WaitTime;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public abstract class PurchasePathConfirmationPage extends AbstractPage {

    private static final By SHIPPING_METHOD_LOCATOR = By.cssSelector("div#shipping_method p");

    @Autowired
    protected ProductItemsListComponent productItemsListComponent;

    public PurchasePathConfirmationPage(String pageName, String pagePath) {
        super(pageName, pagePath);
    }

    public ProductItemsListComponent getProductItemsList() {
        return productItemsListComponent;
    }

    public PurchasePathConfirmationPage() {
        super("Purchase path confirmation", "");
    }

    public String getShippingMethod() {
        String text = webBot.findElement(SHIPPING_METHOD_LOCATOR, WaitTime.FOUR).getText();
        return text.split("\\(")[0].trim();
    }

    public abstract Integer getOrderNumber();

    public List<String> getPidsFromConfirmationPage() {
        List<String> pidsInConfirmationPage = new ArrayList<String>();
        List<WebElement> elements = webBot.findElements(By.xpath(".//td[@class='basket-image']/img"), WaitTime.FOUR);
        for (WebElement element : elements) {
            pidsInConfirmationPage.add(element.getAttribute("alt"));
        }
        if (pidsInConfirmationPage.size()!=0)
            return pidsInConfirmationPage;
        throw new IllegalStateException("Could not get pids from confirmation page");
    }

    public boolean isNonReturnableMessageDisplayedForPid(String pidFromSku) {
        return productItemsListComponent.isNonrefundableWarningMessageDisplayedForPid(pidFromSku);
    }
}