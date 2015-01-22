package com.netaporter.pws.automation.shared.steps;


import com.netaporter.pws.automation.shared.pojos.Promotions.EventRule;
import com.netaporter.pws.automation.shared.pojos.Promotions.Promotion;
import com.netaporter.pws.automation.shared.steps.purchasePath.BasePurchasePathStep;
import com.netaporter.pws.automation.shared.utils.IPromotionsAPI;
import com.netaporter.test.client.product.pojos.ShippingMethod;
import com.netaporter.test.utils.enums.RegionEnum;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.When;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PromotionServiceSteps extends BasePurchasePathStep {

    @Autowired
    IPromotionsAPI promotionClient;

    private Promotion thePromotion;
    private List<Integer> previouslyEnabledPromotions;

    @Before("@promotions")
    public void beforePromotionTest(){
        // do the disable step here
        previouslyEnabledPromotions = promotionClient.disableAllEnabledPromotions();
    }

    @Before("@promotions=INTL,@promotions=AM,@promotions=APAC")
    public void setRegionAndDisablePromotions(Scenario scenario) {
        RegionSettingCommonSteps.regionProperty = System.getProperty("region");
        for(String tagname: scenario.getSourceTagNames()){
            if(tagname.contains("@promotions=")){
                String reg = tagname.replaceFirst("@promotions=","");
                setRegion(RegionEnum.valueOf(reg).name());
                System.setProperty("region", RegionEnum.valueOf(reg).name());
                break;
            }
        }
        beforePromotionTest();
    }

    @After("@promotions=INTL,@promotions=AM,@promotions=APAC")
    public void revertRegionAfterPromotionsTest() {
        afterPromotionTest();
        if(RegionSettingCommonSteps.regionProperty!=null)
            System.setProperty("region",RegionSettingCommonSteps.regionProperty);
    }

    @After("@promotions")
    public void afterPromotionTest(){
        // do the delete and clean up here
        if(thePromotion.getEventId() == null)
            throw new UnsupportedOperationException("No promotion is configured");

        //delete any shipping restrictions before deleting the promotion - there is no cascade delete
        if(promotionClient.checkIsPromotionRestrictedByShippingMethod(thePromotion)) {
            promotionClient.deleteShippingRestrictionsInPromotion(thePromotion.getEventId());
        }
        //delete the promotion
        promotionClient.deletePromotion(thePromotion.getEventId());

        // enable all the disabled promotions again
        promotionClient.enablePromotions(previouslyEnabledPromotions);
    }

    @When("^The promotion (is|is not) applied to my confirmed order")
    public void checkPromotionIsAppliedToOrder(String condition) {

        if(thePromotion.getEventId() == null)
            throw new UnsupportedOperationException("No promotion is configured");

        Integer orderId = (Integer) scenarioSession.getData("latestOrderId");

        if("is".equals(condition))
            assertTrue(promotionClient.promotionIsAppliedToOrder(orderId, thePromotion.getEventId()));
        else
            assertFalse(promotionClient.promotionIsAppliedToOrder(orderId, thePromotion.getEventId()));

    }

    @When("^I configure a (past|present|future) global percentage discount promotion with a discount of (.*) percent$")
    public void configureGlobalPercentageDiscountPromotion(String dateRange, Integer percentageDiscount) {
        createPromotion(generatePromotionTitle(), Promotion.PromotionType.PERCENTAGE_DISCOUNT, percentageDiscount);

        if("past".equals(dateRange))
            setPastPromotionDateRange();
        else if ("present".equals(dateRange))
            setPresentPromotionDateRange();
        else if ("future".equals(dateRange))
            setFuturePromotionDateRange();

    }

    @When("^I configure a (past|present|future) global free shipping promotion$")
    public void configureGlobalFreeShippingPromotion(String dateRange) {
        createPromotion(generatePromotionTitle(), Promotion.PromotionType.FREE_SHIPPING);

        if("past".equals(dateRange))
            setPastPromotionDateRange();
        else if ("present".equals(dateRange))
            setPresentPromotionDateRange();
        else if ("future".equals(dateRange))
            setFuturePromotionDateRange();

    }

    @When("^I create my configured promotion$")
    public void createConfiguredPromotion() {
        promotionClient.createPromotion(thePromotion);
    }

    @When("^I set a (.*) rule with a value of (.*)$")
    public void createAnEventRule(String ruleType, String ruleValues) {
        EventRule newEventRule = new EventRule(EventRule.RuleType.valueOf(ruleType));
        String[] ruleValuesArray = ruleValues.split(",");
        for (String ruleValue : ruleValuesArray){
            newEventRule.getRuleValues().add(ruleValue.trim());
        }
        thePromotion.getEventRules().add(newEventRule);
    }

    @When("^I delete the promotion$")
    public void deleteAPromotion() {

        if(thePromotion.getEventId() == null)
            throw new UnsupportedOperationException("No promotion is configured");

        promotionClient.deletePromotion(thePromotion.getEventId());
    }

    @When("^I enable the promotion$")
    public void enableAPromotion() {

        if(thePromotion.getEventId() == null)
            throw new UnsupportedOperationException("No promotion is configured");

        promotionClient.enablePromotion(thePromotion.getEventId());
    }

    @When("^I disable the promotion$")
    public void disableAPromotion() {

        if(thePromotion.getEventId() == null)
            throw new UnsupportedOperationException("No promotion is configured");

        promotionClient.disablePromotion(thePromotion.getEventId());
    }

    @When("^I disable all enabled promotions$")
    public void disableAllEnabledPromotions() {
        previouslyEnabledPromotions = promotionClient.disableAllEnabledPromotions();
    }

    @When("^I enable all previously enabled promotions$")
    public void enabledAllPreviouslyEnabledPromotions() {
        promotionClient.enablePromotions(previouslyEnabledPromotions);
    }

    @When("^I add a product priced (over|under) the basket threshold (.*) to the promotion$")
    public void addSpecificPricedPidToPromotion(String condition, BigDecimal thresholdValue) {

        if(thePromotion.getEventId() == null)
            throw new UnsupportedOperationException("You must create a promotion before adding products to it");

        Integer thePid = null;

        if("over".equals(condition))
            thePid = productDataAccess.getLegacyDBClient().findInStockProductWithPriceThreshold(webBot.getRegionEnum(), thresholdValue, true);
        else if ("under".equals(condition))
            thePid = productDataAccess.getLegacyDBClient().findInStockProductWithPriceThreshold(webBot.getRegionEnum(), thresholdValue, false);

        promotionClient.addProductToPromotion(thePid, thePromotion.getEventId());
        scenarioSession.putData("promotionPid", thePid);
    }

    @When("^I add the logged in customer to the promotion$")
    public void addCustomerToPromotion() {

        if(thePromotion.getEventId() == null)
            throw new UnsupportedOperationException("You must create a promotion before adding customers to it");

        String email = ((String)scenarioSession.getData("registeredEmailAddress"));
        Integer customerId = productDataAccess.getLegacyDBClient().getCustomerIdByEmail(webBot.getRegionEnum(), email);
        promotionClient.addCustomerToPromotion(customerId, thePromotion.getEventId());
    }

    @When("^I restrict the promotion to shipping method (.*)$")
    public void restrictPromotionToShippingOption(List<String> shippingMethodNames) {

        if(thePromotion.getEventId() == null)
            throw new UnsupportedOperationException("You must create a promotion before adding shipping restrictions to it");

        List<ShippingMethod> shippingMethods = productDataAccess.getLegacyDBClient().getShippingMethods(webBot.getRegionEnum());
        for (ShippingMethod shippingMethod : shippingMethods) {
            if(shippingMethodNames.contains(shippingMethod.getName())) {
                promotionClient.addShippingMethodRestrictionToPromotion(thePromotion, shippingMethod);
            }
        }
    }

    @When("^I restrict the promotion to shipping country (.*)$")
    public void restrictPromotionToShippingCountry(List<String> shippingCountries) {

        if(thePromotion.getEventId() == null) {
            throw new UnsupportedOperationException("You must create a promotion before adding shipping country restrictions");
        }

        Map<String,String> countryCodeMap = productDataAccess.getLegacyDBClient().getCountryCodeAndName(webBot.getRegionEnum(), shippingCountries);
        Set<String> countryCodesSet = countryCodeMap.keySet();
        for (String countryCode : countryCodesSet) {
            promotionClient.addShippingCountryRestrictionToPromotion(thePromotion,countryCode);
        }

    }

    private void createPromotion(String title, Promotion.PromotionType type, Integer percentageDiscount, String priceGroup) {
        thePromotion = new Promotion();
        thePromotion.setTitle(title);
        thePromotion.setInternalTitle(title);
        thePromotion.setSubtitle(title + "SubTitle");
        thePromotion.setEnabled(true);
        if (percentageDiscount != null)
            thePromotion.setPercentageDiscount(percentageDiscount);
        thePromotion.setPriceGroup(priceGroup);
        thePromotion.setPromotionType(type);
    }

    private void createPromotion(String title, Promotion.PromotionType type, Integer percentageDiscount) {
        createPromotion(title, type, percentageDiscount, Promotion.FULLPRICE_PRICEGROUP);
    }

    private void createPromotion(String title, Promotion.PromotionType type) {
        createPromotion(title, type, null);
    }

    private void setPastPromotionDateRange() {
        thePromotion.setStartDate(DateTime.now().minusDays(2).toDate());
        thePromotion.setEndDate(DateTime.now().minusDays(1).toDate());
    }

    private void setPresentPromotionDateRange() {
        thePromotion.setStartDate(DateTime.now().minusDays(1).toDate());
        thePromotion.setEndDate(DateTime.now().plusDays(1).toDate());
    }

    private void setFuturePromotionDateRange() {
        thePromotion.setStartDate(DateTime.now().plusDays(1).toDate());
        thePromotion.setEndDate(DateTime.now().plusDays(2).toDate());
    }

    protected static final String generatePromotionTitle() {
        return "testPromotion" + new Date().getTime();
    }

    @When("^I add a NAP product priced (over|under) the basket threshold (.*) to the promotion$")
    public void addSpecificPricedNAPPidToPromotion(String condition, BigDecimal thresholdValue) {

        if(thePromotion.getEventId() == null)
            throw new UnsupportedOperationException("You must create a promotion before adding products to it");

        Integer thePid = null;
        do {
            if ("over".equals(condition))
                thePid = productDataAccess.getLegacyDBClient().findInStockProductWithPriceThreshold(webBot.getRegionEnum(), thresholdValue, true);
            else if ("under".equals(condition))
                thePid = productDataAccess.getLegacyDBClient().findInStockProductWithPriceThreshold(webBot.getRegionEnum(), thresholdValue, false);
        } while (!productDataAccess.Solr_isPidOrSkuInStock(webBot.getSalesChannelByBrandAndRegion(), thePid.toString()));

        promotionClient.addProductToPromotion(thePid, thePromotion.getEventId());
        scenarioSession.putData("promotionPid", thePid);
    }
}