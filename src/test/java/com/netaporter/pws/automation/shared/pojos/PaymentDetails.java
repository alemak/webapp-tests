package com.netaporter.pws.automation.shared.pojos;

import com.google.common.base.MoreObjects;

/**
 * Created with IntelliJ IDEA.
 * User: m.sun@london.net-a-porter.com
 * Date: 26/10/2012
 * Time: 11:58
 * To change this template use File | Settings | File Templates.
 */
public class PaymentDetails {

    private String cardType;
    private String cardNumber;
    private String cardholderName;
    private String securityNumber;
    private String expiryMonth;
    private String expiryYear;
    private String issueNumber;

    public PaymentDetails(String cardType, String cardNumber, String cardholderName, String securityNumber, String expiryMonth, String expiryYear, String issueNumber) {
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.cardholderName = cardholderName;
        this.securityNumber = securityNumber;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.issueNumber = issueNumber;
    }

    public PaymentDetails(String cardType, String cardNumber, String cardholderName, String securityNumber, String expiryMonth, String expiryYear) {
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.cardholderName = cardholderName;
        this.securityNumber = securityNumber;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
    }

    public String getCardType() {
        return cardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public String getSecurityNumber() {
        return securityNumber;
    }

    public String getExpiryMonth() {
        return expiryMonth;
    }

    public String getExpiryYear() {
        return expiryYear;
    }

    public String getExpiryDate() {
        return getExpiryMonth()+"/"+getExpiryYear();
    }

    public String getIssueNumber() {
        return issueNumber;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("cardNumber", cardNumber).add("securityNumber",securityNumber).toString();
    }
}
