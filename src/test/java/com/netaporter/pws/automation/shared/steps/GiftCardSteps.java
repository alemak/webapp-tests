package com.netaporter.pws.automation.shared.steps;

import com.netaporter.pws.automation.shared.pages.giftCards.BoxedGiftCardPage;
import com.netaporter.pws.automation.shared.pages.giftCards.GiftCardLandingPage;
import com.netaporter.pws.automation.shared.pages.giftCards.VirtualGiftCardPage;
import com.netaporter.pws.automation.shared.steps.purchasePath.BasePurchasePathStep;
import com.netaporter.pws.automation.shared.utils.CustomerDetailsUtil;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

public class GiftCardSteps extends BasePurchasePathStep {

    @Autowired
    GiftCardLandingPage giftCardLandingPage;

    @Autowired
    VirtualGiftCardPage virtualGiftCardPage;

    @Autowired
    BoxedGiftCardPage printedGiftCardPage;

    @Given("^I am on the gift cards page$")
    public void navigateToGiftCardsPage() {
        giftCardLandingPage.go();
    }

    @Given("^I add a printed gift card of value (.*) to my basket$")
    public void addPrintedGiftCardToBag(int value) {
        printedGiftCardPage.go();
        addPrintedGiftCardToBasket(value);
    }

//    @Given("^I add a (printed|virtual) gift card of value (.*) to my basket$")
//    public void addBoxedOrVirtualGiftCardToBag(String giftCardType, int value) {
//        giftCardLandingPage.go();
//
//        GiftCardType giftCard = GiftCardType.valueOf(giftCardType.toUpperCase());
//        if (giftCard == GiftCardType.PRINTED){
//            giftCardLandingPage.clickShopBoxedGiftCard();
//            addPrintedGiftCardToBasket(value);
//        }
//        else {
//            giftCardLandingPage.clickShopVirtualGiftCard();
//            addVirtualGiftCardToBasket(value);
//        }
//    }

    @Given("^I add a virtual gift card of value (.*) to my basket$")
    public void addVirtualGiftCardToBag(int value) {
        virtualGiftCardPage.go();
        addVirtualGiftCardToBasket(value);
    }

    private void addPrintedGiftCardToBasket(int value) {
        giftCardLandingPage.closeDontMissOutPopup();
        String giftCardSku = printedGiftCardPage.setAmount(value);
        printedGiftCardPage.clickBuyButton();
        scenarioSession.addCollectionData(ITEMS_ADDED_TO_BAG, giftCardSku);
    }

    private void addVirtualGiftCardToBasket(int value) {
        giftCardLandingPage.closeDontMissOutPopup();
        String giftCardSku = virtualGiftCardPage.setAmount(value);
        String randomEmail = CustomerDetailsUtil.generateEmailAddress();
        scenarioSession.putData("Virtual gift recipient", randomEmail);
        virtualGiftCardPage.enterRecipientsEmailAddress(randomEmail);
        virtualGiftCardPage.enterConfirmationRecipientsEmailAddress(randomEmail);
        virtualGiftCardPage.clickBuyButton();
        scenarioSession.addCollectionData(ITEMS_ADDED_TO_BAG, giftCardSku);
    }
}
