package com.netaporter.pws.automation.shared.utils;

import com.google.common.base.MoreObjects;
import com.netaporter.pws.automation.shared.pojos.PaymentDetails;
import com.netaporter.test.utils.enums.CardType;
import com.netaporter.test.utils.enums.RegionEnum;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public final class CardPaymentDetails {

	public static final CardPaymentDetails VISA_CREDIT = new CardPaymentDetails(CardType.VISA_CREDIT_CARD, new PaymentDetails("Visa", "4444444444444448", "TestName", "444", "02", "19"), new PaymentDetails("Visa", "1000070000000001", "TestName", "444", "02", "19"), new PaymentDetails("Visa", "4242425000000009", "TestName", "444", "04", "19"));
	public static final CardPaymentDetails VISA_ELECTRON = new CardPaymentDetails(CardType.VISA_ELECTRON, new PaymentDetails("Visa", "4444333322221111", "TestName", "444", "02", "19"), new PaymentDetails("Visa Electron", "1000070000000001", "TestName", "444", "02", "19"), new PaymentDetails("Visa Electron", "4508751100000006", "TestName", "444", "04", "19"));
	public static final CardPaymentDetails VISA_DEBIT = new CardPaymentDetails(CardType.VISA_DEBIT, new PaymentDetails("Visa", "4444444444444448", "TestName", "444", "02", "19"), new PaymentDetails("Visa", "1000070000000001", "TestName", "444", "02", "19"), new PaymentDetails("Visa", "4539792100000003", "TestName", "444", "04", "19"));
    public static final CardPaymentDetails MASTER_CARD = new CardPaymentDetails(CardType.MASTER_CARD, new PaymentDetails("mastercard", "5500005555555559", "TestName", "547", "03", "19"), new PaymentDetails("mastercard", "5573470000000001", "TestName", "547", "03", "19"), new PaymentDetails("mastercard", "1000011000000005", "TestName", "444", "04", "19"));
    public static final CardPaymentDetails AMERICAN_EXPRESS = new CardPaymentDetails(CardType.AMERICAN_EXPRESS, new PaymentDetails("amex", "371449635398431", "TestName", "3434", "03", "19"), new PaymentDetails("amex", "343434100000006", "TestName", "3434", "03", "19"), new PaymentDetails("amex", "343434200000005", "TestName", "3434", "03", "19"));
    public static final CardPaymentDetails JCB = new CardPaymentDetails(CardType.JCB, null, new PaymentDetails("JCB", "3528000000000007", "TestName", "352", "02", "19"), new PaymentDetails("JCB", "3528000000000007", "TestName", "444", "04", "19"));
    public static final CardPaymentDetails MAESTRO = new CardPaymentDetails(CardType.MAESTRO, null, new PaymentDetails("Maestro", "5001630100011123", "TestName", "633", "02", "19", "1"), new PaymentDetails("Maestro", "6799998900000000014", "TestName", "679", "02", "19", "1"));
    public static final CardPaymentDetails MAESTRO_3D_SECURE = new CardPaymentDetails(CardType.MAESTRO_3D_SECURE, null, new PaymentDetails("Maestro", "5001630100011123", "TestName", "679", "01", "19", "1"), new PaymentDetails("Maestro", "6799998900000000014", "TestName", "679", "01", "19", "1"));
    public static final CardPaymentDetails DISCOVER = new CardPaymentDetails(CardType.DISCOVER, new PaymentDetails("Discover", "6011000995500000", "TestName", "679", "01", "19"), null, null);

	public static final Map<CardType, CardPaymentDetails> cardTypeDetails = new HashMap<>();

	static {
        cardTypeDetails.put(VISA_CREDIT.getType(), VISA_CREDIT);
		cardTypeDetails.put(VISA_ELECTRON.getType(), VISA_ELECTRON);
		cardTypeDetails.put(VISA_DEBIT.getType(), VISA_DEBIT);
		cardTypeDetails.put(MASTER_CARD.getType(), MASTER_CARD);
		cardTypeDetails.put(AMERICAN_EXPRESS.getType(), AMERICAN_EXPRESS);
		cardTypeDetails.put(JCB.getType(), JCB);
		cardTypeDetails.put(MAESTRO.getType(), MAESTRO);
		cardTypeDetails.put(MAESTRO_3D_SECURE.getType(), MAESTRO_3D_SECURE);
        cardTypeDetails.put(DISCOVER.getType(), DISCOVER);

    }

	public static CardPaymentDetails getCardPaymentDetails(String cardType) {
		return cardTypeDetails.get(CardType.valueOf(cardType));
	}

	private final CardType type;
	private final PaymentDetails amDetails;
	private final PaymentDetails intlDetails;
	private final PaymentDetails apacDetails;

	private CardPaymentDetails(final CardType type, final PaymentDetails amDetails, final PaymentDetails intlDetails, final PaymentDetails apacDetails) {
		this.type = type;
		this.amDetails = amDetails;
		this.intlDetails = intlDetails;
		this.apacDetails = apacDetails;
	}

    public static int securityNumberLength(CardType selectedCardType) {
        return selectedCardType == CardType.AMERICAN_EXPRESS ? 4 : 3;
    }

    private PaymentDetails getAmDetails() {
		if(amDetails == null) {
			throw new RuntimeException("Card isn't supported in AM");
		}
		return amDetails;
	}

	private PaymentDetails getIntlDetails() {
		if(intlDetails == null) {
			throw new RuntimeException("Card isn't supported in INTL");
		}
		return intlDetails;
	}

	private PaymentDetails getApacDetails() {
		if(apacDetails == null) {
			throw new RuntimeException("Card isn't supported in APAC");
		}
		return apacDetails;
	}

	public PaymentDetails getPaymentDetails(String region) {
		if(region.equals(RegionEnum.AM.name())) {
			return getAmDetails();
		} else if (region.equals(RegionEnum.APAC.name())) {
			return getApacDetails();
		} else if (region.equals(RegionEnum.INTL.name())) {
			return getIntlDetails();
		}
		throw new IllegalArgumentException("Unknown region...");
	}

	public CardType getType() {
		return type;
	}


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("cardType", type).add("am", amDetails).add("intl", intlDetails).add("apac", apacDetails).toString();
    }

    /*
       Prashant.Ramcharan@net-a-porter.com
       Prefer to store cards in a stack so we can discard previously used cards (situations where we retry payment)
       Note. Keep a low stack of card numbers so payment retries can be manageable
    */
    public static HashMap<String, Stack<String>> getPaymentCardCollection(){
        HashMap<String, Stack<String>> paymentCardCollection = new HashMap<String, Stack<String>>();
        Stack<String> visaCards = new Stack<String>();
        visaCards.push("4021935400100818");
        visaCards.push("4929498311405001");
        visaCards.push("4132220000000015");
        paymentCardCollection.put("visa", visaCards);

        Stack<String> masterCards = new Stack<String>();
        masterCards.push("5120790000000158");
        masterCards.push("5120790000000117");
        masterCards.push("5120790000000182");
        paymentCardCollection.put("mastercard", masterCards);

        Stack<String> amexCards = new Stack<String>();
        amexCards.push("341111000000177");
        amexCards.push("341111000000235");
        amexCards.push("341111000000250");
        paymentCardCollection.put("amex", amexCards);

        return paymentCardCollection;
    }
}
