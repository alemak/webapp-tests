package com.netaporter.pws.automation.nap.pages;

import com.netaporter.pws.automation.nap.cucumber.steps.wishlistv3.PageVerificationSteps;
import com.netaporter.pws.automation.nap.pages.components.WishlistV3ProductDetailsSlider;
import com.netaporter.pws.automation.shared.pages.IWishlistV3Page;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.MenuItem;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.NavMenu;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.Price;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.WishlistV3Product;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.fail;
import static org.openqa.selenium.By.cssSelector;

/**
 * User: a.michael@london.net-a-porter.com
 * Date: 20/03/2013
 */

@Component
@Scope("cucumber-glue")
public class WishListV3Page extends AbstractNapPage implements IWishlistV3Page {

    private static final String PAGE_NAME = "Your Wish List";
    private static final String PATH = "wishlist/";

    private static final Pattern INTERNAL_WOAS_HOST_PATTERN = Pattern.compile("https?://[^:]+:2808[01]");
    public static final String CSS_SELECTOR_SHOPPING_BAG_CONTAINER = ".shopping-bag-container";
    public static final String PRODUCT_LIST_ITEM_THUMBNAIL_A_CSS_SELECTOR = ".product-list-item .thumbnail a";
    public static final String PRODUCT_LIST_ITEM_THUMBNAIL_CSS_SELECTOR = ".product-list-item .thumbnail";
    public static final String PRODUCT_LIST_ITEM_THUMBNAIL_IMG_CSS_SELECTOR = ".product-list-item .thumbnail img";


    private WishlistV3ProductDetailsSlider slider;

    public WishListV3Page() {
        super(PAGE_NAME, PATH);
    }

    @Override
    public boolean isPageRegionalised() {
        return false;
    }

    @PostConstruct
    public void postConstruct() {
        slider = new WishlistV3ProductDetailsSlider(webBot);
    }


    public void goToWishlistListID(String listID) {
        String originalPath = getPath();
        setPath(getPath() + listID);
        go();
        setPath(originalPath);
    }

    public void goToAllItemsPage() {
        String originalPath = getPath();
        setPath("wishlist/all-items");
        go();
        setPath(originalPath);
    }

    public void goToClosetPage() {
        String originalPath = getPath();
        setPath("wishlist/closet");
        go();
        setPath(originalPath);
    }

    public void goToAlertsPage() {
        String originalPath = getPath();
        setPath("wishlist/alerts");
        go();
        setPath(originalPath);
    }

    public void goToDefaultWishlist() throws Throwable {
        // Current default is that the default list is the first item
        clickFirstMenuElement();
    }


    public void clickOnWhitespace() {
        //WebElement element = webBot.findElement(By.cssSelector(".wishlist-header"));
        WebElement element = webBot.findElement(By.cssSelector("#header-holder"));
        webBot.clickAtPoint(element, 1, 1);
    }

    public WebElement wishlistHeaderTitle() {
        By element = By.cssSelector("h1");
        return webBot.findElement(element, 5);
    }

    public String getWishlistName() {
        return wishlistHeaderTitle().getText();
    }

    public String getPageTitle() {
        return webBot.getTitle().replace("\u00a0", " ");
    }

    public Boolean isPidInshoppingBagPopup(String pid, String sizeToCheck) {
        String imageString = webBot.findElement(By.cssSelector(".shopping-bag-container a")).getAttribute("href").toString();
        String sizeOnPage = webBot.findElement(By.cssSelector(".product-desc .size")).getText().toString().replaceAll("\\s", "");

        sizeToCheck = sizeToCheck.replaceAll("\\s","");

        if (sizeOnPage.equals("")) {
            sizeOnPage = "OneSize";
        }

        if ((imageString.contains(pid)) && (sizeOnPage.equals(sizeToCheck))) {
            return true;
        }
        return false;
    }

    public Integer numberOfItemsInMiniBag() throws Throwable {
        String numberDisplayed = webBot.findElement(By.cssSelector("a#header-shopping-bag")).getText();
        int i=0;
        while(numberDisplayed.length()==0 && i < 10) {
            Thread.sleep(1000);
            i++;
            numberDisplayed = webBot.findElement(By.cssSelector("a#header-shopping-bag")).getText();
        }
        return Integer.decode(numberDisplayed);
    }

    /**
     * Gets a list of URLs that have the port 28080 or 28081. We shouldn't see these in the HTML, as they are internal WOAS URLs
     */
    public Set<String> findInternalWoasUrls() {
        Matcher m = INTERNAL_WOAS_HOST_PATTERN.matcher(webBot.getPageSource());
        Set<String> allMatches = new HashSet<String>();
        while (m.find()) {
            allMatches.add(m.group());
        }

        return allMatches;
    }


