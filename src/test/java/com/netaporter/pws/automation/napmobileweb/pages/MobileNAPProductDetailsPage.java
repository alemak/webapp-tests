package com.netaporter.pws.automation.napmobileweb.pages;

import com.google.common.base.Predicate;
import com.netaporter.pws.automation.shared.pages.IProductDetailsPage;
import com.netaporter.pws.automation.shared.pojos.Product;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.AddedToWishlistPopup;
import com.netaporter.pws.automation.shared.utils.WaitUtil;
import com.netaporter.test.utils.enums.RegionEnum;
import com.netaporter.test.utils.pages.exceptions.PageElementNotFoundException;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.Select;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@Component
@Scope("cucumber-glue")
public class MobileNAPProductDetailsPage extends AbstractMobileNapPage implements IProductDetailsPage {

    private static final String PAGE_NAME = "Product Details";
    private static final String PATH = "product";

    private static final String SOLD_OUT_TEXT_IN_SIZE_OPTION = "sold out";
    private static final String SKU_NAME_OR_ID = "sku";
    private static final String COMING_SOON_TEXT_IN_SIZE_OPTION = "coming soon";
    public static final By LOCALE_LINK_ELEMENT = By.cssSelector("html>head>link");

    //path to shipping restriction name
    private static By SHIPPING_RESTRICTION_ELEMENT = By.xpath(".//*[@id='shipping-restriction']/a");

    //path to product designer name
    private static By DESIGNER_NAME_ELEMENT = By.cssSelector("#product-details > h1 > a");
    public static final By CANONICAL_LINK_ELEMENT= By.cssSelector("html>head>link");

    //new wishlist general css selectors
    private static final String ADD_TO_WISHLIST_BUTTON = "input[type=button][value=\"ADD TO WISH LIST\"]";
    private static final String WISHLIST_MENU_ITEM = ".menu-item a";
    private static final String WISHLIST_SELECT_SIZE_ERROR = "div#choose-your-size div.error";

    //added to wishlist popup css selectors
    private static final String ADDED_TO_WISHLIST_POPUP = ".popup-item-added";
    private static final String ADDED_TO_WISHLIST_TEXT = ".popup-item-added .added-message";
    private static final String ADDED_TO_WISHLIST_LINK_DETAILS = ".popup-item-added .added-message a";
    private static final String SHOW_WISHLIST_TEXT = ".popup-item-added .proceed-to-purchase";
    private static final String SHOW_WISHLIST_LINK_DETAILS = ".popup-item-added .proceed-to-purchase";
    private static final String ADDED_TO_WISHLIST_IMG_SRC = ".popup-item-added img";
    private static final String ADDED_TO_WISHLIST_POPUP_LINK_DETAILS = ".popup-item-added a";
    private static final String ADDED_TO_WISHLIST_POPUP_CLOSE = ".popup-item-added .popup-close";


    public MobileNAPProductDetailsPage() {
        super(PAGE_NAME, PATH);
    }

    public void goToProduct(String productId) {
        String originalPath = getPath();
        setPath(getPath() + "/" + productId);
        go();
        setPath(originalPath);
    }

    public void goToProductHTTP(String productId) {
        String originalPath = getPath();
        webBot.setBaseUrl(webBot.getBaseUrl().replace("https:","http:"));
        setPath(originalPath + "/" + productId);
        go();
        setPath(originalPath);
    }

    /**
     *
     * @return Price string.  Maybe contain both 'was' and 'now' prices
     */
    public String getListedPriceString() {
        WebElement priceElement = webBot.findElement(By.id("price"));

        return priceElement.getText();
    }

    public String getListedDiscountedPrice() {
        WebElement priceElement = webBot.findElement(By.cssSelector("#price .now"));

        return priceElement.getText().replace("Now ", "");
    }

    public String getListedOriginalPrice() {
        WebElement priceElement = webBot.findElement(By.cssSelector("#price .was"));

        return priceElement.getText().replace("Was ", "");
    }

