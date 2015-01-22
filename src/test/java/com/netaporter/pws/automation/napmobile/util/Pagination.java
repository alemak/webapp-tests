package com.netaporter.pws.automation.napmobile.util;

public enum Pagination {

    Next("Next"),
    Previous("Previous");

    private final String value;

    private Pagination(String s) { value = s; }

    @Override
    public String toString() { return value; }
}
