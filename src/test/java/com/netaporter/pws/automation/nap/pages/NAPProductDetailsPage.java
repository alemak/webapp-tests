package com.netaporter.pws.automation.nap.pages;

import com.netaporter.pws.automation.shared.pages.IProductDetailsPage;
import com.netaporter.pws.automation.shared.pojos.Product;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.MenuItem;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.NavMenu;
import com.netaporter.pws.automation.shared.utils.WaitUtil;
import com.netaporter.test.utils.enums.RegionEnum;
import com.netaporter.test.utils.pages.driver.WaitTime;
import com.netaporter.test.utils.pages.driver.WebDriverUtil;
import com.netaporter.test.utils.pages.exceptions.PageElementNotFoundException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.openqa.selenium.By.cssSelector;

@Component
@Scope("cucumber-glue")
public class NAPProductDetailsPage extends AbstractNapPage implements IProductDetailsPage {

    static Logger logger  = Logger.getLogger(NAPProductDetailsPage.class);
    private static final String PAGE_NAME = "Product Details";
    private static final String PATH = "/product";

    private By LOCALE_LINK_ELEMENT = By.cssSelector("html>head>link");
    private By TELL_A_FRIEND_SUBMISSION_SUCCESSFUL_MESSAGE = By.className("email-thankyou");
    private By TELL_A_FRIEND_EMAIL_ADDRESS_LOCATOR = By.id("tell-friend-email");
    private By TELL_A_FRIEND_EMAIL_SUBJECT_LOCATOR = By.id("tell-friend-subject");
    private By TELL_A_FRIEND_SUBMIT_BUTTON = By.id("emailFriendSubmit");
    private By TELL_A_FRIEND_OVERLAY_LOCATOR = By.id("tell-a-friend-overlay");
    private By TELL_A_FRIEND_BUTTON_LOCATOR = By.id("tell-a-friend-link");
    private By TELL_YOUR_NAME_ELEMENT = By.id("tell-your-name");
    private By TELL_YOUR_EMAIL_ELEMENT = By.id("tell-your-email");
    private By ADD_TO_BAG_BUTTON_LOCATOR = By.className("add-to-bag");
    private By PID_ELEMENT_KEY = By.className("help-contact");
    private By SOLD_OUT_MESSAGE_LOCATOR = By.className("error-message");
    private By OVERLAY_IMAGE_HOLDER_LOCATOR = By.id("overlay_image_holder");
    private By ADD_TO_BAG_AREA_LOCATOR = By.xpath(".//*[@id='button-holder']/div[1]/input");
    private By VIEW_FULL_SIZE_IMAGE_LOCATOR = By.id("full-size-image-link");
    private By PROCEED_TO_PURCHASE = By.cssSelector(".proceed-to-purchase");

    private By BACK_TO_RESULTS_LOCATOR = By.className("back-link");
    private By ADD_TO_WISHLIST_BUTTON_LOCATOR = By.cssSelector(".product-wishlist .secondary-button");
    private By SIZE_ELEMENT = By.xpath(".//*[@id='sku'] ");
    private By SKU_AM_LOCATOR = By.name("sku");
    private By SKU_LOCATOR = By.id("sku");
    private By PRICE_LOCATOR = By.cssSelector("span[itemprop='price']");
    private By WAS_PRICE_LOCATOR = By.cssSelector("span[itemprop='minPrice'].was");
    private By NOW_PRICE_LOCATOR = By.cssSelector("span[itemprop='price'].now");

    private By SHOPPING_BAG_HEADER_ELEMENT = By.id("header-shopping-bag");
    private By ACCORDIONS_KEY = By.xpath(".//*[@id='product-details-container']/ul/li");
    private By SOLD_OUT_PRODUCT_DETAIL = By.id("sold-out-product-details");
    private By SOLD_OUT_VIEW_PRODUCT_DETAIL_ELEMENT = By.id("view-pd");
    private By SOLD_OUT_HIDE_PRODUCT_DETAIL_ELEMENT  =By.id("hide-pd");
    private By SOLD_OUT_IMAGE_ELEMENT  = By.className("sold-out-pic");
    private By SOLD_OUT_TITLE_ELEMENT  = By.xpath(".//*[@id='sold-out-product-details']/div/div[2]/h1");
    private By SOLD_OUT_DESIGNER_ELEMENT  = By.xpath(".//*[@id='sold-out-product-details']/div/div[2]/h2/a");
    private By SOLD_OUT_PRICE_ELEMENT  = By.className("price");
    private By SOLD_OUT_MESSAGE_ELEMENT  = By.className("sold-out-message");
    private By SOLD_OUT_ALTERNATIVE_SUGGESTIONS_ELEMENT  = By.className("alternative-suggestions");
    private By SOLD_OUT_YMAL_CONTAINER_ELEMENT  = By.id("ymal-pids");
    private By SOLD_OUT_YMAL_PIDS_ELEMENT  = By.className("sold-out-product-holder");
    private By SOLD_OUT_YMAL_DESIGNER_ELEMENT  = By.className("sold-out-product-designer-name");
    private By SOLD_OUT_YMAL_TITLE_ELEMENT  = By.className("sold-out-product-title");
    private By SOLD_OUT_YMAL_PRICE_ELEMENT  = By.cssSelector("div.sold-out-product-price.product-feature-price");
    private By SOLD_OUT_YMAL_IMAGE_ELEMENT  = By.className("sold-out-product-image");

    private By MINI_SHOPPING_BAG_LOCATOR = By.className("shopping-bag-container");
    private By PRODUCT_LINK_IN_MINI_SHOPPING_BAG_LOCATOR = By.className("product-page-link");

    //path to shipping restriction name
    private static By SHIPPING_RESTRICTION_ELEMENT = By.xpath(".//*[@id='shipping-restriction']/a");

    //path to product designer name
    private By DESIGNER_NAME_ELEMENT = By.cssSelector("#product-details h2 a");

    private By PRODUCT_DESCRIPTION_ELEMENT = By.cssSelector("#product-details h1");
    private By CANONICAL_LINK_ELEMENT= By.cssSelector("html>head>link");

    private By OUTFIT_MODULE_NO_CAROUSEL_LOCATOR = By.cssSelector(".outfit-pid-holder.product-feature-product");
    private By OUTFIT_MODULE_CAROUSEL_LOCATOR = By.cssSelector(".outfit-pid-holder.product-feature-product.swiper-slide-visible");
    private By OUTFIT_MODULE_CONTAINER = By.id("om-carousel-0");
    private By YMAL_MODULE_LOCATOR = By.cssSelector("#ymal div.product-feature div.product-feature-products div.product-feature-product");
    private By CLOTHING_CATEGORY_WISHLIST_LOCATOR = By.className("item-container-nap");
    private By PRINTED_VOUCHER_LINK = By.xpath(".//*[@id='landing']/div[1]/div[2]/a");
    private By VIRTUAL_VOUCHER_LINK = By.xpath(".//*[@id='landing']/div[1]/div[3]/a");
    private By VOUCHER_PRICE_LOCATOR = By.id("sku");
    private By VOUCHER_ADD_TO_BAG = By.cssSelector("#voucher-form .primary-button");
    private By VOUCHER_PROCEED_TO_PURCHASE = By.cssSelector("#button .primary-button");
    private By VOUCHER_SHOPPING_BAG_TITLE = By.xpath(".//*[@id='shopping_bag_middle']/table/tbody/tr[1]/td[2]/a/h2");


