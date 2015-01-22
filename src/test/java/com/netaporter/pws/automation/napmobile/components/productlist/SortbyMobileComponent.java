package com.netaporter.pws.automation.napmobile.components.productlist;

import com.netaporter.pws.automation.napmobile.components.AbstractNapMobilePageComponent;
import com.netaporter.pws.automation.napmobile.util.SortBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Scope("cucumber-glue")
public class SortbyMobileComponent extends AbstractNapMobilePageComponent {

    /* Page Objects
    --------------------------------------*/
    By bySortBySelect = By.id("sort-by-select");

    /* Constructor
    --------------------------------------*/
    public SortbyMobileComponent() { }

    /* Getters
    --------------------------------------*/
    public By getBySortBySelect() {
        return bySortBySelect;
    }

    public Select getSortBySelect() {
        return new Select(webBot.getDriver().findElement(getBySortBySelect()));
    }

    /* Helpers
    --------------------------------------*/
    public void applySortBySelectOptionByVisibleText(SortBy sortBy) throws IllegalArgumentException {
        sortBySelectOptionByVisibleText(sortBy.toString());
        if (webBot.getDriver().getWindowHandles().size() > 1) {
            webBot.getDriver().switchTo().window("1");
        }
    }

    public String sortBySelectFirstSelectedOptionText() {
        return getSortBySelect().getFirstSelectedOption().getText().trim();
    }

    public String sortBySelectOptionsIndexText(int index) {
        return getSortBySelect().getOptions().get(index).getText().trim();
    }

    public void sortBySelectOptionByVisibleText(String value) {
        WebElement sortBySelect = webBot.getDriver().findElement(getBySortBySelect());
        webBot.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        getSortBySelect().selectByVisibleText(value);
        WebDriverWait wait = new WebDriverWait(webBot.getDriver(), 30);
        wait.until(ExpectedConditions.stalenessOf(sortBySelect));
        webBot.getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public int sortBySelectOptionsSize() {
        return getSortBySelect().getOptions().size();
    }
}
