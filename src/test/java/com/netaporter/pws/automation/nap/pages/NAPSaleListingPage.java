package com.netaporter.pws.automation.nap.pages;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.netaporter.pws.automation.nap.util.WebElementDataExtractingFunctions;
import com.netaporter.pws.automation.shared.pages.IProductListPage;
import com.netaporter.test.utils.pages.driver.WebDriverUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: o_csiki
 * Date: 21/10/13
 */

public class NAPSaleListingPage extends AbstractNAPProductListPage implements IProductListPage {

    private static final By SALE_HOME_SALE_NAVIGATION_ELEMENT_KEY = By.xpath(".//*[@id='content']/ul[1]/ul/li[1]/a");
    private static final By CLOTHING_SALE_NAVIGATION_ELEMENT_KEY = By.xpath(".//*[@id='content']/ul[1]/ul/li[2]/a");
    private static final By BAGS_SALE_NAVIGATION_ELEMENT_KEY = By.xpath(".//*[@id='content']/ul[1]/ul/li[3]/a");
    private static final By SHOES_SALE_NAVIGATION_ELEMENT_KEY = By.xpath(".//*[@id='content']/ul[1]/ul/li[4]/a");
    private static final By ACCESSORIES_SALE_NAVIGATION_ELEMENT_KEY = By.xpath(".//*[@id='content']/ul[1]/ul/li[5]/a");
    private static final By LINGERIE_SALE_NAVIGATION_ELEMENT_KEY = By.xpath(".//*[@id='content']/ul[1]/ul/li[6]/a");

    private final boolean isMultipleSize;

    // Note: move some price/discount/currency logic into WebElementDataExtractingFunctions if possible
    private static final Pattern PRICE_PATTERN = Pattern.compile("\\s[£$€](\\d+)\\s");

    private static final Pattern DISCOUNT_PERCENTAGE_PATTERN = Pattern.compile("\\s(\\d+)[%]");

    private static final Pattern CURRENCY_PATTERN = Pattern.compile("[£$€]");

    //clothing lefthand nav page elements
    public static final By CLOTHING_PAGE_ELEMENT_KEY = By.className("Clothing");
    public static final By ACTIVEWEAR_PAGE_ELEMENT_KEY = By.className("Activewear");
    public static final By BEACHWEAR_PAGE_ELEMENT_KEY = By.className("Beachwear");
    public static final By COATS_PAGE_ELEMENT_KEY = By.className("Coats");
    public static final By DRESSES_PAGE_ELEMENT_KEY = By.className("Dresses");
    public static final By JACKETS_PAGE_ELEMENT_KEY = By.className("Jackets");
    public static final By JEANS_PAGE_ELEMENT_KEY = By.className("Jeans");
    public static final By JUMPSUITS_PAGE_ELEMENT_KEY = By.className("Jumpsuits");
    public static final By KNITWEAR_PAGE_ELEMENT_KEY = By.className("Knitwear");
    public static final By PANTS_PAGE_ELEMENT_KEY = By.className("Pants");
    public static final By SHORTS_PAGE_ELEMENT_KEY = By.className("Shorts");
    public static final By SKIRTS_PAGE_ELEMENT_KEY = By.className("Skirts");
    public static final By TOPS_PAGE_ELEMENT_KEY = By.className("Tops");

    //bags lefthand nav page elements
    public static final By BAGS_PAGE_ELEMENT_KEY = By.className("Bags");
    public static final By BACKPACKS_PAGE_ELEMENT_KEY = By.className("Backpacks");
    public static final By CLUTCHBAGS_PAGE_ELEMENT_KEY = By.className("Clutch_Bags");
    public static final By SHOULDERBAGS_PAGE_ELEMENT_KEY = By.className("Shoulder");
    public static final By TOTEBAGS_PAGE_ELEMENT_KEY = By.className("Tote_Bags");
    public static final By TRAVELBAGS_PAGE_ELEMENT_KEY = By.className("Travel");