    private By ADD_TO_BAG = By.id("addToShoppingBag");
    private By MINI_SHOPPING_BAG_CHECKOUT = By.cssSelector("a.primary-button.checkout");
    private By MINI_SHOPPING_BAG_PRODUCT_IMAGE = By.cssSelector("a.product-page-link img");
    private By MINI_SHOPPING_BAG_DESIGNER = By.className("designer");
    private By MINI_SHOPPING_BAG_TITLE = By.className("product-title");
    private By MINI_SHOPPING_BAG_PRICE = By.className("price");
    private By HEADER_SHOPPING_BAG = By.id("header-shopping-bag");
    private By SIGN_IN_LINK = By.cssSelector("#header-sign-in a[target='_top']");
    private By NAP_LOGO = By.cssSelector("#header-mid>a>img");


    public NAPProductDetailsPage() {
        super(PAGE_NAME, PATH);
    }

    public NAPProductDetailsPage(String pageName, String currentUrl, WebDriverUtil webBot) {
        this(pageName, currentUrl);
        this.webBot = webBot;
    }

    public NAPProductDetailsPage(String pageName, String currentUrl) {
        super(pageName, currentUrl);
    }

    public enum StockLevel {
        OUT_OF_STOCK, LOW_STOCK, IN_STOCK, COMING_SOON, ONE_LEFT
    }

    public enum StockType {
        MULTIPLE_SIZES, ONE_SIZE
    }

    @Override
    public boolean isPageRegionalised() {
        return false;
    }

    public void goToProductHTTP(String productId) {
        String originalPath = getPath();
        webBot.setBaseUrl(webBot.getBaseUrl().replace("https:","http:"));
        setPath(originalPath + "/" + productId);
        go();
        setPath(originalPath);
    }

    public void goToProduct(String productId) {
        String originalPath = getPath();
        //substring to remove an extra / in the url
        setPath((getPath() + "/" + productId).substring(1));
        logger.debug("Going to product with PID " + productId);
        go();
        setPath(originalPath);
        closeDontMissOutPopup();
    }

    public String getListedPriceString() {
        List <WebElement> priceElement = webBot.findElements(PRICE_LOCATOR);
        int size = priceElement.size();
        return priceElement.get(size-1).getText();
    }

    public String getListedDiscountedPrice() {
        WebElement priceElement = webBot.findElement(NOW_PRICE_LOCATOR);

        return priceElement.getText().replace("Now ", "");
    }

    public String getListedOriginalPrice() {
        WebElement priceElement = webBot.findElement(WAS_PRICE_LOCATOR);

        return priceElement.getText().replace("Was ", "");
    }

    public String getWishlistPopupListedPrice() {
        String price = webBot.findElement(By.cssSelector(".popup-window .price")).getText();

        if (price.contains("Now")) {
            // Item discounted
            price = price.replace("Was ", "");
            price = price.replace("Now ", "");
            String[] priceSplit = price.split(" ");
            price = priceSplit[0]+" "+priceSplit[1];
        }
        return price;
    }

    @Override
    public Product addIntoShoppingBag(String inStockSKU) {
        selectSKU(inStockSKU);

        Integer originalShoppingBagQuantity = getOriginalShoppingBagQuantity();

        clickAddToShoppingBagButton();

        if (!isNumberInShoppingBagIconUpdated(originalShoppingBagQuantity))
            return null;

        // TODO don't need to create a complete Product yet as sku is sufficient for verification
        return new Product(null, inStockSKU);
    }

	/*public void addProductToShoppingBagWithNoResult(){
		getAnInStockItemSkuAndSelectSizeIfRequired();
        clickAddToShoppingBagButton();
	}*/

    private Integer getOriginalShoppingBagQuantity() {
        // Note: try five times as shopping bag item count could be empty sometimes
        for (int i = 0; i < 5; i++) {
            try {
                WebElement shoppingBagBasketElement = webBot.findElement(SHOPPING_BAG_HEADER_ELEMENT);
                return Integer.parseInt(shoppingBagBasketElement.getText());
            } catch (NumberFormatException e) {
                System.out.println("Getting item count in shopping bag error: " + e);
                System.out.println("Will try again in two seconds.");
                WaitUtil.waitFor(2000);
            } catch (Exception e2) {
                throw new IllegalStateException("Cannot get shopping bag item count", e2);
            }
        }

        throw new IllegalStateException("Cannot get shopping bag item count");
    }

    private boolean isNumberInShoppingBagIconUpdated(final Integer originalShoppingBagQuantity) {
        int expectedQuantity = originalShoppingBagQuantity + 1;

        try {
            webBot.findElement(By.xpath("//a[@id='header-shopping-bag' and text() = '" + expectedQuantity + "']"), WaitTime.TEN);
        }
        catch (PageElementNotFoundException e) {
            return false;
        }
        return true;
    }


    @Override
    public void addIntoWishlist(String inStockSKU) {
        selectSKU(inStockSKU);
        webBot.click(ADD_TO_WISHLIST_BUTTON_LOCATOR);
    }

    public void clickAddToWishList() {
        webBot.click(ADD_TO_WISHLIST_BUTTON_LOCATOR);
    }

    public void clickAddToWishListOnPopup()throws Throwable {
        Thread.sleep(2000);
        waitForElementToBeVisible(".popup-window .confirm.primary-button");
        webBot.findElement(By.cssSelector(".popup-window .confirm.primary-button")).click();
    }


    private Select createPopupSelector() throws Throwable{
        int i=0;
        while (!(webBot.findElement(By.cssSelector(".popup-window select")).getText().contains("Choose Your Size")) && i<10) {
            Thread.sleep(500);
            i++;
        }
        WebElement popupSkuWebElement = webBot.findElement(By.cssSelector(".popup-window select"));
        return new Select(popupSkuWebElement);
    }

    public String wishlistPopupErrorMessage() {
        if(webBot.exists(null, cssSelector(".popup-window .error"))) {
            return webBot.findElement(By.cssSelector(".popup-window .error")).getText();
        }
        return "NO ERROR";
    }

    public Boolean wishlistPopupHasAppeared() throws Throwable{
        waitForElementToExist(By.cssSelector(".add-to-wishlist-popup.is-open"));
        //waitForElementToNotExist(By.cssSelector(".add-to-wishlist-popup.is-open .dropdown.is-loading"));
        waitForWishlistOptionsToBePopulated();
        return webBot.exists(null, cssSelector(".add-to-wishlist-popup.is-open .popup-window"));
    }

    private void waitForWishlistOptionsToBePopulated() throws InterruptedException {
        for (int i=0; i<10; i++) {
            try {
                List<WebElement> wishlistList = webBot.findElements(By.cssSelector(".wishlist-dropdown .secondary-button"));
                if(wishlistList.size() > 0) {
                    if(!wishlistList.get(0).getText().isEmpty()) {
                        return;
                    }
                }
//                // For single size item, the selector is removed
//                if (! webBot.exists(null, By.cssSelector(".add-to-wishlist-popup .popup-window select"))) {
//                    return;
//                }
//
//                if (! new Select(webBot.findElement(By.cssSelector(".add-to-wishlist-popup .popup-window select[name=sku]"))).getOptions().isEmpty()) {
//                    return;
//                }
            } catch (StaleElementReferenceException sere) {
                // Lazily loaded panel... so ignore this and try again
                sere.printStackTrace();
            }
            Thread.sleep(1000);
        }
        fail("Timed out waiting for add to wishlist popup wishlist list to populate");
    }

