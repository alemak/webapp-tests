package com.netaporter.pws.automation.nap.cucumber.steps.purchasepath;

import com.netaporter.pws.automation.shared.steps.purchasePath.BasePurchasePathStep;
import com.netaporter.pws.automation.shared.utils.MoneyAssertion;
import com.netaporter.test.utils.pages.driver.WaitTime;
import com.netaporter.test.utils.pages.exceptions.PageElementNotFoundException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.Assert.*;

public class CustomerOrdersSteps extends BasePurchasePathStep {


    @When("^I am on a Order Summary page$")
    public void I_am_on_a_Order_Summary_page() throws Throwable {
        customerOrdersPage.go();
        customerOrdersPage.viewAnyOrderSummary();
    }

	@Then("^I should see all summary prices priced in (.*)$")
    public void I_should_see_all_summary_prices_priced_correctly(String currency) throws Throwable {
        List<String> allPrices = customerOrdersPage.getAllPrices();
        String currencySymbol = currency.substring(currency.length()-1);
        MoneyAssertion.assertCorrectCurrencies(currencySymbol, allPrices);
    }

	@Then("^the order appears on my orders page$")
	public void the_order_appears_on_my_orders_page() throws Throwable {
		Integer orderId = (Integer) scenarioSession.getData("latestOrderId");
		Assert.assertNotNull("no previous order has been saved", orderId);
		assertTrue(customerOrdersPage.isOrderPresent(orderId));
	}

    @And("^I click on the previously completed order number$")
    public void I_click_on_the_previously_completed_order_number() throws Throwable {
        String orderConfirmationNumber = getOrderConfirmationNumberFromSession();
        getOrderConfirmationNumberElementFromMyOrdersPage(orderConfirmationNumber).click();
    }

    private String getOrderConfirmationNumberFromSession() {
        return scenarioSession.getData(ORDER_CONFIRMATION_NUMBER);
    }

    @Then("^the previously completed order is displayed as (DISPATCHED)$")
    public void the_previously_completed_order_is_displayed_as_DISPATCHED(String expectedOrderStatus) throws Throwable {
        String orderConfirmationNumber = getOrderConfirmationNumberFromSession();

        //need to give the webapp some time to update the order status
        Thread.sleep(2500);
        webBot.reload();

        WebElement orderConfirmationNumberElement = getOrderConfirmationNumberElementFromMyOrdersPage(orderConfirmationNumber);
        String orderStatusFromPage = orderConfirmationNumberElement.findElement(By.xpath("../../td[3]")).getText();

        assertThat(orderStatusFromPage, equalToIgnoringCase(expectedOrderStatus));
    }

    //todo: move this to CustomerOrdersPage.java
    private WebElement getOrderConfirmationNumberElementFromMyOrdersPage(String orderConfirmationNumber) {
        try {
           return webBot.findElement(By.partialLinkText(orderConfirmationNumber), WaitTime.FOUR);
        }
        catch (PageElementNotFoundException e) {
            fail("Order number "+orderConfirmationNumber+"was not found in the Customer Orders Page. Failing Test");
            e.printStackTrace();
        }
        return null;
    }

    @And("^I click the Create Return/Exchange button$")
    public void I_click_the_Create_Return_Exchange_button() throws Throwable {
        customerOrdersPage.clickCreateReturnExchangeButton();
    }

    @And("^I choose to refund the item for any reason$")
    public void I_choose_to_refund_the_item_for_any_reason() throws Throwable {
        customerOrdersPage.refundItemForAnyReason();
    }

    @And("^I choose the store credit refund method$")
    public void I_choose_the_store_credit_refund_method() throws Throwable {
        customerOrdersPage.chooseStoreCreditRefundMethod();
    }

    @And("^I confirm the return$")
    public void I_confirm_the_return() throws Throwable {
        customerOrdersPage.confirmReturn();
    }

    @Then("^the Confirm Order Return page is shown$")
    public void the_Confirm_Order_Return_page_is_shown() throws Throwable {
        assertTrue("Return confirmation element was not displayed or did not contain correct text", customerOrdersPage.isReturnConfirmed());
    }

    @And("^the item is marked for return in the db$")
    public void the_item_is_marked_for_return_in_the_db() throws Throwable {
        ArrayList<String> skusFromScenarioSession = getReturnableSkusFromScenarioSession();
        String dispatchedOrderItemStatus = hybridProductDataAccess.getLegacyDBClient().getDispatchedOrderItemStatus(webBot.getRegionEnum(), skusFromScenarioSession.get(0));

        assertThat(dispatchedOrderItemStatus, equalToIgnoringCase("RETURN_REQUESTED"));
    }

    @Then("^the Create Return/Exchange button is (visible|not visible)$")
    public void the_Create_Return_Exchange_button_is_not_visible(String visibility) throws Throwable {
        if ("not visible".equalsIgnoreCase(visibility)){
            assertFalse("Create Return/Exchange button was visible for a non returnable order", customerOrdersPage.isCreateReturnExchangeButtonVisible());
        }
        else {
            assertTrue("Create Return/Exchange button was not visible for a returnable order ", customerOrdersPage.isCreateReturnExchangeButtonVisible());
        }
    }

    @Then("^the refundable product will be displayed$")
    public void the_refundable_product_will_be_displayed() throws Throwable {
        Pattern numberPattern = Pattern.compile("\\d+");
        ArrayList<String> returnableSkusFromScenarioSession = scenarioSession.getData(RETURNABLE_SKUS);

        List<WebElement> imageElements = webBot.findElements(By.className("zvezda"), WaitTime.FOUR);

        for (int i=0;i<imageElements.size();i++) {
            Matcher numberMatcher = numberPattern.matcher(imageElements.get(i).getAttribute("src"));
            if (!numberMatcher.find())
                throw new IllegalStateException("Could not get pid from page");

            assertThat(numberMatcher.group(), equalToIgnoringCase(getPidFromSku(returnableSkusFromScenarioSession.get(i))));
        }
    }

    @And("^I mark the item as (nonreturnable|returnable) in the webDB$")
    public void I_mark_the_item_as_nonreturnable_in_the_webDB(String returnability) throws Throwable {
        ArrayList<String> skus = getSkusFromScenarioSession();
        int returnabilityValue = "returnable".equalsIgnoreCase(returnability) ? 1 : 0;
        hybridProductDataAccess.getLegacyDBClient().setSkUReturnability(webBot.getRegionEnum(), skus.get(0), returnabilityValue);
        if (returnabilityValue==1) {
            scenarioSession.addCollectionData(RETURNABLE_SKUS, skus.get(0));
        }

        //set the rest of the skus as returnable, just in case we got nonreturnable skus in the order
        for (int i=1;i<skus.size();i++) {
            hybridProductDataAccess.getLegacyDBClient().setSkUReturnability(webBot.getRegionEnum(), skus.get(i), 1);
            scenarioSession.addCollectionData(RETURNABLE_SKUS, skus.get(i));
        }
    }
}
