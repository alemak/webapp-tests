package com.netaporter.pws.automation.nap.pages.components;

import com.netaporter.pws.automation.shared.pojos.WishlistV3.Price;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.WishlistV3Product;
import com.netaporter.test.utils.enums.RegionEnum;
import com.netaporter.test.utils.pages.driver.WebDriverUtil;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static org.junit.Assert.fail;

/**
 * Created by j.christian on 08/05/2014.
 */
public class WishlistV3ProductDetailsSlider {

    private WebDriverUtil webBot;

    public WishlistV3ProductDetailsSlider(WebDriverUtil webbot) {
        this.webBot = webbot;
    }

    public void clickCloseIcon() {
        webBot.findElement(By.className("collapse-item")).click();
    }

    public WishlistV3Product getProductDetails() throws Throwable {
        WishlistV3Product item = new WishlistV3Product();
        WebElement sliderDetails = getProductSliderDetailsElement();
        item.setDesignerName(getDesignerName(sliderDetails));
        item.setPid(getPID(sliderDetails));

        item.setPriceString(getPriceString(sliderDetails));
        if (item.getPriceString().contains("Now")) {
            item.setDiscountedPrice(getDiscountedPrice(sliderDetails));
            item.setOriginalPrice(getOriginalPrice(sliderDetails));
        } else {
            item.setOriginalPrice(getCurrentPrice(sliderDetails));
        }

        item.setProductName(getProductName(sliderDetails));
        item.setSize(getSize(sliderDetails));
        return item;
    }

    private String getSize(WebElement sliderDetails) throws Throwable {
        String prodSize = getSelectedProductSize();
        if (prodSize == null) {
            // Could not find the size of the product
            return null;
        } else if (prodSize.matches("[\\d]*-[\\d\\d\\d]")) {
            return "";
        } else {
            // Sizes are displayed inconsistently across site.  Removing spaces from the size to compare
            return prodSize.split(" -")[0].trim().replace(" ", "");
        }

    }

    private WebElement getProductSliderDetailsElement() {
        return webBot.findElement(By.cssSelector(".product-details .details"));
    }

    private String getDesignerName(WebElement sliderDetails) {
        return sliderDetails.findElement(By.className("designer")).getText();
    }

    /**
     * TODO refactor the next 3 methods to make common
     */
    private Price getCurrentPrice(WebElement sliderDetails) {
        Price price = null;
        if (webBot.exists(sliderDetails,By.className("price"))) {
            price = new Price();

            String currencySymbol = getCurrencySymbol(sliderDetails);
            price.setCurrencySymbol(currencySymbol);

            WebElement priceElement = sliderDetails.findElement(By.className("price"));
            String priceString = priceElement.getText().replace(currencySymbol, "").trim().replace(",", "");

            price.setValue(Double.parseDouble(priceString));
        }
        return price;
    }

    private Price getOriginalPrice(WebElement sliderDetails) {
        Price price = null;
        if (webBot.exists(sliderDetails,By.className("was"))) {
            price = new Price();

            String currencySymbol = getCurrencySymbol(sliderDetails);
            price.setCurrencySymbol(currencySymbol);

            WebElement priceElement = sliderDetails.findElement(By.className("was"));
            String priceString = priceElement.getText().replace("Was ", "").replace(currencySymbol,"").trim().replace(",","");

            price.setValue(Double.parseDouble(priceString));
        }
        return price;
    }

    private Price getDiscountedPrice(WebElement sliderDetails) {
        Price price = null;
        if (webBot.exists(sliderDetails,By.className("now"))) {
            price = new Price();

            String currencySymbol = getCurrencySymbol(sliderDetails);
            price.setCurrencySymbol(currencySymbol);

            WebElement priceElement = sliderDetails.findElement(By.className("now"));
            String priceString = priceElement.getText().replace("Now ", "").replace(currencySymbol,"").trim();

            price.setValue(Double.parseDouble(priceString));
        }
        return price;
    }

    private String getCurrencySymbol(WebElement sliderDetails) {
        String priceString = getPriceString(sliderDetails);
        return ""+priceString.replaceAll("[0-9a-zA-Z\\.,]", "").trim().charAt(0);
    }

    private String getPriceString(WebElement sliderDetails) {
        if (webBot.exists(sliderDetails, By.className("price"))) {
            return sliderDetails.findElement(By.className("price")).getText().trim();
        }
        return null;
    }

    private String getProductName(WebElement sliderDetails) {
        return sliderDetails.findElement(By.className("name")).getText().trim();
    }

    private Integer getPID(WebElement sliderDetails) {
        String productLink = sliderDetails.findElement(By.cssSelector(".more > a")).getAttribute("href");
        return Integer.parseInt(productLink.split("/")[productLink.split("/").length-1]);
    }

    private String getSelectedProductSize() throws Throwable {
        waitForSizeSelectorToBePopulated();
        Boolean multiSized = isMultipleSizedProduct();
        if (multiSized == null) {
            return null;
        }

        if (multiSized) {

            Select sizeSelector = createSelector();
            return sizeSelector.getFirstSelectedOption().getText().trim();
        }
        return getSingleSizedSKU();
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

    private By SKU_AM_LOCATOR = By.cssSelector(".size select");
    private By SKU_LOCATOR = By.cssSelector(".size select");
    private WebElement findSkuWebElement() {
        if (RegionEnum.AM.name().equals(webBot.getRegion())) {
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

    private Select createSelector() {
        WebElement skuWebElement = findSkuWebElement();

        if ((skuWebElement == null) || (!"select".equals(skuWebElement.getTagName()))) {
            return null;
        }

        return new Select(skuWebElement);
    }

    public void clickAddToBagButton() {
        webBot.findElement(By.cssSelector(".product-details .details .actions button.primary-button.add-to-bag")).click();
    }

    public String getAddToBagButtonText() {
        return webBot.findElement(By.cssSelector(".product-details .details .actions button.primary-button.add-to-bag")).getText();
    }

    public void clickAddToWishlistButton() {
        //body > div.add-to-wishlist-popup.is-open > div.popup-window > div.content > form > div.product > p > button
        webBot.findElement(By.cssSelector(".product-details .details .actions button.secondary-button.add-to-wishlist")).click();
    }

    public void selectAnotherSize(String notThisSize) {
        List<WebElement> sizeOptions = createSelector().getOptions();
        // Remove the first "Select a size" item
        sizeOptions.remove(0);

        for (WebElement nextOption : sizeOptions) {
            if (nextOption.getText().indexOf(notThisSize) == -1) {
                nextOption.click();
                return;
            }
        }
        fail("Couldn't find another size from: " + notThisSize);
    }


    public void waitUntilReady() throws InterruptedException {
        if (! webBot.exists(null, By.cssSelector("#choose-your-size"))) {
            return;
        }
        // Sizes are lazily loaded, so poll for 5 secs until ready
        for (int i=0; i < 10; i++) {
            waitForSizeSelectorToBePopulated();
            if (! createSelector().getOptions().isEmpty()) {
                Thread.sleep(1500);
                return;
            }
            Thread.sleep(500);
        }
        fail("Timed out waiting for product slider to be ready.  Size combo empty");
    }

    private void waitForSizeSelectorToBePopulated() throws InterruptedException {
        int i=0;
        while (!(webBot.findElement(By.cssSelector("select[name=sku]")).getText().contains("Choose Your Size")) && i<10) {
            Thread.sleep(500);
        }
    }
}
