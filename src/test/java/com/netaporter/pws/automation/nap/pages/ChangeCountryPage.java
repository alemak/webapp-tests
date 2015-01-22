package com.netaporter.pws.automation.nap.pages;


import com.netaporter.pws.automation.nap.util.CountryAndLanguageIndex;
import com.netaporter.test.utils.enums.RegionEnum;
import com.netaporter.test.utils.pages.driver.WaitTime;
import com.netaporter.test.utils.pages.regionalisation.RegionaliseWithRegionAndCountrySlashLanguage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Encapsulates the functionality on the ChangeCountryPage page
 */
@Component
@Scope("cucumber-glue")
public class ChangeCountryPage extends AbstractNapPage {

    private static final String PAGE_NAME = "Change Country";
    private static final String PATH = "changecountry.nap";

    private By LANGUAGE_CHOOSER_SELECTOR = By.name("language");
    private By FORM_SUBMIT_LOCATOR = By.id("form-submit-button");

    private static final Map<String, String> channelToXPathElementId = new HashMap<String, String>();
    static {
        channelToXPathElementId.put(RegionEnum.APAC.name().toLowerCase(), "apac");
        channelToXPathElementId.put(RegionEnum.AM.name().toLowerCase(), "states");
        channelToXPathElementId.put(RegionEnum.INTL.name().toLowerCase(), "international");
    }

    public ChangeCountryPage() {
        super(PAGE_NAME, PATH);
        setRegionalisePathBehavior(new RegionaliseWithRegionAndCountrySlashLanguage());
    }

    public void switchToAnyCountry(String channel) throws InterruptedException {
        By element = createCountrySelectionElement(channel);

        Select countryChoices = new Select(webBot.findElement(element));

        countryChoices.selectByIndex(new Random().nextInt(countryChoices.getOptions().size()));

        webBot.findElement(FORM_SUBMIT_LOCATOR).submit();
    }

    public void switchToCountry(String countryName) {
        closeDontMissOutPopup();
        if (selectACountry(RegionEnum.AM.name().toLowerCase(), countryName)
                || selectACountry(RegionEnum.INTL.name().toLowerCase(), countryName)
                || selectACountry(RegionEnum.APAC.name().toLowerCase(), countryName)) {
            webBot.findElement(FORM_SUBMIT_LOCATOR).submit();
        } else {
            throw new IllegalArgumentException("Can't switch to " + countryName + " as no such choice exist.");
        }
        webBot.setCountry(CountryAndLanguageIndex.getCountryUrlParamFromCountryName(countryName));
    }


    public void switchToCountry(String country, RegionEnum region) {
        closeDontMissOutPopup();
        selectACountry(region.name(), country);
        webBot.findElement(FORM_SUBMIT_LOCATOR, WaitTime.FOUR).submit();
        webBot.setCountry(CountryAndLanguageIndex.getCountryUrlParamFromCountryName(country));
    }


    public void switchToLanguage(String language) {
        if (selectALanguage(language)) {
            webBot.findElement(FORM_SUBMIT_LOCATOR).submit();
        } else {
            throw new IllegalArgumentException("Can't switch to language " + language + " as no such choice exist.");
        }
        webBot.setLanguage(CountryAndLanguageIndex.getLanguageUrlParamFromLanguageName(language));
    }

    public void switchToMyPreferencesOverlay() {
        webBot.switchToIFrame(By.xpath("//*[@id=\"lightbox-middle\"]/iframe"));
    }


    private boolean selectACountry(String region, String countryName) {

        By channelCountries = createCountrySelectionElement(region);

        Select countryChoices = new Select(webBot.findElement(channelCountries));

        String languageOptionText;
        for (WebElement countryOption : countryChoices.getOptions()) {
            if (countryName.contains(countryOption.getText())) {
                switchChannelFocus(region); //need this otherwise it won't update properly if it's a country in a different region
                countryOption.click();
                languageOptionText = countryOption.getText();

                countryChoices.selectByVisibleText(languageOptionText);

                return true;
            }
        }

        return false;
    }


    private static By createCountrySelectionElement(String region) {
        return By.xpath("//div[@id='" + channelToXPathElementId.get(region.toLowerCase()) + "']/select");
    }


    private void switchChannelFocus(String region) {
        webBot.click(By.xpath("//div[@id='" + channelToXPathElementId.get(region.toLowerCase()) + "']"));
    }

    public boolean selectALanguage(String languageName){

        Select languageChoices = new Select (webBot.findElement(LANGUAGE_CHOOSER_SELECTOR));

        for(WebElement languageOption : languageChoices.getOptions()) {
            if (languageOption.getText().startsWith(languageName)){
                languageOption.click();
                return true;
            }
        }
        return false;
    }

    /*
    Prashant.Ramcharan@net-a-porter.com
    More efficient way to change language preferences
     */
    public void changeLanguageTo(String desiredLanguage){
        String changeCountryUrl = webBot.executeJavascript("$('.country_select').first().attr('href')");
        webBot.getDriver().navigate().to(changeCountryUrl);
        selectALanguage(desiredLanguage);
        closeDontMissOutPopup();
        webBot.click(By.id("form-submit-button"));
        webBot.setLanguage(CountryAndLanguageIndex.getLanguageUrlParamFromLanguageName(desiredLanguage));
    }

    /*
    Prashant.Ramcharan@net-a-porter.com
    More efficient way to change country preferences
     */
    public void updateCountryPreferences(String channel, String countryName){
        String formattedChannel = channel.toLowerCase();
        By countryChannelSelector = By.id(formattedChannel.concat("_country_dd"));

        String channelMapId = "international";
        if (formattedChannel.equals("am")){
            channelMapId = "states";
        }
        if (formattedChannel.equals("apac")){
            channelMapId = "apac";
        }

        String changeCountryUrl = webBot.executeJavascript("$('.country_select').first().attr('href')");
        webBot.getDriver().navigate().to(changeCountryUrl);
        closeDontMissOutPopup();
        webBot.selectElementText(countryChannelSelector, countryName);
        webBot.click(By.id(channelMapId));
        webBot.click(By.id("form-submit-button"));
        webBot.setCountry(CountryAndLanguageIndex.getCountryUrlParamFromCountryName(countryName));
    }
}