package com.netaporter.pws.automation.napmobile.util;

public class SortByPair implements Pair<Integer, String> {

    private Integer key;
    private String value;

    SortByPair(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public Integer getKey() {
        return key;
    }

    @Override
    public void setKey(Integer key) {
        this.key = key;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }
}
