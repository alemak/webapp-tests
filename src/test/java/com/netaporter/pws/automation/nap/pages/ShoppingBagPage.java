package com.netaporter.pws.automation.nap.pages;

import com.netaporter.pws.automation.nap.pages.components.ProductCarouselComponent;
import com.netaporter.pws.automation.shared.pojos.Product;
import com.netaporter.test.utils.pages.driver.WaitTime;
import com.netaporter.test.utils.pages.exceptions.PageElementNotFoundException;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

@Component
@Scope("cucumber-glue")
public class ShoppingBagPage extends AbstractNapPage {

    @Autowired
    private ProductCarouselComponent productCarouselComponent;

    private static final String PAGE_NAME = "Shopping Bag";
    private static final String PATH = "shoppingbag.nap";

    private By REMOVE_ITEM_FROM_SHOPPING_BAG_LOCATOR = By.id("remove-item");
    private By SHOPPING_BAG_PAGE_NAME_LOCATOR = By.cssSelector("#top_left_purchase_path_progress > h1 > span");
    private By ERROR_LOCATOR = By.className("error");
    private By PRODUCT_IN_SHOPPING_BAG_LOCATOR = By.cssSelector(".zvezda");
    private By PROCEED_TO_PURCHASE_LOCATOR = By.xpath("//div[@id='top_right_purchase_path_progress']/a");
    private By SHIPPING_PRICE_LOCATOR = By.xpath(".//*[@id='basket_shipping_row']//td[@class='total-amount']");
    private By REMOVE_FROM_BAG_BUTTON = By.id("remove-item");
    private static final By NON_RETURNABLE_MESSAGE_LOCATOR = By.id("test");


    public ShoppingBagPage() {
        super(PAGE_NAME, PATH);
    }

    public void viewShoppingBag(){
        // instead of re-constructing the url use the DOM to retrieve the shopping bag url
        String shoppingBagUrl = webBot.executeJavascript("($('a[href*=\"shoppingbag\"]')[0]).href");
        webBot.getDriver().navigate().to(shoppingBagUrl);
    }

    public int getNumberOfItemsInShoppingBag() {
        try {
            webBot.findElement(By.id("empty_bag"), WaitTime.FOUR);
        } catch (PageElementNotFoundException e) {
            return webBot.findElements(By.cssSelector("img.zvezda"), WaitTime.FOUR).size();
        }
        return 0;
    }

    public boolean isSkuInShoppingBag(String sku) {
        List<WebElement> itemsInShoppingBag;
        try {
            itemsInShoppingBag = webBot.findElements(REMOVE_ITEM_FROM_SHOPPING_BAG_LOCATOR, WaitTime.FOUR);
        }
        catch (PageElementNotFoundException e) {
            return false;
        }

        for (WebElement item : itemsInShoppingBag) {
            String href = item.getAttribute("href");
            if (href.contains(sku)) {
                return true;
            }
        }
        return false;
    }

    public Integer isProductInShoppingBagTimes(Product product) {
        List<WebElement> items = webBot.findElements(By.id("remove-item"));
        Integer count = 0;

        for (WebElement item : items) {
            WebElement element = item.findElement(By.id("remove-item"));

            String href = element.getAttribute("href");
            if (href.contains(product.getSku())) {
                count = count + 1;
            }
        }

        return count;
    }

    public List<String> getUnitPrices() {

        List<String> prices = new ArrayList<String>();

        List<WebElement> elements = webBot.findElements(By.xpath("//table[@class='shopping_bag_items']/tbody/tr/td[6]"));

        for (WebElement priceCell : elements) {
            prices.add(priceCell.getText());
        }

        return prices;
    }

    public void clickProcceedToPurchase() {
        webBot.findElement(PROCEED_TO_PURCHASE_LOCATOR, WaitTime.FOUR).click();
    }

    public boolean doesFirstProductInShoppingBagHaveShippingRestriction(){
        return doesElementExist(By.xpath(".//*[@id='shopping_bag_middle']/table/tbody/tr[1]/td[2]/a[2]"));
    }

    public String getItemTotal() {
        WebElement element = webBot.findElement(By.xpath("//table[@class='totals_table']/tbody/tr[1]/td[3]"));

        return element.getText().trim();
    }

    public List<String> getAllVisibleCarouselPids() {
         return productCarouselComponent.getVisibleCarouselPids();
    }

    public boolean isErrorMessageDisplayed() {
        try {
            webBot.findElement(ERROR_LOCATOR, WaitTime.FOUR);
        }
        catch (PageElementNotFoundException e) {
            return false;
        }
        return true;
    }

    public String getPageTitleFromPage() {
        return webBot.findElement(SHOPPING_BAG_PAGE_NAME_LOCATOR).getText();
    }

