package com.netaporter.pws.automation.napmobile.util;

public enum Beauty {

    Beauty("Beauty", 0),
    Bath_and_body("Bath and body", 1),
    Beauty_sets("Beauty sets", 2),
    Cosmetics_cases("Cosmetics cases", 3),
    Fragrance("Fragrance", 4),
    Haircare("Haircare", 5),
    Makeup("Makeup", 6),
    Nails("Nails", 7),
    Skincare("Skincare", 8);

    private final String value;
    private final int shopIndex;

    private Beauty(String s, int i) {
        value = s;
        shopIndex = i;
    }

    @Override
    public String toString() { return value; }

    public int shopIndex() { return shopIndex; }
}