    public String getWishlistPopupHeader() throws Throwable {
        for (int i=0; i<10; i++) {
            try {
               return webBot.findElement(By.cssSelector(".popup-window .header h3")).getText();
            } catch (StaleElementReferenceException sere) {
                // Popup is lazily loaded, so try again
                sere.printStackTrace();
                Thread.sleep(200);
            }
        }
        return null;
    }

    public Boolean wishlistPopupHasDisappeared() throws Throwable{
        waitForElementToNotExist(By.cssSelector(".add-to-wishlist-popup.is-open"));
        return !(webBot.exists(null, cssSelector(".add-to-wishlist-popup.is-open .popup-window")));
    }

    public String getWishlistPopupDesignerName() throws InterruptedException {
        for (int i=0; i<10; i++) {
            try {
                return webBot.findElement(By.cssSelector(".popup-window .designer")).getText();
            } catch (StaleElementReferenceException sere) {
                // Popup is lazily loaded, so try again
                sere.printStackTrace();
                Thread.sleep(200);
            }
        }
        return null;
    }

    public String getWishlistPopupProductDescription() throws InterruptedException {
        for (int i=0; i<10; i++) {
            try {
                return webBot.findElement(By.cssSelector(".popup-window .name")).getText();
            } catch (StaleElementReferenceException sere) {
                // Popup is lazily loaded, so try again
                sere.printStackTrace();
                Thread.sleep(200);
            }
        }
        return null;
    }

    public String getProductDescription() {
        WebElement productDescription = webBot.findElement(PRODUCT_DESCRIPTION_ELEMENT);
        return productDescription.getText();
    }

    public List<WebElement> getPopupWishlistMenuElementsFromPage() throws Throwable {
        Thread.sleep(1000);
        if(!(webBot.exists(null, By.cssSelector(".popup-window div.wishlist-dropdown.is-open")))) {
            webBot.findElement(By.cssSelector(".popup-window .wishlist-dropdown")).click();
        }

        int i=0;
        while (!(webBot.findElements(By.cssSelector(".popup-window li")).get(0).getText().contains("Wish List")) && i<10) {
            //while (!(webBot.findElement(By.cssSelector(".wishlist-dropdown .secondary-button")).getText().contains("Wish List")) && i<10) {
            Thread.sleep(1000);
            i++;
        }
        List<WebElement> wishlistMenu = webBot.findElements(By.cssSelector(".popup-window li"));

        return wishlistMenu;
    }

    public void enterNewWishlistNameInPopup(String newName) throws Throwable {
        waitForElementToExist(cssSelector(".popup-window .create-wishlist-menu-item.is-expanded"));
        webBot.findElement(By.cssSelector(".popup-window .create-wishlist-menu-item input")).sendKeys(newName);
    }

    public void pressCreateButtonOnWishlistPopup() throws Throwable {
        webBot.findElement(By.cssSelector("li.create-wishlist-menu-item form button")).click();
        waitForElementToNotExist(cssSelector(".dropdown.is-open"));
    }

    public void clickCloseButtonOnWishlistPopup() {
        webBot.findElement(By.cssSelector(".popup-window .close")).click();
    }

    public Boolean isSizeSelectorInWishlistPopup() {
        return webBot.exists(null, By.cssSelector(".popup-window select"));
    }

    public Boolean isProductImagePresentInWishlistPopup(String pid) throws InterruptedException {
        for (int i=0; i<10; i++) {
            try {
                return webBot.findElement(By.cssSelector(".popup-window .image img")).getAttribute("src").contains(pid);
            } catch (StaleElementReferenceException sere) {
                // Popup is lazily loaded, so try again
                sere.printStackTrace();
                Thread.sleep(200);
            }
        }
        fail("Failed to get src from image");
        return null;
    }

    /**
     * Gets the wishlist popup wishlist menu from the page.
     */
    public NavMenu getNavMenu() {
        List<WebElement> elements = getNavMenuElements();
        List<MenuItem> menuItems = new ArrayList();

        for (WebElement menuItemElem: elements) {

            MenuItem menuItem = new MenuItem();

            menuItemElem.getAttribute("class").contains("is-selected");
            boolean ticked = menuItemElem.getCssValue("background").contains("/tick.png");
            menuItem.setTicked(ticked);

            WebElement anchor = menuItemElem.findElement(cssSelector("a, span"));

            menuItem.setText(anchor.getText());

            String href = anchor.getAttribute("href");
            menuItem.setHref(href);
            menuItem.setWishlistId(StringUtils.substringAfterLast(href, "/"));

            menuItems.add(menuItem);
        }

        return new NavMenu(menuItems);
    }

    /**
     * Gets a list of WebElements, one for each item in the wishlist popup wishlist menu
     */
    public List<WebElement> getNavMenuElements() {
        //Click menu to make items visible
        if(!webBot.exists(null, By.cssSelector(".popup-window .wishlist-dropdown.is-open"))) {
            webBot.findElement(By.cssSelector(".popup-window .wishlist-dropdown")).click();
        }
        return webBot.findElements(By.cssSelector("li.menu-item, li.menu-form-item"));
    }

    /**
     * Gets the Nav Menu from wishlist popup.
     */
    public NavMenu returnNavMenu() throws Throwable {
        int i=0;
        while (!(webBot.findElements(By.cssSelector(".popup-window li")).get(0).getText().contains("Wish List")) && i<10) {
            Thread.sleep(1000);
            i++;
        }

        List<WebElement> elements = getNavMenuElements();
        List<MenuItem> menuItems = new ArrayList();

        for (WebElement menuItemElem: elements) {

            MenuItem menuItem = new MenuItem();

            boolean ticked = menuItemElem.getAttribute("class").contains("is-selected");
            menuItem.setTicked(ticked);

            boolean isPrivate = menuItemElem.getAttribute("class").contains("is-not-shared");
            menuItem.setPrivate(isPrivate);

            WebElement anchor = menuItemElem.findElement(cssSelector("a, span"));

            menuItem.setText(anchor.getText());

            String href = anchor.getAttribute("href");
            menuItem.setHref(href);
            menuItem.setWishlistId(StringUtils.substringAfterLast(href, "/"));

            menuItems.add(menuItem);
        }
        return new NavMenu(menuItems);
    }

    public void clickViewWishList() throws Throwable {
        waitForElementToExist(By.cssSelector("#button-holder > .item-added-info"));
        List<WebElement> viewWishListLink = webBot.findElements(By.cssSelector("#button-holder > .item-added-info"));
        if(viewWishListLink.size() > 1) {
            fail("View Wish List link shows more than once");
        } else {
            viewWishListLink.get(0).click();
        }
    }

    public void selectPopupWishlistMenuItem(String menuItemString) throws Throwable {
        List<WebElement> menuElements = getPopupWishlistMenuElementsFromPage();
        for (int i=0 ; i<10 ; i++) {
            for(WebElement menuItem: menuElements) {
                if (menuItem.getText().equals(menuItemString)) {
                    menuItem.click();
                    return;
                }
            }
            Thread.sleep(1000);
            menuElements = getPopupWishlistMenuElementsFromPage();
        }
        fail("Did not find menu item "+menuItemString);
    }

