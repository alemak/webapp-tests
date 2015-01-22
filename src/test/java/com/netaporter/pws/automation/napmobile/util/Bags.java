package com.netaporter.pws.automation.napmobile.util;

public enum Bags {

    Bags("Bags", 0),
    Backpacks("Backpacks", 1),
    Belt_bags("Belt bags", 2),
    Clutch_bags("Clutch bags", 3),
    Shoulder_bags("Shoulder bags", 4),
    Tote_bags("Tote bags", 5),
    Travel_bags("Travel bags", 6);

    private final String value;
    private final int shopIndex;

    private Bags(String s, int i) {
        value = s;
        shopIndex = i;
    }

    @Override
    public String toString() { return value; }

    public int shopIndex() { return shopIndex; }
}
