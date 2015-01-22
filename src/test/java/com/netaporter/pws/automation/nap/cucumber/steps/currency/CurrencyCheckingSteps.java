package com.netaporter.pws.automation.nap.cucumber.steps.currency;

import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.pws.automation.nap.pages.NAPProductListPage;
import com.netaporter.pws.automation.shared.pages.IProductListPage;
import com.netaporter.pws.automation.shared.utils.MoneyAssertion;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CurrencyCheckingSteps extends BaseNapSteps {

    @Then("^I should see products priced in (.*)$")
    public void I_should_see_products_are_priced_in_required_currency(String currency) throws Throwable {
        List<String> prices = ((IProductListPage) webBot.getCurrentPage()).getAllProductsPriceAsStrings();
        String currencySymbol = currency.substring(currency.length()-1);
        MoneyAssertion.assertCorrectCurrencies(currencySymbol, prices);
    }

    @When("^I am on any Product pages$")
    public void I_am_on_any_Product_pages() throws Throwable {
        goToAnyProductListPage();
        String pid = ((NAPProductListPage) webBot.getCurrentPage()).getAnyListedPid();
        productDetailsPage.goToProduct(pid);
    }

    @Then("^I should see the product priced in (.*)$")
    public void I_should_see_the_products_are_priced_in_required_currency(String currency) throws Throwable {
        String price = productDetailsPage.getListedPriceString();
        String currencySymbol = currency.substring(currency.length()-1);
        MoneyAssertion.assertCorrectCurrency(currencySymbol, price);
    }

    @Given("^I have added several items into Shopping Bag$")
    public void I_have_added_several_items_into_Shopping_Bag() throws Throwable {
        addAnyInStockItemFromAnyProductListPageIntoShoppingBag();
        addAnyInStockItemFromAnyProductListPageIntoShoppingBag();
    }

    @When("^I am on Shopping Bag page$")
    public void I_am_on_Shopping_Bag_page() throws Throwable {
        shoppingBagPage.go();
    }

    @Then("^I should see the items priced in (.*)$")
    public void I_should_see_the_items_priced_in_USD(String currency) throws Throwable {
        String subTotalCurrency = purchasePathFlowPage.getSubTotalCurrencyInPaymentOrConfirmationPage();
        assertThat(currency, is(subTotalCurrency));
    }

    @Given("^I have registered$")
    public void I_have_registered() throws Throwable {
        registerNewAccountPage.go();
        registerNewAccountPage.enterValidRegistrationFormDetails();
        registerNewAccountPage.submitRegistrationForm();
    }

    @Given("^I have added several items into Wishlist$")
    public void I_have_added_several_items_into_Wishlist() throws Throwable {
        addAnyInStockItemFromAnyProductListPageIntoWishlist();
        addAnyInStockItemFromAnyProductListPageIntoWishlist();
    }

    @Given("^I have added one item into Wishlist$")
    public void I_have_added_one_item_into_Wishlist() throws Throwable {
        addAnyInStockItemFromAnyProductListPageIntoWishlist();
        //webdriver is too fast, it needs to wait until the page refreshes after the product was added to wishlist
        Thread.sleep(500);
    }

}
