package com.netaporter.pws.automation.nap.pages;

import com.netaporter.pws.automation.shared.pages.giftCards.GiftCardLandingPage;
import com.netaporter.test.utils.pages.driver.WaitTime;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class NAPGiftCardLandingPage extends GiftCardLandingPage {

    @Autowired
    public HomePage homePage;

    public NAPGiftCardLandingPage() {
        super("Gift card landing page", "Content/giftvoucherlogin");
    }

    private List<WebElement> getButtonLinks() {
        return webBot.findElements(By.cssSelector("div#landing a"), WaitTime.FOUR);
    }

    @Override
    public void clickShopBoxedGiftCard() {
        getButtonLinks().get(0).click();
    }

    @Override
    public void clickShopVirtualGiftCard() {
        getButtonLinks().get(1).click();
    }

    @Override
    public void go() {
       super.go();
       homePage.closeDontMissOutPopup();
        if ("APAC".equals(webBot.getRegion()))
            homePage.closeApacWelcomeMessage();
    }

}
