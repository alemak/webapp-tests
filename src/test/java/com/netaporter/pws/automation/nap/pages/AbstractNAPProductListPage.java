package com.netaporter.pws.automation.nap.pages;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.netaporter.pws.automation.nap.components.PageNavigationComponent;
import com.netaporter.pws.automation.nap.enums.ImageViewEnum;
import com.netaporter.pws.automation.nap.enums.ProductFilterEnum;
import com.netaporter.pws.automation.nap.util.SortOrder;
import com.netaporter.pws.automation.nap.util.WebElementDataExtractingFunctions;
import com.netaporter.pws.automation.shared.pages.IProductDetailsPage;
import com.netaporter.pws.automation.shared.pages.IProductListPage;
import com.netaporter.pws.automation.shared.utils.WaitUtil;
import com.netaporter.test.utils.pages.driver.WaitTime;
import com.netaporter.test.utils.pages.driver.WebDriverUtil;
import com.netaporter.test.utils.pages.exceptions.PageElementNotFoundException;
import com.netaporter.test.utils.pages.regionalisation.RegionaliseWithRegionName;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.fail;

/**
 * Created by ocsiki on 29/09/2014.
 */
public abstract class AbstractNAPProductListPage extends AbstractNapPage implements IProductListPage {

    public static final String LOCATOR_SIZE_FILTER = " div ul li span a ";
    private static final String LOCATOR_DESIGNER_FILTER = "a[2]";

    @Autowired
    protected PageNavigationComponent pageNavigationComponent;

    @Autowired
    protected NAPProductDetailsPage napProductDetailsPage;

    private By PRODUCTS = By.cssSelector("#product-list .product-image a");
    private By PRICE_ELEMENT_LOCATOR = By.className("price");
    private By ELEMENT_LEVEL3_CATEGORY_OF_SELECTED_LEVEL2_CATEGORY = By.xpath("//*[@id='main-nav']/li[@class='has-children selected']/ul/li/a");
    private By ELEMENT_SELECTED_LEVEL_3_CATEGORY = By.xpath("//*[@id='main-nav']/li[@class='has-children selected']/ul[1]/li[@class='selected']/a");
    private By NUMBER_OF_RESULTS_FROM_HEADER_LOCATOR = By.className("total-number-of-products");
    private By SIZE_SCHEME_DROPDOWN_LOCATOR = By.id("select_shoe_conversion");
    private By SIZE_SCHEME_LOCATOR = By.id("ul_shoes_sizes");
    private By CATEGORIES_IN_LEFT_HAND_NAV_LOCATOR = By.cssSelector("#main-nav li a");
    private By ELEMENT_LEVEL2_CATEGORY = By.xpath("//*[@id='main-nav']/li[@class='has-children']/a");
    private By PAGE_REFRESH_TIME_STAMP_LOCATOR = By.className("pageRefreshTimeStamp");
    private By PARTIAL_UPDATE_TIME_STAMP_LOCATOR = By.className("partialUpdateTimeStamp");
    private By LEFT_HAND_NAV_LOCATOR = By.cssSelector("#main-nav li a");
    private By SORTING_DROPDOWN_LOCATOR = By.xpath(".//*[@id='product-list-price-filter']/select/option");
    private By SELECTED_SORTING_OPTION_LOCATOR = By.xpath(".//*[@id='product-list-price-filter']/select/option[@selected='selected']");
    private By PRODUCT_DESIGNERS = By.xpath(".//*[@id='product-list']/div/div/a/span");


    public static By OUTFIT_VIEW_ELEMENT = By.cssSelector(".secondary-button.product-image-view.product.selected");
    public static By PRODUCT_VIEW_ELEMENT = By.cssSelector(".secondary-button.product-image-view.outfit.selected");


    private static final String DESIGNER_ID_ATTRIBUTE = "data-designer-id";
    private static final String LEVEL3_CATEGORY_DATA_FILTER = "data-filter";


    public AbstractNAPProductListPage(String pageName, String path) {
        super(pageName, path);
    }

    public AbstractNAPProductListPage(String pageName, String path, WebDriverUtil webBot) {
        super(pageName, path);
        setRegionalisePathBehavior(new RegionaliseWithRegionName());
        this.pageNavigationComponent = new PageNavigationComponent(webBot);
    }


