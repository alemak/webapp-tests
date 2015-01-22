package com.netaporter.pws.automation.shared.pojos.WishlistV3;

import java.util.Collections;
import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: a.michael@london.net-a-porter.com
 * Date: 25/04/2013
 * Time: 3:13PM
 * (C) DevilRacing666
 */
public class WishlistV3Product implements Cloneable {


    private Integer pid;
    private String productName;
    private String designerName;
    private String priceString;
    private Price originalPrice;
    private Price discountedPrice;
    private String size;

    public static Comparator<WishlistV3Product> lowHighPriceComparator = new Comparator<WishlistV3Product>() {
//        @Override
        public int compare(WishlistV3Product wishlistV3Product1, WishlistV3Product wishlistV3Product2) {
            Double price1 = wishlistV3Product1.getCurrentPrice().getValue();
            Double price2 = wishlistV3Product2.getCurrentPrice().getValue();

            return price1.compareTo(price2);
        }
    };

    public static Comparator<WishlistV3Product> highLowPriceComparator = new Comparator<WishlistV3Product>() {
//        @Override
        public int compare(WishlistV3Product wishlistV3Product1, WishlistV3Product wishlistV3Product2) {
            Double price1 = wishlistV3Product1.getCurrentPrice().getValue();
            Double price2 = wishlistV3Product2.getCurrentPrice().getValue();

            return price2.compareTo(price1);
        }
    };

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public static Comparator<WishlistV3Product> reverseOrder = Collections.reverseOrder();

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDesignerName() {
        return designerName;
    }

    public void setDesignerName(String designerName) {
        this.designerName = designerName;
    }

    public String getPriceString() {
        return priceString;
    }

    public void setPriceString(String priceString) {
        this.priceString = priceString;
    }

    public Price getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Price salePrice) {
        this.originalPrice = salePrice;
    }


    public Price getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(Price discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public Price getCurrentPrice() {
        if (discountedPrice != null) {
            return discountedPrice;
        }

        return originalPrice;
    }

    public WishlistV3Product clone(){
        WishlistV3Product clone = new WishlistV3Product();
        clone.designerName = this.designerName;
        clone.discountedPrice = this.discountedPrice;
        clone.originalPrice = this.originalPrice;
        clone.pid = this.pid;
        clone.priceString = this.priceString;
        clone.productName = this.productName;
        clone.size = this.size;
        return clone;
    }


}
