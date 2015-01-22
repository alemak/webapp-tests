package com.netaporter.pws.automation.nap.pages;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.netaporter.pws.automation.nap.util.WebElementDataExtractingFunctions;
import com.netaporter.pws.automation.shared.pages.IProductListPage;
import com.netaporter.pws.automation.shared.utils.WaitUtil;
import com.netaporter.test.utils.pages.driver.WaitTime;
import com.netaporter.test.utils.pages.driver.WebDriverUtil;
import com.netaporter.test.utils.pages.exceptions.PageElementNotFoundException;
import com.netaporter.test.utils.pages.regionalisation.RegionaliseWithCountrySlashLanguage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NAPProductListPage extends AbstractNAPProductListPage implements IProductListPage {

    private By LISTING_PAGE_NAME_FROM_HEADER_LOCATOR = By.cssSelector("div.product-list-title > h1 > a");

    public enum PriceRequirement {
        ABOVEOREQUAL(Boolean.TRUE),
        DONTCARE(null),
        BELOW(Boolean.FALSE);

        private Boolean priceOverThreshold;

        private PriceRequirement(Boolean priceOverThreshold) {
            this.priceOverThreshold = priceOverThreshold;
        }

        public boolean isPriceRequiredOverThreshold() {
            return priceOverThreshold == null || priceOverThreshold;
        }
    }

    private By DESIGNER_PAGE_NAV_ELEMENT = By.className("page-nav");

    //Simple pattern to identify if the price is a non sale one
    //Sale prices are formatted like (in english) "was £123 now £123 80% off"
    private static final Pattern NON_SALE_PRICE_PATTERN = Pattern.compile("^\\S+$");

    //This pattern will check the sale prices for presence of this string "Now $8  70" and will return the first number; both $ and £ currency symbols should match
    private static final Pattern SALE_PRICE_PATTERN = Pattern.compile("\\w+\\s.(\\d+)\\s\\s\\d+");
    private static final Random RANDOM_NUMBER_GENERATOR = new Random(new Date().getTime());

    private By PRODUCT_DESCRIPTIONS = By.cssSelector(".description>a");
    private By SELECT_FIRST_PRODUCT_IN_LIST_ELEMENT = By.xpath(".//*[@id='product-list']/div[1]/div[1]/a/img");
    //path to designer name
    private By DESIGNER_NAME_ELEMENTS = By.xpath(".//*[@id='product-list']/div/div/a/span");

    private final boolean isMultipleSize;
    private String subCategoryPath;


    public NAPProductListPage(String pageName, String path) {
        this(pageName, path, false);
        setRegionalisePathBehavior(new RegionaliseWithCountrySlashLanguage());
    }

    public NAPProductListPage(String pageName, String path, boolean isMultipleSize) {
        super(pageName, path);
        this.isMultipleSize = isMultipleSize;
    }
    public NAPProductListPage(String pageName, String path, WebDriverUtil webBot) {
        this(pageName, path, webBot, false);
    }
    public NAPProductListPage(String pageName, String path, WebDriverUtil webBot, boolean isMultipleSize) {
        super(pageName, path, webBot);
        this.isMultipleSize = isMultipleSize;
    }



    @Override
    public boolean isMultipleSize() {
        return isMultipleSize;
    }

    @Override
    public String getPath() {
        return (subCategoryPath == null || subCategoryPath.isEmpty()) ? path : path.replace("All", subCategoryPath);
    }

    @Override
    public void go() {
        super.go();
        WaitUtil.waitForSpinnerToAppearAndDisappear(webBot);
    }

    public void go(String subCategoryPath) {
        this.subCategoryPath = subCategoryPath;
        this.go();
    }

    public boolean isEmpty() {
        try {
            webBot.findElement(DESIGNER_PAGE_NAV_ELEMENT, WaitTime.FOUR);
            return false;
        } catch (PageElementNotFoundException e) {
            return true;
        }
    }

    public List<String> getPIDsMatching(PriceRequirement priceRequirement, int thresholdPrice) {
        List<String> matchingPids = new ArrayList<String>();
        List<String> allPids = getAllProductsPids();
        List<String> allPrices = getAllProductsPriceAsStrings();
        Map<String, String> pidToPriceMap = createPidToPriceMap(allPids, allPrices);

        for (String pid : createAShuffledPidList(allPids)) {
            if (isPriceMeetingRequirement(pidToPriceMap.get(pid), priceRequirement, thresholdPrice)) {
                matchingPids.add(pid);
            }
        }
        return matchingPids;
    }

    private List<String> createAShuffledPidList(List<String> allPids) {
        List<String> shuffledPids = new ArrayList<String>();
        shuffledPids.addAll(allPids);

        Collections.shuffle(shuffledPids);
        return shuffledPids;
    }

    private boolean isPriceMeetingRequirement(String checkingPrice, PriceRequirement priceRequirement, int price) {
        if (PriceRequirement.DONTCARE == priceRequirement) {
            return true;
        }

        try {
            int priceInInteger = getPriceValueFromString(checkingPrice);
            return (priceInInteger >= price) == priceRequirement.isPriceRequiredOverThreshold();
        } catch (NumberFormatException e) {
            return false;
        }
    }

    protected int getPriceValueFromString(String checkingPrice) {
        Matcher nonSaleMatcher = NON_SALE_PRICE_PATTERN.matcher(checkingPrice);
        Matcher saleMatcher = SALE_PRICE_PATTERN.matcher(checkingPrice);

        if (saleMatcher.find()) {
            return Integer.parseInt(saleMatcher.group(1));
        }
        else if (nonSaleMatcher.matches()) {
            //Replace any grouping separators and currency symbol
            //If for some bizarre reason we start showing decimal places on the listing page, this will result in incorrect values
            return Integer.parseInt(checkingPrice.replaceAll("\\D", ""));
        }
        throw new NumberFormatException("Too difficult to extract price. Giving up");
    }

    public HashMap<String, String> getPidAndCurrencyMap() {
        List<String> allProductsPriceAsStrings = getAllProductsPriceAsStrings();
        List<String> allProductPids = getAllProductsPids();
        HashMap<String, String> currencyAndPidMap = new HashMap<String, String>();

        for (int i =0;i<allProductPids.size();i++) {
            currencyAndPidMap.put(allProductPids.get(i), allProductsPriceAsStrings.get(i).substring(0,1));
        }
        return currencyAndPidMap;
    }

    public String getAnyListedPid() {
        WebElement anyProductWebElement = pickAnyProduct();
        return extractPid(anyProductWebElement);
    }

    private WebElement pickAnyProduct() {
        List<WebElement> productsOnPage = getAllProductElementsOnPage();

        int size = productsOnPage.size();

        if (size < 1) {
            throw new RuntimeException("Couldn't find any in stock products on this listing page1: " + pageName);
        }

        int randomProductIndex = RANDOM_NUMBER_GENERATOR.nextInt(size);

        return productsOnPage.get(randomProductIndex);
    }

    public void selectFirstProductInProductListings() {
        webBot.click(SELECT_FIRST_PRODUCT_IN_LIST_ELEMENT);
    }

    public Set<String> getDesignerNames() {
        final List<WebElement> elements = webBot.findElements(DESIGNER_NAME_ELEMENTS, WaitTime.FOUR);
        List<String> stringDesigners = Lists.transform(elements, WebElementDataExtractingFunctions.extractText);

        return Sets.newHashSet(stringDesigners);
    }

    public List<String> getAllProductDescriptions() {
        List<WebElement> productDescriptionElements = webBot.findElements(PRODUCT_DESCRIPTIONS);
        return Lists.newArrayList(Lists.transform(productDescriptionElements, WebElementDataExtractingFunctions.extractTitle));
    }

    public String getListingPageNameFromHeader() {
        return webBot.findElement(LISTING_PAGE_NAME_FROM_HEADER_LOCATOR, WaitTime.FOUR).getText();
    }
}