    @Override
    public Product addIntoShoppingBag(String inStockSKU) {
        selectSKU(inStockSKU);

        Integer originalShoppingBagQuantity = getOriginalShoppingBagQuantity();

        clickAddToShoppingBagButton();

        waitShoppingBagToBeUpdated(originalShoppingBagQuantity);

        // TODO don't need to create a complete Product yet as sku is suffcient for verification
        return new Product(null, inStockSKU);
    }


    private Integer getOriginalShoppingBagQuantity() {
        // Note: try three times as shopping bag item count could be empty sometimes
        for (int i =0; i < 3; i++)
        {
            try {
                WebElement shoppingBagBasketElement = webBot.findElement(By.id("header-basket"));
                return Integer.parseInt(shoppingBagBasketElement.getText());
            } catch (NumberFormatException e) {
                    System.out.println("Getting item count in shopping bag error: " + e);
                    System.out.println("Will try again in two seconds.");
                    WaitUtil.waitFor(2000);
            }
            catch (Exception e2) {
                throw new IllegalStateException("Cannot get shopping bag item count", e2);
            }
        }

        throw new IllegalStateException("Cannot get shopping bag item count");
    }

    private void waitShoppingBagToBeUpdated(final Integer originalShoppingBagQuantity) {

        webBot.waitUntil(new Predicate<WebDriver>() {
            @Override
            public boolean apply(@Nullable WebDriver webDriver) {
                //Check that the item has been added successfully
                int expectedQuantity = originalShoppingBagQuantity + 1;
                return webBot.isElementPresent(By.xpath("//div[@id='header-basket']/a[text() = '" + expectedQuantity + "']"));
            }
        });
    }


    @Override
    public void addIntoWishlist(String inStockSKU) throws Throwable {
        selectSKU(inStockSKU);
        Thread.sleep(1500);
        WebElement button = webBot.findElement(By.xpath("//div[@id='add-wishlist']/div/input"));
        button.click();
    }

    public void selectItemSizeClickNewAddToWishlist(String inStockSKU) throws Throwable {
        selectSKU(inStockSKU);
        Thread.sleep(1500);
        clickAddToWishlistButton();
    }

    public void selectItemSizeClickNewAddToWishlistNotSignedIn(String inStockSKU) throws Throwable {
        selectSKU(inStockSKU);
        Thread.sleep(1500);
        clickAddToWishlistButtonNotSignedIn();
    }

    public void clickAddToWishlistButton() throws Throwable {
        waitForElementToExist(By.cssSelector(ADD_TO_WISHLIST_BUTTON));
        if (doesElementExist(By.cssSelector((ADD_TO_WISHLIST_BUTTON)))) {
            WebElement button = webBot.findElement(By.cssSelector(ADD_TO_WISHLIST_BUTTON));
            button.click();
        } else {
            fail("Element "+ADD_TO_WISHLIST_BUTTON+" does not exist");
        }
    }

    public void clickAddToWishlistButtonNotSignedIn() throws Throwable {
        waitForElementToExist(By.cssSelector(ADD_TO_WISHLIST_BUTTON));
        if (doesElementExist(By.cssSelector((ADD_TO_WISHLIST_BUTTON)))) {
            WebElement button = webBot.findElement(By.cssSelector(ADD_TO_WISHLIST_BUTTON));
            button.click();
        } else {
            fail("Element "+ADD_TO_WISHLIST_BUTTON+" does not exist");
        }
    }

    public List<String> getAvailableWishlists() throws Throwable {

        if(!webBot.exists(null, By.cssSelector(".add-to-wishlist-button.is-open"))) {
            webBot.findElement(By.cssSelector(".add-to-wishlist-button")).click();
            Thread.sleep(1500);
        }
        List<String> names = new ArrayList<String>();
        List<WebElement> wishlists = webBot.findElements(By.cssSelector("div.wishlist-dropdown .menu li.menu-item"));
        for (WebElement element : wishlists) {
            names.add(element.getText());
        }
        return names;
    }

    public WebElement getCreateNewWishlistElement() {
        return webBot.findElement(By.cssSelector(".menu li.create-wishlist-menu-item span"));
    }

