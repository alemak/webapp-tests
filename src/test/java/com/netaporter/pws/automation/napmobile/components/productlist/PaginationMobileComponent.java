package com.netaporter.pws.automation.napmobile.components.productlist;

import com.netaporter.pws.automation.napmobile.components.AbstractNapMobilePageComponent;
import com.netaporter.pws.automation.napmobile.util.Pagination;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class PaginationMobileComponent extends AbstractNapMobilePageComponent {

    /* Page Objects
    --------------------------------------*/
    By byPreviousPage = By.className("previous-page");

    By byNextPage = By.className("next-page");

    By byCurrentPage = By.className("num");

    By byTotalPages = By.className("of");

    /* Constructor
    --------------------------------------*/
    public PaginationMobileComponent() { }

    /* Getters
    --------------------------------------*/
    public By getByPreviousPage() {
        return byPreviousPage;
    }

    public WebElement getPreviousPage() {
        return webBot.getDriver().findElement(getByPreviousPage());
    }

    public By getByNextPage() {
        return byNextPage;
    }

    public WebElement getNextPage() {
        return webBot.getDriver().findElement(getByNextPage());
    }

    public By getByCurrentPage() {
        return byCurrentPage;
    }

    public WebElement getCurrentPage() {
        return webBot.getDriver().findElement(getByCurrentPage());
    }

    public By getByTotalPages() {
        return byTotalPages;
    }

    public WebElement getTotalPages() {
        return webBot.getDriver().findElement(getByTotalPages());
    }

    /* Helpers
    --------------------------------------*/
    public int currentPage() {
        return Integer.parseInt(getCurrentPage().getText());
    }

    public void directionClick(Pagination pagination) {
        WebElement e = null;
        switch(pagination) {
            case Next:
                e = getNextPage();
                e.click();
                break;
            case Previous:
                e = getPreviousPage();
                e.click();
                break;
        }
        WebDriverWait wait = new WebDriverWait(webBot.getDriver(), 30);
        wait.until(ExpectedConditions.stalenessOf(e));
    }

    public int numberOfProductsOnLastPage(int numProducts, int pagination) {
        return numProducts % pagination;
    }

    public int numberOfPages(int numProducts, int pagination) {
        int lastPage = numberOfProductsOnLastPage(numProducts, pagination);
        return ((numProducts - lastPage) / pagination) + 1;
    }

    public int totalPages() {
        return Integer.parseInt(getTotalPages().getText());
    }
}
