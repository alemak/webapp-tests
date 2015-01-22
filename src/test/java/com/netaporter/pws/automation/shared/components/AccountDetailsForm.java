package com.netaporter.pws.automation.shared.components;

import org.openqa.selenium.By;

/**
 * Created with IntelliJ IDEA.
 * User: c.choudhury@london.net-a-porter.com
 * Date: 29/01/2013
 * Time: 16:26
 * To change this template use File | Settings | File Templates.
 */
public class AccountDetailsForm {

    public static final By NEW_EMAIL_ADDRESS_LOCATOR = By.xpath(".//*[@id='email_email1']");
    public static final By CONFIRM_EMAIL_ADDRESS_LOCATOR = By.xpath(".//*[@id='email_email2']");
    public static final By NEW_FIRST_NAME_LOCATOR = By.xpath(".//*[@id='newfirstname']");
    public static final By NEW_LAST_NAME_LOCATOR = By.xpath(".//*[@id='newsurname']");
    public static final By NEW_PASSWORD_LOCATOR = By.id("newpassword");
    public static final By CONFIRM_PASSWORD_LOCATOR = By.id("newpassword2");

    public static final By CHANGE_EMAIL_ADDRESS_VALIDATION_ERROR_ELEMENT = By.xpath("//div[@id=\"change-email-address\"]//[@class=\"li\"]");

    public static final By SUBMIT_EMAIL_FORM_LOCATOR = By.xpath(".//*[@id='change-your-email']/div[2]/input");
    public static final By SUBMIT_EDIT_FIRST_LAST_NAME_FORM_LOCATOR = By.xpath(".//*[@id='change-your-name']/div[2]/p/input");
    public static final By SUBMIT_NEW_PASSWORD = By.xpath(".//*[@id='change-your-passwd']/div[2]/p/input");






}
