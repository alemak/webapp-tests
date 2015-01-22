package com.netaporter.pws.automation.nap.enums;

import com.google.common.collect.Lists;
import com.netaporter.pws.automation.nap.pages.AbstractNAPProductListPage;
import org.openqa.selenium.By;

import java.util.List;

/**
 * Created by ocsiki on 29/09/2014.
 */
public enum ImageViewEnum {

    PRODUCT(AbstractNAPProductListPage.PRODUCT_VIEW_ELEMENT, "_in_sl"), OUTFIT(AbstractNAPProductListPage.OUTFIT_VIEW_ELEMENT, "_ou_sl;_fr_sl;_e1_sl;_e2_sl;_bk_sl;");

    //this locator matches all product images from a product listing page
    public static final By PRODUCT_IMAGE_CSS_CLASS_LOCATOR = By.className("product-image");

    private final By locator;
    private final String urlSegments;

    ImageViewEnum(By locator, String urlSegments) {
        this.locator = locator;
        this.urlSegments = urlSegments;
    }

    public By getLocator() {
        return locator;
    }

    public List<String> getUrlSegments() {
        return Lists.newArrayList(urlSegments.split(";"));
    }
}