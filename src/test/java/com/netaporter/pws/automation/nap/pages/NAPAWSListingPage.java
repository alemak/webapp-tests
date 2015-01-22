package com.netaporter.pws.automation.nap.pages;

import com.netaporter.pws.automation.shared.pages.IProductListPage;
import com.netaporter.test.utils.pages.driver.WaitTime;
import com.netaporter.test.utils.pages.driver.WebDriverUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.fail;

/**
 * User: a.mosincat
 * Date: 10/06/14
 */

public class NAPAWSListingPage extends AbstractNAPProductListPage implements IProductListPage {


    private static final By ELEMENT_LEVEL3_CATEGORIES_OF_SELECTED_LEVEL2_CATEGORY = By.xpath(".//*[@id='subnav']/li[contains(@class,'selected')]/ul/li/a");
    private static final By LEFT_HAND_NAV_LOCATOR = By.xpath(".//*[@id='subnav']/li/a");

    private static final String LEVEL3_CATEGORY_DATA_FILTER = "data-filter" ;
    public static final String STRING_LOCATOR_FOR_SIZE_FILTER = " ul li a";
    private static final By CATEGORIES_IN_LEFT_HAND_NAV_LOCATOR = By.cssSelector("#subnav li a");
    private static final String STRING_LOCATOR_FOR_DESIGNER_FILTER = "a/div";

    private final boolean isMultipleSize;


    public NAPAWSListingPage(String pageName, String path, boolean isMultipleSize) {
        super(pageName, path);
        this.isMultipleSize = isMultipleSize;
    }

    public NAPAWSListingPage(String pageName, String path) {
        this(pageName, path, false);
    }

    public NAPAWSListingPage(String pageName, String path, WebDriverUtil webBot) {
        this(pageName, path, webBot, false);
    }

    public NAPAWSListingPage(String pageName, String path, WebDriverUtil webBot, boolean isMultipleSize) {
        super(pageName, path, webBot);
        this.isMultipleSize = isMultipleSize;
    }


    @Override
    public boolean isMultipleSize() {
        return isMultipleSize;
    }

    @Override
    public boolean isPageRegionalised() {
        return false;
    }


    public void selectAllLevel3CategoriesOfSelectedLevel2Category(String level3) {
        List<WebElement> elements = webBot.findElements(ELEMENT_LEVEL3_CATEGORIES_OF_SELECTED_LEVEL2_CATEGORY);
           for (WebElement element : elements) {
              if (element.getAttribute(LEVEL3_CATEGORY_DATA_FILTER).equalsIgnoreCase(level3)) {
                element.click();
                break;
              }
           }
    }

    @Override
    protected String getStringLocatorForSizeFilter() {
        return STRING_LOCATOR_FOR_SIZE_FILTER;
    }

    @Override
    public void selectARandomSecondLevelCategory() {
        List<WebElement> tableRows = webBot.findElements(LEFT_HAND_NAV_LOCATOR, WaitTime.FOUR);
        Collections.shuffle(tableRows);
        WebElement selectedWebElement = tableRows.get(0);
        selectedWebElement.click();
    }

    @Override
    public void selectFirstLevelCategoryFromLeftHandNav(String category) {
        List<WebElement> tableRows = webBot.findElements(CATEGORIES_IN_LEFT_HAND_NAV_LOCATOR, WaitTime.FOUR);
        boolean isCategoryInLeftNav = false;
        for (WebElement tableRow : tableRows) {
            String selectedWebElement = tableRow.getText();
            if (selectedWebElement.equals(category)) {
                tableRow.click();
                isCategoryInLeftNav = true;
                break;
            }
        }
        if (!isCategoryInLeftNav)
            fail("There was no "+category+" in the left hand navcat or it could not be clicked on page "+webBot.getTitle()+". Failing test.");
//        assumeTrue("Test will be skipped because "+category+" is not displayed in the left hand nav on this page: "+webBot.getCurrentPage().getTitle(), isCategoryInLeftNav);
    }

    @Override
    protected String getStringLocatorForDesignerFilter() {
        return STRING_LOCATOR_FOR_DESIGNER_FILTER;
    }


}