    protected Map<String, String> createPidToPriceMap(List<String> allPids, List<String> allPrices) {
        Map<String, String> pidToPriceMap = new HashMap<String, String>();
        for (int i = 0; i < allPids.size(); i++) {
            pidToPriceMap.put(allPids.get(i), allPrices.get(i));
        }
        return pidToPriceMap;
    }


    protected List<WebElement> getAllProductElementsOnPage() {
        return webBot.findElements(PRODUCTS, WaitTime.FOUR);
    }

    public List<String> getAllProductsPriceAsStrings() {
        List<WebElement> prices = webBot.findElements(PRICE_ELEMENT_LOCATOR);

        return Lists.transform(prices, WebElementDataExtractingFunctions.extractText);
    }

    public List<String> getAllProductCurrencies() {
        List<WebElement> productDescriptionElements = webBot.findElements(PRICE_ELEMENT_LOCATOR);
        return Lists.newArrayList(Lists.transform(productDescriptionElements, WebElementDataExtractingFunctions.extractCurrencyFromText));
    }

    public List<Integer> getAllProductPrices() {
        List<WebElement> prices = webBot.findElements(PRICE_ELEMENT_LOCATOR, WaitTime.FOUR);
        return Lists.transform(prices, WebElementDataExtractingFunctions.extractPriceFromText);
    }

    public List<Integer> getAllProductPercentages() {
        List<WebElement> elements = webBot.findElements(PRICE_ELEMENT_LOCATOR, WaitTime.FOUR);
        return Lists.transform(elements, WebElementDataExtractingFunctions.extractPercentageFromText);
    }


    public Set<String> selectAllLevel3CategoryOfSelectedLevel2Category() {
        List<WebElement> elements = webBot.findElements(ELEMENT_LEVEL3_CATEGORY_OF_SELECTED_LEVEL2_CATEGORY);

        Set<String> result = new HashSet<String>();

        for (WebElement element : elements) {
            result.add(element.getAttribute(LEVEL3_CATEGORY_DATA_FILTER));
            WaitUtil.clickWebElementAndWaitForSpinnerToAppearAndDisappear(element, webBot);
        }
        return result;
    }

    public Set<String> randomlySelectLevel3CategoryOfSelectedLevel2Category(int number) {
        List<WebElement> elements = webBot.findElements(ELEMENT_LEVEL3_CATEGORY_OF_SELECTED_LEVEL2_CATEGORY, WaitTime.FOUR);

        return randomlyToggleLevel3Categories(number, elements);
    }

    private Set<String> randomlyToggleLevel3Categories(int number, List<WebElement> elements) {
        Collections.shuffle(elements);
        Set<String> result = new HashSet<String>();

        int selectedNumber = 0;
        for (WebElement element : elements) {
            result.add(element.getAttribute(LEVEL3_CATEGORY_DATA_FILTER));

            WaitUtil.clickWebElementAndWaitForSpinnerToAppearAndDisappear(element, webBot);

            if (++selectedNumber >= number ) {
                return result;
            }
        }

        throw new IllegalArgumentException("Can't toggle " + number + " level 3 category");
    }


    public Set<String> getSelectedLevel3Categories() {
        Set<String> result = new HashSet<String>();

        try {
            webBot.findElement(ELEMENT_SELECTED_LEVEL_3_CATEGORY, WaitTime.FOUR);
        }
        catch (PageElementNotFoundException e ){
            System.out.println("Selected level 2 category does not have any (selected) level 3 categories or locator is broken");
            return result;
        }

        List<WebElement> selectedNavLevel3 = webBot.findElements(ELEMENT_SELECTED_LEVEL_3_CATEGORY);

        for (WebElement navLevel3 : selectedNavLevel3) {
            String value = navLevel3.getAttribute(LEVEL3_CATEGORY_DATA_FILTER);
            result.add(value);

        }

        return result;
    }


    public Set<String> randomlyUnselectLevel3CategoryOfSelectedLevel2Category(int count) {
        List<WebElement> elements = webBot.findElements(ELEMENT_SELECTED_LEVEL_3_CATEGORY);

        return randomlyToggleLevel3Categories(count, elements);
    }