    //shoes lefthand nav page elements
    public static final By SHOES_PAGE_ELEMENT_KEY = By.className("Shoes");
    public static final By BOOTS_PAGE_ELEMENT_KEY = By.className("Boots");
    public static final By FLATSHOES_PAGE_ELEMENT_KEY = By.className("Flat");
    public static final By PUMPS_PAGE_ELEMENT_KEY = By.className("Pumps");
    public static final By SANDALS_PAGE_ELEMENT_KEY = By.className("Sandals");
    public static final By SNEAKERS_PAGE_ELEMENT_KEY = By.className("Sneakers");

    //accessories lefthand nav page elements
    public static final By ACCESSORIES_PAGE_ELEMENT_KEY = By.className("Accessories");
    public static final By BELTS_PAGE_ELEMENT_KEY = By.className("Belts");
    public static final By BOOKS_PAGE_ELEMENT_KEY = By.className("Books");
    public static final By COLLARS_PAGE_ELEMENT_KEY = By.className("Collars");
    public static final By FINEJEWELRY_PAGE_ELEMENT_KEY = By.className("Fine");
    public static final By GLOVES_PAGE_ELEMENT_KEY = By.className("Gloves");
    public static final By HAIRACCESSORIES_PAGE_ELEMENT_KEY = By.className("Hair");
    public static final By HATS_PAGE_ELEMENT_KEY = By.className("Hats");
    public static final By HOMEWARE_PAGE_ELEMENT_KEY = By.className("Homeware");
    public static final By JEWELRY_PAGE_ELEMENT_KEY = By.className("Jewelry");
    public static final By KEYFOBS_PAGE_ELEMENT_KEY = By.className("Key_Fobs");
    public static final By OPTICALS_PAGE_ELEMENT_KEY = By.className("Opticals");
    public static final By POUCHES_PAGE_ELEMENT_KEY = By.className("Pouches");
    public static final By PRINTS_PAGE_ELEMENT_KEY = By.className("Prints");
    public static final By SCARVES_PAGE_ELEMENT_KEY = By.className("Scarves");
    public static final By SUNGLASSES_PAGE_ELEMENT_KEY = By.className("Sunglasses");
    public static final By TECHNOLOGY_PAGE_ELEMENT_KEY = By.className("Technology");
    public static final By TRAVEL_PAGE_ELEMENT_KEY = By.className("Travel");
    public static final By UMBRELLAS_PAGE_ELEMENT_KEY = By.className("Umbrellas");
    public static final By WALLETS_PAGE_ELEMENT_KEY = By.className("Wallets");
    public static final By WATCHES_PAGE_ELEMENT_KEY = By.className("Watches");

    //lingerie lefthand nav page elements
    public static final By LINGERIE_PAGE_ELEMENT_KEY = By.className("Lingerie");
    public static final By BRAS_PAGE_ELEMENT_KEY = By.className("Bras");
    public static final By BRIEFS_PAGE_ELEMENT_KEY = By.className("Briefs");
    public static final By CAMISOLESANDCHEMISES_PAGE_ELEMENT_KEY = By.className("Camisoles");
    public static final By CORSETRY_PAGE_ELEMENT_KEY = By.className("Corsetry");
    public static final By HOSIERY_PAGE_ELEMENT_KEY = By.className("Hosiery");
    public static final By LINGERIEACCESSORIES_PAGE_ELEMENT_KEY = By.className("Lingerie Accessories");
    public static final By ROBES_PAGE_ELEMENT_KEY = By.className("Robes");
    public static final By SHAPEWEAR_PAGE_ELEMENT_KEY = By.className("Shapewear");
    public static final By SLEEPWEAR_PAGE_ELEMENT_KEY = By.className("Sleepwear");

    private static final By PRODUCT_DESIGNERS = By.xpath(".//*[@id='product-list']/ul/li/div/a/span");

    public NAPSaleListingPage(String pageName, String path, boolean isMultipleSize) {
        super(pageName, path);
        this.isMultipleSize = isMultipleSize;
    }
    public NAPSaleListingPage(String pageName, String path, WebDriverUtil webBot, boolean isMultipleSize) {
        super(pageName, path, webBot);
        this.isMultipleSize = isMultipleSize;
    }
    public NAPSaleListingPage(String pageName, String path) {
        this(pageName, path, false);
    }

