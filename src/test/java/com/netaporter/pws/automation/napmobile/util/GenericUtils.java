package com.netaporter.pws.automation.napmobile.util;

import java.util.Date;
import java.util.Random;

/**
 * Created by ocsiki on 07/08/2014.
 */
public class GenericUtils {

    public static String createRandomString(int length) {
        Random random = new Random(new Date().getTime());
        String result = "";
        for(int i = 0; i < length; i++) {
            char newChar = (char) ('a' + random.nextInt(26));

            result += newChar;
        }

        return result;
    }
}
