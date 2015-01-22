package com.netaporter.pws.automation.nap.cucumber.steps.apacintlorders;

import com.netaporter.pws.automation.nap.components.RegisterAccountForm;
import com.netaporter.pws.automation.nap.cucumber.steps.account.MyFavouriteDesignersSteps;
import com.netaporter.pws.automation.nap.cucumber.steps.purchasepath.PurchasePathFlowSteps;
import com.netaporter.pws.automation.shared.pojos.Product;
import com.netaporter.pws.automation.shared.steps.purchasePath.BasePurchasePathStep;
import com.netaporter.pws.automation.shared.utils.CardPaymentDetails;
import com.netaporter.test.utils.enums.RegionEnum;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * User: x.qi@london.net-a-porter.com
 * Date: 18/12/2012
 */
@Scope("cucumber-glue")
public class ApacIntlOrdersSteps extends BasePurchasePathStep {

//    private static final String REGISTERED_EMAIL_ADDRESS_KEY = "registeredEmailAddress";

    @Given("^I have successfully registered on (AM|APAC|INTL|am|apac|intl) channel website$")
    public void I_have_successfully_registered_on_desired_channel_website(String region) throws Throwable {
        goToDesiredChannel(region);
        //dave environments currently go to a content page which points to live after switching channel, so forcing the tests to go to home page instead
        homePage.go();
        homePage.clickSignInLink();
        signInPage.clickRegisterNow();
        String emailAddress = registerNewAccountPage.enterValidRegistrationFormDetails();
        scenarioSession.putData(REGISTERED_EMAIL_ADDRESS, emailAddress);
        registerNewAccountPage.submitRegistrationForm();
    }

    @When("^I login onto (AM|APAC|INTL|am|intl|apac) channel$")
    public void I_login_onto_desired_channel(String region) throws Throwable {
        goToDesiredChannel(region);

        homePage.clickSignInLink();

        String registeredEmailAddress = (String) scenarioSession.getData(REGISTERED_EMAIL_ADDRESS);
        signInPage.signIn(registeredEmailAddress, RegisterAccountForm.DEFAULT_REGISTRATION_PASSWORD);
    }

    private void goToDesiredChannel(String region) {
        System.setProperty("region", region.toUpperCase());
        if (RegionEnum.APAC.toString().equalsIgnoreCase(region)){
            setRegion(RegionEnum.APAC.name());
            changeCountryPage.go();
            changeCountryPage.switchToCountry("Hong Kong");
            }
        else if (RegionEnum.AM.toString().equalsIgnoreCase(region)){
            setRegion(RegionEnum.AM.name());
            changeCountryPage.go();
            changeCountryPage.switchToCountry("United States");
        }
        else if (RegionEnum.INTL.toString().equalsIgnoreCase(region)){
            setRegion(RegionEnum.INTL.name());
            changeCountryPage.go();
            changeCountryPage.switchToCountry("United Kingdom");
        }
    }

    @Then("^I can see the INTL order is listed$")
    public void I_can_see_the_INTL_order_is_listed() throws Throwable {
        List<String> allOrderNumbers = customerOrdersPage.getAllOrderNumbers();

        String orderConfirmationNumber = (String) scenarioSession.getData("orderConfirmationNumber");

        assertTrue(allOrderNumbers.contains(orderConfirmationNumber));
    }

    @When("^I click the INTL order$")
    public void I_click_the_INTL_order() throws Throwable {
        customerOrdersPage.viewOrderSummary((String) scenarioSession.getData("orderConfirmationNumber"));
    }

    @Then("^I can see the item detail$")
    public void I_can_see_the_item_detail() throws Throwable {
        List<String> urls = customerOrdersPage.getAllItemImageUrls();

        for (String sku : getSkusFromScenarioSession()) {
            String productId = getPidFromSku(sku);
            if (!isItemExist(urls, productId))
            {
                fail("Item (productId:"+ productId + ") does not exists");
            }
        }
    }

    private boolean isItemExist(List<String> urls, String productId) {
        boolean isItemExists = false;
        for (String url : urls) {
            if (url.contains(productId))
            {
                isItemExists = true;
                break;
            }
        }
        return isItemExists;
    }

    private String getProductId(Product product) {
        String sku = product.getSku();

        return sku.split("-")[0];
    }

}
