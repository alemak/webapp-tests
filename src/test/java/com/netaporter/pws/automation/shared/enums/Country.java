package com.netaporter.pws.automation.shared.enums;

/**
 * Created by a.makarenko on 5/22/14.
 */
public enum Country {
     GB("United Kingdom"),
     US("United States"),
     HK("Hong Kong");

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    private String countryName;
     Country(String s) {
        this.setCountryName(s);
    }
}
