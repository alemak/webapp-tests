package com.netaporter.pws.automation.napmobile.util;

public enum Clothing {

    Clothing("Clothing", 0),
    Activewear("Activewear", 1),
    Beachwear("Beachwear", 2),
    Coats("Coats", 3),
    Dresses("Dresses", 4),
    Jackets("Jackets", 5),
    Jeans("Jeans", 6),
    Jumpsuits("Jumpsuits", 7),
    Knitwear("Knitwear", 8),
    Pants("Pants", 9),
    Shorts("Shorts", 10),
    Skirts("Skirts", 11),
    Tops("Tops", 12);

    private final String value;
    private final int shopIndex;

    private Clothing(String s, int i) {
        value = s;
        shopIndex = i;
    }

    @Override
    public String toString() { return value; }

    public int shopIndex() { return shopIndex; }
}