    public void selectLevel3Category(String level3Category) {
        List<WebElement> elements = webBot.findElements(ELEMENT_LEVEL3_CATEGORY_OF_SELECTED_LEVEL2_CATEGORY);

        for (WebElement element : elements) {
            if (level3Category.equalsIgnoreCase(element.getText())) {
                WaitUtil.clickWebElementAndWaitForSpinnerToAppearAndDisappear(element, webBot);
                return;
            }
        }
        fail("Could not find level 3 category: "+level3Category+" to click");
    }


    public int getNumberOfResultsFromHeader() {
        WebElement numberOfResults = webBot.findElement(NUMBER_OF_RESULTS_FROM_HEADER_LOCATOR, WaitTime.FOUR);
        String numberOfResultsTextFormatted = numberOfResults.getText().replaceAll(",.","");
        Scanner in = new Scanner (numberOfResultsTextFormatted).useDelimiter("[^0-9]+");
        return in.nextInt();

    }


    public Map.Entry<Set<String>, Set<String>> selectFilterItemRandomly(ProductFilterEnum filterType) {
        //expandFilterIfClosed(filterType);

        Map.Entry<Set<String>, Set<String>> setSetEntry = randomlySelectOneItem(filterType);

        WaitUtil.waitForSpinnerToAppearAndDisappear(webBot);

        return setSetEntry;
    }


    public Map.Entry<Set<String>, Set<String>> randomlySelectOneItem(ProductFilterEnum filterType) {
        WebElement selectedWebElement = randomlySelectOneFilterItemWebElement(filterType);

     webBot.executeScript("arguments[0].scrollIntoView(true);", selectedWebElement);
        AbstractMap.SimpleEntry<Set<String>, Set<String>> result = createFilterEntry(filterType, selectedWebElement);

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //workaround for the boutiques pages, otherwise webbot does not click on filter facet
        WebElement filterClicker = selectedWebElement.findElement(By.xpath(".//span"));

        if (ProductFilterEnum.DESIGNER.equals(filterType)) {
            WaitUtil.clickWebElementAndWaitForSpinnerToAppearAndDisappear(filterClicker, webBot);
            WaitUtil.clickWebElementAndWaitForSpinnerToAppearAndDisappear(filterClicker, webBot);
        }
       else {
            WaitUtil.clickWebElementAndWaitForSpinnerToAppearAndDisappear(filterClicker, webBot);
        }
        return result;
    }


    private WebElement randomlySelectOneFilterItemWebElement(final ProductFilterEnum filterType) {
        List<WebElement> tableRows;

        if (ProductFilterEnum.SIZE.equals(filterType)){
            webBot.waitUntil(new Predicate<WebDriver>() {
                @Override
                public boolean apply(@Nullable WebDriver webDriver) {
                    WebElement filterFacet = webBot.findElement(By.cssSelector("#" + filterType.getDomIdOrClass() + " " + getStringLocatorForSizeFilter() + " span"));
                    return filterFacet.isDisplayed();
                }
            }, 3);
            tableRows = webBot.findElements(By.cssSelector("#" + filterType.getDomIdOrClass() + getStringLocatorForSizeFilter()));
        }
        else {
            tableRows = webBot.findElements(By.cssSelector("#" + filterType.getDomIdOrClass() + " li"));
        }

        if (tableRows.isEmpty()) {
            throw new IllegalStateException("Empty table rows in selectRandomLiHTMLTag");
        }

        for (WebElement tableRow : tableRows) {
            if (tableRow.getText().equalsIgnoreCase("All")) {
                tableRows.remove(tableRow);
                break;
            }
        }

        Collections.shuffle(tableRows);
        return tableRows.get(0);
    }

    protected String getStringLocatorForSizeFilter() {
        return LOCATOR_SIZE_FILTER;
    }


    private AbstractMap.SimpleEntry<Set<String>, Set<String>> createFilterEntry(ProductFilterEnum filterType, WebElement selectedWebElement) {
        if (ProductFilterEnum.DESIGNER.equals(filterType)) {
            final WebElement element = selectedWebElement.findElement(By.xpath(getStringLocatorForDesignerFilter() + "/span"));
            return new AbstractMap.SimpleEntry<Set<String>, Set<String>>(Sets.newHashSet(selectedWebElement.getAttribute(DESIGNER_ID_ATTRIBUTE)), Sets.newHashSet(element.getText().toUpperCase()));
        } else {
            return new AbstractMap.SimpleEntry<Set<String>, Set<String>>(Sets.newHashSet(selectedWebElement.getText()), Sets.newHashSet(selectedWebElement.getText()));
        }
    }

