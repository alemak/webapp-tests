package com.netaporter.pws.automation.shared.steps.purchasePath;

import com.netaporter.test.client.product.pojos.OrderItemSource;
import com.netaporter.test.utils.network.NetworkUtil;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import junit.framework.TestCase;
import org.junit.Assert;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
@Component
public class PurchasePathOrderConfirmationSteps extends BasePurchasePathStep {

    @Given("^My device identifier and user agent are recorded against my order items$")
    public void checkDeviceIdentifierIsRecordedAgainstTheOrderItemSource() {
        int orderNumber = purchasePathConfirmationPage.getOrderNumber();
        Assert.assertNotNull(orderNumber);

        String ipAddress = NetworkUtil.getFirstNonLoopbackAddress(true, false).getHostAddress();
        List<OrderItemSource> orderItems = productDataAccess.getLegacyDBClient().findOrderItemSource(webBot.getRegionEnum(), orderNumber);
        assertTrue(orderItems.size() > 0);

        for(OrderItemSource item : orderItems) {
            assertTrue("IP address stored in database doesn't match address of client", item.getIpAddress().contains(ipAddress));
            Assert.assertEquals(webBot.getUserAgent(), item.getUserAgent());
        }

    }

    @Given("^My order is confirmed$")
    public void orderIsConfirmed() {
        assertThat(webBot.getCurrentUrl(), containsString("orderconfirmation"));
        int orderNumber = purchasePathConfirmationPage.getOrderNumber();
        Assert.assertNotNull(orderNumber);
        scenarioSession.putData("latestOrderId", orderNumber);
    }

    @Then("^I verify the payment is not authorized$")
    public void verifyPaymentAuthorization(){
       assertTrue(purchasePathPaymentPage.getErrorMessages().get(0).contains("your payment has not been authorized"));
    }

    @Given("^I have a total of (.*) products in my order$")
    public void orderContainsXNumberOfProducts(int productCount) {
        Assert.assertEquals(productCount, purchasePathConfirmationPage.getProductItemsList().getNumberOfItems());
    }

    @Given("^My order shipping method is (.*)$")
    public void orderShippingMethod(String shippingMethod) {
        assertTrue(purchasePathConfirmationPage.getShippingMethod().startsWith(shippingMethod));
    }

    @Then("^The Order Confirmation page is NOT displayed$")
    public void The_Order_Confirmation_page_is_NOT_displayed() {
        assertThat(webBot.getCurrentUrl(), not(containsString("orderconfirmation")));
    }

    @And("^the Order Confirmation page should display the product\\(s\\)$")
    public void the_Order_Confirmation_page_should_display_the_products() throws Throwable {
        Collection<String> skusFromSession = getSkusFromScenarioSession();
        List<String> pidsFromConfirmationPage = purchasePathConfirmationPage.getPidsFromConfirmationPage();

        int numberOfSkusFromSession = skusFromSession.size();
        int numberOfProductsFromConfirmationPage = pidsFromConfirmationPage.size();
        assertEquals("The number of products in the Order Confirmation page ("+numberOfProductsFromConfirmationPage+") does not match the number of products from session ("+numberOfSkusFromSession+").", numberOfSkusFromSession, numberOfProductsFromConfirmationPage);

        for (String skuFromSession : skusFromSession) {
            assertTrue("Sku " + skuFromSession + "does not exist on the Order Confirmation page. \n The pids on the order confirmation page are: "+pidsFromConfirmationPage, pidsFromConfirmationPage.contains(getPidFromSku(skuFromSession)));
        }
        scenarioSession.removeData(ITEMS_ADDED_TO_BAG);
        scenarioSession.removeData(ITEMS_ADDED_TO_BAG_WITH_STOCK);
    }

    @Then("^the nonreturnable product warning message is displayed in the order confirmation page$")
    public void the_nonreturnable_product_warning_message_is_displayed_in_the_order_confirmation_page() throws Throwable {
        String nonReturnableSku = getSkusFromScenarioSession().get(0);
        String pidFromSku = getPidFromSku(nonReturnableSku);
        TestCase.assertTrue("The nonreturnable warning message is not displayed on the order confirmation page for sku "+nonReturnableSku, purchasePathConfirmationPage.isNonReturnableMessageDisplayedForPid(pidFromSku));
    }
}