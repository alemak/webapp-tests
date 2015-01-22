package com.netaporter.pws.automation.napmobile.util;

public enum Accessories {

    Accessories("Accessories", 0),
    Belts("Belts", 1),
    Books("Books", 2),
    Collars("Collars", 3),
    Fine_jewelry("Fine jewelry", 4),
    Gloves("Gloves", 5),
    Hair_accessories("Hair accessories", 6),
    Hats("Hats", 7),
    Homeware("Homeware", 8),
    Jewelry("Jewelry", 9),
    Key_fobs("Key fobs", 10),
    Opticals("Opticals", 11),
    Pouches("Pouches", 12),
    Scarves("Scarves", 13),
    Stationery("Stationery", 14),
    Sunglasses("Sunglasses", 15),
    Technology("Technology", 16),
    Travel("Travel", 17),
    Umbrellas("Umbrellas", 18),
    Wallets("Wallets", 19),
    Watches("Watches", 20);

    private final String value;
    private final int shopIndex;

    private Accessories(String s, int i) {
        value = s;
        shopIndex = i;
    }

    @Override
    public String toString() { return value; }

    public int shopIndex() { return shopIndex; }
}