    public void createNewWishlist(String wishlistName) throws Throwable {
        webBot.findElement(By.cssSelector("li.create-wishlist-menu-item span")).click();
        waitForElementToExist(By.cssSelector("li.create-wishlist-menu-item.is-expanded"));
        webBot.findElement(By.cssSelector("li.create-wishlist-menu-item form input")).sendKeys(wishlistName);
        webBot.findElement(By.cssSelector("li.create-wishlist-menu-item form input")).sendKeys(Keys.ENTER);
        waitForElementToNotExist(By.cssSelector("li.create-wishlist-menu-item.is-expanded"));
    }


    public void selectWishlistToAddItemTo(String wishlistName) throws Throwable {

        Boolean clicked = false;

        Integer i=0;
        String wishlistNames = webBot.findElements(By.cssSelector(WISHLIST_MENU_ITEM)).toString();
        while(!wishlistNames.contains(wishlistName.toUpperCase()) && i < 10) {
            Thread.sleep(1000);
            wishlistNames = getAvailableWishlists().toString();
            i++;
        }

        for (WebElement wishlist : webBot.findElements(By.cssSelector(WISHLIST_MENU_ITEM))) {

            if(wishlist.getText().equals(wishlistName.toUpperCase())) {
                wishlist.click();
                clicked = true;
                break;
            }
        }

        if (!clicked) {
            fail("The wishlist with name '" + wishlistName + "' was not found in the available lists of wishlists");
        }
    }

    public void selectDefaultWishlist() {

        if(webBot.findElements(By.cssSelector(WISHLIST_MENU_ITEM)).get(0).getText().equals("WISH LIST")) {
            webBot.findElements(By.cssSelector(WISHLIST_MENU_ITEM)).get(0).click();
        } else  {
            fail("The default wishlist was not found as the first item in the list of wishlists and therefore was not selected");
        }
    }

    public AddedToWishlistPopup getItemAddedPopupDetails() throws Throwable{

        int timeout = 0;
        while(!isAddedToWishlistPopupDisplayed()) {
            Thread.sleep(100);
            timeout = timeout + 1;
            if (timeout >= 100) {
                fail("Added To Wishlist Popup did not appear.");
            }
        }

        AddedToWishlistPopup popup = new AddedToWishlistPopup();
        popup.setAddedText(webBot.findElement(By.cssSelector(ADDED_TO_WISHLIST_TEXT)).getText());
        popup.setAddedTextLinkDetails(webBot.findElement(By.cssSelector(ADDED_TO_WISHLIST_LINK_DETAILS)).getAttribute("href"));
        popup.setShowWishlistText(webBot.findElement(By.cssSelector(SHOW_WISHLIST_TEXT)).getText());
        popup.setShowWishlistLinkDetails(webBot.findElement(By.cssSelector(SHOW_WISHLIST_LINK_DETAILS)).getAttribute("href"));
        popup.setImageSource(webBot.findElement(By.cssSelector(ADDED_TO_WISHLIST_IMG_SRC)).getAttribute("src"));
        popup.setPopupLinkDetails(webBot.findElement(By.cssSelector(ADDED_TO_WISHLIST_POPUP_LINK_DETAILS)).getAttribute("href"));

        return popup;
    }

    public WebElement getSelectAListButton() {
        return webBot.findElement(By.cssSelector(".add-to-wishlist-button.is-open"));
    }

    public WebElement getAddToListButton() {
        return webBot.findElement(By.cssSelector(".add-to-wishlist-button"));
    }

    public Boolean isAddedToWishlistPopupDisplayed() {
        return webBot.isElementPresent(By.cssSelector(ADDED_TO_WISHLIST_POPUP), 10);
    }

    public void closeAddedToWishlistPopup() throws Throwable {
        webBot.findElement(By.cssSelector(ADDED_TO_WISHLIST_POPUP_CLOSE)).click();
        Thread.sleep(1000);
        if (webBot.isElementPresent(By.cssSelector(ADDED_TO_WISHLIST_POPUP_CLOSE), 4)) {
            fail("The 'Added To Wishlist' popup was still present after attempting to close it");
        }
    }

