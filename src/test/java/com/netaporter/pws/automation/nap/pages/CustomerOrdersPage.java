package com.netaporter.pws.automation.nap.pages;

import com.netaporter.test.utils.pages.driver.WaitTime;
import com.netaporter.test.utils.pages.exceptions.PageElementNotFoundException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.fail;

/**
 * User: x.qi@london.net-a-porter.com
 * Date: 15/10/2012
 */
@Component
@Scope("cucumber-glue")
public class CustomerOrdersPage extends AbstractNapPage {

    private static final String PAGE_NAME = "Customer Orders";
    private static final String PATH = "CustomerOrders.nap";

    private static final Random RANDOM = new Random();

    private By TOTAL_CURRENCY_LOCATOR = By.className("total-currency");
    private By ORDER_LIST_LOCATOR = By.xpath("//table[@id='orders']/tbody/tr[2]/td/a");
    private By CUSTOMER_ORDERS_TABLE_LOCATOR = By.xpath("//table[@id='orders']/tbody/tr/td[2]/a");

    private static final By RETURN_EXCHANGE_BUTTON_LOCATOR = By.partialLinkText("Create a Return/Exchange");

    private static final By RETURN_REASON_DROPDOWN_LOCATOR = By.id("product1-reason-dropdown");
    private static final By RETURN_EXCHANGE_RADIO_BUTTON_LOCATOR = By.id("product1-return-or-exchange");
    private static final By CONTINUE_BUTTON_LOCATOR = By.name("_eventId_review");
    private static final By RETURN_CONFIRMATION_FIELD_LOCATOR = By.xpath(".//*[@id='arma']/h2");


    public CustomerOrdersPage() {
        super(PAGE_NAME, PATH);
    }


    public void viewAnyOrderSummary() {
        List<WebElement> elements = webBot.findElements(ORDER_LIST_LOCATOR);
        elements.get(RANDOM.nextInt(elements.size())).click();
    }

    public void viewOrderSummary(String orderConfirmationNumber) {
        List<WebElement> elements = webBot.findElements(CUSTOMER_ORDERS_TABLE_LOCATOR);

        for (WebElement element : elements) {
            if (element.getText().contains(orderConfirmationNumber))
            {
                element.click();
                return;
            }
        }
        fail("Order " + orderConfirmationNumber + " does not exists");
    }


    public List<String> getAllPrices() {
        ArrayList<String> prices = new ArrayList<String>();

        for (WebElement priceElement : webBot.findElements(TOTAL_CURRENCY_LOCATOR)) {
            prices.add(priceElement.getText());
        }

        for (WebElement priceElement : webBot.findElements(By.xpath("//table[@id='order-details']/tbody/tr/td[5]"))) {
            prices.add(priceElement.getText());
        }
        return prices;
    }

	public boolean isOrderPresent(Integer orderNumber) {
		return webBot.findElement(By.xpath("//table[@id='orders']/tbody/tr/td[2]/a[text()=" + orderNumber + "]")) != null;
	}

    public List<String> getAllOrderNumbers() {
        List<String> orderNumbers = new ArrayList<String>();

        for (WebElement orderNumberElement : webBot.findElements(CUSTOMER_ORDERS_TABLE_LOCATOR)) {
            orderNumbers.add(orderNumberElement.getText().trim());
        }

        return orderNumbers;
    }


    public List<String> getAllItemImageUrls() {
        List<String> imageUrls = new ArrayList<>();

        List<WebElement> imageElements = webBot.findElements(By.xpath("//table[@id='order-details']/tbody/tr/td/img"));

        for (WebElement imgElement : imageElements) {
            imageUrls.add(imgElement.getAttribute("src"));
        }

        return imageUrls;
    }

    public void clickCreateReturnExchangeButton() {
        webBot.findElement(RETURN_EXCHANGE_BUTTON_LOCATOR, WaitTime.FOUR).click();

    }

    public void refundItemForAnyReason() {
        webBot.findElement(RETURN_EXCHANGE_RADIO_BUTTON_LOCATOR, WaitTime.FOUR).click();

        Select returnReasonDropDownSelect = new Select(webBot.findElement(RETURN_REASON_DROPDOWN_LOCATOR));
        returnReasonDropDownSelect.selectByVisibleText("Not for me");

        webBot.findElements(CONTINUE_BUTTON_LOCATOR, WaitTime.FOUR).get(0).click();
    }

    public void chooseStoreCreditRefundMethod() {
        webBot.findElement(By.id("store-credit"), WaitTime.FOUR).click();
        webBot.findElement(By.id("terms"), WaitTime.FOUR).click();
    }

    public void confirmReturn() {
        webBot.findElement(By.name("_eventId_confirm"), WaitTime.FOUR).click();
    }

    public boolean isReturnConfirmed() {
        WebElement confirmationElement;
        try {
            confirmationElement = webBot.findElement(RETURN_CONFIRMATION_FIELD_LOCATOR, WaitTime.FOUR);
        } catch (PageElementNotFoundException e) {
            return false;
        }
        if (confirmationElement.getText().equalsIgnoreCase("Thank you for your request"))
            return true;

        return false;
    }

    public boolean isCreateReturnExchangeButtonVisible() {
        WebElement returnButtonElement;
        try {
            returnButtonElement = webBot.findElement(RETURN_EXCHANGE_BUTTON_LOCATOR, WaitTime.FOUR);
        } catch (PageElementNotFoundException e) {
            return false;
        }
        return returnButtonElement.isDisplayed();
    }
}
