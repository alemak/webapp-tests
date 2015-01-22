package com.netaporter.pws.automation.shared.pojos;

import org.apache.commons.lang3.StringUtils;

/**
 * Address data
 */
public class Address {

    private String address1;
    private String address2;
    private String townOrCity;
    private String provinceOrTerritoryOrState;
    private String area;
    private String postcode;
    private String country;
    private String daytimePhoneNumber;
    private String eveningPhoneNumber;


    public static Address createNAPAddress() {
        Address customerShippingAddress = new Address();
        customerShippingAddress.setAddress1("1 The Village Offices");
        customerShippingAddress.setAddress2("Westfield London");
        customerShippingAddress.setTownOrCity("London");
        customerShippingAddress.setPostcode("W12 7GF");
        customerShippingAddress.setCountry(Country.UNITED_KINGDOM.getName());
        customerShippingAddress.setDaytimePhoneNumber("020 3471 4500");
        return customerShippingAddress;
    }

    public static Address createUKNonLondonAddress() {
        Address customerShippingAddress = new Address();
        customerShippingAddress.setAddress1("Address 1 content");
        customerShippingAddress.setAddress2("Address 2 content");
        customerShippingAddress.setTownOrCity("ACityName");
        customerShippingAddress.setPostcode("AL3 5TT");
        customerShippingAddress.setCountry(Country.UNITED_KINGDOM.getName());
        customerShippingAddress.setDaytimePhoneNumber("2223333");
        return customerShippingAddress;
    }

    public static Address createAnotherAddress() {
        Address customerShippingAddress = new Address();
        customerShippingAddress.setAddress1("200");
        customerShippingAddress.setAddress2("New Kent Road");
        customerShippingAddress.setTownOrCity("Melbourne");
        customerShippingAddress.setPostcode("2000");
        customerShippingAddress.setCountry(Country.AUSTRALIA.getName());
        customerShippingAddress.setDaytimePhoneNumber("2223333");
        return customerShippingAddress;
    }

    public static Address createAnotherAddress(String country) {
        Address customerShippingAddress = new Address();
        customerShippingAddress.setAddress1("Address 1 content");
        customerShippingAddress.setAddress2("Address 2 content");
        customerShippingAddress.setTownOrCity("ACityName");
        customerShippingAddress.setPostcode("123456");
        customerShippingAddress.setCountry(country);
        customerShippingAddress.setDaytimePhoneNumber("2223333");
        return customerShippingAddress;
    }

    public static Address createAnotherAddress(String country, String state, String postCode) {
        Address customerShippingAddress = new Address();
        customerShippingAddress.setAddress1("Address 1 content");
        customerShippingAddress.setAddress2("Address 2 content");
        customerShippingAddress.setTownOrCity("ACityName");
        customerShippingAddress.setPostcode(postCode);
        customerShippingAddress.setCountry(country);
        customerShippingAddress.setDaytimePhoneNumber("2223333");
        if(country.equals("United States") || country.equals("Canada") || country.equals("Hong Kong")){
            customerShippingAddress.setProvinceOrTerritoryOrState(state);
        }
        else if (StringUtils.equalsIgnoreCase("Hong Kong", country)) {
            customerShippingAddress.setArea(state);
        }
        return customerShippingAddress;

    }

    public static Address createUSNonPremierAddress() {
        Address customerShippingAddress = new Address();
        customerShippingAddress.setAddress1("Address 1 content");
        customerShippingAddress.setAddress2("Address 2 content");
        customerShippingAddress.setTownOrCity("ACityName");
        customerShippingAddress.setPostcode("94131");
        customerShippingAddress.setCountry(Country.USA.getName());
        customerShippingAddress.setProvinceOrTerritoryOrState("California");
        customerShippingAddress.setDaytimePhoneNumber("2223333");
        return customerShippingAddress;
    }

    public static Address createUSPremierAddress() {
        Address customerShippingAddress = new Address();
        customerShippingAddress.setAddress1("Address 1 content");
        customerShippingAddress.setAddress2("Address 2 content");
        customerShippingAddress.setTownOrCity("ACityName");
        customerShippingAddress.setPostcode("10001");
        customerShippingAddress.setCountry(Country.USA.getName());
        customerShippingAddress.setProvinceOrTerritoryOrState("New York");
        customerShippingAddress.setDaytimePhoneNumber("2223333");
        return customerShippingAddress;
    }

