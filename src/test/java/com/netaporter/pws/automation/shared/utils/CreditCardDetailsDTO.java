package com.netaporter.pws.automation.shared.utils;

import java.util.Comparator;

public class CreditCardDetailsDTO {

	private String type;
	private String number;

	public CreditCardDetailsDTO(String type, String number) {
		this.type = type;
		this.number = number;
	}

	public String getNumber() {
		return number;
	}

	public String getType() {
		return type;
	}
}
