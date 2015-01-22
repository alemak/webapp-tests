package com.netaporter.pws.automation.shared.pages;

import com.netaporter.pws.automation.shared.pojos.WishlistV3.WishlistV3Product;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Base page for wishlist pages. Currently has two implementations: desktop and mobile
 */
public interface IWishlistV3Page {

    void goToAllItemsPage();
    void goToDefaultWishlist() throws Throwable;

    void clickMenuElementByText(String elem) throws Throwable;

    String getWishlistName();
    Integer numberOfItemsShowingOnPage();

    boolean isViewMoreButtonAvailable();
    public void clickViewMoreButtonAvailable() throws Throwable;
    List<WishlistV3Product> getAllWishlistV3Products();

   // public boolean isAddToBagIconGreyedOutOnMobilePage(Integer numberOfItems);
}
