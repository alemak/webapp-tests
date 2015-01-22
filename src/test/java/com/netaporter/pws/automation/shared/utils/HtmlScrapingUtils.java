package com.netaporter.pws.automation.shared.utils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: andres
 * Date: 11/11/13
 */
public class HtmlScrapingUtils {

    public static final Pattern DECIMAL_NUMBER_REGEXP = Pattern.compile("\\-?\\d+(\\.\\d{1,2})?");

    /**
     * It turns a string representation of a decimal number in a BigDecimal.
     *
     * i.e. The string "Item price: £3,435.00" will be converted to BigDecimal representing the number 3435.00
     *
     * @param decimalNumberInformation
     * @return BigDecimal
     */
    public static BigDecimal decimalNumberStringRepresentationToBigDecimal(String decimalNumberInformation) {
        Matcher decimalNumberMatcher = DECIMAL_NUMBER_REGEXP.matcher(decimalNumberInformation.replaceAll(",", ""));
        decimalNumberMatcher.find();
        return new BigDecimal(decimalNumberMatcher.group());
    }
}