    /**
     * Clicks the product image for the first wishlist item that has the specified product
     */
    public void clickProductThumbnail(Integer pid) {
        List<WebElement> linkElements = getLinkElements();

        for(int i = 0; i <= linkElements.size()-1; i = i + 1) {
            if(linkElements.get(i).getAttribute("src").contains("/" + pid)) {
                linkElements.get(i).click();
                return;
            }
        }
        fail("No product found with pid " + pid);
//
//        try {
//            WebElement itemToClick = webBot.findElement(By.cssSelector(".product-list-item .thumbnail img[src*='" + pid + "']"));
//            //webBot.findElement(By.cssSelector(".product-list-item .thumbnail > img[src*='" + pid + "']"));
//            itemToClick.click();
//        } catch (PageElementNotFoundException e) {
//           // return false;
//            fail("No product found with pid " + pid);
//        }

    }

    /**
     * Clicks the product add to bag for the first wishlist item that has the specified product
     */
    public void clickProductAddToBag(String pid) {
        List<WebElement> addToBagButtonElements = webBot.findElements(By.cssSelector("button.add-to-bag"));
        List<WebElement> linkElements = getLinkElements();

        for(int i = 0; i <= addToBagButtonElements.size(); i = i + 1) {
            if(linkElements.get(i).getAttribute("src").contains("/" + pid)) {
                webBot.mouseOver(linkElements.get(i));
                addToBagButtonElements.get(i).click();
                return;
            }
        }

        fail("No product found with pid " + pid);
    }

    public void clickAllAddToBag() throws Throwable {
        List<WebElement> addToBagButtonElements = webBot.findElements(By.cssSelector("button.add-to-bag"));
        List<WebElement> linkElements = getLinkElements();

        for(int i = 0; i < addToBagButtonElements.size(); i = i + 1) {
            webBot.mouseOver(linkElements.get(i));
            addToBagButtonElements.get(i).click();
            waitForShoppingBagPopupToBeDisplayed();
            waitForShoppingBagPopupToNotBeDisplayed();
        }

    }

    public boolean isAddToBagIconPresentOnPage() {
        List<WebElement> addToBagButtonElements = webBot.findElements(By.cssSelector("button.add-to-bag"));
        List<WebElement> addToBagGreyedOutButtonElements = webBot.findElements(By.cssSelector("button.add-to-bag.is-disabled"));

        Integer addToBagCount = addToBagButtonElements.size() - addToBagGreyedOutButtonElements.size();
        if(addToBagCount > 0) {
            return true;
        }
        return false;
    }

    public List<WebElement> getSizeElementsFromPage() {
        List<WebElement> addToBagButtonElements = webBot.findElements(By.cssSelector("#choose-your-size option"));

        return addToBagButtonElements;
    }

    /**
     * Clicks the product remove for the first wishlist item that has the specified product
     */
    public void clickProductRemove(String pid) throws Throwable {
        List<WebElement> removeButtonElements = webBot.findElements(By.cssSelector("button.remove-item"));
        List<WebElement> linkElements = getLinkElements();

        for(int i = 0; i <= removeButtonElements.size(); i = i + 1) {
            if(linkElements.get(i).getAttribute("src").contains("/" + pid)) {
                webBot.executeJavascript("scroll(250, 0)");
                webBot.click(removeButtonElements.get(i));
                Thread.sleep(1000);
                return;
            }
        }

        fail("No product found with pid " + pid);
    }

