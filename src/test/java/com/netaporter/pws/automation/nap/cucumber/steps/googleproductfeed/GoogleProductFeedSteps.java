package com.netaporter.pws.automation.nap.cucumber.steps.googleproductfeed;

import com.google.common.collect.Sets;
import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.jdom.Element;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * User: x.qi@london.net-a-porter.com
 * Date: 15/03/2013
 */
public class GoogleProductFeedSteps extends BaseNapSteps {

    private static final String KEY_FEED = "feed";
    private static final String KEY_PIDS_AND_STOCK_INFO = "pidsAndStockInfo";
    private static final String KEY_PIDS_AND_DESIRED_INFO = "pidAndInfoToCheck";

    private static final String AVAILABILITY = "availability";
    private static final String PRICE = "price";

    private static final String IN_STOCK_VALUE = "in stock";
    private static final String OUT_OF_STOCK_VALUE = "out of stock";
    private static final Set<String> STOCK_INFO_VALUES = Sets.newHashSet(IN_STOCK_VALUE, OUT_OF_STOCK_VALUE);

    private static final Random RANDOM_GENERATOR = new Random(new Date().getTime());

    private static final Pattern PRODUCT_DETAIL_SALE_PRICE_PATTERN = Pattern.compile("\\w+\\s(.)(\\d+).");
    private static final String SIZE = "size";

    @Given("^I retrieve a product feed (.*)$")
    public void I_retrieve_product_feed_from_url(String fileName) throws Throwable {

        String feedFullUrl = createFeedUrl(fileName);

        HttpURLConnection httpcon = (HttpURLConnection) new URL(feedFullUrl).openConnection();

        SyndFeed feed = new SyndFeedInput().build(new XmlReader(httpcon));

        scenarioSession.putData(KEY_FEED, feed);
    }

    @Then("^The product feed contains more than (\\d+) products$")
    public void product_feed_contains_more_than_products(int count) throws Throwable {
        List<SyndEntry> entries = getSyndEntries();

        assertTrue("Feed only contains " + entries.size() + " entries, which is less than " + count, entries.size() > count);
    }

    @When("^I randomly pick (\\d+) product and (.*) pairs from the feed$")
    public void I_randomly_pick_some_product_and_stock_level_pairs_from_the_feed(int requiredNumber, String productInfo) throws Throwable {
        List<SyndEntry> entries = getSyndEntries();

        Map<String, String> pidsAndDesiredInfo = createAMapContainingRequiredNumberOfPidsAndDesiredInfo(requiredNumber, entries, productInfo);

        scenarioSession.putData(KEY_PIDS_AND_DESIRED_INFO, pidsAndDesiredInfo);
    }

    @Then("^I can find these products have the same (.*) info on the website$")
    public void I_can_find_these_products_have_the_same_info_on_the_website(String desiredInfo) throws Throwable {

        Map<String, String> pidsAndDesiredInfo = (Map<String, String>) scenarioSession.getData(KEY_PIDS_AND_DESIRED_INFO);

        validateDesiredInfo(pidsAndDesiredInfo, desiredInfo);
    }

    private void validateDesiredInfo(Map<String, String> pidsAndDesiredInfo, String desiredInfo) {
        for (Map.Entry<String, String> entry : pidsAndDesiredInfo.entrySet()) {
            String pid = entry.getKey();

            productDetailsPage.goToProduct(pid);

            if (PRICE.equals(desiredInfo)) {
                assertPriceInfo(entry.getValue(), pid);
            }
            else if (AVAILABILITY.equals(desiredInfo)) {
                assertStockInfo(entry.getValue(), pid);
            }
            else if (SIZE.equals(desiredInfo)) {
                assertSizeInfo(entry.getValue(), pid);
            }
        }
    }

    private void assertPriceInfo(String priceInfo, String pid) {

        int spaceIndex = priceInfo.lastIndexOf(" ");
        float expectedPriceValue = Float.parseFloat(priceInfo.substring(0,spaceIndex));
        String expectedPriceCurrency = priceInfo.substring(spaceIndex+1);

        //currencies in the Google feed are displayed as GBP/USD/AUD/EUR, need to be converted to £, $ or €
        Map<String, String> currencyMap = new HashMap<String, String>();
        currencyMap.put("GBP", "£");
        currencyMap.put("USD", "$");
        currencyMap.put("EUR", "€");
        currencyMap.put("AUD", "$");

        String expectedPriceCurrencySymbol = currencyMap.get(expectedPriceCurrency);

        final String listedPrice = productDetailsPage.getListedPriceString();
        float actualPriceValue;
        String actualPriceCurrency;

        //sale prices
        if (listedPrice.startsWith("Was ")){

            int nowIndex = listedPrice.indexOf("N");
            String listedPriceSubstring = listedPrice.substring(nowIndex).replace(",", "");

            Matcher saleMatcher = PRODUCT_DETAIL_SALE_PRICE_PATTERN.matcher(listedPriceSubstring);

            saleMatcher.find();

            actualPriceValue = Integer.parseInt(saleMatcher.group(2));
            actualPriceCurrency = saleMatcher.group(1);

        }
        //non sale prices
        else {

            actualPriceCurrency = listedPrice.substring(0, 1);
            actualPriceValue = Float.parseFloat(listedPrice.substring(1).replace(",", ""));
        }

        assertEquals("Currency value does not match for product with pid " + pid, expectedPriceCurrencySymbol, actualPriceCurrency);
        //cast to int because prices in the google feed do not have decimals
        assertEquals("Price value does not match for product with pid " + pid, (int) expectedPriceValue, (int) actualPriceValue);
    }

