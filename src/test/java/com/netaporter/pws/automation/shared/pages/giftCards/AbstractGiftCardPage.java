package com.netaporter.pws.automation.shared.pages.giftCards;

import com.netaporter.test.utils.pages.AbstractPage;
import com.netaporter.test.utils.pages.regionalisation.RegionaliseWithRegionName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public abstract class AbstractGiftCardPage extends AbstractPage {

    public AbstractGiftCardPage(String pageName, String path) {
        super(pageName, path);
        setRegionalisePathBehavior(new RegionaliseWithRegionName());
    }

    private Select amountsSelect() {
        return new Select(webBot.findElement(By.id("sku")));
    }

    public String setAmount(int amount) {
        StringBuilder builder = new StringBuilder();
        // find the currency symbol from the first option
        Character currencySymbol = amountsSelect().getOptions().get(1).getText().trim().charAt(0);
        String setAmountString = builder.append(currencySymbol).append(amount).toString();

        for(WebElement option : amountsSelect().getOptions()) {
            if(setAmountString.equals(option.getText().trim())) {
                option.click();
                return option.getAttribute("value");
            }
        }
       return null;
    }

    public void clickBuyButton() {
        // try to cope with the different html for all cases in one place

        List<WebElement> pageButtons = webBot.findElement(By.id("content")).findElements(By.name("shoppingBagAdd"));
        if(pageButtons.size() == 1) {
            pageButtons.get(0).click();
            return;
        }

        pageButtons = webBot.findElements(By.cssSelector("input.primary-button"));
        for(WebElement button : pageButtons)
            if("Add to Shopping Bag".equals(button.getAttribute("value"))) {
                button.click();
                return;
            }
    }

}
