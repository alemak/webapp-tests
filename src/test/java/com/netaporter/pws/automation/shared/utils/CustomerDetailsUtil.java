package com.netaporter.pws.automation.shared.utils;

import com.netaporter.pws.automation.shared.pojos.Customer;

import java.util.Date;

public class CustomerDetailsUtil {

    public static final String ANONYMOUS_EMAIL_KEY = "ANONYMOUS_EMAIL";

    public static Customer getArbitraryUser(){
        return new Customer("Chris", "Cole", generateEmailAddress(), "123456", "United Kingdom");
    }

	public static final String generateEmailAddress() {
		return "test" + new Date().getTime() + "@javaTesting.com";
	}
}
