package com.netaporter.pws.automation.napmobile.components.productlist;

import com.google.common.collect.Ordering;
import com.netaporter.pws.automation.napmobile.components.AbstractNapMobilePageComponent;
import com.netaporter.pws.automation.napmobile.util.SortOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Scope("cucumber-glue")
public class ProductlistMobileComponent extends AbstractNapMobilePageComponent {

    /* Page Objects
    --------------------------------------*/
    By byProductListPrices = By.cssSelector("#list .product-row .description .price-wrapper");

    By byProductList = By.id("list");

    By byProductListDesigners = By.cssSelector("#list .product-row .description a span.designer");

    /* Constructor
    --------------------------------------*/
    public ProductlistMobileComponent() { }

    /* Getters
    --------------------------------------*/
    public By getByProductListPrices() {
        return byProductListPrices;
    }

    public By getByProductList() {
        return byProductList;
    }

    public WebElement getProductList() {
        return webBot.getDriver().findElement(getByProductList());
    }

    public By getByProductListDesigners() {
        return byProductListDesigners;
    }

    public List<WebElement> getProductListDesigners() {
        return webBot.getDriver().findElements(getByProductListDesigners());
    }

    /* Helpers
    --------------------------------------*/
    public List<Integer> productListPrices() throws Exception {
        List<Integer> prices = new ArrayList<Integer>();
        List<WebElement> productListPrices = webBot.getDriver().findElements(getByProductListPrices());
        String price;
        Pattern regular = Pattern.compile("\\p{Sc}(\\d+,*\\d*)");
        Pattern sale = Pattern.compile("\\w+ \\p{Sc}(\\d+,*\\d*) \\w+ \\p{Sc}(\\d+,*\\d*) (\\d+)% \\w+");
        for (WebElement productListPrice : productListPrices) {
            Matcher saleMatcher = sale.matcher(productListPrice.getText());
            if (saleMatcher.matches()) {
                price = saleMatcher.group(2).replaceAll(",", "");
                prices.add(Integer.parseInt(price));
            } else {
                Matcher regularMatcher = regular.matcher(productListPrice.getText());
                if (regularMatcher.matches()) {
                    price = regularMatcher.group(1).replaceAll(",", "");
                    prices.add(Integer.parseInt(price));
                } else {
                    throw new Exception("REGEX PATTERN REQUIRED: " + productListPrice.getText());
                }
            }
        }
        return prices;
    }

    public boolean productListSorted(SortOrder sortOrder) throws Exception {
        WebDriverWait wait = new WebDriverWait(webBot.getDriver(), 30);
        wait.until(ExpectedConditions.visibilityOf(getProductList()));
        boolean result = false;
        List<Integer> prices = productListPrices();
        switch(sortOrder) {
            case Ascending:
                result = Ordering.natural().isOrdered(prices);
                break;
            case Descending:
                result = Ordering.natural().reverse().isOrdered(prices);
                break;
        }
        return result;
    }

    public Collection<String> designersDisplayedInProductListing() {
        Collection<String> displayedDesigners = new HashSet<String>();
        List<WebElement> productListDesigners = getProductListDesigners();
        for (WebElement designer : productListDesigners) {
            displayedDesigners.add(designer.getText());
        }
        return displayedDesigners;
    }

    public boolean onlySelectedDesignersAreDisplayed(Collection<String> selectedDesigners) {
        Collection<String> displayedDesigners = designersDisplayedInProductListing();
        return selectedDesigners.equals(displayedDesigners);
    }
}