    public static Address createHKPremierAddress() {
        Address customerShippingAddress = new Address();
        customerShippingAddress.setAddress1("Address 1");
        customerShippingAddress.setAddress2("Address 2");
        customerShippingAddress.setCountry(Country.HONG_KONG.getName());
        customerShippingAddress.setArea("Aberdeen");
        customerShippingAddress.setDaytimePhoneNumber("02073453454");
        return customerShippingAddress;
    }

    public static Address createHKNonPremierAddress() {
        Address customerShippingAddress = new Address();
        customerShippingAddress.setAddress1("Address 1 content");
        customerShippingAddress.setAddress2("Address 2 content");
        customerShippingAddress.setCountry(Country.HONG_KONG.getName());
        customerShippingAddress.setArea("Ping Chau");
        customerShippingAddress.setDaytimePhoneNumber("2223333");
        return customerShippingAddress;
    }

    public static String getTranslatedUSCountry(String language){
        String lang = language.toLowerCase();
        if (lang.equals("french")) {
            return "États-Unis";
        }
        if (lang.equals("german")) {
            return "Vereinigte Staaten";
        }

        if (lang.equals("chinese")) {
            return "美国";
        }
        return "United States";
    }

    public static String getTranslatedUKCountry(String language){
        String lang = language.toLowerCase();
        if (lang.equals("french")) {
            return "Royaume-Uni";
        }
        if (lang.equals("german")) {
            return "Vereinigtes Königreich";
        }

        if (lang.equals("chinese")) {
            return "英国";
        }
        return "United Kingdom";
    }

    private void setArea(String area) {
        this.area = area;
    }

    public String getArea() {
        return area;
    }

    public Address() {
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getTownOrCity() {
        return townOrCity;
    }

    public void setTownOrCity(String townOrCity) {
        this.townOrCity = townOrCity;
    }

    public String getProvinceOrTerritoryOrState() {
        return provinceOrTerritoryOrState;
    }

    public void setProvinceOrTerritoryOrState(String provinceOrTerritoryOrState) {
        this.provinceOrTerritoryOrState = provinceOrTerritoryOrState;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDaytimePhoneNumber() {
        return daytimePhoneNumber;
    }

    public void setDaytimePhoneNumber(String daytimePhoneNumber) {
        this.daytimePhoneNumber = daytimePhoneNumber;
    }

    public String getEveningPhoneNumber() {
        return eveningPhoneNumber;
    }

    public void setEveningPhoneNumber(String eveningPhoneNumber) {
        this.eveningPhoneNumber = eveningPhoneNumber;
    }

    public static Address createUKStandardAddress() {
        Address customerShippingAddress = new Address();
        customerShippingAddress.setAddress1("Address 1 content");
        customerShippingAddress.setAddress2("Address 2 content");
        customerShippingAddress.setCountry(Country.UNITED_KINGDOM.getName());
        customerShippingAddress.setTownOrCity("Isle of Man");
        customerShippingAddress.setPostcode("IM91TP");
        customerShippingAddress.setDaytimePhoneNumber("2223333");
        return customerShippingAddress;
    }

    public static Address createAUNextDayAddress() {
        Address customerShippingAddress = new Address();
        customerShippingAddress.setAddress1("Australia Next Day");
        customerShippingAddress.setAddress2("Address 2 content");
        customerShippingAddress.setCountry(Country.AUSTRALIA.getName());
        customerShippingAddress.setTownOrCity("Sydney");
        customerShippingAddress.setPostcode("2000");
        customerShippingAddress.setDaytimePhoneNumber("2223333");
        return customerShippingAddress;
    }

    public static Address createAUExpressAddress() {
        Address customerShippingAddress = new Address();
        customerShippingAddress.setAddress1("Australia Express");
        customerShippingAddress.setAddress2("Address 2 content");
        customerShippingAddress.setCountry(Country.AUSTRALIA.getName());
        customerShippingAddress.setTownOrCity("Perth");
        customerShippingAddress.setPostcode("6000");
        customerShippingAddress.setDaytimePhoneNumber("2223333");
        return customerShippingAddress;
    }
}
