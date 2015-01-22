package com.netaporter.pws.automation.nap.pages;

import com.netaporter.pws.automation.shared.components.AccountDetailsForm;
import com.netaporter.test.utils.pages.driver.WaitTime;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Encapsulates the functionality on the Account Details page
 */
@Component
@Scope("cucumber-glue")
public class AccountDetailsPage extends AbstractNapPage {

    private static final String PAGE_NAME = "Account Details";
    private static final String PATH = "accountdetails.nap";

    private By CHANGE_COUNTRY_OVERLAY_LOCATOR = By.xpath("//div[@id='lightbox-middle']/iframe");
    private By LOCATION_AND_LANGUAGE_SELECTOR = By.cssSelector("#my-preferences .form-inputs");
    private By UPDATE_PREFERENCES_BUTTON_LOCATOR = By.xpath(".//*[@id='my-preferences']/div/div/div/div/div/div[2]/p/input");
    private By CHANGE_ACCOUNT = By.id("change-account");
    private By CURRENT_PASSWORD_CHANGE_PASSWORD_LOCATOR = By.cssSelector("INPUT#password_current");
    private By CURRENT_PASSWORD_CHANGE_EMAIL_LOCATOR = By.cssSelector("INPUT#password_current_to_change_email");
    private By CURRENT_FIRSTNAME_LOCATOR = By.id("currentfirstname");
    private By CURRENT_SURNAME_LOCATOR = By.id("currentsurname");

    @Autowired
    private ChangeCountryPage changeCountryPage;

    public AccountDetailsPage() {
        super(PAGE_NAME, PATH);
    }

    public void clickUpdatePreferences() {
        webBot.click(UPDATE_PREFERENCES_BUTTON_LOCATOR);
    }

    public void switchToCountry(String country) {
        WebElement switchCountryElement = webBot.findElement(CHANGE_COUNTRY_OVERLAY_LOCATOR);
        webBot.getDriver().switchTo().frame(switchCountryElement);
        changeCountryPage.switchToCountry(country);
    }

    public String getLocationAndLanguage() {
        return webBot.getElementText(LOCATION_AND_LANGUAGE_SELECTOR);
    }

    public void fillEditEmailAddress(String newEmail) {
        webBot.type(AccountDetailsForm.NEW_EMAIL_ADDRESS_LOCATOR, newEmail);
        webBot.type(AccountDetailsForm.CONFIRM_EMAIL_ADDRESS_LOCATOR, newEmail);
    }

    public void submitChangeEmailAddressForm() {
        webBot.click(AccountDetailsForm.SUBMIT_EMAIL_FORM_LOCATOR);
    }

    public void changeFirstNameAndLastName(String firstName, String lastName) {
        webBot.type(AccountDetailsForm.NEW_FIRST_NAME_LOCATOR, firstName);
        webBot.type(AccountDetailsForm.NEW_LAST_NAME_LOCATOR, lastName);

        webBot.click(AccountDetailsForm.SUBMIT_EDIT_FIRST_LAST_NAME_FORM_LOCATOR);
    }

    public void changePassword(String newPassword) {
        webBot.type(AccountDetailsForm.NEW_PASSWORD_LOCATOR, newPassword);
        webBot.type(AccountDetailsForm.CONFIRM_PASSWORD_LOCATOR, newPassword);

        webBot.click(AccountDetailsForm.SUBMIT_NEW_PASSWORD);
    }

    public boolean isAccountChanged(){
        return webBot.findElement(CHANGE_ACCOUNT, WaitTime.FOUR) != null;
    }

    public void enterCurrentPasswordToModifyPassword(String password) {
        webBot.type(CURRENT_PASSWORD_CHANGE_PASSWORD_LOCATOR, password);
    }

    public void enterCurrentPasswordToModifyEmail(String password) {
        webBot.type(CURRENT_PASSWORD_CHANGE_EMAIL_LOCATOR, password);
    }

    public String getCurrentFirstName() {
        return webBot.findElement(CURRENT_FIRSTNAME_LOCATOR, 2).getText();
    }

    public String getCurrentLastName() {
        return webBot.findElement(CURRENT_SURNAME_LOCATOR, 2).getText();
    }
}