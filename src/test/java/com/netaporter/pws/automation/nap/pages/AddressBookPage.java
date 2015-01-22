package com.netaporter.pws.automation.nap.pages;

import com.netaporter.pws.automation.shared.components.AddressComponent;
import com.netaporter.pws.automation.shared.utils.WaitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: c.choudhury@london.net-a-porter.com
 * Date: 06/12/2012
 * Time: 15:26
 * To change this template use File | Settings | File Templates.
 */

@Component
@Scope("cucumber-glue")
public class AddressBookPage extends AbstractNapPage {

    private static final String PAGE_NAME = "Address Book";
    private static final String PATH = "customeraddresses.nap";

    public AddressBookPage() {
        super(PAGE_NAME, PATH);
    }

    private By ADD_SHIPPING_ADDRESS_LINK = By.xpath(".//*[@id='addresses']/span[1]/a");
    private By SHIPPING_ADDRESS_ID = By.id("shipping-address");
    private By BILLING_ADDRESS_ID = By.id("billing-address");
    private By DELETE_SHIPPING_ADDRESS_LINK = By.xpath(".//*[@id='address-left']/div[3]/a[2]");
    private By DELETE_BILLING_ADDRESS = By.xpath(".//*[@id='address-left']/div[3]/a[2]");
    private By EDIT_SHIPPING_ADDRESS_BUTTON_LOCATOR = By.xpath("//div[@id='shipping-address']/div[@id='address-left']/div[3]/a");
    private By EDIT_BILLING_ADDRESS_BUTTON_LOCATOR = By.xpath("//div[@id='billing-address']/div[@id='address-left']/div[3]/a");

    public void clickAddShippingAddress() {
        webBot.click(ADD_SHIPPING_ADDRESS_LINK);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        webBot.switchToPopUpWindow("add_edit_address_top");
    }

    public void clickAddBillingAddress() {
        WebElement addBillingAddress = webBot.findElement(By.xpath("//div[@id='addresses']/span[2]/a"));
        addBillingAddress.click();
        webBot.switchToWindow("addshipping");           
    }

	public void clickEditShippingAddress() {
		clickEditAddress(EDIT_SHIPPING_ADDRESS_BUTTON_LOCATOR);
	}

	public void clickEditBillingAddress() {
		clickEditAddress(EDIT_BILLING_ADDRESS_BUTTON_LOCATOR);
	}

	private void clickEditAddress(By locator) {
		WebElement editShippingAddress = webBot.findElement(locator);
		editShippingAddress.click();
		webBot.switchToWindow("editshipping");
	}

    public void fillInAddress(String title, String firstName, String lastName, String address1, String address2, String city, String postcode,
                                   String country, String daytimePhone) {
        if (!title.isEmpty()) {
            webBot.selectElementText(AddressComponent.TITLE_SELECT, title);
        }
        webBot.type(AddressComponent.FIRST_NAME, firstName);
        webBot.type(AddressComponent.LAST_NAME, lastName);
        webBot.type(AddressComponent.ADDRESS_1, address1);
        webBot.type(AddressComponent.ADDRESS_2, address2);
        webBot.type(AddressComponent.TOWN_CITY, city);
        webBot.type(AddressComponent.POSTCODE_LOCATOR, postcode);
        webBot.type(AddressComponent.DAY_PHONE, daytimePhone);
        webBot.selectElementText(AddressComponent.COUNTRY_SELECT, country);

        //once country is updated the page refreshes and new elements are all reloaded.
        // sleep will ensure new elements are reloaded before continuing
        WaitUtil.waitFor(1000);
    }

    public void fillInHKAddress(String title, String firstName, String lastName, String address1, String address2,
                                   String country, String state, String daytimePhone) {
        webBot.selectElementText(AddressComponent.TITLE_SELECT, title);
        webBot.type(AddressComponent.FIRST_NAME, firstName);
        webBot.type(AddressComponent.LAST_NAME, lastName);
        webBot.type(AddressComponent.ADDRESS_1, address1);
        webBot.type(AddressComponent.ADDRESS_2, address2);
        webBot.type(AddressComponent.DAY_PHONE, daytimePhone);
        webBot.selectElementText(AddressComponent.COUNTRY_SELECT, country);
        //once country is updated the page refreshes and new elements are all reloaded.
        // sleep will ensure new elements are reloaded before continuing
        WaitUtil.waitFor(1000);

        webBot.selectElementText(AddressComponent.PROVINCE_TERRITORY_STATE, state);

    }

    public void fillInUSAddress(String title, String firstName, String lastName, String address1, String address2,
                                String city, String state, String postcode, String daytimePhone) {
        webBot.selectElementText(AddressComponent.TITLE_SELECT, title);
        webBot.type(AddressComponent.FIRST_NAME, firstName);
        webBot.type(AddressComponent.LAST_NAME, lastName);
        webBot.type(AddressComponent.ADDRESS_1, address1);
        webBot.type(AddressComponent.ADDRESS_2, address2);
        webBot.type(AddressComponent.TOWN_CITY, city);
        webBot.type(AddressComponent.POSTCODE_LOCATOR, postcode);
        webBot.type(AddressComponent.DAY_PHONE, daytimePhone);
        webBot.selectElementText(AddressComponent.PROVINCE_TERRITORY_STATE, state);

        //once country is updated the page refreshes and new elements are all reloaded.
        // sleep will ensure new elements are reloaded before continuing
        WaitUtil.waitFor(1000);
    }

    public void submitAddressForm() {
        webBot.click(AddressComponent.SUBMIT);
        webBot.closePopUpWindow();
    }

    public boolean  isElementIdAreaContainText(String cssId, String text) {
        By element = By.id(cssId);
        WebElement addressArea = webBot.findElement(element);
        return  addressArea.getText().contains(text);
    }

    public void clickDeleteShippingAddress() {
        webBot.click(DELETE_SHIPPING_ADDRESS_LINK);
      }

    public void clickDeleteBillingAddress() {
        webBot.click(DELETE_BILLING_ADDRESS);
    }

    public String getShippingAddressFromPage() {
        return webBot.findElement(SHIPPING_ADDRESS_ID, 4).getText();
    }

    public String getBillingAddressFromPage() {
        return webBot.findElement(BILLING_ADDRESS_ID, 4).getText();
    }
}