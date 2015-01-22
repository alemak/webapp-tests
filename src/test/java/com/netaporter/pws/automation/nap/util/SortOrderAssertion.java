package com.netaporter.pws.automation.nap.util;

import org.apache.log4j.Logger;
import java.util.List;

/**
 * User: x.qi@london.net-a-porter.com
 * Date: 13/06/2013
 */
public class SortOrderAssertion {

    static Logger logger  = Logger.getLogger(SortOrderAssertion.class);


    public static boolean assertIntegerValuesInCorrectOrders(SortOrder sortOrder, final List<? extends Comparable> values) {
        if (values.size() == 1 || values.isEmpty()) {
            return true;
        }

        Comparable previousValue = values.get(0);

        for (int i = 1; i < values.size(); i++) {
            Comparable currentValue = values.get(i);

            if (sortOrder.isDescending()) {

                if (previousValue.compareTo(currentValue) < 0) {
                    logger.error(sortOrder+" sorting mismatch between "+previousValue+" and "+currentValue+"!");
                    return false;
                }
            } else {
                if (previousValue.compareTo(currentValue) > 0) {
                    logger.error(sortOrder+" sorting mismatch between "+previousValue+" and "+currentValue+"!");
                    return false;
                }
            }

            previousValue = currentValue;
        }

        return true;
    }
}