    public void clickAddedToWishlistPopupLink() throws Throwable {

        if (isAddedToWishlistPopupDisplayed()) {
            webBot.findElement(By.cssSelector(ADDED_TO_WISHLIST_TEXT)).click();
        } else {
            fail("The 'Added To Wishlist' popup was not found");
        }
    }

    public void clickShowWishlistPopupLink() throws Throwable {

        if (isAddedToWishlistPopupDisplayed()) {
            webBot.findElement(By.cssSelector(SHOW_WISHLIST_TEXT)).click();
        } else {
            fail("The 'Added To Wishlist' popup was not found");
        }
    }

    public String getWishlistSelectSizeError() {
        return webBot.findElement(By.cssSelector(WISHLIST_SELECT_SIZE_ERROR)).getText();
    }

    private Select createSelector() {
        WebElement skuWebElement = findSkuWebElement();

        if ((skuWebElement == null) || (!"select".equals(skuWebElement.getTagName()))) {
            return null;
        }

        return new Select(skuWebElement);
    }


    public void clickAddToShoppingBagButton() {
        try{
            WebElement button = webBot.findElement(By.xpath(".//*[@id='button-holder']/div[1]/input"));
            button.click();
        }catch (PageElementNotFoundException e){
            throw new RuntimeException("Add To Basket Button Not Available (product may be out of stock)");
        }
    }

    private WebElement findSkuWebElement() {
        // TODO webBot.findElementByIdOrName...
        if (RegionEnum.AM.name().equals(getRegion())) {
            return webBot.findElement(By.name(SKU_NAME_OR_ID));

        }

        return webBot.findElement(By.id(SKU_NAME_OR_ID));
    }


    public String getDesignerName() {
        WebElement designerName = webBot.findElement(DESIGNER_NAME_ELEMENT);
        return designerName.getText();
    }


    public String getProductSize(String sku) {
        Boolean isMultipleSized = isMultipleSizedProduct();
        if (isMultipleSized == null) {
            return null;
        }
        if (isMultipleSized) {
            Select sizeSelector = createSelector();
            for (int i = 1; i < sizeSelector.getOptions().size(); i++) {
                if(sizeSelector.getOptions().get(i).getAttribute("value").contains(sku)) {
                    return sizeSelector.getOptions().get(i).getText().trim();
                }
            }
        } else {
            String singleSize = getSingleSizedSKU();
            if (sku != null) {
                return singleSize;
            }
        }

//        WebElement productSize = webBot.findElement(By.xpath(".//*[@id='sku'] "));
        return null;
    }

    public Map<String,String> getAllSizeInfo(){
        Boolean isMultipleSized = isMultipleSizedProduct();
        if (isMultipleSized == null) {
            return null;
        }
        Map<String,String> skuToSizeMap = new HashMap<String, String>();
        if (isMultipleSized) {
            Select sizeSelector = createSelector();
            for (int i = 1; i < sizeSelector.getOptions().size(); i++) {
                WebElement next = sizeSelector.getOptions().get(i);
                skuToSizeMap.put(
                        next.getAttribute("value").trim(),
                        next.getText().trim());
            }
        } else {
            String singleSize = getSingleSizedSKU();
            if (singleSize != null) {
                skuToSizeMap.put(singleSize, singleSize);
            }
        }

        return skuToSizeMap;
    }

    public void go(Product inStockProduct) {
        String pid = inStockProduct.getPid();
        goToProduct(pid);
    }

    // Note: this will only add products that are contained in product details page
    public void addIntoShoppingBag(Set<Product> products) {
        if (isMultipleSizedProduct()) {
            addMultipleSizedProductsIntoShoppingBag(products);
        } else {
            attemptToAddOneSizeProductsIntoShoppingBag(products);
        }
    }

    public boolean isInStock() {
        WebElement firstButton = webBot.findElement(By.xpath(".//*[@id='button-holder']/div[1]"));

        try {
            // already found the parent element, so don't want to wait
            firstButton.findElement(By.tagName("input"));
        }
        catch (NoSuchElementException e)
        {
            return false;
        }

        return true;
    }

