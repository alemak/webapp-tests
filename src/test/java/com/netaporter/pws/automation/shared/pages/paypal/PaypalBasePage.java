package com.netaporter.pws.automation.shared.pages.paypal;

import com.netaporter.test.utils.pages.AbstractPage;
import com.netaporter.test.utils.pages.driver.WaitTime;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("cucumber-glue")
public class PaypalBasePage extends AbstractPage {

    private static final String PAGE_NAME = "PayPal Payments Page";
    private static final String PATH = "";

    private By CANCEL_PAYMENT_LINK_LOCATOR = By.name("cancel_return");

    public PaypalBasePage() {
        this(PAGE_NAME, PATH);
    }

    public PaypalBasePage(String pageName, String path) {
        super(pageName, path);
    }

    public void setBaseUrl(String url){
        webBot.setBaseUrl(url);
    }

    public String goToMyPage(String pageUrl){
        PaypalBasePage pp = new PaypalBasePage();
        String baseUrl = webBot.getBaseUrl();
        webBot.setBaseUrl(pageUrl);
        webBot.goToPage(pp);
        return baseUrl;
    }

    // TODO - waiting on paypal in test env

    public List<com.netaporter.test.client.product.pojos.CestaItem> getOrderItems() {
        return null;
    }

    protected void checkPositionOfElementAndClick(By locator) throws InterruptedException {
        WebElement element = webBot.findElement(locator, WaitTime.EIGHT);
        Point elementLocation = element.getLocation();
        if (elementLocation.getX() > 0 && elementLocation.getY() > 0) {
            element.click();
        }
        else {
            Thread.sleep(2000);
            webBot.click(locator);
        }
    }

    public void clickCancelPaymentButton() throws InterruptedException {
        checkPositionOfElementAndClick(CANCEL_PAYMENT_LINK_LOCATOR);

    }
}