    protected String getStringLocatorForDesignerFilter() {
        return LOCATOR_DESIGNER_FILTER;
    }

    Function<WebElement, String> extractDesignerIdFunction = new Function<WebElement, String>() {
        public String apply(WebElement productWebElement) {
            return productWebElement.getAttribute(DESIGNER_ID_ATTRIBUTE);
        }
    };

    private void expandFilterIfClosed(ProductFilterEnum filterType) {
        WebElement element = webBot.findElement(By.id(filterType.getDomIdOrClass()));
        if (element.getAttribute("class").contains("closed")) {
            webBot.click(By.xpath(".//*[@id='" + filterType.getDomIdOrClass() + "']/span/a"));
        }
    }


    public List<String> getFilters(ProductFilterEnum ProductFilterEnum, boolean isSelected) {
        //expandFilterIfClosed(ProductFilterEnum);

        if (ProductFilterEnum.equals(ProductFilterEnum.DESIGNER)) {
            List<WebElement> elements = webBot.findElements(By.xpath(ProductFilterEnum.getDomXPathForId(isSelected)), WaitTime.FOUR);

            return Lists.transform(elements, extractDesignerIdFunction);
        } else {
            List<WebElement> elements = webBot.findElements(By.xpath(ProductFilterEnum.getDomXPathForName(isSelected)), WaitTime.FOUR);

            return Lists.transform(elements, WebElementDataExtractingFunctions.extractText);
        }
    }


    public Map.Entry<Set<String>, Set<String>> selectAllItemsForFilterByGoingToUrlContainsAllFilterItemValues(ProductFilterEnum ProductFilterEnum) {
        //expandFilterIfClosed(ProductFilterEnum);

        Map.Entry<Set<String>, Set<String>> entry = getAllFilterItems(ProductFilterEnum);

        String parameterString = createParameterString(entry.getKey());

        goToUrlContainsAllFilterItems(ProductFilterEnum.getUrlPrefix(), parameterString);

        WaitUtil.waitForSpinnerToAppearAndDisappear(webBot);

        return entry;
    }


    private void goToUrlContainsAllFilterItems(String parameterPrefix, String parameters) {
        final String newPath = createUrl(getPath(), parameterPrefix, parameters);

        setPath(newPath);
        go();
    }


    private String createUrl(String basePath, String parameterPrefix, String parameters) {
        return basePath + "?" + parameterPrefix + parameters + "&excludeFilters=false";
    }


    private Map.Entry<Set<String>, Set<String>> getAllFilterItems(ProductFilterEnum ProductFilterEnum) {
        List<WebElement> tableRows = webBot.findElements(By.xpath(ProductFilterEnum.getDomXPathForId(false)));

        if (tableRows.isEmpty()) {
            throw new IllegalStateException("Empty table rows in selectRandomLiHTMLTag");
        }

        Set<String> expectedAllFilters = new HashSet<String>();

        if (ProductFilterEnum.DESIGNER.equals(ProductFilterEnum)) {
            expectedAllFilters.addAll(Lists.transform(tableRows, extractDesignerIdFunction));
        } else {
            expectedAllFilters.addAll(Lists.transform(tableRows, WebElementDataExtractingFunctions.extractText));
        }

        return new AbstractMap.SimpleEntry<Set<String>, Set<String>>(expectedAllFilters, expectedAllFilters);
    }

    // no more than
    public int selectFiltersOfCeilPageNumberNew(ProductFilterEnum ProductFilterEnum, int totalPageNumber) {
        expandFilterIfClosed(ProductFilterEnum);

        String originalBasePath = getPath();

        List<String> filters = createShuffledAllFilterKeys(ProductFilterEnum);

        TreeSet<String> newFilters = new TreeSet<String>();

        do {
            attemptToGoToListingWithRequiredTotalNumberOfPagesByAddingProductFilterEnumValueOneByOne(ProductFilterEnum, totalPageNumber, originalBasePath, filters, newFilters);
        }
        while (getPageNavigationComponent().getPageCount() != totalPageNumber);

        return totalPageNumber;
    }


