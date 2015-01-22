package com.netaporter.pws.automation.napmobileweb.cucumber.steps.wishlistv3;

import com.netaporter.pws.automation.napmobileweb.cucumber.steps.BaseMobileNapSteps;
import com.netaporter.pws.automation.napmobileweb.pages.MobileShoppingBagPage;
import com.netaporter.pws.automation.shared.apiclients.ProductServiceAPIClient;
import com.netaporter.pws.automation.shared.pojos.Product;
import com.netaporter.test.client.product.dsl.ProductDsl;
import com.netaporter.test.client.product.pojos.SearchableProduct;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: a.michael@london.net-a-porter.com
 * Date: 07/11/2013
 * Time: 4:49PM
 * (C) DevilRacing666
 */
public class MobileAddToBagSteps extends BaseMobileNapSteps {

    @Autowired
    MobileShoppingBagPage mobileShoppingBagPage;

    @Autowired
    ProductServiceAPIClient productServiceAPIClient;

    @When("^I click on the mobile add to bag on wishlist item (\\d+)$")
    public void addToBag(int itemNumber) throws Throwable {
        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");

        String pid = skuToPid(skuList.get(itemNumber-1));
        Thread.sleep(1000);
        wishListV3Page.clickMobileProductAddToBag(pid);

        int timeout = 0;

        while(!wishListV3Page.isMobileShoppingBagPopupDisplayed()) {
            Thread.sleep(100);
            timeout = timeout + 1;
            if (timeout >= 100) {
                fail("Shopping Bag Popup did not appear.");
            }
        }

        timeout = 0;
        wishListV3Page.clickMobileCloseAddToBagPopup();

        while(wishListV3Page.isMobileShoppingBagPopupDisplayed()) {
            Thread.sleep(100);
            timeout = timeout + 1;
            if (timeout >= 100) {
                fail("Shopping Bag Popup did not go away.");
            }
        }
    }

    @Then("^wishlist item (\\d+) should be in the mobile shopping bag (\\d+) times?$")
    public void checkBagForPid(int itemNumber, int numberOfTimesExpected) throws Throwable {
        List<String> skuList = (List<String>) scenarioSession.getData("listOfSkus");
        String sku = skuList.get(itemNumber-1);

        mobileShoppingBagPage.go();
Thread.sleep(2000);
        SearchableProduct searchableProduct = new SearchableProduct(1,"TEMP","TEMP");
        Product product = new Product(searchableProduct, sku);

        int numberOfTimesActual = mobileShoppingBagPage.isProductInShoppingBagTimes(product);

        assertEquals("Expected sku " + (itemNumber) + " (" + sku + ") to exist " + numberOfTimesExpected + " times:"
                ,numberOfTimesExpected,numberOfTimesActual);
    }

    @And("^I have 2 in stock skus from the same pid for mobile$")
    public void getTwoSkusFromOnePid() throws Throwable {
        List<String> skuList = new ArrayList<String>();
        if ((scenarioSession.getData("listOfSkus")) != null) {
            skuList = (List<String>) scenarioSession.getData("listOfSkus");
        }
        List<String> foundSkus = productServiceAPIClient.getInStockAndVisibleSkus(webBot.getRegion(), 1, ProductDsl.ProductAvailability.IN_STOCK,ProductDsl.ProductCategory.LINGERIE);
        skuList.addAll(foundSkus);
        scenarioSession.putData("listOfSkus",skuList);

        String pid = skuList.get(0).split("-")[0];
        productDetailsPage.goToProduct(pid);

        List<WebElement> sizeOptions = wishListV3Page.getSizeElementsFromPage();

        for ( WebElement sizeOption : sizeOptions ) {
            if ( (!(sizeOption.getText().contains("sold") || sizeOption.getText().contains("Choose")))
                    && (!(sizeOption.getAttribute("value").contains(skuList.get(0))))) {
                skuList.add(sizeOption.getAttribute("value"));
            }
        }
        //skuList = Lists.reverse(skuList);
        scenarioSession.putData("listOfSkus",skuList);
        System.out.println(skuList+"\n");
    }

    @And("^I click on add to bag on all items on mobile$")
    public void clickAllAddToBag() throws Throwable {
        wishListV3Page.clickMobileAllAddToBag();
    }
    @Then("^all (\\d+) items should have the add to bag button disabled$")
    public void checkAddToBagButtons(Integer numberOfItems) throws Throwable {
        assertTrue("Add To Bag icons incorrectly available on SOLD OUT items.", wishListV3Page.isAddToBagIconGreyedOutOnMobilePage(numberOfItems));
    }
}
