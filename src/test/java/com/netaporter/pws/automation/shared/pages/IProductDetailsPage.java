package com.netaporter.pws.automation.shared.pages;

import com.netaporter.pws.automation.shared.pojos.Product;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: J.Christian@net-a-porter.com
 * Date: 26/03/2013
 * Time: 14:26
 * To change this template use File | Settings | File Templates.
 */
public interface IProductDetailsPage {
    List<String> getSKUs();

    Product addIntoShoppingBag(String sku);

    void addIntoWishlist(String sku) throws Throwable ;


    void goToProduct(String productId);

}
