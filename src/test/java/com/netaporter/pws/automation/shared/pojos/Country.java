package com.netaporter.pws.automation.shared.pojos;

/**
 * Data structure for a Country
 */
public enum Country {

    USA("United States", "US"),
    AUSTRALIA("Australia", "AU"),
    MALAWI("Malawi", "MW"),
    UNITED_KINGDOM("United Kingdom", "UK"),
    HONG_KONG("Hong Kong", "HK");;


    private String name;
    private String code;

    Country(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

}
