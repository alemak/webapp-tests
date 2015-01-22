//package com.netaporter.pws.automation.shared.utils;
//
//import com.google.common.collect.ImmutableSet;
//
//import java.util.Map;
//import java.util.function.BiConsumer;
//
//import static com.google.common.base.Joiner.on;
//
//public abstract class FieldWithSynonyms {
////    private final BiConsumer<FieldWithSynonyms, Map<String,String>> optionality;
////    private final ImmutableSet<String> synonyms;
//
//
////    protected FieldWithSynonyms(BiConsumer<FieldWithSynonyms, Map<String,String>> optionality, String... allowedSynonyms) {
////        this.optionality = optionality;
////        this.synonyms = ImmutableSet.copyOf(allowedSynonyms);
////    }
////
////    public String from(Map<String, String> values) {
////        return fromOrElse(values, "");
////    }
////
////    public String fromOrElse(Map<String, String> values, String defaultValue) {
////        for (String synonym : synonyms()) {
////            if (values.containsKey(synonym)) {
////                return values.get(synonym);
////            }
////        }
////
////        optionality.accept(this, values);
////        return defaultValue;
////    }
//
////    public ImmutableSet<String> synonyms() {
////        return synonyms;
////    }
//
////    protected static BiConsumer<FieldWithSynonyms, Map<String,String>> optional() {
////        return (f, vs) -> {};
////    }
////
////    protected static  BiConsumer<FieldWithSynonyms, Map<String,String>> mandatory() {
////        return (f, vs) -> { throw missingMandatoryField(f, vs); };
////    }
////
////    protected static BiConsumer<FieldWithSynonyms, Map<String,String>> mandatoryFor(FieldWithSynonyms mandatoryIfField, String mandatoryIfValue) {
////        return (field, values) -> {
////            if (mandatoryIfField.from(values).equals(mandatoryIfValue)) {
////                throw missingMandatoryField(field, values);
////            }
////        };
////    }
//
//    protected static AssertionError missingMandatoryField(FieldWithSynonyms field, Map<String, String> values) {
//        return new AssertionError("mandatory field named \"" + on("\" or \"").join(field.synonyms()) + "\" not found in " + values);
//    }
//}
