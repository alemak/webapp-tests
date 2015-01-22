package com.netaporter.pws.automation.nap.cucumber.steps.address;

//import com.google.common.base.Converter;
//import com.google.common.collect.Maps;
import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
//import com.netaporter.pws.automation.nap.cucumber.steps.CommonSteps;
import com.netaporter.pws.automation.shared.pojos.Address;
import com.netaporter.pws.automation.shared.utils.WaitUtil;
import com.netaporter.test.utils.enums.RegionEnum;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

//import javax.annotation.Nullable;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import static com.netaporter.pws.automation.nap.cucumber.steps.address.AddressField.*;
//import static java.util.stream.Collectors.toMap;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: c.choudhury@london.net-a-porter.com
 * Date: 07/12/2012
 * Time: 12:09
 * To change this template use File | Settings | File Templates.
 */
public class AddressBookSteps extends BaseNapSteps {

    @Given("^I click on the add shipping address link$")
    public void I_click_on_the_add_shipping_address_link() throws Throwable {
        addressBookPage.clickAddShippingAddress();
    }

    @Given("^I click on the add billing address link$")
    public void I_click_on_the_add_billing_address_link() throws Throwable {
        addressBookPage.clickAddBillingAddress();
    }

    @When("^I add an intl address$")
    public void I_add_an_intl_address() throws Throwable {
        addressBookPage.fillInAddress("Mr", "First Name", "Last Name", "Address 1", "Address 2", "City", "W127GF", "United Kingdom", "02073453454");
        addressBookPage.submitAddressForm();
    }

    @When("^I add an am address$")
    public void I_add_an_am_address() throws Throwable {
        addressBookPage.fillInUSAddress("Mr", "First Name", "Last Name", "Address 1", "Address 2", "City", "New York", "10001", "02073453454");
        addressBookPage.submitAddressForm();
    }

    @When("^I add a HongKong address$")
    public void I_add_a_hongkong_address() throws Throwable {
        addressBookPage.fillInHKAddress("Mr", "First Name", "Last Name", "Address 1", "Address 2", "Hong Kong", "Po Toi Island", "02073453454");
        addressBookPage.submitAddressForm();
    }

    @Then("^My (intl|am|other|HongKong) (billing|shipping) address will appear on the address details page$")
    public void My_new_typeof_address_will_appear_on_the_address_details_page(String region, String billingOrShipping) throws Throwable {
        String addressFromPage = null;
        if ("shipping".equalsIgnoreCase(billingOrShipping)) {
            addressFromPage = addressBookPage.getShippingAddressFromPage();
        }
        else if ("billing".equalsIgnoreCase(billingOrShipping)) {
            addressFromPage = addressBookPage.getBillingAddressFromPage();
        }
        if ("am".equalsIgnoreCase(region)) {
            assertThat(addressFromPage, containsString("Mr"));
            assertThat(addressFromPage, containsString("First Name"));
            assertThat(addressFromPage, containsString("Last Name"));
            assertThat(addressFromPage, containsString("Address 1"));
            assertThat(addressFromPage, containsString("Address 2"));
            assertThat(addressFromPage, containsString("NY"));
            assertThat(addressFromPage, containsString("City"));
            assertThat(addressFromPage, containsString("10001"));
            assertThat(addressFromPage, containsString("UNITED STATES"));
            assertThat(addressFromPage, containsString("02073453454"));
        }
        else if ("intl".equalsIgnoreCase(region)) {
            assertThat(addressFromPage, containsString("Mr"));
            assertThat(addressFromPage, containsString("First Name"));
            assertThat(addressFromPage, containsString("Last Name"));
            assertThat(addressFromPage, containsString("Address 1"));
            assertThat(addressFromPage, containsString("Address 2"));
            assertThat(addressFromPage, containsString("City"));
            assertThat(addressFromPage, containsString("W127GF"));
            assertThat(addressFromPage, containsString("UNITED KINGDOM"));
            assertThat(addressFromPage, containsString("02073453454"));
        }
        else if ("other".equalsIgnoreCase(region)) {
            assertThat(addressFromPage, containsString("Mrs"));
            assertThat(addressFromPage, containsString("First Name2"));
            assertThat(addressFromPage, containsString("Last Name2"));
            assertThat(addressFromPage, containsString("Address 1-2"));
            assertThat(addressFromPage, containsString("Address 2-2"));
            assertThat(addressFromPage, containsString("City2"));
            assertThat(addressFromPage, containsString("W129XL"));
            assertThat(addressFromPage, containsString("MEXICO"));
            assertThat(addressFromPage, containsString("02073453452"));

        }
        else if ("HongKong".equalsIgnoreCase(region)) {
            assertThat(addressFromPage, containsString("Mr"));
            assertThat(addressFromPage, containsString("First Name"));
            assertThat(addressFromPage, containsString("Last Name"));
            assertThat(addressFromPage, containsString("Address 1"));
            assertThat(addressFromPage, containsString("Address 2"));
            assertThat(addressFromPage, containsString("Po Toi Island"));
            assertThat(addressFromPage, containsString("HONG KONG"));
            assertThat(addressFromPage, containsString("02073453454"));
        }
    }