    public NAPSaleListingPage(String pageName, String path, WebDriverUtil webBot) {
        this(pageName, path, webBot, false);
    }

    @Override
    public boolean isMultipleSize() {
        return isMultipleSize;
    }

    @Override
    public boolean isPageRegionalised() {
        return false;
    }

    private List<String> getAllPriceStrings() {

        List<WebElement> productDescriptionElements = webBot.findElements(By.cssSelector(".description"));

        List<String> priceStrings = new ArrayList<String>();

        for (WebElement productDescriptionElement : productDescriptionElements) {
            priceStrings.add(productDescriptionElement.getText());
        }

        return priceStrings;
    }

    public List<String> getAllMarkdownPrices() {

        List<String> allPriceStrings = getAllPriceStrings();
        List<String> allPriceValueStrings = new ArrayList<String>();

        for (String allPriceString : allPriceStrings) {
            int priceValue = getMarkdownPriceValueFromString(allPriceString);
            allPriceValueStrings.add(String.valueOf(priceValue));
        }
        return  allPriceValueStrings;
    }


    public List<String> getAllDiscountValues() {

        List<String> allPriceStrings = getAllPriceStrings();
        List<String> allDiscountValues = new ArrayList<String>();

        for (String priceString : allPriceStrings) {
            int discountValue = getDiscountValueFromString(priceString);
            allDiscountValues.add(String.valueOf(discountValue));
        }
        return allDiscountValues;
    }

    private int getDiscountValueFromString(String priceString) {

        Matcher discountMatcher = DISCOUNT_PERCENTAGE_PATTERN.matcher(priceString.replaceAll("[,.]",""));

        if (discountMatcher.find())  {
            return Integer.parseInt(discountMatcher.group(1));
        }
        else
            throw new NumberFormatException("Could not perform regex extraction of discount integer from price string: "+priceString);
    }

    public List<String> getAllOriginalPrices() {

        List<String> allPriceStrings = getAllPriceStrings();
        List<String> allPriceValueStrings = new ArrayList<String>();

        for (String allPriceString : allPriceStrings) {
            int priceValue = getOriginalPriceValueFromString(allPriceString);
            allPriceValueStrings.add(String.valueOf(priceValue));
        }
        return  allPriceValueStrings;

    }

    protected int getMarkdownPriceValueFromString(String checkingPrice) {
        Matcher saleMatcher = PRICE_PATTERN.matcher(checkingPrice.replaceAll("[,.]",""));
        saleMatcher.find();

        if (saleMatcher.find())
            return Integer.parseInt(saleMatcher.group(1));
        else
            throw new NumberFormatException("Could not perform regex extraction of markdown price integer from price string: "+checkingPrice);
    }

    protected int getOriginalPriceValueFromString(String checkingPrice) {
        Matcher originalPriceMatcher = PRICE_PATTERN.matcher(checkingPrice.replaceAll("[,.]",""));

        if (originalPriceMatcher.find())
            return Integer.parseInt(originalPriceMatcher.group(1));
        else
            throw new NumberFormatException("Could not perform regex extraction of original price integer from price string: "+checkingPrice);
    }

    public Map<String, String> getPidAndMarkdownPriceMap() {

        return createPidToPriceMap(getAllProductsPids(), getAllMarkdownPrices());

    }

    public List<String> getAllProductNames() {

        List<String> allProductNames = new ArrayList<String>();

        List<WebElement> allProductElementsOnPage = getAllProductElementsOnPage();
        for (WebElement webElement : allProductElementsOnPage) {
            allProductNames.add(webElement.getText());
        }

        return allProductNames;
    }

    public HashMap<String, String> getPidAndCurrencyMap() {
        List<String> allProductsPriceAsStrings = getAllProductsPriceAsStrings();
        List<String> allProductPids = getAllProductsPids();
        HashMap<String, String> currencyAndPidMap = new HashMap<String, String>();

        for (int i =0;i<allProductPids.size();i++) {
            currencyAndPidMap.put(allProductPids.get(i), getCurrencyFromPriceString(allProductsPriceAsStrings.get(i)));
        }
        return currencyAndPidMap;
    }