    // No less than
    public int selectFiltersOfFloorPageNumberNew(ProductFilterEnum ProductFilterEnum, int totalPageNumber) {
        expandFilterIfClosed(ProductFilterEnum);

        String originalBasePath = getPath();

        List<String> filters = createShuffledAllFilterKeys(ProductFilterEnum);

        TreeSet<String> newFilters = new TreeSet<String>();

        attemptToGoToListingWithRequiredTotalNumberOfPagesByAddingProductFilterEnumValueOneByOne(ProductFilterEnum, totalPageNumber, originalBasePath, filters, newFilters);

        // can't get to the total number of pages by using applying the filter items, so just goto the page without any filters
        if (getPageNavigationComponent().getPageCount() < totalPageNumber) {
            setPath(originalBasePath);
            go();
        }

        return getPageNavigationComponent().getPageCount();
    }


    private List<String> createShuffledAllFilterKeys(ProductFilterEnum ProductFilterEnum) {
        Set<String> filterValues = getAllFilterItems(ProductFilterEnum).getKey();

        List<String> filters = Lists.newArrayList(filterValues);

        Collections.shuffle(filters);
        return filters;
    }

    private void goToPageForGivenFilterValues(ProductFilterEnum ProductFilterEnum, String basePath, Set<String> filterValues) {
        String parameterString = createParameterString(filterValues);

        String newPath = createUrl(basePath, ProductFilterEnum.getUrlPrefix(), parameterString);

        setPath(newPath);
        go();
    }

    private String attemptToGoToListingWithRequiredTotalNumberOfPagesByAddingProductFilterEnumValueOneByOne(ProductFilterEnum ProductFilterEnum, int totalPageNumber, String originalBasePath, List<String> filters, TreeSet<String> newFilters) {
        String lastFilter = null;
        for(int i =0; i < filters.size(); i++) {
            Collections.shuffle(filters);
            String filterValue = filters.get(i);
            newFilters.add(filterValue);

            goToPageForGivenFilterValues(ProductFilterEnum, originalBasePath, newFilters);

            lastFilter = filterValue;

            if (getPageNavigationComponent().getPageCount() == totalPageNumber) {
                break;
            }
            else if (getPageNavigationComponent().getPageCount() > totalPageNumber)
                newFilters.remove(newFilters.last());
        }
        return lastFilter;
    }


    private String createParameterString(Set<String> ids) {
        StringBuilder filterStringBuilder = new StringBuilder();
        for (String expectedAllFilter : ids) {
            filterStringBuilder.append(expectedAllFilter.replace(" ", "_")).append(";");
        }

        return filterStringBuilder.toString();
    }


    public String getFirstProductImage() {
        return getFirstProductElement().findElement(By.xpath("a/img")).getAttribute("data-src");
    }


    public WebElement getFirstProductElement() {
        return webBot.findElements(ImageViewEnum.PRODUCT_IMAGE_CSS_CLASS_LOCATOR).get(0);
    }


    public void selectImageViewLink(ImageViewEnum imageView) throws Exception {
        WebElement element = webBot.findElement(imageView.getLocator());
        WaitUtil.clickWebElementAndWaitForSpinnerToAppearAndDisappear(element, webBot);
    }


    public void changeSizeSchemeDropDownTo(String sizeScheme) {
        Select sizeDropDownSelect = new Select(webBot.findElement(SIZE_SCHEME_DROPDOWN_LOCATOR, WaitTime.FIVE));
        if(sizeScheme.equals("Italy/Europe") ) {
            sizeDropDownSelect.selectByIndex(1);
            sizeDropDownSelect.selectByVisibleText(sizeScheme);
        } else {
            sizeDropDownSelect.selectByVisibleText(sizeScheme);
        }
    }


    public WebElement getSizeSchemeDropdown() {
        WebElement sizeSchemeDropDown;
        try {
            sizeSchemeDropDown = webBot.findElement(SIZE_SCHEME_DROPDOWN_LOCATOR, WaitTime.FOUR);
        }
        catch (PageElementNotFoundException e) {
            return null;
        }
        return sizeSchemeDropDown;
    }

    public int getCurrentPageNumber() {
        return getPageNavigationComponent().getCurrentPageNumber();
    }


