package com.netaporter.pws.automation.shared.pages.paypal;

import com.netaporter.pws.automation.shared.utils.HtmlScrapingUtils;
import com.netaporter.test.client.product.pojos.CestaItem;
import com.netaporter.test.utils.pages.driver.WaitTime;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Scope("cucumber-glue")
public class PayPalReviewPage extends PaypalBasePage {

    private static final String PAGE_NAME = "Paypal confirmation";
    private static final String PATH = "";
    private static final By POSTAGE_PACKAGING_AMOUNT = By.id("displayShippingAmount");
    private static final By STORE_CREDIT_AMOUNT = By.id("storeCredit");

    private static final String GIFT_CARD_KEYWORD = "GIFT CARD";

    private static final String DESCRIPTION_LABEL_STRIPPING_REGEXP = "(?i)Item description:";
    private static final String QUANTITY_LABEL_STRIPPING_REGEXP = "(?i)Quantity:";

    public PayPalReviewPage() {
        super(PAGE_NAME,PATH);
    }

    public boolean isActiveAndDisplayed() {
        webBot.waitForElementAttributeMatch(By.id("reviewModule"), "class", "panel active", WaitTime.DEFAULT);
        return true;
    }

    public void payNow(){
        webBot.clickAndWaitForJQueryCompletion(By.name("continue"));
    }

    public List<String> getShippingAddress() {
        WebElement shippingAddressDiv = webBot.findElement(By.cssSelector("div.reviewInfo div div"));
        String nameAndAddressString = shippingAddressDiv.getText();
        List<String> nameAndAddress = Arrays.asList(nameAndAddressString.split("\n"));
        List<String> shippingAddress = nameAndAddress.subList(1, nameAndAddress.size());

        List<String> trimmedShippingAddress = new ArrayList<String>();
        for (String addressLine : shippingAddress) {
            trimmedShippingAddress.add(addressLine.trim());
        }
        return trimmedShippingAddress;
    }

    public BigDecimal getTotalAmount(){
        String payPalTotalAmountText = webBot.getElementText(By.xpath(".//*[contains(@class,'finalTotal')]"));

        BigDecimal formattedAmount = HtmlScrapingUtils.decimalNumberStringRepresentationToBigDecimal(payPalTotalAmountText);
        return formattedAmount;
    }

    public BigDecimal getStoreCreditAmountInPaypal() {
        String paypalStoreCreditAmount = webBot.getElementText(By.xpath("(.//a[contains(.,'STORE CREDIT')]/following::*[contains(@class,'amount')])[1]"));
        return HtmlScrapingUtils.decimalNumberStringRepresentationToBigDecimal(paypalStoreCreditAmount);
    }

    public BigDecimal getPaypalPostageAndPackagingAmount() {
        BigDecimal paypalPostagePackagingAmount = new BigDecimal("0.00");

        if(webBot.exists(null, By.id("displayShippingAmount"))) {
            String paypalPostagePackagingAmountText = webBot.findElement(POSTAGE_PACKAGING_AMOUNT).getText();
            paypalPostagePackagingAmount = HtmlScrapingUtils.decimalNumberStringRepresentationToBigDecimal(paypalPostagePackagingAmountText);
        }
        return paypalPostagePackagingAmount;
    }

    public List<CestaItem> getOrderItems() {
        List<CestaItem> orderItems = new ArrayList<CestaItem>();

        List<WebElement> itemElements = webBot.findElements(By.cssSelector("div#miniCart ol li ul li ul"));

        for (WebElement itemElement: itemElements) {
            CestaItem item = scrapeItemInformation(itemElement);

            if (item == null) {
                continue;
            }

            orderItems.add(item);
        }

        return orderItems;
    }

    private Integer scrapeItemQuantity(WebElement webElement) {
        String quantityRepresentation = webElement.getText().replaceAll(QUANTITY_LABEL_STRIPPING_REGEXP, "").trim();
        return Integer.valueOf(quantityRepresentation);
    }

    private CestaItem scrapeItemInformation(WebElement itemInformationElement) {
        CestaItem item = new CestaItem();

        List<WebElement> attributeElements = itemInformationElement.findElements(By.tagName("li"));

        if (attributeElements.size() == 0) {
            return null;
        }

        int attributesIndex = 0;
        WebElement itemTitleRow = attributeElements.get(attributesIndex++);

        if (isStoreCreditTypeItem(itemTitleRow)) {
            return null;
        }

        WebElement nameElement = itemTitleRow.findElement(By.cssSelector("span.name a"));

        String itemName = (String) ((JavascriptExecutor)webBot.getDriver()).executeScript("return arguments[0].innerText;", nameElement);
        String recipient = null;
        String description = null;
        BigDecimal unitPrice = null;
        Integer quantity = null;
        boolean isVoucher = false;

        if (itemName.toUpperCase().contains(GIFT_CARD_KEYWORD)) {

            isVoucher = true;

            WebElement voucherInfoElement = attributeElements.get(attributesIndex++);

            unitPrice = scrapeItemPrice(voucherInfoElement);

            if (unitPrice == null) {
                recipient = StringUtils.trimToNull(voucherInfoElement.getText().replaceAll(DESCRIPTION_LABEL_STRIPPING_REGEXP, ""));

                voucherInfoElement = attributeElements.get(attributesIndex++);
                unitPrice = scrapeItemPrice(voucherInfoElement);
            }

        } else {
            WebElement voucherInfoElement = attributeElements.get(attributesIndex++);
            description = StringUtils.trimToNull(voucherInfoElement.getText().replaceAll(DESCRIPTION_LABEL_STRIPPING_REGEXP, ""));

            voucherInfoElement = attributeElements.get(attributesIndex++);
            unitPrice = scrapeItemPrice(voucherInfoElement);
        }

        WebElement quantityElement = attributeElements.get(attributesIndex++);
        quantity = scrapeItemQuantity(quantityElement);

        item.setDesignerName(itemName);
        item.setProductName(itemName);
        item.setRecipient(recipient);
        item.setColor(description);
        item.setSize(description);
        item.setQuantity(quantity);
        item.setSubtotal(unitPrice);
        item.setVoucherItem(isVoucher);

        return item;
    }

    private BigDecimal scrapeItemPrice(WebElement webElement) {
        BigDecimal unitPrice = null;

        List<WebElement> priceElements = webElement.findElements(By.tagName("span"));
        if (priceElements.size() == 1) {
            unitPrice = HtmlScrapingUtils.decimalNumberStringRepresentationToBigDecimal(priceElements.get(0).getText());
        }

        return unitPrice;
    }

    private boolean isStoreCreditTypeItem(WebElement itemTitleElement) {
        WebElement amountElement = itemTitleElement.findElement(By.xpath("span[@class=\"amount\"]"));
        String amountRepresentation = amountElement.getText();

        BigDecimal amount = HtmlScrapingUtils.decimalNumberStringRepresentationToBigDecimal(amountRepresentation);
        return amount.compareTo(BigDecimal.ZERO) < 0;
    }
}
