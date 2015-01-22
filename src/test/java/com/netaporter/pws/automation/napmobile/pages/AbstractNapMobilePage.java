package com.netaporter.pws.automation.napmobile.pages;

import com.netaporter.pws.automation.napmobile.components.footer.FooterMobileComponent;
import com.netaporter.pws.automation.napmobile.components.header.HeaderMobileComponent;
import com.netaporter.pws.automation.napmobile.components.productlist.*;
import com.netaporter.test.utils.pages.AbstractPage;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractNapMobilePage extends AbstractPage {

    private static final By SEARCH_LOCATOR = By.id("search");
    private static final By SEARCH_BUTTON_LOCATOR = By.className("search-button");
    private By MINI_SHOPPING_BAG_ICON_LOCATOR = By.cssSelector(".shopping-bag a");  //responsive element

    /* Components
            --------------------------------------*/
    @Autowired
    protected FilterMobileComponent filterMobileComponent;

    @Autowired
    protected FooterMobileComponent footerMobileComponent;

    @Autowired
    protected HeaderMobileComponent headerMobileComponent;

    @Autowired
    protected PaginationMobileComponent paginationMobileComponent;

    @Autowired
    protected ProductlistMobileComponent productlistMobileComponent;

    @Autowired
    protected SortbyMobileComponent sortByMobileComponent;

    @Autowired
    protected TitleMobileComponent titleMobileComponent;

    /* Constructors
    --------------------------------------*/
    public AbstractNapMobilePage(String pageName, String path) {
        super(pageName, path);
    }

    public AbstractNapMobilePage(String pageName, String path, List<String> params) {
        super(pageName, path, params);
    }

    /* Helpers
    --------------------------------------*/
    public abstract boolean at();

    public void clickShoppingBagIcon() {
        webBot.findElement(MINI_SHOPPING_BAG_ICON_LOCATOR).click();
    }

    public void search(String keyword) {
        webBot.type(SEARCH_LOCATOR, keyword);
        webBot.click(SEARCH_BUTTON_LOCATOR);
    }
}
