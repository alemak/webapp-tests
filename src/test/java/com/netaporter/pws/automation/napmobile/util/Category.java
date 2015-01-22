package com.netaporter.pws.automation.napmobile.util;

public enum Category {

    All("All", -1),
    Clothing("Clothing", 0),
    Bags("Bags", 1),
    Shoes("Shoes", 2),
    Accessories("Accessories", 3),
    Lingerie("Lingerie", 5),
    Sport("Sport", 4),
    Beauty("Beauty", 5);


    private final String value;
    private final int shopIndex;

    private Category(String s, int i) {
        value = s;
        shopIndex = i;
    }

    @Override
    public String toString() { return value; }

    public int shopIndex() { return shopIndex; }
}