    public void clickAddProductToWishlist(String sku) {
        List<WebElement> moveToWishlistLinks = webBot.findElements(By.cssSelector("#move-to-wishlist"));
        for (WebElement nextLink : moveToWishlistLinks) {
            if (nextLink.getAttribute("data-sku").contains(sku)) {
                nextLink.click();
                return;
            }
        }
        fail("Did not find the 'move to wishlist' link for sku " + sku);
    }

    public void loginOnMoveToWishlistForm(String emailAddress, String password) {
        webBot.switchToIFrame(By.name("registrationFrame"));
        webBot.findElement(By.id("emailaddress")).sendKeys(emailAddress);
        webBot.findElement(By.id("password")).sendKeys(password);
        webBot.findElement(By.cssSelector("input[value=Submit]")).click();
    }

    public void clickProductInShoppingBag() {
        webBot.click(PRODUCT_IN_SHOPPING_BAG_LOCATOR);
    }

    public void clickMoveToWishListOnPopup()throws Throwable {
        waitForWishlistOptionsToBePopulated();
        waitForElementToBeVisible(".popup-window .confirm.primary-button");
        webBot.findElement(By.cssSelector(".popup-window .confirm.primary-button")).click();
        Thread.sleep(2000);
    }

    private void waitForWishlistOptionsToBePopulated() throws InterruptedException {
        for (int i=0; i<10; i++) {
            try {
                // For single size item, the selector is removed
                if (! webBot.exists(null, By.cssSelector(".add-to-wishlist-popup .popup-window select"))) {
                    return;
                }

                if (! new Select(webBot.findElement(By.cssSelector(".add-to-wishlist-popup .popup-window select[name=sku]"))).getOptions().isEmpty()) {
                    return;
                }
            } catch (StaleElementReferenceException sere) {
                // Lazily loaded panel... so ignore this and try again
                sere.printStackTrace();
            }
            Thread.sleep(500);
        }
        fail("Timed out waiting for add to wishlist popup sizes to populate");
    }

    public void addCarouselProductToBag(String inStockVisiblePid) {
       closeDontMissOutPopup();
       productCarouselComponent.clickAddToBagByProductId(inStockVisiblePid);
    }

    public void clickRightAccordionArrowInCarousel() {
        productCarouselComponent.clickRightArrowInCarousel();
    }

    public String getSkuFromPidInCarousel(String pid) {
        return productCarouselComponent.getSkuFromPidInCarousel(pid);
    }

    public boolean isCarouselVisible() {
        return productCarouselComponent.isCarouselVisible();
    }

    public void clickAddToBagOnCarouselProductByIndex(int i) {
        productCarouselComponent.clickAddToBagButtonForCarouselProduct(i);
    }

    public String getCarouselProductPidByIndex(int i) {
        return productCarouselComponent.getCarouselProductPid(i);
    }

    public String clickAnyProductInCarousel() {
        closeDontMissOutPopup();
        return productCarouselComponent.clickAnyProductInCarousel();
    }

    public String getShippingPrice() {
        return webBot.findElement(SHIPPING_PRICE_LOCATOR).getText();
    }

    public String addARandomCarouselProductToBag() {
        return productCarouselComponent.addARandomCarouselProductToBag();
    }

    public void removeProductFromBag(String sku) {
        List<WebElement> elements = webBot.findElements(REMOVE_FROM_BAG_BUTTON);

        for (WebElement e : elements) {
            if(e.getAttribute("href").contains(sku)) {
                e.click();
                return;
            }
        }
    }

    public boolean isFirstProductSoldOut() {
        WebElement errorMessageElement;
        try {
            errorMessageElement = webBot.findElement(By.cssSelector(".error.basket-item-border-bottom"), WaitTime.FOUR);
        }   catch (PageElementNotFoundException e) {
            return false;
        }
        return errorMessageElement.getText().contains("Unfortunately this item is now sold out.".toUpperCase());
    }

    public String getBasketCountNumber() {
        return webBot.findElement(By.className("")).getText();
    }

    public boolean isNonReturnableMessageDisplayedForSku(String pid) {
        WebElement nonReturnableProductElement = null;

        List<WebElement> productLinks = webBot.findElements(By.className("product-link"));
        for (WebElement productLink : productLinks) {
            if (productLink.getAttribute("href").contains(pid))
                nonReturnableProductElement = productLink;
        }
        if(nonReturnableProductElement==null)
            throw new IllegalStateException("Cannot find non-returnable pid in shopping bag.");

        WebElement warningMessageElement;
        try {
            warningMessageElement = nonReturnableProductElement.findElement(By.className("non-returnable"));
        } catch (PageElementNotFoundException e) {
            return false;
        }
        return warningMessageElement.isDisplayed();
    }
}