package com.netaporter.pws.automation.napmobileweb.pages;

import com.netaporter.pws.automation.napmobileweb.cucumber.steps.wishlistv3.MobileAddItemsSteps;
import com.google.common.base.Predicate;
import com.netaporter.pws.automation.shared.pages.IWishlistV3Page;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.MenuItem;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.NavMenu;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.Price;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.WishlistV3Product;
import com.netaporter.test.utils.pages.exceptions.PageElementNotFoundException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.openqa.selenium.By.cssSelector;

/**
 * User: a.michael@london.net-a-porter.com
 * Date: 20/03/2013
 */

@Component
@Scope("cucumber-glue")
public class MobileWishListV3Page extends AbstractMobileNapPage implements IWishlistV3Page {

    private static final String PAGE_NAME = "Your Wish List";
    private static final String PATH = "wishlist/";

    private static final String CSS_SELECTOR_DELETE_BUTTON_PRE_SELECTED = "button.remove-item.is-pending-delete";

    public MobileWishListV3Page() {
        super(PAGE_NAME, PATH);
    }

    @Override
    public boolean isPageRegionalised() {
        return false;
    }

    public void goToAllItemsPage() {
        String originalPath = getPath();
        setPath("wishlist/all-items");
        go();
        setPath(originalPath);
    }

    public void goToWishlistListID(String listID) {
        String originalPath = getPath();
        setPath(getPath() + listID);
        go();
        setPath(originalPath);
    }


    public String getWishlistName() {
        return wishlistHeaderTitle().getText();
    }

    public WebElement wishlistHeaderTitle() {
        By element = By.cssSelector(".wishlist-header h1.name");
        return webBot.findElement(element, 5);
    }

