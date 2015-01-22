package com.netaporter.pws.automation.nap.pages;

import com.netaporter.pws.automation.shared.pages.ICustomerReservationsPage;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class CustomerReservationsPage extends AbstractNapPage implements ICustomerReservationsPage{

    private static final String PAGE_NAME = "Customer Reservations";
    private static final String PATH = "customerreservations.nap";

    public static final By PRODUCT_URL_ELEMENT_LOCATOR = By.xpath("//table/tbody/tr/td[@class='special-order-description']/a");
    public static final By SUBMIT_BUTTON_LOCATOR = By.xpath("//div[@id='shopping_bag_middle']/form/div/input");

    public CustomerReservationsPage() {
        super(PAGE_NAME, PATH);
    }


    public void addReservedItemToShoppingBag(String sku) {

        WebElement buyItNow = webBot.findElement(By.id("reservation-add1"));
        buyItNow.click();

        WebElement productUrlElement = webBot.findElement(PRODUCT_URL_ELEMENT_LOCATOR);
        String productUrl = productUrlElement.getAttribute("href");
        String pid = sku.split("-")[0];

        Assert.assertTrue(productUrl.endsWith("/product/" + pid));

        WebElement submitButton = webBot.findElement(SUBMIT_BUTTON_LOCATOR);
        submitButton.click();
    }

}
