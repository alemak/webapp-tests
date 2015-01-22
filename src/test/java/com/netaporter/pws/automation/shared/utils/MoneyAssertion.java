package com.netaporter.pws.automation.shared.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * User: x.qi@london.net-a-porter.com
 * Date: 12/10/2012
 */
public class MoneyAssertion {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,###.##");

    public static void assertCorrectCurrencies(String expectedCurrencyPrefix, List<String> prices) {
        assertFalse("Prices should not be empty", prices.isEmpty());
        for (String price : prices) {
            assertCorrectCurrency(expectedCurrencyPrefix, price);
        }
    }

    public static void assertCorrectCurrency(String expectedCurrencyPrefix, String price) {
        assertTrue("Actual value == " + price + " ==", price.contains(expectedCurrencyPrefix));
    }

    public static void assertPayDuty(boolean needToPayDuty, String duty, String productSkuOrPid) {
        if (duty == null) fail("Duty value not found");

        Double dutyValue = Double.valueOf(duty.replaceAll("\\D", ""));
        try {
            if (needToPayDuty != !dutyValue.equals(0.0))
            {
                fail((needToPayDuty ? "Expect to" : " Expect not to ") + " pay duty, but" + " actual duty is " + dutyValue + " for pid or sku: " + productSkuOrPid);
            }
        } catch (NumberFormatException e) {
            fail("Reading duty from page failed");
        }
    }

    public static void assertCalculatedSumIsTheSameAsGivenTotal(List<String> itemPrices, String givenTotal) {
        try {
            Number givenTotalValue = parseStringToNumber(givenTotal);

            Double calculatedItemTotal = 0.0;
            for (String unitPrice : itemPrices) {
                // Note: assuming currency symbol is one character long
                String price = unitPrice.substring(1);
                calculatedItemTotal += parseStringToNumber(price).doubleValue();
            }

            assertEquals(createDoubleWithTwoDigitsAfterDecimalPoint(calculatedItemTotal), createDoubleWithTwoDigitsAfterDecimalPoint(givenTotalValue.doubleValue()));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Wrong format price; " + itemPrices + givenTotal, e);
        }
    }


    public static void assertVertexEquals(String sku, Double expectedItemTax, Double actualTaxDouble) {
        Double expectedItemTaxUpper = createDoubleWithTwoDigitsAfterDecimalPoint(expectedItemTax);
        // Note: webapp calculates tax from tax rate, and it gives 1p less (favour to customers) result sometimes
        Double expectedItemTaxLower = createDoubleWithTwoDigitsAfterDecimalPoint(expectedItemTax - 0.01);

        assertTrue("Wrong tax for product of sku: " + sku + " (" + expectedItemTaxUpper + " or " + expectedItemTaxLower + ") vs " + actualTaxDouble, (expectedItemTaxUpper.equals(actualTaxDouble) || expectedItemTaxLower.equals(actualTaxDouble)));
    }

    private static Number parseStringToNumber(String givenTotal) throws ParseException {
        return DECIMAL_FORMAT.parse(givenTotal);
    }

    // Note: this is required due to inaccuracy of double arithmetic operations
    private static Double createDoubleWithTwoDigitsAfterDecimalPoint(Double doubleNumber) {
        return new BigDecimal(doubleNumber).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

}
