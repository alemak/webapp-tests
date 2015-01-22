package com.netaporter.pws.automation.shared.pages.giftCards;

import com.netaporter.test.utils.enums.RegionEnum;
import com.netaporter.test.utils.pages.regionalisation.RegionalisePathBehavior;
import com.netaporter.test.utils.pages.regionalisation.RegionaliseWithRegionName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import java.util.List;

public class VirtualGiftCardPage extends AbstractGiftCardPage {

    public VirtualGiftCardPage() {
        super("Virtual gift card page", "giftcards/virtualcards.nap");
        setRegionalisePathBehavior(new RegionaliseWithRegionName());
    }

    public void enterRecipientsEmailAddress(String text) {
        webBot.findElement(By.id("toEmail")).sendKeys(text);
    }

    public void enterConfirmationRecipientsEmailAddress(String text) {
        webBot.findElement(By.id("confirmToEmail")).sendKeys(text);
    }
}
