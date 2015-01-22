package com.netaporter.pws.automation.shared.utils;

import com.google.common.collect.HashBasedTable;

/**
 * Created by ocsiki on 14/05/2014.
 * To be used for querying the channel_pricing table
 */
public class CountryAndCurrencyUtil {

    private static final HashBasedTable<String, String, String> countryToCurrencyMap = HashBasedTable.create();

    static {
        countryToCurrencyMap.put("United Kingdom", "GB", "£");
        countryToCurrencyMap.put("France", "Europe","€");
        countryToCurrencyMap.put("United States", "Americas", "$");
        countryToCurrencyMap.put("Hong Kong", "HK", "$");
    }

    //returns code to be used in the locality column or DEFAULT
    public static String getCountryCodeForCountryName(String countryName) {
       if (countryToCurrencyMap.row(countryName).size()!=0)
           return countryToCurrencyMap.row(countryName).keySet().iterator().next();
        else
           return "DEFAULT";
    }

    public static String getCurrencyCodeForCountryName(String countryName) {
        if (countryToCurrencyMap.row(countryName).size()!=0)
            return countryToCurrencyMap.row(countryName).values().iterator().next();
        else
            return "£";
    }
}