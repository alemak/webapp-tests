package com.netaporter.pws.automation.nap.pages;

import com.netaporter.pws.automation.shared.components.PaymentForm;
import com.netaporter.pws.automation.shared.pages.purchasePath.PurchasePathPaymentPage;
import com.netaporter.pws.automation.shared.pojos.PaymentDetails;
import com.netaporter.pws.automation.shared.utils.CardPaymentDetails;
import com.netaporter.pws.automation.shared.utils.HtmlScrapingUtils;
import com.netaporter.test.client.product.pojos.CestaItem;
import com.netaporter.test.utils.pages.driver.WaitTime;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NAPPurchasePathPaymentPage extends PurchasePathPaymentPage {

    private static final String EXPECTED_IMAGE_COLUMN_HEADER = "ITEM";
    private static final String EXPECTED_DESCRIPTION_COLUMN_HEADER = "DESCRIPTION";
    private static final String EXPECTED_COLOR_COLUMN_HEADER = "COLOR";
    private static final String EXPECTED_SIZE_COLUMN_HEADER = "SIZE";
    private static final String EXPECTED_QUANTITY_COLUMN_HEADER = "QUANTITY";
    private static final String EXPECTED_UNIT_PRICE_COLUMN_HEADER = "UNIT PRICE";
    private static final String EXPECTED_DISCOUNT_COLUMN_HEADER = "DISCOUNT";
    private static final String EXPECTED_DUTIES_COLUMN_HEADER = "DUTIES";
    private static final String EXPECTED_TAX_COLUMN_HEADER = "TAX";
    private static final String EXPECTED_SUBTOTAL_COLUMN_HEADER = "SUBTOTAL";


    public List<String> getPidsFromPaymentSummaryPage() {
        WebElement orderSummaryElement = webBot.findElement(By.id("order_summary_section"));
        List<WebElement> basketImages = orderSummaryElement.findElements(By.className("basket-image"));
        List<String> pidsInPaymentPage = new ArrayList<String>();
        for (WebElement basketImage : basketImages) {
            String pid = basketImage.findElement(By.xpath("img")).getAttribute("alt");
            pidsInPaymentPage.add(pid);
        }
        return pidsInPaymentPage;
    }

    @Override
    public List<String> getShippingAddressSection() {
        WebElement shippingAddressDiv = webBot.findElement(By.cssSelector("div#shipping_address"));
        WebElement linkTexta = shippingAddressDiv.findElement(By.tagName("a"));
        String address = shippingAddressDiv.getText().trim();
        String linkText = linkTexta.getText().trim();
        return Arrays.asList(address.replaceAll(linkText, "").split("\n"));
    }

    @Override
    public List<String> getBillingAddressSection() {
        WebElement billingAddressDiv = webBot.findElement(By.cssSelector("div#billing_address"));
        List<WebElement> links = billingAddressDiv.findElements(By.tagName("a"));
        String address = billingAddressDiv.getText().trim();
        for(WebElement l : links)
            address = address.replaceAll(l.getText().trim(), "");
        return Arrays.asList(address.trim().split("\n"));
    }

    public List<CestaItem> getOrderItems() {
        List<CestaItem> orderItems = new ArrayList<CestaItem>();

        List<String> headers = scrapeOrderSummaryColumnsHeaders();

        List<WebElement> tableRowElements = webBot.findElements(By.xpath("//div[@id=\"order_summary_section\"]/table[@class=\"shopping_bag_items\"]/tbody/tr"));
        for (WebElement row: tableRowElements) {
            List<WebElement> tdElements = row.findElements(By.tagName("td"));

            int columnIndex = 0;
            WebElement imageColumn = tdElements.get(columnIndex++);
            WebElement productDescriptionColumn = tdElements.get(columnIndex++);
            WebElement colorColumn = tdElements.get(columnIndex++);
            WebElement sizeColumn = tdElements.get(columnIndex++);
            WebElement quantityColumn = tdElements.get(columnIndex++);
            WebElement unitPriceColumn = tdElements.get(columnIndex++);
            WebElement discountColumn = null;
            if (headers.contains(EXPECTED_DISCOUNT_COLUMN_HEADER)) {
                discountColumn = tdElements.get(columnIndex++);
            }
            WebElement dutyColumn = tdElements.get(columnIndex++);
            WebElement taxColumn = tdElements.get(columnIndex++);
            WebElement subtotalColumn = tdElements.get(columnIndex++);

            CestaItem item = scrapeItemInformation(imageColumn, productDescriptionColumn, colorColumn, sizeColumn, quantityColumn, unitPriceColumn, discountColumn, dutyColumn, taxColumn, subtotalColumn);

            orderItems.add(item);
        }

        return orderItems;
    }

    private List<String> scrapeOrderSummaryColumnsHeaders() {
        List<String> headers = new ArrayList<String>();

        List<WebElement> tableHeaderElements = webBot.findElements(By.xpath("//div[@id=\"order_summary_section\"]/table[@class=\"shopping_bag_items\"]/thead/tr/th"));

        for (WebElement webElement: tableHeaderElements) {
            String headerName = null;

            List<WebElement> subElements = webElement.findElements(By.tagName("abbr"));
            if (subElements.size() == 1) {
                headerName = subElements.get(0).getAttribute("title");
            } else {
                headerName = webElement.getText();
            }

            headers.add(headerName.trim());
        }
        return headers;
    }

    private CestaItem scrapeItemInformation(WebElement imageColumn, WebElement productDescriptionColumn, WebElement colorColumn, WebElement sizeColumn, WebElement quantityColumn, WebElement unitPriceColumn, WebElement discountColumn, WebElement dutyColumn, WebElement taxColumn, WebElement subtotalColumn) {
        CestaItem item = new CestaItem();

        boolean isVoucher = "pad_description voucher-summary".equals(productDescriptionColumn.getAttribute("class"));

        String productId = StringUtils.trimToNull(imageColumn.findElement(By.tagName("img")).getAttribute("alt"));

        String designer = null;
        String productName = null;
        String recipient = null;
        String color = null;
        String size = null;

        if (isVoucher) {
            productName = StringUtils.trimToNull(productDescriptionColumn.findElement(By.tagName("h2")).getText());
            List<WebElement> recipientElements = productDescriptionColumn.findElements(By.tagName("h3"));
            if (recipientElements.size() == 1) {
                recipient = StringUtils.trimToNull(recipientElements.get(0).getText());
            }
        } else {
            designer = StringUtils.trimToNull(productDescriptionColumn.findElement(By.className("product-manufacturer")).getText());
            productName = StringUtils.trimToNull(productDescriptionColumn.findElement(By.xpath("div[2]")).getText());
            color = StringUtils.trimToNull(colorColumn.getText());
            size = StringUtils.trimToNull(sizeColumn.getText());
        }

        String quantity = StringUtils.trimToNull(quantityColumn.getText());
        String unitPrice = StringUtils.trimToNull(unitPriceColumn.getText());
        String discount = null;
        if (discountColumn != null) {
            discount = StringUtils.trimToNull(discountColumn.getText());
        }
        String duty = StringUtils.trimToNull(dutyColumn.getText());
        String tax = StringUtils.trimToNull(taxColumn.getText());
        String subtotal = StringUtils.trimToNull(subtotalColumn.getText());

        item.setId(Integer.valueOf(productId));
        item.setDesignerName(designer);
        item.setProductName(productName);
        item.setRecipient(recipient);
        item.setColor(color);
        item.setSize(size);
        item.setQuantity(Integer.valueOf(quantity));

        item.setUnitPrice(HtmlScrapingUtils.decimalNumberStringRepresentationToBigDecimal(unitPrice));
        if (discount != null) {
                item.setDiscount(HtmlScrapingUtils.decimalNumberStringRepresentationToBigDecimal(discount));
        }
        item.setDuty(HtmlScrapingUtils.decimalNumberStringRepresentationToBigDecimal(duty));
        item.setTax(HtmlScrapingUtils.decimalNumberStringRepresentationToBigDecimal(tax));
        item.setSubtotal(HtmlScrapingUtils.decimalNumberStringRepresentationToBigDecimal(subtotal));

        item.setVoucherItem(isVoucher);

        return item;
    }

    public List<String> getShippingAddress() {
        WebElement shippingAddressDiv = webBot.findElement(By.cssSelector("div#shipping_address"));
        List<String> nameAndAddress = Arrays.asList(shippingAddressDiv.getText().split("\n"));
        List<String> shippingAddress = nameAndAddress.subList(2,nameAndAddress.size()-2);

        List<String> trimmedShippingAddress = new ArrayList<String>();

        for (String addressLine : shippingAddress) {
            trimmedShippingAddress.add(addressLine.trim());
        }
        return trimmedShippingAddress;
    }

    @Override
    public void completePaymentDetails(CardPaymentDetails cardType) {
        PaymentDetails paymentDetails = cardType.getPaymentDetails(webBot.getRegion());
        System.out.println(paymentDetails);
        setPaymentOption(cardType.getType().name());
        webBot.type(PaymentForm.CARD_NUMBER, paymentDetails.getCardNumber());
        webBot.type(PaymentForm.CARD_NAME, paymentDetails.getCardholderName());
        webBot.type(PaymentForm.SECURITY_NUMBER, paymentDetails.getSecurityNumber());
        webBot.selectElementText(PaymentForm.EXPIRY_MONTH, paymentDetails.getExpiryMonth());
        webBot.selectElementText(PaymentForm.EXPIRY_YEAR, paymentDetails.getExpiryYear());
        if (paymentDetails.getIssueNumber() != null)
            webBot.type(PaymentForm.ISSUE_NUMBER, paymentDetails.getIssueNumber());
    }

    /*
    Prashant.Ramcharan@net-a-porter.com
     */
    @Override
    public void populatePaymentCardDetails(String cardTypeId, String cardNumber){
        webBot.clickAndWait(By.id(cardTypeId), WaitTime.ONE);

        if (cardTypeId.equalsIgnoreCase("paypal")){
            return;
        }

        webBot.type(By.id("card_number"), cardNumber);
        webBot.type(By.id("cardholders_name"), "Mrs Test");
        webBot.type(By.id("security_number"), cardTypeId.equals("amex") ? "6589" : "658");
        webBot.selectElementText(By.id("expiry_month"), "06");
        webBot.selectElementText(By.id("expiryYear"), "18");

        Boolean issueNumberRequired = webBot.executeJavascript("$('#issue_number').is(\":visible\")");
        if (issueNumberRequired){
            webBot.type(By.id("issue_number"), "123");
        }
    }

    @Override
    public boolean isErrorDisplayedForId(String field) {
        return webBot.findElement(By.id(field+"Error"), WaitTime.FOUR).isDisplayed();
    }
}