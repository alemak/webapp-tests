package com.netaporter.pws.automation.nap.components;

import com.netaporter.pws.automation.nap.util.CountryAndLanguageIndex;
import com.netaporter.test.utils.enums.RegionEnum;
import com.netaporter.test.utils.pages.component.AbstractPageComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by s.joshi on 14/11/2014.
 */
@Component
@Scope("cucumber-glue")

public class ChangeLanguageComponent extends AbstractPageComponent {

    private By HEADER_LANGUAGE_LINK = By.cssSelector(".js-language a");
    private By LANGUAGE_LIST = By.cssSelector(".js-language-list li");
    private By OLD_HEADER_LANGUAGE_LINK = By.xpath(".//*[@id='account-info']/ul[1]/li[3]/a");


    public void clickLanguageLink() {
        webBot.click(HEADER_LANGUAGE_LINK);
    }

    public void switchToLanguage(String language) {
        List<WebElement> languageList = webBot.findElements(LANGUAGE_LIST);

        for (int i =0; i<=languageList.size(); i++){

            if (languageList.get(i).getText().startsWith(language)){
                languageList.get(i).click();
                break;
            }
        }
    }

    public boolean isLanguageDropdownWithLanguageDisplayed() {
       List<WebElement> languageList = webBot.findElements(LANGUAGE_LIST);
       return (webBot.findElement(LANGUAGE_LIST).isDisplayed() && languageList.size() == 4);
    }

    public String getLanguage(){
        return webBot.findElement(By.xpath("html/body")).getAttribute("data-language");
    }

    public boolean isCorrectLanguageDisplayedInHeader(String language, String pageType) {
        if (pageType.equalsIgnoreCase("responsive")){
            return webBot.findElement(HEADER_LANGUAGE_LINK).getText().equalsIgnoreCase(language);

        }else if (pageType.equalsIgnoreCase("unresponsive")){
            return webBot.findElement(OLD_HEADER_LANGUAGE_LINK).getText().equalsIgnoreCase(language);
        } else
            return false;
    }

    public boolean isCorrectLanguageDisplayedInHeader(String language) {
        if (language.equalsIgnoreCase("Français")){
            return getLanguage().equalsIgnoreCase("fr");

        }else if (language.equalsIgnoreCase("Deutsch")){
            return getLanguage().equalsIgnoreCase("de");

        } else if (language.equalsIgnoreCase("中文")){
            return getLanguage().equalsIgnoreCase("zh");

        } else if (language.equalsIgnoreCase("English")){
            return getLanguage().equalsIgnoreCase("en");

        } else
            return false;
    }
}