    public String checkWishlistPopupSelectedWishlistName() {
        return webBot.findElement(By.cssSelector(".popup-window .wishlist-dropdown .toggle")).getText();
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
            webBot.click(ADD_TO_BAG_BUTTON_LOCATOR);
            // As this is AJAXified this can take a little time to finish
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }catch (NullPointerException e){
            throw new RuntimeException("Add To Basket Button Not Available (product may be out of stock)");
        }
    }

    private WebElement findSkuWebElement() {
        if (RegionEnum.AM.name().equals(getRegion())) {
            if (webBot.exists(null, SKU_AM_LOCATOR)) {
                return webBot.findElement(SKU_AM_LOCATOR);
            } else {
                return null;
            }
        }

        if (webBot.exists(null, SKU_LOCATOR)) {
            return webBot.findElement(SKU_LOCATOR);
        }
        return null;
    }

    public boolean hasShippingRestrictionWarningMsg() {
        return doesElementExist(SHIPPING_RESTRICTION_ELEMENT);
    }

    public String getDesignerName() {
        WebElement designerName = webBot.findElement(DESIGNER_NAME_ELEMENT);
        return designerName.getText();
    }

    public String getProductSize() {
        WebElement productSize = null;

        try{
            productSize = webBot.findElement(SIZE_ELEMENT, 2);
        }
        catch(PageElementNotFoundException e) {
            fail("Could not get product sizes. Product may no longer be available");
        }
        return productSize.getText();
    }

    public String getProductSize(String sku) {
        Boolean multiSized = isMultipleSizedProduct();
        if (multiSized == null) {
            return null;
        }
        if (multiSized) {
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
        Boolean multiSized = isMultipleSizedProduct();
        Assert.assertNotNull("Could not find sizes on page", multiSized);

        if (multiSized) {
            addMultipleSizedProductsIntoShoppingBag(products);
        } else {
            attemptToAddOneSizeProductsIntoShoppingBag(products);
        }
    }

    public boolean isInStock() {
        try {
            webBot.findElement(ADD_TO_BAG_AREA_LOCATOR, WaitTime.FOUR);
        }
        catch (PageElementNotFoundException e)
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

        Boolean multiSized = isMultipleSizedProduct();
        if (multiSized==null){
            logger.info("Could not find skus on PDP, product may be sold out.");
            return skus;
        }
        if (multiSized) {
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

    public void addSKUToShoppingBag(String sku) {
        selectSKU(sku);

        clickAddToShoppingBagButton();

        // wait for minibag to appear (1s) and disappear (3s)
        WaitUtil.waitFor(5500);
    }

    /*
     * Will ignore invalid sku
     */
    public void selectSKU(String sku) {
        Boolean multiSized = isMultipleSizedProduct();
        Assert.assertNotNull("Could not find sizes on page", multiSized);

        if (multiSized) {
            Select sizeSelector = createSelector();

            for (WebElement option : sizeSelector.getOptions()) {
                if (option.getAttribute("value").equals(sku)) {
                    sizeSelector.selectByVisibleText(option.getText());
                    return;
                }
            }
            fail("Could not find sku " + sku + " in selector");
        }
    }

    public List<String> getProductSizes() {
        List<String> sizes = new ArrayList<String>();

        Boolean multiSized = isMultipleSizedProduct();
        Assert.assertNotNull("Could not find sizes on page", multiSized);

        if (multiSized) {
            Select sizeSelector = createSelector();
            for (int i = 1; i < sizeSelector.getOptions().size(); i++) {
                sizes.add(sizeSelector.getOptions().get(i).getText());
            }
        } else {
            String sku = getSingleSizedSKU();
            if (sku != null) {
                sizes.add(sku);
            }
        }

        return sizes;
    }
    public List<String> getSoldOutSizes() {
        List<String> sizes = new ArrayList<String>();

        Boolean multiSized = isMultipleSizedProduct();
        Assert.assertNotNull("Could not find sizes on page", multiSized);

        if (multiSized) {
            Select sizeSelector = createSelector();
            for (int i = 1; i < sizeSelector.getOptions().size(); i++) {
                if (sizeSelector.getOptions().get(i).getAttribute("value").contains("so_")){
                    String[] soldOutPid = sizeSelector.getOptions().get(i).getText().split("-");
                    sizes.add(soldOutPid[0]);
                }
            }
        } else {
            String sku = getSingleSizedSKU();
            if (sku != null) {
                sizes.add(sku);
            }
        }
        return sizes;
    }

    public List<String> getLowStockSizes() {
        List<String> sizes = new ArrayList<String>();

        Boolean multiSized = isMultipleSizedProduct();
        Assert.assertNotNull("Could not find sizes on page", multiSized);

        if (multiSized) {
            Select sizeSelector = createSelector();
            for (int i = 1; i < sizeSelector.getOptions().size(); i++) {
                if (sizeSelector.getOptions().get(i).getText().contains("low stock")){
                    String[] lowStockPids = sizeSelector.getOptions().get(i).getText().split("-");
                    sizes.add(lowStockPids[0]);
                }
            }
        } else {
            String sku = getSingleSizedSKU();
            if (sku != null) {
                sizes.add(sku);
            }
        }
        return sizes;
    }

    public List<String> getOneLeftSizes() {
        List<String> sizes = new ArrayList<String>();

        Boolean multiSized = isMultipleSizedProduct();
        Assert.assertNotNull("Could not find sizes on page", multiSized);

        if (multiSized) {
            Select sizeSelector = createSelector();
            for (int i = 1; i < sizeSelector.getOptions().size(); i++) {
                if  (sizeSelector.getOptions().get(i).getText().contains("only 1 left")){
                    String[] oneStockPids = sizeSelector.getOptions().get(i).getText().split("-");
                    sizes.add(oneStockPids[0]);
                }
            }
        } else {
            String sku = getSingleSizedSKU();
            if (sku != null) {
                sizes.add(sku);
            }
        }
        return sizes;
    }

    public List<String> getComingSoonSizes() {
        List<String> sizes = new ArrayList<String>();

        Boolean multiSized = isMultipleSizedProduct();
        Assert.assertNotNull("Could not find sizes on page", multiSized);

        if (multiSized) {
            Select sizeSelector = createSelector();
            for (int i = 1; i < sizeSelector.getOptions().size(); i++) {
                if  (sizeSelector.getOptions().get(i).getText().contains("Coming")){
                    String[] comingSoonPids = sizeSelector.getOptions().get(i).getText().split("-");
                    sizes.add(comingSoonPids[0]);
                }
            }
        } else {
            String sku = getSingleSizedSKU();
            if (sku != null) {
                sizes.add(sku);
            }
        }
        return sizes;
    }

    public String checkPopupWhichSkuSelected(String sku) throws Throwable {
        Boolean multiSized = isMultipleSizedProduct();
        Assert.assertNotNull("Could not find sizes on page", multiSized);

        if (multiSized) {
            Select sizeSelector = createPopupSelector();
            return sizeSelector.getFirstSelectedOption().getAttribute("value");
        } else {
            return sku;
        }
    }

    public void selectSizeBySkuInWishlistPopup(String sku) throws Throwable {
        Boolean multiSized = isMultipleSizedProduct();
        Assert.assertNotNull("Could not find sizes on page", multiSized);

        if (multiSized) {
            Select sizeSelector = createPopupSelector();
            sizeSelector.selectByValue(sku);
        } else {
            return;
        }
    }

    private Boolean isMultipleSizedProduct() {
        WebElement skuWebElement = findSkuWebElement();
        if (skuWebElement == null) {
            return null;
        }
        return "select".equals(skuWebElement.getTagName());
    }

    private String getSingleSizedSKU() {
        WebElement skuWebElement = findSkuWebElement();
        if (skuWebElement != null) {
            return skuWebElement.getAttribute("value");
        }
        return null;
    }

    public String getPidFromUrl() {
        String currentUrl = webBot.getCurrentUrl();
        int lastSlashIndex = currentUrl.lastIndexOf("/");
        int questionMarkIndex = currentUrl.lastIndexOf("?");

        if (questionMarkIndex != -1) {
            return currentUrl.substring(lastSlashIndex + 1, questionMarkIndex);
        } else {
            return currentUrl.substring(lastSlashIndex + 1);
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

                    assertTrue(link.getAttribute("href").contains("http://www.net-a-porter.com/"));
                    assertThat(link.getAttribute("rel"), is("alternate"));

                    numberOfLocaleLinks++;
                }
            } catch (PageElementNotFoundException ignored){
            }
        }

        //this assert will fail if there are less than 6 locale links in the header of the page
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

    public int getPidFromPage() {
        WebElement contactBlock = webBot.findElement(PID_ELEMENT_KEY, WaitTime.FOUR);
        String textCode = contactBlock.getText();
        Scanner scanner = new Scanner(textCode);
        while (scanner.hasNext()) {
            if (scanner.hasNextInt())
                return scanner.nextInt();
            scanner.next();
        }
        fail("Couldn't get pid from string: "+textCode);
        return 0;
    }

    public boolean isBackToResultsLinkDisplayed() throws InterruptedException {
        //back to results link is displayed with a bit of a delay
       webBot.waitForJQueryCompletion();
          try {
             return webBot.findElement(BACK_TO_RESULTS_LOCATOR, 4).isDisplayed();
          }
          catch (PageElementNotFoundException e) {
              return false;
          }
    }

    public  void clickViewWishlist() {
        WebElement button = webBot.findElement(By.xpath(".//*[@id='button-holder']/a"));
        button.click();

    }

    public void clickProceedToPurchase() {
        WebElement button = webBot.findElement(PROCEED_TO_PURCHASE, WaitTime.SEVEN);
        button.click();
    }

    public void clickBackToResultsLink() throws InterruptedException {
        webBot.executeJavascript("window.testValue=1");
        webBot.waitForJQueryCompletion();
        WebElement backToResultsButton = null;
        try {
            backToResultsButton = webBot.findElement(BACK_TO_RESULTS_LOCATOR, WaitTime.SEVEN);
        }
        catch (PageElementNotFoundException e) {
            fail("Could not click on Back_to_Results link. It may not have been visible.");
        }
        backToResultsButton.click();
        //make sure webdriver waits for the page to load before going to the next step
        for (int i=0;i<10;i++) {
            if (webBot.executeJavascript("window.testValue") == null)
                return;
            Thread.sleep(500);
        }
    }

    public void clickSizeDropdown() {
        webBot.click(findSkuWebElement());
    }

    public String getAccordionClass(String accordionName) {
        List<WebElement> links = webBot.findElements(ACCORDIONS_KEY, WaitTime.FOUR);

        for (WebElement link : links) {
            WebElement elementName = link.findElement(By.xpath("span/a"));
            if (elementName.getText().equalsIgnoreCase(accordionName)){
                return link.getAttribute("class");
            }
        }
        throw new InvalidElementStateException("Could not find accordion class for: "+accordionName);
    }

    public void clickViewFullSizeImage() {
        webBot.click(VIEW_FULL_SIZE_IMAGE_LOCATOR);
    }

    public WebElement getFullSizeImageOverlay() {
        WebElement imageOverlay;
        try {
            imageOverlay = webBot.findElement(OVERLAY_IMAGE_HOLDER_LOCATOR);
        }
        catch (PageElementNotFoundException e) {
            return null;
        }
        return imageOverlay;
    }

    public boolean isSoldOutErrorDisplayed() {
        try {
            webBot.findElement(SOLD_OUT_MESSAGE_LOCATOR, WaitTime.FOUR);
            }
        catch (PageElementNotFoundException e) {
            return false;
        }
        return true;
    }

    public void clickTellAFriendButton() {
        webBot.click(TELL_A_FRIEND_BUTTON_LOCATOR);
    }

    public boolean isTellAFriendOverlayDisplayed() {
        try {
            webBot.findElement(TELL_A_FRIEND_OVERLAY_LOCATOR, WaitTime.TEN);
        }
        catch (PageElementNotFoundException e) {
            return false;
        }
        return true;
    }


    public void switchToTellAFriendOverlay() {
        WebElement tellAFriendOverlay = webBot.findElement(TELL_A_FRIEND_OVERLAY_LOCATOR, WaitTime.TEN);
        webBot.getDriver().switchTo().frame(tellAFriendOverlay);
    }


    public void fillTellAFriendEmailSubjectAndSubmit() {
        webBot.type(TELL_A_FRIEND_EMAIL_ADDRESS_LOCATOR, "friend@hotmail.com");
        webBot.type(TELL_A_FRIEND_EMAIL_SUBJECT_LOCATOR, "Test Subject");
        webBot.click(TELL_A_FRIEND_SUBMIT_BUTTON);
    }

    public void fillWholeTellAFriendFormAndSubmit() {
        webBot.type(TELL_YOUR_NAME_ELEMENT, "Test Name");
        webBot.type(TELL_YOUR_EMAIL_ELEMENT, "test@hotmail.com");

        fillTellAFriendEmailSubjectAndSubmit();
    }

    public String getExistingTellAFriendEMail() {
       return webBot.findElement(TELL_YOUR_EMAIL_ELEMENT).getAttribute("value");
    }

    public String getExistingTellAFriendName() {
       return webBot.findElement(TELL_YOUR_NAME_ELEMENT).getAttribute("value");
    }

    public boolean isTellAFriendSubmissionSuccessful() {
        try {
            webBot.findElement(TELL_A_FRIEND_SUBMISSION_SUCCESSFUL_MESSAGE, WaitTime.FOUR);
        }
        catch (PageElementNotFoundException e) {
            return false;
        }
        return true;
    }

    public boolean isFullProductInformationDisplayed(String visibility) {
        if (visibility.equals("shown")) {
            return webBot.findElement(ACCORDIONS_KEY).isDisplayed();
        } else return (webBot.findElement(SOLD_OUT_PRODUCT_DETAIL).getAttribute("style").contains("display: block"));
    }

    public boolean viewProductDetailsLinkIsVisible() {
        if(webBot.exists(null, cssSelector("#product-details-toggle"))) {
            return webBot.findElement(By.cssSelector("#product-details-toggle #view-pd")).isDisplayed();
        }
        return false;
    }

    public void clickViewProductDetailLink() throws InterruptedException {
        if (viewProductDetailsLinkIsVisible()) {
            webBot.click(SOLD_OUT_VIEW_PRODUCT_DETAIL_ELEMENT);
            Thread.sleep(1000);
        }
    }

    public void clickHideProductDetailLink() throws InterruptedException {
        if (!viewProductDetailsLinkIsVisible()) {
            webBot.click(SOLD_OUT_HIDE_PRODUCT_DETAIL_ELEMENT);
            Thread.sleep(1000);
        }
    }

    public boolean isMinimumProductInformationDisplayed() {
        return ((webBot.findElement(SOLD_OUT_IMAGE_ELEMENT, WaitTime.FOUR).isDisplayed()) &&
                (webBot.findElement(SOLD_OUT_TITLE_ELEMENT, WaitTime.FOUR).isDisplayed()) &&
                (webBot.findElement(SOLD_OUT_DESIGNER_ELEMENT, WaitTime.FOUR).isDisplayed()) &&
                (webBot.findElement(SOLD_OUT_PRICE_ELEMENT, WaitTime.FOUR).isDisplayed()) &&
                (webBot.findElement(SOLD_OUT_MESSAGE_ELEMENT, WaitTime.FOUR).isDisplayed()) &&
                (webBot.findElement(SOLD_OUT_ALTERNATIVE_SUGGESTIONS_ELEMENT, WaitTime.FOUR).isDisplayed()) &&
                (webBot.findElement(ADD_TO_WISHLIST_BUTTON_LOCATOR, WaitTime.FOUR).isDisplayed()));
    }


    public boolean isYmalProductInfoDisplayed() {
        List<WebElement> ymalBlock = webBot.findElements(SOLD_OUT_YMAL_CONTAINER_ELEMENT);

        boolean isDisplayed = false;

        for (int i = 0; i < ymalBlock.size(); i++) {
            List<WebElement> ymalList = webBot.findElements(SOLD_OUT_YMAL_PIDS_ELEMENT);
            for (int x = 0; x < ymalList.size(); x++) {
                WebElement ymalPid = ymalList.get(x);

                return !((ymalPid.findElement(By.tagName("a")).getAttribute("href").isEmpty())
                        || (ymalPid.findElement(SOLD_OUT_YMAL_DESIGNER_ELEMENT).getText().isEmpty())
                        || (ymalPid.findElement(SOLD_OUT_YMAL_TITLE_ELEMENT).getText().isEmpty())
                        || (ymalPid.findElement(SOLD_OUT_YMAL_PRICE_ELEMENT).getText().isEmpty()));
            }
        }
        return isDisplayed;
    }

    public String clickRandomYmalProduct()  {
        List<WebElement> Ymalpids = webBot.findElements(SOLD_OUT_YMAL_IMAGE_ELEMENT);
        Collections.shuffle(Ymalpids);
        webBot.waitForJQueryCompletion();
        String imageLink = Ymalpids.get(0).findElement(By.tagName("a")).getAttribute("href");
        String pid = imageLink.replaceAll("[^0-9]", "");
        Ymalpids.get(0).click();
        return pid;
    }

    public String getProductName() {
        return webBot.findElement(By.cssSelector("h1")).getText().trim();
    }

    public void addSoldOutItemToWishList() throws Throwable {
        if (isFullProductInformationDisplayed("shown")) {
            clickHideProductDetailLink();
        }
        webBot.findElement(By.cssSelector("#sold-out-add-wishlist button")).click();
    }

    public void expandedProductDetailsPageAddToWishlist(){
        webBot.findElement(By.id("add-wishlist")).click();
    }

    public boolean isShoppingBagOverlayDisplayed() {
        WebElement shoppingBagOverlay = getMiniShoppingBagOverlay();
        return shoppingBagOverlay.isDisplayed();
    }

    public String getPidFromMiniShoppingBagOverlay() {
        String productPageLink = getMiniShoppingBagOverlay().findElement(PRODUCT_LINK_IN_MINI_SHOPPING_BAG_LOCATOR).getAttribute("href");
        return productPageLink.substring(productPageLink.lastIndexOf("/")+1);
    }

    private WebElement getMiniShoppingBagOverlay() {
        return webBot.findElement(MINI_SHOPPING_BAG_LOCATOR, WaitTime.EIGHT);
    }


    public boolean isOutfitModuleDisplayed() throws InterruptedException {
        Thread.sleep(1000);
        return webBot.findElement(OUTFIT_MODULE_CONTAINER).isDisplayed();
    }

    public boolean isOutfitPidInfoDisplayed() {
        if (webBot.findElement(OUTFIT_MODULE_CONTAINER).getAttribute("class").contains("is-carousel")){
            List<WebElement> outfitPids = webBot.findElements(OUTFIT_MODULE_CAROUSEL_LOCATOR);
            int size = outfitPids.size();
            for (int i = 0; i <= (size - 1); i++) {
                assertFalse(outfitPids.get(i).findElement(By.className("product-feature-designer")).getText().isEmpty());
                assertFalse(outfitPids.get(i).findElement(By.className("product-feature-description")).getText().isEmpty());
                assertFalse(outfitPids.get(i).findElement(By.className("product-feature-price")).getText().isEmpty());
            }
        }else {
            List<WebElement> outfitPids = webBot.findElements(OUTFIT_MODULE_NO_CAROUSEL_LOCATOR);
            int size = outfitPids.size();
            for (int i = 0; i <= (size - 1); i++) {
                assertFalse(outfitPids.get(i).findElement(By.className("product-feature-designer")).getText().isEmpty());
                assertFalse(outfitPids.get(i).findElement(By.className("product-feature-description")).getText().isEmpty());
                assertFalse(outfitPids.get(i).findElement(By.className("product-feature-price")).getText().isEmpty());
            }
        }
        return true;
    }

    public boolean isYmalModuleDisplayed() {
        return webBot.findElement(YMAL_MODULE_LOCATOR, WaitTime.TWO).isDisplayed();
    }

    public boolean isYmalPidInfoDisplayed() {
        List <WebElement> ymalPids = webBot.findElements(YMAL_MODULE_LOCATOR);
        int size = ymalPids.size();
        for (int i = 0; i<=(size-1);i++){
            assertFalse(ymalPids.get(i).findElement(By.className("product-feature-designer")).getText().isEmpty());
            assertFalse(ymalPids.get(i).findElement(By.className("product-feature-description")).getText().isEmpty());
            assertFalse(ymalPids.get(i).findElement(By.className("product-feature-price")).getText().isEmpty());
        }
        return true;
    }

    public void clickVoucher(String voucher) {
        closeDontMissOutPopup();
        if (voucher.equals("Printed")){
            webBot.click(PRINTED_VOUCHER_LINK);
        }else if (voucher.equals("Virtual")){
            webBot.click(VIRTUAL_VOUCHER_LINK);
        }
    }

    public void addVoucherToShoppingBag() {
        closeDontMissOutPopup();
        webBot.click(VOUCHER_ADD_TO_BAG);
    }

    public void clickVoucherProceedToPurchase() {
        webBot.click(VOUCHER_PROCEED_TO_PURCHASE);
    }

    public String selectRandomVoucherPrice() {
        Select voucher_price = new Select (webBot.findElement(VOUCHER_PRICE_LOCATOR));
        int random_price_index = new Random().nextInt(voucher_price.getOptions().size());
        String voucher_selected_price_with_currency = voucher_price.getOptions().get(random_price_index).getText();
        String voucher_selected_price = voucher_selected_price_with_currency.replaceAll("\\s+","").replaceAll("\\£","");
        voucher_price.selectByIndex(random_price_index);
        return voucher_selected_price;
    }

    public void correctVoucherAdded(String voucherPrice, String voucherType) {
        if (voucherType.equals("Printed")){
            assertTrue(voucherPrice + " GBP Printed Gift voucher is missing in shopping bag", webBot.findElement(VOUCHER_SHOPPING_BAG_TITLE,WaitTime.FOUR).getText().equals(voucherPrice + " GBP - PRINTED GIFT CARD"));
        } else if (voucherType.equals("Virtual")){
            assertTrue(voucherPrice +" GBP Virtual Gift voucher is missing in shopping bag",webBot.findElement(VOUCHER_SHOPPING_BAG_TITLE,WaitTime.FOUR).getText().equals(voucherPrice+" GBP - VIRTUAL GIFT CARD"));
        }
    }

    public void fillVoucherInformation(String voucherType) {
        if (voucherType.equals("Virtual")){
            webBot.findElement(By.id("toEmail")).sendKeys("naptech@gmail.com");
            webBot.findElement(By.id("confirmToEmail")).sendKeys("naptech@gmail.com");
        }
    }

    public boolean isSkuInWishList(String sku) {
        List<WebElement> itemsInShoppingBag;
        try {
            itemsInShoppingBag = webBot.findElements(CLOTHING_CATEGORY_WISHLIST_LOCATOR, WaitTime.FOUR);
        }
        catch (PageElementNotFoundException e) {
            return false;
        }

        for (WebElement item : itemsInShoppingBag) {
            String id = item.getAttribute("id");
            if (id.contains(sku)) {
                return true;
            }
        }
        return false;
    }
    //need to update once we can get only 1 left from PS API
    public void isSingleSizeAnalyticsFired(String stockDetail, String pid) {
        if (stockDetail.equalsIgnoreCase("instock")){
            assertEquals("eVar46 value is wrong for pid " +pid,"one-size@in",getAnalyticSizeStockLowLevelTag());
            assertEquals("eVar47 value is wrong for pid " +pid,"one-size@all in stock",getAnalyticSizeStockHighLevelTag());

        }   else if (stockDetail.equalsIgnoreCase("sold out"))  {
            assertTrue("eVar46 value is wrong for pid " + pid, getAnalyticSizeStockLowLevelTag().contains("one-size@out") || getAnalyticSizeStockLowLevelTag().contains("one-size@soon"));
            assertTrue("eVar47 value is wrong for pid " + pid, getAnalyticSizeStockHighLevelTag().contains("one-size@out completely") || getAnalyticSizeStockHighLevelTag().contains("one-size@out back-soon"));
            assertEquals("Page Type value is wrong for pid " +pid,"PRODUCT DETAILS PAGE - SOLD OUT",getAnalyticPageTypeTag());
            assertTrue("event value is " +getAnalyticEventTag()+ " wrong for pid " +pid, getAnalyticEventTag().contains("event46"));

        } else if (stockDetail.equalsIgnoreCase("low stock")){
            assertTrue("evar46 is fired with value: " + getAnalyticSizeStockLowLevelTag() + " for pid "+pid, getAnalyticSizeStockLowLevelTag().equals("one-size@low") || getAnalyticSizeStockLowLevelTag().equals("one-size@one"));
            assertEquals("eVar47 value is wrong for pid "+pid,"one-size@partial stock",getAnalyticSizeStockHighLevelTag());
            assertTrue("event value is "+getAnalyticEventTag()+" wrong for pid "+pid, getAnalyticEventTag().contains("event47"));
        }

    }

//    public void isMultipleSizeAnalyticsFired(String stockDetail) {
//        if (stockDetail.equalsIgnoreCase("low stock")){
//            List<String> lowStockSizes = getLowStockSizes();
//            List<String> soldOutSizes = getSoldOutSizes();
//
//            if (soldOutSizes.size()==0){
//                assertEquals("multiple@partial stock",getAnalyticSizeStockHighLevelTag());
//                assertTrue("event47 is not fired", getAnalyticEventTag().contains("event47"));
//                for (int i=0; i<=lowStockSizes.size()-1; i++){
//                    assertTrue(getAnalyticSizeStockLowLevelTag().contains(lowStockSizes.get(i).replaceAll("..", "$0 ").trim()+"@low"));
//                }
//            } else {
//                assertEquals("multiple@partially sold out",getAnalyticSizeStockHighLevelTag());
//                assertTrue("event47 is not fired", getAnalyticEventTag().contains("event47"));
//                for (int i=0; i<=lowStockSizes.size()-1; i++){
//                    assertTrue(getAnalyticSizeStockLowLevelTag().contains(lowStockSizes.get(i).replaceAll("..", "$0 ").trim()+"@low"));
//                }
//
//            }
//        }else if (stockDetail.equalsIgnoreCase("sold out"))  {
//            List<String> soldOutSizes = getSoldOutSizes();
//            assertEquals("multiple@partially sold out",getAnalyticSizeStockHighLevelTag());
//            assertTrue("event47 is not fired", getAnalyticEventTag().contains("event47"));
//            for (int i=0; i<=soldOutSizes.size()-1; i++){
//                assertTrue(getAnalyticSizeStockLowLevelTag().contains(soldOutSizes.get(i).replaceAll("..", "$0 ").trim()+"@out"));
//                }
//            }
//    }

    public void isMultipleSizeAnalyticsFired(String stockDetail, String pid) {
        List<String> soldOutSizes = getSoldOutSizes();
        List<String> comingSoonSizes = getComingSoonSizes();
        List<String> sizeWithStockDetail = getProductSizes();

        if (soldOutSizes.size()== 0 && comingSoonSizes.size() == 0){
            assertEquals("eVar47 value is wrong for pid " +pid,"multiple@partial stock",getAnalyticSizeStockHighLevelTag());
        } else{
            assertEquals("eVar47 value is wrong for pid " +pid,"multiple@partially sold out",getAnalyticSizeStockHighLevelTag());
        }

        assertTrue("event47 is fired with value: " + getAnalyticEventTag() + " for pid " +pid, getAnalyticEventTag().contains("event47"));

        for (int i=0; i<=sizeWithStockDetail.size()-1; i++){
            String[] size = sizeWithStockDetail.get(i).split("-");

            if (sizeWithStockDetail.get(i).contains("sold out")){
                assertTrue("eVar46 should have been " +size[0].replaceAll("(?<=[^0-9.])(?=[0-9.])"," ").trim()+ "@out" + " but was " +getAnalyticSizeStockLowLevelTag()+ " for pid" +pid,getAnalyticSizeStockLowLevelTag().contains(size[0].replaceAll("(?<=[^0-9.])(?=[0-9.])"," ").trim()+"@out"));
            } else if (sizeWithStockDetail.get(i).contains("coming soon")){
                assertTrue("eVar46 should have been " +size[0].replaceAll("(?<=[^0-9.])(?=[0-9.])"," ").trim()+ "@soon" + " but was " +getAnalyticSizeStockLowLevelTag()+ " for pid" +pid,getAnalyticSizeStockLowLevelTag().contains(size[0].replaceAll("(?<=[^0-9.])(?=[0-9.])"," ").trim()+"@soon"));
            } else if (sizeWithStockDetail.get(i).contains("low")){
                assertTrue("eVar46 should have been " +size[0].replaceAll("(?<=[^0-9.])(?=[0-9.])"," ").trim()+ "@low" + " but was " +getAnalyticSizeStockLowLevelTag()+ " for pid" +pid,getAnalyticSizeStockLowLevelTag().contains(size[0].replaceAll("(?<=[^0-9.])(?=[0-9.])"," ").trim()+"@low"));
            } else if (sizeWithStockDetail.get(i).contains("only 1 left")){
                assertTrue("eVar46 should have been " +size[0].replaceAll("(?<=[^0-9.])(?=[0-9.])"," ").trim()+ "@one" + " but was " +getAnalyticSizeStockLowLevelTag()+ " for pid" +pid,getAnalyticSizeStockLowLevelTag().contains(size[0].replaceAll("(?<=[^0-9.])(?=[0-9.])"," ").trim()+"@one"));
            } else {
                assertTrue("eVar46 should have been " +size[0].replaceAll("(?<=[^0-9.])(?=[0-9.])"," ").trim()+ "@in" + " but was " +getAnalyticSizeStockLowLevelTag()+ " for pid" +pid,getAnalyticSizeStockLowLevelTag().contains(sizeWithStockDetail.get(i).replaceAll("(?<=[^0-9.])(?=[0-9.])"," ").trim()+"@in"));
            }
        }
    }


    public void isSelectedSizeAnalyticFired(String sku) {
        String sizeWithStockLabel = getProductSize(sku);
        String[] size = sizeWithStockLabel.split("-");

        assertTrue("event48 is fired with value: " + getAnalyticEventTag() + " for pid " +sku, getAnalyticEventTag().contains("event48"));

        if (sizeWithStockLabel.contains("- sold out")){
            assertEquals("eVar46 value is wrong for sku " +sku, size[0].trim() + "@out", getAnalyticSizeStockLowLevelTag());
        }else if (sizeWithStockLabel.contains("- only 1 left")){
            assertEquals("eVar46 value is wrong for sku " +sku, size[0].trim()+"@one",getAnalyticSizeStockLowLevelTag());
        } else if (sizeWithStockLabel.contains("- low stock")){
            assertEquals("eVar46 value is wrong for sku " +sku, size[0].trim()+"@low",getAnalyticSizeStockLowLevelTag());
        } else if (sizeWithStockLabel.contains("- coming soon")){
            assertEquals("eVar46 value is wrong for sku " +sku, size[0].trim()+"@soon",getAnalyticSizeStockLowLevelTag());
        }else {
            assertEquals("eVar46 value is wrong for sku " +sku, size[0].trim()+"@in",getAnalyticSizeStockLowLevelTag());
        }
    }

    public void isMultipleCompletelySoldOutAnalyticFired(String pid) {
        List<String> comingSoonSizes = getComingSoonSizes();
        List<String> sizeWithStockDetail = getProductSizes();

        assertTrue("event46 is fired with value: " + getAnalyticEventTag() + " for pid " +pid, getAnalyticEventTag().contains("event46"));
        assertEquals("Page Type value is wrong for pid " +pid, "PRODUCT DETAILS PAGE - SOLD OUT",getAnalyticPageTypeTag());

        if (comingSoonSizes.size()>0){
            assertEquals("eVar47 value is wrong for pid " +pid, "multiple@out back-soon",getAnalyticSizeStockHighLevelTag());
        } else {
            assertEquals("eVar47 value is wrong for pid " +pid, "multiple@out completely",getAnalyticSizeStockHighLevelTag());
        }

        for (int i=0; i<=sizeWithStockDetail.size()-1; i++){
            String[] size = sizeWithStockDetail.get(i).split("-");
            if (sizeWithStockDetail.get(i).contains("sold out")){
                assertTrue("eVar46 should have been "+size[0].replaceAll("(?<=[^0-9.])(?=[0-9.])"," ").trim()+"@out"+" but was "+getAnalyticSizeStockLowLevelTag()+" for pid "+pid,getAnalyticSizeStockLowLevelTag().contains(size[0].replaceAll("..", "$0 ").trim()+"@out"));
            } else if (sizeWithStockDetail.get(i).contains("coming soon")){
                assertTrue("eVar46 should have been "+size[0].replaceAll("(?<=[^0-9.])(?=[0-9.])"," ").trim()+"@soon"+" but was "+getAnalyticSizeStockLowLevelTag()+" for pid "+pid,getAnalyticSizeStockLowLevelTag().contains(size[0].replaceAll("..", "$0 ").trim()+"@soon"));
            }
        }


    }

    public void isDefaultAnalyticFired(String expectedDesigner, String expectedTitle, String pid, List<String> categories) {
        webBot.waitForJQueryCompletion();

        assertTrue("event prodview should have fired but was "+getAnalyticEventTag(),getAnalyticEventTag().contains("prodView"));
        assertEquals(pid,getAnalyticProductTag());
        assertEquals("Designer name is wrong for pid " +pid, expectedDesigner, getAnalyticDesignerNameTag());
        assertEquals("Title value is wrong for pid " +pid, expectedTitle, getAnalyticTitleTag());
        assertEquals("Device type value is wrong for pid " +pid,"Desktop", getAnalyticDeviceTag());
        assertEquals("Title value is wrong for pid " +pid, expectedTitle, getAnalyticPageNameTag());
        assertTrue("The country tag fired was " + getAnalyticCountryTag() +" for pid " +pid, getAnalyticCountryTag().equalsIgnoreCase(webBot.findElement(By.xpath("html/body")).getAttribute("data-country")));
        assertTrue("The Language tag fired was" +getAnalyticLanguageTag() +" for pid " +pid, getAnalyticLanguageTag().equalsIgnoreCase( webBot.findElement(By.xpath("html/body")).getAttribute("data-language")));
        assertTrue("The region tag fired was" +getAnalyticRegionTag() +" for pid " +pid, getAnalyticRegionTag().equalsIgnoreCase( webBot.findElement(By.xpath("html/body")).getAttribute("data-region")));
//        assertEquals(categories.get(0).replaceAll(" ","_"),getAnalyticNavLevel1());
        assertEquals("sub-category value is wrong for pid " +pid, categories.get(1).replaceAll(" ", "_"),getAnalyticNavLevel2());

        if (categories.size() == 3) {
            assertEquals("sub-category value is wrong for pid " +pid, categories.get(2).replaceAll(" ", "_"),getAnalyticNavLevel3());
        }
    }

    public void isProductAddedInWishlist(String pid) {
        List<WebElement> WishlistItems = webBot.findElements(By.cssSelector(".toggle-item>img"));

        for (int i = 0; i <= WishlistItems.size(); i++) {
            assertTrue(WishlistItems.get(i).getAttribute("src").contains(pid));
            break;
        }

    }

    public String getGirdleCurrencySymbol(){
        return webBot.findElement(By.xpath("html/body")).getAttribute("data-currency-symbol");
    }

    public String getGirdleCurrencyCode(){
        return webBot.findElement(By.xpath("html/body")).getAttribute("data-currency-code");
    }

    public void clickAddToBag() {
        webBot.click(ADD_TO_BAG);
    }

    public void miniShoppingBagInfoDisplayed(String designerName,String titleName ,String pid, int expectedPrice) {
        assertEquals(titleName, webBot.findElement(MINI_SHOPPING_BAG_TITLE).getText());
        assertEquals(designerName, webBot.findElement(MINI_SHOPPING_BAG_DESIGNER).getText());
        assertEquals("http://cache.net-a-porter.com/images/products/" + pid + "/" + pid + "_in_s.jpg", webBot.findElement(MINI_SHOPPING_BAG_PRODUCT_IMAGE).getAttribute("src"));
        assertEquals(expectedPrice, webBot.findElement(MINI_SHOPPING_BAG_PRICE).getText());

//        if (getGirdleCurrencySymbol().contains("$")){
//            assertEquals(expectedPrice, webBot.findElement(MINI_SHOPPING_BAG_PRICE).getText());
//        }else if (getGirdleCurrencySymbol().contains("pound")) {
//            assertEquals(expectedPrice, webBot.findElement(MINI_SHOPPING_BAG_PRICE).getText());
//        }else if (getGirdleCurrencySymbol().contains("euro")) {
//            assertEquals(expectedPrice, webBot.findElement(MINI_SHOPPING_BAG_PRICE).getText());
//        }
    }

    public void shoppingCounterIncrement() {
        assertEquals("1",webBot.findElement(HEADER_SHOPPING_BAG).getText());
    }

    public void clickProceedToPurchaseMiniShoppingBag() {
        webBot.click(MINI_SHOPPING_BAG_CHECKOUT);
    }

    public void clickSignInLink() {
        webBot.click(SIGN_IN_LINK);
    }

    public void clickNapLogo() throws InterruptedException {
        webBot.click(NAP_LOGO);
        Thread.sleep(1000);
    }

    public String getPidFromSku(String sku) {
        return sku.substring(0, sku.indexOf("-"));
    }
}