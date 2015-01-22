package com.netaporter.pws.automation.nap.pages;

import com.netaporter.test.utils.enums.RegionEnum;
import org.openqa.selenium.By;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * User: o_csiki
 * Date: 10/10/13
 */
@Component
@Scope("cucumber-glue")
public class NAPSaleLandingPage extends AbstractNapPage {

    //navcat level1 elements
    private static final By CLOTHING_ELEMENT_KEY = By.xpath((".//*[@id='content']/div/div[1]/div[1]/ul/li[1]/a"));
    private static final By BAGS_ELEMENT_KEY = By.xpath(".//*[@id='content']/div/div[1]/div[2]/ul/li[1]/a");
    private static final By SHOES_ELEMENT_KEY = By.xpath(".//*[@id='content']/div/div[1]/div[3]/ul/li[1]/a");
    private static final By ACCESSORIES_ELEMENT_KEY = By.xpath(".//*[@id='content']/div/div[1]/div[4]/ul/li[1]/a");
    private static final By LINGERIE_ELEMENT_KEY = By.xpath(".//*[@id='content']/div/div[1]/div[5]/ul/li[1]/a");
    private static final By SPORT_ELEMENT_KEY = By.xpath(".//*[@id='content']/div/div[1]/div[6]/ul/li[1]/a");

    //navcat level2 elements
    //clothing
    public static final By ALL_SALE_CLOTHING_ELEMENT_KEY = By.className("all_Clothing");
    public static final By ACTIVEWEAR_PAGE_ELEMENT_KEY = By.className("Activewear");
    public static final By BEACHWEAR_ELEMENT_KEY = By.className("Beachwear");
    public static final By COATS_ELEMENT_KEY = By.className("Coats");
    public static final By DRESSES_ELEMENT_KEY = By.className("Dresses");
    public static final By JACKETS_ELEMENT_KEY = By.className("Jackets");
    public static final By JEANS_ELEMENT_KEY = By.className("Jeans");
    public static final By JUMPSUITS_ELEMENT_KEY = By.className("Jumpsuits");
    public static final By KNITWEAR_ELEMENT_KEY = By.className("Knitwear");
    public static final By PANTS_ELEMENT_KEY = By.className("Pants");
    public static final By SHORTS_ELEMENT_KEY = By.className("Shorts");
    public static final By SKIRTS_ELEMENT_KEY = By.className("Skirts");
    public static final By TOPS_ELEMENT_KEY = By.className("Tops");
    //bags
    public static final By ALL_SALE_BAGS_ELEMENT_KEY = By.className("All-bags placeholder");
    public static final By BACKPACKS_ELEMENT_KEY = By.className("Backpacks");
    public static final By CLUTCH_BAGS_ELEMENT_KEY = By.className("Clutch_Bags");
    public static final By SHOULDER_BAGS_ELEMENT_KEY = By.className("Shoulder_Bags");
    public static final By TOTE_BAGS_ELEMENT_KEY = By.className("Tote_Bags");
    public static final By TRAVEL_BAGS_ELEMENT_KEY = By.className("Travel_Bags");