	@When("^I edit the shipping address$")
	public void edit_the_shipping_address() throws Throwable {
		addressBookPage.clickEditShippingAddress();
		addressBookPage.fillInAddress("Mrs", "First Name2", "Last Name2", "Address 1-2", "Address 2-2", "City2", "W129XL", "Mexico", "02073453452");
		addressBookPage.submitAddressForm();
	}

	@When("^I edit the billing address$")
	public void edit_the_billing_address() {
		addressBookPage.clickEditBillingAddress();
		addressBookPage.fillInAddress("Mrs", "First Name2", "Last Name2", "Address 1-2", "Address 2-2", "City2", "W129XL", "Mexico", "02073453452");
        WaitUtil.waitFor(500);
		addressBookPage.submitAddressForm();
	}

    @Then("^the billing address used for the purchase is displayed$")
    public void the_billing_address_used_for_the_purchase_is_displayed() throws Throwable {
        Address usNonPremierAddress = Address.createUSNonPremierAddress();
        assertAddress(addressBookPage.getBillingAddressFromPage(), usNonPremierAddress);
    }

    @Then("^the shipping address used for the purchase is displayed$")
    public void the_shipping_address_used_for_the_purchase_is_displayed() throws Throwable {
        Address usPremierAddress = Address.createUSPremierAddress();
        assertAddress(addressBookPage.getShippingAddressFromPage(), usPremierAddress);
    }

    private void assertAddress(String addressFromPage, Address expectedAddress) {
        assertThat(addressFromPage, containsString(expectedAddress.getAddress1()));
        assertThat(addressFromPage, containsString(expectedAddress.getAddress2()));
        assertThat(addressFromPage, containsString(expectedAddress.getTownOrCity()));
        assertThat(addressFromPage, containsString(expectedAddress.getCountry().toUpperCase()));
        assertThat(addressFromPage, containsString(expectedAddress.getPostcode()));
    }

    @Then("^the shipping address has been updated$")
    public void the_shipping_address_has_been_updated() throws Throwable {
        Address anotherAddress = Address.createAnotherAddress("United States", "New York", "10001");
        assertAddress(addressBookPage.getShippingAddressFromPage(), anotherAddress);
    }

    @When("^I delete the shipping address in my account$")
    public void I_delete_the_shipping_address_in_my_account() throws Throwable {
        addressBookPage.clickDeleteShippingAddress();
    }

    @Then("^the (shipping|billing) address in the address book is removed$")
    public void the_shipping_address_in_the_address_book_is_removed(String addressType) throws Throwable {
        WebElement addressTable = webBot.findElement(By.id(addressType + "-address"));
        List<WebElement> rightAddressElementList = addressTable.findElements(By.id("address-right"));
        assertThat(rightAddressElementList.size(), is(0));
    }

    @When("^I delete the billing address in my account$")
    public void I_delete_the_billing_address_in_my_account() throws Throwable {
        addressBookPage.clickDeleteBillingAddress();
    }

    @And("^I add an address$")
    public void I_add_an_address() throws Throwable {
        switch (RegionEnum.valueOf(webBot.getRegion())) {
            case INTL:
                I_add_an_intl_address();
                break;
            case AM:
                I_add_an_am_address();
                break;
            case APAC:
                I_add_a_hongkong_address();
                break;
        }
    }

    @Then("^My (shipping|billing) address will appear on the address details page$")
    public void My_address_type_address_will_appear_on_the_address_details_page(String billingOrShipping) throws Throwable {
       My_new_typeof_address_will_appear_on_the_address_details_page(webBot.getRegion(),billingOrShipping);
    }

    @And("^I edit the shipping address again$")
    public void I_edit_the_shipping_address_again() throws Throwable {
        addressBookPage.clickEditShippingAddress();
        addressBookPage.fillInAddress("Mrs", "Edited First Name", "Edited Last Name", "Address 1-2", "Address 2-2", "City2", "W129XL", "Mexico", "02073453452");
        addressBookPage.submitAddressForm();
    }

    @Then("^the second edit of the shipping address is saved correctly$")
    public void the_second_edit_of_the_shipping_address_is_saved_correctly() throws Throwable {
        String shippingAddressFromPage = addressBookPage.getShippingAddressFromPage();
        assertThat(shippingAddressFromPage, containsString("Edited First Name"));
        assertThat(shippingAddressFromPage, containsString("Edited Last Name"));
    }


//    @And("^I have a (shipping|billing) address of:$")
//    public void ensureAddressDetails(String addressType, Map<String, String> rawAddressFields) throws Throwable {
//        addressBookPage.go();
//
//        if (addressType.equals("shipping")) {
//            addressBookPage.clickAddShippingAddress();
//        }
//        else {
//            addressBookPage.clickAddBillingAddress();
//        }
//
//        Map<String, String> addressFields = rawAddressFields.keySet().stream()
//                .collect(toMap(s -> s.toLowerCase().replaceAll("\\s", ""), rawAddressFields::get));
//
//        addressBookPage.fillInAddress(
//                title.from(addressFields),
//                firstName.from(addressFields),
//                lastName.from(addressFields),
//                address1.from(addressFields),
//                address2.from(addressFields),
//                town.from(addressFields),
//                postcode.from(addressFields),
//                country.from(addressFields),
//                telephone.from(addressFields));
//
//        addressBookPage.submitAddressForm();
//    }
}
