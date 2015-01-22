package com.netaporter.pws.automation.napmobile.cucumber.steps;

import com.netaporter.pws.automation.napmobile.components.footer.FooterMobileComponent;
import com.netaporter.pws.automation.napmobile.components.header.HeaderMobileComponent;
import com.netaporter.pws.automation.napmobile.components.productlist.*;
import com.netaporter.pws.automation.napmobile.pages.AbstractNapMobilePage;
import com.netaporter.pws.automation.napmobile.pages.NAPProductDetailsMobilePage;
import com.netaporter.pws.automation.napmobile.pages.NAPShoppingBagMobilePage;
import com.netaporter.pws.automation.napmobile.pages.designers.DesignersMobilePage;
import com.netaporter.pws.automation.napmobile.pages.home.HomeMobilePage;
import com.netaporter.pws.automation.napmobile.pages.purchasePath.*;
import com.netaporter.pws.automation.napmobile.pages.shop.*;
import com.netaporter.pws.automation.napmobile.pages.sporter.NapSportLandingMobilePage;
import com.netaporter.pws.automation.napmobile.pages.whatsnew.WhatsNewMobilePage;
import com.netaporter.pws.automation.shared.components.AddressComponent;
import com.netaporter.test.utils.cucumber.steps.LegacyWebAppBaseStep;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseNapMobileSteps extends LegacyWebAppBaseStep {

    /* Components
    --------------------------------------*/
    @Autowired
    protected FilterMobileComponent filterMobileComponent;

    @Autowired
    protected FooterMobileComponent footerMobileComponent;

    @Autowired
    protected HeaderMobileComponent headerMobileComponent;

    @Autowired
    protected PaginationMobileComponent paginationMobileComponent;

    @Autowired
    protected ProductlistMobileComponent productlistMobileComponent;

    @Autowired
    protected SortbyMobileComponent sortByMobileComponent;

    @Autowired
    protected TitleMobileComponent titleMobileComponent;

    /* Pages
    --------------------------------------*/
    @Autowired
    protected HomeMobilePage homeMobilePage;

    @Autowired
    protected DesignersMobilePage designersMobilePage;

    @Autowired
    protected ShopMobilePage shopMobilePage;

    @Autowired
    protected ShopClothingMobilePage shopClothingMobilePage;

    @Autowired
    protected ShopActivewearMobilePage shopActivewearMobilePage;

    @Autowired
    protected ShopShortsMobilePage shopShortsMobilePage;

    @Autowired
    protected ShopTopsMobilePage shopTopsMobilePage;

    @Autowired
    protected ShopBagsMobilePage shopBagsMobilePage;

    @Autowired
    protected ShopBeltbagsMobilePage shopBeltbagsMobilePage;

    @Autowired
    protected ShopClutchbagsMobilePage shopClutchbagsMobilePage;

    @Autowired
    protected ShopBoxMobilePage shopBoxMobilePage;

    @Autowired
    protected ShopShoesMobilePage shopShoesMobilePage;

    @Autowired
    protected ShopPumpsMobilePage shopPumpsMobilePage;

    @Autowired
    protected ShopAccessoriesMobilePage shopAccessoriesMobilePage;

    @Autowired
    protected ShopFinejewelryMobilePage shopFinejewelryMobilePage;

    @Autowired
    protected ShopLingerieMobilePage shopLingerieMobilePage;

    @Autowired
    protected ShopCorsetryMobilePage shopCorsetryMobilePage;

    @Autowired
    protected ShopBeautyMobilePage shopBeautyMobilePage;

    @Autowired
    protected ShopMakeupMobilePage shopMakeupMobilePage;

    @Autowired
    protected WhatsNewMobilePage whatsNewMobilePage;

    @Autowired
    protected NapSportLandingMobilePage napsportLandingMobilePage;

    @Autowired
    protected AddressComponent addressComponent;

    @Autowired
    protected NAPProductDetailsMobilePage napProductDetailsMobilePage;

    @Autowired
    protected NAPShoppingBagMobilePage napShoppingBagMobilePage;

    @Autowired
    protected NAPPurchasePathSignInMobilePage napPurchasePathSignInMobilePage;

    @Autowired
    protected NAPPurchasePathShippingAddressMobilePage napPurchasePathShippingAddressMobilePage;

    @Autowired
    protected NAPPurchasePathShippingOptionsMobilePage napPurchasePathShippingOptionsMobilePage;

    @Autowired
    protected NAPPurchasePathPaymentSummaryMobilePage napPurchasePathPaymentSummaryMobilePage;

    @Autowired
    protected NAPPurchasePathOrderConfirmationMobilePage napPurchasePathOrderConfirmationMobilePage;


    public AbstractNapMobilePage getCurrentNapPage() {
        return (AbstractNapMobilePage)webBot.getCurrentPage();
    }
}