    private void addMultipleSizedProductsIntoShoppingBag(Set<Product> products) {
        WebElement skuWebElement = findSkuWebElement();
        Select sizeSelector = new Select(skuWebElement);

        for (Product product : products) {
            for (WebElement option : sizeSelector.getOptions()) {
                if (option.getAttribute("value").equals(product.getSku())) {
                    sizeSelector.selectByVisibleText(option.getText());

                    clickAddToShoppingBagButton();

                    // wait for minibag to appear (1s) and disappear (3s)
                    WaitUtil.waitFor(4500);
                }
            }

        }
    }

    private void attemptToAddOneSizeProductsIntoShoppingBag(Set<Product> products) {
        WebElement skuWebElement = findSkuWebElement();
        String acceptingSku = skuWebElement.getAttribute("value");

        for (Product product : products) {
            if (acceptingSku.equals(product.getSku())) {
                clickAddToShoppingBagButton();
            }
        }
    }


    public List<String> getSKUs() {
        List<String> skus = new ArrayList<String>();

        if (isMultipleSizedProduct()) {
            Select sizeSelector = createSelector();
            for (int i = 1; i < sizeSelector.getOptions().size(); i++) {
                skus.add(sizeSelector.getOptions().get(i).getAttribute("value"));
            }
        } else {
            String sku = getSingleSizedSKU();
            if (sku != null) {
                skus.add(sku);
            }
        }

        return skus;
    }

    /*
     * Will ignore invalid sku
     */
    public void selectSKU(String sku) {
        if (isMultipleSizedProduct()) {
            Select sizeSelector = createSelector();

            for (WebElement option : sizeSelector.getOptions()) {
                if (option.getAttribute("value").equals(sku)) {
                    sizeSelector.selectByVisibleText(option.getText());
                    break;
                }
            }
        }
    }

    private boolean isMultipleSizedProduct() {
        WebElement skuWebElement = findSkuWebElement();
        return "select".equals(skuWebElement.getTagName());
    }

    private String getSingleSizedSKU() {
        WebElement skuWebElement = findSkuWebElement();
        if (skuWebElement != null) {
            return skuWebElement.getAttribute("value");
        }
        return null;
    }

    public String getPid() {
        String currentUrl = webBot.getCurrentUrl();
        int lastSlashIndex = currentUrl.lastIndexOf("/");
        int questionMarkIndex = currentUrl.lastIndexOf("?");

        if (questionMarkIndex!=-1)
        {
            return currentUrl.substring(lastSlashIndex+1,questionMarkIndex);
        }
        else {
            return currentUrl.substring(lastSlashIndex+1);
        }
    }

    public boolean isTheLocaleLinkDisplayed() {
        int numberOfLocaleLinks = 1;
        for (WebElement link : webBot.findElements(LOCALE_LINK_ELEMENT)) {
            try {
                if (link.getAttribute("hreflang").contains("en-gb") ||
                        link.getAttribute("hreflang").contains("en-us") ||
                        link.getAttribute("hreflang").contains("fr-fr") ||
                        link.getAttribute("hreflang").contains("zn-ch") ||
                        link.getAttribute("hreflang").contains("de-de") ||
                        link.getAttribute("hreflang").contains("x-default")){

                    //assertThat(link.getAttribute("href"), JUnitMatchers.containsString(webBot.getCurrentUrl()));
                    assertTrue(link.getAttribute("href").contains("http://www.net-a-porter.com/"));
                    assertThat(link.getAttribute("rel"), is("alternate"));

                    numberOfLocaleLinks++;
                }
            }

            catch (PageElementNotFoundException e){
            }
        }

        //this assert will fail if there are less that 6 locale links in the head of the page
        assertThat(numberOfLocaleLinks, is(6));
        return true;
    }


    public boolean isTheCanonicalLinkDisplayed() {

        List<WebElement> linkList = webBot.findElements(CANONICAL_LINK_ELEMENT);
                for (WebElement webElement : linkList) {
                    if (webElement.getAttribute("rel").contains("canonical")){
                        return true;
                    }
                }
        return false;
    }


}
