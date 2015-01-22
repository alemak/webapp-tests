//package com.netaporter.pws.automation.nap.cucumber.steps.address;
//
//import com.netaporter.pws.automation.shared.utils.FieldWithSynonyms;
//
//import java.util.Map;
//import java.util.function.BiConsumer;
//
//public class AddressField extends FieldWithSynonyms {
//    public static final AddressField title = new AddressField(optional(), "title");
//    public static final AddressField firstName = new AddressField(mandatory(), "firstname");
//    public static final AddressField lastName = new AddressField(mandatory(), "lastname");
//    public static final AddressField country = new AddressField(mandatory(), "country");
//    public static final AddressField address1 = new AddressField(mandatory(), "address1");
//    public static final AddressField address2 = new AddressField(optional(), "address2");
//    public static final AddressField town = new AddressField(mandatory(), "town", "city");
//    public static final AddressField province = new AddressField(mandatoryFor(country, "United States"), "province", "territory", "state");
//    public static final AddressField postcode = new AddressField(mandatory(), "postcode", "zip", "zipcode");
//    public static final AddressField telephone = new AddressField(mandatory(), "telephone");
//    public static final AddressField mobile = new AddressField(optional(), "mobile");
//
//
//    private AddressField(BiConsumer<FieldWithSynonyms, Map<String,String>> optionality, String... synonyms) {
//        super(optionality, synonyms);
//    }
//}
