package com.netaporter.pws.automation.shared.pages.giftCards;

import com.netaporter.test.utils.pages.AbstractPage;
import com.netaporter.test.utils.pages.driver.WaitTime;
import com.netaporter.test.utils.pages.exceptions.PageElementNotFoundException;
import com.netaporter.test.utils.pages.regionalisation.RegionaliseWithRegionName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public abstract class GiftCardLandingPage extends AbstractPage {

    private By EMAIL_SIGNUP_POPUP = By.className("email_signup_popup_close");

    public GiftCardLandingPage(String pageName, String path) {
        super(pageName, path);
        setRegionalisePathBehavior(new RegionaliseWithRegionName());
    }

    public abstract void clickShopBoxedGiftCard();
    public abstract void clickShopVirtualGiftCard();

    /*
    Prashant.Ramcharan@net-a-porter.com
    Removing dependency on url re-construction. Use the DOM instead.
     */
    public void goToGiftCardPage(){
        String giftCardUrl = webBot.executeJavascript("($('a[href*=\"giftvoucherlogin\"]')[0]).href");
        if (giftCardUrl != null){
            webBot.getDriver().get(giftCardUrl);
            return;
        }
        throw new RuntimeException(String.format("This Net-A-Porter group brand (%s) does not support / contain a link to add gift cards.", webBot.getBaseUrl()));
    }

    public void closeDontMissOutPopup() {
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement dontMissOutPopupElement;
        try {
            dontMissOutPopupElement = webBot.findElement(EMAIL_SIGNUP_POPUP, WaitTime.TWO);
        }
        catch (PageElementNotFoundException e) {
            //element has disappeared, skip the rest
            return;
        }

        try {
            webBot.executeScript("arguments[0].scrollIntoView(true);", dontMissOutPopupElement);
            dontMissOutPopupElement.click();
        }
        catch (WebDriverException e) {
            try {
                //popup sometimes needs a while to crawl into its final position on the page
                Thread.sleep(700);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            webBot.executeScript("arguments[0].scrollIntoView(true);", dontMissOutPopupElement);
            dontMissOutPopupElement.click();
        }
        try {
            webBot.waitExplicitly(WaitTime.TWO);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}