    private List<WebElement> getLinkElements() {
        return webBot.findElements(By.cssSelector(PRODUCT_LIST_ITEM_THUMBNAIL_IMG_CSS_SELECTOR));
    }

//    public boolean doesPidExistsOnPage(String pid) {
//        try {
//            webBot.findElement(By.cssSelector(".product-list-item .thumbnail img[src*='" + pid + "']"));
//            return true;
//        } catch (PageElementNotFoundException e) {
//            return false;
//        }
//    }
//
    public boolean doesPidExistsOnPage(String pid) {
        List<WebElement> linkElements = getLinkElements();

        for(int i = 0; i <= linkElements.size()-1; i = i + 1) {
            if(linkElements.get(i).getAttribute("src").contains("/" + pid)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     */
    public boolean isDeleteListLinkAvailable() throws Throwable {
//        waitForElementToExist(cssSelector(".popup-window .delete-list"));
//        waitForElementToBeVisible(".popup-window .delete-list");
        return webBot.exists(null, cssSelector(".popup-window .delete-list"));
    }

    public boolean isManageListButtonAvailable() {
        return webBot.exists(null, cssSelector(".manage-list.secondary-button"));
    }

    public boolean isShareListButtonAvailable() {
        return webBot.exists(null, cssSelector(".dropdown .toggle.secondary-button"));
    }

    public boolean isViewMoreButtonAvailable() {
        return webBot.exists(null, cssSelector(".pagination button.load-more"));
    }

    public Integer numberOfItemsShowingOnPage() {
        webBot.mouseOver(webBot.findElement(By.cssSelector("#footer"),10));
        return webBot.findElements(By.cssSelector(".product-list .product-list-item")).size();
    }

    public void clickViewMoreButtonAvailable() throws Throwable {
        final Integer numProdsBeforeClick = numberOfItemsShowingOnPage();

        waitForElementToExist(By.cssSelector(".pagination button.load-more"));
        waitForElementToBeVisible(".pagination button.load-more");
        webBot.findElement(By.cssSelector(".pagination button.load-more")).click();
        Integer i = 0;
        System.out.print("Waiting for products to load");
        while(numProdsBeforeClick.equals(numberOfItemsShowingOnPage()) && i < 80) {
            Thread.sleep(250);
            i++;
            System.out.print(".");
        }
        if (i.equals(80)) {
            fail("Products took longer than 20 seconds to load");
        }
        System.out.println("Loaded");
    }

    /**
     * Clicks the delete list button
     */
    public void clickDeleteList() {
        WebElement deleteListButtonElement = getDeleteWishlistWebElement();

        deleteListButtonElement.click();
        return;
    }

    private WebElement getDeleteWishlistWebElement() {
        return webBot.findElement(By.cssSelector(".popup-window .delete-list"));
    }

    private WebElement getShareWishlistButtonWebElement() {
        return webBot.findElement(By.cssSelector(".dropdown .toggle.secondary-button"));
    }

    private WebElement getLinkOnShareWishlistMenuWebElement() {
        return webBot.findElement(By.cssSelector(" .dropdown .menu-item.share-list"));
    }

    public boolean isShareWishListPopupOpen() throws Throwable {
        waitForElementToExist(By.cssSelector(".share-wishlist-popup.is-open"));
        return webBot.exists(null, cssSelector(".share-wishlist-popup.is-open"));
    }

    /**
     * Clicks the Get Link menu item
     */
    public void clickGetLinkMenuItem() {
        WebElement getLinkMenuItemWebElement = getLinkOnShareWishlistMenuWebElement();
        getLinkMenuItemWebElement.click();
    }

    /**
     * Clicks the Share list dropdown button
     */
    public void clickShareList() {
        WebElement shareWishlistButtonWebElement = getShareWishlistButtonWebElement();
        shareWishlistButtonWebElement.click();
    }

    /**
     * Clicks the Manage list button
     */
    public void clickManageList() throws Throwable {
        webBot.waitForJQueryCompletion();
        WebElement manageListButtonElement = getManageWishlistWebElement();
        manageListButtonElement.click();
        Thread.sleep(1500);
    }

    private WebElement getManageWishlistWebElement() {
        return webBot.findElement(By.cssSelector(".wishlist-toolbar .manage-list.secondary-button"));
    }

    public boolean doesManageWishlistButtonExist() {
        return getManageWishlistWebElement().isDisplayed();
    }

    public void clickSharedRadioButtonAndSave(String wishlistName) throws Throwable {
        if(!wishlistName.equals("Wish List")) {
            isManageWishlistPopupDisplayed(wishlistName);
        }
        // I've had to use this method to find the radio buttons as I was getting a weird "By is not clickable" error
        webBot.findElement(By.cssSelector(".manage-wishlist-popup dt+dd label[for=manage-shared]")).click();
        Thread.sleep(1000);
        clickConfirmationButtonInOverlay();
        isManageWishlistPopupNotDisplayed();
    }

    public void clickPrivateRadioButtonAndSave(String wishlistName) throws Throwable {
        isManageWishlistPopupDisplayed(wishlistName);
        // I've had to use this method to find the radio buttons as I was getting a weird "By is not clickable" error
        webBot.findElement(By.cssSelector(".manage-wishlist-popup dt+dd label[for=manage-not-shared]")).click();
        Thread.sleep(1000);
        clickConfirmationButtonInOverlay();
        isManageWishlistPopupNotDisplayed();
    }

    public boolean isCurrentWishlistPrivate() {
        return webBot.exists(null, By.cssSelector(".wishlist-header.is-not-shared"));
    }

    /**
     * Clicks the designer link for the first wishlist item that has the specified product
     */
    public void clickProductDesigner(Integer pid) {
        List<WebElement> linkElements = webBot.findElements(By.cssSelector(".product-list-item .designer a"));

        for(WebElement element: linkElements) {
            if(element.getAttribute("href").endsWith("/" + pid)) {
                element.click();
                return;
            }
        }

        fail("No product found with pid " + pid);
    }

    public void clickSuggestedWishlistName(String suggestedName) throws Throwable {

        Thread.sleep(2000);
        List<WebElement> linkElements = webBot.findElements(By.cssSelector(".create-wishlist-suggestions button"));

        for(WebElement element: linkElements) {
            if(element.getText().contains(suggestedName)) {
                element.click();
                Thread.sleep(2000);
                return;
            }
        }
        fail("No suggested wishlist called  " + suggestedName);
    }

    public boolean doesSuggestedWishlistNameExist(String suggestedName) throws Throwable {
        
        Thread.sleep(2000);
        List<WebElement> linkElements = webBot.findElements(By.cssSelector(".create-wishlist-suggestions button"));

        for(WebElement element: linkElements) {
            if(element.getText().contains(suggestedName)) {
                return true;
            }
        }
        return false;
    }

    public boolean doesSuggestedWishlistListExist() throws Throwable{
        Thread.sleep(500);
        return webBot.exists(null, By.cssSelector(".mega-menu.is-reduced"));
    }

    public void submitCreateListFormWithClick(String listName) {
        getCreateListTextBox().sendKeys(listName);
        getCreateListSubmitButton().click();
        waitForCreateFormToDisappear();
    }

    public void submitCreateListFormWithEnter(String listName)  {
        getCreateListTextBox().sendKeys(listName);
        getCreateListTextBox().sendKeys(Keys.RETURN);
        waitForCreateFormToDisappear();
    }

    private void waitForCreateFormToDisappear() {
        try {
            Thread.sleep(500);
        } catch (Throwable throwable) {
            // Not forwarding this on as some tests expect this form not to disappear
        }
    }

    public boolean isCreateNewListTextDisplayed() {
        return getCreateListMenuItem().isDisplayed();
    }

    public boolean isCreateWishlistFormVisible() {
        return webBot.findElement(By.cssSelector("li.create-wishlist-mega-menu-item form")).isDisplayed();
    }

    public WebElement getCreateListMenuItem() {
        return webBot.findElement(By.cssSelector("li.create-wishlist-mega-menu-item span"));
    }

    private WebElement getCreateListSubmitButton() {
        return webBot.findElement(By.cssSelector("li.create-wishlist-mega-menu-item form button"));
    }

    private WebElement getCreateListTextBox() {
        return webBot.findElement(By.cssSelector("li.create-wishlist-mega-menu-item form input"));
    }

    /**
     * Gets the Nav Menu from the page.
     */
    public NavMenu getNavMenu() throws Throwable {
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

    /**
     * Gets the navigation menu item that links to the all items page
     */
    public WebElement getMenuElementAllItems() throws Throwable {
        return getNavMenuElementByHref("wishlist/all-items");
    }

    public void clickFirstMenuElement() throws Throwable {
        WebElement element = getNavMenuElements().get(0);
        WebElement anchor = element.findElement(cssSelector("a, span"));
        anchor.click();
    }


    public void clickMenuElementByText(String wishlistName) throws Throwable {
        for(WebElement element: getNavMenuElements()) {
            WebElement anchor = element.findElement(cssSelector("a, span"));
            if (anchor.getText().equals(wishlistName)) {
                anchor.click();
                return;
            }
        }
        fail("Could not find wishlist menu item: " + wishlistName);
    }

    public void clickWishlistMenu() throws Throwable {
        Thread.sleep(500);
        webBot.findElement(By.cssSelector((".wishlist-mega-dropdown button.secondary-button"))).click();
        waitForElementToExist(By.cssSelector(".wishlist-mega-dropdown.is-open"));
    }

    public Integer countMenuItems() throws Throwable {
        return webBot.findElements(By.cssSelector("li.menu-item")).size();
    }

    /**
     * Gets the first navigation menu item that links to the page for the specified wishlist
     */
    public WebElement getMenuElementByWishlistId(String wishlistId) throws Throwable {
        return getNavMenuElementByHref("wishlist/" + wishlistId);
    }

    /**
     * Gets the first navigation menu item that has the specified text
     */
    public WebElement getNavMenuElementByText(String text) throws Throwable {
        for(WebElement element: getNavMenuElements()) {
            WebElement anchor = element.findElement(cssSelector("a, span"));
            if(anchor.getText().equals(text)) {
                return element;
            }
        }

        fail("Menu Item with text " + text + " not found");
        return null;
    }

    /**
     * Gets the first navigation menu item that contains the specified string somewhere in it's href
     */
    public WebElement getNavMenuElementByHref(String href) throws Throwable {
        for(WebElement element: getNavMenuElements()) {
            WebElement anchor = element.findElement(cssSelector("a"));
            if(anchor.getAttribute("href").endsWith(href)) {
                return element;
            }
        }

        fail("Menu Item with href " + href + " not found");
        return null;
    }

    /**
     * Clicks the navigation menu, to show the drop down menu
     */
    public void clickToShowNavigationMenu() throws Throwable {
        if(!webBot.exists(null, By.cssSelector(".wishlist-mega-dropdown.is-open"))) {
            webBot.findElement(By.cssSelector(".wishlist-mega-dropdown button.secondary-button")).click();
        }
        waitForElementToExist(By.cssSelector(".wishlist-mega-dropdown.is-open"));
    }

    /**
     * Gets a list of WebElements, one for each item in the naviagtion menu
     */
    public List<WebElement> getNavMenuElements() throws Throwable {
        //Click menu to make items visible
        Thread.sleep(1000);
        clickToShowNavigationMenu();
//        return webBot.findElements(By.cssSelector("li.menu-item, li.menu-form-item, li.create-wishlist-menu-item"));
        return webBot.findElements(By.cssSelector(".wishlist-mega-dropdown li.menu-item, .wishlist-mega-dropdown li.create-wishlist-mega-menu-item"));
    }

    public List<WebElement> getWishlistThumbnails() {
        return webBot.findElements(By.cssSelector(".product-list-item img"));
    }

    public List<String> getWishlistSizes() {
        List<String> sizes = new ArrayList<String>();

        List<WebElement> plis =  webBot.findElements(By.className("product-list-item"));
        for (WebElement listItem : plis) {
            if (webBot.exists(listItem, By.className("size"))) {
                sizes.add(listItem.findElement(By.className("size")).getText());
            } else {
                sizes.add("");
            }
        }
        return sizes;
    }

    /**
     * Gets a list of the URL hrefs to the product page on product images of wishlist items
     */
    public List<String> getProductLinksOnImage() {
        By element = By.cssSelector(PRODUCT_LIST_ITEM_THUMBNAIL_A_CSS_SELECTOR);
        List<WebElement> linkElements = webBot.findElements(element);
        List<String> links = new ArrayList<String>();

        for(WebElement linkElem: linkElements) {
            links.add(linkElem.getAttribute("href"));
        }

        return links;
    }

    /**
     * Gets a list of the URL hrefs to the product page on the designer name of wishlist items
     */
    public List<String> getProductLinksOnDesigner() {
        By element = By.cssSelector(".product-list-item .designer a");
        List<WebElement> linkElements = webBot.findElements(element);
        List<String> links = new ArrayList<String>();

        for(WebElement linkElem: linkElements) {
            links.add(linkElem.getAttribute("href"));
        }

        return links;
    }

    public WebElement wishlistContent() {
        By element = By.cssSelector(".wishlist-content");
        return webBot.findElement(element, 5);
    }

    public List<WishlistV3Product> getAllWishlistV3Products() {

        By element = By.cssSelector(".product-list-item");
        List<WebElement> allProductsList = webBot.findElements(element);

        List<WishlistV3Product> allProducts = new ArrayList<WishlistV3Product>();

        for (WebElement webElement : allProductsList) {
            WishlistV3Product singleProduct = new WishlistV3Product();

            String imgUrl = webElement.findElement(By.tagName("img")).getAttribute("src");
            String pidString = imgUrl.substring(imgUrl.indexOf("/products/") + 10, imgUrl.indexOf("/", imgUrl.indexOf("/products/") + 10 ));

            singleProduct.setPid(Integer.parseInt(pidString));
            singleProduct.setDesignerName(webElement.findElement(cssSelector(".designer")).getText());

            if (webElement.findElements(cssSelector(".price .now")).size() == 0) {
                singleProduct.setPriceString(webElement.findElement(cssSelector(".price")).getText());
                String priceString = webElement.findElement(cssSelector(".price")).getText().replace(",", "");

                String currencySymbol = priceString.substring(0,1);
                Price originalPrice = new Price();
                originalPrice.setCurrencySymbol(currencySymbol);
                originalPrice.setValue(Double.parseDouble(priceString.replace(currencySymbol, "")));
                singleProduct.setOriginalPrice(originalPrice);
            } else {
                String bothPrices = webElement.findElement(cssSelector(".price .was")).getText() + " " +
                        webElement.findElement(cssSelector(".price .now")).getText();
                singleProduct.setPriceString(bothPrices);

                String wasPriceString = webElement.findElement(cssSelector(".price .was")).getText().replace(",", "");
                String wasCurrencySymbol = wasPriceString.substring(0,1);

                Price originalPrice = new Price();
                originalPrice.setCurrencySymbol(wasCurrencySymbol);
                originalPrice.setValue(Double.parseDouble(wasPriceString.replace(wasCurrencySymbol, "")));
                singleProduct.setOriginalPrice(originalPrice);

                String nowPriceString = webElement.findElement(cssSelector(".price .now")).getText().replace(",", "");
                String nowCurrencySymbol = nowPriceString.substring(0,1);

                Price discountedPrice = new Price();
                discountedPrice.setCurrencySymbol(nowCurrencySymbol);
                discountedPrice.setValue(Double.parseDouble(nowPriceString.replace(wasCurrencySymbol, "")));
                singleProduct.setDiscountedPrice(discountedPrice);
            }

            if (webBot.exists(webElement, cssSelector(".size"))) {
                // Sizes are inconsitent across site so removing spaces
                singleProduct.setSize(webElement.findElement(cssSelector(".size")).getText().trim().replace(" ", ""));
            } else {
                singleProduct.setSize("");
            }
            // Hack to work-around inconsistencies with size
            if ((singleProduct.getSize().equals("n/a")) || (singleProduct.getSize().equalsIgnoreCase(PageVerificationSteps.SINGLE_SIZE))) {
                singleProduct.setSize(PageVerificationSteps.SINGLE_SIZE);
            }

            allProducts.add(singleProduct);
        }

        return allProducts;
    }
    /**
     * When on the wishlist page, gets the ID for this wishlist by extracting it from the URL
     */
    public String getWishlistId() {
        String urlAfterSlash = StringUtils.substringAfterLast(webBot.getCurrentUrl(), "/");
        String urlAfterSlashAndBeforeQuery = StringUtils.substringBefore(urlAfterSlash, "?");
        return urlAfterSlashAndBeforeQuery;
    }

    public Boolean isShoppingBagPopupDisplayed() {
        WebElement container = webBot.findElement(By.cssSelector(CSS_SELECTOR_SHOPPING_BAG_CONTAINER));
        return container.isDisplayed();
    }

    public void waitForShoppingBagPopupToBeDisplayed() throws Throwable {
        waitForElementToExist(By.cssSelector(CSS_SELECTOR_SHOPPING_BAG_CONTAINER));
        waitForElementToBeVisible(CSS_SELECTOR_SHOPPING_BAG_CONTAINER);
    }

    public void waitForShoppingBagPopupToNotBeDisplayed() throws Throwable {
        waitForElementToNotBeVisible(CSS_SELECTOR_SHOPPING_BAG_CONTAINER);
    }


    public boolean isDeleteWishlistPopupDisplayed(String wishlistName) {
        // Check for pop-up window
        WebElement popupWindow = webBot.findElement(By.cssSelector(".popup.is-open .popup-window"));
        if ((popupWindow != null) && (popupWindow.isDisplayed())) {

            // Check header text
            WebElement header = webBot.findElement(By.cssSelector(".popup.is-open .popup-window .header h3"));
            if ((header != null) && (header.getText().equalsIgnoreCase("Delete Wish List"))) {

                // Check prompt text: Are you sure you want to permanently delete your XXX Wish List?
                WebElement bodyText = webBot.findElement(By.cssSelector(".popup.is-open .popup-window .content p:first-child"));
                if ((bodyText != null) && (bodyText.getText().equals("Are you sure you want to permanently delete your " + wishlistName + " Wish List?"))) {

                    // Check cancel button exists
                    WebElement cancelButton = webBot.findElement(By.cssSelector(".popup.is-open .popup-window .content .actions .close"));
                    if ((cancelButton != null) && (cancelButton.getText().equals("Cancel"))) {

                        WebElement xButton = webBot.findElement(By.cssSelector(".popup.is-open .popup-window .header .close"));
                        if (xButton != null) {

                            // Check delete button exists
                            WebElement deleteButton = webBot.findElement(By.cssSelector(".popup.is-open .popup-window .content .actions .confirm"));
                            return (deleteButton != null) && (deleteButton.getText().equals("Delete Wish List"));
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean isManageWishlistPopupDisplayed(String wishlistName) throws Throwable {
        waitForElementToExist(By.cssSelector(".manage-wishlist-popup.is-open .popup-window"));

        // Check for pop-up window
        WebElement popupWindow = webBot.findElement(By.cssSelector(".manage-wishlist-popup.is-open .popup-window"));
        if ((popupWindow != null) && (popupWindow.isDisplayed())) {

            // Check header text
            WebElement header = webBot.findElement(By.cssSelector(".manage-wishlist-popup.is-open .popup-window .header h3"));
            if ((header != null) && (header.getText().equalsIgnoreCase("Manage Wish List"))) {

                // Check input field contains current wishlist name
                WebElement inputTextField = webBot.findElement(By.cssSelector(".manage-wishlist-popup.is-open .popup-window .content form p input"));
                if ((inputTextField != null)  && (inputTextField.getAttribute("value").equals(wishlistName))) {

                    // Check cancel button exists
                    WebElement cancelButton = webBot.findElement(By.cssSelector(".manage-wishlist-popup.is-open .popup-window .content .actions .close"));
                    if ((cancelButton != null) && (cancelButton.getText().equals("Cancel"))) {

                        WebElement xButton = webBot.findElement(By.cssSelector(".manage-wishlist-popup.is-open .popup-window .header .close"));
                        if (xButton != null) {

                            // Check rename button exists
                            WebElement saveChangesButton = webBot.findElement(By.cssSelector(".manage-wishlist-popup.is-open .popup-window .content .actions .confirm"));
                            return (saveChangesButton != null) && (saveChangesButton.getText().equals("Save Changes"));
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean isManageWishlistPopupNotDisplayed() throws Throwable {
        waitForElementToNotExist(By.cssSelector(".manage-wishlist-popup.is-open .popup-window"));
        return !webBot.exists(null, cssSelector(".manage-wishlist-popup.is-open .popup-window"));
    }

    public boolean doesShareLinkInOverlayMatchWishlistUrl(String wishlistUrl) throws Throwable {
        waitForElementToExist(By.cssSelector(".get-link-wishlist-popup.is-open"));
        String linkUrl = webBot.findElement(By.cssSelector(".get-link-wishlist-popup.is-open .content p input")).getAttribute("value");

        return linkUrl.endsWith(wishlistUrl);
    }

    public void clickConfirmationButtonInOverlay() {
        webBot.findElement(By.cssSelector(".manage-wishlist-popup .content .actions .confirm")).click();
    }

    public void clickShareConfirmationButtonInOverlay() {
        webBot.findElement(By.cssSelector(".share-wishlist-popup .content .actions .confirm")).click();
    }

    public void clickCloseButtonInGetLinkOverlay() {
        webBot.findElement(By.cssSelector(".get-link-wishlist-popup .content .close")).click();
    }

    public void clickConfirmationButtonInDeleteOverlay() {
        webBot.findElement(By.cssSelector(".popup .content .actions .confirm")).click();
    }

    public void clickCancelButtonInOverlay() {
        webBot.findElement(By.cssSelector(".manage-wishlist-popup .content .actions .close")).click();
    }

    public void clickCancelButtonInDeleteOverlay() {
        webBot.findElement(By.cssSelector(".popup .content .actions .close")).click();
    }

    public void clickXCloseInOverlay() {
        webBot.findElement(By.cssSelector(".manage-wishlist-popup .header .close")).click();
    }

    public void clickXCloseInDeleteOverlay() {
        webBot.findElement(By.cssSelector(".popup .header .close")).click();
    }

    public void clickGlassPaneCloseOverlay() {
        webBot.clickAtPoint(webBot.findElement(By.cssSelector(".popup-overlay")), 5, 5);
    }

    public boolean isErrorPage() {
        return webBot.findElement(By.cssSelector("h1")).getText().equalsIgnoreCase("THIS PAGE CANNOT BE FOUND");
    }


    public void enterNewNameInToManageWishlistOverlay(String newName) {
        WebElement inputTextField = webBot.findElement(By.cssSelector(".manage-wishlist-popup.is-open .popup-window .content form p input"));
        // clear the current text
        inputTextField.clear();
        //inputTextField.sendKeys("\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b");
        inputTextField.sendKeys(newName);
    }

    public boolean doesManageOverlayTextFieldHaveFocus() {
        WebElement inputTextField = webBot.findElement(By.cssSelector(".manage-wishlist-popup.is-open .popup-window .content form p input"));
        return webBot.hasFocus((RemoteWebElement)inputTextField);
    }

    public boolean isWishlistErrorPage() {
        WebElement header = webBot.findElement(By.cssSelector("h1"));
        return header.getText().equalsIgnoreCase("THIS PAGE CANNOT BE FOUND");
    }

    public void clickTopNavWishlistLink() {
        webBot.findElement(By.cssSelector("#wish-list-link")).click();
    }

    public void waitForLoadingPaneToDisappear() throws Throwable {
        int i = 0;
        while ((!webBot.getCurrentUrl().contains("wishlist")) && i < 5) {
            Thread.sleep(2000);
            i++;
        }
        waitForElementToNotExist(By.cssSelector(".layout-product-list.is-loading"));
    }

    public void waitForPopupToAppear() throws Throwable {
        waitForElementToExist(By.cssSelector(".popup.is-open"));
    }

    public void waitForPopupToDisappear() throws Throwable {
        waitForElementToNotExist(By.cssSelector(".popup.is-open"));
    }


    public void waitUntilWishlistMenuIsClosed() throws Throwable {
        waitForElementToNotExist(By.cssSelector(".wishlist-dropdown .is-open"));
    }

    public String getSlugForPid(String pid) {
        List<WebElement> linkElements = webBot.findElements(By.cssSelector(".product-list-item"));

        for(WebElement element: linkElements) {
            if(element.findElement(By.cssSelector("img")).getAttribute("src").contains("/" + pid)) {
                if(webBot.exists(element, By.cssSelector("span.slug"))) {
                    return element.findElement(By.cssSelector("span.slug")).getText();
                } else {
                    return "NO SLUG";
                }
            }
        }
        fail("No product found with pid " + pid);
        return "ERROR";
    }

    public String getSlugForItemInPosition(Integer position) {
        List<WebElement> linkElements = webBot.findElements(By.cssSelector(".product-list-item"));

        WebElement element = linkElements.get(position-1);
        if(webBot.exists(element, By.cssSelector("span.slug"))) {
            return element.findElement(By.cssSelector("span.slug")).getText();
        } else {
            return "NO SLUG";
        }
    }

    public void clickFilterCategory(String filter) throws Throwable {

        Boolean filterMatch = false;
        List<WebElement> filterElements = getFilterElements();

        for (WebElement webElement: filterElements) {
            if(webElement.getText().toLowerCase().equals(filter.toLowerCase())) {
                webElement.click();
                filterMatch = true;
                break;
            }
        }

        if(!filterMatch) {
            fail("Could not find the filter '" + filter + "' to click");
        }
    }

    public WebElement getSelectedFilterElement() {
        return webBot.findElement(By.cssSelector(".filter-dropdown ul li.is-selected a"));
    }

    public WebElement getFilterByWebElement() {
        return webBot.findElement(By.cssSelector(".filter-dropdown span.toggle"));
    }


    public List<WebElement> getFilterElements() {
        return webBot.findElements(By.cssSelector(".filter-dropdown ul li a"));
    }

    public WebElement getTextForEmptyWishlistWhenFiltered() {
        return  webBot.findElement(By.cssSelector("div.layout-product-list"));
    }

    public boolean doesItemSoldOutErrorExist() {
       return webBot.exists(null, By.cssSelector(".error"));
    }

    public void waitForProductSliderToBeDisplayedAndReady() throws Throwable {
        Thread.sleep(1000);
        waitForElementToExist(By.cssSelector(".product-details .collapse-item"));
        slider.waitUntilReady();
    }

    public boolean isProductSliderDisplayed() throws Throwable {
        return webBot.exists(null, By.className("product-details"));
    }


    /*
     * Slider delegate methods
     */

    public void clickCloseProductSliderIcon() {
        slider.clickCloseIcon();
    }

    public WishlistV3Product getProductDetailsFromSlider() throws Throwable {
        return slider.getProductDetails();
    }

    public void clickProductSliderAddToBagButton() {
        slider.clickAddToBagButton();
    }

    public String getSliderAddToBagButtonText() {
        return slider.getAddToBagButtonText();
    }

    public void clickProductSliderAddToWishlistButton() {
        slider.clickAddToWishlistButton();
    }

    public void selectAnotherSize(String notThisSize) {
        slider.selectAnotherSize(notThisSize);
    }

    public boolean customerCareOverlayIsVisible() throws Throwable {
        for (int i=0 ; i<10 ; i++ ) {
            if (webBot.isElementPresent(By.cssSelector(".customer-care-overlay"))) {
                return true;
            }
            Thread.sleep(1000);
        }
        return false;
    }
}
