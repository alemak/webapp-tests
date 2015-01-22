package com.netaporter.pws.automation.nap.util;

import java.util.HashMap;

/**
 * Created by ocsiki on 28/10/14.
 */
public class CountryAndLanguageIndex {

    private static final HashMap<String, String> LANGUAGES_INDEX = new HashMap<String, String>() {
        {
            put("French", "FR");
            put("German", "DE");
            put("Chinese", "ZH");
            put("English", "EN");
        }
    };

    //add more countries in here as needed
    private static final HashMap<String, String> COUNTRIES_INDEX = new HashMap<String, String>() {
        {
            put("United Kingdom", "GB");
            put("United States", "US");
            put("Hong Kong", "HK");
            put("France", "FR");
            put("Germany", "DE");
            put("Australia", "AU");
            put("Canada", "CA");
            put("China", "CN");
            put("Kuwait", "KW");
        }
    };

    public static String getLanguageUrlParamFromLanguageName(String languageName) {
        for (String language : LANGUAGES_INDEX.keySet()) {
            if (languageName.equalsIgnoreCase(language))
                return LANGUAGES_INDEX.get(language);
        }
        throw new IllegalArgumentException("Could not find language URL param for language name: " + languageName);
    }

    public static String getCountryUrlParamFromCountryName(String countryName) {
        for (String country : COUNTRIES_INDEX.keySet()) {
            if (countryName.equalsIgnoreCase(country)) {
                return COUNTRIES_INDEX.get(country);
            }
        }
        throw new IllegalArgumentException("Could not find country URL param for country name: " + countryName);
    }
}