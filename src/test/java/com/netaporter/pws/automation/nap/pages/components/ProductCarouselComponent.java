package com.netaporter.pws.automation.nap.pages.components;

import com.netaporter.test.utils.pages.component.AbstractPageComponent;
import com.netaporter.test.utils.pages.driver.WaitTime;
import com.netaporter.test.utils.pages.exceptions.PageElementNotFoundException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ocsiki on 04/09/2014.
 */
@Component
@Scope("cucumber-glue")
public class ProductCarouselComponent extends AbstractPageComponent {

    public static By RIGHT_CAROUSEL_ARROW_LOCATOR = By.xpath(".//*[@class='next']/span");
    public static By CAROUSEL_PRODUCTS_LIST_LOCATOR = By.className("product-feature-product");
    public static By PRODUCT_CAROUSEL_WRAPPER_LOCATOR = By.className("product-feature-container");
    public static By ADD_TO_BAG_IN_CAROUSEL_LOCATOR = By.className("secondary-button");
    public static By PRODUCT_IMAGE_IN_CAROUSEL_LOCATOR = By.className("product-feature-image");
    public static By PID_IN_CAROUSEL_LOCATOR = By.id("productId");
    public static By SKU_IN_CAROUSEL_LOCATOR = By.id("sku");

    public WebElement getCarouselWrapperElement() {
        //without the sleep we get stale element
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        webBot.waitForJQueryCompletion();
        return webBot.findElement(PRODUCT_CAROUSEL_WRAPPER_LOCATOR, WaitTime.FOUR);
    }

    private List<WebElement> getCarouselProductForm() {
        return getCarouselWrapperElement().findElements(CAROUSEL_PRODUCTS_LIST_LOCATOR);
    }

    public WebElement getCarouselProduct(int productIndex) {
        return getCarouselProductForm().get(productIndex);
    }

    public List<String> getVisibleCarouselPids() {
        List<String> pidList = new ArrayList<String>();
        List<WebElement> carouselProductElements = getCarouselProductForm();
        //there is no way to differentiate between visible and invisible products in the carousel, so we just select them by index (product from 5-9 are always visible at first)
        for(int i=0;i<=4;i++)
            pidList.add(i, getPidFromCarouselElement(carouselProductElements.get(i+5)));
        return pidList;
    }

    public String addARandomCarouselProductToBag() {
        Random r = new Random();
        int i = r.nextInt(5);
        clickAddToBagButtonForCarouselProduct(i);
        return getCarouselProductSku(i);
    }

    private String getPidFromCarouselElement(WebElement carouselElement) {
        return carouselElement.findElement(By.id("productId")).getAttribute("value");
    }

    public void clickAddToBagButtonForCarouselProduct(int productIndex) {
        webBot.click(getCarouselProduct(productIndex+5).findElement(ADD_TO_BAG_IN_CAROUSEL_LOCATOR));
        webBot.waitForJQueryCompletion();
        //need to wait for the page to slide back down to the carousel otherwise its staleElementException in the next step
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clickCarouselProductImage(int productIndex) {
        webBot.click(getCarouselProduct(productIndex+5).findElement(PRODUCT_IMAGE_IN_CAROUSEL_LOCATOR));
        webBot.waitForJQueryCompletion();
    }

    public String getCarouselProductPid(int productIndex) {
        return getCarouselProduct(productIndex+5).findElement(PID_IN_CAROUSEL_LOCATOR).getAttribute("value");
    }

    public String getCarouselProductSku(int productIndex) {
        return getCarouselProduct(productIndex+5).findElement(SKU_IN_CAROUSEL_LOCATOR).getAttribute("value");
    }

    //Upsell custom list
    public boolean isCarouselVisible() {
        try {
            webBot.findElement(PRODUCT_CAROUSEL_WRAPPER_LOCATOR, WaitTime.FOUR);
        }
        catch (PageElementNotFoundException e) {
            return false;
        }
        return true;
    }

    public void clickAddToBagByProductId(String inStockVisiblePid) {
        List<WebElement> carouselProducts = getCarouselProductForm();
        for (WebElement carouselProduct : carouselProducts) {
            if (carouselProduct.findElement(PID_IN_CAROUSEL_LOCATOR).getAttribute("Value").equals(inStockVisiblePid)) {
                carouselProduct.findElement(ADD_TO_BAG_IN_CAROUSEL_LOCATOR).click();
                break;
            }
        }
        //wait for page to scroll after click
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clickRightArrowInCarousel() {
        webBot.findElement(RIGHT_CAROUSEL_ARROW_LOCATOR).click();
    }

    public String getSkuFromPidInCarousel(String inStockVisiblePid) {
        List<WebElement> carouselProducts = getCarouselProductForm();
        for (WebElement carouselProduct : carouselProducts) {
            if (carouselProduct.findElement(PID_IN_CAROUSEL_LOCATOR).getAttribute("Value").equals(inStockVisiblePid)) {
                return carouselProduct.findElement(SKU_IN_CAROUSEL_LOCATOR).getAttribute("value");
            }
        }
        return null;
    }

    public String clickAnyProductInCarousel() {
        int r = new Random().nextInt(5);

        String carouselProductSku = getCarouselProductSku(r);
        clickCarouselProductImage(r);
        return carouselProductSku;
    }
}
