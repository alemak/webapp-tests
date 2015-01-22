package com.netaporter.pws.automation.nap.cucumber.steps.wishlistv3;

import com.netaporter.pws.automation.nap.cucumber.steps.BaseNapSteps;
import com.netaporter.pws.automation.shared.pojos.WishlistV3.WishlistV3Product;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;

import java.util.List;

import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertTrue;
import static junit.framework.TestCase.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by j.christian on 07/05/2014.
 */
public class ProductDetailsSliderSteps extends BaseNapSteps {

    @Then("^the product details slider is displayed$")
    public void productDetailsSliderDisplayed() throws Throwable {
        wishListV3Page.waitForProductSliderToBeDisplayedAndReady();
    }

    @Then("^the product details slider is not displayed$")
    public void productDetailsSliderNotDisplayed() throws Throwable {
        Assert.assertFalse("Product details slider should not be displayed", wishListV3Page.isProductSliderDisplayed());
    }


    @When("^I click the close icon in the product details slider$")
    public void clickCloseIcon() throws InterruptedException {
        wishListV3Page.clickCloseProductSliderIcon();
        Thread.sleep(500); //allow the slider to close
    }

    @And("^the product details in the slider match those from the product details page$")
    public void assertProductDetailsInSliderMatchProductDetailsPage() throws Throwable {
        WishlistV3Product itemFromProdDetailsPage = ((List<WishlistV3Product>) scenarioSession.getData("singleWishlistProducts")).get(0);
        WishlistV3Product itemFromSlider = wishListV3Page.getProductDetailsFromSlider();

        Assert.assertEquals("Designer names should match", itemFromProdDetailsPage.getDesignerName(), itemFromSlider.getDesignerName());
        Assert.assertEquals("Name should match", itemFromProdDetailsPage.getProductName(), itemFromSlider.getProductName());
        if(itemFromProdDetailsPage.getSize() != null) {
            Assert.assertEquals("Size should match", itemFromProdDetailsPage.getSize(), itemFromSlider.getSize());
        }
        Assert.assertEquals("Currency symbol should match", itemFromProdDetailsPage.getCurrentPrice().getCurrencySymbol(), itemFromSlider.getCurrentPrice().getCurrencySymbol());
        Assert.assertEquals("Current prices should match", itemFromProdDetailsPage.getCurrentPrice().getValue(), itemFromSlider.getCurrentPrice().getValue());
        Assert.assertEquals("Discounted prices should match", itemFromProdDetailsPage.getDiscountedPrice(), itemFromSlider.getDiscountedPrice());
        Assert.assertEquals("Original prices should match", itemFromProdDetailsPage.getOriginalPrice(), itemFromSlider.getOriginalPrice());
        Assert.assertEquals("PID should match", itemFromProdDetailsPage.getPid(), itemFromSlider.getPid());
    }

    @And("^I click on the View More Details button in the wishlist product details slider$")
    public void clickViewMoreDetailsButton() throws Throwable {
        wishListV3Page.waitForElementToBeVisible(".product-details .more a");
        webBot.findElement(By.cssSelector(".product-details .more a")).click();
    }

    @And("^the View More Details link in the wishlist product details slider should not be displayed$")
    public void viewMoreDetailsLinkDisabled() throws Throwable {
        wishListV3Page.waitForElementToNotExist(By.cssSelector(".product-details .details .more a"));
    }

    @And("^I am on the product details page of the first item in the wishlist$")
    public void verifyOnProductDetailsPage() {
        Integer firstPID = ((List<WishlistV3Product>) scenarioSession.getData("singleWishlistProducts")).get(0).getPid();
        assertThat("We should be on the product page",
                productDetailsPage.getPidFromUrl(), equalTo(""+firstPID));
    }

    @When("^I click on the add to bag button in the wishlist product details slider$")
    public void clickOnTheAddToBagButton() throws Throwable {
        wishListV3Page.clickProductSliderAddToBagButton();
    }

    @And("^the add to bag button on the slider reads 'Check Availability'$")
    public void addToBagButtonReadsCheckAvailability() {
        String text = wishListV3Page.getSliderAddToBagButtonText();
        assertThat("Add to bag button should read 'Limited Availability'", text.equalsIgnoreCase("Check Availability"));
    }

    @And("^the shopping bag container appears and disappears$")
    public void shoppingBagContainerAppearsAndDissapears() throws Throwable {
        wishListV3Page.waitForShoppingBagPopupToBeDisplayed();
        wishListV3Page.waitForShoppingBagPopupToNotBeDisplayed();
    }

    @And("^the error 'Sorry, item sold out' appears$")
    public void checkForSoldOutError() {
        assertTrue(wishListV3Page.doesItemSoldOutErrorExist());
    }

    @When("^I click on the Add to Wishlist button in the wishlist product details slider$")
    public void clickAddToWishlistButton() {
        wishListV3Page.clickProductSliderAddToWishlistButton();
    }

    @And("^I select another size in the product details slider$")
    public void selectAnotherSize() throws Throwable {
        WishlistV3Product itemFromSlider = wishListV3Page.getProductDetailsFromSlider();
        wishListV3Page.selectAnotherSize(itemFromSlider.getSize());
        scenarioSession.putData("SELECTED_SIZE", wishListV3Page.getProductDetailsFromSlider().getSize());
    }

    @Then("^I wait until the wishlist has updated to have 2 items$")
    public void asset2ItemsInList() throws InterruptedException {
        for (int i=0; i<10; i++) {
            if (wishListV3Page.getAllWishlistV3Products().size() == 2) {
                return;
            }
            Thread.sleep(1000);
        }
        fail("Timed out waiting for 2 items to be in the wishlist");
    }

    @Then("^an item of the size I just selected is added to the front of the current wishlist$")
    public void assetItemJustAddedFirstInWishlist() {
        Assert.assertEquals("Size just selected first in list",
                scenarioSession.getData("SELECTED_SIZE").toString().replaceAll("\\s+", "") , wishListV3Page.getAllWishlistV3Products().get(0).getSize());
    }

}
