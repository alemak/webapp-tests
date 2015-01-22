package com.netaporter.pws.automation.shared.components;

import com.netaporter.test.utils.pages.component.AbstractPageComponent;
import com.netaporter.test.utils.pages.driver.WaitTime;
import com.netaporter.test.utils.pages.exceptions.PageElementNotFoundException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;

public abstract class ProductItemsListComponent extends AbstractPageComponent implements IProductListItems {

    private static final By NONRETURNABLE_MESSAGE_ELEMENT_LOCATOR = By.id("test");

    public List<Map> orderItemList;
    public List<String> tableHeadersList;

    public int getNumberOfItems() {
        return getOrderItems().size();
    }

    public List<Map> getOrderItems() {
        readItemHeaders();
        readOrderItemListFromPage();
        return orderItemList;
    }

    protected abstract void readOrderItemListFromPage();
    protected abstract void readItemHeaders();

    public boolean isNonrefundableWarningMessageDisplayedForPid(String pid) {
        List<WebElement> basketItemElements = webBot.findElements(By.className("basket-image"), WaitTime.FOUR);
        WebElement nonReturnableItemElement = null;
        for (WebElement basketItemElement : basketItemElements) {
            if (basketItemElement.findElement(By.xpath("img")).getAttribute("alt").contains(pid))
               nonReturnableItemElement = basketItemElement.findElement(By.xpath(".."));
        }
        if (nonReturnableItemElement==null)
            throw new IllegalStateException("Could not find non-returnable pid "+pid+" in the payment summary page");

        WebElement nonreturnableMessageElement;
        try {
            nonreturnableMessageElement = nonReturnableItemElement.findElement(By.className("non-returnable"));
        }
        catch (PageElementNotFoundException e) {
            return false;
        }
        return nonreturnableMessageElement.isDisplayed();
    }
}
