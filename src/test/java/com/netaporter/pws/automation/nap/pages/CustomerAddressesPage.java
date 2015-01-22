package com.netaporter.pws.automation.nap.pages;

import com.netaporter.pws.automation.shared.components.AddressComponent;
import com.netaporter.pws.automation.shared.pojos.Address;
import com.netaporter.pws.automation.shared.pojos.Country;
import com.netaporter.pws.automation.shared.utils.WaitUtil;
import com.netaporter.test.utils.pages.driver.WebDriverUtil;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Encapsulates the functionality on the Customer Addresses page
 */
@Component
@Scope("cucumber-glue")
public class CustomerAddressesPage extends AbstractNapPage {

    private static final String PAGE_NAME = "My Address Book";
    private static final String PATH = "customeraddresses.nap";

    private By ADD_SHIPPING_ADDRESS_LINK = By.cssSelector("#addresses a[href $='?addressType=SHIPPING']");
    private static final By ADD_BILLING_ADDRESS_LINK = By.cssSelector("#addresses a[href $='?addressType=BILLING']");
    private By SAVE_ADDRESS_BUTTON = By.name("saveAddress");

    @Autowired
    private AddressComponent addressComponent;

    public CustomerAddressesPage() {
        super(PAGE_NAME, PATH);
    }

    public void clickNewShippingAddress() throws WebDriverUtil.WebDriverUtilException{
        webBot.click(ADD_SHIPPING_ADDRESS_LINK);
        webBot.focusToOtherWindow();
    }

    public void clickNewBillingAddress() throws WebDriverUtil.WebDriverUtilException {
        webBot.click(ADD_BILLING_ADDRESS_LINK);
        webBot.focusToOtherWindow();
    }

   public void fillAddress(String country) {
       if (country.equals(Country.USA.getName())) {
           addressComponent.fillInAddress(Address.createUSNonPremierAddress());
       } else if (country.equals((Country.UNITED_KINGDOM.getName()))) {
           addressComponent.fillInAddress(Address.createNAPAddress());
       } else if (country.equals(Country.HONG_KONG.getName())) {
           addressComponent.fillInAddress(Address.createHKPremierAddress());
       } else {
           addressComponent.fillInAddress(Address.createAnotherAddress(country));
       }
   }

   public void saveAddress() throws WebDriverUtil.WebDriverUtilException {
       webBot.click(SAVE_ADDRESS_BUTTON);
       webBot.focusOnParentWindow();
       WaitUtil.waitFor(300);
   }
}