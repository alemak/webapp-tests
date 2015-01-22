package com.netaporter.pws.automation.napmobile.util;

public enum Lingerie {

    Lingerie("Lingerie", 0),
    Bodysuits("Bodysuits", 1),
    Bras("Bras", 2),
    Briefs("Briefs", 3),
    Camisoles_and_chemises("Camisoles and chemises", 4),
    Corsetry("Corsetry", 5),
    Hosiery("Hosiery", 6),
    Lingerie_accessories("Lingerie accessories", 7),
    Robes("Robes", 8),
    Shapewear("Shapewear", 9),
    Sleepwear("Sleepwear", 10),
    Suspender_belts("Suspender belts", 11);

    private final String value;
    private final int shopIndex;

    private Lingerie(String s, int i) {
        value = s;
        shopIndex = i;
    }

    @Override
    public String toString() { return value; }

    public int shopIndex() { return shopIndex; }
}