    //shoes
    public static final By ALL_SALE_SHOES_ELEMENT_KEY = By.className("All-Shoes placeholder");
    public static final By BOOTS_ELEMENT_KEY = By.className("Boots");
    public static final By FLAT_SHOES_ELEMENT_KEY = By.className("Flat_Shoes");
    public static final By PUMPS_ELEMENT_KEY = By.className("Pumps");
    public static final By SANDALS_ELEMENT_KEY = By.className("Sandals");
    public static final By SNEAKERS_ELEMENT_KEY = By.className("Sneakers");
    //accessories
    public static final By ALL_SALE_ACCESSORIES_ELEMENT_KEY = By.className("all_Accessories");
    public static final By BELTS_ELEMENT_KEY = By.className("Belts");
    public static final By BOOKS_ELEMENT_KEY = By.className("Books");
    public static final By COLLARS_ELEMENT_KEY = By.className("Collars");
    public static final By COSMETIC_CASES_ELEMENT_KEY = By.className("Cosmetic_Cases");
    public static final By GLOVES_ELEMENT_KEY = By.className("Gloves");
    public static final By BRACELETS_ELEMENT_KEY = By.className("Bracelets");
    public static final By FINE_JEWELRY_ELEMENT_KEY = By.className("Fine_Jewelry");
    public static final By KEY_FOBS_ELEMENT_KEY = By.className("Key_Fobs");
    public static final By TECHNOLOGY_ELEMENT_KEY = By.className("Technology");
    public static final By HAIR_ACCESSORIES_ELEMENT_KEY = By.className("Hair_Accessories");
    public static final By HOMEWARE_ELEMENT_KEY = By.className("Homeware");
    public static final By HATS_ELEMENT_KEY = By.className("Hats");
    public static final By JEWELRY_ELEMENT_KEY = By.className("Jewelry");
    public static final By OPTICALS_ELEMENT_KEY = By.className("Opticals");
    public static final By POUCHES_ELEMENT_KEY = By.className("Pouches");
    public static final By PRINTS_ELEMENT_KEY = By.className("Prints");
    public static final By SCARVES_ELEMENT_KEY = By.className("Scarves");
    public static final By SUNGLASSES_ELEMENT_KEY = By.className("Sunglasses");
    public static final By TRAVEL_ELEMENT_KEY = By.className("Travel");
    public static final By UMBRELLAS_ELEMENT_KEY = By.className("Umbrellas");
    public static final By WALLETS_ELEMENT_KEY = By.className("Wallets");
    public static final By WATCHES_ELEMENT_KEY = By.className("Watches");
    //lingerie
    public static final By ALL_SALE_LINGERIE_ELEMENT_KEY = By.className("all_Lingerie");
    public static final By BRAS_ELEMENT_KEY = By.className("Bras");
    public static final By BRIEFS_ELEMENT_KEY = By.className("Briefs");
    public static final By CAMISOLESANDCHEMISES_ELEMENT_KEY = By.className("Camisoles_and_Chemises");
    public static final By CORSETRY_ELEMENT_KEY = By.className("Corsetry");
    public static final By HOSIERY_ELEMENT_KEY = By.className("Hosiery");
    public static final By SHAPEWEAR_ELEMENT_KEY = By.className("Shapewear");
    public static final By SLEEPWEAR_ELEMENT_KEY = By.className("Sleepwear");
    public static final By LINGERIE_ACCESSORIES_ELEMENT_KEY = By.className("Lingerie_Accessories");
    public static final By ROBES_ELEMENT_KEY = By.className("Robes");

    public NAPSaleLandingPage() {
        super("NAP Sale Landing Page", "/sale");
    }

    @Override
    public boolean isPageRegionalised() {
        return false;
    }

