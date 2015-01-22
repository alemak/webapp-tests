package com.netaporter.pws.automation.napmobile.util;

public enum SortOrder {

    Ascending("?sortBy=price-asc"),
    Descending("?sortBy=price-desc");

    private final String value;

    private SortOrder(String s) { value = s; }

    @Override
    public String toString() { return value; }
}