    public int getNumberOfSizesVisible() {
        List<WebElement> sizesAvailable = webBot.findElement(SIZE_SCHEME_LOCATOR).findElements(By.tagName("li"));
        return (sizesAvailable.size());
    }


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

    public boolean isFilterOpen(ProductFilterEnum filterType) {
        WebElement element = webBot.findElement(By.id(filterType.getDomIdOrClass()));

        if (element.getAttribute("class").contains("closed")) {
            return false;
        }
        return true;
    }

    //Can be remove once out of stock product can be query from PS API
    public String clickOnARandomOutOfStockProduct() {
        List<WebElement> allProductDescriptionElements = getAllProductDescriptionElements();
        Collections.shuffle(allProductDescriptionElements);
        String pid = null;
        for (WebElement allProductDescriptionElement : allProductDescriptionElements) {
            WebElement slug = null;
            try {
                slug = allProductDescriptionElement.findElement(By.className("slug"));

            } catch (org.openqa.selenium.NoSuchElementException e) {
                continue;
            }
            if (slug.getText().equalsIgnoreCase("sold out")) {
                pid = extractPidAndClick(allProductDescriptionElement);
                break;
            }
        }
        if (pid==null)
            throw new NullPointerException("Could not extract pid from href. Pid is null");
        return pid;

    }


    private List<WebElement> getAllProductDescriptionElements() {
        return webBot.findElements(By.className("description"));
    }


    private String extractPidAndClick(WebElement allProductDescriptionElement) {
        String productDetailsHref = allProductDescriptionElement.findElement(By.xpath("a")).getAttribute("href");
        allProductDescriptionElement.findElement(By.xpath("a")).click();
        return getPidFromHref(productDetailsHref);
    }


    private String getPidFromHref(String productDetailsHref) {
        Matcher matcher = Pattern.compile("\\d+").matcher(productDetailsHref);
        matcher.find();
        String pid = String.valueOf(matcher.group());
        return pid;
    }


    public String clickOnARandomProduct() {
        List<WebElement> allProductElementsOnPage = getAllProductElementsOnPage();
        Collections.shuffle(allProductElementsOnPage);
        String detailsPageLink = allProductElementsOnPage.get(0).getAttribute("href");
        String pid = getPidFromHref(detailsPageLink);
        allProductElementsOnPage.get(0).click();
        return pid;
    }

    public String clickOnARandomInStockProduct() {
        List<WebElement> allProductDescriptionElements = getAllProductDescriptionElements();
        Collections.shuffle(allProductDescriptionElements);
        String pid = null;
        for (WebElement allProductDescriptionElement : allProductDescriptionElements) {
            try {
                WebElement slug = allProductDescriptionElement.findElement(By.className("slug"));
                if (!slug.getText().equalsIgnoreCase("sold out")) {
                    pid = extractPidAndClick(allProductDescriptionElement);
                    break;
                }
            } catch (NoSuchElementException e) {
                pid = extractPidAndClick(allProductDescriptionElement);
                break;
            }
        }
        if (pid==null)
            throw new NullPointerException("Could not extract pid from href. Pid is null");
        return pid;
    }


    public List<String> getAllProductsPids() {
        List<WebElement> productsOnPage = getAllProductElementsOnPage();

        return Lists.newLinkedList(Lists.transform(productsOnPage, extractPidFunction));
    }


    Function<WebElement, String> extractPidFunction = new Function<WebElement, String>() {
        public String apply(WebElement productWebElement) {
            return extractPid(productWebElement);
        }
    };


    public String extractPid(WebElement anyProductWebElement) {
        String url = anyProductWebElement.getAttribute("href");
        Scanner pidScanner = new Scanner(url);
        pidScanner.useDelimiter("/");
        do {
            pidScanner.next();
            if (pidScanner.hasNextInt())
                return String.valueOf(pidScanner.nextInt());
        } while (pidScanner.hasNext());

        throw new InputMismatchException("Could not extract pid from source:"+url);
    }


    public void sortBy(SortOrder sortOrder) {
        WebElement orderOption = webBot.findElement(By.xpath(".//*[@id='product-list-price-filter']/select/option[@value='" + sortOrder.getOrderValue() + "']"));
        WaitUtil.clickWebElementAndWaitForSpinnerToAppearAndDisappear(orderOption, webBot);
    }