    public NAPSaleListingPage clickSectionAndReturnPage(String section) {

        // Note: use map
       if ("Clothing".equalsIgnoreCase(section))
           clickIfVisibleInLandingPage(CLOTHING_ELEMENT_KEY);
              else if ("All Clothing".equalsIgnoreCase(section))
           clickIfVisibleInLandingPage(ALL_SALE_CLOTHING_ELEMENT_KEY);
       else if ("Bags".equalsIgnoreCase(section))
           clickIfVisibleInLandingPage(BAGS_ELEMENT_KEY);
       else if ("Sport".equalsIgnoreCase(section))
             clickIfVisibleInLandingPage(SPORT_ELEMENT_KEY);
       else if ("All Bags".equalsIgnoreCase(section))
           clickIfVisibleInLandingPage(ALL_SALE_BAGS_ELEMENT_KEY);
       else if ("Shoes".equalsIgnoreCase(section))
           clickIfVisibleInLandingPage(SHOES_ELEMENT_KEY);
       else if ("All shoes".equalsIgnoreCase(section))
           clickIfVisibleInLandingPage(ALL_SALE_SHOES_ELEMENT_KEY);
       else if ("Accessories".equalsIgnoreCase(section))
           clickIfVisibleInLandingPage(ACCESSORIES_ELEMENT_KEY);
       else if ("All Accessories".equalsIgnoreCase(section))
           clickIfVisibleInLandingPage(ALL_SALE_ACCESSORIES_ELEMENT_KEY);
       else if ("Lingerie".equalsIgnoreCase(section))
           clickIfVisibleInLandingPage(LINGERIE_ELEMENT_KEY);
       else if ("All Lingerie".equalsIgnoreCase(section))
           clickIfVisibleInLandingPage(ALL_SALE_LINGERIE_ELEMENT_KEY);
       else if ("Activewear".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(ACTIVEWEAR_PAGE_ELEMENT_KEY);
       else if ("Beachwear".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(BEACHWEAR_ELEMENT_KEY);
       else if ("Coats".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(COATS_ELEMENT_KEY);
       else if ("Dresses".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(DRESSES_ELEMENT_KEY);
       else if ("Jackets".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(JACKETS_ELEMENT_KEY);
       else if ("Jeans".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(JEANS_ELEMENT_KEY);
       else if ("Jumpsuits".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(JUMPSUITS_ELEMENT_KEY);
       else if ("Knitwear".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(KNITWEAR_ELEMENT_KEY);
       else if ("Pants".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(PANTS_ELEMENT_KEY);
       else if ("Shorts".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(SHORTS_ELEMENT_KEY);
       else if ("Skirts".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(SKIRTS_ELEMENT_KEY);
       else if ("Tops".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(TOPS_ELEMENT_KEY);
       else if ("Backpacks".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(BACKPACKS_ELEMENT_KEY);
       else if ("Clutch Bags".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(CLUTCH_BAGS_ELEMENT_KEY);
       else if ("Shoulder Bags".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(SHOULDER_BAGS_ELEMENT_KEY);
       else if ("Tote Bags".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(TOTE_BAGS_ELEMENT_KEY);
       else if ("Travel Bags".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(TRAVEL_BAGS_ELEMENT_KEY);
       else if ("Boots".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(BOOTS_ELEMENT_KEY);
       else if ("Flat Shoes".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(FLAT_SHOES_ELEMENT_KEY);
       else if ("Pumps".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(PUMPS_ELEMENT_KEY);
       else if ("Sandals".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(SANDALS_ELEMENT_KEY);
       else if ("Sneakers".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(SNEAKERS_ELEMENT_KEY);
       else if ("Belts".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(BELTS_ELEMENT_KEY);
       else if ("Books".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(BOOKS_ELEMENT_KEY);
       else if ("Collars".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(COLLARS_ELEMENT_KEY);
       else if ("Fine Jewelry".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(FINE_JEWELRY_ELEMENT_KEY);
       else if ("Gloves".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(GLOVES_ELEMENT_KEY);
       else if ("Hair Accessories".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(HAIR_ACCESSORIES_ELEMENT_KEY);
       else if ("Hats".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(HATS_ELEMENT_KEY);
       else if ("Homeware".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(HOMEWARE_ELEMENT_KEY);
       else if ("Jewelry".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(JEWELRY_ELEMENT_KEY);
       else if ("Key Fobs".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(KEY_FOBS_ELEMENT_KEY);
       else if ("Opticals".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(OPTICALS_ELEMENT_KEY);
       else if ("Pouches".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(POUCHES_ELEMENT_KEY);
       else if ("Prints".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(PRINTS_ELEMENT_KEY);
       else if ("Scarves".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(SCARVES_ELEMENT_KEY);
       else if ("Sunglasses".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(SUNGLASSES_ELEMENT_KEY);
       else if ("Technology".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(TECHNOLOGY_ELEMENT_KEY);
       else if ("Travel".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(TRAVEL_ELEMENT_KEY);
       else if ("Umbrellas".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(UMBRELLAS_ELEMENT_KEY);
       else if ("Wallets".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(WALLETS_ELEMENT_KEY);
       else if ("Watches".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(WATCHES_ELEMENT_KEY);
       else if ("Bras".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(BRAS_ELEMENT_KEY);
       else if ("Briefs".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(BRIEFS_ELEMENT_KEY);
       else if ("Bras".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(BRAS_ELEMENT_KEY);
       else if ("Shapewear".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(SHAPEWEAR_ELEMENT_KEY);
       else if ("Camisoles and Chemises".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(CAMISOLESANDCHEMISES_ELEMENT_KEY);
       else if ("Corsetry".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(CORSETRY_ELEMENT_KEY);
       else if ("Hosiery".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(HOSIERY_ELEMENT_KEY);
       else if ("Lingerie Accessories".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(LINGERIE_ACCESSORIES_ELEMENT_KEY);
       else if ("Robes".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(ROBES_ELEMENT_KEY);
       else if ("Sleepwear".equalsIgnoreCase(section))
            clickIfVisibleInLandingPage(SLEEPWEAR_ELEMENT_KEY);
       else
            throw new UnsupportedOperationException("Invalid sale subcategory specified: "+section);

       NAPSaleListingPage napSaleListingPage =  new NAPSaleListingPage(section+" Sale Listing Page", "/"+section);
       napSaleListingPage.setWebBot(webBot);
       return napSaleListingPage;
    }

    //TODO change or remove this if the region cannot be determined from the page
    public RegionEnum getRegionFromPage() {

        String channelString = webBot.findElement(By.xpath("//div[contains(.,'Channel ')]")).getText();
        String channelName = channelString.substring(channelString.indexOf(" ")+1).toUpperCase();

        return RegionEnum.valueOf(channelName);
    }

    protected boolean clickIfVisibleInLandingPage(final By element) {
        if(!webBot.isElementPresent(element, 3))
            throw new UnsupportedOperationException("Waited for 3 seconds, but element "+element+" was not visible to click");
        webBot.findElement(element).click();
        return true;
    }

    public boolean isElementVisible(String element) {

        if ("Clothing".equalsIgnoreCase(element))
            return webBot.isElementPresent(CLOTHING_ELEMENT_KEY);
        return false;
    }
}
