package com.netaporter.pws.automation.shared.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by x.qi on 22/09/2014.
 */
public class LocaleUrlUtils {
    private static final Pattern URL_PATTERN = Pattern.compile(".*(intl|am|apac)?/([a-z]{2})/([a-z]{2})/.*");

    private static final List<String> NON_LOALISEDLINKS_SEG = Lists.newArrayList("changecountry.nap", "popup=true",
            "j_spring_security_logout", "fashionfix.net-a-porter.com", "editors-photo-diary.net-a-porter.com");
    private static final String NET_A_PORTER_COM = "net-a-porter.com";
    private static final String JAVASCRIPT_HELP_PREFIX = "javascript:help(";
    private static final String STARTING_SLASH = "/";


    public static boolean containsValidLink(String link) {
        return !isValidatingCandidate(link)  || containsLocale(link);
    }


    private static boolean isValidatingCandidate(String link) {
        return isNapLinkNeedToBeLocalised(link) &&
                (link.startsWith(STARTING_SLASH) || link.contains(NET_A_PORTER_COM) || link.startsWith(JAVASCRIPT_HELP_PREFIX));
    }

    private static boolean isNapLinkNeedToBeLocalised(String link) {
        for(String linkSeg: NON_LOALISEDLINKS_SEG) {
            if (link.contains(linkSeg)) {
                return false;
            }
        }

        return true;
    }

    private static boolean containsLocale(String url) {
        Matcher matcher = URL_PATTERN.matcher(url);
        String country = null;
        String language = null;

        while(matcher.find()) {
            country = matcher.group(2);
            language = matcher.group(3);
        }

        if(StringUtils.isNotBlank(country) && StringUtils.isNotBlank(language)) {
            Locale locale = new Locale(language, country);
            if (isValid(locale)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isValid(Locale locale) {
        try {
            return locale.getISO3Language() != null && locale.getISO3Country() != null;
        } catch (MissingResourceException e) {
            return false;
        }
    }
}