    public String randomlySelectOneUnselectedLevel2Category() {
        List<WebElement> level2UnselectedCategory = webBot.findElements(ELEMENT_LEVEL2_CATEGORY);
        Collections.shuffle(level2UnselectedCategory);

        WebElement webElement = level2UnselectedCategory.get(0);
        String href = webElement.getAttribute("href");
        String categoryName = href.substring(href.lastIndexOf("/") + 1);

        WaitUtil.clickWebElementAndWaitForSpinnerToAppearAndDisappear(webElement, webBot);

        return categoryName;
    }


    public String getPageRefreshTimeStamp() {
        WebElement pageRefreshWebElement = webBot.findElement(PAGE_REFRESH_TIME_STAMP_LOCATOR, WaitTime.FOUR);
        return pageRefreshWebElement.getAttribute("data-timestamp");
    }

    public String getPartialUpdateTimeStamp() {
        WebElement pagePartialRefreshWebElement = webBot.findElement(PARTIAL_UPDATE_TIME_STAMP_LOCATOR, WaitTime.FOUR);
        return pagePartialRefreshWebElement.getAttribute("data-timestamp");
    }


    public void mouseOverTheFirstImage() {
        closeDontMissOutPopup();
        webBot.mouseOver(getFirstProductElement());
    }


    @Override
    public IProductDetailsPage gotoProductDetailsPage(String pid) {
        napProductDetailsPage.goToProduct(pid);
        return napProductDetailsPage;
    }

    public void selectARandomSecondLevelCategory() {
        List<WebElement> tableRows = webBot.findElements(LEFT_HAND_NAV_LOCATOR, WaitTime.FOUR);
        Collections.shuffle(tableRows);
        WebElement selectedWebElement = tableRows.get(0);
        selectedWebElement.click();
    }

    public boolean isDropDownDisplayed() {
        WebElement element = webBot.findElement(PageNavigationComponent.VIEW_ALL_DROPDOWN_LOCATOR, WaitTime.FOUR);
        return element.getAttribute("value").contains("60");
    }

    public List<SortOrder> getSortingDropdownOptions() {
        List<SortOrder> results = new ArrayList<SortOrder>();

        for (WebElement element : getSortingDropdownOptionElements()) {
            results.add(SortOrder.create(element.getAttribute("value")));
        }

        return results;
    }

    private List<WebElement> getSortingDropdownOptionElements() {
        return webBot.findElements(SORTING_DROPDOWN_LOCATOR, WaitTime.FOUR);
    }

    public SortOrder randomlySelectSortingOrder(SortOrder ignoreThisOrder) {
        SortOrder result = null;

        List<WebElement> elements = getSortingDropdownOptionElements();
        Collections.shuffle(elements);

        for (WebElement element : elements) {
            result = SortOrder.create(element.getAttribute("value"));

            if (!ignoreThisOrder.equals(result)) {
                WaitUtil.clickWebElementAndWaitForSpinnerToAppearAndDisappear(element, webBot);

                break;
            }
        }

        return result;
    }

    public Set<String> getDesignerIds() {
        final List<WebElement> elements = webBot.findElements(By.xpath(ProductFilterEnum.DESIGNER.getDomXPathForId(true)), WaitTime.FOUR);

        return Sets.newHashSet(Lists.transform(elements, extractDesignerIdFunction));
    }


    public boolean isMultiplePages() {
        return getPageNavigationComponent().getPageCount() > 1;
    }


    public SortOrder getSelectedSortingDropdownOption() {
        WebElement element = webBot.findElement(SELECTED_SORTING_OPTION_LOCATOR);

        return SortOrder.create(element.getAttribute("value"));
    }

    public PageNavigationComponent getPageNavigationComponent() {
        return pageNavigationComponent;
    }

    public Set<String> getAllProductDesigners() {
        List<WebElement> productDesignerElements = webBot.findElements(PRODUCT_DESIGNERS, WaitTime.FOUR);
        return Sets.newHashSet(Lists.transform(productDesignerElements, WebElementDataExtractingFunctions.extractText));
    }

    public List<String> getAllProductDesignersWithDuplicates() {
        List<WebElement> productDesignerElements = webBot.findElements(PRODUCT_DESIGNERS, WaitTime.FOUR);
        return Lists.newArrayList(Lists.transform(productDesignerElements, WebElementDataExtractingFunctions.extractText));
    }
}
