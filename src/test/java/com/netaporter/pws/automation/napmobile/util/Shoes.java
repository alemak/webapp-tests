package com.netaporter.pws.automation.napmobile.util;

public enum Shoes {

    Shoes("Shoes", 0),
    Boots("Boots", 1),
    Flat_shoes("Flat shoes", 2),
    Pumps("Pumps", 3),
    Sandals("Sandals", 4),
    Sneakers("Sneakers", 5);

    private final String value;
    private final int shopIndex;

    private Shoes(String s, int i) {
        value = s;
        shopIndex = i;
    }

    @Override
    public String toString() { return value; }

    public int shopIndex() { return shopIndex; }
}