    private void assertSizeInfo(String sizeInfo, String pid) {
        if ("n/a".equals(sizeInfo) || "One size".equals(sizeInfo)) {
            assertTrue("Product pid: " + pid + " size is n/a in feed, but has multiple sizes on product details page", productDetailsPage.getSKUs().size() == 1);
        }
        else{
            assertTrue("Google feed product size does not exist in the product details page "+pid, productDetailsPage.getProductSize().contains(sizeInfo));
        }
    }

    private void assertStockInfo(String stockInfo, String pid) {
        validateDesiredInfo(stockInfo);
        assertEquals("Stock info does not match for product with pid " + pid, stockInfo.equals(IN_STOCK_VALUE), productDetailsPage.isInStock());
    }

    @When("^I randomly pick one product of each (.*) values from the feed$")
    public void I_randomly_pick_one_product_of_each_desired_values_from_the_feed(String desiredInfo) throws Throwable {
        List<SyndEntry> syndEntries = getSyndEntries();

        Set<String> desiredInfoValues = new HashSet<String>();

        Map<String, String> pidsAndDesiredInfo = new HashMap<String, String>();

        for (SyndEntry syndEntry : syndEntries) {
            String desiredInfoDetails = extractDesiredInfo(syndEntry, desiredInfo);

            validateDesiredInfo(desiredInfoDetails);

            if (!desiredInfoValues.contains(desiredInfoDetails)) {
                desiredInfoValues.add(desiredInfoDetails);
                pidsAndDesiredInfo.put(extractPid(syndEntry), desiredInfoDetails);
            }
            if (AVAILABILITY.equals(desiredInfo)) {
                if (desiredInfoValues.size() == STOCK_INFO_VALUES.size()) {
                    break;
                }
            }
            else if (SIZE.equals(desiredInfo)) {
                //check that all types of sizes have been added
                if (desiredInfoValues.size()>=3 && desiredInfoValues.contains("n/a") && desiredInfoValues.contains("One size")) {
                    break;
                }
            }
        }

        scenarioSession.putData(KEY_PIDS_AND_DESIRED_INFO, pidsAndDesiredInfo);
    }

    private void validateDesiredInfo(String desiredInfo) {
        if (AVAILABILITY.equals(desiredInfo)){
            if (!STOCK_INFO_VALUES.contains(desiredInfo)) {
                throw new UnsupportedOperationException("Don't support this stock info");
            }
        }
    }

    private String createFeedUrl(String fileName) {
        String baseUrl = webBot.getBaseUrl();
        return baseUrl.endsWith("/") ? baseUrl + fileName : baseUrl + "/" + fileName;
    }


    private List<SyndEntry> getSyndEntries() {
        SyndFeed feed = (SyndFeed) scenarioSession.getData(KEY_FEED);
        return feed.getEntries();
    }


    private Map<String, String> createAMapContainingRequiredNumberOfPidsAndDesiredInfo(int totalCount, List<SyndEntry> entries, String desiredInfo) {
        int size = entries.size();
        Map<String, String> pidsAndDesiredInfo = new HashMap<String, String>();

        for (int pickedCount = 0, entryIndex = 0; pickedCount < totalCount && entryIndex < size; entryIndex++) {
            SyndEntry entry = randomlyPickAnEntry(entries);

            String pid = extractPid(entry);
            String extractedDesiredInfo = extractDesiredInfo(entry, desiredInfo);

            if (pidsAndDesiredInfo.put(pid, extractedDesiredInfo) == null) {
                pickedCount++;
            }

        }
        return pidsAndDesiredInfo;
    }

//to remove if not needed anymore
//    private Map<String, String> createAMapContainingRequiredNumberOfPidsAndPriceInfo(int totalCount, List<SyndEntry> entries) {
//        int size = entries.size();
//        Map<String, String> pidsAndPriceInfo = new HashMap<String, String>();
//
//        for (int pickedCount = 0, entryIndex = 0; pickedCount < totalCount && entryIndex < size; entryIndex++) {
//            SyndEntry entry = randomlyPickAnEntry(entries);
//
//            String pid = extractPid(entry);
//            String priceInfo = extractDesiredInfo(entry, PRICE);
//
//
//            if (pidsAndPriceInfo.put(pid, priceInfo) == null) {
//                pickedCount++;
//            }
//
//        }
//        return pidsAndPriceInfo;
//    }

    private String extractDesiredInfo(SyndEntry entry, String desiredInfo) {
        List<Element> elements = (List<Element>) entry.getForeignMarkup();

        for (Element element : elements) {
            if (desiredInfo.equals(element.getName())) {
                return element.getContent(0).getValue();
            }
        }

        throw new IllegalArgumentException("Can't find "+desiredInfo+" info: " + entry);
    }

    private SyndEntry randomlyPickAnEntry(List<SyndEntry> entries) {
        int size = entries.size();
        return entries.get(RANDOM_GENERATOR.nextInt(size));
    }

    private String extractPid(SyndEntry entry) {
        try {
            String path = new URL(entry.getLink()).getPath();
            return path.substring(path.lastIndexOf("/") + 1);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid entry link in entry: "+ entry);
        }
    }

}
