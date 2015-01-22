package com.netaporter.pws.automation.napmobile.pages.shop;

import com.netaporter.pws.automation.napmobile.pages.AbstractNapMobilePage;
import com.netaporter.pws.automation.napmobile.util.*;
import com.netaporter.pws.automation.shared.utils.WaitUtil;
import com.netaporter.test.utils.pages.regionalisation.RegionaliseWithRegionName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Scope("cucumber-glue")
public class ShopMobilePage extends AbstractNapMobilePage {

    private static final String NAME = "Shop Mobile";
    private static final String PATH = "Shop";

    /* Page Objects
    --------------------------------------*/
    @FindBy(id = "main-heading")
    private WebElement titleMainHeading;

    @FindBy(className = "mobile-accordion-control")
    private List<WebElement> accordionControls;

    @FindBy(css = "div.accordion-content.mobile-shop-sub-list-container")
    private List<WebElement> accordionContents;

    @FindBy(className = "mobile-shop-sub-list")
    private List<WebElement> subLists;

    By bySubList = By.className("mobile-shop-sub-list");

    By bySubListItem = By.className("mobile-shop-sub-list-item");

    /* Constructor
    --------------------------------------*/
    public ShopMobilePage() {
        super(NAME, PATH);
        setRegionalisePathBehavior(new RegionaliseWithRegionName());
    }

    /* Getters
    --------------------------------------*/
    public WebElement getTitleMainHeading() {
        return titleMainHeading;
    }

    public List<WebElement> getAccordionControls() {
        return accordionControls;
    }

    public List<WebElement> getAccordionContents() {
        return accordionContents;
    }

    public List<WebElement> getSubLists() {
        return subLists;
    }

    public By getBySubList() {
        return bySubList;
    }

    public By getBySubListItem() {
        return bySubListItem;
    }

    public List<WebElement> getCategorySubListItems(Category category) {
        String locator = "#mobile-shop-list li.mobile-shop-list-item:nth-of-type("
                + Integer.toString(category.shopIndex()+1)
                + ") li.mobile-shop-sub-list-item";
        return webBot.getDriver().findElements(By.cssSelector(locator));
    }

    /* Helpers
    --------------------------------------*/
    public boolean at() {
        return getTitleMainHeading().getText().trim().equals("SHOP");
    }

    public void accordionControlClick(Category category) {
        WebElement accordionControl = getAccordionControls().get(category.shopIndex());
        WebDriverWait wait = new WebDriverWait(webBot.getDriver(), 30);
        wait.until(ExpectedConditions.elementToBeClickable(accordionControl));
        accordionControl.click();
       }

    public String accordionControlText(Category category) {
        WebElement accordionControl = getAccordionControls().get(category.shopIndex());
        WebDriverWait wait = new WebDriverWait(webBot.getDriver(), 30);
        wait.until(ExpectedConditions.visibilityOf(accordionControl));
        return accordionControl.getText().trim();
    }

    public boolean subListDisplayed(Category category) {
        WebElement accordionContent = getAccordionContents().get(category.shopIndex());
        return accordionContent.findElement(getBySubList()).isDisplayed();
    }

    public List<WebElement> subListItems(Category category) {
        WebDriverWait wait = new WebDriverWait(webBot.getDriver(), 30);
        return wait.until(ExpectedConditions.visibilityOfAllElements(getCategorySubListItems(category)));
    }

    public List<String> subListItemsText(Category category) {
        List<WebElement> subListItems = subListItems(category);
        List<String> subListItemsText = new ArrayList<String>();
        for (WebElement subListItem : subListItems) {
            String text = subListItem.findElement(By.tagName("a")).getText();
            subListItemsText.add(text);
        }
        return subListItemsText;
    }

    public List<? extends Enum> subListValues(Category category) {
        List<? extends Enum> subListValues = null;
        switch(category) {
            case Clothing:
                subListValues = Arrays.asList(Clothing.values());
                break;
            case Bags:
                subListValues = Arrays.asList(Bags.values());
                break;
            case Shoes:
                subListValues = Arrays.asList(Shoes.values());
                break;
            case Accessories:
                subListValues = Arrays.asList(Accessories.values());
                break;
            case Lingerie:
                subListValues = Arrays.asList(Lingerie.values());
                break;
            case Beauty:
                subListValues = Arrays.asList(Beauty.values());
                break;
            case Sport:
                subListValues = Arrays.asList(Sport.values());
        }
        return subListValues;
    }

    public List<String> subListValuesText(Category category) {
        List<? extends Enum> subListValues = subListValues(category);
        List<String> subListValuesText = new ArrayList<String>();
        for(Enum subListValue : subListValues) {
            subListValuesText.add(subListValue.toString());
        }
        return subListValuesText;
    }

    public Map<String, List<String>> subListLinksDisplayed(Category category) {
        Map<String, List<String>> results = new HashMap<String, List<String>>();
        results.put("subListValuesText", subListValuesText(category));
        results.put("subListItemsText", subListItemsText(category));
        return results;
    }

    public int subListItemShopIndex(Category category, String subList) {
        int subListItemShopIndex = -1;
        switch(category) {
            case Clothing:
                subListItemShopIndex = Clothing.valueOf(subList).shopIndex();
                break;
            case Bags:
                subListItemShopIndex = Bags.valueOf(subList).shopIndex();
                break;
            case Shoes:
                subListItemShopIndex = Shoes.valueOf(subList).shopIndex();
                break;
            case Accessories:
                subListItemShopIndex = Accessories.valueOf(subList).shopIndex();
                break;
            case Lingerie:
                subListItemShopIndex = Lingerie.valueOf(subList).shopIndex();
                break;
            case Beauty:
                subListItemShopIndex = Beauty.valueOf(subList).shopIndex();
                break;
            case Sport:
                subListItemShopIndex = Sport.valueOf(subList).shopIndex();
        }
        return subListItemShopIndex;
    }

    public void subListLinkClick(Category category, String subList) {
        List<WebElement> subListItems = subListItems(category);
        WebElement subListItem = subListItems.get(subListItemShopIndex(category, subList));
        WebElement subListItemLink = subListItem.findElement(By.tagName("a"));
        subListItemLink.click();
        WebDriverWait wait = new WebDriverWait(webBot.getDriver(), 30);
        wait.until(ExpectedConditions.stalenessOf(subListItemLink));
    }
}
