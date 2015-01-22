//package com.netaporter.pws.automation.shared.utils;
//
//import java.util.Map;
//import java.util.function.BiConsumer;
//
//public class PaymentField extends FieldWithSynonyms {
//    public static PaymentField cardType = new PaymentField(mandatory(), "card type");
//    public static PaymentField cardholderName = new PaymentField(optional(), "name", "cardholder name", "name on card");
//    public static PaymentField cardNumber = new PaymentField(optional(), "card number");
//    public static PaymentField expiryMonth = new PaymentField(optional(), "expiry month");
//    public static PaymentField expiryYear = new PaymentField(optional(), "expiry month");
//    public static PaymentField securityNumber = new PaymentField(optional(), "security number", "cv2", "cvs");
//    public static PaymentField issueNumber = new PaymentField(optional(), "issue number");
//
//    private PaymentField(BiConsumer<FieldWithSynonyms, Map<String, String>> optionality, String... allowedSynonyms) {
//        super(optionality, allowedSynonyms);
//    }
//}
