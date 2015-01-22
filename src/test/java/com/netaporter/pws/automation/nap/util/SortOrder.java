package com.netaporter.pws.automation.nap.util;

/**
 * User: x.qi@london.net-a-porter.com
 * Date: 16/05/2013
 */
public enum SortOrder {

   DEFAULT("", null), NEW_IN("new-in", true), PRICE_HIGH("price-desc", true), PRICE_LOW("price-asc", false), DISCOUNT_HIGH_TO_LOW("discount-desc", true), DISCOUNT_LOW("discount_perc-asc", false);
//legacy listing pages have NEW_IN("newIn", true)
    private String orderValue;

    private Boolean isDescending;

    SortOrder(String orderValue, Boolean descending) {
        this.orderValue = orderValue;
        this.isDescending = descending;
    }

    public String getOrderValue() {
        return orderValue;
    }

    public Boolean isDescending() {
        return isDescending;
    }


    public static SortOrder create(String orderValue) {
        for(SortOrder sortOrder: SortOrder.values()) {
            if (sortOrder.getOrderValue().equals(orderValue)) {
                return sortOrder;
            }
        }

        return null;
    }


}
