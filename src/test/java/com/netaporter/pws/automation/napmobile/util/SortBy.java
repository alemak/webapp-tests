package com.netaporter.pws.automation.napmobile.util;

public enum SortBy {

    Sort_By_Price("Sort By Price"),
    High("High"),
    Low("Low");

    private final String value;

    private SortBy(String s) { value = s; }

    @Override
    public String toString() { return value; }
}
