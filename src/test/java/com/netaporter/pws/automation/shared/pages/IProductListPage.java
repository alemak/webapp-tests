package com.netaporter.pws.automation.shared.pages;

import com.netaporter.pws.automation.nap.components.PageNavigationComponent;
import com.netaporter.pws.automation.nap.enums.ImageViewEnum;
import com.netaporter.pws.automation.nap.enums.ProductFilterEnum;
import com.netaporter.pws.automation.nap.util.SortOrder;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Common behaviour amongst all the product listings pages
 */
public interface IProductListPage {

    IProductDetailsPage gotoProductDetailsPage(String pid);

    void go();

    boolean isMultipleSize();

    boolean isMultiplePages();

    String getPageName();

    String getPath();

    void setPath(String originalPath);

    int getNumberOfResultsFromHeader();


    PageNavigationComponent getPageNavigationComponent();

    int getCurrentPageNumber();



    List<String> getAllProductsPids();

    Set<String> getAllProductDesigners();

    List<Integer> getAllProductPrices();

    List<Integer> getAllProductPercentages();

    Set<String> getDesignerIds();

    List<String> getFilters(ProductFilterEnum filterType, boolean b);


    String clickOnARandomInStockProduct();

    String clickOnARandomProduct();

    String clickOnARandomOutOfStockProduct();

    void mouseOverTheFirstImage();

    String getFirstProductImage();



    Map.Entry<Set<String>,Set<String>> selectAllItemsForFilterByGoingToUrlContainsAllFilterItemValues(ProductFilterEnum productFilter);

    Map.Entry<Set<String>,Set<String>> selectFilterItemRandomly(ProductFilterEnum filterType);

    boolean isFilterOpen(ProductFilterEnum color);



    void selectARandomSecondLevelCategory();

    Set<String> randomlySelectLevel3CategoryOfSelectedLevel2Category(int count);

    Set<String> getSelectedLevel3Categories();

    Set<String> randomlyUnselectLevel3CategoryOfSelectedLevel2Category(int count);

    String randomlySelectOneUnselectedLevel2Category();

    Set<String> selectAllLevel3CategoryOfSelectedLevel2Category();

    void selectFirstLevelCategoryFromLeftHandNav(String category);

    void selectLevel3Category(String level3Category);


    void selectImageViewLink(ImageViewEnum imageView) throws Exception;

    String getPageRefreshTimeStamp();

    String getPartialUpdateTimeStamp();



    void sortBy(SortOrder sortOrder);

    List<SortOrder> getSortingDropdownOptions();

    SortOrder randomlySelectSortingOrder(SortOrder aDefault);

    SortOrder getSelectedSortingDropdownOption();


    Map.Entry<Set<String>, Set<String>> randomlySelectOneItem(ProductFilterEnum productFilter);


    boolean isDropDownDisplayed();

    void changeSizeSchemeDropDownTo(String sizeScheme);

    WebElement getSizeSchemeDropdown();

    int getNumberOfSizesVisible();

    List<String> getAllProductsPriceAsStrings();

    void closeDontMissOutPopup();
}