    public String getCurrencyFromPriceString(String checkingPrice) {
        Matcher currencyMatcher = CURRENCY_PATTERN.matcher(checkingPrice);

        String originalPriceCurrency = "";
        String salePriceCurrency = "";

        if(currencyMatcher.find())
            originalPriceCurrency = currencyMatcher.group();
        else
            throw new NumberFormatException("Could not perform regex extraction of original currency string from price string: "+checkingPrice);


        if(currencyMatcher.find())
            salePriceCurrency = currencyMatcher.group();
        else
            throw new NumberFormatException("Could not perform regex extraction of sale currency string from price string: "+checkingPrice);

        if (!originalPriceCurrency.equals(salePriceCurrency))
            throw new UnsupportedOperationException("Original price currency "+originalPriceCurrency+" does not match the sale price currency "+salePriceCurrency);

        return originalPriceCurrency;
    }

    public boolean clickOnSecondLevelCategory(String category) {

        // Note: use map
        if ("Activewear".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(ACTIVEWEAR_PAGE_ELEMENT_KEY);
        else if ("Beachwear".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(BEACHWEAR_PAGE_ELEMENT_KEY);
        else if ("Coats".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(COATS_PAGE_ELEMENT_KEY);
        else if ("Dresses".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(DRESSES_PAGE_ELEMENT_KEY);
        else if ("Jackets".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(JACKETS_PAGE_ELEMENT_KEY);
        else if ("Jeans".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(JEANS_PAGE_ELEMENT_KEY);
        else if ("Jumpsuits".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(JUMPSUITS_PAGE_ELEMENT_KEY);
        else if ("Knitwear".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(KNITWEAR_PAGE_ELEMENT_KEY);
        else if ("Pants".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(PANTS_PAGE_ELEMENT_KEY);
        else if ("Shorts".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(SHORTS_PAGE_ELEMENT_KEY);
        else if ("Skirts".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(SKIRTS_PAGE_ELEMENT_KEY);
        else if ("Tops".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(TOPS_PAGE_ELEMENT_KEY);
        else if ("Backpacks".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(BACKPACKS_PAGE_ELEMENT_KEY);
        else if ("Clutch_Bags".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(CLUTCHBAGS_PAGE_ELEMENT_KEY);
        else if ("Shoulder_Bags".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(SHOULDERBAGS_PAGE_ELEMENT_KEY);
        else if ("Tote_Bags".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(TOTEBAGS_PAGE_ELEMENT_KEY);
        else if ("Travel_Bags".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(TRAVEL_PAGE_ELEMENT_KEY);
        else if ("Boots".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(BOOTS_PAGE_ELEMENT_KEY);
        else if ("Flat_Shoes".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(FLATSHOES_PAGE_ELEMENT_KEY);
        else if ("Pumps".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(PUMPS_PAGE_ELEMENT_KEY);
        else if ("Sandals".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(SANDALS_PAGE_ELEMENT_KEY);
        else if ("Sneakers".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(SNEAKERS_PAGE_ELEMENT_KEY);
        else if ("Belts".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(BELTS_PAGE_ELEMENT_KEY);
        else if ("Books".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(BOOKS_PAGE_ELEMENT_KEY);
        else if ("Collars".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(COLLARS_PAGE_ELEMENT_KEY);
        else if ("Fine_Jewelry".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(FINEJEWELRY_PAGE_ELEMENT_KEY);
        else if ("Gloves".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(GLOVES_PAGE_ELEMENT_KEY);
        else if ("Hair_Accessories".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(HAIRACCESSORIES_PAGE_ELEMENT_KEY);
        else if ("Hats".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(HATS_PAGE_ELEMENT_KEY);
        else if ("Homeware".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(HOMEWARE_PAGE_ELEMENT_KEY);
        else if ("Jewelry".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(JEWELRY_PAGE_ELEMENT_KEY);
        else if ("Key_Fobs".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(KEYFOBS_PAGE_ELEMENT_KEY);
        else if ("Opticals".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(OPTICALS_PAGE_ELEMENT_KEY);
        else if ("Pouches".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(POUCHES_PAGE_ELEMENT_KEY);
        else if ("Prints".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(PRINTS_PAGE_ELEMENT_KEY);
        else if ("Scarves".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(SCARVES_PAGE_ELEMENT_KEY);
        else if ("Sunglasses".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(SUNGLASSES_PAGE_ELEMENT_KEY);
        else if ("Technology".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(TECHNOLOGY_PAGE_ELEMENT_KEY);
        else if ("Travel".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(TRAVEL_PAGE_ELEMENT_KEY);
        else if ("Umbrellas".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(UMBRELLAS_PAGE_ELEMENT_KEY);
        else if ("Wallets".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(WALLETS_PAGE_ELEMENT_KEY);
        else if ("Watches".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(WATCHES_PAGE_ELEMENT_KEY);
        else if ("Lingerie".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(LINGERIE_PAGE_ELEMENT_KEY);
        else if ("Bras".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(BRAS_PAGE_ELEMENT_KEY);
        else if ("Briefs".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(BRIEFS_PAGE_ELEMENT_KEY);
        else if ("Bras".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(BRAS_PAGE_ELEMENT_KEY);
        else if ("Shapewear".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(SHAPEWEAR_PAGE_ELEMENT_KEY);
        else if ("Camisoles_and_Chemises".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(CAMISOLESANDCHEMISES_PAGE_ELEMENT_KEY);
        else if ("Corsetry".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(CORSETRY_PAGE_ELEMENT_KEY);
        else if ("Hosiery".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(HOSIERY_PAGE_ELEMENT_KEY);
        else if ("Lingerie_Accessories".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(LINGERIEACCESSORIES_PAGE_ELEMENT_KEY);
        else if ("Robes".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(ROBES_PAGE_ELEMENT_KEY);
        else if ("Sleepwear".equalsIgnoreCase(category))
            return clickIfVisibleInListingPage(SLEEPWEAR_PAGE_ELEMENT_KEY);
        else
            throw new UnsupportedOperationException("Invalid sale subcategory specified: "+category);
    }

    @Override
    public Set<String> getAllProductDesigners() {
        List<WebElement> productDesignerElements = webBot.findElements(PRODUCT_DESIGNERS);

        return Sets.newHashSet(Lists.transform(productDesignerElements, WebElementDataExtractingFunctions.extractText));
    }

    public List<String> getAllProductDesignersWithDuplicates() {
        List<WebElement> productDesignerElements = webBot.findElements(PRODUCT_DESIGNERS);

        return Lists.newArrayList(Lists.transform(productDesignerElements, WebElementDataExtractingFunctions.extractText));
    }

    protected boolean clickIfVisibleInListingPage(final By By) {
        if(!webBot.isElementPresent(By, 3))
            throw new UnsupportedOperationException("Waited for 3 seconds, but By "+By+" was not visible to click");
        webBot.findElement(By).findElement(By.xpath("a")).click();
        return true;
    }

    public void clickCategoryFromSaleNavigation(String category) {
        if ("Clothing".equalsIgnoreCase(category))
            webBot.findElement(CLOTHING_SALE_NAVIGATION_ELEMENT_KEY).click();
        else if ("Shoes".equalsIgnoreCase(category))
            webBot.findElement(SHOES_SALE_NAVIGATION_ELEMENT_KEY).click();
        else if ("Bags".equalsIgnoreCase(category))
            webBot.findElement(BAGS_SALE_NAVIGATION_ELEMENT_KEY).click();
        else if ("Accessories".equalsIgnoreCase(category))
            webBot.findElement(ACCESSORIES_SALE_NAVIGATION_ELEMENT_KEY).click();
        else if ("Lingerie".equalsIgnoreCase(category))
            webBot.findElement(LINGERIE_SALE_NAVIGATION_ELEMENT_KEY).click();
        else if ("Sale_Home".equalsIgnoreCase(category))
            webBot.findElement(SALE_HOME_SALE_NAVIGATION_ELEMENT_KEY).click();
        else
            throw new UnsupportedOperationException("Unsupported category from the sale navigation specified:" +category);
    }
}