    public List<WebElement> getSizeElementsFromPage() {
        List<WebElement> addToBagButtonElements = webBot.findElements(By.cssSelector("#choose-your-size option"));

        return addToBagButtonElements;
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


    public void clickProductThumbnail(Integer pid) {
        List<WebElement> linkElements = webBot.findElements(By.cssSelector(".product-list-item > a"));

        for(WebElement element: linkElements) {
            if(element.getAttribute("href").endsWith("/" + pid)) {
                element.click();
                return;
            }
        }

        fail("No product found with pid " + pid);
    }

    public void submitCreateListFormWithClick(String listName) {
        getCreateListTextBox().sendKeys(listName);
        getCreateListSubmitButton().click();
    }

    public void submitCreateListFormWithEnter(String listName) {
        getCreateListTextBox().sendKeys(listName);
        getCreateListTextBox().sendKeys(Keys.RETURN);
    }

    public boolean isCreateNewListTextDisplayed() {
        return getCreateListMenuItem().isDisplayed();
    }

    public boolean isCreateWishlistFormVisible() {
        return webBot.findElement(By.cssSelector("li.create-wishlist-menu-item.is-expanded")).isDisplayed();
    }

    public WebElement getCreateListMenuItem() {
        return webBot.findElement(By.cssSelector("li.create-wishlist-menu-item"));
    }

    private WebElement getCreateListSubmitButton() {
        return webBot.findElement(By.cssSelector("li.create-wishlist-menu-item.is-expanded form button"));
    }

    private WebElement getCreateListTextBox() {
        return webBot.findElement(By.cssSelector("li.create-wishlist-menu-item.is-expanded form input"));
    }

    /**
     * Gets the Nav Menu from the page.
     */
    public NavMenu getNavMenu() {
        List<WebElement> elements = getNavMenuElements();
        List<MenuItem> menuItems = new ArrayList();

        for (WebElement menuItemElem: elements) {

            MenuItem menuItem = new MenuItem();

            menuItemElem.getAttribute("class").contains("is-selected");
            boolean ticked = menuItemElem.getCssValue("background").contains("/wishlist-page/images/header-icons");
            menuItem.setTicked(ticked);

            WebElement anchor = menuItemElem.findElement(By.cssSelector("a, span"));

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
    public WebElement getMenuElementAllItems() {
        return getNavMenuElementByHref("wishlist/all-items");
    }

    public void clickFirstMenuElement() {
        WebElement element = getNavMenuElements().get(0);
        WebElement anchor = element.findElement(By.cssSelector("a, span"));
        anchor.click();
    }


    public void clickMenuElementByText(String wishlistName) {
        for(WebElement element: getNavMenuElements()) {
            WebElement anchor = element.findElement(By.cssSelector("a, span"));
            // Current styling forces the name to upperCase.  Could be an edge case with 2 wishlists
            // with same name if toUpper applied
            if (anchor.getText().equals(wishlistName.toUpperCase())) {
                anchor.click();
                return;
            }
        }
        fail("Could not find wishlist menu item: " + wishlistName);
    }

    /**
     * Gets the first navigation menu item that links to the page for the specified wishlist
     */
    public WebElement getMenuElementByWishlistId(String wishlistId) {
        return getNavMenuElementByHref("wishlist/" + wishlistId);
    }

    /**
     * Gets the first navigation menu item that has the specified text
     */
    public WebElement getNavMenuElementByText(String text) {
        for(WebElement element: getNavMenuElements()) {
            WebElement anchor = element.findElement(By.cssSelector("a, span"));
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
    public WebElement getNavMenuElementByHref(String href) {
        for(WebElement element: getNavMenuElements()) {
            WebElement anchor = element.findElement(By.cssSelector("a"));
            if(anchor.getAttribute("href").contains(href)) {
                return element;
            }
        }

        fail("Menu Item with href " + href + " not found");
        return null;
    }

    /**
     * Clicks the navigation menu, to show the drop down menu
     */
    public void clickToShowNavigationMenu() {
        try {
            Thread.sleep(2000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement menu = webBot.findElement(By.cssSelector(".wishlist-dropdown .menu"));
        if(!menu.isDisplayed()) {
            WebElement menuButton = webBot.findElement(By.cssSelector("h1.toggle"));
            menuButton.click();
        }
    }

    /**
     * Gets a list of WebElements, one for each item in the naviagtion menu
     */
    public List<WebElement> getNavMenuElements() {
        //Click menu to make items visible
        clickToShowNavigationMenu();
        return webBot.findElements(By.cssSelector("li.menu-item, li.menu-form-item, li.create-wishlist-menu-item"));
    }

    public List<WebElement> getWishlistThumbnails() {
        return webBot.findElements(By.cssSelector(".product-list-item a img"));
    }

    public List<WebElement> getWishlistSizes() {
        return webBot.findElements(By.cssSelector(".product-list-item .size"));
    }


    public WebElement wishlistContent() {
        By element = By.cssSelector(".wishlist-content");
        return webBot.findElement(element, 5);
    }

    public boolean isViewMoreButtonAvailable() {
        return webBot.exists(null, cssSelector(".pagination button.load-more"));
    }

    public void clickViewMoreButtonAvailable() {
        final Integer numProdsBeforeClick = getAllWishlistV3Products().size();

        webBot.findElement(By.cssSelector(".pagination button.load-more")).click();

        //Wait until there are now more products on the page up to a maximum of 20 seconds
        webBot.waitUntil(new Predicate<WebDriver>() {
            @Override
            public boolean apply(@Nullable WebDriver input) {
                return getAllWishlistV3Products().size() > numProdsBeforeClick;
            }
        }, 20);
    }

    public void goToDefaultWishlist() {
        // Current default is that the default list is the first item
        clickFirstMenuElement();
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
            singleProduct.setDesignerName(webElement.findElement(By.cssSelector(".designer a")).getText());
            singleProduct.setPriceString(webElement.findElement(By.cssSelector(".price")).getText());


            String priceString = webElement.findElement(By.cssSelector(".price")).getText().replace(",", "").trim();
            String currencySymbol = null;
            if (!priceString.isEmpty()) {
                currencySymbol = priceString.substring(0,1);
            }

            String priceStringWithoutCurrency = priceString.replace(currencySymbol, "");
            if (!priceStringWithoutCurrency.isEmpty()) {
                if(priceStringWithoutCurrency.indexOf(' ') == -1) {
                    // Only original price
                    Price originalPrice = new Price();
                    originalPrice.setCurrencySymbol(currencySymbol);
                    originalPrice.setValue(Double.parseDouble(priceStringWithoutCurrency));
                    singleProduct.setOriginalPrice(originalPrice);

                } else {
                    // Original and sale price
                    Price originalPrice = new Price();
                    originalPrice.setCurrencySymbol(currencySymbol);
                    originalPrice.setValue(Double.parseDouble(priceStringWithoutCurrency.split(" ")[0]));
                    singleProduct.setOriginalPrice(originalPrice);

                    Price salePrice = new Price();
                    salePrice.setCurrencySymbol(currencySymbol);
                    salePrice.setValue(Double.parseDouble(priceStringWithoutCurrency.split(" ")[1]));
                    singleProduct.setDiscountedPrice(salePrice);
                }
            }
            // Hack to work-around inconsistencies with size
            singleProduct.setSize(webElement.findElement(cssSelector(".size")).getText().replace(" ",""));
            if ((singleProduct.getSize().equals("n/a")) || (singleProduct.getSize().equalsIgnoreCase(MobileAddItemsSteps.SINGLE_SIZE))) {
                singleProduct.setSize(MobileAddItemsSteps.SINGLE_SIZE);
            }

            allProducts.add(singleProduct);
        }

        return allProducts;
    }

    public boolean isRemoveItemButtonAvailableOnEachItem() {
        By element = By.cssSelector(".product-list-item");
        List<WebElement> allProductsList = webBot.findElements(element);

        for (WebElement item : allProductsList) {
            if (!webBot.exists(item, By.cssSelector("button.remove-item"))) {
                return false;
            }
        }

        return true;
    }


    public boolean isAddToBagButtonAvailableOnEachItem() {
        By element = By.cssSelector(".product-list-item");
        List<WebElement> allProductsList = webBot.findElements(element);

        for (WebElement item : allProductsList) {
            if (!webBot.exists(item, By.cssSelector(".add-to-bag"))) {
                return false;
            }
        }

        return true;
    }

    public boolean isWishlistErrorPage() {
        WebElement header = webBot.findElement(By.cssSelector("h1"));
        return header.getText().equalsIgnoreCase("THIS PAGE CANNOT BE FOUND");
    }


    public boolean isLegacyWishlistPage() {
        String currentUrl = webBot.getCurrentUrl();

         if (!currentUrl.contains("wishlist.nap")) {
             return false;
         }

        try{
            assertTrue(webBot.isElementPresent(By.id("wishlist-content")));
        }
        catch (AssertionError e){
            return false;
        }

        return true;
    }

    public void clickHeaderWishlistLink() {
        // If Sale => Wishlist in header, if !Sale => Wishlist in footer
        WebElement wishlistLink;
        try {
            wishlistLink = webBot.findElement(By.cssSelector("#header #wishList.wishList.main-menu"));
        } catch (PageElementNotFoundException e) {
            wishlistLink = webBot.findElement(By.cssSelector("#mobileFooter a[href*='wishlist.nap']"));
        }
        wishlistLink.click();
    }

    public Integer numberOfItemsShowingOnPage() {
        return webBot.findElements(By.cssSelector(".product-list .product-list-item")).size();
    }

    /**
     * Clicks the product add to bag for the first wishlist item that has the specified product
     */
    public void clickMobileProductAddToBag(String pid) {
        List<WebElement> addToBagButtonElements = webBot.findElements(By.cssSelector("button.add-to-bag"));
        List<WebElement> linkElements = webBot.findElements(By.cssSelector(".product-list-item > a"));

        for(int i = 0; i <= addToBagButtonElements.size(); i = i + 1) {
            if(linkElements.get(i).getAttribute("href").endsWith("/" + pid)) {
                webBot.mouseOver(linkElements.get(i));
                addToBagButtonElements.get(i).click();
                return;
            }
        }

        fail("No product found with pid " + pid);
    }

    public void clickMobileAllAddToBag() throws Throwable {
        List<WebElement> addToBagButtonElements = webBot.findElements(By.cssSelector("button.add-to-bag"));
        List<WebElement> linkElements = webBot.findElements(By.cssSelector(".product-list-item > a"));

        for(int i = 0; i < addToBagButtonElements.size(); i = i + 1) {
            webBot.mouseOver(linkElements.get(i));
            addToBagButtonElements.get(i).click();
            int timeout = 0;

            while(!isMobileShoppingBagPopupDisplayed()) {
                Thread.sleep(100);
                timeout = timeout + 1;
                if (timeout >= 100) {
                    fail("Shopping Bag Popup did not appear.");
                }
            }

            timeout = 0;
            clickMobileCloseAddToBagPopup();

            while(isMobileShoppingBagPopupDisplayed()) {
                Thread.sleep(100);
                timeout = timeout + 1;
                if (timeout >= 100) {
                    fail("Shopping Bag Popup did not go away.");
                }
            }
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            }
        }

    }

    public Boolean isMobileShoppingBagPopupDisplayed() {
        List<WebElement> container = webBot.findElements(By.cssSelector(".popup-item-added"));
        return container.get(0).isDisplayed();
    }

    public void clickMobileCloseAddToBagPopup() {
        webBot.findElement(By.cssSelector(".popup-close")).click();
    }

    public boolean isAddToBagIconGreyedOutOnMobilePage(Integer numberOfItems) {
        List<WebElement> greyedOutAddToBagButtonElements = webBot.findElements(By.cssSelector(".add-to-bag.is-disabled"));
        if(numberOfItems.equals(greyedOutAddToBagButtonElements.size())) {
            return true;
        }
        return false;
    }

    public boolean doesPidExistsOnPage(String pid) {
        try {
            webBot.findElement(By.cssSelector(".product-list-item .thumbnail > img[src*='" + pid + "']"));
            return true;
        } catch (PageElementNotFoundException e) {
            return false;
        }
    }

    /**
     * Clicks the product remove for the first wishlist item that has the specified product
     *
     */
    public void clickProductRemoveFirstTime(String pid) throws Throwable{
        List<WebElement> removeButtonElements = webBot.findElements(By.cssSelector("button.remove-item"));
        List<WebElement> linkElements = webBot.findElements(By.cssSelector(".product-list-item > a"));

        for(int i = 0; i <= removeButtonElements.size(); i = i + 1) {
            if(linkElements.get(i).getAttribute("href").endsWith("/" + pid)) {
                webBot.mouseOver(linkElements.get(i));
                removeButtonElements.get(i).click();
                return;
            }
        }
        fail("No product found with pid " + pid);
    }

    public void clickProductRemoveAgain() throws Throwable{
        waitForElementToExist(By.cssSelector(CSS_SELECTOR_DELETE_BUTTON_PRE_SELECTED));
        WebElement element = webBot.getElement(By.cssSelector(CSS_SELECTOR_DELETE_BUTTON_PRE_SELECTED));
        element.click();
    }

    public void highlightProductRemove(String pid) throws Throwable{
        List<WebElement> removeButtonElements = webBot.findElements(By.cssSelector("button.remove-item"));
        List<WebElement> linkElements = webBot.findElements(By.cssSelector(".product-list-item > a"));

        for(int i = 0; i <= removeButtonElements.size(); i = i + 1) {
            if(linkElements.get(i).getAttribute("href").endsWith("/" + pid)) {
                webBot.mouseOver(linkElements.get(i));
                removeButtonElements.get(i).click();
                return;
            }
        }
        fail("No product found with pid " + pid);
    }


    public boolean isHighlightedForProductRemove(String pid) throws Throwable{
        List<WebElement> removeButtonElements = webBot.findElements(By.cssSelector("button.remove-item.is-pending-delete"));
        List<WebElement> linkElements = webBot.findElements(By.cssSelector(".product-list-item"));

        for(int i = 0; i <= removeButtonElements.size(); i = i + 1) {
            if((linkElements.get(i).findElement(By.cssSelector("a")).getAttribute("href").endsWith("/" + pid)) &&
                    webBot.exists(linkElements.get(i).findElement(By.cssSelector(".actions")),By.cssSelector("button.remove-item.is-pending-delete"))) {
                return true;
            }
        }
        fail("No product found with pid " + pid);
        return false;
    }

    public String getSlugForPid(String pid) {
        List<WebElement> linkElements = webBot.findElements(By.cssSelector(".product-list-item"));

        for(WebElement element: linkElements) {
            if(element.findElement(By.cssSelector("a")).getAttribute("href").endsWith("/" + pid)) {
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



    public void clickFilterCategory(String filter) throws Throwable {

        List<WebElement> filterElements = getFilterElements();

        Boolean filterMatch = false;
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
        return webBot.findElement(By.cssSelector(".filter-dropdown .is-selected"));
    }

    public WebElement getFilterByWebElement() {
        return webBot.findElement(By.cssSelector(".filter-dropdown span.toggle"));
    }


    public List<WebElement> getFilterElements() {
        return webBot.findElements(By.cssSelector("div.filter-dropdown select option"));
    }

    public WebElement getTextForEmptyWishlistWhenFiltered() {
        return  webBot.findElement(By.cssSelector("div.layout-product-list"));
    }
}
