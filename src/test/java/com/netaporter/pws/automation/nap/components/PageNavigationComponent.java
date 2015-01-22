package com.netaporter.pws.automation.nap.components;

import com.google.common.collect.Lists;
import com.netaporter.pws.automation.nap.util.WebElementDataExtractingFunctions;
import com.netaporter.pws.automation.shared.utils.WaitUtil;
import com.netaporter.test.utils.pages.component.AbstractPageComponent;
import com.netaporter.test.utils.pages.driver.WaitTime;
import com.netaporter.test.utils.pages.driver.WebDriverUtil;
import com.netaporter.test.utils.pages.exceptions.PageElementNotFoundException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: x.qi@london.net-a-porter.com
 * Date: 11/06/2013
 */
@Component
@Scope("cucumber-glue")
public class PageNavigationComponent extends AbstractPageComponent{


    public static final String DOTDOTDOT = "…";

    public static final By VIEW_ALL_DROPDOWN_LOCATOR = By.name("npp");
    private static final By PREVIOUS_PAGE_ELEMENT = By.className("previous-page");
    private static final By NEXT_PAGE_ELEMENT = By.className("next-page");
    private static final By VIEW_ALL_ELEMENT = By.xpath("//*[@id='product-list-menu']/div[3]/div/form/select/option[2]");
    private static final By VIEW_60_ELEMENT = By.xpath("//*[@id='product-list-menu']/div[3]/div[1]/form/select/option[1]");
    private static final By CURRENT_PAGE_NUMBER_ELEMENT_KEY = By.className("pagination-page-current");
    private static final By PAGE_NUMBER_ELEMENT_LOCATOR = By.xpath(".//*[contains(@class, 'pagination-page-')]");
    private static final By ALL_PAGE_NUMBERS_ELEMENT_KEY = By.xpath(".//*[@id='product-list-menu']/div[3]/div/div");
    private static final By PAGINATION_LOCATOR = By.className("page-numbers");
    private static final By PAGINATION_PAGE_NUMBERS_LOCATOR = By.className("pagination-links");

    public PageNavigationComponent(){
        super();
    }
    public PageNavigationComponent(WebDriverUtil webBot) {
        super();
        this.webBot = webBot;
    }

    public static enum PageNumberOption { FIRST(1), SECOND(2), THIRD(3), THIRD_LAST(null), LAST(null);
        private Integer pageNumber;

        PageNumberOption(Integer pageNumber) {
            this.pageNumber = pageNumber;
        }

        public Integer getPageNumber() {
            return pageNumber;
        }
    }

    public static enum PageLinkOption {
         NEXT(NEXT_PAGE_ELEMENT), PREVIOUS(PREVIOUS_PAGE_ELEMENT), VIEW_ALL(VIEW_ALL_ELEMENT), VIEW_60(VIEW_60_ELEMENT);

        private By locator;

        PageLinkOption(By locator) {
            this.locator = locator;
        }

        public By getLocator() {
            return locator;
        }
    }

    public List<String> getAllPageNumbers() {
        String text = webBot.getElementText(ALL_PAGE_NUMBERS_ELEMENT_KEY);

        return Lists.newArrayList(text.split(" "));
    }

    // Note: lazy evaluation
    public List<String> getAllLinksForPageNumber() {

        List<WebElement> pageNumberElements = getPageNumberElements();

        return Lists.transform(pageNumberElements, WebElementDataExtractingFunctions.extractHrefAttribute);
    }

    private List<WebElement> getPageNumberElements() {
        List<WebElement> result = new ArrayList<WebElement>();

        List<WebElement> elements = webBot.findElements(PAGE_NUMBER_ELEMENT_LOCATOR, WaitTime.FOUR);

        int halfSize = elements.size() / 2;
        for(int i = 0; i < halfSize; i++)   {
             result.add(elements.get(i));
        }

        return result;
    }

    public int gotoPage(PageNumberOption pageNumberOption) {
        if(PageNumberOption.LAST.equals(pageNumberOption)) {
            return gotoLastPage();
        }
       if (PageNumberOption.THIRD_LAST.equals(pageNumberOption)) {
           gotoLastPage();

           return clickMatchedPageNumberLink(getPageCount() - 2);
       }

       return clickMatchedPageNumberLink(pageNumberOption.getPageNumber());
    }

    private int gotoLastPage() {
        List<WebElement> pageNumberElements = getPageNumberElements();

        WebElement webElement = pageNumberElements.get(pageNumberElements.size() - 1);
        Integer pageNumber = Integer.valueOf(webElement.getText());

        WaitUtil.clickWebElementAndWaitForSpinnerToAppearAndDisappear(webElement, webBot);
        return pageNumber;
    }


    private Integer clickMatchedPageNumberLink(Integer pageNumber) {

        for(WebElement webElement : getPageNumberElements()) {
            if (webElement.getText().equals(pageNumber.toString())) {
                WaitUtil.clickWebElementAndWaitForSpinnerToAppearAndDisappear(webElement, webBot);
                return pageNumber;
            }
        }

        throw new IllegalArgumentException("No link found for required pageNumber: " + pageNumber);
    }

    public void gotoAnyNonFirstPage() {
        List<WebElement> pageNumberElements = getPageNumberElements();

        Collections.shuffle(pageNumberElements);

        for(WebElement webElement : pageNumberElements) {
            String text = webElement.getText();

            if (!"1".equals(text) && !DOTDOTDOT.equals(text)) {
                WaitUtil.clickWebElementAndWaitForSpinnerToAppearAndDisappear(webElement, webBot);
                return;
            }
        }
    }

    public boolean isDisplayed(PageLinkOption pageLinkOption) {
        WebElement element = webBot.findElement(pageLinkOption.getLocator(), WaitTime.FOUR);

        return element.isDisplayed();
    }

    public void selectLink(PageLinkOption pageLinkOption) {
        for (int i = 0; i < 3; i++) {
            try {
                WebElement element = webBot.findElement(pageLinkOption.getLocator(), WaitTime.FOUR);
                WaitUtil.clickWebElementAndWaitForSpinnerToAppearAndDisappear(element, webBot);
                return;
            } catch (RuntimeException exception) {
                System.out.println("Failed to load page for " + pageLinkOption + " and will try to again");
            }
        }
    }

    public int getPageCount() {
        WebElement maxNumberOfPages;
        try {
            maxNumberOfPages = webBot.findElement(PAGINATION_PAGE_NUMBERS_LOCATOR, WaitTime.FOUR);
        } catch (PageElementNotFoundException e) {
            return 0;
        }
        return Integer.parseInt(maxNumberOfPages.getAttribute("data-lastpage"));
    }

    public int getCurrentPageNumber() {
        WebElement pageNumberElement;
        try {
            pageNumberElement = webBot.findElement(CURRENT_PAGE_NUMBER_ELEMENT_KEY, WaitTime.FOUR);
        } catch (PageElementNotFoundException e) {
            return 1;
        }
        if (pageNumberElement.isDisplayed())
            return Integer.parseInt(pageNumberElement.getText());
        return 1;
    }

    public boolean isPaginationDisplayed() {
        WebElement element;
        try {
            element = webBot.findElement(PAGINATION_LOCATOR, WaitTime.FOUR);
        } catch (PageElementNotFoundException e) {
            return false;
        }
        return element.isDisplayed();
    }
}
