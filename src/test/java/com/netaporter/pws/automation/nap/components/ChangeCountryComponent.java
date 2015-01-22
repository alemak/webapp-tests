package com.netaporter.pws.automation.nap.components;

import com.netaporter.pws.automation.nap.util.CountryAndLanguageIndex;
import com.netaporter.test.utils.enums.RegionEnum;
import com.netaporter.test.utils.pages.component.AbstractPageComponent;
import com.netaporter.test.utils.pages.exceptions.PageElementNotFoundException;
import com.netaporter.test.utils.pages.exceptions.PageElementNotFoundException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by s.joshi on 07/11/2014.
 */

@Component
@Scope("cucumber-glue")


public class ChangeCountryComponent extends AbstractPageComponent {

    private By CHANGE_COUNTRY_CONTAINER = By.className("inner-container");
    private By COUNTRIES_LIST = By.cssSelector(".country-list li");
    private By FLAG_ELEMENT = By.xpath(".//*[@class='country-name-flag']/span[1]");
    private By COUNTRY_CONFIRMATION = By.className("country-confirmation");
    private By CHANGE_COUNTRY_SUBMIT = By.className("js-change-country-submit");


    public boolean isChangeCountryOverlayVisible() {
        return webBot.findElement(CHANGE_COUNTRY_CONTAINER).isDisplayed();
    }

    public String getTotalCountOfCountries() {

        List<WebElement> countryOptions = webBot.findElements(COUNTRIES_LIST);
        return String.valueOf(countryOptions.size()-1);
    }

    public String getFlagName() {
        return webBot.findElement(FLAG_ELEMENT).getAttribute("class");
    }

    public void switchToCountry(String countryName) {
//        closeDontMissOutPopup();
        List<WebElement> countryList = webBot.findElements(COUNTRIES_LIST);

        for (int i =0; i<=countryList.size()-1; i++){
            String countryOption = countryList.get(i).getText();

            if (countryName.equalsIgnoreCase(countryOption)){
                countryList.get(i).click();
                break;
            }
        }
    }

    public boolean isChangeCountryMessageDisplayed() {
        try {
            List<WebElement> countryConfirmationEls = webBot.findElements(COUNTRY_CONFIRMATION);
            return countryConfirmationEls.get(0).isDisplayed();
        } catch (PageElementNotFoundException e) {
            return false;
        }
    }

    public void clickChangeCountryConfirmation() {
        webBot.click(CHANGE_COUNTRY_SUBMIT);
    }
}
