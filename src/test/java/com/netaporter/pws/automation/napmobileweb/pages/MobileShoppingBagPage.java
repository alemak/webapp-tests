package com.netaporter.pws.automation.napmobileweb.pages;

import com.netaporter.pws.automation.shared.pojos.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("cucumber-glue")
public class MobileShoppingBagPage extends AbstractMobileNapPage {

    private static final String PAGE_NAME = "Shopping bag";
    private static final String PATH = "shoppingbag.nap";
    public static final By PROCEED_TO_PURCHASE_BUTTON = By.cssSelector("div.button a");

    public MobileShoppingBagPage() {
        super(PAGE_NAME, PATH);
    }

    public void clickProceedToPurchase() {
        webBot.findElement(PROCEED_TO_PURCHASE_BUTTON).click();
    }

    public Integer isProductInShoppingBagTimes(Product product) {
        List<WebElement> items = webBot.findElements(By.id("remove-item"));
        Integer count = 0;

        for (WebElement item : items) {
            WebElement element = item.findElement(By.id("remove-item"));

            String href = element.getAttribute("href");
            if (href.contains(product.getSku())) {
                count = count + 1;
            }
        }

        return count;
    }

}
