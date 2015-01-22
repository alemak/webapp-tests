package com.netaporter.pws.automation.nap.pages;

import com.netaporter.test.utils.pages.driver.WaitTime;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: c.choudhury@london.net-a-porter.com
 * Date: 08/02/2013
 * Time: 09:49
 * To change this template use File | Settings | File Templates.
 */

@Component
@Scope("cucumber-glue")
public class SearchPage extends AbstractNapPage {

    static final String pageName = "Search page";
    static final String path = "/";

    static final By SEARCH_BOX_ELEMENT = By.xpath(".//*[@id='search']");
    static final By SORT_BY_PRICE_DESCENDING_ELEMENT = By.xpath(".//*[@id='product-list-price-filter']/li[1]/a");

    protected By SEARCH_BOX_LOCATOR = By.id("search-field");
    protected By SEARCH_BUTTON_LOCATOR = By.xpath(".//*[@id='main-nav']/div/div/div[1]/span");
    protected By SEARCH_AUTO_SUGGEST = By.cssSelector(".search-result-list>a>li");
    protected By SEARCH_DESIGNER_FILTER_NAME = By.xpath(".//*[@id='designer-filter']/div[2]/ul/li/a[2]/span");
    protected By SEARCH_NO_RESULT_MESSAGE = By.className("no-results");
    protected By SEARCH_CLEAR_ICON_LOCATOR = By.cssSelector(".search-clear.open");



    public SearchPage() {
        super(pageName, path);
    }

    public void doSearch(String keyword) {
        WebElement searchElement = webBot.findElement(SEARCH_BOX_ELEMENT);
        searchElement.sendKeys(keyword);
        searchElement.submit();
    }

    public void sortByPriceDescending() {
        WebElement sortByPriceDescendingElement = webBot.findElement(SORT_BY_PRICE_DESCENDING_ELEMENT);
        sortByPriceDescendingElement.click();
    }

    public void responsiveSearch(String keyword) {
        webBot.click(SEARCH_BUTTON_LOCATOR);
        WebElement searchBoxElement = webBot.findElement(SEARCH_BOX_LOCATOR);
        webBot.typeAndWait(searchBoxElement, keyword, WaitTime.TWO);
    }

    public void clickAutoSuggestionResult() {
        webBot.assertElementsExist(SEARCH_AUTO_SUGGEST);
        webBot.findElements(SEARCH_AUTO_SUGGEST).get(0).click();
    }

    public String getDesignerName() {
        return webBot.findElement(SEARCH_DESIGNER_FILTER_NAME).getText();
    }

    public void verifyNoResultAutoSuggestMessage() {
        webBot.verifyText(SEARCH_NO_RESULT_MESSAGE,"Sorry, we couldn't find what you are looking for");
    }

}